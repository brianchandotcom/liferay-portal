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
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.InfoFormValues;
import com.liferay.info.item.InfoItemClassPKReference;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemFormProviderTracker;
import com.liferay.info.list.renderer.DefaultInfoListRendererContext;
import com.liferay.info.list.renderer.InfoListRenderer;
import com.liferay.info.list.renderer.InfoListRendererTracker;
import com.liferay.info.pagination.Pagination;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.list.retriever.DefaultLayoutListRetrieverContext;
import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverTracker;
import com.liferay.layout.list.retriever.ListObjectReference;
import com.liferay.layout.list.retriever.ListObjectReferenceFactory;
import com.liferay.layout.list.retriever.ListObjectReferenceFactoryTracker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		String listStyle = ParamUtil.getString(resourceRequest, "listStyle");
		String listItemStyle = ParamUtil.getString(
			resourceRequest, "listItemStyle");
		long segmentsExperienceId = ParamUtil.getLong(
			resourceRequest, "segmentsExperienceId");
		int size = ParamUtil.getInteger(resourceRequest, "size");

		try {
			jsonObject = _getCollectionFieldsJSONObject(
				_portal.getHttpServletRequest(resourceRequest),
				_portal.getHttpServletResponse(resourceResponse),
				layoutObjectReference, listStyle, listItemStyle,
				themeDisplay.getLocale(), segmentsExperienceId, size);
		}
		catch (Exception exception) {
			_log.error("Unable to get collection field", exception);

			jsonObject.put(
				"error",
				LanguageUtil.get(
					themeDisplay.getRequest(), "an-unexpected-error-occurred"));
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonObject);
	}

	private JSONObject _getCollectionFieldsJSONObject(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			String layoutObjectReference, String listStyle,
			String listItemStyle, Locale locale, long segmentsExperienceId,
			int size)
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

				ListObjectReference listObjectReference =
					listObjectReferenceFactory.getListObjectReference(
						layoutObjectReferenceJSONObject);

				// LPS-111037

				String itemType = listObjectReference.getItemType();

				if (Objects.equals(
						DLFileEntryConstants.getClassName(), itemType)) {

					itemType = FileEntry.class.getName();
				}

				InfoItemFormProvider<Object> infoItemFormProvider =
					_infoItemFormProviderTracker.getInfoItemFormProvider(
						itemType);

				if (infoItemFormProvider == null) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to get info item form provdier for class " +
								itemType);
					}

					return JSONFactoryUtil.createJSONObject();
				}

				JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

				List<Object> list = layoutListRetriever.getList(
					listObjectReference, defaultLayoutListRetrieverContext);

				for (Object object : list) {
					jsonArray.put(
						_getDisplayObjectJSONObject(
							infoItemFormProvider, object, locale));
				}

				InfoListRenderer infoListRenderer =
					_infoListRendererTracker.getInfoListRenderer(listStyle);

				if (infoListRenderer != null) {
					UnsyncStringWriter unsyncStringWriter =
						new UnsyncStringWriter();

					HttpServletResponse pipingHttpServletResponse =
						new PipingServletResponse(
							httpServletResponse, unsyncStringWriter);

					DefaultInfoListRendererContext
						defaultInfoListRendererContext =
							new DefaultInfoListRendererContext(
								httpServletRequest, pipingHttpServletResponse);

					defaultInfoListRendererContext.setListItemStyleKey(
						listItemStyle);

					infoListRenderer.render(
						list, defaultInfoListRendererContext);

					jsonObject.put("content", unsyncStringWriter.toString());
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
		InfoItemFormProvider<Object> infoItemFormProvider, Object object,
		Locale locale) {

		JSONObject displayObjectJSONObject = JSONFactoryUtil.createJSONObject();

		InfoFormValues infoFormValues = infoItemFormProvider.getInfoFormValues(
			object);

		for (InfoFieldValue infoFieldValue :
				infoFormValues.getInfoFieldValues()) {

			Object value = infoFieldValue.getValue(locale);

			if (value instanceof ContentAccessor) {
				ContentAccessor contentAccessor = (ContentAccessor)value;

				value = contentAccessor.getContent();
			}

			InfoField infoField = infoFieldValue.getInfoField();

			displayObjectJSONObject.put(infoField.getName(), value);
		}

		InfoItemClassPKReference infoItemClassPKReference =
			infoFormValues.getInfoItemClassPKReference();

		if (infoItemClassPKReference != null) {
			displayObjectJSONObject.put(
				"className", infoItemClassPKReference.getClassName()
			).put(
				"classPK", infoItemClassPKReference.getClassPK()
			);
		}

		return displayObjectJSONObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetCollectionFieldMVCResourceCommand.class);

	@Reference
	private InfoItemFormProviderTracker _infoItemFormProviderTracker;

	@Reference
	private InfoListRendererTracker _infoListRendererTracker;

	@Reference
	private LayoutListRetrieverTracker _layoutListRetrieverTracker;

	@Reference
	private ListObjectReferenceFactoryTracker
		_listObjectReferenceFactoryTracker;

	@Reference
	private Portal _portal;

}