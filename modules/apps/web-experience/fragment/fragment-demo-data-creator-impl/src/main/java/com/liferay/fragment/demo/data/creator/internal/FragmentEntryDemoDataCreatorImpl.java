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

package com.liferay.fragment.demo.data.creator.internal;

import com.liferay.fragment.demo.data.creator.FragmentEntryDemoDataCreator;
import com.liferay.fragment.exception.NoSuchEntryException;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = FragmentEntryDemoDataCreator.class)
public class FragmentEntryDemoDataCreatorImpl
	implements FragmentEntryDemoDataCreator {

	@Activate
	public void activate(BundleContext bundleContext) {
		Collections.addAll(_availableIndexes, new Integer[] {1, 2, 3, 4, 5});

		Collections.shuffle(_availableIndexes);
	}

	@Override
	public FragmentEntry create(
			long userId, long groupId, long fragmentCollectionId)
		throws IOException, PortalException {

		int index = _getNextIndex();

		String name = _getFieldValue(index, "name.txt");
		String css = _getFieldValue(index, "demo.css");
		String html = _getFieldValue(index, "demo.html");
		String js = _getFieldValue(index, "demo.js");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				userId, groupId, fragmentCollectionId, name, css, html, js,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		_fragmentEntryIds.add(fragmentEntry.getFragmentEntryId());

		return fragmentEntry;
	}

	@Override
	public void delete() throws PortalException {
		for (long fragmentEntryId : _fragmentEntryIds) {
			try {
				_fragmentEntryLocalService.deleteFragmentEntry(fragmentEntryId);
			}
			catch (NoSuchEntryException nsee) {
				if (_log.isWarnEnabled()) {
					_log.warn(nsee, nsee);
				}
			}

			_fragmentEntryIds.remove(fragmentEntryId);
		}
	}

	private String _getFieldValue(int index, String fileName)
		throws IOException {

		Class<?> clazz = getClass();

		String contentPath = StringBundler.concat(
			"com/liferay/fragment/demo/data/creator/internal/dependencies",
			"/fragment", String.valueOf(index), "/", fileName);

		return StringUtil.read(clazz.getClassLoader(), contentPath, false);
	}

	private int _getNextIndex() {
		int index = _atomicInteger.getAndIncrement();

		if (index == (_availableIndexes.size() - 1)) {
			_atomicInteger.set(0);
		}

		return _availableIndexes.get(index);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryDemoDataCreatorImpl.class);

	private final AtomicInteger _atomicInteger = new AtomicInteger(0);
	private final List<Integer> _availableIndexes =
		new CopyOnWriteArrayList<>();
	private final List<Long> _fragmentEntryIds = new CopyOnWriteArrayList<>();

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

}