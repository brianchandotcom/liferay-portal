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

package com.liferay.vldap.server.internal.handler.util;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;

import javax.security.sasl.SaslServer;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class LdapHandlerContext {

	public Company getCompany() {
		return _company;
	}

	public long getCompanyId() {
		return _company.getCompanyId();
	}

	public SaslCallbackHandler getSaslCallbackHandler() {
		return _saslCallbackHandler;
	}

	public SaslServer getSaslServer() {
		return _saslServer;
	}

	public User getUser() {
		return _user;
	}

	public void setCompany(Company company) {
		_company = company;
	}

	public void setSaslCallbackHandler(
		SaslCallbackHandler saslCallbackHandler) {

		_saslCallbackHandler = saslCallbackHandler;
	}

	public void setSaslServer(SaslServer saslServer) {
		_saslServer = saslServer;
	}

	public void setUser(User user) {
		_user = user;
	}

	private Company _company;
	private SaslCallbackHandler _saslCallbackHandler;
	private SaslServer _saslServer;
	private User _user;

}