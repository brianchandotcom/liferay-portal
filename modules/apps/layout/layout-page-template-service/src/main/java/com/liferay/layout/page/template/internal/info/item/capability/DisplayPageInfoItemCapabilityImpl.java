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

package com.liferay.layout.page.template.internal.info.item.capability;

import com.liferay.info.exception.CapabilityVerificationException;
import com.liferay.info.item.InfoItemServiceVerifier;
import com.liferay.info.item.capability.InfoItemCapability;
import com.liferay.layout.page.template.info.item.capability.DisplayPageInfoItemCapability;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(
	service = {DisplayPageInfoItemCapability.class, InfoItemCapability.class}
)
public class DisplayPageInfoItemCapabilityImpl
	implements DisplayPageInfoItemCapability {

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		return "display-page";
	}

	@Override
	public void verify(String itemClassName)
		throws CapabilityVerificationException {

		List<Class<?>> missingServiceClasses =
			_infoItemServiceVerifier.getMissingServiceClasses(
				REQUIRED_INFO_ITEM_SERVICE_CLASSES, itemClassName);

		if (!missingServiceClasses.isEmpty()) {
			throw new CapabilityVerificationException(
				this, itemClassName, missingServiceClasses);
		}
	}

	@Reference
	private InfoItemServiceVerifier _infoItemServiceVerifier;

}