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

package com.liferay.client.extension.service;

import com.liferay.client.extension.model.ClientExtensionEntryRel;
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

import java.io.Serializable;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for ClientExtensionEntryRel. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see ClientExtensionEntryRelLocalServiceUtil
 * @generated
 */
@CTAware
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface ClientExtensionEntryRelLocalService
	extends BaseLocalService, CTService<ClientExtensionEntryRel>,
			PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.client.extension.service.impl.ClientExtensionEntryRelLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the client extension entry rel local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link ClientExtensionEntryRelLocalServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * Adds the client extension entry rel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ClientExtensionEntryRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param clientExtensionEntryRel the client extension entry rel
	 * @return the client extension entry rel that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public ClientExtensionEntryRel addClientExtensionEntryRel(
		ClientExtensionEntryRel clientExtensionEntryRel);

	public ClientExtensionEntryRel addClientExtensionEntryRel(
			long userId, long classNameId, long classPK,
			String cetExternalReferenceCode, String type)
		throws PortalException;

	/**
	 * Creates a new client extension entry rel with the primary key. Does not add the client extension entry rel to the database.
	 *
	 * @param clientExtensionEntryRelId the primary key for the new client extension entry rel
	 * @return the new client extension entry rel
	 */
	@Transactional(enabled = false)
	public ClientExtensionEntryRel createClientExtensionEntryRel(
		long clientExtensionEntryRelId);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Deletes the client extension entry rel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ClientExtensionEntryRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param clientExtensionEntryRel the client extension entry rel
	 * @return the client extension entry rel that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public ClientExtensionEntryRel deleteClientExtensionEntryRel(
		ClientExtensionEntryRel clientExtensionEntryRel);

	/**
	 * Deletes the client extension entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ClientExtensionEntryRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param clientExtensionEntryRelId the primary key of the client extension entry rel
	 * @return the client extension entry rel that was removed
	 * @throws PortalException if a client extension entry rel with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public ClientExtensionEntryRel deleteClientExtensionEntryRel(
			long clientExtensionEntryRelId)
		throws PortalException;

	public void deleteClientExtensionEntryRels(long classNameId, long classPK);

	public void deleteClientExtensionEntryRels(
		long classNameId, long classPK, String type);

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.client.extension.model.impl.ClientExtensionEntryRelModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.client.extension.model.impl.ClientExtensionEntryRelModelImpl</code>.
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
	public ClientExtensionEntryRel fetchClientExtensionEntryRel(
		long clientExtensionEntryRelId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ClientExtensionEntryRel fetchClientExtensionEntryRel(
		long classNameId, long classPK, String type);

	/**
	 * Returns the client extension entry rel with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the client extension entry rel's external reference code
	 * @return the matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ClientExtensionEntryRel
		fetchClientExtensionEntryRelByExternalReferenceCode(
			long companyId, String externalReferenceCode);

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #fetchClientExtensionEntryRelByExternalReferenceCode(long, String)}
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ClientExtensionEntryRel fetchClientExtensionEntryRelByReferenceCode(
		long companyId, String externalReferenceCode);

	/**
	 * Returns the client extension entry rel with the matching UUID and company.
	 *
	 * @param uuid the client extension entry rel's UUID
	 * @param companyId the primary key of the company
	 * @return the matching client extension entry rel, or <code>null</code> if a matching client extension entry rel could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ClientExtensionEntryRel
		fetchClientExtensionEntryRelByUuidAndCompanyId(
			String uuid, long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	 * Returns the client extension entry rel with the primary key.
	 *
	 * @param clientExtensionEntryRelId the primary key of the client extension entry rel
	 * @return the client extension entry rel
	 * @throws PortalException if a client extension entry rel with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ClientExtensionEntryRel getClientExtensionEntryRel(
			long clientExtensionEntryRelId)
		throws PortalException;

	/**
	 * Returns the client extension entry rel with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the client extension entry rel's external reference code
	 * @return the matching client extension entry rel
	 * @throws PortalException if a matching client extension entry rel could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ClientExtensionEntryRel
			getClientExtensionEntryRelByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws PortalException;

	/**
	 * Returns the client extension entry rel with the matching UUID and company.
	 *
	 * @param uuid the client extension entry rel's UUID
	 * @param companyId the primary key of the company
	 * @return the matching client extension entry rel
	 * @throws PortalException if a matching client extension entry rel could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ClientExtensionEntryRel getClientExtensionEntryRelByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException;

	/**
	 * Returns a range of all the client extension entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.client.extension.model.impl.ClientExtensionEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of client extension entry rels
	 * @param end the upper bound of the range of client extension entry rels (not inclusive)
	 * @return the range of client extension entry rels
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ClientExtensionEntryRel> getClientExtensionEntryRels(
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ClientExtensionEntryRel> getClientExtensionEntryRels(
		long classNameId, long classPK);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ClientExtensionEntryRel> getClientExtensionEntryRels(
		long classNameId, long classPK, String type, int start, int end);

	/**
	 * Returns the number of client extension entry rels.
	 *
	 * @return the number of client extension entry rels
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getClientExtensionEntryRelsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getClientExtensionEntryRelsCount(
		long classNameId, long classPK, String type);

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
	 * Updates the client extension entry rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ClientExtensionEntryRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param clientExtensionEntryRel the client extension entry rel
	 * @return the client extension entry rel that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public ClientExtensionEntryRel updateClientExtensionEntryRel(
		ClientExtensionEntryRel clientExtensionEntryRel);

	@Override
	@Transactional(enabled = false)
	public CTPersistence<ClientExtensionEntryRel> getCTPersistence();

	@Override
	@Transactional(enabled = false)
	public Class<ClientExtensionEntryRel> getModelClass();

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<ClientExtensionEntryRel>, R, E>
				updateUnsafeFunction)
		throws E;

}