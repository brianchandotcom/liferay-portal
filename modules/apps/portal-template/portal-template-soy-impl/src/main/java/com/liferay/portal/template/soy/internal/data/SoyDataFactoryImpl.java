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

package com.liferay.portal.template.soy.internal.data;

import com.liferay.portal.template.soy.data.SoyDataFactory;
import com.liferay.portal.template.soy.data.SoyHTMLData;
import com.liferay.portal.template.soy.util.SoyRawData;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = SoyDataFactory.class)
public class SoyDataFactoryImpl implements SoyDataFactory {

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 * 			#createSoyRawData(String)}
	 */
	@Deprecated
	@Override
	public SoyHTMLData createSoyHTMLData(String html) {
		return new SoyHTMLDataImpl(html);
	}

	@Override
	public SoyRawData createSoyRawData(String html) {
		return new SoyRawDataImpl(html);
	}

}