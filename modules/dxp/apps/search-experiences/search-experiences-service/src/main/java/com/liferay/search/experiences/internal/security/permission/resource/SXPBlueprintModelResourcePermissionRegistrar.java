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

package com.liferay.search.experiences.internal.security.permission.resource;

import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.StagedModelPermissionLogic;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.search.experiences.constants.SXPConstants;
import com.liferay.search.experiences.constants.SXPPortletKeys;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = {})
public class SXPBlueprintModelResourcePermissionRegistrar {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceRegistration = bundleContext.registerService(
			(Class<ModelResourcePermission<SXPBlueprint>>)
				(Class<?>)ModelResourcePermission.class,
			ModelResourcePermissionFactory.create(
				SXPBlueprint.class, SXPBlueprint::getSXPBlueprintId,
				_sxpBlueprintLocalService::getSXPBlueprint,
				_portletResourcePermission,
				(modelResourcePermission, consumer) -> consumer.accept(
					new StagedModelPermissionLogic<>(
						_stagingPermission, SXPPortletKeys.SXP_BLUEPRINTS_ADMIN,
						SXPBlueprint::getSXPBlueprintId))),
			HashMapDictionaryBuilder.<String, Object>put(
				"model.class.name", SXPBlueprint.class.getName()
			).build());
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	@Reference(target = "(resource.name=" + SXPConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

	private ServiceRegistration<ModelResourcePermission<SXPBlueprint>>
		_serviceRegistration;

	@Reference
	private StagingPermission _stagingPermission;

	@Reference
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

}