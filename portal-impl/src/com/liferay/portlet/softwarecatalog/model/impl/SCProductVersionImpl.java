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

package com.liferay.portlet.softwarecatalog.model.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionLocalServiceUtil;
import com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalServiceUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class SCProductVersionImpl extends SCProductVersionBaseImpl {

	@Override
	public List<SCFrameworkVersion> getFrameworkVersions() {
		return SCFrameworkVersionLocalServiceUtil.
			getProductVersionFrameworkVersions(getProductVersionId());
	}

	@Override
	public SCProductEntry getProductEntry() {
		SCProductEntry productEntry = null;

		try {
			productEntry = SCProductEntryLocalServiceUtil.getProductEntry(
				getProductEntryId());
		}
		catch (Exception e) {
			productEntry = new SCProductEntryImpl();

			_log.error(e);
		}

		return productEntry;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SCProductVersionImpl.class);

}