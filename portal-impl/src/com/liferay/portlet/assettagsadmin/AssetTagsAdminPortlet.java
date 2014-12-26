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

package com.liferay.portlet.assettagsadmin;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.asset.DuplicateTagException;
import com.liferay.portlet.asset.NoSuchTagException;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetTagConstants;
import com.liferay.portlet.asset.service.AssetTagServiceUtil;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Eudaldo Alonso
 */
public class AssetTagsAdminPortlet extends MVCPortlet {

	public void deleteTag(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

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

	public void editTag(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

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

	public void mergeTag(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

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

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest, NoSuchTagException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, PrincipalException.class.getName())) {

			include("/error.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
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

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof AssetTagException ||
			cause instanceof DuplicateTagException ||
			cause instanceof NoSuchTagException ||
			cause instanceof PrincipalException) {

			return true;
		}

		return false;
	}

}