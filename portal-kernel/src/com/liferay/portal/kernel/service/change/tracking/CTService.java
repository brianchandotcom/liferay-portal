/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.change.tracking;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * @author Preston Crary
 */
public interface CTService<T extends CTModel<T>> {

	@Transactional(enabled = false)
	public CTPersistence<T> getCTPersistence();

	@Transactional(enabled = false)
	public Class<T> getModelClass();

	@Transactional(rollbackFor = Throwable.class)
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<T>, R, E> updateUnsafeFunction)
		throws E;

}