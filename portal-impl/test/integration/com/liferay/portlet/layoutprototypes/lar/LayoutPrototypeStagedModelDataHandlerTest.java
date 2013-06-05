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

package com.liferay.portlet.layoutprototypes.lar;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutFriendlyURL;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.LayoutFriendlyURLLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.LayoutTestUtil;

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
public class LayoutPrototypeStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		LayoutPrototype layoutPrototype = LayoutTestUtil.addLayoutPrototype(
			ServiceTestUtil.randomString());

		Layout layout = layoutPrototype.getLayout();

		UnicodeProperties typeSettings = layout.getTypeSettingsProperties();
		typeSettings.setProperty("layoutPrototypeExportTest", "true");

		LayoutLocalServiceUtil.updateLayout(layout);

		addDependentStagedModel(dependentStagedModelsMap, Layout.class, layout);

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLs(
				layout.getPlid());

		Assert.assertEquals(1, layoutFriendlyURLs.size());

		addDependentStagedModel(
			dependentStagedModelsMap, LayoutFriendlyURL.class,
			layoutFriendlyURLs.get(0));

		return layoutPrototype;
	}

	@Override
	protected void deleteStagedModel(
			StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		LayoutPrototypeLocalServiceUtil.deleteLayoutPrototype(
			(LayoutPrototype)stagedModel);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return LayoutPrototypeLocalServiceUtil.
				fetchLayoutPrototypeByUuidAndCompanyId(
					uuid, group.getCompanyId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return LayoutPrototype.class;
	}

	@Override
	protected void validateImport(
			StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		LayoutPrototype importedLayoutPrototype =
			(LayoutPrototype)getStagedModel(stagedModel.getUuid(), group);

		Assert.assertNotNull(importedLayoutPrototype);

		List<StagedModel> dependentLayouts = dependentStagedModelsMap.get(
			Layout.class.getSimpleName());

		Assert.assertEquals(1, dependentLayouts.size());

		Layout dependentLayout = (Layout)dependentLayouts.get(0);

		Layout importedLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				dependentLayout.getUuid(), importedLayoutPrototype.getGroupId(),
				dependentLayout.isPrivateLayout());

		Assert.assertNotNull(importedLayout);

		Assert.assertEquals(
			dependentLayout.getTypeSettingsProperty(
				"layoutPrototypeExportTest"),
			importedLayout.getTypeSettingsProperty(
				"layoutPrototypeExportTest"));

		List<StagedModel> layoutFriendlyURLs = dependentStagedModelsMap.get(
			LayoutFriendlyURL.class.getSimpleName());

		LayoutFriendlyURL layoutFriendlyURL =
			(LayoutFriendlyURL)layoutFriendlyURLs.get(0);

		LayoutFriendlyURL importedLayoutFriendlyURL =
			LayoutFriendlyURLLocalServiceUtil.
				fetchLayoutFriendlyURLByUuidAndGroupId(
					layoutFriendlyURL.getUuid(), importedLayout.getGroupId());

		Assert.assertNotNull(importedLayoutFriendlyURL);

		Assert.assertEquals(
			layoutFriendlyURL.getFriendlyURL(),
			importedLayoutFriendlyURL.getFriendlyURL());
	}

}