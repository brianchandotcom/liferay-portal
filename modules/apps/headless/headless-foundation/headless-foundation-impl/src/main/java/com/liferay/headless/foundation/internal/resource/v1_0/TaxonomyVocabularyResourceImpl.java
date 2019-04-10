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
import com.liferay.headless.foundation.dto.v1_0.TaxonomyVocabulary;
import com.liferay.headless.foundation.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.foundation.internal.odata.entity.v1_0.VocabularyEntityModel;
import com.liferay.headless.foundation.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/taxonomy-vocabulary.properties",
	scope = ServiceScope.PROTOTYPE, service = TaxonomyVocabularyResource.class
)
public class TaxonomyVocabularyResourceImpl
	extends BaseTaxonomyVocabularyResourceImpl implements EntityModelResource {

	@Override
	public void deleteTaxonomyVocabulary(Long taxonomyVocabularyId)
		throws Exception {

		_assetVocabularyService.deleteVocabulary(taxonomyVocabularyId);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public Page<TaxonomyVocabulary> getSiteTaxonomyVocabulariesPage(
			Long siteId, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			booleanQuery -> {
			},
			filter, AssetVocabulary.class, search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ASSET_VOCABULARY_ID),
			searchContext -> {
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(new long[] {siteId});
			},
			document -> _toTaxonomyVocabulary(
				_assetVocabularyService.getVocabulary(
					GetterUtil.getLong(
						document.get(Field.ASSET_VOCABULARY_ID)))),
			sorts);
	}

	@Override
	public TaxonomyVocabulary getTaxonomyVocabulary(Long taxonomyVocabularyId)
		throws Exception {

		AssetVocabulary assetVocabulary = _assetVocabularyService.getVocabulary(
			taxonomyVocabularyId);

		ContentLanguageUtil.addContentLanguageHeader(
			assetVocabulary.getAvailableLanguageIds(),
			assetVocabulary.getDefaultLanguageId(), _httpServletResponse,
			contextAcceptLanguage.getPreferredLocale());

		return _toTaxonomyVocabulary(assetVocabulary);
	}

	@Override
	public TaxonomyVocabulary patchTaxonomyVocabulary(
			Long taxonomyVocabularyId, TaxonomyVocabulary taxonomyVocabulary)
		throws Exception {

		AssetVocabulary assetVocabulary = _assetVocabularyService.getVocabulary(
			taxonomyVocabularyId);

		if (!ArrayUtil.contains(
				assetVocabulary.getAvailableLanguageIds(),
				contextAcceptLanguage.getPreferredLanguageId())) {

			throw new BadRequestException(
				StringBundler.concat(
					"Unable to patch taxonomy vocabulary with language ",
					LocaleUtil.toW3cLanguageId(
						contextAcceptLanguage.getPreferredLanguageId()),
					" because it is only available in the following languages ",
					LocaleUtil.toW3cLanguageIds(
						assetVocabulary.getAvailableLanguageIds())));
		}

		AssetType[] assetTypes = taxonomyVocabulary.getAssetTypes();

		if (assetTypes == null) {
			assetTypes = _getAssetTypes(
				new AssetVocabularySettingsHelper(
					assetVocabulary.getSettings()),
				assetVocabulary.getGroupId());
		}

		return _toTaxonomyVocabulary(
			_assetVocabularyService.updateVocabulary(
				assetVocabulary.getVocabularyId(), null,
				LocalizedMapUtil.patch(
					assetVocabulary.getTitleMap(),
					contextAcceptLanguage.getPreferredLocale(),
					taxonomyVocabulary.getName()),
				LocalizedMapUtil.patch(
					assetVocabulary.getDescriptionMap(),
					contextAcceptLanguage.getPreferredLocale(),
					taxonomyVocabulary.getDescription()),
				_getSettings(assetTypes, assetVocabulary.getGroupId()),
				new ServiceContext()));
	}

	@Override
	public TaxonomyVocabulary postSiteTaxonomyVocabulary(
			Long siteId, TaxonomyVocabulary taxonomyVocabulary)
		throws Exception {

		Locale siteDefaultLocale = LocaleThreadLocal.getSiteDefaultLocale();

		if (!LocaleUtil.equals(
				siteDefaultLocale,
				contextAcceptLanguage.getPreferredLocale())) {

			String w3cLanguageId = LocaleUtil.toW3cLanguageId(
				siteDefaultLocale);

			throw new BadRequestException(
				"Taxonomy vocabularies can only be created with the default " +
					"language " + w3cLanguageId);
		}

		return _toTaxonomyVocabulary(
			_assetVocabularyService.addVocabulary(
				siteId, null,
				Collections.singletonMap(
					contextAcceptLanguage.getPreferredLocale(),
					taxonomyVocabulary.getName()),
				Collections.singletonMap(
					contextAcceptLanguage.getPreferredLocale(),
					taxonomyVocabulary.getDescription()),
				_getSettings(taxonomyVocabulary.getAssetTypes(), siteId),
				ServiceContextUtil.createServiceContext(
					siteId, taxonomyVocabulary.getViewableByAsString())));
	}

	@Override
	public TaxonomyVocabulary putTaxonomyVocabulary(
			Long taxonomyVocabularyId, TaxonomyVocabulary taxonomyVocabulary)
		throws Exception {

		AssetVocabulary assetVocabulary = _assetVocabularyService.getVocabulary(
			taxonomyVocabularyId);

		return _toTaxonomyVocabulary(
			_assetVocabularyService.updateVocabulary(
				assetVocabulary.getVocabularyId(), null,
				LocalizedMapUtil.merge(
					assetVocabulary.getTitleMap(),
					new AbstractMap.SimpleEntry<>(
						contextAcceptLanguage.getPreferredLocale(),
						taxonomyVocabulary.getName())),
				LocalizedMapUtil.merge(
					assetVocabulary.getDescriptionMap(),
					new AbstractMap.SimpleEntry<>(
						contextAcceptLanguage.getPreferredLocale(),
						taxonomyVocabulary.getDescription())),
				_getSettings(
					taxonomyVocabulary.getAssetTypes(),
					assetVocabulary.getGroupId()),
				new ServiceContext()));
	}

	private AssetType _getAssetType(
		long groupId, long classNameId, long classTypePK,
		long[] requiredClassNameIds) {

		return new AssetType() {
			{
				required = ArrayUtil.contains(
					requiredClassNameIds, classNameId);
				setSubtype(
					() -> {
						if (classTypePK ==
								AssetCategoryConstants.ALL_CLASS_TYPE_PK) {

							return "AllAssetSubtypes";
						}

						AssetRendererFactory assetRendererFactory =
							AssetRendererFactoryRegistryUtil.
								getAssetRendererFactoryByClassName(
									_portal.getClassName(classNameId));

						ClassTypeReader classTypeReader =
							assetRendererFactory.getClassTypeReader();

						List<ClassType> classTypes =
							classTypeReader.getAvailableClassTypes(
								new long[] {
									groupId, contextCompany.getGroupId()
								},
								contextAcceptLanguage.getPreferredLocale());

						if (ListUtil.isEmpty(classTypes)) {
							return "AllAssetSubtypes";
						}

						for (ClassType classType : classTypes) {
							if (classType.getClassTypeId() == classTypePK) {
								return classType.getName();
							}
						}

						throw new InternalServerErrorException();
					});
				setType(
					() -> {
						if (classNameId ==
								AssetCategoryConstants.ALL_CLASS_NAME_ID) {

							return AssetTypeType.ALL_ASSET_TYPES.toString();
						}

						AssetTypeType assetTypeType =
							_classNameToAssetTypeTypes.get(
								_portal.getClassName(classNameId));

						if (assetTypeType != null) {
							return assetTypeType.toString();
						}

						return _getModelResource(
							AssetRendererFactoryRegistryUtil.
								getAssetRendererFactoryByClassName(
									_portal.getClassName(classNameId)));
					});
			}
		};
	}

	private AssetType[] _getAssetTypes(
		AssetVocabularySettingsHelper assetVocabularySettingsHelper,
		long groupId) {

		long[] classNameIds = assetVocabularySettingsHelper.getClassNameIds();

		if (ArrayUtil.isEmpty(classNameIds)) {
			return new AssetType[0];
		}

		AssetType[] assetTypes = new AssetType[classNameIds.length];

		long[] classTypePKs = assetVocabularySettingsHelper.getClassTypePKs();
		long[] requiredClassNameIds =
			assetVocabularySettingsHelper.getRequiredClassNameIds();

		for (int i = 0; i < classNameIds.length; i++) {
			long classNameId = classNameIds[i];
			long classTypePK = classTypePKs[i];

			assetTypes[i] = _getAssetType(
				groupId, classNameId, classTypePK, requiredClassNameIds);
		}

		return assetTypes;
	}

	private String _getAvailableAssetTypes(
		List<AssetRendererFactory<?>> categorizableAssetRenderFactories) {

		List assetTypes = ListUtils.union(
			transform(
				categorizableAssetRenderFactories,
				assetRenderedFactory -> {
					String className = assetRenderedFactory.getClassName();

					if (_classNameToAssetTypeTypes.containsKey(className)) {
						AssetTypeType assetTypeType =
							_classNameToAssetTypeTypes.get(className);

						return assetTypeType.toString();
					}

					return _getModelResource(assetRenderedFactory);
				}),
			Collections.singletonList(
				AssetTypeType.ALL_ASSET_TYPES.toString()));

		return Arrays.toString(assetTypes.toArray());
	}

	private long _getClassNameId(String type) {
		AssetTypeType assetTypeType = AssetTypeType.create(type);

		if (Objects.equals(AssetTypeType.ALL_ASSET_TYPES, assetTypeType)) {
			return AssetCategoryConstants.ALL_CLASS_NAME_ID;
		}

		List<AssetRendererFactory<?>> categorizableAssetRenderFactories =
			ListUtil.filter(
				AssetRendererFactoryRegistryUtil.getAssetRendererFactories(
					contextCompany.getCompanyId()),
				AssetRendererFactory::isCategorizable);

		Stream<AssetRendererFactory<?>> stream =
			categorizableAssetRenderFactories.stream();

		Optional<AssetRendererFactory<?>> assetRendererFactoryOptional =
			stream.filter(
				assetRendererFactory -> type.equals(
					_getModelResource(assetRendererFactory))
			).findFirst();

		String className = assetRendererFactoryOptional.map(
			AssetRendererFactory::getClassName
		).orElse(
			_assetTypeTypeToClassNames.get(assetTypeType)
		);

		if (className == null) {
			throw new BadRequestException(
				StringBundler.concat(
					"Asset type ", type,
					" not available, the supported asset types are: ",
					_getAvailableAssetTypes(
						categorizableAssetRenderFactories)));
		}

		return _portal.getClassNameId(className);
	}

	private long _getClassTypePK(long classNameId, String subtype, long groupId)
		throws Exception {

		if (Objects.equals("AllAssetSubtypes", subtype) ||
			(classNameId == AssetCategoryConstants.ALL_CLASS_NAME_ID) ||
			(subtype == null)) {

			return AssetCategoryConstants.ALL_CLASS_TYPE_PK;
		}

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				_portal.getClassName(classNameId));

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

	private String _getModelResource(
		AssetRendererFactory<?> assetRendererFactory) {

		return ResourceActionsUtil.getModelResource(
			contextAcceptLanguage.getPreferredLocale(),
			assetRendererFactory.getClassName());
	}

	private String _getSettings(AssetType[] assetTypes, long groupId)
		throws Exception {

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

			long classNameId = _getClassNameId(assetType.getType());

			classNameIds[i] = classNameId;

			classTypePKs[i] = _getClassTypePK(
				classNameId, assetType.getSubtype(), groupId);

			requiredClassNameIds[i] = assetType.getRequired();
		}

		assetVocabularySettingsHelper.setClassNameIdsAndClassTypePKs(
			classNameIds, classTypePKs, requiredClassNameIds);

		assetVocabularySettingsHelper.setMultiValued(true);

		return assetVocabularySettingsHelper.toString();
	}

	private TaxonomyVocabulary _toTaxonomyVocabulary(
			AssetVocabulary assetVocabulary)
		throws Exception {

		return new TaxonomyVocabulary() {
			{
				assetTypes = _getAssetTypes(
					new AssetVocabularySettingsHelper(
						assetVocabulary.getSettings()),
					assetVocabulary.getGroupId());
				availableLanguages = LocaleUtil.toW3cLanguageIds(
					assetVocabulary.getAvailableLanguageIds());
				creator = CreatorUtil.toCreator(
					_portal,
					_userLocalService.getUser(assetVocabulary.getUserId()));
				dateCreated = assetVocabulary.getCreateDate();
				dateModified = assetVocabulary.getModifiedDate();
				description = assetVocabulary.getDescription(
					contextAcceptLanguage.getPreferredLocale());
				id = assetVocabulary.getVocabularyId();
				name = assetVocabulary.getTitle(
					contextAcceptLanguage.getPreferredLocale());

				numberOfTaxonomyCategories = Optional.ofNullable(
					assetVocabulary.getCategories()
				).map(
					List::size
				).orElse(
					0
				);

				siteId = assetVocabulary.getGroupId();
			}
		};
	}

	private static final Map<AssetTypeType, String> _assetTypeTypeToClassNames =
		new HashMap<AssetTypeType, String>() {
			{
				put(AssetTypeType.WEB_SITE, Group.class.getName());
				put(AssetTypeType.WEB_PAGE, Layout.class.getName());
				put(AssetTypeType.ORGANIZATION, Organization.class.getName());
				put(AssetTypeType.USER_ACCOUNT, User.class.getName());
				put(
					AssetTypeType.BLOG_POSTING,
					"com.liferay.blogs.model.BlogsEntry");
				put(
					AssetTypeType.DOCUMENT,
					"com.liferay.document.library.kernel.model.DLFileEntry");
				put(
					AssetTypeType.KNOWLEDGE_BASE_ARTICLE,
					"com.liferay.knowledge.base.model.KBArticle");
				put(
					AssetTypeType.STRUCTURED_CONTENT,
					"com.liferay.journal.model.JournalArticle");
				put(AssetTypeType.WIKI_PAGE, "com.liferay.wiki.model.WikiPage");
			}
		};
	private static final Map<String, AssetTypeType> _classNameToAssetTypeTypes =
		MapUtils.invertMap(_assetTypeTypeToClassNames);
	private static final EntityModel _entityModel = new VocabularyEntityModel();

	@Reference
	private AssetVocabularyService _assetVocabularyService;

	@Context
	private HttpServletResponse _httpServletResponse;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

	private enum AssetTypeType {

		ALL_ASSET_TYPES("AllAssetTypes"), BLOG_POSTING("BlogPosting"),
		DOCUMENT("Document"), KNOWLEDGE_BASE_ARTICLE("KnowledgeBaseArticle"),
		ORGANIZATION("Organization"), STRUCTURED_CONTENT("StructuredContent"),
		USER_ACCOUNT("UserAccount"), WEB_PAGE("WebPage"), WEB_SITE("WebSite"),
		WIKI_PAGE("WikiPage");

		public static AssetTypeType create(String value) {
			for (AssetTypeType assetTypeType : values()) {
				if (Objects.equals(assetTypeType.getValue(), value)) {
					return assetTypeType;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		public String toString() {
			return _value;
		}

		private AssetTypeType(String value) {
			_value = value;
		}

		private final String _value;

	}

}