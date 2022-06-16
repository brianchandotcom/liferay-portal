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

package com.liferay.commerce.service.base;

import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.service.CommerceOrderTypeLocalService;
import com.liferay.commerce.service.CommerceOrderTypeLocalServiceUtil;
import com.liferay.commerce.service.persistence.CPDAvailabilityEstimatePersistence;
import com.liferay.commerce.service.persistence.CPDefinitionInventoryPersistence;
import com.liferay.commerce.service.persistence.CommerceAddressRestrictionPersistence;
import com.liferay.commerce.service.persistence.CommerceAvailabilityEstimatePersistence;
import com.liferay.commerce.service.persistence.CommerceOrderFinder;
import com.liferay.commerce.service.persistence.CommerceOrderItemFinder;
import com.liferay.commerce.service.persistence.CommerceOrderItemPersistence;
import com.liferay.commerce.service.persistence.CommerceOrderNotePersistence;
import com.liferay.commerce.service.persistence.CommerceOrderPaymentPersistence;
import com.liferay.commerce.service.persistence.CommerceOrderPersistence;
import com.liferay.commerce.service.persistence.CommerceOrderTypePersistence;
import com.liferay.commerce.service.persistence.CommerceOrderTypeRelPersistence;
import com.liferay.commerce.service.persistence.CommerceShipmentFinder;
import com.liferay.commerce.service.persistence.CommerceShipmentItemFinder;
import com.liferay.commerce.service.persistence.CommerceShipmentItemPersistence;
import com.liferay.commerce.service.persistence.CommerceShipmentPersistence;
import com.liferay.commerce.service.persistence.CommerceShippingMethodPersistence;
import com.liferay.commerce.service.persistence.CommerceShippingOptionAccountEntryRelPersistence;
import com.liferay.commerce.service.persistence.CommerceSubscriptionEntryFinder;
import com.liferay.commerce.service.persistence.CommerceSubscriptionEntryPersistence;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the commerce order type local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.commerce.service.impl.CommerceOrderTypeLocalServiceImpl}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.impl.CommerceOrderTypeLocalServiceImpl
 * @generated
 */
