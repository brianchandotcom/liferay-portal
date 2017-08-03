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

package com.liferay.vldap.server.internal.handler;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.vldap.server.internal.directory.DirectoryTree;
import com.liferay.vldap.server.internal.directory.SearchBase;
import com.liferay.vldap.server.internal.directory.ldap.Directory;
import com.liferay.vldap.server.internal.handler.util.LdapHandlerContext;
import com.liferay.vldap.server.internal.util.PortletPropsValues;

import java.util.List;

import org.apache.directory.api.ldap.model.entry.Value;
import org.apache.directory.api.ldap.model.message.CompareRequest;
import org.apache.directory.api.ldap.model.message.Request;
import org.apache.directory.api.ldap.model.message.Response;
import org.apache.directory.api.ldap.model.message.ResultCodeEnum;
import org.apache.mina.core.session.IoSession;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class CompareLdapHandler extends BaseLdapHandler {

	@Override
	public List<Response> messageReceived(
			Request request, IoSession ioSession,
			LdapHandlerContext ldapHandlerContext)
		throws PortalException {

		CompareRequest compareRequest = (CompareRequest)request;

		DirectoryTree directoryTree = new DirectoryTree();

		SearchBase searchBase = directoryTree.getSearchBase(
			compareRequest.getName(), PortletPropsValues.SEARCH_MAX_SIZE);

		if (searchBase == null) {
			return toList(
				getResultResponse(
					compareRequest, ResultCodeEnum.COMPARE_FALSE));
		}

		Directory directory = searchBase.getDirectory();

		if (directory == null) {
			return toList(
				getResultResponse(
					compareRequest, ResultCodeEnum.COMPARE_FALSE));
		}

		Value<?> value = compareRequest.getAssertionValue();

		if (!directory.hasAttribute(
				compareRequest.getAttributeId(), value.getString())) {

			return toList(
				getResultResponse(
					compareRequest, ResultCodeEnum.COMPARE_FALSE));
		}

		return toList(
			getResultResponse(compareRequest, ResultCodeEnum.COMPARE_TRUE));
	}

}