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

package com.liferay.segments.asah.connector.internal.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.string.StringBundler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthew Kong
 * @author David Arques
 */
public class IndividualSegment {

	public Author getAuthor() {
		return _author;
	}

	public Date getDateCreated() {
		return _dateCreated;
	}

	public Date getDateModified() {
		return _dateModified;
	}

	@JsonProperty("_embedded")
	public Map<String, Object> getEmbeddedResources() {
		return _embeddedResources;
	}

	public String getFilter() {
		return _filter;
	}

	public String getFilterMetadata() {
		return _filterMetadata;
	}

	public String getId() {
		return _id;
	}

	public long getIndividualCount() {
		return _individualCount;
	}

	public String getName() {
		return _name;
	}

	public String getScope() {
		return _scope;
	}

	public String getSegmentType() {
		return _segmentType;
	}

	public String getState() {
		return _state;
	}

	public String getStatus() {
		return _status;
	}

	public void setAuthor(Author author) {
		_author = author;
	}

	public void setDateCreated(Date dateCreated) {
		_dateCreated = dateCreated;
	}

	public void setDateModified(Date dateModified) {
		_dateModified = dateModified;
	}

	public void setEmbeddedResources(Map<String, Object> embeddedResources) {
		_embeddedResources = embeddedResources;
	}

	public void setFilter(String filter) {
		_filter = filter;
	}

	public void setFilterMetadata(String filterMetadata) {
		_filterMetadata = filterMetadata;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setIndividualCount(long individualCount) {
		_individualCount = individualCount;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setScope(String scope) {
		_scope = scope;
	}

	public void setSegmentType(String segmentType) {
		_segmentType = segmentType;
	}

	public void setState(String state) {
		_state = state;
	}

	public void setStatus(String status) {
		_status = status;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{author=");
		sb.append(_author);
		sb.append(", dateCreated=");
		sb.append(_dateCreated);
		sb.append(", dateModified=");
		sb.append(_dateModified);
		sb.append(", embeddedResources=");
		sb.append(_embeddedResources);
		sb.append(", filter=");
		sb.append(_filter);
		sb.append(", filterMetadata=");
		sb.append(_filterMetadata);
		sb.append(", id=");
		sb.append(_id);
		sb.append(", individualCount=");
		sb.append(_individualCount);
		sb.append(", name=");
		sb.append(_name);
		sb.append(", scope=");
		sb.append(_scope);
		sb.append(", segmentType=");
		sb.append(_segmentType);
		sb.append(", state=");
		sb.append(_state);
		sb.append(", status=");
		sb.append(_status);
		sb.append("}");

		return sb.toString();
	}

	public enum Scope {

		PROJECT, USER

	}

	public enum State {

		IN_PROGRESS, READY

	}

	public enum Status {

		ACTIVE, INACTIVE

	}

	public enum Type {

		DYNAMIC, STATIC

	}

	private Author _author;
	private Date _dateCreated;
	private Date _dateModified;
	private Map<String, Object> _embeddedResources = new HashMap<>();
	private String _filter;
	private String _filterMetadata;
	private String _id;
	private long _individualCount;
	private String _name;
	private String _scope = Scope.PROJECT.name();
	private String _segmentType = Type.STATIC.name();
	private String _state = State.READY.name();
	private String _status = Status.ACTIVE.name();

}