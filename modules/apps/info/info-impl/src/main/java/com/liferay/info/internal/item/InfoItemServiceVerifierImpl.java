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

package com.liferay.info.internal.item;

import com.liferay.info.exception.CapabilityVerificationException;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.InfoItemServiceVerifier;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = InfoItemServiceVerifier.class)
public class InfoItemServiceVerifierImpl implements InfoItemServiceVerifier {

	@Override
	public List<Class<?>> getMissingServiceClasses(
			Class<?>[] requiredInfoItemServiceClasses, String itemClassName)
		throws CapabilityVerificationException {

		List<Class<?>> missingServiceClasses = new ArrayList<>();

		for (Class<?> serviceClass : requiredInfoItemServiceClasses) {
			Object infoItemService =
				_infoItemServiceTracker.getFirstInfoItemService(
					serviceClass, itemClassName);

			if (infoItemService == null) {
				missingServiceClasses.add(serviceClass);
			}
		}

		return missingServiceClasses;
	}

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

}