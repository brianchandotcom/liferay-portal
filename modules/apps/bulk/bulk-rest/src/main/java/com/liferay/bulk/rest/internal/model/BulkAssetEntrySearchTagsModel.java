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
 * @author Alejandro Tardín
 */
@XmlRootElement
public class BulkAssetEntrySearchTagsModel {

	public BulkAssetEntrySearchTagsModel() {
	}

	public BulkAssetEntrySearchTagsModel(List<String> tagNames) {
		_tagNames = tagNames;
		_status = "success";
	}

	public BulkAssetEntrySearchTagsModel(Throwable throwable) {
		_status = "error";
		_tagNames = null;
	}

	public String getStatus() {
		return _status;
	}

	public List<String> getTagNames() {
		return _tagNames;
	}

	public void setStatus(String status) {
		_status = status;
	}

	public void setTagNames(List<String> tagNames) {
		_tagNames = tagNames;
	}

	private String _status;
	private List<String> _tagNames;

}