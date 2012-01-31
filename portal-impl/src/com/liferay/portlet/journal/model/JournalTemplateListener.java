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

import com.liferay.portal.kernel.freemarker.FreeMarkerEngineUtil;
import com.liferay.portal.kernel.velocity.VelocityEngineUtil;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.servlet.filters.cache.CacheUtil;
import com.liferay.portal.velocity.LiferayResourceCacheUtil;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;

import org.apache.velocity.runtime.resource.ResourceManager;

/**
 * @author Brian Wing Shun Chan
 * @author Jon Steer
 * @author Raymond Augé
 * @author Shuyang Zhou
 */
public class JournalTemplateListener
	extends BaseModelListener<JournalTemplate> {

	@Override
	public void onAfterRemove(JournalTemplate template) {
		clearCache(template);
	}

	@Override
	public void onAfterUpdate(JournalTemplate template) {
		clearCache(template);
	}

	protected void clearCache(JournalTemplate template) {

		// Freemarker cache

		String freeMarkerTemplateId =
			template.getCompanyId() + template.getGroupId() +
				template.getTemplateId();

		FreeMarkerEngineUtil.flushTemplate(freeMarkerTemplateId);

		// Journal content

		JournalContentUtil.clearCache();

		// Layout cache

		CacheUtil.clearCache(template.getCompanyId());

		// Liferay resource cache

		LiferayResourceCacheUtil.remove(
			_RESOURCE_TEMPLATE_NAME_SPACE.concat(freeMarkerTemplateId));

		// Velocity cache

		VelocityEngineUtil.flushTemplate(freeMarkerTemplateId);
	}

	private static final String _RESOURCE_TEMPLATE_NAME_SPACE = String.valueOf(
		ResourceManager.RESOURCE_TEMPLATE);

}