/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.vldap.server.internal.handler;

import com.liferay.vldap.server.internal.handler.util.LdapHandlerContext;

import java.util.ArrayList;
import java.util.List;

import org.apache.directory.api.ldap.model.message.Request;
import org.apache.directory.api.ldap.model.message.Response;
import org.apache.directory.api.ldap.model.message.ResultCodeEnum;
import org.apache.directory.api.ldap.model.message.ResultResponseRequest;
import org.apache.mina.core.session.IoSession;

/**
 * @author Brian Wing Shun Chan
 */
public class UnwillingToPerformLdapHandler extends BaseLdapHandler {

	@Override
	public List<Response> messageReceived(
		Request request, IoSession ioSession,
		LdapHandlerContext ldapHandlerContext) {

		List<Response> responses = new ArrayList<>();

		if (request instanceof ResultResponseRequest) {
			ResultResponseRequest resultResponseRequest =
				(ResultResponseRequest)request;

			Response response = getResultResponse(
				resultResponseRequest, ResultCodeEnum.UNWILLING_TO_PERFORM);

			responses.add(response);
		}

		return responses;
	}

}