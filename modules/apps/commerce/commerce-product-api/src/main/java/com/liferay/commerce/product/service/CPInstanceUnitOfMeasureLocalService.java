/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service;

import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for CPInstanceUnitOfMeasure. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Marco Leo
 * @see CPInstanceUnitOfMeasureLocalServiceUtil
 * @generated
 */
@CTAware
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CPInstanceUnitOfMeasureLocalService
	extends BaseLocalService, CTService<CPInstanceUnitOfMeasure>,
			PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CPInstanceUnitOfMeasureLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the cp instance unit of measure local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CPInstanceUnitOfMeasureLocalServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * Adds the cp instance unit of measure to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPInstanceUnitOfMeasureLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpInstanceUnitOfMeasure the cp instance unit of measure
	 * @return the cp instance unit of measure that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public CPInstanceUnitOfMeasure addCPInstanceUnitOfMeasure(
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure);

	public CPInstanceUnitOfMeasure addCPInstanceUnitOfMeasure(
			long userId, long cpInstanceId, boolean active,
			BigDecimal incrementalOrderQuantity, String key,
			Map<Locale, String> nameMap, int precision, boolean primary,
			double priority, BigDecimal rate, String sku)
		throws PortalException;

	public CPInstanceUnitOfMeasure addOrUpdateCPInstanceUnitOfMeasure(
			long userId, long cpInstanceId, boolean active,
			BigDecimal incrementalOrderQuantity, String key,
			Map<Locale, String> nameMap, int precision, boolean primary,
			double priority, BigDecimal rate, String sku)
		throws PortalException;

	/**
	 * Creates a new cp instance unit of measure with the primary key. Does not add the cp instance unit of measure to the database.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key for the new cp instance unit of measure
	 * @return the new cp instance unit of measure
	 */
	@Transactional(enabled = false)
	public CPInstanceUnitOfMeasure createCPInstanceUnitOfMeasure(
		long CPInstanceUnitOfMeasureId);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Deletes the cp instance unit of measure from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPInstanceUnitOfMeasureLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpInstanceUnitOfMeasure the cp instance unit of measure
	 * @return the cp instance unit of measure that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public CPInstanceUnitOfMeasure deleteCPInstanceUnitOfMeasure(
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure);

	/**
	 * Deletes the cp instance unit of measure with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPInstanceUnitOfMeasureLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the cp instance unit of measure
	 * @return the cp instance unit of measure that was removed
	 * @throws PortalException if a cp instance unit of measure with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public CPInstanceUnitOfMeasure deleteCPInstanceUnitOfMeasure(
			long CPInstanceUnitOfMeasureId)
		throws PortalException;

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> T dslQuery(DSLQuery dslQuery);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int dslQueryCount(DSLQuery dslQuery);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DynamicQuery dynamicQuery();

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end);

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPInstanceUnitOfMeasure fetchCPInstanceUnitOfMeasure(
		long CPInstanceUnitOfMeasureId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPInstanceUnitOfMeasure fetchCPInstanceUnitOfMeasure(
		long cpInstanceId, String key);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPInstanceUnitOfMeasure fetchCPInstanceUnitOfMeasure(
		long companyId, String key, String sku);

	/**
	 * Returns the cp instance unit of measure with the matching UUID and company.
	 *
	 * @param uuid the cp instance unit of measure's UUID
	 * @param companyId the primary key of the company
	 * @return the matching cp instance unit of measure, or <code>null</code> if a matching cp instance unit of measure could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPInstanceUnitOfMeasure
		fetchCPInstanceUnitOfMeasureByUuidAndCompanyId(
			String uuid, long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPInstanceUnitOfMeasure fetchPrimaryCPInstanceUnitOfMeasure(
		long cpInstanceId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPInstanceUnitOfMeasure> getActiveCPInstanceUnitOfMeasures(
		long cpInstanceId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getActiveCPInstanceUnitOfMeasuresCount(long cpInstanceId);

	/**
	 * Returns the cp instance unit of measure with the primary key.
	 *
	 * @param CPInstanceUnitOfMeasureId the primary key of the cp instance unit of measure
	 * @return the cp instance unit of measure
	 * @throws PortalException if a cp instance unit of measure with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPInstanceUnitOfMeasure getCPInstanceUnitOfMeasure(
			long CPInstanceUnitOfMeasureId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPInstanceUnitOfMeasure getCPInstanceUnitOfMeasure(
			long cpInstanceId, String key)
		throws PortalException;

	/**
	 * Returns the cp instance unit of measure with the matching UUID and company.
	 *
	 * @param uuid the cp instance unit of measure's UUID
	 * @param companyId the primary key of the company
	 * @return the matching cp instance unit of measure
	 * @throws PortalException if a matching cp instance unit of measure could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPInstanceUnitOfMeasure getCPInstanceUnitOfMeasureByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException;

	/**
	 * Returns a range of all the cp instance unit of measures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CPInstanceUnitOfMeasureModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp instance unit of measures
	 * @param end the upper bound of the range of cp instance unit of measures (not inclusive)
	 * @return the range of cp instance unit of measures
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPInstanceUnitOfMeasure> getCPInstanceUnitOfMeasures(
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPInstanceUnitOfMeasure> getCPInstanceUnitOfMeasures(
		long cpInstanceId, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPInstanceUnitOfMeasure> getCPInstanceUnitOfMeasures(
		long companyId, String sku);

	/**
	 * Returns the number of cp instance unit of measures.
	 *
	 * @return the number of cp instance unit of measures
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPInstanceUnitOfMeasuresCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPInstanceUnitOfMeasuresCount(long cpInstanceId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPInstanceUnitOfMeasuresCount(long companyId, String sku);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	/**
	 * @throws PortalException
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Updates the cp instance unit of measure in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPInstanceUnitOfMeasureLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpInstanceUnitOfMeasure the cp instance unit of measure
	 * @return the cp instance unit of measure that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public CPInstanceUnitOfMeasure updateCPInstanceUnitOfMeasure(
		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure);

	public CPInstanceUnitOfMeasure updateCPInstanceUnitOfMeasure(
			long cpInstanceUnitOfMeasureId, long cpInstanceId, boolean active,
			BigDecimal incrementalOrderQuantity, String key,
			Map<Locale, String> nameMap, int precision, boolean primary,
			double priority, BigDecimal rate, String sku)
		throws PortalException;

	@Override
	@Transactional(enabled = false)
	public CTPersistence<CPInstanceUnitOfMeasure> getCTPersistence();

	@Override
	@Transactional(enabled = false)
	public Class<CPInstanceUnitOfMeasure> getModelClass();

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<CPInstanceUnitOfMeasure>, R, E>
				updateUnsafeFunction)
		throws E;

}