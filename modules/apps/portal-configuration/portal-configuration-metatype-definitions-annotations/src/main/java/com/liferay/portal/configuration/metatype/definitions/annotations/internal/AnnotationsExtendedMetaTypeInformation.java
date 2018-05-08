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

package com.liferay.portal.configuration.metatype.definitions.annotations.internal;

import com.liferay.portal.configuration.metatype.definitions.ExtendedMetaTypeInformation;
import com.liferay.portal.configuration.metatype.definitions.ExtendedObjectClassDefinition;

import org.osgi.framework.Bundle;
import org.osgi.service.metatype.MetaTypeInformation;

/**
 * @author Iván Zaera
 */
public class AnnotationsExtendedMetaTypeInformation
	implements ExtendedMetaTypeInformation {

	public AnnotationsExtendedMetaTypeInformation(
		Bundle bundle, MetaTypeInformation metaTypeInformation) {

		_bundle = bundle;
		_metaTypeInformation = metaTypeInformation;
	}

	@Override
	public Bundle getBundle() {
		return _metaTypeInformation.getBundle();
	}

	@Override
	public String[] getFactoryPids() {
		return _metaTypeInformation.getFactoryPids();
	}

	@Override
	public String[] getLocales() {
		return _metaTypeInformation.getLocales();
	}

	@Override
	public ExtendedObjectClassDefinition getObjectClassDefinition(
		String id, String locale) {

		return new AnnotationsExtendedObjectClassDefinition(
			_bundle, _metaTypeInformation.getObjectClassDefinition(id, locale));
	}

	@Override
	public String[] getPids() {
		return _metaTypeInformation.getPids();
	}

	private final Bundle _bundle;
	private final MetaTypeInformation _metaTypeInformation;

}