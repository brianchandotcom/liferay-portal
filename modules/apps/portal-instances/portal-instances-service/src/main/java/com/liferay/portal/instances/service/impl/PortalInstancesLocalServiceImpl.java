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

package com.liferay.portal.instances.service.impl;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.instances.service.base.PortalInstancesLocalServiceBaseImpl;
import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.InvokerPortlet;
import com.liferay.portal.kernel.portlet.LiferayRenderRequest;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletInstanceFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.DummyHttpServletResponse;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ColorSchemeFactory;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portlet.RenderRequestFactory;
import com.liferay.portlet.RenderResponseFactory;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import java.sql.SQLException;

import java.util.List;

import javax.portlet.PortletConfig;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	property = "model.class.name=com.liferay.portal.util.PortalInstances",
	service = AopService.class
)
public class PortalInstancesLocalServiceImpl
	extends PortalInstancesLocalServiceBaseImpl {

	@Override
	public void addCompanyId(long companyId) {
		PortalInstances.addCompanyId(companyId);
	}

	@Override
	public long getCompanyId(HttpServletRequest httpServletRequest) {
		return PortalInstances.getCompanyId(httpServletRequest);
	}

	@Override
	public long[] getCompanyIds() {
		return PortalInstances.getCompanyIds();
	}

	@Override
	public long[] getCompanyIdsBySQL() throws SQLException {
		return PortalInstances.getCompanyIdsBySQL();
	}

	@Override
	public long getDefaultCompanyId() {
		return PortalInstances.getDefaultCompanyId();
	}

	@Override
	public String[] getWebIds() {
		return PortalInstances.getWebIds();
	}

	@Override
	public void initializePortalInstance(
			long companyId, String siteInitializerKey,
			ServletContext servletContext)
		throws PortalException {

		Company company = _companyLocalService.getCompany(companyId);

		PortalInstances.initCompany(servletContext, company.getWebId());

		if (Validator.isNull(siteInitializerKey)) {
			return;
		}

		SiteInitializer siteInitializer =
			_siteInitializerRegistry.getSiteInitializer(siteInitializerKey);

		if (siteInitializer == null) {
			throw new PortalException(
				"Invalid site initializer key " + siteInitializerKey);
		}

		PermissionChecker currentThreadPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		String currentThreadPrincipalName = PrincipalThreadLocal.getName();
		ServiceContext currentThreadServiceContext =
			ServiceContextThreadLocal.getServiceContext();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setInitializingPortalInstance(true)) {

			Role role = _roleLocalService.fetchRole(
				companyId, RoleConstants.ADMINISTRATOR);

			List<User> users = _userLocalService.getRoleUsers(role.getRoleId());

			User user = users.get(0);

			PermissionChecker permissionChecker =
				_permissionCheckerFactory.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			PrincipalThreadLocal.setName(user.getUserId());

			Group group = _groupLocalService.getGroup(
				company.getCompanyId(), GroupConstants.GUEST);

			ServiceContextThreadLocal.pushServiceContext(
				_populateServiceContext(
					company, group, currentThreadServiceContext.getRequest(),
					permissionChecker,
					(ServiceContext)currentThreadServiceContext.clone(), user));

			_layoutLocalService.deleteLayouts(
				group.getGroupId(), false, new ServiceContext());

			siteInitializer.initialize(group.getGroupId());
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				currentThreadPermissionChecker);
			PrincipalThreadLocal.setName(currentThreadPrincipalName);
			ServiceContextThreadLocal.pushServiceContext(
				currentThreadServiceContext);
		}
	}

	@Override
	public boolean isAutoLoginIgnoreHost(String host) {
		return PortalInstances.isAutoLoginIgnoreHost(host);
	}

	@Override
	public boolean isAutoLoginIgnorePath(String path) {
		return PortalInstances.isAutoLoginIgnorePath(path);
	}

	@Override
	public boolean isCompanyActive(long companyId) {
		return PortalInstances.isCompanyActive(companyId);
	}

	@Override
	public boolean isVirtualHostsIgnoreHost(String host) {
		return PortalInstances.isVirtualHostsIgnoreHost(host);
	}

	@Override
	public boolean isVirtualHostsIgnorePath(String path) {
		return PortalInstances.isVirtualHostsIgnorePath(path);
	}

	@Override
	public void reload(ServletContext servletContext) {
		PortalInstances.reload(servletContext);
	}

	@Override
	public void removeCompany(long companyId) {
		PortalInstances.removeCompany(companyId);
	}

	@Clusterable
	@Override
	public void synchronizePortalInstances() {
		try {
			long[] initializedCompanyIds = _portal.getCompanyIds();

			List<Long> removeableCompanyIds = ListUtil.fromArray(
				initializedCompanyIds);

			_companyLocalService.forEachCompany(
				company -> {
					removeableCompanyIds.remove(company.getCompanyId());

					if (ArrayUtil.contains(
							initializedCompanyIds, company.getCompanyId())) {

						return;
					}

					ServletContext portalContext = ServletContextPool.get(
						_portal.getPathContext());

					PortalInstances.initCompany(
						portalContext, company.getWebId());
				});

			_companyLocalService.forEachCompanyId(
				companyId -> PortalInstances.removeCompany(companyId),
				ArrayUtil.toLongArray(removeableCompanyIds));
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private ServiceContext _populateServiceContext(
			Company company, Group group, HttpServletRequest httpServletRequest,
			PermissionChecker permissionChecker, ServiceContext serviceContext,
			User user)
		throws PortalException {

		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setRequest(httpServletRequest);
		serviceContext.setScopeGroupId(group.getGroupId());
		serviceContext.setUserId(user.getUserId());

		if (httpServletRequest == null) {
			return serviceContext;
		}

		long controlPanelPlid = _portal.getControlPanelPlid(
			company.getCompanyId());

		Layout controlPanelLayout = _layoutLocalService.getLayout(
			controlPanelPlid);

		httpServletRequest.setAttribute(WebKeys.LAYOUT, controlPanelLayout);

		ThemeDisplay currentThemeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ThemeDisplay themeDisplay = null;

		if (currentThemeDisplay != null) {
			try {
				themeDisplay = (ThemeDisplay)currentThemeDisplay.clone();
			}
			catch (CloneNotSupportedException cloneNotSupportedException) {
				_log.error(cloneNotSupportedException);
			}
		}
		else {
			themeDisplay = new ThemeDisplay();
		}

		themeDisplay.setCompany(company);
		themeDisplay.setLayout(controlPanelLayout);
		themeDisplay.setLayoutSet(controlPanelLayout.getLayoutSet());
		themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)controlPanelLayout.getLayoutType());
		themeDisplay.setLocale(LocaleUtil.getSiteDefault());

		String themeId = PrefsPropsUtil.getString(
			company.getCompanyId(),
			PropsKeys.CONTROL_PANEL_LAYOUT_REGULAR_THEME_ID);

		Theme theme = _themeLocalService.getTheme(
			company.getCompanyId(), themeId);

		themeDisplay.setLookAndFeel(
			theme, _colorSchemeFactory.getDefaultRegularColorScheme());

		themeDisplay.setPermissionChecker(permissionChecker);
		themeDisplay.setPlid(controlPanelPlid);
		themeDisplay.setRealUser(user);
		themeDisplay.setRequest(httpServletRequest);
		themeDisplay.setScopeGroupId(controlPanelLayout.getGroupId());
		themeDisplay.setSiteGroupId(controlPanelLayout.getGroupId());
		themeDisplay.setUser(user);

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		PortletRequest portletRequest =
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		if (portletRequest != null) {
			return serviceContext;
		}

		Portlet portlet = _portletLocalService.getPortletById(
			CompanyConstants.SYSTEM, PortletKeys.PORTAL);

		try {
			InvokerPortlet invokerPortlet = PortletInstanceFactoryUtil.create(
				portlet, httpServletRequest.getServletContext());

			PortletConfig portletConfig = PortletConfigFactoryUtil.create(
				portlet, httpServletRequest.getServletContext());

			LiferayRenderRequest liferayRenderRequest =
				RenderRequestFactory.create(
					httpServletRequest, portlet, invokerPortlet,
					portletConfig.getPortletContext(), WindowState.NORMAL,
					PortletMode.VIEW,
					PortletPreferencesFactoryUtil.fromDefaultXML(
						portlet.getDefaultPreferences()),
					themeDisplay.getPlid());

			httpServletRequest.setAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST, liferayRenderRequest);

			httpServletRequest.setAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE,
				RenderResponseFactory.create(
					new DummyHttpServletResponse(), liferayRenderRequest));
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return serviceContext;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalInstancesLocalServiceImpl.class);

	@Reference
	private ColorSchemeFactory _colorSchemeFactory;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CompanyService _companyService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private SiteInitializerRegistry _siteInitializerRegistry;

	@Reference
	private ThemeLocalService _themeLocalService;

	@Reference
	private UserLocalService _userLocalService;

}