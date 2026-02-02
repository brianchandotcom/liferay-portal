/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.snapshot;

import co.elastic.clients.elasticsearch.snapshot.CreateRepositoryRequest;
import co.elastic.clients.elasticsearch.snapshot.Repository;
import co.elastic.clients.elasticsearch.snapshot.SharedFileSystemRepository;
import co.elastic.clients.elasticsearch.snapshot.SharedFileSystemRepositorySettings;

import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.index.AnalyzeIndexRequestExecutorTest;
import com.liferay.portal.search.engine.adapter.snapshot.CreateSnapshotRepositoryRequest;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class CreateSnapshotRepositoryRequestExecutorImplTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			AnalyzeIndexRequestExecutorTest.class.getSimpleName());

		_elasticsearchFixture.setUp();
	}

	@After
	public void tearDown() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@Test
	public void testCreatePutRepositoryRequest() {
		CreateSnapshotRepositoryRequest createSnapshotRepositoryRequest =
			new CreateSnapshotRepositoryRequest("name", "location");

		createSnapshotRepositoryRequest.setCompress(true);
		createSnapshotRepositoryRequest.setType("fs");
		createSnapshotRepositoryRequest.setVerify(true);

		CreateSnapshotRepositoryRequestExecutor
			createSnapshotRepositoryRequestExecutor =
				new CreateSnapshotRepositoryRequestExecutor(
					_elasticsearchFixture);

		CreateRepositoryRequest createRepositoryRequest =
			createSnapshotRepositoryRequestExecutor.
				createCreateRepositoryRequest(createSnapshotRepositoryRequest);

		Repository repository = createRepositoryRequest.repository();

		SharedFileSystemRepository sharedFileSystemRepository = repository.fs();

		SharedFileSystemRepositorySettings sharedFileSystemRepositorySettings =
			sharedFileSystemRepository.settings();

		Assert.assertEquals(
			createSnapshotRepositoryRequest.isCompress(),
			sharedFileSystemRepositorySettings.compress());
		Assert.assertEquals(
			String.valueOf(createSnapshotRepositoryRequest.getLocation()),
			sharedFileSystemRepositorySettings.location());

		Assert.assertEquals(
			createSnapshotRepositoryRequest.getName(),
			createRepositoryRequest.name());
		Assert.assertEquals("fs", createSnapshotRepositoryRequest.getType());
		Assert.assertEquals(
			createSnapshotRepositoryRequest.isVerify(),
			createRepositoryRequest.verify());
	}

	private ElasticsearchFixture _elasticsearchFixture;

}