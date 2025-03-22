/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Galluzzi
 */
public class StructureUsagesDisplayContext {

	public StructureUsagesDisplayContext(
		HttpServletRequest httpServletRequest,
		ObjectDefinitionResource.Factory objectDefinitionResourceFactory) {

		_httpServletRequest = httpServletRequest;
		_objectDefinitionResourceFactory = objectDefinitionResourceFactory;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getAPIURL() throws Exception {
		ObjectDefinition objectDefinition = _getObjectDefinition();

		return objectDefinition.getRestContextPath();
	}

	public List<DropdownItem> getBulkActionDropdownItems() {
		return Collections.emptyList();
	}

	private ObjectDefinition _getObjectDefinition() throws Exception {
		if (_objectDefinition != null) {
			return _objectDefinition;
		}

		long objectDefinitionId = ParamUtil.getLong(
			_httpServletRequest, "objectDefinitionId");

		ObjectDefinitionResource.Builder builder =
			_objectDefinitionResourceFactory.create();

		ObjectDefinitionResource objectDefinitionResource = builder.user(
			_themeDisplay.getUser()
		).build();

		_objectDefinition = objectDefinitionResource.getObjectDefinition(
			objectDefinitionId);

		return _objectDefinition;
	}

	private final HttpServletRequest _httpServletRequest;
	private ObjectDefinition _objectDefinition;
	private final ObjectDefinitionResource.Factory
		_objectDefinitionResourceFactory;
	private final ThemeDisplay _themeDisplay;

}