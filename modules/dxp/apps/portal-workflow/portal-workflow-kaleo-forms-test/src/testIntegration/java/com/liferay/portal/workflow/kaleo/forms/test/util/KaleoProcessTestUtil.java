/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.kaleo.forms.test.util;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoTaskFormPair;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoTaskFormPairs;
import com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessLocalServiceUtil;

/**
 * @author Inácio Nery
 */
public class KaleoProcessTestUtil {

	public static KaleoProcess addKaleoProcess(String className)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			className);

		long classNameId = PortalUtil.getClassNameId(className);

		DDMTemplate ddmTemplate = addTemplate(
			ddmStructure.getStructureId(), classNameId,
			DDMTemplateConstants.TEMPLATE_MODE_CREATE);

		KaleoTaskFormPairs kaleoTaskFormPairs = new KaleoTaskFormPairs();

		addKaleoTaskFormPair(
			ddmStructure.getStructureId(), classNameId,
			_REVIEW_WORKFLOW_TASK_NAME, kaleoTaskFormPairs);
		addKaleoTaskFormPair(
			ddmStructure.getStructureId(), classNameId,
			_UPDATE_WORKFLOW_TASK_NAME, kaleoTaskFormPairs);

		return KaleoProcessLocalServiceUtil.addKaleoProcess(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			ddmStructure.getStructureId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), ddmTemplate.getTemplateId(),
			"Single Approver", 1, kaleoTaskFormPairs, serviceContext);
	}

	public static KaleoProcess updateKaleoProcess(
			KaleoProcess kaleoProcess, String className)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			className);

		long classNameId = PortalUtil.getClassNameId(className);

		DDMTemplate ddmTemplate = addTemplate(
			ddmStructure.getStructureId(), classNameId,
			DDMTemplateConstants.TEMPLATE_MODE_CREATE);

		KaleoTaskFormPairs kaleoTaskFormPairs = new KaleoTaskFormPairs();

		addKaleoTaskFormPair(
			ddmStructure.getStructureId(), classNameId,
			_REVIEW_WORKFLOW_TASK_NAME, kaleoTaskFormPairs);
		addKaleoTaskFormPair(
			ddmStructure.getStructureId(), classNameId,
			_UPDATE_WORKFLOW_TASK_NAME, kaleoTaskFormPairs);

		return KaleoProcessLocalServiceUtil.updateKaleoProcess(
			kaleoProcess.getKaleoProcessId(), ddmStructure.getStructureId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), ddmTemplate.getTemplateId(),
			"Single Approver", 1, kaleoTaskFormPairs, serviceContext);
	}

	protected static void addKaleoTaskFormPair(
			long structureId, long resourceClassNameId, String workflowTaskName,
			KaleoTaskFormPairs kaleoTaskFormPairs)
		throws Exception {

		DDMTemplate ddmTemplate = addTemplate(
			structureId, resourceClassNameId,
			DDMTemplateConstants.TEMPLATE_MODE_EDIT);

		KaleoTaskFormPair kaleoKaleoTaskFormPair = new KaleoTaskFormPair(
			workflowTaskName, ddmTemplate.getTemplateId());

		kaleoTaskFormPairs.add(kaleoKaleoTaskFormPair);
	}

	protected static DDMTemplate addTemplate(
			long structureId, long resourceClassNameId, String mode)
		throws Exception {

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			structureId, resourceClassNameId);

		ddmTemplate.setMode(mode);
		ddmTemplate.setType(DDMTemplateConstants.TEMPLATE_TYPE_FORM);

		return DDMTemplateLocalServiceUtil.updateDDMTemplate(ddmTemplate);
	}

	private static final String _REVIEW_WORKFLOW_TASK_NAME = "review";

	private static final String _UPDATE_WORKFLOW_TASK_NAME = "update";

}