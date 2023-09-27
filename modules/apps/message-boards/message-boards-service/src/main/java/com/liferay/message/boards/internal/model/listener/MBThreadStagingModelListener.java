/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.model.listener;

import com.liferay.message.boards.model.MBThread;
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
public class MBThreadStagingModelListener extends BaseModelListener<MBThread> {

	@Override
	public void onAfterCreate(MBThread mbThread) throws ModelListenerException {
		_stagingModelListener.onAfterCreate(mbThread);
	}

	@Override
	public void onAfterRemove(MBThread mbThread) throws ModelListenerException {
		_stagingModelListener.onAfterRemove(mbThread);
	}

	@Override
	public void onAfterUpdate(MBThread originalMBThread, MBThread mbThread)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(mbThread);
	}

	@Reference
	private StagingModelListener<MBThread> _stagingModelListener;

}