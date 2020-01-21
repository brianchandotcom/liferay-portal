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

package com.liferay.headless.admin.taxonomy.internal.resource.v1_0;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.headless.admin.taxonomy.dto.v1_0.AssetType;
import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyVocabulary;
import com.liferay.headless.admin.taxonomy.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.admin.taxonomy.internal.odata.entity.v1_0.VocabularyEntityModel;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.headless.common.spi.service.context.ServiceContextUtil;
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
import com.liferay.portal.kernel.util.HashMapBuilder;
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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
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
			assetVocabulary.getDefaultLanguageId(), contextHttpServletResponse,
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

							return "AllAssetTypes";
						}

						String assetTypeType = _classNameToAssetTypeTypes.get(
							_portal.getClassName(classNameId));

						if (assetTypeType != null) {
							return assetTypeType;
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

		List<String> assetTypes = ListUtils.union(
			transform(
				categorizableAssetRenderFactories,
				assetRenderedFactory -> {
					String assetTypeType = _classNameToAssetTypeTypes.get(
						assetRenderedFactory.getClassName());

					if (assetTypeType != null) {
						return assetTypeType;
					}

					return _getModelResource(assetRenderedFactory);
				}),
			Collections.singletonList("AllAssetTypes"));

		return Arrays.toString(assetTypes.toArray());
	}

	private long _getClassNameId(String assetTypeType) {
		if (Objects.equals(assetTypeType, "AllAssetTypes")) {
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
				assetRendererFactory -> assetTypeType.equals(
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
					"Asset type ", assetTypeType,
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

		boolean acceptAllLanguages =
			contextAcceptLanguage.isAcceptAllLanguages();

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
				description_i18n = LocalizedMapUtil.getLocalizedMap(
					acceptAllLanguages, assetVocabulary.getDescriptionMap());
				id = assetVocabulary.getVocabularyId();
				name = assetVocabulary.getTitle(
					contextAcceptLanguage.getPreferredLocale());
				name_i18n = LocalizedMapUtil.getLocalizedMap(
					acceptAllLanguages, assetVocabulary.getTitleMap());
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

	private static final Map<String, String> _assetTypeTypeToClassNames =
		HashMapBuilder.put(
			"BlogPosting", "com.liferay.blogs.model.BlogsEntry"
		).put(
			"Document", "com.liferay.document.library.kernel.model.DLFileEntry"
		).put(
			"KnowledgeBaseArticle", "com.liferay.knowledge.base.model.KBArticle"
		).put(
			"Organization", Organization.class.getName()
		).put(
			"StructuredContent", "com.liferay.journal.model.JournalArticle"
		).put(
			"UserAccount", User.class.getName()
		).put(
			"WebPage", Layout.class.getName()
		).put(
			"WebSite", Group.class.getName()
		).put(
			"WikiPage", "com.liferay.wiki.model.WikiPage"
		).build();
	private static final Map<String, String> _classNameToAssetTypeTypes =
		MapUtils.invertMap(_assetTypeTypeToClassNames);
	private static final EntityModel _entityModel = new VocabularyEntityModel();

	@Reference
	private AssetVocabularyService _assetVocabularyService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}