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

package com.liferay.change.tracking.engine.exception;

/**
 * @author Daniel Kocsis
 */
public class CTEntryCollisionCTEngineException extends CTEngineException {

	public CTEntryCollisionCTEngineException(long companyId, long ctEntryId) {
		super(
			companyId,
			"Unable to publish change entry " + ctEntryId +
				" because it is colliding with other changes");

		_ctEntryId = ctEntryId;
	}

	public long getCtEntryId() {
		return _ctEntryId;
	}

	private final long _ctEntryId;

}