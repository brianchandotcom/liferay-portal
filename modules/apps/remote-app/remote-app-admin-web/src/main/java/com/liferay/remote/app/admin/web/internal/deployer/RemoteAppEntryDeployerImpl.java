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

package com.liferay.remote.app.admin.web.internal.deployer;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.remote.app.admin.web.internal.portlet.RemoteAppEntryPortlet;
import com.liferay.remote.app.deployer.RemoteAppEntryDeployer;
import com.liferay.remote.app.model.RemoteAppEntry;

import javax.portlet.Portlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = RemoteAppEntryDeployer.class)
public class RemoteAppEntryDeployerImpl implements RemoteAppEntryDeployer {

	@Override
	public ServiceRegistration<Portlet> deploy(RemoteAppEntry remoteAppEntry) {
		return _bundleContext.registerService(
			Portlet.class,
			new RemoteAppEntryPortlet(
				remoteAppEntry,
				_npmResolver.resolveModuleName(
					"@liferay/remote-app-admin-web/remote_protocol/bridge")),
			HashMapDictionaryBuilder.<String, Object>put(
				"com.liferay.portlet.company", remoteAppEntry.getCompanyId()
			).put(
				"com.liferay.portlet.css-class-wrapper", "portlet-remote-app"
			).put(
				"com.liferay.portlet.display-category", "category.sample"
			).put(
				"com.liferay.portlet.header-portlet-css",
				"/display/css/main.css"
			).put(
				"com.liferay.portlet.instanceable", true
			).put(
				"javax.portlet.display-name",
				remoteAppEntry.getName(LocaleUtil.US)
			).put(
				"javax.portlet.name",
				StringBundler.concat(
					"com_liferay_remote_app_admin_web_internal_portlet_",
					"RemoteAppEntryPortlet_",
					remoteAppEntry.getRemoteAppEntryId())
			).put(
				"javax.portlet.security-role-ref", "power-user,user"
			).build());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private BundleContext _bundleContext;

	@Reference
	private NPMResolver _npmResolver;

}