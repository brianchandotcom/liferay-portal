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

package com.liferay.portal.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.PortletDataContext;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Provides the local service interface for Organization. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see OrganizationLocalServiceUtil
 * @see com.liferay.portal.service.base.OrganizationLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.OrganizationLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface OrganizationLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link OrganizationLocalServiceUtil} to access the organization local service. Add custom service methods to {@link com.liferay.portal.service.impl.OrganizationLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasGroupOrganization(long groupId, long organizationId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasGroupOrganizations(long groupId);

	/**
	* Returns <code>true</code> if the password policy has been assigned to the
	* organization.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @param organizationId the primary key of the organization
	* @return <code>true</code> if the password policy has been assigned to the
	organization; <code>false</code> otherwise
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasPasswordPolicyOrganization(long passwordPolicyId,
		long organizationId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasUserOrganization(long userId, long organizationId);

	/**
	* Returns <code>true</code> if the user is a member of the organization,
	* optionally focusing on suborganizations or the specified organization.
	* This method is usually called to determine if the user has view access to
	* a resource belonging to the organization.
	*
	* <ol>
	* <li>
	* If <code>inheritSuborganizations=<code>false</code></code>:
	* the method checks whether the user belongs to the organization specified
	* by <code>organizationId</code>. The parameter
	* <code>includeSpecifiedOrganization</code> is ignored.
	* </li>
	* <li>
	* The parameter <code>includeSpecifiedOrganization</code> is
	* ignored unless <code>inheritSuborganizations</code> is also
	* <code>true</code>.
	* </li>
	* <li>
	* If <code>inheritSuborganizations=<code>true</code></code> and
	* <code>includeSpecifiedOrganization=<code>false</code></code>: the method
	* checks
	* whether the user belongs to one of the child organizations of the one
	* specified by <code>organizationId</code>.
	* </li>
	* <li>
	* If <code>inheritSuborganizations=<code>true</code></code> and
	* <code>includeSpecifiedOrganization=<code>true</code></code>: the method
	* checks whether
	* the user belongs to the organization specified by
	* <code>organizationId</code> or any of
	* its child organizations.
	* </li>
	* </ol>
	*
	* @param userId the primary key of the organization's user
	* @param organizationId the primary key of the organization
	* @param inheritSuborganizations if <code>true</code> suborganizations are
	considered in the determination
	* @param includeSpecifiedOrganization if <code>true</code> the
	organization specified by <code>organizationId</code> is
	considered in the determination
	* @return <code>true</code> if the user has access to the organization;
	<code>false</code> otherwise
	* @see com.liferay.portal.kernel.service.persistence.OrganizationFinder
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasUserOrganization(long userId, long organizationId,
		boolean inheritSuborganizations, boolean includeSpecifiedOrganization)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasUserOrganizations(long userId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Adds the organization to the database. Also notifies the appropriate model listeners.
	*
	* @param organization the organization
	* @return the organization that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public Organization addOrganization(Organization organization);

	/**
	* Adds an organization.
	*
	* <p>
	* This method handles the creation and bookkeeping of the organization
	* including its resources, metadata, and internal data structures. It is
	* not necessary to make a subsequent call to {@link
	* #addOrganizationResources(long, Organization)}.
	* </p>
	*
	* @param userId the primary key of the creator/owner of the organization
	* @param parentOrganizationId the primary key of the organization's parent
	organization
	* @param name the organization's name
	* @param site whether the organization is to be associated with a main
	site
	* @return the organization
	*/
	public Organization addOrganization(long userId, long parentOrganizationId,
		java.lang.String name, boolean site) throws PortalException;

	/**
	* Adds an organization.
	*
	* <p>
	* This method handles the creation and bookkeeping of the organization
	* including its resources, metadata, and internal data structures. It is
	* not necessary to make a subsequent call to {@link
	* #addOrganizationResources(long, Organization)}.
	* </p>
	*
	* @param userId the primary key of the creator/owner of the organization
	* @param parentOrganizationId the primary key of the organization's parent
	organization
	* @param name the organization's name
	* @param type the organization's type
	* @param regionId the primary key of the organization's region
	* @param countryId the primary key of the organization's country
	* @param statusId the organization's workflow status
	* @param comments the comments about the organization
	* @param site whether the organization is to be associated with a main
	site
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set asset category IDs, asset tag names,
	and expando bridge attributes for the organization.
	* @return the organization
	*/
	public Organization addOrganization(long userId, long parentOrganizationId,
		java.lang.String name, java.lang.String type, long regionId,
		long countryId, long statusId, java.lang.String comments, boolean site,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new organization with the primary key. Does not add the organization to the database.
	*
	* @param organizationId the primary key for the new organization
	* @return the new organization
	*/
	public Organization createOrganization(long organizationId);

	/**
	* Deletes the organization from the database. Also notifies the appropriate model listeners.
	*
	* @param organization the organization
	* @return the organization that was removed
	* @throws PortalException
	*/
	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public Organization deleteOrganization(Organization organization)
		throws PortalException;

	/**
	* Deletes the organization with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param organizationId the primary key of the organization
	* @return the organization that was removed
	* @throws PortalException if a organization with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public Organization deleteOrganization(long organizationId)
		throws PortalException;

	/**
	* Returns the organization with the name.
	*
	* @param companyId the primary key of the organization's company
	* @param name the organization's name
	* @return the organization with the name, or <code>null</code> if no
	organization could be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Organization fetchOrganization(long companyId, java.lang.String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Organization fetchOrganization(long organizationId);

	/**
	* Returns the organization with the matching UUID and company.
	*
	* @param uuid the organization's UUID
	* @param companyId the primary key of the company
	* @return the matching organization, or <code>null</code> if a matching organization could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Organization fetchOrganizationByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns the organization with the name.
	*
	* @param companyId the primary key of the organization's company
	* @param name the organization's name
	* @return the organization with the name
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Organization getOrganization(long companyId, java.lang.String name)
		throws PortalException;

	/**
	* Returns the organization with the primary key.
	*
	* @param organizationId the primary key of the organization
	* @return the organization
	* @throws PortalException if a organization with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Organization getOrganization(long organizationId)
		throws PortalException;

	/**
	* Returns the organization with the matching UUID and company.
	*
	* @param uuid the organization's UUID
	* @param companyId the primary key of the company
	* @return the matching organization
	* @throws PortalException if a matching organization could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Organization getOrganizationByUuidAndCompanyId(
		java.lang.String uuid, long companyId) throws PortalException;

	/**
	* Updates the organization in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param organization the organization
	* @return the organization that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public Organization updateOrganization(Organization organization);

	/**
	* Updates the organization.
	*
	* @param companyId the primary key of the organization's company
	* @param organizationId the primary key of the organization
	* @param parentOrganizationId the primary key of organization's parent
	organization
	* @param name the organization's name
	* @param type the organization's type
	* @param regionId the primary key of the organization's region
	* @param countryId the primary key of the organization's country
	* @param statusId the organization's workflow status
	* @param comments the comments about the organization
	* @param logo whether to update the ogranization's logo
	* @param logoBytes the new logo image data
	* @param site whether the organization is to be associated with a main
	site
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set asset category IDs and asset tag
	names for the organization, and merge expando bridge attributes
	for the organization.
	* @return the organization
	*/
	public Organization updateOrganization(long companyId, long organizationId,
		long parentOrganizationId, java.lang.String name,
		java.lang.String type, long regionId, long countryId, long statusId,
		java.lang.String comments, boolean logo, byte[] logoBytes,
		boolean site, ServiceContext serviceContext) throws PortalException;

	/**
	* Updates the organization.
	*
	* @param companyId the primary key of the organization's company
	* @param organizationId the primary key of the organization
	* @param parentOrganizationId the primary key of organization's parent
	organization
	* @param name the organization's name
	* @param type the organization's type
	* @param regionId the primary key of the organization's region
	* @param countryId the primary key of the organization's country
	* @param statusId the organization's workflow status
	* @param comments the comments about the organization
	* @param site whether the organization is to be associated with a main
	site
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set asset category IDs and asset tag
	names for the organization, and merge expando bridge
	attributes for the organization.
	* @return the organization
	* @deprecated As of 7.0.0, replaced by {@link #updateOrganization(long,
	long, long, String, String, long, long, long, String,
	boolean, byte[], boolean, ServiceContext)}
	*/
	@java.lang.Deprecated
	public Organization updateOrganization(long companyId, long organizationId,
		long parentOrganizationId, java.lang.String name,
		java.lang.String type, long regionId, long countryId, long statusId,
		java.lang.String comments, boolean site, ServiceContext serviceContext)
		throws PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<Organization> searchOrganizations(
		long companyId, long parentOrganizationId, java.lang.String keywords,
		LinkedHashMap<java.lang.String, java.lang.Object> params, int start,
		int end, Sort sort) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<Organization> searchOrganizations(
		long companyId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, java.lang.String street, java.lang.String city,
		java.lang.String zip, java.lang.String region,
		java.lang.String country,
		LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andSearch, int start, int end, Sort sort)
		throws PortalException;

	/**
	* Returns an ordered range of all the organizations that match the
	* keywords, using the indexer. It is preferable to use this method instead
	* of the non-indexed version whenever possible for performance reasons.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the organization's company
	* @param parentOrganizationId the primary key of the organization's parent
	organization
	* @param keywords the keywords (space separated), which may occur in the
	organization's name, street, city, zipcode, type, region or
	country (optionally <code>null</code>)
	* @param params the finder parameters (optionally <code>null</code>). For
	more information see {@link
	com.liferay.portlet.usersadmin.util.OrganizationIndexer}
	* @param start the lower bound of the range of organizations to return
	* @param end the upper bound of the range of organizations to return (not
	inclusive)
	* @param sort the field and direction by which to sort (optionally
	<code>null</code>)
	* @return the matching organizations ordered by name
	* @see com.liferay.portlet.usersadmin.util.OrganizationIndexer
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits search(long companyId, long parentOrganizationId,
		java.lang.String keywords,
		LinkedHashMap<java.lang.String, java.lang.Object> params, int start,
		int end, Sort sort);

	/**
	* Returns an ordered range of all the organizations whose name, type, or
	* location fields match the keywords specified for them, using the indexer.
	* It is preferable to use this method instead of the non-indexed version
	* whenever possible for performance reasons.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the organization's company
	* @param parentOrganizationId the primary key of the organization's parent
	organization
	* @param name the name keywords (space separated, optionally
	<code>null</code>)
	* @param type the type keywords (optionally <code>null</code>)
	* @param street the street keywords (optionally <code>null</code>)
	* @param city the city keywords (optionally <code>null</code>)
	* @param zip the zipcode keywords (optionally <code>null</code>)
	* @param region the region keywords (optionally <code>null</code>)
	* @param country the country keywords (optionally <code>null</code>)
	* @param params the finder parameters (optionally <code>null</code>). For
	more information see {@link
	com.liferay.portlet.usersadmin.util.OrganizationIndexer}.
	* @param andSearch whether every field must match its keywords or just one
	field
	* @param start the lower bound of the range of organizations to return
	* @param end the upper bound of the range of organizations to return (not
	inclusive)
	* @param sort the field and direction by which to sort (optionally
	<code>null</code>)
	* @return the matching organizations ordered by <code>sort</code>
	* @see com.liferay.portlet.usersadmin.util.OrganizationIndexer
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits search(long companyId, long parentOrganizationId,
		java.lang.String name, java.lang.String type, java.lang.String street,
		java.lang.String city, java.lang.String zip, java.lang.String region,
		java.lang.String country,
		LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andSearch, int start, int end, Sort sort);

	/**
	* Returns the organizations and users that match the keywords specified for
	* them and belong to the parent organization.
	*
	* @param companyId the primary key of the organization and user's company
	* @param parentOrganizationId the primary key of the organization and
	user's parent organization
	* @param keywords the keywords (space separated), which may occur in the
	organization's name, type, or location fields or user's first
	name, middle name, last name, screen name, email address, or
	address fields
	* @param status user's workflow status
	* @param params the finder parameters (optionally <code>null</code>).
	* @param start the lower bound of the range of organizations and users to
	return
	* @param end the upper bound of the range of organizations and users to
	return (not inclusive)
	* @return the matching organizations and users
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits searchOrganizationsAndUsers(long companyId,
		long parentOrganizationId, java.lang.String keywords, int status,
		LinkedHashMap<java.lang.String, java.lang.Object> params, int start,
		int end, Sort[] sorts) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupOrganizationsCount(long groupId);

	/**
	* Returns the number of organizations and users belonging to the parent
	* organization.
	*
	* @param companyId the primary key of the organization and user's company
	* @param parentOrganizationId the primary key of the organization and
	user's parent organization
	* @param status the user's workflow status
	* @return the number of organizations and users belonging to the parent
	organization
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getOrganizationsAndUsersCount(long companyId,
		long parentOrganizationId, int status);

	/**
	* Returns the number of organizations.
	*
	* @return the number of organizations
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getOrganizationsCount();

	/**
	* Returns the number of organizations belonging to the parent organization.
	*
	* @param companyId the primary key of the organization's company
	* @param parentOrganizationId the primary key of the organization's parent
	organization
	* @return the number of organizations belonging to the parent organization
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getOrganizationsCount(long companyId, long parentOrganizationId);

	/**
	* Returns the count of suborganizations of the organization.
	*
	* @param companyId the primary key of the organization's company
	* @param organizationId the primary key of the organization
	* @return the count of suborganizations of the organization
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSuborganizationsCount(long companyId, long organizationId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUserOrganizationsCount(long userId);

	/**
	* Returns the number of organizations that match the keywords, type,
	* region, and country.
	*
	* @param companyId the primary key of the organization's company
	* @param parentOrganizationId the primary key of the organization's parent
	organization
	* @param keywords the keywords (space separated), which may occur in the
	organization's name, street, city, or zipcode (optionally
	<code>null</code>)
	* @param type the organization's type (optionally <code>null</code>)
	* @param regionId the primary key of the organization's region (optionally
	<code>null</code>)
	* @param countryId the primary key of the organization's country
	(optionally <code>null</code>)
	* @param params the finder parameters (optionally <code>null</code>). For
	more information see {@link
	com.liferay.portal.kernel.service.persistence.OrganizationFinder}
	* @return the number of matching organizations
	* @see com.liferay.portal.kernel.service.persistence.OrganizationFinder
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, long parentOrganizationId,
		java.lang.String keywords, java.lang.String type,
		java.lang.Long regionId, java.lang.Long countryId,
		LinkedHashMap<java.lang.String, java.lang.Object> params);

	/**
	* Returns the number of organizations with the type, region, and country,
	* and whose name, street, city, and zipcode match the keywords specified
	* for them.
	*
	* @param companyId the primary key of the organization's company
	* @param parentOrganizationId the primary key of the organization's parent
	organization
	* @param name the name keywords (space separated, optionally
	<code>null</code>)
	* @param type the organization's type (optionally <code>null</code>)
	* @param street the street keywords (optionally <code>null</code>)
	* @param city the city keywords (optionally <code>null</code>)
	* @param zip the zipcode keywords (optionally <code>null</code>)
	* @param regionId the primary key of the organization's region (optionally
	<code>null</code>)
	* @param countryId the primary key of the organization's country
	(optionally <code>null</code>)
	* @param params the finder parameters (optionally <code>null</code>). For
	more information see {@link
	com.liferay.portal.kernel.service.persistence.OrganizationFinder}
	* @param andOperator whether every field must match its keywords, or just
	one field. For example, &quot;organizations with the name
	'Employees' and city 'Chicago'&quot; vs &quot;organizations with
	the name 'Employees' or the city 'Chicago'&quot;.
	* @return the number of matching organizations
	* @see com.liferay.portal.kernel.service.persistence.OrganizationFinder
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, long parentOrganizationId,
		java.lang.String name, java.lang.String type, java.lang.String street,
		java.lang.String city, java.lang.String zip, java.lang.Long regionId,
		java.lang.Long countryId,
		LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator);

	/**
	* Returns the number of organizations and users that match the keywords
	* specified for them and belong to the parent organization.
	*
	* @param companyId the primary key of the organization and user's company
	* @param parentOrganizationId the primary key of the organization and
	user's parent organization
	* @param keywords the keywords (space separated), which may occur in the
	organization's name, type, or location fields or user's first
	name, middle name, last name, screen name, email address, or
	address fields
	* @param status user's workflow status
	* @param params the finder parameters (optionally <code>null</code>).
	* @return the number of matching organizations and users
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchOrganizationsAndUsersCount(long companyId,
		long parentOrganizationId, java.lang.String keywords, int status,
		LinkedHashMap<java.lang.String, java.lang.Object> params)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.OrganizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.OrganizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Organization> getGroupOrganizations(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Organization> getGroupOrganizations(long groupId, int start,
		int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Organization> getGroupOrganizations(long groupId, int start,
		int end, OrderByComparator<Organization> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Organization> getGroupUserOrganizations(long groupId,
		long userId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Organization> getNoAssetOrganizations();

	/**
	* Returns a range of all the organizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.OrganizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of organizations
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Organization> getOrganizations(int start, int end);

	/**
	* Returns all the organizations belonging to the parent organization.
	*
	* @param companyId the primary key of the organization's company
	* @param parentOrganizationId the primary key of the organization's parent
	organization
	* @return the organizations belonging to the parent organization
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Organization> getOrganizations(long companyId,
		long parentOrganizationId);

	/**
	* Returns a range of all the organizations belonging to the parent
	* organization.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the organization's company
	* @param parentOrganizationId the primary key of the organization's parent
	organization
	* @param start the lower bound of the range of organizations to return
	* @param end the upper bound of the range of organizations to return (not
	inclusive)
	* @return the range of organizations belonging to the parent organization
	* @see com.liferay.portal.kernel.service.persistence.OrganizationPersistence#findByC_P(
	long, long, int, int)
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Organization> getOrganizations(long companyId,
		long parentOrganizationId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Organization> getOrganizations(long userId, int start, int end,
		OrderByComparator<Organization> obc) throws PortalException;

	/**
	* Returns the organizations with the primary keys.
	*
	* @param organizationIds the primary keys of the organizations
	* @return the organizations with the primary keys
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Organization> getOrganizations(long[] organizationIds)
		throws PortalException;

	/**
	* Returns all the organizations and users belonging to the parent
	* organization.
	*
	* @param companyId the primary key of the organization and user's company
	* @param parentOrganizationId the primary key of the organization and
	user's parent organization
	* @param status the user's workflow status
	* @param start the lower bound of the range of organizations and users to
	return
	* @param end the upper bound of the range of organizations and users to
	return (not inclusive)
	* @param obc the comparator to order the organizations and users
	(optionally <code>null</code>)
	* @return the organizations and users belonging to the parent organization
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<java.lang.Object> getOrganizationsAndUsers(long companyId,
		long parentOrganizationId, int status, int start, int end,
		OrderByComparator<?> obc);

	/**
	* Returns the parent organizations in order by closest ancestor. The list
	* starts with the organization itself.
	*
	* @param organizationId the primary key of the organization
	* @return the parent organizations in order by closest ancestor
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Organization> getParentOrganizations(long organizationId)
		throws PortalException;

	/**
	* Returns the suborganizations of the organizations.
	*
	* @param organizations the organizations from which to get
	suborganizations
	* @return the suborganizations of the organizations
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Organization> getSuborganizations(
		List<Organization> organizations);

	/**
	* Returns the suborganizations of the organization.
	*
	* @param companyId the primary key of the organization's company
	* @param organizationId the primary key of the organization
	* @return the suborganizations of the organization
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Organization> getSuborganizations(long companyId,
		long organizationId);

	/**
	* Returns the intersection of <code>allOrganizations</code> and
	* <code>availableOrganizations</code>.
	*
	* @param allOrganizations the organizations to check for availability
	* @param availableOrganizations the available organizations
	* @return the intersection of <code>allOrganizations</code> and
	<code>availableOrganizations</code>
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Organization> getSubsetOrganizations(
		List<Organization> allOrganizations,
		List<Organization> availableOrganizations);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Organization> getUserOrganizations(long userId);

	/**
	* Returns all the organizations with which the user is explicitly
	* associated, optionally including the organizations that the user
	* administers or owns.
	*
	* <p>
	* A user is considered to be <i>explicitly</i> associated with an
	* organization if his account is individually created within the
	* organization or if the user is later added as a member.
	* </p>
	*
	* @param userId the primary key of the user
	* @param includeAdministrative whether to include the IDs of organizations
	that the user administers or owns, even if he's not a member of
	the organizations
	* @return the organizations with which the user is explicitly associated,
	optionally including the organizations that the user administers
	or owns
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Organization> getUserOrganizations(long userId,
		boolean includeAdministrative) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Organization> getUserOrganizations(long userId, int start,
		int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Organization> getUserOrganizations(long userId, int start,
		int end, OrderByComparator<Organization> orderByComparator);

	/**
	* Returns a name ordered range of all the organizations that match the
	* keywords, type, region, and country, without using the indexer. It is
	* preferable to use the indexed version {@link #search(long, long, String,
	* LinkedHashMap, int, int, Sort)} instead of this method wherever possible
	* for performance reasons.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the organization's company
	* @param parentOrganizationId the primary key of the organization's parent
	organization
	* @param keywords the keywords (space separated), which may occur in the
	organization's name, street, city, or zipcode (optionally
	<code>null</code>)
	* @param type the organization's type (optionally <code>null</code>)
	* @param regionId the primary key of the organization's region (optionally
	<code>null</code>)
	* @param countryId the primary key of the organization's country
	(optionally <code>null</code>)
	* @param params the finder params. For more information see {@link
	com.liferay.portal.kernel.service.persistence.OrganizationFinder}
	* @param start the lower bound of the range of organizations to return
	* @param end the upper bound of the range of organizations to return (not
	inclusive)
	* @return the matching organizations ordered by name
	* @see com.liferay.portal.kernel.service.persistence.OrganizationFinder
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Organization> search(long companyId, long parentOrganizationId,
		java.lang.String keywords, java.lang.String type,
		java.lang.Long regionId, java.lang.Long countryId,
		LinkedHashMap<java.lang.String, java.lang.Object> params, int start,
		int end);

	/**
	* Returns an ordered range of all the organizations that match the
	* keywords, type, region, and country, without using the indexer. It is
	* preferable to use the indexed version {@link #search(long, long, String,
	* String, String, String, String, String, String, LinkedHashMap, boolean,
	* int, int, Sort)} instead of this method wherever possible for performance
	* reasons.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the organization's company
	* @param parentOrganizationId the primary key of the organization's parent
	organization
	* @param keywords the keywords (space separated), which may occur in the
	organization's name, street, city, or zipcode (optionally
	<code>null</code>)
	* @param type the organization's type (optionally <code>null</code>)
	* @param regionId the primary key of the organization's region (optionally
	<code>null</code>)
	* @param countryId the primary key of the organization's country
	(optionally <code>null</code>)
	* @param params the finder params. For more information see {@link
	com.liferay.portal.kernel.service.persistence.OrganizationFinder}
	* @param start the lower bound of the range of organizations to return
	* @param end the upper bound of the range of organizations to return (not
	inclusive)
	* @param obc the comparator to order the organizations (optionally
	<code>null</code>)
	* @return the matching organizations ordered by comparator <code>obc</code>
	* @see com.liferay.portal.kernel.service.persistence.OrganizationFinder
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Organization> search(long companyId, long parentOrganizationId,
		java.lang.String keywords, java.lang.String type,
		java.lang.Long regionId, java.lang.Long countryId,
		LinkedHashMap<java.lang.String, java.lang.Object> params, int start,
		int end, OrderByComparator<Organization> obc);

	/**
	* Returns a name ordered range of all the organizations with the type,
	* region, and country, and whose name, street, city, and zipcode match the
	* keywords specified for them, without using the indexer. It is preferable
	* to use the indexed version {@link #search(long, long, String, String,
	* String, String, String, String, String, LinkedHashMap, boolean, int, int,
	* Sort)} instead of this method wherever possible for performance reasons.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the organization's company
	* @param parentOrganizationId the primary key of the organization's parent
	* @param name the name keywords (space separated, optionally
	<code>null</code>)
	* @param type the organization's type (optionally <code>null</code>)
	* @param street the street keywords (optionally <code>null</code>)
	* @param city the city keywords (optionally <code>null</code>)
	* @param zip the zipcode keywords (optionally <code>null</code>)
	* @param regionId the primary key of the organization's region (optionally
	<code>null</code>)
	* @param countryId the primary key of the organization's country
	(optionally <code>null</code>)
	* @param params the finder parameters (optionally <code>null</code>). For
	more information see {@link
	com.liferay.portal.kernel.service.persistence.OrganizationFinder}
	* @param andOperator whether every field must match its keywords, or just
	one field. For example, &quot;organizations with the name
	'Employees' and city 'Chicago'&quot; vs &quot;organizations with
	the name 'Employees' or the city 'Chicago'&quot;.
	* @param start the lower bound of the range of organizations to return
	* @param end the upper bound of the range of organizations to return (not
	inclusive)
	* @return the matching organizations ordered by name
	* @see com.liferay.portal.kernel.service.persistence.OrganizationFinder
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Organization> search(long companyId, long parentOrganizationId,
		java.lang.String name, java.lang.String type, java.lang.String street,
		java.lang.String city, java.lang.String zip, java.lang.Long regionId,
		java.lang.Long countryId,
		LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator, int start, int end);

	/**
	* Returns an ordered range of all the organizations with the type, region,
	* and country, and whose name, street, city, and zipcode match the keywords
	* specified for them, without using the indexer. It is preferable to use
	* the indexed version {@link #search(long, long, String, String, String,
	* String, String, String, String, LinkedHashMap, boolean, int, int, Sort)}
	* instead of this method wherever possible for performance reasons.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param companyId the primary key of the organization's company
	* @param parentOrganizationId the primary key of the organization's parent
	organization
	* @param name the name keywords (space separated, optionally
	<code>null</code>)
	* @param type the organization's type (optionally <code>null</code>)
	* @param street the street keywords (optionally <code>null</code>)
	* @param city the city keywords (optionally <code>null</code>)
	* @param zip the zipcode keywords (optionally <code>null</code>)
	* @param regionId the primary key of the organization's region (optionally
	<code>null</code>)
	* @param countryId the primary key of the organization's country
	(optionally <code>null</code>)
	* @param params the finder parameters (optionally <code>null</code>). For
	more information see {@link
	com.liferay.portal.kernel.service.persistence.OrganizationFinder}
	* @param andOperator whether every field must match its keywords, or just
	one field. For example, &quot;organizations with the name
	'Employees' and city 'Chicago'&quot; vs &quot;organizations with
	the name 'Employees' or the city 'Chicago'&quot;.
	* @param start the lower bound of the range of organizations to return
	* @param end the upper bound of the range of organizations to return (not
	inclusive)
	* @param obc the comparator to order the organizations (optionally
	<code>null</code>)
	* @return the matching organizations ordered by comparator <code>obc</code>
	* @see com.liferay.portal.kernel.service.persistence.OrganizationFinder
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Organization> search(long companyId, long parentOrganizationId,
		java.lang.String name, java.lang.String type, java.lang.String street,
		java.lang.String city, java.lang.String zip, java.lang.Long regionId,
		java.lang.Long countryId,
		LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator, int start, int end,
		OrderByComparator<Organization> obc);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	/**
	* Returns the primary key of the organization with the name.
	*
	* @param companyId the primary key of the organization's company
	* @param name the organization's name
	* @return the primary key of the organization with the name, or
	<code>0</code> if the organization could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long getOrganizationId(long companyId, java.lang.String name);

	/**
	* Returns the groupIds of the groups associated with the organization.
	*
	* @param organizationId the organizationId of the organization
	* @return long[] the groupIds of groups associated with the organization
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getGroupPrimaryKeys(long organizationId);

	/**
	* Returns all the IDs of organizations with which the user is explicitly
	* associated, optionally including the IDs of organizations that the user
	* administers or owns.
	*
	* <p>
	* A user is considered to be <i>explicitly</i> associated with an
	* organization if his account is individually created within the
	* organization or if the user is later added to it.
	* </p>
	*
	* @param userId the primary key of the user
	* @param includeAdministrative whether to include the IDs of organizations
	that the user administers or owns, even if he's not a member of
	the organizations
	* @return the IDs of organizations with which the user is explicitly
	associated, optionally including the IDs of organizations that
	the user administers or owns
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getUserOrganizationIds(long userId,
		boolean includeAdministrative) throws PortalException;

	/**
	* Returns the userIds of the users associated with the organization.
	*
	* @param organizationId the organizationId of the organization
	* @return long[] the userIds of users associated with the organization
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getUserPrimaryKeys(long organizationId);

	public void addGroupOrganization(long groupId, Organization organization);

	public void addGroupOrganization(long groupId, long organizationId);

	public void addGroupOrganizations(long groupId,
		List<Organization> organizations);

	public void addGroupOrganizations(long groupId, long[] organizationIds);

	/**
	* Adds a resource for each type of permission available on the
	* organization.
	*
	* @param userId the primary key of the creator/owner of the organization
	* @param organization the organization
	*/
	public void addOrganizationResources(long userId, Organization organization)
		throws PortalException;

	/**
	* Assigns the password policy to the organizations, removing any other
	* currently assigned password policies.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @param organizationIds the primary keys of the organizations
	*/
	public void addPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds);

	public void addUserOrganization(long userId, Organization organization);

	public void addUserOrganization(long userId, long organizationId);

	public void addUserOrganizations(long userId,
		List<Organization> organizations);

	public void addUserOrganizations(long userId, long[] organizationIds);

	public void clearGroupOrganizations(long groupId);

	public void clearUserOrganizations(long userId);

	public void deleteGroupOrganization(long groupId, Organization organization);

	public void deleteGroupOrganization(long groupId, long organizationId);

	public void deleteGroupOrganizations(long groupId,
		List<Organization> organizations);

	public void deleteGroupOrganizations(long groupId, long[] organizationIds);

	/**
	* Deletes the organization's logo.
	*
	* @param organizationId the primary key of the organization
	*/
	public void deleteLogo(long organizationId) throws PortalException;

	public void deleteUserOrganization(long userId, Organization organization);

	public void deleteUserOrganization(long userId, long organizationId);

	public void deleteUserOrganizations(long userId,
		List<Organization> organizations);

	public void deleteUserOrganizations(long userId, long[] organizationIds);

	/**
	* Rebuilds the organization's tree.
	*
	* <p>
	* Only call this method if the tree has become stale through operations
	* other than normal CRUD. Under normal circumstances the tree is
	* automatically rebuilt whenever necessary.
	* </p>
	*
	* @param companyId the primary key of the organization's company
	*/
	public void rebuildTree(long companyId) throws PortalException;

	public void setGroupOrganizations(long groupId, long[] organizationIds);

	public void setUserOrganizations(long userId, long[] organizationIds);

	/**
	* Removes the organizations from the group.
	*
	* @param groupId the primary key of the group
	* @param organizationIds the primary keys of the organizations
	*/
	public void unsetGroupOrganizations(long groupId, long[] organizationIds);

	/**
	* Removes the organizations from the password policy.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @param organizationIds the primary keys of the organizations
	*/
	public void unsetPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds);

	/**
	* Updates the organization's asset with the new asset categories and tag
	* names, removing and adding asset categories and tag names as necessary.
	*
	* @param userId the primary key of the user
	* @param organization the organization
	* @param assetCategoryIds the primary keys of the asset categories
	* @param assetTagNames the asset tag names
	*/
	public void updateAsset(long userId, Organization organization,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws PortalException;
}