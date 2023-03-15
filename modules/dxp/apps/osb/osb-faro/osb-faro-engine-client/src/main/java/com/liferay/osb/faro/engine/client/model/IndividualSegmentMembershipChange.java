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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthew Kong
 */
public class IndividualSegmentMembershipChange {

	public Date getDateChanged() {
		if (_dateChanged == null) {
			return null;
		}

		return new Date(_dateChanged.getTime());
	}

	public Date getDateFirst() {
		if (_dateFirst == null) {
			return null;
		}

		return new Date(_dateFirst.getTime());
	}

	@JsonProperty("_embedded")
	public Map<String, Object> getEmbeddedResources() {
		return _embeddedResources;
	}

	public String getId() {
		return _id;
	}

	public String getIndividualEmail() {
		return _individualEmail;
	}

	public String getIndividualId() {
		return _individualId;
	}

	public String getIndividualName() {
		return _individualName;
	}

	public Long getIndividualsCount() {
		return _individualsCount;
	}

	public String getIndividualSegmentId() {
		return _individualSegmentId;
	}

	public String getOperation() {
		return _operation;
	}

	public boolean isIndividualDeleted() {
		return _individualDeleted;
	}

	public void setDateChanged(Date dateChanged) {
		if (dateChanged != null) {
			_dateChanged = new Date(dateChanged.getTime());
		}
	}

	public void setDateFirst(Date dateFirst) {
		if (dateFirst != null) {
			_dateFirst = new Date(dateFirst.getTime());
		}
	}

	public void setEmbeddedResources(Map<String, Object> embeddedResources) {
		_embeddedResources = embeddedResources;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setIndividualDeleted(boolean individualDeleted) {
		_individualDeleted = individualDeleted;
	}

	public void setIndividualEmail(String individualEmail) {
		_individualEmail = individualEmail;
	}

	public void setIndividualId(String individualId) {
		_individualId = individualId;
	}

	public void setIndividualName(String individualName) {
		_individualName = individualName;
	}

	public void setIndividualsCount(Long individualsCount) {
		_individualsCount = individualsCount;
	}

	public void setIndividualSegmentId(String individualSegmentId) {
		_individualSegmentId = individualSegmentId;
	}

	public void setOperation(String operation) {
		_operation = operation;
	}

	public enum Operation {

		ADDED, REMOVED

	}

	private Date _dateChanged;
	private Date _dateFirst;
	private Map<String, Object> _embeddedResources = new HashMap<>();
	private String _id;
	private boolean _individualDeleted;
	private String _individualEmail;
	private String _individualId;
	private String _individualName;
	private Long _individualsCount;
	private String _individualSegmentId;
	private String _operation = Operation.ADDED.name();

}