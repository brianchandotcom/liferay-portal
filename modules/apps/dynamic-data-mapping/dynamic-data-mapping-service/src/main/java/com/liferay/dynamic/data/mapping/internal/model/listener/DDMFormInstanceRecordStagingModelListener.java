/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = ModelListener.class)
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
	public void onAfterUpdate(
			DDMFormInstanceRecord originalDDMFormInstanceRecord,
			DDMFormInstanceRecord ddmFormInstanceRecord)
		throws ModelListenerException {

		if (_isSkipEvent(ddmFormInstanceRecord)) {
			return;
		}

		_stagingModelListener.onAfterUpdate(ddmFormInstanceRecord);
	}

	private boolean _isSkipEvent(DDMFormInstanceRecord ddmFormInstanceRecord) {
		try {
			StagedModelDataHandler<DDMFormInstanceRecord>
				stagedModelDataHandler =
					(StagedModelDataHandler<DDMFormInstanceRecord>)
						StagedModelDataHandlerRegistryUtil.
							getStagedModelDataHandler(
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
				_log.debug(portalException);
			}
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormInstanceRecordStagingModelListener.class);

	@Reference
	private StagingModelListener<DDMFormInstanceRecord> _stagingModelListener;

}