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

package com.liferay.document.library.repository.cmis.internal;

import com.liferay.portal.kernel.repository.registry.BaseRepositoryDefiner;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseCMISRepositoryDefiner extends BaseRepositoryDefiner {

	@Override
	public String getRepositoryTypeLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return ResourceBundleUtil.getString(
			resourceBundle, _MODEL_RESOURCE_NAME_PREFIX + getClassName());
	}

	private static final String _MODEL_RESOURCE_NAME_PREFIX = "model.resource.";

}