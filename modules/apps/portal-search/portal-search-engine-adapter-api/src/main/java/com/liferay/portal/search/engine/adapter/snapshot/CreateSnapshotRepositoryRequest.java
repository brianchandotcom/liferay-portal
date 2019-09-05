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

package com.liferay.portal.search.engine.adapter.snapshot;

/**
 * @author Michael C. Han
 */
public class CreateSnapshotRepositoryRequest
	implements SnapshotRequest<CreateSnapshotRepositoryResponse> {

	public CreateSnapshotRepositoryRequest(String name, String location) {
		_name = name;
		_location = location;
	}

	@Override
	public CreateSnapshotRepositoryResponse accept(
		SnapshotRequestExecutor snapshotRequestExecutor) {

		return snapshotRequestExecutor.executeSnapshotRequest(this);
	}

	public String getLocation() {
		return _location;
	}

	public String getName() {
		return _name;
	}

	public String getType() {
		return _type;
	}

	public boolean isCompress() {
		return _compress;
	}

	public boolean isVerify() {
		return _verify;
	}

	public void setCompress(boolean compress) {
		_compress = compress;
	}

	public void setType(String type) {
		_type = type;
	}

	public void setVerify(boolean verify) {
		_verify = verify;
	}

	private boolean _compress;
	private final String _location;
	private final String _name;
	private String _type = SnapshotRepositoryDetails.FS_REPOSITORY_TYPE;
	private boolean _verify = true;

}