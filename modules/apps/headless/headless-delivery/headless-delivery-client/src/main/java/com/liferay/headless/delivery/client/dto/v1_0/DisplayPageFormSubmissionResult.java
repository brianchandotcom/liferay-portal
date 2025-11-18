/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.client.dto.v1_0;

import com.liferay.headless.delivery.client.function.UnsafeSupplier;
import com.liferay.headless.delivery.client.serdes.v1_0.DisplayPageFormSubmissionResultSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class DisplayPageFormSubmissionResult
	implements Cloneable, Serializable {

	public static DisplayPageFormSubmissionResult toDTO(String json) {
		return DisplayPageFormSubmissionResultSerDes.toDTO(json);
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

	public Mapping getMapping() {
		return mapping;
	}

	public void setMapping(Mapping mapping) {
		this.mapping = mapping;
	}

	public void setMapping(
		UnsafeSupplier<Mapping, Exception> mappingUnsafeSupplier) {

		try {
			mapping = mappingUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Mapping mapping;

	public FragmentInlineValue getNotificationText() {
		return notificationText;
	}

	public void setNotificationText(FragmentInlineValue notificationText) {
		this.notificationText = notificationText;
	}

	public void setNotificationText(
		UnsafeSupplier<FragmentInlineValue, Exception>
			notificationTextUnsafeSupplier) {

		try {
			notificationText = notificationTextUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FragmentInlineValue notificationText;

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
	public DisplayPageFormSubmissionResult clone()
		throws CloneNotSupportedException {

		return (DisplayPageFormSubmissionResult)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DisplayPageFormSubmissionResult)) {
			return false;
		}

		DisplayPageFormSubmissionResult displayPageFormSubmissionResult =
			(DisplayPageFormSubmissionResult)object;

		return Objects.equals(
			toString(), displayPageFormSubmissionResult.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DisplayPageFormSubmissionResultSerDes.toJSON(this);
	}

}