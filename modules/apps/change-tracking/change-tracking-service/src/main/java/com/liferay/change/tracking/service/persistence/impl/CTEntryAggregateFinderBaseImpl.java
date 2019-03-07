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

package com.liferay.change.tracking.service.persistence.impl;

import com.liferay.change.tracking.model.CTEntryAggregate;
import com.liferay.change.tracking.service.persistence.CTEntryAggregatePersistence;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CTEntryAggregateFinderBaseImpl
	extends BasePersistenceImpl<CTEntryAggregate> {

	public CTEntryAggregateFinderBaseImpl() {
		setModelClass(CTEntryAggregate.class);
	}

	/**
	 * Returns the ct entry aggregate persistence.
	 *
	 * @return the ct entry aggregate persistence
	 */
	public CTEntryAggregatePersistence getCTEntryAggregatePersistence() {
		return ctEntryAggregatePersistence;
	}

	/**
	 * Sets the ct entry aggregate persistence.
	 *
	 * @param ctEntryAggregatePersistence the ct entry aggregate persistence
	 */
	public void setCTEntryAggregatePersistence(
		CTEntryAggregatePersistence ctEntryAggregatePersistence) {

		this.ctEntryAggregatePersistence = ctEntryAggregatePersistence;
	}

	@BeanReference(type = CTEntryAggregatePersistence.class)
	protected CTEntryAggregatePersistence ctEntryAggregatePersistence;

}