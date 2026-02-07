/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.frontend.data.set.filter;

import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.RoleService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Fábio Alves
 */
public class AssigneeSelectionFDSFilter extends BaseSelectionFDSFilter {

	public AssigneeSelectionFDSFilter(
		ClassNameLocalService classNameLocalService, long companyId,
		long[] groupIds, RoleService roleService,
		UserLocalService userLocalService) {

		_classNameLocalService = classNameLocalService;
		_companyId = companyId;
		_groupIds = groupIds;
		_roleService = roleService;
		_userLocalService = userLocalService;
	}

	@Override
	public String getEntityFieldType() {
		return FDSEntityFieldTypes.STRING;
	}

	@Override
	public String getId() {
		return "cmpAssignTo";
	}

	@Override
	public String getLabel() {
		return "assignee";
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		List<SelectionFDSFilterItem> selectionFDSFilterItems =
			new ArrayList<>();

		long roleClassNameId = _classNameLocalService.getClassNameId(
			Role.class.getName());

		try {
			for (Role role :
					_roleService.getRoles(
						_companyId, new int[] {RoleConstants.TYPE_DEPOT})) {

				if (StringUtil.equals(
						DepotRolesConstants.ASSET_LIBRARY_CONNECTED_SITE_MEMBER,
						role.getName())) {

					continue;
				}

				selectionFDSFilterItems.add(
					new SelectionFDSFilterItem(
						role.getName(),
						_getAssigneeKeyValue(
							roleClassNameId, role.getRoleId())));
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		long userClassNameId = _classNameLocalService.getClassNameId(
			User.class.getName());

		for (User user :
				_userLocalService.searchBySocial(
					_companyId, _groupIds, null, null, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS)) {

			selectionFDSFilterItems.add(
				new SelectionFDSFilterItem(
					user.getFullName(),
					_getAssigneeKeyValue(userClassNameId, user.getUserId())));
		}

		return selectionFDSFilterItems;
	}

	@Override
	public boolean isAutocompleteEnabled() {
		return true;
	}

	private String _getAssigneeKeyValue(long classNameId, long classPK) {
		return StringBundler.concat(classNameId, StringPool.UNDERLINE, classPK);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssigneeSelectionFDSFilter.class);

	private final ClassNameLocalService _classNameLocalService;
	private final long _companyId;
	private final long[] _groupIds;
	private final RoleService _roleService;
	private final UserLocalService _userLocalService;

}