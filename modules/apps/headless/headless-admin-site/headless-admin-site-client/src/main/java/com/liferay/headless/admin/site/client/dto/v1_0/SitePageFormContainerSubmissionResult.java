/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.SitePageFormContainerSubmissionResultSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class SitePageFormContainerSubmissionResult
	extends SuccessFormContainerSubmissionResult
	implements Cloneable, Serializable {

	public static SitePageFormContainerSubmissionResult toDTO(String json) {
		return SitePageFormContainerSubmissionResultSerDes.toDTO(json);
	}

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
	public SitePageFormContainerSubmissionResult clone()
		throws CloneNotSupportedException {

		return (SitePageFormContainerSubmissionResult)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SitePageFormContainerSubmissionResult)) {
			return false;
		}

		SitePageFormContainerSubmissionResult
			sitePageFormContainerSubmissionResult =
				(SitePageFormContainerSubmissionResult)object;

		return Objects.equals(
			toString(), sitePageFormContainerSubmissionResult.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SitePageFormContainerSubmissionResultSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-1836914457