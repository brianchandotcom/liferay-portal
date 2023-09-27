/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.summary;

import com.liferay.portal.search.summary.SummaryBuilder;
import com.liferay.portal.search.summary.SummaryBuilderFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bryan Engler
 */
@Component(service = SummaryBuilderFactory.class)
public class SummaryBuilderFactoryImpl implements SummaryBuilderFactory {

	@Override
	public SummaryBuilder newInstance() {
		return new SummaryBuilderImpl();
	}

}