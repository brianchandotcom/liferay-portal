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

package com.liferay.exportimport.test.util.lar;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetLinkLocalServiceUtil;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.lar.ExportImportClassedModelUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.exportimport.kernel.service.ExportImportServiceUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.File;
import java.io.Serializable;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

/**
 * @author Eduardo García
 */
public abstract class BaseExportImportTestCase {

	public void importLayouts(Map<String, String[]> parameterMap)
		throws Exception {

		User user = TestPropsValues.getUser();

		Map<String, Serializable> importLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildImportLayoutSettingsMap(
					user, importedGroup.getGroupId(), false, null,
					parameterMap);

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addExportImportConfiguration(
					user.getUserId(), importedGroup.getGroupId(),
					StringPool.BLANK, StringPool.BLANK,
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					importLayoutSettingsMap, WorkflowConstants.STATUS_DRAFT,
					new ServiceContext());

		ExportImportServiceUtil.importLayouts(
			exportImportConfiguration, larFile);
	}

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();
		importedGroup = GroupTestUtil.addGroup();

		layout = LayoutTestUtil.addLayout(group);

		// Delete and readd to ensure a different layout ID (not ID or UUID).
		// See LPS-32132.

		LayoutLocalServiceUtil.deleteLayout(layout, true, new ServiceContext());

		layout = LayoutTestUtil.addLayout(group);
	}

	@After
	public void tearDown() throws Exception {
		if ((larFile != null) && larFile.exists()) {
			FileUtil.delete(larFile);
		}
	}

	protected AssetLink addAssetLink(
			StagedModel sourceStagedModel, StagedModel targetStagedModel,
			int weight)
		throws PortalException {

		AssetEntry originAssetEntry = getAssetEntry(sourceStagedModel);
		AssetEntry targetAssetEntry = getAssetEntry(targetStagedModel);

		return AssetLinkLocalServiceUtil.addLink(
			TestPropsValues.getUserId(), originAssetEntry.getEntryId(),
			targetAssetEntry.getEntryId(), 0, weight);
	}

	protected void addParameter(
		Map<String, String[]> parameterMap, String name, String value) {

		parameterMap.put(name, new String[] {value});
	}

	protected void addParameter(
		Map<String, String[]> parameterMap, String namespace, String name,
		boolean value) {

		PortletDataHandlerBoolean portletDataHandlerBoolean =
			new PortletDataHandlerBoolean(namespace, name);

		addParameter(
			parameterMap, portletDataHandlerBoolean.getNamespacedControlName(),
			String.valueOf(value));
	}

	protected StagedModel addStagedModel(long groupId) throws Exception {
		return null;
	}

	protected StagedModel addStagedModel(long groupId, Date createdDate)
		throws Exception {

		return null;
	}

	protected void deleteStagedModel(StagedModel stagedModel) throws Exception {
	}

	protected void exportImportLayouts(
			long[] layoutIds, Map<String, String[]> parameterMap)
		throws Exception {

		exportLayouts(layoutIds, getExportParameterMap());

		importLayouts(parameterMap);
	}

	protected void exportLayouts(
			long[] layoutIds, Map<String, String[]> parameterMap)
		throws Exception {

		User user = TestPropsValues.getUser();

		Map<String, Serializable> exportLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildExportLayoutSettingsMap(
					user, group.getGroupId(), false, layoutIds, parameterMap);

		ExportImportConfiguration exportImportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addDraftExportImportConfiguration(
					user.getUserId(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					exportLayoutSettingsMap);

		larFile = ExportImportServiceUtil.exportLayoutsAsFile(
			exportImportConfiguration);
	}

	protected AssetEntry getAssetEntry(StagedModel stagedModel)
		throws PortalException {

		return AssetEntryLocalServiceUtil.getEntry(
			ExportImportClassedModelUtil.getClassName(stagedModel),
			ExportImportClassedModelUtil.getClassPK(stagedModel));
	}

	protected Map<String, String[]> getExportParameterMap() throws Exception {
		Map<String, String[]> parameterMap = new LinkedHashMap<>();

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()});

		return parameterMap;
	}

	protected Map<String, String[]> getImportParameterMap() throws Exception {
		Map<String, String[]> parameterMap = new LinkedHashMap<>();

		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE
			});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()});

		return parameterMap;
	}

	protected StagedModel getStagedModel(String uuid, long groupId)
		throws PortalException {

		return null;
	}

	protected String getStagedModelUuid(StagedModel stagedModel)
		throws PortalException {

		return stagedModel.getUuid();
	}

	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		Assert.assertTrue(
			stagedModel.getCreateDate() + " " +
				importedStagedModel.getCreateDate(),
			DateUtil.equals(
				stagedModel.getCreateDate(),
				importedStagedModel.getCreateDate()));
		Assert.assertTrue(
			stagedModel.getModifiedDate() + " " +
				importedStagedModel.getModifiedDate(),
			DateUtil.equals(
				stagedModel.getModifiedDate(),
				importedStagedModel.getModifiedDate()));
		Assert.assertEquals(
			stagedModel.getUuid(), importedStagedModel.getUuid());
	}

	@DeleteAfterTestRun
	protected Group group;

	@DeleteAfterTestRun
	protected Group importedGroup;

	protected Layout importedLayout;
	protected File larFile;
	protected Layout layout;

}