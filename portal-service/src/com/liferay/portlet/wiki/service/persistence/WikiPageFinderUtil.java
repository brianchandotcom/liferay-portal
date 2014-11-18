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

package com.liferay.portlet.wiki.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @generated
 */
@ProviderType
public class WikiPageFinderUtil {
	public static int countByCreateDate(long groupId, long nodeId,
		java.util.Date createDate, boolean before) {
		return getFinder().countByCreateDate(groupId, nodeId, createDate, before);
	}

	public static int countByCreateDate(long groupId, long nodeId,
		java.sql.Timestamp createDate, boolean before) {
		return getFinder().countByCreateDate(groupId, nodeId, createDate, before);
	}

	public static int filterCountByCreateDate(long groupId, long nodeId,
		java.util.Date createDate, boolean before) {
		return getFinder()
				   .filterCountByCreateDate(groupId, nodeId, createDate, before);
	}

	public static int filterCountByCreateDate(long groupId, long nodeId,
		java.sql.Timestamp createDate, boolean before) {
		return getFinder()
				   .filterCountByCreateDate(groupId, nodeId, createDate, before);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> filterFindByCreateDate(
		long groupId, long nodeId, java.util.Date createDate, boolean before,
		int start, int end) {
		return getFinder()
				   .filterFindByCreateDate(groupId, nodeId, createDate, before,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> filterFindByCreateDate(
		long groupId, long nodeId, java.sql.Timestamp createDate,
		boolean before, int start, int end) {
		return getFinder()
				   .filterFindByCreateDate(groupId, nodeId, createDate, before,
			start, end);
	}

	public static com.liferay.portlet.wiki.model.WikiPage findByResourcePrimKey(
		long resourcePrimKey)
		throws com.liferay.portlet.wiki.NoSuchPageException {
		return getFinder().findByResourcePrimKey(resourcePrimKey);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByCreateDate(
		long groupId, long nodeId, java.util.Date createDate, boolean before,
		int start, int end) {
		return getFinder()
				   .findByCreateDate(groupId, nodeId, createDate, before,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByCreateDate(
		long groupId, long nodeId, java.sql.Timestamp createDate,
		boolean before, int start, int end) {
		return getFinder()
				   .findByCreateDate(groupId, nodeId, createDate, before,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByNoAssets() {
		return getFinder().findByNoAssets();
	}

	public static WikiPageFinder getFinder() {
		if (_finder == null) {
			_finder = (WikiPageFinder)PortalBeanLocatorUtil.locate(WikiPageFinder.class.getName());

			ReferenceRegistry.registerReference(WikiPageFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(WikiPageFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(WikiPageFinderUtil.class, "_finder");
	}

	private static WikiPageFinder _finder;
}