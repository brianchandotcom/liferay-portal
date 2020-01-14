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

package com.liferay.portal.workflow.metrics.internal.search;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;
import com.liferay.portal.workflow.metrics.internal.search.index.BaseWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.internal.search.index.InstanceWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.internal.search.index.NodeWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.internal.search.index.ProcessWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.internal.search.index.TokenWorkflowMetricsIndexer;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true, service = {Indexer.class, WorkflowMetricsIndexer.class}
)
public class WorkflowMetricsIndexer extends BaseIndexer<Object> {

	@Override
	public String getClassName() {
		return WorkflowMetricsIndexer.class.getName();
	}

	@Activate
	protected void activate() throws Exception {
		createIndices(
			instanceWorkflowMetricsIndexer, nodeWorkflowMetricsIndexer,
			processWorkflowMetricsIndexer, tokenWorkflowMetricsIndexer);

		if (!_INDEX_ON_STARTUP) {
			for (Company company : companyLocalService.getCompanies()) {
				instanceWorkflowMetricsIndexer.reindex(company.getCompanyId());
				nodeWorkflowMetricsIndexer.reindex(company.getCompanyId());
				processWorkflowMetricsIndexer.reindex(company.getCompanyId());
				tokenWorkflowMetricsIndexer.reindex(company.getCompanyId());
			}
		}
	}

	protected void createIndices(
			BaseWorkflowMetricsIndexer... baseWorkflowMetricsIndexers)
		throws PortalException {

		if (searchEngineAdapter == null) {
			return;
		}

		for (BaseWorkflowMetricsIndexer baseWorkflowMetricsIndexer :
				baseWorkflowMetricsIndexers) {

			if (_hasIndex(baseWorkflowMetricsIndexer.getIndexName())) {
				continue;
			}

			CreateIndexRequest createIndexRequest = new CreateIndexRequest(
				baseWorkflowMetricsIndexer.getIndexName());

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				StringUtil.read(getClass(), "/META-INF/search/mappings.json"));

			createIndexRequest.setSource(
				JSONUtil.put(
					"mappings",
					JSONUtil.put(
						baseWorkflowMetricsIndexer.getIndexType(),
						jsonObject.get(
							baseWorkflowMetricsIndexer.getIndexType()))
				).put(
					"settings",
					JSONFactoryUtil.createJSONObject(
						StringUtil.read(
							getClass(), "/META-INF/search/settings.json"))
				).toString());

			searchEngineAdapter.execute(createIndexRequest);
		}
	}

	protected void deleteIndices(
			long companyId,
			BaseWorkflowMetricsIndexer... baseWorkflowMetricsIndexers)
		throws PortalException {

		if (searchEngineAdapter == null) {
			return;
		}

		for (BaseWorkflowMetricsIndexer baseWorkflowMetricsIndexer :
				baseWorkflowMetricsIndexers) {

			if (!_hasIndex(baseWorkflowMetricsIndexer.getIndexName())) {
				continue;
			}

			BooleanQuery booleanQuery = new BooleanQueryImpl();

			booleanQuery.add(new MatchAllQuery(), BooleanClauseOccur.MUST);

			BooleanFilter booleanFilter = new BooleanFilter();

			booleanFilter.add(
				new TermFilter("companyId", String.valueOf(companyId)),
				BooleanClauseOccur.MUST);

			booleanQuery.setPreBooleanFilter(booleanFilter);

			DeleteByQueryDocumentRequest deleteByQueryDocumentRequest =
				new DeleteByQueryDocumentRequest(
					booleanQuery, baseWorkflowMetricsIndexer.getIndexName());

			if (PortalRunMode.isTestMode()) {
				deleteByQueryDocumentRequest.setRefresh(true);
			}

			searchEngineAdapter.execute(deleteByQueryDocumentRequest);
		}
	}

	@Override
	protected final void doDelete(Object t) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected final Document doGetDocument(Object object) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected final Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	protected final void doReindex(Object object) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected final void doReindex(String className, long classPK)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		deleteIndices(
			companyId, instanceWorkflowMetricsIndexer,
			nodeWorkflowMetricsIndexer, processWorkflowMetricsIndexer,
			tokenWorkflowMetricsIndexer);

		createIndices(
			instanceWorkflowMetricsIndexer, nodeWorkflowMetricsIndexer,
			processWorkflowMetricsIndexer, tokenWorkflowMetricsIndexer);

		instanceWorkflowMetricsIndexer.reindex(companyId);
		nodeWorkflowMetricsIndexer.reindex(companyId);
		processWorkflowMetricsIndexer.reindex(companyId);
		tokenWorkflowMetricsIndexer.reindex(companyId);
	}

	@Reference
	protected CompanyLocalService companyLocalService;

	@Reference
	protected InstanceWorkflowMetricsIndexer instanceWorkflowMetricsIndexer;

	@Reference
	protected NodeWorkflowMetricsIndexer nodeWorkflowMetricsIndexer;

	@Reference
	protected ProcessWorkflowMetricsIndexer processWorkflowMetricsIndexer;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(search.engine.impl=Elasticsearch)"
	)
	protected volatile SearchEngineAdapter searchEngineAdapter;

	@Reference
	protected TokenWorkflowMetricsIndexer tokenWorkflowMetricsIndexer;

	private boolean _hasIndex(String indexName) {
		IndicesExistsIndexRequest indicesExistsIndexRequest =
			new IndicesExistsIndexRequest(indexName);

		IndicesExistsIndexResponse indicesExistsIndexResponse =
			searchEngineAdapter.execute(indicesExistsIndexRequest);

		return indicesExistsIndexResponse.isExists();
	}

	private static final boolean _INDEX_ON_STARTUP = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.INDEX_ON_STARTUP));

}