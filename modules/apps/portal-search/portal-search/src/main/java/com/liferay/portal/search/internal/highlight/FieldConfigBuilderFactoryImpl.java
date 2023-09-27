/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.highlight;

import com.liferay.portal.search.highlight.FieldConfigBuilder;
import com.liferay.portal.search.highlight.FieldConfigBuilderFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bryan Engler
 */
@Component(service = FieldConfigBuilderFactory.class)
public class FieldConfigBuilderFactoryImpl
	implements FieldConfigBuilderFactory {

	@Override
	public FieldConfigBuilder builder(String fieldName) {
		return new FieldConfigImpl.FieldConfigBuilderImpl(fieldName);
	}

}