/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.search;

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry;
import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionDiagramSearcher extends BaseSearcher {

	public static final String[] CLASS_NAMES = {
		CPDefinitionDiagramEntry.class.getName()
	};

	public static Indexer<?> getInstance() {
		return new CPDefinitionDiagramSearcher();
	}

	public CPDefinitionDiagramSearcher() {
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
			Field.UID);
		setFilterSearch(true);
		setPermissionAware(true);
		setSelectAllLocales(true);
	}

	@Override
	public String[] getSearchClassNames() {
		return CLASS_NAMES;
	}

}