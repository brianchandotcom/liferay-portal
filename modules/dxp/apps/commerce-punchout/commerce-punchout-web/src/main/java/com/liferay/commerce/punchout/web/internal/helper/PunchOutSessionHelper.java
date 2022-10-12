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

package com.liferay.commerce.punchout.web.internal.helper;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.punchout.configuration.PunchOutConfiguration;
import com.liferay.commerce.punchout.constants.PunchOutConstants;
import com.liferay.commerce.punchout.service.PunchOutAccountRoleHelper;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jaclyn Ong
 */
@Component(immediate = true, service = PunchOutSessionHelper.class)
public class PunchOutSessionHelper {

	public HttpSession getHttpSession(HttpServletRequest httpServletRequest) {
		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		return originalHttpServletRequest.getSession();
	}

	public String getPunchOutReturnURL(HttpServletRequest httpServletRequest) {
		HttpSession httpSession = getHttpSession(httpServletRequest);

		Object punchOutReturnUrlObject = httpSession.getAttribute(
			PunchOutConstants.PUNCH_OUT_RETURN_URL_ATTRIBUTE_NAME);

		if (punchOutReturnUrlObject == null) {
			return null;
		}

		return (String)punchOutReturnUrlObject;
	}

	public boolean punchOutAllowed(HttpServletRequest httpServletRequest) {
		try {
			CommerceOrder commerceOrder = _getCommerceOrder(httpServletRequest);

			if (commerceOrder == null) {
				return false;
			}

			CommerceContext commerceContext =
				(CommerceContext)httpServletRequest.getAttribute(
					CommerceWebKeys.COMMERCE_CONTEXT);

			CommerceAccount commerceAccount =
				commerceContext.getCommerceAccount();

			return _punchOutAccountRoleHelper.hasPunchOutRole(
				commerceOrder.getUserId(),
				commerceAccount.getCommerceAccountId());
		}
		catch (Exception exception) {
			_log.error(
				"Failed to determine whether user has " +
					PunchOutConstants.ROLE_NAME_ACCOUNT_PUNCH_OUT +
						" role under commerce account",
				exception);

			return false;
		}
	}

	public boolean punchOutEnabled(HttpServletRequest httpServletRequest) {
		try {
			CommerceContext commerceContext =
				(CommerceContext)httpServletRequest.getAttribute(
					CommerceWebKeys.COMMERCE_CONTEXT);

			long commerceChannelGroupId =
				commerceContext.getCommerceChannelGroupId();

			if (commerceChannelGroupId == 0L) {
				return false;
			}

			PunchOutConfiguration punchOutConfiguration =
				_getPunchOutConfiguration(commerceChannelGroupId);

			if (punchOutConfiguration != null) {
				return punchOutConfiguration.enabled();
			}
		}
		catch (Exception exception) {
			_log.error("Failed to load punch out configuration", exception);
		}

		return false;
	}

	public boolean punchOutSession(HttpServletRequest httpServletRequest) {
		return !Validator.isBlank(getPunchOutReturnURL(httpServletRequest));
	}

	private CommerceOrder _getCommerceOrder(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		String commerceOrderUuid = ParamUtil.getString(
			httpServletRequest, "commerceOrderUuid");

		if (Validator.isNotNull(commerceOrderUuid)) {
			long groupId =
				_commerceChannelLocalService.
					getCommerceChannelGroupIdBySiteGroupId(
						_portal.getScopeGroupId(httpServletRequest));

			return _commerceOrderService.getCommerceOrderByUuidAndGroupId(
				commerceOrderUuid, groupId);
		}

		return _commerceOrderHttpHelper.getCurrentCommerceOrder(
			httpServletRequest);
	}

	private PunchOutConfiguration _getPunchOutConfiguration(
		long commerceChannelGroupId) {

		try {
			return _configurationProvider.getConfiguration(
				PunchOutConfiguration.class,
				new GroupServiceSettingsLocator(
					commerceChannelGroupId, PunchOutConstants.SERVICE_NAME));
		}
		catch (ConfigurationException configurationException) {
			_log.error(
				"Unable to get punch out configuration",
				configurationException);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PunchOutSessionHelper.class);

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Portal _portal;

	@Reference
	private PunchOutAccountRoleHelper _punchOutAccountRoleHelper;

}