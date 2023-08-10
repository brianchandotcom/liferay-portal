/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index.creation.activator;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexCreator;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexReader;
import com.liferay.portal.search.tuning.rankings.web.internal.index.importer.SingleIndexToMultipleIndexImporter;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexNameBuilder;

import java.util.Objects;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 * @author Adam Brandizzi
 */
@Component(service = {})
public class RankingIndexCreationBundleActivator {

	@Activate
	protected void activate() {
		if (Objects.equals(
				_searchEngineInformation.getVendorString(), "Solr")) {

			return;
		}

		if (_singleIndexToMultipleIndexImporter.needImport()) {
			_companyLocalService.forEachCompanyId(
				companyId -> {
					try {
						RankingIndexName rankingIndexName =
							_rankingIndexNameBuilder.getRankingIndexName(
								companyId);

						if (_rankingIndexReader.isExists(rankingIndexName)) {
							return;
						}

						_rankingIndexCreator.create(rankingIndexName);
					}
					catch (Exception exception) {
						_log.error(exception);
					}
				});
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RankingIndexCreationBundleActivator.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private RankingIndexCreator _rankingIndexCreator;

	@Reference
	private RankingIndexNameBuilder _rankingIndexNameBuilder;

	@Reference
	private RankingIndexReader _rankingIndexReader;

	@Reference
	private SearchEngineInformation _searchEngineInformation;

	@Reference
	private SingleIndexToMultipleIndexImporter
		_singleIndexToMultipleIndexImporter;

}