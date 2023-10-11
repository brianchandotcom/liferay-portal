/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.initializer.extender.internal;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.site.dto.v1_0.Site;
import com.liferay.headless.site.resource.v1_0.SiteResource;
import com.liferay.layout.util.LayoutServiceContextHelper;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.site.initializer.extender.SiteInitializerUtil;

import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.net.URLConnection;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Nilton Vieira
 */
@Component(service = {})
public class SiteInitializerClientExtension
	implements BundleTrackerCustomizer<Bundle> {

	@Override
	public Bundle addingBundle(Bundle bundle, BundleEvent bundleEvent) {
		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		if (Validator.isNull(
				headers.get("Liferay-Client-Extension-Site-Initializer")) ||
			_isAlreadyProcessed(bundle)) {

			return null;
		}

		long currentCompanyId = CompanyThreadLocal.getCompanyId();
		PermissionChecker currentPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		String currentPrincipalThreadLocalName = PrincipalThreadLocal.getName();
		ServiceContext currentServiceContext =
			ServiceContextThreadLocal.getServiceContext();

		try {
			_initialize(bundle, headers);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
		finally {
			CompanyThreadLocal.setCompanyId(currentCompanyId);
			PermissionThreadLocal.setPermissionChecker(
				currentPermissionChecker);
			PrincipalThreadLocal.setName(currentPrincipalThreadLocalName);
			ServiceContextThreadLocal.pushServiceContext(currentServiceContext);
		}

		return bundle;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent, Bundle unusedBundle) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent, Bundle unusedBundle) {
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;

		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE, this);

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();
	}

	private Site _addOrUpdateSite(
			String externalReferenceCode, MultipartBody multipartBody,
			User user)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		HttpServletRequest httpServletRequest = serviceContext.getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		httpServletRequest.setAttribute(
			WebKeys.LAYOUT, themeDisplay.getLayout());
		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		SiteResource.Builder builder = _siteResourceFactory.create();

		SiteResource siteResource = builder.user(
			user
		).httpServletRequest(
			httpServletRequest
		).build();

		return siteResource.putSiteByExternalReferenceCode(
			externalReferenceCode, multipartBody);
	}

	private void _initialize(Bundle bundle, Dictionary<String, String> headers)
		throws Exception {

		Map<String, BinaryFile> binaryFileMap = new HashMap<>();
		Enumeration<URL> enumeration = bundle.findEntries(
			headers.get("Liferay-Client-Extension-Site-Initializer"), "*",
			true);
		Site site = null;

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			if (StringUtil.endsWith(url.getPath(), "site-initializer.json")) {
				String json = SiteInitializerUtil.read(
					bundle, "site-initializer.json", url);

				site = Site.toDTO(json);

				if (site == null) {
					_log.error("Unable to transform site from JSON: " + json);

					throw new Exception();
				}
			}
			else if (StringUtil.endsWith(
						url.getPath(), "site-initializer.zip")) {

				URLConnection urlConnection = url.openConnection();

				binaryFileMap.put(
					"file",
					new BinaryFile(
						".zip", "site-initializer",
						urlConnection.getInputStream(),
						urlConnection.getContentLength()));
			}
		}

		Company company = _companyLocalService.getCompanyByWebId(
			PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID));

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				new SiteCallabe(
					company,
					_userLocalService.getUserByScreenName(
						company.getCompanyId(),
						PropsUtil.get(PropsKeys.DEFAULT_ADMIN_SCREEN_NAME)),
					MultipartBody.of(
						binaryFileMap, __ -> _objectMapper,
						Collections.singletonMap("site", site.toString())),
					site));
		}
		catch (Throwable throwable) {
			_log.error(throwable);

			throw new Exception(throwable);
		}
	}

	private boolean _isAlreadyProcessed(Bundle bundle) {
		String lastModifiedString = String.valueOf(bundle.getLastModified());

		File siteInitializerMarkerFile = bundle.getDataFile(
			".liferay-client-extension-site-initializer");

		try {
			if ((siteInitializerMarkerFile != null) &&
				siteInitializerMarkerFile.exists() &&
				Objects.equals(
					FileUtil.read(siteInitializerMarkerFile),
					lastModifiedString)) {

				return true;
			}

			if (!siteInitializerMarkerFile.exists()) {
				siteInitializerMarkerFile.createNewFile();
			}

			FileUtil.write(siteInitializerMarkerFile, lastModifiedString, true);
		}
		catch (IOException ioException) {
			ReflectionUtil.throwException(ioException);
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SiteInitializerClientExtension.class);

	private static final ObjectMapper _objectMapper = new ObjectMapper();
	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	private BundleContext _bundleContext;
	private BundleTracker<?> _bundleTracker;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private LayoutServiceContextHelper _layoutServiceContextHelper;

	@Reference
	private SiteResource.Factory _siteResourceFactory;

	@Reference
	private UserLocalService _userLocalService;

	private class SiteCallabe implements Callable<Site> {

		@Override
		public Site call() throws Exception {
			try (AutoCloseable autoCloseable =
					_layoutServiceContextHelper.getServiceContextAutoCloseable(
						_company)) {

				return _addOrUpdateSite(
					_site.getExternalReferenceCode(), _multipartBody, _user);
			}
			catch (Exception exception) {
				PermissionCacheUtil.clearCache(_user.getUserId());

				throw exception;
			}
		}

		private SiteCallabe(
			Company company, User user, MultipartBody multipartBody,
			Site site) {

			_company = company;
			_user = user;
			_multipartBody = multipartBody;
			_site = site;
		}

		private final Company _company;
		private final MultipartBody _multipartBody;
		private final Site _site;
		private final User _user;

	}

}