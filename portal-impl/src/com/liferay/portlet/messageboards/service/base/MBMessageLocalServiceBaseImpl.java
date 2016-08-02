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

package com.liferay.portlet.messageboards.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.kernel.service.persistence.AssetEntryFinder;
import com.liferay.asset.kernel.service.persistence.AssetEntryPersistence;
import com.liferay.asset.kernel.service.persistence.AssetLinkPersistence;
import com.liferay.asset.kernel.service.persistence.AssetTagFinder;
import com.liferay.asset.kernel.service.persistence.AssetTagPersistence;

import com.liferay.expando.kernel.service.persistence.ExpandoRowPersistence;

import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.service.MBMessageLocalService;
import com.liferay.message.boards.kernel.service.persistence.MBCategoryFinder;
import com.liferay.message.boards.kernel.service.persistence.MBCategoryPersistence;
import com.liferay.message.boards.kernel.service.persistence.MBDiscussionPersistence;
import com.liferay.message.boards.kernel.service.persistence.MBMessageFinder;
import com.liferay.message.boards.kernel.service.persistence.MBMessagePersistence;
import com.liferay.message.boards.kernel.service.persistence.MBStatsUserPersistence;
import com.liferay.message.boards.kernel.service.persistence.MBThreadFinder;
import com.liferay.message.boards.kernel.service.persistence.MBThreadPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
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
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.CompanyPersistence;
import com.liferay.portal.kernel.service.persistence.GroupFinder;
import com.liferay.portal.kernel.service.persistence.GroupPersistence;
import com.liferay.portal.kernel.service.persistence.PortletPreferencesFinder;
import com.liferay.portal.kernel.service.persistence.PortletPreferencesPersistence;
import com.liferay.portal.kernel.service.persistence.SubscriptionPersistence;
import com.liferay.portal.kernel.service.persistence.UserFinder;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.service.persistence.WorkflowInstanceLinkPersistence;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import com.liferay.ratings.kernel.service.persistence.RatingsStatsFinder;
import com.liferay.ratings.kernel.service.persistence.RatingsStatsPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the message-boards message local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.messageboards.service.impl.MBMessageLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.messageboards.service.impl.MBMessageLocalServiceImpl
 * @see com.liferay.message.boards.kernel.service.MBMessageLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class MBMessageLocalServiceBaseImpl extends BaseLocalServiceImpl
	implements MBMessageLocalService, IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.message.boards.kernel.service.MBMessageLocalServiceUtil} to access the message-boards message local service.
	 */

	/**
	 * Adds the message-boards message to the database. Also notifies the appropriate model listeners.
	 *
	 * @param mbMessage the message-boards message
	 * @return the message-boards message that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public MBMessage addMBMessage(MBMessage mbMessage) {
		mbMessage.setNew(true);

		return mbMessagePersistence.update(mbMessage);
	}

	/**
	 * Creates a new message-boards message with the primary key. Does not add the message-boards message to the database.
	 *
	 * @param messageId the primary key for the new message-boards message
	 * @return the new message-boards message
	 */
	@Override
	public MBMessage createMBMessage(long messageId) {
		return mbMessagePersistence.create(messageId);
	}

	/**
	 * Deletes the message-boards message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param messageId the primary key of the message-boards message
	 * @return the message-boards message that was removed
	 * @throws PortalException if a message-boards message with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public MBMessage deleteMBMessage(long messageId) throws PortalException {
		return mbMessagePersistence.remove(messageId);
	}

	/**
	 * Deletes the message-boards message from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mbMessage the message-boards message
	 * @return the message-boards message that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public MBMessage deleteMBMessage(MBMessage mbMessage) {
		return mbMessagePersistence.remove(mbMessage);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(MBMessage.class,
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
		return mbMessagePersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return mbMessagePersistence.findWithDynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return mbMessagePersistence.findWithDynamicQuery(dynamicQuery, start,
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
		return mbMessagePersistence.countWithDynamicQuery(dynamicQuery);
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
		return mbMessagePersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public MBMessage fetchMBMessage(long messageId) {
		return mbMessagePersistence.fetchByPrimaryKey(messageId);
	}

	/**
	 * Returns the message-boards message matching the UUID and group.
	 *
	 * @param uuid the message-boards message's UUID
	 * @param groupId the primary key of the group
	 * @return the matching message-boards message, or <code>null</code> if a matching message-boards message could not be found
	 */
	@Override
	public MBMessage fetchMBMessageByUuidAndGroupId(String uuid, long groupId) {
		return mbMessagePersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the message-boards message with the primary key.
	 *
	 * @param messageId the primary key of the message-boards message
	 * @return the message-boards message
	 * @throws PortalException if a message-boards message with the primary key could not be found
	 */
	@Override
	public MBMessage getMBMessage(long messageId) throws PortalException {
		return mbMessagePersistence.findByPrimaryKey(messageId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(mbMessageLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(MBMessage.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("messageId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(mbMessageLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(MBMessage.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName("messageId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(mbMessageLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(MBMessage.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("messageId");
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
					Criterion statusDateCriterion = portletDataContext.getDateRangeCriteria(
							"statusDate");

					if ((modifiedDateCriterion != null) &&
							(statusDateCriterion != null)) {
						Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

						disjunction.add(modifiedDateCriterion);
						disjunction.add(statusDateCriterion);

						dynamicQuery.add(disjunction);
					}

					StagedModelType stagedModelType = exportActionableDynamicQuery.getStagedModelType();

					long referrerClassNameId = stagedModelType.getReferrerClassNameId();

					Property classNameIdProperty = PropertyFactoryUtil.forName(
							"classNameId");

					if ((referrerClassNameId != StagedModelType.REFERRER_CLASS_NAME_ID_ALL) &&
							(referrerClassNameId != StagedModelType.REFERRER_CLASS_NAME_ID_ANY)) {
						dynamicQuery.add(classNameIdProperty.eq(
								stagedModelType.getReferrerClassNameId()));
					}
					else if (referrerClassNameId == StagedModelType.REFERRER_CLASS_NAME_ID_ANY) {
						dynamicQuery.add(classNameIdProperty.isNotNull());
					}

					Property workflowStatusProperty = PropertyFactoryUtil.forName(
							"status");

					if (portletDataContext.isInitialPublication()) {
						dynamicQuery.add(workflowStatusProperty.ne(
								WorkflowConstants.STATUS_IN_TRASH));
					}
					else {
						StagedModelDataHandler<?> stagedModelDataHandler = StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(MBMessage.class.getName());

						dynamicQuery.add(workflowStatusProperty.in(
								stagedModelDataHandler.getExportableStatuses()));
					}
				}
			});

		exportActionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setGroupId(portletDataContext.getScopeGroupId());

		exportActionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<MBMessage>() {
				@Override
				public void performAction(MBMessage mbMessage)
					throws PortalException {
					StagedModelDataHandlerUtil.exportStagedModel(portletDataContext,
						mbMessage);
				}
			});
		exportActionableDynamicQuery.setStagedModelType(new StagedModelType(
				PortalUtil.getClassNameId(MBMessage.class.getName()),
				StagedModelType.REFERRER_CLASS_NAME_ID_ALL));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return mbMessageLocalService.deleteMBMessage((MBMessage)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return mbMessagePersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the message-boards messages matching the UUID and company.
	 *
	 * @param uuid the UUID of the message-boards messages
	 * @param companyId the primary key of the company
	 * @return the matching message-boards messages, or an empty list if no matches were found
	 */
	@Override
	public List<MBMessage> getMBMessagesByUuidAndCompanyId(String uuid,
		long companyId) {
		return mbMessagePersistence.findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of message-boards messages matching the UUID and company.
	 *
	 * @param uuid the UUID of the message-boards messages
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching message-boards messages, or an empty list if no matches were found
	 */
	@Override
	public List<MBMessage> getMBMessagesByUuidAndCompanyId(String uuid,
		long companyId, int start, int end,
		OrderByComparator<MBMessage> orderByComparator) {
		return mbMessagePersistence.findByUuid_C(uuid, companyId, start, end,
			orderByComparator);
	}

	/**
	 * Returns the message-boards message matching the UUID and group.
	 *
	 * @param uuid the message-boards message's UUID
	 * @param groupId the primary key of the group
	 * @return the matching message-boards message
	 * @throws PortalException if a matching message-boards message could not be found
	 */
	@Override
	public MBMessage getMBMessageByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {
		return mbMessagePersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the message-boards messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.messageboards.model.impl.MBMessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of message-boards messages
	 * @param end the upper bound of the range of message-boards messages (not inclusive)
	 * @return the range of message-boards messages
	 */
	@Override
	public List<MBMessage> getMBMessages(int start, int end) {
		return mbMessagePersistence.findAll(start, end);
	}

	/**
	 * Returns the number of message-boards messages.
	 *
	 * @return the number of message-boards messages
	 */
	@Override
	public int getMBMessagesCount() {
		return mbMessagePersistence.countAll();
	}

	/**
	 * Updates the message-boards message in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param mbMessage the message-boards message
	 * @return the message-boards message that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public MBMessage updateMBMessage(MBMessage mbMessage) {
		return mbMessagePersistence.update(mbMessage);
	}

	/**
	 * Returns the message-boards message local service.
	 *
	 * @return the message-boards message local service
	 */
	public MBMessageLocalService getMBMessageLocalService() {
		return mbMessageLocalService;
	}

	/**
	 * Sets the message-boards message local service.
	 *
	 * @param mbMessageLocalService the message-boards message local service
	 */
	public void setMBMessageLocalService(
		MBMessageLocalService mbMessageLocalService) {
		this.mbMessageLocalService = mbMessageLocalService;
	}

	/**
	 * Returns the message-boards message persistence.
	 *
	 * @return the message-boards message persistence
	 */
	public MBMessagePersistence getMBMessagePersistence() {
		return mbMessagePersistence;
	}

	/**
	 * Sets the message-boards message persistence.
	 *
	 * @param mbMessagePersistence the message-boards message persistence
	 */
	public void setMBMessagePersistence(
		MBMessagePersistence mbMessagePersistence) {
		this.mbMessagePersistence = mbMessagePersistence;
	}

	/**
	 * Returns the message-boards message finder.
	 *
	 * @return the message-boards message finder
	 */
	public MBMessageFinder getMBMessageFinder() {
		return mbMessageFinder;
	}

	/**
	 * Sets the message-boards message finder.
	 *
	 * @param mbMessageFinder the message-boards message finder
	 */
	public void setMBMessageFinder(MBMessageFinder mbMessageFinder) {
		this.mbMessageFinder = mbMessageFinder;
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
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the company local service.
	 *
	 * @return the company local service
	 */
	public com.liferay.portal.kernel.service.CompanyLocalService getCompanyLocalService() {
		return companyLocalService;
	}

	/**
	 * Sets the company local service.
	 *
	 * @param companyLocalService the company local service
	 */
	public void setCompanyLocalService(
		com.liferay.portal.kernel.service.CompanyLocalService companyLocalService) {
		this.companyLocalService = companyLocalService;
	}

	/**
	 * Returns the company persistence.
	 *
	 * @return the company persistence
	 */
	public CompanyPersistence getCompanyPersistence() {
		return companyPersistence;
	}

	/**
	 * Sets the company persistence.
	 *
	 * @param companyPersistence the company persistence
	 */
	public void setCompanyPersistence(CompanyPersistence companyPersistence) {
		this.companyPersistence = companyPersistence;
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
	 * Returns the portlet preferences local service.
	 *
	 * @return the portlet preferences local service
	 */
	public com.liferay.portal.kernel.service.PortletPreferencesLocalService getPortletPreferencesLocalService() {
		return portletPreferencesLocalService;
	}

	/**
	 * Sets the portlet preferences local service.
	 *
	 * @param portletPreferencesLocalService the portlet preferences local service
	 */
	public void setPortletPreferencesLocalService(
		com.liferay.portal.kernel.service.PortletPreferencesLocalService portletPreferencesLocalService) {
		this.portletPreferencesLocalService = portletPreferencesLocalService;
	}

	/**
	 * Returns the portlet preferences persistence.
	 *
	 * @return the portlet preferences persistence
	 */
	public PortletPreferencesPersistence getPortletPreferencesPersistence() {
		return portletPreferencesPersistence;
	}

	/**
	 * Sets the portlet preferences persistence.
	 *
	 * @param portletPreferencesPersistence the portlet preferences persistence
	 */
	public void setPortletPreferencesPersistence(
		PortletPreferencesPersistence portletPreferencesPersistence) {
		this.portletPreferencesPersistence = portletPreferencesPersistence;
	}

	/**
	 * Returns the portlet preferences finder.
	 *
	 * @return the portlet preferences finder
	 */
	public PortletPreferencesFinder getPortletPreferencesFinder() {
		return portletPreferencesFinder;
	}

	/**
	 * Sets the portlet preferences finder.
	 *
	 * @param portletPreferencesFinder the portlet preferences finder
	 */
	public void setPortletPreferencesFinder(
		PortletPreferencesFinder portletPreferencesFinder) {
		this.portletPreferencesFinder = portletPreferencesFinder;
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
	 * Returns the subscription local service.
	 *
	 * @return the subscription local service
	 */
	public com.liferay.portal.kernel.service.SubscriptionLocalService getSubscriptionLocalService() {
		return subscriptionLocalService;
	}

	/**
	 * Sets the subscription local service.
	 *
	 * @param subscriptionLocalService the subscription local service
	 */
	public void setSubscriptionLocalService(
		com.liferay.portal.kernel.service.SubscriptionLocalService subscriptionLocalService) {
		this.subscriptionLocalService = subscriptionLocalService;
	}

	/**
	 * Returns the subscription persistence.
	 *
	 * @return the subscription persistence
	 */
	public SubscriptionPersistence getSubscriptionPersistence() {
		return subscriptionPersistence;
	}

	/**
	 * Sets the subscription persistence.
	 *
	 * @param subscriptionPersistence the subscription persistence
	 */
	public void setSubscriptionPersistence(
		SubscriptionPersistence subscriptionPersistence) {
		this.subscriptionPersistence = subscriptionPersistence;
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
	 * Returns the asset link local service.
	 *
	 * @return the asset link local service
	 */
	public com.liferay.asset.kernel.service.AssetLinkLocalService getAssetLinkLocalService() {
		return assetLinkLocalService;
	}

	/**
	 * Sets the asset link local service.
	 *
	 * @param assetLinkLocalService the asset link local service
	 */
	public void setAssetLinkLocalService(
		com.liferay.asset.kernel.service.AssetLinkLocalService assetLinkLocalService) {
		this.assetLinkLocalService = assetLinkLocalService;
	}

	/**
	 * Returns the asset link persistence.
	 *
	 * @return the asset link persistence
	 */
	public AssetLinkPersistence getAssetLinkPersistence() {
		return assetLinkPersistence;
	}

	/**
	 * Sets the asset link persistence.
	 *
	 * @param assetLinkPersistence the asset link persistence
	 */
	public void setAssetLinkPersistence(
		AssetLinkPersistence assetLinkPersistence) {
		this.assetLinkPersistence = assetLinkPersistence;
	}

	/**
	 * Returns the asset tag local service.
	 *
	 * @return the asset tag local service
	 */
	public com.liferay.asset.kernel.service.AssetTagLocalService getAssetTagLocalService() {
		return assetTagLocalService;
	}

	/**
	 * Sets the asset tag local service.
	 *
	 * @param assetTagLocalService the asset tag local service
	 */
	public void setAssetTagLocalService(
		com.liferay.asset.kernel.service.AssetTagLocalService assetTagLocalService) {
		this.assetTagLocalService = assetTagLocalService;
	}

	/**
	 * Returns the asset tag persistence.
	 *
	 * @return the asset tag persistence
	 */
	public AssetTagPersistence getAssetTagPersistence() {
		return assetTagPersistence;
	}

	/**
	 * Sets the asset tag persistence.
	 *
	 * @param assetTagPersistence the asset tag persistence
	 */
	public void setAssetTagPersistence(AssetTagPersistence assetTagPersistence) {
		this.assetTagPersistence = assetTagPersistence;
	}

	/**
	 * Returns the asset tag finder.
	 *
	 * @return the asset tag finder
	 */
	public AssetTagFinder getAssetTagFinder() {
		return assetTagFinder;
	}

	/**
	 * Sets the asset tag finder.
	 *
	 * @param assetTagFinder the asset tag finder
	 */
	public void setAssetTagFinder(AssetTagFinder assetTagFinder) {
		this.assetTagFinder = assetTagFinder;
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
	 * Returns the message boards category local service.
	 *
	 * @return the message boards category local service
	 */
	public com.liferay.message.boards.kernel.service.MBCategoryLocalService getMBCategoryLocalService() {
		return mbCategoryLocalService;
	}

	/**
	 * Sets the message boards category local service.
	 *
	 * @param mbCategoryLocalService the message boards category local service
	 */
	public void setMBCategoryLocalService(
		com.liferay.message.boards.kernel.service.MBCategoryLocalService mbCategoryLocalService) {
		this.mbCategoryLocalService = mbCategoryLocalService;
	}

	/**
	 * Returns the message boards category persistence.
	 *
	 * @return the message boards category persistence
	 */
	public MBCategoryPersistence getMBCategoryPersistence() {
		return mbCategoryPersistence;
	}

	/**
	 * Sets the message boards category persistence.
	 *
	 * @param mbCategoryPersistence the message boards category persistence
	 */
	public void setMBCategoryPersistence(
		MBCategoryPersistence mbCategoryPersistence) {
		this.mbCategoryPersistence = mbCategoryPersistence;
	}

	/**
	 * Returns the message boards category finder.
	 *
	 * @return the message boards category finder
	 */
	public MBCategoryFinder getMBCategoryFinder() {
		return mbCategoryFinder;
	}

	/**
	 * Sets the message boards category finder.
	 *
	 * @param mbCategoryFinder the message boards category finder
	 */
	public void setMBCategoryFinder(MBCategoryFinder mbCategoryFinder) {
		this.mbCategoryFinder = mbCategoryFinder;
	}

	/**
	 * Returns the message boards discussion local service.
	 *
	 * @return the message boards discussion local service
	 */
	public com.liferay.message.boards.kernel.service.MBDiscussionLocalService getMBDiscussionLocalService() {
		return mbDiscussionLocalService;
	}

	/**
	 * Sets the message boards discussion local service.
	 *
	 * @param mbDiscussionLocalService the message boards discussion local service
	 */
	public void setMBDiscussionLocalService(
		com.liferay.message.boards.kernel.service.MBDiscussionLocalService mbDiscussionLocalService) {
		this.mbDiscussionLocalService = mbDiscussionLocalService;
	}

	/**
	 * Returns the message boards discussion persistence.
	 *
	 * @return the message boards discussion persistence
	 */
	public MBDiscussionPersistence getMBDiscussionPersistence() {
		return mbDiscussionPersistence;
	}

	/**
	 * Sets the message boards discussion persistence.
	 *
	 * @param mbDiscussionPersistence the message boards discussion persistence
	 */
	public void setMBDiscussionPersistence(
		MBDiscussionPersistence mbDiscussionPersistence) {
		this.mbDiscussionPersistence = mbDiscussionPersistence;
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
	 * Returns the message boards stats user local service.
	 *
	 * @return the message boards stats user local service
	 */
	public com.liferay.message.boards.kernel.service.MBStatsUserLocalService getMBStatsUserLocalService() {
		return mbStatsUserLocalService;
	}

	/**
	 * Sets the message boards stats user local service.
	 *
	 * @param mbStatsUserLocalService the message boards stats user local service
	 */
	public void setMBStatsUserLocalService(
		com.liferay.message.boards.kernel.service.MBStatsUserLocalService mbStatsUserLocalService) {
		this.mbStatsUserLocalService = mbStatsUserLocalService;
	}

	/**
	 * Returns the message boards stats user persistence.
	 *
	 * @return the message boards stats user persistence
	 */
	public MBStatsUserPersistence getMBStatsUserPersistence() {
		return mbStatsUserPersistence;
	}

	/**
	 * Sets the message boards stats user persistence.
	 *
	 * @param mbStatsUserPersistence the message boards stats user persistence
	 */
	public void setMBStatsUserPersistence(
		MBStatsUserPersistence mbStatsUserPersistence) {
		this.mbStatsUserPersistence = mbStatsUserPersistence;
	}

	/**
	 * Returns the message boards thread local service.
	 *
	 * @return the message boards thread local service
	 */
	public com.liferay.message.boards.kernel.service.MBThreadLocalService getMBThreadLocalService() {
		return mbThreadLocalService;
	}

	/**
	 * Sets the message boards thread local service.
	 *
	 * @param mbThreadLocalService the message boards thread local service
	 */
	public void setMBThreadLocalService(
		com.liferay.message.boards.kernel.service.MBThreadLocalService mbThreadLocalService) {
		this.mbThreadLocalService = mbThreadLocalService;
	}

	/**
	 * Returns the message boards thread persistence.
	 *
	 * @return the message boards thread persistence
	 */
	public MBThreadPersistence getMBThreadPersistence() {
		return mbThreadPersistence;
	}

	/**
	 * Sets the message boards thread persistence.
	 *
	 * @param mbThreadPersistence the message boards thread persistence
	 */
	public void setMBThreadPersistence(MBThreadPersistence mbThreadPersistence) {
		this.mbThreadPersistence = mbThreadPersistence;
	}

	/**
	 * Returns the message boards thread finder.
	 *
	 * @return the message boards thread finder
	 */
	public MBThreadFinder getMBThreadFinder() {
		return mbThreadFinder;
	}

	/**
	 * Sets the message boards thread finder.
	 *
	 * @param mbThreadFinder the message boards thread finder
	 */
	public void setMBThreadFinder(MBThreadFinder mbThreadFinder) {
		this.mbThreadFinder = mbThreadFinder;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.message.boards.kernel.model.MBMessage",
			mbMessageLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.message.boards.kernel.model.MBMessage");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return MBMessageLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return MBMessage.class;
	}

	protected String getModelClassName() {
		return MBMessage.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = mbMessagePersistence.getDataSource();

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

	@BeanReference(type = MBMessageLocalService.class)
	protected MBMessageLocalService mbMessageLocalService;
	@BeanReference(type = MBMessagePersistence.class)
	protected MBMessagePersistence mbMessagePersistence;
	@BeanReference(type = MBMessageFinder.class)
	protected MBMessageFinder mbMessageFinder;
	@BeanReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.ClassNameLocalService.class)
	protected com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = com.liferay.portal.kernel.service.CompanyLocalService.class)
	protected com.liferay.portal.kernel.service.CompanyLocalService companyLocalService;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = com.liferay.portal.kernel.service.GroupLocalService.class)
	protected com.liferay.portal.kernel.service.GroupLocalService groupLocalService;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = GroupFinder.class)
	protected GroupFinder groupFinder;
	@BeanReference(type = com.liferay.portal.kernel.service.PortletPreferencesLocalService.class)
	protected com.liferay.portal.kernel.service.PortletPreferencesLocalService portletPreferencesLocalService;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = PortletPreferencesFinder.class)
	protected PortletPreferencesFinder portletPreferencesFinder;
	@BeanReference(type = com.liferay.portal.kernel.service.ResourceLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.SubscriptionLocalService.class)
	protected com.liferay.portal.kernel.service.SubscriptionLocalService subscriptionLocalService;
	@BeanReference(type = SubscriptionPersistence.class)
	protected SubscriptionPersistence subscriptionPersistence;
	@BeanReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserFinder.class)
	protected UserFinder userFinder;
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
	@BeanReference(type = com.liferay.asset.kernel.service.AssetLinkLocalService.class)
	protected com.liferay.asset.kernel.service.AssetLinkLocalService assetLinkLocalService;
	@BeanReference(type = AssetLinkPersistence.class)
	protected AssetLinkPersistence assetLinkPersistence;
	@BeanReference(type = com.liferay.asset.kernel.service.AssetTagLocalService.class)
	protected com.liferay.asset.kernel.service.AssetTagLocalService assetTagLocalService;
	@BeanReference(type = AssetTagPersistence.class)
	protected AssetTagPersistence assetTagPersistence;
	@BeanReference(type = AssetTagFinder.class)
	protected AssetTagFinder assetTagFinder;
	@BeanReference(type = com.liferay.expando.kernel.service.ExpandoRowLocalService.class)
	protected com.liferay.expando.kernel.service.ExpandoRowLocalService expandoRowLocalService;
	@BeanReference(type = ExpandoRowPersistence.class)
	protected ExpandoRowPersistence expandoRowPersistence;
	@BeanReference(type = com.liferay.message.boards.kernel.service.MBCategoryLocalService.class)
	protected com.liferay.message.boards.kernel.service.MBCategoryLocalService mbCategoryLocalService;
	@BeanReference(type = MBCategoryPersistence.class)
	protected MBCategoryPersistence mbCategoryPersistence;
	@BeanReference(type = MBCategoryFinder.class)
	protected MBCategoryFinder mbCategoryFinder;
	@BeanReference(type = com.liferay.message.boards.kernel.service.MBDiscussionLocalService.class)
	protected com.liferay.message.boards.kernel.service.MBDiscussionLocalService mbDiscussionLocalService;
	@BeanReference(type = MBDiscussionPersistence.class)
	protected MBDiscussionPersistence mbDiscussionPersistence;
	@BeanReference(type = com.liferay.ratings.kernel.service.RatingsStatsLocalService.class)
	protected com.liferay.ratings.kernel.service.RatingsStatsLocalService ratingsStatsLocalService;
	@BeanReference(type = RatingsStatsPersistence.class)
	protected RatingsStatsPersistence ratingsStatsPersistence;
	@BeanReference(type = RatingsStatsFinder.class)
	protected RatingsStatsFinder ratingsStatsFinder;
	@BeanReference(type = com.liferay.message.boards.kernel.service.MBStatsUserLocalService.class)
	protected com.liferay.message.boards.kernel.service.MBStatsUserLocalService mbStatsUserLocalService;
	@BeanReference(type = MBStatsUserPersistence.class)
	protected MBStatsUserPersistence mbStatsUserPersistence;
	@BeanReference(type = com.liferay.message.boards.kernel.service.MBThreadLocalService.class)
	protected com.liferay.message.boards.kernel.service.MBThreadLocalService mbThreadLocalService;
	@BeanReference(type = MBThreadPersistence.class)
	protected MBThreadPersistence mbThreadPersistence;
	@BeanReference(type = MBThreadFinder.class)
	protected MBThreadFinder mbThreadFinder;
	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}