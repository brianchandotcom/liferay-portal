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

package com.liferay.microsoft.translator.internal;

import com.liferay.microsoft.translator.internal.configuration.MicrosoftTranslatorConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.microsofttranslator.MicrosoftTranslator;
import com.liferay.portal.kernel.microsofttranslator.MicrosoftTranslatorFactory;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Hugo Huijser
 */
@Component(
	configurationPid = "com.liferay.microsoft.translator.internal.configuration.MicrosoftTranslatorConfiguration",
	immediate = true, service = MicrosoftTranslatorFactory.class
)
public class MicrosoftTranslatorFactoryImpl
	implements MicrosoftTranslatorFactory {

	@Override
	public MicrosoftTranslator getMicrosoftTranslator() {
		if (_microsoftTranslator == null) {
			_microsoftTranslator = new MicrosoftTranslatorImpl(
				_microsoftTranslatorConfiguration.subscriptionKey());
		}

		return _microsoftTranslator;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public MicrosoftTranslator getMicrosoftTranslator(
		String clientId, String clientSecret) {

		return new MicrosoftTranslatorImpl(clientSecret);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_microsoftTranslatorConfiguration = ConfigurableUtil.createConfigurable(
			MicrosoftTranslatorConfiguration.class, properties);

		_microsoftTranslator = null;
	}

	@Deactivate
	protected void deactivate() {
		_microsoftTranslator = null;
	}

	private volatile MicrosoftTranslator _microsoftTranslator;
	private volatile MicrosoftTranslatorConfiguration
		_microsoftTranslatorConfiguration;

}