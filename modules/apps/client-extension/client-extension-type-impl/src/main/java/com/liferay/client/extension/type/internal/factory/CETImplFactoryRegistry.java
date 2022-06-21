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

package com.liferay.client.extension.type.internal.factory;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.exception.ClientExtensionEntryTypeException;
import com.liferay.client.extension.type.factory.CETImplFactory;
import com.liferay.petra.string.CharPool;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = CETImplFactoryRegistry.class)
public class CETImplFactoryRegistry {

	public CETImplFactory getCETImplFactory(String type)
		throws ClientExtensionEntryTypeException {

		try {
			Class<? extends CETImplFactoryRegistry> clazz = getClass();

			Field field = clazz.getDeclaredField("_" + type + "CETImplFactory");

			return (CETImplFactory)field.get(this);
		}
		catch (Exception exception) {
			throw new ClientExtensionEntryTypeException(
				"No CET implementation factory registered for type " + type,
				exception);
		}
	}

	public Collection<String> getTypes() {
		return _types;
	}

	@Activate
	protected void activate() {
		_types = new ArrayList<>();

		Class<? extends CETImplFactoryRegistry> clazz = getClass();

		for (Field field : clazz.getDeclaredFields()) {
			String name = field.getName();

			if (name.charAt(0) != CharPool.UNDERLINE) {
				continue;
			}

			int i = name.indexOf("CETImplFactory");

			if (i == -1) {
				return;
			}

			_types.add(name.substring(1, i));
		}

		_types = Collections.unmodifiableList(_types);
	}

	@Reference(
		target = "(type=" + ClientExtensionEntryConstants.TYPE_CUSTOM_ELEMENT + ")"
	)
	private CETImplFactory _customElementCETImplFactory;

	@Reference(
		target = "(type=" + ClientExtensionEntryConstants.TYPE_GLOBAL_CSS + ")"
	)
	private CETImplFactory _globalCSSCETImplFactory;

	@Reference(
		target = "(type=" + ClientExtensionEntryConstants.TYPE_GLOBAL_JS + ")"
	)
	private CETImplFactory _globalJSCETImplFactory;

	@Reference(
		target = "(type=" + ClientExtensionEntryConstants.TYPE_IFRAME + ")"
	)
	private CETImplFactory _iframeCETImplFactory;

	@Reference(
		target = "(type=" + ClientExtensionEntryConstants.TYPE_THEME_CSS + ")"
	)
	private CETImplFactory _themeCSSCETImplFactory;

	@Reference(
		target = "(type=" + ClientExtensionEntryConstants.TYPE_THEME_FAVICON + ")"
	)
	private CETImplFactory _themeFaviconCETImplFactory;

	@Reference(
		target = "(type=" + ClientExtensionEntryConstants.TYPE_THEME_JS + ")"
	)
	private CETImplFactory _themeJSCETImplFactory;

	private List<String> _types;

}