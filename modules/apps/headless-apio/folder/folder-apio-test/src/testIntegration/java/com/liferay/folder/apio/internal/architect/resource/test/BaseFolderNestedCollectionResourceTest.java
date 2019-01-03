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

package com.liferay.folder.apio.internal.architect.resource.test;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.lang.reflect.Method;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author Rubén Pulido
 */
public class BaseFolderNestedCollectionResourceTest {

	protected Folder addFolder(
			long groupId, com.liferay.folder.apio.architect.model.Folder folder)
		throws Exception {

		NestedCollectionResource nestedCollectionResource =
			_getNestedCollectionResource();

		Class<?> clazz = nestedCollectionResource.getClass();

		Method method = clazz.getDeclaredMethod(
			"_addFolder", long.class,
			com.liferay.folder.apio.architect.model.Folder.class);

		method.setAccessible(true);

		return (Folder)method.invoke(
			_getNestedCollectionResource(), groupId, folder);
	}

	protected PageItems<Folder> getPageItems(
			Pagination pagination, long groupId)
		throws Exception {

		NestedCollectionResource nestedCollectionResource =
			_getNestedCollectionResource();

		Class<?> clazz = nestedCollectionResource.getClass();

		Method method = clazz.getDeclaredMethod(
			"_getPageItems", Pagination.class, long.class);

		method.setAccessible(true);

		return (PageItems)method.invoke(
			_getNestedCollectionResource(), pagination, groupId);
	}

	protected Folder updateFolder(
			long folderId,
			com.liferay.folder.apio.architect.model.Folder folder)
		throws Exception {

		NestedCollectionResource nestedCollectionResource =
			_getNestedCollectionResource();

		Class<?> clazz = nestedCollectionResource.getClass();

		Method method = clazz.getDeclaredMethod(
			"_updateFolder", long.class,
			com.liferay.folder.apio.architect.model.Folder.class);

		method.setAccessible(true);

		return (Folder)method.invoke(
			_getNestedCollectionResource(), folderId, folder);
	}

	private NestedCollectionResource _getNestedCollectionResource()
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		Collection<NestedCollectionResource> collection = registry.getServices(
			NestedCollectionResource.class,
			"(component.name=com.liferay.folder.apio.internal.architect." +
				"resource.FolderNestedCollectionResource)");

		Iterator<NestedCollectionResource> iterator = collection.iterator();

		return iterator.next();
	}

}