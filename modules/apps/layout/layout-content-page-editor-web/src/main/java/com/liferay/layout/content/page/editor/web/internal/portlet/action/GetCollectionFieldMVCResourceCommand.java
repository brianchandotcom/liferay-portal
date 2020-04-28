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

import com.liferay.asset.info.display.contributor.util.ContentAccessor;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.pagination.Pagination;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.list.retriever.DefaultLayoutListRetrieverContext;
import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverTracker;
import com.liferay.layout.list.retriever.ListObjectReference;
import com.liferay.layout.list.retriever.ListObjectReferenceFactory;
import com.liferay.layout.list.retriever.ListObjectReferenceFactoryTracker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

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
		"mvc.command.name=/content_layout/get_collection_field"
	},
	service = MVCResourceCommand.class
)
public class GetCollectionFieldMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String layoutObjectReference = ParamUtil.getString(
			resourceRequest, "layoutObjectReference");
		long segmentsExperienceId = ParamUtil.getLong(
			resourceRequest, "segmentsExperienceId");
		int size = ParamUtil.getInteger(resourceRequest, "size");

		try {
			jsonObject = _getCollectionFieldsJSONObject(
				layoutObjectReference, themeDisplay.getLocale(),
				segmentsExperienceId, size);
		}
		catch (Exception exception) {
			jsonObject.put(
				"error",
				LanguageUtil.get(
					themeDisplay.getRequest(), "an-unexpected-error-occurred"));
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonObject);
	}

	private JSONObject _getCollectionFieldsJSONObject(
			String layoutObjectReference, Locale locale,
			long segmentsExperienceId, int size)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		JSONObject layoutObjectReferenceJSONObject =
			JSONFactoryUtil.createJSONObject(layoutObjectReference);

		String type = layoutObjectReferenceJSONObject.getString("type");

		LayoutListRetriever layoutListRetriever =
			_layoutListRetrieverTracker.getLayoutListRetriever(type);

		if (layoutListRetriever != null) {
			ListObjectReferenceFactory listObjectReferenceFactory =
				_listObjectReferenceFactoryTracker.getListObjectReference(type);

			if (listObjectReferenceFactory != null) {
				DefaultLayoutListRetrieverContext
					defaultLayoutListRetrieverContext =
						new DefaultLayoutListRetrieverContext();

				defaultLayoutListRetrieverContext.setPagination(
					Pagination.of(size, 0));
				defaultLayoutListRetrieverContext.
					setSegmentsExperienceIdsOptional(
						new long[] {segmentsExperienceId});

				JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

				ListObjectReference listObjectReference =
					listObjectReferenceFactory.getListObjectReference(
						layoutObjectReferenceJSONObject);

				List list = layoutListRetriever.getList(
					listObjectReference, defaultLayoutListRetrieverContext);

				// LPS-111037

				String itemType = listObjectReference.getItemType();

				if (Objects.equals(
						DLFileEntryConstants.getClassName(), itemType)) {

					itemType = FileEntry.class.getName();
				}

				InfoDisplayContributor infoDisplayContributor =
					_infoDisplayContributorTracker.getInfoDisplayContributor(
						itemType);

				for (Object object : list) {
					jsonArray.put(
						_getDisplayObjectJSONObject(
							infoDisplayContributor, object, locale));
				}

				jsonObject.put(
					"items", jsonArray
				).put(
					"length",
					layoutListRetriever.getListCount(
						listObjectReference, defaultLayoutListRetrieverContext)
				);
			}
		}

		return jsonObject;
	}

	private JSONObject _getDisplayObjectJSONObject(
			InfoDisplayContributor infoDisplayContributor, Object object,
			Locale locale)
		throws PortalException {

		JSONObject displayObjectJSONObject = JSONFactoryUtil.createJSONObject();

		Map<String, Object> infoDisplayFieldsValues =
			infoDisplayContributor.getInfoDisplayFieldsValues(object, locale);

		for (Map.Entry<String, Object> entry :
				infoDisplayFieldsValues.entrySet()) {

			Object fieldValue = entry.getValue();

			if (fieldValue instanceof ContentAccessor) {
				ContentAccessor contentAccessor = (ContentAccessor)fieldValue;

				fieldValue = contentAccessor.getContent();
			}

			displayObjectJSONObject.put(entry.getKey(), fieldValue);
		}

		displayObjectJSONObject.put(
			"className", infoDisplayContributor.getClassName()
		).put(
			"classPK",
			infoDisplayContributor.getInfoDisplayObjectClassPK(object)
		);

		return displayObjectJSONObject;
	}

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Reference
	private LayoutListRetrieverTracker _layoutListRetrieverTracker;

	@Reference
	private ListObjectReferenceFactoryTracker
		_listObjectReferenceFactoryTracker;

}