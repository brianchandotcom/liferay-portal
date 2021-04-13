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

package com.liferay.translation.google.cloud.translator.internal.translator;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.translation.google.cloud.translator.internal.configuration.GoogleCloudTranslatorConfiguration;
import com.liferay.translation.translator.Translator;
import com.liferay.translation.translator.TranslatorPacket;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import java.util.Map;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.translation.google.cloud.translator.internal.configuration.GoogleCloudTranslatorConfiguration",
	service = Translator.class
)
public class GoogleCloudTranslator implements Translator {

	@Override
	public TranslatorPacket translate(TranslatorPacket translatorPacket) {
		return translatorPacket;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_googleCloudTranslatorConfiguration =
			ConfigurableUtil.createConfigurable(
				GoogleCloudTranslatorConfiguration.class, properties);
	}

	private GoogleCloudTranslatorConfiguration _googleCloudTranslatorConfiguration;

}