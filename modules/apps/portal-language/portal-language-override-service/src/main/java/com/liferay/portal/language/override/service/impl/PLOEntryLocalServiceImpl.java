/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.language.override.service.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.language.override.model.PLOEntry;
import com.liferay.portal.language.override.service.base.PLOEntryLocalServiceBaseImpl;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Drew Brokke
 */
@Component(
	property = "model.class.name=com.liferay.portal.language.override.model.PLOEntry",
	service = AopService.class
)
public class PLOEntryLocalServiceImpl extends PLOEntryLocalServiceBaseImpl {

	@Override
	public PLOEntry addOrUpdatePLOEntry(
			long companyId, long userId, String key, String languageId,
			String value)
		throws PortalException {

		PLOEntry ploEntry = fetchPLOEntry(companyId, key, languageId);

		if (ploEntry == null) {
			ploEntry = createPLOEntry(counterLocalService.increment());

			ploEntry.setCompanyId(companyId);

			User user = _userLocalService.getUser(userId);

			ploEntry.setUserId(user.getUserId());

			ploEntry.setKey(key);
			ploEntry.setLanguageId(languageId);
			ploEntry.setValue(value);

			return addPLOEntry(ploEntry);
		}

		if (Objects.equals(ploEntry.getValue(), value)) {
			return ploEntry;
		}

		ploEntry.setValue(value);

		return updatePLOEntry(ploEntry);
	}

	@Override
	public void deletePLOEntries(long companyId, String key) {
		ploEntryPersistence.removeByC_K(companyId, key);
	}

	@Override
	public PLOEntry deletePLOEntry(
		long companyId, String key, String languageId) {

		PLOEntry ploEntry = fetchPLOEntry(companyId, key, languageId);

		if (ploEntry == null) {
			return null;
		}

		return deletePLOEntry(ploEntry);
	}

	@Override
	public PLOEntry fetchPLOEntry(
		long companyId, String key, String languageId) {

		return ploEntryPersistence.fetchByC_K_L(companyId, key, languageId);
	}

	@Override
	public List<PLOEntry> getPLOEntries(long companyId) {
		return ploEntryPersistence.findByCompanyId(companyId);
	}

	@Override
	public List<PLOEntry> getPLOEntriesByLanguageId(
		long companyId, String languageId) {

		return ploEntryPersistence.findByC_L(companyId, languageId);
	}

	@Override
	public void setPLOEntries(
			long companyId, long userId, String key,
			Map<Locale, String> localizationMap)
		throws PortalException {

		for (Map.Entry<Locale, String> entry : localizationMap.entrySet()) {
			String languageId = LanguageUtil.getLanguageId(entry.getKey());
			String value = StringUtil.trim(entry.getValue());

			if ((value == null) || value.equals(StringPool.BLANK)) {
				deletePLOEntry(companyId, key, languageId);
			}
			else {
				addOrUpdatePLOEntry(companyId, userId, key, languageId, value);
			}
		}
	}

	@Reference
	private UserLocalService _userLocalService;

}