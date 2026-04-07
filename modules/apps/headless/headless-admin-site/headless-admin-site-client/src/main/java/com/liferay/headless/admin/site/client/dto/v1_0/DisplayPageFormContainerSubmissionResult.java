/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.DisplayPageFormContainerSubmissionResultSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class DisplayPageFormContainerSubmissionResult
	extends SuccessFormContainerSubmissionResult
	implements Cloneable, Serializable {

	public static DisplayPageFormContainerSubmissionResult toDTO(String json) {
		return DisplayPageFormContainerSubmissionResultSerDes.toDTO(json);
	}

	public Boolean getDefaultDisplayPage() {
		return defaultDisplayPage;
	}

	public void setDefaultDisplayPage(Boolean defaultDisplayPage) {
		this.defaultDisplayPage = defaultDisplayPage;
	}

	public void setDefaultDisplayPage(
		UnsafeSupplier<Boolean, Exception> defaultDisplayPageUnsafeSupplier) {

		try {
			defaultDisplayPage = defaultDisplayPageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean defaultDisplayPage;

	public ItemExternalReference getItemExternalReference() {
		return itemExternalReference;
	}

	public void setItemExternalReference(
		ItemExternalReference itemExternalReference) {

		this.itemExternalReference = itemExternalReference;
	}

	public void setItemExternalReference(
		UnsafeSupplier<ItemExternalReference, Exception>
			itemExternalReferenceUnsafeSupplier) {

		try {
			itemExternalReference = itemExternalReferenceUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ItemExternalReference itemExternalReference;

	public SuccessNotificationMessage getSuccessNotificationMessage() {
		return successNotificationMessage;
	}

	public void setSuccessNotificationMessage(
		SuccessNotificationMessage successNotificationMessage) {

		this.successNotificationMessage = successNotificationMessage;
	}

	public void setSuccessNotificationMessage(
		UnsafeSupplier<SuccessNotificationMessage, Exception>
			successNotificationMessageUnsafeSupplier) {

		try {
			successNotificationMessage =
				successNotificationMessageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected SuccessNotificationMessage successNotificationMessage;

	@Override
	public DisplayPageFormContainerSubmissionResult clone()
		throws CloneNotSupportedException {

		return (DisplayPageFormContainerSubmissionResult)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DisplayPageFormContainerSubmissionResult)) {
			return false;
		}

		DisplayPageFormContainerSubmissionResult
			displayPageFormContainerSubmissionResult =
				(DisplayPageFormContainerSubmissionResult)object;

		return Objects.equals(
			toString(), displayPageFormContainerSubmissionResult.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DisplayPageFormContainerSubmissionResultSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-130976216