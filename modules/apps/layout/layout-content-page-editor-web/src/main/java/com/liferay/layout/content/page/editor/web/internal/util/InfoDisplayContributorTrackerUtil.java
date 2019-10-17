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

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = {})
public class InfoDisplayContributorTrackerUtil {

	public static InfoDisplayContributor getInfoDisplayContributor(
		String className) {

		return _infoDisplayContributorTracker.getInfoDisplayContributor(
			className);
	}

	@Reference(unbind = "-")
	protected void setAdminUtilHelper(
		InfoDisplayContributorTracker infoDisplayContributorTracker) {

		_infoDisplayContributorTracker = infoDisplayContributorTracker;
	}

	private static InfoDisplayContributorTracker _infoDisplayContributorTracker;

}