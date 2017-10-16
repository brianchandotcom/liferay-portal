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

package com.liferay.document.library.file.rank.internal.service;

import com.liferay.document.library.file.rank.service.DLFileRankLocalService;
import com.liferay.document.library.kernel.model.DLFileRank;
import com.liferay.document.library.kernel.service.DLFileRankLocalServiceWrapper;
import com.liferay.petra.model.adapter.util.ModelAdapterUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class ModularDLFileRankLocalServiceWrapper
	extends DLFileRankLocalServiceWrapper {

	public ModularDLFileRankLocalServiceWrapper() {
		super(null);
	}

	public ModularDLFileRankLocalServiceWrapper(
		com.liferay.document.library.kernel.service.DLFileRankLocalService
			dlFileRankLocalService) {

		super(dlFileRankLocalService);
	}

	@Override
	public DLFileRank addDLFileRank(DLFileRank dlFileRank) {
		return ModelAdapterUtil.adapt(
			DLFileRank.class,
			_dlFileRankLocalService.addDLFileRank(
				ModelAdapterUtil.adapt(
					com.liferay.document.library.file.rank.model.
						DLFileRank.class,
					dlFileRank)));
	}

	@Override
	public DLFileRank addFileRank(
		long groupId, long companyId, long userId, long fileEntryId,
		ServiceContext serviceContext) {

		return ModelAdapterUtil.adapt(
			DLFileRank.class,
			_dlFileRankLocalService.addFileRank(
				groupId, companyId, userId, fileEntryId, serviceContext));
	}

	@Override
	public void checkFileRanks() {
		_dlFileRankLocalService.checkFileRanks();
	}

	@Override
	public DLFileRank createDLFileRank(long fileRankId) {
		return ModelAdapterUtil.adapt(
			DLFileRank.class,
			_dlFileRankLocalService.createDLFileRank(fileRankId));
	}

	@Override
	public DLFileRank deleteDLFileRank(DLFileRank dlFileRank) {
		return ModelAdapterUtil.adapt(
			DLFileRank.class,
			_dlFileRankLocalService.deleteDLFileRank(
				ModelAdapterUtil.adapt(
					com.liferay.document.library.file.rank.model.
						DLFileRank.class,
					dlFileRank)));
	}

	@Override
	public DLFileRank deleteDLFileRank(long fileRankId) throws PortalException {
		return ModelAdapterUtil.adapt(
			DLFileRank.class,
			_dlFileRankLocalService.deleteDLFileRank(fileRankId));
	}

	@Override
	public void deleteFileRank(DLFileRank dlFileRank) {
		_dlFileRankLocalService.deleteFileRank(
			ModelAdapterUtil.adapt(
				com.liferay.document.library.file.rank.model.DLFileRank.class,
				dlFileRank));
	}

	@Override
	public void deleteFileRank(long fileRankId) throws PortalException {
		_dlFileRankLocalService.deleteFileRank(fileRankId);
	}

	@Override
	public void deleteFileRanksByFileEntryId(long fileEntryId) {
		_dlFileRankLocalService.deleteFileRanksByFileEntryId(fileEntryId);
	}

	@Override
	public void deleteFileRanksByUserId(long userId) {
		_dlFileRankLocalService.deleteFileRanksByUserId(userId);
	}

	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return _dlFileRankLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public void disableFileRanks(long fileEntryId) {
		_dlFileRankLocalService.disableFileRanks(fileEntryId);
	}

	@Override
	public void disableFileRanksByFolderId(long folderId)
		throws PortalException {

		_dlFileRankLocalService.disableFileRanksByFolderId(folderId);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		return _dlFileRankLocalService.dynamicQuery();
	}

	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return _dlFileRankLocalService.dynamicQuery(dynamicQuery);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return _dlFileRankLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return _dlFileRankLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return _dlFileRankLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return _dlFileRankLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public void enableFileRanks(long fileEntryId) {
		_dlFileRankLocalService.enableFileRanks(fileEntryId);
	}

	@Override
	public void enableFileRanksByFolderId(long folderId)
		throws PortalException {

		_dlFileRankLocalService.enableFileRanksByFolderId(folderId);
	}

	@Override
	public DLFileRank fetchDLFileRank(long fileRankId) {
		return ModelAdapterUtil.adapt(
			DLFileRank.class,
			_dlFileRankLocalService.fetchDLFileRank(fileRankId));
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		return _dlFileRankLocalService.getActionableDynamicQuery();
	}

	@Override
	public DLFileRank getDLFileRank(long fileRankId) throws PortalException {
		return ModelAdapterUtil.adapt(
			DLFileRank.class,
			_dlFileRankLocalService.getDLFileRank(fileRankId));
	}

	@Override
	public List<DLFileRank> getDLFileRanks(int start, int end) {
		return ModelAdapterUtil.adapt(
			DLFileRank.class,
			_dlFileRankLocalService.getDLFileRanks(start, end));
	}

	@Override
	public int getDLFileRanksCount() {
		return _dlFileRankLocalService.getDLFileRanksCount();
	}

	@Override
	public List<DLFileRank> getFileRanks(long groupId, long userId) {
		return ModelAdapterUtil.adapt(
			DLFileRank.class,
			_dlFileRankLocalService.getFileRanks(groupId, userId));
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _dlFileRankLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return _dlFileRankLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return _dlFileRankLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public DLFileRank updateDLFileRank(DLFileRank dlFileRank) {
		return ModelAdapterUtil.adapt(
			DLFileRank.class,
			_dlFileRankLocalService.updateDLFileRank(
				ModelAdapterUtil.adapt(
					com.liferay.document.library.file.rank.model.
						DLFileRank.class,
					dlFileRank)));
	}

	@Override
	public DLFileRank updateFileRank(
		long groupId, long companyId, long userId, long fileEntryId,
		ServiceContext serviceContext) {

		return ModelAdapterUtil.adapt(
			DLFileRank.class,
			_dlFileRankLocalService.updateFileRank(
				groupId, companyId, userId, fileEntryId, serviceContext));
	}

	@Reference(unbind = "-")
	protected void setDLFileRankLocalService(
		DLFileRankLocalService dlFileRankLocalService) {

		_dlFileRankLocalService = dlFileRankLocalService;
	}

	private DLFileRankLocalService _dlFileRankLocalService;

}