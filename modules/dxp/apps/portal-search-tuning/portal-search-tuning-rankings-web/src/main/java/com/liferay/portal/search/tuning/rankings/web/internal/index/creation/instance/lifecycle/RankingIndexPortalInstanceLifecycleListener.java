/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index.creation.instance.lifecycle;

import com.liferay.petra.io.Deserializer;
import com.liferay.petra.io.Serializer;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.search.capabilities.SearchCapabilities;
import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexCreator;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexReader;
import com.liferay.portal.search.tuning.rankings.web.internal.index.importer.SingleIndexToMultipleIndexImporter;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexNameBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.nio.ByteBuffer;

import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration",
	service = PortalInstanceLifecycleListener.class
)
public class RankingIndexPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		_createIndex(company.getCompanyId());
	}

	@Override
	public void portalInstanceUnregistered(Company company) throws Exception {
		_deleteIndex(company.getCompanyId());
	}

	@Activate
	@Modified
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;

		_elasticsearchConfiguration = ConfigurableUtil.createConfigurable(
			ElasticsearchConfiguration.class, properties);

		if (_shouldCreateIndexes()) {
			_companyLocalService.forEachCompanyId(
				companyId -> _createIndex(companyId));
		}
	}

	private void _createIndex(long companyId) {
		if (!_searchCapabilities.isResultRankingsSupported() ||
			(companyId == CompanyConstants.SYSTEM)) {

			return;
		}

		RankingIndexName rankingIndexName =
			_rankingIndexNameBuilder.getRankingIndexName(companyId);

		if (_rankingIndexReader.isExists(rankingIndexName)) {
			return;
		}

		_rankingIndexCreator.create(rankingIndexName);

		if (_singleIndexToMultipleIndexImporter.needImport()) {
			_singleIndexToMultipleIndexImporter.importRankings(companyId);
		}
	}

	private void _deleteIndex(long companyId) {
		if (!_searchCapabilities.isResultRankingsSupported() ||
			(companyId == CompanyConstants.SYSTEM)) {

			return;
		}

		RankingIndexName rankingIndexName =
			_rankingIndexNameBuilder.getRankingIndexName(companyId);

		if (!_rankingIndexReader.isExists(rankingIndexName)) {
			return;
		}

		_rankingIndexCreator.delete(rankingIndexName);
	}

	private boolean _shouldCreateIndexes() {
		File dataFile = _bundleContext.getDataFile(
			"elasticsearch_configuration_production_mode_enabled.data");

		if (dataFile.exists() && !StartupHelperUtil.isDBNew()) {
			try {
				Deserializer deserializer = new Deserializer(
					ByteBuffer.wrap(FileUtil.getBytes(dataFile)));

				if (deserializer.readBoolean() ==
						_elasticsearchConfiguration.productionModeEnabled()) {

					return false;
				}
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to read Elasticsearch configuration",
						exception);
				}
			}
		}

		Serializer serializer = new Serializer();

		serializer.writeBoolean(
			_elasticsearchConfiguration.productionModeEnabled());

		try (OutputStream outputStream = new FileOutputStream(dataFile)) {
			serializer.writeTo(outputStream);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to update Elasticsearch configuration", exception);
			}
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RankingIndexPortalInstanceLifecycleListener.class);

	private volatile BundleContext _bundleContext;

	@Reference
	private CompanyLocalService _companyLocalService;

	private volatile ElasticsearchConfiguration _elasticsearchConfiguration;

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