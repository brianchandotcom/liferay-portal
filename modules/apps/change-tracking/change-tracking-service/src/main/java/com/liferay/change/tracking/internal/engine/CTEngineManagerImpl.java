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

import com.liferay.change.tracking.configuration.CTConfiguration;
import com.liferay.change.tracking.configuration.CTConfigurationRegistry;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.engine.CTEngineManager;
import com.liferay.change.tracking.engine.exception.CTCollectionDescriptionCTEngineException;
import com.liferay.change.tracking.engine.exception.CTCollectionNameCTEngineException;
import com.liferay.change.tracking.engine.exception.CTEngineException;
import com.liferay.change.tracking.engine.exception.CTEngineSystemException;
import com.liferay.change.tracking.exception.CTCollectionDescriptionException;
import com.liferay.change.tracking.exception.CTCollectionNameException;
import com.liferay.change.tracking.internal.util.ChangeTrackingThreadLocal;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTEntryAggregate;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryAggregateLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.change.tracking.settings.CTSettingsManager;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
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
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
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

		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctCollectionId);

		if (ctCollection == null) {
			_log.error(
				"Unable to checkout change tracking collection " +
					ctCollectionId);

			return;
		}

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					_ctSettingsManager.setUserCTSetting(
						userId, _RECENT_CT_COLLECTION_ID,
						String.valueOf(ctCollectionId));

					return null;
				});
		}
		catch (Throwable t) {
			_log.error(
				"Unable to update user's recent change tracking collection", t);
		}
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

		if (CTConstants.CT_COLLECTION_NAME_PRODUCTION.equals(name) ||
			!isChangeTrackingEnabled(companyId)) {

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
		if (!isChangeTrackingEnabled(companyId)) {
			return;
		}

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					_ctCollectionLocalService.deleteCompanyCTCollections(
						companyId);

					_productionCTCollections.remove(companyId);

					_ctSettingsManager.setGlobalCTSetting(
						companyId, _CHANGE_TRACKING_ENABLED,
						String.valueOf(Boolean.FALSE));

					return null;
				});
		}
		catch (Throwable t) {
			_log.error("Unable to disable change tracking", t);
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

			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					_enableChangeTracking(userId);

					return null;
				});
		}
		catch (Throwable t) {
			_log.error("Unable to enable change tracking", t);
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
	public Optional<CTCollection> getCTCollectionOptional(long ctCollectionId) {
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
	public List<CTEntry> getCTEntries(
		CTCollection ctCollection, long[] groupIds, long[] userIds,
		long[] classNameIds, int[] changeTypes, Boolean collision,
		QueryDefinition<CTEntry> queryDefinition) {

		return _ctEntryLocalService.search(
			ctCollection, groupIds, userIds, classNameIds, changeTypes,
			collision, queryDefinition);
	}

	@Override
	public List<CTEntry> getCTEntries(
		CTCollection ctCollection, String keywords,
		QueryDefinition<CTEntry> queryDefinition) {

		return _ctEntryLocalService.search(
			ctCollection, keywords, queryDefinition);
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
		CTCollection ctCollection, long[] groupIds, long[] userIds,
		long[] classNameIds, int[] changeTypes, Boolean collision,
		QueryDefinition<CTEntry> queryDefinition) {

		return (int)_ctEntryLocalService.searchCount(
			ctCollection, groupIds, userIds, classNameIds, changeTypes,
			collision, queryDefinition);
	}

	@Override
	public int getCTEntriesCount(
		CTCollection ctCollection, String keywords,
		QueryDefinition<CTEntry> queryDefinition) {

		return _ctEntryLocalService.searchCount(
			ctCollection, keywords, queryDefinition);
	}

	@Override
	public int getCTEntriesCount(
		long ctCollectionId, QueryDefinition<CTEntry> queryDefinition) {

		return _ctEntryLocalService.getCTEntriesCount(
			ctCollectionId, queryDefinition);
	}

	@Override
	public List<CTEntryAggregate> getCTEntryAggregates(long ctCollectionId) {
		return _ctEntryAggregateLocalService.getCTCollectionCTEntryAggregates(
			ctCollectionId);
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

		CTCollection productionCTCollection = _productionCTCollections.get(
			companyId);

		if (productionCTCollection == null) {
			productionCTCollection =
				_ctCollectionLocalService.fetchCTCollection(
					companyId, CTConstants.CT_COLLECTION_NAME_PRODUCTION);

			_productionCTCollections.put(companyId, productionCTCollection);
		}

		return Optional.ofNullable(productionCTCollection);
	}

	@Override
	public long getRecentCTCollectionId(long userId) {
		Optional<CTCollection> ctCollectionOptional = getCTCollectionOptional(
			GetterUtil.getLong(
				_ctSettingsManager.getUserCTSetting(
					userId, _RECENT_CT_COLLECTION_ID)));

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
		return GetterUtil.getBoolean(
			_ctSettingsManager.getGlobalCTSetting(
				companyId, _CHANGE_TRACKING_ENABLED));
	}

	@Override
	public boolean isChangeTrackingSupported(
		long companyId, Class<? extends BaseModel> clazz) {

		Optional<CTConfiguration<?, ?>> ctConfigurationOptional =
			_ctConfigurationRegistry.getCTConfigurationOptionalByVersionClass(
				clazz);

		return ctConfigurationOptional.isPresent();
	}

	@Override
	public boolean isChangeTrackingSupported(
		long companyId, long modelClassNameId) {

		String modelClassName = _portal.getClassName(modelClassNameId);

		Optional<CTConfiguration<?, ?>> ctConfigurationOptional =
			_ctConfigurationRegistry.
				getCTConfigurationOptionalByVersionClassName(modelClassName);

		return ctConfigurationOptional.isPresent();
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

	private void _copyEntriesFromProduction(CTCollection ctCollection) {
		Optional<CTCollection> productionCTCollectionOptional =
			getProductionCTCollectionOptional(ctCollection.getCompanyId());

		List<CTEntry> productionCTEntries = productionCTCollectionOptional.map(
			CTCollection::getCtCollectionId
		).map(
			this::getCTEntries
		).orElse(
			Collections.emptyList()
		);

		for (CTEntry ctEntry : productionCTEntries) {
			_ctCollectionLocalService.addCTEntryCTCollection(
				ctEntry.getCtEntryId(), ctCollection);
		}

		List<CTEntryAggregate> productionCTEntryAggregates =
			productionCTCollectionOptional.map(
				CTCollection::getCtCollectionId
			).map(
				this::getCTEntryAggregates
			).orElse(
				Collections.emptyList()
			);

		for (CTEntryAggregate productionCTEntryAggregate :
				productionCTEntryAggregates) {

			_ctCollectionLocalService.addCTEntryAggregateCTCollection(
				productionCTEntryAggregate.getCtEntryAggregateId(),
				ctCollection);
		}
	}

	private Optional<CTCollection> _createCTCollection(
			long userId, String name, String description)
		throws CTEngineException {

		CTCollection ctCollection = null;

		try {
			ctCollection = TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					CTCollection addedCTCollection =
						_ctCollectionLocalService.addCTCollection(
							userId, name, description, new ServiceContext());

					_copyEntriesFromProduction(addedCTCollection);

					return addedCTCollection;
				});
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

	private void _enableChangeTracking(long userId) throws PortalException {
		Optional<CTCollection> ctCollectionOptional = _createCTCollection(
			userId, CTConstants.CT_COLLECTION_NAME_PRODUCTION,
			StringPool.BLANK);

		long companyId = _getCompanyId(userId);

		CTCollection productionCTCollection = ctCollectionOptional.orElseThrow(
			() -> new CTEngineSystemException(
				companyId,
				"Unable to create production change tracking collection"));

		_productionCTCollections.put(companyId, productionCTCollection);

		_generateCTEntriesAndCTEntryAggregatesForAllCTConfigurations(
			userId, productionCTCollection);

		_ctSettingsManager.setGlobalCTSetting(
			companyId, _CHANGE_TRACKING_ENABLED, String.valueOf(Boolean.TRUE));

		checkoutCTCollection(
			userId, productionCTCollection.getCtCollectionId());
	}

	private void _generateCTEntriesAndCTEntryAggregatesForAllCTConfigurations(
		long userId, CTCollection ctCollection) {

		List<CTConfiguration<?, ?>> ctConfigurations =
			_ctConfigurationRegistry.getAllCTConfigurations();

		ctConfigurations.forEach(
			ctConfiguration -> _generateCTEntriesForCTConfiguration(
				userId, ctConfiguration, ctCollection));

		ctConfigurations.forEach(
			ctConfiguration -> _generateCTEntryAggregatesForCTConfiguration(
				userId, ctConfiguration, ctCollection));
	}

	@SuppressWarnings("unchecked")
	private void _generateCTEntriesForCTConfiguration(
		long userId, CTConfiguration ctConfiguration,
		CTCollection ctCollection) {

		Function<Long, List> resourceEntitiesByCompanyIdFunction =
			ctConfiguration.getResourceEntitiesByCompanyIdFunction();

		List<BaseModel> resourceEntitiesByCompanyId =
			resourceEntitiesByCompanyIdFunction.apply(
				ctCollection.getCompanyId());

		Function<BaseModel, Serializable>
			resourceEntityIdFromResourceEntityFunction =
				ctConfiguration.getResourceEntityIdFromResourceEntityFunction();

		for (BaseModel resourceEntity : resourceEntitiesByCompanyId) {
			Serializable resourcePrimKey =
				resourceEntityIdFromResourceEntityFunction.apply(
					resourceEntity);

			_generateCTEntriesForResourceEntity(
				userId, ctConfiguration, ctCollection, resourceEntity,
				resourcePrimKey);
		}
	}

	@SuppressWarnings("unchecked")
	private void _generateCTEntriesForResourceEntity(
		long userId, CTConfiguration ctConfiguration, CTCollection ctCollection,
		BaseModel resourceEntity, Serializable resourcePrimKey) {

		Function<BaseModel, List> versionEntitiesFromResourceEntityFunction =
			ctConfiguration.getVersionEntitiesFromResourceEntityFunction();

		List<BaseModel> versionEntities =
			versionEntitiesFromResourceEntityFunction.apply(resourceEntity);

		for (BaseModel versionEntity : versionEntities) {
			Function<BaseModel, Serializable>
				versionEntityIdFromVersionEntityFunction =
					ctConfiguration.
						getVersionEntityIdFromVersionEntityFunction();

			Serializable versionEntityId =
				versionEntityIdFromVersionEntityFunction.apply(versionEntity);

			try {
				_ctEntryLocalService.addCTEntry(
					userId,
					_portal.getClassNameId(
						ctConfiguration.getVersionEntityClass()),
					GetterUtil.getLong(versionEntityId),
					GetterUtil.getLong(resourcePrimKey),
					CTConstants.CT_CHANGE_TYPE_ADDITION,
					ctCollection.getCtCollectionId(), new ServiceContext());
			}
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					StringBundler sb = new StringBundler(5);

					sb.append("Unable to add change tracking entry to ");
					sb.append(ctConfiguration.getContentType());
					sb.append(" {");
					sb.append(versionEntityId);
					sb.append("}");

					_log.warn(sb.toString(), pe);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private <V extends BaseModel, R extends BaseModel> void
		_generateCTEntryAggregateForCTEntry(
			long userId, CTConfiguration ctConfiguration,
			CTCollection ctCollection, CTEntry ctEntry,
			List<Function<V, List<R>>> versionEntityRelatedEntitiesFunctions) {

		Function<Long, V> versionEntityByVersionEntityIdFunction =
			ctConfiguration.getVersionEntityByVersionEntityIdFunction();

		V versionEntity = versionEntityByVersionEntityIdFunction.apply(
			ctEntry.getModelClassPK());

		versionEntityRelatedEntitiesFunctions.forEach(
			relatedEntitiesFunction ->
				_generateCTEntryAggregateForRelatedEntity(
					userId, ctCollection, ctEntry, versionEntity,
					relatedEntitiesFunction));
	}

	private <R extends BaseModel> void
		_generateCTEntryAggregateForRelatedEntity(
			long userId, CTCollection ctCollection, CTEntry ctEntry,
			R relatedEntity) {

		long relatedEntityClassPK = (Long)relatedEntity.getPrimaryKeyObj();

		CTEntry relatedCTEntry = _ctEntryLocalService.fetchCTEntry(
			_portal.getClassNameId(relatedEntity.getModelClassName()),
			relatedEntityClassPK);

		if (relatedCTEntry == null) {
			List<CTEntry> relatedCTEntries =
				_ctEntryLocalService.fetchCTEntries(
					ctCollection.getCtCollectionId(), relatedEntityClassPK,
					new QueryDefinition<>());

			if (ListUtil.isEmpty(relatedCTEntries)) {
				return;
			}

			relatedCTEntry = relatedCTEntries.get(0);
		}

		CTEntryAggregate ctEntryAggregate =
			_ctEntryAggregateLocalService.fetchLatestCTEntryAggregate(
				ctCollection.getCtCollectionId(), ctEntry.getCtEntryId());

		if (ctEntryAggregate == null) {
			try {
				ctEntryAggregate =
					_ctEntryAggregateLocalService.addCTEntryAggregate(
						userId, ctCollection.getCtCollectionId(),
						ctEntry.getCtEntryId(), new ServiceContext());
			}
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to add CTEntryAggregate: " +
							pe.getLocalizedMessage());
				}
			}
		}

		_ctEntryAggregateLocalService.addCTEntry(
			ctEntryAggregate, relatedCTEntry);
	}

	private <V extends BaseModel, R extends BaseModel> void
		_generateCTEntryAggregateForRelatedEntity(
			long userId, CTCollection ctCollection, CTEntry ctEntry,
			V versionEntity,
			Function<V, List<R>> versionEntityRelatedEntityFunction) {

		List<R> relatedEntities = versionEntityRelatedEntityFunction.apply(
			versionEntity);

		Stream<R> relatedEntityStream = relatedEntities.stream();

		relatedEntityStream.filter(
			Objects::nonNull
		).forEach(
			relatedEntity -> _generateCTEntryAggregateForRelatedEntity(
				userId, ctCollection, ctEntry, relatedEntity)
		);
	}

	@SuppressWarnings("unchecked")
	private <V extends BaseModel, R extends BaseModel> void
		_generateCTEntryAggregatesForCTConfiguration(
			long userId, CTConfiguration ctConfiguration,
			CTCollection ctCollection) {

		List<Function<V, List<R>>> versionEntityRelatedEntitiesFunctions =
			ctConfiguration.getVersionEntityRelatedEntitiesFunctions();

		if (ListUtil.isEmpty(versionEntityRelatedEntitiesFunctions)) {
			return;
		}

		Class<V> versionEntityClass = ctConfiguration.getVersionEntityClass();

		List<CTEntry> ctEntries = _ctEntryLocalService.fetchCTEntries(
			versionEntityClass.getName());

		ctEntries.forEach(
			ctEntry -> _generateCTEntryAggregateForCTEntry(
				userId, ctConfiguration, ctCollection, ctEntry,
				versionEntityRelatedEntitiesFunctions));
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
				"name", CTConstants.CT_COLLECTION_NAME_PRODUCTION));
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

	private static final String _CHANGE_TRACKING_ENABLED =
		"changeTrackingEnabled";

	private static final String _RECENT_CT_COLLECTION_ID =
		"recentCTCollectionId";

	private static final Log _log = LogFactoryUtil.getLog(
		CTEngineManagerImpl.class);

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTConfigurationRegistry _ctConfigurationRegistry;

	@Reference
	private CTEntryAggregateLocalService _ctEntryAggregateLocalService;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private CTProcessLocalService _ctProcessLocalService;

	@Reference
	private CTSettingsManager _ctSettingsManager;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	private final Map<Long, CTCollection> _productionCTCollections =
		new HashMap<>();
	private final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private UserLocalService _userLocalService;

}