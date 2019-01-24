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

package com.liferay.blog.apio.internal.architect.resource.test;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.blog.apio.architect.model.BlogPosting;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author Víctor Galán
 */
public class BaseBlogPostingNestedCollectionResourceTest {

	protected BlogsEntry addBlogsEntry(long groupId, BlogPosting blogPosting)
		throws Exception {

		NestedCollectionResource nestedCollectionResource =
			_getNestedCollectionResource();

		Class<?> clazz = nestedCollectionResource.getClass();

		Method method = clazz.getDeclaredMethod(
			"_addBlogsEntry", long.class, BlogPosting.class);

		method.setAccessible(true);

		try {
			return (BlogsEntry)method.invoke(
				nestedCollectionResource, groupId, blogPosting);
		}
		catch (InvocationTargetException ite) {
			throw (Exception)ite.getTargetException();
		}
	}

	protected PageItems<BlogsEntry> getPageItems(
			Pagination pagination, long groupId)
		throws Exception {

		NestedCollectionResource nestedCollectionResource =
			_getNestedCollectionResource();

		Class<? extends NestedCollectionResource> clazz =
			nestedCollectionResource.getClass();

		Method method = clazz.getDeclaredMethod(
			"_getPageItems", Pagination.class, long.class);

		method.setAccessible(true);

		return (PageItems)method.invoke(
			nestedCollectionResource, pagination, groupId);
	}

	protected BlogsEntry updateBlogsEntry(
			long blogEntryId, BlogPosting blogPosting)
		throws Exception {

		NestedCollectionResource nestedCollectionResource =
			_getNestedCollectionResource();

		Class<?> clazz = nestedCollectionResource.getClass();

		Method method = clazz.getDeclaredMethod(
			"_updateBlogsEntry", long.class, BlogPosting.class);

		method.setAccessible(true);

		return (BlogsEntry)method.invoke(
			nestedCollectionResource, blogEntryId, blogPosting);
	}

	private NestedCollectionResource _getNestedCollectionResource()
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		Collection<NestedCollectionResource> collection = registry.getServices(
			NestedCollectionResource.class,
			"(component.name=com.liferay.blog.apio.internal.architect." +
				"resource." + "BlogPostingNestedCollectionResource)");

		Iterator<NestedCollectionResource> iterator = collection.iterator();

		return iterator.next();
	}

}