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

package com.liferay.portlet.layoutsetprototypes.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutFriendlyURL;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.LayoutFriendlyURLLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.LayoutTestUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Daniela Zapata Riesco
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class LayoutSetPrototypeStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		LayoutSetPrototype layoutSetPrototype =
			LayoutTestUtil.addLayoutSetPrototype(
				ServiceTestUtil.randomString());

		List<Layout> layoutSetPrototypeLayouts =
			LayoutLocalServiceUtil.getLayouts(
				layoutSetPrototype.getGroup().getGroupId(), true,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		Assert.assertEquals(1, layoutSetPrototypeLayouts.size());

		_addDependentLayout(
			LayoutSetPrototype.class, layoutSetPrototypeLayouts.get(0));

		_addDependentLayoutFriendlyURLs(
			LayoutSetPrototype.class,
			layoutSetPrototypeLayouts.get(0).getPlid());

		LayoutPrototype layoutPrototype = _addLayoutPrototype(
			dependentStagedModelsMap);

		Layout layoutSetPrototypeLayout = LayoutTestUtil.addLayout(
			layoutSetPrototype.getGroup().getGroupId(),
			ServiceTestUtil.randomString(), true, layoutPrototype, true);

		_addDependentLayout(LayoutSetPrototype.class, layoutSetPrototypeLayout);

		_addDependentLayoutFriendlyURLs(
			LayoutSetPrototype.class, layoutSetPrototypeLayout.getPlid());

		return layoutSetPrototype;
	}

	@Override
	protected void deleteStagedModel(
			StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		LayoutSetPrototypeLocalServiceUtil.deleteLayoutSetPrototype(
			(LayoutSetPrototype)stagedModel);

		List<StagedModel> dependentLayoutSetPrototypeStagedModels =
			dependentStagedModelsMap.get(LayoutPrototype.class.getSimpleName());

		LayoutPrototype layoutPrototype =
			(LayoutPrototype)dependentLayoutSetPrototypeStagedModels.get(0);

		LayoutPrototypeLocalServiceUtil.deleteLayoutPrototype(layoutPrototype);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return LayoutSetPrototypeLocalServiceUtil.
				fetchLayoutSetPrototypeByUuidAndCompanyId(
					uuid, group.getCompanyId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return LayoutSetPrototype.class;
	}

	@Override
	protected void validateImport(
			StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		LayoutSetPrototype importedLayoutSetPrototype =
			(LayoutSetPrototype)getStagedModel(stagedModel.getUuid(), group);

		Assert.assertNotNull(importedLayoutSetPrototype);

		LayoutPrototype importedLayoutPrototype =
			_retrieveImportedLayoutPrototype(dependentStagedModelsMap, group);

		Layout layoutSetPrototypeLayoutFromLAR =
			_retrieveLayoutSetPrototypeLayoutsFromLAR(stagedModel);

		_validateLayouts(
			importedLayoutSetPrototype, importedLayoutPrototype,
			layoutSetPrototypeLayoutFromLAR);
	}

	private void _addDependentLayout(Class<?> clazz, Layout layout)
		throws SystemException {

		List<Layout> dependentLayouts = _dependentLayoutsMap.get(
			clazz.getSimpleName());

		if (dependentLayouts == null) {
			dependentLayouts = new ArrayList<Layout>();

			_dependentLayoutsMap.put(clazz.getSimpleName(), dependentLayouts);
		}

		dependentLayouts.add(layout);

		UnicodeProperties typeSettings = layout.getTypeSettingsProperties();
		typeSettings.setProperty("layoutPrototypeExportTest", "true");

		LayoutLocalServiceUtil.updateLayout(layout);
	}

	private void _addDependentLayoutFriendlyURLs(Class<?> clazz, long plid)
		throws SystemException {

		List<LayoutFriendlyURL> dependentLayoutFriendlyURLs =
			_dependentLayoutFriendlyURLsMap.get(clazz.getSimpleName());

		if (dependentLayoutFriendlyURLs == null) {
			dependentLayoutFriendlyURLs = new ArrayList<LayoutFriendlyURL>();

			_dependentLayoutFriendlyURLsMap.put(
				clazz.getSimpleName(), dependentLayoutFriendlyURLs);
		}

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLs(plid);

		Assert.assertEquals(1, layoutFriendlyURLs.size());

		dependentLayoutFriendlyURLs.add(layoutFriendlyURLs.get(0));
	}

	private LayoutPrototype _addLayoutPrototype(
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		LayoutPrototype layoutPrototype = LayoutTestUtil.addLayoutPrototype(
			ServiceTestUtil.randomString());

		addDependentStagedModel(
			dependentStagedModelsMap, LayoutPrototype.class, layoutPrototype);

		List<Layout> layoutPrototypeLayouts =
			LayoutLocalServiceUtil.getLayouts(
				layoutPrototype.getGroupId(), true,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		Assert.assertEquals(1, layoutPrototypeLayouts.size());

		addDependentStagedModel(
			dependentStagedModelsMap, Layout.class,
			layoutPrototypeLayouts.get(0));

		_addDependentLayout(
			LayoutPrototype.class, layoutPrototypeLayouts.get(0));

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLs(
				layoutPrototypeLayouts.get(0).getPlid());

		Assert.assertEquals(1, layoutFriendlyURLs.size());

		addDependentStagedModel(
			dependentStagedModelsMap, LayoutFriendlyURL.class,
			layoutFriendlyURLs.get(0));

		_addDependentLayoutFriendlyURLs(
			LayoutPrototype.class, layoutPrototypeLayouts.get(0).getPlid());

		return layoutPrototype;
	}

	private List<LayoutFriendlyURL> _getDependentLayoutFriendlyURLs(
		Class<?> clazz) {
		return _dependentLayoutFriendlyURLsMap.get(clazz.getSimpleName());
	}

	private List<Layout> _getDependentLayouts(Class<?> clazz) {
		return _dependentLayoutsMap.get(clazz.getSimpleName());
	}

	private LayoutPrototype _retrieveImportedLayoutPrototype(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws SystemException {

		List<StagedModel> dependentLayoutPrototypeStagedModels =
			dependentStagedModelsMap.get(LayoutPrototype.class.getSimpleName());

		Assert.assertEquals(1, dependentLayoutPrototypeStagedModels.size());

		LayoutPrototype layoutPrototype =
			(LayoutPrototype)dependentLayoutPrototypeStagedModels.get(0);

		LayoutPrototype importedLayoutPrototype =
			LayoutPrototypeLocalServiceUtil.
				fetchLayoutPrototypeByUuidAndCompanyId(
					layoutPrototype.getUuid(), group.getCompanyId());

		Assert.assertNotNull(importedLayoutPrototype);

		return importedLayoutPrototype;
	}

	private Layout _retrieveLayoutSetPrototypeLayoutsFromLAR(
			StagedModel stagedModel)
		throws DocumentException, IOException {

		String path = ExportImportPathUtil.getModelPath(
			stagedModel, _LAR_FILE_NAME);

		InputStream larFile = portletDataContext.getZipEntryAsInputStream(path);

		ZipReader larZipReader = ZipReaderFactoryUtil.getZipReader(larFile);

		String manifest = larZipReader.getEntryAsString("manifest.xml");

		Document manifestDocument = SAXReaderUtil.read(manifest);

		List<Element> stagedModelDataElements =
			manifestDocument.getRootElement().element("Layout").elements();

		List<Layout> importedLayoutSetPrototypeLayouts = new ArrayList<Layout>(
			stagedModelDataElements.size());

		for (Element stagedModelDataElement : stagedModelDataElements) {
			String layoutPrototypeUuid = stagedModelDataElement.attributeValue(
				"layout-prototype-uuid");

			if (Validator.isNotNull(layoutPrototypeUuid)) {
				String layoutPath = stagedModelDataElement.attributeValue(
					"path");

				Layout layoutSetPrototypeLayout =
					(Layout)portletDataContext.fromXML(
						larZipReader.getEntryAsString(layoutPath));

				importedLayoutSetPrototypeLayouts.add(layoutSetPrototypeLayout);
			}
		}

		Assert.assertEquals(1, importedLayoutSetPrototypeLayouts.size());

		return importedLayoutSetPrototypeLayouts.get(0);
	}

	private void _validateLayouts(
			LayoutSetPrototype importedLayoutSetPrototype,
			LayoutPrototype importedLayoutPrototype,
			Layout layoutSetPrototypeLayout)
		throws PortalException, SystemException {

		_validatePrototypeLayouts(
			LayoutSetPrototype.class,
			importedLayoutSetPrototype.getGroup().getGroupId());

		_validatePrototypeLayouts(
			LayoutPrototype.class, importedLayoutPrototype.getGroupId());

		Assert.assertNotNull(layoutSetPrototypeLayout.getLayoutPrototypeUuid());

		Layout importedLayoutSetPrototypeLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				layoutSetPrototypeLayout.getUuid(),
				importedLayoutSetPrototype.getGroup().getGroupId(), true);

		Assert.assertNotNull(importedLayoutSetPrototypeLayout);

		Assert.assertEquals(
			importedLayoutSetPrototype.getGroup().getGroupId(),
			importedLayoutSetPrototypeLayout.getGroupId());

		Assert.assertEquals(
			importedLayoutPrototype.getUuid(),
			importedLayoutSetPrototypeLayout.getLayoutPrototypeUuid());
	}

	private void _validatePrototypeLayouts(Class<?> clazz, long groupId)
		throws SystemException {

		List<Layout> dependentLayouts = _getDependentLayouts(clazz);

		for (Layout dependentLayout : dependentLayouts) {
			Layout importedDependentLayout =
				LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
					dependentLayout.getUuid(), groupId,
					dependentLayout.getPrivateLayout());

			Assert.assertNotNull(importedDependentLayout);

			Assert.assertEquals(
				dependentLayout.getTypeSettingsProperty(
					"layoutPrototypeExportTest"),
				importedDependentLayout.getTypeSettingsProperty(
					"layoutPrototypeExportTest"));
		}

		List<LayoutFriendlyURL> dependentLayoutFriendlyURLs =
			_getDependentLayoutFriendlyURLs(clazz);

		for (LayoutFriendlyURL dependentLayoutFriendlyURL :
			dependentLayoutFriendlyURLs) {
			LayoutFriendlyURL importedLayoutFriendlyURL =
				LayoutFriendlyURLLocalServiceUtil.
					fetchLayoutFriendlyURLByUuidAndGroupId(
						dependentLayoutFriendlyURL.getUuid(), groupId);

			Assert.assertNotNull(importedLayoutFriendlyURL);
		}
	}

	private static final String _LAR_FILE_NAME = "layout.lar";

	private Map<String, List<Layout>> _dependentLayoutsMap =
		new HashMap<String, List<Layout>>();

	private Map<String, List<LayoutFriendlyURL>>
		_dependentLayoutFriendlyURLsMap =
			new HashMap<String, List<LayoutFriendlyURL>>();

}