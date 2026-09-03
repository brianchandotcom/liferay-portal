/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.site;

import com.liferay.exportimport.site.ExportImportSiteProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.staging.StagingGroupHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(service = ExportImportSiteProvider.class)
public class ExportImportSiteProviderImpl implements ExportImportSiteProvider {

	@Override
	public int getChildSiteCount(Group group) {
		if (group == null) {
			return 0;
		}

		return ListUtil.filter(
			_groupLocalService.getGroups(
				group.getCompanyId(), group.getGroupId(), true),
			this::isSupported
		).size();
	}

	@Override
	public String getDescriptiveName(Group group, Locale locale) {
		if (group == null) {
			return StringPool.BLANK;
		}

		try {
			return group.getDescriptiveName(locale);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return group.getName(locale);
		}
	}

	@Override
	public String getPath(Group group, Locale locale) {
		if (group == null) {
			return StringPool.BLANK;
		}

		if (group.isCompany()) {
			return getDescriptiveName(group, locale);
		}

		List<String> names = new ArrayList<>();

		names.add(
			getDescriptiveName(
				_groupLocalService.fetchCompanyGroup(group.getCompanyId()),
				locale));

		List<Group> ancestors = group.getAncestors();

		for (int i = ancestors.size() - 1; i >= 0; i--) {
			names.add(getDescriptiveName(ancestors.get(i), locale));
		}

		names.add(getDescriptiveName(group, locale));

		return StringUtil.merge(names, " / ");
	}

	@Override
	public List<Group> getSupportedSites(
			long companyId, String keywords,
			OrderByComparator<Group> orderByComparator)
		throws PortalException {

		return ListUtil.filter(
			_groupService.search(
				companyId, _getSupportedClassNameIds(), keywords,
				LinkedHashMapBuilder.<String, Object>put(
					"active", Boolean.TRUE
				).put(
					"site", Boolean.TRUE
				).build(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator),
			this::isSupported);
	}

	@Override
	public boolean isSupported(Group group) {
		if ((group != null) &&
			ArrayUtil.contains(
				_getSupportedClassNameIds(), group.getClassNameId()) &&
			group.isSite() && group.isActive() && !group.isCMS() &&
			!group.isDepot() && !group.isLayoutPrototype() &&
			!group.isLayoutSetPrototype() && !group.isStaged() &&
			!group.isStagingGroup() &&
			!_stagingGroupHelper.isCompanyGroup(group)) {

			return true;
		}

		return false;
	}

	private long[] _getSupportedClassNameIds() {
		return new long[] {
			_portal.getClassNameId(Company.class.getName()),
			_portal.getClassNameId(Group.class.getName())
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportSiteProviderImpl.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private GroupService _groupService;

	@Reference
	private Portal _portal;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

}