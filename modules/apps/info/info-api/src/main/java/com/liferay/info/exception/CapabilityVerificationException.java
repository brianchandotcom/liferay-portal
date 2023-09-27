/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.exception;

import com.liferay.info.item.capability.InfoItemCapability;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;

import java.util.List;

/**
 * @author Jorge Ferrer
 */
public class CapabilityVerificationException extends RuntimeException {

	public CapabilityVerificationException(
		InfoItemCapability infoItemCapability, String infoItemClassName,
		List<Class<?>> missingServiceClasses) {

		_infoItemCapability = infoItemCapability;
		_infoItemClassName = infoItemClassName;
		_missingServiceClasses = missingServiceClasses;
	}

	public InfoItemCapability getInfoItemCapability() {
		return _infoItemCapability;
	}

	public String getInfoItemClassName() {
		return _infoItemClassName;
	}

	@Override
	public String getMessage() {
		return StringBundler.concat(
			"Failed validation of capability ", _infoItemCapability.getKey(),
			" for item class name ", _infoItemClassName,
			". An implementation for the following services is required: ",
			StringUtil.merge(
				TransformUtil.transform(_missingServiceClasses, Class::getName),
				", "),
			".");
	}

	public List<Class<?>> getMissingServiceClasses() {
		return _missingServiceClasses;
	}

	private final InfoItemCapability _infoItemCapability;
	private final String _infoItemClassName;
	private final List<Class<?>> _missingServiceClasses;

}