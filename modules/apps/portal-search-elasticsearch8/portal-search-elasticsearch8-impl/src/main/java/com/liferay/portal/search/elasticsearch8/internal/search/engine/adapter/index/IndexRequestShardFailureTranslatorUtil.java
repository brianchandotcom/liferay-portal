/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.index;

import co.elastic.clients.elasticsearch._types.ErrorCause;
import co.elastic.clients.elasticsearch._types.ShardFailure;

import com.liferay.portal.search.engine.adapter.index.IndexRequestShardFailure;

/**
 * @author Michael C. Han
 */
public class IndexRequestShardFailureTranslatorUtil {

	public static IndexRequestShardFailure translate(
		ShardFailure shardFailure) {

		IndexRequestShardFailure indexRequestShardFailure =
			new IndexRequestShardFailure();

		indexRequestShardFailure.setIndex(shardFailure.index());

		ErrorCause errorCause = shardFailure.reason();

		indexRequestShardFailure.setReason(errorCause.reason());

		indexRequestShardFailure.setShardId(shardFailure.shard());

		return indexRequestShardFailure;
	}

}