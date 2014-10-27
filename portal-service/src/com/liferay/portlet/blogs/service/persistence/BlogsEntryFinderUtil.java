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

package com.liferay.portlet.blogs.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @generated
 */
@ProviderType
public class BlogsEntryFinderUtil {
	public static int countByOrganizationId(long organizationId,
		java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.blogs.model.BlogsEntry> queryDefinition) {
		return getFinder()
				   .countByOrganizationId(organizationId, displayDate,
			queryDefinition);
	}

	public static int countByOrganizationIds(
		java.util.List<java.lang.Long> organizationIds,
		java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.blogs.model.BlogsEntry> queryDefinition) {
		return getFinder()
				   .countByOrganizationIds(organizationIds, displayDate,
			queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByGroupIds(
		long companyId, long groupId, java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.blogs.model.BlogsEntry> queryDefinition) {
		return getFinder()
				   .findByGroupIds(companyId, groupId, displayDate,
			queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByOrganizationId(
		long organizationId, java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.blogs.model.BlogsEntry> queryDefinition) {
		return getFinder()
				   .findByOrganizationId(organizationId, displayDate,
			queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByOrganizationIds(
		java.util.List<java.lang.Long> organizationIds,
		java.util.Date displayDate,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<com.liferay.portlet.blogs.model.BlogsEntry> queryDefinition) {
		return getFinder()
				   .findByOrganizationIds(organizationIds, displayDate,
			queryDefinition);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByNoAssets() {
		return getFinder().findByNoAssets();
	}

	public static BlogsEntryFinder getFinder() {
		if (_finder == null) {
			_finder = (BlogsEntryFinder)PortalBeanLocatorUtil.locate(BlogsEntryFinder.class.getName());

			ReferenceRegistry.registerReference(BlogsEntryFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(BlogsEntryFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(BlogsEntryFinderUtil.class,
			"_finder");
	}

	private static BlogsEntryFinder _finder;
}