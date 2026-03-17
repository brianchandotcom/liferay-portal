/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.service;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.launch.model.LaunchSet;
import com.liferay.petra.sql.dsl.query.DSLQuery;
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
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for LaunchSet. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see LaunchSetLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface LaunchSetLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.launch.service.impl.LaunchSetLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the launch set local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link LaunchSetLocalServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * Adds the launch set to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LaunchSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param launchSet the launch set
	 * @return the launch set that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public LaunchSet addLaunchSet(LaunchSet launchSet);

	public LaunchSet addLaunchSet(
			String externalReferenceCode, long userId, String description,
			String name)
		throws PortalException;

	/**
	 * Creates a new launch set with the primary key. Does not add the launch set to the database.
	 *
	 * @param launchSetId the primary key for the new launch set
	 * @return the new launch set
	 */
	@Transactional(enabled = false)
	public LaunchSet createLaunchSet(long launchSetId);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Deletes the launch set from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LaunchSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param launchSet the launch set
	 * @return the launch set that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public LaunchSet deleteLaunchSet(LaunchSet launchSet);

	/**
	 * Deletes the launch set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LaunchSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param launchSetId the primary key of the launch set
	 * @return the launch set that was removed
	 * @throws PortalException if a launch set with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public LaunchSet deleteLaunchSet(long launchSetId) throws PortalException;

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.launch.model.impl.LaunchSetModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.launch.model.impl.LaunchSetModelImpl</code>.
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
	public LaunchSet fetchLaunchSet(long launchSetId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LaunchSet fetchLaunchSetByExternalReferenceCode(
		String externalReferenceCode, long companyId);

	/**
	 * Returns the launch set with the matching UUID and company.
	 *
	 * @param uuid the launch set's UUID
	 * @param companyId the primary key of the company
	 * @return the matching launch set, or <code>null</code> if a matching launch set could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LaunchSet fetchLaunchSetByUuidAndCompanyId(
		String uuid, long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	 * Returns the launch set with the primary key.
	 *
	 * @param launchSetId the primary key of the launch set
	 * @return the launch set
	 * @throws PortalException if a launch set with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LaunchSet getLaunchSet(long launchSetId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LaunchSet getLaunchSetByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException;

	/**
	 * Returns the launch set with the matching UUID and company.
	 *
	 * @param uuid the launch set's UUID
	 * @param companyId the primary key of the company
	 * @return the matching launch set
	 * @throws PortalException if a matching launch set could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LaunchSet getLaunchSetByUuidAndCompanyId(String uuid, long companyId)
		throws PortalException;

	/**
	 * Returns a range of all the launch sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.launch.model.impl.LaunchSetModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of launch sets
	 * @param end the upper bound of the range of launch sets (not inclusive)
	 * @return the range of launch sets
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LaunchSet> getLaunchSets(int start, int end);

	/**
	 * Returns the number of launch sets.
	 *
	 * @return the number of launch sets
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLaunchSetsCount();

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
	 * Updates the launch set in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LaunchSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param launchSet the launch set
	 * @return the launch set that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public LaunchSet updateLaunchSet(LaunchSet launchSet);

}
// SB-Hash:859177618