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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;
import com.liferay.user.associated.data.web.internal.util.SelectedUserHelper;
import com.liferay.user.associated.data.web.internal.util.UADApplicationSummaryHelper;
import com.liferay.user.associated.data.web.internal.util.UADReviewDataHelper;

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
			ActionRequest actionRequest, String entityType,
			UnsafeConsumer<Object, Exception> unsafeConsumer)
		throws Exception {

		for (Object entity : getEntities(actionRequest, entityType)) {
			unsafeConsumer.accept(entity);
		}
	}

	protected void doNonreviewableRedirect(
		ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String mvcRenderCommandName = null;

		long selectedUserId = getSelectedUserId(actionRequest);

		if (uadApplicationSummaryHelper.getTotalNonreviewableUADEntitiesCount(
			selectedUserId) == 0) {

			if (uadApplicationSummaryHelper.getTotalReviewableUADEntitiesCount(
				selectedUserId) == 0) {

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

		if (uadApplicationSummaryHelper.getTotalReviewableUADEntitiesCount(
			selectedUserId) == 0) {

			if (uadApplicationSummaryHelper.
				getTotalNonreviewableUADEntitiesCount(selectedUserId) ==
				0) {

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

	protected List<Object> getEntities(
			ActionRequest actionRequest, String entityType)
		throws Exception {

		List<Object> entities = new ArrayList<>();

		String[] primaryKeys = ParamUtil.getStringValues(
			actionRequest, "primaryKeys__" + entityType);

		String uadRegistryKey = ParamUtil.getString(
			actionRequest, "uadRegistryKey__" + entityType);

		UADDisplay uadDisplay = uadRegistry.getUADDisplay(uadRegistryKey);

		for (String primaryKey : primaryKeys) {
			entities.add(uadDisplay.get(primaryKey));
		}

		return entities;
	}

	protected Object getEntity(ActionRequest actionRequest, String entityType)
		throws Exception {

		UADDisplay uadDisplay = getUADDisplay(actionRequest, entityType);

		String primaryKey = ParamUtil.getString(
			actionRequest, "primaryKey__" + entityType);

		return uadDisplay.get(primaryKey);
	}

	protected String getEntityType(ActionRequest actionRequest) {
		Map<String, String[]> parameterMap = actionRequest.getParameterMap();

		for (String key : parameterMap.keySet()) {
			if (key.startsWith("primaryKey__")) {
				return key.substring(key.lastIndexOf("_") + 1);
			}
		}

		return null;
	}

	protected List<String> getEntityTypes(ActionRequest actionRequest) {
		List<String> entityTypes = new ArrayList<>();

		Map<String, String[]> parameterMap = actionRequest.getParameterMap();

		for (String key : parameterMap.keySet()) {
			if (key.startsWith("primaryKeys__")) {
				entityTypes.add(key.substring(key.lastIndexOf("_") + 1));
			}
		}

		return entityTypes;
	}

	protected User getSelectedUser(ActionRequest actionRequest)
		throws PortalException {

		return selectedUserHelper.getSelectedUser(actionRequest);
	}

	protected long getSelectedUserId(ActionRequest actionRequest)
		throws PortalException {

		return selectedUserHelper.getSelectedUserId(actionRequest);
	}

	protected UADAnonymizer getUADAnonymizer(ActionRequest actionRequest) {
		return uadRegistry.getUADAnonymizer(getUADRegistryKey(actionRequest));
	}

	protected UADAnonymizer getUADAnonymizer(
		ActionRequest actionRequest, String entityType) {

		return uadRegistry.getUADAnonymizer(
			ParamUtil.getString(
				actionRequest, "uadRegistryKey__" + entityType));
	}

	protected UADDisplay getUADDisplay(ActionRequest actionRequest) {
		return uadRegistry.getUADDisplay(getUADRegistryKey(actionRequest));
	}

	protected UADDisplay getUADDisplay(
		ActionRequest actionRequest, String entityType) {

		return uadRegistry.getUADDisplay(
			ParamUtil.getString(
				actionRequest, "uadRegistryKey__" + entityType));
	}

	protected String getUADRegistryKey(ActionRequest actionRequest) {
		return ParamUtil.getString(actionRequest, "uadRegistryKey");
	}

	@Reference
	protected SelectedUserHelper selectedUserHelper;

	@Reference
	protected UADApplicationSummaryHelper uadApplicationSummaryHelper;

	@Reference
	protected UADRegistry uadRegistry;

	@Reference
	protected UADReviewDataHelper uadReviewDataHelper;

}