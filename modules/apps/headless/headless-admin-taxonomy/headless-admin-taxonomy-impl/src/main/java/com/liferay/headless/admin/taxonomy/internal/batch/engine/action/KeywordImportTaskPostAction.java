/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.internal.batch.engine.action;

import com.liferay.batch.engine.action.ImportTaskPostAction;
import com.liferay.headless.admin.taxonomy.dto.v1_0.Keyword;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = ImportTaskPostAction.class)
public class KeywordImportTaskPostAction extends BaseImportTaskPostAction {

	@Override
	protected Class getItemClass() {
		return Keyword.class;
	}

}