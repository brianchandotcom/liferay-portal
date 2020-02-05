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

package com.liferay.fragment.service;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.fragment.model.FragmentComposition;
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
 * Provides the local service interface for FragmentComposition. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentCompositionLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface FragmentCompositionLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FragmentCompositionLocalServiceUtil} to access the fragment composition local service. Add custom service methods to <code>com.liferay.fragment.service.impl.FragmentCompositionLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	 * Adds the fragment composition to the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentComposition the fragment composition
	 * @return the fragment composition that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public FragmentComposition addFragmentComposition(
		FragmentComposition fragmentComposition);

	/**
	 * Creates a new fragment composition with the primary key. Does not add the fragment composition to the database.
	 *
	 * @param fragmentCompositionId the primary key for the new fragment composition
	 * @return the new fragment composition
	 */
	@Transactional(enabled = false)
	public FragmentComposition createFragmentComposition(
		long fragmentCompositionId);

	/**
	 * Deletes the fragment composition from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentComposition the fragment composition
	 * @return the fragment composition that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public FragmentComposition deleteFragmentComposition(
		FragmentComposition fragmentComposition);

	/**
	 * Deletes the fragment composition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentCompositionId the primary key of the fragment composition
	 * @return the fragment composition that was removed
	 * @throws PortalException if a fragment composition with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public FragmentComposition deleteFragmentComposition(
			long fragmentCompositionId)
		throws PortalException;

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.fragment.model.impl.FragmentCompositionModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.fragment.model.impl.FragmentCompositionModelImpl</code>.
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
	public FragmentComposition fetchFragmentComposition(
		long fragmentCompositionId);

	/**
	 * Returns the fragment composition matching the UUID and group.
	 *
	 * @param uuid the fragment composition's UUID
	 * @param groupId the primary key of the group
	 * @return the matching fragment composition, or <code>null</code> if a matching fragment composition could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FragmentComposition fetchFragmentCompositionByUuidAndGroupId(
		String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	/**
	 * Returns the fragment composition with the primary key.
	 *
	 * @param fragmentCompositionId the primary key of the fragment composition
	 * @return the fragment composition
	 * @throws PortalException if a fragment composition with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FragmentComposition getFragmentComposition(
			long fragmentCompositionId)
		throws PortalException;

	/**
	 * Returns the fragment composition matching the UUID and group.
	 *
	 * @param uuid the fragment composition's UUID
	 * @param groupId the primary key of the group
	 * @return the matching fragment composition
	 * @throws PortalException if a matching fragment composition could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FragmentComposition getFragmentCompositionByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException;

	/**
	 * Returns a range of all the fragment compositions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.fragment.model.impl.FragmentCompositionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @return the range of fragment compositions
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentComposition> getFragmentCompositions(
		int start, int end);

	/**
	 * Returns all the fragment compositions matching the UUID and company.
	 *
	 * @param uuid the UUID of the fragment compositions
	 * @param companyId the primary key of the company
	 * @return the matching fragment compositions, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentComposition> getFragmentCompositionsByUuidAndCompanyId(
		String uuid, long companyId);

	/**
	 * Returns a range of fragment compositions matching the UUID and company.
	 *
	 * @param uuid the UUID of the fragment compositions
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of fragment compositions
	 * @param end the upper bound of the range of fragment compositions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching fragment compositions, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FragmentComposition> getFragmentCompositionsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FragmentComposition> orderByComparator);

	/**
	 * Returns the number of fragment compositions.
	 *
	 * @return the number of fragment compositions
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFragmentCompositionsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Updates the fragment composition in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentComposition the fragment composition
	 * @return the fragment composition that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public FragmentComposition updateFragmentComposition(
		FragmentComposition fragmentComposition);

}