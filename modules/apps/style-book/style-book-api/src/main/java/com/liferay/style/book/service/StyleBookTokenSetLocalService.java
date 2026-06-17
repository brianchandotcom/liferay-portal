/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.service;

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
import com.liferay.style.book.model.StyleBookTokenSet;

import java.io.Serializable;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for StyleBookTokenSet. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see StyleBookTokenSetLocalServiceUtil
 * @generated
 */
@CTAware
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface StyleBookTokenSetLocalService
	extends BaseLocalService, CTService<StyleBookTokenSet>,
			PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.style.book.service.impl.StyleBookTokenSetLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the style book token set local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link StyleBookTokenSetLocalServiceUtil} if injection and service tracking are not available.
	 */
	public StyleBookTokenSet addStyleBookTokenSet(
			String externalReferenceCode, long userId, String description,
			String frontendTokenCategoryName, String name,
			long styleBookEntryId, String themeId)
		throws PortalException;

	/**
	 * Adds the style book token set to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect StyleBookTokenSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param styleBookTokenSet the style book token set
	 * @return the style book token set that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public StyleBookTokenSet addStyleBookTokenSet(
		StyleBookTokenSet styleBookTokenSet);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Creates a new style book token set with the primary key. Does not add the style book token set to the database.
	 *
	 * @param styleBookTokenSetId the primary key for the new style book token set
	 * @return the new style book token set
	 */
	@Transactional(enabled = false)
	public StyleBookTokenSet createStyleBookTokenSet(long styleBookTokenSetId);

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	/**
	 * Deletes the style book token set with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect StyleBookTokenSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param styleBookTokenSetId the primary key of the style book token set
	 * @return the style book token set that was removed
	 * @throws PortalException if a style book token set with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public StyleBookTokenSet deleteStyleBookTokenSet(long styleBookTokenSetId)
		throws PortalException;

	/**
	 * Deletes the style book token set from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect StyleBookTokenSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param styleBookTokenSet the style book token set
	 * @return the style book token set that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public StyleBookTokenSet deleteStyleBookTokenSet(
		StyleBookTokenSet styleBookTokenSet);

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.style.book.model.impl.StyleBookTokenSetModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.style.book.model.impl.StyleBookTokenSetModelImpl</code>.
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
	public StyleBookTokenSet fetchStyleBookTokenSet(long styleBookTokenSetId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public StyleBookTokenSet fetchStyleBookTokenSetByExternalReferenceCode(
		String externalReferenceCode, long groupId);

	/**
	 * Returns the style book token set matching the UUID and group.
	 *
	 * @param uuid the style book token set's UUID
	 * @param groupId the primary key of the group
	 * @return the matching style book token set, or <code>null</code> if a matching style book token set could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public StyleBookTokenSet fetchStyleBookTokenSetByUuidAndGroupId(
		String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

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
	 * Returns the style book token set with the primary key.
	 *
	 * @param styleBookTokenSetId the primary key of the style book token set
	 * @return the style book token set
	 * @throws PortalException if a style book token set with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public StyleBookTokenSet getStyleBookTokenSet(long styleBookTokenSetId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public StyleBookTokenSet getStyleBookTokenSetByExternalReferenceCode(
			String externalReferenceCode, long groupId)
		throws PortalException;

	/**
	 * Returns the style book token set matching the UUID and group.
	 *
	 * @param uuid the style book token set's UUID
	 * @param groupId the primary key of the group
	 * @return the matching style book token set
	 * @throws PortalException if a matching style book token set could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public StyleBookTokenSet getStyleBookTokenSetByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException;

	/**
	 * Returns a range of all the style book token sets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.style.book.model.impl.StyleBookTokenSetModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of style book token sets
	 * @param end the upper bound of the range of style book token sets (not inclusive)
	 * @return the range of style book token sets
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<StyleBookTokenSet> getStyleBookTokenSets(int start, int end);

	/**
	 * Returns all the style book token sets matching the UUID and company.
	 *
	 * @param uuid the UUID of the style book token sets
	 * @param companyId the primary key of the company
	 * @return the matching style book token sets, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<StyleBookTokenSet> getStyleBookTokenSetsByUuidAndCompanyId(
		String uuid, long companyId);

	/**
	 * Returns a range of style book token sets matching the UUID and company.
	 *
	 * @param uuid the UUID of the style book token sets
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of style book token sets
	 * @param end the upper bound of the range of style book token sets (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching style book token sets, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<StyleBookTokenSet> getStyleBookTokenSetsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<StyleBookTokenSet> orderByComparator);

	/**
	 * Returns the number of style book token sets.
	 *
	 * @return the number of style book token sets
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getStyleBookTokenSetsCount();

	/**
	 * Updates the style book token set in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect StyleBookTokenSetLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param styleBookTokenSet the style book token set
	 * @return the style book token set that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public StyleBookTokenSet updateStyleBookTokenSet(
		StyleBookTokenSet styleBookTokenSet);

	@Override
	@Transactional(enabled = false)
	public CTPersistence<StyleBookTokenSet> getCTPersistence();

	@Override
	@Transactional(enabled = false)
	public Class<StyleBookTokenSet> getModelClass();

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<StyleBookTokenSet>, R, E>
				updateUnsafeFunction)
		throws E;

}
// LIFERAY-SERVICE-BUILDER-HASH:1940051513