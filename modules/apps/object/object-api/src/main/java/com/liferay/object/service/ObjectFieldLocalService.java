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

package com.liferay.object.service;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.model.ObjectStateFlow;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.query.DSLQuery;
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
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for ObjectField. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Marco Leo
 * @see ObjectFieldLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface ObjectFieldLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.object.service.impl.ObjectFieldLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the object field local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link ObjectFieldLocalServiceUtil} if injection and service tracking are not available.
	 */
	@Indexable(type = IndexableType.REINDEX)
	public ObjectField addCustomObjectField(
			long userId, long listTypeDefinitionId, long objectDefinitionId,
			String businessType, String dbType, String defaultValue,
			boolean indexed, boolean indexedAsKeyword, String indexedLanguageId,
			Map<Locale, String> labelMap, String name, boolean required,
			boolean state, List<ObjectFieldSetting> objectFieldSettings)
		throws PortalException;

	/**
	 * Adds the object field to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectFieldLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectField the object field
	 * @return the object field that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public ObjectField addObjectField(ObjectField objectField);

	@Indexable(type = IndexableType.REINDEX)
	public ObjectField addOrUpdateSystemObjectField(
			long userId, long objectDefinitionId, String businessType,
			String dbColumnName, String dbTableName, String dbType,
			String defaultValue, boolean indexed, boolean indexedAsKeyword,
			String indexedLanguageId, Map<Locale, String> labelMap, String name,
			boolean required, boolean state)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public ObjectField addSystemObjectField(
			long userId, long objectDefinitionId, String businessType,
			String dbColumnName, String dbTableName, String dbType,
			String defaultValue, boolean indexed, boolean indexedAsKeyword,
			String indexedLanguageId, Map<Locale, String> labelMap, String name,
			boolean required, boolean state)
		throws PortalException;

	/**
	 * Creates a new object field with the primary key. Does not add the object field to the database.
	 *
	 * @param objectFieldId the primary key for the new object field
	 * @return the new object field
	 */
	@Transactional(enabled = false)
	public ObjectField createObjectField(long objectFieldId);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Deletes the object field with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectFieldLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectFieldId the primary key of the object field
	 * @return the object field that was removed
	 * @throws PortalException if a object field with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public ObjectField deleteObjectField(long objectFieldId)
		throws PortalException;

	/**
	 * Deletes the object field from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectFieldLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectField the object field
	 * @return the object field that was removed
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public ObjectField deleteObjectField(ObjectField objectField)
		throws PortalException;

	public void deleteObjectFieldByObjectDefinitionId(Long objectDefinitionId)
		throws PortalException;

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Indexable(type = IndexableType.DELETE)
	public ObjectField deleteRelationshipTypeObjectField(long objectFieldId)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectFieldModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectFieldModelImpl</code>.
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
	public ObjectField fetchObjectField(long objectFieldId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ObjectField fetchObjectField(long objectDefinitionId, String name);

	/**
	 * Returns the object field with the matching UUID and company.
	 *
	 * @param uuid the object field's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object field, or <code>null</code> if a matching object field could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ObjectField fetchObjectFieldByUuidAndCompanyId(
		String uuid, long companyId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ObjectField> getActiveObjectFields(
			List<ObjectField> objectFields)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ObjectField> getCustomObjectFields(long objectFieldId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ObjectField> getListTypeDefinitionObjectFields(
		long listTypeDefinitionId, boolean state);

	/**
	 * Returns the object field with the primary key.
	 *
	 * @param objectFieldId the primary key of the object field
	 * @return the object field
	 * @throws PortalException if a object field with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ObjectField getObjectField(long objectFieldId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ObjectField getObjectField(long objectDefinitionId, String name)
		throws PortalException;

	/**
	 * Returns the object field with the matching UUID and company.
	 *
	 * @param uuid the object field's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object field
	 * @throws PortalException if a matching object field could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ObjectField getObjectFieldByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException;

	/**
	 * Returns a range of all the object fields.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectFieldModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object fields
	 * @param end the upper bound of the range of object fields (not inclusive)
	 * @return the range of object fields
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ObjectField> getObjectFields(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ObjectField> getObjectFields(long objectDefinitionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ObjectField> getObjectFields(
		long objectDefinitionId, String dbTableName);

	/**
	 * Returns the number of object fields.
	 *
	 * @return the number of object fields
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getObjectFieldsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getObjectFieldsCount(long objectDefinitionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getObjectFieldsCountByListTypeDefinitionId(
		long listTypeDefinitionId);

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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Table getTable(long objectDefinitionId, String name)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public ObjectField updateCustomObjectField(
			long objectFieldId, String externalReferenceCode,
			long listTypeDefinitionId, String businessType, String dbType,
			String defaultValue, boolean indexed, boolean indexedAsKeyword,
			String indexedLanguageId, Map<Locale, String> labelMap, String name,
			boolean required, boolean state,
			List<ObjectFieldSetting> objectFieldSettings,
			ObjectStateFlow objectStateFlow)
		throws PortalException;

	public ObjectField updateObjectField(
			long userId, long objectDefinitionId, long objectFieldId,
			String externalReferenceCode, long listTypeDefinitionId,
			String businessType, String dbColumnName, String dbTableName,
			String dbType, String defaultValue, boolean indexed,
			boolean indexedAsKeyword, String indexedLanguageId,
			Map<Locale, String> labelMap, String name, boolean required,
			boolean state, boolean system,
			List<ObjectFieldSetting> objectFieldSettings,
			ObjectStateFlow objectStateFlow)
		throws PortalException;

	/**
	 * Updates the object field in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectFieldLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectField the object field
	 * @return the object field that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public ObjectField updateObjectField(ObjectField objectField);

	@Indexable(type = IndexableType.REINDEX)
	public ObjectField updateRequired(long objectFieldId, boolean required)
		throws PortalException;

}