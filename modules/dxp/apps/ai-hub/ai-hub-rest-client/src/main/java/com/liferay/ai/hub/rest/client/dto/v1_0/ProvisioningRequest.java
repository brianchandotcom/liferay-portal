/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.client.dto.v1_0;

import com.liferay.ai.hub.rest.client.function.UnsafeSupplier;
import com.liferay.ai.hub.rest.client.serdes.v1_0.ProvisioningRequestSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Feliphe Marinho
 * @generated
 */
@Generated("")
public class ProvisioningRequest implements Cloneable, Serializable {

	public static ProvisioningRequest toDTO(String json) {
		return ProvisioningRequestSerDes.toDTO(json);
	}

	public String getAccountEntryExternalReferenceCode() {
		return accountEntryExternalReferenceCode;
	}

	public void setAccountEntryExternalReferenceCode(
		String accountEntryExternalReferenceCode) {

		this.accountEntryExternalReferenceCode =
			accountEntryExternalReferenceCode;
	}

	public void setAccountEntryExternalReferenceCode(
		UnsafeSupplier<String, Exception>
			accountEntryExternalReferenceCodeUnsafeSupplier) {

		try {
			accountEntryExternalReferenceCode =
				accountEntryExternalReferenceCodeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String accountEntryExternalReferenceCode;

	public Long getAccountEntryId() {
		return accountEntryId;
	}

	public void setAccountEntryId(Long accountEntryId) {
		this.accountEntryId = accountEntryId;
	}

	public void setAccountEntryId(
		UnsafeSupplier<Long, Exception> accountEntryIdUnsafeSupplier) {

		try {
			accountEntryId = accountEntryIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long accountEntryId;

	public String getAccountEntryName() {
		return accountEntryName;
	}

	public void setAccountEntryName(String accountEntryName) {
		this.accountEntryName = accountEntryName;
	}

	public void setAccountEntryName(
		UnsafeSupplier<String, Exception> accountEntryNameUnsafeSupplier) {

		try {
			accountEntryName = accountEntryNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String accountEntryName;

	public UserAccount[] getUserAccounts() {
		return userAccounts;
	}

	public void setUserAccounts(UserAccount[] userAccounts) {
		this.userAccounts = userAccounts;
	}

	public void setUserAccounts(
		UnsafeSupplier<UserAccount[], Exception> userAccountsUnsafeSupplier) {

		try {
			userAccounts = userAccountsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected UserAccount[] userAccounts;

	@Override
	public ProvisioningRequest clone() throws CloneNotSupportedException {
		return (ProvisioningRequest)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ProvisioningRequest)) {
			return false;
		}

		ProvisioningRequest provisioningRequest = (ProvisioningRequest)object;

		return Objects.equals(toString(), provisioningRequest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ProvisioningRequestSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:1830339160