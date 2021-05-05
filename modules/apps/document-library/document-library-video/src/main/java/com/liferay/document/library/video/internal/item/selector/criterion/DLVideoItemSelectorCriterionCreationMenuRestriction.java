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

package com.liferay.document.library.video.internal.item.selector.criterion;

import com.liferay.document.library.item.selector.criterion.DLItemSelectorCriterionCreationMenuRestriction;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.video.internal.constants.DLVideoConstants;
import com.liferay.item.selector.criteria.video.criterion.VideoItemSelectorCriterion;

import java.util.Collections;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"model.class.name=com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion",
		"model.class.name=com.liferay.item.selector.criteria.video.criterion.VideoItemSelectorCriterion"
	},
	service = DLItemSelectorCriterionCreationMenuRestriction.class
)
public class DLVideoItemSelectorCriterionCreationMenuRestriction
	implements DLItemSelectorCriterionCreationMenuRestriction
		<VideoItemSelectorCriterion> {

	@Override
	public Set<String> getAllowedCreationMenuUIItemKeys() {
		return Collections.singleton(
			DLFileEntryType.class.getSimpleName() +
				DLVideoConstants.DL_FILE_ENTRY_TYPE_KEY);
	}

}