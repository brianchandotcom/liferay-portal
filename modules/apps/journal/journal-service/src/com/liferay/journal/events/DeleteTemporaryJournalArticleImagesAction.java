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

package com.liferay.journal.events;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(immediate = true)
public class DeleteTemporaryJournalArticleImagesAction extends SimpleAction {

	@Override
	public void run(String[] ids) throws ActionException {
		try {
			doRun(ids);
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	@Activate
	protected void activate() throws ActionException {
		run(new String[] {});
	}

	protected void doRun(String[] ids) throws Exception {

		// Delete temporary images

		if (_log.isDebugEnabled()) {
			_log.debug("Delete temporary images");
		}

		DB db = DBFactoryUtil.getDB();

		db.runSQL(_DELETE_TEMP_IMAGES_1);
		db.runSQL(_DELETE_TEMP_IMAGES_2);
	}

	@Reference(target = "(original.bean=true)", unbind = "-")
	protected void setServletContext(ServletContext servletContext) {
	}

	private static final String _DELETE_TEMP_IMAGES_1 =
		"delete from Image where imageId IN (SELECT articleImageId FROM " +
			"JournalArticleImage where tempImage = TRUE)";

	private static final String _DELETE_TEMP_IMAGES_2 =
		"delete from JournalArticleImage where tempImage = TRUE";

	private static final Log _log = LogFactoryUtil.getLog(
		DeleteTemporaryJournalArticleImagesAction.class);

}