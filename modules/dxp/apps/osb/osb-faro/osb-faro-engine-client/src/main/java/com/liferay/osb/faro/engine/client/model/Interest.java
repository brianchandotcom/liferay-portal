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
 * @author Shinn Lok
 */
public class Interest {

	public Date getDateRecorded() {
		if (_dateRecorded == null) {
			return null;
		}

		return new Date(_dateRecorded.getTime());
	}

	@JsonProperty("_embedded")
	public Map<String, Object> getEmbeddedResources() {
		return _embeddedResources;
	}

	public String getIdentifier() {
		return _identifier;
	}

	public String getName() {
		return _name;
	}

	public String getOwnerIdentifier() {
		return _ownerIdentifier;
	}

	public String getOwnerType() {
		return _ownerType;
	}

	public double getScore() {
		return _score;
	}

	public int getViews() {
		return _views;
	}

	public void setDateRecorded(Date dateRecorded) {
		if (dateRecorded != null) {
			_dateRecorded = new Date(dateRecorded.getTime());
		}
	}

	public void setEmbeddedResources(Map<String, Object> embeddedResources) {
		_embeddedResources = embeddedResources;
	}

	public void setIdentifier(String identifier) {
		_identifier = identifier;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOwnerIdentifier(String ownerIdentifier) {
		_ownerIdentifier = ownerIdentifier;
	}

	public void setOwnerType(String ownerType) {
		_ownerType = ownerType;
	}

	public void setScore(double score) {
		_score = score;
	}

	public void setViews(int views) {
		_views = views;
	}

	private Date _dateRecorded;
	private Map<String, Object> _embeddedResources = new HashMap<>();
	private String _identifier;
	private String _name;
	private String _ownerIdentifier;
	private String _ownerType;
	private double _score;
	private int _views;

}