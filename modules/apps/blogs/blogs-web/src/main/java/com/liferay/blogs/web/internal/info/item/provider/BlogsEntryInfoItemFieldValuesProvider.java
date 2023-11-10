/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.web.internal.info.item.provider;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.info.item.provider.AssetEntryInfoItemFieldSetProvider;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.web.internal.info.item.BlogsEntryInfoItemFields;
import com.liferay.expando.info.item.provider.ExpandoInfoItemFieldSetProvider;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.type.WebImage;
import com.liferay.layout.page.template.info.item.provider.DisplayPageInfoItemFieldSetProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 * @author Jorge Ferrer
 */
@Component(
	property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemFieldValuesProvider.class
)
public class BlogsEntryInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<BlogsEntry> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(BlogsEntry blogsEntry) {
		try {
			return InfoItemFieldValues.builder(
			).infoFieldValues(
				_getBlogsEntryInfoFieldValues(blogsEntry)
			).infoFieldValues(
				_assetEntryInfoItemFieldSetProvider.getInfoFieldValues(
					BlogsEntry.class.getName(), blogsEntry.getEntryId())
			).infoFieldValues(
				_displayPageInfoItemFieldSetProvider.getInfoFieldValues(
					new InfoItemReference(
						BlogsEntry.class.getName(), blogsEntry.getEntryId()),
					StringPool.BLANK, BlogsEntry.class.getSimpleName(),
					_getThemeDisplay())
			).infoFieldValues(
				_expandoInfoItemFieldSetProvider.getInfoFieldValues(
					BlogsEntry.class.getName(), blogsEntry)
			).infoFieldValues(
				_infoItemFieldReaderFieldSetProvider.getInfoFieldValues(
					BlogsEntry.class.getName(), blogsEntry)
			).infoFieldValues(
				_templateInfoItemFieldSetProvider.getInfoFieldValues(
					BlogsEntry.class.getName(), blogsEntry)
			).infoItemReference(
				new InfoItemReference(
					BlogsEntry.class.getName(), blogsEntry.getEntryId())
			).build();
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			throw new RuntimeException(
				"Caught unexpected exception", noSuchInfoItemException);
		}
		catch (Exception exception) {
			throw new RuntimeException("Unexpected exception", exception);
		}
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
			blogsEntryFieldValues.add(
				new InfoFieldValue<>(
					BlogsEntryInfoItemFields.createDateInfoField,
					blogsEntry.getCreateDate()));
			blogsEntryFieldValues.add(
				new InfoFieldValue<>(
					BlogsEntryInfoItemFields.modifiedDateInfoField,
					blogsEntry.getModifiedDate()));

			if (themeDisplay != null) {
				WebImage smallWebImage = new WebImage(
					blogsEntry.getSmallImageURL(themeDisplay),
					new InfoItemReference(
						FileEntry.class.getName(),
						new ClassPKInfoItemIdentifier(
							blogsEntry.getSmallImageFileEntryId())));

				smallWebImage.setAlt(blogsEntry.getSmallImageAlt());

				blogsEntryFieldValues.add(
					new InfoFieldValue<>(
						BlogsEntryInfoItemFields.smallImageInfoField,
						smallWebImage));

				String coverImageURL = blogsEntry.getCoverImageURL(
					themeDisplay);

				WebImage coverWebImage = new WebImage(
					coverImageURL,
					new InfoItemReference(
						FileEntry.class.getName(),
						new ClassPKInfoItemIdentifier(
							blogsEntry.getCoverImageFileEntryId())));

				coverWebImage.setAlt(blogsEntry.getCoverImageAlt());

				blogsEntryFieldValues.add(
					new InfoFieldValue<>(
						BlogsEntryInfoItemFields.coverImageInfoField,
						coverWebImage));

				if (Validator.isNotNull(coverImageURL)) {
					blogsEntryFieldValues.add(
						new InfoFieldValue<>(
							BlogsEntryInfoItemFields.previewImageInfoField,
							coverWebImage));
				}
				else {
					blogsEntryFieldValues.add(
						new InfoFieldValue<>(
							BlogsEntryInfoItemFields.previewImageInfoField,
							smallWebImage));
				}
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
					BlogsEntryInfoItemFields.displayDateInfoField,
					blogsEntry.getDisplayDate()));
			blogsEntryFieldValues.add(
				new InfoFieldValue<>(
					BlogsEntryInfoItemFields.publishDateInfoField,
					blogsEntry.getDisplayDate()));

			if ((themeDisplay != null) &&
				!FeatureFlagManagerUtil.isEnabled("LPS-195205")) {

				blogsEntryFieldValues.add(
					new InfoFieldValue<>(
						BlogsEntryInfoItemFields.displayPageURLInfoField,
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

	private String _getDisplayPageURL(BlogsEntry blogsEntry)
		throws PortalException {

		return _assetDisplayPageFriendlyURLProvider.getFriendlyURL(
			new InfoItemReference(
				BlogsEntry.class.getName(),
				new ClassPKInfoItemIdentifier(blogsEntry.getEntryId())),
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
	private DisplayPageInfoItemFieldSetProvider
		_displayPageInfoItemFieldSetProvider;

	@Reference
	private ExpandoInfoItemFieldSetProvider _expandoInfoItemFieldSetProvider;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private TemplateInfoItemFieldSetProvider _templateInfoItemFieldSetProvider;

	@Reference
	private UserLocalService _userLocalService;

}