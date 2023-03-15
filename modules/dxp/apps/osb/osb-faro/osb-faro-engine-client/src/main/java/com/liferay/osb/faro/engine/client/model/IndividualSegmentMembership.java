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
public class IndividualSegmentMembership {

	public Date getDateCreated() {
		if (_dateCreated == null) {
			return null;
		}

		return new Date(_dateCreated.getTime());
	}

	public Date getDateRemoved() {
		if (_dateRemoved == null) {
			return null;
		}

		return new Date(_dateRemoved.getTime());
	}

	public String getIndividualId() {
		return _individualId;
	}

	public String getIndividualSegmentId() {
		return _individualSegmentId;
	}

	public String getStatus() {
		return _status;
	}

	public void setDateCreated(Date dateCreated) {
		if (dateCreated != null) {
			_dateCreated = new Date(dateCreated.getTime());
		}
	}

	public void setDateRemoved(Date dateRemoved) {
		if (dateRemoved != null) {
			_dateRemoved = new Date(dateRemoved.getTime());
		}
	}

	public void setIndividualId(String individualId) {
		_individualId = individualId;
	}

	public void setIndividualSegmentId(String individualSegmentId) {
		_individualSegmentId = individualSegmentId;
	}

	public void setStatus(String status) {
		_status = status;
	}

	public enum Status {

		ACTIVE, INACTIVE

	}

	private Date _dateCreated;
	private Date _dateRemoved;
	private String _individualId;
	private String _individualSegmentId;
	private String _status = Status.ACTIVE.name();

}