/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.exportimport.changeset.Changeset;
import com.liferay.exportimport.changeset.ChangesetManager;
import com.liferay.exportimport.changeset.constants.ChangesetPortletKeys;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactoryUtil;
import com.liferay.exportimport.kernel.service.StagingLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Chaitanya Sammetla
 */
@RunWith(Arquillian.class)
public class JournalArticleCircularReferencePublishTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		_liveGroup = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_liveGroup.getGroupId());

		Map<String, Serializable> attributes = serviceContext.getAttributes();

		attributes.putAll(
			ExportImportConfigurationParameterMapFactoryUtil.
				buildParameterMap());

		StagingLocalServiceUtil.enableLocalStaging(
			TestPropsValues.getUserId(), _liveGroup, false, false,
			serviceContext);

		_stagingGroup = _liveGroup.getStagingGroup();

		_stagingLayout = LayoutTestUtil.addTypePortletLayout(_stagingGroup);

		StagingUtil.publishLayouts(
			TestPropsValues.getUserId(), _stagingGroup.getGroupId(),
			_liveGroup.getGroupId(), false,
			ExportImportConfigurationParameterMapFactoryUtil.
				buildParameterMap());

		_liveLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
			_stagingLayout.getUuid(), _liveGroup.getGroupId(),
			_stagingLayout.isPrivateLayout());
	}

	@Test
	public void testIndividualPublishPreservesCircularArticleReference()
		throws Exception {

		DataDefinitionResource dataDefinitionResource =
			_dataDefinitionResourceFactory.create(
			).user(
				TestPropsValues.getUser()
			).build();

		String dataDefinitionString = _read(
			"repeatable_journal_article_field_data_definition.json");

		DataDefinition dataDefinition =
			dataDefinitionResource.postSiteDataDefinitionByContentType(
				_stagingGroup.getGroupId(), "journal",
				DataDefinition.toDTO(dataDefinitionString));

		String structureKey = dataDefinition.getDataDefinitionKey();

		JournalArticle wc1 = JournalTestUtil.addArticleWithXMLContent(
			_stagingGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT,
			_buildXmlContent("{}"), structureKey, null, LocaleUtil.US);

		JournalArticle wc2 = JournalTestUtil.addArticleWithXMLContent(
			_stagingGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT,
			_buildXmlContent(
				_getArticleReferenceJSONObject(
					wc1
				).toString()),
			structureKey, null, LocaleUtil.US);

		JournalArticle stagingWc1 = JournalTestUtil.updateArticle(
			wc1, RandomTestUtil.randomString(),
			_buildXmlContent(
				_getArticleReferenceJSONObject(
					wc2
				).toString()));

		StagingUtil.publishPortlet(
			TestPropsValues.getUserId(), _stagingGroup.getGroupId(),
			_liveGroup.getGroupId(), _stagingLayout.getPlid(),
			_liveLayout.getPlid(), JournalPortletKeys.JOURNAL,
			ExportImportConfigurationParameterMapFactoryUtil.
				buildParameterMap());

		JournalArticle liveWc1 =
			JournalArticleLocalServiceUtil.fetchJournalArticleByUuidAndGroupId(
				stagingWc1.getUuid(), _liveGroup.getGroupId());
		JournalArticle liveWc2 =
			JournalArticleLocalServiceUtil.fetchJournalArticleByUuidAndGroupId(
				wc2.getUuid(), _liveGroup.getGroupId());

		Assert.assertNotNull(liveWc1);
		Assert.assertNotNull(liveWc2);

		String liveWc2ContentString = liveWc2.getContent();

		Assert.assertTrue(
			liveWc2ContentString.contains(
				"\"classPK\":\"" + liveWc1.getResourcePrimKey() + "\""));

		Changeset changeset = Changeset.create(
		).addStagedModel(
			() -> stagingWc1
		).build();

		_changesetManager.addChangeset(changeset);

		Map<String, String[]> parameterMap =
			ExportImportConfigurationParameterMapFactoryUtil.
				buildParameterMap();

		parameterMap.put("changesetUuid", new String[] {changeset.getUuid()});

		StagingUtil.publishPortlet(
			TestPropsValues.getUserId(), _stagingGroup.getGroupId(),
			_liveGroup.getGroupId(), _stagingLayout.getPlid(),
			_liveLayout.getPlid(), ChangesetPortletKeys.CHANGESET,
			parameterMap);

		liveWc2 =
			JournalArticleLocalServiceUtil.fetchJournalArticleByUuidAndGroupId(
				wc2.getUuid(), _liveGroup.getGroupId());

		Assert.assertNotNull(liveWc2);

		liveWc2ContentString = liveWc2.getContent();

		Assert.assertTrue(
			liveWc2ContentString.contains(
				"\"classPK\":\"" + liveWc1.getResourcePrimKey() + "\""));
	}

	private String _buildXmlContent(String referenceJSON) {
		return StringBundler.concat(
			"<?xml version=\"1.0\"?>",
			"<root available-locales=\"en_US\" default-locale=\"en_US\" ",
			"version=\"1.0\">",
			"<dynamic-element field-reference=\"JournalArticle45391501\" ",
			"index-type=\"keyword\" instance-id=\"",
			RandomTestUtil.randomString(),
			"\" name=\"JournalArticle45391501\" type=\"journal_article\">",
			"<dynamic-content language-id=\"en_US\"><![CDATA[", referenceJSON,
			"]]></dynamic-content></dynamic-element></root>");
	}

	private JSONObject _getArticleReferenceJSONObject(JournalArticle article)
		throws Exception {

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			JournalArticle.class.getName(), article.getResourcePrimKey());

		return JSONUtil.put(
			"assetEntryId", assetEntry.getEntryId()
		).put(
			"className", JournalArticle.class.getName()
		).put(
			"classNameId", PortalUtil.getClassNameId(JournalArticle.class)
		).put(
			"classPK", article.getResourcePrimKey()
		).put(
			"type", "Web Content Article"
		);
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	@Inject
	private ChangesetManager _changesetManager;

	@Inject
	private DataDefinitionResource.Factory _dataDefinitionResourceFactory;

	@DeleteAfterTestRun
	private Group _liveGroup;

	private Layout _liveLayout;
	private Group _stagingGroup;
	private Layout _stagingLayout;

}