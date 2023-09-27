/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.status.internal.request.contributor;

import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.kernel.request.contributor.StatusLayoutUtilityPageEntryRequestContributor;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.servlet.I18nServlet;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;

import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = {
		"utility.page.type=" + LayoutUtilityPageEntryConstants.TYPE_SC_INTERNAL_SERVER_ERROR,
		"utility.page.type=" + LayoutUtilityPageEntryConstants.TYPE_SC_NOT_FOUND
	},
	service = StatusLayoutUtilityPageEntryRequestContributor.class
)
public class CommonStatusLayoutUtilityPageEntryRequestContributor
	implements StatusLayoutUtilityPageEntryRequestContributor {

	@Override
	public void addAttributesAndParameters(
		DynamicServletRequest dynamicServletRequest) {

		String host = _portal.getHost(dynamicServletRequest);

		host = StringUtil.toLowerCase(host);
		host = StringUtil.trim(host);

		VirtualHost virtualHost = _virtualHostLocalService.fetchVirtualHost(
			host);

		String currentURL = _portal.getCurrentURL(dynamicServletRequest);

		if (Validator.isNull(currentURL)) {
			_addVirtualHostAttributesAndParameters(
				dynamicServletRequest, null, virtualHost);

			return;
		}

		String pathProxy = _portal.getPathProxy();

		if (Validator.isNotNull(pathProxy) &&
			currentURL.startsWith(pathProxy)) {

			currentURL = currentURL.substring(pathProxy.length());
		}

		String contextPath = dynamicServletRequest.getContextPath();

		if (Validator.isNotNull(contextPath) &&
			!contextPath.equals(StringPool.SLASH)) {

			currentURL = currentURL.substring(contextPath.length());
		}

		if (Validator.isNull(currentURL)) {
			_addVirtualHostAttributesAndParameters(
				dynamicServletRequest, null, virtualHost);

			return;
		}

		String languageId = StringPool.BLANK;

		Set<String> languageIds = I18nServlet.getLanguageIds();

		for (String currentLanguageId : languageIds) {
			if (StringUtil.startsWith(
					currentURL, currentLanguageId + StringPool.FORWARD_SLASH)) {

				currentURL = currentURL.substring(currentLanguageId.length());

				languageId = currentLanguageId.substring(1);

				break;
			}
		}

		if (Validator.isNull(currentURL) ||
			currentURL.equals(StringPool.SLASH)) {

			_addVirtualHostAttributesAndParameters(
				dynamicServletRequest, languageId, virtualHost);

			return;
		}

		String[] urlParts = currentURL.split("\\/", 4);

		if ((currentURL.charAt(0) != CharPool.SLASH) &&
			(urlParts.length != 4)) {

			_addVirtualHostAttributesAndParameters(
				dynamicServletRequest, languageId, virtualHost);

			return;
		}

		String urlPrefix = StringPool.SLASH + urlParts[1];

		if (!(_PUBLIC_GROUP_SERVLET_MAPPING.equals(urlPrefix) ||
			  _PRIVATE_GROUP_SERVLET_MAPPING.equals(urlPrefix) ||
			  _PRIVATE_USER_SERVLET_MAPPING.equals(urlPrefix))) {

			_addVirtualHostAttributesAndParameters(
				dynamicServletRequest, languageId, virtualHost);

			return;
		}

		long companyId = 0;

		if (virtualHost != null) {
			companyId = virtualHost.getCompanyId();
		}
		else {
			companyId = PortalInstances.getDefaultCompanyId();
		}

		Group group = _groupLocalService.fetchFriendlyURLGroup(
			companyId, StringPool.SLASH + urlParts[2]);

		if (group == null) {
			_addVirtualHostAttributesAndParameters(
				dynamicServletRequest, languageId, virtualHost);

			return;
		}

		User user = _getUser(group.getCompanyId(), dynamicServletRequest);

		if (user == null) {
			_addVirtualHostAttributesAndParameters(
				dynamicServletRequest, languageId, virtualHost);

			return;
		}

		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			user);

		Layout layout = _getFirstLayout(group.getGroupId(), permissionChecker);

		if (layout == null) {
			_addVirtualHostAttributesAndParameters(
				dynamicServletRequest, languageId, virtualHost);

			return;
		}

		_addLayoutAttributesAndParameters(
			dynamicServletRequest, languageId, layout);
	}

	private void _addLayoutAttributesAndParameters(
		DynamicServletRequest dynamicServletRequest, String languageId,
		Layout layout) {

		dynamicServletRequest.setParameter(
			"groupId", String.valueOf(layout.getGroupId()));
		dynamicServletRequest.setParameter(
			"layoutId", String.valueOf(layout.getLayoutId()));

		if (Validator.isNotNull(languageId)) {
			dynamicServletRequest.setAttribute(
				WebKeys.I18N_LANGUAGE_ID, languageId);
		}
	}

	private void _addVirtualHostAttributesAndParameters(
		DynamicServletRequest dynamicServletRequest, String languageId,
		VirtualHost virtualHost) {

		if ((virtualHost == null) || (virtualHost.getLayoutSetId() == 0)) {
			return;
		}

		LayoutSet layoutSet = null;

		try {
			layoutSet = _layoutSetLocalService.getLayoutSet(
				virtualHost.getLayoutSetId());

			User user = _getUser(
				layoutSet.getCompanyId(), dynamicServletRequest);

			if (user == null) {
				return;
			}

			PermissionChecker permissionChecker =
				_permissionCheckerFactory.create(user);

			Layout layout = _getFirstLayout(
				layoutSet.getGroupId(), permissionChecker);

			if (layout != null) {
				_addLayoutAttributesAndParameters(
					dynamicServletRequest, languageId, layout);
			}
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}
	}

	private Layout _getFirstLayout(
		long groupId, PermissionChecker permissionChecker) {

		Layout layout = _getFirstLayout(groupId, permissionChecker, false);

		if (layout != null) {
			return layout;
		}

		return _getFirstLayout(groupId, permissionChecker, true);
	}

	private Layout _getFirstLayout(
		long groupId, PermissionChecker permissionChecker,
		boolean privateLayout) {

		for (Layout layout :
				_layoutLocalService.getLayouts(groupId, privateLayout)) {

			if (_hasViewPermission(layout, permissionChecker)) {
				return layout;
			}
		}

		return null;
	}

	private User _getUser(
		long companyId, DynamicServletRequest dynamicServletRequest) {

		try {
			User user = _portal.getUser(dynamicServletRequest);

			if (user != null) {
				return user;
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return _userLocalService.fetchGuestUser(companyId);
	}

	private boolean _hasViewPermission(
		Layout layout, PermissionChecker permissionChecker) {

		try {
			if (LayoutPermissionUtil.contains(
					permissionChecker, layout, ActionKeys.VIEW)) {

				return true;
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return false;
	}

	private static final String _PRIVATE_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING;

	private static final String _PRIVATE_USER_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;

	private static final String _PUBLIC_GROUP_SERVLET_MAPPING =
		PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING;

	private static final Log _log = LogFactoryUtil.getLog(
		CommonStatusLayoutUtilityPageEntryRequestContributor.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private VirtualHostLocalService _virtualHostLocalService;

}