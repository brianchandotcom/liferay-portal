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

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Alejandro Tardín
 */
@XmlRootElement
public class BulkStatusModel {

	public BulkStatusModel() {
	}

	public BulkStatusModel(boolean busy) {
		_busy = busy;
	}

	public boolean isBusy() {
		return _busy;
	}

	public void setBusy(boolean busy) {
		_busy = busy;
	}

	private boolean _busy;

}