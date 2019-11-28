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

package com.liferay.info.internal.provider;

import com.liferay.info.internal.util.GenericsUtil;
import com.liferay.info.provider.InfoObjectDDMTemplateProvider;
import com.liferay.info.provider.InfoObjectDDMTemplateProviderTracker;
import com.liferay.portal.kernel.util.Portal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true, service = InfoObjectDDMTemplateProviderTracker.class
)
public class InfoObjectDDMTemplateProviderTrackerImpl
	implements InfoObjectDDMTemplateProviderTracker {

	@Override
	public InfoObjectDDMTemplateProvider getInfoObjectDDMTemplateProvider(
		String className) {

		return _infoObjectDDMTemplateProviders.get(className);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setInfoEditURLProviders(
		InfoObjectDDMTemplateProvider infoObjectDDMTemplateProvider,
		Map<String, Object> properties) {

		String className = GenericsUtil.getItemClassName(
			infoObjectDDMTemplateProvider);

		_infoObjectDDMTemplateProviders.put(
			className, infoObjectDDMTemplateProvider);
	}

	protected void unsetInfoEditURLProviders(
		InfoObjectDDMTemplateProvider infoObjectDDMTemplateProvider,
		Map<String, Object> properties) {

		String className = GenericsUtil.getItemClassName(
			infoObjectDDMTemplateProvider);

		_infoObjectDDMTemplateProviders.remove(className);
	}

	private final Map<String, InfoObjectDDMTemplateProvider>
		_infoObjectDDMTemplateProviders = new ConcurrentHashMap<>();

	@Reference
	private Portal _portal;

}