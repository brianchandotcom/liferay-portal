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

package com.liferay.asset.categories.selector.web.portlet;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-asset-categories-selector",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Asset Categories Selector",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class AssetCategoriesSelectorPortlet extends MVCPortlet {

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		String resourceID = GetterUtil.getString(
			resourceRequest.getResourceID());

		if (resourceID.equals("getCategories")) {
			try {
				JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

				List<AssetCategory> categories = getCategories(resourceRequest);

				for (AssetCategory category : categories) {
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

					List<AssetCategory> childCategories =
						_assetCategoryService.getChildCategories(
							category.getCategoryId());

					jsonObject.put("categoryId", category.getCategoryId());
					jsonObject.put("childrenCount", childCategories.size());
					jsonObject.put("hasChildren", !childCategories.isEmpty());
					jsonObject.put("name", category.getName());
					jsonObject.put(
						"parentCategoryId", category.getParentCategoryId());
					jsonObject.put(
						"titleCurrentValue", category.getTitleCurrentValue());

					jsonArray.put(jsonObject);
				}

				writeJSON(
					resourceRequest, resourceResponse, jsonArray.toString());
			}
			catch (PortalException pe) {
				throw new PortletException();
			}
		}
		else {
			super.serveResource(resourceRequest, resourceResponse);
		}
	}

	protected List<AssetCategory> getCategories(PortletRequest portletRequest)
		throws PortalException {

		long categoryId = ParamUtil.getLong(portletRequest, "categoryId");
		long vocabularyId = ParamUtil.getLong(portletRequest, "vocabularyId");
		int start = ParamUtil.getInteger(
			portletRequest, "start", QueryUtil.ALL_POS);
		int end = ParamUtil.getInteger(
			portletRequest, "end", QueryUtil.ALL_POS);

		if ((categoryId <= 0) && (vocabularyId <= 0)) {
			return Collections.emptyList();
		}

		if (categoryId > 0) {
			return _assetCategoryService.getChildCategories(
				categoryId, start, end, null);
		}

		long parentCategoryId = ParamUtil.getLong(
			portletRequest, "parentCategoryId",
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		return _assetCategoryService.getVocabularyCategories(
			parentCategoryId, vocabularyId, start, end, null);
	}

	@Reference(unbind = "-")
	protected void setAssetCategoryService(
		AssetCategoryService assetCategoryService) {

		_assetCategoryService = assetCategoryService;
	}

	private AssetCategoryService _assetCategoryService;

}