/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.internal.manager;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.journal.model.JournalArticle;
import com.liferay.layout.admin.kernel.model.LayoutTypePortletConstants;
import com.liferay.object.model.ObjectEntry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.util.LayoutTypeControllerTracker;
import com.liferay.redirect.provider.RedirectProvider;
import com.liferay.site.configuration.manager.SitemapConfigurationManager;
import com.liferay.site.constants.SitemapConstants;
import com.liferay.site.manager.SitemapManager;
import com.liferay.site.provider.SitemapURLProvider;
import com.liferay.site.storage.helper.SitemapStorageHelper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 * @author Vilmos Papp
 */
@Component(service = SitemapManager.class)
public class SitemapManagerImpl implements SitemapManager {

	@Override
	public void addURLElement(
		Element element, String url,
		UnicodeProperties typeSettingsUnicodeProperties, Date modifiedDate,
		String canonicalURL, Map<Locale, String> alternateURLs, long groupId) {

		String path = HttpComponentsUtil.getPath(url);
		String contextPath = _portal.getPathContext();

		if (Validator.isNotNull(contextPath) && path.startsWith(contextPath)) {
			path = path.substring(contextPath.length());
		}

		String friendlyURL = _getFriendlyURL(groupId, path);

		if (friendlyURL.startsWith(StringPool.SLASH)) {
			friendlyURL = friendlyURL.substring(1);
		}

		String fullURL = path;

		if (fullURL.startsWith(StringPool.SLASH)) {
			fullURL = fullURL.substring(1);
		}

		RedirectProvider.Redirect redirect = _redirectProvider.getRedirect(
			groupId, friendlyURL, fullURL, null);

		if (redirect != null) {
			return;
		}

		Element urlElement = element.addElement("url");

		Element locElement = urlElement.addElement("loc");

		locElement.addText(encodeXML(url));

		if (modifiedDate != null) {
			DateFormat iso8601DateFormat = DateUtil.getISO8601Format();

			Element lastmodElement = urlElement.addElement("lastmod");

			lastmodElement.addText(iso8601DateFormat.format(modifiedDate));
		}

		if (typeSettingsUnicodeProperties == null) {
			if (Validator.isNotNull(
					PropsValues.SITES_SITEMAP_DEFAULT_CHANGE_FREQUENCY)) {

				Element changefreqElement = urlElement.addElement("changefreq");

				changefreqElement.addText(
					PropsValues.SITES_SITEMAP_DEFAULT_CHANGE_FREQUENCY);
			}

			if (Validator.isNotNull(
					PropsValues.SITES_SITEMAP_DEFAULT_PRIORITY)) {

				Element priorityElement = urlElement.addElement("priority");

				priorityElement.addText(
					PropsValues.SITES_SITEMAP_DEFAULT_PRIORITY);
			}
		}
		else {
			String changefreq = typeSettingsUnicodeProperties.getProperty(
				"sitemap-changefreq");

			if (Validator.isNotNull(changefreq)) {
				Element changefreqElement = urlElement.addElement("changefreq");

				changefreqElement.addText(changefreq);
			}
			else if (Validator.isNotNull(
						PropsValues.SITES_SITEMAP_DEFAULT_CHANGE_FREQUENCY)) {

				Element changefreqElement = urlElement.addElement("changefreq");

				changefreqElement.addText(
					PropsValues.SITES_SITEMAP_DEFAULT_CHANGE_FREQUENCY);
			}

			String priority = typeSettingsUnicodeProperties.getProperty(
				"sitemap-priority");

			if (Validator.isNotNull(priority)) {
				Element priorityElement = urlElement.addElement("priority");

				priorityElement.addText(priority);
			}
			else if (Validator.isNotNull(
						PropsValues.SITES_SITEMAP_DEFAULT_PRIORITY)) {

				Element priorityElement = urlElement.addElement("priority");

				priorityElement.addText(
					PropsValues.SITES_SITEMAP_DEFAULT_PRIORITY);
			}
		}

		if (alternateURLs != null) {
			for (Map.Entry<Locale, String> entry : alternateURLs.entrySet()) {
				Locale locale = entry.getKey();
				String href = entry.getValue();

				Element alternateURLElement = urlElement.addElement(
					"xhtml:link", "http://www.w3.org/1999/xhtml");

				alternateURLElement.addAttribute("href", href);
				alternateURLElement.addAttribute(
					"hreflang", LocaleUtil.toW3cLanguageId(locale));
				alternateURLElement.addAttribute("rel", "alternate");
			}

			Element alternateURLElement = urlElement.addElement(
				"xhtml:link", "http://www.w3.org/1999/xhtml");

			alternateURLElement.addAttribute("href", canonicalURL);
			alternateURLElement.addAttribute("hreflang", "x-default");
			alternateURLElement.addAttribute("rel", "alternate");
		}

		if (element.attributeValue(_ATTRIBUTE_NAME_PAGE) == null) {
			_removeOldestElement(element, urlElement);

			return;
		}

		_addEntryAndPaginate(element, urlElement);
	}

