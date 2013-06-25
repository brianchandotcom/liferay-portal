/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the SystemEvent service. Represents a row in the &quot;SystemEvent&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see SystemEventModel
 * @see com.liferay.portal.model.impl.SystemEventImpl
 * @see com.liferay.portal.model.impl.SystemEventModelImpl
 * @generated
 */
public interface SystemEvent extends SystemEventModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.SystemEventImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SystemEvent, Long> SYSTEM_EVENT_ID_ACCESSOR = new Accessor<SystemEvent, Long>() {
			@Override
			public Long get(SystemEvent systemEvent) {
				return systemEvent.getSystemEventId();
			}
		};

	public java.lang.String getReferrerClassName();

	public void setReferrerClassName(java.lang.String referrerClassName);
}