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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portlet.trash.model.TrashEntry;

/**
 * @author Zsolt Berentey
 */
public interface TrashedModel {

	public int getStatus();

	public TrashEntry getTrashEntry() throws PortalException, SystemException;

	public long getTrashEntryClassPK();

	public TrashHandler getTrashHandler();

	public boolean isInTrash();

	public boolean isInTrashContainer();

}