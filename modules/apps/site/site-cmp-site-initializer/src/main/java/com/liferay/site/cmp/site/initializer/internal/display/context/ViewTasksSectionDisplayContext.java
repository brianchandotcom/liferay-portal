/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.display.context;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectState;
import com.liferay.object.model.ObjectStateFlow;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectStateFlowLocalService;
import com.liferay.object.service.ObjectStateLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.RoleService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.site.cmp.site.initializer.internal.constants.CMPActionConstants;
import com.liferay.site.cmp.site.initializer.internal.frontend.data.set.filter.AssigneeSelectionFDSFilter;
import com.liferay.site.cmp.site.initializer.internal.frontend.data.set.filter.CreateDateFDSFilter;
import com.liferay.site.cmp.site.initializer.internal.frontend.data.set.filter.DueDateRangeFDSFilter;
import com.liferay.site.cmp.site.initializer.internal.frontend.data.set.filter.ProjectSelectionFDSFilter;
import com.liferay.site.cmp.site.initializer.internal.frontend.data.set.filter.StateSelectionFDSFilter;
import com.liferay.site.cmp.site.initializer.internal.frontend.data.set.filter.TagSelectionFDSFilter;
import com.liferay.site.cmp.site.initializer.internal.util.ActionUtil;

import jakarta.portlet.ActionRequest;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Gabriel Albuquerque
 */
public class ViewTasksSectionDisplayContext extends BaseSectionDisplayContext {

	public ViewTasksSectionDisplayContext(
		AssetTagLocalService assetTagLocalService,
		ClassNameLocalService classNameLocalService,
		DepotEntryLocalService depotEntryLocalService,
		HttpServletRequest httpServletRequest,
		ListTypeEntryLocalService listTypeEntryLocalService,
		ObjectEntryService objectEntryService,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectStateFlowLocalService objectStateFlowLocalService,
		ObjectStateLocalService objectStateLocalService,
		ObjectDefinition projectObjectDefinition, RoleService roleService,
		ObjectDefinition taskObjectDefinition) {

		super(httpServletRequest, taskObjectDefinition, objectEntryService);

		_assetTagLocalService = assetTagLocalService;
		_classNameLocalService = classNameLocalService;
		_depotEntryLocalService = depotEntryLocalService;
		_listTypeEntryLocalService = listTypeEntryLocalService;
		_objectFieldLocalService = objectFieldLocalService;
		_objectStateFlowLocalService = objectStateFlowLocalService;
		_objectStateLocalService = objectStateLocalService;
		_projectObjectDefinition = projectObjectDefinition;
		_roleService = roleService;

		_assetEntry = (AssetEntry)httpServletRequest.getAttribute(
			WebKeys.LAYOUT_ASSET_ENTRY);
	}

