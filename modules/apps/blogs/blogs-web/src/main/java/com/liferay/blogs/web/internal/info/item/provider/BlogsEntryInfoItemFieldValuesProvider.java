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

package com.liferay.blogs.web.internal.info.item.provider;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.info.item.provider.AssetEntryInfoItemFieldSetProvider;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.web.internal.info.item.BlogsEntryInfoItemFields;
import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldSetProvider;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemClassPKReference;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.type.WebImage;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;

import java.text.Format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 * @author Jorge Ferrer
 */
@Component(
	immediate = true, property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemFieldValuesProvider.class
)
public class BlogsEntryInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<BlogsEntry> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(BlogsEntry blogsEntry) {
		InfoItemFieldValues infoItemFieldValues = new InfoItemFieldValues(
			new InfoItemClassPKReference(
				BlogsEntry.class.getName(), blogsEntry.getEntryId()));

		infoItemFieldValues.addAll(_getBlogsEntryInfoFieldValues(blogsEntry));

		try {
			infoItemFieldValues.addAll(
				_assetEntryInfoItemFieldSetProvider.getInfoFieldValues(
					BlogsEntry.class.getName(), blogsEntry.getEntryId()));
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			throw new RuntimeException(
				"Caught unexpected exception", noSuchInfoItemException);
		}

		infoItemFieldValues.addAll(
			_expandoInfoItemFieldSetProvider.getInfoFieldValues(
				BlogsEntry.class.getName(), blogsEntry));
		infoItemFieldValues.addAll(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldValues(
				BlogsEntry.class.getName(), blogsEntry));

		return infoItemFieldValues;
	}

	private List<InfoFieldValue<Object>> _getBlogsEntryInfoFieldValues(
		BlogsEntry blogsEntry) {

		List<InfoFieldValue<Object>> blogsEntryFieldValues = new ArrayList<>();

		ThemeDisplay themeDisplay = _getThemeDisplay();

		try {
			blogsEntryFieldValues.add(
				new InfoFieldValue<>(
					BlogsEntryInfoItemFields.titleInfoField,
					blogsEntry.getTitle()));

			blogsEntryFieldValues.add(
				new InfoFieldValue<>(
					BlogsEntryInfoItemFields.subtitleInfoField,
					blogsEntry.getSubtitle()));

			blogsEntryFieldValues.add(
				new InfoFieldValue<>(
					BlogsEntryInfoItemFields.descriptionInfoField,
					blogsEntry.getDescription()));

			if (themeDisplay != null) {
				WebImage smallWebImage = new WebImage(
					blogsEntry.getSmallImageURL(themeDisplay));

				smallWebImage.setAlt(blogsEntry.getSmallImageAlt());

				blogsEntryFieldValues.add(
					new InfoFieldValue<>(
						BlogsEntryInfoItemFields.smallImageInfoField,
						smallWebImage));

				WebImage coverWebImage = new WebImage(
					blogsEntry.getCoverImageURL(themeDisplay));

				coverWebImage.setAlt(blogsEntry.getCoverImageAlt());

				blogsEntryFieldValues.add(
					new InfoFieldValue<>(
						BlogsEntryInfoItemFields.coverImageInfoField,
						coverWebImage));
			}

			blogsEntryFieldValues.add(
				new InfoFieldValue<>(
					BlogsEntryInfoItemFields.coverImageCaptionInfoField,
					blogsEntry.getCoverImageCaption()));

			User user = _userLocalService.fetchUser(blogsEntry.getUserId());

			if (user != null) {
				blogsEntryFieldValues.add(
					new InfoFieldValue<>(
						BlogsEntryInfoItemFields.authorNameInfoField,
						user.getFullName()));

				if (themeDisplay != null) {
					WebImage webImage = new WebImage(
						user.getPortraitURL(themeDisplay));

					webImage.setAlt(user.getFullName());

					blogsEntryFieldValues.add(
						new InfoFieldValue<>(
							BlogsEntryInfoItemFields.
								authorProfileImageInfoField,
							webImage));
				}
			}

			blogsEntryFieldValues.add(
				new InfoFieldValue<>(
					BlogsEntryInfoItemFields.publishDateInfoField,
					_getDateValue(blogsEntry.getDisplayDate())));

			if (themeDisplay != null) {
				blogsEntryFieldValues.add(
					new InfoFieldValue<>(
						BlogsEntryInfoItemFields.displayPageUrlInfoField,
						_getDisplayPageURL(blogsEntry)));
			}

			blogsEntryFieldValues.add(
				new InfoFieldValue<>(
					BlogsEntryInfoItemFields.contentInfoField,
					blogsEntry.getContent()));

			return blogsEntryFieldValues;
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private String _getDateValue(Date date) {
		if (date == null) {
			return StringPool.BLANK;
		}

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(
			locale);

		return dateFormatDateTime.format(date);
	}

	private String _getDisplayPageURL(BlogsEntry blogsEntry)
		throws PortalException {

		return _assetDisplayPageFriendlyURLProvider.getFriendlyURL(
			BlogsEntry.class.getName(), blogsEntry.getEntryId(),
			_getThemeDisplay());
	}

	private ThemeDisplay _getThemeDisplay() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getThemeDisplay();
		}

		return null;
	}

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private AssetEntryInfoItemFieldSetProvider
		_assetEntryInfoItemFieldSetProvider;

	@Reference
	private ExpandoInfoItemFieldSetProvider _expandoInfoItemFieldSetProvider;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private UserLocalService _userLocalService;

}