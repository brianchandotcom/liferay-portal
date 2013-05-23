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

package com.liferay.portal.lar;

import com.liferay.portal.RequiredGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetLinkLocalServiceUtil;

import java.io.File;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Juan Fernández
 */
public class BasePortletExportImportTestCase extends PowerMockito {

	public String getNamespace() {
		return null;
	}

	public String getPortletId() {
		return null;
	}

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();
		importedGroup = GroupTestUtil.addGroup();

		layout = LayoutTestUtil.addLayout(
			group.getGroupId(), ServiceTestUtil.randomString());

		// Delete and readd to ensure a different layout ID (not ID or UUID).
		// See LPS-32132.

		LayoutLocalServiceUtil.deleteLayout(layout, true, new ServiceContext());

		layout = LayoutTestUtil.addLayout(
			group.getGroupId(), ServiceTestUtil.randomString());
	}

	@After
	public void tearDown() throws Exception {
		try {
			GroupLocalServiceUtil.deleteGroup(group);

			if (importedGroup != null) {
				GroupLocalServiceUtil.deleteGroup(importedGroup);
			}
		}
		catch (RequiredGroupException rge) {
		}

		LayoutLocalServiceUtil.deleteLayout(layout);
		LayoutLocalServiceUtil.deleteLayout(importedLayout);

		if ((larFile != null) && larFile.exists()) {
			FileUtil.delete(larFile);
		}
	}

	@Test
	public void testExportImportAssetLinks() throws Exception {
		StagedModel stagedModel = addStagedModel(group.getGroupId());

		StagedModel relatedStagedModel1 = addStagedModel(group.getGroupId());
		StagedModel relatedStagedModel2 = addStagedModel(group.getGroupId());

		addAssetLink(
			group.getGroupId(), getStagedModelUuid(stagedModel),
			getStagedModelUuid(relatedStagedModel1), 1);
		addAssetLink(
			group.getGroupId(), getStagedModelUuid(stagedModel),
			getStagedModelUuid(relatedStagedModel2), 2);

		doExportImportPortlet(getPortletId());

		StagedModel importedStagedModel = getStagedModel(
			getStagedModelUuid(stagedModel), importedGroup.getGroupId());

		Assert.assertNotNull(importedStagedModel);

		validateImportedLinks(getStagedModelUuid(stagedModel));
	}

	protected AssetLink addAssetLink(
			long groupId, String sourceStagedModelUuid,
			String targetStagedModelUuid, int weight)
		throws PortalException, SystemException {

		AssetEntry originAssetEntry = AssetEntryLocalServiceUtil.getEntry(
			groupId, sourceStagedModelUuid);
		AssetEntry targetAssetEntry = AssetEntryLocalServiceUtil.getEntry(
			groupId, targetStagedModelUuid);

		return AssetLinkLocalServiceUtil.addLink(
			TestPropsValues.getUserId(), originAssetEntry.getEntryId(),
			targetAssetEntry.getEntryId(), 0, weight);
	}

	protected void addParameter(
		Map<String, String[]> parameterMap, String name, boolean value) {

		addParameter(parameterMap, getNamespace(), name, value);
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

	protected void doExportImportPortlet(String portletId) throws Exception {
		larFile = LayoutLocalServiceUtil.exportPortletInfoAsFile(
			layout.getPlid(), layout.getGroupId(), portletId,
			getExportParameterMap(), null, null);

		importedLayout = LayoutTestUtil.addLayout(
			importedGroup.getGroupId(), ServiceTestUtil.randomString());

		LayoutLocalServiceUtil.importPortletInfo(
			TestPropsValues.getUserId(), importedLayout.getPlid(),
			importedGroup.getGroupId(), portletId, getImportParameterMap(),
			larFile);
	}

	protected Map<String, String[]> getExportParameterMap() throws Exception {
		Map<String, String[]> parameterMap =
			new LinkedHashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()});

		return parameterMap;
	}

	protected Map<String, String[]> getImportParameterMap() throws Exception {
		Map<String, String[]> parameterMap =
			new LinkedHashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()});

		return parameterMap;
	}

	@SuppressWarnings("unused")
	protected StagedModel getStagedModel(String uuid, long groupId)
		throws PortalException, SystemException {

		return null;
	}

	@SuppressWarnings("unused")
	protected String getStagedModelUuid(StagedModel stagedModel)
		throws PortalException, SystemException {

		return stagedModel.getUuid();
	}

	protected void validateImportedLinks(String uuid)
		throws PortalException, SystemException {

		AssetEntry originalAssetEntry = AssetEntryLocalServiceUtil.getEntry(
			group.getGroupId(), uuid);

		List<AssetLink> originalAssetLinks = AssetLinkLocalServiceUtil.getLinks(
			originalAssetEntry.getEntryId());

		AssetEntry importedAssetEntry =  AssetEntryLocalServiceUtil.getEntry(
			importedGroup.getGroupId(), uuid);

		List<AssetLink> importedAssetLinks = AssetLinkLocalServiceUtil.getLinks(
			importedAssetEntry.getEntryId());

		Assert.assertEquals(
			originalAssetLinks.size(), importedAssetLinks.size());

		for (AssetLink originalLink : originalAssetLinks) {
			AssetEntry sourceAssetEntry = AssetEntryLocalServiceUtil.getEntry(
				originalLink.getEntryId1());

			AssetEntry targetAssetEntry = AssetEntryLocalServiceUtil.getEntry(
				originalLink.getEntryId2());

			Iterator<AssetLink> iterator = importedAssetLinks.iterator();

			while (iterator.hasNext()) {
				AssetLink importedLink = iterator.next();

				AssetEntry importedLinkSourceAssetEntry =
					AssetEntryLocalServiceUtil.getEntry(
						importedLink.getEntryId1());
				AssetEntry importedLinkTargetAssetEntry =
					AssetEntryLocalServiceUtil.getEntry(
						importedLink.getEntryId2());

				if (!sourceAssetEntry.getClassUuid().equals(
						importedLinkSourceAssetEntry.getClassUuid())) {

					continue;
				}

				if (!targetAssetEntry.getClassUuid().equals(
						importedLinkTargetAssetEntry.getClassUuid())) {

					continue;
				}

				Assert.assertEquals(
					originalLink.getWeight(), importedLink.getWeight());
				Assert.assertEquals(
					originalLink.getType(), importedLink.getType());

				iterator.remove();

				break;
			}
		}

		Assert.assertEquals(0, importedAssetLinks.size());
	}

	protected Group group;
	protected Group importedGroup;
	protected Layout importedLayout;
	protected File larFile;
	protected Layout layout;

}