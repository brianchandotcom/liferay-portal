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

package com.liferay.change.tracking.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class LayoutCTTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_ctCollection = _ctCollectionLocalService.addCTCollection(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			LayoutCTTest.class.getName(), null);
		_group = GroupTestUtil.addGroup();
		_layoutClassNameId = _classNameLocalService.getClassNameId(
			Layout.class);
	}

	@Test
	public void testAddLayout() throws Exception {
		Layout layout = null;

		try (SafeCloseable safeCloseable1 =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			layout = LayoutTestUtil.addTypePortletLayout(_group);

			Assert.assertEquals(
				layout, _layoutLocalService.fetchLayout(layout.getPlid()));

			try (SafeCloseable safeCloseable2 =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						CTConstants.CT_COLLECTION_ID_PRODUCTION)) {

				Assert.assertNull(
					_layoutLocalService.fetchLayout(layout.getPlid()));
			}
		}

		CTEntry ctEntry = _ctEntryLocalService.fetchCTEntry(
			_ctCollection.getCtCollectionId(), _layoutClassNameId,
			layout.getPlid());

		Assert.assertNotNull(ctEntry);

		Assert.assertEquals(
			CTConstants.CT_CHANGE_TYPE_ADDITION, ctEntry.getChangeType());
	}

	@Test
	public void testDeleteCTCollectionAdd() throws Exception {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			LayoutTestUtil.addTypePortletLayout(_group);
		}

		_ctCollectionLocalService.deleteCTCollection(_ctCollection);

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select * from Layout where ctCollectionId = " +
					_ctCollection.getCtCollectionId());
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertFalse(resultSet.next());
		}
		finally {
			_ctCollection = null;
		}
	}

	@Test
	public void testDeleteCTCollectionModify() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			layout.setTitle(RandomTestUtil.randomString());

			_layoutLocalService.updateLayout(layout);
		}

		_ctCollectionLocalService.deleteCTCollection(_ctCollection);

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select * from Layout where ctCollectionId = " +
					_ctCollection.getCtCollectionId());
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertFalse(resultSet.next());
		}
		finally {
			_ctCollection = null;
		}
	}

	@Test
	public void testDeleteCTCollectionPublishDelete() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			_layoutLocalService.deleteLayout(layout);
		}

		_ctProcessLocalService.addCTProcess(
			_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

		_ctCollectionLocalService.deleteCTCollection(_ctCollection);

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select * from Layout where ctCollectionId = " +
					_ctCollection.getCtCollectionId());
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertFalse(resultSet.next());
		}
		finally {
			_ctCollection = null;
		}
	}

	@Test
	public void testDeleteCTCollectionPublishModify() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			layout.setTitle(RandomTestUtil.randomString());

			_layoutLocalService.updateLayout(layout);
		}

		_ctProcessLocalService.addCTProcess(
			_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

		_ctCollectionLocalService.deleteCTCollection(_ctCollection);

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select * from Layout where ctCollectionId = " +
					_ctCollection.getCtCollectionId());
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertFalse(resultSet.next());
		}
		finally {
			_ctCollection = null;
		}
	}

	@Test
	public void testModifyLayout() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		String originalFriendlyURL = layout.getFriendlyURL();

		try (SafeCloseable safeCloseable1 =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			Assert.assertEquals(
				layout, _layoutLocalService.fetchLayout(layout.getPlid()));

			layout.setFriendlyURL("/testModifyLayout");

			layout = _layoutLocalService.updateLayout(layout);

			try (SafeCloseable safeCloseable2 =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						CTConstants.CT_COLLECTION_ID_PRODUCTION)) {

				Layout productionLayout = _layoutLocalService.fetchLayout(
					layout.getPlid());

				Assert.assertEquals(
					originalFriendlyURL, productionLayout.getFriendlyURL());
			}
		}

		CTEntry ctEntry = _ctEntryLocalService.fetchCTEntry(
			_ctCollection.getCtCollectionId(), _layoutClassNameId,
			layout.getPlid());

		Assert.assertNotNull(ctEntry);

		Assert.assertEquals(
			CTConstants.CT_CHANGE_TYPE_MODIFICATION, ctEntry.getChangeType());
	}

	@Test
	public void testModifyLayoutWithPagination() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		String description = layout.getDescription();

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			String ctDescription = RandomTestUtil.randomString();

			layout.setDescription(ctDescription);

			layout = _layoutLocalService.updateLayout(layout);

			List<Layout> layouts = _layoutLocalService.getLayouts(
				_group.getGroupId(), layout.isPrivateLayout(), 0, 2, null);

			Assert.assertEquals(layouts.toString(), 1, layouts.size());

			layout = layouts.get(0);

			Assert.assertEquals(ctDescription, layout.getDescription());
		}

		List<Layout> layouts = _layoutLocalService.getLayouts(
			_group.getGroupId(), layout.isPrivateLayout(), 0, 2, null);

		Assert.assertEquals(layouts.toString(), 1, layouts.size());

		layout = layouts.get(0);

		Assert.assertEquals(description, layout.getDescription());

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			Layout newLayout = LayoutTestUtil.addTypePortletLayout(_group);

			layouts = _layoutLocalService.getLayouts(
				_group.getGroupId(), layout.isPrivateLayout(), 1, 2,
				OrderByComparatorFactoryUtil.create("Layout", "plid", true));

			Assert.assertEquals(layouts.toString(), 1, layouts.size());

			Assert.assertEquals(newLayout, layouts.get(0));
		}
	}

	@Test
	public void testPublishAvoidsConstraintViolationsWithAddRemove()
		throws Exception {

		Layout layout1 = LayoutTestUtil.addTypePortletLayout(_group);

		String friendlyURL = layout1.getFriendlyURL();

		Layout layout2 = null;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			_layoutLocalService.deleteLayout(layout1);

			layout2 = LayoutTestUtil.addTypePortletLayout(_group);

			layout2.setFriendlyURL(friendlyURL);

			layout2 = _layoutLocalService.updateLayout(layout2);
		}

		_ctProcessLocalService.addCTProcess(
			_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

		Assert.assertNull(_layoutLocalService.fetchLayout(layout1.getPlid()));

		layout2 = _layoutLocalService.fetchLayout(layout2.getPlid());

		Assert.assertNotNull(layout2);

		Assert.assertEquals(friendlyURL, layout2.getFriendlyURL());
	}

	@Test
	public void testPublishAvoidsConstraintViolationsWithModifications()
		throws Exception {

		Layout layout1 = LayoutTestUtil.addTypePortletLayout(_group);
		Layout layout2 = LayoutTestUtil.addTypePortletLayout(_group);

		String friendlyURLA = layout1.getFriendlyURL();
		String friendlyURLB = layout2.getFriendlyURL();

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			layout1.setFriendlyURL("/friendlyURLSwap");

			layout1 = _layoutLocalService.updateLayout(layout1);

			layout2.setFriendlyURL(friendlyURLA);

			layout2 = _layoutLocalService.updateLayout(layout2);

			layout1.setFriendlyURL(friendlyURLB);

			layout1 = _layoutLocalService.updateLayout(layout1);
		}

		_ctProcessLocalService.addCTProcess(
			_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

		layout1 = _layoutLocalService.fetchLayout(layout1.getPlid());

		Assert.assertNotNull(layout1);

		layout2 = _layoutLocalService.fetchLayout(layout2.getPlid());

		Assert.assertNotNull(layout2);

		Assert.assertEquals(friendlyURLB, layout1.getFriendlyURL());

		Assert.assertEquals(friendlyURLA, layout2.getFriendlyURL());
	}

	@Test
	public void testPublishCTEntriesValues() throws Exception {
		Layout deletedLayout = LayoutTestUtil.addTypePortletLayout(_group);
		Layout modifiedLayout = LayoutTestUtil.addTypePortletLayout(_group);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			LayoutTestUtil.addTypePortletLayout(_group);

			_layoutLocalService.deleteLayout(deletedLayout);

			modifiedLayout.setFriendlyURL("/testModifyLayout");

			_layoutLocalService.updateLayout(modifiedLayout);
		}

		_ctProcessLocalService.addCTProcess(
			_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select changeType from CTEntry inner join Layout on ",
					"CTEntry.modelClassNameId = ",
					_classNameLocalService.getClassNameId(Layout.class),
					" and CTEntry.modelClassPK = Layout.plid and ",
					"CTEntry.modelMvccVersion = Layout.mvccVersion and ",
					"CTEntry.ctCollectionId = Layout.ctCollectionId where ",
					"CTEntry.ctCollectionId = ",
					_ctCollection.getCtCollectionId(),
					" order by ctEntryId ASC"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertTrue(resultSet.next());

			Assert.assertEquals(
				CTConstants.CT_CHANGE_TYPE_DELETION,
				resultSet.getLong("changeType"));

			Assert.assertFalse(resultSet.next());
		}

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select changeType from CTEntry inner join Layout on ",
					"CTEntry.modelClassNameId = ",
					_classNameLocalService.getClassNameId(Layout.class),
					" and CTEntry.modelClassPK = Layout.plid and ",
					"CTEntry.modelMvccVersion = Layout.mvccVersion where ",
					"CTEntry.ctCollectionId = ",
					_ctCollection.getCtCollectionId(),
					" and Layout.ctCollectionId = ",
					CTConstants.CT_COLLECTION_ID_PRODUCTION,
					" order by ctEntryId ASC"));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertTrue(resultSet.next());

			Assert.assertEquals(
				CTConstants.CT_CHANGE_TYPE_ADDITION,
				resultSet.getLong("changeType"));

			Assert.assertTrue(resultSet.next());

			Assert.assertEquals(
				CTConstants.CT_CHANGE_TYPE_MODIFICATION,
				resultSet.getLong("changeType"));

			Assert.assertFalse(resultSet.next());
		}
	}

	@Test
	public void testPublishLayoutWithAssetTag() throws Exception {
		String tagName1 = "layoutcttesttag1";
		String tagName2 = "layoutcttesttag2";

		Layout layout1 = LayoutTestUtil.addTypePortletLayout(_group);
		Layout layout2 = LayoutTestUtil.addTypePortletLayout(_group);

		_layoutLocalService.updateAsset(
			layout1.getUserId(), layout1, null, new String[] {tagName1});

		AssetEntry assetEntry1 = _assetEntryLocalService.getEntry(
			Layout.class.getName(), layout1.getPlid());
		AssetEntry assetEntry2 = _assetEntryLocalService.getEntry(
			Layout.class.getName(), layout2.getPlid());

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			_layoutLocalService.updateAsset(
				layout1.getUserId(), layout1, null, new String[] {tagName2});

			List<AssetTag> assetTags = _assetTagLocalService.getEntryTags(
				assetEntry1.getEntryId());

			Assert.assertEquals(assetTags.toString(), 1, assetTags.size());

			AssetTag assetTag = assetTags.get(0);

			Assert.assertEquals(tagName2, assetTag.getName());

			assetTags = _assetTagLocalService.getEntryTags(
				assetEntry2.getEntryId());

			Assert.assertTrue(assetTags.toString(), assetTags.isEmpty());
		}

		List<AssetTag> assetTags = _assetTagLocalService.getEntryTags(
			assetEntry1.getEntryId());

		Assert.assertEquals(assetTags.toString(), 1, assetTags.size());

		AssetTag assetTag = assetTags.get(0);

		Assert.assertEquals(tagName1, assetTag.getName());

		_ctProcessLocalService.addCTProcess(
			_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

		assetTags = _assetTagLocalService.getEntryTags(
			assetEntry1.getEntryId());

		Assert.assertEquals(assetTags.toString(), 1, assetTags.size());

		assetTag = assetTags.get(0);

		Assert.assertEquals(tagName2, assetTag.getName());
		Assert.assertEquals(1, assetTag.getAssetCount());
	}

	@Test
	public void testPublishLayoutWithConflictingConstraints() throws Exception {
		String friendlyURL = "/testModifyLayout";

		Layout layout1 = LayoutTestUtil.addTypePortletLayout(_group);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			layout1.setFriendlyURL(friendlyURL);

			layout1 = _layoutLocalService.updateLayout(layout1);
		}

		Layout layout2 = LayoutTestUtil.addTypePortletLayout(_group);

		layout2.setFriendlyURL(friendlyURL);

		layout2 = _layoutLocalService.updateLayout(layout2);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.background.task.internal.messaging." +
					"BackgroundTaskMessageListener",
				LoggerTestUtil.ERROR)) {

			_ctProcessLocalService.addCTProcess(
				_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Unable to execute background task", logEntry.getMessage());
		}

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			Layout layout3 = _layoutLocalService.fetchLayout(layout1.getPlid());

			Assert.assertNotNull(layout3);

			Assert.assertEquals(layout3.getFriendlyURL(), friendlyURL);
		}

		Layout layout4 = _layoutLocalService.fetchLayout(layout2.getPlid());

		Assert.assertNotNull(layout4);

		Assert.assertEquals(layout4.getFriendlyURL(), friendlyURL);
	}

	@Test
	public void testPublishLayoutWithConflictingUpdate() throws Exception {
		String ctFriendlyURL = "/testCTLayout";
		String conflictingFriendlyURL = "/testConflictingLayout";

		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			layout.setFriendlyURL(ctFriendlyURL);

			_layoutLocalService.updateLayout(layout);
		}

		layout.setFriendlyURL(conflictingFriendlyURL);

		layout = _layoutLocalService.updateLayout(layout);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.background.task.internal.messaging." +
					"BackgroundTaskMessageListener",
				LoggerTestUtil.ERROR)) {

			_ctProcessLocalService.addCTProcess(
				_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Throwable throwable = logEntry.getThrowable();

			Assert.assertNotNull(throwable);

			String message = throwable.getMessage();

			Assert.assertTrue(message, message.startsWith("Unable to publish"));
		}

		layout = _layoutLocalService.fetchLayout(layout.getPlid());

		Assert.assertNotNull(layout);

		Assert.assertEquals(layout.getFriendlyURL(), conflictingFriendlyURL);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			layout = _layoutLocalService.fetchLayout(layout.getPlid());

			Assert.assertNotNull(layout);

			Assert.assertEquals(layout.getFriendlyURL(), ctFriendlyURL);
		}
	}

	@Test
	public void testPublishModifiedLayout() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			layout.setFriendlyURL("/testModifyLayout");

			layout = _layoutLocalService.updateLayout(layout);
		}

		_ctProcessLocalService.addCTProcess(
			_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

		Layout productionLayout = _layoutLocalService.fetchLayout(
			layout.getPlid());

		Assert.assertNotNull(productionLayout);

		Assert.assertEquals(
			layout.getFriendlyURL(), productionLayout.getFriendlyURL());
	}

	@Test
	public void testPublishModifiedLayoutMergeableConflict() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		String title = layout.getTitle();

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			layout = _layoutLocalService.updateLayout(layout);
		}

		layout = _layoutLocalService.getLayout(layout.getPlid());

		layout.setTitle(RandomTestUtil.randomString());

		layout = _layoutLocalService.updateLayout(layout);

		_ctProcessLocalService.addCTProcess(
			_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

		Layout productionLayout = _layoutLocalService.fetchLayout(
			layout.getPlid());

		Assert.assertNotNull(productionLayout);

		Assert.assertEquals(title, productionLayout.getTitle());
	}

	@Test
	public void testPublishModifiedLayoutWithIgnorable() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		Date modifiedDate = layout.getModifiedDate();

		modifiedDate = new Date((modifiedDate.getTime() / 1000) * 1000);

		layout.setModifiedDate(modifiedDate);

		layout = _layoutLocalService.updateLayout(layout);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			layout.setModifiedDate(
				new Date(modifiedDate.getTime() + Time.HOUR));

			layout = _layoutLocalService.updateLayout(layout);
		}

		modifiedDate = layout.getModifiedDate();

		_ctProcessLocalService.addCTProcess(
			_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

		Layout productionLayout = _layoutLocalService.fetchLayout(
			layout.getPlid());

		Assert.assertNotNull(productionLayout);

		Assert.assertEquals(modifiedDate, productionLayout.getModifiedDate());
	}

	@Test
	public void testPublishModifiedLayoutWithIgnorableConflict()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		Date modifiedDate = layout.getModifiedDate();

		modifiedDate = new Date((modifiedDate.getTime() / 1000) * 1000);

		layout.setModifiedDate(modifiedDate);

		layout = _layoutLocalService.updateLayout(layout);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			layout.setModifiedDate(
				new Date(modifiedDate.getTime() + Time.HOUR));

			layout = _layoutLocalService.updateLayout(layout);
		}

		layout = _layoutLocalService.getLayout(layout.getPlid());

		layout.setModifiedDate(
			new Date(modifiedDate.getTime() + (2 * Time.HOUR)));

		layout = _layoutLocalService.updateLayout(layout);

		modifiedDate = layout.getModifiedDate();

		_ctProcessLocalService.addCTProcess(
			_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

		Layout productionLayout = _layoutLocalService.fetchLayout(
			layout.getPlid());

		Assert.assertNotNull(productionLayout);

		Assert.assertEquals(modifiedDate, productionLayout.getModifiedDate());
	}

	@Test
	public void testPublishModifiedLayoutWithMergeableChange()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		String title = RandomTestUtil.randomString();

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			layout.setTitle(title);

			layout = _layoutLocalService.updateLayout(layout);
		}

		_ctProcessLocalService.addCTProcess(
			_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

		Layout productionLayout = _layoutLocalService.fetchLayout(
			layout.getPlid());

		Assert.assertNotNull(productionLayout);

		Assert.assertEquals(title, productionLayout.getTitle());
	}

	@Test
	public void testPublishModifiedLayoutWithTargetDeleted() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			layout.setFriendlyURL("/testModifyLayout");

			layout = _layoutLocalService.updateLayout(layout);
		}

		_layoutLocalService.deleteLayout(layout);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.background.task.internal.messaging." +
					"BackgroundTaskMessageListener",
				LoggerTestUtil.ERROR)) {

			_ctProcessLocalService.addCTProcess(
				_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Throwable throwable = logEntry.getThrowable();

			Assert.assertNotNull(throwable);

			String message = throwable.toString();

			Assert.assertTrue(message, message.contains("Unable to publish "));
		}
	}

	@Test
	public void testPublishNewLayout() throws Exception {
		Layout layout = null;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			layout = LayoutTestUtil.addTypePortletLayout(_group);
		}

		_ctProcessLocalService.addCTProcess(
			_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

		Assert.assertEquals(
			layout, _layoutLocalService.fetchLayout(layout.getPlid()));
	}

	@Test
	public void testPublishRemovedLayout() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			_layoutLocalService.deleteLayout(layout);
		}

		_ctProcessLocalService.addCTProcess(
			_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

		Assert.assertNull(_layoutLocalService.fetchLayout(layout.getPlid()));
	}

	@Test
	public void testRemoveLayout() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			layout = _layoutLocalService.deleteLayout(layout);

			Assert.assertNull(
				_layoutLocalService.fetchLayout(layout.getPlid()));

			try (SafeCloseable safeCloseable2 =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						CTConstants.CT_COLLECTION_ID_PRODUCTION)) {

				Assert.assertEquals(
					layout, _layoutLocalService.fetchLayout(layout.getPlid()));
			}
		}

		CTEntry ctEntry = _ctEntryLocalService.fetchCTEntry(
			_ctCollection.getCtCollectionId(), _layoutClassNameId,
			layout.getPlid());

		Assert.assertNotNull(ctEntry);

		Assert.assertEquals(
			CTConstants.CT_CHANGE_TYPE_DELETION, ctEntry.getChangeType());
	}

	@Test
	public void testScratchedAddThenDelete() throws Exception {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

			try (Connection connection = DataAccess.getConnection();
				PreparedStatement preparedStatement =
					connection.prepareStatement(
						"select ctCollectionId from Layout where plid = " +
							layout.getPlid());
				ResultSet resultSet = preparedStatement.executeQuery()) {

				Assert.assertTrue(resultSet.next());

				Assert.assertEquals(
					_ctCollection.getCtCollectionId(),
					resultSet.getLong("ctCollectionId"));

				Assert.assertFalse(resultSet.next());
			}

			_layoutLocalService.deleteLayout(layout);

			CTEntry ctEntry = _ctEntryLocalService.fetchCTEntry(
				_ctCollection.getCtCollectionId(),
				_classNameLocalService.getClassNameId(Layout.class),
				layout.getPlid());

			Assert.assertNull(ctEntry);

			try (Connection connection = DataAccess.getConnection();
				PreparedStatement preparedStatement =
					connection.prepareStatement(
						"select * from Layout where plid = " +
							layout.getPlid());
				ResultSet resultSet = preparedStatement.executeQuery()) {

				Assert.assertFalse(resultSet.next());
			}
		}
	}

	@Test
	public void testScratchedModifyThenDelete() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			layout.setTitle(RandomTestUtil.randomString());

			layout = _layoutLocalService.updateLayout(layout);

			try (Connection connection = DataAccess.getConnection();
				PreparedStatement preparedStatement =
					connection.prepareStatement(
						"select COUNT(*) from Layout where plid = " +
							layout.getPlid());
				ResultSet resultSet = preparedStatement.executeQuery()) {

				Assert.assertTrue(resultSet.next());

				Assert.assertEquals(2, resultSet.getLong(1));

				Assert.assertFalse(resultSet.next());
			}

			_layoutLocalService.deleteLayout(layout);

			CTEntry ctEntry = _ctEntryLocalService.fetchCTEntry(
				_ctCollection.getCtCollectionId(),
				_classNameLocalService.getClassNameId(Layout.class),
				layout.getPlid());

			Assert.assertNotNull(ctEntry);

			Assert.assertEquals(
				CTConstants.CT_CHANGE_TYPE_DELETION, ctEntry.getChangeType());

			try (Connection connection = DataAccess.getConnection();
				PreparedStatement preparedStatement =
					connection.prepareStatement(
						"select ctCollectionId from Layout where plid = " +
							layout.getPlid());
				ResultSet resultSet = preparedStatement.executeQuery()) {

				Assert.assertTrue(resultSet.next());

				Assert.assertEquals(
					CTConstants.CT_COLLECTION_ID_PRODUCTION,
					resultSet.getLong("ctCollectionId"));

				Assert.assertFalse(resultSet.next());
			}
		}
	}

	@Inject
	private static AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private static AssetTagLocalService _assetTagLocalService;

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTEntryLocalService _ctEntryLocalService;

	@Inject
	private static CTProcessLocalService _ctProcessLocalService;

	private static long _layoutClassNameId;

	@Inject
	private static LayoutLocalService _layoutLocalService;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@DeleteAfterTestRun
	private Group _group;

}