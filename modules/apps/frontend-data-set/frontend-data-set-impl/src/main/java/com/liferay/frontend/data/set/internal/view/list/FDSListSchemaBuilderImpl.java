/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.view.list;

import com.liferay.frontend.data.set.view.FDSSchemaLabelField;
import com.liferay.frontend.data.set.view.list.FDSListSchema;
import com.liferay.frontend.data.set.view.list.FDSListSchemaBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mylena Monte
 */
public class FDSListSchemaBuilderImpl implements FDSListSchemaBuilder {

	@Override
	public FDSListSchemaBuilder add(FDSSchemaLabelField fdsSchemaLabelField) {
		_fdsSchemaLabelFieldsList.add(fdsSchemaLabelField);

		return this;
	}

	@Override
	public FDSListSchema build() {
		_fdsListSchema.setFDSSchemaLabelFieldsList(_fdsSchemaLabelFieldsList);

		return _fdsListSchema;
	}

	private final FDSListSchema _fdsListSchema = new FDSListSchema();
	private final List<FDSSchemaLabelField> _fdsSchemaLabelFieldsList =
		new ArrayList<>();

}