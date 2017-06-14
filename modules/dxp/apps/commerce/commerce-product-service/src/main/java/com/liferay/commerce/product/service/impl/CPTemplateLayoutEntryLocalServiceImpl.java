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

import com.liferay.commerce.product.model.CPTemplateLayoutEntry;
import com.liferay.commerce.product.service.base.CPTemplateLayoutEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class CPTemplateLayoutEntryLocalServiceImpl
	extends CPTemplateLayoutEntryLocalServiceBaseImpl {

	@Override
	public CPTemplateLayoutEntry addCPTemplateLayoutEntry(
			long groupId, long companyId, Class<?> clazz, long classPK,
			String layoutUuid)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		return addCPTemplateLayoutEntry(
			groupId, companyId, classNameId, classPK, layoutUuid);
	}

	@Override
	public CPTemplateLayoutEntry addCPTemplateLayoutEntry(
			long groupId, long companyId, long classNameId, long classPK,
			String layoutUuid)
		throws PortalException {

		CPTemplateLayoutEntry oldCPTemplateLayoutEntry =
			cpTemplateLayoutEntryPersistence.fetchByG_C_C(
				groupId, classNameId, classPK);

		if (oldCPTemplateLayoutEntry != null) {
			oldCPTemplateLayoutEntry.setLayoutUuid(layoutUuid);
			return cpTemplateLayoutEntryPersistence.update(
				oldCPTemplateLayoutEntry);
		}

		long cpTemplateLayoutEntryId = counterLocalService.increment();

		CPTemplateLayoutEntry cpTemplateLayoutEntry =
			createCPTemplateLayoutEntry(cpTemplateLayoutEntryId);

		cpTemplateLayoutEntry.setCompanyId(companyId);
		cpTemplateLayoutEntry.setGroupId(groupId);
		cpTemplateLayoutEntry.setClassNameId(classNameId);
		cpTemplateLayoutEntry.setClassPK(classPK);
		cpTemplateLayoutEntry.setLayoutUuid(layoutUuid);

		return cpTemplateLayoutEntryPersistence.update(cpTemplateLayoutEntry);
	}

	@Override
	public void deleteCPTemplateLayoutEntry(
			long groupId, long companyId, Class<?> clazz, long classPK)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		cpTemplateLayoutEntryPersistence.removeByG_C_C(
			groupId, classNameId, classPK);
	}

	@Override
	public CPTemplateLayoutEntry getCPTemplateLayoutEntry(
			long groupId, long companyId, long classNameId, long classPK)
		throws PortalException {

		return cpTemplateLayoutEntryPersistence.findByG_C_C(
			groupId, classNameId, classPK);
	}

}