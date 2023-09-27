/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.snapshot;

import com.liferay.portal.search.engine.adapter.ccr.CrossClusterRequest;

/**
 * @author Michael C. Han
 */
public class CreateSnapshotRepositoryRequest
	extends CrossClusterRequest
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