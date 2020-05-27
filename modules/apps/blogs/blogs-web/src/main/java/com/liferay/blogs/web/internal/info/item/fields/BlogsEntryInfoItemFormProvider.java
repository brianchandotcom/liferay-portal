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

package com.liferay.blogs.web.internal.info.item.fields;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.info.item.provider.AssetEntryInfoItemFieldSetProvider;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldSetProvider;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.InfoForm;
import com.liferay.info.field.InfoFormValues;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.item.InfoItemClassPKReference;
import com.liferay.info.item.NoSuchInfoItemException;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;

import java.text.Format;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
	service = InfoItemFormProvider.class
)
public class BlogsEntryInfoItemFormProvider
	implements InfoItemFormProvider<BlogsEntry> {

	@Override
	public InfoForm getInfoForm() {
		InfoForm infoForm = new InfoForm(BlogsEntry.class.getName());

		infoForm.addAll(_getBlogsEntryInfoFieldSetEntries());

		infoForm.add(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldSet(
				BlogsEntry.class.getName()));

		infoForm.add(
			_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
				AssetEntry.class.getName()));

		infoForm.add(
			_expandoInfoItemFieldSetProvider.getInfoFieldSet(
				BlogsEntry.class.getName()));

		return infoForm;
	}

	@Override
	public InfoFormValues getInfoFormValues(BlogsEntry blogsEntry) {
		InfoFormValues infoFormValues = new InfoFormValues();

		infoFormValues.addAll(_getBlogsEntryInfoFieldValues(blogsEntry));

		infoFormValues.setInfoItemClassPKReference(
			new InfoItemClassPKReference(
				BlogsEntry.class.getName(), blogsEntry.getEntryId()));

		try {
			infoFormValues.addAll(
				_assetEntryInfoItemFieldSetProvider.getInfoFieldValues(
					BlogsEntry.class.getName(), blogsEntry.getEntryId()));
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			throw new RuntimeException(
				"Caught unexpected exception", noSuchInfoItemException);
		}

		infoFormValues.addAll(
			_expandoInfoItemFieldSetProvider.getInfoFieldValues(
				BlogsEntry.class.getName(), blogsEntry));
		infoFormValues.addAll(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldValues(
				BlogsEntry.class.getName(), blogsEntry));

		return infoFormValues;
	}

	private Collection<InfoFieldSetEntry> _getBlogsEntryInfoFieldSetEntries() {
		return Arrays.asList(
			_titleInfoField, _subtitleInfoField, _descriptionInfoField,
			_smallImageInfoField, _coverImageInfoField,
			_coverImageCaptionInfoField, _authorNameInfoField,
			_authorProfileImageInfoField, _publishDateInfoField,
			_displayPageUrlInfoField, _contentInfoField);
	}

	private List<InfoFieldValue<Object>> _getBlogsEntryInfoFieldValues(
		BlogsEntry blogsEntry) {

		List<InfoFieldValue<Object>> blogsEntryFieldValues = new ArrayList<>();

		ThemeDisplay themeDisplay = _getThemeDisplay();

		try {
			blogsEntryFieldValues.add(
				new InfoFieldValue<>(_titleInfoField, blogsEntry.getTitle()));

			blogsEntryFieldValues.add(
				new InfoFieldValue<>(
					_subtitleInfoField, blogsEntry.getSubtitle()));

			blogsEntryFieldValues.add(
				new InfoFieldValue<>(
					_descriptionInfoField, blogsEntry.getDescription()));

			if (themeDisplay != null) {
				blogsEntryFieldValues.add(
					new InfoFieldValue<>(
						_smallImageInfoField,
						_getImageJSONObject(
							blogsEntry.getSmallImageAlt(),
							blogsEntry.getSmallImageURL(themeDisplay))));
			}

			if (themeDisplay != null) {
				blogsEntryFieldValues.add(
					new InfoFieldValue<>(
						_coverImageInfoField,
						_getImageJSONObject(
							blogsEntry.getCoverImageAlt(),
							blogsEntry.getCoverImageURL(themeDisplay))));
			}

			blogsEntryFieldValues.add(
				new InfoFieldValue<>(
					_coverImageCaptionInfoField,
					blogsEntry.getCoverImageCaption()));

			User user = _userLocalService.fetchUser(blogsEntry.getUserId());

			if (user != null) {
				blogsEntryFieldValues.add(
					new InfoFieldValue<>(
						_authorNameInfoField, user.getFullName()));

				blogsEntryFieldValues.add(
					new InfoFieldValue<>(
						_authorProfileImageInfoField,
						_getImageJSONObject(
							user.getFullName(),
							user.getPortraitURL(themeDisplay))));
			}

			blogsEntryFieldValues.add(
				new InfoFieldValue<>(
					_publishDateInfoField,
					_getDateValue(blogsEntry.getDisplayDate())));

			blogsEntryFieldValues.add(
				new InfoFieldValue<>(
					_displayPageUrlInfoField, _getDisplayPageURL(blogsEntry)));

			blogsEntryFieldValues.add(
				new InfoFieldValue<>(
					_contentInfoField, blogsEntry.getContent()));

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

	private JSONObject _getImageJSONObject(String alt, String url) {
		return JSONUtil.put(
			"alt", alt
		).put(
			"url", url
		);
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

	private final InfoField _authorNameInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(getClass(), "author-name"), "authorName");
	private final InfoField _authorProfileImageInfoField = new InfoField(
		ImageInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			"com.liferay.journal.lang", "author-profile-image"),
		"authorProfileImage");
	private final InfoField _contentInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(getClass(), "content"), "content");
	private final InfoField _coverImageCaptionInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(getClass(), "cover-image-caption"),
		"coverImageCaption");
	private final InfoField _coverImageInfoField = new InfoField(
		ImageInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize("com.liferay.journal.lang", "cover-image"),
		"coverImage");
	private final InfoField _descriptionInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(getClass(), "description"), "description");
	private final InfoField _displayPageUrlInfoField = new InfoField(
		URLInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(
			"com.liferay.asset.info.display.impl", "display-page-url"),
		"displayPageURL");

	@Reference
	private ExpandoInfoItemFieldSetProvider _expandoInfoItemFieldSetProvider;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	private final InfoField _publishDateInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(getClass(), "publish-date"), "publishDate");
	private final InfoField _smallImageInfoField = new InfoField(
		ImageInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize("com.liferay.journal.lang", "small-image"),
		"smallImage");
	private final InfoField _subtitleInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(getClass(), "subtitle"), "subtitle");
	private final InfoField _titleInfoField = new InfoField(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(getClass(), "title"), "title");

	@Reference
	private UserLocalService _userLocalService;

}