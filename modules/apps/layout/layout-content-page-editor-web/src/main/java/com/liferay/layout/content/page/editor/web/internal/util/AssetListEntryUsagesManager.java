/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.list.asset.entry.provider.AssetListAssetEntryProvider;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntryUsage;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.asset.list.service.AssetListEntryUsageLocalService;
import com.liferay.asset.util.AssetHelper;
import com.liferay.asset.util.AssetPublisherAddItemHolder;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.info.collection.provider.SingleFormVariationInfoCollectionProvider;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.info.search.InfoSearchClassMapperRegistry;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayRenderRequest;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletInstanceFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.RenderRequestFactory;
import com.liferay.portlet.RenderResponseFactory;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuPortletKeys;
import com.liferay.segments.SegmentsEntryRetriever;
import com.liferay.segments.context.RequestContextMapper;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import javax.portlet.PortletConfig;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.WindowState;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(service = AssetListEntryUsagesManager.class)
public class AssetListEntryUsagesManager {

	public JSONArray getPageContentsJSONArray(
			List<String> hiddenItemIds, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			LayoutStructure layoutStructure, long plid,
			List<String> restrictedItemIds)
		throws PortalException {

		JSONArray mappedContentsJSONArray = _jsonFactory.createJSONArray();

		Set<String> uniqueAssetListEntryUsagesKeys = new HashSet<>();

		for (AssetListEntryUsage assetListEntryUsage :
				_assetListEntryUsageLocalService.getAssetEntryListUsagesByPlid(
					plid)) {

			if (!_exists(assetListEntryUsage)) {
				continue;
			}

			String uniqueKey = _generateUniqueLayoutClassedModelUsageKey(
				assetListEntryUsage);

			if (uniqueAssetListEntryUsagesKeys.contains(uniqueKey) ||
				_isCollectionStyledLayoutStructureItemDeletedOrHidden(
					assetListEntryUsage, hiddenItemIds, layoutStructure) ||
				_isFragmentEntryLinkDeletedOrHidden(
					assetListEntryUsage, hiddenItemIds, layoutStructure)) {

				continue;
			}

			mappedContentsJSONArray.put(
				_getPageContentJSONObject(
					assetListEntryUsage, httpServletRequest,
					httpServletResponse, _getRedirect(httpServletRequest),
					restrictedItemIds));

			uniqueAssetListEntryUsagesKeys.add(uniqueKey);
		}

		return mappedContentsJSONArray;
	}

	@Activate
	protected void activate() {
		_collectionStyledLayoutStructureItemClassNameId =
			_portal.getClassNameId(
				CollectionStyledLayoutStructureItem.class.getName());
		_fragmentEntryLinkClassNameId = _portal.getClassNameId(
			FragmentEntryLink.class.getName());
	}

	private LiferayRenderRequest _createRenderRequest(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		Portlet portlet = _portletLocalService.getPortletById(
			ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET);
		ServletContext servletContext =
			(ServletContext)httpServletRequest.getAttribute(WebKeys.CTX);

		PortletConfig portletConfig = PortletConfigFactoryUtil.create(
			portlet, servletContext);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		LiferayRenderRequest liferayRenderRequest = RenderRequestFactory.create(
			httpServletRequest, portlet,
			PortletInstanceFactoryUtil.create(portlet, servletContext),
			portletConfig.getPortletContext(), WindowState.NORMAL,
			PortletMode.VIEW,
			_portletPreferencesLocalService.getStrictPreferences(
				PortletPreferencesFactoryUtil.getPortletPreferencesIds(
					httpServletRequest, portlet.getPortletId())),
			themeDisplay.getPlid());

		liferayRenderRequest.setPortletRequestDispatcherRequest(
			httpServletRequest);

		liferayRenderRequest.defineObjects(
			portletConfig,
			RenderResponseFactory.create(
				httpServletResponse, liferayRenderRequest));

		return liferayRenderRequest;
	}

