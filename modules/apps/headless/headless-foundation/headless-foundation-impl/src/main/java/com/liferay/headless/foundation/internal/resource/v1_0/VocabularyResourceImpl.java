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

package com.liferay.headless.foundation.internal.resource.v1_0;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.headless.common.spi.service.context.ServiceContextUtil;
import com.liferay.headless.foundation.dto.v1_0.AssetType;
import com.liferay.headless.foundation.dto.v1_0.Vocabulary;
import com.liferay.headless.foundation.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.foundation.internal.odata.entity.v1_0.VocabularyEntityModel;
import com.liferay.headless.foundation.resource.v1_0.VocabularyResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.ClassNameService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.ContentLanguageUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/vocabulary.properties",
	scope = ServiceScope.PROTOTYPE, service = VocabularyResource.class
)
public class VocabularyResourceImpl
	extends BaseVocabularyResourceImpl implements EntityModelResource {

	@Override
	public boolean deleteVocabulary(Long vocabularyId) throws Exception {
		_assetVocabularyService.deleteVocabulary(vocabularyId);

		return true;
	}

	@Override
	public Page<Vocabulary> getContentSpaceVocabulariesPage(
			Long contentSpaceId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			booleanQuery -> {
			},
			filter, AssetVocabulary.class, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ASSET_VOCABULARY_ID),
			searchContext -> {
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(new long[] {contentSpaceId});
			},
			document -> _toVocabulary(
				_assetVocabularyService.getVocabulary(
					GetterUtil.getLong(
						document.get(Field.ASSET_VOCABULARY_ID)))),
			sorts);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public Vocabulary getVocabulary(Long vocabularyId) throws Exception {
		AssetVocabulary assetVocabulary = _assetVocabularyService.getVocabulary(
			vocabularyId);

		ContentLanguageUtil.addContentLanguageHeader(
			assetVocabulary.getAvailableLanguageIds(),
			assetVocabulary.getDefaultLanguageId(), _contextHttpServletResponse,
			contextAcceptLanguage.getPreferredLocale());

		return _toVocabulary(assetVocabulary);
	}

	@Override
	public Vocabulary postContentSpaceVocabulary(
			Long contentSpaceId, Vocabulary vocabulary)
		throws Exception {

		return _toVocabulary(
			_assetVocabularyService.addVocabulary(
				contentSpaceId, null,
				Collections.singletonMap(
					contextAcceptLanguage.getPreferredLocale(),
					vocabulary.getName()),
				Collections.singletonMap(
					contextAcceptLanguage.getPreferredLocale(),
					vocabulary.getDescription()),
				_toSettings(vocabulary.getAssetTypes(), contentSpaceId),
				ServiceContextUtil.createServiceContext(
					contentSpaceId, vocabulary.getViewableBy())));
	}

	@Override
	public Vocabulary putVocabulary(Long vocabularyId, Vocabulary vocabulary)
		throws Exception {

		AssetVocabulary assetVocabulary = _assetVocabularyService.getVocabulary(
			vocabularyId);

		return _toVocabulary(
			_assetVocabularyService.updateVocabulary(
				assetVocabulary.getVocabularyId(), null,
				LocalizedMapUtil.merge(
					assetVocabulary.getTitleMap(),
					new AbstractMap.SimpleEntry<>(
						contextAcceptLanguage.getPreferredLocale(),
						vocabulary.getName())),
				LocalizedMapUtil.merge(
					assetVocabulary.getDescriptionMap(),
					new AbstractMap.SimpleEntry<>(
						contextAcceptLanguage.getPreferredLocale(),
						vocabulary.getDescription())),
				_toSettings(
					vocabulary.getAssetTypes(), assetVocabulary.getGroupId()),
				new ServiceContext()));
	}

	private AssetType[] _toAssetTypes(
			AssetVocabularySettingsHelper assetVocabularySettingsHelper,
			long groupId)
		throws PortalException {

		long[] classNameIds = assetVocabularySettingsHelper.getClassNameIds();

		if (ArrayUtil.isEmpty(classNameIds)) {
			return new AssetType[0];
		}

		long[] requiredClassNameIds =
			assetVocabularySettingsHelper.getRequiredClassNameIds();
		long[] classTypePKs = assetVocabularySettingsHelper.getClassTypePKs();

		List<AssetType> assetTypes = new ArrayList<>();

		for (int i = 0; i < classNameIds.length; i++) {
			long classNameId = classNameIds[i];
			final long classTypePK = classTypePKs[i];

			assetTypes.add(
				new AssetType() {
					{
						subtype = _toSubtype(classNameId, classTypePK, groupId);
						type = _toType(classNameId);
						required = _toRequired(
							classNameId, requiredClassNameIds);
					}
				});
		}

		return assetTypes.toArray(new AssetType[assetTypes.size()]);
	}

	private long _toClassNameId(AssetType.Type type) {
		ClassName className = null;

		if (Objects.equals(AssetType.Type.BLOG_POSTING, type)) {
			className = _classNameService.fetchClassName(
				"com.liferay.blogs.model.BlogsEntry");
		}
		else if (Objects.equals(AssetType.Type.DOCUMENT, type)) {
			className = _classNameService.fetchClassName(
				FileEntry.class.getName());
		}
		else if (Objects.equals(AssetType.Type.KNOWLEDGE_BASE_ARTICLE, type)) {
			className = _classNameService.fetchClassName(
				"com.liferay.knowledge.base.model.KBArticle");
		}
		else if (Objects.equals(AssetType.Type.ORGANIZATION, type)) {
			className = _classNameService.fetchClassName(
				Organization.class.getName());
		}
		else if (Objects.equals(AssetType.Type.STRUCTURED_CONTENT, type)) {
			className = _classNameService.fetchClassName(
				"com.liferay.journal.model.JournalArticle");
		}
		else if (Objects.equals(AssetType.Type.USER_ACCOUNT, type)) {
			className = _classNameService.fetchClassName(User.class.getName());
		}
		else if (Objects.equals(AssetType.Type.WEB_PAGE, type)) {
			className = _classNameService.fetchClassName(
				Layout.class.getName());
		}
		else if (Objects.equals(AssetType.Type.WEB_SITE, type)) {
			className = _classNameService.fetchClassName(Group.class.getName());
		}
		else if (Objects.equals(AssetType.Type.WIKI_PAGE, type)) {
			className = _classNameService.fetchClassName(
				"com.liferay.wiki.model.WikiPage");
		}

		if (className == null) {
			throw new BadRequestException("Invalid AssetType " + type);
		}

		return className.getClassNameId();
	}

	private long _toClassTypePK(long classNameId, String subtype, long groupId)
		throws PortalException {

		if (Objects.equals("AllAssetSubtypes", subtype)) {
			return AssetCategoryConstants.ALL_CLASS_TYPE_PK;
		}

		ClassName className = _classNameService.fetchByClassNameId(classNameId);

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className.getClassName());

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		List<ClassType> classTypes = classTypeReader.getAvailableClassTypes(
			new long[] {groupId, contextCompany.getGroupId()},
			contextAcceptLanguage.getPreferredLocale());

		if (ListUtil.isEmpty(classTypes)) {
			return AssetCategoryConstants.ALL_CLASS_TYPE_PK;
		}

		for (ClassType classType : classTypes) {
			if (Objects.equals(classType.getName(), subtype)) {
				return classType.getClassTypeId();
			}
		}

		throw new BadRequestException("Invalid subtype " + subtype);
	}

	private boolean _toRequired(long classNameId, long[] requiredClassNameIds) {
		if (ArrayUtil.isEmpty(requiredClassNameIds)) {
			return false;
		}

		for (long requiredClassNameId : requiredClassNameIds) {
			if (requiredClassNameId == classNameId) {
				return true;
			}
		}

		return false;
	}

	private String _toSettings(AssetType[] assetTypes, long groupId)
		throws PortalException {

		AssetVocabularySettingsHelper assetVocabularySettingsHelper =
			new AssetVocabularySettingsHelper();

		if (ArrayUtil.isEmpty(assetTypes)) {
			return assetVocabularySettingsHelper.toString();
		}

		long[] classNameIds = new long[assetTypes.length];
		long[] classTypePKs = new long[assetTypes.length];
		boolean[] requiredClassNameIds = new boolean[assetTypes.length];

		for (int i = 0; i < assetTypes.length; i++) {
			AssetType assetType = assetTypes[i];

			long classNameId = _toClassNameId(assetType.getType());

			classNameIds[i] = classNameId;

			classTypePKs[i] = _toClassTypePK(
				classNameId, assetType.getSubtype(), groupId);

			requiredClassNameIds[i] = assetType.getRequired();
		}

		assetVocabularySettingsHelper.setClassNameIdsAndClassTypePKs(
			classNameIds, classTypePKs, requiredClassNameIds);

		assetVocabularySettingsHelper.setMultiValued(true);

		return assetVocabularySettingsHelper.toString();
	}

	private String _toSubtype(long classNameId, long classTypePK, long groupId)
		throws PortalException {

		if (classTypePK == AssetCategoryConstants.ALL_CLASS_TYPE_PK) {
			return "AllAssetSubtypes";
		}

		ClassName className = _classNameService.fetchByClassNameId(classNameId);

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className.getClassName());

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		List<ClassType> classTypes = classTypeReader.getAvailableClassTypes(
			new long[] {groupId, contextCompany.getGroupId()},
			contextAcceptLanguage.getPreferredLocale());

		if (ListUtil.isEmpty(classTypes)) {
			return "All";
		}

		for (ClassType classType : classTypes) {
			if (classType.getClassTypeId() == classTypePK) {
				return classType.getName();
			}
		}

		throw new InternalServerErrorException();
	}

	private AssetType.Type _toType(long classNameId) {
		if (classNameId == AssetCategoryConstants.ALL_CLASS_NAME_ID) {
			return AssetType.Type.ALL_ASSET_TYPES;
		}

		ClassName className = _classNameService.fetchByClassNameId(classNameId);

		if (Objects.equals(
				FileEntry.class.getName(), className.getClassName())) {

			return AssetType.Type.DOCUMENT;
		}
		else if (Objects.equals(
					Group.class.getName(), className.getClassName())) {

			return AssetType.Type.WEB_SITE;
		}
		else if (Objects.equals(
					Layout.class.getName(), className.getClassName())) {

			return AssetType.Type.WEB_PAGE;
		}
		else if (Objects.equals(
					Organization.class.getName(), className.getClassName())) {

			return AssetType.Type.ORGANIZATION;
		}
		else if (Objects.equals(
					User.class.getName(), className.getClassName())) {

			return AssetType.Type.USER_ACCOUNT;
		}
		else if (Objects.equals(
					"com.liferay.blogs.model.BlogsEntry",
					className.getClassName())) {

			return AssetType.Type.BLOG_POSTING;
		}
		else if (Objects.equals(
					"com.liferay.knowledge.base.model.KBArticle",
					className.getClassName())) {

			return AssetType.Type.KNOWLEDGE_BASE_ARTICLE;
		}
		else if (Objects.equals(
					"com.liferay.journal.model.JournalArticle",
					className.getClassName())) {

			return AssetType.Type.STRUCTURED_CONTENT;
		}
		else if (Objects.equals(
					"com.liferay.wiki.model.WikiPage",
					className.getClassName())) {

			return AssetType.Type.WIKI_PAGE;
		}

		return null;
	}

	private Vocabulary _toVocabulary(AssetVocabulary assetVocabulary)
		throws Exception {

		return new Vocabulary() {
			{
				assetTypes = _toAssetTypes(
					new AssetVocabularySettingsHelper(
						assetVocabulary.getSettings()),
					assetVocabulary.getGroupId());
				availableLanguages = LocaleUtil.toW3cLanguageIds(
					assetVocabulary.getAvailableLanguageIds());
				contentSpace = assetVocabulary.getGroupId();
				creator = CreatorUtil.toCreator(
					_portal,
					_userLocalService.getUser(assetVocabulary.getUserId()));
				dateCreated = assetVocabulary.getCreateDate();
				dateModified = assetVocabulary.getModifiedDate();
				description = assetVocabulary.getDescription(
					contextAcceptLanguage.getPreferredLocale());
				hasCategories = ListUtil.isNotEmpty(
					assetVocabulary.getCategories());
				id = assetVocabulary.getVocabularyId();
				name = assetVocabulary.getTitle(
					contextAcceptLanguage.getPreferredLocale());
			}
		};
	}

	private static final EntityModel _entityModel = new VocabularyEntityModel();

	@Reference
	private AssetVocabularyService _assetVocabularyService;

	@Reference
	private ClassNameService _classNameService;

	@Context
	private HttpServletResponse _contextHttpServletResponse;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}