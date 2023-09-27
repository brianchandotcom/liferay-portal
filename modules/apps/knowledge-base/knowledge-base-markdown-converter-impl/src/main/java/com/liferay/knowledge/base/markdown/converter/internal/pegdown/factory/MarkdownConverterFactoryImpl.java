/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.markdown.converter.internal.pegdown.factory;

import com.liferay.knowledge.base.markdown.converter.MarkdownConverter;
import com.liferay.knowledge.base.markdown.converter.factory.MarkdownConverterFactory;
import com.liferay.knowledge.base.markdown.converter.internal.pegdown.LiferayPegDownConverter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(enabled = true, service = MarkdownConverterFactory.class)
public class MarkdownConverterFactoryImpl implements MarkdownConverterFactory {

	@Override
	public MarkdownConverter create() {
		return new LiferayPegDownConverter();
	}

}