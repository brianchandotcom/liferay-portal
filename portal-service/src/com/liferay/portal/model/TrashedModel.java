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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.trash.kernel.model.TrashEntry;

/**
 * @author Zsolt Berentey
 */
@ProviderType
public interface TrashedModel {

	public int getStatus();

	public TrashEntry getTrashEntry() throws PortalException;

	public long getTrashEntryClassPK();

	public TrashHandler getTrashHandler();

	public boolean isInTrash();

	public boolean isInTrashContainer();

	public boolean isInTrashExplicitly();

	public boolean isInTrashImplicitly();

}