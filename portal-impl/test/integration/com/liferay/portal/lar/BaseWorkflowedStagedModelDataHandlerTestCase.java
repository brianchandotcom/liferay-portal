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

import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.model.WorkflowedModel;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Daniel Kocsis
 */
public abstract class BaseWorkflowedStagedModelDataHandlerTestCase
	extends BaseStagedModelDataHandlerTestCase {

	@Test
	@Transactional
	public void testExportWorkflowedStagedModels() throws Exception {
		initExport();

		List<StagedModel> stagedModels = null;

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

			stagedModels = addWorkflowedStagedModels(stagingGroup);
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}

		for (StagedModel stagedModel : stagedModels) {
			Assert.assertTrue(
				"Staged model is not a workflowed model",
				stagedModel instanceof WorkflowedModel);

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, stagedModel);

			validateWorkflowedExport(portletDataContext, stagedModel);
		}
	}

	protected abstract List<StagedModel> addWorkflowedStagedModels(Group group)
		throws Exception;

	protected Element getExportStagedModelElement(
		PortletDataContext portletDataContext, StagedModel stagedModel) {

		Element rootElement = portletDataContext.getExportDataRootElement();

		Class<?> modelClass = stagedModel.getModelClass();

		Element modelElement = rootElement.element(modelClass.getSimpleName());

		Assert.assertNotNull("Unable to find model element", modelElement);

		XPath xPath = SAXReaderUtil.createXPath(
			"staged-model[@path ='" +
				ExportImportPathUtil.getModelPath(stagedModel) + "']");

		return (Element)xPath.selectSingleNode(modelElement);
	}

	protected void validateWorkflowedExport(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {

		StagedModelDataHandler<?> stagedModelDataHandler =
			StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
				stagedModel.getModelClassName());

		WorkflowedModel workflowedModel = (WorkflowedModel)stagedModel;

		Element exportStagedModelElement = getExportStagedModelElement(
			portletDataContext, stagedModel);

		if (ArrayUtil.contains(
				stagedModelDataHandler.getExportableStatuses(),
				workflowedModel.getStatus())) {

			Assert.assertNotNull(
				"Staged model should be exported", exportStagedModelElement);
		}
		else {
			Assert.assertNull(
				"Staged model should not be exported",
				exportStagedModelElement);
		}
	}

}