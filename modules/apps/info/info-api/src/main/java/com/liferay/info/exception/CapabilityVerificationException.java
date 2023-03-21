/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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