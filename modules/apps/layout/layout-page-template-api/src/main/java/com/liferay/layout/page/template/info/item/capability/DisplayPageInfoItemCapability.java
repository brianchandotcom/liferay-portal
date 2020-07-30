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

package com.liferay.layout.page.template.info.item.capability;

import com.liferay.info.item.capability.InfoItemCapability;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;

import java.util.Locale;

/**
 * @author Jorge Ferrer
 */
public class DisplayPageInfoItemCapability implements InfoItemCapability {

	public static final DisplayPageInfoItemCapability INSTANCE =
		new DisplayPageInfoItemCapability();

	@Override
	public String getLabel(Locale locale) {
		return "display-page";
	}

	@Override
	public Class<?>[] getRequiredServiceClasses() {
		return new Class<?>[] {
			InfoItemFormProvider.class, InfoItemFieldValuesProvider.class
		};
	}

	private DisplayPageInfoItemCapability() {
	}

}