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

package com.liferay.change.tracking.service.impl;

import com.liferay.change.tracking.exception.CTCollectionDescriptionException;
import com.liferay.change.tracking.exception.CTCollectionNameException;
import com.liferay.change.tracking.internal.CTPersistenceHelperThreadLocal;
import com.liferay.change.tracking.internal.CTServiceRegistry;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.change.tracking.service.base.CTCollectionLocalServiceBaseImpl;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Daniel Kocsis
 */
@Component(
	property = "model.class.name=com.liferay.change.tracking.model.CTCollection",
	service = AopService.class
)
public class CTCollectionLocalServiceImpl
	extends CTCollectionLocalServiceBaseImpl {

	@Override
	public CTCollection addCTCollection(
			long userId, String name, String description)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		_validate(name, description);

		long ctCollectionId = counterLocalService.increment();

		CTCollection ctCollection = ctCollectionPersistence.create(
			ctCollectionId);

		ctCollection.setCompanyId(user.getCompanyId());
		ctCollection.setUserId(user.getUserId());
		ctCollection.setUserName(user.getFullName());

		ctCollection.setName(name);
		ctCollection.setDescription(description);
		ctCollection.setStatus(WorkflowConstants.STATUS_DRAFT);

		return ctCollectionPersistence.update(ctCollection);
	}

	@Override
	public void deleteCompanyCTCollections(long companyId)
		throws PortalException {

		List<CTCollection> ctCollections =
			ctCollectionPersistence.findByCompanyId(companyId);

		for (CTCollection ctCollection : ctCollections) {
			ctCollectionLocalService.deleteCTCollection(ctCollection);
		}
	}

	@Override
	public CTCollection deleteCTCollection(CTCollection ctCollection)
		throws PortalException {

		List<CTEntry> ctEntries = ctEntryPersistence.findByCTCollectionId(
			ctCollection.getCtCollectionId());

		Set<Long> modelClassNameIds = new HashSet<>();

		for (CTEntry ctEntry : ctEntries) {
			modelClassNameIds.add(ctEntry.getModelClassNameId());

			_ctEntryLocalService.deleteCTEntry(ctEntry);
		}

		for (long modelClassNameId : modelClassNameIds) {
			CTService<?> ctService = _ctServiceRegistry.getCTService(
				modelClassNameId);

			if (ctService != null) {
				_removeCTModels(ctService, ctCollection.getCtCollectionId());
			}
		}

		ctPreferencesPersistence.removeByCollectionId(
			ctCollection.getCtCollectionId());

		List<CTProcess> ctProcesses = ctProcessPersistence.findByCollectionId(
			ctCollection.getCtCollectionId());

		for (CTProcess ctProcess : ctProcesses) {
			_ctProcessLocalService.deleteCTProcess(ctProcess);
		}

		return ctCollectionPersistence.remove(ctCollection);
	}

	@Override
	public CTCollection fetchCTCollection(long companyId, String name) {
		return ctCollectionPersistence.fetchByC_N(companyId, name);
	}

	@Override
	public List<CTCollection> getCTCollections(
		long companyId, int status, int start, int end,
		OrderByComparator<CTCollection> orderByComparator) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return ctCollectionPersistence.findByCompanyId(
				companyId, start, end, orderByComparator);
		}

		return ctCollectionPersistence.findByC_S(
			companyId, status, start, end, orderByComparator);
	}

	@Override
	public List<CTCollection> getCTCollections(
		long companyId, QueryDefinition<CTCollection> queryDefinition) {

		if (queryDefinition == null) {
			return ctCollectionPersistence.findByCompanyId(companyId);
		}

		return ctCollectionPersistence.findByCompanyId(
			companyId, queryDefinition.getStart(), queryDefinition.getEnd(),
			queryDefinition.getOrderByComparator());
	}

	@Override
	public List<CTCollection> getCTCollections(
		long companyId, QueryDefinition<CTCollection> queryDefinition,
		boolean includeProduction) {

		if (includeProduction) {
			return ctCollectionLocalService.getCTCollections(
				companyId, queryDefinition);
		}

		DynamicQuery dynamicQuery = ctCollectionLocalService.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(companyId));

		boolean includeActive = GetterUtil.getBoolean(
			queryDefinition.getAttribute("includeActive"));

		if (!includeActive) {
			Property ctCollectionIdProperty = PropertyFactoryUtil.forName(
				"ctCollectionId");

			long activeCTCollectionId = GetterUtil.getLong(
				queryDefinition.getAttribute("activeCTCollectionId"));

			dynamicQuery.add(ctCollectionIdProperty.ne(activeCTCollectionId));
		}

		int status = queryDefinition.getStatus();

		if (status != WorkflowConstants.STATUS_ANY) {
			Property statusProperty = PropertyFactoryUtil.forName("status");

			if (queryDefinition.isExcludeStatus()) {
				dynamicQuery.add(statusProperty.ne(status));
			}
			else {
				dynamicQuery.add(statusProperty.eq(status));
			}
		}

		return ctCollectionLocalService.dynamicQuery(
			dynamicQuery, queryDefinition.getStart(), queryDefinition.getEnd(),
			queryDefinition.getOrderByComparator());
	}

	@Override
	public CTCollection updateCTCollection(
			long userId, long ctCollectionId, String name, String description)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		_validate(name, description);

		CTCollection ctCollection = ctCollectionPersistence.findByPrimaryKey(
			ctCollectionId);

		Date modifiedDate = new Date();

		ctCollection.setModifiedDate(modifiedDate);

		ctCollection.setName(name);
		ctCollection.setDescription(description);
		ctCollection.setStatusByUserId(user.getUserId());
		ctCollection.setStatusByUserName(user.getFullName());
		ctCollection.setStatusDate(modifiedDate);

		return ctCollectionPersistence.update(ctCollection);
	}

	@Override
	public CTCollection updateStatus(
			long userId, CTCollection ctCollection, int status)
		throws PortalException {

		Date modifiedDate = new Date();

		ctCollection.setModifiedDate(modifiedDate);

		ctCollection.setStatus(status);

		User user = userLocalService.getUser(userId);

		ctCollection.setStatusByUserId(user.getUserId());
		ctCollection.setStatusByUserName(user.getFullName());

		ctCollection.setStatusDate(modifiedDate);

		return ctCollectionPersistence.update(ctCollection);
	}

	private <T extends CTModel<T>> void _removeCTModels(
		CTService<T> ctService, long ctCollectionId) {

		try (SafeClosable safeClosable1 =
				CTPersistenceHelperThreadLocal.setEnabled(false);
			SafeClosable safeClosable2 =
				CTCollectionThreadLocal.setCTCollectionId(ctCollectionId)) {

			ctService.updateWithUnsafeFunction(
				ctPersistence -> {
					List<T> ctModels = ctPersistence.findByCTCollectionId(
						ctCollectionId);

					for (T ctModel : ctModels) {
						ctPersistence.removeCTModel(ctModel, true);
					}

					return null;
				});
		}
	}

	private void _validate(String name, String description)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new CTCollectionNameException();
		}

		int nameMaxLength = ModelHintsUtil.getMaxLength(
			CTCollection.class.getName(), "name");

		if (name.length() > nameMaxLength) {
			throw new CTCollectionNameException("Name is too long");
		}

		int descriptionMaxLength = ModelHintsUtil.getMaxLength(
			CTCollection.class.getName(), "description");

		if ((description != null) &&
			(description.length() > descriptionMaxLength)) {

			throw new CTCollectionDescriptionException(
				"Description is too long");
		}
	}

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private CTProcessLocalService _ctProcessLocalService;

	@Reference
	private CTServiceRegistry _ctServiceRegistry;

}