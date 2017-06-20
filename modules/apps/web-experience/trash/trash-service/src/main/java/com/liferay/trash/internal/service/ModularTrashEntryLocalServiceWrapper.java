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

package com.liferay.trash.internal.service;

import com.liferay.petra.model.adapter.util.ModelAdapterUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorAdapter;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.trash.kernel.model.TrashEntry;
import com.liferay.trash.kernel.service.TrashEntryLocalServiceWrapper;
import com.liferay.trash.service.TrashEntryLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class ModularTrashEntryLocalServiceWrapper
	extends TrashEntryLocalServiceWrapper {

	public ModularTrashEntryLocalServiceWrapper() {
		super(null);
	}

	public ModularTrashEntryLocalServiceWrapper(
		com.liferay.trash.kernel.service.TrashEntryLocalService
			trashEntryLocalService) {

		super(trashEntryLocalService);
	}

	@Override
	public TrashEntry addTrashEntry(
			long userId, long groupId, String className, long classPK,
			String classUuid, String referrerClassName, int status,
			List<ObjectValuePair<Long, Integer>> statusOVPs,
			UnicodeProperties typeSettingsProperties)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			TrashEntry.class,
			_trashEntryLocalService.addTrashEntry(
				userId, groupId, className, classPK, classUuid,
				referrerClassName, status, statusOVPs, typeSettingsProperties));
	}

	@Override
	public void checkEntries() throws PortalException {
		_trashEntryLocalService.checkEntries();
	}

	@Override
	public TrashEntry createTrashEntry(long entryId) {
		return ModelAdapterUtil.adapt(
			TrashEntry.class,
			_trashEntryLocalService.createTrashEntry(entryId));
	}

	@Override
	public void deleteEntries(long groupId) {
		_trashEntryLocalService.deleteEntries(groupId);
	}

	@Override
	public TrashEntry deleteEntry(long entryId) {
		com.liferay.trash.model.TrashEntry trashEntry =
			_trashEntryLocalService.deleteEntry(entryId);

		if (trashEntry == null) {
			return null;
		}

		return ModelAdapterUtil.adapt(TrashEntry.class, trashEntry);
	}

	@Override
	public TrashEntry deleteEntry(String className, long classPK) {
		com.liferay.trash.model.TrashEntry trashEntry =
			_trashEntryLocalService.deleteEntry(className, classPK);

		if (trashEntry == null) {
			return null;
		}

		return ModelAdapterUtil.adapt(TrashEntry.class, trashEntry);
	}

	@Override
	public TrashEntry deleteEntry(TrashEntry trashEntry) {
		com.liferay.trash.model.TrashEntry deleteTrashEntry =
			_trashEntryLocalService.deleteEntry(
				ModelAdapterUtil.adapt(
					com.liferay.trash.model.TrashEntry.class, trashEntry));

		if (deleteTrashEntry == null) {
			return null;
		}

		return ModelAdapterUtil.adapt(TrashEntry.class, deleteTrashEntry);
	}

	@Override
	public TrashEntry deleteTrashEntry(long entryId) throws PortalException {
		com.liferay.trash.model.TrashEntry trashEntry =
			_trashEntryLocalService.deleteTrashEntry(entryId);

		if (trashEntry == null) {
			return null;
		}

		return ModelAdapterUtil.adapt(TrashEntry.class, trashEntry);
	}

	@Override
	public TrashEntry deleteTrashEntry(TrashEntry trashEntry) {
		com.liferay.trash.model.TrashEntry deleteTrashEntry =
			_trashEntryLocalService.deleteTrashEntry(
				ModelAdapterUtil.adapt(
					com.liferay.trash.model.TrashEntry.class, trashEntry));

		if (deleteTrashEntry == null) {
			return null;
		}

		return ModelAdapterUtil.adapt(TrashEntry.class, deleteTrashEntry);
	}

	@Override
	public TrashEntry fetchEntry(long entryId) {
		com.liferay.trash.model.TrashEntry trashEntry =
			_trashEntryLocalService.fetchEntry(entryId);

		if (trashEntry == null) {
			return null;
		}

		return ModelAdapterUtil.adapt(TrashEntry.class, trashEntry);
	}

	@Override
	public TrashEntry fetchEntry(String className, long classPK) {
		com.liferay.trash.model.TrashEntry trashEntry =
			_trashEntryLocalService.fetchEntry(className, classPK);

		if (trashEntry == null) {
			return null;
		}

		return ModelAdapterUtil.adapt(TrashEntry.class, trashEntry);
	}

	@Override
	public TrashEntry fetchTrashEntry(long entryId) {
		com.liferay.trash.model.TrashEntry trashEntry =
			_trashEntryLocalService.fetchTrashEntry(entryId);

		if (trashEntry == null) {
			return null;
		}

		return ModelAdapterUtil.adapt(TrashEntry.class, trashEntry);
	}

	@Override
	public List<TrashEntry> getEntries(long groupId) {
		return ModelAdapterUtil.adapt(
			TrashEntry.class, _trashEntryLocalService.getEntries(groupId));
	}

	@Override
	public List<TrashEntry> getEntries(long groupId, int start, int end) {
		return ModelAdapterUtil.adapt(
			TrashEntry.class,
			_trashEntryLocalService.getEntries(groupId, start, end));
	}

	@Override
	public List<TrashEntry> getEntries(
		long groupId, int start, int end, OrderByComparator<TrashEntry> obc) {

		return ModelAdapterUtil.adapt(
			TrashEntry.class,
			_trashEntryLocalService.getEntries(
				groupId, start, end,
				new TrashEntryOrderByComparatorAdapter(obc)));
	}

	@Override
	public List<TrashEntry> getEntries(long groupId, String className) {
		return ModelAdapterUtil.adapt(
			TrashEntry.class,
			_trashEntryLocalService.getEntries(groupId, className));
	}

	@Override
	public int getEntriesCount(long groupId) {
		return _trashEntryLocalService.getEntriesCount(groupId);
	}

	@Override
	public TrashEntry getEntry(long entryId) throws PortalException {
		return ModelAdapterUtil.adapt(
			TrashEntry.class, _trashEntryLocalService.getEntry(entryId));
	}

	@Override
	public TrashEntry getEntry(String className, long classPK)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			TrashEntry.class,
			_trashEntryLocalService.getEntry(className, classPK));
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return _trashEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public List<TrashEntry> getTrashEntries(int start, int end) {
		return ModelAdapterUtil.adapt(
			TrashEntry.class,
			_trashEntryLocalService.getTrashEntries(start, end));
	}

	@Override
	public int getTrashEntriesCount() {
		return _trashEntryLocalService.getTrashEntriesCount();
	}

	@Override
	public TrashEntry getTrashEntry(long entryId) throws PortalException {
		return ModelAdapterUtil.adapt(
			TrashEntry.class, _trashEntryLocalService.getTrashEntry(entryId));
	}

	@Override
	public Hits search(
		long companyId, long groupId, long userId, String keywords, int start,
		int end, Sort sort) {

		return _trashEntryLocalService.search(
			companyId, groupId, userId, keywords, start, end, sort);
	}

	@Override
	public BaseModelSearchResult<TrashEntry> searchTrashEntries(
		long companyId, long groupId, long userId, String keywords, int start,
		int end, Sort sort) {

		BaseModelSearchResult<com.liferay.trash.model.TrashEntry>
			baseModelSearchResult = _trashEntryLocalService.searchTrashEntries(
				companyId, groupId, userId, keywords, start, end, sort);

		return new BaseModelSearchResult<>(
			ModelAdapterUtil.adapt(
				TrashEntry.class, baseModelSearchResult.getBaseModels()),
			baseModelSearchResult.getLength());
	}

	@Reference(unbind = "-")
	protected void setTrashEntryLocalService(
		TrashEntryLocalService trashEntryLocalService) {

		_trashEntryLocalService = trashEntryLocalService;
	}

	private TrashEntryLocalService _trashEntryLocalService;

	private static class TrashEntryOrderByComparatorAdapter
		extends
			OrderByComparatorAdapter
				<com.liferay.trash.model.TrashEntry, TrashEntry> {

		public TrashEntryOrderByComparatorAdapter(
			OrderByComparator<TrashEntry> orderByComparator) {

			super(orderByComparator);
		}

		@Override
		public TrashEntry adapt(com.liferay.trash.model.TrashEntry trashEntry) {
			return ModelAdapterUtil.adapt(TrashEntry.class, trashEntry);
		}

	}

}