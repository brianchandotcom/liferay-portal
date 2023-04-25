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

package com.liferay.client.extension.web.internal.type.deployer;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.CustomElementCET;
import com.liferay.client.extension.type.IFrameCET;
import com.liferay.client.extension.type.JSImportMapsEntryCET;
import com.liferay.client.extension.type.deployer.CETDeployer;
import com.liferay.client.extension.web.internal.frontend.js.importmaps.extender.ClientExtensionJSImportMapsContributor;
import com.liferay.client.extension.web.internal.portlet.CETPortletFriendlyURLMapper;
import com.liferay.client.extension.web.internal.portlet.CustomElementCETPortlet;
import com.liferay.client.extension.web.internal.portlet.IFrameCETPortlet;
import com.liferay.client.extension.web.internal.portlet.action.CETPortletConfigurationAction;
import com.liferay.client.extension.web.internal.util.CETUtil;
import com.liferay.frontend.js.importmaps.extender.JSImportMapsContributor;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.portlet.Portlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = CETDeployer.class)
public class CETDeployerImpl implements CETDeployer {

	@Override
	public List<ServiceRegistration<?>> deploy(CET cet) {
		if (Objects.equals(
				cet.getType(),
				ClientExtensionEntryConstants.TYPE_CUSTOM_ELEMENT)) {

			return _deployCustomElementCET((CustomElementCET)cet);
		}
		else if (Objects.equals(
					cet.getType(), ClientExtensionEntryConstants.TYPE_IFRAME)) {

			return _deployIframeCET((IFrameCET)cet);
		}
		else if (Objects.equals(
					cet.getType(),
					ClientExtensionEntryConstants.TYPE_JS_IMPORT_MAPS_ENTRY)) {

			return _deployJsImportMapsEntryCET((JSImportMapsEntryCET)cet);
		}

		return Collections.emptyList();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private List<ServiceRegistration<?>> _deployCustomElementCET(
		CustomElementCET customElementCET) {

		List<ServiceRegistration<?>> serviceRegistrations = new ArrayList<>();

		String portletId = _getPortletId(customElementCET);

		serviceRegistrations.add(
			_register(
				ConfigurationAction.class,
				new CETPortletConfigurationAction(
					"/entry/configuration.jsp", portletId)));

		String friendlyURLMapping = customElementCET.getFriendlyURLMapping();

		if (!customElementCET.isInstanceable() &&
			Validator.isNotNull(friendlyURLMapping)) {

			serviceRegistrations.add(
				_register(
					FriendlyURLMapper.class,
					new CETPortletFriendlyURLMapper(
						friendlyURLMapping, portletId)));
		}

		serviceRegistrations.add(
			_register(
				Portlet.class,
				new CustomElementCETPortlet(
					customElementCET, _npmResolver, portletId)));

		return serviceRegistrations;
	}

	private List<ServiceRegistration<?>> _deployIframeCET(IFrameCET iFrameCET) {
		List<ServiceRegistration<?>> serviceRegistrations = new ArrayList<>();

		String portletId = _getPortletId(iFrameCET);

		serviceRegistrations.add(
			_register(
				ConfigurationAction.class,
				new CETPortletConfigurationAction(
					"/entry/configuration.jsp", portletId)));

		String friendlyURLMapping = iFrameCET.getFriendlyURLMapping();

		if (!iFrameCET.isInstanceable() &&
			Validator.isNotNull(friendlyURLMapping)) {

			serviceRegistrations.add(
				_register(
					FriendlyURLMapper.class,
					new CETPortletFriendlyURLMapper(
						friendlyURLMapping, portletId)));
		}

		serviceRegistrations.add(
			_register(
				Portlet.class,
				new IFrameCETPortlet(iFrameCET, _npmResolver, portletId)));

		return serviceRegistrations;
	}

	private List<ServiceRegistration<?>> _deployJsImportMapsEntryCET(
		JSImportMapsEntryCET jsImportMapsEntryCET) {

		ServiceRegistration<?> serviceRegistration = _register(
			JSImportMapsContributor.class,
			new ClientExtensionJSImportMapsContributor(
				jsImportMapsEntryCET.getBareSpecifier(), _jsonFactory,
				jsImportMapsEntryCET.getURL()));

		return Arrays.asList(serviceRegistration);
	}

	private String _getPortletId(CET cet) {
		return StringBundler.concat(
			"com_liferay_client_extension_web_internal_portlet_",
			"ClientExtensionEntryPortlet_", cet.getCompanyId(),
			StringPool.UNDERLINE,
			CETUtil.normalizeExternalReferenceCodeForPortletId(
				cet.getExternalReferenceCode()));
	}

	private <T extends Registrable> ServiceRegistration<?> _register(
		Class<? super T> clazz, T registrable) {

		return _bundleContext.registerService(
			clazz, registrable, registrable.getDictionary());
	}

	private BundleContext _bundleContext;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private NPMResolver _npmResolver;

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.client.extension.web)(release.schema.version>=2.0.0))"
	)
	private Release _release;

}