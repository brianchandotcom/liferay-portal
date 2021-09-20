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

package com.liferay.object.web.internal.object.entries.portlet;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.web.internal.constants.ObjectWebKeys;
import com.liferay.object.web.internal.object.entries.display.context.ViewObjectEntriesDisplayContext;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectEntriesPortlet extends MVCPortlet {

	public ObjectEntriesPortlet(
		long objectDefinitionId,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectScopeProviderRegistry objectScopeProviderRegistry, Portal portal,
		String restContextPath) {

		_objectDefinitionId = objectDefinitionId;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectScopeProviderRegistry = objectScopeProviderRegistry;
		_portal = portal;
		_restContextPath = restContextPath;
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				_objectDefinitionId);

		renderRequest.setAttribute(
			ObjectWebKeys.OBJECT_DEFINITION, objectDefinition);

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			new ViewObjectEntriesDisplayContext(
				httpServletRequest,
				_getRestContextPath(httpServletRequest, objectDefinition)));

		super.render(renderRequest, renderResponse);
	}

	private String _getRestContextPath(
		HttpServletRequest httpServletRequest,
		ObjectDefinition objectDefinition) {

		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				objectDefinition.getScope());

		if (!objectScopeProvider.isGroupAware()) {
			return _restContextPath;
		}

		try {
			return StringBundler.concat(
				_restContextPath, "/scopes/",
				objectScopeProvider.getGroupId(httpServletRequest));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}

		return _restContextPath;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntriesPortlet.class);

	private final long _objectDefinitionId;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectScopeProviderRegistry _objectScopeProviderRegistry;
	private final Portal _portal;
	private final String _restContextPath;

}