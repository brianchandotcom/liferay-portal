/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence.change.tracking.helper;

import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.io.Serializable;

/**
 * @author Preston Crary
 */
public class CTPersistenceHelperUtil {

	public static <T extends CTModel<T>> boolean isInsert(T ctModel) {
		CTPersistenceHelper ctPersistenceHelper = _ctPersistenceHelper;

		if (ctPersistenceHelper == null) {
			return ctModel.isNew();
		}

		return ctPersistenceHelper.isInsert(ctModel);
	}

	public static <T extends CTModel<T>> boolean isProductionMode(
		Class<T> ctModelClass) {

		CTPersistenceHelper ctPersistenceHelper = _ctPersistenceHelper;

		if (ctPersistenceHelper == null) {
			return true;
		}

		return ctPersistenceHelper.isProductionMode(ctModelClass);
	}

	public static <T extends CTModel<T>> boolean isProductionMode(
		Class<T> ctModelClass, Serializable primaryKey) {

		CTPersistenceHelper ctPersistenceHelper = _ctPersistenceHelper;

		if (ctPersistenceHelper == null) {
			return true;
		}

		return ctPersistenceHelper.isProductionMode(ctModelClass, primaryKey);
	}

	public static <T extends CTModel<T>> boolean isRemove(T ctModel) {
		CTPersistenceHelper ctPersistenceHelper = _ctPersistenceHelper;

		if (ctPersistenceHelper == null) {
			return true;
		}

		return ctPersistenceHelper.isRemove(ctModel);
	}

	private CTPersistenceHelperUtil() {
	}

	private static volatile CTPersistenceHelper _ctPersistenceHelper =
		ServiceProxyFactory.newServiceTrackedInstance(
			CTPersistenceHelper.class, CTPersistenceHelperUtil.class,
			"_ctPersistenceHelper", false, true);

}