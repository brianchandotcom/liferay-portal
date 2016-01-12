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

package com.liferay.portlet.calendar.service.persistence.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.service.persistence.CalEventPersistence;

import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CalEventFinderBaseImpl extends BasePersistenceImpl<CalEvent> {
	@Override
	public Set<String> getBadColumnNames() {
		return getCalEventPersistence().getBadColumnNames();
	}

	/**
	 * Returns the cal event persistence.
	 *
	 * @return the cal event persistence
	 */
	public CalEventPersistence getCalEventPersistence() {
		return calEventPersistence;
	}

	/**
	 * Sets the cal event persistence.
	 *
	 * @param calEventPersistence the cal event persistence
	 */
	public void setCalEventPersistence(CalEventPersistence calEventPersistence) {
		this.calEventPersistence = calEventPersistence;
	}

	@BeanReference(type = CalEventPersistence.class)
	protected CalEventPersistence calEventPersistence;
}