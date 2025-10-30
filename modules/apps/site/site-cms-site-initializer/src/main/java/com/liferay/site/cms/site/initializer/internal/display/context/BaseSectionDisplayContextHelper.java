/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.object.constants.ObjectDefinitionSettingConstants;
import com.liferay.object.model.ObjectDefinitionSetting;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectDefinitionSettingLocalService;
import com.liferay.object.service.ObjectEntryFolderLocalServiceUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Daniel Sanz
 */
public class BaseSectionDisplayContextHelper {

	public BaseSectionDisplayContextHelper(
		DepotEntryLocalService depotEntryLocalService,
		GroupLocalService groupLocalService,
		ObjectDefinitionSettingLocalService objectDefinitionSettingLocalService,
		ModelResourcePermission<ObjectEntryFolder>
			objectEntryFolderModelResourcePermission) {

		_depotEntryLocalService = depotEntryLocalService;
		_groupLocalService = groupLocalService;
		_objectDefinitionSettingLocalService =
			objectDefinitionSettingLocalService;
		_objectEntryFolderModelResourcePermission =
			objectEntryFolderModelResourcePermission;
	}

	public String appendStatus(String filterString) {
		return StringBundler.concat(
			filterString, " and status in (", StringUtil.merge(_statuses, ", "),
			")");
	}

	public String getAdditionalAPIURLParameters(
		String filter, HttpServletRequest httpServletRequest,
		String rootObjectEntryFolderExternalReferenceCode) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ObjectEntryFolder objectEntryFolder = _getObjectEntryFolder(
			themeDisplay.getCompanyId(),
			httpServletRequest.getAttribute(InfoDisplayWebKeys.INFO_ITEM),
			rootObjectEntryFolderExternalReferenceCode);

		StringBundler sb = new StringBundler(9);

		sb.append("emptySearch=true&filter=");

		if (objectEntryFolder != null) {
			sb.append("folderId eq ");
			sb.append(objectEntryFolder.getObjectEntryFolderId());

			if (objectEntryFolder.getStatus() ==
					WorkflowConstants.STATUS_IN_TRASH) {

				sb.append(" and status eq ");
				sb.append(WorkflowConstants.STATUS_IN_TRASH);
			}
			else {
				sb.append(" and status in (");
				sb.append(StringUtil.merge(_statuses, ", "));
				sb.append(")");
			}
		}
		else {
			sb.append(filter);
		}

		sb.append("&nestedFields=embedded,file.metadata,");
		sb.append("file.previewURL,file.thumbnailURL,");
		sb.append("systemProperties.objectDefinitionBrief");

