/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.client.extension.web.internal.type.deployer;

import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.CustomCheckoutStepCET;
import com.liferay.client.extension.type.deployer.CommerceCETDeployer;
import com.liferay.commerce.client.extension.web.internal.util.CustomCheckoutStep;
import com.liferay.commerce.util.CommerceCheckoutStep;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.catapult.PortalCatapult;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(service = CommerceCETDeployer.class)
public class CommerceCETDeployerImpl implements CommerceCETDeployer {

	@Override
	public List<ServiceRegistration<?>> deploy(CET cet) {
		List<ServiceRegistration<?>> serviceRegistrations = new ArrayList<>();

		CustomCheckoutStepCET customCheckoutStepCET =
			(CustomCheckoutStepCET)cet;

		serviceRegistrations.add(
			_bundleContext.registerService(
				CommerceCheckoutStep.class,
				new CustomCheckoutStep(
					customCheckoutStepCET.getActive(),
					customCheckoutStepCET.getActionURL(),
					customCheckoutStepCET.getBaseURL(), _jsonFactory,
					_jspRenderer, customCheckoutStepCET.getCheckoutStepLabel(),
					customCheckoutStepCET.getCheckoutStepName(),
					customCheckoutStepCET.
						getOAuth2ApplicationExternalReferenceCode(),
					customCheckoutStepCET.getOrder(), _portalCatapult,
					customCheckoutStepCET.getReadyURL(),
					customCheckoutStepCET.getRenderURL(),
					customCheckoutStepCET.getSennaDisabled(), _servletContext,
					customCheckoutStepCET.getShowControls(), _userService,
					customCheckoutStepCET.getVisible()),
				HashMapDictionaryBuilder.<String, Object>put(
					"commerce.checkout.step.name",
					customCheckoutStepCET.getCheckoutStepName()
				).put(
					"commerce.checkout.step.order",
					Integer.valueOf(
						customCheckoutStepCET.getCheckoutStepOrder())
				).build()));

		return serviceRegistrations;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
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
	private JSPRenderer _jspRenderer;

	@Reference
	private PortalCatapult _portalCatapult;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.client.extension.web)"
	)
	private ServletContext _servletContext;

	@Reference
	private UserService _userService;

}