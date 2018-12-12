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

package com.liferay.segments.internal.asah.client.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Matthew Kong
 */
public class Individual {

	public Individual() {
	}

	public Map<String, Set<String>> getDataSourceIndividualPKs() {
		return _dataSourceIndividualPKs;
	}

	public Date getDateCreated() {
		return _dateCreated;
	}

	public Date getDateModified() {
		return _dateModified;
	}

	public Map<String, List<Field>> getDemographics() {
		return _demographics;
	}

	public String getId() {
		return _id;
	}

	public void setDataSourceIndividualPKs(
		Map<String, Set<String>> dataSourceIndividualPKs) {

		_dataSourceIndividualPKs = dataSourceIndividualPKs;
	}

	public void setDateCreated(Date dateCreated) {
		_dateCreated = dateCreated;
	}

	public void setDateModified(Date dateModified) {
		_dateModified = dateModified;
	}

	public void setDemographics(Map<String, List<Field>> demographics) {
		_demographics = demographics;
	}

	public void setId(String id) {
		_id = id;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(12);

		sb.append("Individual{");
		sb.append("_dataSourceIndividualPKs='");
		sb.append(_dataSourceIndividualPKs);
		sb.append("', _dateCreated='");
		sb.append(_dateCreated);
		sb.append("', _dateModified='");
		sb.append(_dateModified);
		sb.append("', _demographics='");
		sb.append(_demographics);
		sb.append("', _id='");
		sb.append(_id);
		sb.append("}");

		return sb.toString();
	}

	private Map<String, Set<String>> _dataSourceIndividualPKs = new HashMap<>();
	private Date _dateCreated;
	private Date _dateModified;
	private Map<String, List<Field>> _demographics = new HashMap<>();
	private String _id;

}