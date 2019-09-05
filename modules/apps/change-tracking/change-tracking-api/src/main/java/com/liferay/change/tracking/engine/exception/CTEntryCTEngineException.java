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
 * @author Gergely Mathe
 */
public class CTEntryCTEngineException extends CTEngineException {

	public CTEntryCTEngineException(
		long ctEntryId, long companyId, long userId, long modelClassNameId,
		long modelClassPK, long modelResourcePrimKey, long ctCollectionId) {

		super(companyId);

		_ctEntryId = ctEntryId;
		_userId = userId;
		_modelClassNameId = modelClassNameId;
		_modelClassPK = modelClassPK;
		_modelResourcePrimKey = modelResourcePrimKey;
		_ctCollectionId = ctCollectionId;
	}

	public CTEntryCTEngineException(
		long ctEntryId, long companyId, long userId, long modelClassNameId,
		long modelClassPK, long modelResourcePrimKey, long ctCollectionId,
		String msg) {

		super(companyId, msg);

		_ctEntryId = ctEntryId;
		_userId = userId;
		_modelClassNameId = modelClassNameId;
		_modelClassPK = modelClassPK;
		_modelResourcePrimKey = modelResourcePrimKey;
		_ctCollectionId = ctCollectionId;
	}

	public CTEntryCTEngineException(
		long ctEntryId, long companyId, long userId, long modelClassNameId,
		long modelClassPK, long modelResourcePrimKey, long ctCollectionId,
		String msg, Throwable cause) {

		super(companyId, msg, cause);

		_ctEntryId = ctEntryId;
		_userId = userId;
		_modelClassNameId = modelClassNameId;
		_modelClassPK = modelClassPK;
		_modelResourcePrimKey = modelResourcePrimKey;
		_ctCollectionId = ctCollectionId;
	}

	public CTEntryCTEngineException(
		long ctEntryId, long companyId, long userId, long modelClassNameId,
		long modelClassPK, long modelResourcePrimKey, long ctCollectionId,
		Throwable cause) {

		super(companyId, cause);

		_ctEntryId = ctEntryId;
		_userId = userId;
		_modelClassNameId = modelClassNameId;
		_modelClassPK = modelClassPK;
		_modelResourcePrimKey = modelResourcePrimKey;
		_ctCollectionId = ctCollectionId;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public long getCtEntryId() {
		return _ctEntryId;
	}

	public long getModelClassNameId() {
		return _modelClassNameId;
	}

	public long getModelClassPK() {
		return _modelClassPK;
	}

	public long getModelResourcePrimKey() {
		return _modelResourcePrimKey;
	}

	public long getUserId() {
		return _userId;
	}

	private final long _ctCollectionId;
	private final long _ctEntryId;
	private final long _modelClassNameId;
	private final long _modelClassPK;
	private final long _modelResourcePrimKey;
	private final long _userId;

}