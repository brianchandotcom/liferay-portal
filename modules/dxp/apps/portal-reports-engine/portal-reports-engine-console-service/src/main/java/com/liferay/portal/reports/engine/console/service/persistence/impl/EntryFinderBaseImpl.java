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

package com.liferay.portal.reports.engine.console.service.persistence.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.reports.engine.console.model.Entry;
import com.liferay.portal.reports.engine.console.service.persistence.EntryPersistence;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class EntryFinderBaseImpl extends BasePersistenceImpl<Entry> {
	public EntryFinderBaseImpl() {
		setModelClass(Entry.class);
	}

	/**
	 * Returns the entry persistence.
	 *
	 * @return the entry persistence
	 */
	public EntryPersistence getEntryPersistence() {
		return entryPersistence;
	}

	/**
	 * Sets the entry persistence.
	 *
	 * @param entryPersistence the entry persistence
	 */
	public void setEntryPersistence(EntryPersistence entryPersistence) {
		this.entryPersistence = entryPersistence;
	}

	@BeanReference(type = EntryPersistence.class)
	protected EntryPersistence entryPersistence;
}