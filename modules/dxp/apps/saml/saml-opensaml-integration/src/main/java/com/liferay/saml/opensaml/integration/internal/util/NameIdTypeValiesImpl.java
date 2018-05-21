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