/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.staticexport.internal;

import com.liferay.layout.renderer.LayoutPreviewRenderer;
import com.liferay.layout.util.LayoutServiceContextHelper;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.frontend.hashed.files.HashedFilesUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.DummyHttpServletResponse;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.site.staticexport.StaticSiteExport;
import com.liferay.site.staticexport.StaticSiteExportLayout;
import com.liferay.site.staticexport.StaticSiteExportReport;
import com.liferay.site.staticexport.StaticSiteExportResource;
import com.liferay.site.staticexport.StaticSiteExporter;

import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(service = StaticSiteExporter.class)
public class StaticSiteExporterImpl implements StaticSiteExporter {

	@Override
	public StaticSiteExport export(long groupId, Set<Locale> locales)
		throws PortalException {

		return _export(
			groupId, _layoutService.getLayouts(groupId, false), locales);
	}

	@Override
	public StaticSiteExport export(
			long groupId, Set<Long> layoutIds, Set<Locale> locales)
		throws PortalException {

		List<Layout> layouts = new ArrayList<>();

		for (long layoutId : layoutIds) {
			layouts.add(_layoutService.getLayout(groupId, false, layoutId));
		}

		return _export(groupId, layouts, locales);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private void _addPortalHostName(
		String hostName, Set<String> portalHostNames) {

		if (Validator.isNotNull(hostName)) {
			portalHostNames.add(
				StringUtil.toLowerCase(StringUtil.trim(hostName)));
		}
	}

	private StaticSiteExport _export(
			long groupId, List<Layout> layouts, Set<Locale> locales)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		Company company = _companyLocalService.getCompany(group.getCompanyId());

		try (AutoCloseable autoCloseable =
				_layoutServiceContextHelper.getServiceContextAutoCloseable(
					company)) {

			List<StaticSiteExportReport.Failure> layoutFailures =
				new ArrayList<>();

			List<StaticSiteExportLayout> staticSiteExportLayouts =
				_exportStaticSiteExportLayouts(
					groupId, layoutFailures, layouts, locales);

			Map<StaticSiteExportLayout, StaticSiteExportDocument>
				staticSiteExportDocuments = new LinkedHashMap<>();

			for (StaticSiteExportLayout staticSiteExportLayout :
					staticSiteExportLayouts) {

				staticSiteExportDocuments.put(
					staticSiteExportLayout,
					new StaticSiteExportDocument(
						staticSiteExportLayout.getHTML(), _jsonFactory));
			}

			List<StaticSiteExportReport.Failure> resourceFailures =
				new ArrayList<>();

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			String portalURL = _portal.getPortalURL(
				company.getVirtualHostname(),
				_portal.getPortalServerPort(false), false);

			Set<String> portalHostNames = _getPortalHostNames(
				company, group, portalURL);

			List<StaticSiteExportResource> staticSiteExportResources =
				_fetchStaticSiteExportResources(
					serviceContext.getRequest(), locales, portalHostNames,
					portalURL, resourceFailures,
					staticSiteExportDocuments.values());

			StaticSiteExportURLRewriter staticSiteExportURLRewriter =
				new StaticSiteExportURLRewriter(
					_getPagePaths(group, staticSiteExportLayouts),
					portalHostNames,
					_getResourcePaths(staticSiteExportResources));

			return new StaticSiteExportImpl(
				_rewriteLayouts(
					staticSiteExportDocuments, staticSiteExportURLRewriter),
				new StaticSiteExportReport(layoutFailures, resourceFailures),
				staticSiteExportResources);
		}
		catch (PortalException portalException) {
			throw portalException;
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}
	}

	private List<StaticSiteExportLayout> _exportStaticSiteExportLayouts(
			long groupId, List<StaticSiteExportReport.Failure> layoutFailures,
			List<Layout> layouts, Set<Locale> locales)
		throws PortalException {

		List<StaticSiteExportLayout> staticSiteExportLayouts =
			new ArrayList<>();

		Locale siteDefaultLocale = _portal.getSiteDefaultLocale(groupId);

		for (Layout layout : _getExportableLayouts(layouts)) {
			long segmentsExperienceId =
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(layout.getPlid());

			for (Locale locale : locales) {
				String friendlyURL = layout.getFriendlyURL(locale);

				try {
					staticSiteExportLayouts.add(
						new StaticSiteExportLayout(
							_layoutPreviewRenderer.render(
								layout, locale, segmentsExperienceId),
							locale,
							_getPath(friendlyURL, locale, siteDefaultLocale),
							layout.getPlid()));
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn("Unable to render " + friendlyURL, exception);
					}

					layoutFailures.add(
						new StaticSiteExportReport.Failure(
							exception.getMessage(), friendlyURL));
				}
			}
		}

