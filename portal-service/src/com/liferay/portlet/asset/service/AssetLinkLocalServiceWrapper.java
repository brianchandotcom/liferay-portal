/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetLinkLocalService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetLinkLocalService
 * @generated
 */
public class AssetLinkLocalServiceWrapper implements AssetLinkLocalService,
	ServiceWrapper<AssetLinkLocalService> {
	public AssetLinkLocalServiceWrapper(
		AssetLinkLocalService assetLinkLocalService) {
		_assetLinkLocalService = assetLinkLocalService;
	}

	/**
	* Adds the asset link to the database. Also notifies the appropriate model listeners.
	*
	* @param assetLink the asset link
	* @return the asset link that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink addAssetLink(
		com.liferay.portlet.asset.model.AssetLink assetLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.addAssetLink(assetLink);
	}

	/**
	* Creates a new asset link with the primary key. Does not add the asset link to the database.
	*
	* @param linkId the primary key for the new asset link
	* @return the new asset link
	*/
	public com.liferay.portlet.asset.model.AssetLink createAssetLink(
		long linkId) {
		return _assetLinkLocalService.createAssetLink(linkId);
	}

	/**
	* Deletes the asset link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param linkId the primary key of the asset link
	* @return the asset link that was removed
	* @throws PortalException if a asset link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink deleteAssetLink(
		long linkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.deleteAssetLink(linkId);
	}

	/**
	* Deletes the asset link from the database. Also notifies the appropriate model listeners.
	*
	* @param assetLink the asset link
	* @return the asset link that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink deleteAssetLink(
		com.liferay.portlet.asset.model.AssetLink assetLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.deleteAssetLink(assetLink);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _assetLinkLocalService.dynamicQuery();
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
		return _assetLinkLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _assetLinkLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _assetLinkLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _assetLinkLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.asset.model.AssetLink fetchAssetLink(long linkId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.fetchAssetLink(linkId);
	}

	/**
	* Returns the asset link with the primary key.
	*
	* @param linkId the primary key of the asset link
	* @return the asset link
	* @throws PortalException if a asset link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink getAssetLink(long linkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.getAssetLink(linkId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the asset links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset links
	* @param end the upper bound of the range of asset links (not inclusive)
	* @return the range of asset links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> getAssetLinks(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.getAssetLinks(start, end);
	}

	/**
	* Returns the number of asset links.
	*
	* @return the number of asset links
	* @throws SystemException if a system exception occurred
	*/
	public int getAssetLinksCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.getAssetLinksCount();
	}

	/**
	* Updates the asset link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetLink the asset link
	* @return the asset link that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink updateAssetLink(
		com.liferay.portlet.asset.model.AssetLink assetLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.updateAssetLink(assetLink);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _assetLinkLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_assetLinkLocalService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* Adds a new asset link.
	*
	* @param userId the primary key of the link's creator
	* @param entryId1 the primary key of the first asset entry
	* @param entryId2 the primary key of the second asset entry
	* @param type the link type. Acceptable values include {@link
	com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_RELATED}
	which is a bidirectional relationship and {@link
	com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_CHILD}
	which is a unidirectional relationship. For more information see
	{@link com.liferay.portlet.asset.model.AssetLinkConstants}
	* @param weight the weight of the relationship, allowing precedence
	ordering of links
	* @return the asset link
	* @throws PortalException if the user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetLink addLink(long userId,
		long entryId1, long entryId2, int type, int weight)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.addLink(userId, entryId1, entryId2, type,
			weight);
	}

	/**
	* Deletes the asset link.
	*
	* @param link the asset link
	* @throws SystemException if a system exception occurred
	*/
	public void deleteLink(com.liferay.portlet.asset.model.AssetLink link)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetLinkLocalService.deleteLink(link);
	}

	/**
	* Deletes the asset link.
	*
	* @param linkId the primary key of the asset link
	* @throws PortalException if the asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteLink(long linkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetLinkLocalService.deleteLink(linkId);
	}

	/**
	* Deletes all links associated with the asset entry.
	*
	* @param entryId the primary key of the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public void deleteLinks(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetLinkLocalService.deleteLinks(entryId);
	}

	/**
	* Delete all links that associate the two asset entries.
	*
	* @param entryId1 the primary key of the first asset entry
	* @param entryId2 the primary key of the second asset entry
	* @throws SystemException if a system exception occurred
	*/
	public void deleteLinks(long entryId1, long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetLinkLocalService.deleteLinks(entryId1, entryId2);
	}

	/**
	* Returns all the asset links whose first entry ID is the given entry ID.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset links whose first entry ID is the given entry ID
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> getDirectLinks(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.getDirectLinks(entryId);
	}

	/**
	* Returns all the asset links of the given link type whose first entry ID
	* is the given entry ID.
	*
	* @param entryId the primary key of the asset entry
	* @param typeId the link type. Acceptable values include {@link
	com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_RELATED}
	which is a bidirectional relationship and {@link
	com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_CHILD}
	which is a unidirectional relationship. For more information see
	{@link com.liferay.portlet.asset.model.AssetLinkConstants}
	* @return the asset links of the given link type whose first entry ID is
	the given entry ID
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> getDirectLinks(
		long entryId, int typeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.getDirectLinks(entryId, typeId);
	}

	/**
	* Returns all the asset links whose first or second entry ID is the given
	* entry ID.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset links whose first or second entry ID is the given entry
	ID
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> getLinks(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.getLinks(entryId);
	}

	/**
	* Returns all the asset links of the given link type whose first or second
	* entry ID is the given entry ID.
	*
	* @param entryId the primary key of the asset entry
	* @param typeId the link type. Acceptable values include {@link
	com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_RELATED}
	which is a bidirectional relationship and {@link
	com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_CHILD}
	which is a unidirectional relationship. For more information see
	{@link com.liferay.portlet.asset.model.AssetLinkConstants}
	* @return the asset links of the given link type whose first or second
	entry ID is the given entry ID
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> getLinks(
		long entryId, int typeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.getLinks(entryId, typeId);
	}

	/**
	* Returns all the asset links of the given link type whose second entry ID
	* is the given entry ID.
	*
	* @param entryId the primary key of the asset entry
	* @param typeId the link type. Acceptable values include {@link
	com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_RELATED}
	which is a bidirectional relationship and {@link
	com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_CHILD}
	which is a unidirectional relationship. For more information see
	{@link com.liferay.portlet.asset.model.AssetLinkConstants}
	* @return the asset links of the given link type whose second entry ID is
	the given entry ID
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> getReverseLinks(
		long entryId, int typeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetLinkLocalService.getReverseLinks(entryId, typeId);
	}

	/**
	* Updates all links of the asset entry, replacing them with links
	* associating the asset entry with the asset entries of the given link
	* entry IDs.
	*
	* <p>
	* If no link exists with a given link entry ID, a new link is created
	* associating the current asset entry with the asset entry of that link
	* entry ID. An existing link is deleted if either of its entry IDs is not
	* contained in the given link entry IDs.
	* </p>
	*
	* @param userId the primary key of the user updating the links
	* @param entryId the primary key of the asset entry to be managed
	* @param linkEntryIds the primary keys of the asset entries to be linked
	with the asset entry to be managed
	* @param typeId the type of the asset links to be created. Acceptable
	values include {@link
	com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_RELATED}
	which is a bidirectional relationship and {@link
	com.liferay.portlet.asset.model.AssetLinkConstants#TYPE_CHILD}
	which is a unidirectional relationship. For more information see
	{@link com.liferay.portlet.asset.model.AssetLinkConstants}
	* @throws PortalException if the user could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void updateLinks(long userId, long entryId, long[] linkEntryIds,
		int typeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetLinkLocalService.updateLinks(userId, entryId, linkEntryIds, typeId);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public AssetLinkLocalService getWrappedAssetLinkLocalService() {
		return _assetLinkLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedAssetLinkLocalService(
		AssetLinkLocalService assetLinkLocalService) {
		_assetLinkLocalService = assetLinkLocalService;
	}

	public AssetLinkLocalService getWrappedService() {
		return _assetLinkLocalService;
	}

	public void setWrappedService(AssetLinkLocalService assetLinkLocalService) {
		_assetLinkLocalService = assetLinkLocalService;
	}

	private AssetLinkLocalService _assetLinkLocalService;
}