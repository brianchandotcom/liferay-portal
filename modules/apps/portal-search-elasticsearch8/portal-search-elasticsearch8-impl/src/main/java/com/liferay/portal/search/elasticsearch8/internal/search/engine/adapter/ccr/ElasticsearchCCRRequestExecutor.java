/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.ccr;

import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.ccr.CCRRequestExecutor;
import com.liferay.portal.search.engine.adapter.ccr.FollowInfoCCRRequest;
import com.liferay.portal.search.engine.adapter.ccr.FollowInfoCCRResponse;
import com.liferay.portal.search.engine.adapter.ccr.PauseFollowCCRRequest;
import com.liferay.portal.search.engine.adapter.ccr.PauseFollowCCRResponse;
import com.liferay.portal.search.engine.adapter.ccr.PutFollowCCRRequest;
import com.liferay.portal.search.engine.adapter.ccr.PutFollowCCRResponse;
import com.liferay.portal.search.engine.adapter.ccr.UnfollowCCRRequest;
import com.liferay.portal.search.engine.adapter.ccr.UnfollowCCRResponse;

/**
 * @author Bryan Engler
 */
public class ElasticsearchCCRRequestExecutor implements CCRRequestExecutor {

	public ElasticsearchCCRRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_followInfoCCRRequestExecutor = new FollowInfoCCRRequestExecutor(
			elasticsearchClientResolver);
		_pauseFollowCCRRequestExecutor = new PauseFollowCCRRequestExecutor(
			elasticsearchClientResolver);
		_putFollowCCRRequestExecutor = new PutFollowCCRRequestExecutor(
			elasticsearchClientResolver);
		_unfollowCCRRequestExecutor = new UnfollowCCRRequestExecutor(
			elasticsearchClientResolver);
	}

	@Override
	public FollowInfoCCRResponse executeCCRRequest(
		FollowInfoCCRRequest followInfoCCRRequest) {

		return _followInfoCCRRequestExecutor.execute(followInfoCCRRequest);
	}

	@Override
	public PauseFollowCCRResponse executeCCRRequest(
		PauseFollowCCRRequest pauseFollowCCRRequest) {

		return _pauseFollowCCRRequestExecutor.execute(pauseFollowCCRRequest);
	}

	@Override
	public PutFollowCCRResponse executeCCRRequest(
		PutFollowCCRRequest putFollowCCRRequest) {

		return _putFollowCCRRequestExecutor.execute(putFollowCCRRequest);
	}

	@Override
	public UnfollowCCRResponse executeCCRRequest(
		UnfollowCCRRequest unfollowCCRRequest) {

		return _unfollowCCRRequestExecutor.execute(unfollowCCRRequest);
	}

	private final FollowInfoCCRRequestExecutor _followInfoCCRRequestExecutor;
	private final PauseFollowCCRRequestExecutor _pauseFollowCCRRequestExecutor;
	private final PutFollowCCRRequestExecutor _putFollowCCRRequestExecutor;
	private final UnfollowCCRRequestExecutor _unfollowCCRRequestExecutor;

}