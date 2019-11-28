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

package com.liferay.info.internal.item.provider;

import com.liferay.info.internal.util.GenericsUtil;
import com.liferay.info.item.provider.InfoItemDDMTemplateProvider;
import com.liferay.info.item.provider.InfoItemDDMTemplateProviderTracker;
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
@Component(immediate = true, service = InfoItemDDMTemplateProviderTracker.class)
public class InfoItemDDMTemplateProviderTrackerImpl
	implements InfoItemDDMTemplateProviderTracker {

	@Override
	public InfoItemDDMTemplateProvider getInfoItemDDMTemplateProvider(
		String className) {

		return _infoItemDDMTemplateProviders.get(className);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setInfoEditURLProviders(
		InfoItemDDMTemplateProvider infoItemDDMTemplateProvider,
		Map<String, Object> properties) {

		String className = GenericsUtil.getItemClassName(
			infoItemDDMTemplateProvider);

		_infoItemDDMTemplateProviders.put(
			className, infoItemDDMTemplateProvider);
	}

	protected void unsetInfoEditURLProviders(
		InfoItemDDMTemplateProvider infoItemDDMTemplateProvider,
		Map<String, Object> properties) {

		String className = GenericsUtil.getItemClassName(
			infoItemDDMTemplateProvider);

		_infoItemDDMTemplateProviders.remove(className);
	}

	private final Map<String, InfoItemDDMTemplateProvider>
		_infoItemDDMTemplateProviders = new ConcurrentHashMap<>();

	@Reference
	private Portal _portal;

}