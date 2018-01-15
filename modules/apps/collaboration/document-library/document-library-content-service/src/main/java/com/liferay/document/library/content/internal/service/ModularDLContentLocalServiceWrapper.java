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

package com.liferay.document.library.content.internal.service;

import com.liferay.document.library.content.service.DLContentLocalService;
import com.liferay.document.library.kernel.exception.NoSuchContentException;
import com.liferay.document.library.kernel.model.DLContent;
import com.liferay.document.library.kernel.model.DLContentDataBlobModel;
import com.liferay.document.library.kernel.service.DLContentLocalServiceWrapper;
import com.liferay.petra.model.adapter.util.ModelAdapterUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.InputStream;
import java.io.Serializable;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class ModularDLContentLocalServiceWrapper
	extends DLContentLocalServiceWrapper {

	public ModularDLContentLocalServiceWrapper() {
		super(null);
	}

	public ModularDLContentLocalServiceWrapper(
		com.liferay.document.library.kernel.service.DLContentLocalService
			dlContentLocalService) {

		super(dlContentLocalService);
	}

	@Override
	public DLContent addContent(
		long companyId, long repositoryId, String path, String version,
		byte[] bytes) {

		return ModelAdapterUtil.adapt(
			DLContent.class,
			_dlContentLocalService.addContent(
				companyId, repositoryId, path, version, bytes));
	}

	@Override
	public DLContent addContent(
		long companyId, long repositoryId, String path, String version,
		InputStream inputStream, long size) {

		return ModelAdapterUtil.adapt(
			DLContent.class,
			_dlContentLocalService.addContent(
				companyId, repositoryId, path, version, inputStream, size));
	}

	@Override
	public DLContent addDLContent(DLContent dlContent) {
		return ModelAdapterUtil.adapt(
			DLContent.class,
			_dlContentLocalService.addDLContent(
				ModelAdapterUtil.adapt(
					com.liferay.document.library.content.model.DLContent.class,
					dlContent)));
	}

	@Override
	public DLContent createDLContent(long contentId) {
		return ModelAdapterUtil.adapt(
			DLContent.class, _dlContentLocalService.createDLContent(contentId));
	}

	@Override
	public void deleteContent(
			long companyId, long repositoryId, String path, String version)
		throws PortalException {

		_dlContentLocalService.deleteContent(
			companyId, repositoryId, path, version);
	}

	@Override
	public void deleteContents(long companyId, long repositoryId, String path) {
		_dlContentLocalService.deleteContents(companyId, repositoryId, path);
	}

	@Override
	public void deleteContentsByDirectory(
		long companyId, long repositoryId, String dirName) {

		_dlContentLocalService.deleteContentsByDirectory(
			companyId, repositoryId, dirName);
	}

	@Override
	public DLContent deleteDLContent(DLContent dlContent) {
		return ModelAdapterUtil.adapt(
			DLContent.class,
			_dlContentLocalService.deleteDLContent(
				ModelAdapterUtil.adapt(
					com.liferay.document.library.content.model.DLContent.class,
					dlContent)));
	}

	@Override
	public DLContent deleteDLContent(long contentId) throws PortalException {
		return ModelAdapterUtil.adapt(
			DLContent.class, _dlContentLocalService.deleteDLContent(contentId));
	}

	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return _dlContentLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		return _dlContentLocalService.dynamicQuery();
	}

	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return _dlContentLocalService.dynamicQuery(dynamicQuery);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return _dlContentLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return _dlContentLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return _dlContentLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return _dlContentLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public DLContent fetchDLContent(long contentId) {
		return ModelAdapterUtil.adapt(
			DLContent.class, _dlContentLocalService.fetchDLContent(contentId));
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		return _dlContentLocalService.getActionableDynamicQuery();
	}

	@Override
	public DLContent getContent(long companyId, long repositoryId, String path)
		throws NoSuchContentException {

		try {
			return ModelAdapterUtil.adapt(
				DLContent.class,
				_dlContentLocalService.getContent(
					companyId, repositoryId, path));
		}
		catch (
			com.liferay.document.library.content.exception.
				NoSuchContentException nsce) {

			throw new NoSuchContentException(nsce.getMessage());
		}
	}

	@Override
	public DLContent getContent(
			long companyId, long repositoryId, String path, String version)
		throws NoSuchContentException {

		try {
			return ModelAdapterUtil.adapt(
				DLContent.class,
				_dlContentLocalService.getContent(
					companyId, repositoryId, path, version));
		}
		catch (
			com.liferay.document.library.content.exception.
				NoSuchContentException nsce) {

			throw new NoSuchContentException(nsce.getMessage());
		}
	}

	@Override
	public List<DLContent> getContents(long companyId, long repositoryId) {
		return ModelAdapterUtil.adapt(
			DLContent.class,
			_dlContentLocalService.getContents(companyId, repositoryId));
	}

	@Override
	public List<DLContent> getContents(
		long companyId, long repositoryId, String path) {

		return ModelAdapterUtil.adapt(
			DLContent.class,
			_dlContentLocalService.getContents(companyId, repositoryId, path));
	}

	@Override
	public List<DLContent> getContentsByDirectory(
		long companyId, long repositoryId, String dirName) {

		return ModelAdapterUtil.adapt(
			DLContent.class,
			_dlContentLocalService.getContentsByDirectory(
				companyId, repositoryId, dirName));
	}

	@Override
	public DLContentDataBlobModel getDataBlobModel(Serializable primaryKey) {
		com.liferay.document.library.content.model.DLContentDataBlobModel
			dataBlobModel = _dlContentLocalService.getDataBlobModel(primaryKey);

		return new DLContentDataBlobModel(
			dataBlobModel.getContentId(), dataBlobModel.getDataBlob());
	}

	@Override
	public DLContent getDLContent(long contentId) throws PortalException {
		return ModelAdapterUtil.adapt(
			DLContent.class, _dlContentLocalService.getDLContent(contentId));
	}

	@Override
	public List<DLContent> getDLContents(int start, int end) {
		return ModelAdapterUtil.adapt(
			DLContent.class, _dlContentLocalService.getDLContents(start, end));
	}

	@Override
	public int getDLContentsCount() {
		return _dlContentLocalService.getDLContentsCount();
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _dlContentLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return _dlContentLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return _dlContentLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public boolean hasContent(
		long companyId, long repositoryId, String path, String version) {

		return _dlContentLocalService.hasContent(
			companyId, repositoryId, path, version);
	}

	@Override
	public DLContent updateDLContent(DLContent dlContent) {
		return ModelAdapterUtil.adapt(
			DLContent.class,
			_dlContentLocalService.updateDLContent(
				ModelAdapterUtil.adapt(
					com.liferay.document.library.content.model.DLContent.class,
					dlContent)));
	}

	@Override
	public void updateDLContent(
		long companyId, long oldRepositoryId, long newRepositoryId,
		String oldPath, String newPath) {

		_dlContentLocalService.updateDLContent(
			companyId, oldRepositoryId, newRepositoryId, oldPath, newPath);
	}

	@Reference(unbind = "-")
	protected void setDLContentLocalService(
		DLContentLocalService dlContentLocalService) {

		_dlContentLocalService = dlContentLocalService;
	}

	private DLContentLocalService _dlContentLocalService;

}