/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.client.dto.v1_0;

import com.liferay.headless.digital.sales.room.client.function.UnsafeSupplier;
import com.liferay.headless.digital.sales.room.client.serdes.v1_0.InvitedMemberBriefSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Stefano Motta
 * @generated
 */
@Generated("")
public class InvitedMemberBrief implements Cloneable, Serializable {

	public static InvitedMemberBrief toDTO(String json) {
		return InvitedMemberBriefSerDes.toDTO(json);
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setEmailAddress(
		UnsafeSupplier<String, Exception> emailAddressUnsafeSupplier) {

		try {
			emailAddress = emailAddressUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String emailAddress;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long id;

	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

	public void setRoleKey(
		UnsafeSupplier<String, Exception> roleKeyUnsafeSupplier) {

		try {
			roleKey = roleKeyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String roleKey;

	@Override
	public InvitedMemberBrief clone() throws CloneNotSupportedException {
		return (InvitedMemberBrief)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof InvitedMemberBrief)) {
			return false;
		}

		InvitedMemberBrief invitedMemberBrief = (InvitedMemberBrief)object;

		return Objects.equals(toString(), invitedMemberBrief.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return InvitedMemberBriefSerDes.toJSON(this);
	}

}