/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.model.listener;

import com.liferay.knowledge.base.model.KBComment;
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
public class KBCommentStagingModelListener
	extends BaseModelListener<KBComment> {

	@Override
	public void onAfterCreate(KBComment kbComment)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(kbComment);
	}

	@Override
	public void onAfterRemove(KBComment kbComment)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(kbComment);
	}

	@Override
	public void onAfterUpdate(KBComment originalKBComment, KBComment kbComment)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(kbComment);
	}

	@Reference
	private StagingModelListener<KBComment> _stagingModelListener;

}