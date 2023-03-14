/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.search.JournalArticleBlueprint;
import com.liferay.journal.test.util.search.JournalArticleContent;
import com.liferay.journal.test.util.search.JournalArticleSearchFixture;
import com.liferay.journal.test.util.search.JournalArticleTitle;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.module.util.BundleUtil;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Jorge Díaz
 */
@RunWith(Arquillian.class)
public class IndexerRequestBufferTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		builder.setPropagation(Propagation.REQUIRED);
		builder.setRollbackForClasses(Exception.class);

		_transactionConfig = builder.build();
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_journalArticleSearchFixture = new JournalArticleSearchFixture(
			_ddmStructureLocalService, _journalArticleLocalService, _portal);

		_journalArticleSearchFixture.setUp();
	}

	@Test
	public void testJournalArticleIndexedOnlyOnce() throws Throwable {
		TransactionInvokerUtil.invoke(
			_transactionConfig,
			() -> {
				_addJournalArticle();

				Assert.assertEquals(1, _getIndexerRequestBufferSize());

				return null;
			});
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	private void _addJournalArticle() throws Exception {
		_journalArticleSearchFixture.addArticle(
			new JournalArticleBlueprint() {
				{
					setGroupId(_group.getGroupId());
					setJournalArticleContent(new JournalArticleContent());
					setJournalArticleTitle(
						new JournalArticleTitle() {
							{
								put(
									LocaleUtil.US,
									RandomTestUtil.randomString());
							}
						});
				}
			});
	}

	private int _getIndexerRequestBufferSize() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		bundle = BundleUtil.getBundle(
			bundle.getBundleContext(), "com.liferay.portal.search");

		Class<?> indexerRequestBufferClass = bundle.loadClass(
			"com.liferay.portal.search.internal.buffer.IndexerRequestBuffer");

		Object indexerRequestBuffer = ReflectionTestUtil.invoke(
			indexerRequestBufferClass, "get", new Class<?>[0]);

		return (int)ReflectionTestUtil.invoke(
			indexerRequestBuffer, "size", new Class<?>[0]);
	}

	@Inject
	private static DDMStructureLocalService _ddmStructureLocalService;

	@Inject
	private static JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private static Portal _portal;

	private static TransactionConfig _transactionConfig;

	@DeleteAfterTestRun
	private Group _group;

	private JournalArticleSearchFixture _journalArticleSearchFixture;

}