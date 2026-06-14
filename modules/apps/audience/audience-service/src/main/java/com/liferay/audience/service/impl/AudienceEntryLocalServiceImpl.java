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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

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
			String externalReferenceCode, String json, String name,
			ServiceContext serviceContext)
		throws PortalException {

		_validate(name);

		AudienceEntry audienceEntry = audienceEntryPersistence.create(
			counterLocalService.increment());

		audienceEntry.setExternalReferenceCode(externalReferenceCode);

		User user = _userLocalService.getUser(serviceContext.getUserId());

		audienceEntry.setCompanyId(user.getCompanyId());
		audienceEntry.setUserId(user.getUserId());
		audienceEntry.setUserName(user.getFullName());

		audienceEntry.setJSON(json);
		audienceEntry.setName(name);

		return audienceEntryPersistence.update(audienceEntry);
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
			long audienceEntryId, String json, String name)
		throws PortalException {

		_validate(name);

		AudienceEntry audienceEntry = audienceEntryPersistence.findByPrimaryKey(
			audienceEntryId);

		audienceEntry.setJSON(json);
		audienceEntry.setName(name);

		return audienceEntryPersistence.update(audienceEntry);
	}

	private void _validate(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new AudienceEntryNameException();
		}
	}

	@Reference
	private CustomSQL _customSQL;

	@Reference
	private UserLocalService _userLocalService;

}