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

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.exception.CPFriendlyURLLengthException;
import com.liferay.commerce.product.exception.DuplicateCPFriendlyURLEntryException;
import com.liferay.commerce.product.model.CPFriendlyURLEntry;
import com.liferay.commerce.product.service.base.CPFriendlyURLEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;

import java.util.List;

/**
 * @author Marco Leo
 */
public class CPFriendlyURLEntryLocalServiceImpl
	extends CPFriendlyURLEntryLocalServiceBaseImpl {

	@Override
	public CPFriendlyURLEntry addCPFriendlyURLEntry(
			long groupId, long companyId, Class<?> clazz, long classPK,
			String languageId, String urlTitle)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		return addCPFriendlyURLEntry(
			groupId, companyId, classNameId, classPK, languageId, urlTitle);
	}

	@Override
	public List<CPFriendlyURLEntry> getCPFriendlyURLEntries(
		long groupId, long companyId, long classNameId, long classPK) {

		return cpFriendlyURLEntryPersistence.findByG_C_C_C(
			groupId, companyId, classNameId, classPK);
	}

	@Override
	public CPFriendlyURLEntry addCPFriendlyURLEntry(
			long groupId, long companyId, long classNameId, long classPK,
			String languageId, String urlTitle)
		throws PortalException {

		String normalizedUrlTitle = FriendlyURLNormalizerUtil.normalize(
			urlTitle);

		validate(
			groupId, companyId, classNameId, classPK, languageId,
			normalizedUrlTitle);

		CPFriendlyURLEntry mainCPFriendlyURLEntry =
			cpFriendlyURLEntryPersistence.fetchByG_C_C_C_L_M(
				groupId, companyId, classNameId, classPK, languageId, true);

		if (mainCPFriendlyURLEntry != null) {
			mainCPFriendlyURLEntry.setMain(false);

			cpFriendlyURLEntryPersistence.update(mainCPFriendlyURLEntry);
		}

		CPFriendlyURLEntry oldCPFriendlyURLEntry =
			cpFriendlyURLEntryPersistence.fetchByG_C_C_C_L_U(
				groupId, companyId, classNameId, classPK, languageId,
				normalizedUrlTitle);

		if (oldCPFriendlyURLEntry != null) {
			oldCPFriendlyURLEntry.setMain(true);

			return cpFriendlyURLEntryPersistence.update(oldCPFriendlyURLEntry);
		}

		long cpFriendlyURLEntryId = counterLocalService.increment();

		CPFriendlyURLEntry cpFriendlyURLEntry = createCPFriendlyURLEntry(
			cpFriendlyURLEntryId);

		cpFriendlyURLEntry.setCompanyId(companyId);
		cpFriendlyURLEntry.setGroupId(groupId);
		cpFriendlyURLEntry.setClassNameId(classNameId);
		cpFriendlyURLEntry.setClassPK(classPK);
		cpFriendlyURLEntry.setLanguageId(languageId);
		cpFriendlyURLEntry.setUrlTitle(normalizedUrlTitle);
		cpFriendlyURLEntry.setMain(true);

		return cpFriendlyURLEntryPersistence.update(cpFriendlyURLEntry);
	}

	@Override
	public void deleteCPFriendlyURLEntry(
		long groupId, long companyId, Class<?> clazz, long classPK) {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		cpFriendlyURLEntryPersistence.removeByG_C_C_C(
			groupId, companyId, classNameId, classPK);
	}

	@Override
	public void validate(
			long groupId, long companyId, long classNameId, long classPK,
			String languageId, String urlTitle)
		throws PortalException {

		int maxLength = ModelHintsUtil.getMaxLength(
			CPFriendlyURLEntry.class.getName(), "urlTitle");

		if (urlTitle.length() > maxLength) {
			throw new CPFriendlyURLLengthException();
		}

		String normalizedUrlTitle = FriendlyURLNormalizerUtil.normalize(
			urlTitle);

		if (classPK > 0) {
			CPFriendlyURLEntry cpFriendlyURLEntry =
				cpFriendlyURLEntryPersistence.fetchByG_C_C_C_L_U(
					groupId, companyId, classNameId, classPK, languageId,
					normalizedUrlTitle);

			if (cpFriendlyURLEntry != null) {
				return;
			}
		}

		int count = cpFriendlyURLEntryPersistence.countByG_C_C_U(
			groupId, companyId, classNameId, normalizedUrlTitle);

		if (count > 0) {
			throw new DuplicateCPFriendlyURLEntryException();
		}
	}

}