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
public class IndividualSegmentMembershipChangeAggregation {

	public long getAddedIndividualsCount() {
		return _addedIndividualsCount;
	}

	public long getAnonymousIndividualsCount() {
		return _anonymousIndividualsCount;
	}

	public long getIndividualsCount() {
		return _individualsCount;
	}

	public Date getIntervalInitDate() {
		if (_intervalInitDate == null) {
			return null;
		}

		return new Date(_intervalInitDate.getTime());
	}

	public long getKnownIndividualsCount() {
		return _knownIndividualsCount;
	}

	public long getRemovedIndividualsCount() {
		return _removedIndividualsCount;
	}

	public void setAddedIndividualsCount(long addedIndividualsCount) {
		_addedIndividualsCount = addedIndividualsCount;
	}

	public void setAnonymousIndividualsCount(long anonymousIndividualsCount) {
		_anonymousIndividualsCount = anonymousIndividualsCount;
	}

	public void setIndividualsCount(long individualsCount) {
		_individualsCount = individualsCount;
	}

	public void setIntervalInitDate(Date intervalInitDate) {
		if (intervalInitDate != null) {
			_intervalInitDate = new Date(intervalInitDate.getTime());
		}
	}

	public void setKnownIndividualsCount(long knownIndividualsCount) {
		_knownIndividualsCount = knownIndividualsCount;
	}

	public void setRemovedIndividualsCount(long removedIndividualsCount) {
		_removedIndividualsCount = removedIndividualsCount;
	}

	private long _addedIndividualsCount;
	private long _anonymousIndividualsCount;
	private long _individualsCount;
	private Date _intervalInitDate;
	private long _knownIndividualsCount;
	private long _removedIndividualsCount;

}