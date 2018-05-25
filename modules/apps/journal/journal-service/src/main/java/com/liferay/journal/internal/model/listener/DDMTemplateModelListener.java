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

package com.liferay.journal.internal.model.listener;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.journal.util.JournalContent;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.servlet.filters.cache.CacheUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(immediate = true, service = ModelListener.class)
public class DDMTemplateModelListener extends BaseModelListener<DDMTemplate> {

	@Override
	public void onAfterRemove(DDMTemplate ddmTemplate) {
		clearCache(ddmTemplate);
	}

	@Override
	public void onAfterUpdate(DDMTemplate ddmTemplate) {
		clearCache(ddmTemplate);
	}

	protected void clearCache(DDMTemplate ddmTemplate) {
		if (ddmTemplate == null) {
			return;
		}

		// Article cache

		_journalContent.clearCache(ddmTemplate.getTemplateKey());

		// Layout cache

		CacheUtil.clearCache(ddmTemplate.getCompanyId());
	}

	@Reference
	private JournalContent _journalContent;

}