/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

/**
 * <p>
 * This class is a wrapper for {@link OrganizationLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrganizationLocalService
 * @generated
 */
public class OrganizationLocalServiceWrapper implements OrganizationLocalService {
	public OrganizationLocalServiceWrapper(
		OrganizationLocalService organizationLocalService) {
		_organizationLocalService = organizationLocalService;
	}

	/**
	* Adds the organization to the database. Also notifies the appropriate model listeners.
	*
	* @param organization the organization
	* @return the organization that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Organization addOrganization(
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.addOrganization(organization);
	}

	/**
	* Creates a new organization with the primary key. Does not add the organization to the database.
	*
	* @param organizationId the primary key for the new organization
	* @return the new organization
	*/
	public com.liferay.portal.model.Organization createOrganization(
		long organizationId) {
		return _organizationLocalService.createOrganization(organizationId);
	}

	/**
	* Deletes the organization with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param organizationId the primary key of the organization
	* @throws PortalException if a organization with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteOrganization(long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_organizationLocalService.deleteOrganization(organizationId);
	}

	/**
	* Deletes the organization from the database. Also notifies the appropriate model listeners.
	*
	* @param organization the organization
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void deleteOrganization(
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_organizationLocalService.deleteOrganization(organization);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the organization with the primary key.
	*
	* @param organizationId the primary key of the organization
	* @return the organization
	* @throws PortalException if a organization with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Organization getOrganization(
		long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.getOrganization(organizationId);
	}

	/**
	* Returns a range of all the organizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of organizations
	* @param end the upper bound of the range of organizations (not inclusive)
	* @return the range of organizations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.getOrganizations(start, end);
	}

	/**
	* Returns the number of organizations.
	*
	* @return the number of organizations
	* @throws SystemException if a system exception occurred
	*/
	public int getOrganizationsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.getOrganizationsCount();
	}

	/**
	* Updates the organization in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param organization the organization
	* @return the organization that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Organization updateOrganization(
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.updateOrganization(organization);
	}

	/**
	* Updates the organization in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param organization the organization
	* @param merge whether to merge the organization with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the organization that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Organization updateOrganization(
		com.liferay.portal.model.Organization organization, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.updateOrganization(organization, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _organizationLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_organizationLocalService.setBeanIdentifier(beanIdentifier);
	}

	public void addGroupOrganizations(long groupId, long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_organizationLocalService.addGroupOrganizations(groupId, organizationIds);
	}

	public com.liferay.portal.model.Organization addOrganization(long userId,
		long parentOrganizationId, java.lang.String name,
		java.lang.String type, boolean recursable, long regionId,
		long countryId, int statusId, java.lang.String comments, boolean site,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.addOrganization(userId,
			parentOrganizationId, name, type, recursable, regionId, countryId,
			statusId, comments, site, serviceContext);
	}

	public void addOrganizationResources(long userId,
		com.liferay.portal.model.Organization organization)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_organizationLocalService.addOrganizationResources(userId, organization);
	}

	public void addPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_organizationLocalService.addPasswordPolicyOrganizations(passwordPolicyId,
			organizationIds);
	}

	public void deleteLogo(long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_organizationLocalService.deleteLogo(organizationId);
	}

	public java.util.List<com.liferay.portal.model.Organization> getGroupOrganizations(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.getGroupOrganizations(groupId);
	}

	public com.liferay.portal.model.Organization getOrganization(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.getOrganization(companyId, name);
	}

	public long getOrganizationId(long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.getOrganizationId(companyId, name);
	}

	public java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		long companyId, long parentOrganizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.getOrganizations(companyId,
			parentOrganizationId);
	}

	public java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		long companyId, long parentOrganizationId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.getOrganizations(companyId,
			parentOrganizationId, start, end);
	}

	public java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.getOrganizations(organizationIds);
	}

	public int getOrganizationsCount(long companyId, long parentOrganizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.getOrganizationsCount(companyId,
			parentOrganizationId);
	}

	public java.util.List<com.liferay.portal.model.Organization> getParentOrganizations(
		long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.getParentOrganizations(organizationId);
	}

	public java.util.List<com.liferay.portal.model.Organization> getSuborganizations(
		java.util.List<com.liferay.portal.model.Organization> organizations)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.getSuborganizations(organizations);
	}

	public java.util.List<com.liferay.portal.model.Organization> getSubsetOrganizations(
		java.util.List<com.liferay.portal.model.Organization> allOrganizations,
		java.util.List<com.liferay.portal.model.Organization> availableOrganizations) {
		return _organizationLocalService.getSubsetOrganizations(allOrganizations,
			availableOrganizations);
	}

	public java.util.List<com.liferay.portal.model.Organization> getUserOrganizations(
		long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.getUserOrganizations(userId);
	}

	public java.util.List<com.liferay.portal.model.Organization> getUserOrganizations(
		long userId, boolean inheritUserGroups)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.getUserOrganizations(userId,
			inheritUserGroups);
	}

	public java.util.List<com.liferay.portal.model.Organization> getUserOrganizations(
		long userId, boolean inheritUserGroups, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.getUserOrganizations(userId,
			inheritUserGroups, start, end);
	}

	public java.util.List<com.liferay.portal.model.Organization> getUserOrganizations(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.getUserOrganizations(userId, start, end);
	}

	public int getUserOrganizationsCount(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.getUserOrganizationsCount(userId);
	}

	public boolean hasGroupOrganization(long groupId, long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.hasGroupOrganization(groupId,
			organizationId);
	}

	public boolean hasPasswordPolicyOrganization(long passwordPolicyId,
		long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.hasPasswordPolicyOrganization(passwordPolicyId,
			organizationId);
	}

	public boolean hasUserOrganization(long userId, long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.hasUserOrganization(userId,
			organizationId);
	}

	public boolean hasUserOrganization(long userId, long organizationId,
		boolean inheritSuborganizations, boolean inheritUserGroups,
		boolean includeSpecifiedOrganization)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.hasUserOrganization(userId,
			organizationId, inheritSuborganizations, inheritUserGroups,
			includeSpecifiedOrganization);
	}

	public void rebuildTree(long companyId, boolean force)
		throws com.liferay.portal.kernel.exception.SystemException {
		_organizationLocalService.rebuildTree(companyId, force);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long parentOrganizationId, java.lang.String keywords,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.search(companyId,
			parentOrganizationId, keywords, params, start, end, sort);
	}

	public java.util.List<com.liferay.portal.model.Organization> search(
		long companyId, long parentOrganizationId, java.lang.String keywords,
		java.lang.String type, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.search(companyId,
			parentOrganizationId, keywords, type, regionId, countryId, params,
			start, end);
	}

	public java.util.List<com.liferay.portal.model.Organization> search(
		long companyId, long parentOrganizationId, java.lang.String keywords,
		java.lang.String type, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.search(companyId,
			parentOrganizationId, keywords, type, regionId, countryId, params,
			start, end, obc);
	}

	public java.util.List<com.liferay.portal.model.Organization> search(
		long companyId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, java.lang.String street, java.lang.String city,
		java.lang.String zip, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.search(companyId,
			parentOrganizationId, name, type, street, city, zip, regionId,
			countryId, params, andOperator, start, end);
	}

	public java.util.List<com.liferay.portal.model.Organization> search(
		long companyId, long parentOrganizationId, java.lang.String name,
		java.lang.String type, java.lang.String street, java.lang.String city,
		java.lang.String zip, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.search(companyId,
			parentOrganizationId, name, type, street, city, zip, regionId,
			countryId, params, andOperator, start, end, obc);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long parentOrganizationId, java.lang.String name,
		java.lang.String type, java.lang.String street, java.lang.String city,
		java.lang.String zip, java.lang.String region,
		java.lang.String country,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andSearch, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.search(companyId,
			parentOrganizationId, name, type, street, city, zip, region,
			country, params, andSearch, start, end, sort);
	}

	public int searchCount(long companyId, long parentOrganizationId,
		java.lang.String keywords, java.lang.String type,
		java.lang.Long regionId, java.lang.Long countryId,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.searchCount(companyId,
			parentOrganizationId, keywords, type, regionId, countryId, params);
	}

	public int searchCount(long companyId, long parentOrganizationId,
		java.lang.String name, java.lang.String type, java.lang.String street,
		java.lang.String city, java.lang.String zip, java.lang.Long regionId,
		java.lang.Long countryId,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.searchCount(companyId,
			parentOrganizationId, name, type, street, city, zip, regionId,
			countryId, params, andOperator);
	}

	public void setGroupOrganizations(long groupId, long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_organizationLocalService.setGroupOrganizations(groupId, organizationIds);
	}

	public void unsetGroupOrganizations(long groupId, long[] organizationIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_organizationLocalService.unsetGroupOrganizations(groupId,
			organizationIds);
	}

	public void unsetPasswordPolicyOrganizations(long passwordPolicyId,
		long[] organizationIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_organizationLocalService.unsetPasswordPolicyOrganizations(passwordPolicyId,
			organizationIds);
	}

	public void updateAsset(long userId,
		com.liferay.portal.model.Organization organization,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_organizationLocalService.updateAsset(userId, organization,
			assetCategoryIds, assetTagNames);
	}

	public com.liferay.portal.model.Organization updateOrganization(
		long companyId, long organizationId, long parentOrganizationId,
		java.lang.String name, java.lang.String type, boolean recursable,
		long regionId, long countryId, int statusId, java.lang.String comments,
		boolean site, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _organizationLocalService.updateOrganization(companyId,
			organizationId, parentOrganizationId, name, type, recursable,
			regionId, countryId, statusId, comments, site, serviceContext);
	}

	public OrganizationLocalService getWrappedOrganizationLocalService() {
		return _organizationLocalService;
	}

	public void setWrappedOrganizationLocalService(
		OrganizationLocalService organizationLocalService) {
		_organizationLocalService = organizationLocalService;
	}

	private OrganizationLocalService _organizationLocalService;
}