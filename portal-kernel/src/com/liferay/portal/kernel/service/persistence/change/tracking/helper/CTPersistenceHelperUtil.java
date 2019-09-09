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

package com.liferay.portal.kernel.service.persistence.change.tracking.helper;

import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

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