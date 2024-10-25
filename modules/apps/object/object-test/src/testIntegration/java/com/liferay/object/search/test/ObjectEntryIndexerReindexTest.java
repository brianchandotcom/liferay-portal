/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.field.setting.builder.ObjectFieldSettingBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.test.rule.SearchTestRule;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Victor Kammerer
 */
@RunWith(Arquillian.class)
public class ObjectEntryIndexerReindexTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testReindex() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.object.internal.search.spi.model.index." +
					"contributor.ObjectEntryModelDocumentContributor",
				LoggerTestUtil.WARN)) {

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.addCustomObjectDefinition(
					TestPropsValues.getUserId(), 0, null, false, true, true,
					false,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					ObjectDefinitionTestUtil.getRandomName(), null, null,
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString()),
					true, ObjectDefinitionConstants.SCOPE_COMPANY,
					ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
					Arrays.asList(
						ObjectFieldUtil.createObjectField(
							ObjectFieldConstants.BUSINESS_TYPE_TEXT,
							ObjectFieldConstants.DB_TYPE_STRING, true, true,
							null, RandomTestUtil.randomString(), "name",
							Arrays.asList(
								new ObjectFieldSettingBuilder(
								).name(
									ObjectFieldSettingConstants.
										NAME_UNIQUE_VALUES
								).value(
									Boolean.TRUE.toString()
								).build()),
							false)));

			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				objectDefinition.getObjectDefinitionId());

			ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
				TestPropsValues.getUserId(), 0,
				objectDefinition.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					"longTextLocalized_i18n",
					HashMapBuilder.put(
						"en_US", RandomTestUtil.randomString()
					).put(
						"pt_BR", RandomTestUtil.randomString()
					).build()
				).put(
					"richTextLocalized_i18n",
					HashMapBuilder.put(
						"en_US", RandomTestUtil.randomString()
					).put(
						"pt_BR", RandomTestUtil.randomString()
					).build()
				).put(
					"textLocalized_i18n",
					HashMapBuilder.put(
						"en_US", "en_US " + RandomTestUtil.randomString()
					).put(
						"pt_BR", "pt_BR " + RandomTestUtil.randomString()
					).build()
				).build(),
				ServiceContextTestUtil.getServiceContext());

			String originalName = PrincipalThreadLocal.getName();

			_user = TestPropsValues.getUser();

			try {
				PrincipalThreadLocal.setName(null);

				Indexer<ObjectEntry> indexer =
					IndexerRegistryUtil.nullSafeGetIndexer(
						objectEntry.getModelClassName());

				indexer.reindex(
					objectDefinition.getClassName(),
					objectEntry.getObjectEntryId());

				String searchTerm = String.valueOf(
					objectEntry.getObjectEntryId());

				FieldValuesAssert.assertFieldValue(
					Field.ENTRY_CLASS_NAME, objectEntry.getModelClassName(),
					search(searchTerm));

				List<LogEntry> logEntries = logCapture.getLogEntries();

				if (!logEntries.isEmpty()) {
					LogEntry logEntry = logEntries.get(0);

					Assert.assertNotEquals(
						"Unable to index object entry " +
							objectEntry.getObjectEntryId(),
						logEntry.getMessage());
				}

				Assert.assertEquals(
					logEntries.toString(), 0, logEntries.size());
			}
			finally {
				PrincipalThreadLocal.setName(originalName);

				_objectDefinitionLocalService.deleteObjectDefinition(
					objectDefinition);
			}
		}
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected SearchResponse search(String searchTerm) {
		return searcher.search(
			searchRequestBuilderFactory.builder(
			).companyId(
				_user.getCompanyId()
			).queryString(
				searchTerm
			).build());
	}

	@Inject
	protected Searcher searcher;

	@Inject
	protected SearchRequestBuilderFactory searchRequestBuilderFactory;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	private User _user;

}