	@Override
	public String encodeXML(String input) {
		return StringUtil.replace(
			input, new char[] {'&', '<', '>', '\'', '\"'},
			new String[] {"&amp;", "&lt;", "&gt;", "&apos;", "&quot;"});
	}

	@Override
	public Map<Locale, String> getAlternateURLs(
			String canonicalURL, ThemeDisplay themeDisplay, Layout layout)
		throws PortalException {

		return _portal.getAlternateURLs(canonicalURL, themeDisplay, layout);
	}

	@Override
	public long getAssetTypeClassNameId(String assetTypeKey) {
		return GetterUtil.getLong(_assetTypeClassNameIds.get(assetTypeKey));
	}

	@Override
	public Map<Long, String> getAssetTypeKeys() {
		return _assetTypeKeys;
	}

	@Override
	public String getSitemap(
			long groupId, boolean privateLayout, ThemeDisplay themeDisplay)
		throws PortalException {

		return getSitemap(null, groupId, privateLayout, themeDisplay);
	}

	@Override
	public String getSitemap(
			long assetTypeClassNameId, String layoutUuid, long groupId,
			boolean privateLayout, ThemeDisplay themeDisplay)
		throws PortalException {

		return getSitemap(
			assetTypeClassNameId, layoutUuid, groupId, 1, privateLayout,
			themeDisplay);
	}

	@Override
	public String getSitemap(
			long assetTypeClassNameId, String layoutUuid, long groupId,
			int page, boolean privateLayout, ThemeDisplay themeDisplay)
		throws PortalException {

		if (assetTypeClassNameId > 0) {
			long companyId = themeDisplay.getCompanyId();

			SitemapURLProvider sitemapURLProvider =
				_serviceTrackerMap.getService(assetTypeClassNameId);

			if ((sitemapURLProvider == null) ||
				!_sitemapConfigurationManager.xmlSitemapIndexCompanyEnabled(
					companyId) ||
				!StringUtil.equals(
					_sitemapConfigurationManager.xmlSitemapIndexMode(companyId),
					SitemapConstants.INDEX_MODE_ASSET_TYPE) ||
				!sitemapURLProvider.isInclude(companyId, groupId)) {

				return null;
			}

			return _getAssetTypeSitemap(
				assetTypeClassNameId, groupId, page, privateLayout,
				themeDisplay);
		}

		if (Validator.isNull(layoutUuid) &&
			_sitemapConfigurationManager.xmlSitemapIndexCompanyEnabled(
				themeDisplay.getCompanyId())) {

			return _getIndexSitemap(groupId, privateLayout, themeDisplay);
		}

		return _getSitemap(groupId, layoutUuid, privateLayout, themeDisplay);
	}

	@Override
	public String getSitemap(
			String layoutUuid, long groupId, boolean privateLayout,
			ThemeDisplay themeDisplay)
		throws PortalException {

		return getSitemap(0, layoutUuid, groupId, privateLayout, themeDisplay);
	}

