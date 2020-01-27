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

package com.liferay.user.associated.data.web.internal.portlet.action;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;
import com.liferay.user.associated.data.web.internal.util.SelectedUserHelper;
import com.liferay.user.associated.data.web.internal.util.UADApplicationSummaryHelper;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
public abstract class BaseUADMVCActionCommand extends BaseMVCActionCommand {

	protected void doMultipleAction(
			List<Serializable> primaryKeys,
			UnsafeConsumer<Serializable, Exception> unsafeConsumer)
		throws Exception {

		for (Serializable primaryKey : primaryKeys) {
			unsafeConsumer.accept(primaryKey);
		}
	}

	protected void doNonreviewableRedirect(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String mvcRenderCommandName = null;

		long selectedUserId = getSelectedUserId(actionRequest);

		int totalNonreviewableUADEntitiesCount =
			uadApplicationSummaryHelper.getTotalNonreviewableUADEntitiesCount(
				selectedUserId);

		if (totalNonreviewableUADEntitiesCount == 0) {
			int totalReviewableUADEntitiesCount =
				uadApplicationSummaryHelper.getTotalReviewableUADEntitiesCount(
					selectedUserId);

			if (totalReviewableUADEntitiesCount == 0) {
				mvcRenderCommandName = "/completed_data_erasure";
			}
			else {
				mvcRenderCommandName = "/review_uad_data";
			}
		}

		if (Validator.isNull(mvcRenderCommandName)) {
			return;
		}

		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			actionRequest, UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
			PortletRequest.RENDER_PHASE);

		liferayPortletURL.setParameter(
			"p_u_i_d", String.valueOf(selectedUserId));
		liferayPortletURL.setParameter(
			"mvcRenderCommandName", mvcRenderCommandName);

		sendRedirect(
			actionRequest, actionResponse, liferayPortletURL.toString());
	}

	protected void doReviewableRedirect(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String mvcRenderCommandName = null;

		long selectedUserId = getSelectedUserId(actionRequest);

		int totalReviewableUADEntitiesCount =
			uadApplicationSummaryHelper.getTotalReviewableUADEntitiesCount(
				selectedUserId);

		if (totalReviewableUADEntitiesCount == 0) {
			int totalNonreviewableUADEntitiesCount =
				uadApplicationSummaryHelper.
					getTotalNonreviewableUADEntitiesCount(selectedUserId);

			if (totalNonreviewableUADEntitiesCount == 0) {
				mvcRenderCommandName = "/completed_data_erasure";
			}
			else {
				mvcRenderCommandName = "/anonymize_nonreviewable_uad_data";
			}
		}

		if (Validator.isNull(mvcRenderCommandName)) {
			return;
		}

		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			actionRequest, UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
			PortletRequest.RENDER_PHASE);

		liferayPortletURL.setParameter(
			"p_u_i_d", String.valueOf(selectedUserId));
		liferayPortletURL.setParameter(
			"mvcRenderCommandName", mvcRenderCommandName);

		sendRedirect(
			actionRequest, actionResponse, liferayPortletURL.toString());
	}

	protected String[] getApplicationKeys(ActionRequest actionRequest) {
		String applicationKey = ParamUtil.getString(
			actionRequest, "applicationKey");

		if (Validator.isNotNull(applicationKey)) {
			return new String[] {applicationKey};
		}

		return ParamUtil.getStringValues(actionRequest, "applicationKeys");
	}

	protected List<String> getEntityTypes(ActionRequest actionRequest) {
		List<String> entityTypes = new ArrayList<>();

		Map<String, String[]> parameterMap = actionRequest.getParameterMap();

		for (String key : parameterMap.keySet()) {
			if (key.startsWith("uadRegistryKey__")) {
				entityTypes.add(
					StringUtil.removeSubstring(key, "uadRegistryKey__"));
			}
		}

		return entityTypes;
	}

	protected String[] getPrimaryKeys(
		ActionRequest actionRequest, String entityType) {

		String primaryKey = ParamUtil.getString(
			actionRequest, "primaryKey__" + entityType);

		if (Validator.isNotNull(primaryKey)) {
			return new String[] {primaryKey};
		}

		return ParamUtil.getStringValues(
			actionRequest, "primaryKeys__" + entityType);
	}

	protected User getSelectedUser(ActionRequest actionRequest)
		throws PortalException {

		return selectedUserHelper.getSelectedUser(actionRequest);
	}

	protected long getSelectedUserId(ActionRequest actionRequest)
		throws PortalException {

		return selectedUserHelper.getSelectedUserId(actionRequest);
	}

	protected UADAnonymizer getUADAnonymizer(
		ActionRequest actionRequest, String entityType) {

		return uadRegistry.getUADAnonymizer(
			getUADRegistryKey(actionRequest, entityType));
	}

	protected UADDisplay getUADDisplay(
		ActionRequest actionRequest, String entityType) {

		return uadRegistry.getUADDisplay(
			getUADRegistryKey(actionRequest, entityType));
	}

	protected String getUADRegistryKey(
		ActionRequest actionRequest, String entityType) {

		return ParamUtil.getString(
			actionRequest, "uadRegistryKey__" + entityType);
	}

	@Reference
	protected SelectedUserHelper selectedUserHelper;

	@Reference
	protected UADApplicationSummaryHelper uadApplicationSummaryHelper;

	@Reference
	protected UADRegistry uadRegistry;

}