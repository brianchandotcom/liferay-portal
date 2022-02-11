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

package com.liferay.content.dashboard.web.internal.item;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.content.dashboard.web.internal.configuration.FFBlogsEntryContentDashboardItemConfiguration;
import com.liferay.content.dashboard.web.internal.item.action.ContentDashboardItemActionProviderTracker;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemSubtypeFactory;
import com.liferay.content.dashboard.web.internal.item.type.ContentDashboardItemSubtypeFactoryTracker;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;

import java.util.Map;
import java.util.Optional;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	configurationPid = "com.liferay.content.dashboard.web.internal.configuration.FFBlogsEntryContentDashboardItemConfiguration",
	service = {}
)
public class BlogsEntryContentDashboardItemFactory
	implements ContentDashboardItemFactory<BlogsEntry> {

	@Override
	public ContentDashboardItem<BlogsEntry> create(long classPK)
		throws PortalException {

		BlogsEntry blogsEntry = _blogsEntryLocalService.getBlogsEntry(classPK);

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			BlogsEntry.class.getName(), blogsEntry.getEntryId());

		if (assetEntry == null) {
			throw new NoSuchModelException(
				"Unable to find an asset entry for blogs entry " +
					blogsEntry.getEntryId());
		}

		InfoItemFieldValuesProvider<BlogsEntry> infoItemFieldValuesProvider =
			infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class, BlogsEntry.class.getName());

		return new BlogsEntryContentDashboardItem(
			assetEntry.getCategories(), assetEntry.getTags(), blogsEntry,
			_contentDashboardItemActionProviderTracker,
			_groupLocalService.fetchGroup(blogsEntry.getGroupId()),
			infoItemFieldValuesProvider, _language, _portal);
	}

	@Override
	public Optional<ContentDashboardItemSubtypeFactory>
		getContentDashboardItemSubtypeFactoryOptional() {

		return _contentDashboardItemSubtypeFactoryTracker.
			getContentDashboardItemSubtypeFactoryOptional(
				BlogsEntry.class.getName());
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		FFBlogsEntryContentDashboardItemConfiguration
			ffBlogsEntryContentDashboardItemConfiguration =
				ConfigurableUtil.createConfigurable(
					FFBlogsEntryContentDashboardItemConfiguration.class,
					properties);

		if (ffBlogsEntryContentDashboardItemConfiguration.blogsEntryEnabled()) {
			_serviceRegistration = bundleContext.registerService(
				ContentDashboardItemFactory.class, this, null);
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Reference
	protected InfoItemServiceTracker infoItemServiceTracker;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference
	private ContentDashboardItemActionProviderTracker
		_contentDashboardItemActionProviderTracker;

	@Reference
	private ContentDashboardItemSubtypeFactoryTracker
		_contentDashboardItemSubtypeFactoryTracker;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	private ServiceRegistration<ContentDashboardItemFactory>
		_serviceRegistration;

	@Reference
	private UserLocalService _userLocalService;

}