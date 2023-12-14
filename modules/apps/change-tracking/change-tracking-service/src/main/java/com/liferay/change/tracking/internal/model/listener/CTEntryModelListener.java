/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.model.listener;

import com.liferay.change.tracking.closure.CTClosureFactory;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = ModelListener.class)
public class CTEntryModelListener extends BaseModelListener<CTEntry> {

	@Override
	public void onAfterCreate(CTEntry ctEntry) {
		_ctClosureFactory.clearCache(ctEntry.getCtCollectionId());
	}

	@Override
	public void onAfterRemove(CTEntry ctEntry) {
		_ctClosureFactory.clearCache(ctEntry.getCtCollectionId());
	}

	@Override
	public void onAfterUpdate(CTEntry originalCTEntry, CTEntry ctEntry)
		throws ModelListenerException {

		if (originalCTEntry.getCtCollectionId() !=
				ctEntry.getCtCollectionId()) {

			_ctClosureFactory.clearCache(originalCTEntry.getCtCollectionId());
		}

		_ctClosureFactory.clearCache(ctEntry.getCtCollectionId());
	}

	@Reference
	private CTClosureFactory _ctClosureFactory;

}