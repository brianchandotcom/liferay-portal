/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.item.selector.criterion;

import com.liferay.item.selector.ItemSelectorCriterion;

import java.util.Set;

/**
 * @author Adolfo Pérez
 */
public interface DLItemSelectorCriterionCreationMenuRestriction
	<T extends ItemSelectorCriterion> {

	public Set<String> getAllowedCreationMenuUIItemKeys();

}