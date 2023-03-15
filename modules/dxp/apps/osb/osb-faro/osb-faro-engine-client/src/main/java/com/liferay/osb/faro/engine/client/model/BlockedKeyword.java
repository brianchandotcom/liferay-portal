/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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