/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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