public abstract class CommerceOrderTypeLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements CommerceOrderTypeLocalService, IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>CommerceOrderTypeLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>CommerceOrderTypeLocalServiceUtil</code>.
	 */

	/**
	 * Adds the commerce order type to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderTypeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderType the commerce order type
	 * @return the commerce order type that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrderType addCommerceOrderType(
		CommerceOrderType commerceOrderType) {

		commerceOrderType.setNew(true);

		return commerceOrderTypePersistence.update(commerceOrderType);
	}

	/**
	 * Creates a new commerce order type with the primary key. Does not add the commerce order type to the database.
	 *
	 * @param commerceOrderTypeId the primary key for the new commerce order type
	 * @return the new commerce order type
	 */
	@Override
	@Transactional(enabled = false)
	public CommerceOrderType createCommerceOrderType(long commerceOrderTypeId) {
		return commerceOrderTypePersistence.create(commerceOrderTypeId);
	}

	/**
	 * Deletes the commerce order type with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderTypeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderTypeId the primary key of the commerce order type
	 * @return the commerce order type that was removed
	 * @throws PortalException if a commerce order type with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommerceOrderType deleteCommerceOrderType(long commerceOrderTypeId)
		throws PortalException {

		return commerceOrderTypePersistence.remove(commerceOrderTypeId);
	}

	/**
	 * Deletes the commerce order type from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderTypeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderType the commerce order type
	 * @return the commerce order type that was removed
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommerceOrderType deleteCommerceOrderType(
			CommerceOrderType commerceOrderType)
		throws PortalException {

		return commerceOrderTypePersistence.remove(commerceOrderType);
	}

	@Override
	public <T> T dslQuery(DSLQuery dslQuery) {
		return commerceOrderTypePersistence.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(DSLQuery dslQuery) {
		Long count = dslQuery(dslQuery);

		return count.intValue();
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			CommerceOrderType.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return commerceOrderTypePersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return commerceOrderTypePersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return commerceOrderTypePersistence.findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return commerceOrderTypePersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return commerceOrderTypePersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public CommerceOrderType fetchCommerceOrderType(long commerceOrderTypeId) {
		return commerceOrderTypePersistence.fetchByPrimaryKey(
			commerceOrderTypeId);
	}

	/**
	 * Returns the commerce order type with the matching UUID and company.
	 *
	 * @param uuid the commerce order type's UUID
	 * @param companyId the primary key of the company
	 * @return the matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	@Override
	public CommerceOrderType fetchCommerceOrderTypeByUuidAndCompanyId(
		String uuid, long companyId) {

		return commerceOrderTypePersistence.fetchByUuid_C_First(
			uuid, companyId, null);
	}

	/**
	 * Returns the commerce order type with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce order type's external reference code
	 * @return the matching commerce order type, or <code>null</code> if a matching commerce order type could not be found
	 */
	@Override
	public CommerceOrderType fetchCommerceOrderTypeByExternalReferenceCode(
		long companyId, String externalReferenceCode) {

		return commerceOrderTypePersistence.fetchByC_ERC(
			companyId, externalReferenceCode);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #fetchCommerceOrderTypeByExternalReferenceCode(long, String)}
	 */
	@Deprecated
	@Override
	public CommerceOrderType fetchCommerceOrderTypeByReferenceCode(
		long companyId, String externalReferenceCode) {

		return fetchCommerceOrderTypeByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce order type with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce order type's external reference code
	 * @return the matching commerce order type
	 * @throws PortalException if a matching commerce order type could not be found
	 */
	@Override
	public CommerceOrderType getCommerceOrderTypeByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		return commerceOrderTypePersistence.findByC_ERC(
			companyId, externalReferenceCode);
	}

	/**
	 * Returns the commerce order type with the primary key.
	 *
	 * @param commerceOrderTypeId the primary key of the commerce order type
	 * @return the commerce order type
	 * @throws PortalException if a commerce order type with the primary key could not be found
	 */
	@Override
	public CommerceOrderType getCommerceOrderType(long commerceOrderTypeId)
		throws PortalException {

		return commerceOrderTypePersistence.findByPrimaryKey(
			commerceOrderTypeId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(
			commerceOrderTypeLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CommerceOrderType.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("commerceOrderTypeId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			commerceOrderTypeLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(CommerceOrderType.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"commerceOrderTypeId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(
			commerceOrderTypeLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CommerceOrderType.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("commerceOrderTypeId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		final ExportActionableDynamicQuery exportActionableDynamicQuery =
			new ExportActionableDynamicQuery() {

				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary =
						portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(
						stagedModelType, modelAdditionCount);

					long modelDeletionCount =
						ExportImportHelperUtil.getModelDeletionCount(
							portletDataContext, stagedModelType);

					manifestSummary.addModelDeletionCount(
						stagedModelType, modelDeletionCount);

					return modelAdditionCount;
				}

			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Criterion modifiedDateCriterion =
						portletDataContext.getDateRangeCriteria("modifiedDate");

					Criterion statusDateCriterion =
						portletDataContext.getDateRangeCriteria("statusDate");

					if ((modifiedDateCriterion != null) &&
						(statusDateCriterion != null)) {

						Disjunction disjunction =
							RestrictionsFactoryUtil.disjunction();

						disjunction.add(modifiedDateCriterion);
						disjunction.add(statusDateCriterion);

						dynamicQuery.add(disjunction);
					}

					Property workflowStatusProperty =
						PropertyFactoryUtil.forName("status");

					if (portletDataContext.isInitialPublication()) {
						dynamicQuery.add(
							workflowStatusProperty.ne(
								WorkflowConstants.STATUS_IN_TRASH));
					}
					else {
						StagedModelDataHandler<?> stagedModelDataHandler =
							StagedModelDataHandlerRegistryUtil.
								getStagedModelDataHandler(
									CommerceOrderType.class.getName());

						dynamicQuery.add(
							workflowStatusProperty.in(
								stagedModelDataHandler.
									getExportableStatuses()));
					}
				}

			});

		exportActionableDynamicQuery.setCompanyId(
			portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CommerceOrderType>() {

				@Override
				public void performAction(CommerceOrderType commerceOrderType)
					throws PortalException {

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, commerceOrderType);
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				PortalUtil.getClassNameId(CommerceOrderType.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return commerceOrderTypePersistence.create(
			((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return commerceOrderTypeLocalService.deleteCommerceOrderType(
			(CommerceOrderType)persistedModel);
	}

	@Override
	public BasePersistence<CommerceOrderType> getBasePersistence() {
		return commerceOrderTypePersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return commerceOrderTypePersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns the commerce order type with the matching UUID and company.
	 *
	 * @param uuid the commerce order type's UUID
	 * @param companyId the primary key of the company
	 * @return the matching commerce order type
	 * @throws PortalException if a matching commerce order type could not be found
	 */
	@Override
	public CommerceOrderType getCommerceOrderTypeByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException {

		return commerceOrderTypePersistence.findByUuid_C_First(
			uuid, companyId, null);
	}

	/**
	 * Returns a range of all the commerce order types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceOrderTypeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order types
	 * @param end the upper bound of the range of commerce order types (not inclusive)
	 * @return the range of commerce order types
	 */
	@Override
	public List<CommerceOrderType> getCommerceOrderTypes(int start, int end) {
		return commerceOrderTypePersistence.findAll(start, end);
	}

	/**
	 * Returns the number of commerce order types.
	 *
	 * @return the number of commerce order types
	 */
	@Override
	public int getCommerceOrderTypesCount() {
		return commerceOrderTypePersistence.countAll();
	}

	/**
	 * Updates the commerce order type in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceOrderTypeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceOrderType the commerce order type
	 * @return the commerce order type that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrderType updateCommerceOrderType(
		CommerceOrderType commerceOrderType) {

		return commerceOrderTypePersistence.update(commerceOrderType);
	}

	/**
	 * Returns the commerce address local service.
	 *
	 * @return the commerce address local service
	 */
	public com.liferay.commerce.service.CommerceAddressLocalService
		getCommerceAddressLocalService() {

		return commerceAddressLocalService;
	}

	/**
	 * Sets the commerce address local service.
	 *
	 * @param commerceAddressLocalService the commerce address local service
	 */
	public void setCommerceAddressLocalService(
		com.liferay.commerce.service.CommerceAddressLocalService
			commerceAddressLocalService) {

		this.commerceAddressLocalService = commerceAddressLocalService;
	}

	/**
	 * Returns the commerce address restriction local service.
	 *
	 * @return the commerce address restriction local service
	 */
	public com.liferay.commerce.service.CommerceAddressRestrictionLocalService
		getCommerceAddressRestrictionLocalService() {

		return commerceAddressRestrictionLocalService;
	}

	/**
	 * Sets the commerce address restriction local service.
	 *
	 * @param commerceAddressRestrictionLocalService the commerce address restriction local service
	 */
	public void setCommerceAddressRestrictionLocalService(
		com.liferay.commerce.service.CommerceAddressRestrictionLocalService
			commerceAddressRestrictionLocalService) {

		this.commerceAddressRestrictionLocalService =
			commerceAddressRestrictionLocalService;
	}

	/**
	 * Returns the commerce address restriction persistence.
	 *
	 * @return the commerce address restriction persistence
	 */
	public CommerceAddressRestrictionPersistence
		getCommerceAddressRestrictionPersistence() {

		return commerceAddressRestrictionPersistence;
	}

	/**
	 * Sets the commerce address restriction persistence.
	 *
	 * @param commerceAddressRestrictionPersistence the commerce address restriction persistence
	 */
	public void setCommerceAddressRestrictionPersistence(
		CommerceAddressRestrictionPersistence
			commerceAddressRestrictionPersistence) {

		this.commerceAddressRestrictionPersistence =
			commerceAddressRestrictionPersistence;
	}

	/**
	 * Returns the commerce availability estimate local service.
	 *
	 * @return the commerce availability estimate local service
	 */
	public com.liferay.commerce.service.CommerceAvailabilityEstimateLocalService
		getCommerceAvailabilityEstimateLocalService() {

		return commerceAvailabilityEstimateLocalService;
	}

	/**
	 * Sets the commerce availability estimate local service.
	 *
	 * @param commerceAvailabilityEstimateLocalService the commerce availability estimate local service
	 */
	public void setCommerceAvailabilityEstimateLocalService(
		com.liferay.commerce.service.CommerceAvailabilityEstimateLocalService
			commerceAvailabilityEstimateLocalService) {

		this.commerceAvailabilityEstimateLocalService =
			commerceAvailabilityEstimateLocalService;
	}

	/**
	 * Returns the commerce availability estimate persistence.
	 *
	 * @return the commerce availability estimate persistence
	 */
	public CommerceAvailabilityEstimatePersistence
		getCommerceAvailabilityEstimatePersistence() {

		return commerceAvailabilityEstimatePersistence;
	}

	/**
	 * Sets the commerce availability estimate persistence.
	 *
	 * @param commerceAvailabilityEstimatePersistence the commerce availability estimate persistence
	 */
	public void setCommerceAvailabilityEstimatePersistence(
		CommerceAvailabilityEstimatePersistence
			commerceAvailabilityEstimatePersistence) {

		this.commerceAvailabilityEstimatePersistence =
			commerceAvailabilityEstimatePersistence;
	}

	/**
	 * Returns the commerce order local service.
	 *
	 * @return the commerce order local service
	 */
	public com.liferay.commerce.service.CommerceOrderLocalService
		getCommerceOrderLocalService() {

		return commerceOrderLocalService;
	}

	/**
	 * Sets the commerce order local service.
	 *
	 * @param commerceOrderLocalService the commerce order local service
	 */
	public void setCommerceOrderLocalService(
		com.liferay.commerce.service.CommerceOrderLocalService
			commerceOrderLocalService) {

		this.commerceOrderLocalService = commerceOrderLocalService;
	}

	/**
	 * Returns the commerce order persistence.
	 *
	 * @return the commerce order persistence
	 */
	public CommerceOrderPersistence getCommerceOrderPersistence() {
		return commerceOrderPersistence;
	}

	/**
	 * Sets the commerce order persistence.
	 *
	 * @param commerceOrderPersistence the commerce order persistence
	 */
	public void setCommerceOrderPersistence(
		CommerceOrderPersistence commerceOrderPersistence) {

		this.commerceOrderPersistence = commerceOrderPersistence;
	}

	/**
	 * Returns the commerce order finder.
	 *
	 * @return the commerce order finder
	 */
	public CommerceOrderFinder getCommerceOrderFinder() {
		return commerceOrderFinder;
	}

	/**
	 * Sets the commerce order finder.
	 *
	 * @param commerceOrderFinder the commerce order finder
	 */
	public void setCommerceOrderFinder(
		CommerceOrderFinder commerceOrderFinder) {

		this.commerceOrderFinder = commerceOrderFinder;
	}

	/**
	 * Returns the commerce order item local service.
	 *
	 * @return the commerce order item local service
	 */
	public com.liferay.commerce.service.CommerceOrderItemLocalService
		getCommerceOrderItemLocalService() {

		return commerceOrderItemLocalService;
	}

	/**
	 * Sets the commerce order item local service.
	 *
	 * @param commerceOrderItemLocalService the commerce order item local service
	 */
	public void setCommerceOrderItemLocalService(
		com.liferay.commerce.service.CommerceOrderItemLocalService
			commerceOrderItemLocalService) {

		this.commerceOrderItemLocalService = commerceOrderItemLocalService;
	}

	/**
	 * Returns the commerce order item persistence.
	 *
	 * @return the commerce order item persistence
	 */
	public CommerceOrderItemPersistence getCommerceOrderItemPersistence() {
		return commerceOrderItemPersistence;
	}

	/**
	 * Sets the commerce order item persistence.
	 *
	 * @param commerceOrderItemPersistence the commerce order item persistence
	 */
	public void setCommerceOrderItemPersistence(
		CommerceOrderItemPersistence commerceOrderItemPersistence) {

		this.commerceOrderItemPersistence = commerceOrderItemPersistence;
	}

	/**
	 * Returns the commerce order item finder.
	 *
	 * @return the commerce order item finder
	 */
	public CommerceOrderItemFinder getCommerceOrderItemFinder() {
		return commerceOrderItemFinder;
	}

	/**
	 * Sets the commerce order item finder.
	 *
	 * @param commerceOrderItemFinder the commerce order item finder
	 */
	public void setCommerceOrderItemFinder(
		CommerceOrderItemFinder commerceOrderItemFinder) {

		this.commerceOrderItemFinder = commerceOrderItemFinder;
	}

	/**
	 * Returns the commerce order note local service.
	 *
	 * @return the commerce order note local service
	 */
	public com.liferay.commerce.service.CommerceOrderNoteLocalService
		getCommerceOrderNoteLocalService() {

		return commerceOrderNoteLocalService;
	}

	/**
	 * Sets the commerce order note local service.
	 *
	 * @param commerceOrderNoteLocalService the commerce order note local service
	 */
	public void setCommerceOrderNoteLocalService(
		com.liferay.commerce.service.CommerceOrderNoteLocalService
			commerceOrderNoteLocalService) {

		this.commerceOrderNoteLocalService = commerceOrderNoteLocalService;
	}

	/**
	 * Returns the commerce order note persistence.
	 *
	 * @return the commerce order note persistence
	 */
	public CommerceOrderNotePersistence getCommerceOrderNotePersistence() {
		return commerceOrderNotePersistence;
	}

	/**
	 * Sets the commerce order note persistence.
	 *
	 * @param commerceOrderNotePersistence the commerce order note persistence
	 */
	public void setCommerceOrderNotePersistence(
		CommerceOrderNotePersistence commerceOrderNotePersistence) {

		this.commerceOrderNotePersistence = commerceOrderNotePersistence;
	}

	/**
	 * Returns the commerce order payment local service.
	 *
	 * @return the commerce order payment local service
	 */
	public com.liferay.commerce.service.CommerceOrderPaymentLocalService
		getCommerceOrderPaymentLocalService() {

		return commerceOrderPaymentLocalService;
	}

	/**
	 * Sets the commerce order payment local service.
	 *
	 * @param commerceOrderPaymentLocalService the commerce order payment local service
	 */
	public void setCommerceOrderPaymentLocalService(
		com.liferay.commerce.service.CommerceOrderPaymentLocalService
			commerceOrderPaymentLocalService) {

		this.commerceOrderPaymentLocalService =
			commerceOrderPaymentLocalService;
	}

	/**
	 * Returns the commerce order payment persistence.
	 *
	 * @return the commerce order payment persistence
	 */
	public CommerceOrderPaymentPersistence
		getCommerceOrderPaymentPersistence() {

		return commerceOrderPaymentPersistence;
	}

	/**
	 * Sets the commerce order payment persistence.
	 *
	 * @param commerceOrderPaymentPersistence the commerce order payment persistence
	 */
	public void setCommerceOrderPaymentPersistence(
		CommerceOrderPaymentPersistence commerceOrderPaymentPersistence) {

		this.commerceOrderPaymentPersistence = commerceOrderPaymentPersistence;
	}

	/**
	 * Returns the commerce order type local service.
	 *
	 * @return the commerce order type local service
	 */
	public CommerceOrderTypeLocalService getCommerceOrderTypeLocalService() {
		return commerceOrderTypeLocalService;
	}

	/**
	 * Sets the commerce order type local service.
	 *
	 * @param commerceOrderTypeLocalService the commerce order type local service
	 */
	public void setCommerceOrderTypeLocalService(
		CommerceOrderTypeLocalService commerceOrderTypeLocalService) {

		this.commerceOrderTypeLocalService = commerceOrderTypeLocalService;
	}

	/**
	 * Returns the commerce order type persistence.
	 *
	 * @return the commerce order type persistence
	 */
	public CommerceOrderTypePersistence getCommerceOrderTypePersistence() {
		return commerceOrderTypePersistence;
	}

	/**
	 * Sets the commerce order type persistence.
	 *
	 * @param commerceOrderTypePersistence the commerce order type persistence
	 */
	public void setCommerceOrderTypePersistence(
		CommerceOrderTypePersistence commerceOrderTypePersistence) {

		this.commerceOrderTypePersistence = commerceOrderTypePersistence;
	}

	/**
	 * Returns the commerce order type rel local service.
	 *
	 * @return the commerce order type rel local service
	 */
	public com.liferay.commerce.service.CommerceOrderTypeRelLocalService
		getCommerceOrderTypeRelLocalService() {

		return commerceOrderTypeRelLocalService;
	}

	/**
	 * Sets the commerce order type rel local service.
	 *
	 * @param commerceOrderTypeRelLocalService the commerce order type rel local service
	 */
	public void setCommerceOrderTypeRelLocalService(
		com.liferay.commerce.service.CommerceOrderTypeRelLocalService
			commerceOrderTypeRelLocalService) {

		this.commerceOrderTypeRelLocalService =
			commerceOrderTypeRelLocalService;
	}

	/**
	 * Returns the commerce order type rel persistence.
	 *
	 * @return the commerce order type rel persistence
	 */
	public CommerceOrderTypeRelPersistence
		getCommerceOrderTypeRelPersistence() {

		return commerceOrderTypeRelPersistence;
	}

	/**
	 * Sets the commerce order type rel persistence.
	 *
	 * @param commerceOrderTypeRelPersistence the commerce order type rel persistence
	 */
	public void setCommerceOrderTypeRelPersistence(
		CommerceOrderTypeRelPersistence commerceOrderTypeRelPersistence) {

		this.commerceOrderTypeRelPersistence = commerceOrderTypeRelPersistence;
	}

	/**
	 * Returns the commerce shipment local service.
	 *
	 * @return the commerce shipment local service
	 */
	public com.liferay.commerce.service.CommerceShipmentLocalService
		getCommerceShipmentLocalService() {

		return commerceShipmentLocalService;
	}

	/**
	 * Sets the commerce shipment local service.
	 *
	 * @param commerceShipmentLocalService the commerce shipment local service
	 */
	public void setCommerceShipmentLocalService(
		com.liferay.commerce.service.CommerceShipmentLocalService
			commerceShipmentLocalService) {

		this.commerceShipmentLocalService = commerceShipmentLocalService;
	}

	/**
	 * Returns the commerce shipment persistence.
	 *
	 * @return the commerce shipment persistence
	 */
	public CommerceShipmentPersistence getCommerceShipmentPersistence() {
		return commerceShipmentPersistence;
	}

	/**
	 * Sets the commerce shipment persistence.
	 *
	 * @param commerceShipmentPersistence the commerce shipment persistence
	 */
	public void setCommerceShipmentPersistence(
		CommerceShipmentPersistence commerceShipmentPersistence) {

		this.commerceShipmentPersistence = commerceShipmentPersistence;
	}

	/**
	 * Returns the commerce shipment finder.
	 *
	 * @return the commerce shipment finder
	 */
	public CommerceShipmentFinder getCommerceShipmentFinder() {
		return commerceShipmentFinder;
	}

	/**
	 * Sets the commerce shipment finder.
	 *
	 * @param commerceShipmentFinder the commerce shipment finder
	 */
	public void setCommerceShipmentFinder(
		CommerceShipmentFinder commerceShipmentFinder) {

		this.commerceShipmentFinder = commerceShipmentFinder;
	}

	/**
	 * Returns the commerce shipment item local service.
	 *
	 * @return the commerce shipment item local service
	 */
	public com.liferay.commerce.service.CommerceShipmentItemLocalService
		getCommerceShipmentItemLocalService() {

		return commerceShipmentItemLocalService;
	}

	/**
	 * Sets the commerce shipment item local service.
	 *
	 * @param commerceShipmentItemLocalService the commerce shipment item local service
	 */
	public void setCommerceShipmentItemLocalService(
		com.liferay.commerce.service.CommerceShipmentItemLocalService
			commerceShipmentItemLocalService) {

		this.commerceShipmentItemLocalService =
			commerceShipmentItemLocalService;
	}

	/**
	 * Returns the commerce shipment item persistence.
	 *
	 * @return the commerce shipment item persistence
	 */
	public CommerceShipmentItemPersistence
		getCommerceShipmentItemPersistence() {

		return commerceShipmentItemPersistence;
	}

	/**
	 * Sets the commerce shipment item persistence.
	 *
	 * @param commerceShipmentItemPersistence the commerce shipment item persistence
	 */
	public void setCommerceShipmentItemPersistence(
		CommerceShipmentItemPersistence commerceShipmentItemPersistence) {

		this.commerceShipmentItemPersistence = commerceShipmentItemPersistence;
	}

	/**
	 * Returns the commerce shipment item finder.
	 *
	 * @return the commerce shipment item finder
	 */
	public CommerceShipmentItemFinder getCommerceShipmentItemFinder() {
		return commerceShipmentItemFinder;
	}

	/**
	 * Sets the commerce shipment item finder.
	 *
	 * @param commerceShipmentItemFinder the commerce shipment item finder
	 */
	public void setCommerceShipmentItemFinder(
		CommerceShipmentItemFinder commerceShipmentItemFinder) {

		this.commerceShipmentItemFinder = commerceShipmentItemFinder;
	}

	/**
	 * Returns the commerce shipping method local service.
	 *
	 * @return the commerce shipping method local service
	 */
	public com.liferay.commerce.service.CommerceShippingMethodLocalService
		getCommerceShippingMethodLocalService() {

		return commerceShippingMethodLocalService;
	}

	/**
	 * Sets the commerce shipping method local service.
	 *
	 * @param commerceShippingMethodLocalService the commerce shipping method local service
	 */
	public void setCommerceShippingMethodLocalService(
		com.liferay.commerce.service.CommerceShippingMethodLocalService
			commerceShippingMethodLocalService) {

		this.commerceShippingMethodLocalService =
			commerceShippingMethodLocalService;
	}

	/**
	 * Returns the commerce shipping method persistence.
	 *
	 * @return the commerce shipping method persistence
	 */
	public CommerceShippingMethodPersistence
		getCommerceShippingMethodPersistence() {

		return commerceShippingMethodPersistence;
	}

	/**
	 * Sets the commerce shipping method persistence.
	 *
	 * @param commerceShippingMethodPersistence the commerce shipping method persistence
	 */
	public void setCommerceShippingMethodPersistence(
		CommerceShippingMethodPersistence commerceShippingMethodPersistence) {

		this.commerceShippingMethodPersistence =
			commerceShippingMethodPersistence;
	}

	/**
	 * Returns the commerce shipping option account entry rel local service.
	 *
	 * @return the commerce shipping option account entry rel local service
	 */
	public com.liferay.commerce.service.
		CommerceShippingOptionAccountEntryRelLocalService
			getCommerceShippingOptionAccountEntryRelLocalService() {

		return commerceShippingOptionAccountEntryRelLocalService;
	}

	/**
	 * Sets the commerce shipping option account entry rel local service.
	 *
	 * @param commerceShippingOptionAccountEntryRelLocalService the commerce shipping option account entry rel local service
	 */
	public void setCommerceShippingOptionAccountEntryRelLocalService(
		com.liferay.commerce.service.
			CommerceShippingOptionAccountEntryRelLocalService
				commerceShippingOptionAccountEntryRelLocalService) {

		this.commerceShippingOptionAccountEntryRelLocalService =
			commerceShippingOptionAccountEntryRelLocalService;
	}

	/**
	 * Returns the commerce shipping option account entry rel persistence.
	 *
	 * @return the commerce shipping option account entry rel persistence
	 */
	public CommerceShippingOptionAccountEntryRelPersistence
		getCommerceShippingOptionAccountEntryRelPersistence() {

		return commerceShippingOptionAccountEntryRelPersistence;
	}

	/**
	 * Sets the commerce shipping option account entry rel persistence.
	 *
	 * @param commerceShippingOptionAccountEntryRelPersistence the commerce shipping option account entry rel persistence
	 */
	public void setCommerceShippingOptionAccountEntryRelPersistence(
		CommerceShippingOptionAccountEntryRelPersistence
			commerceShippingOptionAccountEntryRelPersistence) {

		this.commerceShippingOptionAccountEntryRelPersistence =
			commerceShippingOptionAccountEntryRelPersistence;
	}

	/**
	 * Returns the commerce subscription entry local service.
	 *
	 * @return the commerce subscription entry local service
	 */
	public com.liferay.commerce.service.CommerceSubscriptionEntryLocalService
		getCommerceSubscriptionEntryLocalService() {

		return commerceSubscriptionEntryLocalService;
	}

	/**
	 * Sets the commerce subscription entry local service.
	 *
	 * @param commerceSubscriptionEntryLocalService the commerce subscription entry local service
	 */
	public void setCommerceSubscriptionEntryLocalService(
		com.liferay.commerce.service.CommerceSubscriptionEntryLocalService
			commerceSubscriptionEntryLocalService) {

		this.commerceSubscriptionEntryLocalService =
			commerceSubscriptionEntryLocalService;
	}

	/**
	 * Returns the commerce subscription entry persistence.
	 *
	 * @return the commerce subscription entry persistence
	 */
	public CommerceSubscriptionEntryPersistence
		getCommerceSubscriptionEntryPersistence() {

		return commerceSubscriptionEntryPersistence;
	}

	/**
	 * Sets the commerce subscription entry persistence.
	 *
	 * @param commerceSubscriptionEntryPersistence the commerce subscription entry persistence
	 */
	public void setCommerceSubscriptionEntryPersistence(
		CommerceSubscriptionEntryPersistence
			commerceSubscriptionEntryPersistence) {

		this.commerceSubscriptionEntryPersistence =
			commerceSubscriptionEntryPersistence;
	}

	/**
	 * Returns the commerce subscription entry finder.
	 *
	 * @return the commerce subscription entry finder
	 */
	public CommerceSubscriptionEntryFinder
		getCommerceSubscriptionEntryFinder() {

		return commerceSubscriptionEntryFinder;
	}

	/**
	 * Sets the commerce subscription entry finder.
	 *
	 * @param commerceSubscriptionEntryFinder the commerce subscription entry finder
	 */
	public void setCommerceSubscriptionEntryFinder(
		CommerceSubscriptionEntryFinder commerceSubscriptionEntryFinder) {

		this.commerceSubscriptionEntryFinder = commerceSubscriptionEntryFinder;
	}

	/**
	 * Returns the cpd availability estimate local service.
	 *
	 * @return the cpd availability estimate local service
	 */
	public com.liferay.commerce.service.CPDAvailabilityEstimateLocalService
		getCPDAvailabilityEstimateLocalService() {

		return cpdAvailabilityEstimateLocalService;
	}

	/**
	 * Sets the cpd availability estimate local service.
	 *
	 * @param cpdAvailabilityEstimateLocalService the cpd availability estimate local service
	 */
	public void setCPDAvailabilityEstimateLocalService(
		com.liferay.commerce.service.CPDAvailabilityEstimateLocalService
			cpdAvailabilityEstimateLocalService) {

		this.cpdAvailabilityEstimateLocalService =
			cpdAvailabilityEstimateLocalService;
	}

	/**
	 * Returns the cpd availability estimate persistence.
	 *
	 * @return the cpd availability estimate persistence
	 */
	public CPDAvailabilityEstimatePersistence
		getCPDAvailabilityEstimatePersistence() {

		return cpdAvailabilityEstimatePersistence;
	}

	/**
	 * Sets the cpd availability estimate persistence.
	 *
	 * @param cpdAvailabilityEstimatePersistence the cpd availability estimate persistence
	 */
	public void setCPDAvailabilityEstimatePersistence(
		CPDAvailabilityEstimatePersistence cpdAvailabilityEstimatePersistence) {

		this.cpdAvailabilityEstimatePersistence =
			cpdAvailabilityEstimatePersistence;
	}

	/**
	 * Returns the cp definition inventory local service.
	 *
	 * @return the cp definition inventory local service
	 */
	public com.liferay.commerce.service.CPDefinitionInventoryLocalService
		getCPDefinitionInventoryLocalService() {

		return cpDefinitionInventoryLocalService;
	}

	/**
	 * Sets the cp definition inventory local service.
	 *
	 * @param cpDefinitionInventoryLocalService the cp definition inventory local service
	 */
	public void setCPDefinitionInventoryLocalService(
		com.liferay.commerce.service.CPDefinitionInventoryLocalService
			cpDefinitionInventoryLocalService) {

		this.cpDefinitionInventoryLocalService =
			cpDefinitionInventoryLocalService;
	}

	/**
	 * Returns the cp definition inventory persistence.
	 *
	 * @return the cp definition inventory persistence
	 */
	public CPDefinitionInventoryPersistence
		getCPDefinitionInventoryPersistence() {

		return cpDefinitionInventoryPersistence;
	}

	/**
	 * Sets the cp definition inventory persistence.
	 *
	 * @param cpDefinitionInventoryPersistence the cp definition inventory persistence
	 */
	public void setCPDefinitionInventoryPersistence(
		CPDefinitionInventoryPersistence cpDefinitionInventoryPersistence) {

		this.cpDefinitionInventoryPersistence =
			cpDefinitionInventoryPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService
		getCounterLocalService() {

		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService
			counterLocalService) {

		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService
		getClassNameLocalService() {

		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService
			classNameLocalService) {

		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {

		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService
		getResourceLocalService() {

		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService
			resourceLocalService) {

		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService
		getUserLocalService() {

		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {

		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register(
			"com.liferay.commerce.model.CommerceOrderType",
			commerceOrderTypeLocalService);

		_setLocalServiceUtilService(commerceOrderTypeLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.commerce.model.CommerceOrderType");

		_setLocalServiceUtilService(null);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return CommerceOrderTypeLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return CommerceOrderType.class;
	}

	protected String getModelClassName() {
		return CommerceOrderType.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource =
				commerceOrderTypePersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource, sql);

			sqlUpdate.update();
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	private void _setLocalServiceUtilService(
		CommerceOrderTypeLocalService commerceOrderTypeLocalService) {

		try {
			Field field =
				CommerceOrderTypeLocalServiceUtil.class.getDeclaredField(
					"_service");

			field.setAccessible(true);

			field.set(null, commerceOrderTypeLocalService);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@BeanReference(
		type = com.liferay.commerce.service.CommerceAddressLocalService.class
	)
	protected com.liferay.commerce.service.CommerceAddressLocalService
		commerceAddressLocalService;

	@BeanReference(
		type = com.liferay.commerce.service.CommerceAddressRestrictionLocalService.class
	)
	protected
		com.liferay.commerce.service.CommerceAddressRestrictionLocalService
			commerceAddressRestrictionLocalService;

	@BeanReference(type = CommerceAddressRestrictionPersistence.class)
	protected CommerceAddressRestrictionPersistence
		commerceAddressRestrictionPersistence;

	@BeanReference(
		type = com.liferay.commerce.service.CommerceAvailabilityEstimateLocalService.class
	)
	protected
		com.liferay.commerce.service.CommerceAvailabilityEstimateLocalService
			commerceAvailabilityEstimateLocalService;

	@BeanReference(type = CommerceAvailabilityEstimatePersistence.class)
	protected CommerceAvailabilityEstimatePersistence
		commerceAvailabilityEstimatePersistence;

	@BeanReference(
		type = com.liferay.commerce.service.CommerceOrderLocalService.class
	)
	protected com.liferay.commerce.service.CommerceOrderLocalService
		commerceOrderLocalService;

	@BeanReference(type = CommerceOrderPersistence.class)
	protected CommerceOrderPersistence commerceOrderPersistence;

	@BeanReference(type = CommerceOrderFinder.class)
	protected CommerceOrderFinder commerceOrderFinder;

	@BeanReference(
		type = com.liferay.commerce.service.CommerceOrderItemLocalService.class
	)
	protected com.liferay.commerce.service.CommerceOrderItemLocalService
		commerceOrderItemLocalService;

	@BeanReference(type = CommerceOrderItemPersistence.class)
	protected CommerceOrderItemPersistence commerceOrderItemPersistence;

	@BeanReference(type = CommerceOrderItemFinder.class)
	protected CommerceOrderItemFinder commerceOrderItemFinder;

	@BeanReference(
		type = com.liferay.commerce.service.CommerceOrderNoteLocalService.class
	)
	protected com.liferay.commerce.service.CommerceOrderNoteLocalService
		commerceOrderNoteLocalService;

	@BeanReference(type = CommerceOrderNotePersistence.class)
	protected CommerceOrderNotePersistence commerceOrderNotePersistence;

	@BeanReference(
		type = com.liferay.commerce.service.CommerceOrderPaymentLocalService.class
	)
	protected com.liferay.commerce.service.CommerceOrderPaymentLocalService
		commerceOrderPaymentLocalService;

	@BeanReference(type = CommerceOrderPaymentPersistence.class)
	protected CommerceOrderPaymentPersistence commerceOrderPaymentPersistence;

	@BeanReference(type = CommerceOrderTypeLocalService.class)
	protected CommerceOrderTypeLocalService commerceOrderTypeLocalService;

	@BeanReference(type = CommerceOrderTypePersistence.class)
	protected CommerceOrderTypePersistence commerceOrderTypePersistence;

	@BeanReference(
		type = com.liferay.commerce.service.CommerceOrderTypeRelLocalService.class
	)
	protected com.liferay.commerce.service.CommerceOrderTypeRelLocalService
		commerceOrderTypeRelLocalService;

	@BeanReference(type = CommerceOrderTypeRelPersistence.class)
	protected CommerceOrderTypeRelPersistence commerceOrderTypeRelPersistence;

	@BeanReference(
		type = com.liferay.commerce.service.CommerceShipmentLocalService.class
	)
	protected com.liferay.commerce.service.CommerceShipmentLocalService
		commerceShipmentLocalService;

	@BeanReference(type = CommerceShipmentPersistence.class)
	protected CommerceShipmentPersistence commerceShipmentPersistence;

	@BeanReference(type = CommerceShipmentFinder.class)
	protected CommerceShipmentFinder commerceShipmentFinder;

	@BeanReference(
		type = com.liferay.commerce.service.CommerceShipmentItemLocalService.class
	)
	protected com.liferay.commerce.service.CommerceShipmentItemLocalService
		commerceShipmentItemLocalService;

	@BeanReference(type = CommerceShipmentItemPersistence.class)
	protected CommerceShipmentItemPersistence commerceShipmentItemPersistence;

	@BeanReference(type = CommerceShipmentItemFinder.class)
	protected CommerceShipmentItemFinder commerceShipmentItemFinder;

	@BeanReference(
		type = com.liferay.commerce.service.CommerceShippingMethodLocalService.class
	)
	protected com.liferay.commerce.service.CommerceShippingMethodLocalService
		commerceShippingMethodLocalService;

	@BeanReference(type = CommerceShippingMethodPersistence.class)
	protected CommerceShippingMethodPersistence
		commerceShippingMethodPersistence;

	@BeanReference(
		type = com.liferay.commerce.service.CommerceShippingOptionAccountEntryRelLocalService.class
	)
	protected com.liferay.commerce.service.
		CommerceShippingOptionAccountEntryRelLocalService
			commerceShippingOptionAccountEntryRelLocalService;

	@BeanReference(
		type = CommerceShippingOptionAccountEntryRelPersistence.class
	)
	protected CommerceShippingOptionAccountEntryRelPersistence
		commerceShippingOptionAccountEntryRelPersistence;

	@BeanReference(
		type = com.liferay.commerce.service.CommerceSubscriptionEntryLocalService.class
	)
	protected com.liferay.commerce.service.CommerceSubscriptionEntryLocalService
		commerceSubscriptionEntryLocalService;

	@BeanReference(type = CommerceSubscriptionEntryPersistence.class)
	protected CommerceSubscriptionEntryPersistence
		commerceSubscriptionEntryPersistence;

	@BeanReference(type = CommerceSubscriptionEntryFinder.class)
	protected CommerceSubscriptionEntryFinder commerceSubscriptionEntryFinder;

	@BeanReference(
		type = com.liferay.commerce.service.CPDAvailabilityEstimateLocalService.class
	)
	protected com.liferay.commerce.service.CPDAvailabilityEstimateLocalService
		cpdAvailabilityEstimateLocalService;

	@BeanReference(type = CPDAvailabilityEstimatePersistence.class)
	protected CPDAvailabilityEstimatePersistence
		cpdAvailabilityEstimatePersistence;

	@BeanReference(
		type = com.liferay.commerce.service.CPDefinitionInventoryLocalService.class
	)
	protected com.liferay.commerce.service.CPDefinitionInventoryLocalService
		cpDefinitionInventoryLocalService;

	@BeanReference(type = CPDefinitionInventoryPersistence.class)
	protected CPDefinitionInventoryPersistence cpDefinitionInventoryPersistence;

	@ServiceReference(
		type = com.liferay.counter.kernel.service.CounterLocalService.class
	)
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.ClassNameLocalService.class
	)
	protected com.liferay.portal.kernel.service.ClassNameLocalService
		classNameLocalService;

	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.ResourceLocalService.class
	)
	protected com.liferay.portal.kernel.service.ResourceLocalService
		resourceLocalService;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.UserLocalService.class
	)
	protected com.liferay.portal.kernel.service.UserLocalService
		userLocalService;

	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;

	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry
		persistedModelLocalServiceRegistry;

}