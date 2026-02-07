/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.frontend.data.set.filter;

import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.RoleService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;

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

		long roleClassNameId = _classNameLocalService.getClassNameId(
			Role.class.getName());
		long userClassNameId = _classNameLocalService.getClassNameId(
			User.class.getName());

		return ListUtil.concat(
			TransformUtil.transform(
				_roleService.getRoles(
					_companyId, new int[] {RoleConstants.TYPE_DEPOT}),
				role -> new SelectionFDSFilterItem(
					role.getName(),
					_getValue(roleClassNameId, role.getRoleId()))),
			TransformUtil.transform(
				_userLocalService.searchBySocial(
					_companyId, _groupIds, null, null, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS),
				user -> new SelectionFDSFilterItem(
					user.getFullName(),
					_getValue(userClassNameId, user.getUserId()))));
	}

	@Override
	public boolean isAutocompleteEnabled() {
		return true;
	}

	private String _getValue(long classNameId, long classPK) {
		return StringBundler.concat(classNameId, StringPool.UNDERLINE, classPK);
	}

	private final ClassNameLocalService _classNameLocalService;
	private final long _companyId;
	private final long[] _groupIds;
	private final RoleService _roleService;
	private final UserLocalService _userLocalService;

}