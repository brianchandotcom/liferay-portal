/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.view.table;

import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(service = FDSTableSchemaBuilderFactory.class)
public class FDSTableSchemaBuilderFactoryImpl
	implements FDSTableSchemaBuilderFactory {

	@Override
	public FDSTableSchemaBuilder create() {
		return new FDSTableSchemaBuilderImpl();
	}

}