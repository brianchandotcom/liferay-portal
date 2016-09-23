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

package com.liferay.portlet.documentlibrary.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.kernel.service.persistence.AssetEntryFinder;
import com.liferay.asset.kernel.service.persistence.AssetEntryPersistence;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryFinder;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryPersistence;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryTypeFinder;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryTypePersistence;
import com.liferay.document.library.kernel.service.persistence.DLFileShortcutPersistence;
import com.liferay.document.library.kernel.service.persistence.DLFileVersionPersistence;
import com.liferay.document.library.kernel.service.persistence.DLFolderFinder;
import com.liferay.document.library.kernel.service.persistence.DLFolderPersistence;

import com.liferay.expando.kernel.service.persistence.ExpandoRowPersistence;

import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.GroupFinder;
import com.liferay.portal.kernel.service.persistence.GroupPersistence;
import com.liferay.portal.kernel.service.persistence.RepositoryPersistence;
import com.liferay.portal.kernel.service.persistence.UserFinder;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.service.persistence.WebDAVPropsPersistence;
import com.liferay.portal.kernel.service.persistence.WorkflowDefinitionLinkPersistence;
import com.liferay.portal.kernel.service.persistence.WorkflowInstanceLinkPersistence;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import com.liferay.ratings.kernel.service.persistence.RatingsStatsFinder;
import com.liferay.ratings.kernel.service.persistence.RatingsStatsPersistence;

