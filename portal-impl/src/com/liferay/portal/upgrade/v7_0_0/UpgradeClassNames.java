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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Cristina González
 */
public class UpgradeClassNames extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		for (String[] packageConversion : _PACKAGE_CONVERSION) {
			String oldPackage = packageConversion[0];
			String newPackage = packageConversion[1];

			// Upgrade Counter

			runSQL(
				"update Counter set name = replace(name, '" + oldPackage +
					"', '" + newPackage + "');");

			// Upgrade ClassName

			runSQL(
				"update ClassName_ set value = replace(value, '" + oldPackage +
					"', '"+ newPackage + "');");

			// Upgrade Lock

			runSQL(
				"update Lock_ set className = replace(className, '" +
					oldPackage + "', '"+ newPackage + "')");

			// Upgrade Kaleo

			runSQL(
				"update KaleoInstance set className = replace(className, '" +
					oldPackage + "', '"+ newPackage + "')");
			runSQL(
				"update KaleoInstanceToken set className = replace(" +
					"className, '" + oldPackage + "', '"+ newPackage + "')");
			runSQL(
				"update KaleoTaskInstanceToken set className = " + "replace(" +
					"className, '" + oldPackage + "', '"+ newPackage + "')");
		}
	}

	private static final String[][] _PACKAGE_CONVERSION = {
		{
			"com.liferay.portal.model.ExportImportConfiguration",
			"com.liferay.exportimport.kernel.model.ExportImportConfiguration"
		},
		{
			"com.liferay.portal.model", "com.liferay.portal.kernel.model"
		},
		{
			"com.liferay.portlet.announcements.model",
			"com.liferay.announcements.kernel.model"
		},
		{
			"com.liferay.portlet.asset.model", "com.liferay.asset.kernel.model"
		},
		{
			"com.liferay.portlet.blogs.model", "com.liferay.blogs.kernel.model"
		},
		{
			"com.liferay.counter.model", "com.liferay.counter.kernel.model"
		},
		{
			"com.liferay.portlet.documentlibrary.model",
			"com.liferay.document.library.kernel.model"
		},
		{
			"com.liferay.portlet.documentlibrary.model",
			"com.liferay.document.library.kernel.model"
		},
		{
			"com.liferay.portlet.dynamicdatamapping.model",
			"com.liferay.dynamic.data.mapping.kernel"
		},
		{
			"com.liferay.portlet.expando.model",
			"com.liferay.expando.kernel.model"
		},
		{
			"com.liferay.portal.kernel.mail", "com.liferay.mail.kernel.model"
		},
		{
			"com.liferay.portlet.messageboards.model",
			"com.liferay.message.boards.kernel.model"
		},
		{
			"com.liferay.portlet.ratings.model",
			"com.liferay.ratings.kernel.model"
		},
		{
			"com.liferay.portlet.social.model",
			"com.liferay.social.kernel.model"
		},
		{
			"com.liferay.portlet.trash.model", "com.liferay.trash.kernel.model"
		}
	};

}