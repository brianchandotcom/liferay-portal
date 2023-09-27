/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.model.listener;

import com.liferay.knowledge.base.model.KBArticle;
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
public class KBArticleStagingModelListener
	extends BaseModelListener<KBArticle> {

	@Override
	public void onAfterCreate(KBArticle kbArticle)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(kbArticle);
	}

	@Override
	public void onAfterRemove(KBArticle kbArticle)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(kbArticle);
	}

	@Override
	public void onAfterUpdate(KBArticle originalKBArticle, KBArticle kbArticle)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(kbArticle);
	}

	@Reference
	private StagingModelListener<KBArticle> _stagingModelListener;

}