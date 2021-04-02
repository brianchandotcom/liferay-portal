/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.organization.web.internal.display.context;

import com.liferay.commerce.organization.web.internal.constants.CommerceOrganizationConstants;
import com.liferay.commerce.organization.web.internal.display.context.util.CommerceOrganizationRequestHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletQName;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.users.admin.configuration.UserFileUploadsConfiguration;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrganizationDisplayContext {

	public CommerceOrganizationDisplayContext(
		HttpServletRequest httpServletRequest,
		OrganizationService organizationService,
		UserFileUploadsConfiguration userFileUploadsConfiguration,
		UserLocalService userLocalService) {

		_organizationService = organizationService;
		_userFileUploadsConfiguration = userFileUploadsConfiguration;
		_userLocalService = userLocalService;

		_commerceOrganizationRequestHelper =
			new CommerceOrganizationRequestHelper(httpServletRequest);
	}

	public String getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		HttpServletRequest httpServletRequest =
			PortalUtil.getOriginalServletRequest(
				_commerceOrganizationRequestHelper.getRequest());

		_keywords = ParamUtil.getString(httpServletRequest, "q", null);

		if (_keywords == null) {
			return StringPool.BLANK;
		}

		return _keywords;
	}

	public String getLogo(Organization organization) {
		return organization.getLogoURL();
	}

	public Organization getOrganization() throws PortalException {
		long organizationId = ParamUtil.getLong(
			_commerceOrganizationRequestHelper.getRequest(), "organizationId");

		if (organizationId > 0) {
			return _organizationService.getOrganization(organizationId);
		}

		return null;
	}

	public long getOrganizationId() throws PortalException {
		Organization organization = getOrganization();

		if (organization != null) {
			return organization.getOrganizationId();
		}

		return OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID;
	}

	public PortletURL getPortletURL() throws PortalException {
		LiferayPortletResponse liferayPortletResponse =
			_commerceOrganizationRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		HttpServletRequest httpServletRequest =
			PortalUtil.getOriginalServletRequest(
				_commerceOrganizationRequestHelper.getRequest());

		String backURL = ParamUtil.getString(
			httpServletRequest,
			PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE + "backURL");

		if (Validator.isNotNull(backURL)) {
			portletURL.setParameter(
				PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE + "backURL",
				backURL);
		}

		String redirect = ParamUtil.getString(
			_commerceOrganizationRequestHelper.getRequest(), "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		String delta = ParamUtil.getString(
			_commerceOrganizationRequestHelper.getRequest(), "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			_commerceOrganizationRequestHelper.getRequest(), "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		String keywords = getKeywords();

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		Organization organization = getOrganization();

		if (organization != null) {
			portletURL.setParameter(
				"organizationId",
				String.valueOf(organization.getOrganizationId()));
		}

		return portletURL;
	}

	public User getSelectedUser() throws PortalException {
		return _userLocalService.getUser(getSelectedUserId());
	}

	public long getSelectedUserId() {
		long userId = ParamUtil.getLong(
			_commerceOrganizationRequestHelper.getRequest(), "userId");

		if (userId > 0) {
			return userId;
		}

		return _commerceOrganizationRequestHelper.getUserId();
	}

	public UserFileUploadsConfiguration getUserFileUploadsConfiguration() {
		return _userFileUploadsConfiguration;
	}

	public String getViewMode() {
		return ParamUtil.getString(
			_commerceOrganizationRequestHelper.getRequest(), "viewMode",
			CommerceOrganizationConstants.LIST_VIEW_MODE);
	}

	public boolean hasAddOrganizationPermissions() throws PortalException {
		if (getOrganizationId() ==
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

			return PortalPermissionUtil.contains(
				_commerceOrganizationRequestHelper.getPermissionChecker(),
				ActionKeys.ADD_ORGANIZATION);
		}

		return OrganizationPermissionUtil.contains(
			_commerceOrganizationRequestHelper.getPermissionChecker(),
			getOrganizationId(), ActionKeys.ADD_ORGANIZATION);
	}

	private final CommerceOrganizationRequestHelper
		_commerceOrganizationRequestHelper;
	private String _keywords;
	private final OrganizationService _organizationService;
	private final UserFileUploadsConfiguration _userFileUploadsConfiguration;
	private final UserLocalService _userLocalService;

}