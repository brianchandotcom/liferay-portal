/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.internal.model.listener;

import com.liferay.dynamic.data.lists.constants.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.staging.model.listener.StagingModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(service = ModelListener.class)
public class DDLRecordSetStagingModelListener
	extends BaseModelListener<DDLRecordSet> {

	@Override
	public void onAfterCreate(DDLRecordSet ddlRecordSet)
		throws ModelListenerException {

		if (_isSkipEvent(ddlRecordSet)) {
			return;
		}

		_stagingModelListener.onAfterCreate(ddlRecordSet);
	}

	@Override
	public void onAfterRemove(DDLRecordSet ddlRecordSet)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(ddlRecordSet);
	}

	@Override
	public void onAfterUpdate(
			DDLRecordSet originalDDLRecordSet, DDLRecordSet ddlRecordSet)
		throws ModelListenerException {

		if (_isSkipEvent(ddlRecordSet)) {
			return;
		}

		_stagingModelListener.onAfterUpdate(ddlRecordSet);
	}

	private boolean _isSkipEvent(DDLRecordSet ddlRecordSet) {
		if (ddlRecordSet.getScope() !=
				DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS) {

			return true;
		}

		return false;
	}

	@Reference
	private StagingModelListener<DDLRecordSet> _stagingModelListener;

}