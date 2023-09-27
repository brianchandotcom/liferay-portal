/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = ModelListener.class)
public class DDMTemplateModelListener extends BaseModelListener<DDMTemplate> {

	@Override
	public void onAfterRemove(DDMTemplate ddmTemplate) {
		clearCache(ddmTemplate);
	}

	@Override
	public void onAfterUpdate(
		DDMTemplate originalDDMTemplate, DDMTemplate ddmTemplate) {

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