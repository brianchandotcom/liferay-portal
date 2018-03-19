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

package com.liferay.blogs.web.internal.asset.display.contributor;

import com.liferay.asset.display.contributor.AssetDisplayContributor;
import com.liferay.asset.display.contributor.BaseAssetDisplayContributor;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;

import java.util.Locale;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = AssetDisplayContributor.class)
public class BlogsEntryAssetDisplayContributor
	extends BaseAssetDisplayContributor<BlogsEntry>
	implements AssetDisplayContributor {

	@Override
	public String getClassName() {
		return BlogsEntry.class.getName();
	}

	@Override
	protected String[] getAssetEntryModelFields() {
		return new String[] {"subtitle", "content"};
	}

	@Override
	protected Object getFieldValue(
		BlogsEntry entry, String field, Locale locale) {

		if (Objects.equals(field, "content")) {
			return entry.getContent();
		}

		if (Objects.equals(field, "subtitle")) {
			return entry.getSubtitle();
		}

		return StringPool.BLANK;
	}

	@Override
	@Reference(
		target = "(bundle.symbolic.name=com.liferay.blogs.web)", unbind = "-"
	)
	protected void setResourceBundleLoader(
		ResourceBundleLoader resourceBundleLoader) {

		this.resourceBundleLoader = new AggregateResourceBundleLoader(
			resourceBundleLoader,
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader());
	}

}