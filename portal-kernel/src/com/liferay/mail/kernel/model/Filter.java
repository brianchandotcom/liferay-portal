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

package com.liferay.mail.kernel.model;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public class Filter implements Serializable {

	public Filter() {
	}

	public Filter(String emailAddress, String folder) {
		_emailAddress = emailAddress;
		_folder = folder;
	}

	public String getEmailAddress() {
		return _emailAddress;
	}

	public String getFolder() {
		return _folder;
	}

	public void setEmailAddress(String emailAddress) {
		_emailAddress = emailAddress;
	}

	public void setFolder(String folder) {
		_folder = folder;
	}

	private String _emailAddress;
	private String _folder;

}