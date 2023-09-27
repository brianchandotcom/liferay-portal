/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.model.listener;

import com.liferay.message.boards.model.MBThreadFlag;
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
public class MBThreadFlagStagingModelListener
	extends BaseModelListener<MBThreadFlag> {

	@Override
	public void onAfterCreate(MBThreadFlag mbThreadFlag)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(mbThreadFlag);
	}

	@Override
	public void onAfterRemove(MBThreadFlag mbThreadFlag)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(mbThreadFlag);
	}

	@Override
	public void onAfterUpdate(
			MBThreadFlag originalMBThreadFlag, MBThreadFlag mbThreadFlag)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(mbThreadFlag);
	}

	@Reference
	private StagingModelListener<MBThreadFlag> _stagingModelListener;

}