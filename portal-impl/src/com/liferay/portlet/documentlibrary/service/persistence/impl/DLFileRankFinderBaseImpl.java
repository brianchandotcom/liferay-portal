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

package com.liferay.portlet.documentlibrary.service.persistence.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPersistence;

import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLFileRankFinderBaseImpl extends BasePersistenceImpl<DLFileRank> {
	/**
	 * Returns the document library file rank persistence.
	 *
	 * @return the document library file rank persistence
	 */
	public DLFileRankPersistence getDLFileRankPersistence() {
		return dlFileRankPersistence;
	}

	/**
	 * Sets the document library file rank persistence.
	 *
	 * @param dlFileRankPersistence the document library file rank persistence
	 */
	public void setDLFileRankPersistence(
		DLFileRankPersistence dlFileRankPersistence) {
		this.dlFileRankPersistence = dlFileRankPersistence;
	}

	@Override
	protected Set<String> getBadColumnNames() {
		return getDLFileRankPersistence().getBadColumnNames();
	}

	@BeanReference(type = DLFileRankPersistence.class)
	protected DLFileRankPersistence dlFileRankPersistence;
}