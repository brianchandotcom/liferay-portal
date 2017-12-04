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

import com.liferay.fragment.demo.data.creator.FragmentCollectionDemoDataCreator;
import com.liferay.fragment.exception.NoSuchCollectionException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.IOException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = FragmentCollectionDemoDataCreator.class)
public class FragmentCollectionDemoDataCreatorImpl
	implements FragmentCollectionDemoDataCreator {

	@Override
	public FragmentCollection create(long userId, long groupId, String name)
		throws IOException, PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				userId, groupId, name, null, serviceContext);

		_entryIds.add(fragmentCollection.getFragmentCollectionId());

		return fragmentCollection;
	}

	@Override
	public void delete() throws PortalException {
		for (long entryId : _entryIds) {
			try {
				_fragmentCollectionLocalService.deleteFragmentCollection(
					entryId);
			}
			catch (NoSuchCollectionException nsce) {
				if (_log.isWarnEnabled()) {
					_log.warn(nsce, nsce);
				}
			}

			_entryIds.remove(entryId);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentCollectionDemoDataCreatorImpl.class);

	private final List<Long> _entryIds = new CopyOnWriteArrayList<>();

	@Reference
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

}