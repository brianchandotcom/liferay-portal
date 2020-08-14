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

package com.liferay.headless.delivery.internal.dto.v1_0.converter;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.image.finder.AMImageFinder;
import com.liferay.adaptive.media.image.finder.AMImageQueryBuilder;
import com.liferay.adaptive.media.image.mime.type.AMImageMimeTypeProvider;
import com.liferay.adaptive.media.image.processor.AMImageAttribute;
import com.liferay.adaptive.media.image.processor.AMImageProcessor;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetLinkLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManagerUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMBeanTranslator;
import com.liferay.headless.delivery.dto.v1_0.AdaptedImage;
import com.liferay.headless.delivery.dto.v1_0.ContentField;
import com.liferay.headless.delivery.dto.v1_0.Document;
import com.liferay.headless.delivery.dto.v1_0.DocumentType;
import com.liferay.headless.delivery.dto.v1_0.TaxonomyCategoryBrief;
import com.liferay.headless.delivery.internal.dto.v1_0.util.AggregateRatingUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.ContentFieldUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.ContentValueUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.RelatedContentUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.TaxonomyCategoryBriefUtil;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.GroupUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	property = "dto.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = {DocumentDTOConverter.class, DTOConverter.class}
)
public class DocumentDTOConverter
	implements DTOConverter<DLFileEntry, Document> {

	@Override
	public String getContentType() {
		return Document.class.getSimpleName();
	}

	@Override
	public Document toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		FileEntry fileEntry = _dlAppService.getFileEntry(
			(Long)dtoConverterContext.getId());

		Group group = _groupLocalService.fetchGroup(fileEntry.getGroupId());

		FileVersion fileVersion = fileEntry.getFileVersion();

		return new Document() {
			{
				actions = dtoConverterContext.getActions();
				adaptedImages = _getAdaptiveMedias(
					dtoConverterContext, fileEntry);
				aggregateRating = AggregateRatingUtil.toAggregateRating(
					_ratingsStatsLocalService.fetchStats(
						DLFileEntry.class.getName(),
						fileEntry.getFileEntryId()));
				assetLibraryKey = GroupUtil.getAssetLibraryKey(group);
				contentUrl = _dlURLHelper.getPreviewURL(
					fileEntry, fileVersion, null, "");
				contentValue = ContentValueUtil.toContentValue(
					"contentValue", fileEntry::getContentStream,
					dtoConverterContext.getUriInfoOptional());
				creator = CreatorUtil.toCreator(
					_portal, dtoConverterContext.getUriInfoOptional(),
					_userLocalService.fetchUser(fileEntry.getUserId()));
				customFields = CustomFieldsUtil.toCustomFields(
					dtoConverterContext.isAcceptAllLanguages(),
					DLFileEntry.class.getName(), fileVersion.getFileVersionId(),
					fileEntry.getCompanyId(), dtoConverterContext.getLocale());
				dateCreated = fileEntry.getCreateDate();
				dateModified = fileEntry.getModifiedDate();
				description = fileEntry.getDescription();
				documentFolderId = fileEntry.getFolderId();
				documentType = _toDocumentType(
					dtoConverterContext, fileVersion);
				encodingFormat = fileEntry.getMimeType();
				fileExtension = fileEntry.getExtension();
				id = fileEntry.getFileEntryId();
				keywords = ListUtil.toArray(
					_assetTagLocalService.getTags(
						DLFileEntry.class.getName(),
						fileEntry.getFileEntryId()),
					AssetTag.NAME_ACCESSOR);
				numberOfComments = _commentManager.getCommentsCount(
					DLFileEntry.class.getName(), fileEntry.getFileEntryId());
				relatedContents = RelatedContentUtil.toRelatedContents(
					_assetEntryLocalService, _assetLinkLocalService,
					dtoConverterContext.getDTOConverterRegistry(),
					DLFileEntry.class.getName(), fileEntry.getFileEntryId(),
					dtoConverterContext.getLocale());
				siteId = GroupUtil.getSiteId(group);
				sizeInBytes = fileEntry.getSize();
				taxonomyCategoryBriefs = TransformUtil.transformToArray(
					_assetCategoryLocalService.getCategories(
						DLFileEntry.class.getName(),
						fileEntry.getFileEntryId()),
					assetCategory ->
						TaxonomyCategoryBriefUtil.toTaxonomyCategoryBrief(
							dtoConverterContext.isAcceptAllLanguages(),
							assetCategory, dtoConverterContext.getLocale()),
					TaxonomyCategoryBrief.class);
				title = fileEntry.getTitle();
			}
		};
	}

	private AdaptedImage[] _getAdaptiveMedias(
			DTOConverterContext dtoConverterContext, FileEntry fileEntry)
		throws Exception {

		if (!_amImageMimeTypeProvider.isMimeTypeSupported(
				fileEntry.getMimeType())) {

			return new AdaptedImage[0];
		}

		Stream<AdaptiveMedia<AMImageProcessor>> adaptiveMediaStream =
			_amImageFinder.getAdaptiveMediaStream(
				amImageQueryBuilder -> amImageQueryBuilder.forFileEntry(
					fileEntry
				).withConfigurationStatus(
					AMImageQueryBuilder.ConfigurationStatus.ANY
				).done());

		List<AdaptiveMedia<AMImageProcessor>> adaptiveMedias =
			adaptiveMediaStream.collect(Collectors.toList());

		return TransformUtil.transformToArray(
			adaptiveMedias,
			adaptiveMedia -> _toAdaptedImage(
				adaptiveMedia, dtoConverterContext.getUriInfoOptional()),
			AdaptedImage.class);
	}

	private List<DDMFormValues> _getDDMFormValues(
			DLFileEntryType dlFileEntryType, DLFileVersion dlFileVersion)
		throws Exception {

		List<DDMFormValues> ddmFormValues = new ArrayList<>();

		for (DDMStructure ddmStructure : dlFileEntryType.getDDMStructures()) {
			DLFileEntryMetadata dlFileEntryMetadata =
				_dlFileEntryMetadataLocalService.fetchFileEntryMetadata(
					ddmStructure.getStructureId(),
					dlFileVersion.getFileVersionId());

			if (dlFileEntryMetadata == null) {
				continue;
			}

			ddmFormValues.add(
				_ddmBeanTranslator.translate(
					StorageEngineManagerUtil.getDDMFormValues(
						dlFileEntryMetadata.getDDMStorageId())));
		}

		return ddmFormValues;
	}

	private <T, S> T _getValue(
		AdaptiveMedia<S> adaptiveMedia, AMAttribute<S, T> amAttribute) {

		Optional<T> valueOptional = adaptiveMedia.getValueOptional(amAttribute);

		return valueOptional.orElse(null);
	}

	private AdaptedImage _toAdaptedImage(
			AdaptiveMedia<AMImageProcessor> adaptiveMedia,
			Optional<UriInfo> uriInfoOptional)
		throws Exception {

		if (adaptiveMedia == null) {
			return null;
		}

		return new AdaptedImage() {
			{
				contentUrl = String.valueOf(adaptiveMedia.getURI());
				contentValue = ContentValueUtil.toContentValue(
					"adaptedImages.contentValue", adaptiveMedia::getInputStream,
					uriInfoOptional);
				height = _getValue(
					adaptiveMedia, AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT);
				resolutionName = _getValue(
					adaptiveMedia,
					AMAttribute.getConfigurationUuidAMAttribute());
				sizeInBytes = _getValue(
					adaptiveMedia, AMAttribute.getContentLengthAMAttribute());
				width = _getValue(
					adaptiveMedia, AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH);
			}
		};
	}

	private DocumentType _toDocumentType(
			DTOConverterContext dtoConverterContext, FileVersion fileVersion)
		throws Exception {

		if (!(fileVersion.getModel() instanceof DLFileVersion)) {
			return null;
		}

		DLFileVersion dlFileVersion = (DLFileVersion)fileVersion.getModel();

		DLFileEntryType dlFileEntryType = dlFileVersion.getDLFileEntryType();

		List<DDMFormValues> ddmFormValues = _getDDMFormValues(
			dlFileEntryType, dlFileVersion);

		return new DocumentType() {
			{
				description = dlFileEntryType.getDescription(
					dtoConverterContext.getLocale());
				description_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					dlFileEntryType.getDescriptionMap());
				name = dlFileEntryType.getName(dtoConverterContext.getLocale());
				name_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					dlFileEntryType.getNameMap());

				setAvailableLanguages(
					() -> {
						Set<Locale> locales = new HashSet<>();

						for (DDMFormValues ddmFormValue : ddmFormValues) {
							locales.addAll(ddmFormValue.getAvailableLocales());
						}

						return LocaleUtil.toW3cLanguageIds(
							locales.toArray(new Locale[0]));
					});
				setContentFields(
					() -> {
						List<DDMFormFieldValue> ddmFormFieldValues =
							new ArrayList<>();

						for (DDMFormValues ddmFormValue : ddmFormValues) {
							ddmFormFieldValues.addAll(
								ddmFormValue.getDDMFormFieldValues());
						}

						return TransformUtil.transformToArray(
							ddmFormFieldValues,
							ddmFormFieldValue ->
								ContentFieldUtil.toContentField(
									ddmFormFieldValue, _dlAppService,
									_dlURLHelper, dtoConverterContext,
									_journalArticleService,
									_layoutLocalService),
							ContentField.class);
					});
			}
		};
	}

	@Reference
	private AMImageFinder _amImageFinder;

	@Reference
	private AMImageMimeTypeProvider _amImageMimeTypeProvider;

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetLinkLocalService _assetLinkLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private CommentManager _commentManager;

	@Reference
	private DDMBeanTranslator _ddmBeanTranslator;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLFileEntryMetadataLocalService _dlFileEntryMetadataLocalService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private RatingsStatsLocalService _ratingsStatsLocalService;

	@Reference
	private UserLocalService _userLocalService;

}