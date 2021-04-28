/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.service;

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for CPDefinitionDiagramSettings. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionDiagramSettingsLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CPDefinitionDiagramSettingsLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.shop.by.diagram.service.impl.CPDefinitionDiagramSettingsLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the cp definition diagram settings local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CPDefinitionDiagramSettingsLocalServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * Adds the cp definition diagram settings to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionDiagramSettingsLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionDiagramSettings the cp definition diagram settings
	 * @return the cp definition diagram settings that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public CPDefinitionDiagramSettings addCPDefinitionDiagramSettings(
		CPDefinitionDiagramSettings cpDefinitionDiagramSettings);

	@Indexable(type = IndexableType.REINDEX)
	public CPDefinitionDiagramSettings addCPDefinitionDiagramSettings(
			long userId, long cpDefinitionId, long assetCategoryId, String name)
		throws PortalException;

	/**
	 * Creates a new cp definition diagram settings with the primary key. Does not add the cp definition diagram settings to the database.
	 *
	 * @param CPDefinitionDiagramSettingsId the primary key for the new cp definition diagram settings
	 * @return the new cp definition diagram settings
	 */
	@Transactional(enabled = false)
	public CPDefinitionDiagramSettings createCPDefinitionDiagramSettings(
		long CPDefinitionDiagramSettingsId);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Deletes the cp definition diagram settings from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionDiagramSettingsLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionDiagramSettings the cp definition diagram settings
	 * @return the cp definition diagram settings that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public CPDefinitionDiagramSettings deleteCPDefinitionDiagramSettings(
		CPDefinitionDiagramSettings cpDefinitionDiagramSettings);

	/**
	 * Deletes the cp definition diagram settings with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionDiagramSettingsLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param CPDefinitionDiagramSettingsId the primary key of the cp definition diagram settings
	 * @return the cp definition diagram settings that was removed
	 * @throws PortalException if a cp definition diagram settings with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public CPDefinitionDiagramSettings deleteCPDefinitionDiagramSettings(
			long CPDefinitionDiagramSettingsId)
		throws PortalException;

	public CPDefinitionDiagramSettings
			deleteCPDefinitionDiagramSettingsByCPDefinitionId(
				long cpDefinitionId)
		throws PortalException;

	public void deleteCPDefinitionDiagramSettingsCategory(long assetCategoryId)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramSettingsModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramSettingsModelImpl</code>.
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
	public CPDefinitionDiagramSettings fetchCPDefinitionDiagramSettings(
		long CPDefinitionDiagramSettingsId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionDiagramSettings
		fetchCPDefinitionDiagramSettingsByCPDefinitionId(long cpDefinitionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	 * Returns the cp definition diagram settings with the primary key.
	 *
	 * @param CPDefinitionDiagramSettingsId the primary key of the cp definition diagram settings
	 * @return the cp definition diagram settings
	 * @throws PortalException if a cp definition diagram settings with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionDiagramSettings getCPDefinitionDiagramSettings(
			long CPDefinitionDiagramSettingsId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionDiagramSettings
			getCPDefinitionDiagramSettingsByCPDefinitionId(long cpDefinitionId)
		throws PortalException;

	/**
	 * Returns a range of all the cp definition diagram settingses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.shop.by.diagram.model.impl.CPDefinitionDiagramSettingsModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram settingses
	 * @param end the upper bound of the range of cp definition diagram settingses (not inclusive)
	 * @return the range of cp definition diagram settingses
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionDiagramSettings> getCPDefinitionDiagramSettingses(
		int start, int end);

	/**
	 * Returns the number of cp definition diagram settingses.
	 *
	 * @return the number of cp definition diagram settingses
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPDefinitionDiagramSettingsesCount();

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
	 * Updates the cp definition diagram settings in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CPDefinitionDiagramSettingsLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param cpDefinitionDiagramSettings the cp definition diagram settings
	 * @return the cp definition diagram settings that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public CPDefinitionDiagramSettings updateCPDefinitionDiagramSettings(
		CPDefinitionDiagramSettings cpDefinitionDiagramSettings);

	@Indexable(type = IndexableType.REINDEX)
	public CPDefinitionDiagramSettings updateCPDefinitionDiagramSettings(
			long cpDefinitionDiagramSettingsId, long assetCategoryId,
			String name)
		throws PortalException;

}