	public Map<String, Object> getAdditionalProps() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"projectId",
			() -> {
				if (_assetEntry == null) {
					return null;
				}

				return _assetEntry.getClassPK();
			}
		).put(
			"states",
			() -> {
				JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

				ObjectField objectField =
					_objectFieldLocalService.fetchObjectField(
						objectDefinition.getObjectDefinitionId(), "state");

				for (ListTypeEntry listTypeEntry :
						_listTypeEntryLocalService.getListTypeEntries(
							objectField.getListTypeDefinitionId())) {

					jsonArray.put(
						JSONUtil.put(
							"key", listTypeEntry.getKey()
						).put(
							"name",
							listTypeEntry.getName(themeDisplay.getLocale())
						).put(
							"nextStates",
							_getNextStatesJSONArray(listTypeEntry, objectField)
						));
				}

				return jsonArray;
			}
		).build();
	}

	public String getAPIURL() {
		StringBundler sb = new StringBundler(11);

		sb.append("/o/search/v1.0/search?emptySearch=true");

		if (_assetEntry == null) {
			sb.append("&entryClassNames=");
			sb.append(HtmlUtil.escapeURL(objectDefinition.getClassName()));
			sb.append(StringPool.COMMA);
			sb.append(KaleoTaskInstanceToken.class.getName());
		}

		sb.append("&filter=(objectDefinitionId eq ");
		sb.append(objectDefinition.getObjectDefinitionId());

		if (_assetEntry != null) {
			sb.append(" and scopeGroupId eq ");
			sb.append(_assetEntry.getGroupId());
		}
		else {
			sb.append(" or keywords/any(k:startswith(k, '");
			sb.append(objectDefinition.getExternalReferenceCode());
			sb.append("'))");
		}

		sb.append(")&nestedFields=cmpProjectToCMPTasks,embedded");

		return sb.toString();
	}

	public CreationMenu getCreationMenu() throws Exception {
		if (!hasAddObjectEntryPortletResourcePermission()) {
			return null;
		}

		return CreationMenuBuilder.addPrimaryDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", CMPActionConstants.CREATE_TASK);
				dropdownItem.putData(
					"addProjectURL",
					StringBundler.concat(
						ActionUtil.getAddProjectURL(
							_projectObjectDefinition, themeDisplay),
						"&action=",
						CMPActionConstants.CREATE_PROJECT_GLOBAL_TASK));
				dropdownItem.putData(
					"addTaskURL",
					StringBundler.concat(
						ActionUtil.getAddTaskURL(
							0, objectDefinition, 0, themeDisplay),
						"&action=", CMPActionConstants.CREATE_GLOBAL_TASK));
				dropdownItem.putData(
					"objectDefinitionId",
					String.valueOf(objectDefinition.getObjectDefinitionId()));

				if (_assetEntry != null) {
					dropdownItem.putData(
						"redirect",
						ActionUtil.getAddTaskURL(
							_assetEntry.getGroupId(), objectDefinition,
							_assetEntry.getClassPK(), themeDisplay));
				}

				dropdownItem.putData(
					"title",
					objectDefinition.getLabel(themeDisplay.getLocale()));
				dropdownItem.setIcon("forms");
				dropdownItem.setLabel(
					LanguageUtil.get(
						httpServletRequest,
						(_assetEntry == null) ? "new" : "new-task"));
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
				"get", "update", null,
				HashMapBuilder.<String, Object>put(
					"entryClassName", objectDefinition.getClassName()
				).build()),
			new FDSActionDropdownItem(
				StringBundler.concat(
					ActionUtil.getBaseViewTaskURL(
						objectDefinition, themeDisplay),
					"{embedded.id}?redirect=", themeDisplay.getURLCurrent()),
				"view", "actionLink",
				LanguageUtil.get(httpServletRequest, "view"), null, "get", null,
				HashMapBuilder.<String, Object>put(
					"entryClassName", objectDefinition.getClassName()
				).build()),
			new FDSActionDropdownItem(
				StringBundler.concat(
					"/o", objectDefinition.getRESTContextPath(),
					"/scopes/{embedded.scopeId}/by-external-reference-code",
					"/{embedded.externalReferenceCode}/subscribe"),
				"bell-on", "subscribe",
				LanguageUtil.get(httpServletRequest, "watch-task"), "post",
				"subscribe", "async"),
			new FDSActionDropdownItem(
				StringBundler.concat(
					"/o", objectDefinition.getRESTContextPath(),
					"/scopes/{embedded.scopeId}/by-external-reference-code",
					"/{embedded.externalReferenceCode}/unsubscribe"),
				"bell-off", "unsubscribe",
				LanguageUtil.get(httpServletRequest, "stop-watching-task"),
				"post", "unsubscribe", "async"),
			new FDSActionDropdownItem(
				StringPool.BLANK, null, "assign-to",
				LanguageUtil.get(httpServletRequest, "assign-to-..."), "get",
				"update", null,
				HashMapBuilder.<String, Object>put(
					"entryClassName", objectDefinition.getClassName()
				).build()),
			new FDSActionDropdownItem(
				null, "trash", "delete",
				LanguageUtil.get(httpServletRequest, "delete"), null, "delete",
				null,
				HashMapBuilder.<String, Object>put(
					"entryClassName", objectDefinition.getClassName()
				).build()),
			new FDSActionDropdownItem(
				PortletURLBuilder.create(
					PortalUtil.getControlPanelPortletURL(
						httpServletRequest, PortletKeys.MY_WORKFLOW_TASK,
						ActionRequest.RENDER_PHASE)
				).setMVCPath(
					"/edit_workflow_task.jsp"
				).setRedirect(
					themeDisplay.getURLCurrent()
				).setParameter(
					"workflowTaskId", "{embedded.id}"
				).buildString(),
				"view", "actionLinkWorkflowTask",
				LanguageUtil.get(httpServletRequest, "view"), null, "get", null,
				HashMapBuilder.<String, Object>put(
					"entryClassName", KaleoTaskInstanceToken.class.getName()
				).build()),
			new FDSActionDropdownItem(
				null, null, "assignToMeWorkflowTask",
				LanguageUtil.get(httpServletRequest, "assign-to-me"), null,
				"assignToMe", null,
				HashMapBuilder.<String, Object>put(
					"embedded.assignedToMe", false
				).put(
					"embedded.completed", false
				).put(
					"entryClassName", KaleoTaskInstanceToken.class.getName()
				).build()),
			new FDSActionDropdownItem(
				null, null, "assignToWorkflowTask",
				LanguageUtil.get(httpServletRequest, "assign-to-..."), null,
				"assignToUser", null,
				HashMapBuilder.<String, Object>put(
					"embedded.completed", false
				).put(
					"entryClassName", KaleoTaskInstanceToken.class.getName()
				).build()),
			new FDSActionDropdownItem(
				null, "date-time", "updateDueDateWorkflowTask",
				LanguageUtil.get(httpServletRequest, "update-due-date"), null,
				"updateDueDate", null,
				HashMapBuilder.<String, Object>put(
					"embedded.completed", false
				).put(
					"entryClassName", KaleoTaskInstanceToken.class.getName()
				).build()));
	}

	public List<FDSFilter> getFDSFilters() {
		List<FDSFilter> fdsFilters = new ArrayList<>();

		fdsFilters.add(
			new AssigneeSelectionFDSFilter(
				_classNameLocalService, _projectObjectDefinition.getCompanyId(),
				_roleService));

		fdsFilters.add(new CreateDateFDSFilter());
		fdsFilters.add(new DueDateRangeFDSFilter());

		if (_assetEntry == null) {
			fdsFilters.add(
				new ProjectSelectionFDSFilter(_projectObjectDefinition));
		}

		fdsFilters.add(new StateSelectionFDSFilter());
		fdsFilters.add(
			new TagSelectionFDSFilter(
				_assetTagLocalService, _depotEntryLocalService, _assetEntry,
				_projectObjectDefinition));

		return fdsFilters;
	}

	public Map<String, Object> getTasksQuickFiltersProperties() {
		return HashMapBuilder.<String, Object>put(
			"projectId",
			() -> {
				if (_assetEntry == null) {
					return null;
				}

				return _assetEntry.getClassPK();
			}
		).build();
	}

	private JSONArray _getNextStatesJSONArray(
		ListTypeEntry currentListTypeEntry, ObjectField objectField) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		ObjectStateFlow objectStateFlow =
			_objectStateFlowLocalService.fetchObjectFieldObjectStateFlow(
				objectField.getObjectFieldId());

		ObjectState objectState =
			_objectStateLocalService.fetchObjectStateFlowObjectState(
				currentListTypeEntry.getListTypeEntryId(),
				objectStateFlow.getObjectStateFlowId());

		for (ObjectState nextObjectState :
				_objectStateLocalService.getNextObjectStates(
					objectState.getObjectStateId())) {

			ListTypeEntry nextListTypeEntry =
				_listTypeEntryLocalService.fetchListTypeEntry(
					nextObjectState.getListTypeEntryId());

			jsonArray.put(nextListTypeEntry.getKey());
		}

		return jsonArray;
	}

	private final AssetEntry _assetEntry;
	private final AssetTagLocalService _assetTagLocalService;
	private final ClassNameLocalService _classNameLocalService;
	private final DepotEntryLocalService _depotEntryLocalService;
	private final ListTypeEntryLocalService _listTypeEntryLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectStateFlowLocalService _objectStateFlowLocalService;
	private final ObjectStateLocalService _objectStateLocalService;
	private final ObjectDefinition _projectObjectDefinition;
	private final RoleService _roleService;

}