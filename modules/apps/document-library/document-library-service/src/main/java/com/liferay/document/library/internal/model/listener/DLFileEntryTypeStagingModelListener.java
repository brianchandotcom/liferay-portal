/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.model.listener;

import com.liferay.document.library.kernel.model.DLFileEntryType;
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
public class DLFileEntryTypeStagingModelListener
	extends BaseModelListener<DLFileEntryType> {

	@Override
	public void onAfterCreate(DLFileEntryType dlFileEntryType)
		throws ModelListenerException {

		if (!dlFileEntryType.isExportable()) {
			return;
		}

		_stagingModelListener.onAfterCreate(dlFileEntryType);
	}

	@Override
	public void onAfterRemove(DLFileEntryType dlFileEntryType)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(dlFileEntryType);
	}

	@Override
	public void onAfterUpdate(
			DLFileEntryType originalDLFileEntryType,
			DLFileEntryType dlFileEntryType)
		throws ModelListenerException {

		if (!dlFileEntryType.isExportable()) {
			return;
		}

		_stagingModelListener.onAfterUpdate(dlFileEntryType);
	}

	@Reference
	private StagingModelListener<DLFileEntryType> _stagingModelListener;

}