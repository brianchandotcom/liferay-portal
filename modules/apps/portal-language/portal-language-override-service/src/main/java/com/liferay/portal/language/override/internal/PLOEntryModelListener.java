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

package com.liferay.portal.language.override.internal;

import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.language.override.model.PLOEntry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = ModelListener.class)
public class PLOEntryModelListener extends BaseModelListener<PLOEntry> {

	@Override
	public void onAfterCreate(PLOEntry ploEntry) {
		_ploLanguageOverrideProvider.clear(
			ploEntry.getCompanyId(), ploEntry.getLanguageId());
	}

	@Override
	public void onAfterRemove(PLOEntry ploEntry) {
		_ploLanguageOverrideProvider.clear(
			ploEntry.getCompanyId(), ploEntry.getLanguageId());
	}

	@Override
	public void onAfterUpdate(PLOEntry originalPLOEntry, PLOEntry ploEntry) {
		_ploLanguageOverrideProvider.clear(
			ploEntry.getCompanyId(), ploEntry.getLanguageId());
	}

	@Reference
	private PLOLanguageOverrideProvider _ploLanguageOverrideProvider;

}