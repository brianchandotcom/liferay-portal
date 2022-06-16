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
import com.liferay.frontend.token.definition.FrontendToken;
import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.frontend.token.definition.FrontendTokenMapping;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.info.type.WebImage;
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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.SegmentsEntryRetriever;
import com.liferay.segments.context.RequestContextMapper;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.util.DefaultStyleBookEntryUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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

		Theme theme = _themeDisplay.getTheme();

		String colorPalette = theme.getSetting("color-palette");

		_themeColorsCssClasses = SetUtil.fromArray(
			StringUtil.split(colorPalette));
	}

	public String getAddInfoItemActionURL() {
		StringBundler sb = new StringBundler(3);

		sb.append(PortalUtil.getPortalURL(_httpServletRequest));
		sb.append(PortalUtil.getPathContext());
		sb.append("/c/portal/add_info_item");

		return sb.toString();
	}

	public List<String> getCollectionStyledLayoutStructureItemIds() {
		List<String> collectionStyledLayoutStructureItemIds =
			(List<String>)_httpServletRequest.getAttribute(
				_COLLECTION_STYLED_LAYOUT_STRUCTURE_ITEM_IDS);

		if (collectionStyledLayoutStructureItemIds == null) {
			collectionStyledLayoutStructureItemIds = new ArrayList<>();

			_httpServletRequest.setAttribute(
				_COLLECTION_STYLED_LAYOUT_STRUCTURE_ITEM_IDS,
				collectionStyledLayoutStructureItemIds);
		}

		return collectionStyledLayoutStructureItemIds;
	}

	public String getColorCssClasses(
		StyledLayoutStructureItem styledLayoutStructureItem) {

		StringBundler cssClassSB = new StringBundler(4);

		String backgroundColorCssClass =
			styledLayoutStructureItem.getBackgroundColorCssClass();

		if (_themeColorsCssClasses.contains(backgroundColorCssClass)) {
			cssClassSB.append("bg-");
			cssClassSB.append(backgroundColorCssClass);
		}

		String textColorCssClass =
			styledLayoutStructureItem.getTextColorCssClass();

		if (_themeColorsCssClasses.contains(textColorCssClass)) {
			cssClassSB.append(" text-");
			cssClassSB.append(textColorCssClass);
		}

		return cssClassSB.toString();
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

	public DefaultFragmentRendererContext getDefaultFragmentRendererContext(
		int collectionElementIndex, FragmentEntryLink fragmentEntryLink,
		InfoForm infoForm, String itemId) {

		DefaultFragmentRendererContext defaultFragmentRendererContext =
			new DefaultFragmentRendererContext(fragmentEntryLink);

		defaultFragmentRendererContext.setDisplayObject(
			_httpServletRequest.getAttribute(
				InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT));
		defaultFragmentRendererContext.setLocale(_themeDisplay.getLocale());

		Layout layout = _themeDisplay.getLayout();

		if (!Objects.equals(layout.getType(), LayoutConstants.TYPE_PORTLET)) {
			defaultFragmentRendererContext.setInfoForm(infoForm);
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
				getCollectionStyledLayoutStructureItemIds());
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

		StringBundler styleSB = new StringBundler(8);

		JSONObject backgroundImageJSONObject =
			styledLayoutStructureItem.getBackgroundImageJSONObject();

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

		String backgroundImageURL = _getBackgroundImage(
			backgroundImageJSONObject);

		if (Validator.isNotNull(backgroundImageURL)) {
			styleSB.append("--lfr-background-image-");
			styleSB.append(styledLayoutStructureItem.getItemId());
			styleSB.append(": url(");
			styleSB.append(backgroundImageURL);
			styleSB.append(");");
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

		FrontendTokenDefinitionRegistry frontendTokenDefinitionRegistry =
			ServletContextUtil.getFrontendTokenDefinitionRegistry();

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.fetchLayoutSet(
			_themeDisplay.getSiteGroupId(), false);

		FrontendTokenDefinition frontendTokenDefinition =
			frontendTokenDefinitionRegistry.getFrontendTokenDefinition(
				layoutSet.getThemeId());

		if (frontendTokenDefinition == null) {
			return _frontendTokensJSONObject;
		}

		StyleBookEntry styleBookEntry = null;

		if (!ParamUtil.getBoolean(
				_httpServletRequest, "styleBookEntryPreview")) {

			styleBookEntry = DefaultStyleBookEntryUtil.getDefaultStyleBookEntry(
				_themeDisplay.getLayout());
		}

		JSONObject frontendTokenValuesJSONObject = null;

		if (styleBookEntry != null) {
			frontendTokenValuesJSONObject = JSONFactoryUtil.createJSONObject(
				styleBookEntry.getFrontendTokensValues());
		}
		else {
			frontendTokenValuesJSONObject = JSONFactoryUtil.createJSONObject();
		}

		Collection<FrontendToken> frontendTokens =
			frontendTokenDefinition.getFrontendTokens();

		for (FrontendToken frontendToken : frontendTokens) {
			List<FrontendTokenMapping> frontendTokenMappings = new ArrayList<>(
				frontendToken.getFrontendTokenMappings(
					FrontendTokenMapping.TYPE_CSS_VARIABLE));

			if (ListUtil.isEmpty(frontendTokenMappings)) {
				continue;
			}

			_frontendTokensJSONObject.put(
				frontendToken.getName(),
				JSONUtil.put(
					FrontendTokenMapping.TYPE_CSS_VARIABLE,
					() -> {
						FrontendTokenMapping frontendTokenMapping =
							frontendTokenMappings.get(0);

						return frontendTokenMapping.getValue();
					}
				).put(
					"value",
					Optional.ofNullable(
						frontendTokenValuesJSONObject.getJSONObject(
							frontendToken.getName())
					).map(
						valueJSONObject -> valueJSONObject.getString("value")
					).orElse(
						frontendToken.getDefaultValue()
					)
				));
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

	private static final String _COLLECTION_STYLED_LAYOUT_STRUCTURE_ITEM_IDS =
		"COLLECTION_STYLED_LAYOUT_STRUCTURE_ITEM_IDS";

	private static final Log _log = LogFactoryUtil.getLog(
		RenderLayoutStructureDisplayContext.class);

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
	private final Set<String> _themeColorsCssClasses;
	private final ThemeDisplay _themeDisplay;

}