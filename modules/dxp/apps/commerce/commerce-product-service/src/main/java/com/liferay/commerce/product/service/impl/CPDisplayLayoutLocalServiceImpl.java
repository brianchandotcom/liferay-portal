/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.service.base.CPDisplayLayoutLocalServiceBaseImpl;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Marco Leo
 */
public class CPDisplayLayoutLocalServiceImpl
	extends CPDisplayLayoutLocalServiceBaseImpl {

	@Override
	public CPDisplayLayout addCPDisplayLayout(
		Class<?> clazz, long classPK, String layoutUuid,
		ServiceContext serviceContext) {

		long classNameId = classNameLocalService.getClassNameId(clazz);

		CPDisplayLayout oldCPDisplayLayout =
			cpDisplayLayoutPersistence.fetchByC_C(classNameId, classPK);

		if (oldCPDisplayLayout != null) {
			oldCPDisplayLayout.setLayoutUuid(layoutUuid);

			return cpDisplayLayoutPersistence.update(oldCPDisplayLayout);
		}

		long cpDisplayLayoutId = counterLocalService.increment();

		CPDisplayLayout cpDisplayLayout = createCPDisplayLayout(
			cpDisplayLayoutId);

		cpDisplayLayout.setCompanyId(serviceContext.getCompanyId());
		cpDisplayLayout.setGroupId(serviceContext.getScopeGroupId());
		cpDisplayLayout.setClassNameId(classNameId);
		cpDisplayLayout.setClassPK(classPK);
		cpDisplayLayout.setLayoutUuid(layoutUuid);

		return cpDisplayLayoutPersistence.update(cpDisplayLayout);
	}

	@Override
	public void deleteCPDisplayLayout(Class<?> clazz, long classPK) {
		long classNameId = classNameLocalService.getClassNameId(clazz);

		CPDisplayLayout cpDisplayLayout = cpDisplayLayoutPersistence.fetchByC_C(
			classNameId, classPK);

		if (cpDisplayLayout != null) {
			cpDisplayLayoutPersistence.remove(cpDisplayLayout);
		}
	}

	@Override
	public CPDisplayLayout fetchCPDisplayLayout(Class<?> clazz, long classPK) {
		long classNameId = classNameLocalService.getClassNameId(clazz);

		return cpDisplayLayoutPersistence.fetchByC_C(classNameId, classPK);
	}

}