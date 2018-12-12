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

import com.liferay.petra.string.StringPool;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Adolfo Pérez
 */
@XmlRootElement
public class BulkActionResponseModel {

	public static final BulkActionResponseModel SUCCESS =
		new BulkActionResponseModel();

	public BulkActionResponseModel() {
		_status = "success";
		_description = StringPool.BLANK;
	}

	public BulkActionResponseModel(Throwable throwable) {
		_status = "error";
		_description = throwable.getMessage();
	}

	public String getDescription() {
		return _description;
	}

	public String getStatus() {
		return _status;
	}

	private final String _description;
	private final String _status;

}