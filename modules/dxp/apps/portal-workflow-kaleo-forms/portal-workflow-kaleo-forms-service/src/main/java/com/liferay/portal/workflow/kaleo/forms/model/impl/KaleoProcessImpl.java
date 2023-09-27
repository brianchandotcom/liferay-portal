/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		return getWorkflowDefinitionName() + "@" +
			getWorkflowDefinitionVersion();
	}

}