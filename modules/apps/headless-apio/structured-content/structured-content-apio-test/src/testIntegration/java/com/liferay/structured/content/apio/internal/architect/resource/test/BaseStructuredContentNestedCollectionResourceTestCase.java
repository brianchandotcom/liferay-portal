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

import com.liferay.apio.architect.language.AcceptLanguage;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleWrapper;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.filter.Filter;
import com.liferay.portal.odata.sort.Sort;
import com.liferay.portal.test.rule.Inject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Locale;

/**
 * @author Julio Camarero
 */
public abstract class BaseStructuredContentNestedCollectionResourceTestCase {

	protected JournalArticleWrapper getJournalArticleWrapper(
			long journalArticleId, ThemeDisplay themeDisplay)
		throws Throwable {

		Class<? extends NestedCollectionResource> clazz =
			_nestedCollectionResource.getClass();

		Method method = clazz.getDeclaredMethod(
			"_getJournalArticleWrapper", long.class, AcceptLanguage.class,
			ThemeDisplay.class);

		method.setAccessible(true);

		try {
			return (JournalArticleWrapper)method.invoke(
				_nestedCollectionResource, journalArticleId, _acceptLanguage,
				themeDisplay);
		}
		catch (InvocationTargetException ite) {
			throw ite.getCause();
		}
	}

	protected PageItems<JournalArticle> getPageItems(
			Pagination pagination, long contentSpaceId,
			AcceptLanguage acceptLanguage, ThemeDisplay themeDisplay,
			Filter filter, Sort sort)
		throws Exception {

		Class<? extends NestedCollectionResource> clazz =
			_nestedCollectionResource.getClass();

		Method method = clazz.getDeclaredMethod(
			"_getPageItems", Pagination.class, long.class, AcceptLanguage.class,
			ThemeDisplay.class, Filter.class, Sort.class);

		method.setAccessible(true);

		return (PageItems)method.invoke(
			_nestedCollectionResource, pagination, contentSpaceId,
			acceptLanguage, themeDisplay, filter, sort);
	}

	protected com.liferay.portal.kernel.search.filter.Filter getSearchFilter(
			Filter filter, Locale locale)
		throws Exception {

		Class<? extends NestedCollectionResource> clazz =
			_nestedCollectionResource.getClass();

		Method method = clazz.getDeclaredMethod(
			"_getSearchFilter", Filter.class, Locale.class);

		method.setAccessible(true);

		return (com.liferay.portal.kernel.search.filter.Filter)method.invoke(
			_nestedCollectionResource, filter, locale);
	}

	protected ThemeDisplay getThemeDisplay(Group group, Locale locale)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = CompanyLocalServiceUtil.getCompanyById(
			group.getCompanyId());

		themeDisplay.setCompany(company);

		themeDisplay.setLocale(locale);
		themeDisplay.setScopeGroupId(group.getGroupId());

		return themeDisplay;
	}

	private static final AcceptLanguage _acceptLanguage =
		() -> LocaleUtil.getDefault();

	@Inject(
		filter = "component.name=com.liferay.structured.content.apio.internal.architect.resource.StructuredContentNestedCollectionResource"
	)
	private NestedCollectionResource _nestedCollectionResource;

}