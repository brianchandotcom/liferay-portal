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

package com.liferay.layout.taglib.internal.display.context;

import com.liferay.asset.info.display.contributor.util.ContentAccessor;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.info.list.renderer.DefaultInfoListRendererContext;
import com.liferay.info.list.renderer.InfoListRenderer;
import com.liferay.info.list.renderer.InfoListRendererContext;
import com.liferay.info.list.renderer.InfoListRendererTracker;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.type.WebImage;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.layout.list.retriever.DefaultLayoutListRetrieverContext;
import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverTracker;
import com.liferay.layout.list.retriever.ListObjectReference;
import com.liferay.layout.list.retriever.ListObjectReferenceFactory;
import com.liferay.layout.list.retriever.ListObjectReferenceFactoryTracker;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.StyledLayoutStructureItem;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.PortletJSONUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rubén Pulido
 */
public class RenderFragmentLayoutDisplayContext {

	public RenderFragmentLayoutDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		InfoItemServiceTracker infoItemServiceTracker,
		InfoListRendererTracker infoListRendererTracker,
		LayoutDisplayPageProviderTracker layoutDisplayPageProviderTracker,
		LayoutListRetrieverTracker layoutListRetrieverTracker,
		ListObjectReferenceFactoryTracker listObjectReferenceFactoryTracker) {

		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;
		_infoItemServiceTracker = infoItemServiceTracker;
		_infoListRendererTracker = infoListRendererTracker;
		_layoutDisplayPageProviderTracker = layoutDisplayPageProviderTracker;
		_layoutListRetrieverTracker = layoutListRetrieverTracker;
		_listObjectReferenceFactoryTracker = listObjectReferenceFactoryTracker;
	}

	public List<Object> getCollection(
		CollectionStyledLayoutStructureItem collectionStyledLayoutStructureItem,
		long[] segmentsExperienceIds) {

		JSONObject collectionJSONObject =
			collectionStyledLayoutStructureItem.getCollectionJSONObject();

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
			Pagination.of(
				collectionStyledLayoutStructureItem.getNumberOfItems(), 0));

		return layoutListRetriever.getList(
			listObjectReference, defaultLayoutListRetrieverContext);
	}

	public String getContainerLinkHref(
			ContainerStyledLayoutStructureItem
				containerStyledLayoutStructureItem,
			Object displayObject)
		throws PortalException {

		JSONObject linkJSONObject =
			containerStyledLayoutStructureItem.getLinkJSONObject();

		if (linkJSONObject == null) {
			return StringPool.BLANK;
		}

		String mappedField = linkJSONObject.getString("mappedField");

		if (Validator.isNotNull(mappedField)) {
			Object infoItem = _httpServletRequest.getAttribute(
				InfoDisplayWebKeys.INFO_ITEM);

			InfoItemDetails infoItemDetails =
				(InfoItemDetails)_httpServletRequest.getAttribute(
					InfoDisplayWebKeys.INFO_ITEM_DETAILS);

			if ((infoItem != null) && (infoItemDetails != null)) {
				InfoItemFieldValuesProvider<Object>
					infoItemFieldValuesProvider =
						_infoItemServiceTracker.getFirstInfoItemService(
							InfoItemFieldValuesProvider.class,
							infoItemDetails.getClassName());

				if (infoItemFieldValuesProvider != null) {
					InfoFieldValue<Object> infoItemFieldValue =
						infoItemFieldValuesProvider.getInfoItemFieldValue(
							infoItem, mappedField);

					if (infoItemFieldValue != null) {
						Object object = infoItemFieldValue.getValue(
							LocaleUtil.getDefault());

						if (object instanceof String) {
							String fieldValue = (String)object;

							if (Validator.isNotNull(fieldValue)) {
								return fieldValue;
							}

							return StringPool.BLANK;
						}
					}
				}
			}
		}

		String fieldId = linkJSONObject.getString("fieldId");

		if (Validator.isNotNull(fieldId)) {
			long classNameId = linkJSONObject.getLong("classNameId");
			long classPK = linkJSONObject.getLong("classPK");

			if ((classNameId != 0L) && (classPK != 0L)) {
				String className = PortalUtil.getClassName(classNameId);

				InfoItemFieldValuesProvider<Object>
					infoItemFieldValuesProvider =
						_infoItemServiceTracker.getFirstInfoItemService(
							InfoItemFieldValuesProvider.class, className);

				InfoItemObjectProvider<Object> infoItemObjectProvider =
					_infoItemServiceTracker.getFirstInfoItemService(
						InfoItemObjectProvider.class, className);

				if ((infoItemObjectProvider != null) &&
					(infoItemFieldValuesProvider != null)) {

					InfoItemIdentifier infoItemIdentifier =
						new ClassPKInfoItemIdentifier(classPK);

					Object infoItem = infoItemObjectProvider.getInfoItem(
						infoItemIdentifier);

					if (infoItem != null) {
						InfoFieldValue<Object> infoItemFieldValue =
							infoItemFieldValuesProvider.getInfoItemFieldValue(
								infoItem, fieldId);

						if (infoItemFieldValue != null) {
							Object object = infoItemFieldValue.getValue(
								LocaleUtil.getDefault());

							if (object instanceof String) {
								String fieldValue = (String)object;

								if (Validator.isNotNull(fieldValue)) {
									return fieldValue;
								}

								return StringPool.BLANK;
							}
						}
					}
				}
			}
		}

		String collectionFieldId = linkJSONObject.getString(
			"collectionFieldId");

		if (Validator.isNotNull(collectionFieldId)) {
			String mappedCollectionValue = _getMappedCollectionValue(
				collectionFieldId, displayObject);

			if (Validator.isNotNull(mappedCollectionValue)) {
				return mappedCollectionValue;
			}
		}

		String href = linkJSONObject.getString("href");

		if (Validator.isNotNull(href)) {
			return href;
		}

		return StringPool.BLANK;
	}

	public String getContainerLinkTarget(
		ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem) {

		JSONObject linkJSONObject =
			containerStyledLayoutStructureItem.getLinkJSONObject();

		if (linkJSONObject == null) {
			return StringPool.BLANK;
		}

		return linkJSONObject.getString("target");
	}

	public String getCssClass(
		StyledLayoutStructureItem styledLayoutStructureItem) {

		StringBundler cssClassSB = new StringBundler(43);

		if (Validator.isNotNull(styledLayoutStructureItem.getAlign())) {
			cssClassSB.append(" ");
			cssClassSB.append(styledLayoutStructureItem.getAlign());
		}

		if (Validator.isNotNull(
				styledLayoutStructureItem.getBackgroundColorCssClass())) {

			cssClassSB.append(" bg-");
			cssClassSB.append(
				styledLayoutStructureItem.getBackgroundColorCssClass());
		}

		if (Validator.isNotNull(
				styledLayoutStructureItem.getBorderColorCssClass())) {

			cssClassSB.append(" border-");
			cssClassSB.append(
				styledLayoutStructureItem.getBorderColorCssClass());
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getBorderRadius())) {
			cssClassSB.append(" ");
			cssClassSB.append(styledLayoutStructureItem.getBorderRadius());
		}

		if (Objects.equals(
				styledLayoutStructureItem.getContentDisplay(), "block")) {

			cssClassSB.append(" d-block");
		}

		if (Objects.equals(
				styledLayoutStructureItem.getContentDisplay(), "flex")) {

			cssClassSB.append(" d-flex");
		}

		if (Validator.isNotNull(
				styledLayoutStructureItem.getFontWeightCssClass())) {

			cssClassSB.append(StringPool.SPACE);
			cssClassSB.append(
				styledLayoutStructureItem.getFontWeightCssClass());
		}

		if (Validator.isNotNull(
				styledLayoutStructureItem.getFontSizeCssClass())) {

			cssClassSB.append(StringPool.SPACE);
			cssClassSB.append(styledLayoutStructureItem.getFontSizeCssClass());
		}

		if (Validator.isNotNull(
				styledLayoutStructureItem.getHeightCssClass())) {

			cssClassSB.append(StringPool.SPACE);
			cssClassSB.append(styledLayoutStructureItem.getHeightCssClass());
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getJustify())) {
			cssClassSB.append(" ");
			cssClassSB.append(styledLayoutStructureItem.getJustify());
		}

		boolean addHorizontalMargin = true;

		if (styledLayoutStructureItem instanceof
				ContainerStyledLayoutStructureItem) {

			ContainerStyledLayoutStructureItem
				containerStyledLayoutStructureItem =
					(ContainerStyledLayoutStructureItem)
						styledLayoutStructureItem;

			if (Objects.equals(
					containerStyledLayoutStructureItem.getWidthType(),
					"fixed")) {

				cssClassSB.append(" container");
			}

			if (!Objects.equals(
					containerStyledLayoutStructureItem.getWidthType(),
					"fixed")) {

				addHorizontalMargin = false;
			}
		}

		if (styledLayoutStructureItem.getMarginBottom() != -1L) {
			cssClassSB.append(" mb-");
			cssClassSB.append(styledLayoutStructureItem.getMarginBottom());
		}

		if (addHorizontalMargin) {
			if (styledLayoutStructureItem.getMarginLeft() != -1L) {
				cssClassSB.append(" ml-");
				cssClassSB.append(styledLayoutStructureItem.getMarginLeft());
			}

			if (styledLayoutStructureItem.getMarginRight() != -1L) {
				cssClassSB.append(" mr-");
				cssClassSB.append(styledLayoutStructureItem.getMarginRight());
			}
		}

		if (styledLayoutStructureItem.getMarginTop() != -1L) {
			cssClassSB.append(" mt-");
			cssClassSB.append(styledLayoutStructureItem.getMarginTop());
		}

		if (styledLayoutStructureItem.getPaddingBottom() != -1L) {
			cssClassSB.append(" pb-");
			cssClassSB.append(styledLayoutStructureItem.getPaddingBottom());
		}

		if (styledLayoutStructureItem.getPaddingLeft() != -1L) {
			cssClassSB.append(" pl-");
			cssClassSB.append(styledLayoutStructureItem.getPaddingLeft());
		}

		if (styledLayoutStructureItem.getPaddingRight() != -1L) {
			cssClassSB.append(" pr-");
			cssClassSB.append(styledLayoutStructureItem.getPaddingRight());
		}

		if (styledLayoutStructureItem.getPaddingTop() != -1L) {
			cssClassSB.append(" pt-");
			cssClassSB.append(styledLayoutStructureItem.getPaddingTop());
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getShadow())) {
			cssClassSB.append(StringPool.SPACE);
			cssClassSB.append(styledLayoutStructureItem.getShadow());
		}

		if (Validator.isNotNull(
				styledLayoutStructureItem.getTextAlignCssClass()) &&
			!Objects.equals(
				styledLayoutStructureItem.getTextAlignCssClass(), "none")) {

			cssClassSB.append(StringPool.SPACE);
			cssClassSB.append(styledLayoutStructureItem.getTextAlignCssClass());
		}

		if (Validator.isNotNull(
				styledLayoutStructureItem.getTextColorCssClass())) {

			cssClassSB.append(StringPool.SPACE);
			cssClassSB.append(styledLayoutStructureItem.getTextColorCssClass());
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getWidthCssClass())) {
			cssClassSB.append(StringPool.SPACE);
			cssClassSB.append(styledLayoutStructureItem.getWidthCssClass());
		}

		return cssClassSB.toString();
	}

	public InfoListRenderer<?> getInfoListRenderer(
		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem) {

		if (Validator.isNull(
				collectionStyledLayoutStructureItem.getListStyle())) {

			return null;
		}

		return _infoListRendererTracker.getInfoListRenderer(
			collectionStyledLayoutStructureItem.getListStyle());
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

	public LayoutDisplayPageProvider<?> getLayoutDisplayPageProvider(
		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem) {

		ListObjectReference listObjectReference = _getListObjectReference(
			collectionStyledLayoutStructureItem.getCollectionJSONObject());

		if (listObjectReference == null) {
			return null;
		}

		String className = listObjectReference.getItemType();

		if (Objects.equals(className, DLFileEntry.class.getName())) {
			className = FileEntry.class.getName();
		}

		return _layoutDisplayPageProviderTracker.getLayoutDisplayPageProvider(
			className);
	}

	public String getPortletFooterPaths() {
		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			_httpServletResponse, unsyncStringWriter);

		for (Portlet portlet : _getPortlets()) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			try {
				PortletJSONUtil.populatePortletJSONObject(
					_httpServletRequest, StringPool.BLANK, portlet, jsonObject);

				PortletJSONUtil.writeHeaderPaths(
					pipingServletResponse, jsonObject);
			}
			catch (Exception exception) {
				_log.error(
					"Unable to write portlet footer paths " +
						portlet.getPortletId(),
					exception);
			}
		}

		return unsyncStringWriter.toString();
	}

	public String getPortletHeaderPaths() {
		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			_httpServletResponse, unsyncStringWriter);

		for (Portlet portlet : _getPortlets()) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			try {
				PortletJSONUtil.populatePortletJSONObject(
					_httpServletRequest, StringPool.BLANK, portlet, jsonObject);

				PortletJSONUtil.writeFooterPaths(
					pipingServletResponse, jsonObject);
			}
			catch (Exception exception) {
				_log.error(
					"Unable to write portlet header paths " +
						portlet.getPortletId(),
					exception);
			}
		}

		return unsyncStringWriter.toString();
	}

	public String getStyle(StyledLayoutStructureItem styledLayoutStructureItem)
		throws PortalException {

		StringBundler styleSB = new StringBundler(39);

		styleSB.append("box-sizing: border-box;");

		String backgroundImage = _getBackgroundImage(
			styledLayoutStructureItem.getBackgroundImageJSONObject());

		if (Validator.isNotNull(
				styledLayoutStructureItem.getBackgroundColor())) {

			styleSB.append("background-color: ");
			styleSB.append(styledLayoutStructureItem.getBackgroundColor());
			styleSB.append(StringPool.SEMICOLON);
		}

		if (Validator.isNotNull(backgroundImage)) {
			styleSB.append("background-position: 50% 50%; background-repeat: ");
			styleSB.append("no-repeat; background-size: cover; ");
			styleSB.append("background-image: url(");
			styleSB.append(backgroundImage);
			styleSB.append(");");
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getBorderColor())) {
			styleSB.append("border-color: ");
			styleSB.append(styledLayoutStructureItem.getBorderColor());
			styleSB.append(StringPool.SEMICOLON);
		}

		if (styledLayoutStructureItem.getBorderWidth() != -1L) {
			styleSB.append("border-style: solid; border-width: ");
			styleSB.append(styledLayoutStructureItem.getBorderWidth());
			styleSB.append("px;");
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getFontFamily())) {
			styleSB.append("font-family: ");
			styleSB.append(styledLayoutStructureItem.getFontFamily());
			styleSB.append(StringPool.SEMICOLON);
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getMaxHeight())) {
			styleSB.append("max-height: ");
			styleSB.append(styledLayoutStructureItem.getMaxHeight());
			styleSB.append(StringPool.SEMICOLON);
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getMaxWidth())) {
			styleSB.append("max-width: ");
			styleSB.append(styledLayoutStructureItem.getMaxWidth());
			styleSB.append(StringPool.SEMICOLON);
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getMinHeight())) {
			styleSB.append("min-height: ");
			styleSB.append(styledLayoutStructureItem.getMinHeight());
			styleSB.append(StringPool.SEMICOLON);
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getMinWidth())) {
			styleSB.append("min-width: ");
			styleSB.append(styledLayoutStructureItem.getMinWidth());
			styleSB.append(StringPool.SEMICOLON);
		}

		if (styledLayoutStructureItem.getOpacity() != -1L) {
			styleSB.append("opacity: ");
			styleSB.append(styledLayoutStructureItem.getOpacity() / 100.0);
			styleSB.append(StringPool.SEMICOLON);
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getOverflow())) {
			styleSB.append("overflow: ");
			styleSB.append(styledLayoutStructureItem.getOverflow());
			styleSB.append(StringPool.SEMICOLON);
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getTextColor())) {
			styleSB.append("color: ");
			styleSB.append(styledLayoutStructureItem.getTextColor());
			styleSB.append(StringPool.SEMICOLON);
		}

		return styleSB.toString();
	}

	private String _getBackgroundImage(JSONObject rowConfigJSONObject)
		throws PortalException {

		if (rowConfigJSONObject == null) {
			return StringPool.BLANK;
		}

		String mappedField = rowConfigJSONObject.getString("mappedField");

		if (Validator.isNotNull(mappedField)) {
			Object infoItem = _httpServletRequest.getAttribute(
				InfoDisplayWebKeys.INFO_ITEM);

			InfoItemDetails infoItemDetails =
				(InfoItemDetails)_httpServletRequest.getAttribute(
					InfoDisplayWebKeys.INFO_ITEM_DETAILS);

			if ((infoItem != null) && (infoItemDetails != null)) {
				InfoItemFieldValuesProvider<Object>
					infoItemFieldValuesProvider =
						_infoItemServiceTracker.getFirstInfoItemService(
							InfoItemFieldValuesProvider.class,
							infoItemDetails.getClassName());

				if (infoItemFieldValuesProvider != null) {
					InfoFieldValue<Object> infoItemFieldValue =
						infoItemFieldValuesProvider.getInfoItemFieldValue(
							infoItem, mappedField);

					if (infoItemFieldValue != null) {
						Object object = infoItemFieldValue.getValue(
							LocaleUtil.getDefault());

						if (object instanceof JSONObject) {
							JSONObject fieldValueJSONObject =
								(JSONObject)object;

							return fieldValueJSONObject.getString(
								"url", StringPool.BLANK);
						}
						else if (object instanceof String) {
							return (String)object;
						}
						else if (object instanceof WebImage) {
							WebImage webImage = (WebImage)object;

							return webImage.getUrl();
						}
					}
				}
			}
		}

		String fieldId = rowConfigJSONObject.getString("fieldId");

		if (Validator.isNotNull(fieldId)) {
			long classNameId = rowConfigJSONObject.getLong("classNameId");
			long classPK = rowConfigJSONObject.getLong("classPK");

			if ((classNameId != 0L) && (classPK != 0L)) {
				String className = PortalUtil.getClassName(classNameId);

				InfoItemFieldValuesProvider<Object>
					infoItemFieldValuesProvider =
						_infoItemServiceTracker.getFirstInfoItemService(
							InfoItemFieldValuesProvider.class, className);

				InfoItemObjectProvider<Object> infoItemObjectProvider =
					_infoItemServiceTracker.getFirstInfoItemService(
						InfoItemObjectProvider.class, className);

				if ((infoItemObjectProvider != null) &&
					(infoItemFieldValuesProvider != null)) {

					InfoItemIdentifier infoItemIdentifier =
						new ClassPKInfoItemIdentifier(classPK);

					Object infoItem = infoItemObjectProvider.getInfoItem(
						infoItemIdentifier);

					if (infoItem != null) {
						InfoFieldValue<Object> infoItemFieldValue =
							infoItemFieldValuesProvider.getInfoItemFieldValue(
								infoItem, fieldId);

						if (infoItemFieldValue != null) {
							Object object = infoItemFieldValue.getValue(
								LocaleUtil.getDefault());

							if (object instanceof JSONObject) {
								JSONObject fieldValueJSONObject =
									(JSONObject)object;

								return fieldValueJSONObject.getString(
									"url", StringPool.BLANK);
							}
							else if (object instanceof String) {
								return (String)object;
							}
							else if (object instanceof WebImage) {
								WebImage webImage = (WebImage)object;

								return webImage.getUrl();
							}
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

	private String _getMappedCollectionValue(
		String collectionFieldId, Object displayObject) {

		if (!(displayObject instanceof ClassedModel)) {
			return StringPool.BLANK;
		}

		ClassedModel classedModel = (ClassedModel)displayObject;

		// LPS-111037

		String className = classedModel.getModelClassName();

		if (classedModel instanceof FileEntry) {
			className = FileEntry.class.getName();
		}

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class, className);

		if (infoItemFieldValuesProvider == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get info item field values provider for class " +
						className);
			}

			return StringPool.BLANK;
		}

		InfoFieldValue<Object> infoFieldValue =
			infoItemFieldValuesProvider.getInfoItemFieldValue(
				displayObject, collectionFieldId);

		if (infoFieldValue == null) {
			return StringPool.BLANK;
		}

		Object value = infoFieldValue.getValue();

		if (value instanceof ContentAccessor) {
			ContentAccessor contentAccessor = (ContentAccessor)infoFieldValue;

			return contentAccessor.getContent();
		}

		if (value instanceof String) {
			return (String)value;
		}

		return StringPool.BLANK;
	}

	private List<Portlet> _getPortlets() {
		if (_portlets != null) {
			return _portlets;
		}

		_portlets = new ArrayList<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<PortletPreferences> portletPreferencesList =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, themeDisplay.getPlid());

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(), portletPreferences.getPortletId());

			_portlets.add(portlet);
		}

		return _portlets;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RenderFragmentLayoutDisplayContext.class);

	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private final InfoItemServiceTracker _infoItemServiceTracker;
	private final InfoListRendererTracker _infoListRendererTracker;
	private final LayoutDisplayPageProviderTracker
		_layoutDisplayPageProviderTracker;
	private final LayoutListRetrieverTracker _layoutListRetrieverTracker;
	private final ListObjectReferenceFactoryTracker
		_listObjectReferenceFactoryTracker;
	private List<Portlet> _portlets;

}