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

package com.liferay.saml.opensaml.integration.internal.util;

import com.liferay.saml.util.NameIdTypeValues;

import org.opensaml.saml2.core.NameIDType;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = NameIdTypeValues.class)
public class NameIdTypeValiesImpl implements NameIdTypeValues {

	@Override
	public String getEmail() {
		return NameIDType.EMAIL;
	}

	@Override
	public String getEncrypted() {
		return NameIDType.ENCRYPTED;
	}

	@Override
	public String getEntity() {
		return NameIDType.ENTITY;
	}

	@Override
	public String getKerberos() {
		return NameIDType.KERBEROS;
	}

	@Override
	public String getPersistent() {
		return NameIDType.PERSISTENT;
	}

	@Override
	public String getTransient() {
		return NameIDType.TRANSIENT;
	}

	@Override
	public String getUnspecified() {
		return NameIDType.UNSPECIFIED;
	}

	@Override
	public String getWinDomainQualified() {
		return NameIDType.WIN_DOMAIN_QUALIFIED;
	}

	@Override
	public String getX509Subject() {
		return NameIDType.X509_SUBJECT;
	}

}