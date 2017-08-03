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