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

package com.liferay.portlet.bookmarks.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.lar.BasePortletExportImportTestCase;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.util.test.BookmarksTestUtil;

import java.util.Map;

import org.junit.runner.RunWith;

/**
 * @author Juan Fernández
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class BookmarksExportImportTest extends BasePortletExportImportTestCase {

	@Override
	public String getNamespace() {
		return BookmarksPortletDataHandler.NAMESPACE;
	}

	@Override
	public String getPortletId() {
		return PortletKeys.BOOKMARKS;
	}

	@Override
	protected StagedModel addStagedModel(long groupId) throws Exception {
		return BookmarksTestUtil.addEntry(groupId, true);
	}

	@Override
	protected void deleteStagedModel(StagedModel stagedModel) throws Exception {
		BookmarksEntryLocalServiceUtil.deleteEntry((BookmarksEntry)stagedModel);
	}

	@Override
	protected Map<String, String[]> getExportParameterMap() throws Exception {
		Map<String, String[]> parameterMap = super.getExportParameterMap();

		addParameter(parameterMap, "entries", true);

		return parameterMap;
	}

	@Override
	protected Map<String, String[]> getImportParameterMap() throws Exception {
		Map<String, String[]> parameterMap = super.getImportParameterMap();

		addParameter(parameterMap, "entries", true);

		return parameterMap;
	}

	@Override
	protected StagedModel getStagedModel(String uuid, long groupId)
		throws PortalException, SystemException {

		return BookmarksEntryLocalServiceUtil.getBookmarksEntryByUuidAndGroupId(
			uuid, groupId);
	}

}