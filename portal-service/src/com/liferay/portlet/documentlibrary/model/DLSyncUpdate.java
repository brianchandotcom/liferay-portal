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

package com.liferay.portlet.documentlibrary.model;

import java.util.Collection;
import java.util.Date;

/**
 * @author Michael Young
 */
public class DLSyncUpdate {

	public DLSyncUpdate(Collection<DLSync> dlSyncs, Date lastAccessDate) {
		_dlSyncs = dlSyncs;
		_lastAccessDate = lastAccessDate;
	}

	public Collection<DLSync> getDLSyncs() {
		return _dlSyncs;
	}

	public Date getLastAccessDate() {
		return _lastAccessDate;
	}

	private Collection<DLSync> _dlSyncs;
	private Date _lastAccessDate = new Date();

}