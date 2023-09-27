/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.util;

import com.liferay.saml.util.NameIdTypeValues;

import org.opensaml.saml.saml2.core.NameIDType;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = NameIdTypeValues.class)
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