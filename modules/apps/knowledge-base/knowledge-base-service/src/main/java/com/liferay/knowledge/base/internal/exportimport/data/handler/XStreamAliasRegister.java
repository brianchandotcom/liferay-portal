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

package com.liferay.knowledge.base.internal.exportimport.data.handler;

import com.liferay.exportimport.kernel.xstream.XStreamAlias;
import com.liferay.knowledge.base.model.impl.KBArticleImpl;
import com.liferay.knowledge.base.model.impl.KBCommentImpl;
import com.liferay.knowledge.base.model.impl.KBTemplateImpl;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Lance Ji
 */
@Component(immediate = true, service = {})
public class XStreamAliasRegister {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceRegistrations.add(
			bundleContext.registerService(
				XStreamAlias.class,
				new XStreamAlias(KBArticleImpl.class, "KBArticle"), null));
		_serviceRegistrations.add(
			bundleContext.registerService(
				XStreamAlias.class,
				new XStreamAlias(KBCommentImpl.class, "KBComment"), null));
		_serviceRegistrations.add(
			bundleContext.registerService(
				XStreamAlias.class,
				new XStreamAlias(KBTemplateImpl.class, "KBTemplate"), null));
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();

}