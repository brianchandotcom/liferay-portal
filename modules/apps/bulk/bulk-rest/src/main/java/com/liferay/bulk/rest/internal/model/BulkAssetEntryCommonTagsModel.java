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

package com.liferay.bulk.rest.internal.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Adolfo Pérez
 */
@XmlRootElement
public class BulkAssetEntryCommonTagsModel {

	public BulkAssetEntryCommonTagsModel() {
	}

	public BulkAssetEntryCommonTagsModel(
		String description, List<String> tagNames, long[] groupIds) {

		_description = description;
		_groupIds = groupIds;
		_tagNames = tagNames;
		_status = "success";
	}

	public BulkAssetEntryCommonTagsModel(Throwable throwable) {
		_description = throwable.getMessage();
		_status = "error";
		_groupIds = null;
		_tagNames = null;
	}

	public String getDescription() {
		return _description;
	}

	public String getStatus() {
		return _status;
	}

	public long[] getGroupIds() {
		return _groupIds;
	}

	public List<String> getTagNames() {
		return _tagNames;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setStatus(String status) {
		_status = status;
	}

	public void setGroupIds(long[] groupIds) {
		_groupIds = groupIds;
	}

	public void setTagNames(List<String> tagNames) {
		_tagNames = tagNames;
	}

	private String _description;
	private String _status;
	private long[] _groupIds;
	private List<String> _tagNames;

}