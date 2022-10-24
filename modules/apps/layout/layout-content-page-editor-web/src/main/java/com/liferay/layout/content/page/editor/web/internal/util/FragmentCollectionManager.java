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

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.service.FragmentCollectionService;
import com.liferay.fragment.service.FragmentCompositionService;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.fragment.util.comparator.FragmentCollectionContributorNameComparator;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.constants.ContentPageEditorConstants;
import com.liferay.layout.util.PortalPreferencesUtil;
import com.liferay.layout.util.structure.DropZoneLayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = FragmentCollectionManager.class)
public class FragmentCollectionManager {

	public List<Map<String, Object>> getFragmentCollectionMapsList(
		long groupId, HttpServletRequest httpServletRequest,
		boolean includeEmpty, boolean includeSystem,
		DropZoneLayoutStructureItem masterDropZoneLayoutStructureItem,
		ThemeDisplay themeDisplay) {

		List<Map<String, Object>> allFragmentCollectionMapsList =
			new ArrayList<>();

		boolean hideInputFragments = ObjectUtil.hideInputFragments(
			themeDisplay.getCompanyId(), _infoItemServiceTracker,
			themeDisplay.getPermissionChecker());

		PortalPreferences portalPreferences =
			_portletPreferencesFactory.getPortalPreferences(httpServletRequest);

		Set<String> highlightedFragmentEntryKeys = SetUtil.fromArray(
			portalPreferences.getValues(
				ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
				"highlightedFragmentEntryKeys", new String[0]));

		if (includeSystem) {
			allFragmentCollectionMapsList =
				_getSystemFragmentCollectionMapsList(
					hideInputFragments, highlightedFragmentEntryKeys,
					httpServletRequest, masterDropZoneLayoutStructureItem,
					themeDisplay);
		}

		List<FragmentCollection> fragmentCollections =
			_fragmentCollectionService.getFragmentCollections(
				new long[] {
					themeDisplay.getCompanyGroupId(), groupId,
					CompanyConstants.SYSTEM
				});

		for (FragmentCollection fragmentCollection : fragmentCollections) {
			if (!includeSystem &&
				(fragmentCollection.getGroupId() !=
					themeDisplay.getScopeGroupId())) {

				continue;
			}

			List<FragmentEntry> fragmentEntries =
				_fragmentEntryService.getFragmentEntriesByStatus(
					fragmentCollection.getGroupId(),
					fragmentCollection.getFragmentCollectionId(),
					WorkflowConstants.STATUS_APPROVED);

			List<Map<String, Object>> fragmentEntryMapsList =
				_getFragmentEntryMapsList(
					hideInputFragments, fragmentEntries,
					highlightedFragmentEntryKeys,
					masterDropZoneLayoutStructureItem, themeDisplay);

			fragmentEntryMapsList.addAll(
				_getFragmentCompositionMapsList(
					_fragmentCompositionService.getFragmentCompositions(
						fragmentCollection.getGroupId(),
						fragmentCollection.getFragmentCollectionId(),
						WorkflowConstants.STATUS_APPROVED),
					highlightedFragmentEntryKeys,
					masterDropZoneLayoutStructureItem, themeDisplay));

			if (!includeEmpty && ListUtil.isEmpty(fragmentEntryMapsList)) {
				continue;
			}

			allFragmentCollectionMapsList.add(
				HashMapBuilder.<String, Object>put(
					"fragmentCollectionId",
					fragmentCollection.getFragmentCollectionId()
				).put(
					"fragmentEntries", fragmentEntryMapsList
				).put(
					"name", fragmentCollection.getName()
				).build());
		}

		List<String> sortedFragmentCollectionKeys =
			getSortedFragmentCollectionKeys(portalPreferences);

		if (!sortedFragmentCollectionKeys.isEmpty()) {
			Map<String, Map<String, Object>> fragmentCollectionMaps =
				new LinkedHashMap<>();

			for (Map<String, Object> fragmentCollectionMap :
					allFragmentCollectionMapsList) {

				fragmentCollectionMaps.put(
					String.valueOf(
						fragmentCollectionMap.get("fragmentCollectionId")),
					fragmentCollectionMap);
			}

			allFragmentCollectionMapsList =
				_getSortedFragmentCollectionMapsList(
					fragmentCollectionMaps, sortedFragmentCollectionKeys);
		}

		if (SetUtil.isNotEmpty(highlightedFragmentEntryKeys)) {
			Map<String, Map<String, Object>> highlightedFragmentMaps =
				new TreeMap<>();

			for (Map<String, Object> fragmentCollectionMap :
					allFragmentCollectionMapsList) {

				List<Map<String, Object>> fragmentEntryMapsList =
					(List<Map<String, Object>>)
						fragmentCollectionMap.computeIfAbsent(
							"fragmentEntries", key -> new LinkedList<>());

				for (Map<String, Object> fragmentEntryMap :
						fragmentEntryMapsList) {

					if (GetterUtil.getBoolean(
							fragmentEntryMap.get("highlighted"))) {

						highlightedFragmentMaps.put(
							(String)fragmentEntryMap.get("name"),
							fragmentEntryMap);
					}
				}
			}

			if (!highlightedFragmentMaps.isEmpty()) {
				allFragmentCollectionMapsList.add(
					0,
					HashMapBuilder.<String, Object>put(
						"fragmentCollectionId", "highlighted"
					).put(
						"fragmentEntries", highlightedFragmentMaps.values()
					).put(
						"name",
						() -> _language.get(
							themeDisplay.getLocale(), "favorites")
					).build());
			}
		}

		return allFragmentCollectionMapsList;
	}

