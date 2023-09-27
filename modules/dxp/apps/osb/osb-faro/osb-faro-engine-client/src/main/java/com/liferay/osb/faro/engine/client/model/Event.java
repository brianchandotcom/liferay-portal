/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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