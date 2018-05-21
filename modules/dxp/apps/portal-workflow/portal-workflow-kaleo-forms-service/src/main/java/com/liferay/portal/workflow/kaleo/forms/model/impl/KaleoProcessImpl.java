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

package com.liferay.portal.workflow.kaleo.forms.model.impl;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink;
import com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessLinkLocalServiceUtil;

import java.util.List;
import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public class KaleoProcessImpl extends KaleoProcessBaseImpl {

	@Override
	public DDLRecordSet getDDLRecordSet() throws PortalException {
		return DDLRecordSetLocalServiceUtil.getRecordSet(getDDLRecordSetId());
	}

	@Override
	public DDMTemplate getDDMTemplate() throws PortalException {
		return DDMTemplateLocalServiceUtil.getTemplate(getDDMTemplateId());
	}

	@Override
	public String getDescription() throws PortalException {
		DDLRecordSet ddlRecordSet = getDDLRecordSet();

		return ddlRecordSet.getDescription();
	}

	@Override
	public String getDescription(Locale locale) throws PortalException {
		DDLRecordSet ddlRecordSet = getDDLRecordSet();

		return ddlRecordSet.getDescription(locale);
	}

	@Override
	public List<KaleoProcessLink> getKaleoProcessLinks() {
		return KaleoProcessLinkLocalServiceUtil.getKaleoProcessLinks(
			getKaleoProcessId());
	}

	@Override
	public String getName() throws PortalException {
		DDLRecordSet ddlRecordSet = getDDLRecordSet();

		return ddlRecordSet.getName();
	}

	@Override
	public String getName(Locale locale) throws PortalException {
		DDLRecordSet ddlRecordSet = getDDLRecordSet();

		return ddlRecordSet.getName(locale);
	}

	@Override
	public String getWorkflowDefinition() {
		String workflowDefinition =
			getWorkflowDefinitionName() + "@" + getWorkflowDefinitionVersion();

		return workflowDefinition;
	}

}