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

import java.util.ArrayList;
import java.util.List;

import org.apache.directory.api.ldap.model.message.LdapResult;
import org.apache.directory.api.ldap.model.message.Response;
import org.apache.directory.api.ldap.model.message.ResultCodeEnum;
import org.apache.directory.api.ldap.model.message.ResultResponse;
import org.apache.directory.api.ldap.model.message.ResultResponseRequest;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public abstract class BaseLdapHandler implements LdapHandler {

	protected <T extends ResultResponseRequest> ResultResponse
		getResultResponse(T request) {

		return getResultResponse(request, ResultCodeEnum.SUCCESS);
	}

	protected <T extends ResultResponseRequest> ResultResponse
		getResultResponse(T request, ResultCodeEnum resultCode) {

		ResultResponse resultResponse = request.getResultResponse();

		LdapResult ldapResult = resultResponse.getLdapResult();

		ldapResult.setResultCode(resultCode);

		return resultResponse;
	}

	protected List<Response> toList(Response response) {
		List<Response> responses = new ArrayList<>();

		responses.add(response);

		return responses;
	}

}