		return staticSiteExportLayouts;
	}

	private StaticSiteExportResource _fetchStaticSiteExportResource(
		boolean optional, List<StaticSiteExportReport.Failure> resourceFailures,
		StaticSiteExportResourceFetcher staticSiteExportResourceFetcher,
		String url) {

		try {
			File file = staticSiteExportResourceFetcher.fetch(url);

			if (file != null) {
				StaticSiteExportURL staticSiteExportURL =
					new StaticSiteExportURL(url);

				return new StaticSiteExportResource(
					file, staticSiteExportURL.getArchivePath(), url);
			}

			if (!optional) {
				resourceFailures.add(
					new StaticSiteExportReport.Failure(
						"No servlet serves the resource", url));
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to fetch " + url, exception);
			}

			if (!optional) {
				resourceFailures.add(
					new StaticSiteExportReport.Failure(
						exception.getMessage(), url));
			}
		}

		return null;
	}

	private List<StaticSiteExportResource> _fetchStaticSiteExportResources(
		HttpServletRequest httpServletRequest, Set<Locale> locales,
		Set<String> portalHostNames, String portalURL,
		List<StaticSiteExportReport.Failure> resourceFailures,
		Collection<StaticSiteExportDocument> staticSiteExportDocuments) {

		List<StaticSiteExportResource> staticSiteExportResources =
			new ArrayList<>();

		Set<String> fetchedURLs = new HashSet<>();
		Set<String> moduleNames = new HashSet<>();

		StaticSiteExportBundleResourceResolver
			staticSiteExportBundleResourceResolver =
				new StaticSiteExportBundleResourceResolver(_bundleContext);

		StaticSiteExportResourceFetcher staticSiteExportResourceFetcher =
			new StaticSiteExportResourceFetcher(
				httpServletRequest, new DummyHttpServletResponse(), portalURL,
				ServletContextPool.get(_portal.getServletContextName()),
				staticSiteExportBundleResourceResolver);

		for (StaticSiteExportDocument staticSiteExportDocument :
				staticSiteExportDocuments) {

			_fetchStaticSiteExportResources(
				fetchedURLs, locales, moduleNames, false, portalHostNames,
				resourceFailures, staticSiteExportBundleResourceResolver,
				staticSiteExportResourceFetcher, staticSiteExportResources,
				_getResourceURLs(portalHostNames, staticSiteExportDocument));
			_fetchStaticSiteExportResources(
				fetchedURLs, locales, moduleNames, true, portalHostNames,
				resourceFailures, staticSiteExportBundleResourceResolver,
				staticSiteExportResourceFetcher, staticSiteExportResources,
				_getModuleURLs(
					staticSiteExportDocument.getHTML(), locales, moduleNames,
					staticSiteExportBundleResourceResolver));
		}

		return staticSiteExportResources;
	}

	private void _fetchStaticSiteExportResources(
		Set<String> fetchedURLs, Set<Locale> locales, Set<String> moduleNames,
		boolean optional, Set<String> portalHostNames,
		List<StaticSiteExportReport.Failure> resourceFailures,
		StaticSiteExportBundleResourceResolver
			staticSiteExportBundleResourceResolver,
		StaticSiteExportResourceFetcher staticSiteExportResourceFetcher,
		List<StaticSiteExportResource> staticSiteExportResources,
		Set<String> urls) {

		for (String url : urls) {
			if (!fetchedURLs.add(url)) {
				continue;
			}

			StaticSiteExportResource staticSiteExportResource =
				_fetchStaticSiteExportResource(
					optional, resourceFailures, staticSiteExportResourceFetcher,
					url);

			if (staticSiteExportResource == null) {
				continue;
			}

			staticSiteExportResources.add(staticSiteExportResource);

			_fetchStaticSiteExportResources(
				fetchedURLs, locales, moduleNames, true, portalHostNames,
				resourceFailures, staticSiteExportBundleResourceResolver,
				staticSiteExportResourceFetcher, staticSiteExportResources,
				_getNestedResourceURLs(
					locales, moduleNames, portalHostNames,
					staticSiteExportBundleResourceResolver,
					staticSiteExportResource));
		}
	}

	private List<Layout> _getExportableLayouts(List<Layout> layouts) {
		List<Layout> exportableLayouts = new ArrayList<>();

		for (Layout layout : layouts) {
			if (layout.isHidden() || !layout.isPublished() ||
				layout.isSystem() ||
				(layout.getStatus() != WorkflowConstants.STATUS_APPROVED) ||
				!Objects.equals(
					layout.getType(), LayoutConstants.TYPE_CONTENT)) {

				continue;
			}

			exportableLayouts.add(layout);
		}

		return exportableLayouts;
	}

	private Set<String> _getModuleURLs(
		String content, Set<Locale> locales, Set<String> moduleNames,
		StaticSiteExportBundleResourceResolver
			staticSiteExportBundleResourceResolver) {

		Set<String> moduleURLs = new LinkedHashSet<>();

		Matcher matcher = _moduleNamePattern.matcher(content);

		while (matcher.find()) {
			String moduleName = matcher.group(1);

			if (!moduleNames.add(moduleName)) {
				continue;
			}

			for (Locale locale : locales) {
				moduleURLs.add(
					StringBundler.concat(
						"/o/js/language/", LocaleUtil.toLanguageId(locale),
						StringPool.SLASH, moduleName, "/all.js"));
			}

			try {
				for (String resourcePath :
						staticSiteExportBundleResourceResolver.getResourcePaths(
							moduleName, "/__liferay__/")) {

					if (resourcePath.endsWith(".map")) {
						continue;
					}

					if (HashedFilesUtil.containsHash(resourcePath)) {
						resourcePath = HashedFilesUtil.removeHash(resourcePath);
					}

					moduleURLs.add(
						StringBundler.concat("/o/", moduleName, resourcePath));
				}
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to list the resources of " + moduleName,
						exception);
				}
			}
		}

		return moduleURLs;
	}

	private Set<String> _getNestedResourceURLs(
		Set<Locale> locales, Set<String> moduleNames,
		Set<String> portalHostNames,
		StaticSiteExportBundleResourceResolver
			staticSiteExportBundleResourceResolver,
		StaticSiteExportResource staticSiteExportResource) {

		String url = staticSiteExportResource.getURL();

		try {
			if (url.endsWith(".css")) {
				return _getStylesheetResourceURLs(
					FileUtil.read(staticSiteExportResource.getFile()),
					portalHostNames, url);
			}

			if (url.endsWith(".js")) {
				return _getModuleURLs(
					FileUtil.read(staticSiteExportResource.getFile()), locales,
					moduleNames, staticSiteExportBundleResourceResolver);
			}
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to read " + url, ioException);
			}
		}

		return Collections.emptySet();
	}

	private Map<String, String> _getPagePaths(
			Group group, List<StaticSiteExportLayout> staticSiteExportLayouts)
		throws PortalException {

		Map<String, String> pagePaths = new HashMap<>();

		Layout defaultLayout = _layoutLocalService.fetchFirstLayout(
			group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		Locale siteDefaultLocale = _portal.getSiteDefaultLocale(
			group.getGroupId());

		String siteURL =
			_portal.getPathFriendlyURLPublic() + group.getFriendlyURL();

		for (StaticSiteExportLayout staticSiteExportLayout :
				staticSiteExportLayouts) {

			Layout layout = _layoutLocalService.fetchLayout(
				staticSiteExportLayout.getPlid());

			Locale locale = staticSiteExportLayout.getLocale();

			String path = staticSiteExportLayout.getPath();

			List<String> urls = new ArrayList<>();

			urls.add(siteURL + layout.getFriendlyURL(locale));

			if ((defaultLayout != null) &&
				(defaultLayout.getPlid() == layout.getPlid())) {

				urls.add(siteURL);
				urls.add(siteURL + StringPool.SLASH);
			}

			for (String url : urls) {
				if (Objects.equals(locale, siteDefaultLocale)) {
					pagePaths.put(url, path);
				}

				pagePaths.put(
					StringBundler.concat(
						StringPool.SLASH, LocaleUtil.toLanguageId(locale), url),
					path);
				pagePaths.put(
					StringBundler.concat(
						StringPool.SLASH, locale.getLanguage(), url),
					path);
			}
		}

		return pagePaths;
	}

	private String _getPath(
		String friendlyURL, Locale locale, Locale siteDefaultLocale) {

		String path = StringUtil.removeFirst(friendlyURL, StringPool.SLASH);

		if (Objects.equals(locale, siteDefaultLocale)) {
			return path + ".html";
		}

		return StringBundler.concat(
			LocaleUtil.toLanguageId(locale), StringPool.SLASH, path, ".html");
	}

	private Set<String> _getPortalHostNames(
		Company company, Group group, String portalURL) {

		Set<String> portalHostNames = new HashSet<>();

		_addPortalHostName(company.getVirtualHostname(), portalHostNames);

		LayoutSet layoutSet = group.getPublicLayoutSet();

		Map<String, String> virtualHostnames = layoutSet.getVirtualHostnames();

		for (String virtualHostname : virtualHostnames.keySet()) {
			_addPortalHostName(virtualHostname, portalHostNames);
		}

		StaticSiteExportURL staticSiteExportURL = new StaticSiteExportURL(
			portalURL);

		_addPortalHostName(staticSiteExportURL.getHostName(), portalHostNames);

		_addPortalHostName(PropsValues.WEB_SERVER_HOST, portalHostNames);

		for (String validHost : PropsValues.VIRTUAL_HOSTS_VALID_HOSTS) {
			if (!Objects.equals(validHost, StringPool.STAR)) {
				_addPortalHostName(validHost, portalHostNames);
			}
		}

		return portalHostNames;
	}

	private Map<String, String> _getResourcePaths(
		List<StaticSiteExportResource> staticSiteExportResources) {

		Map<String, String> resourcePaths = new HashMap<>();

		for (StaticSiteExportResource staticSiteExportResource :
				staticSiteExportResources) {

			resourcePaths.put(
				staticSiteExportResource.getURL(),
				staticSiteExportResource.getPath());
		}

		return resourcePaths;
	}

	private Set<String> _getResourceURLs(
		Set<String> portalHostNames,
		StaticSiteExportDocument staticSiteExportDocument) {

		Set<String> resourceURLs = new LinkedHashSet<>();

		for (String url : staticSiteExportDocument.getURLs()) {
			if (Validator.isNull(url)) {
				continue;
			}

			StaticSiteExportURL staticSiteExportURL = new StaticSiteExportURL(
				portalHostNames, url);

			if (staticSiteExportURL.isResource()) {
				resourceURLs.add(staticSiteExportURL.getURL());
			}
		}

		return resourceURLs;
	}

	private Set<String> _getStylesheetResourceURLs(
		String content, Set<String> portalHostNames, String url) {

		Set<String> stylesheetResourceURLs = new LinkedHashSet<>();

		StaticSiteExportURL staticSiteExportURL = new StaticSiteExportURL(
			portalHostNames, url);

		Matcher matcher = _stylesheetResourceURLPattern.matcher(content);

		while (matcher.find()) {
			String stylesheetResourceURL = matcher.group(1);

			if (stylesheetResourceURL == null) {
				stylesheetResourceURL = matcher.group(2);
			}

			StaticSiteExportURL resolvedStaticSiteExportURL =
				staticSiteExportURL.resolve(stylesheetResourceURL);

			if (resolvedStaticSiteExportURL != null) {
				stylesheetResourceURLs.add(
					resolvedStaticSiteExportURL.getURL());
			}
		}

		return stylesheetResourceURLs;
	}

	private List<StaticSiteExportLayout> _rewriteLayouts(
		Map<StaticSiteExportLayout, StaticSiteExportDocument>
			staticSiteExportDocuments,
		StaticSiteExportURLRewriter staticSiteExportURLRewriter) {

		List<StaticSiteExportLayout> rewrittenStaticSiteExportLayouts =
			new ArrayList<>();

		for (Map.Entry<StaticSiteExportLayout, StaticSiteExportDocument> entry :
				staticSiteExportDocuments.entrySet()) {

			StaticSiteExportLayout staticSiteExportLayout = entry.getKey();

			StaticSiteExportDocument staticSiteExportDocument =
				entry.getValue();

			staticSiteExportURLRewriter.rewrite(staticSiteExportDocument);

			rewrittenStaticSiteExportLayouts.add(
				new StaticSiteExportLayout(
					staticSiteExportDocument.getHTML(),
					staticSiteExportLayout.getLocale(),
					staticSiteExportLayout.getPath(),
					staticSiteExportLayout.getPlid()));
		}

		return rewrittenStaticSiteExportLayouts;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StaticSiteExporterImpl.class);

	private static final Pattern _moduleNamePattern = Pattern.compile(
		"([a-z0-9][a-z0-9.\\-]*)/__liferay__/");
	private static final Pattern _stylesheetResourceURLPattern =
		Pattern.compile(
			"url\\(\\s*[\"']?([^)\"'\\s]+)|@import\\s+[\"']([^\"']+)");

	private BundleContext _bundleContext;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPreviewRenderer _layoutPreviewRenderer;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private LayoutServiceContextHelper _layoutServiceContextHelper;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}