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

package com.liferay.commerce.user.segment.web.internal.portlet.action;

import com.liferay.commerce.user.segment.constants.CommerceUserSegmentPortletKeys;
import com.liferay.commerce.user.segment.exception.CommerceUserSegmentCriterionTypeException;
import com.liferay.commerce.user.segment.exception.NoSuchUserSegmentCriterionException;
import com.liferay.commerce.user.segment.exception.NoSuchUserSegmentEntryException;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentCriterionService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

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
		"javax.portlet.name=" + CommerceUserSegmentPortletKeys.COMMERCE_USER_SEGMENT,
		"mvc.command.name=editCommerceUserSegmentCriterion"
	},
	service = MVCActionCommand.class
)
public class EditCommerceUserSegmentCriterionMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceUserSegmentCriteria(
			ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceUserSegmentCriterionIds = null;

		long commerceUserSegmentCriterionId = ParamUtil.getLong(
			actionRequest, "commerceUserSegmentCriterionId");

		if (commerceUserSegmentCriterionId > 0) {
			deleteCommerceUserSegmentCriterionIds =
				new long[] {commerceUserSegmentCriterionId};
		}
		else {
			deleteCommerceUserSegmentCriterionIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceUserSegmentCriterionIds"),
				0L);
		}

		for (long deleteCommerceUserSegmentCriterionId :
				deleteCommerceUserSegmentCriterionIds) {

			_commerceUserSegmentCriterionService.
				deleteCommerceUserSegmentCriterion(
					deleteCommerceUserSegmentCriterionId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCommerceUserSegmentCriterion(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceUserSegmentCriteria(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchUserSegmentCriterionException ||
				e instanceof NoSuchUserSegmentEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof CommerceUserSegmentCriterionTypeException) {
				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcPath", "/edit_user_segment_criterion.jsp");
			}
			else {
				throw e;
			}
		}
	}

	protected String getTypeSettings(
			long commerceUserSegmentCriterionId, String type,
			ActionRequest actionRequest)
		throws PortalException {

		String typeSettings = ParamUtil.getString(
			actionRequest, "typeSettings");

		if (Validator.isNotNull(typeSettings)) {
			return typeSettings;
		}

		CommerceUserSegmentCriterion commerceUserSegmentCriterion = null;

		if (commerceUserSegmentCriterionId > 0) {
			commerceUserSegmentCriterion =
				_commerceUserSegmentCriterionService.
					getCommerceUserSegmentCriterion(
						commerceUserSegmentCriterionId);
		}

		if ((commerceUserSegmentCriterion != null) &&
			type.equals(commerceUserSegmentCriterion.getType())) {

			typeSettings = commerceUserSegmentCriterion.getTypeSettings();
		}

		String[] typeSettingsArray = StringUtil.split(typeSettings);

		String[] addTypeSettings = ParamUtil.getStringValues(
			actionRequest, "addTypeSettings");
		String[] deleteTypeSettings = ParamUtil.getStringValues(
			actionRequest, "deleteTypeSettings");

		if (deleteTypeSettings.length > 0) {
			for (String deleteTypeSetting : deleteTypeSettings) {
				typeSettingsArray = ArrayUtil.remove(
					typeSettingsArray, deleteTypeSetting);
			}
		}

		if (addTypeSettings.length > 0) {
			typeSettingsArray = ArrayUtil.append(
				typeSettingsArray, addTypeSettings);
		}

		return StringUtil.merge(typeSettingsArray);
	}

	protected void updateCommerceUserSegmentCriterion(
			ActionRequest actionRequest)
		throws PortalException {

		long commerceUserSegmentCriterionId = ParamUtil.getLong(
			actionRequest, "commerceUserSegmentCriterionId");

		long commerceUserSegmentEntryId = ParamUtil.getLong(
			actionRequest, "commerceUserSegmentEntryId");

		String type = ParamUtil.getString(actionRequest, "type");

		String typeSettings = getTypeSettings(
			commerceUserSegmentCriterionId, type, actionRequest);

		double priority = ParamUtil.getDouble(actionRequest, "priority");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceUserSegmentCriterion.class.getName(), actionRequest);

		if (commerceUserSegmentCriterionId > 0) {
			_commerceUserSegmentCriterionService.
				updateCommerceUserSegmentCriterion(
					commerceUserSegmentCriterionId, type, typeSettings,
					priority, serviceContext);
		}
		else {
			_commerceUserSegmentCriterionService.
				addCommerceUserSegmentCriterion(
					commerceUserSegmentEntryId, type, typeSettings, priority,
					serviceContext);
		}
	}

	@Reference
	private CommerceUserSegmentCriterionService
		_commerceUserSegmentCriterionService;

}