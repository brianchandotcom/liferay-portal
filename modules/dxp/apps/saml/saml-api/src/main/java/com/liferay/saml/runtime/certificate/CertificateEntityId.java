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

package com.liferay.saml.runtime.certificate;

import java.util.Objects;

/**
 * @author Michael C. Han
 */
public class CertificateEntityId {

	public CertificateEntityId(
		String commonName, String organization, String organizationUnit,
		String locality, String state, String country) {

		_commonName = commonName;
		_organization = organization;
		_organizationUnit = organizationUnit;
		_locality = locality;
		_state = state;
		_country = country;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if ((object == null) || (getClass() != object.getClass())) {
			return false;
		}

		CertificateEntityId certificateEntityId = (CertificateEntityId)object;

		if (Objects.equals(_commonName, certificateEntityId._commonName) &&
			Objects.equals(_country, certificateEntityId._country) &&
			Objects.equals(_locality, certificateEntityId._locality) &&
			Objects.equals(_organization, certificateEntityId._organization) &&
			Objects.equals(
				_organizationUnit, certificateEntityId._organizationUnit) &&
			Objects.equals(_state, certificateEntityId._state)) {

			return true;
		}

		return false;
	}

	public String getCommonName() {
		return _commonName;
	}

	public String getCountry() {
		return _country;
	}

	public String getLocality() {
		return _locality;
	}

	public String getOrganization() {
		return _organization;
	}

	public String getOrganizationUnit() {
		return _organizationUnit;
	}

	public String getState() {
		return _state;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			_commonName, _country, _locality, _organization, _organizationUnit,
			_state);
	}

	private final String _commonName;
	private final String _country;
	private final String _locality;
	private final String _organization;
	private final String _organizationUnit;
	private final String _state;

}