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

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
 * @author Daniel Kocsis
 * @author Mate Thurzo
 */
public abstract class BaseStagedModelDataHandlerTestCase extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		liveGroup = GroupTestUtil.addGroup();
		stagingGroup = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(liveGroup);
		GroupLocalServiceUtil.deleteGroup(stagingGroup);
	}

	@Test
	@Transactional
	public void testStagedModelDataHandler() throws Exception {

		// Export

		initExport();

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			addDependentStagedModelsMap(stagingGroup);

		StagedModel stagedModel = addStagedModel(
			stagingGroup, dependentStagedModelsMap);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, stagedModel);

		validateExport(
			portletDataContext, stagedModel, dependentStagedModelsMap);

		// Import

		initImport();

		// Reread the staged model for import from ZIP for true testing

		StagedModel exportedStagedModel = readExportedStagedModel(stagedModel);

		Assert.assertNotNull(exportedStagedModel);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedStagedModel);

		validateImport(stagedModel, dependentStagedModelsMap, liveGroup);
	}

	protected List<StagedModel> addDependentStagedModel(
		Map<String, List<StagedModel>> dependentStagedModelsMap, Class<?> clazz,
		StagedModel dependentStagedModel) {

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			clazz.getSimpleName());

		if (dependentStagedModels == null) {
			dependentStagedModels = new ArrayList<StagedModel>();

			dependentStagedModelsMap.put(
				clazz.getSimpleName(), dependentStagedModels);
		}

		dependentStagedModels.add(dependentStagedModel);

		return dependentStagedModels;
	}

	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		return new HashMap<String, List<StagedModel>>();
	}

	protected abstract StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception;

	protected Date getEndDate() {
		return new Date();
	}

	protected Map<String, String[]> getParameterMap() {
		Map<String, String[]> parameterMap =
			new LinkedHashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE});
		parameterMap.put(
			PortletDataHandlerKeys.IGNORE_LAST_PUBLISH_DATE,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()});

		return parameterMap;
	}

	protected abstract StagedModel getStagedModel(String uuid, Group group);

	protected abstract Class<? extends StagedModel> getStagedModelClass();

	protected String getStagedModelPath(long groupId, StagedModel stagedModel) {
		return ExportImportPathUtil.getModelPath(stagedModel);
	}

	protected Date getStartDate() {
		return new Date(System.currentTimeMillis() - Time.HOUR);
	}

	protected void initExport() throws Exception {
		zipWriter = ZipWriterFactoryUtil.getZipWriter();

		portletDataContext = new PortletDataContextImpl(
			stagingGroup.getCompanyId(), stagingGroup.getGroupId(),
			getParameterMap(), getStartDate(), getEndDate(), zipWriter);

		rootElement = SAXReaderUtil.createElement("root");

		portletDataContext.setExportDataRootElement(rootElement);
	}

	protected void initImport() throws Exception {
		userIdStrategy = new CurrentUserIdStrategy(TestPropsValues.getUser());
		zipReader = ZipReaderFactoryUtil.getZipReader(zipWriter.getFile());

		portletDataContext = new PortletDataContextImpl(
			liveGroup.getCompanyId(), liveGroup.getGroupId(), getParameterMap(),
			userIdStrategy, zipReader);

		portletDataContext.setImportDataRootElement(rootElement);
		portletDataContext.setSourceGroupId(stagingGroup.getGroupId());
	}

	protected StagedModel readExportedStagedModel(StagedModel stagedModel) {
		String stagedModelPath = getStagedModelPath(
			stagingGroup.getGroupId(), stagedModel);

		StagedModel exportedStagedModel =
			(StagedModel)portletDataContext.getZipEntryAsObject(
				stagedModelPath);

		return exportedStagedModel;
	}

	protected void validateExport(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		Element rootElement = portletDataContext.getExportDataRootElement();

		List<Element> stagedModelGroupElements = rootElement.elements();

		for (Element stagedModelGroupElement : stagedModelGroupElements) {
			String className = stagedModelGroupElement.getName();

			List<StagedModel> dependentStagedModels =
				dependentStagedModelsMap.get(className);

			if (dependentStagedModels == null) {
				dependentStagedModels = new ArrayList<StagedModel>();
			}
			else {
				dependentStagedModels = ListUtil.copy(dependentStagedModels);
			}

			String stagedModelClassSimpleName =
				getStagedModelClass().getSimpleName();

			if (className.equals(stagedModelClassSimpleName)) {
				dependentStagedModels.add(stagedModel);
			}

			List<Element> elements = stagedModelGroupElement.elements();

			Assert.assertEquals(dependentStagedModels.size(), elements.size());

			for (Element element : elements) {
				String path = element.attributeValue("path");

				Assert.assertNotNull(path);

				Iterator<StagedModel> iterator =
					dependentStagedModels.iterator();

				while (iterator.hasNext()) {
					StagedModel dependentStagedModel = iterator.next();

					String dependentStagedModelPath = getStagedModelPath(
						stagingGroup.getGroupId(), dependentStagedModel);

					if (path.equals(dependentStagedModelPath)) {
						iterator.remove();
					}
				}
			}

			Assert.assertTrue(
				"There is more than one element exported with the same path",
				dependentStagedModels.isEmpty());
		}
	}

	protected void validateImport(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {
	}

	protected void validateImport(
			StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		StagedModel importedStagedModel = getStagedModel(
			stagedModel.getUuid(), group);

		Assert.assertNotNull(importedStagedModel);

		validateImport(dependentStagedModelsMap, group);
	}

	protected Group liveGroup;
	protected PortletDataContext portletDataContext;
	protected Element rootElement;
	protected Group stagingGroup;
	protected UserIdStrategy userIdStrategy;
	protected ZipReader zipReader;
	protected ZipWriter zipWriter;

}