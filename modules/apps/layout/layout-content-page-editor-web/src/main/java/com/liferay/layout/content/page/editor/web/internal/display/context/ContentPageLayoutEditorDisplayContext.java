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

package com.liferay.layout.content.page.editor.web.internal.display.context;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalServiceUtil;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.collection.provider.SingleFormVariationInfoCollectionProvider;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.layout.content.page.editor.sidebar.panel.ContentPageEditorSidebarPanel;
import com.liferay.layout.content.page.editor.web.internal.configuration.FFLayoutContentPageEditorConfiguration;
import com.liferay.layout.content.page.editor.web.internal.configuration.PageEditorConfiguration;
import com.liferay.layout.content.page.editor.web.internal.constants.ContentPageEditorActionKeys;
import com.liferay.layout.content.page.editor.web.internal.segments.SegmentsExperienceUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalServiceUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.model.SegmentsExperimentRelTable;
import com.liferay.segments.service.SegmentsEntryServiceUtil;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;
import com.liferay.segments.service.SegmentsExperimentRelLocalServiceUtil;
import com.liferay.staging.StagingGroupHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

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
		CommentManager commentManager,
		List<ContentPageEditorSidebarPanel> contentPageEditorSidebarPanels,
		FFLayoutContentPageEditorConfiguration
			ffLayoutContentPageEditorConfiguration,
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererController fragmentRendererController,
		FragmentRendererTracker fragmentRendererTracker,
		FrontendTokenDefinitionRegistry frontendTokenDefinitionRegistry,
		HttpServletRequest httpServletRequest,
		InfoItemServiceTracker infoItemServiceTracker,
		ItemSelector itemSelector,
		PageEditorConfiguration pageEditorConfiguration,
		PortletRequest portletRequest, RenderResponse renderResponse,
		StagingGroupHelper stagingGroupHelper) {

		super(
			commentManager, contentPageEditorSidebarPanels,
			ffLayoutContentPageEditorConfiguration,
			fragmentCollectionContributorTracker,
			fragmentEntryConfigurationParser, fragmentRendererController,
			fragmentRendererTracker, frontendTokenDefinitionRegistry,
			httpServletRequest, infoItemServiceTracker, itemSelector,
			pageEditorConfiguration, portletRequest, renderResponse,
			stagingGroupHelper);
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
			getFragmentEntryActionURL(
				"/layout_content_page_editor/add_segments_experience"));
		configContext.put(
			"availableSegmentsEntries", _getAvailableSegmentsEntries());
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
			PortalUtil.getOriginalServletRequest(httpServletRequest),
			"segmentsExperienceId", -1);

		if ((_segmentsExperienceId != -1) &&
			(_segmentsExperienceId != SegmentsExperienceConstants.ID_DEFAULT)) {

			_segmentsExperienceId = Optional.ofNullable(
				SegmentsExperienceLocalServiceUtil.fetchSegmentsExperience(
					_segmentsExperienceId)
			).map(
				SegmentsExperience::getSegmentsExperienceId
			).orElseGet(
				super::getSegmentsExperienceId
			);
		}
		else {
			_segmentsExperienceId = super.getSegmentsExperienceId();
		}

		return _segmentsExperienceId;
	}

	private AssetListEntry _getAssetListEntry(String collectionPK) {
		return AssetListEntryLocalServiceUtil.fetchAssetListEntry(
			GetterUtil.getLong(collectionPK));
	}

	private String _getAssetListEntryItemTypeLabel(
		AssetListEntry assetListEntry) {

		if (Objects.equals(
				assetListEntry.getAssetEntryType(),
				AssetEntry.class.getName())) {

			return LanguageUtil.get(httpServletRequest, "multiple-item-types");
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
				PortalUtil.getClassNameId(AssetListEntry.class.getName())
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
			SegmentsEntryServiceUtil.getSegmentsEntries(
				_getStagingAwareGroupId(), true);

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
			portletURL.setParameter("redirect", themeDisplay.getURLCurrent());

			_editSegmentsEntryURL = portletURL.toString();
		}

		return _editSegmentsEntryURL;
	}

	private InfoCollectionProvider<?> _getInfoCollectionProvider(
		String collectionPK) {

		List<InfoCollectionProvider<?>> infoCollectionProviders =
			(List<InfoCollectionProvider<?>>)
				(List<?>)infoItemServiceTracker.getAllInfoItemServices(
					InfoCollectionProvider.class);

		Stream<InfoCollectionProvider<?>> stream =
			infoCollectionProviders.stream();

		Optional<InfoCollectionProvider<?>> infoCollectionProviderOptional =
			stream.filter(
				infoCollectionProvider -> Objects.equals(
					infoCollectionProvider.getKey(), collectionPK)
			).findFirst();

		if (infoCollectionProviderOptional.isPresent()) {
			return infoCollectionProviderOptional.get();
		}

		return null;
	}

	private String _getInfoCollectionProviderItemTypeLabel(
		InfoCollectionProvider<?> infoCollectionProvider) {

		String className = infoCollectionProvider.getCollectionItemClassName();

		if (Objects.equals(className, AssetEntry.class.getName())) {
			return LanguageUtil.get(httpServletRequest, "multiple-item-types");
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
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					themeDisplay.getScopeGroupId(), themeDisplay.getPlid(),
					true);

		if (layoutPageTemplateStructure == null) {
			return Collections.emptyList();
		}

		List<Map<String, Object>> layoutDataList = new ArrayList<>();

		List<LayoutPageTemplateStructureRel> layoutPageTemplateStructureRels =
			LayoutPageTemplateStructureRelLocalServiceUtil.
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
			PortalUtil.getOriginalServletRequest(httpServletRequest),
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

			typeLabel = LanguageUtil.get(
				httpServletRequest, "collection-provider");
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

				typeLabel = LanguageUtil.get(
					httpServletRequest, "dynamic-collection");
			}
			else {
				typeLabel = LanguageUtil.get(
					httpServletRequest, "manual-collection");
			}
		}

		return HashMapBuilder.<String, Object>put(
			"itemType",
			HashMapBuilder.<String, Object>put(
				"groupItemTypeTitle",
				LanguageUtil.get(httpServletRequest, "item-type")
			).put(
				"label", itemTypeLabel
			).build()
		).put(
			"linkedCollection", linkedCollectionJSONArray
		).put(
			"mappingDescription",
			LanguageUtil.get(
				httpServletRequest,
				"this-page-is-associated-to-the-following-collection")
		).put(
			"type",
			HashMapBuilder.<String, Object>put(
				"groupTypeTitle", LanguageUtil.get(httpServletRequest, "type")
			).put(
				"label", typeLabel
			).build()
		).put(
			"subtype",
			HashMapBuilder.<String, Object>put(
				"groupSubtypeTitle",
				LanguageUtil.get(httpServletRequest, "name")
			).put(
				"label", subtypeLabel
			).put(
				"url", subtypeURL
			).build()
		).build();
	}

	private long _getStagingAwareGroupId() {
		long groupId = getGroupId();

		if (stagingGroupHelper.isStagingGroup(groupId) &&
			!stagingGroupHelper.isStagedPortlet(
				groupId, SegmentsPortletKeys.SEGMENTS)) {

			Group group = stagingGroupHelper.fetchLiveGroup(groupId);

			if (group != null) {
				groupId = group.getGroupId();
			}
		}

		return groupId;
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

		if (SegmentsExperienceConstants.ID_DEFAULT == segmentsExperienceId) {
			_lockedSegmentsExperience =
				SegmentsExperienceUtil.
					hasDefaultSegmentsExperienceLockedSegmentsExperiment(
						themeDisplay);
		}
		else {
			SegmentsExperience segmentsExperience =
				SegmentsExperienceLocalServiceUtil.getSegmentsExperience(
					segmentsExperienceId);

			_lockedSegmentsExperience =
				segmentsExperience.hasSegmentsExperiment();
		}

		return _lockedSegmentsExperience;
	}

	private boolean _isShowSegmentsExperiences() throws Exception {
		if (_showSegmentsExperiences != null) {
			return _showSegmentsExperiences;
		}

		Group group = GroupLocalServiceUtil.getGroup(getGroupId());

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
			PortalUtil.getOriginalServletRequest(httpServletRequest),
			"segmentsExperienceId", -1);

		if (segmentsExperienceId == -1) {
			return false;
		}

		SegmentsExperience segmentsExperience =
			SegmentsExperienceLocalServiceUtil.fetchSegmentsExperience(
				segmentsExperienceId);

		if (segmentsExperience != null) {
			List<SegmentsExperimentRel> segmentsExperimentRels =
				SegmentsExperimentRelLocalServiceUtil.dslQuery(
					DSLQueryFactoryUtil.select(
						SegmentsExperimentRelTable.INSTANCE
					).from(
						SegmentsExperimentRelTable.INSTANCE
					).where(
						SegmentsExperimentRelTable.INSTANCE.
							segmentsExperienceId.eq(
								segmentsExperience.getSegmentsExperienceId())
					));

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

	private String _editSegmentsEntryURL;
	private Boolean _lockedSegmentsExperience;
	private Long _segmentsEntryId;
	private Long _segmentsExperienceId;
	private Boolean _showSegmentsExperiences;

}