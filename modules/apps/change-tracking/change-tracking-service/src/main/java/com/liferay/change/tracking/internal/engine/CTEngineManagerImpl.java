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

package com.liferay.change.tracking.internal.engine;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.definition.CTDefinition;
import com.liferay.change.tracking.definition.CTDefinitionRegistry;
import com.liferay.change.tracking.engine.CTEngineManager;
import com.liferay.change.tracking.engine.exception.CTCollectionDescriptionCTEngineException;
import com.liferay.change.tracking.engine.exception.CTCollectionNameCTEngineException;
import com.liferay.change.tracking.engine.exception.CTEngineException;
import com.liferay.change.tracking.exception.CTCollectionDescriptionException;
import com.liferay.change.tracking.exception.CTCollectionNameException;
import com.liferay.change.tracking.internal.util.ChangeTrackingThreadLocal;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.model.impl.CTCollectionImpl;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = CTEngineManager.class)
public class CTEngineManagerImpl implements CTEngineManager {

	@Override
	public void checkoutCTCollection(long userId, long ctCollectionId) {
		long companyId = _getCompanyId(userId);

		if (companyId <= 0) {
			return;
		}

		if (!isChangeTrackingEnabled(companyId)) {
			return;
		}

		if (ctCollectionId != CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			CTCollection ctCollection =
				_ctCollectionLocalService.fetchCTCollection(ctCollectionId);

			if (ctCollection == null) {
				_log.error(
					"Unable to checkout change tracking collection " +
						ctCollectionId);

				return;
			}
		}

		CTPreferences ctPreferences =
			_ctPreferencesLocalService.getCTPreferences(companyId, userId);

		ctPreferences.setCtCollectionId(ctCollectionId);

		_ctPreferencesLocalService.updateCTPreferences(ctPreferences);
	}

	@Override
	public long countByKeywords(
		long companyId, QueryDefinition<CTCollection> queryDefinition) {

		return _ctCollectionLocalService.dynamicQueryCount(
			_getKeywordsDynamicQuery(companyId, queryDefinition));
	}

	@Override
	public Optional<CTCollection> createCTCollection(
			long userId, String name, String description)
		throws CTEngineException {

		long companyId = _getCompanyId(userId);

		if (companyId <= 0) {
			return Optional.empty();
		}

		if (!isChangeTrackingEnabled(companyId)) {
			return Optional.empty();
		}

		return _createCTCollection(userId, name, description);
	}

	@Override
	public void deleteCTCollection(long ctCollectionId) {
		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctCollectionId);

		if (ctCollection == null) {
			_log.error(
				"Unable to delete change tracking collection " +
					ctCollectionId);

			return;
		}

