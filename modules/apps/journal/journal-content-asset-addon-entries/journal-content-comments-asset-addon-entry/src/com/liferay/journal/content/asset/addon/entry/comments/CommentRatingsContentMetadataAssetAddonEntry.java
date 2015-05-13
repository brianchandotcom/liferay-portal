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

package com.liferay.journal.content.asset.addon.entry.comments;

import com.liferay.journal.content.web.util.ContentMetadataAssetAddonEntry;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BaseAssetAddonEntry;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Julio Camarero
 */
@Component(
	immediate = true, service = {ContentMetadataAssetAddonEntry.class, CommentRatingsContentMetadataAssetAddonEntry.class}
)
public class CommentRatingsContentMetadataAssetAddonEntry
	extends BaseAssetAddonEntry implements ContentMetadataAssetAddonEntry {

	@Override
	public String getIcon() {
		return "comment-alt";
	}

	@Override
	public String getKey() {
		return "enableCommentRatings";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "comment-ratings");
	}

	@Override
	public Double getWeight() {
		return 4.0;
	}

}