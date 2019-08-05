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

package com.liferay.segments.demo.data.creator.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.demo.data.creator.SegmentsEntryDemoDataCreator;
import com.liferay.segments.exception.NoSuchEntryException;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsEntryRelLocalService;

import java.io.IOException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(service = SegmentsEntryDemoDataCreator.class)
public class SegmentsEntryDemoDataCreatorImpl
	implements SegmentsEntryDemoDataCreator {

	@Activate
	public void activate(BundleContext bundleContext) {
		Collections.addAll(_availableIndexes, new Integer[] {1, 2});

		Collections.shuffle(_availableIndexes);
	}

	@Override
	public SegmentsEntry create(long userId, long groupId)
		throws IOException, PortalException {

		int index = _getNextIndex();

		Map<Locale, String> nameMap = _getNameMap(index);
		Map<Locale, String> descriptionMap = _getDescriptionMap(index);
		String criteria = _getCriteria(index);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(userId);

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.addSegmentsEntry(
				StringUtil.randomString(), nameMap, descriptionMap, true,
				criteria, SegmentsEntryConstants.SOURCE_DEFAULT,
				User.class.getName(), serviceContext);

		if (Validator.isNull(criteria)) {
			long[] groupUserIds = _userLocalService.getGroupUserIds(groupId);

			for (long groupUserId : groupUserIds) {
				_segmentsEntryRelLocalService.addSegmentsEntryRel(
					segmentsEntry.getSegmentsEntryId(),
					_portal.getClassNameId(User.class), groupUserId,
					serviceContext);
			}
		}

		_segmentsEntryIds.add(segmentsEntry.getSegmentsEntryId());

		return segmentsEntry;
	}

	@Override
	public void delete() throws PortalException {
		for (long entryId : _segmentsEntryIds) {
			try {
				_segmentsEntryLocalService.deleteSegmentsEntry(entryId);
			}
			catch (NoSuchEntryException nsee) {
				if (_log.isWarnEnabled()) {
					_log.warn(nsee, nsee);
				}
			}

			_segmentsEntryIds.remove(entryId);
		}
	}

	private String _getCriteria(int index) {
		Class<?> clazz = getClass();

		String contentPath = StringBundler.concat(
			"com/liferay/segments/demo/data/creator/internal/dependencies",
			"/segment", index, "/criteria.txt");

		try {
			Criteria criteria = new Criteria();

			criteria.addCriterion(
				"user", Criteria.Type.MODEL,
				StringUtil.read(clazz.getClassLoader(), contentPath, false),
				Criteria.Conjunction.AND);

			return CriteriaSerializer.serialize(criteria);
		}
		catch (IOException ioe) {
			return StringPool.BLANK;
		}
	}

	private Map<Locale, String> _getDescriptionMap(int index)
		throws IOException {

		Class<?> clazz = getClass();

		String descriptionPath = StringBundler.concat(
			"com/liferay/segments/demo/data/creator/internal/dependencies",
			"/segment", index, "/description.txt");

		String description = StringUtil.read(
			clazz.getClassLoader(), descriptionPath, false);

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(LocaleUtil.getSiteDefault(), description);

		return descriptionMap;
	}

	private Map<Locale, String> _getNameMap(int index) throws IOException {
		Class<?> clazz = getClass();

		String namePath = StringBundler.concat(
			"com/liferay/segments/demo/data/creator/internal/dependencies",
			"/segment", index, "/name.txt");

		String name = StringUtil.read(clazz.getClassLoader(), namePath, false);

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getSiteDefault(), name);

		return nameMap;
	}

	private int _getNextIndex() {
		int index = _atomicInteger.getAndIncrement();

		if (index == (_availableIndexes.size() - 1)) {
			_atomicInteger.set(0);
		}

		return _availableIndexes.get(index);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsEntryDemoDataCreatorImpl.class);

	private final AtomicInteger _atomicInteger = new AtomicInteger(0);
	private final List<Integer> _availableIndexes =
		new CopyOnWriteArrayList<>();

	@Reference
	private Portal _portal;

	private final List<Long> _segmentsEntryIds = new CopyOnWriteArrayList<>();

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Reference
	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

	@Reference
	private UserLocalService _userLocalService;

}