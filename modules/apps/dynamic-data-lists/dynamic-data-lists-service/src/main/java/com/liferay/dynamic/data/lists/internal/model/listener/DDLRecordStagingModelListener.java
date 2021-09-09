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

package com.liferay.dynamic.data.lists.internal.model.listener;

import com.liferay.dynamic.data.lists.constants.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.staging.model.listener.StagingModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(immediate = true, service = ModelListener.class)
public class DDLRecordStagingModelListener
	extends BaseModelListener<DDLRecord> {

	@Override
	public void onAfterCreate(DDLRecord ddlRecord)
		throws ModelListenerException {

		if (_isSkipEvent(ddlRecord)) {
			return;
		}

		_stagingModelListener.onAfterCreate(ddlRecord);
	}

	@Override
	public void onAfterRemove(DDLRecord ddlRecord)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(ddlRecord);
	}

	@Override
	public void onAfterUpdate(DDLRecord originalDDLRecord, DDLRecord ddlRecord)
		throws ModelListenerException {

		if (_isSkipEvent(ddlRecord)) {
			return;
		}

		_stagingModelListener.onAfterUpdate(ddlRecord);
	}

	private boolean _isSkipEvent(DDLRecord ddlRecord) {
		try {
			DDLRecordSet recordSet = ddlRecord.getRecordSet();

			if (recordSet.getScope() !=
					DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS) {

				return true;
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDLRecordStagingModelListener.class);

	@Reference
	private StagingModelListener<DDLRecord> _stagingModelListener;

}