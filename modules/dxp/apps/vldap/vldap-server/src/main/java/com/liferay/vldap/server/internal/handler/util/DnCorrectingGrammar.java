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

import org.apache.directory.api.asn1.ber.grammar.AbstractGrammar;
import org.apache.directory.api.asn1.ber.grammar.GrammarTransition;
import org.apache.directory.api.asn1.ber.tlv.UniversalTag;
import org.apache.directory.api.ldap.codec.LdapMessageGrammar;
import org.apache.directory.api.ldap.codec.LdapStatesEnum;

/**
 * @author Minhchau Dang
 */
public class DnCorrectingGrammar<E extends LiferayLdapMessageContainer>
	extends AbstractGrammar<E> {

	@Override
	public GrammarTransition<E> getTransition(Enum<?> state, int tag) {
		GrammarTransition<E> grammarTransition = _abstractGrammar.getTransition(
			state, tag);

		Enum<?> previousState = grammarTransition.getPreviousState();
		Enum<?> currentState = grammarTransition.getCurrentState();

		if (currentState == LdapStatesEnum.NAME_STATE) {
			grammarTransition = new GrammarTransition<>(
				previousState, currentState, UniversalTag.OCTET_STRING,
				_dnCorrectingStoreName);
		}

		return grammarTransition;
	}

	protected DnCorrectingGrammar() {
		_abstractGrammar = (AbstractGrammar<E>)LdapMessageGrammar.getInstance();

		_dnCorrectingStoreName = new DnCorrectingStoreName<>();
	}

	private final AbstractGrammar<E> _abstractGrammar;
	private final DnCorrectingStoreName<E> _dnCorrectingStoreName;

}