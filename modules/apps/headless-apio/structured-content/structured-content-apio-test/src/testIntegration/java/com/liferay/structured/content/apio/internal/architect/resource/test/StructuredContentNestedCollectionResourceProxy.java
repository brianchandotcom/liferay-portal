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

package com.liferay.structured.content.apio.internal.architect.resource.test;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleWrapper;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.structured.content.apio.architect.filter.Filter;
import com.liferay.structured.content.apio.architect.sort.Sort;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julio Camarero
 */
@Component(
	immediate = true,
	service = StructuredContentNestedCollectionResourceProxy.class
)
public class StructuredContentNestedCollectionResourceProxy {

	protected BooleanClause<Query> getBooleanClause(
			Filter filter, Locale locale)
		throws Exception {

		Class<? extends NestedCollectionResource> clazz =
			_nestedCollectionResource.getClass();

		Method method = clazz.getDeclaredMethod(
			"getBooleanClause", Filter.class, Locale.class);

		method.setAccessible(true);

		return (BooleanClause<Query>)method.invoke(
			_nestedCollectionResource, filter, locale);
	}

	protected JournalArticleWrapper getJournalArticleWrapper(
			long journalArticleId, ThemeDisplay themeDisplay)
		throws Throwable {

		Class<? extends NestedCollectionResource> clazz =
			_nestedCollectionResource.getClass();

		Method method = clazz.getDeclaredMethod(
			"_getJournalArticleWrapper", long.class, ThemeDisplay.class);

		method.setAccessible(true);

		try {
			return (JournalArticleWrapper)method.invoke(
				_nestedCollectionResource, journalArticleId, themeDisplay);
		}
		catch (InvocationTargetException ite) {
			ite.printStackTrace();

			throw ite.getCause();
		}
	}

	protected PageItems<JournalArticle> getPageItems(
			Pagination pagination, long contentSpaceId,
			ThemeDisplay themeDisplay, Filter filter, Sort sort)
		throws Exception {

		Class<? extends NestedCollectionResource> clazz =
			_nestedCollectionResource.getClass();

		Method method = clazz.getDeclaredMethod(
			"_getPageItems", Pagination.class, long.class, ThemeDisplay.class,
			Filter.class, Sort.class);

		method.setAccessible(true);

		return (PageItems)method.invoke(
			_nestedCollectionResource, pagination, contentSpaceId, themeDisplay,
			filter, sort);
	}

	@Reference(
		target = "(component.name=com.liferay.structured.content.apio.internal.architect.resource.StructuredContentNestedCollectionResource)"
	)
	private NestedCollectionResource _nestedCollectionResource;

}