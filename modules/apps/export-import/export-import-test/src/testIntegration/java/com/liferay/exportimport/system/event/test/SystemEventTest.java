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

package com.liferay.exportimport.system.event.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationFactory;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.StagingLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.SystemEvent;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.SystemEventLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.GroupUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Akos Thurzo
 */
@RunWith(Arquillian.class)
@Sync(cleanTransaction = true)
public class SystemEventTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	public long doTestRemoteStaging() throws Exception {
		setPortalProperty(
			"TUNNELING_SERVLET_SHARED_SECRET",
			"F0E1D2C3B4A5968778695A4B3C2D1E0F");

		setPortalProperty("TUNNELING_SERVLET_SHARED_SECRET_HEX", true);

		_stagingGroup = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(_stagingGroup.getGroupId());

		Map<String, Serializable> attributes = serviceContext.getAttributes();

		attributes.putAll(
			ExportImportConfigurationParameterMapFactoryUtil.
				buildParameterMap());

		attributes.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.FALSE.toString()});
		attributes.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.FALSE.toString()});

		String pathContext = _portal.getPathContext();

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		// Fall back to default port

		if (_serverPort <= 0) {
			_serverPort = 8080;
		}

		StagingLocalServiceUtil.enableRemoteStaging(
			TestPropsValues.getUserId(), _stagingGroup, false, false,
			"localhost", _serverPort, pathContext, false,
			_liveGroup.getGroupId(), serviceContext);

		_exportImportConfiguration =
			ExportImportConfigurationFactory.
				buildDefaultRemotePublishingExportImportConfiguration(
					TestPropsValues.getUser(), _stagingGroup.getGroupId(),
					false, "localhost", _serverPort, pathContext, false,
					_liveGroup.getGroupId());

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_stagingGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		StagingUtil.publishLayouts(
			TestPropsValues.getUserId(), _exportImportConfiguration);

		List<JournalArticle> articles =
			JournalArticleLocalServiceUtil.getArticles(_liveGroup.getGroupId());

		Assert.assertEquals(articles.toString(), 1, articles.size());

		JournalArticleLocalServiceUtil.deleteArticle(
			_stagingGroup.getGroupId(), journalArticle.getArticleId(),
			new ServiceContext());

		SystemEvent systemEvent = SystemEventLocalServiceUtil.fetchSystemEvent(
			_stagingGroup.getGroupId(),
			ClassNameLocalServiceUtil.getClassNameId(JournalArticle.class),
			journalArticle.getResourcePrimKey(),
			SystemEventConstants.TYPE_DELETE);

		Assert.assertNotNull(systemEvent);

		GroupUtil.clearCache();

		StagingUtil.publishLayouts(
			TestPropsValues.getUserId(), _exportImportConfiguration);

		Assert.assertEquals(
			0,
			JournalArticleLocalServiceUtil.getArticlesCount(
				_liveGroup.getGroupId()));

		journalArticle = articles.get(0);

		return journalArticle.getResourcePrimKey();
	}

	public void setPortalProperty(String propertyName, Object value)
		throws Exception {

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, propertyName);

		field.setAccessible(true);

		Field modifiersField = Field.class.getDeclaredField("modifiers");

		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		field.set(null, value);
	}

	@Before
	public void setUp() throws Exception {
		_liveGroup = GroupTestUtil.addGroup();

		if (_serverPort <= 0) {
			_serverPort = _portal.getPortalServerPort(false);

			if (_serverPort <= 0) {
				_serverPort = _portal.getPortalServerPort(true);
			}
		}
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_liveGroup);

		try {
			GroupLocalServiceUtil.deleteGroup(_stagingGroup);
		}
		catch (NoSuchGroupException nsge) {
		}
	}

	@Test
	public void testLocalStaging() throws Exception {
		GroupTestUtil.enableLocalStaging(_liveGroup);

		_stagingGroup = _liveGroup.getStagingGroup();

		_exportImportConfiguration =
			ExportImportConfigurationFactory.
				buildDefaultLocalPublishingExportImportConfiguration(
					TestPropsValues.getUser(), _stagingGroup.getGroupId(),
					_liveGroup.getGroupId(), false);

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_stagingGroup.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		StagingUtil.publishLayouts(
			TestPropsValues.getUserId(), _exportImportConfiguration);

		List<JournalArticle> articles =
			JournalArticleLocalServiceUtil.getArticles(_liveGroup.getGroupId());

		Assert.assertEquals(articles.toString(), 1, articles.size());

		JournalArticleLocalServiceUtil.deleteArticle(
			_stagingGroup.getGroupId(), journalArticle.getArticleId(),
			new ServiceContext());

		SystemEvent systemEvent = SystemEventLocalServiceUtil.fetchSystemEvent(
			_stagingGroup.getGroupId(),
			ClassNameLocalServiceUtil.getClassNameId(JournalArticle.class),
			journalArticle.getResourcePrimKey(),
			SystemEventConstants.TYPE_DELETE);

		Assert.assertNotNull(systemEvent);

		StagingUtil.publishLayouts(
			TestPropsValues.getUserId(), _exportImportConfiguration);

		Assert.assertEquals(
			0,
			JournalArticleLocalServiceUtil.getArticlesCount(
				_liveGroup.getGroupId()));

		JournalArticle firstJournalArticle = articles.get(0);

		systemEvent = SystemEventLocalServiceUtil.fetchSystemEvent(
			_liveGroup.getGroupId(),
			ClassNameLocalServiceUtil.getClassNameId(JournalArticle.class),
			firstJournalArticle.getResourcePrimKey(),
			SystemEventConstants.TYPE_DELETE);

		Assert.assertNull(systemEvent);
	}

	@Test
	public void testRemoteStaging1() throws Exception {
		setPortalProperty("STAGING_LIVE_GROUP_REMOTE_STAGING_ENABLED", false);

		long classPK = doTestRemoteStaging();

		SystemEvent systemEvent = SystemEventLocalServiceUtil.fetchSystemEvent(
			_liveGroup.getGroupId(),
			ClassNameLocalServiceUtil.getClassNameId(JournalArticle.class),
			classPK, SystemEventConstants.TYPE_DELETE);

		Assert.assertNull(systemEvent);
	}

	@Test
	public void testRemoteStaging2() throws Exception {
		setPortalProperty("STAGING_LIVE_GROUP_REMOTE_STAGING_ENABLED", true);

		long classPK = doTestRemoteStaging();

		SystemEvent systemEvent = SystemEventLocalServiceUtil.fetchSystemEvent(
			_liveGroup.getGroupId(),
			ClassNameLocalServiceUtil.getClassNameId(JournalArticle.class),
			classPK, SystemEventConstants.TYPE_DELETE);

		Assert.assertNotNull(systemEvent);
	}

	private ExportImportConfiguration _exportImportConfiguration;
	private Group _liveGroup;

	@Inject
	private Portal _portal;

	private int _serverPort;
	private Group _stagingGroup;

}