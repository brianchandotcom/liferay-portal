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

package com.liferay.change.tracking.rest.internal.resource.v1_0;

import com.liferay.change.tracking.constants.CTActionKeys;
import com.liferay.change.tracking.constants.CTDestinationNames;
import com.liferay.change.tracking.mapping.CTMappingTableInfo;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.rest.dto.v1_0.Publication;
import com.liferay.change.tracking.rest.dto.v1_0.Status;
import com.liferay.change.tracking.rest.internal.odata.entity.v1_0.PublicationEntityModel;
import com.liferay.change.tracking.rest.internal.util.v1_0.PublishUtil;
import com.liferay.change.tracking.rest.resource.v1_0.PublicationResource;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.service.CTPreferencesService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.util.PropsValues;
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
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author David Truong
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/publication.properties",
	scope = ServiceScope.PROTOTYPE, service = PublicationResource.class
)
@CTAware
public class PublicationResourceImpl extends BasePublicationResourceImpl {

	@CTAware(onProduction = true)
	@Override
	public void deletePublication(Long ctCollectionId) throws PortalException {
		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctCollectionId);

		if (ctCollection != null) {
			_ctCollectionService.deleteCTCollection(ctCollection);
		}
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public Publication getPublication(Long ctCollectionId) throws Exception {
		return _toPublication(
			_ctCollectionLocalService.getCTCollection(ctCollectionId));
	}

	@Override
	public Page<Publication> getPublicationsPage(
			Integer[] statuses, String search, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> booleanQuery.getPreBooleanFilter(), null,
			CTCollection.class.getName(), search, pagination,
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
			document -> _toPublication(
				_ctCollectionLocalService.fetchCTCollection(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@CTAware(onProduction = true)
	@Override
	public Publication postPublication(Publication publication)
		throws Exception {

		return _toPublication(
			_ctCollectionService.addCTCollection(
				contextCompany.getCompanyId(), contextUser.getUserId(),
				publication.getName(), publication.getDescription()));
	}

	@CTAware(onProduction = true)
	@Override
	public void postPublicationCheckout(Long ctCollectionId)
		throws PortalException {

		_ctPreferencesService.checkoutCTCollection(
			contextCompany.getCompanyId(), contextUser.getUserId(),
			ctCollectionId);
	}

	@CTAware(onProduction = true)
	@Override
	public void postPublicationPublish(Long ctCollectionId)
		throws PortalException {

		_ctCollectionService.publishCTCollection(
			contextUser.getUserId(), ctCollectionId);
	}

	@CTAware(onProduction = true)
	@Override
	public void postPublicationSchedulePublish(
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

		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctCollectionId);

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
	public Response postPublicationsPageExportBatch(
			Integer[] status, String search, Sort[] sorts, String callbackURL,
			String contentType, String fieldNames)
		throws Exception {

		return null;
	}

	@CTAware(onProduction = true)
	@Override
	public Publication putPublication(
			Long ctCollectionId, Publication publication)
		throws Exception {

		return _toPublication(
			_ctCollectionService.updateCTCollection(
				contextUser.getUserId(), ctCollectionId, publication.getName(),
				publication.getDescription()));
	}

	private Date _getDateScheduled(CTCollection ctCollection) throws Exception {
		if (ctCollection.getStatus() != WorkflowConstants.STATUS_SCHEDULED) {
			return null;
		}

		SchedulerResponse schedulerResponse =
			_schedulerEngineHelper.getScheduledJob(
				String.valueOf(ctCollection.getCtCollectionId()),
				CTDestinationNames.CT_COLLECTION_SCHEDULED_PUBLISH,
				StorageType.PERSISTED);

		if (schedulerResponse == null) {
			return null;
		}

		return _schedulerEngineHelper.getStartTime(schedulerResponse);
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

	private Publication _toPublication(CTCollection ctCollection)
		throws Exception {

		return new Publication() {
			{
				actions = HashMapBuilder.put(
					"checkout",
					() -> {
						if ((ctCollection.getStatus() !=
								WorkflowConstants.STATUS_DRAFT) ||
							(ctCollection.getCtCollectionId() ==
								CTCollectionThreadLocal.getCTCollectionId())) {

							return null;
						}

						return addAction(
							ActionKeys.UPDATE, "postPublicationCheckout",
							CTCollection.class.getName(),
							ctCollection.getCtCollectionId());
					}
				).put(
					"delete",
					() -> addAction(
						ActionKeys.DELETE, "deletePublication",
						CTCollection.class.getName(),
						ctCollection.getCtCollectionId())
				).put(
					"get",
					addAction(
						ActionKeys.VIEW, "getPublication",
						CTCollection.class.getName(),
						ctCollection.getCtCollectionId())
				).put(
					"permissions",
					() -> {
						if (ctCollection.getStatus() !=
								WorkflowConstants.STATUS_DRAFT) {

							return null;
						}

						return addAction(
							ActionKeys.PERMISSIONS, "patchPublication",
							CTCollection.class.getName(),
							ctCollection.getCtCollectionId());
					}
				).put(
					"publish",
					() -> {
						if (!_isPublishEnabled(
								ctCollection.getCtCollectionId())) {

							return null;
						}

						return addAction(
							CTActionKeys.PUBLISH, "postPublicationPublish",
							CTCollection.class.getName(),
							ctCollection.getCtCollectionId());
					}
				).put(
					"schedule",
					() -> {
						if (!_isPublishEnabled(
								ctCollection.getCtCollectionId()) ||
							!PropsValues.SCHEDULER_ENABLED) {

							return null;
						}

						return addAction(
							CTActionKeys.PUBLISH,
							"postPublicationSchedulePublish",
							CTCollection.class.getName(),
							ctCollection.getCtCollectionId());
					}
				).put(
					"update",
					() -> addAction(
						ActionKeys.UPDATE, "putPublication",
						CTCollection.class.getName(),
						ctCollection.getCtCollectionId())
				).build();
				dateCreated = ctCollection.getCreateDate();
				dateModified = ctCollection.getModifiedDate();
				dateScheduled = _getDateScheduled(ctCollection);
				description = ctCollection.getDescription();
				id = ctCollection.getCtCollectionId();
				name = ctCollection.getName();
				ownerName = ctCollection.getUserName();
				status = _toStatus(ctCollection.getStatus());
			}
		};
	}

	private Status _toStatus(int status) throws Exception {
		String statusLabel;

		if (status == WorkflowConstants.STATUS_APPROVED) {
			statusLabel = "published";
		}
		else if (status == WorkflowConstants.STATUS_EXPIRED) {
			statusLabel = "out-of-date";
		}
		else if (status == WorkflowConstants.STATUS_DRAFT) {
			statusLabel = "in-progress";
		}
		else if (status == WorkflowConstants.STATUS_DENIED) {
			statusLabel = "failed";
		}
		else if (status == WorkflowConstants.STATUS_SCHEDULED) {
			statusLabel = "scheduled";
		}
		else {
			statusLabel = StringPool.BLANK;
		}

		return new Status() {
			{
				code = status;
				label = statusLabel;
				label_i18n = _language.get(
					contextCompany.getLocale(), statusLabel);
			}
		};
	}

	private static final EntityModel _entityModel =
		new PublicationEntityModel();

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTCollectionService _ctCollectionService;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private CTPreferencesService _ctPreferencesService;

	@Reference
	private Language _language;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

	@Reference
	private UserLocalService _userLocalService;

}