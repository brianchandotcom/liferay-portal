/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.service.impl;

import com.liferay.audience.constants.AudienceActionKeys;
import com.liferay.audience.constants.AudienceConstants;
import com.liferay.audience.model.AudienceEntry;
import com.liferay.audience.service.base.AudienceEntryServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=audience",
		"json.web.service.context.path=AudienceEntry"
	},
	service = AopService.class
)
public class AudienceEntryServiceImpl extends AudienceEntryServiceBaseImpl {

	@Override
	public AudienceEntry addAudienceEntry(
			String externalReferenceCode, String json,
			Map<Locale, String> nameMap, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			AudienceActionKeys.MANAGE_AUDIENCE_ENTRIES);

		return audienceEntryLocalService.addAudienceEntry(
			externalReferenceCode, json, nameMap, serviceContext);
	}

	@Override
	public AudienceEntry deleteAudienceEntry(long audienceEntryId)
		throws PortalException {

		_audienceEntryResourcePermission.check(
			getPermissionChecker(), audienceEntryId, ActionKeys.DELETE);

		return audienceEntryLocalService.deleteAudienceEntry(audienceEntryId);
	}

	@Override
	public List<AudienceEntry> getAudienceEntries(
		long companyId, int start, int end,
		OrderByComparator<AudienceEntry> orderByComparator) {

		return audienceEntryPersistence.filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public List<AudienceEntry> getAudienceEntries(
		long companyId, String name, int start, int end,
		OrderByComparator<AudienceEntry> orderByComparator) {

		return audienceEntryPersistence.filterFindByC_LikeN(
			companyId,
			_customSQL.keywords(name, false, WildcardMode.SURROUND)[0], start,
			end, orderByComparator);
	}

	@Override
	public int getAudienceEntriesCount(long companyId) {
		return audienceEntryPersistence.filterCountByCompanyId(companyId);
	}

	@Override
	public int getAudienceEntriesCount(long companyId, String name) {
		return audienceEntryPersistence.filterCountByC_LikeN(
			companyId,
			_customSQL.keywords(name, false, WildcardMode.SURROUND)[0]);
	}

	@Override
	public AudienceEntry getAudienceEntry(long audienceEntryId)
		throws PortalException {

		_audienceEntryResourcePermission.check(
			getPermissionChecker(), audienceEntryId, ActionKeys.VIEW);

		return audienceEntryLocalService.getAudienceEntry(audienceEntryId);
	}

	@Override
	public AudienceEntry updateAudienceEntry(
			long audienceEntryId, String json, Map<Locale, String> nameMap,
			ServiceContext serviceContext)
		throws PortalException {

		_audienceEntryResourcePermission.check(
			getPermissionChecker(), audienceEntryId, ActionKeys.UPDATE);

		return audienceEntryLocalService.updateAudienceEntry(
			audienceEntryId, json, nameMap, serviceContext);
	}

	@Reference(
		target = "(model.class.name=com.liferay.audience.model.AudienceEntry)"
	)
	private ModelResourcePermission<AudienceEntry>
		_audienceEntryResourcePermission;

	@Reference
	private CustomSQL _customSQL;

	@Reference(
		target = "(resource.name=" + AudienceConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}