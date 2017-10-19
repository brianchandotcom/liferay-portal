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

package com.liferay.message.boards.internal.service;

import com.liferay.message.boards.kernel.model.MBStatsUser;
import com.liferay.message.boards.kernel.service.MBStatsUserLocalServiceWrapper;
import com.liferay.message.boards.service.MBStatsUserLocalService;
import com.liferay.petra.model.adapter.util.ModelAdapterUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class ModularMBStatsUserLocalServiceWrapper
	extends MBStatsUserLocalServiceWrapper {

	public ModularMBStatsUserLocalServiceWrapper() {
		super(null);
	}

	public ModularMBStatsUserLocalServiceWrapper(
		com.liferay.message.boards.kernel.service.MBStatsUserLocalService
			mbStatsUserLocalService) {

		super(mbStatsUserLocalService);
	}

	@Override
	public MBStatsUser addMBStatsUser(MBStatsUser mbStatsUser) {
		return ModelAdapterUtil.adapt(
			MBStatsUser.class,
			_mbStatsUserLocalService.addMBStatsUser(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBStatsUser.class,
					mbStatsUser)));
	}

	@Override
	public MBStatsUser addStatsUser(long groupId, long userId) {
		return ModelAdapterUtil.adapt(
			MBStatsUser.class,
			_mbStatsUserLocalService.addStatsUser(groupId, userId));
	}

	@Override
	public MBStatsUser createMBStatsUser(long statsUserId) {
		return ModelAdapterUtil.adapt(
			MBStatsUser.class,
			_mbStatsUserLocalService.createMBStatsUser(statsUserId));
	}

	@Override
	public MBStatsUser deleteMBStatsUser(long statsUserId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBStatsUser.class,
			_mbStatsUserLocalService.deleteMBStatsUser(statsUserId));
	}

	@Override
	public MBStatsUser deleteMBStatsUser(MBStatsUser mbStatsUser) {
		return ModelAdapterUtil.adapt(
			MBStatsUser.class,
			_mbStatsUserLocalService.deleteMBStatsUser(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBStatsUser.class,
					mbStatsUser)));
	}

	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return _mbStatsUserLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void deleteStatsUser(long statsUserId) throws PortalException {
		_mbStatsUserLocalService.deleteStatsUser(statsUserId);
	}

	@Override
	public void deleteStatsUser(MBStatsUser statsUser) {
		_mbStatsUserLocalService.deleteStatsUser(
			ModelAdapterUtil.adapt(
				com.liferay.message.boards.model.MBStatsUser.class, statsUser));
	}

	@Override
	public void deleteStatsUsersByGroupId(long groupId) {
		_mbStatsUserLocalService.deleteStatsUsersByGroupId(groupId);
	}

	@Override
	public void deleteStatsUsersByUserId(long userId) {
		_mbStatsUserLocalService.deleteStatsUsersByUserId(userId);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		return _mbStatsUserLocalService.dynamicQuery();
	}

	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return _mbStatsUserLocalService.dynamicQuery(dynamicQuery);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return _mbStatsUserLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return _mbStatsUserLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return _mbStatsUserLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return _mbStatsUserLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public MBStatsUser fetchMBStatsUser(long statsUserId) {
		return ModelAdapterUtil.adapt(
			MBStatsUser.class,
			_mbStatsUserLocalService.fetchMBStatsUser(statsUserId));
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		return _mbStatsUserLocalService.getActionableDynamicQuery();
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _mbStatsUserLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public Date getLastPostDateByUserId(long groupId, long userId) {
		return _mbStatsUserLocalService.getLastPostDateByUserId(
			groupId, userId);
	}

	@Override
	public MBStatsUser getMBStatsUser(long statsUserId) throws PortalException {
		return ModelAdapterUtil.adapt(
			MBStatsUser.class,
			_mbStatsUserLocalService.getMBStatsUser(statsUserId));
	}

	@Override
	public List<MBStatsUser> getMBStatsUsers(int start, int end) {
		return ModelAdapterUtil.adapt(
			MBStatsUser.class,
			_mbStatsUserLocalService.getMBStatsUsers(start, end));
	}

	@Override
	public int getMBStatsUsersCount() {
		return _mbStatsUserLocalService.getMBStatsUsersCount();
	}

	@Override
	public long getMessageCountByGroupId(long groupId) {
		return _mbStatsUserLocalService.getMessageCountByGroupId(groupId);
	}

	@Override
	public long getMessageCountByUserId(long userId) {
		return _mbStatsUserLocalService.getMessageCountByUserId(userId);
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return _mbStatsUserLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return _mbStatsUserLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public MBStatsUser getStatsUser(long groupId, long userId) {
		return ModelAdapterUtil.adapt(
			MBStatsUser.class,
			_mbStatsUserLocalService.getStatsUser(groupId, userId));
	}

	@Override
	public List<MBStatsUser> getStatsUsersByGroupId(
			long groupId, int start, int end)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			MBStatsUser.class,
			_mbStatsUserLocalService.getStatsUsersByGroupId(
				groupId, start, end));
	}

	@Override
	public int getStatsUsersByGroupIdCount(long groupId)
		throws PortalException {

		return _mbStatsUserLocalService.getStatsUsersByGroupIdCount(groupId);
	}

	@Override
	public List<MBStatsUser> getStatsUsersByUserId(long userId) {
		return ModelAdapterUtil.adapt(
			MBStatsUser.class,
			_mbStatsUserLocalService.getStatsUsersByUserId(userId));
	}

	@Override
	public MBStatsUser updateMBStatsUser(MBStatsUser mbStatsUser) {
		return ModelAdapterUtil.adapt(
			MBStatsUser.class,
			_mbStatsUserLocalService.updateMBStatsUser(
				ModelAdapterUtil.adapt(
					com.liferay.message.boards.model.MBStatsUser.class,
					mbStatsUser)));
	}

	@Override
	public MBStatsUser updateStatsUser(long groupId, long userId) {
		return ModelAdapterUtil.adapt(
			MBStatsUser.class,
			_mbStatsUserLocalService.updateStatsUser(groupId, userId));
	}

	@Override
	public MBStatsUser updateStatsUser(
		long groupId, long userId, Date lastPostDate) {

		return ModelAdapterUtil.adapt(
			MBStatsUser.class,
			_mbStatsUserLocalService.updateStatsUser(
				groupId, userId, lastPostDate));
	}

	@Override
	public MBStatsUser updateStatsUser(
		long groupId, long userId, int messageCount, Date lastPostDate) {

		return ModelAdapterUtil.adapt(
			MBStatsUser.class,
			_mbStatsUserLocalService.updateStatsUser(
				groupId, userId, messageCount, lastPostDate));
	}

	@Reference
	private MBStatsUserLocalService _mbStatsUserLocalService;

}