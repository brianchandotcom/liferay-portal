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

package com.liferay.journal.service.persistence.impl;

import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.persistence.JournalFolderPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class JournalFolderFinderBaseImpl extends BasePersistenceImpl<JournalFolder> {
	/**
	 * Returns the journal folder persistence.
	 *
	 * @return the journal folder persistence
	 */
	public JournalFolderPersistence getJournalFolderPersistence() {
		return journalFolderPersistence;
	}

	/**
	 * Sets the journal folder persistence.
	 *
	 * @param journalFolderPersistence the journal folder persistence
	 */
	public void setJournalFolderPersistence(
		JournalFolderPersistence journalFolderPersistence) {
		this.journalFolderPersistence = journalFolderPersistence;
	}

	@Override
	protected Set<String> getBadColumnNames() {
		return getJournalFolderPersistence().getBadColumnNames();
	}

	@BeanReference(type = JournalFolderPersistence.class)
	protected JournalFolderPersistence journalFolderPersistence;
}