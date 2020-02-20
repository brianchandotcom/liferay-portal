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

package com.liferay.dynamic.data.mapping.internal.model.listener;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.exportimport.kernel.lar.ExportImportClassedModelUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.staging.model.listener.StagingModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(immediate = true, service = ModelListener.class)
public class DDMFormInstanceRecordStagingModelListener
	extends BaseModelListener<DDMFormInstanceRecord> {

	@Override
	public void onAfterCreate(DDMFormInstanceRecord ddmFormInstanceRecord)
		throws ModelListenerException {

		if (_isSkipEvent(ddmFormInstanceRecord)) {
			return;
		}

		_stagingModelListener.onAfterCreate(ddmFormInstanceRecord);
	}

	@Override
	public void onAfterRemove(DDMFormInstanceRecord ddmFormInstanceRecord)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(ddmFormInstanceRecord);
	}

	@Override
	public void onAfterUpdate(DDMFormInstanceRecord ddmFormInstanceRecord)
		throws ModelListenerException {

		if (_isSkipEvent(ddmFormInstanceRecord)) {
			return;
		}

		_stagingModelListener.onAfterUpdate(ddmFormInstanceRecord);
	}

	private boolean _isSkipEvent(DDMFormInstanceRecord ddmFormInstanceRecord) {
		try {
			StagedModelDataHandler stagedModelDataHandler =
				StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
					ExportImportClassedModelUtil.getClassName(
						ddmFormInstanceRecord));

			if (stagedModelDataHandler != null) {
				int[] exportableStatuses =
					stagedModelDataHandler.getExportableStatuses();

				DDMFormInstanceRecordVersion formInstanceRecordVersion =
					ddmFormInstanceRecord.getFormInstanceRecordVersion();

				if (ArrayUtil.contains(
						exportableStatuses,
						formInstanceRecordVersion.getStatus())) {

					return false;
				}
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceRecordStagingModelListener.class);

	@Reference
	private StagingModelListener<DDMFormInstanceRecord> _stagingModelListener;

}