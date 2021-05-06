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
import com.liferay.fragment.entry.processor.helper.FragmentEntryProcessorHelper;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.frontend.token.definition.FrontendTokenMapping;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
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
import com.liferay.layout.responsive.ResponsiveLayoutStructureUtil;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItemUtil;
import com.liferay.layout.util.structure.StyledLayoutStructureItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.SegmentsEntryRetriever;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsWebKeys;
import com.liferay.segments.context.RequestContextMapper;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.util.DefaultStyleBookEntryUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rubén Pulido
 */
public class RenderLayoutStructureDisplayContext {

	public RenderLayoutStructureDisplayContext(
		Map<String, Object> fieldValues,
		FragmentEntryProcessorHelper fragmentEntryProcessorHelper,
		FrontendTokenDefinitionRegistry frontendTokenDefinitionRegistry,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		InfoItemServiceTracker infoItemServiceTracker,
		InfoListRendererTracker infoListRendererTracker,
		LayoutDisplayPageProviderTracker layoutDisplayPageProviderTracker,
		LayoutListRetrieverTracker layoutListRetrieverTracker,
		LayoutStructure layoutStructure,
		ListObjectReferenceFactoryTracker listObjectReferenceFactoryTracker,
		String mainItemId, String mode,
		RequestContextMapper requestContextMapper,
		SegmentsEntryRetriever segmentsEntryRetriever, boolean showPreview) {

		_fieldValues = fieldValues;
		_fragmentEntryProcessorHelper = fragmentEntryProcessorHelper;
		_frontendTokenDefinitionRegistry = frontendTokenDefinitionRegistry;
		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;
		_infoItemServiceTracker = infoItemServiceTracker;
		_infoListRendererTracker = infoListRendererTracker;
		_layoutDisplayPageProviderTracker = layoutDisplayPageProviderTracker;
		_layoutListRetrieverTracker = layoutListRetrieverTracker;
		_layoutStructure = layoutStructure;
		_listObjectReferenceFactoryTracker = listObjectReferenceFactoryTracker;
		_mainItemId = mainItemId;
		_mode = mode;
		_requestContextMapper = requestContextMapper;
		_segmentsEntryRetriever = segmentsEntryRetriever;
		_showPreview = showPreview;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<Object> getCollection(
		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem) {

		JSONObject collectionJSONObject =
			collectionStyledLayoutStructureItem.getCollectionJSONObject();

		if ((collectionJSONObject == null) ||
			(collectionJSONObject.length() <= 0)) {

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

		defaultLayoutListRetrieverContext.setAssetCategoryIds(
			_getAssetCategoryIds());
		defaultLayoutListRetrieverContext.setContextObject(
			_httpServletRequest.getAttribute(InfoDisplayWebKeys.INFO_ITEM));
		defaultLayoutListRetrieverContext.setSegmentsEntryIds(
			_getSegmentsEntryIds());
		defaultLayoutListRetrieverContext.setPagination(
			Pagination.of(
				collectionStyledLayoutStructureItem.getNumberOfItems(), 0));

		return layoutListRetriever.getList(
			listObjectReference, defaultLayoutListRetrieverContext);
	}

	public LayoutDisplayPageProvider<?> getCollectionLayoutDisplayPageProvider(
		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem) {

		JSONObject collectionJSONObject =
			collectionStyledLayoutStructureItem.getCollectionJSONObject();

		if ((collectionJSONObject == null) ||
			(collectionJSONObject.length() <= 0)) {

			return null;
		}

		ListObjectReference listObjectReference = _getListObjectReference(
			collectionJSONObject);

		if (listObjectReference == null) {
			return null;
		}

		String className = listObjectReference.getItemType();

		if (Objects.equals(className, DLFileEntry.class.getName())) {
			className = FileEntry.class.getName();
		}

		return _layoutDisplayPageProviderTracker.
			getLayoutDisplayPageProviderByClassName(className);
	}

	public String getContainerLinkHref(
			ContainerStyledLayoutStructureItem
				containerStyledLayoutStructureItem,
			Object displayObject)
		throws PortalException {

		return getContainerLinkHref(
			containerStyledLayoutStructureItem, displayObject,
			LocaleUtil.getMostRelevantLocale());
	}

	public String getContainerLinkHref(
			ContainerStyledLayoutStructureItem
				containerStyledLayoutStructureItem,
			Object displayObject, Locale locale)
		throws PortalException {

		JSONObject linkJSONObject =
			containerStyledLayoutStructureItem.getLinkJSONObject();

		if (linkJSONObject == null) {
			return StringPool.BLANK;
		}

		JSONObject localizedJSONObject = linkJSONObject.getJSONObject(
			LocaleUtil.toLanguageId(locale));

		if ((localizedJSONObject != null) &&
			(localizedJSONObject.length() > 0)) {

			linkJSONObject = localizedJSONObject;
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

		JSONObject layoutJSONObject = linkJSONObject.getJSONObject("layout");

		if (layoutJSONObject != null) {
			long groupId = layoutJSONObject.getLong("groupId");
			boolean privateLayout = layoutJSONObject.getBoolean(
				"privateLayout");
			long layoutId = layoutJSONObject.getLong("layoutId");

			Layout layout = LayoutLocalServiceUtil.fetchLayout(
				groupId, privateLayout, layoutId);

			if (layout == null) {
				return StringPool.POUND;
			}

			return PortalUtil.getLayoutFullURL(layout, _themeDisplay);
		}

		String href = linkJSONObject.getString("href");

		if (Validator.isNotNull(href)) {
			return href;
		}

		return StringPool.BLANK;
	}

	public String getContainerLinkTarget(
		ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem) {

		return getContainerLinkTarget(
			containerStyledLayoutStructureItem,
			LocaleUtil.getMostRelevantLocale());
	}

	public String getContainerLinkTarget(
		ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem,
		Locale locale) {

		JSONObject linkJSONObject =
			containerStyledLayoutStructureItem.getLinkJSONObject();

		if (linkJSONObject == null) {
			return StringPool.BLANK;
		}

		JSONObject localizedJSONObject = linkJSONObject.getJSONObject(
			LocaleUtil.toLanguageId(locale));

		if ((localizedJSONObject != null) &&
			(localizedJSONObject.length() > 0)) {

			linkJSONObject = localizedJSONObject;
		}

		return linkJSONObject.getString("target");
	}

	public String getCssClass(
			StyledLayoutStructureItem styledLayoutStructureItem)
		throws Exception {

		StringBundler cssClassSB = new StringBundler(33);

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

		if (Objects.equals(
				styledLayoutStructureItem.getContentDisplay(), "flex-column")) {

			cssClassSB.append(" d-flex flex-column");
		}

		if (Objects.equals(
				styledLayoutStructureItem.getContentDisplay(), "flex-row")) {

			cssClassSB.append(" d-flex flex-row");
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

				cssClassSB.append(" container-fluid container-fluid-max-xl");

				addHorizontalMargin = false;
			}
		}

		if (styledLayoutStructureItem.getMarginBottom() != -1L) {
			cssClassSB.append(" mb-lg-");
			cssClassSB.append(styledLayoutStructureItem.getMarginBottom());
		}

		if (addHorizontalMargin) {
			if (styledLayoutStructureItem.getMarginLeft() != -1L) {
				cssClassSB.append(" ml-lg-");
				cssClassSB.append(styledLayoutStructureItem.getMarginLeft());
			}

			if (styledLayoutStructureItem.getMarginRight() != -1L) {
				cssClassSB.append(" mr-lg-");
				cssClassSB.append(styledLayoutStructureItem.getMarginRight());
			}
		}

		if (styledLayoutStructureItem.getMarginTop() != -1L) {
			cssClassSB.append(" mt-lg-");
			cssClassSB.append(styledLayoutStructureItem.getMarginTop());
		}

		if (styledLayoutStructureItem.getPaddingBottom() != -1L) {
			cssClassSB.append(" pb-lg-");
			cssClassSB.append(styledLayoutStructureItem.getPaddingBottom());
		}

		if (styledLayoutStructureItem.getPaddingLeft() != -1L) {
			cssClassSB.append(" pl-lg-");
			cssClassSB.append(styledLayoutStructureItem.getPaddingLeft());
		}

		if (styledLayoutStructureItem.getPaddingRight() != -1L) {
			cssClassSB.append(" pr-lg-");
			cssClassSB.append(styledLayoutStructureItem.getPaddingRight());
		}

		if (styledLayoutStructureItem.getPaddingTop() != -1L) {
			cssClassSB.append(" pt-lg-");
			cssClassSB.append(styledLayoutStructureItem.getPaddingTop());
		}

		String textAlignCssClass =
			styledLayoutStructureItem.getTextAlignCssClass();

		if (Validator.isNotNull(textAlignCssClass) &&
			!Objects.equals(textAlignCssClass, "none")) {

			if (!StringUtil.startsWith(textAlignCssClass, "text-")) {
				cssClassSB.append(" text-");
			}
			else {
				cssClassSB.append(StringPool.SPACE);
			}

			cssClassSB.append(styledLayoutStructureItem.getTextAlignCssClass());
		}

		if (Validator.isNotNull(
				styledLayoutStructureItem.getTextColorCssClass())) {

			cssClassSB.append(" text-");
			cssClassSB.append(styledLayoutStructureItem.getTextColorCssClass());
		}

		String responsiveCssClassValues =
			ResponsiveLayoutStructureUtil.getResponsiveCssClassValues(
				styledLayoutStructureItem);

		if (Validator.isNotNull(responsiveCssClassValues)) {
			cssClassSB.append(StringPool.SPACE);
			cssClassSB.append(responsiveCssClassValues);
		}

		return cssClassSB.toString();
	}

	public DefaultFragmentRendererContext getDefaultFragmentRendererContext(
		FragmentEntryLink fragmentEntryLink, String itemId) {

		DefaultFragmentRendererContext defaultFragmentRendererContext =
			new DefaultFragmentRendererContext(fragmentEntryLink);

		defaultFragmentRendererContext.setDisplayObject(
			_httpServletRequest.getAttribute(
				InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT));
		defaultFragmentRendererContext.setLocale(_themeDisplay.getLocale());

		Layout layout = _themeDisplay.getLayout();

		if (!Objects.equals(layout.getType(), LayoutConstants.TYPE_PORTLET)) {
			defaultFragmentRendererContext.setFieldValues(_fieldValues);
			defaultFragmentRendererContext.setMode(_mode);
			defaultFragmentRendererContext.setPreviewClassNameId(
				_getPreviewClassNameId());
			defaultFragmentRendererContext.setPreviewClassPK(
				_getPreviewClassPK());
			defaultFragmentRendererContext.setPreviewType(_getPreviewType());
			defaultFragmentRendererContext.setPreviewVersion(
				_getPreviewVersion());
			defaultFragmentRendererContext.setSegmentsExperienceIds(
				_getSegmentsExperienceIds());
		}

		if (LayoutStructureItemUtil.hasAncestor(
				itemId, LayoutDataItemTypeConstants.TYPE_COLLECTION_ITEM,
				_layoutStructure)) {

			defaultFragmentRendererContext.setUseCachedContent(false);
		}

		return defaultFragmentRendererContext;
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

	public LayoutStructure getLayoutStructure() {
		return _layoutStructure;
	}

	public List<String> getMainChildrenItemIds() {
		LayoutStructure layoutStructure = getLayoutStructure();

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItem(_getMainItemId());

		return layoutStructureItem.getChildrenItemIds();
	}

	public String getStyle(StyledLayoutStructureItem styledLayoutStructureItem)
		throws Exception {

		StringBundler styleSB = new StringBundler(60);

		styleSB.append("box-sizing: border-box;");

		if (Validator.isNotNull(
				styledLayoutStructureItem.getBackgroundColor())) {

			styleSB.append("background-color: ");
			styleSB.append(
				getStyleFromStyleBookEntry(
					styledLayoutStructureItem.getBackgroundColor()));
			styleSB.append(StringPool.SEMICOLON);
		}

		JSONObject backgroundImageJSONObject =
			styledLayoutStructureItem.getBackgroundImageJSONObject();

		String backgroundImage = _getBackgroundImage(backgroundImageJSONObject);

		if (Validator.isNotNull(backgroundImage)) {
			styleSB.append("background-position: 50% 50%; background-repeat: ");
			styleSB.append("no-repeat; background-size: cover; ");
			styleSB.append("background-image: url(");
			styleSB.append(backgroundImage);
			styleSB.append(");");
		}

		long fileEntryId = 0;

		if (backgroundImageJSONObject.has("fileEntryId")) {
			fileEntryId = backgroundImageJSONObject.getLong("fileEntryId");
		}
		else if (backgroundImageJSONObject.has("classNameId") &&
				 backgroundImageJSONObject.has("classPK") &&
				 backgroundImageJSONObject.has("fieldId")) {

			fileEntryId = _fragmentEntryProcessorHelper.getFileEntryId(
				backgroundImageJSONObject.getLong("classNameId"),
				backgroundImageJSONObject.getLong("classPK"),
				backgroundImageJSONObject.getString("fieldId"),
				LocaleUtil.fromLanguageId(_themeDisplay.getLanguageId()));
		}
		else if (backgroundImageJSONObject.has("collectionFieldId")) {
			fileEntryId = _fragmentEntryProcessorHelper.getFileEntryId(
				_httpServletRequest.getAttribute(
					InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT),
				backgroundImageJSONObject.getString("collectionFieldId"),
				LocaleUtil.fromLanguageId(_themeDisplay.getLanguageId()));
		}
		else if (backgroundImageJSONObject.has("mappedField")) {
			fileEntryId = _getFileEntryId(
				backgroundImageJSONObject.getString("mappedField"),
				LocaleUtil.fromLanguageId(_themeDisplay.getLanguageId()));
		}

		if (fileEntryId != 0) {
			styleSB.append("--background-image-file-entry-id:");
			styleSB.append(fileEntryId);
			styleSB.append(StringPool.SEMICOLON);
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getBorderColor())) {
			styleSB.append("border-color: ");
			styleSB.append(
				getStyleFromStyleBookEntry(
					styledLayoutStructureItem.getBorderColor()));
			styleSB.append(StringPool.SEMICOLON);
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getBorderRadius())) {
			styleSB.append("border-radius: ");
			styleSB.append(
				getStyleFromStyleBookEntry(
					styledLayoutStructureItem.getBorderRadius()));
			styleSB.append(StringPool.SEMICOLON);
		}

		if (styledLayoutStructureItem.getBorderWidth() != -1L) {
			styleSB.append("border-style: solid; border-width: ");
			styleSB.append(styledLayoutStructureItem.getBorderWidth());
			styleSB.append("px;");
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getShadow())) {
			styleSB.append("box-shadow: ");
			styleSB.append(
				getStyleFromStyleBookEntry(
					styledLayoutStructureItem.getShadow()));
			styleSB.append(StringPool.SEMICOLON);
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getFontFamily())) {
			styleSB.append("font-family: ");
			styleSB.append(
				getStyleFromStyleBookEntry(
					styledLayoutStructureItem.getFontFamily()));
			styleSB.append(StringPool.SEMICOLON);
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getFontSize())) {
			styleSB.append("font-size: ");
			styleSB.append(
				getStyleFromStyleBookEntry(
					styledLayoutStructureItem.getFontSize()));
			styleSB.append(StringPool.SEMICOLON);
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getFontWeight())) {
			styleSB.append("font-weight: ");
			styleSB.append(
				getStyleFromStyleBookEntry(
					styledLayoutStructureItem.getFontWeight()));
			styleSB.append(StringPool.SEMICOLON);
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getHeight())) {
			styleSB.append("height: ");
			styleSB.append(styledLayoutStructureItem.getHeight());
			styleSB.append(StringPool.SEMICOLON);
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getMaxHeight())) {
			styleSB.append("max-height: ");
			styleSB.append(
				getStyleFromStyleBookEntry(
					styledLayoutStructureItem.getMaxHeight()));
			styleSB.append(StringPool.SEMICOLON);
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getMaxWidth())) {
			styleSB.append("max-width: ");
			styleSB.append(
				getStyleFromStyleBookEntry(
					styledLayoutStructureItem.getMaxWidth()));
			styleSB.append(StringPool.SEMICOLON);
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getMinHeight())) {
			styleSB.append("min-height: ");
			styleSB.append(
				getStyleFromStyleBookEntry(
					styledLayoutStructureItem.getMinHeight()));
			styleSB.append(StringPool.SEMICOLON);
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getMinWidth())) {
			styleSB.append("min-width: ");
			styleSB.append(
				getStyleFromStyleBookEntry(
					styledLayoutStructureItem.getMinWidth()));
			styleSB.append(StringPool.SEMICOLON);
		}

		if (styledLayoutStructureItem.getOpacity() != -1L) {
			styleSB.append("opacity: ");
			styleSB.append(styledLayoutStructureItem.getOpacity() / 100.0);
			styleSB.append(StringPool.SEMICOLON);
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getOverflow())) {
			styleSB.append("overflow: ");
			styleSB.append(
				getStyleFromStyleBookEntry(
					styledLayoutStructureItem.getOverflow()));
			styleSB.append(StringPool.SEMICOLON);
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getTextColor())) {
			styleSB.append("color: ");
			styleSB.append(
				getStyleFromStyleBookEntry(
					styledLayoutStructureItem.getTextColor()));
			styleSB.append(StringPool.SEMICOLON);
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getWidth())) {
			styleSB.append("width: ");
			styleSB.append(styledLayoutStructureItem.getWidth());
			styleSB.append(StringPool.SEMICOLON);
		}

		return styleSB.toString();
	}

	public String getStyleFromStyleBookEntry(String styleValue)
		throws Exception {

		JSONObject frontendTokensValuesJSONObject =
			_getFrontendTokensJSONObject();

		JSONObject styleValueJSONObject =
			frontendTokensValuesJSONObject.getJSONObject(styleValue);

		if (styleValueJSONObject == null) {
			return styleValue;
		}

		String cssVariable = styleValueJSONObject.getString(
			FrontendTokenMapping.TYPE_CSS_VARIABLE);

		return "var(--" + cssVariable + ")";
	}

	private long[][] _getAssetCategoryIds() {
		if (_assetCategoryIds != null) {
			return _assetCategoryIds;
		}

		Set<long[]> assetCategoryIdsSet = new HashSet<>();

		HttpServletRequest originalHttpServletRequest =
			PortalUtil.getOriginalServletRequest(_httpServletRequest);

		Map<String, String[]> parameterMap =
			originalHttpServletRequest.getParameterMap();

		Set<String> parameterNames = parameterMap.keySet();

		Stream<String> parameterNameStream = parameterNames.stream();

		Set<String> categoryIdParameterNames = parameterNameStream.filter(
			parameterName -> parameterName.startsWith("categoryId_")
		).collect(
			Collectors.toSet()
		);

		for (String categoryIdParameterName : categoryIdParameterNames) {
			String[] values = parameterMap.get(categoryIdParameterName);

			if (ArrayUtil.isNotEmpty(values)) {
				assetCategoryIdsSet.add(
					ArrayUtil.filter(
						GetterUtil.getLongValues(values),
						categoryId -> categoryId != 0));
			}
		}

		_assetCategoryIds = assetCategoryIdsSet.toArray(
			new long[assetCategoryIdsSet.size()][]);

		return _assetCategoryIds;
	}

	private String _getBackgroundImage(JSONObject jsonObject) throws Exception {
		if (jsonObject == null) {
			return StringPool.BLANK;
		}

		String mappedCollectionValue = StringPool.BLANK;

		String collectionFieldId = jsonObject.getString("collectionFieldId");

		if (Validator.isNotNull(collectionFieldId)) {
			Object displayObject = _httpServletRequest.getAttribute(
				InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT);

			mappedCollectionValue = _getMappedCollectionValue(
				collectionFieldId, displayObject);
		}

		if (Validator.isNotNull(mappedCollectionValue)) {
			return mappedCollectionValue;
		}

		String mappedField = jsonObject.getString("mappedField");

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

		String fieldId = jsonObject.getString("fieldId");

		if (Validator.isNotNull(fieldId)) {
			long classNameId = jsonObject.getLong("classNameId");
			long classPK = jsonObject.getLong("classPK");

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

		String backgroundImageURL = jsonObject.getString("url");

		if (Validator.isNotNull(backgroundImageURL)) {
			return backgroundImageURL;
		}

		return StringPool.BLANK;
	}

	private long _getFileEntryId(String fieldId, Locale locale)
		throws Exception {

		InfoItemDetails infoItemDetails =
			(InfoItemDetails)_httpServletRequest.getAttribute(
				InfoDisplayWebKeys.INFO_ITEM_DETAILS);

		if (infoItemDetails == null) {
			return 0;
		}

		InfoItemReference infoItemReference =
			infoItemDetails.getInfoItemReference();

		if (infoItemReference == null) {
			return 0;
		}

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier)) {
			return 0;
		}

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)infoItemIdentifier;

		return _fragmentEntryProcessorHelper.getFileEntryId(
			PortalUtil.getClassNameId(infoItemReference.getClassName()),
			classPKInfoItemIdentifier.getClassPK(), fieldId, locale);
	}

	private JSONObject _getFrontendTokensJSONObject() throws Exception {
		if (_frontendTokensJSONObject != null) {
			return _frontendTokensJSONObject;
		}

		_frontendTokensJSONObject = JSONFactoryUtil.createJSONObject();

		StyleBookEntry styleBookEntry = null;

		boolean styleBookEntryPreview = ParamUtil.getBoolean(
			_httpServletRequest, "styleBookEntryPreview");

		if (!styleBookEntryPreview) {
			styleBookEntry = DefaultStyleBookEntryUtil.getDefaultStyleBookEntry(
				_themeDisplay.getLayout());
		}

		JSONObject frontendTokenValuesJSONObject =
			JSONFactoryUtil.createJSONObject();

		if (styleBookEntry != null) {
			frontendTokenValuesJSONObject = JSONFactoryUtil.createJSONObject(
				styleBookEntry.getFrontendTokensValues());
		}

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.fetchLayoutSet(
			_themeDisplay.getSiteGroupId(), false);

		FrontendTokenDefinition frontendTokenDefinition =
			_frontendTokenDefinitionRegistry.getFrontendTokenDefinition(
				layoutSet.getThemeId());

		if (frontendTokenDefinition == null) {
			return JSONFactoryUtil.createJSONObject();
		}

		JSONObject frontendTokenDefinitionJSONObject =
			JSONFactoryUtil.createJSONObject(
				frontendTokenDefinition.getJSON(_themeDisplay.getLocale()));

		JSONArray frontendTokenCategoriesJSONArray =
			frontendTokenDefinitionJSONObject.getJSONArray(
				"frontendTokenCategories");

		for (int i = 0; i < frontendTokenCategoriesJSONArray.length(); i++) {
			JSONObject frontendTokenCategoryJSONObject =
				frontendTokenCategoriesJSONArray.getJSONObject(i);

			JSONArray frontendTokenSetsJSONArray =
				frontendTokenCategoryJSONObject.getJSONArray(
					"frontendTokenSets");

			for (int j = 0; j < frontendTokenSetsJSONArray.length(); j++) {
				JSONObject frontendTokenSetJSONObject =
					frontendTokenSetsJSONArray.getJSONObject(j);

				JSONArray frontendTokensJSONArray =
					frontendTokenSetJSONObject.getJSONArray("frontendTokens");

				for (int k = 0; k < frontendTokensJSONArray.length(); k++) {
					JSONObject frontendTokenJSONObject =
						frontendTokensJSONArray.getJSONObject(k);

					String cssVariable = StringPool.BLANK;

					JSONArray mappingsJSONArray =
						frontendTokenJSONObject.getJSONArray("mappings");

					for (int l = 0; l < mappingsJSONArray.length(); l++) {
						JSONObject mappingJSONObject =
							mappingsJSONArray.getJSONObject(l);

						if (Objects.equals(
								mappingJSONObject.getString("type"),
								FrontendTokenMapping.TYPE_CSS_VARIABLE)) {

							cssVariable = mappingJSONObject.getString("value");
						}
					}

					String value = StringPool.BLANK;

					String name = frontendTokenJSONObject.getString("name");

					JSONObject valueJSONObject =
						frontendTokenValuesJSONObject.getJSONObject(name);

					if (valueJSONObject != null) {
						value = valueJSONObject.getString("value");
					}
					else {
						value = frontendTokenJSONObject.getString(
							"defaultValue");
					}

					_frontendTokensJSONObject.put(
						name,
						JSONUtil.put(
							FrontendTokenMapping.TYPE_CSS_VARIABLE, cssVariable
						).put(
							"value", value
						));
				}
			}
		}

		return _frontendTokensJSONObject;
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

	private String _getMainItemId() {
		if (Validator.isNotNull(_mainItemId)) {
			return _mainItemId;
		}

		return _layoutStructure.getMainItemId();
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

		Object value = infoFieldValue.getValue(
			LocaleUtil.fromLanguageId(_themeDisplay.getLanguageId()));

		if (value instanceof ContentAccessor) {
			ContentAccessor contentAccessor = (ContentAccessor)infoFieldValue;

			return contentAccessor.getContent();
		}

		if (value instanceof String) {
			return (String)value;
		}

		if (!(value instanceof WebImage)) {
			return StringPool.BLANK;
		}

		WebImage webImage = (WebImage)value;

		String url = webImage.getUrl();

		if (Validator.isNotNull(url)) {
			return url;
		}

		return StringPool.BLANK;
	}

	private long _getPreviewClassNameId() {
		if (_previewClassNameId != null) {
			return _previewClassNameId;
		}

		if (!_showPreview) {
			return 0;
		}

		_previewClassNameId = ParamUtil.getLong(
			_httpServletRequest, "previewClassNameId");

		return _previewClassNameId;
	}

	private long _getPreviewClassPK() {
		if (_previewClassPK != null) {
			return _previewClassPK;
		}

		if (!_showPreview) {
			return 0;
		}

		_previewClassPK = ParamUtil.getLong(
			_httpServletRequest, "previewClassPK");

		return _previewClassPK;
	}

	private int _getPreviewType() {
		if (_previewType != null) {
			return _previewType;
		}

		if (!_showPreview) {
			return 0;
		}

		_previewType = ParamUtil.getInteger(_httpServletRequest, "previewType");

		return _previewType;
	}

	private String _getPreviewVersion() {
		if (_previewVersion != null) {
			return _previewVersion;
		}

		if (!_showPreview) {
			return null;
		}

		_previewVersion = ParamUtil.getString(
			_httpServletRequest, "previewVersion");

		return _previewVersion;
	}

	private long[] _getSegmentsEntryIds() {
		if (_segmentsEntryIds != null) {
			return _segmentsEntryIds;
		}

		_segmentsEntryIds = _segmentsEntryRetriever.getSegmentsEntryIds(
			_themeDisplay.getScopeGroupId(), _themeDisplay.getUserId(),
			_requestContextMapper.map(_httpServletRequest));

		return _segmentsEntryIds;
	}

	private long[] _getSegmentsExperienceIds() {
		if (_segmentsExperienceIds != null) {
			return _segmentsExperienceIds;
		}

		_segmentsExperienceIds = GetterUtil.getLongValues(
			_httpServletRequest.getAttribute(
				SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS),
			new long[] {SegmentsExperienceConstants.ID_DEFAULT});

		return _segmentsExperienceIds;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RenderLayoutStructureDisplayContext.class);

	private long[][] _assetCategoryIds;
	private final Map<String, Object> _fieldValues;
	private final FragmentEntryProcessorHelper _fragmentEntryProcessorHelper;
	private final FrontendTokenDefinitionRegistry
		_frontendTokenDefinitionRegistry;
	private JSONObject _frontendTokensJSONObject;
	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private final InfoItemServiceTracker _infoItemServiceTracker;
	private final InfoListRendererTracker _infoListRendererTracker;
	private final LayoutDisplayPageProviderTracker
		_layoutDisplayPageProviderTracker;
	private final LayoutListRetrieverTracker _layoutListRetrieverTracker;
	private final LayoutStructure _layoutStructure;
	private final ListObjectReferenceFactoryTracker
		_listObjectReferenceFactoryTracker;
	private final String _mainItemId;
	private final String _mode;
	private Long _previewClassNameId;
	private Long _previewClassPK;
	private Integer _previewType;
	private String _previewVersion;
	private final RequestContextMapper _requestContextMapper;
	private long[] _segmentsEntryIds;
	private final SegmentsEntryRetriever _segmentsEntryRetriever;
	private long[] _segmentsExperienceIds;
	private final boolean _showPreview;
	private final ThemeDisplay _themeDisplay;

}