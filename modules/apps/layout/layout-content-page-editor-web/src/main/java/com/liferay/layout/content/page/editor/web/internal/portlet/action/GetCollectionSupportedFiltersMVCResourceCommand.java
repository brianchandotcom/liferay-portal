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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.info.filter.InfoFilter;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverTracker;
import com.liferay.layout.list.retriever.ListObjectReference;
import com.liferay.layout.list.retriever.ListObjectReferenceFactory;
import com.liferay.layout.list.retriever.ListObjectReferenceFactoryTracker;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;

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
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/get_collection_supported_filters"
	},
	service = MVCResourceCommand.class
)
public class GetCollectionSupportedFiltersMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		String collections = ParamUtil.getString(
			resourceRequest, "collections");

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			_getSupportedFiltersJSONObject(
				JSONFactoryUtil.createJSONArray(collections)));
	}

	private JSONObject _getSupportedFiltersJSONObject(
			JSONArray collectionsJSONArray)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (int i = 0; i < collectionsJSONArray.length(); i++) {
			JSONObject collectionJSONObject =
				collectionsJSONArray.getJSONObject(i);

			JSONObject layoutObjectReferenceJSONObject =
				collectionJSONObject.getJSONObject("layoutObjectReference");

			String type = layoutObjectReferenceJSONObject.getString("type");

			LayoutListRetriever<?, ListObjectReference> layoutListRetriever =
				(LayoutListRetriever<?, ListObjectReference>)
					_layoutListRetrieverTracker.getLayoutListRetriever(type);

			if (layoutListRetriever == null) {
				continue;
			}

			ListObjectReferenceFactory<?> listObjectReferenceFactory =
				_listObjectReferenceFactoryTracker.getListObjectReference(type);

			if (listObjectReferenceFactory == null) {
				continue;
			}

			List<InfoFilter> supportedInfoFilters =
				layoutListRetriever.getSupportedInfoFilters(
					listObjectReferenceFactory.getListObjectReference(
						layoutObjectReferenceJSONObject));

			jsonObject.put(
				collectionJSONObject.getString("collectionId"),
				JSONUtil.toJSONArray(
					supportedInfoFilters, InfoFilter::getFilterTypeName));
		}

		return jsonObject;
	}

	@Reference
	private LayoutListRetrieverTracker _layoutListRetrieverTracker;

	@Reference
	private ListObjectReferenceFactoryTracker
		_listObjectReferenceFactoryTracker;

}