/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.model.listener;

import com.liferay.message.boards.model.MBDiscussion;
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
public class MBDiscussionStagingModelListener
	extends BaseModelListener<MBDiscussion> {

	@Override
	public void onAfterCreate(MBDiscussion mbDiscussion)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(mbDiscussion);
	}

	@Override
	public void onAfterRemove(MBDiscussion mbDiscussion)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(mbDiscussion);
	}

	@Override
	public void onAfterUpdate(
			MBDiscussion originalMBDiscussion, MBDiscussion mbDiscussion)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(mbDiscussion);
	}

	@Reference
	private StagingModelListener<MBDiscussion> _stagingModelListener;

}