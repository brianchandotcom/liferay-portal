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
import com.liferay.segments.SegmentsEntryRetriever;
import com.liferay.segments.context.RequestContextMapper;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.util.DefaultStyleBookEntryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rubén Pulido
 */
public class RenderLayoutStructureDisplayContext {

	public RenderLayoutStructureDisplayContext(
		HttpServletRequest httpServletRequest, LayoutStructure layoutStructure,
		String mainItemId, String mode, boolean showPreview) {

		_httpServletRequest = httpServletRequest;
		_layoutStructure = layoutStructure;
		_mainItemId = mainItemId;
		_mode = mode;
		_showPreview = showPreview;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<String> getCollectionStyledLayoutStructureItemIds() {
		return _collectionStyledLayoutStructureItemIds;
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

		String align = styledLayoutStructureItem.getAlign();

		if (Validator.isNotNull(align)) {
			cssClassSB.append(" ");
			cssClassSB.append(align);
		}

		String backgroundColorCssClass =
			styledLayoutStructureItem.getBackgroundColorCssClass();

		if (Validator.isNotNull(backgroundColorCssClass)) {
			cssClassSB.append(" bg-");
			cssClassSB.append(backgroundColorCssClass);
		}

		String borderColorCssClass =
			styledLayoutStructureItem.getBorderColorCssClass();

		if (Validator.isNotNull(borderColorCssClass)) {
			cssClassSB.append(" border-");
			cssClassSB.append(borderColorCssClass);
		}

		String display = styledLayoutStructureItem.getDisplay();

		if (Objects.equals(display, "none")) {
			cssClassSB.append(" d-lg-");
			cssClassSB.append(display);
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
		else if (Validator.isNotNull(display)) {
			cssClassSB.append(" d-lg-");
			cssClassSB.append(display);
		}

		String justify = styledLayoutStructureItem.getJustify();

		if (Validator.isNotNull(justify)) {
			cssClassSB.append(" ");
			cssClassSB.append(justify);
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

		String marginBottom = styledLayoutStructureItem.getMarginBottom();

		if (Validator.isNotNull(marginBottom)) {
			cssClassSB.append(" mb-lg-");
			cssClassSB.append(marginBottom);
		}

		if (addHorizontalMargin) {
			String marginLeft = styledLayoutStructureItem.getMarginLeft();

			if (Validator.isNotNull(marginLeft)) {
				cssClassSB.append(" ml-lg-");
				cssClassSB.append(marginLeft);
			}

			String marginRight = styledLayoutStructureItem.getMarginRight();

			if (Validator.isNotNull(marginRight)) {
				cssClassSB.append(" mr-lg-");
				cssClassSB.append(marginRight);
			}
		}

		String marginTop = styledLayoutStructureItem.getMarginTop();

		if (Validator.isNotNull(marginTop)) {
			cssClassSB.append(" mt-lg-");
			cssClassSB.append(marginTop);
		}

		String paddingBottom = styledLayoutStructureItem.getPaddingBottom();

		if (Validator.isNotNull(paddingBottom)) {
			cssClassSB.append(" pb-lg-");
			cssClassSB.append(paddingBottom);
		}

		String paddingLeft = styledLayoutStructureItem.getPaddingLeft();

		if (Validator.isNotNull(paddingLeft)) {
			cssClassSB.append(" pl-lg-");
			cssClassSB.append(paddingLeft);
		}

		String paddingRight = styledLayoutStructureItem.getPaddingRight();

		if (Validator.isNotNull(paddingRight)) {
			cssClassSB.append(" pr-lg-");
			cssClassSB.append(paddingRight);
		}

		String paddingTop = styledLayoutStructureItem.getPaddingTop();

		if (Validator.isNotNull(paddingTop)) {
			cssClassSB.append(" pt-lg-");
			cssClassSB.append(paddingTop);
		}

		String textAlignCssClass =
			styledLayoutStructureItem.getTextAlignCssClass();

		if (Validator.isNotNull(textAlignCssClass) &&
			!Objects.equals(textAlignCssClass, "none")) {

			if (!StringUtil.startsWith(textAlignCssClass, "text-")) {
				cssClassSB.append(" text-lg-");
			}
			else {
				cssClassSB.append(StringPool.SPACE);
			}

			cssClassSB.append(textAlignCssClass);
		}

		String textColorCssClass =
			styledLayoutStructureItem.getTextColorCssClass();

		if (Validator.isNotNull(textColorCssClass)) {
			cssClassSB.append(" text-");
			cssClassSB.append(textColorCssClass);
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
		FragmentEntryLink fragmentEntryLink, String itemId,
		List<String> collectionStyledLayoutStructureItemIds,
		int collectionElementIndex) {

		DefaultFragmentRendererContext defaultFragmentRendererContext =
			new DefaultFragmentRendererContext(fragmentEntryLink);

		defaultFragmentRendererContext.setDisplayObject(
			_httpServletRequest.getAttribute(
				InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT));
		defaultFragmentRendererContext.setLocale(_themeDisplay.getLocale());

		Layout layout = _themeDisplay.getLayout();

		if (!Objects.equals(layout.getType(), LayoutConstants.TYPE_PORTLET)) {
			defaultFragmentRendererContext.setMode(_mode);
			defaultFragmentRendererContext.setPreviewClassNameId(
				_getPreviewClassNameId());
			defaultFragmentRendererContext.setPreviewClassPK(
				_getPreviewClassPK());
			defaultFragmentRendererContext.setPreviewType(_getPreviewType());
			defaultFragmentRendererContext.setPreviewVersion(
				_getPreviewVersion());
			defaultFragmentRendererContext.setSegmentsEntryIds(
				_getSegmentsEntryIds());
		}

		if (LayoutStructureItemUtil.hasAncestor(
				itemId, LayoutDataItemTypeConstants.TYPE_COLLECTION_ITEM,
				_layoutStructure)) {

			defaultFragmentRendererContext.setUseCachedContent(false);
		}

		defaultFragmentRendererContext.
			setCollectionStyledLayoutStructureItemIds(
				collectionStyledLayoutStructureItemIds);
		defaultFragmentRendererContext.setCollectionElementIndex(
			collectionElementIndex);

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

		String backgroundColor = styledLayoutStructureItem.getBackgroundColor();

		if (Validator.isNotNull(backgroundColor)) {
			styleSB.append("background-color: ");
			styleSB.append(getStyleFromStyleBookEntry(backgroundColor));
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

		String borderColor = styledLayoutStructureItem.getBorderColor();

		if (Validator.isNotNull(borderColor)) {
			styleSB.append("border-color: ");
			styleSB.append(getStyleFromStyleBookEntry(borderColor));
			styleSB.append(StringPool.SEMICOLON);
		}

		String borderRadius = styledLayoutStructureItem.getBorderRadius();

		if (Validator.isNotNull(borderRadius)) {
			styleSB.append("border-radius: ");
			styleSB.append(getStyleFromStyleBookEntry(borderRadius));
			styleSB.append(StringPool.SEMICOLON);
		}

		String borderWidth = styledLayoutStructureItem.getBorderWidth();

		if (Validator.isNotNull(borderWidth)) {
			styleSB.append("border-style: solid; border-width: ");
			styleSB.append(borderWidth);
			styleSB.append("px;");
		}

		String shadow = styledLayoutStructureItem.getShadow();

		if (Validator.isNotNull(shadow)) {
			styleSB.append("box-shadow: ");
			styleSB.append(getStyleFromStyleBookEntry(shadow));
			styleSB.append(StringPool.SEMICOLON);
		}

		String fontFamily = styledLayoutStructureItem.getFontFamily();

		if (Validator.isNotNull(fontFamily)) {
			styleSB.append("font-family: ");
			styleSB.append(getStyleFromStyleBookEntry(fontFamily));
			styleSB.append(StringPool.SEMICOLON);
		}

		String fontSize = styledLayoutStructureItem.getFontSize();

		if (Validator.isNotNull(fontSize)) {
			styleSB.append("font-size: ");
			styleSB.append(getStyleFromStyleBookEntry(fontSize));
			styleSB.append(StringPool.SEMICOLON);
		}

		String fontWeight = styledLayoutStructureItem.getFontWeight();

		if (Validator.isNotNull(fontWeight)) {
			styleSB.append("font-weight: ");
			styleSB.append(getStyleFromStyleBookEntry(fontWeight));
			styleSB.append(StringPool.SEMICOLON);
		}

		String height = styledLayoutStructureItem.getHeight();

		if (Validator.isNotNull(height)) {
			styleSB.append("height: ");
			styleSB.append(height);
			styleSB.append(StringPool.SEMICOLON);
		}

		String maxHeight = styledLayoutStructureItem.getMaxHeight();

		if (Validator.isNotNull(maxHeight)) {
			styleSB.append("max-height: ");
			styleSB.append(getStyleFromStyleBookEntry(maxHeight));
			styleSB.append(StringPool.SEMICOLON);
		}

		String maxWidth = styledLayoutStructureItem.getMaxWidth();

		if (Validator.isNotNull(maxWidth)) {
			styleSB.append("max-width: ");
			styleSB.append(getStyleFromStyleBookEntry(maxWidth));
			styleSB.append(StringPool.SEMICOLON);
		}

		String minHeight = styledLayoutStructureItem.getMinHeight();

		if (Validator.isNotNull(minHeight)) {
			styleSB.append("min-height: ");
			styleSB.append(getStyleFromStyleBookEntry(minHeight));
			styleSB.append(StringPool.SEMICOLON);
		}

		String minWidth = styledLayoutStructureItem.getMinWidth();

		if (Validator.isNotNull(minWidth)) {
			styleSB.append("min-width: ");
			styleSB.append(getStyleFromStyleBookEntry(minWidth));
			styleSB.append(StringPool.SEMICOLON);
		}

		String opacityValue = styledLayoutStructureItem.getOpacity();

		if (Validator.isNotNull(opacityValue)) {
			int opacity = GetterUtil.getInteger(opacityValue, 100);

			styleSB.append("opacity: ");
			styleSB.append(opacity / 100.0);
			styleSB.append(StringPool.SEMICOLON);
		}

		String overflow = styledLayoutStructureItem.getOverflow();

		if (Validator.isNotNull(overflow)) {
			styleSB.append("overflow: ");
			styleSB.append(getStyleFromStyleBookEntry(overflow));
			styleSB.append(StringPool.SEMICOLON);
		}

		String textColor = styledLayoutStructureItem.getTextColor();

		if (Validator.isNotNull(textColor)) {
			styleSB.append("color: ");
			styleSB.append(getStyleFromStyleBookEntry(textColor));
			styleSB.append(StringPool.SEMICOLON);
		}

		String width = styledLayoutStructureItem.getWidth();

		if (Validator.isNotNull(width)) {
			styleSB.append("width: ");
			styleSB.append(width);
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

	private long[] _getSegmentsEntryIds() {
		if (_segmentsEntryIds != null) {
			return _segmentsEntryIds;
		}

		SegmentsEntryRetriever segmentsEntryRetriever =
			ServletContextUtil.getSegmentsEntryRetriever();

		RequestContextMapper requestContextMapper =
			ServletContextUtil.getRequestContextMapper();

		_segmentsEntryIds = segmentsEntryRetriever.getSegmentsEntryIds(
			_themeDisplay.getScopeGroupId(), _themeDisplay.getUserId(),
			requestContextMapper.map(_httpServletRequest));

		return _segmentsEntryIds;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RenderLayoutStructureDisplayContext.class);

	private final List<String> _collectionStyledLayoutStructureItemIds =
		new ArrayList<>();
	private JSONObject _frontendTokensJSONObject;
	private final HttpServletRequest _httpServletRequest;
	private final LayoutStructure _layoutStructure;
	private final String _mainItemId;
	private final String _mode;
	private Long _previewClassNameId;
	private Long _previewClassPK;
	private Integer _previewType;
	private String _previewVersion;
	private long[] _segmentsEntryIds;
	private final boolean _showPreview;
	private final ThemeDisplay _themeDisplay;

}