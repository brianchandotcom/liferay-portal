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

package com.liferay.layout.page.template.service;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
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
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for LayoutPageTemplateStructure. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateStructureLocalServiceUtil
 * @generated
 */
@CTAware
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface LayoutPageTemplateStructureLocalService
	extends BaseLocalService, CTService<LayoutPageTemplateStructure>,
			PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.layout.page.template.service.impl.LayoutPageTemplateStructureLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the layout page template structure local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link LayoutPageTemplateStructureLocalServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * Adds the layout page template structure to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LayoutPageTemplateStructureLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param layoutPageTemplateStructure the layout page template structure
	 * @return the layout page template structure that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public LayoutPageTemplateStructure addLayoutPageTemplateStructure(
		LayoutPageTemplateStructure layoutPageTemplateStructure);

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #addLayoutPageTemplateStructure(long, long, long, long,
	 String, ServiceContext)}
	 */
	@Deprecated
	public LayoutPageTemplateStructure addLayoutPageTemplateStructure(
			long userId, long groupId, long classNameId, long classPK,
			String data, ServiceContext serviceContext)
		throws PortalException;

	public LayoutPageTemplateStructure addLayoutPageTemplateStructure(
			long userId, long groupId, long plid, String data,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * Creates a new layout page template structure with the primary key. Does not add the layout page template structure to the database.
	 *
	 * @param layoutPageTemplateStructureId the primary key for the new layout page template structure
	 * @return the new layout page template structure
	 */
	@Transactional(enabled = false)
	public LayoutPageTemplateStructure createLayoutPageTemplateStructure(
		long layoutPageTemplateStructureId);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Deletes the layout page template structure from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LayoutPageTemplateStructureLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param layoutPageTemplateStructure the layout page template structure
	 * @return the layout page template structure that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public LayoutPageTemplateStructure deleteLayoutPageTemplateStructure(
		LayoutPageTemplateStructure layoutPageTemplateStructure);

	/**
	 * Deletes the layout page template structure with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LayoutPageTemplateStructureLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param layoutPageTemplateStructureId the primary key of the layout page template structure
	 * @return the layout page template structure that was removed
	 * @throws PortalException if a layout page template structure with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public LayoutPageTemplateStructure deleteLayoutPageTemplateStructure(
			long layoutPageTemplateStructureId)
		throws PortalException;

	public LayoutPageTemplateStructure deleteLayoutPageTemplateStructure(
			long groupId, long plid)
		throws PortalException;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #deleteLayoutPageTemplateStructure(long, long)}
	 */
	@Deprecated
	public LayoutPageTemplateStructure deleteLayoutPageTemplateStructure(
			long groupId, long classNameId, long classPK)
		throws PortalException;

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> T dslQuery(DSLQuery dslQuery);

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.page.template.model.impl.LayoutPageTemplateStructureModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.page.template.model.impl.LayoutPageTemplateStructureModelImpl</code>.
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
	public LayoutPageTemplateStructure fetchLayoutPageTemplateStructure(
		long layoutPageTemplateStructureId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutPageTemplateStructure fetchLayoutPageTemplateStructure(
		long groupId, long plid);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutPageTemplateStructure fetchLayoutPageTemplateStructure(
			long groupId, long plid, boolean rebuildStructure)
		throws PortalException;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #fetchLayoutPageTemplateStructure(long, long)}
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutPageTemplateStructure fetchLayoutPageTemplateStructure(
		long groupId, long classNameId, long classPK);

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #fetchLayoutPageTemplateStructure(long, long, boolean)}
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutPageTemplateStructure fetchLayoutPageTemplateStructure(
			long groupId, long classNameId, long classPK,
			boolean rebuildStructure)
		throws PortalException;

	/**
	 * Returns the layout page template structure matching the UUID and group.
	 *
	 * @param uuid the layout page template structure's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout page template structure, or <code>null</code> if a matching layout page template structure could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutPageTemplateStructure
		fetchLayoutPageTemplateStructureByUuidAndGroupId(
			String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	 * Returns the layout page template structure with the primary key.
	 *
	 * @param layoutPageTemplateStructureId the primary key of the layout page template structure
	 * @return the layout page template structure
	 * @throws PortalException if a layout page template structure with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutPageTemplateStructure getLayoutPageTemplateStructure(
			long layoutPageTemplateStructureId)
		throws PortalException;

	/**
	 * Returns the layout page template structure matching the UUID and group.
	 *
	 * @param uuid the layout page template structure's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout page template structure
	 * @throws PortalException if a matching layout page template structure could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LayoutPageTemplateStructure
			getLayoutPageTemplateStructureByUuidAndGroupId(
				String uuid, long groupId)
		throws PortalException;

	/**
	 * Returns a range of all the layout page template structures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.page.template.model.impl.LayoutPageTemplateStructureModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @return the range of layout page template structures
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateStructure> getLayoutPageTemplateStructures(
		int start, int end);

	/**
	 * Returns all the layout page template structures matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout page template structures
	 * @param companyId the primary key of the company
	 * @return the matching layout page template structures, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateStructure>
		getLayoutPageTemplateStructuresByUuidAndCompanyId(
			String uuid, long companyId);

	/**
	 * Returns a range of layout page template structures matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout page template structures
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of layout page template structures
	 * @param end the upper bound of the range of layout page template structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching layout page template structures, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LayoutPageTemplateStructure>
		getLayoutPageTemplateStructuresByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			OrderByComparator<LayoutPageTemplateStructure> orderByComparator);

	/**
	 * Returns the number of layout page template structures.
	 *
	 * @return the number of layout page template structures
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLayoutPageTemplateStructuresCount();

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

	public LayoutPageTemplateStructure rebuildLayoutPageTemplateStructure(
			long groupId, long plid)
		throws PortalException;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #rebuildLayoutPageTemplateStructure(long, long)}
	 */
	@Deprecated
	public LayoutPageTemplateStructure rebuildLayoutPageTemplateStructure(
			long groupId, long classNameId, long classPK)
		throws PortalException;

	/**
	 * Updates the layout page template structure in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect LayoutPageTemplateStructureLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param layoutPageTemplateStructure the layout page template structure
	 * @return the layout page template structure that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public LayoutPageTemplateStructure updateLayoutPageTemplateStructure(
		LayoutPageTemplateStructure layoutPageTemplateStructure);

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #updateLayoutPageTemplateStructureData(long, long, long,
	 String)}
	 */
	@Deprecated
	public LayoutPageTemplateStructure updateLayoutPageTemplateStructure(
			long groupId, long classNameId, long classPK,
			long segmentsExperienceId, String data)
		throws PortalException;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #updateLayoutPageTemplateStructureData(long, long, String)}
	 */
	@Deprecated
	public LayoutPageTemplateStructure updateLayoutPageTemplateStructure(
			long groupId, long classNameId, long classPK, String data)
		throws PortalException;

	public LayoutPageTemplateStructure updateLayoutPageTemplateStructureData(
			long groupId, long plid, long segmentsExperienceId, String data)
		throws PortalException;

	public LayoutPageTemplateStructure updateLayoutPageTemplateStructureData(
			long groupId, long plid, String data)
		throws PortalException;

	@Override
	@Transactional(enabled = false)
	public CTPersistence<LayoutPageTemplateStructure> getCTPersistence();

	@Override
	@Transactional(enabled = false)
	public Class<LayoutPageTemplateStructure> getModelClass();

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<LayoutPageTemplateStructure>, R, E>
				updateUnsafeFunction)
		throws E;

}