	@Override
	public InputStream getSitemapInputStream(
			String assetTypeKey, String layoutUuid, long groupId, int page,
			boolean privateLayout, ThemeDisplay themeDisplay)
		throws PortalException {

		long companyId = themeDisplay.getCompanyId();

		if (_sitemapConfigurationManager.xmlSitemapIndexCompanyEnabled(
				companyId) &&
			StringUtil.equals(
				_sitemapConfigurationManager.xmlSitemapIndexMode(companyId),
				SitemapConstants.INDEX_MODE_ASSET_TYPE)) {

			try {
				if (assetTypeKey == null) {
					return _sitemapStorageHelper.getSitemapInputStream(
						companyId, groupId);
				}

				return _sitemapStorageHelper.getSitemapInputStream(
					companyId, groupId, assetTypeKey, page);
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException);
				}
			}
		}

		String xml = getSitemap(
			getAssetTypeClassNameId(assetTypeKey), layoutUuid, groupId, page,
			privateLayout, themeDisplay);

		if (xml == null) {
			return null;
		}

		return new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_maximumEntries = MAXIMUM_ENTRIES;

		Map<String, Long> assetTypeClassNameIds = new HashMap<>();
		Map<Long, String> assetTypeKeys = new HashMap<>();

		for (Map.Entry<String, String> entry :
				_assetTypeKeysByClassName.entrySet()) {

			long classNameId = _classNameLocalService.getClassNameId(
				entry.getKey());

			assetTypeClassNameIds.put(entry.getValue(), classNameId);
			assetTypeKeys.put(classNameId, entry.getValue());
		}

		_assetTypeClassNameIds = Collections.unmodifiableMap(
			assetTypeClassNameIds);
		_assetTypeKeys = Collections.unmodifiableMap(assetTypeKeys);

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			_bundleContext, SitemapURLProvider.class, null,
			(serviceReference, emitter) -> {
				SitemapURLProvider sitemapURLProvider =
					_bundleContext.getService(serviceReference);

				emitter.emit(
					_classNameLocalService.getClassNameId(
						sitemapURLProvider.getClassName()));
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private void _addEntryAndPaginate(Element element, Element newElement) {
		int entries =
			GetterUtil.getInteger(element.attributeValue("entries")) + 1;

		int newElementSize = _getSize(newElement);

		int size =
			GetterUtil.getInteger(element.attributeValue("size")) +
				newElementSize;

		if ((entries <= _maximumEntries) && (size < _MAXIMUM_SIZE)) {
			element.addAttribute("entries", String.valueOf(entries));
			element.addAttribute("size", String.valueOf(size));

			return;
		}

		element.remove(newElement);

		String assetTypeKey = element.attributeValue(
			_ATTRIBUTE_NAME_ASSET_TYPE_KEY);

		long companyId = GetterUtil.getLong(
			element.attributeValue(_ATTRIBUTE_NAME_COMPANY_ID));
		long groupId = GetterUtil.getLong(
			element.attributeValue(_ATTRIBUTE_NAME_GROUP_ID));
		int page = GetterUtil.getInteger(
			element.attributeValue(_ATTRIBUTE_NAME_PAGE));

		_storePage(assetTypeKey, companyId, element, groupId, page);

		element.clearContent();

		_initEntriesAndSize(element);
		_initPagination(assetTypeKey, companyId, element, groupId);

		element.addAttribute(_ATTRIBUTE_NAME_PAGE, String.valueOf(page + 1));

		element.add(newElement);

		element.addAttribute("entries", "1");
		element.addAttribute(
			"size",
			String.valueOf(
				GetterUtil.getInteger(element.attributeValue("size")) +
					newElementSize));
	}

	private void _addSitemapElement(
		String assetTypeKey, Element element, long groupId, Date modifiedDate,
		int page, boolean privateLayout, String url) {

		Element sitemapElement = element.addElement("sitemap");

		Element locElement = sitemapElement.addElement("loc");

		StringBundler sb = new StringBundler(10);

		sb.append(url);
		sb.append(_portal.getPathContext());
		sb.append("/sitemap-");
		sb.append(assetTypeKey);
		sb.append(".xml?groupId=");
		sb.append(groupId);
		sb.append("&privateLayout=");
		sb.append(privateLayout);

		if (page > 0) {
			sb.append("&page=");
			sb.append(page);
		}

		locElement.addText(sb.toString());

		if (modifiedDate != null) {
			DateFormat iso8601DateFormat = DateUtil.getISO8601Format();

			Element lastmodElement = sitemapElement.addElement("lastmod");

			lastmodElement.addText(iso8601DateFormat.format(modifiedDate));
		}
	}

	private Document _createSitemapDocument(
		String rootElementName, String rootElementNamespace) {

		Document document = _saxReader.createDocument();

		document.setXMLEncoding(StringPool.UTF8);

		Element rootElement = document.addElement(
			rootElementName, rootElementNamespace);

		rootElement.addAttribute("xmlns:xhtml", "http://www.w3.org/1999/xhtml");
		rootElement.addAttribute(
			"xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		rootElement.addAttribute(
			"xsi:schemaLocation",
			"http://www.w3.org/1999/xhtml " +
				"http://www.w3.org/2002/08/xhtml/xhtml1-strict.xsd");

		return document;
	}

	private Date _getAssetTypeModifiedDate(
			long classNameId, long companyId, long groupId)
		throws PortalException {

		SitemapURLProvider sitemapURLProvider = _serviceTrackerMap.getService(
			classNameId);

		if (sitemapURLProvider == null) {
			return null;
		}

		return sitemapURLProvider.getModifiedDate(companyId, groupId);
	}

	private int _getAssetTypePageCount(
			String assetTypeKey, long companyId, long groupId)
		throws PortalException {

		int pageCount = 1;

		while (_sitemapStorageHelper.hasSitemapFile(
					companyId, groupId, assetTypeKey, pageCount + 1)) {

			pageCount++;
		}

		return pageCount;
	}

	private String _getAssetTypeSitemap(
			long assetTypeClassNameId, long groupId, int page,
			boolean privateLayout, ThemeDisplay themeDisplay)
		throws PortalException {

		_regenerateAssetTypeSitemap(
			assetTypeClassNameId, groupId, privateLayout, themeDisplay);

		String assetTypeKey = _assetTypeKeys.get(assetTypeClassNameId);
		long companyId = themeDisplay.getCompanyId();

		try {
			return StringUtil.read(
				_sitemapStorageHelper.getSitemapInputStream(
					companyId, groupId, assetTypeKey, page));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return null;
	}

	private String _getFriendlyURL(long groupId, String path) {
		int[] groupFriendlyURLIndex = _portal.getGroupFriendlyURLIndex(path);

		if (groupFriendlyURLIndex != null) {
			if (groupFriendlyURLIndex[1] < path.length()) {
				return path.substring(groupFriendlyURLIndex[1]);
			}

			return StringPool.BLANK;
		}

		long companyId = CompanyThreadLocal.getCompanyId();

		if (companyId == 0) {
			try {
				Group group = _groupLocalService.getGroup(groupId);

				companyId = group.getCompanyId();
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException);
				}

				companyId = 0;
			}
		}

		for (Locale availableLocale :
				_language.getAvailableLocales(companyId)) {

			String i18nPath =
				StringPool.SLASH + LocaleUtil.toLanguageId(availableLocale);

			if (path.startsWith(i18nPath + StringPool.SLASH) ||
				path.equals(i18nPath)) {

				path = path.substring(i18nPath.length());

				groupFriendlyURLIndex = _portal.getGroupFriendlyURLIndex(path);

				if (groupFriendlyURLIndex != null) {
					if (groupFriendlyURLIndex[1] < path.length()) {
						return path.substring(groupFriendlyURLIndex[1]);
					}

					return StringPool.BLANK;
				}

				return path;
			}
		}

		return path;
	}

	private String _getIndexSitemap(
			long groupId, boolean privateLayout, ThemeDisplay themeDisplay)
		throws PortalException {

		Document document = _createSitemapDocument(
			"sitemapindex", "http://www.sitemaps.org/schemas/sitemap/0.9");

		Element rootElement = document.getRootElement();

		_initEntriesAndSize(rootElement);

		long companyId = themeDisplay.getCompanyId();

		String xmlSitemapIndexMode =
			_sitemapConfigurationManager.xmlSitemapIndexMode(companyId);

		if (StringUtil.equals(
				xmlSitemapIndexMode, SitemapConstants.INDEX_MODE_ASSET_TYPE)) {

			String portalURL = themeDisplay.getPortalURL();

			for (Map.Entry<Long, String> entry : _assetTypeKeys.entrySet()) {
				long assetTypeClassNameId = entry.getKey();

				SitemapURLProvider sitemapURLProvider =
					_serviceTrackerMap.getService(assetTypeClassNameId);

				if ((sitemapURLProvider == null) ||
					!sitemapURLProvider.isInclude(companyId, groupId)) {

					continue;
				}

				String assetTypeKey = entry.getValue();

				if (!_sitemapStorageHelper.hasSitemapFile(
						companyId, groupId, assetTypeKey, 1)) {

					_regenerateAssetTypeSitemap(
						assetTypeClassNameId, groupId, privateLayout,
						themeDisplay);
				}

				Date assetTypeModifiedDate = _getAssetTypeModifiedDate(
					assetTypeClassNameId, companyId, groupId);

				int assetTypePageCount = _getAssetTypePageCount(
					assetTypeKey, companyId, groupId);

				if (assetTypePageCount <= 1) {
					_addSitemapElement(
						assetTypeKey, rootElement, groupId,
						assetTypeModifiedDate, 0, privateLayout, portalURL);
				}
				else {
					for (int page = 1; page <= assetTypePageCount; page++) {
						_addSitemapElement(
							assetTypeKey, rootElement, groupId,
							assetTypeModifiedDate, page, privateLayout,
							portalURL);
					}
				}
			}
		}
		else {
			for (LayoutSet layoutSet :
					_getLayoutSets(
						groupId, null, privateLayout, themeDisplay)) {

				_visitLayoutSet(rootElement, layoutSet, themeDisplay);
			}
		}

		_removeEntriesAndSize(rootElement);

		String xml = document.asXML();

		if (StringUtil.equals(
				xmlSitemapIndexMode, SitemapConstants.INDEX_MODE_ASSET_TYPE)) {

			try {
				_sitemapStorageHelper.storeSitemapFile(companyId, groupId, xml);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}
			}
		}

		return xml;
	}

	private List<LayoutSet> _getLayoutSets(
			long groupId, String layoutUuid, boolean privateLayout,
			ThemeDisplay themeDisplay)
		throws PortalException {

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			groupId, privateLayout);

		if (Validator.isNotNull(layoutUuid) ||
			!_isCompanyVirtualHostname(themeDisplay)) {

			return ListUtil.fromArray(layoutSet);
		}

		Group group = _groupLocalService.getGroup(groupId);

		if (!group.isGuest()) {
			return ListUtil.fromArray(layoutSet);
		}

		try {
			List<LayoutSet> layoutSets = new ArrayList<>();

			if (MapUtil.isEmpty(layoutSet.getVirtualHostnames())) {
				layoutSets.add(layoutSet);
			}

			layoutSets.addAll(
				TransformUtil.transformToList(
					_sitemapConfigurationManager.getCompanySitemapGroupIds(
						themeDisplay.getCompanyId()),
					curGroupId -> {
						Group curGroup = _groupLocalService.fetchGroup(
							curGroupId);

						if ((curGroup == null) || curGroup.isGuest()) {
							return null;
						}

						LayoutSet curLayoutSet =
							_layoutSetLocalService.getLayoutSet(
								curGroup.getGroupId(), privateLayout);

						if (MapUtil.isNotEmpty(
								curLayoutSet.getVirtualHostnames())) {

							return null;
						}

						return curLayoutSet;
					}));

			return layoutSets;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return Collections.emptyList();
	}

	private String _getSitemap(
			long groupId, String layoutUuid, boolean privateLayout,
			ThemeDisplay themeDisplay)
		throws PortalException {

		Document document = _createSitemapDocument(
			"urlset", "http://www.sitemaps.org/schemas/sitemap/0.9");

		Element rootElement = document.getRootElement();

		_initEntriesAndSize(rootElement);

		_visitLayoutSets(
			rootElement,
			_getLayoutSets(groupId, layoutUuid, privateLayout, themeDisplay),
			layoutUuid, themeDisplay);

		_removeEntriesAndSize(rootElement);

		return document.asXML();
	}

	private List<SitemapURLProvider> _getSitemapURLProviders() {
		return TransformUtil.transform(
			_serviceTrackerMap.keySet(),
			classNameId -> _serviceTrackerMap.getService(classNameId));
	}

	private int _getSize(Element element) {
		String string = element.asXML();

		byte[] bytes = string.getBytes();

		int offset = 0;

		String name = element.getName();

		if (name.equals("url")) {
			Set<Locale> availableLocales = _language.getAvailableLocales();

			int availableLocalesSize = availableLocales.size();

			offset = (availableLocalesSize + 1) * _ATTRIBUTE_XHTML.length;

			offset += _ATTRIBUTE_XMLNS.length;
		}

		return bytes.length - offset;
	}

	private void _initEntriesAndSize(Element element) {
		element.addAttribute("entries", "0");

		int size = _getSize(element);

		element.addAttribute("size", String.valueOf(size));
	}

	private void _initPagination(
		String assetTypeKey, long companyId, Element element, long groupId) {

		element.addAttribute(_ATTRIBUTE_NAME_ASSET_TYPE_KEY, assetTypeKey);
		element.addAttribute(
			_ATTRIBUTE_NAME_COMPANY_ID, String.valueOf(companyId));
		element.addAttribute(_ATTRIBUTE_NAME_GROUP_ID, String.valueOf(groupId));
		element.addAttribute(_ATTRIBUTE_NAME_PAGE, "1");
	}

	private boolean _isCompanyVirtualHostname(ThemeDisplay themeDisplay) {
		Company company = themeDisplay.getCompany();

		String virtualHostname = company.getVirtualHostname();

		if (Validator.isNull(virtualHostname)) {
			virtualHostname = "localhost";
		}

		return Objects.equals(virtualHostname, themeDisplay.getServerName());
	}

	private void _regenerateAssetTypeSitemap(
			long assetTypeClassNameId, long groupId, boolean privateLayout,
			ThemeDisplay themeDisplay)
		throws PortalException {

		Document document = _createSitemapDocument(
			"urlset", "http://www.sitemaps.org/schemas/sitemap/0.9");

		Element rootElement = document.getRootElement();

		_initEntriesAndSize(rootElement);

		String assetTypeKey = _assetTypeKeys.get(assetTypeClassNameId);

		long companyId = themeDisplay.getCompanyId();

		try {
			_sitemapStorageHelper.deleteSitemaps(
				companyId, groupId, assetTypeKey);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		_initPagination(assetTypeKey, companyId, rootElement, groupId);

		SitemapURLProvider sitemapURLProvider = _serviceTrackerMap.getService(
			assetTypeClassNameId);

		for (LayoutSet curLayoutSet :
				_getLayoutSets(groupId, null, privateLayout, themeDisplay)) {

			sitemapURLProvider.visitLayoutSet(
				rootElement, curLayoutSet, themeDisplay);
		}

		_storePage(
			assetTypeKey, companyId, rootElement, groupId,
			GetterUtil.getInteger(
				rootElement.attributeValue(_ATTRIBUTE_NAME_PAGE)));
	}

	private void _removeAttribute(Element element, String name) {
		Attribute attribute = element.attribute(name);

		if (attribute != null) {
			element.remove(attribute);
		}
	}

	private void _removeEntriesAndSize(Element element) {
		Attribute entriesAttribute = element.attribute("entries");
		Attribute sizeAttribute = element.attribute("size");

		if (_log.isDebugEnabled()) {
			StringBundler sb = new StringBundler(4);

			sb.append("Created site map with ");

			if (entriesAttribute != null) {
				sb.append(entriesAttribute.getValue());
			}
			else {
				sb.append("no");
			}

			sb.append(" entries and size ");

			if (sizeAttribute != null) {
				int size = GetterUtil.getInteger(sizeAttribute.getValue());

				sb.append(
					TextFormatter.formatStorageSize(
						size, LocaleUtil.fromLanguageId("en_US")));
			}
			else {
				sb.append("0");
			}

			_log.debug(sb.toString());
		}

		_removeAttribute(element, "entries");
		_removeAttribute(element, "size");
	}

	private void _removeOldestElement(Element element, Element newElement) {
		int entries = GetterUtil.getInteger(element.attributeValue("entries"));
		int size = GetterUtil.getInteger(element.attributeValue("size"));

		entries++;
		size += _getSize(newElement);

		while ((entries > _maximumEntries) || (size >= _MAXIMUM_SIZE)) {
			Element oldestUrlElement = element.element(newElement.getName());

			entries--;
			size -= _getSize(oldestUrlElement);

			element.remove(oldestUrlElement);
		}

		element.addAttribute("entries", String.valueOf(entries));
		element.addAttribute("size", String.valueOf(size));
	}

	private void _removePaginationAttributes(Element element) {
		_removeAttribute(element, _ATTRIBUTE_NAME_ASSET_TYPE_KEY);
		_removeAttribute(element, _ATTRIBUTE_NAME_COMPANY_ID);
		_removeAttribute(element, _ATTRIBUTE_NAME_GROUP_ID);
		_removeAttribute(element, _ATTRIBUTE_NAME_PAGE);
	}

	private void _storePage(
		String assetTypeKey, long companyId, Element element, long groupId,
		int page) {

		_removeEntriesAndSize(element);
		_removePaginationAttributes(element);

		Document document = element.getDocument();

		try {
			_sitemapStorageHelper.storeSitemapFile(
				companyId, groupId, assetTypeKey, page, document.asXML());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}
	}

	private void _visitLayoutSet(
			Element element, LayoutSet layoutSet, ThemeDisplay themeDisplay)
		throws PortalException {

		if (layoutSet.isPrivateLayout()) {
			return;
		}

		String portalURL = themeDisplay.getPortalURL();

		Map<String, LayoutTypeController> layoutTypeControllers =
			LayoutTypeControllerTracker.getLayoutTypeControllers();

		for (Map.Entry<String, LayoutTypeController> entry :
				layoutTypeControllers.entrySet()) {

			LayoutTypeController layoutTypeController = entry.getValue();

			if (!layoutTypeController.isSitemapable()) {
				continue;
			}

			List<Layout> layouts = _layoutLocalService.getAllLayouts(
				layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
				entry.getKey());

			for (Layout layout : layouts) {
				if (layout.isSystem() && !layout.isTypeAssetDisplay()) {
					continue;
				}

				UnicodeProperties typeSettingsUnicodeProperties =
					layout.getTypeSettingsProperties();

				boolean sitemapInclude = GetterUtil.getBoolean(
					typeSettingsUnicodeProperties.getProperty(
						LayoutTypePortletConstants.SITEMAP_INCLUDE),
					true);

				if (!sitemapInclude) {
					continue;
				}

				Element sitemapElement = element.addElement("sitemap");

				Element locElement = sitemapElement.addElement("loc");

				locElement.addText(
					StringBundler.concat(
						portalURL, _portal.getPathContext(),
						"/sitemap.xml?p_l_id=", layout.getPlid(),
						"&layoutUuid=", layout.getUuid(), "&groupId=",
						layoutSet.getGroupId(), "&privateLayout=",
						layout.isPrivateLayout()));

				_removeOldestElement(element, sitemapElement);
			}
		}
	}

	private void _visitLayoutSets(
			Element element, List<LayoutSet> layoutSets, String layoutUuid,
			ThemeDisplay themeDisplay)
		throws PortalException {

		if (ListUtil.isEmpty(layoutSets)) {
			return;
		}

		for (SitemapURLProvider sitemapURLProvider :
				_getSitemapURLProviders()) {

			if (!sitemapURLProvider.isInclude(
					themeDisplay.getCompanyId(),
					themeDisplay.getScopeGroupId())) {

				continue;
			}

			if (Validator.isNull(layoutUuid)) {
				for (LayoutSet curLayoutSet : layoutSets) {
					sitemapURLProvider.visitLayoutSet(
						element, curLayoutSet, themeDisplay);
				}
			}
			else {
				sitemapURLProvider.visitLayout(
					element, layoutUuid, layoutSets.get(0), themeDisplay);
			}
		}
	}

	private static final String _ATTRIBUTE_NAME_ASSET_TYPE_KEY =
		"_assetTypeKey";

	private static final String _ATTRIBUTE_NAME_COMPANY_ID = "_companyId";

	private static final String _ATTRIBUTE_NAME_GROUP_ID = "_groupId";

	private static final String _ATTRIBUTE_NAME_PAGE = "_page";

	private static final byte[] _ATTRIBUTE_XHTML =
		" xmlns:xhtml=\"http://www.w3.org/1999/xhtml\"".getBytes();

	private static final byte[] _ATTRIBUTE_XMLNS =
		" xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\"".getBytes();

	private static final int _MAXIMUM_SIZE = 50 * 1024 * 1024;

	private static final Log _log = LogFactoryUtil.getLog(
		SitemapManagerImpl.class.getName());

	private static final Map<String, String> _assetTypeKeysByClassName = Map.of(
		AssetCategory.class.getName(), "categories",
		JournalArticle.class.getName(), "web-content", Layout.class.getName(),
		"pages", ObjectEntry.class.getName(), "object-entries");
	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	private Map<String, Long> _assetTypeClassNameIds;
	private Map<Long, String> _assetTypeKeys;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	private int _maximumEntries;

	@Reference
	private Portal _portal;

	@Reference
	private RedirectProvider _redirectProvider;

	@Reference
	private SAXReader _saxReader;

	private ServiceTrackerMap<Long, SitemapURLProvider> _serviceTrackerMap;

	@Reference
	private SitemapConfigurationManager _sitemapConfigurationManager;

	@Reference
	private SitemapStorageHelper _sitemapStorageHelper;

}