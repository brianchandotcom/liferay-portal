/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.validator;

import java.util.Collections;

import org.apache.commons.validator.routines.DomainValidator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Drew Brokke
 */
@Component(service = DomainValidatorFactory.class)
public class DomainValidatorFactory {

	public DomainValidator create(String[] customTLDs) {
		return DomainValidator.getInstance(
			false,
			Collections.singletonList(
				new DomainValidator.Item(
					DomainValidator.ArrayType.GENERIC_PLUS, customTLDs)));
	}

}