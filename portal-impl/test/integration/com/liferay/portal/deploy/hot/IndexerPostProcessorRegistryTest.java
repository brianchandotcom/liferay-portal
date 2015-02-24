/**
 * Copyright 2000-present Liferay, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liferay.portal.deploy.hot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.registry.ServiceTracker;

import java.lang.reflect.Field;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Gregory Amerson
 */
public class IndexerPostProcessorRegistryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			new SyntheticBundleRule("bundle.indexerpostprocessorregistry"));

	@BeforeClass
	public static void setupRegistry() {
		if (_registry == null) {
			_registry = new IndexerPostProcessorRegistry();
		}
	}

	@Test
	public void testMultipleEntityIndexerPostProcessors()
		throws Exception {

		Indexer userIndexer =
			IndexerRegistryUtil.getIndexer("com.liferay.portal.model.User");

		IndexerPostProcessor[] userIndexerPostProcessors =
			userIndexer.getIndexerPostProcessors();

		assertEquals(1, userIndexerPostProcessors.length);

		IndexerPostProcessor userIndexerPostProcessor =
			userIndexerPostProcessors[0];

		assertNotNull(userIndexerPostProcessor);

		Indexer userGroupIndexer =
			IndexerRegistryUtil.getIndexer(
				"com.liferay.portal.model.UserGroup");

		IndexerPostProcessor[] userGroupIndexerPostProcessors =
			userGroupIndexer.getIndexerPostProcessors();

		assertEquals(1, userGroupIndexerPostProcessors.length);

		IndexerPostProcessor userGroupIndexerPostProcessor =
			userGroupIndexerPostProcessors[0];

		assertNotNull(userGroupIndexerPostProcessor);

		assertEquals(userIndexerPostProcessor, userGroupIndexerPostProcessor);
	}

	@Test
	public void testMultipleIndexerPostProcessors()
		throws Exception {

		Indexer mbMessageIndexer =
			IndexerRegistryUtil.getIndexer(
				"com.liferay.portlet.messageboards.util.MBMessageIndexer");

		IndexerPostProcessor[] mbMessageIndexerPostProcessors =
			mbMessageIndexer.getIndexerPostProcessors();

		assertEquals(1, mbMessageIndexerPostProcessors.length);

		IndexerPostProcessor mbMessageIndexerPostProcessor =
			mbMessageIndexerPostProcessors[0];

		assertNotNull(mbMessageIndexerPostProcessor);

		Indexer mbThreadIndexer =
			IndexerRegistryUtil.getIndexer(
				"com.liferay.portlet.messageboards.util.MBThreadIndexer");

		IndexerPostProcessor[] mbThreadIndexerPostProcessors =
			mbThreadIndexer.getIndexerPostProcessors();

		assertEquals(1, mbThreadIndexerPostProcessors.length);

		IndexerPostProcessor mbThreadIndexerPostProcessor =
			mbThreadIndexerPostProcessors[0];

		assertNotNull(mbThreadIndexerPostProcessor);

		assertEquals(
			mbMessageIndexerPostProcessor, mbThreadIndexerPostProcessor);
	}

	@Test
	public void testNumOfIndexerPostProcessors()
		throws Exception {

		Field serviceTrackerField =
			ReflectionUtil.getDeclaredField(
				IndexerPostProcessorRegistry.class, "_serviceTracker");

		ServiceTracker<IndexerPostProcessor, IndexerPostProcessor>
			serviceTracker =
				(ServiceTracker<IndexerPostProcessor, IndexerPostProcessor>)
					serviceTrackerField.get(_registry);

		assertEquals(4, serviceTracker.getServices().length);
	}

	@Test
	public void testSingleEntityIndexerPostProcessor()
		throws Exception {

		Indexer contactIndexer =
			IndexerRegistryUtil.getIndexer("com.liferay.portal.model.Contact");

		IndexerPostProcessor[] contactIndexerPostProcessors =
			contactIndexer.getIndexerPostProcessors();

		assertEquals(1, contactIndexerPostProcessors.length);

		IndexerPostProcessor contactIndexerPostProcessor =
			contactIndexerPostProcessors[0];

		assertNotNull(contactIndexerPostProcessor);
	}

	@Test
	public void testSingleIndexerPostProcessor()
		throws Exception {

		Indexer blogsIndexer =
			IndexerRegistryUtil.getIndexer(
				"com.liferay.portlet.blogs.util.BlogsIndexer");

		IndexerPostProcessor[] blogIndexerPostProcessors =
			blogsIndexer.getIndexerPostProcessors();

		assertEquals(1, blogIndexerPostProcessors.length);

		IndexerPostProcessor blogsIndexerPostProcessor =
			blogIndexerPostProcessors[0];

		assertNotNull(blogsIndexerPostProcessor);
	}

	private static IndexerPostProcessorRegistry _registry;

}
