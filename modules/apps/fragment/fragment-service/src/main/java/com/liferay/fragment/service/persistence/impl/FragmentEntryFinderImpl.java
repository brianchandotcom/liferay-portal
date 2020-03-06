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

package com.liferay.fragment.service.persistence.impl;

import com.liferay.fragment.service.persistence.FragmentEntryFinder;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;

import java.util.List;

/**
 * @author Pavel Savinov
 */
public class FragmentEntryFinderImpl
	extends FragmentEntryLinkFinderBaseImpl implements FragmentEntryFinder {

	public static final String COUNT_FC_BY_G_FCI =
		FragmentEntryFinder.class.getName() + ".countFC_ByG_FCI";

	public static final String COUNT_FC_BY_G_FCI_N =
		FragmentEntryFinder.class.getName() + ".countFC_ByG_FCI_N";

	public static final String COUNT_FE_BY_G_FCI =
		FragmentEntryFinder.class.getName() + ".countFE_ByG_FCI";

	public static final String COUNT_FE_BY_G_FCI_N =
		FragmentEntryFinder.class.getName() + ".countFE_ByG_FCI_N";

	public static final String FIND_FC_BY_G_FCI =
		FragmentEntryFinder.class.getName() + ".findFC_ByG_FCI";

	public static final String FIND_FC_BY_G_FCI_N =
		FragmentEntryFinder.class.getName() + ".findFC_ByG_FCI_N";

	public static final String FIND_FE_BY_G_FCI =
		FragmentEntryFinder.class.getName() + ".findFE_ByG_FCI";

	public static final String FIND_FE_BY_G_FCI_N =
		FragmentEntryFinder.class.getName() + ".findFE_ByG_FCI_N";

	@Override
	public int countFC_FE_ByG_FCI(
		long groupId, long fragmentCollectionId,
		QueryDefinition<?> queryDefinition) {
	}

	@Override
	public int countFC_FE_ByG_FCI_N(
		long groupId, long fragmentCollectionId, String name,
		QueryDefinition<?> queryDefinition) {
	}

	@Override
	public List<Object> findFC_FE_ByG_FCI(
		long groupId, long fragmentCollectionId,
		QueryDefinition<?> queryDefinition) {
	}

	@Override
	public List<Object> findFC_FE_ByG_FCI_N(
		long groupId, long fragmentCollectionId, String name,
		QueryDefinition<?> queryDefinition) {
	}

}