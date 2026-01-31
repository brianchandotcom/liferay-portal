/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.snapshot;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotRequestExecutor;

/**
 * @author Shuyang Zhou
 */
public class SnapshotRequestExecutorTestUtil {

	public static SnapshotRequestExecutor createSnapshotRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		SnapshotRequestExecutor snapshotRequestExecutor =
			new ElasticsearchSnapshotRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			snapshotRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		ReflectionTestUtil.invoke(
			snapshotRequestExecutor, "activate", new Class<?>[0]);

		return snapshotRequestExecutor;
	}

}