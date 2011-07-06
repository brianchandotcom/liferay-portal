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

package com.liferay.portlet.messageboards.model;

import com.liferay.portal.model.SaveableModel;

/**
 * The extended model interface for the MBThread service. Represents a row in the &quot;MBThread&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see MBThreadModel
 * @see com.liferay.portlet.messageboards.model.impl.MBThreadImpl
 * @see com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl
 * @generated
 */
public interface MBThread extends MBThreadModel, SaveableModel {
	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.messageboards.model.impl.MBThreadImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public java.lang.String getAttachmentsDir();

	public com.liferay.portal.model.Lock getLock();

	public boolean hasLock(long userId);

	public boolean isLocked();
}