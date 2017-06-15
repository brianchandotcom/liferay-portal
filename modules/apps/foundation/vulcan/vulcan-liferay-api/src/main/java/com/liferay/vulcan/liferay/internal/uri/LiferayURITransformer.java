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

package com.liferay.vulcan.liferay.internal.uri;

import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.vulcan.liferay.scope.GroupScoped;
import com.liferay.vulcan.representor.Resource;
import com.liferay.vulcan.uri.CollectionResourceURITransformer;
import com.liferay.vulcan.wiring.osgi.ResourceManager;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class LiferayURITransformer implements CollectionResourceURITransformer {

	@Override
	public <T> String transformCollectionItemSingleResourceURI(
		String uri, Class<T> modelClass, T model) {

		Optional<Resource<T>> optional = _resourceManager.getResourceOptional(
			modelClass);

		return optional.filter(
			resource -> resource instanceof GroupScoped
		).map(
			resource -> {
				GroupScoped<T> groupScoped = (GroupScoped<T>)resource;

				long groupId = groupScoped.getGroupId(model);

				return String.format("/group/%d/%s", groupId, uri);
			}
		).orElse(
			uri
		);
	}

	@Override
	public <T> String transformPageURI(String uri, Class<T> modelClass) {
		Optional<Resource<T>> optional = _resourceManager.getResourceOptional(
			modelClass);

		return optional.filter(
			resource -> resource instanceof GroupScoped
		).map(
			resource -> String.format(
				"/group/%d/%s", GroupThreadLocal.getGroupId(), uri)

		).orElse(
			uri
		);
	}

	@Reference
	private ResourceManager _resourceManager;

}