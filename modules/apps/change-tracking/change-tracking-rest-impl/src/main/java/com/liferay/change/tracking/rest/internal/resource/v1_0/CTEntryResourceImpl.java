/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.rest.internal.resource.v1_0;

import com.liferay.change.tracking.exception.NoSuchEntryException;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.rest.dto.v1_0.CTEntry;
import com.liferay.change.tracking.rest.internal.odata.entity.v1_0.CTEntryEntityModel;
import com.liferay.change.tracking.rest.resource.v1_0.CTEntryResource;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.spi.display.CTDisplayRendererRegistry;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleTable;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.sql.CTSQLModeThreadLocal;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author David Truong
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/ct-entry.properties",
	scope = ServiceScope.PROTOTYPE, service = CTEntryResource.class
)
public class CTEntryResourceImpl extends BaseCTEntryResourceImpl {

	@Override
	public Page<CTEntry> getCtCollectionCTEntriesPage(
			Long ctCollectionId, String search, Boolean showHideable,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> booleanQuery.getPreBooleanFilter(), filter,
			com.liferay.change.tracking.model.CTEntry.class.getName(), search,
			pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute("ctCollectionId", ctCollectionId);
				searchContext.setAttribute("showHideable", showHideable);
				searchContext.setCompanyId(contextCompany.getCompanyId());

				if (Validator.isNotNull(search)) {
					searchContext.setKeywords(search);
				}
			},
			sorts,
			document -> _toCTEntry(
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
	}

	@Override
	public CTEntry
			getCtCollectionCTEntryByModelClassNameByModelClassPkModelClassPK(
				Long ctCollectionId, Long modelClassNameId, Long modelClassPK)
		throws Exception {

		com.liferay.change.tracking.model.CTEntry ctEntry;

		if (modelClassNameId == _classNameLocalService.getClassNameId(
				JournalArticle.class.getName())) {

			JournalArticle journalArticle =
				_journalArticleLocalService.getJournalArticle(modelClassPK);

			List<Long> journalArticleClassPK = _ctEntryLocalService.dslQuery(
				DSLQueryFactoryUtil.select(
					JournalArticleTable.INSTANCE.id
				).from(
					JournalArticleTable.INSTANCE
				).where(
					JournalArticleTable.INSTANCE.resourcePrimKey.eq(
						journalArticle.getResourcePrimKey()
					).and(
						JournalArticleTable.INSTANCE.ctCollectionId.eq(
							ctCollectionId)
					)
				));

			if (journalArticleClassPK.size() != 1) {
				throw new Exception(
					StringBundler.concat(
						"More than 1 result received from query for entity ",
						"with modelClassNamePK: ", modelClassPK,
						" of modelClassNameId: ", modelClassNameId, " (",
						_classNameLocalService.getClassName(
							modelClassNameId
						).getModelClassName(),
						") in ctCollectionId: ", ctCollectionId));
			}

			ctEntry = _ctEntryLocalService.fetchCTEntry(
				ctCollectionId, modelClassNameId, journalArticleClassPK.get(0));
		}
		else {
			ctEntry = _ctEntryLocalService.fetchCTEntry(
				ctCollectionId, modelClassNameId, modelClassPK);
		}

		if (ctEntry == null) {
			throw new NoSuchEntryException(
				StringBundler.concat(
					"No change tracking entry exists with change tracking ",
					"collection ID ", ctCollectionId, ", model class name ID ",
					modelClassNameId, ", and model class PK ", modelClassPK));
		}

		return _ctEntryDTOConverter.toDTO(
			_getDTOConverterContext(ctEntry), ctEntry);
	}

	@Override
	public CTEntry getCTEntry(Long ctEntryId) throws Exception {
		return _toCTEntry(ctEntryId);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	private <T extends BaseModel<T>> DefaultDTOConverterContext
			_getDTOConverterContext(
				com.liferay.change.tracking.model.CTEntry ctEntry)
		throws Exception {

		return new DefaultDTOConverterContext(
			contextAcceptLanguage.isAcceptAllLanguages(),
			HashMapBuilder.put(
				"get",
				addAction(
					ActionKeys.VIEW, ctEntry.getCtCollectionId(), "getCTEntry",
					_ctCollectionModelResourcePermission)
			).put(
				"move-changes",
				() -> {
					CTSQLModeThreadLocal.CTSQLMode ctSQLMode =
						_ctDisplayRendererRegistry.getCTSQLMode(
							ctEntry.getCtCollectionId(), ctEntry);

					T model = _ctDisplayRendererRegistry.fetchCTModel(
						ctEntry.getCtCollectionId(), ctSQLMode,
						ctEntry.getModelClassNameId(),
						ctEntry.getModelClassPK());

					if (!FeatureFlagManagerUtil.isEnabled(
							contextCompany.getCompanyId(), "LPS-171364") ||
						(model == null) ||
						_ctDisplayRendererRegistry.isHideable(
							model, ctEntry.getModelClassNameId())) {

						return null;
					}

					return addAction(
						ActionKeys.VIEW, ctEntry.getCtCollectionId(),
						"getCTEntry", _ctCollectionModelResourcePermission);
				}
			).put(
				"view-discard",
				addAction(
					ActionKeys.VIEW, ctEntry.getCtCollectionId(), "getCTEntry",
					_ctCollectionModelResourcePermission)
			).build(),
			null, contextHttpServletRequest, ctEntry.getCtCollectionId(),
			contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
			contextUser);
	}

	private CTEntry _toCTEntry(Long ctEntryId) throws Exception {
		com.liferay.change.tracking.model.CTEntry ctEntry =
			_ctEntryLocalService.getCTEntry(ctEntryId);

		return _ctEntryDTOConverter.toDTO(
			_getDTOConverterContext(ctEntry), ctEntry);
	}

	private static final EntityModel _entityModel = new CTEntryEntityModel();

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.change.tracking.model.CTCollection)"
	)
	private volatile ModelResourcePermission<CTCollection>
		_ctCollectionModelResourcePermission;

	@Reference
	private CTDisplayRendererRegistry _ctDisplayRendererRegistry;

	@Reference(
		target = "(component.name=com.liferay.change.tracking.rest.internal.dto.v1_0.converter.CTEntryDTOConverter)"
	)
	private DTOConverter<com.liferay.change.tracking.model.CTEntry, CTEntry>
		_ctEntryDTOConverter;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

}