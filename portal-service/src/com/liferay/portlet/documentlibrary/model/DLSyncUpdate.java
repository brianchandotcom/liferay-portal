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

import com.liferay.portal.kernel.json.JSON;

import java.util.Date;
import java.util.List;

/**
 * @author Michael Young
 */
@JSON
public class DLSyncUpdate {

	public DLSyncUpdate(List<DLSync> dlSyncs, Date lastAccessDate) {
		_dlSyncs = dlSyncs;
		_lastAccessDate = lastAccessDate;
	}

	@JSON
	public List<DLSync> getDLSyncs() {
		return _dlSyncs;
	}

	public Date getLastAccessDate() {
		return _lastAccessDate;
	}

	private List<DLSync> _dlSyncs;
	private Date _lastAccessDate = new Date();

}