/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.system.event.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationFactory;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.StagingLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.Serializable;

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
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	public long doTestRemoteStaging() throws Exception {
		try (SafeCloseable safeCloseable1 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"TUNNELING_SERVLET_SHARED_SECRET",
					"F0E1D2C3B4A5968778695A4B3C2D1E0F");
			SafeCloseable safeCloseable2 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"TUNNELING_SERVLET_SHARED_SECRET_HEX", true)) {

			_stagingGroup = GroupTestUtil.addGroup();

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);
			serviceContext.setScopeGroupId(_stagingGroup.getGroupId());

			Map<String, Serializable> attributes =
				serviceContext.getAttributes();

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

			UserTestUtil.setUser(TestPropsValues.getUser());

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
				JournalArticleLocalServiceUtil.getArticles(
					_liveGroup.getGroupId());

			Assert.assertEquals(articles.toString(), 1, articles.size());

			JournalArticleLocalServiceUtil.deleteArticle(
				_stagingGroup.getGroupId(), journalArticle.getArticleId(),
				new ServiceContext());

			Assert.assertNotNull(
				SystemEventLocalServiceUtil.fetchSystemEvent(
					_stagingGroup.getGroupId(),
					ClassNameLocalServiceUtil.getClassNameId(
						JournalArticle.class),
					journalArticle.getResourcePrimKey(),
					SystemEventConstants.TYPE_DELETE));

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
	}

	@Before
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

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
		catch (NoSuchGroupException noSuchGroupException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchGroupException);
			}
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

		Assert.assertNull(
			SystemEventLocalServiceUtil.fetchSystemEvent(
				_liveGroup.getGroupId(),
				ClassNameLocalServiceUtil.getClassNameId(JournalArticle.class),
				firstJournalArticle.getResourcePrimKey(),
				SystemEventConstants.TYPE_DELETE));
	}

	@Test
	public void testRemoteStaging1() throws Exception {
		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"STAGING_LIVE_GROUP_REMOTE_STAGING_ENABLED", false)) {

			long classPK = doTestRemoteStaging();

			Assert.assertNull(
				SystemEventLocalServiceUtil.fetchSystemEvent(
					_liveGroup.getGroupId(),
					ClassNameLocalServiceUtil.getClassNameId(
						JournalArticle.class),
					classPK, SystemEventConstants.TYPE_DELETE));
		}
	}

	@Test
	public void testRemoteStaging2() throws Exception {
		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"STAGING_LIVE_GROUP_REMOTE_STAGING_ENABLED", true)) {

			long classPK = doTestRemoteStaging();

			Assert.assertNotNull(
				SystemEventLocalServiceUtil.fetchSystemEvent(
					_liveGroup.getGroupId(),
					ClassNameLocalServiceUtil.getClassNameId(
						JournalArticle.class),
					classPK, SystemEventConstants.TYPE_DELETE));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SystemEventTest.class);

	private ExportImportConfiguration _exportImportConfiguration;
	private Group _liveGroup;

	@Inject
	private Portal _portal;

	private int _serverPort;
	private Group _stagingGroup;

}