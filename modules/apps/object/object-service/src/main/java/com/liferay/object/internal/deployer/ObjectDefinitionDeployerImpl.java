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

package com.liferay.object.internal.deployer;

import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.object.deployer.ObjectDefinitionDeployer;
import com.liferay.object.internal.info.list.provider.ObjectEntryInfoListProvider;
import com.liferay.object.internal.security.permission.resource.ObjectEntryModelResourcePermission;
import com.liferay.object.internal.security.permission.resource.ObjectEntryPortletPermissionLogic;
import com.liferay.object.internal.workflow.ObjectEntryWorkflowHandler;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.exception.ResourceActionsException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = ObjectDefinitionDeployer.class)
public class ObjectDefinitionDeployerImpl implements ObjectDefinitionDeployer {

	public List<ServiceRegistration<?>> deploy(
		ObjectDefinition objectDefinition) {

		try {
			_importResourceAction(objectDefinition);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		PortletResourcePermission portletResourcePermission =
			PortletResourcePermissionFactory.create(
				objectDefinition.getResourceName(),
				new ObjectEntryPortletPermissionLogic());

		return Arrays.asList(
			_bundleContext.registerService(
				InfoListProvider.class,
				new ObjectEntryInfoListProvider(
					objectDefinition, _objectEntryLocalService),
				null),
			_bundleContext.registerService(
				WorkflowHandler.class,
				new ObjectEntryWorkflowHandler(
					objectDefinition, _objectEntryLocalService),
				HashMapDictionaryBuilder.<String, Object>put(
					"model.class.name", objectDefinition.getClassName()
				).build()),
			_bundleContext.registerService(
				PortletResourcePermission.class, portletResourcePermission,
				HashMapDictionaryBuilder.<String, Object>put(
					"resource.name", objectDefinition.getResourceName()
				).put(
					"object", "true"
				).build()),
			_bundleContext.registerService(
				ModelResourcePermission.class,
				new ObjectEntryModelResourcePermission(
					objectDefinition.getClassName(), _objectEntryLocalService,
					portletResourcePermission),
				HashMapDictionaryBuilder.<String, Object>put(
					"model.class.name", objectDefinition.getClassName()
				).put(
					"object", "true"
				).build()));
	}

	@Override
	public void undeploy(ObjectDefinition objectDefinition) {

		//TODO Should we remove the resource actions at each

		// undeploy or just if we delete the object?
		// How does it works in portal?

	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private void _importResourceAction(ObjectDefinition objectDefinition)
		throws DocumentException, IOException, ResourceActionsException {

		String xml = StringUtil.read(
			ObjectDefinitionDeployerImpl.class.getClassLoader(),
			_DEPENDENCIES_PATH + "resource-actions.xml.tpl");

		_resourceActions.populateModelResources(
			SAXReaderUtil.read(
				StringUtil.replace(
					StringUtil.replace(
						xml, "[$RESOURCE_NAME$]", objectDefinition.getName()),
					"[$MODEL_NAME$]", objectDefinition.getDBTableName())));
	}

	private static final String _DEPENDENCIES_PATH = "resource-actions/";

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectDefinitionDeployerImpl.class);

	private BundleContext _bundleContext;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ResourceActions _resourceActions;

}