/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.exception;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Javier Moral
 */
public class NoSuchEntityException extends PortalException {

	public NoSuchEntityException(String entity, String externalReferenceCode) {
		this(entity, externalReferenceCode, null);
	}

	public NoSuchEntityException(
		String entity, String externalReferenceCode, String parentEntity) {

		super(_getMessage(entity, externalReferenceCode, parentEntity));

		_entity = entity;
		_externalReferenceCode = externalReferenceCode;
		_parentEntity = parentEntity;
	}

	public String getEntity() {
		return _entity;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public String getParentEntity() {
		return _parentEntity;
	}

	private static String _getMessage(
		String entity, String externalReferenceCode, String parentEntity) {

		if (Validator.isNull(parentEntity)) {
			return StringBundler.concat(
				"No ", entity, " exists with the external reference code ",
				externalReferenceCode);
		}

		return StringBundler.concat(
			"No ", entity, " with external reference code ",
			externalReferenceCode, " exists in this ", parentEntity);
	}

	private final String _entity;
	private final String _externalReferenceCode;
	private final String _parentEntity;

}