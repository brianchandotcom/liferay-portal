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

package com.liferay.blogs.internal.wrapper;

import com.liferay.blogs.kernel.model.BlogsStatsUser;
import com.liferay.blogs.kernel.service.BlogsStatsUserLocalService;
import com.liferay.blogs.kernel.service.BlogsStatsUserLocalServiceWrapper;
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
public class ModularBlogsStatsUserLocalServiceWrapper
	extends BlogsStatsUserLocalServiceWrapper {

	public ModularBlogsStatsUserLocalServiceWrapper() {
		super(null);
	}

	public ModularBlogsStatsUserLocalServiceWrapper(
		BlogsStatsUserLocalService blogsStatsUserLocalService) {

		super(blogsStatsUserLocalService);
	}

	@Override
	public BlogsStatsUser addBlogsStatsUser(BlogsStatsUser blogsStatsUser) {
		return super.addBlogsStatsUser(blogsStatsUser);
	}

	@Override
	public BlogsStatsUser createBlogsStatsUser(long statsUserId) {
		return super.createBlogsStatsUser(statsUserId);
	}

	@Override
	public BlogsStatsUser deleteBlogsStatsUser(BlogsStatsUser blogsStatsUser) {
		return super.deleteBlogsStatsUser(blogsStatsUser);
	}

	@Override
	public BlogsStatsUser deleteBlogsStatsUser(long statsUserId)
		throws PortalException {

		return super.deleteBlogsStatsUser(statsUserId);
	}

	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return super.deletePersistedModel(persistedModel);
	}

	@Override
	public void deleteStatsUser(BlogsStatsUser statsUsers) {
		_blogsStatsUserLocalService.deleteStatsUser(statsUsers);
	}

	@Override
	public void deleteStatsUser(long statsUserId) throws PortalException {
		_blogsStatsUserLocalService.deleteStatsUser(statsUserId);
	}

	@Override
	public void deleteStatsUserByGroupId(long groupId) {
		_blogsStatsUserLocalService.deleteStatsUserByGroupId(groupId);
	}

	@Override
	public void deleteStatsUserByUserId(long userId) {
		_blogsStatsUserLocalService.deleteStatsUserByUserId(userId);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		return super.dynamicQuery();
	}

	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return super.dynamicQuery(dynamicQuery);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return super.dynamicQuery(dynamicQuery, start, end);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return super.dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return super.dynamicQueryCount(dynamicQuery);
	}

	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return super.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public BlogsStatsUser fetchBlogsStatsUser(long statsUserId) {
		return super.fetchBlogsStatsUser(statsUserId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		return super.getActionableDynamicQuery();
	}

	@Override
	public BlogsStatsUser getBlogsStatsUser(long statsUserId)
		throws PortalException {

		return super.getBlogsStatsUser(statsUserId);
	}

	@Override
	public List<BlogsStatsUser> getBlogsStatsUsers(int start, int end) {
		return super.getBlogsStatsUsers(start, end);
	}

	@Override
	public int getBlogsStatsUsersCount() {
		return super.getBlogsStatsUsersCount();
	}

	@Override
	public List<BlogsStatsUser> getCompanyStatsUsers(
		long companyId, int start, int end) {

		return _blogsStatsUserLocalService.getCompanyStatsUsers(
			companyId, start, end);
	}

	@Override
	public List<BlogsStatsUser> getCompanyStatsUsers(
		long companyId, int start, int end,
		OrderByComparator<BlogsStatsUser> obc) {

		return _blogsStatsUserLocalService.getCompanyStatsUsers(
			companyId, start, end, obc);
	}

	@Override
	public int getCompanyStatsUsersCount(long companyId) {
		return _blogsStatsUserLocalService.getCompanyStatsUsersCount(companyId);
	}

	@Override
	public List<BlogsStatsUser> getGroupsStatsUsers(
		long companyId, long groupId, int start, int end) {

		return _blogsStatsUserLocalService.getGroupsStatsUsers(
			companyId, groupId, start, end);
	}

	@Override
	public List<BlogsStatsUser> getGroupStatsUsers(
		long groupId, int start, int end) {

		return _blogsStatsUserLocalService.getGroupStatsUsers(
			groupId, start, end);
	}

	@Override
	public List<BlogsStatsUser> getGroupStatsUsers(
		long groupId, int start, int end,
		OrderByComparator<BlogsStatsUser> obc) {

		return _blogsStatsUserLocalService.getGroupStatsUsers(
			groupId, start, end, obc);
	}

	@Override
	public int getGroupStatsUsersCount(long groupId) {
		return _blogsStatsUserLocalService.getGroupStatsUsersCount(groupId);
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return super.getIndexableActionableDynamicQuery();
	}

	@Override
	public List<BlogsStatsUser> getOrganizationStatsUsers(
		long organizationId, int start, int end) {

		return _blogsStatsUserLocalService.getOrganizationStatsUsers(
			organizationId, start, end);
	}

	@Override
	public List<BlogsStatsUser> getOrganizationStatsUsers(
		long organizationId, int start, int end,
		OrderByComparator<BlogsStatsUser> obc) {

		return _blogsStatsUserLocalService.getOrganizationStatsUsers(
			organizationId, start, end, obc);
	}

	@Override
	public int getOrganizationStatsUsersCount(long organizationId) {
		return _blogsStatsUserLocalService.getOrganizationStatsUsersCount(
			organizationId);
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return _blogsStatsUserLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return super.getPersistedModel(primaryKeyObj);
	}

	@Override
	public BlogsStatsUser getStatsUser(long groupId, long userId)
		throws PortalException {

		return _blogsStatsUserLocalService.getStatsUser(groupId, userId);
	}

	@Override
	public BlogsStatsUserLocalService getWrappedService() {
		return super.getWrappedService();
	}

	@Override
	public void setWrappedService(
		BlogsStatsUserLocalService blogsStatsUserLocalService) {

		super.setWrappedService(blogsStatsUserLocalService);
	}

	@Override
	public BlogsStatsUser updateBlogsStatsUser(BlogsStatsUser blogsStatsUser) {
		return super.updateBlogsStatsUser(blogsStatsUser);
	}

	@Override
	public void updateStatsUser(long groupId, long userId)
		throws PortalException {

		_blogsStatsUserLocalService.updateStatsUser(groupId, userId);
	}

	@Override
	public void updateStatsUser(long groupId, long userId, Date displayDate)
		throws PortalException {

		_blogsStatsUserLocalService.updateStatsUser(
			groupId, userId, displayDate);
	}

	@Reference
	protected void setBlogsStatsUserLocalService(
		com.
			liferay.
				blogs.
					service.
						BlogsStatsUserLocalService blogsStatsUserLocalService) {

		_blogsStatsUserLocalService = blogsStatsUserLocalService;
	}

	private com.liferay.blogs.service.BlogsStatsUserLocalService
		_blogsStatsUserLocalService;

}