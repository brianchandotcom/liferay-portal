/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.model.listener;

import com.liferay.message.boards.model.MBBan;
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
public class MBBanStagingModelListener extends BaseModelListener<MBBan> {

	@Override
	public void onAfterCreate(MBBan mbBan) throws ModelListenerException {
		_stagingModelListener.onAfterCreate(mbBan);
	}

	@Override
	public void onAfterRemove(MBBan mbBan) throws ModelListenerException {
		_stagingModelListener.onAfterRemove(mbBan);
	}

	@Override
	public void onAfterUpdate(MBBan originalMBBan, MBBan mbBan)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(mbBan);
	}

	@Reference
	private StagingModelListener<MBBan> _stagingModelListener;

}