		return sb.toString();
	}

	public CreationMenu getCreationMenu(
		List<DropdownItem> dropdownItems,
		HttpServletRequest httpServletRequest) {

		return new CreationMenu() {
			{
				if (_hasAddEntryPermission(httpServletRequest)) {
					for (DropdownItem dropdownItem : dropdownItems) {
						JSONArray depotEntriesJSONArray =
							_getDepotEntriesJSONArray(
								dropdownItem, httpServletRequest);

						if (depotEntriesJSONArray == null) {
							continue;
						}

						dropdownItem.putData(
							"assetLibraries", depotEntriesJSONArray);

						addPrimaryDropdownItem(dropdownItem);
					}
				}
			}
		};
	}

	public JSONArray getDepotEntriesJSONArray(
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ObjectEntryFolder objectEntryFolder = getObjectEntryFolder(
			themeDisplay.getCompanyId(),
			httpServletRequest.getAttribute(InfoDisplayWebKeys.INFO_ITEM));

		if (objectEntryFolder != null) {
			return _getDepotEntriesJSONArray(
				List.of(objectEntryFolder.getGroupId()), httpServletRequest);
		}

		return _getDepotEntriesJSONArray(
			TransformUtil.transform(
				_depotEntryLocalService.getDepotEntries(
					themeDisplay.getCompanyId(), DepotConstants.TYPE_SPACE),
				DepotEntry::getGroupId),
			httpServletRequest);
	}

	protected ObjectEntryFolder getObjectEntryFolder(
		long companyId, Object object) {

		if (object instanceof DepotEntry) {
			DepotEntry depotEntry = (DepotEntry)object;

			return ObjectEntryFolderLocalServiceUtil.
				fetchObjectEntryFolderByExternalReferenceCode(
					getRootObjectEntryFolderExternalReferenceCode(),
					depotEntry.getGroupId(), companyId);
		}
		else if (object instanceof ObjectEntryFolder) {
			return (ObjectEntryFolder)object;
		}

		return null;
	}

	protected String getRootObjectEntryFolderExternalReferenceCode() {
		return null;
	}

	private List<Long> _getAcceptedGroupIds(long objectDefinitionId) {
		List<Long> acceptedGroupIds = new ArrayList<>();

		ObjectDefinitionSetting objectDefinitionSetting =
			_objectDefinitionSettingLocalService.fetchObjectDefinitionSetting(
				objectDefinitionId,
				ObjectDefinitionSettingConstants.NAME_ACCEPTED_GROUP_IDS);

		for (String groupId :
				StringUtil.split(objectDefinitionSetting.getValue())) {

			DepotEntry depotEntry =
				_depotEntryLocalService.fetchGroupDepotEntry(
					GetterUtil.getLong(groupId));

			if (depotEntry != null) {
				acceptedGroupIds.add(depotEntry.getGroupId());
			}
		}

		return acceptedGroupIds;
	}

	private JSONArray _getDepotEntriesJSONArray(
		DropdownItem dropdownItem, HttpServletRequest httpServletRequest) {

		Map<String, Object> dropdownItemData =
			(HashMap<String, Object>)dropdownItem.get("data");

		long objectDefinitionId = GetterUtil.getLong(
			dropdownItemData.get("objectDefinitionId"));

		if (objectDefinitionId != 0) {
			return _getDepotEntriesJSONArray(
				httpServletRequest, objectDefinitionId);
		}

		return getDepotEntriesJSONArray(httpServletRequest);
	}

	private JSONArray _getDepotEntriesJSONArray(
		HttpServletRequest httpServletRequest, long objectDefinitionId) {

		if (_isAcceptAllGroups(objectDefinitionId)) {
			return getDepotEntriesJSONArray(httpServletRequest);
		}

		List<Long> acceptedGroupIds = _getAcceptedGroupIds(objectDefinitionId);

		if (acceptedGroupIds.isEmpty()) {
			return null;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ObjectEntryFolder objectEntryFolder = getObjectEntryFolder(
			themeDisplay.getCompanyId(),
			httpServletRequest.getAttribute(InfoDisplayWebKeys.INFO_ITEM));

		if (objectEntryFolder != null) {
			if (!acceptedGroupIds.contains(objectEntryFolder.getGroupId())) {
				return null;
			}

			return _getDepotEntriesJSONArray(
				List.of(objectEntryFolder.getGroupId()), httpServletRequest);
		}

		return _getDepotEntriesJSONArray(acceptedGroupIds, httpServletRequest);
	}

	private JSONArray _getDepotEntriesJSONArray(
		List<Long> groupIds, HttpServletRequest httpServletRequest) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Long groupId : groupIds) {
			JSONObject jsonObject = _getJSONObject(groupId, httpServletRequest);

			if (jsonObject != null) {
				jsonArray.put(jsonObject);
			}
		}

		return jsonArray;
	}

	private JSONObject _getJSONObject(
		long groupId, HttpServletRequest httpServletRequest) {

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return null;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return JSONUtil.put(
			"externalReferenceCode", group.getExternalReferenceCode()
		).put(
			"groupId", group.getGroupId()
		).put(
			"name", group.getName(themeDisplay.getLocale())
		);
	}

	private ObjectEntryFolder _getObjectEntryFolder(
		long companyId, Object object,
		String rootObjectEntryFolderExternalReferenceCode) {

		if (object instanceof DepotEntry) {
			DepotEntry depotEntry = (DepotEntry)object;

			return ObjectEntryFolderLocalServiceUtil.
				fetchObjectEntryFolderByExternalReferenceCode(
					rootObjectEntryFolderExternalReferenceCode,
					depotEntry.getGroupId(), companyId);
		}
		else if (object instanceof ObjectEntryFolder) {
			return (ObjectEntryFolder)object;
		}

		return null;
	}

	private boolean _hasAddEntryPermission(
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ObjectEntryFolder objectEntryFolder = getObjectEntryFolder(
			themeDisplay.getCompanyId(),
			httpServletRequest.getAttribute(InfoDisplayWebKeys.INFO_ITEM));

		if (objectEntryFolder == null) {
			return true;
		}

		try {
			return _objectEntryFolderModelResourcePermission.contains(
				themeDisplay.getPermissionChecker(),
				objectEntryFolder.getObjectEntryFolderId(),
				ActionKeys.ADD_ENTRY);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return false;
	}

	private boolean _isAcceptAllGroups(long objectDefinitionId) {
		ObjectDefinitionSetting objectDefinitionSetting =
			_objectDefinitionSettingLocalService.fetchObjectDefinitionSetting(
				objectDefinitionId,
				ObjectDefinitionSettingConstants.NAME_ACCEPT_ALL_GROUPS);

		if ((objectDefinitionSetting != null) &&
			GetterUtil.getBoolean(objectDefinitionSetting.getValue())) {

			return true;
		}

		objectDefinitionSetting =
			_objectDefinitionSettingLocalService.fetchObjectDefinitionSetting(
				objectDefinitionId,
				ObjectDefinitionSettingConstants.NAME_ACCEPTED_GROUP_IDS);

		if ((objectDefinitionSetting == null) ||
			Validator.isNull(objectDefinitionSetting.getValue())) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseSectionDisplayContextHelper.class);

	private static final List<Integer> _statuses = Arrays.asList(
		WorkflowConstants.STATUS_APPROVED, WorkflowConstants.STATUS_DRAFT,
		WorkflowConstants.STATUS_EXPIRED, WorkflowConstants.STATUS_PENDING,
		WorkflowConstants.STATUS_SCHEDULED);

	private final DepotEntryLocalService _depotEntryLocalService;
	private final GroupLocalService _groupLocalService;
	private final ObjectDefinitionSettingLocalService
		_objectDefinitionSettingLocalService;
	private final ModelResourcePermission<ObjectEntryFolder>
		_objectEntryFolderModelResourcePermission;

}