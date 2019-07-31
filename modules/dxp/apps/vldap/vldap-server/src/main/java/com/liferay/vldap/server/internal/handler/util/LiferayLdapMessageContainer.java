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

import org.apache.directory.api.ldap.codec.api.LdapMessageContainer;
import org.apache.directory.api.ldap.codec.standalone.StandaloneLdapApiService;
import org.apache.directory.api.ldap.model.message.Message;

/**
 * @author Minhchau Dang
 */
public class LiferayLdapMessageContainer extends LdapMessageContainer<Message> {

	public LiferayLdapMessageContainer() throws Exception {
		super(new StandaloneLdapApiService());

		setGrammar(new DnCorrectingGrammar<>());
	}

}