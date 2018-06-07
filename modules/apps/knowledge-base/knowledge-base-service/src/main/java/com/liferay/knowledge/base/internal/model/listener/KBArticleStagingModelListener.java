/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
@Component(immediate = true, service = ModelListener.class)
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
	public void onAfterUpdate(KBArticle kbArticle)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(kbArticle);
	}

	@Reference
	private StagingModelListener<KBArticle> _stagingModelListener;

}