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

package com.liferay.layout.type.controller.portlet.internal.display.context;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.info.list.renderer.DefaultInfoListRendererContext;
import com.liferay.info.list.renderer.InfoListRenderer;
import com.liferay.info.list.renderer.InfoListRendererContext;
import com.liferay.info.list.renderer.InfoListRendererTracker;
import com.liferay.info.pagination.Pagination;
import com.liferay.layout.list.retriever.DefaultLayoutListRetrieverContext;
import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverTracker;
import com.liferay.layout.list.retriever.ListObjectReference;
import com.liferay.layout.list.retriever.ListObjectReferenceFactory;
import com.liferay.layout.list.retriever.ListObjectReferenceFactoryTracker;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.util.structure.CollectionLayoutStructureItem;
import com.liferay.layout.util.structure.ContainerLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsWebKeys;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eudaldo Alonso
 */
public class PortletLayoutDisplayContext {

	public PortletLayoutDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		InfoDisplayContributorTracker infoDisplayContributorTracker,
		InfoListRendererTracker infoListRendererTracker,
		LayoutListRetrieverTracker layoutListRetrieverTracker,
		ListObjectReferenceFactoryTracker listObjectReferenceFactoryTracker) {

		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;
		_infoDisplayContributorTracker = infoDisplayContributorTracker;
		_infoListRendererTracker = infoListRendererTracker;
		_layoutListRetrieverTracker = layoutListRetrieverTracker;
		_listObjectReferenceFactoryTracker = listObjectReferenceFactoryTracker;
	}

	public List<Object> getCollection(
		CollectionLayoutStructureItem collectionLayoutStructureItem) {

		JSONObject collectionJSONObject =
			collectionLayoutStructureItem.getCollectionJSONObject();

		if (collectionJSONObject.length() <= 0) {
			return Collections.emptyList();
		}

		String type = collectionJSONObject.getString("type");

		LayoutListRetriever<?, ListObjectReference> layoutListRetriever =
			(LayoutListRetriever<?, ListObjectReference>)
				_layoutListRetrieverTracker.getLayoutListRetriever(type);

		if (layoutListRetriever == null) {
			return Collections.emptyList();
		}

		ListObjectReferenceFactory<?> listObjectReferenceFactory =
			_listObjectReferenceFactoryTracker.getListObjectReference(type);

		if (listObjectReferenceFactory == null) {
			return Collections.emptyList();
		}

		DefaultLayoutListRetrieverContext defaultLayoutListRetrieverContext =
			new DefaultLayoutListRetrieverContext();

		defaultLayoutListRetrieverContext.setSegmentsExperienceIdsOptional(
			_getSegmentsExperienceIds());
		defaultLayoutListRetrieverContext.setPagination(
			Pagination.of(collectionLayoutStructureItem.getNumberOfItems(), 0));

		return layoutListRetriever.getList(
			listObjectReferenceFactory.getListObjectReference(
				collectionJSONObject),
			defaultLayoutListRetrieverContext);
	}

	public List<Object> getCollection(
		CollectionLayoutStructureItem collectionLayoutStructureItem,
		long[] segmentsExperienceIds) {

		JSONObject collectionJSONObject =
			collectionLayoutStructureItem.getCollectionJSONObject();

		if (collectionJSONObject.length() <= 0) {
			return Collections.emptyList();
		}

		ListObjectReference listObjectReference = _getListObjectReference(
			collectionJSONObject);

		if (listObjectReference == null) {
			return Collections.emptyList();
		}

		LayoutListRetriever<?, ListObjectReference> layoutListRetriever =
			(LayoutListRetriever<?, ListObjectReference>)
				_layoutListRetrieverTracker.getLayoutListRetriever(
					collectionJSONObject.getString("type"));

		if (layoutListRetriever == null) {
			return Collections.emptyList();
		}

		DefaultLayoutListRetrieverContext defaultLayoutListRetrieverContext =
			new DefaultLayoutListRetrieverContext();

		defaultLayoutListRetrieverContext.setSegmentsExperienceIdsOptional(
			segmentsExperienceIds);
		defaultLayoutListRetrieverContext.setPagination(
			Pagination.of(collectionLayoutStructureItem.getNumberOfItems(), 0));

		return layoutListRetriever.getList(
			listObjectReference, defaultLayoutListRetrieverContext);
	}

	public InfoDisplayContributor<?> getCollectionInfoDisplayContributor(
		CollectionLayoutStructureItem collectionLayoutStructureItem) {

		ListObjectReference listObjectReference = _getListObjectReference(
			collectionLayoutStructureItem.getCollectionJSONObject());

		if (listObjectReference == null) {
			return null;
		}

		String className = listObjectReference.getItemType();

		if (Objects.equals(className, DLFileEntry.class.getName())) {
			className = FileEntry.class.getName();
		}

		return _infoDisplayContributorTracker.getInfoDisplayContributor(
			className);
	}

	public String getCssClass(
		ContainerLayoutStructureItem containerLayoutStructureItem) {

		StringBundler cssClassSB = new StringBundler(31);

		if (Validator.isNotNull(containerLayoutStructureItem.getAlign())) {
			cssClassSB.append(" ");
			cssClassSB.append(containerLayoutStructureItem.getAlign());
		}

		if (Validator.isNotNull(
				containerLayoutStructureItem.getBackgroundColorCssClass())) {

			cssClassSB.append(" bg-");
			cssClassSB.append(
				containerLayoutStructureItem.getBackgroundColorCssClass());
		}

		if (Validator.isNotNull(
				containerLayoutStructureItem.getBorderColor())) {

			cssClassSB.append(" border-");
			cssClassSB.append(containerLayoutStructureItem.getBorderColor());
		}

		if (Validator.isNotNull(
				containerLayoutStructureItem.getBorderRadius())) {

			cssClassSB.append(" ");
			cssClassSB.append(containerLayoutStructureItem.getBorderRadius());
		}

		if (Objects.equals(
				containerLayoutStructureItem.getContentDisplay(), "block")) {

			cssClassSB.append(" d-block");
		}

		if (Objects.equals(
				containerLayoutStructureItem.getContentDisplay(), "flex")) {

			cssClassSB.append(" d-flex");
		}

		if (Validator.isNotNull(containerLayoutStructureItem.getJustify())) {
			cssClassSB.append(" ");
			cssClassSB.append(containerLayoutStructureItem.getJustify());
		}

		if (containerLayoutStructureItem.getMarginBottom() != -1L) {
			cssClassSB.append(" mb-");
			cssClassSB.append(containerLayoutStructureItem.getMarginBottom());
		}

		if (!Objects.equals(
				containerLayoutStructureItem.getWidthType(), "fixed")) {

			if (containerLayoutStructureItem.getMarginLeft() != -1L) {
				cssClassSB.append(" ml-");
				cssClassSB.append(containerLayoutStructureItem.getMarginLeft());
			}

			if (containerLayoutStructureItem.getMarginRight() != -1L) {
				cssClassSB.append(" mr-");
				cssClassSB.append(
					containerLayoutStructureItem.getMarginRight());
			}
		}

		if (containerLayoutStructureItem.getMarginTop() != -1L) {
			cssClassSB.append(" mt-");
			cssClassSB.append(containerLayoutStructureItem.getMarginTop());
		}

		if (containerLayoutStructureItem.getPaddingBottom() != -1L) {
			cssClassSB.append(" pb-");
			cssClassSB.append(containerLayoutStructureItem.getPaddingBottom());
		}

		if (containerLayoutStructureItem.getPaddingLeft() != -1L) {
			cssClassSB.append(" pl-");
			cssClassSB.append(containerLayoutStructureItem.getPaddingLeft());
		}

		if (containerLayoutStructureItem.getPaddingRight() != -1L) {
			cssClassSB.append(" pr-");
			cssClassSB.append(containerLayoutStructureItem.getPaddingRight());
		}

		if (containerLayoutStructureItem.getPaddingTop() != -1L) {
			cssClassSB.append(" pt-");
			cssClassSB.append(containerLayoutStructureItem.getPaddingTop());
		}

		if (Validator.isNotNull(containerLayoutStructureItem.getShadow())) {
			cssClassSB.append(" ");
			cssClassSB.append(containerLayoutStructureItem.getShadow());
		}

		if (Objects.equals(
				containerLayoutStructureItem.getWidthType(), "fixed")) {

			cssClassSB.append(" container");
		}

		return cssClassSB.toString();
	}

	public InfoListRenderer<?> getInfoListRenderer(
		CollectionLayoutStructureItem collectionLayoutStructureItem) {

		if (Validator.isNull(collectionLayoutStructureItem.getListStyle())) {
			return null;
		}

		return _infoListRendererTracker.getInfoListRenderer(
			collectionLayoutStructureItem.getListStyle());
	}

	public InfoListRendererContext getInfoListRendererContext(
		String listItemStyle, String templateKey) {

		DefaultInfoListRendererContext defaultInfoListRendererContext =
			new DefaultInfoListRendererContext(
				_httpServletRequest, _httpServletResponse);

		defaultInfoListRendererContext.setListItemRendererKey(listItemStyle);
		defaultInfoListRendererContext.setTemplateKey(templateKey);

		return defaultInfoListRendererContext;
	}

	public LayoutStructure getLayoutStructure() {
		if (_layoutStructure != null) {
			return _layoutStructure;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = LayoutLocalServiceUtil.fetchLayout(
			themeDisplay.getPlid());

		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(
					layout.getMasterLayoutPlid());

		if (masterLayoutPageTemplateEntry == null) {
			_layoutStructure = _getDefaultMasterLayoutStructure();

			return _layoutStructure;
		}

		LayoutPageTemplateStructure masterLayoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					masterLayoutPageTemplateEntry.getGroupId(),
					masterLayoutPageTemplateEntry.getPlid());

		String data = masterLayoutPageTemplateStructure.getData(
			SegmentsExperienceConstants.ID_DEFAULT);

		if (Validator.isNull(data)) {
			_layoutStructure = _getDefaultMasterLayoutStructure();

			return _layoutStructure;
		}

		_layoutStructure = LayoutStructure.of(data);

		return _layoutStructure;
	}

	public String getStyle(
			ContainerLayoutStructureItem containerLayoutStructureItem)
		throws PortalException {

		String backgroundImage = _getBackgroundImage(
			containerLayoutStructureItem.getBackgroundImageJSONObject());

		StringBundler styleSB = new StringBundler(12);

		styleSB.append("box-sizing: border-box;");

		if (Validator.isNotNull(backgroundImage)) {
			styleSB.append("background-position: 50% 50%; background-repeat: ");
			styleSB.append("no-repeat; background-size: cover; ");
			styleSB.append("background-image: url(");
			styleSB.append(backgroundImage);
			styleSB.append(");");
		}

		if (containerLayoutStructureItem.getBorderWidth() != -1L) {
			styleSB.append("border-style: solid; border-width: ");
			styleSB.append(containerLayoutStructureItem.getBorderWidth());
			styleSB.append("px;");
		}

		if (containerLayoutStructureItem.getOpacity() != -1L) {
			styleSB.append("opacity: ");
			styleSB.append(containerLayoutStructureItem.getOpacity() / 100.0);
			styleSB.append(";");
		}

		return styleSB.toString();
	}

	private String _getBackgroundImage(JSONObject rowConfigJSONObject)
		throws PortalException {

		if (rowConfigJSONObject == null) {
			return StringPool.BLANK;
		}

		String fieldId = rowConfigJSONObject.getString("fieldId");

		if (Validator.isNotNull(fieldId)) {
			long classNameId = rowConfigJSONObject.getLong("classNameId");
			long classPK = rowConfigJSONObject.getLong("classPK");

			if ((classNameId != 0L) && (classPK != 0L)) {
				InfoDisplayContributor<Object> infoDisplayContributor =
					(InfoDisplayContributor<Object>)
						_infoDisplayContributorTracker.
							getInfoDisplayContributor(
								PortalUtil.getClassName(classNameId));

				if (infoDisplayContributor != null) {
					InfoDisplayObjectProvider<Object>
						infoDisplayObjectProvider =
							(InfoDisplayObjectProvider<Object>)
								infoDisplayContributor.
									getInfoDisplayObjectProvider(classPK);

					if (infoDisplayObjectProvider != null) {
						Object object =
							infoDisplayContributor.getInfoDisplayFieldValue(
								infoDisplayObjectProvider.getDisplayObject(),
								fieldId, LocaleUtil.getDefault());

						if (object instanceof JSONObject) {
							JSONObject fieldValueJSONObject =
								(JSONObject)object;

							return fieldValueJSONObject.getString(
								"url", StringPool.BLANK);
						}
					}
				}
			}
		}

		String backgroundImageURL = rowConfigJSONObject.getString("url");

		if (Validator.isNotNull(backgroundImageURL)) {
			return backgroundImageURL;
		}

		return StringPool.BLANK;
	}

	private LayoutStructure _getDefaultMasterLayoutStructure() {
		LayoutStructure layoutStructure = new LayoutStructure();

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.addRootLayoutStructureItem();

		layoutStructure.addDropZoneLayoutStructureItem(
			rootLayoutStructureItem.getItemId(), 0);

		return layoutStructure;
	}

	private ListObjectReference _getListObjectReference(
		JSONObject collectionJSONObject) {

		String type = collectionJSONObject.getString("type");

		LayoutListRetriever<?, ?> layoutListRetriever =
			_layoutListRetrieverTracker.getLayoutListRetriever(type);

		if (layoutListRetriever == null) {
			return null;
		}

		ListObjectReferenceFactory<?> listObjectReferenceFactory =
			_listObjectReferenceFactoryTracker.getListObjectReference(type);

		if (listObjectReferenceFactory == null) {
			return null;
		}

		return listObjectReferenceFactory.getListObjectReference(
			collectionJSONObject);
	}

	private long[] _getSegmentsExperienceIds() {
		return GetterUtil.getLongValues(
			_httpServletRequest.getAttribute(
				SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS),
			new long[] {SegmentsExperienceConstants.ID_DEFAULT});
	}

	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private final InfoDisplayContributorTracker _infoDisplayContributorTracker;
	private final InfoListRendererTracker _infoListRendererTracker;
	private final LayoutListRetrieverTracker _layoutListRetrieverTracker;
	private LayoutStructure _layoutStructure;
	private final ListObjectReferenceFactoryTracker
		_listObjectReferenceFactoryTracker;

}