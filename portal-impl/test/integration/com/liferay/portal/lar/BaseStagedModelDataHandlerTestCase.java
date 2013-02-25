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
import com.liferay.portal.kernel.util.Validator;
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
import com.liferay.portal.xml.ElementImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.dom4j.DocumentHelper;

import org.junit.After;
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

		_liveGroup = GroupTestUtil.addGroup();
		_stagingGroup = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_liveGroup);

		GroupLocalServiceUtil.deleteGroup(_stagingGroup);
	}

	@Test
	@Transactional
	public void testStagedModelDataHandler() throws Exception {
		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		PortletDataContext portletDataContext = new PortletDataContextImpl(
			_stagingGroup.getCompanyId(), _stagingGroup.getGroupId(),
			getParameterMap(), new HashSet<String>(), getStartDate(),
			getEndDate(), zipWriter);

		// Export

		Map<String, List<StagedModel>> dependentStagedModels =
			addDependentStagedModels(_stagingGroup);

		StagedModel stagedModel = addStagedModel(
			_stagingGroup, dependentStagedModels);

		Element[] stagedModelsParentElements =
			getDependentStagedModelsParentElements(dependentStagedModels);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, stagedModelsParentElements, stagedModel);

		// Validate Export

		validateExport(
			stagedModel, dependentStagedModels, stagedModelsParentElements);

		// Import

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(
			zipWriter.getFile());

		UserIdStrategy userIdStrategy = new CurrentUserIdStrategy(
			TestPropsValues.getUser());

		portletDataContext = new PortletDataContextImpl(
			_liveGroup.getCompanyId(), _liveGroup.getGroupId(),
			getParameterMap(), new HashSet<String>(), userIdStrategy,
			zipReader);

		portletDataContext.setSourceGroupId(_stagingGroup.getGroupId());

		Element importElement = getImportedStagedModelElement(
			stagedModel, stagedModelsParentElements);

		Assert.assertNotNull(importElement);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, importElement);

		// Validate import

		validateImport(stagedModel, dependentStagedModels, _liveGroup);
	}

	protected Map<String, List<StagedModel>> addDependentStagedModels(
			Group group)
		throws Exception {

		return new HashMap<String, List<StagedModel>>();
	}

	protected abstract StagedModel addStagedModel(
			Group group, Map<String, List<StagedModel>> dependentStagedModels)
		throws Exception;

	protected Element[] getDependentStagedModelsParentElements(
		Map<String, List<StagedModel>> dependentStagedModels) {

		List<Element> stagedModelElements = new ArrayList<Element>();

		for (String className : dependentStagedModels.keySet()) {
			Element dependentStagedModelElement = new ElementImpl(
				DocumentHelper.createElement(className));

			stagedModelElements.add(dependentStagedModelElement);
		}

		if (!dependentStagedModels.containsKey(getStagedModelClassName())) {
			Element dependentStagedModelElement = new ElementImpl(
				DocumentHelper.createElement(getStagedModelClassName()));

			stagedModelElements.add(dependentStagedModelElement);
		}

		return stagedModelElements.toArray(
			new Element[stagedModelElements.size()]);
	}

	protected Date getEndDate() {
		return new Date();
	}

	protected Element getImportedStagedModelElement(
		StagedModel stagedModel, Element[] stagedModelElements) {

		Element rootElement = new ElementImpl(
			DocumentHelper.createElement("root"));

		for (Element element : stagedModelElements) {
			rootElement.add(element);
		}

		StringBundler sb = new StringBundler(6);

		sb.append(getStagedModelClassName());
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(getXMLElementName());
		sb.append("[@path='");
		sb.append(StagedModelPathUtil.getPath(stagedModel));
		sb.append("']");

		XPath xPathSelector = SAXReaderUtil.createXPath(sb.toString());

		Element stagedModelElement = (Element)xPathSelector.selectSingleNode(
			rootElement);

		return stagedModelElement;
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

	protected abstract String getXMLElementName();

	protected void validateDependentImportedStagedModels(
			Map<String, List<StagedModel>> dependentStagedModels, Group group)
		throws Exception {

		return;
	}

	protected void validateExport(
			StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Element[] stagedModelsParentElements)
		throws Exception {

		for (Element stagedModelsParentElement : stagedModelsParentElements) {
			String className = stagedModelsParentElement.getName();

			List<StagedModel> stagedModelList = dependentStagedModelsMap.get(
				className);

			if (stagedModelList == null) {
				stagedModelList = new ArrayList<StagedModel>();
			}
			else {
				stagedModelList = ListUtil.copy(stagedModelList);
			}

			if (className.equals(getStagedModelClassName())) {
				stagedModelList.add(stagedModel);
			}

			List<Element> stagedModelElements =
				stagedModelsParentElement.elements();

			Assert.assertEquals(
				"The number of exported elements in the xml is not right",
				stagedModelList.size(), stagedModelElements.size());

			for (Element stagedModelElement : stagedModelElements) {
				String path = stagedModelElement.attributeValue("path");

				if (Validator.isNull(path)) {
					Assert.fail(
						"Path is not defined for an element in the exported " +
							"xml");
				}

				Iterator<StagedModel> iterator = stagedModelList.iterator();

				while (iterator.hasNext()) {
					StagedModel curModel = iterator.next();

					String curPath = StagedModelPathUtil.getPath(curModel);

					if (path.equals(curPath)) {
						iterator.remove();
					}
				}
			}

			if (!stagedModelList.isEmpty()) {
				Assert.fail(
					"There is more than one element exported with the same " +
						"path");
			}
		}
	}

	protected void validateImport(
			StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		StagedModel importedModel = getStagedModel(
			stagedModel.getUuid(), group);

		Assert.assertNotNull(importedModel);

		validateDependentImportedStagedModels(dependentStagedModelsMap, group);
	}

	private Group _liveGroup;
	private Group _stagingGroup;

}