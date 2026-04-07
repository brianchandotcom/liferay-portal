/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.client.dto.v1_0;

import com.liferay.headless.admin.site.client.function.UnsafeSupplier;
import com.liferay.headless.admin.site.client.serdes.v1_0.StayInPageFormContainerSubmissionResultSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class StayInPageFormContainerSubmissionResult
	extends SuccessFormContainerSubmissionResult
	implements Cloneable, Serializable {

	public static StayInPageFormContainerSubmissionResult toDTO(String json) {
		return StayInPageFormContainerSubmissionResultSerDes.toDTO(json);
	}

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
	public StayInPageFormContainerSubmissionResult clone()
		throws CloneNotSupportedException {

		return (StayInPageFormContainerSubmissionResult)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof StayInPageFormContainerSubmissionResult)) {
			return false;
		}

		StayInPageFormContainerSubmissionResult
			stayInPageFormContainerSubmissionResult =
				(StayInPageFormContainerSubmissionResult)object;

		return Objects.equals(
			toString(), stayInPageFormContainerSubmissionResult.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return StayInPageFormContainerSubmissionResultSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-168183375