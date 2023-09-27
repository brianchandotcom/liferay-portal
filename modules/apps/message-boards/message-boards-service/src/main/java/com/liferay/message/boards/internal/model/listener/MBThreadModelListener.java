/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.model.listener;

import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBThreadFlagLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = ModelListener.class)
public class MBThreadModelListener extends BaseModelListener<MBThread> {

	@Override
	public void onBeforeRemove(MBThread mbThread)
		throws ModelListenerException {

		_mbThreadFlagLocalService.deleteThreadFlagsByThreadId(
			mbThread.getThreadId());
	}

	@Reference
	private MBThreadFlagLocalService _mbThreadFlagLocalService;

}