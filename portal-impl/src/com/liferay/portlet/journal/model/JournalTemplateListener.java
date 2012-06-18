/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.model;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateResourceLoaderUtil;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.servlet.filters.cache.CacheUtil;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Jon Steer
 * @author Raymond Augé
 * @author Shuyang Zhou
 */
public class JournalTemplateListener
	extends BaseModelListener<JournalTemplate> {

	@Override
	public void onAfterRemove(JournalTemplate template)
		throws ModelListenerException {

		try {
			clearCache(template);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onAfterUpdate(JournalTemplate template)
		throws ModelListenerException {

		try {
			clearCache(template);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	protected void clearCache(JournalTemplate template) throws Exception {

		// Freemarker cache

		String freeMarkerTemplateId =
			template.getCompanyId() + template.getGroupId() +
				template.getTemplateId();

		TemplateResourceLoaderUtil.clearCache(
			TemplateManager.FREEMARKER, freeMarkerTemplateId);

		// Journal content

		JournalContentUtil.clearCache();

		// Layout cache

		CacheUtil.clearCache(template.getCompanyId());

		// Velocity cache

		TemplateResourceLoaderUtil.clearCache(
			TemplateManager.VELOCITY, freeMarkerTemplateId);
	}

}