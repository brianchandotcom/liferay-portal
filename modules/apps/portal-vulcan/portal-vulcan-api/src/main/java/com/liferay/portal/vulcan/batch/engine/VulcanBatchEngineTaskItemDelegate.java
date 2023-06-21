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

package com.liferay.portal.vulcan.batch.engine;

import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.Serializable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.UriInfo;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Javier Gamarra
 */
@ProviderType
public interface VulcanBatchEngineTaskItemDelegate<T> {

	public void create(
			Collection<T> items, Map<String, Serializable> parameters)
		throws Exception;

	public void delete(
			Collection<T> items, Map<String, Serializable> parameters)
		throws Exception;

	public default Set<String> getAvailableCreateStrategies() {
		return null;
	}

	public default Set<String> getAvailableUpdateStrategies() {
		return null;
	}

	public EntityModel getEntityModel(Map<String, List<String>> multivaluedMap)
		throws Exception;

	public default Class<?> getResourceClass() {
		return getClass();
	}

	public default String getVersion() {
		return "v1.0";
	}

	public Page<T> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception;

	public void setContextBatchUnsafeBiConsumer(
		UnsafeBiConsumer
			<Collection<T>, UnsafeFunction<T, T, Exception>, Exception>
				contextBatchUnsafeBiConsumer);

	public void setContextCompany(Company contextCompany);

	public void setContextUriInfo(UriInfo uriInfo);

	public void setContextUser(User contextUser);

	public void setGroupLocalService(GroupLocalService groupLocalService);

	public void setLanguageId(String languageId);

	public void update(
			Collection<T> items, Map<String, Serializable> parameters)
		throws Exception;

}