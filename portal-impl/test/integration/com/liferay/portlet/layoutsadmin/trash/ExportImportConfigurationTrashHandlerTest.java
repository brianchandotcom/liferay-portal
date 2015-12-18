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

package com.liferay.portlet.layoutsadmin.trash;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationConstants;
import com.liferay.portlet.exportimport.model.ExportImportConfiguration;
import com.liferay.portlet.exportimport.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portlet.layoutsadmin.util.test.ExportImportConfigurationTestUtil;
import com.liferay.portlet.trash.test.BaseTrashHandlerTestCase;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Levente Hudak
 */
@Sync
public class ExportImportConfigurationTrashHandlerTest
	extends BaseTrashHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, ServiceContext serviceContext)
		throws Exception {

		Group group = (Group)parentBaseModel;

		return ExportImportConfigurationTestUtil.addExportImportConfiguration(
			group.getGroupId(),
			ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT);
	}

	@Override
	protected BaseModel<?> getBaseModel(long primaryKey) throws Exception {
		return ExportImportConfigurationLocalServiceUtil.
			getExportImportConfiguration(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return ExportImportConfiguration.class;
	}

	@Override
	protected int getNotInTrashBaseModelsCount(BaseModel<?> parentBaseModel)
		throws Exception {

		return ExportImportConfigurationLocalServiceUtil.
			getExportImportConfigurationsCount(
				(Long)parentBaseModel.getPrimaryKeyObj());
	}

	@Override
	protected String getUniqueTitle(BaseModel<?> baseModel) {
		return null;
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		ExportImportConfigurationLocalServiceUtil.
			moveExportImportConfigurationToTrash(
				TestPropsValues.getUserId(), primaryKey);
	}

}