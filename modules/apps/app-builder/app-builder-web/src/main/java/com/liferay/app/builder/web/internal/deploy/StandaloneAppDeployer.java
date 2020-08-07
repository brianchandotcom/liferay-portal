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

package com.liferay.app.builder.web.internal.deploy;

import com.liferay.app.builder.constants.AppBuilderPortletKeys;
import com.liferay.app.builder.deploy.AppDeployer;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.web.internal.layout.type.controller.AppPortletLayoutTypeController;
import com.liferay.app.builder.web.internal.model.AppPortletLayoutTypeAccessPolicy;
import com.liferay.app.builder.web.internal.portlet.AppPortlet;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	immediate = true, property = "app.builder.deploy.type=standalone",
	service = AppDeployer.class
)
public class StandaloneAppDeployer extends BaseAppDeployer {

	@Override
	public void deploy(long appId) throws Exception {
		AppBuilderApp appBuilderApp =
			appBuilderAppLocalService.getAppBuilderApp(appId);

		appBuilderApp.setActive(true);

		_serviceRegistrationsMap.computeIfAbsent(
			appId,
			key -> {
				try {
					String appName = appBuilderApp.getName(
						LocaleThreadLocal.getThemeDisplayLocale());
					String portletName = _getPortletName(appId);

					return ArrayUtil.append(
						_deployPortlet(appBuilderApp, appName, portletName),
						new ServiceRegistration<?>[] {
							_deployLayoutTypeController(
								appBuilderApp.getCompanyId(), appId, appName,
								portletName),
							_deployLayoutTypeAccessPolicy(portletName)
						});
				}
				catch (PortalException portalException) {
					throw new IllegalStateException(portalException);
				}
			});

		appBuilderAppLocalService.updateAppBuilderApp(appBuilderApp);
	}

	@Override
	public void undeploy(long appId) throws Exception {
		if (!undeploy(
				appBuilderAppLocalService, appId, _serviceRegistrationsMap)) {

			return;
		}

		AppBuilderApp appBuilderApp =
			appBuilderAppLocalService.getAppBuilderApp(appId);

		Group group = _groupLocalService.getGroup(
			appBuilderApp.getCompanyId(), _getGroupName(appId));

		group.setActive(false);

		_groupLocalService.updateGroup(group);
	}

	@Deactivate
	protected void deactivate() {
		super.deactivate();

		_serviceRegistrationsMap.clear();
	}

	private Group _addGroup(long companyId, long appId) throws PortalException {
		Map<Locale, String> nameMap = Collections.singletonMap(
			LocaleUtil.getDefault(), _getGroupName(appId));

		return _groupLocalService.addGroup(
			_userLocalService.getDefaultUserId(companyId),
			GroupConstants.DEFAULT_PARENT_GROUP_ID, null, 0,
			GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap, null,
			GroupConstants.TYPE_SITE_PRIVATE, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			_getGroupFriendlyURL(appId), false, false, true, null);
	}

	private Layout _addPublicLayout(
			long companyId, long groupId, String portletName)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAttribute(
			"layout.instanceable.allowed", Boolean.TRUE);
		serviceContext.setAttribute("layoutUpdateable", Boolean.FALSE);
		serviceContext.setScopeGroupId(groupId);

		long defaultUserId = _userLocalService.getDefaultUserId(companyId);

		serviceContext.setUserId(defaultUserId);

		return _layoutLocalService.addLayout(
			defaultUserId, groupId, false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "Shared",
			StringPool.BLANK, StringPool.BLANK, portletName, true, null,
			serviceContext);
	}

	private ServiceRegistration<?> _deployLayoutTypeAccessPolicy(
		String portletName) {

		return deployLayoutTypeAccessPolicy(
			new AppPortletLayoutTypeAccessPolicy(),
			new HashMapDictionary<String, Object>() {
				{
					put("layout.type", portletName);
				}
			});
	}

	private ServiceRegistration<?> _deployLayoutTypeController(
			long companyId, long appId, String appName, String portletName)
		throws PortalException {

		Group group = _groupLocalService.fetchFriendlyURLGroup(
			companyId, _getGroupFriendlyURL(appId));

		if (group == null) {
			group = _addGroup(companyId, appId);
		}
		else {
			group.setActive(true);

			_groupLocalService.updateGroup(group);
		}

		if (Validator.isNull(
				_layoutLocalService.fetchDefaultLayout(
					group.getGroupId(), false))) {

			_addPublicLayout(companyId, group.getGroupId(), portletName);
		}

		return deployLayoutTypeController(
			new AppPortletLayoutTypeController(
				_servletContext, appName, portletName),
			new HashMapDictionary<String, Object>() {
				{
					put("layout.type", portletName);
				}
			});
	}

	private ServiceRegistration<?>[] _deployPortlet(
		AppBuilderApp appBuilderApp, String appName, String portletName) {

		return deployPortlet(
			new AppPortlet(
				appBuilderApp, appBuilderAppPortletTabServiceTrackerMap,
				"standalone", appName,
				appPortletMVCResourceCommandServiceTrackerMap, portletName),
			HashMapBuilder.<String, Object>put(
				"com.liferay.portlet.application-type", "full-page-application"
			).build());
	}

	private String _getGroupFriendlyURL(long appId) {
		return "/" + StringUtil.toLowerCase(_getGroupName(appId));
	}

	private String _getGroupName(long appId) {
		return GroupConstants.APP + appId;
	}

	private String _getPortletName(long appId) {
		return AppBuilderPortletKeys.STANDALONE_APP + "_" + appId;
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	private final ConcurrentHashMap<Long, ServiceRegistration<?>[]>
		_serviceRegistrationsMap = new ConcurrentHashMap<>();

	@Reference(target = "(osgi.web.symbolicname=com.liferay.app.builder.web)")
	private ServletContext _servletContext;

	@Reference
	private UserLocalService _userLocalService;

}