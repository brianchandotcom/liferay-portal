/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.search;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.search.BaseSearchTestCase;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.util.MBTestUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class MBMessageSearchTest extends BaseSearchTestCase {

	@Override
	public void testSearchByDDMStructureField() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testSearchComments() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testSearchExpireAllVersions() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testSearchExpireLatestVersion() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testSearchStatus() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testSearchVersions() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testSearchWithinDDMStructure() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	protected void addAttachment(ClassedModel classedModel) throws Exception {
		MBMessage message = (MBMessage)classedModel;

		List<FileEntry> fileEntries = message.getAttachmentsFileEntries();

		List<String> existingFiles = new ArrayList<String>();

		for (FileEntry fileEntry : fileEntries) {
			existingFiles.add(fileEntry.getTitle());
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(message.getGroupId());

		MBMessageLocalServiceUtil.updateMessage(
			TestPropsValues.getUserId(), message.getMessageId(),
			getSearchKeywords(), getSearchKeywords(), _getInputStreamOVPs(),
			existingFiles, 0, false, serviceContext);
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		MBCategory category = (MBCategory)parentBaseModel;

		return MBTestUtil.addMessage(
			category.getCategoryId(), keywords, approved, serviceContext);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return MBMessage.class;
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return MBTestUtil.addCategory(serviceContext);
	}

	@Override
	protected String getSearchKeywords() {
		return "Title";
	}

	private List<ObjectValuePair<String, InputStream>> _getInputStreamOVPs() {
		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			new ArrayList<ObjectValuePair<String, InputStream>>(1);

		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/OSX_Test.docx");

		ObjectValuePair<String, InputStream> inputStreamOVP =
			new ObjectValuePair<String, InputStream>(
				getSearchKeywords(), inputStream);

		inputStreamOVPs.add(inputStreamOVP);

		return inputStreamOVPs;
	}

}