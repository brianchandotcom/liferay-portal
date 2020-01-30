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

import com.liferay.change.tracking.conflict.ConflictInfo;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.exception.CTCollectionDescriptionException;
import com.liferay.change.tracking.exception.CTCollectionNameException;
import com.liferay.change.tracking.internal.CTServiceCopier;
import com.liferay.change.tracking.internal.CTServiceRegistry;
import com.liferay.change.tracking.internal.CTTableMapperHelper;
import com.liferay.change.tracking.internal.conflict.CTConflictChecker;
import com.liferay.change.tracking.internal.conflict.ConstraintResolverConflictInfo;
import com.liferay.change.tracking.internal.resolver.ConstraintResolverKey;
import com.liferay.change.tracking.model.CTAutoResolutionInfo;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.resolver.ConstraintResolver;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.change.tracking.service.base.CTCollectionLocalServiceBaseImpl;
import com.liferay.change.tracking.service.persistence.CTAutoResolutionInfoPersistence;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Daniel Kocsis
 * @author Preston Crary
 */
@Component(
	property = "model.class.name=com.liferay.change.tracking.model.CTCollection",
	service = AopService.class
)
public class CTCollectionLocalServiceImpl
	extends CTCollectionLocalServiceBaseImpl {

	@Override
	public CTCollection addCTCollection(
			long companyId, long userId, String name, String description)
		throws PortalException {

		_validate(name, description);

		long ctCollectionId = counterLocalService.increment(
			CTCollection.class.getName());

		CTCollection ctCollection = ctCollectionPersistence.create(
			ctCollectionId);

		ctCollection.setCompanyId(companyId);
		ctCollection.setUserId(userId);
		ctCollection.setName(name);
		ctCollection.setDescription(description);
		ctCollection.setStatus(WorkflowConstants.STATUS_DRAFT);

		return ctCollectionPersistence.update(ctCollection);
	}

	@Override
	public Map<Long, List<ConflictInfo>> checkConflicts(
			CTCollection ctCollection)
		throws PortalException {

		Map<Long, List<ConflictInfo>> conflictInfoMap = new HashMap<>();

		List<CTEntry> ctEntries = ctEntryPersistence.findByCTCollectionId(
			ctCollection.getCtCollectionId());

		Map<Long, CTConflictChecker<?>> ctConflictCheckers = new HashMap<>();

		for (CTEntry ctEntry : ctEntries) {
			CTConflictChecker<?> ctConflictChecker =
				ctConflictCheckers.computeIfAbsent(
					ctEntry.getModelClassNameId(),
					modelClassNameId -> {
						CTService<?> ctService =
							_ctServiceRegistry.getCTService(modelClassNameId);

						if (ctService == null) {
							throw new SystemException(
								StringBundler.concat(
									"Unable to check conflicts for ",
									ctCollection, " because service for ",
									modelClassNameId, " is missing"));
						}

						return new CTConflictChecker<>(
							ctService, _serviceTrackerMap,
							ctCollection.getCtCollectionId(),
							CTConstants.CT_COLLECTION_ID_PRODUCTION);
					});

			ctConflictChecker.addCTEntry(ctEntry);
		}

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					ctCollection.getCtCollectionId())) {

			for (Map.Entry<Long, CTConflictChecker<?>> entry :
					ctConflictCheckers.entrySet()) {

				CTConflictChecker<?> ctConflictChecker = entry.getValue();

				List<ConflictInfo> conflictInfos = ctConflictChecker.check();

				if (!conflictInfos.isEmpty()) {
					conflictInfoMap.put(entry.getKey(), conflictInfos);
				}
			}
		}

		// Exclude created CTAutoResolutionInfos

		List<CTAutoResolutionInfo> ctAutoResolutionInfos =
			_ctAutoResolutionInfoPersistence.findByCTCollectionId(
				ctCollection.getCtCollectionId());

		for (Map.Entry<Long, List<ConflictInfo>> entry :
				conflictInfoMap.entrySet()) {

			for (ConflictInfo conflictInfo : entry.getValue()) {
				if (!conflictInfo.isResolved()) {
					continue;
				}

				CTAutoResolutionInfo ctAutoResolutionInfo =
					_ctAutoResolutionInfoPersistence.create(
						counterLocalService.increment(
							CTAutoResolutionInfo.class.getName()));

				ctAutoResolutionInfo.setCompanyId(ctCollection.getCompanyId());
				ctAutoResolutionInfo.setCreateDate(new Date());
				ctAutoResolutionInfo.setCtCollectionId(
					ctCollection.getCtCollectionId());
				ctAutoResolutionInfo.setModelClassNameId(entry.getKey());
				ctAutoResolutionInfo.setSourceModelClassPK(
					conflictInfo.getSourcePrimaryKey());
				ctAutoResolutionInfo.setTargetModelClassPK(
					conflictInfo.getTargetPrimaryKey());

				if (conflictInfo instanceof ConstraintResolverConflictInfo) {
					ConstraintResolverConflictInfo
						constraintResolverConflictInfo =
							(ConstraintResolverConflictInfo)conflictInfo;

					ConstraintResolver<?> constraintResolver =
						constraintResolverConflictInfo.getConstraintResolver();

					ctAutoResolutionInfo.setConflictIdentifier(
						StringUtil.merge(
							constraintResolver.getUniqueIndexColumnNames(),
							StringPool.COMMA));

					constraintResolverConflictInfo.setCtAutoResolutionInfoId(
						ctAutoResolutionInfo.getCtAutoResolutionInfoId());
				}

				_ctAutoResolutionInfoPersistence.update(ctAutoResolutionInfo);
			}
		}

		for (CTAutoResolutionInfo ctAutoResolutionInfo :
				ctAutoResolutionInfos) {

			List<ConflictInfo> conflictInfos = conflictInfoMap.computeIfAbsent(
				ctAutoResolutionInfo.getModelClassNameId(),
				key -> new ArrayList<>());

			ClassName className = _classNameLocalService.getClassName(
				ctAutoResolutionInfo.getModelClassNameId());

			List<String> uniqueIndexes = StringUtil.split(
				ctAutoResolutionInfo.getConflictIdentifier(), CharPool.COMMA);

			ConstraintResolver<?> constraintResolver =
				_serviceTrackerMap.getService(
					new ConstraintResolverKey(
						className.getValue(),
						uniqueIndexes.toArray(new String[0])));

			if (constraintResolver != null) {
				ConstraintResolverConflictInfo constraintResolverConflictInfo =
					new ConstraintResolverConflictInfo(
						constraintResolver,
						ctAutoResolutionInfo.getSourceModelClassPK(),
						ctAutoResolutionInfo.getTargetModelClassPK(), true);

				constraintResolverConflictInfo.setCtAutoResolutionInfoId(
					ctAutoResolutionInfo.getCtAutoResolutionInfoId());

				conflictInfos.add(constraintResolverConflictInfo);
			}
		}

		return conflictInfoMap;
	}

	@Override
	public void deleteCompanyCTCollections(long companyId) {
		List<CTCollection> ctCollections =
			ctCollectionPersistence.findByCompanyId(companyId);

		for (CTCollection ctCollection : ctCollections) {
			deleteCTCollection(ctCollection);
		}
	}

	@Override
	public void deleteCTAutoResolutionInfo(long ctAutoResolutionInfoId) {
		CTAutoResolutionInfo ctAutoResolutionInfo =
			_ctAutoResolutionInfoPersistence.fetchByPrimaryKey(
				ctAutoResolutionInfoId);

		if (ctAutoResolutionInfo != null) {
			_ctAutoResolutionInfoPersistence.remove(ctAutoResolutionInfo);
		}
	}

	@Override
	public CTCollection deleteCTCollection(CTCollection ctCollection) {
		_ctServiceRegistry.onBeforeRemove(ctCollection.getCtCollectionId());

		try {
			for (CTTableMapperHelper ctTableMapperHelper :
					_ctServiceRegistry.getCTTableMapperHelpers()) {

				ctTableMapperHelper.delete(ctCollection.getCtCollectionId());
			}
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}

		List<CTEntry> ctEntries = ctEntryPersistence.findByCTCollectionId(
			ctCollection.getCtCollectionId());

		Set<Long> modelClassNameIds = new HashSet<>();

		for (CTEntry ctEntry : ctEntries) {
			modelClassNameIds.add(ctEntry.getModelClassNameId());
		}

		for (long modelClassNameId : modelClassNameIds) {
			CTService<?> ctService = _ctServiceRegistry.getCTService(
				modelClassNameId);

			if (ctService == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No CTService found for classNameId " +
							modelClassNameId);
				}

				continue;
			}

			ctService.updateWithUnsafeFunction(
				ctPersistence -> {
					Connection connection = CurrentConnectionUtil.getConnection(
						ctPersistence.getDataSource());

					try (PreparedStatement preparedStatement =
							connection.prepareStatement(
								StringBundler.concat(
									"delete from ",
									ctPersistence.getTableName(),
									" where ctCollectionId = ",
									ctCollection.getCtCollectionId()))) {

						return preparedStatement.executeUpdate();
					}
					catch (Exception exception) {
						throw new SystemException(exception);
					}
				});
		}

		_ctAutoResolutionInfoPersistence.removeByCTCollectionId(
			ctCollection.getCtCollectionId());

		for (CTEntry ctEntry : ctEntries) {
			ctEntryPersistence.remove(ctEntry);
		}

		ctMessagePersistence.removeByCTCollectionId(
			ctCollection.getCtCollectionId());

		for (CTPreferences ctPreferences :
				ctPreferencesPersistence.findByCollectionId(
					ctCollection.getCtCollectionId())) {

			ctPreferences.setCtCollectionId(
				CTConstants.CT_COLLECTION_ID_PRODUCTION);

			ctPreferencesPersistence.update(ctPreferences);
		}

		List<CTProcess> ctProcesses = ctProcessPersistence.findByCollectionId(
			ctCollection.getCtCollectionId());

		for (CTProcess ctProcess : ctProcesses) {
			_ctProcessLocalService.deleteCTProcess(ctProcess);
		}

		return ctCollectionPersistence.remove(ctCollection);
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
	public CTCollection undoCTCollection(
			long ctCollectionId, long userId, String name, String description)
		throws PortalException {

		CTCollection undoCTCollection =
			ctCollectionPersistence.findByPrimaryKey(ctCollectionId);

		if (undoCTCollection.getStatus() != WorkflowConstants.STATUS_APPROVED) {
			throw new IllegalArgumentException(
				"Unable to undo " + undoCTCollection +
					" because it is not published");
		}

		CTCollection newCTCollection = addCTCollection(
			undoCTCollection.getCompanyId(), userId, name, description);

		CTPreferences ctPreferences =
			_ctPreferencesLocalService.getCTPreferences(
				undoCTCollection.getCompanyId(), userId);

		ctPreferences.setCtCollectionId(newCTCollection.getCtCollectionId());

		ctPreferencesPersistence.update(ctPreferences);

		List<CTEntry> publishedCTEntries =
			ctEntryPersistence.findByCTCollectionId(
				undoCTCollection.getCtCollectionId());

		Map<Long, CTServiceCopier> ctServiceCopiers = new HashMap<>();

		long batchCounter = counterLocalService.increment(
			CTEntry.class.getName(), publishedCTEntries.size());

		batchCounter -= publishedCTEntries.size();

		for (CTEntry publishedCTEntry : publishedCTEntries) {
			ctServiceCopiers.computeIfAbsent(
				publishedCTEntry.getModelClassNameId(),
				modelClassNameId -> {
					CTService<?> ctService = _ctServiceRegistry.getCTService(
						modelClassNameId);

					if (ctService != null) {
						return new CTServiceCopier<>(
							ctService, undoCTCollection.getCtCollectionId(),
							newCTCollection.getCtCollectionId());
					}

					throw new SystemException(
						StringBundler.concat(
							"Unable to undo ", undoCTCollection,
							" because service for ", modelClassNameId,
							" is missing"));
				});

			CTEntry ctEntry = ctEntryPersistence.create(++batchCounter);

			ctEntry.setCompanyId(newCTCollection.getCompanyId());
			ctEntry.setUserId(newCTCollection.getUserId());
			ctEntry.setCtCollectionId(newCTCollection.getCtCollectionId());
			ctEntry.setModelClassNameId(publishedCTEntry.getModelClassNameId());
			ctEntry.setModelClassPK(publishedCTEntry.getModelClassPK());
			ctEntry.setModelMvccVersion(publishedCTEntry.getModelMvccVersion());

			int changeType = publishedCTEntry.getChangeType();

			if (changeType == CTConstants.CT_CHANGE_TYPE_ADDITION) {
				changeType = CTConstants.CT_CHANGE_TYPE_DELETION;
			}
			else if (changeType == CTConstants.CT_CHANGE_TYPE_DELETION) {
				changeType = CTConstants.CT_CHANGE_TYPE_ADDITION;
			}

			ctEntry.setChangeType(changeType);

			ctEntryPersistence.update(ctEntry);
		}

		try {
			for (CTServiceCopier ctServiceCopier : ctServiceCopiers.values()) {
				ctServiceCopier.copy();
			}

			for (CTTableMapperHelper ctTableMapperHelper :
					_ctServiceRegistry.getCTTableMapperHelpers()) {

				ctTableMapperHelper.undo(
					undoCTCollection.getCtCollectionId(),
					newCTCollection.getCtCollectionId());
			}
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}

		_ctServiceRegistry.onAfterCopy(undoCTCollection, newCTCollection);

		return newCTCollection;
	}

	@Override
	public CTCollection updateCTCollection(
			long userId, long ctCollectionId, String name, String description)
		throws PortalException {

		_validate(name, description);

		CTCollection ctCollection = ctCollectionPersistence.findByPrimaryKey(
			ctCollectionId);

		Date modifiedDate = new Date();

		ctCollection.setModifiedDate(modifiedDate);

		ctCollection.setName(name);
		ctCollection.setDescription(description);
		ctCollection.setStatusByUserId(userId);
		ctCollection.setStatusDate(modifiedDate);

		return ctCollectionPersistence.update(ctCollection);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ConstraintResolver.class, null,
			(serviceReference, emitter) -> {
				ConstraintResolver<?> constraintResolver =
					bundleContext.getService(serviceReference);

				emitter.emit(
					new ConstraintResolverKey(
						constraintResolver.getModelClass(),
						constraintResolver.getUniqueIndexColumnNames()));
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
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

	private static final Log _log = LogFactoryUtil.getLog(
		CTCollectionLocalServiceImpl.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CTAutoResolutionInfoPersistence _ctAutoResolutionInfoPersistence;

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private CTProcessLocalService _ctProcessLocalService;

	@Reference
	private CTServiceRegistry _ctServiceRegistry;

	private ServiceTrackerMap<ConstraintResolverKey, ConstraintResolver>
		_serviceTrackerMap;

}