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

import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.service.base.CPDisplayLayoutLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class CPDisplayLayoutLocalServiceImpl
	extends CPDisplayLayoutLocalServiceBaseImpl {

	@Override
	public CPDisplayLayout addCPDisplayLayout(
			long groupId, long companyId, Class<?> clazz, long classPK,
			String layoutUuid)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		return addCPDisplayLayout(
			groupId, companyId, classNameId, classPK, layoutUuid);
	}

	@Override
	public CPDisplayLayout addCPDisplayLayout(
			long groupId, long companyId, long classNameId, long classPK,
			String layoutUuid)
		throws PortalException {

		CPDisplayLayout oldCPDisplayLayout =
			cpDisplayLayoutPersistence.fetchByC_C(classNameId, classPK);

		if (oldCPDisplayLayout != null) {
			oldCPDisplayLayout.setLayoutUuid(layoutUuid);
			return cpDisplayLayoutPersistence.update(oldCPDisplayLayout);
		}

		long cpDisplayLayoutId = counterLocalService.increment();

		CPDisplayLayout cpDisplayLayout = createCPDisplayLayout(
			cpDisplayLayoutId);

		cpDisplayLayout.setCompanyId(companyId);
		cpDisplayLayout.setGroupId(groupId);
		cpDisplayLayout.setClassNameId(classNameId);
		cpDisplayLayout.setClassPK(classPK);
		cpDisplayLayout.setLayoutUuid(layoutUuid);

		return cpDisplayLayoutPersistence.update(cpDisplayLayout);
	}

	@Override
	public void deleteCPDisplayLayout(
			long companyId, Class<?> clazz, long classPK)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		cpDisplayLayoutPersistence.removeByC_C(classNameId, classPK);
	}

	@Override
	public CPDisplayLayout getCPDisplayLayout(
			long companyId, long classNameId, long classPK)
		throws PortalException {

		return cpDisplayLayoutPersistence.findByC_C(classNameId, classPK);
	}

}