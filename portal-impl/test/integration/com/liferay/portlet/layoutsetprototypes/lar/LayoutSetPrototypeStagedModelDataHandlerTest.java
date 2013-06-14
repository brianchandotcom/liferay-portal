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

		List<Layout> layouts =
			LayoutLocalServiceUtil.getLayouts(
				layoutSetPrototype.getGroup().getGroupId(), true,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		Assert.assertEquals(1, layouts.size());

		Layout layout = layouts.get(0);

		_addDependentLayout(LayoutSetPrototype.class, layout);

		_addDependentLayoutFriendlyURLs(
			LayoutSetPrototype.class, layout.getPlid());

		LayoutPrototype layoutPrototype = _addLayoutPrototype(
			dependentStagedModelsMap);

		Layout layoutPrototypeBasedLayout = LayoutTestUtil.addLayout(
			layoutSetPrototype.getGroup().getGroupId(),
			ServiceTestUtil.randomString(), true, layoutPrototype, true);

		_addDependentLayout(
			LayoutSetPrototype.class, layoutPrototypeBasedLayout);

		_addDependentLayoutFriendlyURLs(
			LayoutSetPrototype.class, layoutPrototypeBasedLayout.getPlid());

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

		Layout importedLayout = _importLayoutFromLAR(stagedModel);

		_validateLayouts(
			importedLayoutSetPrototype, importedLayoutPrototype,
			importedLayout);
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

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			layoutPrototype.getGroupId(), true,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		Assert.assertEquals(1, layouts.size());

		Layout layout = layouts.get(0);

		addDependentStagedModel(dependentStagedModelsMap, Layout.class, layout);

		_addDependentLayout(LayoutPrototype.class, layout);

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLs(
				layout.getPlid());

		Assert.assertEquals(1, layoutFriendlyURLs.size());

		addDependentStagedModel(
			dependentStagedModelsMap, LayoutFriendlyURL.class,
			layoutFriendlyURLs.get(0));

		_addDependentLayoutFriendlyURLs(
			LayoutPrototype.class, layout.getPlid());

		return layoutPrototype;
	}

	private List<LayoutFriendlyURL> _getDependentLayoutFriendlyURLs(
		Class<?> clazz) {

		return _dependentLayoutFriendlyURLsMap.get(clazz.getSimpleName());
	}

	private List<Layout> _getDependentLayouts(Class<?> clazz) {
		return _dependentLayoutsMap.get(clazz.getSimpleName());
	}

	private Layout _importLayoutFromLAR(StagedModel stagedModel)
		throws DocumentException, IOException {

		LayoutSetPrototypeStagedModelDataHandler
			layoutSetPrototypeStagedModelDataHandler =
			new LayoutSetPrototypeStagedModelDataHandler();

		String fileName =
			layoutSetPrototypeStagedModelDataHandler.
				getLayoutSetPrototypeLarFileName(
					(LayoutSetPrototype)stagedModel);

		String modelPath = ExportImportPathUtil.getModelPath(
			stagedModel, fileName);

		InputStream larFile = portletDataContext.getZipEntryAsInputStream(
			modelPath);

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(larFile);

		String manifest = zipReader.getEntryAsString("manifest.xml");

		Document document = SAXReaderUtil.read(manifest);

		List<Element> layoutElements =
			document.getRootElement().element("Layout").elements();

		List<Layout> importedLayouts = new ArrayList<Layout>(
			layoutElements.size());

		for (Element layoutElement : layoutElements) {
			String layoutPrototypeUuid = layoutElement.attributeValue(
				"layout-prototype-uuid");

			if (Validator.isNotNull(layoutPrototypeUuid)) {
				String path = layoutElement.attributeValue("path");

				String entryString = zipReader.getEntryAsString(path);

				Layout layout = (Layout)portletDataContext.fromXML(entryString);

				importedLayouts.add(layout);
			}
		}

		Assert.assertEquals(1, importedLayouts.size());

		return importedLayouts.get(0);
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

		Layout importedLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				layoutSetPrototypeLayout.getUuid(),
				importedLayoutSetPrototype.getGroup().getGroupId(), true);

		Assert.assertNotNull(importedLayout);

		Assert.assertEquals(
			importedLayoutSetPrototype.getGroup().getGroupId(),
			importedLayout.getGroupId());

		Assert.assertEquals(
			importedLayoutPrototype.getUuid(),
			importedLayout.getLayoutPrototypeUuid());
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

	private Map<String, List<Layout>> _dependentLayoutsMap =
		new HashMap<String, List<Layout>>();

	private Map<String, List<LayoutFriendlyURL>>
		_dependentLayoutFriendlyURLsMap =
			new HashMap<String, List<LayoutFriendlyURL>>();

}