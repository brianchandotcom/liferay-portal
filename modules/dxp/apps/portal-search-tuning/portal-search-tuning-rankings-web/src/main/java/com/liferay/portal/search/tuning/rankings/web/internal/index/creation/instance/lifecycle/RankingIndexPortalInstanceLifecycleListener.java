/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index.creation.instance.lifecycle;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.EveryNodeEveryStartup;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.search.capabilities.SearchCapabilities;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexCreator;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexReader;
import com.liferay.portal.search.tuning.rankings.web.internal.index.importer.SingleIndexToMultipleIndexImporter;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexNameBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class RankingIndexPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener
	implements EveryNodeEveryStartup {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (!_searchCapabilities.isResultRankingsSupported()) {
			return;
		}

		RankingIndexName rankingIndexName =
			_rankingIndexNameBuilder.getRankingIndexName(
				company.getCompanyId());

		if (!_rankingIndexReader.isExists(rankingIndexName)) {
			_rankingIndexCreator.create(rankingIndexName);
		}

		if (_singleIndexToMultipleIndexImporter.needImport()) {
			_singleIndexToMultipleIndexImporter.importRankings(
				company.getCompanyId());
		}
	}

	@Override
	public void portalInstanceUnregistered(Company company) throws Exception {
		if (!_searchCapabilities.isResultRankingsSupported()) {
			return;
		}

		RankingIndexName rankingIndexName =
			_rankingIndexNameBuilder.getRankingIndexName(
				company.getCompanyId());

		if (!_rankingIndexReader.isExists(rankingIndexName)) {
			return;
		}

		_rankingIndexCreator.delete(rankingIndexName);
	}

	@Reference
	private RankingIndexCreator _rankingIndexCreator;

	@Reference
	private RankingIndexNameBuilder _rankingIndexNameBuilder;

	@Reference
	private RankingIndexReader _rankingIndexReader;

	@Reference
	private SearchCapabilities _searchCapabilities;

	@Reference
	private SingleIndexToMultipleIndexImporter
		_singleIndexToMultipleIndexImporter;

}