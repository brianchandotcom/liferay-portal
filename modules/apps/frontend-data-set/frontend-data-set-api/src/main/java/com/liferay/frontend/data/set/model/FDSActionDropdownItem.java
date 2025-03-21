/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.model;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;

import java.util.List;

/**
 * @author Marco Leo
 */
public class FDSActionDropdownItem extends DropdownItem {

	public FDSActionDropdownItem(
		String href, String icon, String id, String label, String method,
		String permissionKey, String target) {

		setHref(href);
		setIcon(icon);
		setId(id);
		setLabel(label);
		setMethod(method);
		setPermissionKey(permissionKey);
		setTarget(target);
	}

	public FDSActionDropdownItem(
		String href, String icon, String id, String label, String method,
		String permissionKey, String target,
		List<FDSActionDropdownItemFilter> visibilityFilters) {

		setHref(href);
		setIcon(icon);
		setId(id);
		setLabel(label);
		setMethod(method);
		setPermissionKey(permissionKey);
		setTarget(target);
		setVisibilityFilters(visibilityFilters);
	}

	public FDSActionDropdownItem(
		String confirmationMessage, String href, String icon, String id,
		String label, String method, String permissionKey, String target) {

		this(href, icon, id, label, method, permissionKey, target);

		setConfirmationMessage(confirmationMessage);
	}

	public FDSActionDropdownItem(
		String confirmationMessage, String confirmationMessageType,
		String errorMessage, String href, String icon, String id, String label,
		String method, String modalSize, String permissionKey,
		String requestBody, String successMessage, String target, String title,
		String type) {

		this(href, icon, id, label, method, permissionKey, target);

		setConfirmationMessage(confirmationMessage);
		setConfirmationMessageType(confirmationMessageType);
		setErrorMessage(errorMessage);
		setModalSize(modalSize);
		setRequestBody(requestBody);
		setRequestBody(requestBody);
		setSuccessMessage(successMessage);
		setTitle(title);
		setType(type);
	}

	public FDSActionDropdownItem(
		String confirmationMessage, String confirmationMessageType,
		String errorMessage, String href, String icon, String id, String label,
		String method, String modalSize, String permissionKey,
		String requestBody, String successMessage, String target, String title,
		String type, List<FDSActionDropdownItemFilter> visibilityFilters) {

		this(
			href, icon, id, label, method, permissionKey, target,
			visibilityFilters);

		setConfirmationMessage(confirmationMessage);
		setConfirmationMessageType(confirmationMessageType);
		setErrorMessage(errorMessage);
		setModalSize(modalSize);
		setRequestBody(requestBody);
		setRequestBody(requestBody);
		setSuccessMessage(successMessage);
		setTitle(title);
		setType(type);
	}

	public void setConfirmationMessage(String confirmationMessage) {
		putData("confirmationMessage", confirmationMessage);
	}

	public void setConfirmationMessageType(String confirmationMessageType) {
		putData("confirmationMessageType", confirmationMessageType);
	}

	public void setErrorMessage(String errorMessage) {
		putData("errorMessage", errorMessage);
	}

	public void setId(String id) {
		putData("id", id);
	}

	public void setMethod(String method) {
		putData("method", method);
	}

	public void setModalSize(String modalSize) {
		putData("modalSize", modalSize);
	}

	public void setPermissionKey(String permissionKey) {
		putData("permissionKey", permissionKey);
	}

	public void setRequestBody(String requestBody) {
		putData("requestBody", requestBody);
	}

	public void setSuccessMessage(String successMessage) {
		putData("successMessage", successMessage);
	}

	public void setTitle(String title) {
		putData("title", title);
	}

	public void setType(String type) {
		putData("type", type);
	}

	public void setVisibilityFilters(
		List<FDSActionDropdownItemFilter> fdsActionDropdownItemFilters) {

		putData("visibilityFilters", fdsActionDropdownItemFilters);
	}

}