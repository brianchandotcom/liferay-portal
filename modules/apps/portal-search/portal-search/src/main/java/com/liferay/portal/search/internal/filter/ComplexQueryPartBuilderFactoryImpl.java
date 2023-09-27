/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.filter;

import com.liferay.portal.search.filter.ComplexQueryPartBuilder;
import com.liferay.portal.search.filter.ComplexQueryPartBuilderFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(service = ComplexQueryPartBuilderFactory.class)
public class ComplexQueryPartBuilderFactoryImpl
	implements ComplexQueryPartBuilderFactory {

	@Override
	public ComplexQueryPartBuilder builder() {
		return new ComplexQueryPartImpl.Builder();
	}

}