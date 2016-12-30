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

package com.liferay.document.library.subscription.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.randomizerbumpers.TikaSafeRandomizerBumper;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestUtil;
import com.liferay.portlet.subscriptions.test.BaseSubscriptionClassTypeTestCase;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
@Sync
public class DLSubscriptionClassTypeTest
	extends BaseSubscriptionClassTypeTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@Override
	protected long addBaseModelWithClassType(
			long containerModelId, long classTypeId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		DLAppTestUtil.populateNotificationsServiceContext(
			serviceContext, Constants.ADD);
		DLAppTestUtil.populateServiceContext(serviceContext, classTypeId);

		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), group.getGroupId(), containerModelId,
			RandomTestUtil.randomString() + ".txt", ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomBytes(TikaSafeRandomizerBumper.INSTANCE),
			serviceContext);

		return fileEntry.getFileEntryId();
	}

	@Override
	protected long addClassType() throws Exception {
		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			DLFileEntryMetadata.class.getName());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntryType fileEntryType =
			DLFileEntryTypeLocalServiceUtil.addFileEntryType(
				TestPropsValues.getUserId(), group.getGroupId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				new long[] {ddmStructure.getStructureId()}, serviceContext);

		return fileEntryType.getFileEntryTypeId();
	}

	@Override
	protected void addSubscriptionClassType(long classTypeId) throws Exception {
		DLAppLocalServiceUtil.subscribeFileEntryType(
			user.getUserId(), group.getGroupId(), classTypeId);
	}

	@Override
	protected void deleteSubscriptionClassType(long classTypeId)
		throws Exception {

		DLAppLocalServiceUtil.unsubscribeFileEntryType(
			user.getUserId(), group.getGroupId(), classTypeId);
	}

	@Override
	protected Long getDefaultClassTypeId() throws Exception {
		DLFileEntryType basicEntryType =
			DLFileEntryTypeLocalServiceUtil.getDLFileEntryType(
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);

		Assert.assertNotNull(basicEntryType);

		return basicEntryType.getPrimaryKey();
	}

	@Override
	protected void updateBaseModel(long userId, long baseModelId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), userId);

		DLAppTestUtil.populateNotificationsServiceContext(
			serviceContext, Constants.UPDATE);

		DLAppLocalServiceUtil.updateFileEntry(
			userId, baseModelId, RandomTestUtil.randomString(),
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, false,
			RandomTestUtil.randomBytes(TikaSafeRandomizerBumper.INSTANCE),
			serviceContext);
	}

}