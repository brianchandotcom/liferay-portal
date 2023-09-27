/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.internal.info.item.capability;

import com.liferay.info.exception.CapabilityVerificationException;
import com.liferay.info.item.InfoItemServiceVerifier;
import com.liferay.info.item.capability.InfoItemCapability;
import com.liferay.template.info.item.capability.TemplateInfoItemCapability;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "info.item.capability.key=" + TemplateInfoItemCapability.KEY,
	service = InfoItemCapability.class
)
public class TemplateInfoItemCapabilityImpl
	implements TemplateInfoItemCapability {

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		return "template";
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