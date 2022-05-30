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

package com.liferay.document.library.web.internal.display.context;

import com.liferay.document.library.configuration.DLSizeLimitConfigurationProvider;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletResponse;

/**
 * @author Adolfo Pérez
 */
public class DLSizeLimitConfigurationDisplayContext {

	public DLSizeLimitConfigurationDisplayContext(
		DLSizeLimitConfigurationProvider dlSizeLimitConfigurationProvider,
		PortletResponse portletResponse, String scope, long scopePK) {

		_dlSizeLimitConfigurationProvider = dlSizeLimitConfigurationProvider;
		_portletResponse = portletResponse;
		_scope = scope;
		_scopePK = scopePK;
	}

	public long getFileMaxSize() {
		if (_scope.equals(
				ExtendedObjectClassDefinition.Scope.COMPANY.getValue())) {

			return _dlSizeLimitConfigurationProvider.getCompanyFileMaxSize(
				_scopePK);
		}
		else if (_scope.equals(
					ExtendedObjectClassDefinition.Scope.GROUP.getValue())) {

			return _dlSizeLimitConfigurationProvider.getGroupFileMaxSize(
				_scopePK);
		}
		else if (_scope.equals(
					ExtendedObjectClassDefinition.Scope.SYSTEM.getValue())) {

			return _dlSizeLimitConfigurationProvider.getSystemFileMaxSize();
		}
		else {
			throw new IllegalArgumentException("Unsupported scope: " + _scope);
		}
	}

	public Map<String, Object> getFileSizePerMimeTypeData() {
		List<Map<String, Object>> sizeList = new ArrayList<>();

		Map<String, Long> mimeTypeSizeLimit = _getMimeTypeSizeLimit();

		mimeTypeSizeLimit.forEach(
			(mimeType, size) -> sizeList.add(
				HashMapBuilder.<String, Object>put(
					"mimeType", mimeType
				).put(
					"size", size
				).build()));

		return HashMapBuilder.<String, Object>put(
			"portletNamespace", _portletResponse.getNamespace()
		).put(
			"sizeList", sizeList
		).build();
	}

	private Map<String, Long> _getMimeTypeSizeLimit() {
		if (_scope.equals(
				ExtendedObjectClassDefinition.Scope.COMPANY.getValue())) {

			return _dlSizeLimitConfigurationProvider.
				getCompanyMimeTypeSizeLimit(_scopePK);
		}
		else if (_scope.equals(
					ExtendedObjectClassDefinition.Scope.GROUP.getValue())) {

			return _dlSizeLimitConfigurationProvider.getGroupMimeTypeSizeLimit(
				_scopePK);
		}
		else if (_scope.equals(
					ExtendedObjectClassDefinition.Scope.SYSTEM.getValue())) {

			return _dlSizeLimitConfigurationProvider.
				getSystemMimeTypeSizeLimit();
		}
		else {
			throw new IllegalArgumentException("Unsupported scope: " + _scope);
		}
	}

	private final DLSizeLimitConfigurationProvider
		_dlSizeLimitConfigurationProvider;
	private final PortletResponse _portletResponse;
	private final String _scope;
	private final long _scopePK;

}