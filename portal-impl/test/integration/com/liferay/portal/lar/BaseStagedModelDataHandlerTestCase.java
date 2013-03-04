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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelPathUtil;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
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
import java.util.HashSet;
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

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		PortletDataContext portletDataContext = new PortletDataContextImpl(
			stagingGroup.getCompanyId(), stagingGroup.getGroupId(),
			getParameterMap(), new HashSet<String>(), getStartDate(),
			getEndDate(), zipWriter);

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			addDependentStagedModels(stagingGroup);

		StagedModel stagedModel = addStagedModel(
			stagingGroup, dependentStagedModelsMap);

		Element[] dependentStagedModelsElements =
			getDependentStagedModelsElements(dependentStagedModelsMap);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, dependentStagedModelsElements, stagedModel);

		validateExport(
			stagedModel, dependentStagedModelsMap,
			dependentStagedModelsElements);

		// Import

		UserIdStrategy userIdStrategy = new CurrentUserIdStrategy(
			TestPropsValues.getUser());

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(
			zipWriter.getFile());

		portletDataContext = new PortletDataContextImpl(
			liveGroup.getCompanyId(), liveGroup.getGroupId(), getParameterMap(),
			new HashSet<String>(), userIdStrategy, zipReader);

		portletDataContext.setSourceGroupId(stagingGroup.getGroupId());

		Element importedStagedModelElement = getImportedStagedModelElement(
			stagedModel, dependentStagedModelsElements);

		Assert.assertNotNull(importedStagedModelElement);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, importedStagedModelElement);

		validateImport(stagedModel, dependentStagedModelsMap, liveGroup);
	}

	protected Map<String, List<StagedModel>> addDependentStagedModels(
			Group group)
		throws Exception {

		return new HashMap<String, List<StagedModel>>();
	}

	protected abstract StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception;

	protected Element[] getDependentStagedModelsElements(
		Map<String, List<StagedModel>> dependentStagedModelsMap) {

		List<Element> dependentStagedModelsElements = new ArrayList<Element>();

		for (String className : dependentStagedModelsMap.keySet()) {
			Element dependentStagedModelsElement = SAXReaderUtil.createElement(
				className);

			dependentStagedModelsElements.add(dependentStagedModelsElement);
		}

		if (!dependentStagedModelsMap.containsKey(getStagedModelClassName())) {
			Element dependentStagedModelsElement = SAXReaderUtil.createElement(
				getStagedModelClassName());

			dependentStagedModelsElements.add(dependentStagedModelsElement);
		}

		return dependentStagedModelsElements.toArray(
			new Element[dependentStagedModelsElements.size()]);
	}

	protected abstract String getElementName();

	protected Date getEndDate() {
		return new Date();
	}

	protected Element getImportedStagedModelElement(
		StagedModel stagedModel, Element[] dependentStagedModelsElements) {

		Element rootElement = SAXReaderUtil.createElement("root");

		for (Element dependentStagedModelsElement :
				dependentStagedModelsElements) {

			rootElement.add(dependentStagedModelsElement);
		}

		StringBundler sb = new StringBundler(6);

		sb.append(getStagedModelClassName());
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(getElementName());
		sb.append("[@path='");
		sb.append(StagedModelPathUtil.getPath(stagedModel));
		sb.append("']");

		XPath xPath = SAXReaderUtil.createXPath(sb.toString());

		return (Element)xPath.selectSingleNode(rootElement);
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

	protected abstract String getStagedModelClassName();

	protected Date getStartDate() {
		return new Date(System.currentTimeMillis() - Time.HOUR);
	}

	protected void validateExport(
			StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Element[] dependentStagedModelsElements)
		throws Exception {

		for (Element dependentStagedModelsElement :
				dependentStagedModelsElements) {

			String className = dependentStagedModelsElement.getName();

			List<StagedModel> dependentStagedModels =
				dependentStagedModelsMap.get(className);

			if (dependentStagedModels == null) {
				dependentStagedModels = new ArrayList<StagedModel>();
			}
			else {
				dependentStagedModels = ListUtil.copy(dependentStagedModels);
			}

			if (className.equals(getStagedModelClassName())) {
				dependentStagedModels.add(stagedModel);
			}

			List<Element> elements = dependentStagedModelsElement.elements();

			Assert.assertEquals(dependentStagedModels.size(), elements.size());

			for (Element element : elements) {
				String path = element.attributeValue("path");

				Assert.assertNotNull(path);

				Iterator<StagedModel> iterator =
					dependentStagedModels.iterator();

				while (iterator.hasNext()) {
					StagedModel dependentStagedModel = iterator.next();

					String dependentStagedModelPath =
						StagedModelPathUtil.getPath(dependentStagedModel);

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
	protected Group stagingGroup;

}