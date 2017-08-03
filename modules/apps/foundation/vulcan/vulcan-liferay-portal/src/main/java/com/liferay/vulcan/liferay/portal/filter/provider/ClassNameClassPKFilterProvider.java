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

package com.liferay.vulcan.liferay.portal.filter.provider;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.vulcan.filter.FilterProvider;
import com.liferay.vulcan.filter.TypeIdFilterProviderHelper;
import com.liferay.vulcan.liferay.portal.filter.ClassNameClassPKFilter;
import com.liferay.vulcan.liferay.portal.internal.filter.ClassNameClassPKFilterImpl;
import com.liferay.vulcan.resource.Resource;
import com.liferay.vulcan.wiring.osgi.manager.ResourceManager;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Allows resources to provide {@link ClassNameClassPKFilter} in {@link
 * com.liferay.vulcan.resource.builder.RoutesBuilder}'s
 * <code>filteredCollectionPage</code> methods.
 *
 * As well as some utility methods for getting a filter's query param map, a
 * filter's name or creating a new filter based on a <code>className</code> and
 * a <code>classPK</code>.
 *
 * @author Alejandro Hernández
 */
@Component(
	immediate = true,
	service = {ClassNameClassPKFilterProvider.class, FilterProvider.class}
)
public class ClassNameClassPKFilterProvider
	implements FilterProvider<ClassNameClassPKFilter> {

	/**
	 * Creates a new {@link ClassNameClassPKFilter} from a given
	 * <code>className</code> and <code>classPK</code>.
	 *
	 * @param  className the className that will be used to filter.
	 * @param  classPK the classPK that will be used to filter.
	 * @return an instance of a {@link ClassNameClassPKFilter}.
	 */
	public ClassNameClassPKFilter create(String className, Long classPK) {
		Optional<Resource<Object>> optional =
			_resourceManager.getResourceOptional(className);

		Resource resource = optional.orElseThrow(NotFoundException::new);

		return new ClassNameClassPKFilterImpl(
			className, classPK, resource.getPath());
	}

	@Override
	public String getFilterName() {
		return "assetType_id";
	}

	@Override
	public Map<String, String> getQueryParamMap(
		ClassNameClassPKFilter queryParamFilterType) {

		return _typeIdFilterProviderHelper.getQueryParamMap(
			queryParamFilterType);
	}

	@Override
	public ClassNameClassPKFilter provide(
		HttpServletRequest httpServletRequest) {

		String id = _typeIdFilterProviderHelper.getId(httpServletRequest);
		String type = _typeIdFilterProviderHelper.getType(httpServletRequest);

		Long classPK = GetterUtil.getLong(id);

		if ((type == null) || (classPK == GetterUtil.DEFAULT_LONG)) {
			throw new BadRequestException();
		}

		String className = _resourceManager.getClassName(type);

		return new ClassNameClassPKFilterImpl(className, classPK, type);
	}

	@Reference
	private ResourceManager _resourceManager;

	@Reference
	private TypeIdFilterProviderHelper _typeIdFilterProviderHelper;

}