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

package com.liferay.portlet.layoutsetprototypes.lar;

import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.lar.test.BaseStagedModelDataHandlerTestCase;
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
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Daniela Zapata Riesco
 */
public class LayoutSetPrototypeStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			TransactionalTestRule.INSTANCE);

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		_layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.
				fetchLayoutSetPrototypeByUuidAndCompanyId(
					_layoutSetPrototype.getUuid(),
					_layoutSetPrototype.getCompanyId());

		LayoutSetPrototypeLocalServiceUtil.deleteLayoutSetPrototype(
			_layoutSetPrototype);

		_layoutPrototype =
			LayoutPrototypeLocalServiceUtil.
				fetchLayoutPrototypeByUuidAndCompanyId(
					_layoutPrototype.getUuid(),
					_layoutPrototype.getCompanyId());

		LayoutPrototypeLocalServiceUtil.deleteLayoutPrototype(_layoutPrototype);
	}

	protected void addLayout(Class<?> clazz, Layout layout) throws Exception {
		List<Layout> layouts = _layouts.get(clazz.getSimpleName());

		if (layouts == null) {
			layouts = new ArrayList<>();

			_layouts.put(clazz.getSimpleName(), layouts);
		}

		layouts.add(layout);

		UnicodeProperties typeSettings = layout.getTypeSettingsProperties();

		typeSettings.setProperty(
			LayoutSetPrototypeStagedModelDataHandlerTest.class.getName(),
			Boolean.TRUE.toString());

		LayoutLocalServiceUtil.updateLayout(layout);
	}

	protected void addLayoutFriendlyURLs(Class<?> clazz, long plid)
		throws Exception {

		List<LayoutFriendlyURL> layoutFriendlyURLs = _layoutFriendlyURLs.get(
			clazz.getSimpleName());

		if (layoutFriendlyURLs == null) {
			layoutFriendlyURLs = new ArrayList<>();

			_layoutFriendlyURLs.put(clazz.getSimpleName(), layoutFriendlyURLs);
		}

		List<LayoutFriendlyURL> layoutLayoutFriendlyURLs =
			LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLs(plid);

		Assert.assertEquals(1, layoutLayoutFriendlyURLs.size());

		layoutFriendlyURLs.add(layoutLayoutFriendlyURLs.get(0));
	}

	protected LayoutPrototype addLayoutPrototype(
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		_layoutPrototype = LayoutTestUtil.addLayoutPrototype(
			RandomTestUtil.randomString());

		addDependentStagedModel(
			dependentStagedModelsMap, LayoutPrototype.class, _layoutPrototype);

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			_layoutPrototype.getGroupId(), true,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		Assert.assertEquals(1, layouts.size());

		Layout layout = layouts.get(0);

		addDependentStagedModel(dependentStagedModelsMap, Layout.class, layout);

		addLayout(LayoutPrototype.class, layout);

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLs(
				layout.getPlid());

		Assert.assertEquals(1, layoutFriendlyURLs.size());

		addDependentStagedModel(
			dependentStagedModelsMap, LayoutFriendlyURL.class,
			layoutFriendlyURLs.get(0));

		addLayoutFriendlyURLs(LayoutPrototype.class, layout.getPlid());

		return _layoutPrototype;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		_layoutSetPrototype = LayoutTestUtil.addLayoutSetPrototype(
			RandomTestUtil.randomString());

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			_layoutSetPrototype.getGroupId(), true,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		Assert.assertEquals(1, layouts.size());

		Layout layout = layouts.get(0);

		addLayout(LayoutSetPrototype.class, layout);
		addLayoutFriendlyURLs(LayoutSetPrototype.class, layout.getPlid());

		LayoutPrototype layoutPrototype = addLayoutPrototype(
			dependentStagedModelsMap);

		Layout prototypedLayout = LayoutTestUtil.addLayout(
			_layoutSetPrototype.getGroupId(), true, layoutPrototype, true);

		addLayout(LayoutSetPrototype.class, prototypedLayout);
		addLayoutFriendlyURLs(
			LayoutSetPrototype.class, prototypedLayout.getPlid());

		return _layoutSetPrototype;
	}

	@Override
	protected void deleteStagedModel(
			StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		LayoutSetPrototypeLocalServiceUtil.deleteLayoutSetPrototype(
			(LayoutSetPrototype)stagedModel);

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			LayoutPrototype.class.getSimpleName());

		LayoutPrototype layoutPrototype =
			(LayoutPrototype)dependentStagedModels.get(0);

		LayoutPrototypeLocalServiceUtil.deleteLayoutPrototype(layoutPrototype);
	}

	protected LayoutPrototype getImportedLayoutPrototype(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

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

	protected List<LayoutFriendlyURL> getLayoutFriendlyURLs(Class<?> clazz) {
		return _layoutFriendlyURLs.get(clazz.getSimpleName());
	}

	protected List<Layout> getLayouts(Class<?> clazz) {
		return _layouts.get(clazz.getSimpleName());
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

	protected Layout importLayoutFromLAR(StagedModel stagedModel)
		throws DocumentException, IOException {

		LayoutSetPrototypeStagedModelDataHandler
			layoutSetPrototypeStagedModelDataHandler =
				new LayoutSetPrototypeStagedModelDataHandler();

		String fileName =
			layoutSetPrototypeStagedModelDataHandler.
				getLayoutSetPrototypeLARFileName(
					(LayoutSetPrototype)stagedModel);

		String modelPath = ExportImportPathUtil.getModelPath(
			stagedModel, fileName);

		InputStream inputStream = portletDataContext.getZipEntryAsInputStream(
			modelPath);

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(inputStream);

		Document document = SAXReaderUtil.read(
			zipReader.getEntryAsString("manifest.xml"));

		Element rootElement = document.getRootElement();

		Element layoutElement = rootElement.element("Layout");

		List<Element> elements = layoutElement.elements();

		List<Layout> importedLayouts = new ArrayList<>(elements.size());

		for (Element element : elements) {
			String layoutPrototypeUuid = element.attributeValue(
				"layout-prototype-uuid");

			if (Validator.isNotNull(layoutPrototypeUuid)) {
				String path = element.attributeValue("path");

				Layout layout = (Layout)portletDataContext.fromXML(
					zipReader.getEntryAsString(path));

				importedLayouts.add(layout);
			}
		}

		Assert.assertEquals(1, importedLayouts.size());

		try {
			return importedLayouts.get(0);
		}
		finally {
			zipReader.close();

			StreamUtil.cleanUp(inputStream);
		}
	}

	@Override
	protected void validateImport(
			StagedModel stagedModel, StagedModelAssets stagedModelAssets,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		LayoutSetPrototype importedLayoutSetPrototype =
			(LayoutSetPrototype)getStagedModel(stagedModel.getUuid(), group);

		Assert.assertNotNull(importedLayoutSetPrototype);

		LayoutPrototype importedLayoutPrototype = getImportedLayoutPrototype(
			dependentStagedModelsMap, group);

		Layout importedLayout = importLayoutFromLAR(stagedModel);

		validateLayouts(
			importedLayoutSetPrototype, importedLayoutPrototype,
			importedLayout);
	}

	protected void validateLayouts(
			LayoutSetPrototype importedLayoutSetPrototype,
			LayoutPrototype importedLayoutPrototype,
			Layout layoutSetPrototypeLayout)
		throws Exception {

		validatePrototypedLayouts(
			LayoutSetPrototype.class, importedLayoutSetPrototype.getGroupId());
		validatePrototypedLayouts(
			LayoutPrototype.class, importedLayoutPrototype.getGroupId());

		Assert.assertNotNull(layoutSetPrototypeLayout.getLayoutPrototypeUuid());

		Layout importedLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				layoutSetPrototypeLayout.getUuid(),
				importedLayoutSetPrototype.getGroupId(), true);

		Assert.assertNotNull(importedLayout);
		Assert.assertEquals(
			importedLayoutSetPrototype.getGroupId(),
			importedLayout.getGroupId());
		Assert.assertEquals(
			importedLayoutPrototype.getUuid(),
			importedLayout.getLayoutPrototypeUuid());
	}

	protected void validatePrototypedLayouts(Class<?> clazz, long groupId)
		throws Exception {

		List<Layout> layouts = getLayouts(clazz);

		for (Layout layout : layouts) {
			Layout importedLayout =
				LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
					layout.getUuid(), groupId, layout.getPrivateLayout());

			Assert.assertNotNull(importedLayout);
			Assert.assertEquals(
				layout.getTypeSettingsProperty(
					LayoutSetPrototypeStagedModelDataHandlerTest.class.
						getName()),
				importedLayout.getTypeSettingsProperty(
					LayoutSetPrototypeStagedModelDataHandlerTest.class.
						getName()));
		}

		List<LayoutFriendlyURL> layoutFriendlyURLs = getLayoutFriendlyURLs(
			clazz);

		for (LayoutFriendlyURL layoutFriendlyURL : layoutFriendlyURLs) {
			LayoutFriendlyURL importedLayoutFriendlyURL =
				LayoutFriendlyURLLocalServiceUtil.
					fetchLayoutFriendlyURLByUuidAndGroupId(
						layoutFriendlyURL.getUuid(), groupId);

			Assert.assertNotNull(importedLayoutFriendlyURL);
			Assert.assertEquals(
				layoutFriendlyURL.getFriendlyURL(),
				importedLayoutFriendlyURL.getFriendlyURL());
		}
	}

	private final Map<String, List<LayoutFriendlyURL>> _layoutFriendlyURLs =
		new HashMap<>();
	private LayoutPrototype _layoutPrototype;
	private final Map<String, List<Layout>> _layouts = new HashMap<>();
	private LayoutSetPrototype _layoutSetPrototype;

}