	public List<String> getSortedFragmentCollectionKeys(
		PortalPreferences portalPreferences) {

		return PortalPreferencesUtil.getSortedPortalPreferencesValues(
			portalPreferences,
			ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
			"sortedFragmentCollectionKeys");
	}

	public void updateSortedFragmentCollectionKeys(
		PortalPreferences portalPreferences,
		String[] sortedFragmentCollectionKeys) {

		PortalPreferencesUtil.updateSortedPortalPreferencesValues(
			portalPreferences,
			ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
			"sortedFragmentCollectionKeys", sortedFragmentCollectionKeys);
	}

	private Map<String, Map<String, Object>> _getDynamicFragmentCollectionMaps(
		boolean hideInputFragments, Set<String> highlightedFragmentEntryKeys,
		HttpServletRequest httpServletRequest,
		DropZoneLayoutStructureItem masterDropZoneLayoutStructureItem,
		ThemeDisplay themeDisplay) {

		Map<String, Map<String, Object>> dynamicFragmentCollectionMaps =
			new LinkedHashMap<>();

		for (FragmentRenderer fragmentRenderer :
				_fragmentRendererTracker.getFragmentRenderers()) {

			if (!fragmentRenderer.isSelectable(httpServletRequest) ||
				!_isAllowedFragmentEntryKey(
					fragmentRenderer.getKey(),
					masterDropZoneLayoutStructureItem)) {

				continue;
			}

			if ((fragmentRenderer.getType() == FragmentConstants.TYPE_INPUT) &&
				hideInputFragments) {

				continue;
			}

			Map<String, Object> dynamicFragmentCollectionMap =
				dynamicFragmentCollectionMaps.computeIfAbsent(
					fragmentRenderer.getCollectionKey(),
					key -> HashMapBuilder.<String, Object>put(
						"fragmentCollectionId",
						fragmentRenderer.getCollectionKey()
					).put(
						"name",
						() -> _language.get(
							themeDisplay.getLocale(),
							"fragment.collection.label." +
								fragmentRenderer.getCollectionKey())
					).build());

			List<Map<String, Object>> fragmentEntryMapsList =
				(List<Map<String, Object>>)
					dynamicFragmentCollectionMap.computeIfAbsent(
						"fragmentEntries", key -> new LinkedList<>());

			fragmentEntryMapsList.add(
				HashMapBuilder.<String, Object>put(
					"fragmentEntryKey", fragmentRenderer.getKey()
				).put(
					"highlighted",
					highlightedFragmentEntryKeys.contains(
						fragmentRenderer.getKey())
				).put(
					"icon", fragmentRenderer.getIcon()
				).put(
					"imagePreviewURL",
					fragmentRenderer.getImagePreviewURL(httpServletRequest)
				).put(
					"name", fragmentRenderer.getLabel(themeDisplay.getLocale())
				).build());
		}

		return dynamicFragmentCollectionMaps;
	}

