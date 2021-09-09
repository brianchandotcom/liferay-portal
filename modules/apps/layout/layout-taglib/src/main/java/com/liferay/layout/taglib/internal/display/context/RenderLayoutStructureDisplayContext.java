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
import com.liferay.info.type.WebImage;
import com.liferay.layout.responsive.ResponsiveLayoutStructureUtil;
import com.liferay.layout.taglib.internal.servlet.ServletContextUtil;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsWebKeys;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.util.DefaultStyleBookEntryUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rubén Pulido
 */
public class RenderLayoutStructureDisplayContext {

	public RenderLayoutStructureDisplayContext(
		Map<String, Object> fieldValues, HttpServletRequest httpServletRequest,
		LayoutStructure layoutStructure, String mainItemId, String mode,
		boolean showPreview) {

		_fieldValues = fieldValues;
		_httpServletRequest = httpServletRequest;
		_layoutStructure = layoutStructure;
		_mainItemId = mainItemId;
		_mode = mode;
		_showPreview = showPreview;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
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
				InfoItemServiceTracker infoItemServiceTracker =
					ServletContextUtil.getInfoItemServiceTracker();

				InfoItemFieldValuesProvider<Object>
					infoItemFieldValuesProvider =
						infoItemServiceTracker.getFirstInfoItemService(
							InfoItemFieldValuesProvider.class,
							infoItemDetails.getClassName());

				if (infoItemFieldValuesProvider != null) {
					InfoFieldValue<Object> infoFieldValue =
						infoItemFieldValuesProvider.getInfoFieldValue(
							infoItem, mappedField);

					if (infoFieldValue != null) {
						Object object = infoFieldValue.getValue(
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
				InfoItemServiceTracker infoItemServiceTracker =
					ServletContextUtil.getInfoItemServiceTracker();

				String className = PortalUtil.getClassName(classNameId);

				InfoItemFieldValuesProvider<Object>
					infoItemFieldValuesProvider =
						infoItemServiceTracker.getFirstInfoItemService(
							InfoItemFieldValuesProvider.class, className);

				InfoItemObjectProvider<Object> infoItemObjectProvider =
					infoItemServiceTracker.getFirstInfoItemService(
						InfoItemObjectProvider.class, className);

				if ((infoItemObjectProvider != null) &&
					(infoItemFieldValuesProvider != null)) {

					InfoItemIdentifier infoItemIdentifier =
						new ClassPKInfoItemIdentifier(classPK);

					Object infoItem = infoItemObjectProvider.getInfoItem(
						infoItemIdentifier);

					if (infoItem != null) {
						InfoFieldValue<Object> infoFieldValue =
							infoItemFieldValuesProvider.getInfoFieldValue(
								infoItem, fieldId);

						if (infoFieldValue != null) {
							Object object = infoFieldValue.getValue(
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

		JSONObject hrefJSONObject = linkJSONObject.getJSONObject("href");

		if (hrefJSONObject != null) {
			return hrefJSONObject.getString(LocaleUtil.toLanguageId(locale));
		}

		return StringPool.BLANK;
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

		if (Objects.equals(styledLayoutStructureItem.getDisplay(), "none")) {
			cssClassSB.append(" d-lg-");
			cssClassSB.append(styledLayoutStructureItem.getDisplay());
		}
		else if (Objects.equals(
					styledLayoutStructureItem.getContentDisplay(),
					"flex-column")) {

			cssClassSB.append(" d-flex flex-column");
		}
		else if (Objects.equals(
					styledLayoutStructureItem.getContentDisplay(),
					"flex-row")) {

			cssClassSB.append(" d-flex flex-row");
		}
		else if (Validator.isNotNull(styledLayoutStructureItem.getDisplay())) {
			cssClassSB.append(" d-lg-");
			cssClassSB.append(styledLayoutStructureItem.getDisplay());
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

		if (Validator.isNotNull(styledLayoutStructureItem.getMarginBottom())) {
			cssClassSB.append(" mb-lg-");
			cssClassSB.append(styledLayoutStructureItem.getMarginBottom());
		}

		if (addHorizontalMargin) {
			if (Validator.isNotNull(
					styledLayoutStructureItem.getMarginLeft())) {

				cssClassSB.append(" ml-lg-");
				cssClassSB.append(styledLayoutStructureItem.getMarginLeft());
			}

			if (Validator.isNotNull(
					styledLayoutStructureItem.getMarginRight())) {

				cssClassSB.append(" mr-lg-");
				cssClassSB.append(styledLayoutStructureItem.getMarginRight());
			}
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getMarginTop())) {
			cssClassSB.append(" mt-lg-");
			cssClassSB.append(styledLayoutStructureItem.getMarginTop());
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getPaddingBottom())) {
			cssClassSB.append(" pb-lg-");
			cssClassSB.append(styledLayoutStructureItem.getPaddingBottom());
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getPaddingLeft())) {
			cssClassSB.append(" pl-lg-");
			cssClassSB.append(styledLayoutStructureItem.getPaddingLeft());
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getPaddingRight())) {
			cssClassSB.append(" pr-lg-");
			cssClassSB.append(styledLayoutStructureItem.getPaddingRight());
		}

		if (Validator.isNotNull(styledLayoutStructureItem.getPaddingTop())) {
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

		StringBundler styleSB = new StringBundler(59);

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

			FragmentEntryProcessorHelper fragmentEntryProcessorHelper =
				ServletContextUtil.getFragmentEntryProcessorHelper();

			fileEntryId = fragmentEntryProcessorHelper.getFileEntryId(
				backgroundImageJSONObject.getLong("classNameId"),
				backgroundImageJSONObject.getLong("classPK"),
				backgroundImageJSONObject.getString("fieldId"),
				LocaleUtil.fromLanguageId(_themeDisplay.getLanguageId()));
		}
		else if (backgroundImageJSONObject.has("collectionFieldId")) {
			FragmentEntryProcessorHelper fragmentEntryProcessorHelper =
				ServletContextUtil.getFragmentEntryProcessorHelper();

			fileEntryId = fragmentEntryProcessorHelper.getFileEntryId(
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

		if (Validator.isNotNull(styledLayoutStructureItem.getBorderWidth())) {
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

		if (Validator.isNotNull(styledLayoutStructureItem.getOpacity())) {
			int opacity = GetterUtil.getInteger(
				styledLayoutStructureItem.getOpacity(), 100);

			styleSB.append("opacity: ");
			styleSB.append(opacity / 100.0);
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
				InfoItemServiceTracker infoItemServiceTracker =
					ServletContextUtil.getInfoItemServiceTracker();

				InfoItemFieldValuesProvider<Object>
					infoItemFieldValuesProvider =
						infoItemServiceTracker.getFirstInfoItemService(
							InfoItemFieldValuesProvider.class,
							infoItemDetails.getClassName());

				if (infoItemFieldValuesProvider != null) {
					InfoFieldValue<Object> infoFieldValue =
						infoItemFieldValuesProvider.getInfoFieldValue(
							infoItem, mappedField);

					if (infoFieldValue != null) {
						Object object = infoFieldValue.getValue(
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
				InfoItemServiceTracker infoItemServiceTracker =
					ServletContextUtil.getInfoItemServiceTracker();

				String className = PortalUtil.getClassName(classNameId);

				InfoItemFieldValuesProvider<Object>
					infoItemFieldValuesProvider =
						infoItemServiceTracker.getFirstInfoItemService(
							InfoItemFieldValuesProvider.class, className);

				InfoItemObjectProvider<Object> infoItemObjectProvider =
					infoItemServiceTracker.getFirstInfoItemService(
						InfoItemObjectProvider.class, className);

				if ((infoItemObjectProvider != null) &&
					(infoItemFieldValuesProvider != null)) {

					InfoItemIdentifier infoItemIdentifier =
						new ClassPKInfoItemIdentifier(classPK);

					Object infoItem = infoItemObjectProvider.getInfoItem(
						infoItemIdentifier);

					if (infoItem != null) {
						InfoFieldValue<Object> infoFieldValue =
							infoItemFieldValuesProvider.getInfoFieldValue(
								infoItem, fieldId);

						if (infoFieldValue != null) {
							Object object = infoFieldValue.getValue(
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
			return PortalUtil.getPathContext() + backgroundImageURL;
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

		FragmentEntryProcessorHelper fragmentEntryProcessorHelper =
			ServletContextUtil.getFragmentEntryProcessorHelper();

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)infoItemIdentifier;

		return fragmentEntryProcessorHelper.getFileEntryId(
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

		FrontendTokenDefinitionRegistry frontendTokenDefinitionRegistry =
			ServletContextUtil.getFrontendTokenDefinitionRegistry();

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.fetchLayoutSet(
			_themeDisplay.getSiteGroupId(), false);

		FrontendTokenDefinition frontendTokenDefinition =
			frontendTokenDefinitionRegistry.getFrontendTokenDefinition(
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

		InfoItemServiceTracker infoItemServiceTracker =
			ServletContextUtil.getInfoItemServiceTracker();

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			infoItemServiceTracker.getFirstInfoItemService(
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
			infoItemFieldValuesProvider.getInfoFieldValue(
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

	private final Map<String, Object> _fieldValues;
	private JSONObject _frontendTokensJSONObject;
	private final HttpServletRequest _httpServletRequest;
	private final LayoutStructure _layoutStructure;
	private final String _mainItemId;
	private final String _mode;
	private Long _previewClassNameId;
	private Long _previewClassPK;
	private Integer _previewType;
	private String _previewVersion;
	private long[] _segmentsExperienceIds;
	private final boolean _showPreview;
	private final ThemeDisplay _themeDisplay;

}