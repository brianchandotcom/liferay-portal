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

package com.liferay.dynamic.data.mapping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.model.DDMTemplateLink;

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

/**
 * Provides the local service interface for DDMTemplateLink. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateLinkLocalServiceUtil
 * @see com.liferay.dynamic.data.mapping.service.base.DDMTemplateLinkLocalServiceBaseImpl
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMTemplateLinkLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DDMTemplateLinkLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMTemplateLinkLocalServiceUtil} to access the ddm template link local service. Add custom service methods to {@link com.liferay.dynamic.data.mapping.service.impl.DDMTemplateLinkLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the ddm template link to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmTemplateLink the ddm template link
	* @return the ddm template link that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public DDMTemplateLink addDDMTemplateLink(DDMTemplateLink ddmTemplateLink);

	public DDMTemplateLink addTemplateLink(long classNameId, long classPK,
		long templateId);

	/**
	* Creates a new ddm template link with the primary key. Does not add the ddm template link to the database.
	*
	* @param templateLinkId the primary key for the new ddm template link
	* @return the new ddm template link
	*/
	public DDMTemplateLink createDDMTemplateLink(long templateLinkId);

	/**
	* Deletes the ddm template link from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmTemplateLink the ddm template link
	* @return the ddm template link that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public DDMTemplateLink deleteDDMTemplateLink(
		DDMTemplateLink ddmTemplateLink);

	/**
	* Deletes the ddm template link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param templateLinkId the primary key of the ddm template link
	* @return the ddm template link that was removed
	* @throws PortalException if a ddm template link with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public DDMTemplateLink deleteDDMTemplateLink(long templateLinkId)
		throws PortalException;

	public DDMTemplateLink deleteTemplateLink(DDMTemplateLink templateLink);

	public DDMTemplateLink deleteTemplateLink(long classNameId, long classPK);

	public DDMTemplateLink deleteTemplateLink(long templateLinkId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMTemplateLink fetchDDMTemplateLink(long templateLinkId);

	/**
	* Returns the ddm template link with the primary key.
	*
	* @param templateLinkId the primary key of the ddm template link
	* @return the ddm template link
	* @throws PortalException if a ddm template link with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMTemplateLink getDDMTemplateLink(long templateLinkId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMTemplateLink getTemplateLink(long classNameId, long classPK)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DDMTemplateLink getTemplateLink(long templateLinkId)
		throws PortalException;

	/**
	* Updates the ddm template link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmTemplateLink the ddm template link
	* @return the ddm template link that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public DDMTemplateLink updateDDMTemplateLink(
		DDMTemplateLink ddmTemplateLink);

	public DDMTemplateLink updateTemplateLink(long classNameId, long classPK,
		long templateId);

	public DDMTemplateLink updateTemplateLink(long templateLinkId,
		long templateId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

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

	/**
	* Returns the number of ddm template links.
	*
	* @return the number of ddm template links
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDDMTemplateLinksCount();

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMTemplateLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMTemplateLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	/**
	* Returns a range of all the ddm template links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMTemplateLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ddm template links
	* @param end the upper bound of the range of ddm template links (not inclusive)
	* @return the range of ddm template links
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMTemplateLink> getDDMTemplateLinks(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DDMTemplateLink> getTemplateLinks(long classNameId);

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
}