	private Map<String, Map<String, Object>>
		_getFragmentCollectionContributorMaps(
			boolean hideInputFragments,
			Set<String> highlightedFragmentEntryKeys,
			DropZoneLayoutStructureItem masterDropZoneLayoutStructureItem,
			ThemeDisplay themeDisplay) {

		Map<String, Map<String, Object>> fragmentCollectionContributorMaps =
			new LinkedHashMap<>();

		List<FragmentCollectionContributor> fragmentCollectionContributors =
			_fragmentCollectionContributorTracker.
				getFragmentCollectionContributors();

		Collections.sort(
			fragmentCollectionContributors,
			new FragmentCollectionContributorNameComparator(
				themeDisplay.getLocale()));

		for (FragmentCollectionContributor fragmentCollectionContributor :
				fragmentCollectionContributors) {

			List<FragmentComposition> fragmentCompositions =
				fragmentCollectionContributor.getFragmentCompositions(
					themeDisplay.getLocale());
			List<FragmentEntry> fragmentEntries =
				fragmentCollectionContributor.getFragmentEntries(
					themeDisplay.getLocale());

			if (ListUtil.isEmpty(fragmentCompositions) &&
				ListUtil.isEmpty(fragmentEntries)) {

				continue;
			}

			List<Map<String, Object>> fragmentEntryMapsList =
				_getFragmentEntryMapsList(
					hideInputFragments, fragmentEntries,
					highlightedFragmentEntryKeys,
					masterDropZoneLayoutStructureItem, themeDisplay);

			fragmentEntryMapsList.addAll(
				_getFragmentCompositionMapsList(
					fragmentCompositions, highlightedFragmentEntryKeys,
					masterDropZoneLayoutStructureItem, themeDisplay));

			if (ListUtil.isEmpty(fragmentEntryMapsList)) {
				continue;
			}

			fragmentEntryMapsList.sort(
				(fragmentEntryMap1, fragmentEntryMap2) -> {
					String name1 = String.valueOf(
						fragmentEntryMap1.get("name"));
					String name2 = String.valueOf(
						fragmentEntryMap2.get("name"));

					return name1.compareTo(name2);
				});

			fragmentCollectionContributorMaps.put(
				fragmentCollectionContributor.getFragmentCollectionKey(),
				HashMapBuilder.<String, Object>put(
					"fragmentCollectionId",
					fragmentCollectionContributor.getFragmentCollectionKey()
				).put(
					"fragmentEntries", fragmentEntryMapsList
				).put(
					"name",
					fragmentCollectionContributor.getName(
						themeDisplay.getLocale())
				).build());
		}

		return fragmentCollectionContributorMaps;
	}

	private List<Map<String, Object>> _getFragmentCompositionMapsList(
		List<FragmentComposition> fragmentCompositions,
		Set<String> highlightedFragmentEntryKeys,
		DropZoneLayoutStructureItem masterDropZoneLayoutStructureItem,
		ThemeDisplay themeDisplay) {

		List<Map<String, Object>> filteredFragmentCompositions =
			new ArrayList<>();

		for (FragmentComposition fragmentComposition : fragmentCompositions) {
			if (!_isAllowedFragmentEntryKey(
					fragmentComposition.getFragmentCompositionKey(),
					masterDropZoneLayoutStructureItem)) {

				continue;
			}

			filteredFragmentCompositions.add(
				HashMapBuilder.<String, Object>put(
					"fragmentEntryKey",
					fragmentComposition.getFragmentCompositionKey()
				).put(
					"groupId", fragmentComposition.getGroupId()
				).put(
					"highlighted",
					highlightedFragmentEntryKeys.contains(
						_getFragmentUniqueKey(
							fragmentComposition.getFragmentCompositionKey(),
							fragmentComposition.getGroupId()))
				).put(
					"icon", fragmentComposition.getIcon()
				).put(
					"imagePreviewURL",
					fragmentComposition.getImagePreviewURL(themeDisplay)
				).put(
					"name", fragmentComposition.getName()
				).put(
					"type", ContentPageEditorConstants.TYPE_COMPOSITION
				).build());
		}

		return filteredFragmentCompositions;
	}

