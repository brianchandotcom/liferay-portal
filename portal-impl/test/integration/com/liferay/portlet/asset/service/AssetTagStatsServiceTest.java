/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetTagStats;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Matthew Kong
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class AssetTagStatsServiceTest {

	@Before
	public void setUp() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			TestPropsValues.getGroupId());

		serviceContext.setAssetTagNames(_ASSET_TAG_NAMES);

		_article = JournalTestUtil.addArticle(
			TestPropsValues.getGroupId(), ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString(100), serviceContext);

		AssetTag assetTag = AssetTagLocalServiceUtil.getTag(
			TestPropsValues.getGroupId(), _ASSET_TAG_NAME);

		_tagId = assetTag.getTagId();
	}

	@Test
	@Transactional
	public void testGetTagStatsAfterAddingAsset() throws Exception {
		AssetTagStats assetTagStats = AssetTagStatsLocalServiceUtil.getTagStats(
			_tagId, _classNameId);

		Assert.assertEquals(1, assetTagStats.getAssetCount());
	}

	@Test
	@Transactional
	public void testGetTagStatsAfterDeletingAsset() throws Exception {
		JournalArticleLocalServiceUtil.deleteArticle(_article);

		AssetTagStats assetTagStats = AssetTagStatsLocalServiceUtil.getTagStats(
			_tagId, _classNameId);

		Assert.assertEquals(0, assetTagStats.getAssetCount());
	}

	private static final String _ASSET_TAG_NAME = "basketball";

	private static final String[] _ASSET_TAG_NAMES = {_ASSET_TAG_NAME};

	private JournalArticle _article = null;
	private long _classNameId = PortalUtil.getClassNameId(JournalArticle.class);
	private long _tagId = 0;

}