		if (ctCollection.isProduction()) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Deleting the production change tracking collection is " +
						"not allowed");
			}

			return;
		}

		try {
			_ctCollectionLocalService.deleteCTCollection(ctCollection);
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to delete change tracking collection " + ctCollectionId,
				pe);
		}
	}

	@Override
	public void disableChangeTracking(long companyId) {
		CTPreferences ctPreferences =
			_ctPreferencesLocalService.fetchCTPreferences(companyId, 0);

		if (ctPreferences == null) {
			return;
		}

		_ctPreferencesLocalService.deleteCTPreferences(ctPreferences);

		try {
			_ctCollectionLocalService.deleteCompanyCTCollections(companyId);

			_productionCTCollections.remove(companyId);
		}
		catch (PortalException pe) {
			_log.error("Unable to disable change tracking", pe);
		}
	}

	@Override
	public void enableChangeTracking(long companyId, long userId) {
		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get user " + userId);
			}

			return;
		}

		if (!isChangeTrackingAllowed(companyId)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Change tracking is not allowed in company " + companyId);
			}

			return;
		}

		if (isChangeTrackingEnabled(companyId)) {
			return;
		}

		try {
			ChangeTrackingThreadLocal.setModelUpdateInProgress(true);

			_ctPreferencesLocalService.getCTPreferences(companyId, 0);
		}
		finally {
			ChangeTrackingThreadLocal.setModelUpdateInProgress(false);
		}
	}

	@Override
	public Map<Integer, Long> getCTCollectionChangeTypeCounts(
		long ctCollectionId) {

		List<CTEntry> ctEntries = getCTEntries(ctCollectionId);

		Stream<CTEntry> ctEntriesStream = ctEntries.stream();

		return ctEntriesStream.collect(
			Collectors.groupingBy(
				CTEntry::getChangeType, Collectors.counting()));
	}

	@Override
	public Optional<CTCollection> getCTCollectionOptional(
		long companyId, long ctCollectionId) {

		if (ctCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			return getProductionCTCollectionOptional(companyId);
		}

		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctCollectionId);

		return Optional.ofNullable(ctCollection);
	}

	@Override
	public List<CTCollection> getCTCollections(long companyId) {
		if (!isChangeTrackingEnabled(companyId)) {
			return Collections.emptyList();
		}

		return _ctCollectionLocalService.getCTCollections(companyId, null);
	}

	@Override
	public List<CTEntry> getCTEntries(long ctCollectionId) {
		return _ctEntryLocalService.getCTCollectionCTEntries(ctCollectionId);
	}

	@Override
	public List<CTEntry> getCTEntries(
		long ctCollectionId, QueryDefinition<CTEntry> queryDefinition) {

		if (queryDefinition == null) {
			return _ctEntryLocalService.getCTCollectionCTEntries(
				ctCollectionId);
		}

		return _ctEntryLocalService.getCTCollectionCTEntries(
			ctCollectionId, queryDefinition.getStatus(),
			queryDefinition.getStart(), queryDefinition.getEnd(),
			queryDefinition.getOrderByComparator());
	}

	@Override
	public int getCTEntriesCount(
		long ctCollectionId, QueryDefinition<CTEntry> queryDefinition) {

		return _ctEntryLocalService.getCTEntriesCount(
			ctCollectionId, queryDefinition);
	}

	@Override
	public List<CTProcess> getCTProcesses(
		long companyId, long userId, String keywords,
		QueryDefinition<?> queryDefinition) {

		return _ctProcessLocalService.getCTProcesses(
			companyId, userId, keywords, queryDefinition);
	}

	@Override
	public Optional<CTProcess> getLatestCTProcessOptional(long companyId) {
		return Optional.ofNullable(
			_ctProcessLocalService.fetchLatestCTProcess(companyId));
	}

	@Override
	public Optional<CTCollection> getProductionCTCollectionOptional(
		long companyId) {

		if (!isChangeTrackingEnabled(companyId)) {
			return Optional.empty();
		}

		CTCollection productionCTCollection = _productionCTCollections.get(
			companyId);

		if (productionCTCollection == null) {
			productionCTCollection = new CTCollectionImpl() {

				@Override
				public String getName() {
					ResourceBundle resourceBundle =
						ResourceBundleUtil.getBundle(
							LocaleThreadLocal.getDefaultLocale(),
							CTEngineManagerImpl.class);

					return ResourceBundleUtil.getString(
						resourceBundle, "production");
				}

			};

			productionCTCollection.setCtCollectionId(
				CTConstants.CT_COLLECTION_ID_PRODUCTION);
			productionCTCollection.setCompanyId(companyId);

			try {
				productionCTCollection.setUserId(
					_userLocalService.getDefaultUserId(companyId));
			}
			catch (PortalException pe) {
				throw new RuntimeException(pe);
			}

			productionCTCollection.setStatus(WorkflowConstants.STATUS_APPROVED);

			_productionCTCollections.put(companyId, productionCTCollection);
		}

		return Optional.of(productionCTCollection);
	}

	@Override
	public long getRecentCTCollectionId(long userId) {
		long companyId = _getCompanyId(userId);

		long ctCollectionId = CTConstants.CT_COLLECTION_ID_PRODUCTION;

		CTPreferences ctPreferences =
			_ctPreferencesLocalService.fetchCTPreferences(companyId, userId);

		if (ctPreferences != null) {
			ctCollectionId = ctPreferences.getCtCollectionId();
		}

		Optional<CTCollection> ctCollectionOptional = getCTCollectionOptional(
			companyId, ctCollectionId);

		return ctCollectionOptional.map(
			CTCollection::getCtCollectionId
		).orElse(
			0L
		);
	}

	@Override
	public boolean isChangeTrackingAllowed(long companyId) {
		List<Group> groups = _groupLocalService.getCompanyGroups(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Stream<Group> groupStream = groups.parallelStream();

		Predicate<Group> groupPredicate =
			group -> group.isStagingGroup() || group.isStaged();

		if (groupStream.anyMatch(groupPredicate)) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isChangeTrackingEnabled(long companyId) {
		CTPreferences ctPreferences =
			_ctPreferencesLocalService.fetchCTPreferences(companyId, 0);

		if (ctPreferences == null) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isChangeTrackingSupported(
		long companyId, Class<? extends BaseModel> clazz) {

		Optional<CTDefinition<?, ?>> ctDefinitionOptional =
			_ctDefinitionRegistry.getCTDefinitionOptionalByVersionClass(clazz);

		return ctDefinitionOptional.isPresent();
	}

	@Override
	public boolean isChangeTrackingSupported(
		long companyId, long modelClassNameId) {

		String modelClassName = _portal.getClassName(modelClassNameId);

		Optional<CTDefinition<?, ?>> ctDefinitionOptional =
			_ctDefinitionRegistry.getCTDefinitionOptionalByVersionClassName(
				modelClassName);

		return ctDefinitionOptional.isPresent();
	}

	@Override
	public void publishCTCollection(
		long userId, long ctCollectionId, boolean ignoreCollision) {

		long companyId = _getCompanyId(userId);

		if (companyId <= 0) {
			return;
		}

		if (!isChangeTrackingEnabled(companyId)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to publish change tracking collection because " +
						"change tracking is not enabled in company " +
							companyId);
			}

			return;
		}

		try {
			_ctProcessLocalService.addCTProcess(
				userId, ctCollectionId, ignoreCollision, new ServiceContext());
		}
		catch (Throwable t) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to publish change tracking entries to change " +
						"tracking collection " + ctCollectionId,
					t);
			}
		}
	}

	@Override
	public List<CTCollection> searchByKeywords(
		long companyId, QueryDefinition<CTCollection> queryDefinition) {

		return _ctCollectionLocalService.dynamicQuery(
			_getKeywordsDynamicQuery(companyId, queryDefinition),
			queryDefinition.getStart(), queryDefinition.getEnd(),
			queryDefinition.getOrderByComparator());
	}

	private Optional<CTCollection> _createCTCollection(
			long userId, String name, String description)
		throws CTEngineException {

		CTCollection ctCollection = null;

		try {
			ctCollection = _ctCollectionLocalService.addCTCollection(
				userId, name, description);
		}
		catch (CTCollectionDescriptionException ctcde) {
			throw new CTCollectionDescriptionCTEngineException(
				CompanyThreadLocal.getCompanyId(), ctcde.getMessage(), ctcde);
		}
		catch (CTCollectionNameException ctcne) {
			throw new CTCollectionNameCTEngineException(
				CompanyThreadLocal.getCompanyId(), ctcne.getMessage(), ctcne);
		}
		catch (Throwable t) {
			_log.error(
				"Unable to create change tracking collection with name " + name,
				t);
		}

		return Optional.ofNullable(ctCollection);
	}

	private long _getCompanyId(long userId) {
		long companyId = 0;

		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = user.getCompanyId();
		}

		if (companyId <= 0) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get company ID");
			}
		}

		return companyId;
	}

	private DynamicQuery _getKeywordsDynamicQuery(
		long companyId, QueryDefinition<CTCollection> queryDefinition) {

		DynamicQuery dynamicQuery = _ctCollectionLocalService.dynamicQuery();

		dynamicQuery.add(RestrictionsFactoryUtil.eq("companyId", companyId));
		dynamicQuery.add(
			RestrictionsFactoryUtil.ne(
				"status", WorkflowConstants.STATUS_APPROVED));

		Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

		String keywords = GetterUtil.getString(
			queryDefinition.getAttribute("keywords"));

		for (String keyword : StringUtil.split(keywords, CharPool.SPACE)) {
			disjunction.add(
				RestrictionsFactoryUtil.ilike("name", _wildcard(keyword)));

			disjunction.add(
				RestrictionsFactoryUtil.ilike(
					"description", _wildcard(keyword)));
		}

		dynamicQuery.add(disjunction);

		return dynamicQuery;
	}

	private String _wildcard(String value) {
		return CharPool.PERCENT + value + CharPool.PERCENT;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTEngineManagerImpl.class);

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTDefinitionRegistry _ctDefinitionRegistry;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private CTProcessLocalService _ctProcessLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	private final Map<Long, CTCollection> _productionCTCollections =
		new ConcurrentHashMap<>();

	@Reference
	private UserLocalService _userLocalService;

}