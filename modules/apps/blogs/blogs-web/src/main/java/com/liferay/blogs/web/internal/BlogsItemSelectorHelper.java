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

package com.liferay.blogs.web.internal;

import com.liferay.blogs.configuration.BlogsFileUploadsConfiguration;
import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.item.selector.criterion.BlogsItemSelectorCriterion;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.DownloadFileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.item.selector.criteria.upload.criterion.UploadItemSelectorCriterion;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Roberto Díaz
 */
@Component(
	configurationPid = "com.liferay.blogs.configuration.BlogsFileUploadsConfiguration",
	service = BlogsItemSelectorHelper.class
)
public class BlogsItemSelectorHelper {

	public String getItemSelectorURL(
		RequestBackedPortletURLFactory requestBackedPortletURLFactory,
		ThemeDisplay themeDisplay, String itemSelectedEventName) {

		if (_itemSelector == null) {
			return null;
		}

		ItemSelectorCriterion blogsItemSelectorCriterion =
			new BlogsItemSelectorCriterion();

		blogsItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType());

		ImageItemSelectorCriterion imageItemSelectorCriterion =
			new ImageItemSelectorCriterion();

		imageItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new DownloadFileEntryItemSelectorReturnType());

		PortletURL uploadURL = requestBackedPortletURLFactory.createActionURL(
			BlogsPortletKeys.BLOGS);

		uploadURL.setParameter(
			ActionRequest.ACTION_NAME, "/blogs/upload_cover_image");

		ItemSelectorCriterion uploadItemSelectorCriterion =
			new UploadItemSelectorCriterion(
				BlogsPortletKeys.BLOGS, uploadURL.toString(),
				LanguageUtil.get(themeDisplay.getLocale(), "blog-images"),
				_blogsFileUploadsConfiguration.imageMaxSize(),
				_blogsFileUploadsConfiguration.imageExtensions());

		uploadItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new DownloadFileEntryItemSelectorReturnType());

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, itemSelectedEventName,
			blogsItemSelectorCriterion, imageItemSelectorCriterion,
			uploadItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_blogsFileUploadsConfiguration = ConfigurableUtil.createConfigurable(
			BlogsFileUploadsConfiguration.class, properties);
	}

	private BlogsFileUploadsConfiguration _blogsFileUploadsConfiguration;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile ItemSelector _itemSelector;

}