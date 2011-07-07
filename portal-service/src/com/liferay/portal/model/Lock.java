/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

/**
 * The extended model interface for the Lock service. Represents a row in the &quot;Lock_&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see LockModel
 * @see com.liferay.portal.model.impl.LockImpl
 * @see com.liferay.portal.model.impl.LockModelImpl
 * @generated
 */
public interface Lock extends LockModel, SaveableModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.LockImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public long getExpirationTime();

	public boolean isExpired();

	public boolean isNeverExpires();
}