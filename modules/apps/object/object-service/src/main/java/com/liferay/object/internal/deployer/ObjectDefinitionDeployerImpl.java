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

import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.object.deployer.ObjectDefinitionDeployer;
import com.liferay.object.internal.info.list.provider.ObjectEntrySingleFormVariationInfoCollectionProvider;
import com.liferay.object.internal.workflow.ObjectEntryWorkflowHandler;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.workflow.WorkflowHandler;

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

		return Arrays.asList(
			_bundleContext.registerService(
				InfoCollectionProvider.class,
				new ObjectEntrySingleFormVariationInfoCollectionProvider(
					objectDefinition, _objectEntryLocalService),
				null),
			_bundleContext.registerService(
				WorkflowHandler.class,
				new ObjectEntryWorkflowHandler(
					objectDefinition, _objectEntryLocalService),
				HashMapDictionaryBuilder.<String, Object>put(
					"model.class.name", objectDefinition.getClassName()
				).build()));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private BundleContext _bundleContext;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}