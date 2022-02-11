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
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntryModel;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.fragment.entry.processor.helper.FragmentEntryProcessorHelper;
import com.liferay.info.collection.provider.item.selector.criterion.InfoCollectionProviderItemSelectorCriterion;
import com.liferay.info.collection.provider.item.selector.criterion.RelatedInfoItemCollectionProviderItemSelectorCriterion;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.info.list.renderer.DefaultInfoListRendererContext;
import com.liferay.info.list.renderer.InfoListRenderer;
import com.liferay.info.list.renderer.InfoListRendererTracker;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.type.WebImage;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.item.selector.criteria.info.item.criterion.InfoListItemSelectorCriterion;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.configuration.FFLayoutContentPageEditorConfiguration;
import com.liferay.layout.content.page.editor.web.internal.util.LayoutObjectReferenceUtil;
import com.liferay.layout.list.retriever.ClassedModelListObjectReference;
import com.liferay.layout.list.retriever.DefaultLayoutListRetrieverContext;
import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverTracker;
import com.liferay.layout.list.retriever.ListObjectReference;
import com.liferay.layout.list.retriever.ListObjectReferenceFactory;
import com.liferay.layout.list.retriever.ListObjectReferenceFactoryTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.segments.SegmentsEntryRetriever;
import com.liferay.segments.context.RequestContextMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.portlet.PortletURL;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	configurationPid = "com.liferay.layout.content.page.editor.web.internal.configuration.FFLayoutContentPageEditorConfiguration",
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/get_collection_field"
	},
	service = MVCResourceCommand.class
)
public class GetCollectionFieldMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ffLayoutContentPageEditorConfiguration =
			ConfigurableUtil.createConfigurable(
				FFLayoutContentPageEditorConfiguration.class, properties);
	}

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

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

		if ((numberOfItemsPerPage <= 0) ||
			(numberOfItemsPerPage >
				PropsValues.SEARCH_CONTAINER_PAGE_MAX_DELTA)) {

			numberOfItemsPerPage = PropsValues.SEARCH_CONTAINER_PAGE_MAX_DELTA;
		}

		int numberOfPages = ParamUtil.getInteger(
			resourceRequest, "numberOfPages");

		String paginationType = ParamUtil.getString(
			resourceRequest, "paginationType");

		boolean paginationEnabled = _isPaginationEnabled(paginationType);

		boolean showAllItems = ParamUtil.getBoolean(
			resourceRequest, "showAllItems");
		String templateKey = ParamUtil.getString(
			resourceRequest, "templateKey");

		try {
			jsonObject = _getCollectionFieldsJSONObject(
				_portal.getHttpServletRequest(resourceRequest),
				_portal.getHttpServletResponse(resourceResponse), activePage,
				displayAllItems, displayAllPages, languageId,
				layoutObjectReference, listStyle, listItemStyle,
				resourceResponse.getNamespace(), numberOfItems,
				numberOfItemsPerPage, numberOfPages, paginationEnabled,
				showAllItems, templateKey);
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

	private Optional<AssetListEntry> _getAssetListEntryOptional(
		ListObjectReference listObjectReference) {

		// LPS-133832

		if (listObjectReference instanceof ClassedModelListObjectReference) {
			ClassedModelListObjectReference classedModelListObjectReference =
				(ClassedModelListObjectReference)listObjectReference;

			AssetListEntry assetListEntry =
				_assetListEntryLocalService.fetchAssetListEntry(
					classedModelListObjectReference.getClassPK());

			if (assetListEntry == null) {
				return Optional.empty();
			}

			return Optional.of(assetListEntry);
		}

		return Optional.empty();
	}

	private JSONObject _getCollectionFieldsJSONObject(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, int activePage,
			boolean displayAllItems, boolean displayAllPages, String languageId,
			String layoutObjectReference, String listStyle,
			String listItemStyle, String namespace, int numberOfItems,
			int numberOfItemsPerPage, int numberOfPages,
			boolean paginationEnabled, boolean showAllItems, String templateKey)
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

				defaultLayoutListRetrieverContext.setConfiguration(
					LayoutObjectReferenceUtil.getConfiguration(
						layoutObjectReferenceJSONObject));

				Object infoItem = _getInfoItem(httpServletRequest);

				if (infoItem != null) {
					defaultLayoutListRetrieverContext.setContextObject(
						infoItem);
				}

				ListObjectReference listObjectReference =
					listObjectReferenceFactory.getListObjectReference(
						layoutObjectReferenceJSONObject);

				int listCount = layoutListRetriever.getListCount(
					listObjectReference, defaultLayoutListRetrieverContext);

				if (activePage < 1) {
					activePage = 1;
				}

				Pagination pagination = _getPagination(
					activePage, listCount, displayAllPages, displayAllItems,
					numberOfItems, numberOfItemsPerPage, numberOfPages,
					paginationEnabled, showAllItems);

				defaultLayoutListRetrieverContext.setPagination(pagination);

				long[] segmentsEntryIds =
					_segmentsEntryRetriever.getSegmentsEntryIds(
						_portal.getScopeGroupId(httpServletRequest),
						_portal.getUserId(httpServletRequest),
						_requestContextMapper.map(httpServletRequest));

				defaultLayoutListRetrieverContext.setSegmentsEntryIds(
					segmentsEntryIds);

				// LPS-111037

				Optional<AssetListEntry> assetListEntryOptional =
					_getAssetListEntryOptional(listObjectReference);

				String itemType = assetListEntryOptional.map(
					AssetListEntryModel::getAssetEntryType
				).orElse(
					listObjectReference.getItemType()
				);

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
							"Unable to get info item form provider for class " +
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
					"customCollectionSelectorURL",
					_getCustomCollectionSelectorURL(
						httpServletRequest, itemType, namespace)
				).put(
					"items", jsonArray
				).put(
					"itemSubtype",
					assetListEntryOptional.map(
						AssetListEntry::getAssetEntrySubtype
					).orElse(
						null
					)
				).put(
					"itemType", itemType
				).put(
					"length",
					layoutListRetriever.getListCount(
						listObjectReference, defaultLayoutListRetrieverContext)
				).put(
					"totalNumberOfItems",
					_getTotalNumberOfItems(
						listCount, displayAllPages, displayAllItems,
						numberOfItems, numberOfItemsPerPage, numberOfPages,
						paginationEnabled)
				);
			}
		}

		return jsonObject;
	}

	private String _getCustomCollectionSelectorURL(
		HttpServletRequest httpServletRequest, String itemType,
		String namespace) {

		InfoListItemSelectorCriterion infoListItemSelectorCriterion =
			new InfoListItemSelectorCriterion();

		infoListItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new InfoListItemSelectorReturnType());
		infoListItemSelectorCriterion.setItemTypes(
			_getInfoItemFormProviderClassNames());

		InfoCollectionProviderItemSelectorCriterion
			infoCollectionProviderItemSelectorCriterion =
				new InfoCollectionProviderItemSelectorCriterion();

		infoCollectionProviderItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				new InfoListProviderItemSelectorReturnType());
		infoCollectionProviderItemSelectorCriterion.setItemTypes(
			_getInfoItemFormProviderClassNames());

		RelatedInfoItemCollectionProviderItemSelectorCriterion
			relatedInfoItemCollectionProviderItemSelectorCriterion =
				new RelatedInfoItemCollectionProviderItemSelectorCriterion();

		relatedInfoItemCollectionProviderItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				new InfoListProviderItemSelectorReturnType());

		List<String> sourceItemTypes = new ArrayList<>();

		sourceItemTypes.add(itemType);

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				itemType);

		if (assetRendererFactory != null) {
			sourceItemTypes.add(AssetEntry.class.getName());
		}

		relatedInfoItemCollectionProviderItemSelectorCriterion.
			setSourceItemTypes(sourceItemTypes);

		PortletURL infoListSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			namespace + "selectInfoList", infoListItemSelectorCriterion,
			infoCollectionProviderItemSelectorCriterion,
			relatedInfoItemCollectionProviderItemSelectorCriterion);

		if (infoListSelectorURL == null) {
			return StringPool.BLANK;
		}

		return infoListSelectorURL.toString();
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

				long fileEntryId = _getFileEntryId(webImage);

				if (fileEntryId != 0) {
					JSONObject valueJSONObject = (JSONObject)value;

					valueJSONObject.put(
						"fileEntryId", String.valueOf(fileEntryId));
				}
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
				"classNameId",
				_portal.getClassNameId(infoItemReference.getClassName())
			).put(
				"classPK", infoItemReference.getClassPK()
			);
		}

		return displayObjectJSONObject;
	}

	private long _getFileEntryId(WebImage webImage) {
		InfoItemReference infoItemReference = webImage.getInfoItemReference();

		if ((infoItemReference == null) ||
			!Objects.equals(
				infoItemReference.getClassName(), FileEntry.class.getName())) {

			return 0;
		}

		InfoItemIdentifier fileEntryInfoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		if (!(fileEntryInfoItemIdentifier instanceof
				ClassPKInfoItemIdentifier)) {

			return 0;
		}

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)fileEntryInfoItemIdentifier;

		return classPKInfoItemIdentifier.getClassPK();
	}

	private Object _getInfoItem(HttpServletRequest httpServletRequest) {
		long classNameId = ParamUtil.getLong(httpServletRequest, "classNameId");
		long classPK = ParamUtil.getLong(httpServletRequest, "classPK");

		if ((classNameId <= 0) && (classPK <= 0)) {
			return null;
		}

		InfoItemObjectProvider<Object> infoItemObjectProvider =
			(InfoItemObjectProvider<Object>)
				_infoItemServiceTracker.getFirstInfoItemService(
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

	private List<String> _getInfoItemFormProviderClassNames() {
		List<String> infoItemClassNames =
			_infoItemServiceTracker.getInfoItemClassNames(
				InfoItemFormProvider.class);

		if (infoItemClassNames.contains(FileEntry.class.getName())) {
			infoItemClassNames.add(DLFileEntryConstants.getClassName());
			infoItemClassNames.remove(FileEntry.class.getName());
		}

		return infoItemClassNames;
	}

	private Pagination _getPagination(
		int activePage, int count, boolean displayAllPages,
		boolean displayAllItems, int numberOfItems, int numberOfItemsPerPage,
		int numberOfPages, boolean paginationEnabled, boolean showAllItems) {

		int end = numberOfItems;
		int start = 0;

		if (_ffLayoutContentPageEditorConfiguration.
				paginationImprovementsEnabled()) {

			if (paginationEnabled) {
				int maxNumberOfItems = count;

				if (!displayAllPages && (numberOfPages > 0)) {
					maxNumberOfItems = numberOfPages * numberOfItemsPerPage;
				}

				end = Math.min(
					Math.min(
						activePage * numberOfItemsPerPage, maxNumberOfItems),
					count);

				start = (activePage - 1) * numberOfItemsPerPage;
			}
			else if (displayAllItems) {
				end = count;
			}
		}
		else {
			if (paginationEnabled) {
				int maxNumberOfItems = numberOfItems;

				if (showAllItems) {
					maxNumberOfItems = count;
				}

				end = Math.min(
					Math.min(
						activePage * numberOfItemsPerPage, maxNumberOfItems),
					count);

				start = (activePage - 1) * numberOfItemsPerPage;
			}
		}

		return Pagination.of(end, start);
	}

	private int _getTotalNumberOfItems(
		int count, boolean displayAllPages, boolean displayAllItems,
		int numberOfItems, int numberOfItemsPerPage, int numberOfPages,
		boolean paginationEnabled) {

		if (_ffLayoutContentPageEditorConfiguration.
				paginationImprovementsEnabled()) {

			if (!paginationEnabled) {
				if (displayAllItems) {
					return count;
				}

				return Math.min(count, numberOfItems);
			}

			if (displayAllPages || (numberOfPages <= 0)) {
				return count;
			}

			return Math.min(count, numberOfPages * numberOfItemsPerPage);
		}

		return Math.min(count, numberOfItems);
	}

	private boolean _isPaginationEnabled(String paginationType) {
		if (Objects.equals(paginationType, "numeric") ||
			Objects.equals(paginationType, "simple")) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetCollectionFieldMVCResourceCommand.class);

	private static volatile FFLayoutContentPageEditorConfiguration
		_ffLayoutContentPageEditorConfiguration;

	@Reference
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Reference
	private FragmentEntryProcessorHelper _fragmentEntryProcessorHelper;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private InfoListRendererTracker _infoListRendererTracker;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private LayoutListRetrieverTracker _layoutListRetrieverTracker;

	@Reference
	private ListObjectReferenceFactoryTracker
		_listObjectReferenceFactoryTracker;

	@Reference
	private Portal _portal;

	@Reference
	private RequestContextMapper _requestContextMapper;

	@Reference
	private SegmentsEntryRetriever _segmentsEntryRetriever;

}