import com.liferay.trash.kernel.service.persistence.TrashEntryPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the document library folder local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.documentlibrary.service.impl.DLFolderLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.documentlibrary.service.impl.DLFolderLocalServiceImpl
 * @see com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class DLFolderLocalServiceBaseImpl extends BaseLocalServiceImpl
	implements DLFolderLocalService, IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil} to access the document library folder local service.
	 */

	/**
	 * Adds the document library folder to the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlFolder the document library folder
	 * @return the document library folder that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DLFolder addDLFolder(DLFolder dlFolder) {
		dlFolder.setNew(true);

		return dlFolderPersistence.update(dlFolder);
	}

	/**
	 * Creates a new document library folder with the primary key. Does not add the document library folder to the database.
	 *
	 * @param folderId the primary key for the new document library folder
	 * @return the new document library folder
	 */
	@Override
	public DLFolder createDLFolder(long folderId) {
		return dlFolderPersistence.create(folderId);
	}

	/**
	 * Deletes the document library folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param folderId the primary key of the document library folder
	 * @return the document library folder that was removed
	 * @throws PortalException if a document library folder with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DLFolder deleteDLFolder(long folderId) throws PortalException {
		return dlFolderPersistence.remove(folderId);
	}

	/**
	 * Deletes the document library folder from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlFolder the document library folder
	 * @return the document library folder that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DLFolder deleteDLFolder(DLFolder dlFolder) {
		return dlFolderPersistence.remove(dlFolder);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(DLFolder.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return dlFolderPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) {
		return dlFolderPersistence.findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator) {
		return dlFolderPersistence.findWithDynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return dlFolderPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return dlFolderPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public DLFolder fetchDLFolder(long folderId) {
		return dlFolderPersistence.fetchByPrimaryKey(folderId);
	}

	/**
	 * Returns the document library folder matching the UUID and group.
	 *
	 * @param uuid the document library folder's UUID
	 * @param groupId the primary key of the group
	 * @return the matching document library folder, or <code>null</code> if a matching document library folder could not be found
	 */
	@Override
	public DLFolder fetchDLFolderByUuidAndGroupId(String uuid, long groupId) {
		return dlFolderPersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the document library folder with the primary key.
	 *
	 * @param folderId the primary key of the document library folder
	 * @return the document library folder
	 * @throws PortalException if a document library folder with the primary key could not be found
	 */
	@Override
	public DLFolder getDLFolder(long folderId) throws PortalException {
		return dlFolderPersistence.findByPrimaryKey(folderId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(dlFolderLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(DLFolder.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("folderId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(dlFolderLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(DLFolder.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName("folderId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(dlFolderLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(DLFolder.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("folderId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {
		final ExportActionableDynamicQuery exportActionableDynamicQuery = new ExportActionableDynamicQuery() {
				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary = portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(stagedModelType,
						modelAdditionCount);

					long modelDeletionCount = ExportImportHelperUtil.getModelDeletionCount(portletDataContext,
							stagedModelType);

					manifestSummary.addModelDeletionCount(stagedModelType,
						modelDeletionCount);

					return modelAdditionCount;
				}
			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(new ActionableDynamicQuery.AddCriteriaMethod() {
				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Criterion modifiedDateCriterion = portletDataContext.getDateRangeCriteria(
							"modifiedDate");

					if (modifiedDateCriterion != null) {
						Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

						disjunction.add(RestrictionsFactoryUtil.gtProperty(
								"modifiedDate", "lastPublishDate"));

						Property lastPublishDateProperty = PropertyFactoryUtil.forName(
								"lastPublishDate");

						disjunction.add(lastPublishDateProperty.isNull());

						Conjunction conjunction = RestrictionsFactoryUtil.conjunction();

						conjunction.add(modifiedDateCriterion);
						conjunction.add(disjunction);

						modifiedDateCriterion = conjunction;
					}

					Criterion statusDateCriterion = portletDataContext.getDateRangeCriteria(
							"statusDate");

					if ((modifiedDateCriterion != null) &&
							(statusDateCriterion != null)) {
						Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

						disjunction.add(modifiedDateCriterion);
						disjunction.add(statusDateCriterion);

						dynamicQuery.add(disjunction);
					}

					Property workflowStatusProperty = PropertyFactoryUtil.forName(
							"status");

					if (portletDataContext.isInitialPublication()) {
						dynamicQuery.add(workflowStatusProperty.ne(
								WorkflowConstants.STATUS_IN_TRASH));
					}
					else {
						StagedModelDataHandler<?> stagedModelDataHandler = StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(DLFolder.class.getName());

						dynamicQuery.add(workflowStatusProperty.in(
								stagedModelDataHandler.getExportableStatuses()));
					}
				}
			});

		exportActionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setGroupId(portletDataContext.getScopeGroupId());

		exportActionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<DLFolder>() {
				@Override
				public void performAction(DLFolder dlFolder)
					throws PortalException {
					StagedModelDataHandlerUtil.exportStagedModel(portletDataContext,
						dlFolder);
				}
			});
		exportActionableDynamicQuery.setStagedModelType(new StagedModelType(
				PortalUtil.getClassNameId(DLFolder.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return dlFolderLocalService.deleteDLFolder((DLFolder)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return dlFolderPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the document library folders matching the UUID and company.
	 *
	 * @param uuid the UUID of the document library folders
	 * @param companyId the primary key of the company
	 * @return the matching document library folders, or an empty list if no matches were found
	 */
	@Override
	public List<DLFolder> getDLFoldersByUuidAndCompanyId(String uuid,
		long companyId) {
		return dlFolderPersistence.findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of document library folders matching the UUID and company.
	 *
	 * @param uuid the UUID of the document library folders
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching document library folders, or an empty list if no matches were found
	 */
	@Override
	public List<DLFolder> getDLFoldersByUuidAndCompanyId(String uuid,
		long companyId, int start, int end,
		OrderByComparator<DLFolder> orderByComparator) {
		return dlFolderPersistence.findByUuid_C(uuid, companyId, start, end,
			orderByComparator);
	}

	/**
	 * Returns the document library folder matching the UUID and group.
	 *
	 * @param uuid the document library folder's UUID
	 * @param groupId the primary key of the group
	 * @return the matching document library folder
	 * @throws PortalException if a matching document library folder could not be found
	 */
	@Override
	public DLFolder getDLFolderByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {
		return dlFolderPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the document library folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFolderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library folders
	 * @param end the upper bound of the range of document library folders (not inclusive)
	 * @return the range of document library folders
	 */
	@Override
	public List<DLFolder> getDLFolders(int start, int end) {
		return dlFolderPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of document library folders.
	 *
	 * @return the number of document library folders
	 */
	@Override
	public int getDLFoldersCount() {
		return dlFolderPersistence.countAll();
	}

	/**
	 * Updates the document library folder in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param dlFolder the document library folder
	 * @return the document library folder that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DLFolder updateDLFolder(DLFolder dlFolder) {
		return dlFolderPersistence.update(dlFolder);
	}

	/**
	 */
	@Override
	public void addDLFileEntryTypeDLFolder(long fileEntryTypeId, long folderId) {
		dlFileEntryTypePersistence.addDLFolder(fileEntryTypeId, folderId);
	}

	/**
	 */
	@Override
	public void addDLFileEntryTypeDLFolder(long fileEntryTypeId,
		DLFolder dlFolder) {
		dlFileEntryTypePersistence.addDLFolder(fileEntryTypeId, dlFolder);
	}

	/**
	 */
	@Override
	public void addDLFileEntryTypeDLFolders(long fileEntryTypeId,
		long[] folderIds) {
		dlFileEntryTypePersistence.addDLFolders(fileEntryTypeId, folderIds);
	}

	/**
	 */
	@Override
	public void addDLFileEntryTypeDLFolders(long fileEntryTypeId,
		List<DLFolder> dlFolders) {
		dlFileEntryTypePersistence.addDLFolders(fileEntryTypeId, dlFolders);
	}

	/**
	 */
	@Override
	public void clearDLFileEntryTypeDLFolders(long fileEntryTypeId) {
		dlFileEntryTypePersistence.clearDLFolders(fileEntryTypeId);
	}

	/**
	 */
	@Override
	public void deleteDLFileEntryTypeDLFolder(long fileEntryTypeId,
		long folderId) {
		dlFileEntryTypePersistence.removeDLFolder(fileEntryTypeId, folderId);
	}

	/**
	 */
	@Override
	public void deleteDLFileEntryTypeDLFolder(long fileEntryTypeId,
		DLFolder dlFolder) {
		dlFileEntryTypePersistence.removeDLFolder(fileEntryTypeId, dlFolder);
	}

	/**
	 */
	@Override
	public void deleteDLFileEntryTypeDLFolders(long fileEntryTypeId,
		long[] folderIds) {
		dlFileEntryTypePersistence.removeDLFolders(fileEntryTypeId, folderIds);
	}

	/**
	 */
	@Override
	public void deleteDLFileEntryTypeDLFolders(long fileEntryTypeId,
		List<DLFolder> dlFolders) {
		dlFileEntryTypePersistence.removeDLFolders(fileEntryTypeId, dlFolders);
	}

	/**
	 * Returns the fileEntryTypeIds of the document library file entry types associated with the document library folder.
	 *
	 * @param folderId the folderId of the document library folder
	 * @return long[] the fileEntryTypeIds of document library file entry types associated with the document library folder
	 */
	@Override
	public long[] getDLFileEntryTypePrimaryKeys(long folderId) {
		return dlFolderPersistence.getDLFileEntryTypePrimaryKeys(folderId);
	}

	/**
	 */
	@Override
	public List<DLFolder> getDLFileEntryTypeDLFolders(long fileEntryTypeId) {
		return dlFileEntryTypePersistence.getDLFolders(fileEntryTypeId);
	}

	/**
	 */
	@Override
	public List<DLFolder> getDLFileEntryTypeDLFolders(long fileEntryTypeId,
		int start, int end) {
		return dlFileEntryTypePersistence.getDLFolders(fileEntryTypeId, start,
			end);
	}

	/**
	 */
	@Override
	public List<DLFolder> getDLFileEntryTypeDLFolders(long fileEntryTypeId,
		int start, int end, OrderByComparator<DLFolder> orderByComparator) {
		return dlFileEntryTypePersistence.getDLFolders(fileEntryTypeId, start,
			end, orderByComparator);
	}

	/**
	 */
	@Override
	public int getDLFileEntryTypeDLFoldersCount(long fileEntryTypeId) {
		return dlFileEntryTypePersistence.getDLFoldersSize(fileEntryTypeId);
	}

	/**
	 */
	@Override
	public boolean hasDLFileEntryTypeDLFolder(long fileEntryTypeId,
		long folderId) {
		return dlFileEntryTypePersistence.containsDLFolder(fileEntryTypeId,
			folderId);
	}

	/**
	 */
	@Override
	public boolean hasDLFileEntryTypeDLFolders(long fileEntryTypeId) {
		return dlFileEntryTypePersistence.containsDLFolders(fileEntryTypeId);
	}

	/**
	 */
	@Override
	public void setDLFileEntryTypeDLFolders(long fileEntryTypeId,
		long[] folderIds) {
		dlFileEntryTypePersistence.setDLFolders(fileEntryTypeId, folderIds);
	}

	/**
	 * Returns the document library folder local service.
	 *
	 * @return the document library folder local service
	 */
	public DLFolderLocalService getDLFolderLocalService() {
		return dlFolderLocalService;
	}

	/**
	 * Sets the document library folder local service.
	 *
	 * @param dlFolderLocalService the document library folder local service
	 */
	public void setDLFolderLocalService(
		DLFolderLocalService dlFolderLocalService) {
		this.dlFolderLocalService = dlFolderLocalService;
	}

	/**
	 * Returns the document library folder persistence.
	 *
	 * @return the document library folder persistence
	 */
	public DLFolderPersistence getDLFolderPersistence() {
		return dlFolderPersistence;
	}

	/**
	 * Sets the document library folder persistence.
	 *
	 * @param dlFolderPersistence the document library folder persistence
	 */
	public void setDLFolderPersistence(DLFolderPersistence dlFolderPersistence) {
		this.dlFolderPersistence = dlFolderPersistence;
	}

	/**
	 * Returns the document library folder finder.
	 *
	 * @return the document library folder finder
	 */
	public DLFolderFinder getDLFolderFinder() {
		return dlFolderFinder;
	}

	/**
	 * Sets the document library folder finder.
	 *
	 * @param dlFolderFinder the document library folder finder
	 */
	public void setDLFolderFinder(DLFolderFinder dlFolderFinder) {
		this.dlFolderFinder = dlFolderFinder;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the group local service.
	 *
	 * @return the group local service
	 */
	public com.liferay.portal.kernel.service.GroupLocalService getGroupLocalService() {
		return groupLocalService;
	}

	/**
	 * Sets the group local service.
	 *
	 * @param groupLocalService the group local service
	 */
	public void setGroupLocalService(
		com.liferay.portal.kernel.service.GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	/**
	 * Returns the group persistence.
	 *
	 * @return the group persistence
	 */
	public GroupPersistence getGroupPersistence() {
		return groupPersistence;
	}

	/**
	 * Sets the group persistence.
	 *
	 * @param groupPersistence the group persistence
	 */
	public void setGroupPersistence(GroupPersistence groupPersistence) {
		this.groupPersistence = groupPersistence;
	}

	/**
	 * Returns the group finder.
	 *
	 * @return the group finder
	 */
	public GroupFinder getGroupFinder() {
		return groupFinder;
	}

	/**
	 * Sets the group finder.
	 *
	 * @param groupFinder the group finder
	 */
	public void setGroupFinder(GroupFinder groupFinder) {
		this.groupFinder = groupFinder;
	}

	/**
	 * Returns the repository local service.
	 *
	 * @return the repository local service
	 */
	public com.liferay.portal.kernel.service.RepositoryLocalService getRepositoryLocalService() {
		return repositoryLocalService;
	}

	/**
	 * Sets the repository local service.
	 *
	 * @param repositoryLocalService the repository local service
	 */
	public void setRepositoryLocalService(
		com.liferay.portal.kernel.service.RepositoryLocalService repositoryLocalService) {
		this.repositoryLocalService = repositoryLocalService;
	}

	/**
	 * Returns the repository persistence.
	 *
	 * @return the repository persistence
	 */
	public RepositoryPersistence getRepositoryPersistence() {
		return repositoryPersistence;
	}

	/**
	 * Sets the repository persistence.
	 *
	 * @param repositoryPersistence the repository persistence
	 */
	public void setRepositoryPersistence(
		RepositoryPersistence repositoryPersistence) {
		this.repositoryPersistence = repositoryPersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	/**
	 * Returns the user finder.
	 *
	 * @return the user finder
	 */
	public UserFinder getUserFinder() {
		return userFinder;
	}

	/**
	 * Sets the user finder.
	 *
	 * @param userFinder the user finder
	 */
	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	/**
	 * Returns the web d a v props local service.
	 *
	 * @return the web d a v props local service
	 */
	public com.liferay.portal.kernel.service.WebDAVPropsLocalService getWebDAVPropsLocalService() {
		return webDAVPropsLocalService;
	}

	/**
	 * Sets the web d a v props local service.
	 *
	 * @param webDAVPropsLocalService the web d a v props local service
	 */
	public void setWebDAVPropsLocalService(
		com.liferay.portal.kernel.service.WebDAVPropsLocalService webDAVPropsLocalService) {
		this.webDAVPropsLocalService = webDAVPropsLocalService;
	}

	/**
	 * Returns the web d a v props persistence.
	 *
	 * @return the web d a v props persistence
	 */
	public WebDAVPropsPersistence getWebDAVPropsPersistence() {
		return webDAVPropsPersistence;
	}

	/**
	 * Sets the web d a v props persistence.
	 *
	 * @param webDAVPropsPersistence the web d a v props persistence
	 */
	public void setWebDAVPropsPersistence(
		WebDAVPropsPersistence webDAVPropsPersistence) {
		this.webDAVPropsPersistence = webDAVPropsPersistence;
	}

	/**
	 * Returns the workflow definition link local service.
	 *
	 * @return the workflow definition link local service
	 */
	public com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService getWorkflowDefinitionLinkLocalService() {
		return workflowDefinitionLinkLocalService;
	}

	/**
	 * Sets the workflow definition link local service.
	 *
	 * @param workflowDefinitionLinkLocalService the workflow definition link local service
	 */
	public void setWorkflowDefinitionLinkLocalService(
		com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService) {
		this.workflowDefinitionLinkLocalService = workflowDefinitionLinkLocalService;
	}

	/**
	 * Returns the workflow definition link persistence.
	 *
	 * @return the workflow definition link persistence
	 */
	public WorkflowDefinitionLinkPersistence getWorkflowDefinitionLinkPersistence() {
		return workflowDefinitionLinkPersistence;
	}

	/**
	 * Sets the workflow definition link persistence.
	 *
	 * @param workflowDefinitionLinkPersistence the workflow definition link persistence
	 */
	public void setWorkflowDefinitionLinkPersistence(
		WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence) {
		this.workflowDefinitionLinkPersistence = workflowDefinitionLinkPersistence;
	}

	/**
	 * Returns the workflow instance link local service.
	 *
	 * @return the workflow instance link local service
	 */
	public com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService getWorkflowInstanceLinkLocalService() {
		return workflowInstanceLinkLocalService;
	}

	/**
	 * Sets the workflow instance link local service.
	 *
	 * @param workflowInstanceLinkLocalService the workflow instance link local service
	 */
	public void setWorkflowInstanceLinkLocalService(
		com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService) {
		this.workflowInstanceLinkLocalService = workflowInstanceLinkLocalService;
	}

	/**
	 * Returns the workflow instance link persistence.
	 *
	 * @return the workflow instance link persistence
	 */
	public WorkflowInstanceLinkPersistence getWorkflowInstanceLinkPersistence() {
		return workflowInstanceLinkPersistence;
	}

	/**
	 * Sets the workflow instance link persistence.
	 *
	 * @param workflowInstanceLinkPersistence the workflow instance link persistence
	 */
	public void setWorkflowInstanceLinkPersistence(
		WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence) {
		this.workflowInstanceLinkPersistence = workflowInstanceLinkPersistence;
	}

	/**
	 * Returns the asset entry local service.
	 *
	 * @return the asset entry local service
	 */
	public com.liferay.asset.kernel.service.AssetEntryLocalService getAssetEntryLocalService() {
		return assetEntryLocalService;
	}

	/**
	 * Sets the asset entry local service.
	 *
	 * @param assetEntryLocalService the asset entry local service
	 */
	public void setAssetEntryLocalService(
		com.liferay.asset.kernel.service.AssetEntryLocalService assetEntryLocalService) {
		this.assetEntryLocalService = assetEntryLocalService;
	}

	/**
	 * Returns the asset entry persistence.
	 *
	 * @return the asset entry persistence
	 */
	public AssetEntryPersistence getAssetEntryPersistence() {
		return assetEntryPersistence;
	}

	/**
	 * Sets the asset entry persistence.
	 *
	 * @param assetEntryPersistence the asset entry persistence
	 */
	public void setAssetEntryPersistence(
		AssetEntryPersistence assetEntryPersistence) {
		this.assetEntryPersistence = assetEntryPersistence;
	}

	/**
	 * Returns the asset entry finder.
	 *
	 * @return the asset entry finder
	 */
	public AssetEntryFinder getAssetEntryFinder() {
		return assetEntryFinder;
	}

	/**
	 * Sets the asset entry finder.
	 *
	 * @param assetEntryFinder the asset entry finder
	 */
	public void setAssetEntryFinder(AssetEntryFinder assetEntryFinder) {
		this.assetEntryFinder = assetEntryFinder;
	}

	/**
	 * Returns the d l app helper local service.
	 *
	 * @return the d l app helper local service
	 */
	public com.liferay.document.library.kernel.service.DLAppHelperLocalService getDLAppHelperLocalService() {
		return dlAppHelperLocalService;
	}

	/**
	 * Sets the d l app helper local service.
	 *
	 * @param dlAppHelperLocalService the d l app helper local service
	 */
	public void setDLAppHelperLocalService(
		com.liferay.document.library.kernel.service.DLAppHelperLocalService dlAppHelperLocalService) {
		this.dlAppHelperLocalService = dlAppHelperLocalService;
	}

	/**
	 * Returns the document library file entry local service.
	 *
	 * @return the document library file entry local service
	 */
	public com.liferay.document.library.kernel.service.DLFileEntryLocalService getDLFileEntryLocalService() {
		return dlFileEntryLocalService;
	}

	/**
	 * Sets the document library file entry local service.
	 *
	 * @param dlFileEntryLocalService the document library file entry local service
	 */
	public void setDLFileEntryLocalService(
		com.liferay.document.library.kernel.service.DLFileEntryLocalService dlFileEntryLocalService) {
		this.dlFileEntryLocalService = dlFileEntryLocalService;
	}

	/**
	 * Returns the document library file entry persistence.
	 *
	 * @return the document library file entry persistence
	 */
	public DLFileEntryPersistence getDLFileEntryPersistence() {
		return dlFileEntryPersistence;
	}

	/**
	 * Sets the document library file entry persistence.
	 *
	 * @param dlFileEntryPersistence the document library file entry persistence
	 */
	public void setDLFileEntryPersistence(
		DLFileEntryPersistence dlFileEntryPersistence) {
		this.dlFileEntryPersistence = dlFileEntryPersistence;
	}

	/**
	 * Returns the document library file entry finder.
	 *
	 * @return the document library file entry finder
	 */
	public DLFileEntryFinder getDLFileEntryFinder() {
		return dlFileEntryFinder;
	}

	/**
	 * Sets the document library file entry finder.
	 *
	 * @param dlFileEntryFinder the document library file entry finder
	 */
	public void setDLFileEntryFinder(DLFileEntryFinder dlFileEntryFinder) {
		this.dlFileEntryFinder = dlFileEntryFinder;
	}

	/**
	 * Returns the document library file entry type local service.
	 *
	 * @return the document library file entry type local service
	 */
	public com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService getDLFileEntryTypeLocalService() {
		return dlFileEntryTypeLocalService;
	}

	/**
	 * Sets the document library file entry type local service.
	 *
	 * @param dlFileEntryTypeLocalService the document library file entry type local service
	 */
	public void setDLFileEntryTypeLocalService(
		com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {
		this.dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
	}

	/**
	 * Returns the document library file entry type persistence.
	 *
	 * @return the document library file entry type persistence
	 */
	public DLFileEntryTypePersistence getDLFileEntryTypePersistence() {
		return dlFileEntryTypePersistence;
	}

	/**
	 * Sets the document library file entry type persistence.
	 *
	 * @param dlFileEntryTypePersistence the document library file entry type persistence
	 */
	public void setDLFileEntryTypePersistence(
		DLFileEntryTypePersistence dlFileEntryTypePersistence) {
		this.dlFileEntryTypePersistence = dlFileEntryTypePersistence;
	}

	/**
	 * Returns the document library file entry type finder.
	 *
	 * @return the document library file entry type finder
	 */
	public DLFileEntryTypeFinder getDLFileEntryTypeFinder() {
		return dlFileEntryTypeFinder;
	}

	/**
	 * Sets the document library file entry type finder.
	 *
	 * @param dlFileEntryTypeFinder the document library file entry type finder
	 */
	public void setDLFileEntryTypeFinder(
		DLFileEntryTypeFinder dlFileEntryTypeFinder) {
		this.dlFileEntryTypeFinder = dlFileEntryTypeFinder;
	}

	/**
	 * Returns the document library file shortcut local service.
	 *
	 * @return the document library file shortcut local service
	 */
	public com.liferay.document.library.kernel.service.DLFileShortcutLocalService getDLFileShortcutLocalService() {
		return dlFileShortcutLocalService;
	}

	/**
	 * Sets the document library file shortcut local service.
	 *
	 * @param dlFileShortcutLocalService the document library file shortcut local service
	 */
	public void setDLFileShortcutLocalService(
		com.liferay.document.library.kernel.service.DLFileShortcutLocalService dlFileShortcutLocalService) {
		this.dlFileShortcutLocalService = dlFileShortcutLocalService;
	}

	/**
	 * Returns the document library file shortcut persistence.
	 *
	 * @return the document library file shortcut persistence
	 */
	public DLFileShortcutPersistence getDLFileShortcutPersistence() {
		return dlFileShortcutPersistence;
	}

	/**
	 * Sets the document library file shortcut persistence.
	 *
	 * @param dlFileShortcutPersistence the document library file shortcut persistence
	 */
	public void setDLFileShortcutPersistence(
		DLFileShortcutPersistence dlFileShortcutPersistence) {
		this.dlFileShortcutPersistence = dlFileShortcutPersistence;
	}

	/**
	 * Returns the document library file version local service.
	 *
	 * @return the document library file version local service
	 */
	public com.liferay.document.library.kernel.service.DLFileVersionLocalService getDLFileVersionLocalService() {
		return dlFileVersionLocalService;
	}

	/**
	 * Sets the document library file version local service.
	 *
	 * @param dlFileVersionLocalService the document library file version local service
	 */
	public void setDLFileVersionLocalService(
		com.liferay.document.library.kernel.service.DLFileVersionLocalService dlFileVersionLocalService) {
		this.dlFileVersionLocalService = dlFileVersionLocalService;
	}

	/**
	 * Returns the document library file version persistence.
	 *
	 * @return the document library file version persistence
	 */
	public DLFileVersionPersistence getDLFileVersionPersistence() {
		return dlFileVersionPersistence;
	}

	/**
	 * Sets the document library file version persistence.
	 *
	 * @param dlFileVersionPersistence the document library file version persistence
	 */
	public void setDLFileVersionPersistence(
		DLFileVersionPersistence dlFileVersionPersistence) {
		this.dlFileVersionPersistence = dlFileVersionPersistence;
	}

	/**
	 * Returns the expando row local service.
	 *
	 * @return the expando row local service
	 */
	public com.liferay.expando.kernel.service.ExpandoRowLocalService getExpandoRowLocalService() {
		return expandoRowLocalService;
	}

	/**
	 * Sets the expando row local service.
	 *
	 * @param expandoRowLocalService the expando row local service
	 */
	public void setExpandoRowLocalService(
		com.liferay.expando.kernel.service.ExpandoRowLocalService expandoRowLocalService) {
		this.expandoRowLocalService = expandoRowLocalService;
	}

	/**
	 * Returns the expando row persistence.
	 *
	 * @return the expando row persistence
	 */
	public ExpandoRowPersistence getExpandoRowPersistence() {
		return expandoRowPersistence;
	}

	/**
	 * Sets the expando row persistence.
	 *
	 * @param expandoRowPersistence the expando row persistence
	 */
	public void setExpandoRowPersistence(
		ExpandoRowPersistence expandoRowPersistence) {
		this.expandoRowPersistence = expandoRowPersistence;
	}

	/**
	 * Returns the ratings stats local service.
	 *
	 * @return the ratings stats local service
	 */
	public com.liferay.ratings.kernel.service.RatingsStatsLocalService getRatingsStatsLocalService() {
		return ratingsStatsLocalService;
	}

	/**
	 * Sets the ratings stats local service.
	 *
	 * @param ratingsStatsLocalService the ratings stats local service
	 */
	public void setRatingsStatsLocalService(
		com.liferay.ratings.kernel.service.RatingsStatsLocalService ratingsStatsLocalService) {
		this.ratingsStatsLocalService = ratingsStatsLocalService;
	}

	/**
	 * Returns the ratings stats persistence.
	 *
	 * @return the ratings stats persistence
	 */
	public RatingsStatsPersistence getRatingsStatsPersistence() {
		return ratingsStatsPersistence;
	}

	/**
	 * Sets the ratings stats persistence.
	 *
	 * @param ratingsStatsPersistence the ratings stats persistence
	 */
	public void setRatingsStatsPersistence(
		RatingsStatsPersistence ratingsStatsPersistence) {
		this.ratingsStatsPersistence = ratingsStatsPersistence;
	}

	/**
	 * Returns the ratings stats finder.
	 *
	 * @return the ratings stats finder
	 */
	public RatingsStatsFinder getRatingsStatsFinder() {
		return ratingsStatsFinder;
	}

	/**
	 * Sets the ratings stats finder.
	 *
	 * @param ratingsStatsFinder the ratings stats finder
	 */
	public void setRatingsStatsFinder(RatingsStatsFinder ratingsStatsFinder) {
		this.ratingsStatsFinder = ratingsStatsFinder;
	}

	/**
	 * Returns the trash entry local service.
	 *
	 * @return the trash entry local service
	 */
	public com.liferay.trash.kernel.service.TrashEntryLocalService getTrashEntryLocalService() {
		return trashEntryLocalService;
	}

	/**
	 * Sets the trash entry local service.
	 *
	 * @param trashEntryLocalService the trash entry local service
	 */
	public void setTrashEntryLocalService(
		com.liferay.trash.kernel.service.TrashEntryLocalService trashEntryLocalService) {
		this.trashEntryLocalService = trashEntryLocalService;
	}

	/**
	 * Returns the trash entry persistence.
	 *
	 * @return the trash entry persistence
	 */
	public TrashEntryPersistence getTrashEntryPersistence() {
		return trashEntryPersistence;
	}

	/**
	 * Sets the trash entry persistence.
	 *
	 * @param trashEntryPersistence the trash entry persistence
	 */
	public void setTrashEntryPersistence(
		TrashEntryPersistence trashEntryPersistence) {
		this.trashEntryPersistence = trashEntryPersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.document.library.kernel.model.DLFolder",
			dlFolderLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.document.library.kernel.model.DLFolder");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return DLFolderLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return DLFolder.class;
	}

	protected String getModelClassName() {
		return DLFolder.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = dlFolderPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = DLFolderLocalService.class)
	protected DLFolderLocalService dlFolderLocalService;
	@BeanReference(type = DLFolderPersistence.class)
	protected DLFolderPersistence dlFolderPersistence;
	@BeanReference(type = DLFolderFinder.class)
	protected DLFolderFinder dlFolderFinder;
	@BeanReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.GroupLocalService.class)
	protected com.liferay.portal.kernel.service.GroupLocalService groupLocalService;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = GroupFinder.class)
	protected GroupFinder groupFinder;
	@BeanReference(type = com.liferay.portal.kernel.service.RepositoryLocalService.class)
	protected com.liferay.portal.kernel.service.RepositoryLocalService repositoryLocalService;
	@BeanReference(type = RepositoryPersistence.class)
	protected RepositoryPersistence repositoryPersistence;
	@BeanReference(type = com.liferay.portal.kernel.service.ResourceLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserFinder.class)
	protected UserFinder userFinder;
	@BeanReference(type = com.liferay.portal.kernel.service.WebDAVPropsLocalService.class)
	protected com.liferay.portal.kernel.service.WebDAVPropsLocalService webDAVPropsLocalService;
	@BeanReference(type = WebDAVPropsPersistence.class)
	protected WebDAVPropsPersistence webDAVPropsPersistence;
	@BeanReference(type = com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService.class)
	protected com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService;
	@BeanReference(type = WorkflowDefinitionLinkPersistence.class)
	protected WorkflowDefinitionLinkPersistence workflowDefinitionLinkPersistence;
	@BeanReference(type = com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService.class)
	protected com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService workflowInstanceLinkLocalService;
	@BeanReference(type = WorkflowInstanceLinkPersistence.class)
	protected WorkflowInstanceLinkPersistence workflowInstanceLinkPersistence;
	@BeanReference(type = com.liferay.asset.kernel.service.AssetEntryLocalService.class)
	protected com.liferay.asset.kernel.service.AssetEntryLocalService assetEntryLocalService;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = AssetEntryFinder.class)
	protected AssetEntryFinder assetEntryFinder;
	@BeanReference(type = com.liferay.document.library.kernel.service.DLAppHelperLocalService.class)
	protected com.liferay.document.library.kernel.service.DLAppHelperLocalService dlAppHelperLocalService;
	@BeanReference(type = com.liferay.document.library.kernel.service.DLFileEntryLocalService.class)
	protected com.liferay.document.library.kernel.service.DLFileEntryLocalService dlFileEntryLocalService;
	@BeanReference(type = DLFileEntryPersistence.class)
	protected DLFileEntryPersistence dlFileEntryPersistence;
	@BeanReference(type = DLFileEntryFinder.class)
	protected DLFileEntryFinder dlFileEntryFinder;
	@BeanReference(type = com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService.class)
	protected com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService dlFileEntryTypeLocalService;
	@BeanReference(type = DLFileEntryTypePersistence.class)
	protected DLFileEntryTypePersistence dlFileEntryTypePersistence;
	@BeanReference(type = DLFileEntryTypeFinder.class)
	protected DLFileEntryTypeFinder dlFileEntryTypeFinder;
	@BeanReference(type = com.liferay.document.library.kernel.service.DLFileShortcutLocalService.class)
	protected com.liferay.document.library.kernel.service.DLFileShortcutLocalService dlFileShortcutLocalService;
	@BeanReference(type = DLFileShortcutPersistence.class)
	protected DLFileShortcutPersistence dlFileShortcutPersistence;
	@BeanReference(type = com.liferay.document.library.kernel.service.DLFileVersionLocalService.class)
	protected com.liferay.document.library.kernel.service.DLFileVersionLocalService dlFileVersionLocalService;
	@BeanReference(type = DLFileVersionPersistence.class)
	protected DLFileVersionPersistence dlFileVersionPersistence;
	@BeanReference(type = com.liferay.expando.kernel.service.ExpandoRowLocalService.class)
	protected com.liferay.expando.kernel.service.ExpandoRowLocalService expandoRowLocalService;
	@BeanReference(type = ExpandoRowPersistence.class)
	protected ExpandoRowPersistence expandoRowPersistence;
	@BeanReference(type = com.liferay.ratings.kernel.service.RatingsStatsLocalService.class)
	protected com.liferay.ratings.kernel.service.RatingsStatsLocalService ratingsStatsLocalService;
	@BeanReference(type = RatingsStatsPersistence.class)
	protected RatingsStatsPersistence ratingsStatsPersistence;
	@BeanReference(type = RatingsStatsFinder.class)
	protected RatingsStatsFinder ratingsStatsFinder;
	@BeanReference(type = com.liferay.trash.kernel.service.TrashEntryLocalService.class)
	protected com.liferay.trash.kernel.service.TrashEntryLocalService trashEntryLocalService;
	@BeanReference(type = TrashEntryPersistence.class)
	protected TrashEntryPersistence trashEntryPersistence;
	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}