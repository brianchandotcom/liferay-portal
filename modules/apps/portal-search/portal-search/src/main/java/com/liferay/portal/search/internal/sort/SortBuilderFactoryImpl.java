/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.sort;

import com.liferay.portal.search.sort.SortBuilder;
import com.liferay.portal.search.sort.SortBuilderFactory;
import com.liferay.portal.search.sort.Sorts;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 */
@Component(service = SortBuilderFactory.class)
public class SortBuilderFactoryImpl implements SortBuilderFactory {

	@Override
	public SortBuilder getSortBuilder() {
		return new SortBuilderImpl(_sorts);
	}

	@Reference
	private Sorts _sorts;

}