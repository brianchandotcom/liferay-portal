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

package com.liferay.wsrp.web.internal.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.wsrp.constants.WSRPPortletKeys;
import com.liferay.wsrp.model.WSRPConsumer;
import com.liferay.wsrp.model.WSRPConsumerPortlet;
import com.liferay.wsrp.model.WSRPProducer;
import com.liferay.wsrp.service.WSRPConsumerLocalService;
import com.liferay.wsrp.service.WSRPConsumerPortletLocalService;
import com.liferay.wsrp.service.WSRPProducerLocalService;
import com.liferay.wsrp.util.MarkupCharacterSetsHelper;
import com.liferay.wsrp.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=WSRP", "javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.copy-request-parameters=true",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/admin/view.jsp",
		"javax.portlet.name=" + WSRPPortletKeys.WSRP_ADMIN,
		"javax.portlet.portlet-info.keywords=WSRP",
		"javax.portlet.portlet-info.short-title=WSRP",
		"javax.portlet.portlet-info.title=WSRP",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class WSRPAdminPortlet extends MVCPortlet {

	public void deleteWSRPConsumer(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long wsrpConsumerId = ParamUtil.getLong(
			actionRequest, "wsrpConsumerId");

		_wSRPConsumerLocalService.deleteWSRPConsumer(wsrpConsumerId);
	}

	public void deleteWSRPConsumerPortlet(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long wsrpConsumerPortletId = ParamUtil.getLong(
			actionRequest, "wsrpConsumerPortletId");

		_wSRPConsumerPortletLocalService.deleteWSRPConsumerPortlet(
			wsrpConsumerPortletId);
	}

	public void deleteWSRPProducer(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long wsrpProducerId = ParamUtil.getLong(
			actionRequest, "wsrpProducerId");

		_wSRPProducerLocalService.deleteWSRPProducer(wsrpProducerId);
	}

	public void restartConsumer(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			doRestartConsumer(actionRequest, actionResponse);
		}
		catch (PortalException pe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			SessionErrors.add(actionRequest, "restartConsumer");
		}
	}

	public void updateServiceDescription(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			doUpdateServiceDescription(actionRequest, actionResponse);
		}
		catch (PortalException pe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			SessionErrors.add(actionRequest, "updateServiceDescription");
		}
	}

	public void updateWSRPConsumer(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			doUpdateWSRPConsumer(actionRequest, actionResponse);
		}
		catch (PortalException pe) {
			SessionErrors.add(actionRequest, pe.getClass());
		}
	}

	public void updateWSRPConsumerPortlet(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			doUpdateWSRPConsumerPortlet(actionRequest, actionResponse);
		}
		catch (PortalException pe) {
			SessionErrors.add(actionRequest, pe.getClass());
		}
	}

	public void updateWSRPConsumerRegistration(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			doUpdateWSRPConsumerRegistration(actionRequest, actionResponse);
		}
		catch (PortalException pe) {
			SessionErrors.add(actionRequest, pe.getClass());
		}
	}

	public void updateWSRPProducer(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			doUpdateWSRPProducer(actionRequest, actionResponse);
		}
		catch (PortalException pe) {
			SessionErrors.add(actionRequest, pe.getClass());
		}
	}

	@Override
	protected void checkPermissions(PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!permissionChecker.isCompanyAdmin()) {
			throw new PrincipalException();
		}
	}

	protected void doRestartConsumer(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long wsrpConsumerId = ParamUtil.getLong(
			actionRequest, "wsrpConsumerId");

		_wSRPConsumerLocalService.restartConsumer(wsrpConsumerId);
	}

	protected void doUpdateServiceDescription(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long wsrpConsumerId = ParamUtil.getLong(
			actionRequest, "wsrpConsumerId");

		_wSRPConsumerLocalService.updateServiceDescription(wsrpConsumerId);
	}

	protected void doUpdateWSRPConsumer(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long wsrpConsumerId = ParamUtil.getLong(
			actionRequest, "wsrpConsumerId");

		String adminPortletId = _portal.getPortletId(actionRequest);
		String name = ParamUtil.getString(actionRequest, "name");
		String url = ParamUtil.getString(actionRequest, "url");
		String forwardCookies = ParamUtil.getString(
			actionRequest, "forwardCookies");
		String forwardHeaders = ParamUtil.getString(
			actionRequest, "forwardHeaders");
		String markupCharacterSets =
			_markupCharacterSetsHelper.getSupportedMarkupCharacterSets(
				ParamUtil.getString(actionRequest, "markupCharacterSets"));

		if (wsrpConsumerId <= 0) {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				WSRPConsumer.class.getName(), actionRequest);

			_wSRPConsumerLocalService.addWSRPConsumer(
				themeDisplay.getCompanyId(), adminPortletId, name, url,
				forwardCookies, forwardHeaders, markupCharacterSets,
				serviceContext);
		}
		else {
			_wSRPConsumerLocalService.updateWSRPConsumer(
				wsrpConsumerId, adminPortletId, name, url, forwardCookies,
				forwardHeaders, markupCharacterSets);
		}
	}

	protected void doUpdateWSRPConsumerPortlet(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long wsrpConsumerPortletId = ParamUtil.getLong(
			actionRequest, "wsrpConsumerPortletId");

		long wsrpConsumerId = ParamUtil.getLong(
			actionRequest, "wsrpConsumerId");
		String name = ParamUtil.getString(actionRequest, "name");
		String portletHandle = ParamUtil.getString(
			actionRequest, "portletHandle");

		if (wsrpConsumerPortletId <= 0) {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				WSRPConsumerPortlet.class.getName(), actionRequest);

			_wSRPConsumerPortletLocalService.addWSRPConsumerPortlet(
				wsrpConsumerId, name, portletHandle, serviceContext);
		}
		else {
			_wSRPConsumerPortletLocalService.updateWSRPConsumerPortlet(
				wsrpConsumerPortletId, name);
		}
	}

	protected void doUpdateWSRPConsumerRegistration(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long wsrpConsumerId = ParamUtil.getLong(
			actionRequest, "wsrpConsumerId");

		String adminPortletId = _portal.getPortletId(actionRequest);

		boolean inbandRegistration = ParamUtil.getBoolean(
			actionRequest, "inbandRegistration");

		UnicodeProperties registrationProperties = null;

		if (inbandRegistration) {
			registrationProperties = new UnicodeProperties();

			for (int i = 0;; i++) {
				String registrationPropertyName = ParamUtil.getString(
					actionRequest, "registrationPropertyName" + i);

				String registrationPropertyValue = ParamUtil.getString(
					actionRequest, "registrationPropertyValue" + i);

				if (Validator.isNull(registrationPropertyName)) {
					break;
				}

				registrationProperties.setProperty(
					registrationPropertyName, registrationPropertyValue);
			}
		}

		String registrationHandle = ParamUtil.getString(
			actionRequest, "registrationHandle");

		_wSRPConsumerLocalService.registerWSRPConsumer(
			wsrpConsumerId, adminPortletId, registrationProperties,
			registrationHandle);
	}

	protected void doUpdateWSRPProducer(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long wsrpProducerId = ParamUtil.getLong(
			actionRequest, "wsrpProducerId");

		String name = ParamUtil.getString(actionRequest, "name");
		String version = ParamUtil.getString(actionRequest, "version");
		String portletIds = ParamUtil.getString(actionRequest, "portletIds");

		if (wsrpProducerId <= 0) {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				WSRPProducer.class.getName(), actionRequest);

			_wSRPProducerLocalService.addWSRPProducer(
				themeDisplay.getUserId(), name, version, portletIds,
				serviceContext);
		}
		else {
			_wSRPProducerLocalService.updateWSRPProducer(
				wsrpProducerId, name, version, portletIds);
		}
	}

	@Reference(unbind = "-")
	protected void setWSRPConsumerLocalService(
		WSRPConsumerLocalService wSRPConsumerLocalService) {

		_wSRPConsumerLocalService = wSRPConsumerLocalService;
	}

	@Reference(unbind = "-")
	protected void setWSRPConsumerPortletLocalService(
		WSRPConsumerPortletLocalService wSRPConsumerPortletLocalService) {

		_wSRPConsumerPortletLocalService = wSRPConsumerPortletLocalService;
	}

	@Reference(unbind = "-")
	protected void setWSRPProducerLocalService(
		WSRPProducerLocalService wSRPProducerLocalService) {

		_wSRPProducerLocalService = wSRPProducerLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WSRPAdminPortlet.class);

	private static WSRPConsumerLocalService _wSRPConsumerLocalService;
	private static WSRPConsumerPortletLocalService
		_wSRPConsumerPortletLocalService;
	private static WSRPProducerLocalService _wSRPProducerLocalService;

	@Reference
	private MarkupCharacterSetsHelper _markupCharacterSetsHelper;

	@Reference
	private Portal _portal;

}