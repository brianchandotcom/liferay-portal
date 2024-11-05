/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.props.test.util.PropsTemporarySwapper;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.test.util.WikiTestUtil;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@RunWith(Arquillian.class)
public class WikiNodeStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	@Test
	public void testCleanStagedModelDataHandler() throws Exception {
		super.testCleanStagedModelDataHandler();

		try (PropsTemporarySwapper propsTemporarySwapper =
				new PropsTemporarySwapper(
					"feature.flag.LPD-35013", Boolean.FALSE.toString())) {

			initExport();

			Map<String, List<StagedModel>> dependentStagedModelsMap =
				addDependentStagedModelsMap(stagingGroup);

			StagedModel stagedModel = addStagedModel(
				stagingGroup, dependentStagedModelsMap);

			addComments(stagedModel);

			addRatings(stagedModel);

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, stagedModel);

			validateExport(
				portletDataContext, stagedModel, dependentStagedModelsMap);

			initImport();

			deleteStagedModel(
				stagedModel, dependentStagedModelsMap, stagingGroup);

			StagedModel exportedStagedModel = readExportedStagedModel(
				stagedModel);

			Assert.assertNull(exportedStagedModel);
		}
	}

	@Override
	@Test
	public void testExportImportWithDefaultData() throws Exception {
		super.testExportImportWithDefaultData();

		try (PropsTemporarySwapper propsTemporarySwapper =
				new PropsTemporarySwapper(
					"feature.flag.LPD-35013", Boolean.FALSE.toString())) {

			initExport();

			Map<String, List<StagedModel>> defaultDependentStagedModelsMap =
				addDefaultDependentStagedModelsMap(stagingGroup);

			StagedModel stagedModel = addDefaultStagedModel(
				stagingGroup, defaultDependentStagedModelsMap);

			if (stagedModel == null) {
				return;
			}

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, stagedModel);

			validateExport(
				portletDataContext, stagedModel,
				defaultDependentStagedModelsMap);

			Map<String, List<StagedModel>> secondDependentStagedModelsMap =
				addDefaultDependentStagedModelsMap(liveGroup);

			addDefaultStagedModel(liveGroup, secondDependentStagedModelsMap);

			initImport();

			StagedModel exportedStagedModel = readExportedStagedModel(
				stagedModel);

			Assert.assertNull(exportedStagedModel);
		}
	}

	@Override
	@Test
	public void testStagedModelDataHandler() throws Exception {
		super.testStagedModelDataHandler();

		try (PropsTemporarySwapper propsTemporarySwapper =
				new PropsTemporarySwapper(
					"feature.flag.LPD-35013", Boolean.FALSE.toString())) {

			initExport();

			Map<String, List<StagedModel>> dependentStagedModelsMap =
				addDependentStagedModelsMap(stagingGroup);

			StagedModel stagedModel = addStagedModel(
				stagingGroup, dependentStagedModelsMap);

			addComments(stagedModel);

			addRatings(stagedModel);

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, stagedModel);

			validateExport(
				portletDataContext, stagedModel, dependentStagedModelsMap);

			initImport();

			StagedModel exportedStagedModel = readExportedStagedModel(
				stagedModel);

			Assert.assertNull(exportedStagedModel);
		}
	}

	@Override
	protected StagedModel addDefaultStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		WikiNode wikiNode = WikiNodeLocalServiceUtil.fetchNode(
			group.getGroupId(), "Main");

		if (wikiNode != null) {
			return wikiNode;
		}

		return WikiTestUtil.addDefaultNode(group.getGroupId());
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		return WikiTestUtil.addNode(group.getGroupId());
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return WikiNodeLocalServiceUtil.getWikiNodeByUuidAndGroupId(
			uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return WikiNode.class;
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		WikiNode node = (WikiNode)stagedModel;
		WikiNode importedNode = (WikiNode)importedStagedModel;

		Assert.assertEquals(
			node.getExternalReferenceCode(),
			importedNode.getExternalReferenceCode());
		Assert.assertEquals(node.getName(), importedNode.getName());
		Assert.assertEquals(
			node.getDescription(), importedNode.getDescription());
		Assert.assertEquals(
			node.getLastPostDate(), importedNode.getLastPostDate());
	}

}