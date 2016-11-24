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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for Organization. This utility wraps
 * {@link com.liferay.portal.service.impl.OrganizationLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see OrganizationLocalService
 * @see com.liferay.portal.service.base.OrganizationLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.OrganizationLocalServiceImpl
 * @generated
 */
@ProviderType
public class OrganizationLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.OrganizationLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static boolean hasGroupOrganization(long groupId, long organizationId) {
		return getService().hasGroupOrganization(groupId, organizationId);
	}

	public static boolean hasGroupOrganizations(long groupId) {
		return getService().hasGroupOrganizations(groupId);
	}

	/**
	* Returns <code>true</code> if the password policy has been assigned to the
	* organization.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @param organizationId the primary key of the organization
	* @return <code>true</code> if the password policy has been assigned to the
	organization; <code>false</code> otherwise
	*/
	public static boolean hasPasswordPolicyOrganization(long passwordPolicyId,
		long organizationId) {
		return getService()
				   .hasPasswordPolicyOrganization(passwordPolicyId,
			organizationId);
	}

	public static boolean hasUserOrganization(long userId, long organizationId) {
		return getService().hasUserOrganization(userId, organizationId);
	}

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
	public static boolean hasUserOrganization(long userId, long organizationId,
		boolean inheritSuborganizations, boolean includeSpecifiedOrganization)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .hasUserOrganization(userId, organizationId,
			inheritSuborganizations, includeSpecifiedOrganization);
	}

	public static boolean hasUserOrganizations(long userId) {
		return getService().hasUserOrganizations(userId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Adds the organization to the database. Also notifies the appropriate model listeners.
	*
	* @param organization the organization
	* @return the organization that was added
	*/
	public static com.liferay.portal.kernel.model.Organization addOrganization(
		com.liferay.portal.kernel.model.Organization organization) {
		return getService().addOrganization(organization);
	}

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
	public static com.liferay.portal.kernel.model.Organization addOrganization(
		long userId, long parentOrganizationId, java.lang.String name,
		boolean site)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addOrganization(userId, parentOrganizationId, name, site);
	}

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
	public static com.liferay.portal.kernel.model.Organization addOrganization(
		long userId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, long regionId, long countryId, long statusId,
		java.lang.String comments, boolean site, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addOrganization(userId, parentOrganizationId, name, type,
			regionId, countryId, statusId, comments, site, serviceContext);
	}

	/**
	* Creates a new organization with the primary key. Does not add the organization to the database.
	*
	* @param organizationId the primary key for the new organization
	* @return the new organization
	*/
	public static com.liferay.portal.kernel.model.Organization createOrganization(
		long organizationId) {
		return getService().createOrganization(organizationId);
	}

	/**
	* Deletes the organization from the database. Also notifies the appropriate model listeners.
	*
	* @param organization the organization
	* @return the organization that was removed
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.Organization deleteOrganization(
		com.liferay.portal.kernel.model.Organization organization)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteOrganization(organization);
	}

	/**
	* Deletes the organization with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param organizationId the primary key of the organization
	* @return the organization that was removed
	* @throws PortalException if a organization with the primary key could not be found
	*/
	public static com.liferay.portal.kernel.model.Organization deleteOrganization(
		long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteOrganization(organizationId);
	}

	/**
	* Returns the organization with the name.
	*
	* @param companyId the primary key of the organization's company
	* @param name the organization's name
	* @return the organization with the name, or <code>null</code> if no
	organization could be found
	*/
	public static com.liferay.portal.kernel.model.Organization fetchOrganization(
		long companyId, java.lang.String name) {
		return getService().fetchOrganization(companyId, name);
	}

	public static com.liferay.portal.kernel.model.Organization fetchOrganization(
		long organizationId) {
		return getService().fetchOrganization(organizationId);
	}

	/**
	* Returns the organization with the matching UUID and company.
	*
	* @param uuid the organization's UUID
	* @param companyId the primary key of the company
	* @return the matching organization, or <code>null</code> if a matching organization could not be found
	*/
	public static com.liferay.portal.kernel.model.Organization fetchOrganizationByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService().fetchOrganizationByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns the organization with the name.
	*
	* @param companyId the primary key of the organization's company
	* @param name the organization's name
	* @return the organization with the name
	*/
	public static com.liferay.portal.kernel.model.Organization getOrganization(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getOrganization(companyId, name);
	}

	/**
	* Returns the organization with the primary key.
	*
	* @param organizationId the primary key of the organization
	* @return the organization
	* @throws PortalException if a organization with the primary key could not be found
	*/
	public static com.liferay.portal.kernel.model.Organization getOrganization(
		long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getOrganization(organizationId);
	}

	/**
	* Returns the organization with the matching UUID and company.
	*
	* @param uuid the organization's UUID
	* @param companyId the primary key of the company
	* @return the matching organization
	* @throws PortalException if a matching organization could not be found
	*/
	public static com.liferay.portal.kernel.model.Organization getOrganizationByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getOrganizationByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Updates the organization in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param organization the organization
	* @return the organization that was updated
	*/
	public static com.liferay.portal.kernel.model.Organization updateOrganization(
		com.liferay.portal.kernel.model.Organization organization) {
		return getService().updateOrganization(organization);
	}

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
	public static com.liferay.portal.kernel.model.Organization updateOrganization(
		long companyId, long organizationId, long parentOrganizationId,
		java.lang.String name, java.lang.String type, long regionId,
		long countryId, long statusId, java.lang.String comments, boolean logo,
		byte[] logoBytes, boolean site, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateOrganization(companyId, organizationId,
			parentOrganizationId, name, type, regionId, countryId, statusId,
			comments, logo, logoBytes, site, serviceContext);
	}

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
	@Deprecated
	public static com.liferay.portal.kernel.model.Organization updateOrganization(
		long companyId, long organizationId, long parentOrganizationId,
		java.lang.String name, java.lang.String type, long regionId,
		long countryId, long statusId, java.lang.String comments, boolean site,
		ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateOrganization(companyId, organizationId,
			parentOrganizationId, name, type, regionId, countryId, statusId,
			comments, site, serviceContext);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portal.kernel.model.Organization> searchOrganizations(
		long companyId, long parentOrganizationId, java.lang.String keywords,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchOrganizations(companyId, parentOrganizationId,
			keywords, params, start, end, sort);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portal.kernel.model.Organization> searchOrganizations(
		long companyId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, java.lang.String street, java.lang.String city,
		java.lang.String zip, java.lang.String region,
		java.lang.String country,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andSearch, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchOrganizations(companyId, parentOrganizationId, name,
			type, street, city, zip, region, country, params, andSearch, start,
			end, sort);
	}

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
	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		long parentOrganizationId, java.lang.String keywords,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.search.Sort sort) {
		return getService()
				   .search(companyId, parentOrganizationId, keywords, params,
			start, end, sort);
	}

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
	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		long parentOrganizationId, java.lang.String name,
		java.lang.String type, java.lang.String street, java.lang.String city,
		java.lang.String zip, java.lang.String region,
		java.lang.String country,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andSearch, int start, int end,
		com.liferay.portal.kernel.search.Sort sort) {
		return getService()
				   .search(companyId, parentOrganizationId, name, type, street,
			city, zip, region, country, params, andSearch, start, end, sort);
	}

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
	public static com.liferay.portal.kernel.search.Hits searchOrganizationsAndUsers(
		long companyId, long parentOrganizationId, java.lang.String keywords,
		int status,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.search.Sort[] sorts)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchOrganizationsAndUsers(companyId,
			parentOrganizationId, keywords, status, params, start, end, sorts);
	}

	public static int getGroupOrganizationsCount(long groupId) {
		return getService().getGroupOrganizationsCount(groupId);
	}

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
	public static int getOrganizationsAndUsersCount(long companyId,
		long parentOrganizationId, int status) {
		return getService()
				   .getOrganizationsAndUsersCount(companyId,
			parentOrganizationId, status);
	}

	/**
	* Returns the number of organizations.
	*
	* @return the number of organizations
	*/
	public static int getOrganizationsCount() {
		return getService().getOrganizationsCount();
	}

	/**
	* Returns the number of organizations belonging to the parent organization.
	*
	* @param companyId the primary key of the organization's company
	* @param parentOrganizationId the primary key of the organization's parent
	organization
	* @return the number of organizations belonging to the parent organization
	*/
	public static int getOrganizationsCount(long companyId,
		long parentOrganizationId) {
		return getService()
				   .getOrganizationsCount(companyId, parentOrganizationId);
	}

	/**
	* Returns the count of suborganizations of the organization.
	*
	* @param companyId the primary key of the organization's company
	* @param organizationId the primary key of the organization
	* @return the count of suborganizations of the organization
	*/
	public static int getSuborganizationsCount(long companyId,
		long organizationId) {
		return getService().getSuborganizationsCount(companyId, organizationId);
	}

	public static int getUserOrganizationsCount(long userId) {
		return getService().getUserOrganizationsCount(userId);
	}

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
	public static int searchCount(long companyId, long parentOrganizationId,
		java.lang.String keywords, java.lang.String type,
		java.lang.Long regionId, java.lang.Long countryId,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params) {
		return getService()
				   .searchCount(companyId, parentOrganizationId, keywords,
			type, regionId, countryId, params);
	}

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
	public static int searchCount(long companyId, long parentOrganizationId,
		java.lang.String name, java.lang.String type, java.lang.String street,
		java.lang.String city, java.lang.String zip, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator) {
		return getService()
				   .searchCount(companyId, parentOrganizationId, name, type,
			street, city, zip, regionId, countryId, params, andOperator);
	}

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
	public static int searchOrganizationsAndUsersCount(long companyId,
		long parentOrganizationId, java.lang.String keywords, int status,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchOrganizationsAndUsersCount(companyId,
			parentOrganizationId, keywords, status, params);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Organization> getGroupOrganizations(
		long groupId) {
		return getService().getGroupOrganizations(groupId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Organization> getGroupOrganizations(
		long groupId, int start, int end) {
		return getService().getGroupOrganizations(groupId, start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Organization> getGroupOrganizations(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Organization> orderByComparator) {
		return getService()
				   .getGroupOrganizations(groupId, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Organization> getGroupUserOrganizations(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getGroupUserOrganizations(groupId, userId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Organization> getNoAssetOrganizations() {
		return getService().getNoAssetOrganizations();
	}

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
	public static java.util.List<com.liferay.portal.kernel.model.Organization> getOrganizations(
		int start, int end) {
		return getService().getOrganizations(start, end);
	}

	/**
	* Returns all the organizations belonging to the parent organization.
	*
	* @param companyId the primary key of the organization's company
	* @param parentOrganizationId the primary key of the organization's parent
	organization
	* @return the organizations belonging to the parent organization
	*/
	public static java.util.List<com.liferay.portal.kernel.model.Organization> getOrganizations(
		long companyId, long parentOrganizationId) {
		return getService().getOrganizations(companyId, parentOrganizationId);
	}

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
	public static java.util.List<com.liferay.portal.kernel.model.Organization> getOrganizations(
		long companyId, long parentOrganizationId, int start, int end) {
		return getService()
				   .getOrganizations(companyId, parentOrganizationId, start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Organization> getOrganizations(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Organization> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getOrganizations(userId, start, end, obc);
	}

	/**
	* Returns the organizations with the primary keys.
	*
	* @param organizationIds the primary keys of the organizations
	* @return the organizations with the primary keys
	*/
	public static java.util.List<com.liferay.portal.kernel.model.Organization> getOrganizations(
		long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getOrganizations(organizationIds);
	}

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
	public static java.util.List<java.lang.Object> getOrganizationsAndUsers(
		long companyId, long parentOrganizationId, int status, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator<?> obc) {
		return getService()
				   .getOrganizationsAndUsers(companyId, parentOrganizationId,
			status, start, end, obc);
	}

	/**
	* Returns the parent organizations in order by closest ancestor. The list
	* starts with the organization itself.
	*
	* @param organizationId the primary key of the organization
	* @return the parent organizations in order by closest ancestor
	*/
	public static java.util.List<com.liferay.portal.kernel.model.Organization> getParentOrganizations(
		long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getParentOrganizations(organizationId);
	}

	/**
	* Returns the suborganizations of the organizations.
	*
	* @param organizations the organizations from which to get
	suborganizations
	* @return the suborganizations of the organizations
	*/
	public static java.util.List<com.liferay.portal.kernel.model.Organization> getSuborganizations(
		java.util.List<com.liferay.portal.kernel.model.Organization> organizations) {
		return getService().getSuborganizations(organizations);
	}

	/**
	* Returns the suborganizations of the organization.
	*
	* @param companyId the primary key of the organization's company
	* @param organizationId the primary key of the organization
	* @return the suborganizations of the organization
	*/
	public static java.util.List<com.liferay.portal.kernel.model.Organization> getSuborganizations(
		long companyId, long organizationId) {
		return getService().getSuborganizations(companyId, organizationId);
	}

	/**
	* Returns the intersection of <code>allOrganizations</code> and
	* <code>availableOrganizations</code>.
	*
	* @param allOrganizations the organizations to check for availability
	* @param availableOrganizations the available organizations
	* @return the intersection of <code>allOrganizations</code> and
	<code>availableOrganizations</code>
	*/
	public static java.util.List<com.liferay.portal.kernel.model.Organization> getSubsetOrganizations(
		java.util.List<com.liferay.portal.kernel.model.Organization> allOrganizations,
		java.util.List<com.liferay.portal.kernel.model.Organization> availableOrganizations) {
		return getService()
				   .getSubsetOrganizations(allOrganizations,
			availableOrganizations);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Organization> getUserOrganizations(
		long userId) {
		return getService().getUserOrganizations(userId);
	}

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
	public static java.util.List<com.liferay.portal.kernel.model.Organization> getUserOrganizations(
		long userId, boolean includeAdministrative)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUserOrganizations(userId, includeAdministrative);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Organization> getUserOrganizations(
		long userId, int start, int end) {
		return getService().getUserOrganizations(userId, start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Organization> getUserOrganizations(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Organization> orderByComparator) {
		return getService()
				   .getUserOrganizations(userId, start, end, orderByComparator);
	}

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
	public static java.util.List<com.liferay.portal.kernel.model.Organization> search(
		long companyId, long parentOrganizationId, java.lang.String keywords,
		java.lang.String type, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end) {
		return getService()
				   .search(companyId, parentOrganizationId, keywords, type,
			regionId, countryId, params, start, end);
	}

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
	public static java.util.List<com.liferay.portal.kernel.model.Organization> search(
		long companyId, long parentOrganizationId, java.lang.String keywords,
		java.lang.String type, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Organization> obc) {
		return getService()
				   .search(companyId, parentOrganizationId, keywords, type,
			regionId, countryId, params, start, end, obc);
	}

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
	public static java.util.List<com.liferay.portal.kernel.model.Organization> search(
		long companyId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, java.lang.String street, java.lang.String city,
		java.lang.String zip, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator, int start, int end) {
		return getService()
				   .search(companyId, parentOrganizationId, name, type, street,
			city, zip, regionId, countryId, params, andOperator, start, end);
	}

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
	public static java.util.List<com.liferay.portal.kernel.model.Organization> search(
		long companyId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, java.lang.String street, java.lang.String city,
		java.lang.String zip, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.Organization> obc) {
		return getService()
				   .search(companyId, parentOrganizationId, name, type, street,
			city, zip, regionId, countryId, params, andOperator, start, end, obc);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	/**
	* Returns the primary key of the organization with the name.
	*
	* @param companyId the primary key of the organization's company
	* @param name the organization's name
	* @return the primary key of the organization with the name, or
	<code>0</code> if the organization could not be found
	*/
	public static long getOrganizationId(long companyId, java.lang.String name) {
		return getService().getOrganizationId(companyId, name);
	}

	/**
	* Returns the groupIds of the groups associated with the organization.
	*
	* @param organizationId the organizationId of the organization
	* @return long[] the groupIds of groups associated with the organization
	*/
	public static long[] getGroupPrimaryKeys(long organizationId) {
		return getService().getGroupPrimaryKeys(organizationId);
	}

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
	public static long[] getUserOrganizationIds(long userId,
		boolean includeAdministrative)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUserOrganizationIds(userId, includeAdministrative);
	}

	/**
	* Returns the userIds of the users associated with the organization.
	*
	* @param organizationId the organizationId of the organization
	* @return long[] the userIds of users associated with the organization
	*/
	public static long[] getUserPrimaryKeys(long organizationId) {
		return getService().getUserPrimaryKeys(organizationId);
	}

	public static void addGroupOrganization(long groupId,
		com.liferay.portal.kernel.model.Organization organization) {
		getService().addGroupOrganization(groupId, organization);
	}

	public static void addGroupOrganization(long groupId, long organizationId) {
		getService().addGroupOrganization(groupId, organizationId);
	}

	public static void addGroupOrganizations(long groupId,
		java.util.List<com.liferay.portal.kernel.model.Organization> organizations) {
		getService().addGroupOrganizations(groupId, organizations);
	}

	public static void addGroupOrganizations(long groupId,
		long[] organizationIds) {
		getService().addGroupOrganizations(groupId, organizationIds);
	}

	/**
	* Adds a resource for each type of permission available on the
	* organization.
	*
	* @param userId the primary key of the creator/owner of the organization
	* @param organization the organization
	*/
	public static void addOrganizationResources(long userId,
		com.liferay.portal.kernel.model.Organization organization)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().addOrganizationResources(userId, organization);
	}

	/**
	* Assigns the password policy to the organizations, removing any other
	* currently assigned password policies.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @param organizationIds the primary keys of the organizations
	*/
	public static void addPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds) {
		getService()
			.addPasswordPolicyOrganizations(passwordPolicyId, organizationIds);
	}

	public static void addUserOrganization(long userId,
		com.liferay.portal.kernel.model.Organization organization) {
		getService().addUserOrganization(userId, organization);
	}

	public static void addUserOrganization(long userId, long organizationId) {
		getService().addUserOrganization(userId, organizationId);
	}

	public static void addUserOrganizations(long userId,
		java.util.List<com.liferay.portal.kernel.model.Organization> organizations) {
		getService().addUserOrganizations(userId, organizations);
	}

	public static void addUserOrganizations(long userId, long[] organizationIds) {
		getService().addUserOrganizations(userId, organizationIds);
	}

	public static void clearGroupOrganizations(long groupId) {
		getService().clearGroupOrganizations(groupId);
	}

	public static void clearUserOrganizations(long userId) {
		getService().clearUserOrganizations(userId);
	}

	public static void deleteGroupOrganization(long groupId,
		com.liferay.portal.kernel.model.Organization organization) {
		getService().deleteGroupOrganization(groupId, organization);
	}

	public static void deleteGroupOrganization(long groupId, long organizationId) {
		getService().deleteGroupOrganization(groupId, organizationId);
	}

	public static void deleteGroupOrganizations(long groupId,
		java.util.List<com.liferay.portal.kernel.model.Organization> organizations) {
		getService().deleteGroupOrganizations(groupId, organizations);
	}

	public static void deleteGroupOrganizations(long groupId,
		long[] organizationIds) {
		getService().deleteGroupOrganizations(groupId, organizationIds);
	}

	/**
	* Deletes the organization's logo.
	*
	* @param organizationId the primary key of the organization
	*/
	public static void deleteLogo(long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteLogo(organizationId);
	}

	public static void deleteUserOrganization(long userId,
		com.liferay.portal.kernel.model.Organization organization) {
		getService().deleteUserOrganization(userId, organization);
	}

	public static void deleteUserOrganization(long userId, long organizationId) {
		getService().deleteUserOrganization(userId, organizationId);
	}

	public static void deleteUserOrganizations(long userId,
		java.util.List<com.liferay.portal.kernel.model.Organization> organizations) {
		getService().deleteUserOrganizations(userId, organizations);
	}

	public static void deleteUserOrganizations(long userId,
		long[] organizationIds) {
		getService().deleteUserOrganizations(userId, organizationIds);
	}

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
	public static void rebuildTree(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().rebuildTree(companyId);
	}

	public static void setGroupOrganizations(long groupId,
		long[] organizationIds) {
		getService().setGroupOrganizations(groupId, organizationIds);
	}

	public static void setUserOrganizations(long userId, long[] organizationIds) {
		getService().setUserOrganizations(userId, organizationIds);
	}

	/**
	* Removes the organizations from the group.
	*
	* @param groupId the primary key of the group
	* @param organizationIds the primary keys of the organizations
	*/
	public static void unsetGroupOrganizations(long groupId,
		long[] organizationIds) {
		getService().unsetGroupOrganizations(groupId, organizationIds);
	}

	/**
	* Removes the organizations from the password policy.
	*
	* @param passwordPolicyId the primary key of the password policy
	* @param organizationIds the primary keys of the organizations
	*/
	public static void unsetPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds) {
		getService()
			.unsetPasswordPolicyOrganizations(passwordPolicyId, organizationIds);
	}

	/**
	* Updates the organization's asset with the new asset categories and tag
	* names, removing and adding asset categories and tag names as necessary.
	*
	* @param userId the primary key of the user
	* @param organization the organization
	* @param assetCategoryIds the primary keys of the asset categories
	* @param assetTagNames the asset tag names
	*/
	public static void updateAsset(long userId,
		com.liferay.portal.kernel.model.Organization organization,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.updateAsset(userId, organization, assetCategoryIds, assetTagNames);
	}

	public static OrganizationLocalService getService() {
		if (_service == null) {
			_service = (OrganizationLocalService)PortalBeanLocatorUtil.locate(OrganizationLocalService.class.getName());

			ReferenceRegistry.registerReference(OrganizationLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static OrganizationLocalService _service;
}