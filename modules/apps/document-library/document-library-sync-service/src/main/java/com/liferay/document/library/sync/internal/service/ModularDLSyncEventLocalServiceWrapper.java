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

package com.liferay.document.library.sync.internal.service;

import com.liferay.document.library.kernel.model.DLSyncEvent;
import com.liferay.document.library.kernel.service.DLSyncEventLocalService;
import com.liferay.document.library.kernel.service.DLSyncEventLocalServiceWrapper;
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

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class ModularDLSyncEventLocalServiceWrapper
	extends DLSyncEventLocalServiceWrapper {

	public ModularDLSyncEventLocalServiceWrapper() {
		super(null);
	}

	public ModularDLSyncEventLocalServiceWrapper(
		DLSyncEventLocalService dlSyncEventLocalService) {

		super(dlSyncEventLocalService);
	}

	@Override
	public DLSyncEvent addDLSyncEvent(DLSyncEvent dlSyncEvent) {
		return ModelAdapterUtil.adapt(
			DLSyncEvent.class,
			_dlSyncEventLocalService.addDLSyncEvent(
				ModelAdapterUtil.adapt(
					com.liferay.document.library.sync.model.DLSyncEvent.class,
					dlSyncEvent)));
	}

	@Override
	public DLSyncEvent addDLSyncEvent(String event, String type, long typePK) {
		return ModelAdapterUtil.adapt(
			DLSyncEvent.class,
			_dlSyncEventLocalService.addDLSyncEvent(event, type, typePK));
	}

	@Override
	public DLSyncEvent createDLSyncEvent(long syncEventId) {
		return ModelAdapterUtil.adapt(
			DLSyncEvent.class,
			_dlSyncEventLocalService.createDLSyncEvent(syncEventId));
	}

	@Override
	public DLSyncEvent deleteDLSyncEvent(DLSyncEvent dlSyncEvent) {
		return ModelAdapterUtil.adapt(
			DLSyncEvent.class,
			_dlSyncEventLocalService.deleteDLSyncEvent(
				ModelAdapterUtil.adapt(
					com.liferay.document.library.sync.model.DLSyncEvent.class,
					dlSyncEvent)));
	}

	@Override
	public DLSyncEvent deleteDLSyncEvent(long syncEventId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			DLSyncEvent.class,
			_dlSyncEventLocalService.deleteDLSyncEvent(syncEventId));
	}

	@Override
	public void deleteDLSyncEvents() {
		_dlSyncEventLocalService.deleteDLSyncEvents();
	}

	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			DLSyncEvent.class,
			_dlSyncEventLocalService.deletePersistedModel(
				ModelAdapterUtil.adapt(
					com.liferay.document.library.sync.model.DLSyncEvent.class,
					persistedModel)));
	}

	@Override
	public DynamicQuery dynamicQuery() {
		return _dlSyncEventLocalService.dynamicQuery();
	}

	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return _dlSyncEventLocalService.dynamicQuery(dynamicQuery);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return _dlSyncEventLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return _dlSyncEventLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return _dlSyncEventLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return _dlSyncEventLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public DLSyncEvent fetchDLSyncEvent(long syncEventId) {
		return ModelAdapterUtil.adapt(
			DLSyncEvent.class,
			_dlSyncEventLocalService.fetchDLSyncEvent(syncEventId));
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		return _dlSyncEventLocalService.getActionableDynamicQuery();
	}

	@Override
	public DLSyncEvent getDLSyncEvent(long syncEventId) throws PortalException {
		return ModelAdapterUtil.adapt(
			DLSyncEvent.class,
			_dlSyncEventLocalService.getDLSyncEvent(syncEventId));
	}

	@Override
	public List<DLSyncEvent> getDLSyncEvents(int start, int end) {
		return ModelAdapterUtil.adapt(
			DLSyncEvent.class,
			_dlSyncEventLocalService.getDLSyncEvents(start, end));
	}

	@Override
	public List<DLSyncEvent> getDLSyncEvents(long modifiedTime) {
		return ModelAdapterUtil.adapt(
			DLSyncEvent.class,
			_dlSyncEventLocalService.getDLSyncEvents(modifiedTime));
	}

	@Override
	public int getDLSyncEventsCount() {
		return _dlSyncEventLocalService.getDLSyncEventsCount();
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _dlSyncEventLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public List<DLSyncEvent> getLatestDLSyncEvents() {
		return ModelAdapterUtil.adapt(
			DLSyncEvent.class,
			_dlSyncEventLocalService.getLatestDLSyncEvents());
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			DLSyncEvent.class,
			_dlSyncEventLocalService.getPersistedModel(primaryKeyObj));
	}

	@Override
	public DLSyncEvent updateDLSyncEvent(DLSyncEvent dlSyncEvent) {
		return ModelAdapterUtil.adapt(
			DLSyncEvent.class,
			_dlSyncEventLocalService.updateDLSyncEvent(
				ModelAdapterUtil.adapt(
					com.liferay.document.library.sync.model.DLSyncEvent.class,
					dlSyncEvent)));
	}

	@Reference
	private com.liferay.document.library.sync.service.DLSyncEventLocalService
		_dlSyncEventLocalService;

}