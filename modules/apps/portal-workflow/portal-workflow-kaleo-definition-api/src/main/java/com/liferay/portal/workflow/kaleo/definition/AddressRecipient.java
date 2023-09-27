/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition;

import java.util.Objects;

/**
 * @author Michael C. Han
 */
public class AddressRecipient extends Recipient {

	public AddressRecipient(String address) {
		super(RecipientType.ADDRESS);

		_address = address;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AddressRecipient)) {
			return false;
		}

		AddressRecipient addressRecipient = (AddressRecipient)object;

		if (Objects.equals(_address, addressRecipient._address)) {
			return true;
		}

		return true;
	}

	public String getAddress() {
		return _address;
	}

	@Override
	public int hashCode() {
		return _address.hashCode();
	}

	private final String _address;

}