	private boolean _exists(AssetListEntryUsage assetListEntryUsage) {
		if (Objects.equals(
				assetListEntryUsage.getClassName(),
				AssetListEntry.class.getName())) {

			AssetListEntry assetListEntry =
				_assetListEntryLocalService.fetchAssetListEntry(
					GetterUtil.getLong(assetListEntryUsage.getKey()));

			if (assetListEntry != null) {
				return true;
			}
		}

		if (Objects.equals(
				assetListEntryUsage.getClassName(),
				InfoCollectionProvider.class.getName())) {

			InfoCollectionProvider<?> infoCollectionProvider =
				_infoItemServiceRegistry.getInfoItemService(
					InfoCollectionProvider.class, assetListEntryUsage.getKey());

			if (infoCollectionProvider == null) {
				infoCollectionProvider =
					_infoItemServiceRegistry.getInfoItemService(
						RelatedInfoItemCollectionProvider.class,
						assetListEntryUsage.getKey());
			}

			if (infoCollectionProvider != null) {
				return true;
			}
		}

		return false;
	}

	private String _generateUniqueLayoutClassedModelUsageKey(
		AssetListEntryUsage assetListEntryUsage) {

		return assetListEntryUsage.getClassNameId() + StringPool.DASH +
			assetListEntryUsage.getKey();
	}

	private String _getAssetEntryListSubtypeLabel(
		AssetListEntry assetListEntry, Locale locale) {

		String typeLabel = _resourceActions.getModelResource(
			locale, assetListEntry.getAssetEntryType());

		String subtypeLabel = _getSubtypeLabel(assetListEntry, locale);

		if (Validator.isNull(subtypeLabel)) {
			return typeLabel;
		}

		return typeLabel + " - " + subtypeLabel;
	}

