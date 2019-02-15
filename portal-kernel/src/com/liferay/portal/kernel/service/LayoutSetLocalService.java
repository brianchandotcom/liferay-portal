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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetVersion;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.version.VersionService;
import com.liferay.portal.kernel.service.version.VersionServiceListener;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for LayoutSet. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetLocalServiceUtil
 * @generated
 */
@OSGiBeanProperties(property =  {
	"model.class.name=com.liferay.portal.kernel.model.LayoutSet", "version.model.class.name=com.liferay.portal.kernel.model.LayoutSetVersion"})
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface LayoutSetLocalService extends BaseLocalService,
	PersistedModelLocalService, VersionService<LayoutSet, LayoutSetVersion> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutSetLocalServiceUtil} to access the layout set local service. Add custom service methods to <code>com.liferay.portal.service.impl.LayoutSetLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the layout set to the database. Also notifies the appropriate model listeners.
	*
	* @param layoutSet the layout set
	* @return the layout set that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public LayoutSet addLayoutSet(LayoutSet layoutSet);

	public LayoutSet addLayoutSet(long groupId, boolean privateLayout)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public LayoutSet checkout(LayoutSet publishedLayoutSet, int version)
		throws PortalException;

	/**
	* Creates a new layout set. Does not add the layout set to the database.
	*
	* @return the new layout set
	*/
	@Transactional(enabled = false)
	@Override
	public LayoutSet create();

	@Indexable(type = IndexableType.DELETE)
	@Override
	public LayoutSet delete(LayoutSet publishedLayoutSet)
		throws PortalException;

	@Indexable(type = IndexableType.DELETE)
	@Override
	public LayoutSet deleteDraft(LayoutSet draftLayoutSet)
		throws PortalException;

	/**
	* Deletes the layout set from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutSet the layout set
	* @return the layout set that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public LayoutSet deleteLayoutSet(LayoutSet layoutSet);

	/**
	* Deletes the layout set with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutSetId the primary key of the layout set
	* @return the layout set that was removed
	* @throws PortalException if a layout set with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public LayoutSet deleteLayoutSet(long layoutSetId)
		throws PortalException;

	public void deleteLayoutSet(long groupId, boolean privateLayout,
		ServiceContext serviceContext) throws PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Override
	public LayoutSetVersion deleteVersion(LayoutSetVersion layoutSetVersion)
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.LayoutSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.LayoutSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

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
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSet fetchDraft(LayoutSet layoutSet);

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSet fetchDraft(long primaryKey);

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSetVersion fetchLatestVersion(LayoutSet layoutSet);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSet fetchLayoutSet(long layoutSetId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSet fetchLayoutSet(long groupId, boolean privateLayout);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSet fetchLayoutSet(String virtualHostname);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSet fetchLayoutSetByLogoId(boolean privateLayout, long logoId)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSet fetchPublished(LayoutSet layoutSet);

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSet fetchPublished(long primaryKey);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSet getDraft(LayoutSet layoutSet) throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSet getDraft(long primaryKey) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the layout set with the primary key.
	*
	* @param layoutSetId the primary key of the layout set
	* @return the layout set
	* @throws PortalException if a layout set with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSet getLayoutSet(long layoutSetId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSet getLayoutSet(long groupId, boolean privateLayout)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSet getLayoutSet(String virtualHostname)
		throws PortalException;

	/**
	* Returns a range of all the layout sets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.LayoutSetModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout sets
	* @param end the upper bound of the range of layout sets (not inclusive)
	* @return the range of layout sets
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutSet> getLayoutSets(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutSet> getLayoutSetsByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid);

	/**
	* Returns the number of layout sets.
	*
	* @return the number of layout sets
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutSetsCount();

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

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutSetVersion getVersion(LayoutSet layoutSet, int version)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutSetVersion> getVersions(LayoutSet layoutSet);

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public LayoutSet publishDraft(LayoutSet draftLayoutSet)
		throws PortalException;

	@Override
	public void registerListener(
		VersionServiceListener<LayoutSet, LayoutSetVersion> versionServiceListener);

	@Override
	public void unregisterListener(
		VersionServiceListener<LayoutSet, LayoutSetVersion> versionServiceListener);

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public LayoutSet updateDraft(LayoutSet draftLayoutSet)
		throws PortalException;

	/**
	* Updates the layout set in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param layoutSet the layout set
	* @return the layout set that was updated
	* @throws PortalException
	*/
	@Indexable(type = IndexableType.REINDEX)
	public LayoutSet updateLayoutSet(LayoutSet draftLayoutSet)
		throws PortalException;

	/**
	* Updates the state of the layout set prototype link.
	*
	* @param groupId the primary key of the group
	* @param privateLayout whether the layout set is private to the group
	* @param layoutSetPrototypeLinkEnabled whether the layout set prototype is
	link enabled
	* @param layoutSetPrototypeUuid the uuid of the layout set prototype to
	link with
	*/
	public void updateLayoutSetPrototypeLinkEnabled(long groupId,
		boolean privateLayout, boolean layoutSetPrototypeLinkEnabled,
		String layoutSetPrototypeUuid) throws PortalException;

	public LayoutSet updateLogo(long groupId, boolean privateLayout,
		boolean logo, byte[] bytes) throws PortalException;

	public LayoutSet updateLogo(long groupId, boolean privateLayout,
		boolean logo, File file) throws PortalException;

	public LayoutSet updateLogo(long groupId, boolean privateLayout,
		boolean logo, InputStream is) throws PortalException;

	public LayoutSet updateLogo(long groupId, boolean privateLayout,
		boolean logo, InputStream is, boolean cleanUpStream)
		throws PortalException;

	public LayoutSet updateLookAndFeel(long groupId, boolean privateLayout,
		String themeId, String colorSchemeId, String css)
		throws PortalException;

	public void updateLookAndFeel(long groupId, String themeId,
		String colorSchemeId, String css) throws PortalException;

	public LayoutSet updatePageCount(long groupId, boolean privateLayout)
		throws PortalException;

	public LayoutSet updateSettings(long groupId, boolean privateLayout,
		String settings) throws PortalException;

	public LayoutSet updateVirtualHost(long groupId, boolean privateLayout,
		String virtualHostname) throws PortalException;
}