/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.SuccessNotificationMessageSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class SuccessNotificationMessage implements Cloneable, Serializable {

	public static SuccessNotificationMessage toDTO(String json) {
		return SuccessNotificationMessageSerDes.toDTO(json);
	}

	public FragmentInlineValue getMessage() {
		return message;
	}

	public void setMessage(FragmentInlineValue message) {
		this.message = message;
	}

	public void setMessage(
		UnsafeSupplier<FragmentInlineValue, Exception> messageUnsafeSupplier) {

		try {
			message = messageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentInlineValue message;

	public Boolean getShowNotification() {
		return showNotification;
	}

	public void setShowNotification(Boolean showNotification) {
		this.showNotification = showNotification;
	}

	public void setShowNotification(
		UnsafeSupplier<Boolean, Exception> showNotificationUnsafeSupplier) {

		try {
			showNotification = showNotificationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean showNotification;

	@Override
	public SuccessNotificationMessage clone()
		throws CloneNotSupportedException {

		return (SuccessNotificationMessage)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SuccessNotificationMessage)) {
			return false;
		}

		SuccessNotificationMessage successNotificationMessage =
			(SuccessNotificationMessage)object;

		return Objects.equals(
			toString(), successNotificationMessage.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SuccessNotificationMessageSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:1245827084