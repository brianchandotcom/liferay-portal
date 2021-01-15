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
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.layout.content.page.editor.sidebar.panel.ContentPageEditorSidebarPanel;
import com.liferay.layout.content.page.editor.web.internal.configuration.FFLayoutContentPageEditorConfiguration;
import com.liferay.layout.content.page.editor.web.internal.configuration.PageEditorConfiguration;
import com.liferay.layout.content.page.editor.web.internal.constants.ContentPageEditorActionKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalServiceUtil;
import com.liferay.petra.reflect.GenericUtil;
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
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.constants.SegmentsPortletKeys;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsEntryServiceUtil;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;
import com.liferay.segments.service.SegmentsExperienceServiceUtil;
import com.liferay.segments.service.SegmentsExperimentLocalServiceUtil;
import com.liferay.staging.StagingGroupHelper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
			pageEditorConfiguration, portletRequest, renderResponse);

		_stagingGroupHelper = stagingGroupHelper;
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
			"defaultSegmentsExperienceId",
			String.valueOf(SegmentsExperienceConstants.ID_DEFAULT));
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
			"availableSegmentsExperiences", _getAvailableSegmentsExperiences());
		stateContext.put("layoutDataList", _getLayoutDataList());
		stateContext.put(
			"segmentsExperienceId", String.valueOf(getSegmentsExperienceId()));
		stateContext.put(
			"segmentsExperimentStatus",
			_getSegmentsExperimentStatus(getSegmentsExperienceId()));

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

		_segmentsExperienceId = SegmentsExperienceConstants.ID_DEFAULT;

		long selectedSegmentsExperienceId = ParamUtil.getLong(
			PortalUtil.getOriginalServletRequest(httpServletRequest),
			"segmentsExperienceId", -1);

		if ((selectedSegmentsExperienceId != -1) &&
			(selectedSegmentsExperienceId !=
				SegmentsExperienceConstants.ID_DEFAULT)) {

			SegmentsExperience segmentsExperience =
				SegmentsExperienceLocalServiceUtil.fetchSegmentsExperience(
					selectedSegmentsExperienceId);

			if (segmentsExperience != null) {
				_segmentsExperienceId =
					segmentsExperience.getSegmentsExperienceId();
			}
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
						_log.debug(portalException, portalException);
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

	private Map<String, Object> _getAvailableSegmentsExperiences()
		throws Exception {

		Map<String, Object> availableSegmentsExperiences = new HashMap<>();

		Layout draftLayout = themeDisplay.getLayout();

		Layout layout = LayoutLocalServiceUtil.getLayout(
			draftLayout.getClassPK());

		String layoutFullURL = PortalUtil.getLayoutFullURL(
			layout, themeDisplay);

		List<SegmentsExperience> segmentsExperiences =
			SegmentsExperienceServiceUtil.getSegmentsExperiences(
				getGroupId(), PortalUtil.getClassNameId(Layout.class.getName()),
				themeDisplay.getPlid(), true);

		boolean addedDefault = false;

		for (SegmentsExperience segmentsExperience : segmentsExperiences) {
			availableSegmentsExperiences.put(
				String.valueOf(segmentsExperience.getSegmentsExperienceId()),
				HashMapBuilder.<String, Object>put(
					"hasLockedSegmentsExperiment",
					segmentsExperience.hasSegmentsExperiment()
				).put(
					"name", segmentsExperience.getName(themeDisplay.getLocale())
				).put(
					"priority", segmentsExperience.getPriority()
				).put(
					"segmentsEntryId",
					String.valueOf(segmentsExperience.getSegmentsEntryId())
				).put(
					"segmentsExperienceId",
					String.valueOf(segmentsExperience.getSegmentsExperienceId())
				).put(
					"segmentsExperimentStatus",
					_getSegmentsExperimentStatus(
						segmentsExperience.getSegmentsExperienceId())
				).put(
					"segmentsExperimentURL",
					_getSegmentsExperimentURL(
						layoutFullURL,
						segmentsExperience.getSegmentsExperienceId())
				).build());
		}

		if (!addedDefault) {
			availableSegmentsExperiences.put(
				String.valueOf(SegmentsExperienceConstants.ID_DEFAULT),
				_getDefaultExperience(layoutFullURL));
		}

		return availableSegmentsExperiences;
	}

	private String _getClassName(InfoListProvider<?> infoListProvider) {
		Class<?> clazz = infoListProvider.getClass();

		Type[] genericInterfaceTypes = clazz.getGenericInterfaces();

		for (Type genericInterfaceType : genericInterfaceTypes) {
			ParameterizedType parameterizedType =
				(ParameterizedType)genericInterfaceType;

			Class<?> typeClazz =
				(Class<?>)parameterizedType.getActualTypeArguments()[0];

			return typeClazz.getName();
		}

		return StringPool.BLANK;
	}

	private HashMap<String, Object> _getDefaultExperience(String layoutFullURL)
		throws Exception {

		return HashMapBuilder.<String, Object>put(
			"hasLockedSegmentsExperiment",
			_hasDefaultSegmentsExperienceLockedSegmentsExperiment()
		).put(
			"name",
			SegmentsExperienceConstants.getDefaultSegmentsExperienceName(
				themeDisplay.getLocale())
		).put(
			"priority", SegmentsExperienceConstants.PRIORITY_DEFAULT
		).put(
			"segmentsEntryId", String.valueOf(SegmentsEntryConstants.ID_DEFAULT)
		).put(
			"segmentsExperienceId",
			String.valueOf(SegmentsExperienceConstants.ID_DEFAULT)
		).put(
			"segmentsExperimentStatus",
			_getSegmentsExperimentStatus(SegmentsExperienceConstants.ID_DEFAULT)
		).put(
			"segmentsExperimentURL",
			_getSegmentsExperimentURL(
				layoutFullURL, SegmentsExperienceConstants.ID_DEFAULT)
		).build();
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

	private InfoListProvider<?> _getInfoListProvider(String collectionPK) {
		List<InfoListProvider<?>> infoListProviders =
			(List<InfoListProvider<?>>)
				(List<?>)infoItemServiceTracker.getAllInfoItemServices(
					InfoListProvider.class);

		Stream<InfoListProvider<?>> stream = infoListProviders.stream();

		Optional<InfoListProvider<?>> infoListProviderOptional = stream.filter(
			infoListProvider -> Objects.equals(
				infoListProvider.getKey(), collectionPK)
		).findFirst();

		if (infoListProviderOptional.isPresent()) {
			return infoListProviderOptional.get();
		}

		return null;
	}

	private String _getInfoListProviderItemTypeLabel(
		InfoListProvider<?> infoListProvider) {

		String className = _getClassName(infoListProvider);

		if (Objects.equals(className, AssetEntry.class.getName())) {
			return LanguageUtil.get(httpServletRequest, "multiple-item-types");
		}

		if (Validator.isNotNull(className)) {
			return ResourceActionsUtil.getModelResource(
				themeDisplay.getLocale(), className);
		}

		return StringPool.BLANK;
	}

	private JSONArray _getInfoListProviderLinkedCollectionJSONArray(
		InfoListProvider<?> infoListProvider) {

		return JSONUtil.put(
			JSONUtil.put(
				"itemType", GenericUtil.getGenericClassName(infoListProvider)
			).put(
				"key", infoListProvider.getKey()
			).put(
				"title", infoListProvider.getLabel(LocaleUtil.getDefault())
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

	private Optional<SegmentsExperiment> _getSegmentsExperimentOptional(
			long segmentsExperienceId)
		throws Exception {

		Layout draftLayout = themeDisplay.getLayout();

		Layout layout = LayoutLocalServiceUtil.getLayout(
			draftLayout.getClassPK());

		return Optional.ofNullable(
			SegmentsExperimentLocalServiceUtil.fetchSegmentsExperiment(
				segmentsExperienceId, PortalUtil.getClassNameId(Layout.class),
				layout.getPlid(),
				SegmentsExperimentConstants.Status.getExclusiveStatusValues()));
	}

	private Map<String, Object> _getSegmentsExperimentStatus(
			long segmentsExperienceId)
		throws Exception {

		Optional<SegmentsExperiment> segmentsExperimentOptional =
			_getSegmentsExperimentOptional(segmentsExperienceId);

		if (!segmentsExperimentOptional.isPresent()) {
			return null;
		}

		SegmentsExperiment segmentsExperiment =
			segmentsExperimentOptional.get();

		SegmentsExperimentConstants.Status status =
			SegmentsExperimentConstants.Status.valueOf(
				segmentsExperiment.getStatus());

		return HashMapBuilder.<String, Object>put(
			"label",
			LanguageUtil.get(
				ResourceBundleUtil.getBundle(
					themeDisplay.getLocale(), getClass()),
				status.getLabel())
		).put(
			"value", status.getValue()
		).build();
	}

	private String _getSegmentsExperimentURL(
		String layoutFullURL, long segmentsExperienceId) {

		HttpUtil.addParameter(
			layoutFullURL, "p_l_back_url", themeDisplay.getURLCurrent());

		return HttpUtil.addParameter(
			layoutFullURL, "segmentsExperienceId", segmentsExperienceId);
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

			InfoListProvider<?> infoListProvider = _getInfoListProvider(
				collectionPK);

			if (infoListProvider != null) {
				itemTypeLabel = _getInfoListProviderItemTypeLabel(
					infoListProvider);
				linkedCollectionJSONArray =
					_getInfoListProviderLinkedCollectionJSONArray(
						infoListProvider);
				subtypeLabel = infoListProvider.getLabel(
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

		if (_stagingGroupHelper.isStagingGroup(groupId) &&
			!_stagingGroupHelper.isStagedPortlet(
				groupId, SegmentsPortletKeys.SEGMENTS)) {

			Group group = _stagingGroupHelper.fetchLiveGroup(groupId);

			if (group != null) {
				groupId = group.getGroupId();
			}
		}

		return groupId;
	}

	private boolean _hasDefaultSegmentsExperienceLockedSegmentsExperiment()
		throws Exception {

		Optional<SegmentsExperiment> segmentsExperimentOptional =
			_getSegmentsExperimentOptional(
				SegmentsExperienceConstants.ID_DEFAULT);

		if (!segmentsExperimentOptional.isPresent()) {
			return false;
		}

		SegmentsExperiment segmentsExperiment =
			segmentsExperimentOptional.get();

		List<Integer> lockedStatusValuesList = ListUtil.fromArray(
			SegmentsExperimentConstants.Status.getLockedStatusValues());

		return lockedStatusValuesList.contains(segmentsExperiment.getStatus());
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
				_hasDefaultSegmentsExperienceLockedSegmentsExperiment();
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

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentPageLayoutEditorDisplayContext.class);

	private String _editSegmentsEntryURL;
	private Boolean _lockedSegmentsExperience;
	private Long _segmentsEntryId;
	private Long _segmentsExperienceId;
	private Boolean _showSegmentsExperiences;
	private final StagingGroupHelper _stagingGroupHelper;

}