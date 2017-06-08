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

package com.liferay.portal.workflow.kaleo.service.persistence.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoTaskInstanceTokenPersistence;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KaleoTaskInstanceTokenFinderBaseImpl extends BasePersistenceImpl<KaleoTaskInstanceToken> {
	/**
	 * Returns the kaleo task instance token persistence.
	 *
	 * @return the kaleo task instance token persistence
	 */
	public KaleoTaskInstanceTokenPersistence getKaleoTaskInstanceTokenPersistence() {
		return kaleoTaskInstanceTokenPersistence;
	}

	/**
	 * Sets the kaleo task instance token persistence.
	 *
	 * @param kaleoTaskInstanceTokenPersistence the kaleo task instance token persistence
	 */
	public void setKaleoTaskInstanceTokenPersistence(
		KaleoTaskInstanceTokenPersistence kaleoTaskInstanceTokenPersistence) {
		this.kaleoTaskInstanceTokenPersistence = kaleoTaskInstanceTokenPersistence;
	}

	@BeanReference(type = KaleoTaskInstanceTokenPersistence.class)
	protected KaleoTaskInstanceTokenPersistence kaleoTaskInstanceTokenPersistence;
}