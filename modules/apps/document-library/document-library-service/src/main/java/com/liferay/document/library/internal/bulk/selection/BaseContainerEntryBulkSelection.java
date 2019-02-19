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

package com.liferay.document.library.internal.bulk.selection;

import com.liferay.bulk.selection.BulkSelection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseContainerEntryBulkSelection<T>
	implements BulkSelection<T> {

	public BaseContainerEntryBulkSelection(
		long containerId, Map<String, String[]> parameterMap,
		ResourceBundleLoader resourceBundleLoader, Language language) {

		_containerId = containerId;
		_parameterMap = parameterMap;
		_resourceBundleLoader = resourceBundleLoader;
		_language = language;
	}

	@Override
	public String describe(Locale locale) throws PortalException {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return _language.format(
			resourceBundle, "these-changes-will-be-applied-to-x-items",
			getEntriesCount());
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return _parameterMap;
	}

	@Override
	public boolean isMultiple() {
		return true;
	}

	@Override
	public Serializable serialize() {
		return "all:" + _containerId;
	}

	protected abstract int getEntriesCount() throws PortalException;

	private final long _containerId;
	private final Language _language;
	private final Map<String, String[]> _parameterMap;
	private final ResourceBundleLoader _resourceBundleLoader;

}