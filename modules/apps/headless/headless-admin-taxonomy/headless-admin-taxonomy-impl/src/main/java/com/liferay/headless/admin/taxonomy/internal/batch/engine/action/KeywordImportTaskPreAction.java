/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.internal.batch.engine.action;

import com.liferay.batch.engine.action.ImportTaskPreAction;
import com.liferay.headless.admin.taxonomy.dto.v1_0.Creator;
import com.liferay.headless.admin.taxonomy.dto.v1_0.Keyword;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = ImportTaskPreAction.class)
public class KeywordImportTaskPreAction extends BaseImportTaskPreAction {

	@Override
	protected Creator getCreator(Object item) {
		if (!(item instanceof Keyword)) {
			return null;
		}

		Keyword keyword = (Keyword)item;

		return keyword.getCreator();
	}

	@Override
	protected Class<Keyword> getItemClass() {
		return Keyword.class;
	}

}