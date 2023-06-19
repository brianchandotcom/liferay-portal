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
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class DLSizeLimitConfigurationDisplayContext {

	public DLSizeLimitConfigurationDisplayContext(
		DLSizeLimitConfigurationProvider dlSizeLimitConfigurationProvider,
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse, String scope,
		long scopePK) {

		_dlSizeLimitConfigurationProvider = dlSizeLimitConfigurationProvider;
		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_scope = scope;
		_scopePK = scopePK;
	}

	public long getCopyFilesMaxSize() {
		if (_scope.equals(
				ExtendedObjectClassDefinition.Scope.COMPANY.getValue())) {

			return _dlSizeLimitConfigurationProvider.getCompanyMaxSizeToCopy(
				_scopePK);
		}
		else if (_scope.equals(
					ExtendedObjectClassDefinition.Scope.GROUP.getValue())) {

			return _dlSizeLimitConfigurationProvider.getGroupMaxSizeToCopy(
				_scopePK);
		}
		else if (_scope.equals(
					ExtendedObjectClassDefinition.Scope.SYSTEM.getValue())) {

			return _dlSizeLimitConfigurationProvider.getSystemMaxSizeToCopy();
		}

		throw new IllegalArgumentException("Unsupported scope: " + _scope);
	}

	public String getEditDLSizeLimitConfigurationURL() {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/instance_settings/edit_size_limits"
		).setRedirect(
			PortalUtil.getCurrentURL(_httpServletRequest)
		).setParameter(
			"scope", _scope
		).setParameter(
			"scopePK", _scopePK
		).buildString();
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

		throw new IllegalArgumentException("Unsupported scope: " + _scope);
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
			"portletNamespace", _liferayPortletResponse.getNamespace()
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

		throw new IllegalArgumentException("Unsupported scope: " + _scope);
	}

	private final DLSizeLimitConfigurationProvider
		_dlSizeLimitConfigurationProvider;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final String _scope;
	private final long _scopePK;

}