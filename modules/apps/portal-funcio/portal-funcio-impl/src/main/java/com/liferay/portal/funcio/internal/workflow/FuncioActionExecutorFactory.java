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

package com.liferay.portal.funcio.internal.workflow;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.Dictionary;
import java.util.Map;

import org.osgi.framework.Constants;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.portal.funcio.internal.workflow.configuration.FuncioActionExecutorFactoryConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, service = {}
)
public class FuncioActionExecutorFactory {

	@Activate
	protected void activate(
			ComponentContext componentContext, Map<String, Object> properties)
		throws Exception {

		String key = _getExternalReferenceCode(properties);

		Dictionary<String, Object> copy =
			HashMapDictionaryBuilder.<String, Object>putAll(
				properties
			).build();

		copy.remove(Constants.SERVICE_PID);
		copy.put(
			"com.liferay.portal.workflow.kaleo.runtime.action.executor." +
				"language",
			"function#" + key);

		_componentInstance = _componentFactory.newInstance(copy);
	}

	@Deactivate
	protected void deactivate() {
		if (_componentInstance != null) {
			_componentInstance.dispose();
		}
	}

	private String _getExternalReferenceCode(Map<String, Object> properties) {
		String externalReferenceCode = GetterUtil.getString(
			properties.get(Constants.SERVICE_PID));

		int index = externalReferenceCode.indexOf('~');

		if (index > 0) {
			externalReferenceCode = externalReferenceCode.substring(index + 1);
		}

		return externalReferenceCode;
	}

	@Reference(target = "(component.factory=funcio.action.executor)")
	private ComponentFactory<FuncioActionExecutor> _componentFactory;

	private ComponentInstance<FuncioActionExecutor> _componentInstance;

}