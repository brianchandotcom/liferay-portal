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
 * @author Matthew Kong
 */
public class Event {

	public Date getEndDate() {
		if (_endDate == null) {
			return null;
		}

		return new Date(_endDate.getTime());
	}

	public PostalAddress getLocation() {
		return _location;
	}

	public String getName() {
		return _name;
	}

	public String getSameAs() {
		return _sameAs;
	}

	public Date getStartDate() {
		if (_startDate == null) {
			return null;
		}

		return new Date(_startDate.getTime());
	}

	public void setEndDate(Date endDate) {
		if (endDate != null) {
			_endDate = new Date(endDate.getTime());
		}
	}

	public void setLocation(PostalAddress location) {
		_location = location;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setSameAs(String sameAs) {
		_sameAs = sameAs;
	}

	public void setStartDate(Date startDate) {
		if (startDate != null) {
			_startDate = new Date(startDate.getTime());
		}
	}

	private Date _endDate;
	private PostalAddress _location;
	private String _name;
	private String _sameAs;
	private Date _startDate;

}