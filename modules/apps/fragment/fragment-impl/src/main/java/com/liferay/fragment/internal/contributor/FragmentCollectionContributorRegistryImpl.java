/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.fragment.internal.contributor;

import com.liferay.fragment.configuration.FragmentServiceConfiguration;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.validator.FragmentEntryValidator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.resource.bundle.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.DirectRequestDispatcherFactoryUtil;
import com.liferay.portal.kernel.servlet.DummyHttpServletResponse;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ConcurrentHashMapBuilder;
import com.liferay.portal.kernel.util.FriendlyURLNormalizer;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplayFactory;
import com.liferay.portal.util.PropsValues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.security.Principal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.portlet.PortletPreferences;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Jürgen Kappler
 */
@Component(service = FragmentCollectionContributorRegistry.class)
public class FragmentCollectionContributorRegistryImpl
	implements FragmentCollectionContributorRegistry {

	@Override
	public FragmentCollectionContributor getFragmentCollectionContributor(
		String fragmentCollectionKey) {

		FragmentCollectionBag fragmentCollectionBag =
			_serviceTrackerMap.getService(fragmentCollectionKey);

		if (fragmentCollectionBag == null) {
			return null;
		}

		return fragmentCollectionBag._fragmentCollectionContributor;
	}

	@Override
	public List<FragmentCollectionContributor>
		getFragmentCollectionContributors() {

		List<FragmentCollectionContributor> fragmentCollectionContributors =
			new ArrayList<>();

		for (FragmentCollectionBag fragmentCollectionBag :
				_serviceTrackerMap.values()) {

			FragmentCollectionContributor fragmentCollectionContributor =
				fragmentCollectionBag._fragmentCollectionContributor;

			if (MapUtil.isNotEmpty(fragmentCollectionContributor.getNames())) {
				fragmentCollectionContributors.add(
					fragmentCollectionContributor);
			}
		}

		return fragmentCollectionContributors;
	}

	@Override
	public FragmentComposition getFragmentComposition(
		String fragmentCompositionKey) {

		int index = fragmentCompositionKey.indexOf("-composition-");

		if (index == -1) {
			return null;
		}

		FragmentCollectionBag fragmentCollectionBag =
			_serviceTrackerMap.getService(
				fragmentCompositionKey.substring(0, index));

		if (fragmentCollectionBag == null) {
			return null;
		}

		Map<String, FragmentComposition> fragmentCompostions =
			fragmentCollectionBag._fragmentCompostions;

		return fragmentCompostions.get(fragmentCompositionKey);
	}

	@Override
	public Map<String, FragmentEntry> getFragmentEntries() {
		Map<String, FragmentEntry> fragmentEntries = new HashMap<>();

		for (FragmentCollectionBag fragmentCollectionBag :
				_serviceTrackerMap.values()) {

			fragmentEntries.putAll(fragmentCollectionBag._fragmentEntries);
		}

		return fragmentEntries;
	}

	@Override
	public Map<String, FragmentEntry> getFragmentEntries(Locale locale) {
		Map<String, FragmentEntry> fragmentEntries = new HashMap<>();

		for (FragmentCollectionBag fragmentCollectionBag :
				_serviceTrackerMap.values()) {

			FragmentCollectionContributor fragmentCollectionContributor =
				fragmentCollectionBag._fragmentCollectionContributor;

			for (FragmentEntry fragmentEntry :
					fragmentCollectionContributor.getFragmentEntries(
						_SUPPORTED_FRAGMENT_TYPES, locale)) {

				fragmentEntries.put(
					fragmentEntry.getFragmentEntryKey(), fragmentEntry);
			}
		}

		return fragmentEntries;
	}

	@Override
	public FragmentEntry getFragmentEntry(String fragmentEntryKey) {
		if (fragmentEntryKey == null) {
			return null;
		}

		int index = fragmentEntryKey.indexOf(CharPool.DASH);

		if (index == -1) {
			return null;
		}

		FragmentCollectionBag fragmentCollectionBag =
			_serviceTrackerMap.getService(fragmentEntryKey.substring(0, index));

		if (fragmentCollectionBag == null) {
			return null;
		}

		Map<String, FragmentEntry> fragmentEntries =
			fragmentCollectionBag._fragmentEntries;

		return fragmentEntries.get(fragmentEntryKey);
	}

	@Override
	public ResourceBundleLoader getResourceBundleLoader() {
		List<ResourceBundleLoader> resourceBundleLoaders = new ArrayList<>();

		for (FragmentCollectionBag fragmentCollectionBag :
				_serviceTrackerMap.values()) {

			FragmentCollectionContributor fragmentCollectionContributor =
				fragmentCollectionBag._fragmentCollectionContributor;

			ResourceBundleLoader resourceBundleLoader =
				fragmentCollectionContributor.getResourceBundleLoader();

			if (resourceBundleLoader != null) {
				resourceBundleLoaders.add(resourceBundleLoader);
			}
		}

		return new AggregateResourceBundleLoader(
			resourceBundleLoaders.toArray(new ResourceBundleLoader[0]));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, FragmentCollectionContributor.class,
			"fragment.collection.key",
			new FragmentCollectionContributorServiceTrackerCustomizer(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@Reference
	protected FragmentEntryProcessorRegistry fragmentEntryProcessorRegistry;

	@Reference
	protected FragmentEntryValidator fragmentEntryValidator;

	private Configuration _getFragmentServiceCompanyConfiguration(
			long companyId)
		throws ConfigurationException {

		try {
			String filterString = StringBundler.concat(
				"(&(", ConfigurationAdmin.SERVICE_FACTORYPID, StringPool.EQUAL,
				FragmentServiceConfiguration.class.getName(), ".scoped",
				")(companyId=", companyId, "))");

			Configuration[] configurations =
				_configurationAdmin.listConfigurations(filterString);

			if (configurations != null) {
				return configurations[0];
			}

			return null;
		}
		catch (InvalidSyntaxException | IOException exception) {
			throw new ConfigurationException(exception);
		}
	}

	private boolean _isPropagateContributedFragmentChanges(long companyId)
		throws ConfigurationException {

		if (_getFragmentServiceCompanyConfiguration(companyId) != null) {
			FragmentServiceConfiguration companyFragmentServiceConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					FragmentServiceConfiguration.class, companyId);

			return companyFragmentServiceConfiguration.
				propagateContributedFragmentChanges();
		}

		FragmentServiceConfiguration systemFragmentServiceConfiguration =
			ConfigurationProviderUtil.getSystemConfiguration(
				FragmentServiceConfiguration.class);

		return systemFragmentServiceConfiguration.
			propagateContributedFragmentChanges();
	}

	private void _updateFragmentEntryLinks(
		Map<String, FragmentEntry> fragmentEntries) {

		_companyLocalService.forEachCompany(
			company -> {
				try {
					if (!_isPropagateContributedFragmentChanges(
							company.getCompanyId())) {

						PortletPreferences portletPreferences =
							_portalPreferencesLocalService.getPreferences(
								company.getCompanyId(),
								PortletKeys.PREFS_OWNER_TYPE_COMPANY);

						portletPreferences.setValue(
							"alreadyPropagateContributedFragmentChanges",
							Boolean.FALSE.toString());

						portletPreferences.store();

						return;
					}
				}
				catch (Exception exception) {
					_log.error(exception);

					return;
				}

				try (ServiceContextTemporarySwapper
						serviceContextTemporarySwapper =
							new ServiceContextTemporarySwapper(company)) {

					Set<String> fragmentEntriesSet = fragmentEntries.keySet();

					List<FragmentEntryLink> fragmentEntryLinks =
						_fragmentEntryLinkLocalService.getFragmentEntryLinks(
							company.getCompanyId(),
							fragmentEntriesSet.toArray(new String[0]));

					for (FragmentEntryLink fragmentEntryLink :
							fragmentEntryLinks) {

						FragmentEntry fragmentEntry = fragmentEntries.get(
							fragmentEntryLink.getRendererKey());

						if (fragmentEntry == null) {
							continue;
						}

						try {
							_fragmentEntryLinkLocalService.updateLatestChanges(
								fragmentEntry, fragmentEntryLink);
						}
						catch (PortalException portalException) {
							_log.error(portalException);
						}
					}
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			});
	}

	private boolean _validateFragmentEntry(FragmentEntry fragmentEntry) {
		try {
			fragmentEntryValidator.validateConfiguration(
				fragmentEntry.getConfiguration());
			fragmentEntryValidator.validateTypeOptions(
				fragmentEntry.getType(), fragmentEntry.getTypeOptions());

			fragmentEntryProcessorRegistry.validateFragmentEntryHTML(
				fragmentEntry.getHtml(), fragmentEntry.getConfiguration());

			return true;
		}
		catch (PortalException portalException) {
			_log.error("Unable to validate fragment entry", portalException);
		}

		return false;
	}

	private static final int[] _SUPPORTED_FRAGMENT_TYPES = {
		FragmentConstants.TYPE_COMPONENT, FragmentConstants.TYPE_INPUT,
		FragmentConstants.TYPE_SECTION
	};

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentCollectionContributorRegistryImpl.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FriendlyURLNormalizer _friendlyURLNormalizer;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortalPreferencesLocalService _portalPreferencesLocalService;

	private ServiceTrackerMap<String, FragmentCollectionBag> _serviceTrackerMap;

	@Reference
	private UserLocalService _userLocalService;

	private static class FragmentCollectionBag {

		private FragmentCollectionBag(
			FragmentCollectionContributor fragmentCollectionContributor,
			Map<String, FragmentComposition> fragmentCompostions,
			Map<String, FragmentEntry> fragmentEntries) {

			_fragmentCollectionContributor = fragmentCollectionContributor;
			_fragmentCompostions = fragmentCompostions;
			_fragmentEntries = fragmentEntries;
		}

		private final FragmentCollectionContributor
			_fragmentCollectionContributor;
		private final Map<String, FragmentComposition> _fragmentCompostions;
		private final Map<String, FragmentEntry> _fragmentEntries;

	}

	private class FragmentCollectionContributorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<FragmentCollectionContributor, FragmentCollectionBag> {

		@Override
		public FragmentCollectionBag addingService(
			ServiceReference<FragmentCollectionContributor> serviceReference) {

			FragmentCollectionContributor fragmentCollectionContributor =
				_bundleContext.getService(serviceReference);

			Map<String, FragmentComposition> fragmentCompositions =
				new HashMap<>();
			Map<String, FragmentEntry> fragmentEntries = new HashMap<>();

			for (FragmentComposition fragmentComposition :
					fragmentCollectionContributor.getFragmentCompositions()) {

				fragmentCompositions.put(
					fragmentComposition.getFragmentCompositionKey(),
					fragmentComposition);
			}

			for (FragmentEntry fragmentEntry :
					fragmentCollectionContributor.getFragmentEntries(
						_SUPPORTED_FRAGMENT_TYPES)) {

				if (!_validateFragmentEntry(fragmentEntry)) {
					continue;
				}

				fragmentEntries.put(
					fragmentEntry.getFragmentEntryKey(), fragmentEntry);
			}

			_updateFragmentEntryLinks(fragmentEntries);

			return new FragmentCollectionBag(
				fragmentCollectionContributor, fragmentCompositions,
				fragmentEntries);
		}

		@Override
		public void modifiedService(
			ServiceReference<FragmentCollectionContributor> serviceReference,
			FragmentCollectionBag fragmentCollectionBag) {
		}

		@Override
		public void removedService(
			ServiceReference<FragmentCollectionContributor> serviceReference,
			FragmentCollectionBag fragmentCollectionBag) {

			_bundleContext.ungetService(serviceReference);
		}

		private FragmentCollectionContributorServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		private final BundleContext _bundleContext;

	}

	private class MockHttpServletRequest implements HttpServletRequest {

		@Override
		public boolean authenticate(HttpServletResponse httpServletResponse)
			throws IOException, ServletException {

			return false;
		}

		@Override
		public String changeSessionId() {
			return null;
		}

		@Override
		public AsyncContext getAsyncContext() {
			return null;
		}

		@Override
		public Object getAttribute(String name) {
			return _attributes.get(name);
		}

		@Override
		public Enumeration<String> getAttributeNames() {
			return Collections.enumeration(_attributes.keySet());
		}

		@Override
		public String getAuthType() {
			return null;
		}

		@Override
		public String getCharacterEncoding() {
			return null;
		}

		@Override
		public int getContentLength() {
			return 0;
		}

		@Override
		public long getContentLengthLong() {
			return 0;
		}

		@Override
		public String getContentType() {
			return null;
		}

		@Override
		public String getContextPath() {
			return null;
		}

		@Override
		public Cookie[] getCookies() {
			return new Cookie[0];
		}

		@Override
		public long getDateHeader(String name) {
			return 0;
		}

		@Override
		public DispatcherType getDispatcherType() {
			return null;
		}

		@Override
		public String getHeader(String name) {
			return null;
		}

		@Override
		public Enumeration<String> getHeaderNames() {
			return Collections.emptyEnumeration();
		}

		@Override
		public Enumeration<String> getHeaders(String name) {
			return null;
		}

		@Override
		public ServletInputStream getInputStream() throws IOException {
			return null;
		}

		@Override
		public int getIntHeader(String name) {
			return 0;
		}

		@Override
		public String getLocalAddr() {
			return null;
		}

		@Override
		public Locale getLocale() {
			return null;
		}

		@Override
		public Enumeration<Locale> getLocales() {
			return null;
		}

		@Override
		public String getLocalName() {
			return null;
		}

		@Override
		public int getLocalPort() {
			return 0;
		}

		@Override
		public String getMethod() {
			return HttpMethods.GET;
		}

		@Override
		public String getParameter(String name) {
			return null;
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			return Collections.emptyMap();
		}

		@Override
		public Enumeration<String> getParameterNames() {
			return null;
		}

		@Override
		public String[] getParameterValues(String name) {
			return new String[0];
		}

		@Override
		public Part getPart(String name) throws IOException, ServletException {
			return null;
		}

		@Override
		public Collection<Part> getParts()
			throws IOException, ServletException {

			return null;
		}

		@Override
		public String getPathInfo() {
			return null;
		}

		@Override
		public String getPathTranslated() {
			return null;
		}

		@Override
		public String getProtocol() {
			return null;
		}

		@Override
		public String getQueryString() {
			return null;
		}

		@Override
		public BufferedReader getReader() throws IOException {
			return null;
		}

		@Override
		public String getRealPath(String path) {
			return null;
		}

		@Override
		public String getRemoteAddr() {
			return null;
		}

		@Override
		public String getRemoteHost() {
			return null;
		}

		@Override
		public int getRemotePort() {
			return 0;
		}

		@Override
		public String getRemoteUser() {
			return null;
		}

		@Override
		public RequestDispatcher getRequestDispatcher(String path) {
			return DirectRequestDispatcherFactoryUtil.getRequestDispatcher(
				ServletContextPool.get(_portal.getServletContextName()), path);
		}

		@Override
		public String getRequestedSessionId() {
			return null;
		}

		@Override
		public String getRequestURI() {
			return StringPool.BLANK;
		}

		@Override
		public StringBuffer getRequestURL() {
			return null;
		}

		@Override
		public String getScheme() {
			return null;
		}

		@Override
		public String getServerName() {
			return null;
		}

		@Override
		public int getServerPort() {
			return 0;
		}

		@Override
		public ServletContext getServletContext() {
			return ServletContextPool.get(_portal.getServletContextName());
		}

		@Override
		public String getServletPath() {
			return null;
		}

		@Override
		public HttpSession getSession() {
			return _httpSession;
		}

		@Override
		public HttpSession getSession(boolean create) {
			return _httpSession;
		}

		@Override
		public Principal getUserPrincipal() {
			return null;
		}

		@Override
		public boolean isAsyncStarted() {
			return false;
		}

		@Override
		public boolean isAsyncSupported() {
			return false;
		}

		@Override
		public boolean isRequestedSessionIdFromCookie() {
			return false;
		}

		@Override
		public boolean isRequestedSessionIdFromUrl() {
			return false;
		}

		@Override
		public boolean isRequestedSessionIdFromURL() {
			return false;
		}

		@Override
		public boolean isRequestedSessionIdValid() {
			return false;
		}

		@Override
		public boolean isSecure() {
			return false;
		}

		@Override
		public boolean isUserInRole(String role) {
			return false;
		}

		@Override
		public void login(String userName, String password)
			throws ServletException {
		}

		@Override
		public void logout() throws ServletException {
		}

		@Override
		public void removeAttribute(String name) {
			_attributes.remove(name);
		}

		@Override
		public void setAttribute(String name, Object value) {
			if ((name != null) && (value != null)) {
				_attributes.put(name, value);
			}
		}

		@Override
		public void setCharacterEncoding(String encoding)
			throws UnsupportedEncodingException {
		}

		@Override
		public AsyncContext startAsync() throws IllegalStateException {
			return null;
		}

		@Override
		public AsyncContext startAsync(
				ServletRequest servletRequest, ServletResponse servletResponse)
			throws IllegalStateException {

			return null;
		}

		@Override
		public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass)
			throws IOException, ServletException {

			return null;
		}

		private final Map<String, Object> _attributes =
			ConcurrentHashMapBuilder.<String, Object>put(
				WebKeys.CTX,
				ServletContextPool.get(_portal.getServletContextName())
			).build();

		private final HttpSession _httpSession = new HttpSession() {

			@Override
			public Object getAttribute(String name) {
				return _attributes.get(name);
			}

			@Override
			public Enumeration<String> getAttributeNames() {
				return Collections.enumeration(_attributes.keySet());
			}

			@Override
			public long getCreationTime() {
				return 0;
			}

			@Override
			public String getId() {
				return StringPool.BLANK;
			}

			@Override
			public long getLastAccessedTime() {
				return 0;
			}

			@Override
			public int getMaxInactiveInterval() {
				return 0;
			}

			@Override
			public ServletContext getServletContext() {
				return null;
			}

			@Override
			public HttpSessionContext getSessionContext() {
				return null;
			}

			@Override
			public Object getValue(String name) {
				return null;
			}

			@Override
			public String[] getValueNames() {
				return new String[0];
			}

			@Override
			public void invalidate() {
			}

			@Override
			public boolean isNew() {
				return true;
			}

			@Override
			public void putValue(String name, Object value) {
			}

			@Override
			public void removeAttribute(String name) {
			}

			@Override
			public void removeValue(String name) {
			}

			@Override
			public void setAttribute(String name, Object value) {
				_attributes.put(name, value);
			}

			@Override
			public void setMaxInactiveInterval(int interval) {
			}

		};

	}

	private class ServiceContextTemporarySwapper implements AutoCloseable {

		public ServiceContextTemporarySwapper(Company company)
			throws PortalException {

			_company = company;

			_originalCompanyId = CompanyThreadLocal.getCompanyId();
			_originalPermissionChecker =
				PermissionThreadLocal.getPermissionChecker();
			_originalName = PrincipalThreadLocal.getName();

			_originalServiceContext =
				ServiceContextThreadLocal.getServiceContext();

			if (_originalServiceContext == null) {
				_httpServletRequest = new MockHttpServletRequest();
				_httpServletResponse = new DummyHttpServletResponse();
			}
			else {
				ThemeDisplay themeDisplay =
					_originalServiceContext.getThemeDisplay();

				if (_originalServiceContext.getRequest() != null) {
					_httpServletRequest = _originalServiceContext.getRequest();
				}
				else if ((themeDisplay != null) &&
						 (themeDisplay.getRequest() != null)) {

					_httpServletRequest = themeDisplay.getRequest();
				}
				else {
					_httpServletRequest = new MockHttpServletRequest();
				}

				if ((_originalServiceContext.getResponse() == null) &&
					(themeDisplay != null)) {

					_httpServletResponse = themeDisplay.getResponse();
				}
				else {
					_httpServletResponse =
						_originalServiceContext.getResponse();
				}
			}

			_setCompanyServiceContext();
		}

		@Override
		public void close() {
			CompanyThreadLocal.setCompanyId(_originalCompanyId);
			PermissionThreadLocal.setPermissionChecker(
				_originalPermissionChecker);
			PrincipalThreadLocal.setName(_originalName);
			ServiceContextThreadLocal.pushServiceContext(
				_originalServiceContext);
		}

		private HttpServletRequest _getHttpServletRequest(
				PermissionChecker permissionChecker, User user)
			throws PortalException {

			ThemeDisplay themeDisplay = _getThemeDisplay(
				_company, permissionChecker, user);

			HttpServletRequest companyHttpServletRequest =
				new HttpServletRequestWrapper(_httpServletRequest) {

					@Override
					public Object getAttribute(String name) {
						if (Objects.equals(name, WebKeys.COMPANY_ID)) {
							return _company.getCompanyId();
						}

						if (Objects.equals(name, WebKeys.LAYOUT)) {
							return themeDisplay.getLayout();
						}

						if (Objects.equals(name, WebKeys.THEME_DISPLAY)) {
							return themeDisplay;
						}

						if (Objects.equals(name, WebKeys.USER)) {
							return user;
						}

						if (Objects.equals(name, WebKeys.USER_ID)) {
							return user.getUserId();
						}

						return super.getAttribute(name);
					}

				};

			themeDisplay.setRequest(companyHttpServletRequest);

			themeDisplay.setResponse(_httpServletResponse);

			return companyHttpServletRequest;
		}

		private ThemeDisplay _getThemeDisplay(
				Company company, PermissionChecker permissionChecker, User user)
			throws PortalException {

			ThemeDisplay themeDisplay = ThemeDisplayFactory.create();

			themeDisplay.setCompany(company);

			Group group = _groupLocalService.getGroup(
				company.getCompanyId(), GroupConstants.GUEST);

			String friendlyURL = _friendlyURLNormalizer.normalizeWithEncoding(
				PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_FRIENDLY_URL);

			Layout layout = _layoutLocalService.fetchLayoutByFriendlyURL(
				group.getGroupId(), false, friendlyURL);

			if (layout == null) {
				layout = _layoutLocalService.fetchFirstLayout(
					group.getGroupId(), false,
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, false);
			}

			if (layout == null) {
				layout = _layoutLocalService.fetchFirstLayout(
					group.getGroupId(), false,
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
			}

			themeDisplay.setLanguageId(layout.getDefaultLanguageId());
			themeDisplay.setLayout(layout);

			LayoutSet layoutSet = layout.getLayoutSet();

			themeDisplay.setLayoutSet(layoutSet);

			themeDisplay.setLayoutTypePortlet(
				(LayoutTypePortlet)layout.getLayoutType());
			themeDisplay.setLocale(
				LocaleUtil.fromLanguageId(layout.getDefaultLanguageId()));
			themeDisplay.setLookAndFeel(layoutSet.getTheme(), null);
			themeDisplay.setPermissionChecker(permissionChecker);
			themeDisplay.setPlid(layout.getPlid());
			themeDisplay.setPortalDomain(company.getVirtualHostname());
			themeDisplay.setPortalURL(
				company.getPortalURL(layout.getGroupId()));
			themeDisplay.setRealUser(user);
			themeDisplay.setScopeGroupId(layout.getGroupId());
			themeDisplay.setServerPort(
				_portal.getPortalServerPort(_isHttpsEnabled()));
			themeDisplay.setSiteGroupId(layout.getGroupId());
			themeDisplay.setTimeZone(user.getTimeZone());
			themeDisplay.setUser(user);

			return themeDisplay;
		}

		private boolean _isHttpsEnabled() {
			if (Objects.equals(
					Http.HTTPS,
					PropsUtil.get(PropsKeys.PORTAL_INSTANCE_PROTOCOL)) ||
				Objects.equals(
					Http.HTTPS, PropsUtil.get(PropsKeys.WEB_SERVER_PROTOCOL))) {

				return true;
			}

			return false;
		}

		private void _setCompanyServiceContext() throws PortalException {
			CompanyThreadLocal.setCompanyId(_company.getCompanyId());

			User user = _userLocalService.fetchGuestUser(
				_company.getCompanyId());

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			PrincipalThreadLocal.setName(user.getUserId());

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(_company.getCompanyId());
			serviceContext.setRequest(
				_getHttpServletRequest(permissionChecker, user));
			serviceContext.setUserId(user.getUserId());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);
		}

		private final Company _company;
		private final HttpServletRequest _httpServletRequest;
		private final HttpServletResponse _httpServletResponse;
		private final long _originalCompanyId;
		private final String _originalName;
		private final PermissionChecker _originalPermissionChecker;
		private final ServiceContext _originalServiceContext;

	}

}