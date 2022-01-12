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

package com.liferay.commerce.product.service.base;

import com.liferay.commerce.product.model.CPInstanceOptionValueRel;
import com.liferay.commerce.product.service.CPInstanceOptionValueRelLocalService;
import com.liferay.commerce.product.service.CPInstanceOptionValueRelLocalServiceUtil;
import com.liferay.commerce.product.service.persistence.CPAttachmentFileEntryFinder;
import com.liferay.commerce.product.service.persistence.CPAttachmentFileEntryPersistence;
import com.liferay.commerce.product.service.persistence.CPDefinitionFinder;
import com.liferay.commerce.product.service.persistence.CPDefinitionLinkPersistence;
import com.liferay.commerce.product.service.persistence.CPDefinitionLocalizationPersistence;
import com.liferay.commerce.product.service.persistence.CPDefinitionOptionRelPersistence;
import com.liferay.commerce.product.service.persistence.CPDefinitionOptionValueRelPersistence;
import com.liferay.commerce.product.service.persistence.CPDefinitionPersistence;
import com.liferay.commerce.product.service.persistence.CPDefinitionSpecificationOptionValuePersistence;
import com.liferay.commerce.product.service.persistence.CPDisplayLayoutPersistence;
import com.liferay.commerce.product.service.persistence.CPInstanceFinder;
import com.liferay.commerce.product.service.persistence.CPInstanceOptionValueRelFinder;
import com.liferay.commerce.product.service.persistence.CPInstanceOptionValueRelPersistence;
import com.liferay.commerce.product.service.persistence.CPInstancePersistence;
import com.liferay.commerce.product.service.persistence.CPMeasurementUnitPersistence;
import com.liferay.commerce.product.service.persistence.CPOptionCategoryPersistence;
import com.liferay.commerce.product.service.persistence.CPOptionPersistence;
import com.liferay.commerce.product.service.persistence.CPOptionValuePersistence;
import com.liferay.commerce.product.service.persistence.CPSpecificationOptionPersistence;
import com.liferay.commerce.product.service.persistence.CPTaxCategoryFinder;
import com.liferay.commerce.product.service.persistence.CPTaxCategoryPersistence;
import com.liferay.commerce.product.service.persistence.CProductPersistence;
import com.liferay.commerce.product.service.persistence.CommerceCatalogPersistence;
import com.liferay.commerce.product.service.persistence.CommerceChannelPersistence;
import com.liferay.commerce.product.service.persistence.CommerceChannelRelFinder;
import com.liferay.commerce.product.service.persistence.CommerceChannelRelPersistence;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the cp instance option value rel local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.commerce.product.service.impl.CPInstanceOptionValueRelLocalServiceImpl}.
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.product.service.impl.CPInstanceOptionValueRelLocalServiceImpl
 * @generated
 */
public abstract class CPInstanceOptionValueRelLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements CPInstanceOptionValueRelLocalService,
			   CTService<CPInstanceOptionValueRel>, IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>CPInstanceOptionValueRelLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>CPInstanceOptionValueRelLocalServiceUtil</code>.
	 */

	/**
	 * Adds the cp instance option value rel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPInstanceOptionValueRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpInstanceOptionValueRel the cp instance option value rel
	 * @return the cp instance option value rel that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPInstanceOptionValueRel addCPInstanceOptionValueRel(
		CPInstanceOptionValueRel cpInstanceOptionValueRel) {

		cpInstanceOptionValueRel.setNew(true);

		return cpInstanceOptionValueRelPersistence.update(
			cpInstanceOptionValueRel);
	}

	/**
	 * Creates a new cp instance option value rel with the primary key. Does not add the cp instance option value rel to the database.
	 *
	 * @param CPInstanceOptionValueRelId the primary key for the new cp instance option value rel
	 * @return the new cp instance option value rel
	 */
	@Override
	@Transactional(enabled = false)
	public CPInstanceOptionValueRel createCPInstanceOptionValueRel(
		long CPInstanceOptionValueRelId) {

		return cpInstanceOptionValueRelPersistence.create(
			CPInstanceOptionValueRelId);
	}

	/**
	 * Deletes the cp instance option value rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPInstanceOptionValueRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param CPInstanceOptionValueRelId the primary key of the cp instance option value rel
	 * @return the cp instance option value rel that was removed
	 * @throws PortalException if a cp instance option value rel with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CPInstanceOptionValueRel deleteCPInstanceOptionValueRel(
			long CPInstanceOptionValueRelId)
		throws PortalException {

		return cpInstanceOptionValueRelPersistence.remove(
			CPInstanceOptionValueRelId);
	}

	/**
	 * Deletes the cp instance option value rel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPInstanceOptionValueRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpInstanceOptionValueRel the cp instance option value rel
	 * @return the cp instance option value rel that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CPInstanceOptionValueRel deleteCPInstanceOptionValueRel(
		CPInstanceOptionValueRel cpInstanceOptionValueRel) {

		return cpInstanceOptionValueRelPersistence.remove(
			cpInstanceOptionValueRel);
	}

	@Override
	public <T> T dslQuery(DSLQuery dslQuery) {
		return cpInstanceOptionValueRelPersistence.dslQuery(dslQuery);
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
			CPInstanceOptionValueRel.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return cpInstanceOptionValueRelPersistence.findWithDynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPInstanceOptionValueRelModelImpl</code>.
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

		return cpInstanceOptionValueRelPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPInstanceOptionValueRelModelImpl</code>.
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

		return cpInstanceOptionValueRelPersistence.findWithDynamicQuery(
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
		return cpInstanceOptionValueRelPersistence.countWithDynamicQuery(
			dynamicQuery);
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

		return cpInstanceOptionValueRelPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public CPInstanceOptionValueRel fetchCPInstanceOptionValueRel(
		long CPInstanceOptionValueRelId) {

		return cpInstanceOptionValueRelPersistence.fetchByPrimaryKey(
			CPInstanceOptionValueRelId);
	}

	/**
	 * Returns the cp instance option value rel matching the UUID and group.
	 *
	 * @param uuid the cp instance option value rel's UUID
	 * @param groupId the primary key of the group
	 * @return the matching cp instance option value rel, or <code>null</code> if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel
		fetchCPInstanceOptionValueRelByUuidAndGroupId(
			String uuid, long groupId) {

		return cpInstanceOptionValueRelPersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the cp instance option value rel with the primary key.
	 *
	 * @param CPInstanceOptionValueRelId the primary key of the cp instance option value rel
	 * @return the cp instance option value rel
	 * @throws PortalException if a cp instance option value rel with the primary key could not be found
	 */
	@Override
	public CPInstanceOptionValueRel getCPInstanceOptionValueRel(
			long CPInstanceOptionValueRelId)
		throws PortalException {

		return cpInstanceOptionValueRelPersistence.findByPrimaryKey(
			CPInstanceOptionValueRelId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(
			cpInstanceOptionValueRelLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CPInstanceOptionValueRel.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"CPInstanceOptionValueRelId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			cpInstanceOptionValueRelLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(
			CPInstanceOptionValueRel.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"CPInstanceOptionValueRelId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(
			cpInstanceOptionValueRelLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CPInstanceOptionValueRel.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"CPInstanceOptionValueRelId");
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
					portletDataContext.addDateRangeCriteria(
						dynamicQuery, "modifiedDate");
				}

			});

		exportActionableDynamicQuery.setCompanyId(
			portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CPInstanceOptionValueRel>() {

				@Override
				public void performAction(
						CPInstanceOptionValueRel cpInstanceOptionValueRel)
					throws PortalException {

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, cpInstanceOptionValueRel);
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				PortalUtil.getClassNameId(
					CPInstanceOptionValueRel.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return cpInstanceOptionValueRelPersistence.create(
			((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return cpInstanceOptionValueRelLocalService.
			deleteCPInstanceOptionValueRel(
				(CPInstanceOptionValueRel)persistedModel);
	}

	@Override
	public BasePersistence<CPInstanceOptionValueRel> getBasePersistence() {
		return cpInstanceOptionValueRelPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return cpInstanceOptionValueRelPersistence.findByPrimaryKey(
			primaryKeyObj);
	}

	/**
	 * Returns all the cp instance option value rels matching the UUID and company.
	 *
	 * @param uuid the UUID of the cp instance option value rels
	 * @param companyId the primary key of the company
	 * @return the matching cp instance option value rels, or an empty list if no matches were found
	 */
	@Override
	public List<CPInstanceOptionValueRel>
		getCPInstanceOptionValueRelsByUuidAndCompanyId(
			String uuid, long companyId) {

		return cpInstanceOptionValueRelPersistence.findByUuid_C(
			uuid, companyId);
	}

	/**
	 * Returns a range of cp instance option value rels matching the UUID and company.
	 *
	 * @param uuid the UUID of the cp instance option value rels
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching cp instance option value rels, or an empty list if no matches were found
	 */
	@Override
	public List<CPInstanceOptionValueRel>
		getCPInstanceOptionValueRelsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			OrderByComparator<CPInstanceOptionValueRel> orderByComparator) {

		return cpInstanceOptionValueRelPersistence.findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the cp instance option value rel matching the UUID and group.
	 *
	 * @param uuid the cp instance option value rel's UUID
	 * @param groupId the primary key of the group
	 * @return the matching cp instance option value rel
	 * @throws PortalException if a matching cp instance option value rel could not be found
	 */
	@Override
	public CPInstanceOptionValueRel getCPInstanceOptionValueRelByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return cpInstanceOptionValueRelPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the cp instance option value rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPInstanceOptionValueRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp instance option value rels
	 * @param end the upper bound of the range of cp instance option value rels (not inclusive)
	 * @return the range of cp instance option value rels
	 */
	@Override
	public List<CPInstanceOptionValueRel> getCPInstanceOptionValueRels(
		int start, int end) {

		return cpInstanceOptionValueRelPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of cp instance option value rels.
	 *
	 * @return the number of cp instance option value rels
	 */
	@Override
	public int getCPInstanceOptionValueRelsCount() {
		return cpInstanceOptionValueRelPersistence.countAll();
	}

	/**
	 * Updates the cp instance option value rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPInstanceOptionValueRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpInstanceOptionValueRel the cp instance option value rel
	 * @return the cp instance option value rel that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPInstanceOptionValueRel updateCPInstanceOptionValueRel(
		CPInstanceOptionValueRel cpInstanceOptionValueRel) {

		return cpInstanceOptionValueRelPersistence.update(
			cpInstanceOptionValueRel);
	}

	/**
	 * Returns the commerce catalog local service.
	 *
	 * @return the commerce catalog local service
	 */
	public com.liferay.commerce.product.service.CommerceCatalogLocalService
		getCommerceCatalogLocalService() {

		return commerceCatalogLocalService;
	}

	/**
	 * Sets the commerce catalog local service.
	 *
	 * @param commerceCatalogLocalService the commerce catalog local service
	 */
	public void setCommerceCatalogLocalService(
		com.liferay.commerce.product.service.CommerceCatalogLocalService
			commerceCatalogLocalService) {

		this.commerceCatalogLocalService = commerceCatalogLocalService;
	}

	/**
	 * Returns the commerce catalog persistence.
	 *
	 * @return the commerce catalog persistence
	 */
	public CommerceCatalogPersistence getCommerceCatalogPersistence() {
		return commerceCatalogPersistence;
	}

	/**
	 * Sets the commerce catalog persistence.
	 *
	 * @param commerceCatalogPersistence the commerce catalog persistence
	 */
	public void setCommerceCatalogPersistence(
		CommerceCatalogPersistence commerceCatalogPersistence) {

		this.commerceCatalogPersistence = commerceCatalogPersistence;
	}

	/**
	 * Returns the commerce channel local service.
	 *
	 * @return the commerce channel local service
	 */
	public com.liferay.commerce.product.service.CommerceChannelLocalService
		getCommerceChannelLocalService() {

		return commerceChannelLocalService;
	}

	/**
	 * Sets the commerce channel local service.
	 *
	 * @param commerceChannelLocalService the commerce channel local service
	 */
	public void setCommerceChannelLocalService(
		com.liferay.commerce.product.service.CommerceChannelLocalService
			commerceChannelLocalService) {

		this.commerceChannelLocalService = commerceChannelLocalService;
	}

	/**
	 * Returns the commerce channel persistence.
	 *
	 * @return the commerce channel persistence
	 */
	public CommerceChannelPersistence getCommerceChannelPersistence() {
		return commerceChannelPersistence;
	}

	/**
	 * Sets the commerce channel persistence.
	 *
	 * @param commerceChannelPersistence the commerce channel persistence
	 */
	public void setCommerceChannelPersistence(
		CommerceChannelPersistence commerceChannelPersistence) {

		this.commerceChannelPersistence = commerceChannelPersistence;
	}

	/**
	 * Returns the commerce channel rel local service.
	 *
	 * @return the commerce channel rel local service
	 */
	public com.liferay.commerce.product.service.CommerceChannelRelLocalService
		getCommerceChannelRelLocalService() {

		return commerceChannelRelLocalService;
	}

	/**
	 * Sets the commerce channel rel local service.
	 *
	 * @param commerceChannelRelLocalService the commerce channel rel local service
	 */
	public void setCommerceChannelRelLocalService(
		com.liferay.commerce.product.service.CommerceChannelRelLocalService
			commerceChannelRelLocalService) {

		this.commerceChannelRelLocalService = commerceChannelRelLocalService;
	}

	/**
	 * Returns the commerce channel rel persistence.
	 *
	 * @return the commerce channel rel persistence
	 */
	public CommerceChannelRelPersistence getCommerceChannelRelPersistence() {
		return commerceChannelRelPersistence;
	}

	/**
	 * Sets the commerce channel rel persistence.
	 *
	 * @param commerceChannelRelPersistence the commerce channel rel persistence
	 */
	public void setCommerceChannelRelPersistence(
		CommerceChannelRelPersistence commerceChannelRelPersistence) {

		this.commerceChannelRelPersistence = commerceChannelRelPersistence;
	}

	/**
	 * Returns the commerce channel rel finder.
	 *
	 * @return the commerce channel rel finder
	 */
	public CommerceChannelRelFinder getCommerceChannelRelFinder() {
		return commerceChannelRelFinder;
	}

	/**
	 * Sets the commerce channel rel finder.
	 *
	 * @param commerceChannelRelFinder the commerce channel rel finder
	 */
	public void setCommerceChannelRelFinder(
		CommerceChannelRelFinder commerceChannelRelFinder) {

		this.commerceChannelRelFinder = commerceChannelRelFinder;
	}

	/**
	 * Returns the cp attachment file entry local service.
	 *
	 * @return the cp attachment file entry local service
	 */
	public
		com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService
			getCPAttachmentFileEntryLocalService() {

		return cpAttachmentFileEntryLocalService;
	}

	/**
	 * Sets the cp attachment file entry local service.
	 *
	 * @param cpAttachmentFileEntryLocalService the cp attachment file entry local service
	 */
	public void setCPAttachmentFileEntryLocalService(
		com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService
			cpAttachmentFileEntryLocalService) {

		this.cpAttachmentFileEntryLocalService =
			cpAttachmentFileEntryLocalService;
	}

	/**
	 * Returns the cp attachment file entry persistence.
	 *
	 * @return the cp attachment file entry persistence
	 */
	public CPAttachmentFileEntryPersistence
		getCPAttachmentFileEntryPersistence() {

		return cpAttachmentFileEntryPersistence;
	}

	/**
	 * Sets the cp attachment file entry persistence.
	 *
	 * @param cpAttachmentFileEntryPersistence the cp attachment file entry persistence
	 */
	public void setCPAttachmentFileEntryPersistence(
		CPAttachmentFileEntryPersistence cpAttachmentFileEntryPersistence) {

		this.cpAttachmentFileEntryPersistence =
			cpAttachmentFileEntryPersistence;
	}

	/**
	 * Returns the cp attachment file entry finder.
	 *
	 * @return the cp attachment file entry finder
	 */
	public CPAttachmentFileEntryFinder getCPAttachmentFileEntryFinder() {
		return cpAttachmentFileEntryFinder;
	}

	/**
	 * Sets the cp attachment file entry finder.
	 *
	 * @param cpAttachmentFileEntryFinder the cp attachment file entry finder
	 */
	public void setCPAttachmentFileEntryFinder(
		CPAttachmentFileEntryFinder cpAttachmentFileEntryFinder) {

		this.cpAttachmentFileEntryFinder = cpAttachmentFileEntryFinder;
	}

	/**
	 * Returns the cp definition local service.
	 *
	 * @return the cp definition local service
	 */
	public com.liferay.commerce.product.service.CPDefinitionLocalService
		getCPDefinitionLocalService() {

		return cpDefinitionLocalService;
	}

	/**
	 * Sets the cp definition local service.
	 *
	 * @param cpDefinitionLocalService the cp definition local service
	 */
	public void setCPDefinitionLocalService(
		com.liferay.commerce.product.service.CPDefinitionLocalService
			cpDefinitionLocalService) {

		this.cpDefinitionLocalService = cpDefinitionLocalService;
	}

	/**
	 * Returns the cp definition persistence.
	 *
	 * @return the cp definition persistence
	 */
	public CPDefinitionPersistence getCPDefinitionPersistence() {
		return cpDefinitionPersistence;
	}

	/**
	 * Sets the cp definition persistence.
	 *
	 * @param cpDefinitionPersistence the cp definition persistence
	 */
	public void setCPDefinitionPersistence(
		CPDefinitionPersistence cpDefinitionPersistence) {

		this.cpDefinitionPersistence = cpDefinitionPersistence;
	}

	/**
	 * Returns the cp definition finder.
	 *
	 * @return the cp definition finder
	 */
	public CPDefinitionFinder getCPDefinitionFinder() {
		return cpDefinitionFinder;
	}

	/**
	 * Sets the cp definition finder.
	 *
	 * @param cpDefinitionFinder the cp definition finder
	 */
	public void setCPDefinitionFinder(CPDefinitionFinder cpDefinitionFinder) {
		this.cpDefinitionFinder = cpDefinitionFinder;
	}

	/**
	 * Returns the cp definition link local service.
	 *
	 * @return the cp definition link local service
	 */
	public com.liferay.commerce.product.service.CPDefinitionLinkLocalService
		getCPDefinitionLinkLocalService() {

		return cpDefinitionLinkLocalService;
	}

	/**
	 * Sets the cp definition link local service.
	 *
	 * @param cpDefinitionLinkLocalService the cp definition link local service
	 */
	public void setCPDefinitionLinkLocalService(
		com.liferay.commerce.product.service.CPDefinitionLinkLocalService
			cpDefinitionLinkLocalService) {

		this.cpDefinitionLinkLocalService = cpDefinitionLinkLocalService;
	}

	/**
	 * Returns the cp definition link persistence.
	 *
	 * @return the cp definition link persistence
	 */
	public CPDefinitionLinkPersistence getCPDefinitionLinkPersistence() {
		return cpDefinitionLinkPersistence;
	}

	/**
	 * Sets the cp definition link persistence.
	 *
	 * @param cpDefinitionLinkPersistence the cp definition link persistence
	 */
	public void setCPDefinitionLinkPersistence(
		CPDefinitionLinkPersistence cpDefinitionLinkPersistence) {

		this.cpDefinitionLinkPersistence = cpDefinitionLinkPersistence;
	}

	/**
	 * Returns the cp definition localization persistence.
	 *
	 * @return the cp definition localization persistence
	 */
	public CPDefinitionLocalizationPersistence
		getCPDefinitionLocalizationPersistence() {

		return cpDefinitionLocalizationPersistence;
	}

	/**
	 * Sets the cp definition localization persistence.
	 *
	 * @param cpDefinitionLocalizationPersistence the cp definition localization persistence
	 */
	public void setCPDefinitionLocalizationPersistence(
		CPDefinitionLocalizationPersistence
			cpDefinitionLocalizationPersistence) {

		this.cpDefinitionLocalizationPersistence =
			cpDefinitionLocalizationPersistence;
	}

	/**
	 * Returns the cp definition option rel local service.
	 *
	 * @return the cp definition option rel local service
	 */
	public
		com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService
			getCPDefinitionOptionRelLocalService() {

		return cpDefinitionOptionRelLocalService;
	}

	/**
	 * Sets the cp definition option rel local service.
	 *
	 * @param cpDefinitionOptionRelLocalService the cp definition option rel local service
	 */
	public void setCPDefinitionOptionRelLocalService(
		com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService
			cpDefinitionOptionRelLocalService) {

		this.cpDefinitionOptionRelLocalService =
			cpDefinitionOptionRelLocalService;
	}

	/**
	 * Returns the cp definition option rel persistence.
	 *
	 * @return the cp definition option rel persistence
	 */
	public CPDefinitionOptionRelPersistence
		getCPDefinitionOptionRelPersistence() {

		return cpDefinitionOptionRelPersistence;
	}

	/**
	 * Sets the cp definition option rel persistence.
	 *
	 * @param cpDefinitionOptionRelPersistence the cp definition option rel persistence
	 */
	public void setCPDefinitionOptionRelPersistence(
		CPDefinitionOptionRelPersistence cpDefinitionOptionRelPersistence) {

		this.cpDefinitionOptionRelPersistence =
			cpDefinitionOptionRelPersistence;
	}

	/**
	 * Returns the cp definition option value rel local service.
	 *
	 * @return the cp definition option value rel local service
	 */
	public
		com.liferay.commerce.product.service.
			CPDefinitionOptionValueRelLocalService
				getCPDefinitionOptionValueRelLocalService() {

		return cpDefinitionOptionValueRelLocalService;
	}

	/**
	 * Sets the cp definition option value rel local service.
	 *
	 * @param cpDefinitionOptionValueRelLocalService the cp definition option value rel local service
	 */
	public void setCPDefinitionOptionValueRelLocalService(
		com.liferay.commerce.product.service.
			CPDefinitionOptionValueRelLocalService
				cpDefinitionOptionValueRelLocalService) {

		this.cpDefinitionOptionValueRelLocalService =
			cpDefinitionOptionValueRelLocalService;
	}

	/**
	 * Returns the cp definition option value rel persistence.
	 *
	 * @return the cp definition option value rel persistence
	 */
	public CPDefinitionOptionValueRelPersistence
		getCPDefinitionOptionValueRelPersistence() {

		return cpDefinitionOptionValueRelPersistence;
	}

	/**
	 * Sets the cp definition option value rel persistence.
	 *
	 * @param cpDefinitionOptionValueRelPersistence the cp definition option value rel persistence
	 */
	public void setCPDefinitionOptionValueRelPersistence(
		CPDefinitionOptionValueRelPersistence
			cpDefinitionOptionValueRelPersistence) {

		this.cpDefinitionOptionValueRelPersistence =
			cpDefinitionOptionValueRelPersistence;
	}

	/**
	 * Returns the cp definition specification option value local service.
	 *
	 * @return the cp definition specification option value local service
	 */
	public com.liferay.commerce.product.service.
		CPDefinitionSpecificationOptionValueLocalService
			getCPDefinitionSpecificationOptionValueLocalService() {

		return cpDefinitionSpecificationOptionValueLocalService;
	}

	/**
	 * Sets the cp definition specification option value local service.
	 *
	 * @param cpDefinitionSpecificationOptionValueLocalService the cp definition specification option value local service
	 */
	public void setCPDefinitionSpecificationOptionValueLocalService(
		com.liferay.commerce.product.service.
			CPDefinitionSpecificationOptionValueLocalService
				cpDefinitionSpecificationOptionValueLocalService) {

		this.cpDefinitionSpecificationOptionValueLocalService =
			cpDefinitionSpecificationOptionValueLocalService;
	}

	/**
	 * Returns the cp definition specification option value persistence.
	 *
	 * @return the cp definition specification option value persistence
	 */
	public CPDefinitionSpecificationOptionValuePersistence
		getCPDefinitionSpecificationOptionValuePersistence() {

		return cpDefinitionSpecificationOptionValuePersistence;
	}

	/**
	 * Sets the cp definition specification option value persistence.
	 *
	 * @param cpDefinitionSpecificationOptionValuePersistence the cp definition specification option value persistence
	 */
	public void setCPDefinitionSpecificationOptionValuePersistence(
		CPDefinitionSpecificationOptionValuePersistence
			cpDefinitionSpecificationOptionValuePersistence) {

		this.cpDefinitionSpecificationOptionValuePersistence =
			cpDefinitionSpecificationOptionValuePersistence;
	}

	/**
	 * Returns the cp display layout local service.
	 *
	 * @return the cp display layout local service
	 */
	public com.liferay.commerce.product.service.CPDisplayLayoutLocalService
		getCPDisplayLayoutLocalService() {

		return cpDisplayLayoutLocalService;
	}

	/**
	 * Sets the cp display layout local service.
	 *
	 * @param cpDisplayLayoutLocalService the cp display layout local service
	 */
	public void setCPDisplayLayoutLocalService(
		com.liferay.commerce.product.service.CPDisplayLayoutLocalService
			cpDisplayLayoutLocalService) {

		this.cpDisplayLayoutLocalService = cpDisplayLayoutLocalService;
	}

	/**
	 * Returns the cp display layout persistence.
	 *
	 * @return the cp display layout persistence
	 */
	public CPDisplayLayoutPersistence getCPDisplayLayoutPersistence() {
		return cpDisplayLayoutPersistence;
	}

	/**
	 * Sets the cp display layout persistence.
	 *
	 * @param cpDisplayLayoutPersistence the cp display layout persistence
	 */
	public void setCPDisplayLayoutPersistence(
		CPDisplayLayoutPersistence cpDisplayLayoutPersistence) {

		this.cpDisplayLayoutPersistence = cpDisplayLayoutPersistence;
	}

	/**
	 * Returns the cp instance local service.
	 *
	 * @return the cp instance local service
	 */
	public com.liferay.commerce.product.service.CPInstanceLocalService
		getCPInstanceLocalService() {

		return cpInstanceLocalService;
	}

	/**
	 * Sets the cp instance local service.
	 *
	 * @param cpInstanceLocalService the cp instance local service
	 */
	public void setCPInstanceLocalService(
		com.liferay.commerce.product.service.CPInstanceLocalService
			cpInstanceLocalService) {

		this.cpInstanceLocalService = cpInstanceLocalService;
	}

	/**
	 * Returns the cp instance persistence.
	 *
	 * @return the cp instance persistence
	 */
	public CPInstancePersistence getCPInstancePersistence() {
		return cpInstancePersistence;
	}

	/**
	 * Sets the cp instance persistence.
	 *
	 * @param cpInstancePersistence the cp instance persistence
	 */
	public void setCPInstancePersistence(
		CPInstancePersistence cpInstancePersistence) {

		this.cpInstancePersistence = cpInstancePersistence;
	}

	/**
	 * Returns the cp instance finder.
	 *
	 * @return the cp instance finder
	 */
	public CPInstanceFinder getCPInstanceFinder() {
		return cpInstanceFinder;
	}

	/**
	 * Sets the cp instance finder.
	 *
	 * @param cpInstanceFinder the cp instance finder
	 */
	public void setCPInstanceFinder(CPInstanceFinder cpInstanceFinder) {
		this.cpInstanceFinder = cpInstanceFinder;
	}

	/**
	 * Returns the cp instance option value rel local service.
	 *
	 * @return the cp instance option value rel local service
	 */
	public CPInstanceOptionValueRelLocalService
		getCPInstanceOptionValueRelLocalService() {

		return cpInstanceOptionValueRelLocalService;
	}

	/**
	 * Sets the cp instance option value rel local service.
	 *
	 * @param cpInstanceOptionValueRelLocalService the cp instance option value rel local service
	 */
	public void setCPInstanceOptionValueRelLocalService(
		CPInstanceOptionValueRelLocalService
			cpInstanceOptionValueRelLocalService) {

		this.cpInstanceOptionValueRelLocalService =
			cpInstanceOptionValueRelLocalService;
	}

	/**
	 * Returns the cp instance option value rel persistence.
	 *
	 * @return the cp instance option value rel persistence
	 */
	public CPInstanceOptionValueRelPersistence
		getCPInstanceOptionValueRelPersistence() {

		return cpInstanceOptionValueRelPersistence;
	}

	/**
	 * Sets the cp instance option value rel persistence.
	 *
	 * @param cpInstanceOptionValueRelPersistence the cp instance option value rel persistence
	 */
	public void setCPInstanceOptionValueRelPersistence(
		CPInstanceOptionValueRelPersistence
			cpInstanceOptionValueRelPersistence) {

		this.cpInstanceOptionValueRelPersistence =
			cpInstanceOptionValueRelPersistence;
	}

	/**
	 * Returns the cp instance option value rel finder.
	 *
	 * @return the cp instance option value rel finder
	 */
	public CPInstanceOptionValueRelFinder getCPInstanceOptionValueRelFinder() {
		return cpInstanceOptionValueRelFinder;
	}

	/**
	 * Sets the cp instance option value rel finder.
	 *
	 * @param cpInstanceOptionValueRelFinder the cp instance option value rel finder
	 */
	public void setCPInstanceOptionValueRelFinder(
		CPInstanceOptionValueRelFinder cpInstanceOptionValueRelFinder) {

		this.cpInstanceOptionValueRelFinder = cpInstanceOptionValueRelFinder;
	}

	/**
	 * Returns the cp measurement unit local service.
	 *
	 * @return the cp measurement unit local service
	 */
	public com.liferay.commerce.product.service.CPMeasurementUnitLocalService
		getCPMeasurementUnitLocalService() {

		return cpMeasurementUnitLocalService;
	}

	/**
	 * Sets the cp measurement unit local service.
	 *
	 * @param cpMeasurementUnitLocalService the cp measurement unit local service
	 */
	public void setCPMeasurementUnitLocalService(
		com.liferay.commerce.product.service.CPMeasurementUnitLocalService
			cpMeasurementUnitLocalService) {

		this.cpMeasurementUnitLocalService = cpMeasurementUnitLocalService;
	}

	/**
	 * Returns the cp measurement unit persistence.
	 *
	 * @return the cp measurement unit persistence
	 */
	public CPMeasurementUnitPersistence getCPMeasurementUnitPersistence() {
		return cpMeasurementUnitPersistence;
	}

	/**
	 * Sets the cp measurement unit persistence.
	 *
	 * @param cpMeasurementUnitPersistence the cp measurement unit persistence
	 */
	public void setCPMeasurementUnitPersistence(
		CPMeasurementUnitPersistence cpMeasurementUnitPersistence) {

		this.cpMeasurementUnitPersistence = cpMeasurementUnitPersistence;
	}

	/**
	 * Returns the cp option local service.
	 *
	 * @return the cp option local service
	 */
	public com.liferay.commerce.product.service.CPOptionLocalService
		getCPOptionLocalService() {

		return cpOptionLocalService;
	}

	/**
	 * Sets the cp option local service.
	 *
	 * @param cpOptionLocalService the cp option local service
	 */
	public void setCPOptionLocalService(
		com.liferay.commerce.product.service.CPOptionLocalService
			cpOptionLocalService) {

		this.cpOptionLocalService = cpOptionLocalService;
	}

	/**
	 * Returns the cp option persistence.
	 *
	 * @return the cp option persistence
	 */
	public CPOptionPersistence getCPOptionPersistence() {
		return cpOptionPersistence;
	}

	/**
	 * Sets the cp option persistence.
	 *
	 * @param cpOptionPersistence the cp option persistence
	 */
	public void setCPOptionPersistence(
		CPOptionPersistence cpOptionPersistence) {

		this.cpOptionPersistence = cpOptionPersistence;
	}

	/**
	 * Returns the cp option category local service.
	 *
	 * @return the cp option category local service
	 */
	public com.liferay.commerce.product.service.CPOptionCategoryLocalService
		getCPOptionCategoryLocalService() {

		return cpOptionCategoryLocalService;
	}

	/**
	 * Sets the cp option category local service.
	 *
	 * @param cpOptionCategoryLocalService the cp option category local service
	 */
	public void setCPOptionCategoryLocalService(
		com.liferay.commerce.product.service.CPOptionCategoryLocalService
			cpOptionCategoryLocalService) {

		this.cpOptionCategoryLocalService = cpOptionCategoryLocalService;
	}

	/**
	 * Returns the cp option category persistence.
	 *
	 * @return the cp option category persistence
	 */
	public CPOptionCategoryPersistence getCPOptionCategoryPersistence() {
		return cpOptionCategoryPersistence;
	}

	/**
	 * Sets the cp option category persistence.
	 *
	 * @param cpOptionCategoryPersistence the cp option category persistence
	 */
	public void setCPOptionCategoryPersistence(
		CPOptionCategoryPersistence cpOptionCategoryPersistence) {

		this.cpOptionCategoryPersistence = cpOptionCategoryPersistence;
	}

	/**
	 * Returns the cp option value local service.
	 *
	 * @return the cp option value local service
	 */
	public com.liferay.commerce.product.service.CPOptionValueLocalService
		getCPOptionValueLocalService() {

		return cpOptionValueLocalService;
	}

	/**
	 * Sets the cp option value local service.
	 *
	 * @param cpOptionValueLocalService the cp option value local service
	 */
	public void setCPOptionValueLocalService(
		com.liferay.commerce.product.service.CPOptionValueLocalService
			cpOptionValueLocalService) {

		this.cpOptionValueLocalService = cpOptionValueLocalService;
	}

	/**
	 * Returns the cp option value persistence.
	 *
	 * @return the cp option value persistence
	 */
	public CPOptionValuePersistence getCPOptionValuePersistence() {
		return cpOptionValuePersistence;
	}

	/**
	 * Sets the cp option value persistence.
	 *
	 * @param cpOptionValuePersistence the cp option value persistence
	 */
	public void setCPOptionValuePersistence(
		CPOptionValuePersistence cpOptionValuePersistence) {

		this.cpOptionValuePersistence = cpOptionValuePersistence;
	}

	/**
	 * Returns the c product local service.
	 *
	 * @return the c product local service
	 */
	public com.liferay.commerce.product.service.CProductLocalService
		getCProductLocalService() {

		return cProductLocalService;
	}

	/**
	 * Sets the c product local service.
	 *
	 * @param cProductLocalService the c product local service
	 */
	public void setCProductLocalService(
		com.liferay.commerce.product.service.CProductLocalService
			cProductLocalService) {

		this.cProductLocalService = cProductLocalService;
	}

	/**
	 * Returns the c product persistence.
	 *
	 * @return the c product persistence
	 */
	public CProductPersistence getCProductPersistence() {
		return cProductPersistence;
	}

	/**
	 * Sets the c product persistence.
	 *
	 * @param cProductPersistence the c product persistence
	 */
	public void setCProductPersistence(
		CProductPersistence cProductPersistence) {

		this.cProductPersistence = cProductPersistence;
	}

	/**
	 * Returns the cp specification option local service.
	 *
	 * @return the cp specification option local service
	 */
	public
		com.liferay.commerce.product.service.CPSpecificationOptionLocalService
			getCPSpecificationOptionLocalService() {

		return cpSpecificationOptionLocalService;
	}

	/**
	 * Sets the cp specification option local service.
	 *
	 * @param cpSpecificationOptionLocalService the cp specification option local service
	 */
	public void setCPSpecificationOptionLocalService(
		com.liferay.commerce.product.service.CPSpecificationOptionLocalService
			cpSpecificationOptionLocalService) {

		this.cpSpecificationOptionLocalService =
			cpSpecificationOptionLocalService;
	}

	/**
	 * Returns the cp specification option persistence.
	 *
	 * @return the cp specification option persistence
	 */
	public CPSpecificationOptionPersistence
		getCPSpecificationOptionPersistence() {

		return cpSpecificationOptionPersistence;
	}

	/**
	 * Sets the cp specification option persistence.
	 *
	 * @param cpSpecificationOptionPersistence the cp specification option persistence
	 */
	public void setCPSpecificationOptionPersistence(
		CPSpecificationOptionPersistence cpSpecificationOptionPersistence) {

		this.cpSpecificationOptionPersistence =
			cpSpecificationOptionPersistence;
	}

	/**
	 * Returns the cp tax category local service.
	 *
	 * @return the cp tax category local service
	 */
	public com.liferay.commerce.product.service.CPTaxCategoryLocalService
		getCPTaxCategoryLocalService() {

		return cpTaxCategoryLocalService;
	}

	/**
	 * Sets the cp tax category local service.
	 *
	 * @param cpTaxCategoryLocalService the cp tax category local service
	 */
	public void setCPTaxCategoryLocalService(
		com.liferay.commerce.product.service.CPTaxCategoryLocalService
			cpTaxCategoryLocalService) {

		this.cpTaxCategoryLocalService = cpTaxCategoryLocalService;
	}

	/**
	 * Returns the cp tax category persistence.
	 *
	 * @return the cp tax category persistence
	 */
	public CPTaxCategoryPersistence getCPTaxCategoryPersistence() {
		return cpTaxCategoryPersistence;
	}

	/**
	 * Sets the cp tax category persistence.
	 *
	 * @param cpTaxCategoryPersistence the cp tax category persistence
	 */
	public void setCPTaxCategoryPersistence(
		CPTaxCategoryPersistence cpTaxCategoryPersistence) {

		this.cpTaxCategoryPersistence = cpTaxCategoryPersistence;
	}

	/**
	 * Returns the cp tax category finder.
	 *
	 * @return the cp tax category finder
	 */
	public CPTaxCategoryFinder getCPTaxCategoryFinder() {
		return cpTaxCategoryFinder;
	}

	/**
	 * Sets the cp tax category finder.
	 *
	 * @param cpTaxCategoryFinder the cp tax category finder
	 */
	public void setCPTaxCategoryFinder(
		CPTaxCategoryFinder cpTaxCategoryFinder) {

		this.cpTaxCategoryFinder = cpTaxCategoryFinder;
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
			"com.liferay.commerce.product.model.CPInstanceOptionValueRel",
			cpInstanceOptionValueRelLocalService);

		_setLocalServiceUtilService(cpInstanceOptionValueRelLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.commerce.product.model.CPInstanceOptionValueRel");

		_setLocalServiceUtilService(null);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return CPInstanceOptionValueRelLocalService.class.getName();
	}

	@Override
	public CTPersistence<CPInstanceOptionValueRel> getCTPersistence() {
		return cpInstanceOptionValueRelPersistence;
	}

	@Override
	public Class<CPInstanceOptionValueRel> getModelClass() {
		return CPInstanceOptionValueRel.class;
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<CPInstanceOptionValueRel>, R, E>
				updateUnsafeFunction)
		throws E {

		return updateUnsafeFunction.apply(cpInstanceOptionValueRelPersistence);
	}

	protected String getModelClassName() {
		return CPInstanceOptionValueRel.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource =
				cpInstanceOptionValueRelPersistence.getDataSource();

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
		CPInstanceOptionValueRelLocalService
			cpInstanceOptionValueRelLocalService) {

		try {
			Field field =
				CPInstanceOptionValueRelLocalServiceUtil.class.getDeclaredField(
					"_service");

			field.setAccessible(true);

			field.set(null, cpInstanceOptionValueRelLocalService);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@BeanReference(
		type = com.liferay.commerce.product.service.CommerceCatalogLocalService.class
	)
	protected com.liferay.commerce.product.service.CommerceCatalogLocalService
		commerceCatalogLocalService;

	@BeanReference(type = CommerceCatalogPersistence.class)
	protected CommerceCatalogPersistence commerceCatalogPersistence;

	@BeanReference(
		type = com.liferay.commerce.product.service.CommerceChannelLocalService.class
	)
	protected com.liferay.commerce.product.service.CommerceChannelLocalService
		commerceChannelLocalService;

	@BeanReference(type = CommerceChannelPersistence.class)
	protected CommerceChannelPersistence commerceChannelPersistence;

	@BeanReference(
		type = com.liferay.commerce.product.service.CommerceChannelRelLocalService.class
	)
	protected
		com.liferay.commerce.product.service.CommerceChannelRelLocalService
			commerceChannelRelLocalService;

	@BeanReference(type = CommerceChannelRelPersistence.class)
	protected CommerceChannelRelPersistence commerceChannelRelPersistence;

	@BeanReference(type = CommerceChannelRelFinder.class)
	protected CommerceChannelRelFinder commerceChannelRelFinder;

	@BeanReference(
		type = com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService.class
	)
	protected
		com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService
			cpAttachmentFileEntryLocalService;

	@BeanReference(type = CPAttachmentFileEntryPersistence.class)
	protected CPAttachmentFileEntryPersistence cpAttachmentFileEntryPersistence;

	@BeanReference(type = CPAttachmentFileEntryFinder.class)
	protected CPAttachmentFileEntryFinder cpAttachmentFileEntryFinder;

	@BeanReference(
		type = com.liferay.commerce.product.service.CPDefinitionLocalService.class
	)
	protected com.liferay.commerce.product.service.CPDefinitionLocalService
		cpDefinitionLocalService;

	@BeanReference(type = CPDefinitionPersistence.class)
	protected CPDefinitionPersistence cpDefinitionPersistence;

	@BeanReference(type = CPDefinitionFinder.class)
	protected CPDefinitionFinder cpDefinitionFinder;

	@BeanReference(
		type = com.liferay.commerce.product.service.CPDefinitionLinkLocalService.class
	)
	protected com.liferay.commerce.product.service.CPDefinitionLinkLocalService
		cpDefinitionLinkLocalService;

	@BeanReference(type = CPDefinitionLinkPersistence.class)
	protected CPDefinitionLinkPersistence cpDefinitionLinkPersistence;

	@BeanReference(type = CPDefinitionLocalizationPersistence.class)
	protected CPDefinitionLocalizationPersistence
		cpDefinitionLocalizationPersistence;

	@BeanReference(
		type = com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService.class
	)
	protected
		com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService
			cpDefinitionOptionRelLocalService;

	@BeanReference(type = CPDefinitionOptionRelPersistence.class)
	protected CPDefinitionOptionRelPersistence cpDefinitionOptionRelPersistence;

	@BeanReference(
		type = com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService.class
	)
	protected
		com.liferay.commerce.product.service.
			CPDefinitionOptionValueRelLocalService
				cpDefinitionOptionValueRelLocalService;

	@BeanReference(type = CPDefinitionOptionValueRelPersistence.class)
	protected CPDefinitionOptionValueRelPersistence
		cpDefinitionOptionValueRelPersistence;

	@BeanReference(
		type = com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueLocalService.class
	)
	protected com.liferay.commerce.product.service.
		CPDefinitionSpecificationOptionValueLocalService
			cpDefinitionSpecificationOptionValueLocalService;

	@BeanReference(type = CPDefinitionSpecificationOptionValuePersistence.class)
	protected CPDefinitionSpecificationOptionValuePersistence
		cpDefinitionSpecificationOptionValuePersistence;

	@BeanReference(
		type = com.liferay.commerce.product.service.CPDisplayLayoutLocalService.class
	)
	protected com.liferay.commerce.product.service.CPDisplayLayoutLocalService
		cpDisplayLayoutLocalService;

	@BeanReference(type = CPDisplayLayoutPersistence.class)
	protected CPDisplayLayoutPersistence cpDisplayLayoutPersistence;

	@BeanReference(
		type = com.liferay.commerce.product.service.CPInstanceLocalService.class
	)
	protected com.liferay.commerce.product.service.CPInstanceLocalService
		cpInstanceLocalService;

	@BeanReference(type = CPInstancePersistence.class)
	protected CPInstancePersistence cpInstancePersistence;

	@BeanReference(type = CPInstanceFinder.class)
	protected CPInstanceFinder cpInstanceFinder;

	@BeanReference(type = CPInstanceOptionValueRelLocalService.class)
	protected CPInstanceOptionValueRelLocalService
		cpInstanceOptionValueRelLocalService;

	@BeanReference(type = CPInstanceOptionValueRelPersistence.class)
	protected CPInstanceOptionValueRelPersistence
		cpInstanceOptionValueRelPersistence;

	@BeanReference(type = CPInstanceOptionValueRelFinder.class)
	protected CPInstanceOptionValueRelFinder cpInstanceOptionValueRelFinder;

	@BeanReference(
		type = com.liferay.commerce.product.service.CPMeasurementUnitLocalService.class
	)
	protected com.liferay.commerce.product.service.CPMeasurementUnitLocalService
		cpMeasurementUnitLocalService;

	@BeanReference(type = CPMeasurementUnitPersistence.class)
	protected CPMeasurementUnitPersistence cpMeasurementUnitPersistence;

	@BeanReference(
		type = com.liferay.commerce.product.service.CPOptionLocalService.class
	)
	protected com.liferay.commerce.product.service.CPOptionLocalService
		cpOptionLocalService;

	@BeanReference(type = CPOptionPersistence.class)
	protected CPOptionPersistence cpOptionPersistence;

	@BeanReference(
		type = com.liferay.commerce.product.service.CPOptionCategoryLocalService.class
	)
	protected com.liferay.commerce.product.service.CPOptionCategoryLocalService
		cpOptionCategoryLocalService;

	@BeanReference(type = CPOptionCategoryPersistence.class)
	protected CPOptionCategoryPersistence cpOptionCategoryPersistence;

	@BeanReference(
		type = com.liferay.commerce.product.service.CPOptionValueLocalService.class
	)
	protected com.liferay.commerce.product.service.CPOptionValueLocalService
		cpOptionValueLocalService;

	@BeanReference(type = CPOptionValuePersistence.class)
	protected CPOptionValuePersistence cpOptionValuePersistence;

	@BeanReference(
		type = com.liferay.commerce.product.service.CProductLocalService.class
	)
	protected com.liferay.commerce.product.service.CProductLocalService
		cProductLocalService;

	@BeanReference(type = CProductPersistence.class)
	protected CProductPersistence cProductPersistence;

	@BeanReference(
		type = com.liferay.commerce.product.service.CPSpecificationOptionLocalService.class
	)
	protected
		com.liferay.commerce.product.service.CPSpecificationOptionLocalService
			cpSpecificationOptionLocalService;

	@BeanReference(type = CPSpecificationOptionPersistence.class)
	protected CPSpecificationOptionPersistence cpSpecificationOptionPersistence;

	@BeanReference(
		type = com.liferay.commerce.product.service.CPTaxCategoryLocalService.class
	)
	protected com.liferay.commerce.product.service.CPTaxCategoryLocalService
		cpTaxCategoryLocalService;

	@BeanReference(type = CPTaxCategoryPersistence.class)
	protected CPTaxCategoryPersistence cpTaxCategoryPersistence;

	@BeanReference(type = CPTaxCategoryFinder.class)
	protected CPTaxCategoryFinder cpTaxCategoryFinder;

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