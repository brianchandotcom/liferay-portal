/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.document.library.google.docs.internal.item.selector.criterion;

import com.liferay.document.library.display.context.DLUIItemKeys;
import com.liferay.document.library.google.docs.internal.util.constants.GoogleDocsConstants;
import com.liferay.document.library.item.selector.criterion.DLItemSelectorCriterionCreationMenuRestriction;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion",
		"model.class.name=com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion"
	},
	service = DLItemSelectorCriterionCreationMenuRestriction.class
)
public class DLFileItemSelectorCriterionCreationMenuRestriction
	implements DLItemSelectorCriterionCreationMenuRestriction
		<FileItemSelectorCriterion> {

	@Override
	public Set<String> getAllowedCreationMenuUIItemKeys() {
		return _allowedCreationMenuUIItemKeys;
	}

	private static final Set<String> _allowedCreationMenuUIItemKeys =
		SetUtil.fromArray(
			DLFileEntryType.class.getSimpleName() +
				GoogleDocsConstants.DL_FILE_ENTRY_TYPE_KEY,
			DLUIItemKeys.ADD_FOLDER, DLUIItemKeys.UPLOAD);

}