/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.rest.internal.resource.v1_0;

import com.liferay.change.tracking.constants.CTActionKeys;
import com.liferay.change.tracking.mapping.CTMappingTableInfo;
import com.liferay.change.tracking.rest.dto.v1_0.CTCollection;
import com.liferay.change.tracking.rest.internal.odata.entity.v1_0.CTCollectionEntityModel;
import com.liferay.change.tracking.rest.internal.util.v1_0.PublishUtil;
import com.liferay.change.tracking.rest.resource.v1_0.CTCollectionResource;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.service.CTPreferencesService;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author David Truong
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/ct-collection.properties",
	scope = ServiceScope.PROTOTYPE, service = CTCollectionResource.class
)
@CTAware(onProduction = true)
public class CTCollectionResourceImpl extends BaseCTCollectionResourceImpl {

	@Override
	public void deleteCTCollection(Long ctCollectionId) throws PortalException {
		com.liferay.change.tracking.model.CTCollection ctCollection =
			_ctCollectionLocalService.fetchCTCollection(ctCollectionId);

		if (ctCollection != null) {
			_ctCollectionService.deleteCTCollection(ctCollection);
		}
	}

	@Override
	public void deleteCTCollectionByExternalReferenceCode(
			String externalReferenceCode)
		throws PortalException {

		com.liferay.change.tracking.model.CTCollection ctCollection =
			_ctCollectionLocalService.fetchCTCollectionByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (ctCollection != null) {
			_ctCollectionService.deleteCTCollection(ctCollection);
		}
	}

	@Override
	public CTCollection getCTCollection(Long ctCollectionId) throws Exception {
		return _toCTCollection(ctCollectionId);
	}

