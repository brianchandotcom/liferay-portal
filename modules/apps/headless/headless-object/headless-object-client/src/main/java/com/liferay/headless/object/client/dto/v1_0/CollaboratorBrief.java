/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.object.client.dto.v1_0;

import com.liferay.headless.object.client.function.UnsafeSupplier;
import com.liferay.headless.object.client.serdes.v1_0.CollaboratorBriefSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Date;
import java.util.Objects;

/**
 * @author Alicia García
 * @generated
 */
@Generated("")
public class CollaboratorBrief implements Cloneable, Serializable {

	public static CollaboratorBrief toDTO(String json) {
		return CollaboratorBriefSerDes.toDTO(json);
	}

	public String[] getActionIds() {
		return actionIds;
	}

	public void setActionIds(String[] actionIds) {
		this.actionIds = actionIds;
	}

	public void setActionIds(
		UnsafeSupplier<String[], Exception> actionIdsUnsafeSupplier) {

		try {
			actionIds = actionIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] actionIds;

	public Date getDateExpired() {
		return dateExpired;
	}

	public void setDateExpired(Date dateExpired) {
		this.dateExpired = dateExpired;
	}

	public void setDateExpired(
		UnsafeSupplier<Date, Exception> dateExpiredUnsafeSupplier) {

		try {
			dateExpired = dateExpiredUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateExpired;

	public Boolean getShare() {
		return share;
	}

	public void setShare(Boolean share) {
		this.share = share;
	}

	public void setShare(
		UnsafeSupplier<Boolean, Exception> shareUnsafeSupplier) {

		try {
			share = shareUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean share;

	@Override
	public CollaboratorBrief clone() throws CloneNotSupportedException {
		return (CollaboratorBrief)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CollaboratorBrief)) {
			return false;
		}

		CollaboratorBrief collaboratorBrief = (CollaboratorBrief)object;

		return Objects.equals(toString(), collaboratorBrief.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CollaboratorBriefSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:1878192204