/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.snapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class GetSnapshotRepositoriesResponse implements SnapshotResponse {

	public void addSnapshotRepositoryMetadata(
		SnapshotRepositoryDetails snapshotRepositoryDetails) {

		_snapshotRepositoryDetails.add(snapshotRepositoryDetails);
	}

	public List<SnapshotRepositoryDetails> getSnapshotRepositoryDetails() {
		return _snapshotRepositoryDetails;
	}

	private final List<SnapshotRepositoryDetails> _snapshotRepositoryDetails =
		new ArrayList<>();

}