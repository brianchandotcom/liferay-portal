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

package com.liferay.vulcan.application.internal.endpoint.liferay;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.vulcan.contributor.APIContributor;
import com.liferay.vulcan.contributor.PathProvider;
import com.liferay.vulcan.endpoint.RootEndpoint;
import com.liferay.vulcan.resource.GroupScoped;
import com.liferay.vulcan.resource.Resource;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = LiferayRootEndpoint.class)
@Path("/")
public class LiferayRootEndpoint implements RootEndpoint {

	@Activate
	public void activate(BundleContext bundleContext) {
		ServiceReferenceMapper<String, APIContributor> serviceMapper =
			ServiceReferenceMapperFactory.create(
				bundleContext, (service, emitter) ->
					emitter.emit(service.getPath()));

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, APIContributor.class, null, serviceMapper);
	}

	@Deactivate
	public void deactivate() {
		_serviceTrackerMap.close();
	}

	@Override
	@Path("/{path}")
	public Resource getApiContributors(@PathParam("path") String path) {
		if (!_serviceTrackerMap.containsKey(path)) {
			throw new NotFoundException();
		}

		APIContributor apiContributor = _serviceTrackerMap.getService(path);

		if (apiContributor instanceof PathProvider) {
			LiferayDispatcher liferayDispatcher = new LiferayDispatcher(
				(PathProvider)apiContributor);

			_resourceContext.initResource(liferayDispatcher);

			return liferayDispatcher;
		}

		if (apiContributor instanceof Resource) {
			Resource resource = (Resource)apiContributor;

			if (resource instanceof GroupScoped) {
				((GroupScoped)resource).setGroupId(
					GroupThreadLocal.getGroupId());
			}

			_resourceContext.initResource(resource);

			return resource;
		}

		throw new NotFoundException();
	}

	@Path("/group/{id}/")
	public LiferayRootEndpoint getGroupScopeResource(@PathParam("id") long id) {
		GroupThreadLocal.setGroupId(id);

		return this;
	}

	@Context
	protected void setResourceContext(ResourceContext resourceContext) {
		_resourceContext = resourceContext;
	}

	private ResourceContext _resourceContext;
	private ServiceTrackerMap<String, APIContributor> _serviceTrackerMap;

}