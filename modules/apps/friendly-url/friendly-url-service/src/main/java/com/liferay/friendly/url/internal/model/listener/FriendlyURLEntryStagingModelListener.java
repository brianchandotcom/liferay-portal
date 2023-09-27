/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.internal.model.listener;

import com.liferay.friendly.url.model.FriendlyURLEntry;
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
public class FriendlyURLEntryStagingModelListener
	extends BaseModelListener<FriendlyURLEntry> {

	@Override
	public void onAfterCreate(FriendlyURLEntry friendlyURLEntry)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(friendlyURLEntry);
	}

	@Override
	public void onAfterRemove(FriendlyURLEntry friendlyURLEntry)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(friendlyURLEntry);
	}

	@Override
	public void onAfterUpdate(
			FriendlyURLEntry originalFriendlyURLEntry,
			FriendlyURLEntry friendlyURLEntry)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(friendlyURLEntry);
	}

	@Reference
	private StagingModelListener<FriendlyURLEntry> _stagingModelListener;

}