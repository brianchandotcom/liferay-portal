/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service.persistence;

/**
 * @author Brian Wing Shun Chan
 */
public interface AssetVocabularyFinder {
	public int countByG_N(long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int filterCountByG_N(long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> filterFindByG_N(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException;

	public java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> findByG_N(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchVocabularyException;
}