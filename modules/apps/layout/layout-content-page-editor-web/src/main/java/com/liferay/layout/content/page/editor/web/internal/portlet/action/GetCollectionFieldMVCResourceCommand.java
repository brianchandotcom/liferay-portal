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

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.entry.processor.helper.FragmentEntryProcessorHelper;
import com.liferay.fragment.processor.DefaultFragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.info.collection.provider.item.selector.criterion.InfoCollectionProviderItemSelectorCriterion;
import com.liferay.info.collection.provider.item.selector.criterion.RelatedInfoItemCollectionProviderItemSelectorCriterion;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.info.list.renderer.DefaultInfoListRendererContext;
import com.liferay.info.list.renderer.InfoListRenderer;
import com.liferay.info.list.renderer.InfoListRendererRegistry;
import com.liferay.info.search.InfoSearchClassMapperRegistry;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.util.LayoutObjectReferenceUtil;
import com.liferay.layout.helper.CollectionPaginationHelper;
import com.liferay.layout.list.permission.provider.LayoutListPermissionProvider;
import com.liferay.layout.list.permission.provider.LayoutListPermissionProviderRegistry;
import com.liferay.layout.list.retriever.ClassedModelListObjectReference;
import com.liferay.layout.list.retriever.DefaultLayoutListRetrieverContext;
import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverRegistry;
import com.liferay.layout.list.retriever.ListObjectReference;
import com.liferay.layout.list.retriever.ListObjectReferenceFactory;
import com.liferay.layout.list.retriever.ListObjectReferenceFactoryRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.SegmentsEntryRetriever;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.context.RequestContextMapper;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.portlet.PortletURL;
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

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String languageId = ParamUtil.getString(
			resourceRequest, "languageId", themeDisplay.getLanguageId());

		int activePage = ParamUtil.getInteger(resourceRequest, "activePage");
		boolean displayAllItems = ParamUtil.getBoolean(
			resourceRequest, "displayAllItems");
		boolean displayAllPages = ParamUtil.getBoolean(
			resourceRequest, "displayAllPages");
		String layoutObjectReference = ParamUtil.getString(
			resourceRequest, "layoutObjectReference");
		String listStyle = ParamUtil.getString(resourceRequest, "listStyle");
		String listItemStyle = ParamUtil.getString(
			resourceRequest, "listItemStyle");
		int numberOfItems = ParamUtil.getInteger(
			resourceRequest, "numberOfItems");
		int numberOfItemsPerPage = ParamUtil.getInteger(
			resourceRequest, "numberOfItemsPerPage");
		int numberOfPages = ParamUtil.getInteger(
			resourceRequest, "numberOfPages");
		String paginationType = ParamUtil.getString(
			resourceRequest, "paginationType");
		long segmentsExperienceId = ParamUtil.getLong(
			resourceRequest, "segmentsExperienceId");
		String templateKey = ParamUtil.getString(
			resourceRequest, "templateKey");

		try {
			jsonObject = _getCollectionFieldsJSONObject(
				_portal.getHttpServletRequest(resourceRequest),
				_portal.getHttpServletResponse(resourceResponse), activePage,
				displayAllItems, displayAllPages, languageId,
				layoutObjectReference, listStyle, listItemStyle,
				resourceResponse.getNamespace(), numberOfItems,
				numberOfItemsPerPage, numberOfPages, paginationType,
				segmentsExperienceId, templateKey);
		}
		catch (Exception exception) {
			_log.error("Unable to get collection field", exception);

			jsonObject.put(
				"error",
				_language.get(
					themeDisplay.getRequest(), "an-unexpected-error-occurred"));
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonObject);
	}

	private AssetListEntry _getAssetListEntry(
		ListObjectReference listObjectReference) {

		// LPS-133832

		if (listObjectReference instanceof ClassedModelListObjectReference) {
			ClassedModelListObjectReference classedModelListObjectReference =
				(ClassedModelListObjectReference)listObjectReference;

			return _assetListEntryLocalService.fetchAssetListEntry(
				classedModelListObjectReference.getClassPK());
		}

		return null;
	}

	private JSONObject _getCollectionFieldsJSONObject(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, int activePage,
			boolean displayAllItems, boolean displayAllPages, String languageId,
			String layoutObjectReference, String listStyle,
			String listItemStyle, String namespace, int numberOfItems,
			int numberOfItemsPerPage, int numberOfPages, String paginationType,
			long segmentsExperienceId, String templateKey)
		throws PortalException {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		JSONObject layoutObjectReferenceJSONObject =
			_jsonFactory.createJSONObject(layoutObjectReference);

		String type = layoutObjectReferenceJSONObject.getString("type");

		LayoutListRetriever<?, ListObjectReference> layoutListRetriever =
			(LayoutListRetriever<?, ListObjectReference>)
				_layoutListRetrieverRegistry.getLayoutListRetriever(type);

		if (layoutListRetriever == null) {
			return jsonObject;
		}

		ListObjectReferenceFactory<?> listObjectReferenceFactory =
			_listObjectReferenceFactoryRegistry.getListObjectReference(type);

		if (listObjectReferenceFactory == null) {
			return jsonObject;
		}

		DefaultLayoutListRetrieverContext defaultLayoutListRetrieverContext =
			new DefaultLayoutListRetrieverContext();

		defaultLayoutListRetrieverContext.setConfiguration(
			LayoutObjectReferenceUtil.getConfiguration(
				layoutObjectReferenceJSONObject));

		Object infoItem = _getInfoItem(httpServletRequest);

		if (infoItem != null) {
			defaultLayoutListRetrieverContext.setContextObject(infoItem);
		}

		ListObjectReference listObjectReference =
			listObjectReferenceFactory.getListObjectReference(
				layoutObjectReferenceJSONObject);

		String originalItemType = null;

		AssetListEntry assetListEntry = _getAssetListEntry(listObjectReference);

		if (assetListEntry != null) {
			originalItemType = assetListEntry.getAssetEntryType();
		}
		else {
			originalItemType = listObjectReference.getItemType();
		}

		if (!_hasViewPermission(httpServletRequest, listObjectReference)) {
			jsonObject.put(
				"customCollectionSelectorURL", StringPool.BLANK
			).put(
				"isRestricted", true
			).put(
				"items", _jsonFactory.createJSONArray()
			).put(
				"itemSubtype",
				() -> {
					if (assetListEntry == null) {
						return null;
					}

					return assetListEntry.getAssetEntrySubtype();
				}
			).put(
				"itemType", originalItemType
			).put(
				"length", 0
			).put(
				"totalNumberOfItems", 0
			);

			return jsonObject;
		}

		int listCount = layoutListRetriever.getListCount(
			listObjectReference, defaultLayoutListRetrieverContext);

		if (activePage < 1) {
			activePage = 1;
		}

		defaultLayoutListRetrieverContext.setPagination(
			_collectionPaginationHelper.getPagination(
				activePage, listCount, displayAllPages, displayAllItems,
				numberOfItems, numberOfItemsPerPage, numberOfPages,
				paginationType));
		defaultLayoutListRetrieverContext.setSegmentsEntryIds(
			_getSegmentsEntryIds(segmentsExperienceId));

		String itemType = _infoSearchClassMapperRegistry.getClassName(
			originalItemType);

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			(InfoItemFieldValuesProvider<Object>)
				_infoItemServiceRegistry.getFirstInfoItemService(
					InfoItemFieldValuesProvider.class, itemType);

		if (infoItemFieldValuesProvider == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get info item form provider for class " +
						itemType);
			}

			return _jsonFactory.createJSONObject();
		}

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		List<Object> list = layoutListRetriever.getList(
			listObjectReference, defaultLayoutListRetrieverContext);

		for (Object object : list) {
			jsonArray.put(
				_getDisplayObjectJSONObject(
					httpServletRequest, httpServletResponse,
					infoItemFieldValuesProvider, object,
					LocaleUtil.fromLanguageId(languageId)));
		}

		InfoListRenderer<Object> infoListRenderer =
			(InfoListRenderer<Object>)
				_infoListRendererRegistry.getInfoListRenderer(listStyle);

		if (infoListRenderer != null) {
			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

			HttpServletResponse pipingHttpServletResponse =
				new PipingServletResponse(
					httpServletResponse, unsyncStringWriter);

			DefaultInfoListRendererContext defaultInfoListRendererContext =
				new DefaultInfoListRendererContext(
					httpServletRequest, pipingHttpServletResponse);

			defaultInfoListRendererContext.setListItemRendererKey(
				listItemStyle);
			defaultInfoListRendererContext.setTemplateKey(templateKey);

			infoListRenderer.render(list, defaultInfoListRendererContext);

			jsonObject.put("content", unsyncStringWriter.toString());
		}

		jsonObject.put(
			"customCollectionSelectorURL",
			_getCustomCollectionSelectorURL(
				httpServletRequest, itemType, namespace)
		).put(
			"isRestricted", false
		).put(
			"items", jsonArray
		).put(
			"itemSubtype",
			() -> {
				if (assetListEntry == null) {
					return null;
				}

				return assetListEntry.getAssetEntrySubtype();
			}
		).put(
			"itemType", originalItemType
		).put(
			"length",
			layoutListRetriever.getListCount(
				listObjectReference, defaultLayoutListRetrieverContext)
		).put(
			"totalNumberOfItems",
			_collectionPaginationHelper.getTotalNumberOfItems(
				listCount, displayAllPages, displayAllItems, numberOfItems,
				numberOfItemsPerPage, numberOfPages, paginationType)
		);

		return jsonObject;
	}

	private String _getCustomCollectionSelectorURL(
		HttpServletRequest httpServletRequest, String itemType,
		String namespace) {

		InfoCollectionProviderItemSelectorCriterion
			infoCollectionProviderItemSelectorCriterion =
				new InfoCollectionProviderItemSelectorCriterion();

		infoCollectionProviderItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				new InfoListItemSelectorReturnType(),
				new InfoListProviderItemSelectorReturnType());
		infoCollectionProviderItemSelectorCriterion.setType(
			InfoCollectionProviderItemSelectorCriterion.Type.
				SUPPORTED_INFO_FRAMEWORK_COLLECTIONS);

		RelatedInfoItemCollectionProviderItemSelectorCriterion
			relatedInfoItemCollectionProviderItemSelectorCriterion =
				new RelatedInfoItemCollectionProviderItemSelectorCriterion();

		relatedInfoItemCollectionProviderItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				new InfoListProviderItemSelectorReturnType());

		List<String> sourceItemTypes = new ArrayList<>();

		sourceItemTypes.add(itemType);

		String className = _infoSearchClassMapperRegistry.getSearchClassName(
			itemType);

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if (assetRendererFactory != null) {
			sourceItemTypes.add(AssetEntry.class.getName());
		}

		relatedInfoItemCollectionProviderItemSelectorCriterion.
			setSourceItemTypes(sourceItemTypes);

		PortletURL infoListSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			namespace + "selectInfoList",
			infoCollectionProviderItemSelectorCriterion,
			relatedInfoItemCollectionProviderItemSelectorCriterion);

		if (infoListSelectorURL == null) {
			return StringPool.BLANK;
		}

		return infoListSelectorURL.toString();
	}

	private JSONObject _getDisplayObjectJSONObject(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider,
		Object object, Locale locale) {

		InfoItemFieldValues infoItemFieldValues =
			infoItemFieldValuesProvider.getInfoItemFieldValues(object);

		InfoItemReference infoItemReference =
			infoItemFieldValues.getInfoItemReference();

		if (infoItemReference == null) {
			return _jsonFactory.createJSONObject();
		}

		JSONObject displayObjectJSONObject = JSONUtil.put(
			"className", infoItemReference.getClassName()
		).put(
			"classNameId",
			_portal.getClassNameId(infoItemReference.getClassName())
		).put(
			"classPK", infoItemReference.getClassPK()
		);

		FragmentEntryProcessorContext fragmentEntryProcessorContext =
			new DefaultFragmentEntryProcessorContext(
				httpServletRequest, httpServletResponse,
				FragmentEntryLinkConstants.EDIT, locale);

		for (InfoFieldValue<Object> infoFieldValue :
				infoItemFieldValues.getInfoFieldValues()) {

			InfoField<?> infoField = infoFieldValue.getInfoField();

			displayObjectJSONObject.put("fieldId", infoField.getUniqueId());

			try {
				Object value = _fragmentEntryProcessorHelper.getFieldValue(
					displayObjectJSONObject, new HashMap<>(),
					fragmentEntryProcessorContext);

				displayObjectJSONObject.put(
					infoField.getName(), value
				).put(
					infoField.getUniqueId(), value
				);
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException);
				}
			}
		}

		return displayObjectJSONObject;
	}

	private Object _getInfoItem(HttpServletRequest httpServletRequest) {
		long classNameId = ParamUtil.getLong(httpServletRequest, "classNameId");
		long classPK = ParamUtil.getLong(httpServletRequest, "classPK");

		if ((classNameId <= 0) && (classPK <= 0)) {
			return null;
		}

		InfoItemObjectProvider<Object> infoItemObjectProvider =
			(InfoItemObjectProvider<Object>)
				_infoItemServiceRegistry.getFirstInfoItemService(
					InfoItemObjectProvider.class,
					_portal.getClassName(classNameId));

		if (infoItemObjectProvider == null) {
			return null;
		}

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			new ClassPKInfoItemIdentifier(classPK);

		try {
			return infoItemObjectProvider.getInfoItem(
				classPKInfoItemIdentifier);
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchInfoItemException);
			}
		}

		return null;
	}

	private long[] _getSegmentsEntryIds(long segmentsExperienceId) {
		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				segmentsExperienceId);

		if (Objects.equals(
				SegmentsExperienceConstants.KEY_DEFAULT,
				segmentsExperience.getSegmentsExperienceKey())) {

			return new long[] {SegmentsEntryConstants.ID_DEFAULT};
		}

		return new long[] {
			segmentsExperience.getSegmentsEntryId(),
			SegmentsEntryConstants.ID_DEFAULT
		};
	}

	private boolean _hasViewPermission(
		HttpServletRequest httpServletRequest,
		ListObjectReference listObjectReference) {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-169923")) {
			return true;
		}

		Class<? extends ListObjectReference> listObjectReferenceClass =
			listObjectReference.getClass();

		LayoutListPermissionProvider<ListObjectReference>
			layoutListPermissionProvider =
				(LayoutListPermissionProvider<ListObjectReference>)
					_layoutListPermissionProviderRegistry.
						getLayoutListPermissionProvider(
							listObjectReferenceClass.getName());

		if (layoutListPermissionProvider == null) {
			return true;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return layoutListPermissionProvider.hasPermission(
			themeDisplay.getPermissionChecker(), listObjectReference,
			ActionKeys.VIEW);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetCollectionFieldMVCResourceCommand.class);

	@Reference
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Reference
	private CollectionPaginationHelper _collectionPaginationHelper;

	@Reference
	private FragmentEntryProcessorHelper _fragmentEntryProcessorHelper;

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private InfoListRendererRegistry _infoListRendererRegistry;

	@Reference
	private InfoSearchClassMapperRegistry _infoSearchClassMapperRegistry;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private LayoutListPermissionProviderRegistry
		_layoutListPermissionProviderRegistry;

	@Reference
	private LayoutListRetrieverRegistry _layoutListRetrieverRegistry;

	@Reference
	private ListObjectReferenceFactoryRegistry
		_listObjectReferenceFactoryRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private RequestContextMapper _requestContextMapper;

	@Reference
	private SegmentsEntryRetriever _segmentsEntryRetriever;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}