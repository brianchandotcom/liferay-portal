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

package com.liferay.commerce.availability.range.web.internal.portlet.action;

import com.liferay.commerce.admin.constants.CommerceAdminPortletKeys;
import com.liferay.commerce.exception.NoSuchAvailabilityRangeException;
import com.liferay.commerce.model.CommerceAvailabilityRange;
import com.liferay.commerce.service.CommerceAvailabilityRangeService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceAdminPortletKeys.COMMERCE_ADMIN,
		"mvc.command.name=editCommerceAvailabilityRange"
	},
	service = MVCActionCommand.class
)
public class EditCommerceAvailabilityRangeMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceAvailabilityRanges(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceAvailabilityRangeIds = null;

		long commerceAvailabilityRangeId = ParamUtil.getLong(
			actionRequest, "commerceAvailabilityRangeId");

		if (commerceAvailabilityRangeId > 0) {
			deleteCommerceAvailabilityRangeIds =
				new long[] {commerceAvailabilityRangeId};
		}
		else {
			deleteCommerceAvailabilityRangeIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceAvailabilityRangeIds"),
				0L);
		}

		for (long deleteCommerceAvailabilityRangeId :
				deleteCommerceAvailabilityRangeIds) {

			_commerceAvailabilityRangeService.deleteCommerceAvailabilityRange(
				deleteCommerceAvailabilityRangeId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteCommerceAvailabilityRanges(actionRequest);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				updateCommerceAvailabilityRange(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchAvailabilityRangeException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
		}
	}

	protected void updateCommerceAvailabilityRange(ActionRequest actionRequest)
		throws PortalException {

		long commerceAvailabilityRangeId = ParamUtil.getLong(
			actionRequest, "commerceAvailabilityRangeId");

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");
		double priority = ParamUtil.getDouble(actionRequest, "priority");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceAvailabilityRange.class.getName(), actionRequest);

		if (commerceAvailabilityRangeId <= 0) {
			_commerceAvailabilityRangeService.addCommerceAvailabilityRange(
				titleMap, priority, serviceContext);
		}
		else {
			_commerceAvailabilityRangeService.updateCommerceAvailabilityRange(
				commerceAvailabilityRangeId, titleMap, priority,
				serviceContext);
		}
	}

	@Reference
	private CommerceAvailabilityRangeService _commerceAvailabilityRangeService;

}