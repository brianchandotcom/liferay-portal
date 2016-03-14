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

package com.liferay.dynamic.data.lists.exporter.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.lists.exporter.DDLExporter;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Marcellus Tavares
 * @author Manuel de la Peña
 */
@ProviderType
public abstract class BaseDDLExporter implements DDLExporter {

	@Override
	public byte[] export(
			long recordSetId, int status, int start, int end, Locale locale)
		throws Exception {

		return doExport(recordSetId, status, start, end, null, locale);
	}

	@Override
	public byte[] export(
			long recordSetId, int status, int start, int end,
			OrderByComparator<DDLRecord> orderByComparator, Locale locale)
		throws Exception {

		return doExport(
			recordSetId, status, start, end, orderByComparator, locale);
	}

	@Override
	public byte[] export(long recordSetId, int status, Locale locale)
		throws Exception {

		return doExport(
			recordSetId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null,
			locale);
	}

	@Override
	public byte[] export(long recordSetId, Locale locale) throws Exception {
		return doExport(
			recordSetId, WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null, locale);
	}

	protected abstract byte[] doExport(
			long recordSetId, int status, int start, int end,
			OrderByComparator<DDLRecord> orderByComparator, Locale locale)
		throws Exception;

	protected List<DDMFormField> getDDMFormFields(DDMStructure ddmStructure)
		throws Exception {

		List<DDMFormField> ddmFormFields = new ArrayList<>();

		for (DDMFormField ddmFormField : ddmStructure.getDDMFormFields(false)) {
			ddmFormFields.add(ddmFormField);
		}

		return ddmFormFields;
	}

	protected String getStatusMessage(int status, Locale locale) {
		String statusLabel = WorkflowConstants.getStatusLabel(status);

		return LanguageUtil.get(locale, statusLabel);
	}

}