	public CTCollection getCTCollectionByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		return _toCTCollection(externalReferenceCode);
	}

	@Override
	public Page<CTCollection> getCTCollectionsPage(
			Integer[] statuses, String search, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> booleanQuery.getPreBooleanFilter(), null,
			com.liferay.change.tracking.model.CTCollection.class.getName(),
			search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute("statuses", statuses);
				searchContext.setCompanyId(contextCompany.getCompanyId());

				if (Validator.isNotNull(search)) {
					searchContext.setKeywords(search);
				}
			},
			sorts,
			document -> _toCTCollection(
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public CTCollection patchCTCollectionByExternalReferenceCode(
			String externalReferenceCode, CTCollection ctCollection)
		throws Exception {

		com.liferay.change.tracking.model.CTCollection ctCollectionModel =
			_ctCollectionLocalService.fetchCTCollectionByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		return _toCTCollection(
			_ctCollectionService.updateCTCollection(
				contextUser.getUserId(), ctCollectionModel.getCtCollectionId(),
				ctCollection.getName(), ctCollection.getDescription()));
	}

	@Override
	public CTCollection postCTCollection(CTCollection ctCollection)
		throws Exception {

		return _toCTCollection(
			_ctCollectionService.addCTCollection(
				ctCollection.getExternalReferenceCode(),
				contextCompany.getCompanyId(), contextUser.getUserId(), 0,
				ctCollection.getName(), ctCollection.getDescription()));
	}

	@Override
	public void postCTCollectionCheckout(Long ctCollectionId)
		throws PortalException {

		_ctPreferencesService.checkoutCTCollection(
			contextCompany.getCompanyId(), contextUser.getUserId(),
			ctCollectionId);
	}

	@Override
	public void postCTCollectionPublish(Long ctCollectionId)
		throws PortalException {

		_ctCollectionService.publishCTCollection(
			contextUser.getUserId(), ctCollectionId);
	}

	@Override
	public void postCTCollectionSchedulePublish(
			Long ctCollectionId, Date publishDate)
		throws PortalException {

		if (publishDate == null) {
			_ctCollectionService.publishCTCollection(
				contextUser.getUserId(), ctCollectionId);

			return;
		}

		Date currentDate = new Date(System.currentTimeMillis());

		if (!publishDate.after(currentDate)) {
			throw new IllegalArgumentException(
				"The publish time must be in the future");
		}

		com.liferay.change.tracking.model.CTCollection ctCollection =
			_ctCollectionLocalService.fetchCTCollection(ctCollectionId);

		if (ctCollection.getStatus() == WorkflowConstants.STATUS_SCHEDULED) {
			PublishUtil.unschedulePublish(
				ctCollectionId, _ctCollectionLocalService,
				_schedulerEngineHelper);
		}

		PublishUtil.schedulePublish(
			ctCollectionId, _ctCollectionLocalService,
			_ctPreferencesLocalService, _schedulerEngineHelper, publishDate,
			_triggerFactory, contextUser.getUserId());
	}

	@Override
	public Response postCTCollectionsPageExportBatch(
		Integer[] status, String search, Sort[] sorts, String callbackURL,
		String contentType, String fieldNames) {

		return null;
	}

	@Override
	public CTCollection putCTCollection(
			Long ctCollectionId, CTCollection ctCollection)
		throws Exception {

		return _toCTCollection(
			_ctCollectionService.updateCTCollection(
				contextUser.getUserId(), ctCollectionId, ctCollection.getName(),
				ctCollection.getDescription()));
	}

	private DefaultDTOConverterContext _getDTOConverterContext(
			com.liferay.change.tracking.model.CTCollection ctCollection)
		throws Exception {

		return new DefaultDTOConverterContext(
			contextAcceptLanguage.isAcceptAllLanguages(),
			HashMapBuilder.put(
				"checkout",
				() -> {
					if ((ctCollection.getStatus() !=
							WorkflowConstants.STATUS_DRAFT) ||
						(ctCollection.getCtCollectionId() ==
							CTCollectionThreadLocal.getCTCollectionId())) {

						return null;
					}

					return addAction(
						ActionKeys.UPDATE, ctCollection.getCtCollectionId(),
						"postCTCollectionCheckout",
						_ctCollectionModelResourcePermission);
				}
			).put(
				"delete",
				() -> addAction(
					ActionKeys.DELETE, ctCollection.getCtCollectionId(),
					"deleteCTCollection", _ctCollectionModelResourcePermission)
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, ctCollection.getCtCollectionId(),
					"getCTCollection", _ctCollectionModelResourcePermission)
			).put(
				"permissions",
				() -> {
					if (ctCollection.getStatus() !=
							WorkflowConstants.STATUS_DRAFT) {

						return null;
					}

					return addAction(
						ActionKeys.PERMISSIONS,
						ctCollection.getCtCollectionId(), "patchCTCollection",
						_ctCollectionModelResourcePermission);
				}
			).put(
				"publish",
				() -> {
					if (!_isPublishEnabled(ctCollection.getCtCollectionId())) {
						return null;
					}

					return addAction(
						CTActionKeys.PUBLISH, ctCollection.getCtCollectionId(),
						"postCTCollectionPublish",
						_ctCollectionModelResourcePermission);
				}
			).put(
				"schedule",
				() -> {
					if (!_isPublishEnabled(ctCollection.getCtCollectionId()) ||
						!PropsValues.SCHEDULER_ENABLED) {

						return null;
					}

					return addAction(
						CTActionKeys.PUBLISH, ctCollection.getCtCollectionId(),
						"postCTCollectionSchedulePublish",
						_ctCollectionModelResourcePermission);
				}
			).put(
				"update",
				() -> addAction(
					ActionKeys.UPDATE, ctCollection.getCtCollectionId(),
					"putCTCollection", _ctCollectionModelResourcePermission)
			).build(),
			null, contextHttpServletRequest, ctCollection.getCtCollectionId(),
			contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
			contextUser);
	}

	private boolean _isPublishEnabled(long ctCollectionId) {
		int count = _ctEntryLocalService.getCTCollectionCTEntriesCount(
			ctCollectionId);

		if (count > 0) {
			return true;
		}

		List<CTMappingTableInfo> mappingTableInfos =
			_ctCollectionLocalService.getCTMappingTableInfos(ctCollectionId);

		if (!mappingTableInfos.isEmpty()) {
			return true;
		}

		return false;
	}

	private CTCollection _toCTCollection(
			com.liferay.change.tracking.model.CTCollection ctCollection)
		throws Exception {

		if (ctCollection == null) {
			return null;
		}

		return _toCTCollection(ctCollection.getCtCollectionId());
	}

	private CTCollection _toCTCollection(Long ctCollectionId) throws Exception {
		com.liferay.change.tracking.model.CTCollection ctCollection =
			_ctCollectionLocalService.getCTCollection(ctCollectionId);

		return _ctCollectionDTOConverter.toDTO(
			_getDTOConverterContext(ctCollection), ctCollection);
	}

	private CTCollection _toCTCollection(String externalReferenceCode)
		throws Exception {

		com.liferay.change.tracking.model.CTCollection ctCollection =
			_ctCollectionLocalService.getCTCollectionByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		return _ctCollectionDTOConverter.toDTO(
			_getDTOConverterContext(ctCollection), ctCollection);
	}

	private static final EntityModel _entityModel =
		new CTCollectionEntityModel();

	@Reference(
		target = "(component.name=com.liferay.change.tracking.rest.internal.dto.v1_0.converter.CTCollectionDTOConverter)"
	)
	private DTOConverter
		<com.liferay.change.tracking.model.CTCollection, CTCollection>
			_ctCollectionDTOConverter;

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.change.tracking.model.CTCollection)"
	)
	private volatile ModelResourcePermission
		<com.liferay.change.tracking.model.CTCollection>
			_ctCollectionModelResourcePermission;

	@Reference
	private CTCollectionService _ctCollectionService;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private CTPreferencesService _ctPreferencesService;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}