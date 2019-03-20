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

package com.liferay.headless.web.experience.internal.resource.v1_0;

import com.liferay.adaptive.media.AMAttribute;
import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.image.finder.AMImageFinder;
import com.liferay.adaptive.media.image.finder.AMImageQueryBuilder;
import com.liferay.adaptive.media.image.mime.type.AMImageMimeTypeProvider;
import com.liferay.adaptive.media.image.processor.AMImageAttribute;
import com.liferay.adaptive.media.image.processor.AMImageProcessor;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMTemplateService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.headless.web.experience.dto.v1_0.AdaptedImage;
import com.liferay.headless.web.experience.dto.v1_0.BlogPosting;
import com.liferay.headless.web.experience.dto.v1_0.ContentField;
import com.liferay.headless.web.experience.dto.v1_0.ContentListElement;
import com.liferay.headless.web.experience.dto.v1_0.Document;
import com.liferay.headless.web.experience.dto.v1_0.Folder;
import com.liferay.headless.web.experience.dto.v1_0.GenericContentListElement;
import com.liferay.headless.web.experience.dto.v1_0.Geo;
import com.liferay.headless.web.experience.dto.v1_0.Image;
import com.liferay.headless.web.experience.dto.v1_0.KnowledgeBaseArticle;
import com.liferay.headless.web.experience.dto.v1_0.ParentKnowledgeBaseFolder;
import com.liferay.headless.web.experience.dto.v1_0.RenderedContent;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContent;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContentLink;
import com.liferay.headless.web.experience.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.web.experience.dto.v1_0.Value;
import com.liferay.headless.web.experience.internal.dto.v1_0.util.AggregateRatingUtil;
import com.liferay.headless.web.experience.internal.dto.v1_0.util.ContentDocumentUtil;
import com.liferay.headless.web.experience.internal.dto.v1_0.util.ContentStructureUtil;
import com.liferay.headless.web.experience.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.web.experience.resource.v1_0.ContentListElementResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.util.JournalContent;
import com.liferay.journal.util.JournalConverter;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.knowledge.base.service.KBFolderService;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.wiki.model.WikiPage;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.Context;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/content-list-element.properties",
	scope = ServiceScope.PROTOTYPE, service = ContentListElementResource.class
)
public class ContentListElementResourceImpl
	extends BaseContentListElementResourceImpl {

	@Override
	public Page<ContentListElement> getContentListContentsPage(
			Long contentListId, Pagination pagination)
		throws Exception {

		AssetListEntry assetListEntry =
			_assetListEntryService.fetchAssetListEntry(contentListId);

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.getDefaultSegmentsEntry(
				assetListEntry.getGroupId());

		long segmentsEntryId = segmentsEntry.getSegmentsEntryId();

		return Page.of(
			transform(
				assetListEntry.getAssetEntries(
					segmentsEntryId, pagination.getStartPosition(),
					pagination.getEndPosition()),
				this::_toAssetListElement),
			pagination, assetListEntry.getAssetEntriesCount(segmentsEntryId));
	}

	private AdaptedImage[] _getAdaptiveMedias(FileEntry fileEntry)
		throws Exception {

		if (!_amImageMimeTypeProvider.isMimeTypeSupported(
				fileEntry.getMimeType())) {

			return new AdaptedImage[0];
		}

		Stream<AdaptiveMedia<AMImageProcessor>> stream =
			_amImageFinder.getAdaptiveMediaStream(
				amImageQueryBuilder -> amImageQueryBuilder.forFileEntry(
					fileEntry
				).withConfigurationStatus(
					AMImageQueryBuilder.ConfigurationStatus.ANY
				).done());

		return stream.map(
			this::_toAdaptedImage
		).toArray(
			AdaptedImage[]::new
		);
	}

	private String _getContentType(String className) {
		if (className.equals(BlogsEntry.class.getName())) {
			return "BlogPosting";
		}
		else if (className.equals(BookmarksEntry.class.getName())) {
			return "Bookmark";
		}
		else if (className.equals(BookmarksFolder.class.getName())) {
			return "BookmarkFolder";
		}
		else if (className.equals(CalendarBooking.class.getName())) {
			return "CalendarEvent";
		}
		else if (className.equals(DLFileEntry.class.getName())) {
			return "Document";
		}
		else if (className.equals(DLFolder.class.getName())) {
			return "Folder";
		}
		else if (className.equals(JournalArticle.class.getName())) {
			return "StructuredContent";
		}
		else if (className.equals(JournalFolder.class.getName())) {
			return "GoogleDriveShortcut";
		}
		else if (className.equals(JournalFolder.class.getName())) {
			return "StructuredContentFolder";
		}
		else if (className.equals(KBArticle.class.getName())) {
			return "KnowledgeBaseArticle";
		}
		else if (className.equals(MBMessage.class.getName())) {
			return "MessageBoardMessage";
		}
		else if (className.equals(WikiPage.class.getName())) {
			return "WikiPage";
		}
		else {
			return className;
		}
	}

	private Image _getImage(BlogsEntry blogsEntry) throws Exception {
		long coverImageFileEntryId = blogsEntry.getCoverImageFileEntryId();

		if (coverImageFileEntryId == 0) {
			return null;
		}

		FileEntry fileEntry = _dlAppService.getFileEntry(coverImageFileEntryId);

		return new Image() {
			{
				caption = blogsEntry.getCoverImageCaption();
				contentUrl = _dlURLHelper.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null, "", false,
					false);
				imageId = coverImageFileEntryId;
			}
		};
	}

	private <T, S> T _getValue(
		AdaptiveMedia<S> adaptiveMedia, AMAttribute<S, T> amAttribute) {

		Optional<T> optional = adaptiveMedia.getValueOptional(amAttribute);

		return optional.orElse(null);
	}

	private AdaptedImage _toAdaptedImage(
		AdaptiveMedia<AMImageProcessor> adaptiveMedia) {

		return new AdaptedImage() {
			{
				contentUrl = String.valueOf(adaptiveMedia.getURI());
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

	private ContentListElement _toAssetListElement(AssetEntry assetEntry)
		throws Exception {

		String className = assetEntry.getClassName();

		ContentListElement contentListElement = null;

		if (className.equals(BlogsEntry.class.getName())) {
			contentListElement = _toBlogPosting(
				_blogsEntryService.getEntry(assetEntry.getClassPK()));
		}
		else if (className.equals(DLFileEntry.class.getName())) {
			contentListElement = _toDocument(
				_dlAppService.getFileEntry(assetEntry.getClassPK()));
		}
		else if (className.equals(DLFolder.class.getName())) {
			contentListElement = _toFolder(
				_dlAppService.getFolder(assetEntry.getClassPK()));
		}
		else if (className.equals(JournalArticle.class.getName())) {
			contentListElement = _toStructuredContent(
				_journalArticleService.getLatestArticle(
					assetEntry.getClassPK()));
		}
		else if (className.equals(KBArticle.class.getName())) {
			contentListElement = _toKBArticle(
				_kbArticleService.getLatestKBArticle(
					assetEntry.getClassPK(),
					WorkflowConstants.STATUS_APPROVED));
		}
		else {
			contentListElement = _toGenericContentListElement(assetEntry);
		}

		contentListElement.setContentType(_getContentType(className));
		contentListElement.setOrder(assetEntry.getPriority());

		return contentListElement;
	}

	private BlogPosting _toBlogPosting(BlogsEntry blogsEntry) throws Exception {
		return new BlogPosting() {
			{
				alternativeHeadline = blogsEntry.getSubtitle();
				aggregateRating = AggregateRatingUtil.toAggregateRating(
					_ratingsStatsLocalService.fetchStats(
						BlogsEntry.class.getName(), blogsEntry.getEntryId()));
				articleBody = blogsEntry.getContent();
				contentSpaceId = blogsEntry.getGroupId();
				creator = CreatorUtil.toCreator(
					_portal, _userLocalService.getUser(blogsEntry.getUserId()));
				dateCreated = blogsEntry.getCreateDate();
				dateModified = blogsEntry.getModifiedDate();
				datePublished = blogsEntry.getDisplayDate();
				description = blogsEntry.getDescription();
				encodingFormat = "text/html";
				friendlyUrlPath = blogsEntry.getUrlTitle();
				headline = blogsEntry.getTitle();
				id = blogsEntry.getEntryId();
				image = _getImage(blogsEntry);
				keywords = ListUtil.toArray(
					_assetTagLocalService.getTags(
						BlogsEntry.class.getName(), blogsEntry.getEntryId()),
					AssetTag.NAME_ACCESSOR);
				numberOfComments = _commentManager.getCommentsCount(
					BlogsEntry.class.getName(), blogsEntry.getEntryId());
				taxonomyCategories = transformToArray(
					_assetCategoryLocalService.getCategories(
						BlogsEntry.class.getName(), blogsEntry.getEntryId()),
					assetCategory -> new TaxonomyCategory() {
						{
							taxonomyCategoryId = assetCategory.getCategoryId();
							taxonomyCategoryName = assetCategory.getName();
						}
					},
					TaxonomyCategory.class);
			}
		};
	}

	private ContentField _toContentField(DDMFormFieldValue ddmFormFieldValue)
		throws Exception {

		DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

		return new ContentField() {
			{
				dataType = ContentStructureUtil.toDataType(ddmFormField);
				inputControl = ContentStructureUtil.toInputControl(
					ddmFormField);
				name = ddmFormField.getName();
				repeatable = ddmFormField.isRepeatable();
				value = _toValue(
					ddmFormFieldValue,
					contextAcceptLanguage.getPreferredLocale());

				nestedFields = transformToArray(
					ddmFormFieldValue.getNestedDDMFormFieldValues(),
					value -> _toContentField(value), ContentField.class);
			}
		};
	}

	private ContentField[] _toContentFields(JournalArticle journalArticle)
		throws Exception {

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		Fields fields = _journalConverter.getDDMFields(
			ddmStructure, journalArticle.getContent());

		DDMFormValues ddmFormValues = _fieldsToDDMFormValuesConverter.convert(
			ddmStructure, fields);

		return transformToArray(
			ddmFormValues.getDDMFormFieldValues(), this::_toContentField,
			ContentField.class);
	}

	private Document _toDocument(FileEntry fileEntry) throws Exception {
		return _toDocument(
			fileEntry, fileEntry.getFileVersion(),
			_userLocalService.getUserById(fileEntry.getUserId()));
	}

	private Document _toDocument(
			FileEntry fileEntry, FileVersion fileVersion, User user)
		throws Exception {

		return new Document() {
			{
				adaptedImages = _getAdaptiveMedias(fileEntry);
				aggregateRating = AggregateRatingUtil.toAggregateRating(
					_ratingsStatsLocalService.fetchStats(
						DLFileEntry.class.getName(),
						fileEntry.getFileEntryId()));
				contentUrl = _dlURLHelper.getPreviewURL(
					fileEntry, fileVersion, null, "");
				creator = CreatorUtil.toCreator(_portal, user);
				dateCreated = fileEntry.getCreateDate();
				dateModified = fileEntry.getModifiedDate();
				description = fileEntry.getDescription();
				encodingFormat = fileEntry.getMimeType();
				fileExtension = fileEntry.getExtension();
				folderId = fileEntry.getFolderId();
				id = fileEntry.getFileEntryId();
				keywords = ListUtil.toArray(
					_assetTagLocalService.getTags(
						DLFileEntry.class.getName(),
						fileEntry.getFileEntryId()),
					AssetTag.NAME_ACCESSOR);
				numberOfComments = _commentManager.getCommentsCount(
					DLFileEntry.class.getName(), fileEntry.getFileEntryId());
				sizeInBytes = fileEntry.getSize();
				taxonomyCategories = transformToArray(
					_assetCategoryLocalService.getCategories(
						DLFileEntry.class.getName(),
						fileEntry.getFileEntryId()),
					assetCategory -> new TaxonomyCategory() {
						{
							taxonomyCategoryId = assetCategory.getCategoryId();
							taxonomyCategoryName = assetCategory.getName();
						}
					},
					TaxonomyCategory.class);
				title = fileEntry.getTitle();
			}
		};
	}

	private Folder _toFolder(
			com.liferay.portal.kernel.repository.model.Folder folder)
		throws Exception {

		return new Folder() {
			{
				contentSpaceId = folder.getGroupId();
				creator = CreatorUtil.toCreator(
					_portal, _userLocalService.getUser(folder.getUserId()));
				dateCreated = folder.getCreateDate();
				dateModified = folder.getModifiedDate();
				description = folder.getDescription();
				id = folder.getFolderId();
				name = folder.getName();
				numberOfDocuments = _dlAppService.getFileEntriesCount(
					folder.getRepositoryId(), folder.getFolderId());
				numberOfFolders = _dlAppService.getFoldersCount(
					folder.getRepositoryId(), folder.getFolderId());
			}
		};
	}

	private GenericContentListElement _toGenericContentListElement(
		AssetEntry inputAssetEntry) {

		return new GenericContentListElement() {
			{
				contentSpaceId = inputAssetEntry.getGroupId();
				dateCreated = inputAssetEntry.getCreateDate();
				dateModified = inputAssetEntry.getModifiedDate();
				description = inputAssetEntry.getDescription(
					contextAcceptLanguage.getPreferredLocale());
				id = inputAssetEntry.getPrimaryKey();
				title = inputAssetEntry.getTitle(
					contextAcceptLanguage.getPreferredLocale());
			}
		};
	}

	private KnowledgeBaseArticle _toKBArticle(KBArticle kbArticle)
		throws PortalException {

		if (kbArticle == null) {
			return null;
		}

		return new KnowledgeBaseArticle() {
			{
				aggregateRating = AggregateRatingUtil.toAggregateRating(
					_ratingsStatsLocalService.fetchStats(
						KBArticle.class.getName(),
						kbArticle.getResourcePrimKey()));
				articleBody = kbArticle.getContent();
				contentSpaceId = kbArticle.getGroupId();
				creator = CreatorUtil.toCreator(
					_portal, _userLocalService.getUser(kbArticle.getUserId()));
				dateCreated = kbArticle.getCreateDate();
				dateModified = kbArticle.getModifiedDate();
				description = kbArticle.getDescription();
				friendlyUrlPath = kbArticle.getUrlTitle();
				id = kbArticle.getResourcePrimKey();
				keywords = ListUtil.toArray(
					_assetTagLocalService.getTags(
						KBArticle.class.getName(), kbArticle.getClassPK()),
					AssetTag.NAME_ACCESSOR);
				numberOfAttachments = Optional.ofNullable(
					kbArticle.getAttachmentsFileEntries()
				).map(
					List::size
				).orElse(
					0
				);
				numberOfKnowledgeBaseArticles =
					_kbArticleService.getKBArticlesCount(
						kbArticle.getGroupId(), kbArticle.getResourcePrimKey(),
						WorkflowConstants.STATUS_APPROVED);
				parentKnowledgeBaseFolderId = kbArticle.getKbFolderId();
				taxonomyCategories = transformToArray(
					_assetCategoryLocalService.getCategories(
						KBArticle.class.getName(), kbArticle.getClassPK()),
					assetCategory -> _toTaxonomyCategory(assetCategory),
					TaxonomyCategory.class);
				title = kbArticle.getTitle();

				setParentKnowledgeBaseFolder(
					() -> {
						if (kbArticle.getKbFolderId() <= 0) {
							return null;
						}

						return _toParentKnowledgeBaseFolder(
							_kbFolderService.getKBFolder(
								kbArticle.getKbFolderId()));
					});
			}
		};
	}

	private ParentKnowledgeBaseFolder _toParentKnowledgeBaseFolder(
		KBFolder kbFolder) {

		if (kbFolder == null) {
			return null;
		}

		return new ParentKnowledgeBaseFolder() {
			{
				folderId = kbFolder.getKbFolderId();
				folderName = kbFolder.getName();
			}
		};
	}

	private StructuredContent _toStructuredContent(
			JournalArticle journalArticle)
		throws Exception {

		DDMStructure ddmStructure = journalArticle.getDDMStructure();

		return new StructuredContent() {
			{
				availableLanguages = LocaleUtil.toW3cLanguageIds(
					journalArticle.getAvailableLanguageIds());
				aggregateRating = AggregateRatingUtil.toAggregateRating(
					_ratingsStatsLocalService.fetchStats(
						JournalArticle.class.getName(),
						journalArticle.getResourcePrimKey()));
				contentFields = _toContentFields(journalArticle);
				contentSpaceId = journalArticle.getGroupId();
				contentStructureId = ddmStructure.getStructureId();
				creator = CreatorUtil.toCreator(
					_portal,
					_userLocalService.getUserById(journalArticle.getUserId()));
				dateCreated = journalArticle.getCreateDate();
				dateModified = journalArticle.getModifiedDate();
				datePublished = journalArticle.getDisplayDate();
				description = journalArticle.getDescription(
					contextAcceptLanguage.getPreferredLocale());
				id = journalArticle.getResourcePrimKey();
				keywords = ListUtil.toArray(
					_assetTagLocalService.getTags(
						JournalArticle.class.getName(),
						journalArticle.getResourcePrimKey()),
					AssetTag.NAME_ACCESSOR);
				lastReviewed = journalArticle.getReviewDate();
				numberOfComments = _commentManager.getCommentsCount(
					JournalArticle.class.getName(),
					journalArticle.getResourcePrimKey());
				renderedContents = transformToArray(
					ddmStructure.getTemplates(),
					ddmTemplate -> new RenderedContent() {
						{
							renderedContentURL = getJAXRSLink(
								"getStructuredContentRenderedContentTemplate",
								journalArticle.getResourcePrimKey(),
								ddmTemplate.getTemplateId());
							templateName = ddmTemplate.getName(
								contextAcceptLanguage.getPreferredLocale());
						}
					},
					RenderedContent.class);
				taxonomyCategories = transformToArray(
					_assetCategoryLocalService.getCategories(
						JournalArticle.class.getName(),
						journalArticle.getResourcePrimKey()),
					assetCategory -> new TaxonomyCategory() {
						{
							taxonomyCategoryId = assetCategory.getCategoryId();
							taxonomyCategoryName = assetCategory.getName();
						}
					},
					TaxonomyCategory.class);
				title = journalArticle.getTitle(
					contextAcceptLanguage.getPreferredLocale());
			}
		};
	}

	private TaxonomyCategory _toTaxonomyCategory(AssetCategory assetCategory) {
		return new TaxonomyCategory() {
			{
				taxonomyCategoryId = assetCategory.getCategoryId();
				taxonomyCategoryName = assetCategory.getName();
			}
		};
	}

	private Value _toValue(DDMFormFieldValue ddmFormFieldValue, Locale locale)
		throws Exception {

		com.liferay.dynamic.data.mapping.model.Value value =
			ddmFormFieldValue.getValue();

		if (value == null) {
			return null;
		}

		DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

		String valueString = String.valueOf(value.getString(locale));

		if (Objects.equals(
				DDMFormFieldType.DOCUMENT_LIBRARY, ddmFormField.getType())) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				valueString);

			long classPK = jsonObject.getLong("classPK");

			if (classPK == 0) {
				return null;
			}

			FileEntry fileEntry = _dlAppService.getFileEntry(classPK);

			return new Value() {
				{
					document = ContentDocumentUtil.toContentDocument(
						_dlURLHelper, fileEntry);
				}
			};
		}

		if (Objects.equals(
				DDMFormFieldType.GEOLOCATION, ddmFormField.getType())) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				valueString);

			return new Value() {
				{
					geo = new Geo() {
						{
							latitude = jsonObject.getDouble("latitude");
							longitude = jsonObject.getDouble("longitude");
						}
					};
				}
			};
		}

		if (Objects.equals(DDMFormFieldType.IMAGE, ddmFormField.getType())) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				valueString);

			long fileEntryId = jsonObject.getLong("fileEntryId");

			if (fileEntryId == 0) {
				return null;
			}

			FileEntry fileEntry = _dlAppService.getFileEntry(fileEntryId);

			return new Value() {
				{
					image = ContentDocumentUtil.toContentDocument(
						_dlURLHelper, fileEntry);
					imageDescription = jsonObject.getString("alt");
					imageId = fileEntryId;
				}
			};
		}

		if (Objects.equals(
				DDMFormFieldType.JOURNAL_ARTICLE, ddmFormField.getType())) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				valueString);

			long classPK = jsonObject.getLong("classPK");

			if (classPK == 0) {
				return null;
			}

			JournalArticle journalArticle =
				_journalArticleService.getLatestArticle(classPK);

			return new Value() {
				{
					structuredContentLink = new StructuredContentLink() {
						{
							id = journalArticle.getId();
							title = journalArticle.getTitle();
						}
					};
				}
			};
		}

		if (Objects.equals(
				DDMFormFieldType.LINK_TO_PAGE, ddmFormField.getType())) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				valueString);

			long layoutId = jsonObject.getLong("layoutId");

			if (layoutId == 0) {
				return null;
			}

			long groupId = jsonObject.getLong("groupId");
			boolean privateLayout = jsonObject.getBoolean("privateLayout");

			Layout layoutByUuidAndGroupId = _layoutLocalService.getLayout(
				groupId, privateLayout, layoutId);

			return new Value() {
				{
					link = layoutByUuidAndGroupId.getFriendlyURL();
				}
			};
		}

		return new Value() {
			{
				data = valueString;
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
	private AssetListEntryService _assetListEntryService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private BlogsEntryService _blogsEntryService;

	@Reference
	private CommentManager _commentManager;

	@Context
	private HttpServletRequest _contextHttpServletRequest;

	@Reference
	private DDMTemplateService _ddmTemplateService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private JournalContent _journalContent;

	@Reference
	private JournalConverter _journalConverter;

	@Reference
	private KBArticleService _kbArticleService;

	@Reference
	private KBFolderService _kbFolderService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private RatingsStatsLocalService _ratingsStatsLocalService;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}