/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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