/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.orm;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.model.BaseModel;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public class ModelRemovalThreadLocal {

	public static boolean isRemoving(BaseModel<?> baseModel) {
		BaseModel<?> removingBaseModel = _removingBaseModel.get();

		if ((removingBaseModel == null) ||
			(removingBaseModel.getModelClass() != baseModel.getModelClass())) {

			return false;
		}

		Serializable primaryKeyObj = removingBaseModel.getPrimaryKeyObj();

		return primaryKeyObj.equals(baseModel.getPrimaryKeyObj());
	}

	public static SafeCloseable setRemovingBaseModelWithSafeCloseable(
		BaseModel<?> baseModel) {

		return _removingBaseModel.setWithSafeCloseable(baseModel);
	}

	private static final CentralizedThreadLocal<BaseModel<?>>
		_removingBaseModel = new CentralizedThreadLocal<>(
			ModelRemovalThreadLocal.class + "._removingBaseModel");

}