/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.model.listener;

import com.liferay.message.boards.model.MBCategory;
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
public class MBCategoryStagingModelListener
	extends BaseModelListener<MBCategory> {

	@Override
	public void onAfterCreate(MBCategory mbCategory)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(mbCategory);
	}

	@Override
	public void onAfterRemove(MBCategory mbCategory)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(mbCategory);
	}

	@Override
	public void onAfterUpdate(
			MBCategory originalMBCategory, MBCategory mbCategory)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(mbCategory);
	}

	@Reference
	private StagingModelListener<MBCategory> _stagingModelListener;

}