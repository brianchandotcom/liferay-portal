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

package com.liferay.wiki.lar.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.lar.test.BasePortletExportImportTestCase;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.model.WikiPageResource;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.service.WikiPageResourceLocalServiceUtil;
import com.liferay.wiki.util.test.WikiTestUtil;
import com.liferay.wiki.web.lar.WikiPortletDataHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Akos Thurzo
 */
@RunWith(Arquillian.class)
@Sync
public class WikiExportImportTest extends BasePortletExportImportTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	public String getNamespace() {
		return WikiPortletDataHandler.NAMESPACE;
	}

	@Override
	public String getPortletId() {
		return WikiPortletKeys.WIKI;
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		ServiceTestUtil.setUser(TestPropsValues.getUser());
	}

	@Override
	protected StagedModel addStagedModel(long groupId) throws Exception {
		WikiNode wikiNode = WikiTestUtil.addNode(groupId);

		return WikiTestUtil.addPage(groupId, wikiNode.getNodeId(), true);
	}

	@Override
	protected StagedModel addStagedModel(long groupId, Date createdDate)
		throws Exception {

		WikiNode wikiNode = WikiTestUtil.addNode(groupId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setCreateDate(createdDate);
		serviceContext.setLayoutFullURL("http://localhost");
		serviceContext.setModifiedDate(createdDate);

		return WikiTestUtil.addPage(
			TestPropsValues.getUserId(), wikiNode.getNodeId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), true,
			serviceContext);
	}

	@Override
	protected StagedModel addVersion(StagedModel stagedModel) throws Exception {
		WikiPage page = (WikiPage)stagedModel;

		return WikiTestUtil.updatePage(
			page, page.getUserId(), RandomTestUtil.randomString(),
			page.getContent(), true,
			ServiceContextTestUtil.getServiceContext());
	}

	@Override
	protected void deleteStagedModel(StagedModel stagedModel) throws Exception {
		WikiPageLocalServiceUtil.deletePage((WikiPage)stagedModel);
	}

	@Override
	protected AssetEntry getAssetEntry(StagedModel stagedModel)
		throws PortalException {

		WikiPage page = (WikiPage)stagedModel;

		return AssetEntryLocalServiceUtil.getEntry(
			page.getGroupId(), page.getUuid());
	}

	@Override
	protected Map<String, String[]> getExportParameterMap() throws Exception {
		Map<String, String[]> parameterMap = super.getExportParameterMap();

		addParameter(parameterMap, "referenced-content", true);
		addParameter(parameterMap, "wiki-pages", true);

		return parameterMap;
	}

	@Override
	protected Map<String, String[]> getImportParameterMap() throws Exception {
		Map<String, String[]> parameterMap = super.getImportParameterMap();

		addParameter(parameterMap, "referenced-content", true);
		addParameter(parameterMap, "wiki-pages", true);

		return parameterMap;
	}

	@Override
	protected StagedModel getStagedModel(String uuid, long groupId) {
		return WikiPageLocalServiceUtil.fetchWikiPageByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	protected boolean isVersioningEnabled() {
		return true;
	}

	@Override
	protected void testExportImportDisplayStyle(long groupId, String scopeType)
		throws Exception {
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		WikiPage page = (WikiPage)stagedModel;
		WikiPage importedPage = (WikiPage)importedStagedModel;

		Assert.assertEquals(page.getTitle(), importedPage.getTitle());
		Assert.assertEquals(
			(Double)page.getVersion(), (Double)importedPage.getVersion());
		Assert.assertEquals(page.isMinorEdit(), importedPage.isMinorEdit());
		Assert.assertEquals(page.getContent(), importedPage.getContent());
		Assert.assertEquals(page.getSummary(), importedPage.getSummary());
		Assert.assertEquals(page.getFormat(), importedPage.getFormat());
		Assert.assertEquals(page.getHead(), importedPage.getHead());
		Assert.assertEquals(
			page.getParentTitle(), importedPage.getParentTitle());
		Assert.assertEquals(
			page.getRedirectTitle(), importedPage.getRedirectTitle());
		Assert.assertEquals(page.getStatus(), importedPage.getStatus());

		WikiPageResource pageResource =
			WikiPageResourceLocalServiceUtil.getPageResource(
				page.getResourcePrimKey());
		WikiPageResource importedPageResource =
			WikiPageResourceLocalServiceUtil.getPageResource(
				importedPage.getResourcePrimKey());

		Assert.assertEquals(
			pageResource.getUuid(), importedPageResource.getUuid());
	}

	@Override
	protected void validateVersions() throws Exception {
		List<WikiNode> nodes = WikiNodeLocalServiceUtil.getNodes(
			group.getGroupId());

		for (WikiNode node : nodes) {
			List<WikiPage> pages = WikiPageLocalServiceUtil.getPages(
				node.getNodeId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (WikiPage page : pages) {
				WikiPage importedPage = (WikiPage)getStagedModel(
					page.getUuid(), importedGroup.getGroupId());

				Assert.assertNotNull(importedPage);

				validateImportedStagedModel(page, importedPage);
			}
		}
	}

}