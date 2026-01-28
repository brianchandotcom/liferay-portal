/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.rest.client.dto.v1_0;

import com.liferay.segments.asah.rest.client.function.UnsafeSupplier;
import com.liferay.segments.asah.rest.client.serdes.v1_0.MembershipSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Membership implements Cloneable, Serializable {

	public static Membership toDTO(String json) {
		return MembershipSerDes.toDTO(json);
	}

	public String getIndividualPK() {
		return individualPK;
	}

	public void setIndividualPK(String individualPK) {
		this.individualPK = individualPK;
	}

	public void setIndividualPK(
		UnsafeSupplier<String, Exception> individualPKUnsafeSupplier) {

		try {
			individualPK = individualPKUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String individualPK;

	public String[] getIndividualSegmentIds() {
		return individualSegmentIds;
	}

	public void setIndividualSegmentIds(String[] individualSegmentIds) {
		this.individualSegmentIds = individualSegmentIds;
	}

	public void setIndividualSegmentIds(
		UnsafeSupplier<String[], Exception>
			individualSegmentIdsUnsafeSupplier) {

		try {
			individualSegmentIds = individualSegmentIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] individualSegmentIds;

	public Boolean getRemoved() {
		return removed;
	}

	public void setRemoved(Boolean removed) {
		this.removed = removed;
	}

	public void setRemoved(
		UnsafeSupplier<Boolean, Exception> removedUnsafeSupplier) {

		try {
			removed = removedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean removed;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setUserId(
		UnsafeSupplier<Long, Exception> userIdUnsafeSupplier) {

		try {
			userId = userIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long userId;

	@Override
	public Membership clone() throws CloneNotSupportedException {
		return (Membership)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Membership)) {
			return false;
		}

		Membership membership = (Membership)object;

		return Objects.equals(toString(), membership.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return MembershipSerDes.toJSON(this);
	}

}