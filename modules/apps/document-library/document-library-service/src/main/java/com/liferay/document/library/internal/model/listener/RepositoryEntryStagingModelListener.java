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

package com.liferay.document.library.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.RepositoryEntry;
import com.liferay.staging.model.listener.StagingModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(service = ModelListener.class)
public class RepositoryEntryStagingModelListener
	extends BaseModelListener<RepositoryEntry> {

	@Override
	public void onAfterCreate(RepositoryEntry repositoryEntry)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(repositoryEntry);
	}

	@Override
	public void onAfterRemove(RepositoryEntry repositoryEntry)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(repositoryEntry);
	}

	@Override
	public void onAfterUpdate(RepositoryEntry repositoryEntry)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(repositoryEntry);
	}

	@Reference
	private StagingModelListener<RepositoryEntry> _stagingModelListener;

}