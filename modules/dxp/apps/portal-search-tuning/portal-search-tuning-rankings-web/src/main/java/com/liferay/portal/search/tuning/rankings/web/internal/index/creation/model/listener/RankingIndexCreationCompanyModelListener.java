/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index.creation.model.listener;

import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexCreator;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexReader;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexNameBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(immediate = true, service = ModelListener.class)
public class RankingIndexCreationCompanyModelListener
	extends BaseModelListener<Company> {

	@Override
	public void onAfterCreate(Company company) {
		RankingIndexName rankingIndexName = _getRankingIndexName(company);

		if (_rankingIndexReader.isExists(rankingIndexName)) {
			return;
		}

		_rankingIndexCreator.create(rankingIndexName);
	}

	@Override
	public void onBeforeRemove(Company company) {
		RankingIndexName rankingIndexName = _getRankingIndexName(company);

		if (!_rankingIndexReader.isExists(rankingIndexName)) {
			return;
		}

		_rankingIndexCreator.delete(rankingIndexName);
	}

	private RankingIndexName _getRankingIndexName(Company company) {
		return _rankingIndexNameBuilder.getRankingIndexName(
			company.getCompanyId());
	}

	@Reference
	private RankingIndexCreator _rankingIndexCreator;

	@Reference
	private RankingIndexNameBuilder _rankingIndexNameBuilder;

	@Reference
	private RankingIndexReader _rankingIndexReader;

}