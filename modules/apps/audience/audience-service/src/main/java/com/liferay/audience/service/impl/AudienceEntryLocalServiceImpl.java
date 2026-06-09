/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.service.impl;

import com.liferay.audience.exception.AudienceEntryNameException;
import com.liferay.audience.model.AudienceEntry;
import com.liferay.audience.service.base.AudienceEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.audience.model.AudienceEntry",
	service = AopService.class
)
public class AudienceEntryLocalServiceImpl
	extends AudienceEntryLocalServiceBaseImpl {

	@Override
	public AudienceEntry addAudienceEntry(
			String externalReferenceCode, String json,
			Map<Locale, String> nameMap, ServiceContext serviceContext)
		throws PortalException {

		// Audience entry

		_validateName(nameMap);

		AudienceEntry audienceEntry = audienceEntryPersistence.create(
			counterLocalService.increment());

		audienceEntry.setUuid(serviceContext.getUuid());
		audienceEntry.setExternalReferenceCode(externalReferenceCode);

		User user = _userLocalService.getUser(serviceContext.getUserId());

		audienceEntry.setCompanyId(user.getCompanyId());
		audienceEntry.setUserId(user.getUserId());
		audienceEntry.setUserName(user.getFullName());

		audienceEntry.setCreateDate(serviceContext.getCreateDate(new Date()));
		audienceEntry.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		audienceEntry.setJSON(json);
		audienceEntry.setNameMap(nameMap);

		audienceEntry = audienceEntryPersistence.update(audienceEntry);

		// Resources

		_resourceLocalService.addModelResources(audienceEntry, serviceContext);

		return audienceEntry;
	}

	@Override
	public AudienceEntry deleteAudienceEntry(AudienceEntry audienceEntry)
		throws PortalException {

		// Audience entry

		audienceEntryPersistence.remove(audienceEntry);

		// Resources

		_resourceLocalService.deleteResource(
			audienceEntry, ResourceConstants.SCOPE_INDIVIDUAL);

		return audienceEntry;
	}

	@Override
	public AudienceEntry deleteAudienceEntry(long audienceEntryId)
		throws PortalException {

		AudienceEntry audienceEntry = audienceEntryPersistence.findByPrimaryKey(
			audienceEntryId);

		return audienceEntryLocalService.deleteAudienceEntry(audienceEntry);
	}

	@Override
	public List<AudienceEntry> getAudienceEntries(
		long companyId, int start, int end,
		OrderByComparator<AudienceEntry> orderByComparator) {

		return audienceEntryPersistence.findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public List<AudienceEntry> getAudienceEntries(
			long companyId, String name, int start, int end,
			OrderByComparator<AudienceEntry> orderByComparator)
		throws PortalException {

		return audienceEntryPersistence.findByC_LikeN(
			companyId,
			_customSQL.keywords(name, false, WildcardMode.SURROUND)[0], start,
			end, orderByComparator);
	}

	@Override
	public int getAudienceEntriesCount(long companyId) {
		return audienceEntryPersistence.countByCompanyId(companyId);
	}

	@Override
	public int getAudienceEntriesCount(long companyId, String name) {
		return audienceEntryPersistence.countByC_LikeN(
			companyId,
			_customSQL.keywords(name, false, WildcardMode.SURROUND)[0]);
	}

	@Override
	public AudienceEntry updateAudienceEntry(
			long audienceEntryId, String json, Map<Locale, String> nameMap,
			ServiceContext serviceContext)
		throws PortalException {

		// Audience entry

		AudienceEntry audienceEntry = audienceEntryPersistence.findByPrimaryKey(
			audienceEntryId);

		_validateName(nameMap);

		audienceEntry.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		audienceEntry.setJSON(json);
		audienceEntry.setNameMap(nameMap);

		return audienceEntryPersistence.update(audienceEntry);
	}

	private void _validateName(Map<Locale, String> nameMap)
		throws PortalException {

		Locale defaultLocale = LocaleUtil.getDefault();

		if (nameMap.isEmpty() || Validator.isNull(nameMap.get(defaultLocale))) {
			throw new AudienceEntryNameException(
				"Name is null for locale " + defaultLocale.getDisplayName());
		}
	}

	@Reference
	private CustomSQL _customSQL;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}