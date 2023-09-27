/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = {})
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