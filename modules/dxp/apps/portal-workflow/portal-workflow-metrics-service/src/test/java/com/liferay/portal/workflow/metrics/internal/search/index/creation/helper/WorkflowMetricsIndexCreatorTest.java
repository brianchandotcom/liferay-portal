/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.internal.search.index.creation.helper;

import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.capabilities.SearchCapabilities;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchResponse;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.workflow.metrics.internal.search.index.WorkflowMetricsIndex;
import com.liferay.portal.workflow.metrics.search.index.constants.WorkflowMetricsIndexNameConstants;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.InOrder;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Carolina Barbosa
 */
public class WorkflowMetricsIndexCreatorTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_setUpBackgroundTaskLocalService();
		_setUpIndexNameBuilder();
		_setUpPrincipalThreadLocal();
		_setUpQueries();
		_setUpSearchCapabilities();
		_setUpSearchEngineAdapter();
		_setUpWorkflowMetricsIndex();
	}

	@After
	public void tearDown() {
		_principalThreadLocalMockedStatic.close();
		_workflowMetricsIndexMockedStatic.close();
	}

	@Test
	public void testReindex() throws Exception {
		Company company = Mockito.mock(Company.class);

		Mockito.when(
			company.getCompanyId()
		).thenReturn(
			_COMPANY_ID
		);

		Mockito.when(
			company.getGroupId()
		).thenReturn(
			_GROUP_ID
		);

		User user = Mockito.mock(User.class);

		Mockito.when(
			user.getUserId()
		).thenReturn(
			_USER_ID
		);

		Mockito.when(
			company.getGuestUser()
		).thenReturn(
			user
		);

		_workflowMetricsIndexCreator.reindex(company);

		InOrder inOrder = Mockito.inOrder(
			_backgroundTaskLocalService, PrincipalThreadLocal.class);

		inOrder.verify(
			_principalThreadLocalMockedStatic, PrincipalThreadLocal::getName);

		inOrder.verify(
			_principalThreadLocalMockedStatic,
			() -> PrincipalThreadLocal.setName(_USER_ID));

		inOrder.verify(
			_backgroundTaskLocalService, Mockito.times(1)
		).addBackgroundTask(
			Mockito.eq(_USER_ID), Mockito.eq(_GROUP_ID),
			Mockito.eq("WorkflowMetricsIndexCreator"),
			Mockito.eq(
				"com.liferay.portal.workflow.metrics.internal.background." +
					"task.WorkflowMetricsReindexBackgroundTaskExecutor"),
			Mockito.any(Map.class), Mockito.any(ServiceContext.class)
		);

		inOrder.verify(
			_principalThreadLocalMockedStatic,
			() -> PrincipalThreadLocal.setName(_NAME));
	}

	private void _setUpBackgroundTaskLocalService() throws Exception {
		BackgroundTask backgroundTask = Mockito.mock(BackgroundTask.class);

		Mockito.when(
			_backgroundTaskLocalService.addBackgroundTask(
				Mockito.any(Long.class), Mockito.any(Long.class),
				Mockito.any(String.class), Mockito.any(String.class),
				Mockito.any(Map.class), Mockito.any(ServiceContext.class))
		).thenReturn(
			backgroundTask
		);

		ReflectionTestUtil.setFieldValue(
			_workflowMetricsIndexCreator, "_backgroundTaskLocalService",
			_backgroundTaskLocalService);
	}

	private void _setUpIndexNameBuilder() {
		ReflectionTestUtil.setFieldValue(
			_workflowMetricsIndexCreator, "_indexNameBuilder",
			_indexNameBuilder);
	}

	private void _setUpPrincipalThreadLocal() {
		_principalThreadLocalMockedStatic.when(
			PrincipalThreadLocal::getName
		).thenReturn(
			_NAME
		);
	}

	private void _setUpQueries() {
		BooleanQuery booleanQuery = Mockito.mock(BooleanQuery.class);

		Mockito.when(
			_queries.booleanQuery()
		).thenReturn(
			booleanQuery
		);

		ReflectionTestUtil.setFieldValue(
			_workflowMetricsIndexCreator, "_queries", _queries);
	}

	private void _setUpSearchCapabilities() {
		Mockito.when(
			_searchCapabilities.isWorkflowMetricsSupported()
		).thenReturn(
			true
		);

		ReflectionTestUtil.setFieldValue(
			_workflowMetricsIndexCreator, "_searchCapabilities",
			_searchCapabilities);
	}

	private void _setUpSearchEngineAdapter() {
		CountSearchResponse countSearchResponse = Mockito.mock(
			CountSearchResponse.class);

		Mockito.when(
			countSearchResponse.getCount()
		).thenReturn(
			0L
		);

		Mockito.when(
			_searchEngineAdapter.execute(Mockito.any(CountSearchRequest.class))
		).thenReturn(
			countSearchResponse
		);

		ReflectionTestUtil.setFieldValue(
			_workflowMetricsIndexCreator, "_searchEngineAdapter",
			_searchEngineAdapter);
	}

	private void _setUpWorkflowMetricsIndex() {
		_workflowMetricsIndexMockedStatic.when(
			() -> WorkflowMetricsIndex.getIndexName(
				_indexNameBuilder,
				WorkflowMetricsIndexNameConstants.SUFFIX_PROCESS, _COMPANY_ID)
		).thenReturn(
			RandomTestUtil.randomString()
		);
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final long _GROUP_ID = RandomTestUtil.randomLong();

	private static final String _NAME = RandomTestUtil.randomString();

	private static final long _USER_ID = RandomTestUtil.randomLong();

	private static final MockedStatic<PrincipalThreadLocal>
		_principalThreadLocalMockedStatic = Mockito.mockStatic(
			PrincipalThreadLocal.class);
	private static final MockedStatic<WorkflowMetricsIndex>
		_workflowMetricsIndexMockedStatic = Mockito.mockStatic(
			WorkflowMetricsIndex.class);

	private final BackgroundTaskLocalService _backgroundTaskLocalService =
		Mockito.mock(BackgroundTaskLocalService.class);
	private final IndexNameBuilder _indexNameBuilder = Mockito.mock(
		IndexNameBuilder.class);
	private final Queries _queries = Mockito.mock(Queries.class);
	private final SearchCapabilities _searchCapabilities = Mockito.mock(
		SearchCapabilities.class);
	private final SearchEngineAdapter _searchEngineAdapter = Mockito.mock(
		SearchEngineAdapter.class);
	private final WorkflowMetricsIndexCreator _workflowMetricsIndexCreator =
		new WorkflowMetricsIndexCreator();

}