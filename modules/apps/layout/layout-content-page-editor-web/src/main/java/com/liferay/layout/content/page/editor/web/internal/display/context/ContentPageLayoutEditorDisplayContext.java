/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.display.context;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.collection.provider.SingleFormVariationInfoCollectionProvider;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.info.search.InfoSearchClassMapperRegistry;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.layout.content.page.editor.sidebar.panel.ContentPageEditorSidebarPanel;
import com.liferay.layout.content.page.editor.web.internal.configuration.PageEditorConfiguration;
import com.liferay.layout.content.page.editor.web.internal.constants.ContentPageEditorActionKeys;
import com.liferay.layout.content.page.editor.web.internal.segments.SegmentsExperienceUtil;
import com.liferay.layout.content.page.editor.web.internal.util.ContentManager;
import com.liferay.layout.content.page.editor.web.internal.util.FragmentCollectionManager;
import com.liferay.layout.content.page.editor.web.internal.util.FragmentEntryLinkManager;
import com.liferay.layout.manager.LayoutLockManager;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalService;
import com.liferay.learn.LearnMessage;
import com.liferay.learn.LearnMessageUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.configuration.provider.SegmentsConfigurationProvider;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.manager.SegmentsExperienceManager;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.service.SegmentsEntryService;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.service.SegmentsExperimentRelLocalService;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class ContentPageLayoutEditorDisplayContext
	extends ContentPageEditorDisplayContext {

	public ContentPageLayoutEditorDisplayContext(
		AssetListEntryLocalService assetListEntryLocalService,
		List<ContentPageEditorSidebarPanel> contentPageEditorSidebarPanels,
		ContentManager contentManager,
		FragmentCollectionManager fragmentCollectionManager,
		FragmentEntryLinkManager fragmentEntryLinkManager,
		FragmentEntryLinkLocalService fragmentEntryLinkLocalService,
		FragmentEntryLocalService fragmentEntryLocalService,
		FrontendTokenDefinitionRegistry frontendTokenDefinitionRegistry,
		GroupLocalService groupLocalService,
		HttpServletRequest httpServletRequest,
		InfoItemServiceRegistry infoItemServiceRegistry,
		InfoSearchClassMapperRegistry infoSearchClassMapperRegistry,
		ItemSelector itemSelector, JSONFactory jsonFactory, Language language,
		LayoutLocalService layoutLocalService,
		LayoutLockManager layoutLockManager,
		LayoutSetLocalService layoutSetLocalService,
		LayoutPageTemplateEntryLocalService layoutPageTemplateEntryLocalService,
		LayoutPageTemplateEntryService layoutPageTemplateEntryService,
		LayoutPageTemplateStructureLocalService
			layoutPageTemplateStructureLocalService,
		LayoutPageTemplateStructureRelLocalService
			layoutPageTemplateStructureRelLocalService,
		LayoutPermission layoutPermission,
		PageEditorConfiguration pageEditorConfiguration, Portal portal,
		PortletRequest portletRequest, PortletURLFactory portletURLFactory,
		RenderResponse renderResponse,
		SegmentsConfigurationProvider segmentsConfigurationProvider,
		SegmentsExperienceManager segmentsExperienceManager,
		SegmentsExperienceLocalService segmentsExperienceLocalService,
		SegmentsExperimentRelLocalService segmentsExperimentRelLocalService,
		SegmentsEntryService segmentsEntryService, Staging staging,
		StagingGroupHelper stagingGroupHelper,
		StyleBookEntryLocalService styleBookEntryLocalService,
		UserLocalService userLocalService,
		WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService) {

		super(
			contentPageEditorSidebarPanels, contentManager,
			fragmentCollectionManager, fragmentEntryLinkManager,
			fragmentEntryLinkLocalService, fragmentEntryLocalService,
			frontendTokenDefinitionRegistry, httpServletRequest,
			infoItemServiceRegistry, infoSearchClassMapperRegistry,
			itemSelector, jsonFactory, language, layoutLocalService,
			layoutLockManager, layoutPageTemplateEntryLocalService,
			layoutPageTemplateEntryService, layoutPermission,
			layoutSetLocalService, pageEditorConfiguration, portal,
			portletRequest, portletURLFactory, renderResponse,
			segmentsConfigurationProvider, segmentsExperienceManager,
			segmentsExperienceLocalService, segmentsExperimentRelLocalService,
			staging, stagingGroupHelper, styleBookEntryLocalService,
			userLocalService, workflowDefinitionLinkLocalService);

		_assetListEntryLocalService = assetListEntryLocalService;
		_groupLocalService = groupLocalService;
		_layoutPageTemplateStructureLocalService =
			layoutPageTemplateStructureLocalService;
		_layoutPageTemplateStructureRelLocalService =
			layoutPageTemplateStructureRelLocalService;
		_segmentsExperimentRelLocalService = segmentsExperimentRelLocalService;
		_segmentsEntryService = segmentsEntryService;
	}

	@Override
	public Map<String, Object> getEditorContext(String npmResolvedPackageName)
		throws Exception {

		Map<String, Object> editorContext = super.getEditorContext(
			npmResolvedPackageName);

		if (!_isShowSegmentsExperiences()) {
			return editorContext;
		}

		Map<String, Object> configContext =
			(Map<String, Object>)editorContext.get("config");

		configContext.put(
			"addSegmentsExperienceURL",
			HttpComponentsUtil.addParameter(
				HttpComponentsUtil.addParameter(
					getFragmentEntryActionURL(
						"/layout_content_page_editor/add_segments_experience"),
					getPortletNamespace() + "plid", themeDisplay.getPlid()),
				getPortletNamespace() + "groupId",
				themeDisplay.getScopeGroupId()));
		configContext.put(
			"availableSegmentsEntries", _getAvailableSegmentsEntries());

		LearnMessage learnMessage = LearnMessageUtil.getLearnMessage(
			"content-page-personalization",
			language.getLanguageId(httpServletRequest),
			"layout-content-page-editor-web");

		configContext.put(
			"contentPagePersonalizationLearnURL", learnMessage.getURL());

		configContext.put(
			"defaultSegmentsEntryId", SegmentsEntryConstants.ID_DEFAULT);
		configContext.put(
			"deleteSegmentsExperienceURL",
			getFragmentEntryActionURL(
				"/layout_content_page_editor/delete_segments_experience"));
		configContext.put("editSegmentsEntryURL", _getEditSegmentsEntryURL());
		configContext.put("plid", themeDisplay.getPlid());

		Layout layout = themeDisplay.getLayout();

		if (Objects.equals(layout.getType(), LayoutConstants.TYPE_COLLECTION)) {
			configContext.put(
				"selectedMappingTypes", _getSelectedMappingTypes());
		}

		configContext.put(
			"selectedSegmentsEntryId", String.valueOf(_getSegmentsEntryId()));
		configContext.put(
			"singleSegmentsExperienceMode", _isSingleSegmentsExperienceMode());

		Map<String, Object> stateContext =
			(Map<String, Object>)editorContext.get("state");

		stateContext.put(
			"availableSegmentsExperiences",
			SegmentsExperienceUtil.getAvailableSegmentsExperiences(
				httpServletRequest));
		stateContext.put("layoutDataList", _getLayoutDataList());
		stateContext.put(
			"segmentsExperimentStatus",
			SegmentsExperienceUtil.getSegmentsExperimentStatus(
				themeDisplay, getSegmentsExperienceId()));

		Map<String, Object> permissionsContext =
			(Map<String, Object>)stateContext.get("permissions");

		permissionsContext.put(
			ContentPageEditorActionKeys.EDIT_SEGMENTS_ENTRY,
			_hasEditSegmentsEntryPermission());
		permissionsContext.put(
			ContentPageEditorActionKeys.LOCKED_SEGMENTS_EXPERIMENT,
			_isLockedSegmentsExperience(getSegmentsExperienceId()));

		return editorContext;
	}

	@Override
	protected long getSegmentsExperienceId() {
		if (_segmentsExperienceId != null) {
			return _segmentsExperienceId;
		}

		_segmentsExperienceId = ParamUtil.getLong(
			portal.getOriginalServletRequest(httpServletRequest),
			"segmentsExperienceId", -1);

		if (_segmentsExperienceId != -1) {
			SegmentsExperience segmentsExperience =
				segmentsExperienceLocalService.fetchSegmentsExperience(
					_segmentsExperienceId);

			if (segmentsExperience != null) {
				_segmentsExperienceId =
					segmentsExperience.getSegmentsExperienceId();
			}
			else {
				_segmentsExperienceId = super.getSegmentsExperienceId();
			}
		}
		else {
			_segmentsExperienceId = super.getSegmentsExperienceId();
		}

		return _segmentsExperienceId;
	}

	private AssetListEntry _getAssetListEntry(String collectionPK) {
		return _assetListEntryLocalService.fetchAssetListEntry(
			GetterUtil.getLong(collectionPK));
	}

	private String _getAssetListEntryItemTypeLabel(
		AssetListEntry assetListEntry) {

		if (Objects.equals(
				assetListEntry.getAssetEntryType(),
				AssetEntry.class.getName())) {

			return language.get(httpServletRequest, "multiple-item-types");
		}

		String assetEntryTypeLabel = ResourceActionsUtil.getModelResource(
			themeDisplay.getLocale(), assetListEntry.getAssetEntryType());

		long classTypeId = GetterUtil.getLong(
			assetListEntry.getAssetEntrySubtype(), -1);

		if (classTypeId >= 0) {
			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(
						assetListEntry.getAssetEntryType());

			if ((assetRendererFactory != null) &&
				assetRendererFactory.isSupportsClassTypes()) {

				ClassTypeReader classTypeReader =
					assetRendererFactory.getClassTypeReader();

				try {
					ClassType classType = classTypeReader.getClassType(
						classTypeId, themeDisplay.getLocale());

					assetEntryTypeLabel =
						assetEntryTypeLabel + " - " + classType.getName();
				}
				catch (PortalException portalException) {
					if (_log.isDebugEnabled()) {
						_log.debug(portalException);
					}
				}
			}
		}

		return assetEntryTypeLabel;
	}

	private String _getAssetListEntryItemTypeURL(AssetListEntry assetListEntry)
		throws Exception {

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			portletRequest, AssetListEntry.class.getName(),
			PortletProvider.Action.EDIT);

		if (portletURL == null) {
			return StringPool.BLANK;
		}

		portletURL.setParameter("redirect", themeDisplay.getURLCurrent());
		portletURL.setParameter("backURL", themeDisplay.getURLCurrent());
		portletURL.setParameter(
			"assetListEntryId",
			String.valueOf(assetListEntry.getAssetListEntryId()));

		return portletURL.toString();
	}

	private JSONArray _getAssetListEntryLinkedCollectionJSONArray(
		AssetListEntry assetListEntry) {

		return JSONUtil.put(
			JSONUtil.put(
				"classNameId",
				portal.getClassNameId(AssetListEntry.class.getName())
			).put(
				"classPK", String.valueOf(assetListEntry.getAssetListEntryId())
			).put(
				"itemSubtype", assetListEntry.getAssetEntrySubtype()
			).put(
				"itemType", assetListEntry.getAssetEntryType()
			).put(
				"title", assetListEntry.getTitle()
			).put(
				"type", InfoListItemSelectorReturnType.class.getName()
			));
	}

	private Map<String, Object> _getAvailableSegmentsEntries() {
		Map<String, Object> availableSegmentsEntries = new HashMap<>();

		List<SegmentsEntry> segmentsEntries =
			_segmentsEntryService.getSegmentsEntries(
				stagingGroupHelper.getStagedPortletGroupId(
					getGroupId(), SegmentsPortletKeys.SEGMENTS),
				true);

		for (SegmentsEntry segmentsEntry : segmentsEntries) {
			availableSegmentsEntries.put(
				String.valueOf(segmentsEntry.getSegmentsEntryId()),
				HashMapBuilder.<String, Object>put(
					"name", segmentsEntry.getName(themeDisplay.getLocale())
				).put(
					"segmentsEntryId",
					String.valueOf(segmentsEntry.getSegmentsEntryId())
				).build());
		}

		availableSegmentsEntries.put(
			String.valueOf(SegmentsEntryConstants.ID_DEFAULT),
			HashMapBuilder.<String, Object>put(
				"name",
				SegmentsEntryConstants.getDefaultSegmentsEntryName(
					themeDisplay.getLocale())
			).put(
				"segmentsEntryId", SegmentsEntryConstants.ID_DEFAULT
			).build());

		return availableSegmentsEntries;
	}

	private String _getEditSegmentsEntryURL() throws Exception {
		if (_editSegmentsEntryURL != null) {
			return _editSegmentsEntryURL;
		}

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, SegmentsEntry.class.getName(),
			PortletProvider.Action.EDIT);

		if (portletURL == null) {
			_editSegmentsEntryURL = StringPool.BLANK;
		}
		else {
			_editSegmentsEntryURL = layoutLockManager.getUnlockDraftLayoutURL(
				portal.getLiferayPortletResponse(renderResponse),
				() -> {
					portletURL.setParameter(
						"redirect", themeDisplay.getURLCurrent());

					return portletURL.toString();
				});
		}

		return _editSegmentsEntryURL;
	}

	private InfoCollectionProvider<?> _getInfoCollectionProvider(
		String collectionPK) {

		for (InfoCollectionProvider<?> infoCollectionProvider :
				(List<InfoCollectionProvider<?>>)
					(List<?>)infoItemServiceRegistry.getAllInfoItemServices(
						InfoCollectionProvider.class)) {

			if (Objects.equals(infoCollectionProvider.getKey(), collectionPK)) {
				return infoCollectionProvider;
			}
		}

		return null;
	}

	private String _getInfoCollectionProviderItemTypeLabel(
		InfoCollectionProvider<?> infoCollectionProvider) {

		String className = infoCollectionProvider.getCollectionItemClassName();

		if (Objects.equals(className, AssetEntry.class.getName())) {
			return language.get(httpServletRequest, "multiple-item-types");
		}

		if (Validator.isNotNull(className)) {
			return ResourceActionsUtil.getModelResource(
				themeDisplay.getLocale(), className);
		}

		return StringPool.BLANK;
	}

	private JSONArray _getInfoCollectionProviderLinkedCollectionJSONArray(
		InfoCollectionProvider<?> infoCollectionProvider) {

		return JSONUtil.put(
			JSONUtil.put(
				"itemSubtype",
				() -> {
					if (infoCollectionProvider instanceof
							SingleFormVariationInfoCollectionProvider) {

						SingleFormVariationInfoCollectionProvider<?>
							singleFormVariationInfoCollectionProvider =
								(SingleFormVariationInfoCollectionProvider<?>)
									infoCollectionProvider;

						return singleFormVariationInfoCollectionProvider.
							getFormVariationKey();
					}

					return null;
				}
			).put(
				"itemType", infoCollectionProvider.getCollectionItemClassName()
			).put(
				"key", infoCollectionProvider.getKey()
			).put(
				"title",
				infoCollectionProvider.getLabel(LocaleUtil.getDefault())
			).put(
				"type", InfoListProviderItemSelectorReturnType.class.getName()
			));
	}

	private List<Map<String, Object>> _getLayoutDataList() throws Exception {
		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					themeDisplay.getScopeGroupId(), themeDisplay.getPlid());

		if (layoutPageTemplateStructure == null) {
			return Collections.emptyList();
		}

		List<Map<String, Object>> layoutDataList = new ArrayList<>();

		List<LayoutPageTemplateStructureRel> layoutPageTemplateStructureRels =
			_layoutPageTemplateStructureRelLocalService.
				getLayoutPageTemplateStructureRels(
					layoutPageTemplateStructure.
						getLayoutPageTemplateStructureId());

		for (LayoutPageTemplateStructureRel layoutPageTemplateStructureRel :
				layoutPageTemplateStructureRels) {

			layoutDataList.add(
				HashMapBuilder.<String, Object>put(
					"layoutData",
					JSONFactoryUtil.createJSONObject(
						layoutPageTemplateStructureRel.getData())
				).put(
					"segmentsExperienceId",
					layoutPageTemplateStructureRel.getSegmentsExperienceId()
				).build());
		}

		return layoutDataList;
	}

	private long _getSegmentsEntryId() {
		if (_segmentsEntryId != null) {
			return _segmentsEntryId;
		}

		_segmentsEntryId = ParamUtil.getLong(
			portal.getOriginalServletRequest(httpServletRequest),
			"segmentsEntryId");

		return _segmentsEntryId;
	}

	private Map<String, Object> _getSelectedMappingTypes() throws Exception {
		Layout layout = themeDisplay.getLayout();

		if (!Objects.equals(
				layout.getType(), LayoutConstants.TYPE_COLLECTION)) {

			return Collections.emptyMap();
		}

		String collectionPK = layout.getTypeSettingsProperty("collectionPK");
		String collectionType = layout.getTypeSettingsProperty(
			"collectionType");

		if (Validator.isNull(collectionPK) ||
			Validator.isNull(collectionType)) {

			return Collections.emptyMap();
		}

		String itemTypeLabel = StringPool.BLANK;
		JSONArray linkedCollectionJSONArray = JSONFactoryUtil.createJSONArray();
		String subtypeLabel = StringPool.BLANK;
		String typeLabel = StringPool.BLANK;
		String subtypeURL = StringPool.BLANK;

		if (Objects.equals(
				collectionType,
				InfoListProviderItemSelectorReturnType.class.getName())) {

			InfoCollectionProvider<?> infoCollectionProvider =
				_getInfoCollectionProvider(collectionPK);

			if (infoCollectionProvider != null) {
				itemTypeLabel = _getInfoCollectionProviderItemTypeLabel(
					infoCollectionProvider);
				linkedCollectionJSONArray =
					_getInfoCollectionProviderLinkedCollectionJSONArray(
						infoCollectionProvider);
				subtypeLabel = infoCollectionProvider.getLabel(
					themeDisplay.getLocale());
			}

			typeLabel = language.get(httpServletRequest, "collection-provider");
		}
		else if (Objects.equals(
					collectionType,
					InfoListItemSelectorReturnType.class.getName())) {

			AssetListEntry assetListEntry = _getAssetListEntry(collectionPK);

			if (assetListEntry != null) {
				itemTypeLabel = _getAssetListEntryItemTypeLabel(assetListEntry);
				linkedCollectionJSONArray =
					_getAssetListEntryLinkedCollectionJSONArray(assetListEntry);
				subtypeLabel = assetListEntry.getTitle();
				subtypeURL = _getAssetListEntryItemTypeURL(assetListEntry);
			}

			if (assetListEntry.getType() ==
					AssetListEntryTypeConstants.TYPE_DYNAMIC) {

				typeLabel = language.get(
					httpServletRequest, "dynamic-collection");
			}
			else {
				typeLabel = language.get(
					httpServletRequest, "manual-collection");
			}
		}

		return HashMapBuilder.<String, Object>put(
			"itemType",
			HashMapBuilder.<String, Object>put(
				"groupItemTypeTitle",
				language.get(httpServletRequest, "item-type")
			).put(
				"label", itemTypeLabel
			).build()
		).put(
			"linkedCollection", linkedCollectionJSONArray
		).put(
			"mappingDescription",
			language.get(
				httpServletRequest,
				"this-page-is-associated-to-the-following-collection")
		).put(
			"subtype",
			HashMapBuilder.<String, Object>put(
				"groupSubtypeTitle", language.get(httpServletRequest, "name")
			).put(
				"label", subtypeLabel
			).put(
				"url", subtypeURL
			).build()
		).put(
			"type",
			HashMapBuilder.<String, Object>put(
				"groupTypeTitle", language.get(httpServletRequest, "type")
			).put(
				"label", typeLabel
			).build()
		).build();
	}

	private boolean _hasEditSegmentsEntryPermission() throws Exception {
		if (Validator.isNull(_getEditSegmentsEntryURL())) {
			return false;
		}

		return true;
	}

	private Boolean _isLockedSegmentsExperience(long segmentsExperienceId)
		throws Exception {

		if (_lockedSegmentsExperience != null) {
			return _lockedSegmentsExperience;
		}

		SegmentsExperience segmentsExperience =
			segmentsExperienceLocalService.getSegmentsExperience(
				segmentsExperienceId);

		_lockedSegmentsExperience = segmentsExperience.hasSegmentsExperiment();

		return _lockedSegmentsExperience;
	}

	private boolean _isShowSegmentsExperiences() throws Exception {
		if (_showSegmentsExperiences != null) {
			return _showSegmentsExperiences;
		}

		Group group = _groupLocalService.getGroup(getGroupId());

		if (!group.isLayoutSetPrototype() && !group.isUser()) {
			_showSegmentsExperiences = true;
		}
		else {
			_showSegmentsExperiences = false;
		}

		return _showSegmentsExperiences;
	}

	private boolean _isSingleSegmentsExperienceMode() {
		long segmentsExperienceId = ParamUtil.getLong(
			portal.getOriginalServletRequest(httpServletRequest),
			"segmentsExperienceId", -1);

		if (segmentsExperienceId == -1) {
			return false;
		}

		SegmentsExperience segmentsExperience =
			segmentsExperienceLocalService.fetchSegmentsExperience(
				segmentsExperienceId);

		if (segmentsExperience != null) {
			List<SegmentsExperimentRel> segmentsExperimentRels =
				_segmentsExperimentRelLocalService.
					getSegmentsExperimentRelsBySegmentsExperienceId(
						segmentsExperience.getSegmentsExperienceId());

			if (segmentsExperimentRels.isEmpty()) {
				return false;
			}

			SegmentsExperimentRel segmentsExperimentRel =
				segmentsExperimentRels.get(0);

			try {
				return !segmentsExperimentRel.isControl();
			}
			catch (PortalException portalException) {
				_log.error(portalException);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentPageLayoutEditorDisplayContext.class);

	private final AssetListEntryLocalService _assetListEntryLocalService;
	private String _editSegmentsEntryURL;
	private final GroupLocalService _groupLocalService;
	private final LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;
	private final LayoutPageTemplateStructureRelLocalService
		_layoutPageTemplateStructureRelLocalService;
	private Boolean _lockedSegmentsExperience;
	private Long _segmentsEntryId;
	private final SegmentsEntryService _segmentsEntryService;
	private Long _segmentsExperienceId;
	private final SegmentsExperimentRelLocalService
		_segmentsExperimentRelLocalService;
	private Boolean _showSegmentsExperiences;

}