/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.frontend.data.set.filter;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.site.cms.site.initializer.internal.constants.CMSSiteInitializerFDSNames;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	property = {
		"frontend.data.set.name=" + CMSSiteInitializerFDSNames.ALL_SECTION,
		"frontend.data.set.name=" + CMSSiteInitializerFDSNames.CONTENTS_SECTION,
		"frontend.data.set.name=" + CMSSiteInitializerFDSNames.FILES_SECTION,
		"frontend.data.set.name=" + CMSSiteInitializerFDSNames.RECYCLE_BIN_SECTION,
		"frontend.data.set.name=" + CMSSiteInitializerFDSNames.VIEW_CONTENTS_FOLDER,
		"frontend.data.set.name=" + CMSSiteInitializerFDSNames.VIEW_FILES_FOLDER,
		"service.ranking:Integer=96"
	},
	service = FDSFilter.class
)
public class AuthorSelectionFDSFilter extends BaseSelectionFDSFilter {

	@Override
	public String getEntityFieldType() {
		return FDSEntityFieldTypes.INTEGER;
	}

	@Override
	public String getId() {
		return "creatorId";
	}

	@Override
	public String getLabel() {
		return "author";
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		List<SelectionFDSFilterItem> selectionFDSFilterItems =
			new ArrayList<>();

		long[] depotEntryGroupIds = _getDepotEntryGroupIds();

		Set<User> userSet = new HashSet<>(_getUsers(depotEntryGroupIds, null));

		userSet.addAll(_getUsers(null, _getUserGroupIds(depotEntryGroupIds)));

		if (userSet.isEmpty()) {
			return selectionFDSFilterItems;
		}

		List<User> userList = new ArrayList<>(userSet);

		userList.sort(Comparator.comparing(User::getFullName));

		for (User user : userList) {
			selectionFDSFilterItems.add(
				new SelectionFDSFilterItem(
					user.getFullName(), user.getUserId()));
		}

		return selectionFDSFilterItems;
	}

	@Override
	public boolean isAutocompleteEnabled() {
		return true;
	}

	private long[] _getDepotEntryGroupIds() {
		List<Long> groupIds = new ArrayList<>();

		for (long groupId :
				_depotEntryLocalService.getDepotEntryGroupIds(
					CompanyThreadLocal.getCompanyId(),
					DepotConstants.TYPE_SPACE)) {

			if (_isAssetLibraryAdminOrAssetLibraryMember(groupId)) {
				groupIds.add(groupId);
			}
		}

		return ArrayUtil.toLongArray(groupIds);
	}

	private long[] _getUserGroupIds(long[] groupIds) {
		Set<Long> userGroupIds = new HashSet<>();

		for (long groupId : groupIds) {
			userGroupIds.addAll(
				TransformUtil.transform(
					_userGroupLocalService.getGroupUserGroups(groupId),
					UserGroup::getUserGroupId));
		}

		return ArrayUtil.toLongArray(userGroupIds);
	}

	private List<User> _getUsers(long[] groupIds, long[] userGroupIds) {
		return _userLocalService.searchBySocial(
			CompanyThreadLocal.getCompanyId(), groupIds, userGroupIds, null,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	private boolean _isAssetLibraryAdminOrAssetLibraryMember(long groupId) {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker.isGroupAdmin(groupId)) {
			return true;
		}

		return _groupLocalService.hasUserGroup(
			PrincipalThreadLocal.getUserId(), groupId);
	}

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserLocalService _userLocalService;

}