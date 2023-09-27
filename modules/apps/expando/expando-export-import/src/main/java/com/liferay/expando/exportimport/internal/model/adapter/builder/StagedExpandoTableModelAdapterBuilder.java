/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.exportimport.internal.model.adapter.builder;

import com.liferay.expando.exportimport.internal.model.adapter.StagedExpandoTableImpl;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.model.adapter.StagedExpandoTable;
import com.liferay.portal.kernel.model.adapter.builder.ModelAdapterBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Akos Thurzo
 */
@Component(service = ModelAdapterBuilder.class)
public class StagedExpandoTableModelAdapterBuilder
	implements ModelAdapterBuilder<ExpandoTable, StagedExpandoTable> {

	@Override
	public StagedExpandoTable build(ExpandoTable expandoTable) {
		return new StagedExpandoTableImpl(expandoTable);
	}

}