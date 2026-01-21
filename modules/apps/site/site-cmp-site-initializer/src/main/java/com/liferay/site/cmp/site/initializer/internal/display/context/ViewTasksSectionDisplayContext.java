/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.display.context;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.cmp.site.initializer.internal.frontend.data.set.filter.CreateDateFDSFilter;
import com.liferay.site.cmp.site.initializer.internal.frontend.data.set.filter.DueDateRangeFDSFilter;
import com.liferay.site.cmp.site.initializer.internal.frontend.data.set.filter.ProjectSelectionFDSFilter;
import com.liferay.site.cmp.site.initializer.internal.frontend.data.set.filter.StateSelectionFDSFilter;
import com.liferay.site.cmp.site.initializer.internal.util.ActionUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Gabriel Albuquerque
 */
public class ViewTasksSectionDisplayContext extends BaseSectionDisplayContext {

	public ViewTasksSectionDisplayContext(
		HttpServletRequest httpServletRequest,
		ObjectDefinition projectObjectDefinition,
		ObjectDefinition taskObjectDefinition) {

		super(httpServletRequest, taskObjectDefinition);

		_assetEntry = (AssetEntry)httpServletRequest.getAttribute(
			WebKeys.LAYOUT_ASSET_ENTRY);

		_projectObjectDefinition = projectObjectDefinition;
	}

	public String getAPIURL() {
		StringBundler sb = new StringBundler(6);

		sb.append("/o/search/v1.0/search?emptySearch=true&");
		sb.append("filter=objectDefinitionId eq ");
		sb.append(objectDefinition.getObjectDefinitionId());

		if (_assetEntry != null) {
			sb.append(" and scopeGroupId eq ");
			sb.append(_assetEntry.getGroupId());
		}

		sb.append("&nestedFields=cmpProjectToCMPTasks,embedded");

		return sb.toString();
	}

	public CreationMenu getCreationMenu() {
		if (_assetEntry == null) {
			return null;
		}

		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "createTask");
				dropdownItem.putData(
					"objectDefinitionId",
					String.valueOf(objectDefinition.getObjectDefinitionId()));
				dropdownItem.putData(
					"redirect",
					StringBundler.concat(
						themeDisplay.getPortalURL(), themeDisplay.getPathMain(),
						GroupConstants.CMS_FRIENDLY_URL,
						"/add_task?objectDefinitionId=",
						objectDefinition.getObjectDefinitionId(), "&plid=",
						themeDisplay.getPlid(), "&projectGroupId=",
						_assetEntry.getGroupId(), "&projectId=",
						_assetEntry.getClassPK(), "&redirect=",
						themeDisplay.getURLCurrent()));
				dropdownItem.putData(
					"title",
					objectDefinition.getLabel(themeDisplay.getLocale()));
				dropdownItem.setIcon("forms");
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "new-task"));
			}
		).build();
	}

	public Map<String, Object> getEmptyState() {
		return HashMapBuilder.<String, Object>put(
			"description",
			LanguageUtil.get(
				httpServletRequest, "click-new-to-create-your-first-task")
		).put(
			"image", "/states/cmp_empty_state_tasks.svg"
		).put(
			"title", LanguageUtil.get(httpServletRequest, "no-tasks-yet")
		).build();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		return ListUtil.fromArray(
			new FDSActionDropdownItem(
				StringBundler.concat(
					ActionUtil.getBaseEditTaskURL(
						objectDefinition, themeDisplay),
					"{embedded.id}?redirect=", themeDisplay.getURLCurrent()),
				"pencil", "edit", LanguageUtil.get(httpServletRequest, "edit"),
				"get", "update", null),
			new FDSActionDropdownItem(
				StringBundler.concat(
					ActionUtil.getBaseViewTaskURL(
						objectDefinition, themeDisplay),
					"{embedded.id}?redirect=", themeDisplay.getURLCurrent()),
				"view", "actionLink",
				LanguageUtil.get(httpServletRequest, "view"), null, "get",
				null),
			new FDSActionDropdownItem(
				StringPool.BLANK, null, "assign-to",
				LanguageUtil.get(httpServletRequest, "assign-to-..."), null,
				"get", null),
			new FDSActionDropdownItem(
				null, "trash", "delete",
				LanguageUtil.get(httpServletRequest, "delete"), null, "delete",
				null));
	}

	public List<FDSFilter> getFDSFilters() {
		List<FDSFilter> fdsFilters = new ArrayList<>();

		fdsFilters.add(new CreateDateFDSFilter());
		fdsFilters.add(new DueDateRangeFDSFilter());

		if (_assetEntry == null) {
			fdsFilters.add(
				new ProjectSelectionFDSFilter(_projectObjectDefinition));
		}

		fdsFilters.add(new StateSelectionFDSFilter());

		return fdsFilters;
	}

	public Map<String, Object> getTaskQuickFiltersProps() {
		if (_assetEntry != null) {
			return HashMapBuilder.<String, Object>put(
				"cmpProjectId", _assetEntry.getClassPK()
			).build();
		}

		return HashMapBuilder.<String, Object>put(
			"blockedCountURL", _getCountURL("state eq 'blocked'")
		).put(
			"inProgressCountURL", _getCountURL("state eq 'inProgress'")
		).put(
			"overdueCountURL",
			_getCountURL(
				StringBundler.concat(
					"dueDate lt ", LocalDate.now(), " and state ne 'done'"))
		).put(
			"totalCountURL", _getCountURL(null)
		).build();
	}

	private String _getCountURL(String filterString) {
		StringBundler sb = new StringBundler(7);

		sb.append("/o/search/v1.0/search?emptySearch=true&");
		sb.append("filter=objectDefinitionId eq ");
		sb.append(objectDefinition.getObjectDefinitionId());

		if (_assetEntry != null) {
			sb.append(" and scopeGroupId eq ");
			sb.append(_assetEntry.getGroupId());
		}

		if (filterString != null) {
			sb.append(" and ");
			sb.append(filterString);
		}

		return sb.toString();
	}

	private final AssetEntry _assetEntry;
	private final ObjectDefinition _projectObjectDefinition;

}