/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import java.util.Date;

/**
 * @author Marcellus Tavares
 */
public class BlockedKeyword {

	public Date getCreateDate() {
		if (_createDate == null) {
			return null;
		}

		return new Date(_createDate.getTime());
	}

	public String getId() {
		return _id;
	}

	public String getKeyword() {
		return _keyword;
	}

	public boolean isDuplicate() {
		return _duplicate;
	}

	public void setCreateDate(Date createDate) {
		if (createDate != null) {
			_createDate = new Date(createDate.getTime());
		}
	}

	public void setDuplicate(boolean duplicate) {
		_duplicate = duplicate;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setKeyword(String keyword) {
		_keyword = keyword;
	}

	private Date _createDate;
	private boolean _duplicate;
	private String _id;
	private String _keyword;

}