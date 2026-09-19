/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.group;

import com.liferay.exportimport.group.ExportImportGroupHelper;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(service = ExportImportGroupHelper.class)
public class ExportImportGroupHelperImpl implements ExportImportGroupHelper {

	@Override
	public int getChildGroupCount(Group group) throws PortalException {
		List<Group> groups = ListUtil.filter(
			_groupService.getGroups(
				group.getCompanyId(), group.getGroupId(), true),
			this::isGroupSupported);

		return groups.size();
	}

	@Override
	public String getGroupPath(Group group, Locale locale)
		throws PortalException {

		List<Group> ancestorGroups = group.getAncestors();

		StringBundler sb = new StringBundler((ancestorGroups.size() * 2) + 1);

		Collections.reverse(ancestorGroups);

		for (Group ancestorGroup : ancestorGroups) {
			sb.append(ancestorGroup.getDescriptiveName(locale));
			sb.append(" / ");
		}

		sb.append(group.getDescriptiveName(locale));

		return sb.toString();
	}

	@Override
	public List<Group> getSupportedGroups(
			long companyId, String keywords,
			OrderByComparator<Group> orderByComparator)
		throws PortalException {

		return ListUtil.filter(
			_groupService.search(
				companyId,
				new long[] {
					_portal.getClassNameId(Company.class.getName()),
					_portal.getClassNameId(Group.class.getName())
				},
				keywords,
				LinkedHashMapBuilder.<String, Object>put(
					"active", Boolean.TRUE
				).put(
					"site", Boolean.TRUE
				).build(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator),
			this::isGroupSupported);
	}

	@Override
	public boolean isGroupSupported(Group group) {
		if (group.isActive() && (group.isCompany() || group.isRegularSite()) &&
			group.isSite() && !group.isStaged() && !group.isStagingGroup()) {

			return true;
		}

		return false;
	}

	@Reference
	private GroupService _groupService;

	@Reference
	private Portal _portal;

}