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
import com.liferay.fragment.entry.processor.helper.FragmentEntryProcessorHelper;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.list.renderer.DefaultInfoListRendererContext;
import com.liferay.info.list.renderer.InfoListRenderer;
import com.liferay.info.list.renderer.InfoListRendererTracker;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.type.WebImage;
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
import com.liferay.portal.kernel.util.LocaleUtil;
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
		"mvc.command.name=/layout_content_page_editor/get_collection_field"
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

		String languageId = ParamUtil.getString(
			resourceRequest, "languageId", themeDisplay.getLanguageId());

		String layoutObjectReference = ParamUtil.getString(
			resourceRequest, "layoutObjectReference");
		String listStyle = ParamUtil.getString(resourceRequest, "listStyle");
		String listItemStyle = ParamUtil.getString(
			resourceRequest, "listItemStyle");
		String templateKey = ParamUtil.getString(
			resourceRequest, "templateKey");
		long segmentsExperienceId = ParamUtil.getLong(
			resourceRequest, "segmentsExperienceId");
		int size = ParamUtil.getInteger(resourceRequest, "size");

		try {
			jsonObject = _getCollectionFieldsJSONObject(
				_portal.getHttpServletRequest(resourceRequest),
				_portal.getHttpServletResponse(resourceResponse), languageId,
				layoutObjectReference, listStyle, listItemStyle, templateKey,
				segmentsExperienceId, size);
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
			HttpServletResponse httpServletResponse, String languageId,
			String layoutObjectReference, String listStyle,
			String listItemStyle, String templateKey, long segmentsExperienceId,
			int size)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		JSONObject layoutObjectReferenceJSONObject =
			JSONFactoryUtil.createJSONObject(layoutObjectReference);

		String type = layoutObjectReferenceJSONObject.getString("type");

		LayoutListRetriever<?, ListObjectReference> layoutListRetriever =
			(LayoutListRetriever<?, ListObjectReference>)
				_layoutListRetrieverTracker.getLayoutListRetriever(type);

		if (layoutListRetriever != null) {
			ListObjectReferenceFactory<?> listObjectReferenceFactory =
				_listObjectReferenceFactoryTracker.getListObjectReference(type);

			if (listObjectReferenceFactory != null) {
				DefaultLayoutListRetrieverContext
					defaultLayoutListRetrieverContext =
						new DefaultLayoutListRetrieverContext();

				defaultLayoutListRetrieverContext.setPagination(
					Pagination.of(size, 0));
				defaultLayoutListRetrieverContext.setSegmentsExperienceIds(
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

				InfoItemFieldValuesProvider<Object>
					infoItemFieldValuesProvider =
						(InfoItemFieldValuesProvider<Object>)
							_infoItemServiceTracker.getFirstInfoItemService(
								InfoItemFieldValuesProvider.class, itemType);

				if (infoItemFieldValuesProvider == null) {
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
							infoItemFieldValuesProvider, object,
							LocaleUtil.fromLanguageId(languageId)));
				}

				InfoListRenderer<Object> infoListRenderer =
					(InfoListRenderer<Object>)
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

					defaultInfoListRendererContext.setListItemRendererKey(
						listItemStyle);
					defaultInfoListRendererContext.setTemplateKey(templateKey);

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
		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider,
		Object object, Locale locale) {

		JSONObject displayObjectJSONObject = JSONFactoryUtil.createJSONObject();

		InfoItemFieldValues infoItemFieldValues =
			infoItemFieldValuesProvider.getInfoItemFieldValues(object);

		for (InfoFieldValue<Object> infoFieldValue :
				infoItemFieldValues.getInfoFieldValues()) {

			Object value = infoFieldValue.getValue(locale);

			if (value instanceof ContentAccessor) {
				ContentAccessor contentAccessor = (ContentAccessor)value;

				value = contentAccessor.getContent();
			}

			if (value instanceof WebImage) {
				WebImage webImage = (WebImage)value;

				value = webImage.toJSONObject();
			}
			else {
				value = _fragmentEntryProcessorHelper.formatMappedValue(
					value, locale);
			}

			InfoField infoField = infoFieldValue.getInfoField();

			displayObjectJSONObject.put(infoField.getName(), value);
		}

		InfoItemReference infoItemReference =
			infoItemFieldValues.getInfoItemReference();

		if (infoItemReference != null) {
			displayObjectJSONObject.put(
				"className", infoItemReference.getClassName()
			).put(
				"classPK", infoItemReference.getClassPK()
			);
		}

		return displayObjectJSONObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetCollectionFieldMVCResourceCommand.class);

	@Reference
	private FragmentEntryProcessorHelper _fragmentEntryProcessorHelper;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

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