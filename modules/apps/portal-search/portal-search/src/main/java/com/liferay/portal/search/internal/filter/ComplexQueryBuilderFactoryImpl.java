/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.filter;

import com.liferay.portal.search.filter.ComplexQueryBuilder;
import com.liferay.portal.search.filter.ComplexQueryBuilderFactory;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.script.Scripts;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = ComplexQueryBuilderFactory.class)
public class ComplexQueryBuilderFactoryImpl
	implements ComplexQueryBuilderFactory {

	@Override
	public ComplexQueryBuilder builder() {
		return new ComplexQueryBuilderImpl(_queries, _scripts);
	}

	@Reference
	private Queries _queries;

	@Reference
	private Scripts _scripts;

}