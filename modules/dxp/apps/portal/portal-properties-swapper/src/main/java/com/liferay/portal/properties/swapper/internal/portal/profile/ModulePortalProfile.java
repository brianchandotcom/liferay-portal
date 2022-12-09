/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.properties.swapper.internal.portal.profile;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.profile.BaseDSModulePortalProfile;
import com.liferay.portal.profile.PortalProfile;
import com.liferay.portal.properties.swapper.internal.DefaultCompanyLogoSwapper;
import com.liferay.portal.properties.swapper.internal.DefaultCompanyNameSwapper;
import com.liferay.portal.properties.swapper.internal.DefaultGuestGroupLogoSwapper;
import com.liferay.portal.properties.swapper.internal.DefaultLiferayLogoSwapper;
import com.liferay.portal.properties.swapper.internal.DefaultLiferayNameSwapper;

import java.util.Collections;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(service = PortalProfile.class)
public class ModulePortalProfile extends BaseDSModulePortalProfile {

	@Activate
	protected void activate(ComponentContext componentContext) {
		init(
			componentContext,
			Collections.singleton(PortalProfile.PORTAL_PROFILE_NAME_DXP),
			DefaultCompanyLogoSwapper.class.getName(),
			DefaultCompanyNameSwapper.class.getName(),
			DefaultGuestGroupLogoSwapper.class.getName(),
			DefaultLiferayLogoSwapper.class.getName(),
			DefaultLiferayNameSwapper.class.getName());
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference(target = "(default=true)")
	private Store _store;

}