	private JSONObject _getAssetListEntryActionsJSONObject(
		AssetListEntry assetListEntry, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String redirect) {

		return JSONUtil.put(
			"addItems",
			() -> {
				try {
					JSONArray addItemsJSONArray =
						_getAssetListEntryAddItemsJSONArray(
							assetListEntry, httpServletRequest,
							httpServletResponse);

					if ((addItemsJSONArray != null) &&
						(addItemsJSONArray.length() > 0)) {

						return addItemsJSONArray;
					}
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(exception);
					}
				}

				return null;
			}
		).put(
			"editURL",
			() -> _getAssetListEntryEditURL(
				assetListEntry, httpServletRequest, redirect)
		).put(
			"permissionsURL",
			() -> _getAssetListEntryPermissionsURL(
				assetListEntry, httpServletRequest)
		).put(
			"viewItemsURL",
			() -> _getViewItemsURL(
				String.valueOf(assetListEntry.getAssetListEntryId()),
				InfoListItemSelectorReturnType.class.getName(),
				httpServletRequest, redirect)
		);
	}

	private JSONArray _getAssetListEntryAddItemsJSONArray(
			AssetListEntry assetListEntry,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		JSONArray addItemsJSONArray = _jsonFactory.createJSONArray();

		for (AssetPublisherAddItemHolder assetPublisherAddItemHolder :
				_getAssetPublisherAddItemHolders(
					assetListEntry, httpServletRequest, httpServletResponse)) {

			addItemsJSONArray.put(
				JSONUtil.put(
					"href", assetPublisherAddItemHolder.getPortletURL()
				).put(
					"label", assetPublisherAddItemHolder.getModelResource()
				));
		}

		return addItemsJSONArray;
	}

	private String _getAssetListEntryEditURL(
		AssetListEntry assetListEntry, HttpServletRequest httpServletRequest,
		String redirect) {

		try {
			return PortletURLBuilder.create(
				PortletProviderUtil.getPortletURL(
					httpServletRequest, AssetListEntry.class.getName(),
					PortletProvider.Action.EDIT)
			).setRedirect(
				redirect
			).setBackURL(
				redirect
			).setParameter(
				"assetListEntryId", assetListEntry.getAssetListEntryId()
			).buildString();
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return null;
	}

	private String _getAssetListEntryPermissionsURL(
		AssetListEntry assetListEntry, HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			if (_assetListEntryModelResourcePermission.contains(
					themeDisplay.getPermissionChecker(), assetListEntry,
					ActionKeys.PERMISSIONS)) {

				return PermissionsURLTag.doTag(
					StringPool.BLANK, AssetListEntry.class.getName(),
					HtmlUtil.escape(assetListEntry.getTitle()), null,
					String.valueOf(assetListEntry.getAssetListEntryId()),
					LiferayWindowState.POP_UP.toString(), null,
					httpServletRequest);
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return null;
	}

	private List<AssetPublisherAddItemHolder> _getAssetPublisherAddItemHolders(
			AssetListEntry assetListEntry,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		AssetEntryQuery assetEntryQuery =
			_assetListAssetEntryProvider.getAssetEntryQuery(
				assetListEntry,
				_segmentsEntryRetriever.getSegmentsEntryIds(
					_portal.getScopeGroupId(httpServletRequest),
					_portal.getUserId(httpServletRequest),
					_requestContextMapper.map(httpServletRequest)),
				StringPool.BLANK);

		long[] allTagIds = assetEntryQuery.getAllTagIds();

		String[] allTagNames = new String[allTagIds.length];

		int index = 0;

		for (long tagId : allTagIds) {
			AssetTag assetTag = _assetTagLocalService.getAssetTag(tagId);

			allTagNames[index++] = assetTag.getName();
		}

		LiferayPortletRequest liferayPortletRequest = _createRenderRequest(
			httpServletRequest, httpServletResponse);

		PortletResponse portletResponse =
			(PortletResponse)liferayPortletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(portletResponse);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _assetHelper.getAssetPublisherAddItemHolders(
			liferayPortletRequest, liferayPortletResponse,
			assetListEntry.getGroupId(), assetEntryQuery.getClassNameIds(),
			assetEntryQuery.getClassTypeIds(),
			assetEntryQuery.getAllCategoryIds(), allTagNames,
			_getRedirect(
				assetListEntry.getAssetListEntryId(), httpServletRequest,
				themeDisplay));
	}

	private String _getInfoCollectionProviderSubtypeLabel(
		long groupId, InfoCollectionProvider<?> infoCollectionProvider,
		Locale locale) {

		String className = infoCollectionProvider.getCollectionItemClassName();

		if (Validator.isNull(className)) {
			return StringPool.BLANK;
		}

		if (!(infoCollectionProvider instanceof
				SingleFormVariationInfoCollectionProvider)) {

			return _resourceActions.getModelResource(locale, className);
		}

		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class, className);

		if (infoItemFormVariationsProvider == null) {
			return _resourceActions.getModelResource(locale, className);
		}

		SingleFormVariationInfoCollectionProvider<?>
			singleFormVariationInfoCollectionProvider =
				(SingleFormVariationInfoCollectionProvider<?>)
					infoCollectionProvider;

		InfoItemFormVariation infoItemFormVariation =
			infoItemFormVariationsProvider.getInfoItemFormVariation(
				groupId,
				singleFormVariationInfoCollectionProvider.
					getFormVariationKey());

		if (infoItemFormVariation == null) {
			return _resourceActions.getModelResource(locale, className);
		}

		return _resourceActions.getModelResource(locale, className) + " - " +
			infoItemFormVariation.getLabel(locale);
	}

	private JSONObject _getPageContentJSONObject(
		AssetListEntryUsage assetListEntryUsage,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String redirect,
		List<String> restrictedItemIds) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		JSONObject mappedContentJSONObject = JSONUtil.put(
			"className", assetListEntryUsage.getClassName()
		).put(
			"classNameId", assetListEntryUsage.getClassNameId()
		).put(
			"classPK", assetListEntryUsage.getKey()
		).put(
			"icon", "list-ul"
		).put(
			"isRestricted",
			() -> {
				if ((assetListEntryUsage.getContainerType() ==
						_collectionStyledLayoutStructureItemClassNameId) &&
					restrictedItemIds.contains(
						assetListEntryUsage.getContainerKey())) {

					return true;
				}

				return false;
			}
		).put(
			"type", _language.get(httpServletRequest, "collection")
		);

		if (Objects.equals(
				assetListEntryUsage.getClassName(),
				AssetListEntry.class.getName())) {

			AssetListEntry assetListEntry =
				_assetListEntryLocalService.fetchAssetListEntry(
					GetterUtil.getLong(assetListEntryUsage.getKey()));

			if (assetListEntry != null) {
				mappedContentJSONObject.put(
					"actions",
					_getAssetListEntryActionsJSONObject(
						assetListEntry, httpServletRequest, httpServletResponse,
						redirect)
				).put(
					"subtype",
					_getAssetEntryListSubtypeLabel(
						assetListEntry, themeDisplay.getLocale())
				).put(
					"title", assetListEntry.getTitle()
				);
			}
		}

		if (!Objects.equals(
				assetListEntryUsage.getClassName(),
				InfoCollectionProvider.class.getName())) {

			return mappedContentJSONObject;
		}

		InfoCollectionProvider<?> infoCollectionProvider =
			_infoItemServiceRegistry.getInfoItemService(
				InfoCollectionProvider.class, assetListEntryUsage.getKey());

		if (infoCollectionProvider == null) {
			infoCollectionProvider =
				_infoItemServiceRegistry.getInfoItemService(
					RelatedInfoItemCollectionProvider.class,
					assetListEntryUsage.getKey());
		}

		if (infoCollectionProvider == null) {
			return mappedContentJSONObject;
		}

		InfoCollectionProvider<?> finalInfoCollectionProvider =
			infoCollectionProvider;

		return mappedContentJSONObject.put(
			"actions",
			() -> {
				if (finalInfoCollectionProvider instanceof
						RelatedInfoItemCollectionProvider) {

					return null;
				}

				return JSONUtil.put(
					"viewItemsURL",
					() -> _getViewItemsURL(
						finalInfoCollectionProvider.getKey(),
						InfoListProviderItemSelectorReturnType.class.getName(),
						httpServletRequest, redirect));
			}
		).put(
			"subtype",
			_getInfoCollectionProviderSubtypeLabel(
				themeDisplay.getScopeGroupId(), infoCollectionProvider,
				themeDisplay.getLocale())
		).put(
			"title", infoCollectionProvider.getLabel(themeDisplay.getLocale())
		);
	}

	private String _getRedirect(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		try {
			return HttpComponentsUtil.setParameter(
				_portal.getLayoutRelativeURL(layout, themeDisplay), "p_l_mode",
				Constants.EDIT);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return themeDisplay.getURLCurrent();
	}

	private String _getRedirect(
			long assetListEntryId, HttpServletRequest httpServletRequest,
			ThemeDisplay themeDisplay)
		throws Exception {

		String currentURL = HttpComponentsUtil.addParameter(
			_portal.getLayoutRelativeURL(
				themeDisplay.getLayout(), themeDisplay),
			"p_l_mode", Constants.EDIT);

		return HttpComponentsUtil.addParameter(
			PortletURLBuilder.create(
				_portal.getControlPanelPortletURL(
					httpServletRequest,
					ProductNavigationControlMenuPortletKeys.
						PRODUCT_NAVIGATION_CONTROL_MENU,
					PortletRequest.ACTION_PHASE)
			).setActionName(
				"/control_menu/add_collection_item"
			).setRedirect(
				currentURL
			).setParameter(
				"assetListEntryId", assetListEntryId
			).buildString(),
			"portletResource",
			ProductNavigationControlMenuPortletKeys.
				PRODUCT_NAVIGATION_CONTROL_MENU);
	}

	private String _getSubtypeLabel(
		AssetListEntry assetListEntry, Locale locale) {

		long classTypeId = GetterUtil.getLong(
			assetListEntry.getAssetEntrySubtype(), -1);

		if (classTypeId < 0) {
			return StringPool.BLANK;
		}

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				_infoSearchClassMapperRegistry.getSearchClassName(
					assetListEntry.getAssetEntryType()));

		if ((assetRendererFactory == null) ||
			!assetRendererFactory.isSupportsClassTypes()) {

			return StringPool.BLANK;
		}

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		try {
			ClassType classType = classTypeReader.getClassType(
				classTypeId, locale);

			return classType.getName();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return StringPool.BLANK;
		}
	}

	private String _getViewItemsURL(
		String collectionPK, String collectionType,
		HttpServletRequest httpServletRequest, String redirect) {

		try {
			return PortletURLBuilder.create(
				PortletProviderUtil.getPortletURL(
					httpServletRequest, AssetListEntry.class.getName(),
					PortletProvider.Action.VIEW)
			).setRedirect(
				redirect
			).setParameter(
				"collectionPK", collectionPK
			).setParameter(
				"collectionType", collectionType
			).setParameter(
				"showActions", true
			).setWindowState(
				LiferayWindowState.POP_UP
			).buildString();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return null;
	}

	private boolean _isCollectionStyledLayoutStructureItemDeletedOrHidden(
		AssetListEntryUsage assetListEntryUsage, List<String> hiddenItemIds,
		LayoutStructure layoutStructure) {

		if (assetListEntryUsage.getContainerType() !=
				_collectionStyledLayoutStructureItemClassNameId) {

			return false;
		}

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItem(
				assetListEntryUsage.getContainerKey());

		if (layoutStructureItem == null) {
			_assetListEntryUsageLocalService.deleteAssetListEntryUsage(
				assetListEntryUsage);

			return true;
		}

		if (layoutStructure.isItemMarkedForDeletion(
				layoutStructureItem.getItemId()) ||
			hiddenItemIds.contains(layoutStructureItem.getItemId())) {

			return true;
		}

		return false;
	}

	private boolean _isFragmentEntryLinkDeletedOrHidden(
		AssetListEntryUsage assetListEntryUsage, List<String> hiddenItemIds,
		LayoutStructure layoutStructure) {

		if (assetListEntryUsage.getContainerType() !=
				_fragmentEntryLinkClassNameId) {

			return false;
		}

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				GetterUtil.getLong(assetListEntryUsage.getContainerKey()));

		if (fragmentEntryLink == null) {
			_assetListEntryUsageLocalService.deleteAssetListEntryUsage(
				assetListEntryUsage);

			return true;
		}

		if (fragmentEntryLink.isDeleted()) {
			return true;
		}

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItemByFragmentEntryLinkId(
				fragmentEntryLink.getFragmentEntryLinkId());

		if ((layoutStructureItem == null) ||
			layoutStructure.isItemMarkedForDeletion(
				layoutStructureItem.getItemId()) ||
			hiddenItemIds.contains(layoutStructureItem.getItemId())) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetListEntryUsagesManager.class);

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private AssetListAssetEntryProvider _assetListAssetEntryProvider;

	@Reference
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.asset.list.model.AssetListEntry)"
	)
	private ModelResourcePermission<AssetListEntry>
		_assetListEntryModelResourcePermission;

	@Reference
	private AssetListEntryUsageLocalService _assetListEntryUsageLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	private long _collectionStyledLayoutStructureItemClassNameId;
	private long _fragmentEntryLinkClassNameId;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private InfoSearchClassMapperRegistry _infoSearchClassMapperRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private RequestContextMapper _requestContextMapper;

	@Reference
	private ResourceActions _resourceActions;

	@Reference
	private SegmentsEntryRetriever _segmentsEntryRetriever;

}