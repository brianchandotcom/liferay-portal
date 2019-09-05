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

package com.liferay.digital.signature.internal.model.field;

import com.liferay.digital.signature.model.field.EmailAddressDSField;

/**
 * @author Michael C. Han
 */
public class EmailAddressDSFieldImpl
	extends UserEntryDSFieldImpl<EmailAddressDSField>
	implements EmailAddressDSField {

	public EmailAddressDSFieldImpl(
		String documentKey, String fieldKey, Integer pageNumber) {

		super(documentKey, fieldKey, pageNumber);
	}

	@Override
	public Boolean getSenderRequired() {
		return _senderRequired;
	}

	public void setSenderRequired(Boolean senderRequired) {
		_senderRequired = senderRequired;
	}

	private Boolean _senderRequired;

}