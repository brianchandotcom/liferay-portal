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

package com.liferay.portlet.assettagadmin.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.asset.DuplicateTagException;
import com.liferay.portlet.asset.NoSuchTagException;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetTagConstants;
import com.liferay.portlet.asset.service.AssetTagServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class EditTagAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateTag(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteTag(actionRequest);
			}
			else if (cmd.equals(Constants.MERGE)) {
				mergeTag(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof AssetTagException) {
				SessionErrors.add(actionRequest, e.getClass(), e);
			}
			else if (e instanceof DuplicateTagException) {
				SessionErrors.add(actionRequest, e.getClass());
			}
			else if (e instanceof NoSuchTagException ||
					 e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.asset_tag_admin.error");
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		ActionUtil.getTag(renderRequest);

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.asset_tag_admin.edit_tag"));
	}

	protected void deleteTag(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteTagIds = null;

		long tagId = ParamUtil.getLong(actionRequest, "tagId");

		if (tagId > 0) {
			deleteTagIds = new long[] {tagId};
		}
		else {
			deleteTagIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteTagIds"), 0L);
		}

		for (long deleteTagId : deleteTagIds) {
			AssetTagServiceUtil.deleteTag(deleteTagId);
		}
	}

	protected String[] getTagProperties(ActionRequest actionRequest) {
		int[] tagPropertiesIndexes = StringUtil.split(
			ParamUtil.getString(actionRequest, "tagPropertiesIndexes"), 0);

		String[] tagProperties = new String[tagPropertiesIndexes.length];

		for (int i = 0; i < tagPropertiesIndexes.length; i++) {
			int tagPropertiesIndex = tagPropertiesIndexes[i];

			String key = ParamUtil.getString(
				actionRequest, "key" + tagPropertiesIndex);

			if (Validator.isNull(key)) {
				continue;
			}

			String value = ParamUtil.getString(
				actionRequest, "value" + tagPropertiesIndex);

			tagProperties[i] =
				key + AssetTagConstants.PROPERTY_KEY_VALUE_SEPARATOR + value;
		}

		return tagProperties;
	}

	protected void mergeTag(ActionRequest actionRequest) throws Exception {
		long[] mergeTagIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "mergeTagIds"), 0L);
		long targetTagId = ParamUtil.getLong(actionRequest, "targetTagId");
		boolean overrideTagsProperties = ParamUtil.getBoolean(
			actionRequest, "overrideTagsProperties");

		for (long mergeTagId : mergeTagIds) {
			if (targetTagId == mergeTagId) {
				continue;
			}

			AssetTagServiceUtil.mergeTags(
				mergeTagId, targetTagId, overrideTagsProperties);
		}
	}

	protected void updateTag(ActionRequest actionRequest) throws Exception {
		long tagId = ParamUtil.getLong(actionRequest, "tagId");

		String name = ParamUtil.getString(actionRequest, "name");

		String[] tagProperties = getTagProperties(actionRequest);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			AssetTag.class.getName(), actionRequest);

		if (tagId <= 0) {

			// Add tag

			AssetTagServiceUtil.addTag(name, tagProperties, serviceContext);
		}
		else {

			// Update tag

			AssetTagServiceUtil.updateTag(
				tagId, name, tagProperties, serviceContext);
		}
	}

}