	private List<Map<String, Object>> _getFragmentEntryMapsList(
		boolean hideInputFragments, List<FragmentEntry> fragmentEntries,
		Set<String> highlightedFragmentEntryKeys,
		DropZoneLayoutStructureItem masterDropZoneLayoutStructureItem,
		ThemeDisplay themeDisplay) {

		List<Map<String, Object>> fragmentEntryMapsList = new ArrayList<>();

		for (FragmentEntry fragmentEntry : fragmentEntries) {
			if (!_isAllowedFragmentEntryKey(
					fragmentEntry.getFragmentEntryKey(),
					masterDropZoneLayoutStructureItem) ||
				((fragmentEntry.isTypeInput() ||
				  Objects.equals(
					  fragmentEntry.getFragmentEntryKey(),
					  "INPUTS-submit-button")) &&
				 hideInputFragments)) {

				continue;
			}

			fragmentEntryMapsList.add(
				HashMapBuilder.<String, Object>put(
					"fragmentEntryKey", fragmentEntry.getFragmentEntryKey()
				).put(
					"groupId", fragmentEntry.getGroupId()
				).put(
					"highlighted",
					highlightedFragmentEntryKeys.contains(
						_getFragmentUniqueKey(
							fragmentEntry.getFragmentEntryKey(),
							fragmentEntry.getGroupId()))
				).put(
					"icon", fragmentEntry.getIcon()
				).put(
					"imagePreviewURL",
					fragmentEntry.getImagePreviewURL(themeDisplay)
				).put(
					"name", fragmentEntry.getName()
				).put(
					"type",
					FragmentConstants.getTypeLabel(fragmentEntry.getType())
				).build());
		}

		return fragmentEntryMapsList;
	}

	private String _getFragmentUniqueKey(
		String fragmentEntryKey, long groupId) {

		if (groupId <= 0) {
			return fragmentEntryKey;
		}

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return fragmentEntryKey;
		}

