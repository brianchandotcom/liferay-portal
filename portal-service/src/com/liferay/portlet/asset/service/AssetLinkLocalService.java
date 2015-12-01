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

package com.liferay.portlet.asset.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

/**
 * Provides the local service interface for AssetLink. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see AssetLinkLocalServiceUtil
 * @see com.liferay.portlet.asset.service.base.AssetLinkLocalServiceBaseImpl
 * @see com.liferay.portlet.asset.service.impl.AssetLinkLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface AssetLinkLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetLinkLocalServiceUtil} to access the asset link local service. Add custom service methods to {@link com.liferay.portlet.asset.service.impl.AssetLinkLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the asset link to the database. Also notifies the appropriate model listeners.
	*
	* @param assetLink the asset link
	* @return the asset link that was added
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.asset.model.AssetLink addAssetLink(
		com.liferay.portlet.asset.model.AssetLink assetLink);

	/**
	* Adds a new asset link.
	*
	* @param userId the primary key of the link's creator
	* @param entryId1 the primary key of the first asset entry
	* @param entryId2 the primary key of the second asset entry
	* @param type the link type. Acceptable values include {@link
	AssetLinkConstants#TYPE_RELATED} which is a bidirectional
	relationship and {@link AssetLinkConstants#TYPE_CHILD} which is a
	unidirectional relationship. For more information see {@link
	AssetLinkConstants}
	* @param weight the weight of the relationship, allowing precedence
	ordering of links
	* @return the asset link
	*/
	public com.liferay.portlet.asset.model.AssetLink addLink(long userId,
		long entryId1, long entryId2, int type, int weight)
		throws PortalException;

	/**
	* Creates a new asset link with the primary key. Does not add the asset link to the database.
	*
	* @param linkId the primary key for the new asset link
	* @return the new asset link
	*/
	public com.liferay.portlet.asset.model.AssetLink createAssetLink(
		long linkId);

	/**
	* Deletes the asset link from the database. Also notifies the appropriate model listeners.
	*
	* @param assetLink the asset link
	* @return the asset link that was removed
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.asset.model.AssetLink deleteAssetLink(
		com.liferay.portlet.asset.model.AssetLink assetLink);

	/**
	* Deletes the asset link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param linkId the primary key of the asset link
	* @return the asset link that was removed
	* @throws PortalException if a asset link with the primary key could not be found
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.liferay.portlet.asset.model.AssetLink deleteAssetLink(
		long linkId) throws PortalException;

	public void deleteGroupLinks(long groupId);

	/**
	* Deletes the asset link.
	*
	* @param link the asset link
	*/
	public void deleteLink(com.liferay.portlet.asset.model.AssetLink link);

	/**
	* Deletes the asset link.
	*
	* @param linkId the primary key of the asset link
	*/
	public void deleteLink(long linkId) throws PortalException;

	/**
	* Deletes all links associated with the asset entry.
	*
	* @param entryId the primary key of the asset entry
	*/
	public void deleteLinks(long entryId);

	/**
	* Delete all links that associate the two asset entries.
	*
	* @param entryId1 the primary key of the first asset entry
	* @param entryId2 the primary key of the second asset entry
	*/
	public void deleteLinks(long entryId1, long entryId2);

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws PortalException;

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

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
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end);

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
	*/
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.asset.model.AssetLink fetchAssetLink(long linkId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the asset link with the primary key.
	*
	* @param linkId the primary key of the asset link
	* @return the asset link
	* @throws PortalException if a asset link with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.asset.model.AssetLink getAssetLink(long linkId)
		throws PortalException;

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
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> getAssetLinks(
		int start, int end);

	/**
	* Returns the number of asset links.
	*
	* @return the number of asset links
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAssetLinksCount();

	/**
	* Returns all the asset links whose first entry ID is the given entry ID.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset links whose first entry ID is the given entry ID
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> getDirectLinks(
		long entryId);

	/**
	* Returns all the asset links of the given link type whose first entry ID
	* is the given entry ID.
	*
	* @param entryId the primary key of the asset entry
	* @param typeId the link type. Acceptable values include {@link
	AssetLinkConstants#TYPE_RELATED} which is a bidirectional
	relationship and {@link AssetLinkConstants#TYPE_CHILD} which is a
	unidirectional relationship. For more information see {@link
	AssetLinkConstants}
	* @return the asset links of the given link type whose first entry ID is
	the given entry ID
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> getDirectLinks(
		long entryId, int typeId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionbleDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns all the asset links whose first or second entry ID is the given
	* entry ID.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset links whose first or second entry ID is the given entry
	ID
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> getLinks(
		long entryId);

	/**
	* Returns all the asset links of the given link type whose first or second
	* entry ID is the given entry ID.
	*
	* @param entryId the primary key of the asset entry
	* @param typeId the link type. Acceptable values include {@link
	AssetLinkConstants#TYPE_RELATED} which is a bidirectional
	relationship and {@link AssetLinkConstants#TYPE_CHILD} which is a
	unidirectional relationship. For more information see {@link
	AssetLinkConstants}
	* @return the asset links of the given link type whose first or second
	entry ID is the given entry ID
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> getLinks(
		long entryId, int typeId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj) throws PortalException;

	/**
	* Returns all the asset links of the given link type whose second entry ID
	* is the given entry ID.
	*
	* @param entryId the primary key of the asset entry
	* @param typeId the link type. Acceptable values include {@link
	AssetLinkConstants#TYPE_RELATED} which is a bidirectional
	relationship and {@link AssetLinkConstants#TYPE_CHILD} which is a
	unidirectional relationship. For more information see {@link
	AssetLinkConstants}
	* @return the asset links of the given link type whose second entry ID is
	the given entry ID
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.asset.model.AssetLink> getReverseLinks(
		long entryId, int typeId);

	/**
	* Updates the asset link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetLink the asset link
	* @return the asset link that was updated
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.liferay.portlet.asset.model.AssetLink updateAssetLink(
		com.liferay.portlet.asset.model.AssetLink assetLink);

	public com.liferay.portlet.asset.model.AssetLink updateLink(long userId,
		long entryId1, long entryId2, int typeId, int weight)
		throws PortalException;

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
	values include {@link AssetLinkConstants#TYPE_RELATED} which is a
	bidirectional relationship and {@link
	AssetLinkConstants#TYPE_CHILD} which is a unidirectional
	relationship. For more information see {@link AssetLinkConstants}
	*/
	public void updateLinks(long userId, long entryId, long[] linkEntryIds,
		int typeId) throws PortalException;
}