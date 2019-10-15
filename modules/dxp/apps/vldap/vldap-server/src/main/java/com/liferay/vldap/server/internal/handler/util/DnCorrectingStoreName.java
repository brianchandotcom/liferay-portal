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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import org.apache.directory.api.asn1.ber.grammar.GrammarAction;
import org.apache.directory.api.asn1.ber.tlv.BerValue;
import org.apache.directory.api.asn1.ber.tlv.TLV;
import org.apache.directory.api.ldap.model.exception.LdapInvalidDnException;
import org.apache.directory.api.ldap.model.message.BindRequest;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.api.util.Strings;

/**
 * @author Minhchau Dang
 */
public class DnCorrectingStoreName<E extends LiferayLdapMessageContainer>
	extends GrammarAction<E> {

	@Override
	public void action(E messageContainer) {
		BindRequest bindRequest = (BindRequest)messageContainer.getMessage();

		TLV tlv = messageContainer.getCurrentTLV();

		BerValue berValue = tlv.getValue();

		byte[] dnBytes = berValue.getData();

		String dnString = Strings.utf8ToString(dnBytes);

		Dn dn = _getDn(dnString);

		bindRequest.setDn(dn);
	}

	private Dn _getDn(String dnString) {
		if (Validator.isNull(dnString)) {
			return Dn.EMPTY_DN;
		}

		String fixedDnString = dnString;

		if (!dnString.contains(StringPool.EQUAL)) {
			if (Validator.isEmailAddress(dnString)) {
				fixedDnString = "mail=" + dnString;
			}
			else {
				fixedDnString = "cn=" + dnString;
			}
		}

		try {
			return new Dn(fixedDnString);
		}
		catch (LdapInvalidDnException lide) {
			_log.error("Unable to convert " + dnString + " to a valid DN");

			return Dn.EMPTY_DN;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DnCorrectingStoreName.class);

}