		return fragmentEntryKey + StringPool.POUND + group.getGroupKey();
	}

	private List<Map<String, Object>> _getSortedFragmentCollectionMapsList(
		Map<String, Map<String, Object>> fragmentCollectionMaps,
		List<String> sortedFragmentCollectionKeys) {

		List<Map<String, Object>> sortedFragmentCollectionMapsList =
			new LinkedList<>();

		for (String collectionKey : sortedFragmentCollectionKeys) {
			Map<String, Object> fragmentCollectionMap =
				fragmentCollectionMaps.remove(collectionKey);

			if (fragmentCollectionMap == null) {
				continue;
			}

			sortedFragmentCollectionMapsList.add(fragmentCollectionMap);
		}

		sortedFragmentCollectionMapsList.addAll(
			fragmentCollectionMaps.values());

		return sortedFragmentCollectionMapsList;
	}

	private List<Map<String, Object>> _getSystemFragmentCollectionMapsList(
		boolean hideInputFragments, Set<String> highlightedFragmentEntryKeys,
		HttpServletRequest httpServletRequest,
		DropZoneLayoutStructureItem masterDropZoneLayoutStructureItem,
		ThemeDisplay themeDisplay) {

		Map<String, Map<String, Object>> fragmentCollectionMaps =
			_getFragmentCollectionContributorMaps(
				hideInputFragments, highlightedFragmentEntryKeys,
				masterDropZoneLayoutStructureItem, themeDisplay);

		fragmentCollectionMaps.putAll(
			_getDynamicFragmentCollectionMaps(
				hideInputFragments, highlightedFragmentEntryKeys,
				httpServletRequest, masterDropZoneLayoutStructureItem,
				themeDisplay));

		Map<String, List<Map<String, Object>>> layoutElementMapsListMap =
			ObjectUtil.getLayoutElementMapsListMap(
				themeDisplay.getCompanyId(), _infoItemServiceTracker,
				themeDisplay.getPermissionChecker());

		for (Map.Entry<String, List<Map<String, Object>>> entry :
				layoutElementMapsListMap.entrySet()) {

			List<Map<String, Object>> layoutElementMapsList =
				new LinkedList<>();

			for (Map<String, Object> layoutElementMap : entry.getValue()) {
				String fragmentEntryKey = (String)layoutElementMap.get(
					"fragmentEntryKey");

				if (!_isAllowedFragmentEntryKey(
						fragmentEntryKey, masterDropZoneLayoutStructureItem)) {

					continue;
				}

				layoutElementMapsList.add(
					HashMapBuilder.create(
						layoutElementMap
					).put(
						"highlighted",
						highlightedFragmentEntryKeys.contains(fragmentEntryKey)
					).put(
						"name",
						_language.get(
							themeDisplay.getLocale(),
							(String)layoutElementMap.get("languageKey"))
					).build());
			}

			if (layoutElementMapsList.isEmpty()) {
				continue;
			}

			String collectionKey = entry.getKey();

			Map<String, Object> fragmentCollectionMap =
				fragmentCollectionMaps.computeIfAbsent(
					collectionKey,
					key -> HashMapBuilder.<String, Object>put(
						"fragmentCollectionId", collectionKey
					).put(
						"name",
						_language.get(
							themeDisplay.getLocale(),
							"fragment.collection.label." +
								StringUtil.toLowerCase(collectionKey))
					).build());

			List<Map<String, Object>> fragmentEntryMapsList =
				(List<Map<String, Object>>)
					fragmentCollectionMap.computeIfAbsent(
						"fragmentEntries", key -> new LinkedList<>());

			fragmentEntryMapsList.addAll(0, layoutElementMapsList);
		}

		return _getSortedFragmentCollectionMapsList(
			fragmentCollectionMaps,
			ListUtil.fromArray(_SORTED_FRAGMENT_COLLECTION_KEYS));
	}

	private boolean _isAllowedFragmentEntryKey(
		String fragmentEntryKey,
		DropZoneLayoutStructureItem masterDropZoneLayoutStructureItem) {

		List<String> fragmentEntryKeys = Collections.emptyList();
		boolean allowNewFragmentEntries = true;

		if (masterDropZoneLayoutStructureItem != null) {
			fragmentEntryKeys =
				masterDropZoneLayoutStructureItem.getFragmentEntryKeys();
			allowNewFragmentEntries =
				masterDropZoneLayoutStructureItem.isAllowNewFragmentEntries();
		}

		if (allowNewFragmentEntries) {
			if (ListUtil.isEmpty(fragmentEntryKeys) ||
				!fragmentEntryKeys.contains(fragmentEntryKey)) {

				return true;
			}

			return false;
		}

		if (ListUtil.isNotEmpty(fragmentEntryKeys) &&
			fragmentEntryKeys.contains(fragmentEntryKey)) {

			return true;
		}

		return false;
	}

	private static final String[] _SORTED_FRAGMENT_COLLECTION_KEYS = {
		"layout-elements", "BASIC_COMPONENT", "INPUTS", "content-display"
	};

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentCollectionService _fragmentCollectionService;

	@Reference
	private FragmentCompositionService _fragmentCompositionService;

	@Reference
	private FragmentEntryService _fragmentEntryService;

	@Reference
	private FragmentRendererTracker _fragmentRendererTracker;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private Language _language;

	@Reference
	private PortletPreferencesFactory _portletPreferencesFactory;

}