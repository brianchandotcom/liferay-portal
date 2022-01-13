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

package com.liferay.object.web.internal.object.entries.frontend.data.set.data.model;

/**
 * @author Marco Leo
 */
public class RelatedModel {

	public RelatedModel(long id, String label) {
		_id = id;
		_label = label;
	}

	public long getId() {
		return _id;
	}

	public String getLabel() {
		return _label;
	}

	private final long _id;
	private final String _label;

}