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

package com.liferay.dispatch.talend.web.internal.metadata;

import com.liferay.dispatch.metadata.DispatchTriggerMetadata;
import com.liferay.dispatch.metadata.DispatchTriggerMetadataFactory;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.repository.DispatchFileRepository;
import com.liferay.dispatch.talend.web.internal.executor.TalendDispatchTaskExecutor;
import com.liferay.expando.kernel.exception.DuplicateColumnNameException;
import com.liferay.expando.kernel.exception.DuplicateTableNameException;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.CompanyLocalService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
@Component(
	immediate = true,
	property = "dispatch.task.executor.type=" + TalendDispatchTaskExecutor.TALEND,
	service = DispatchTriggerMetadataFactory.class
)
public class TalendDispatchTriggerMetadataFactory
	implements DispatchTriggerMetadataFactory {

	@Override
	public DispatchTriggerMetadata instance(DispatchTrigger dispatchTrigger) {
		FileEntry fileEntry = _dispatchFileRepository.fetchFileEntry(
			dispatchTrigger.getDispatchTriggerId());

		TalendDispatchTriggerMetadata.Builder builder =
			new TalendDispatchTriggerMetadata.Builder();

		if (fileEntry != null) {
			builder.ready(true);

			_build(dispatchTrigger, builder);

			return builder.build();
		}

		builder.error("talend-archive-file-misses", null);
		builder.ready(false);

		return builder.build();
	}

	@Activate
	protected void activate() {
		_companyLocalService.forEachCompany(
			company -> {
				try {
					_setupExpando(company.getCompanyId());
				}
				catch (Exception exception) {
					_log.error("Unable to setup expando", exception);
				}
			});
	}

	private void _build(
		DispatchTrigger dispatchTrigger,
		TalendDispatchTriggerMetadata.Builder builder) {

		ExpandoBridge expandoBridge = dispatchTrigger.getExpandoBridge();

		builder.attribute(
			"talend-job-design-archive-name",
			String.valueOf(expandoBridge.getAttribute("fileName")));
	}

	private void _setupExpando(long companyId) throws Exception {
		ExpandoTable expandoTable = null;

		try {
			expandoTable = _expandoTableLocalService.addTable(
				companyId, DispatchTrigger.class.getName(),
				ExpandoTableConstants.DEFAULT_TABLE_NAME);
		}
		catch (DuplicateTableNameException duplicateTableNameException) {
			if (_log.isDebugEnabled()) {
				_log.debug(duplicateTableNameException);
			}

			expandoTable = _expandoTableLocalService.getTable(
				companyId, DispatchTrigger.class.getName(),
				ExpandoTableConstants.DEFAULT_TABLE_NAME);
		}

		try {
			_expandoColumnLocalService.addColumn(
				expandoTable.getTableId(), "fileName",
				ExpandoColumnConstants.STRING);
		}
		catch (DuplicateColumnNameException duplicateColumnNameException) {
			if (_log.isDebugEnabled()) {
				_log.debug(duplicateColumnNameException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TalendDispatchTriggerMetadataFactory.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DispatchFileRepository _dispatchFileRepository;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

}