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

package com.liferay.fragment.service.impl;

import com.liferay.fragment.exception.DuplicateFragmentCollectionKeyException;
import com.liferay.fragment.exception.FragmentCollectionNameException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.service.base.FragmentCollectionLocalServiceBaseImpl;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = "model.class.name=com.liferay.fragment.model.FragmentCollection",
	service = AopService.class
)
public class FragmentCollectionLocalServiceImpl
	extends FragmentCollectionLocalServiceBaseImpl {

	@Override
	public FragmentCollection addFragmentCollection(
			long userId, long groupId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		return addFragmentCollection(
			userId, groupId, StringPool.BLANK, name, description,
			serviceContext);
	}

	@Override
	public FragmentCollection addFragmentCollection(
			long userId, long groupId, String fragmentCollectionKey,
			String name, String description, ServiceContext serviceContext)
		throws PortalException {

		// Fragment collection

		User user = userLocalService.getUser(userId);

		validate(name);

		if (Validator.isNull(fragmentCollectionKey)) {
			fragmentCollectionKey = generateFragmentCollectionKey(
				groupId, name);
		}

		fragmentCollectionKey = _getFragmentCollectionKey(
			fragmentCollectionKey);

		validateFragmentCollectionKey(groupId, fragmentCollectionKey);

		long fragmentCollectionId = counterLocalService.increment();

		FragmentCollection fragmentCollection =
			fragmentCollectionPersistence.create(fragmentCollectionId);

		fragmentCollection.setUuid(serviceContext.getUuid());
		fragmentCollection.setGroupId(groupId);
		fragmentCollection.setCompanyId(user.getCompanyId());
		fragmentCollection.setUserId(user.getUserId());
		fragmentCollection.setUserName(user.getFullName());
		fragmentCollection.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		fragmentCollection.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		fragmentCollection.setFragmentCollectionKey(fragmentCollectionKey);
		fragmentCollection.setName(name);
		fragmentCollection.setDescription(description);

		fragmentCollectionPersistence.update(fragmentCollection);

		return fragmentCollection;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public FragmentCollection deleteFragmentCollection(
			FragmentCollection fragmentCollection)
		throws PortalException {

		/// Fragment collection

		fragmentCollectionPersistence.remove(fragmentCollection);

		// Resources

		resourceLocalService.deleteResource(
			fragmentCollection.getCompanyId(),
			FragmentCollection.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			fragmentCollection.getFragmentCollectionId());

		// Images

		PortletFileRepositoryUtil.deletePortletFolder(
			fragmentCollection.getResourcesFolderId(false));

		// Fragment entries

		List<FragmentEntry> fragmentEntries =
			fragmentEntryPersistence.findByFragmentCollectionId(
				fragmentCollection.getFragmentCollectionId());

		for (FragmentEntry fragmentEntry : fragmentEntries) {
			_fragmentEntryLocalService.deleteFragmentEntry(fragmentEntry);
		}

		return fragmentCollection;
	}

	@Override
	public FragmentCollection deleteFragmentCollection(
			long fragmentCollectionId)
		throws PortalException {

		return fragmentCollectionLocalService.deleteFragmentCollection(
			getFragmentCollection(fragmentCollectionId));
	}

	@Override
	public FragmentCollection fetchFragmentCollection(
		long fragmentCollectionId) {

		return fragmentCollectionPersistence.fetchByPrimaryKey(
			fragmentCollectionId);
	}

	@Override
	public FragmentCollection fetchFragmentCollection(
		long groupId, String fragmentCollectionKey) {

		return fragmentCollectionPersistence.fetchByG_FCK(
			groupId, _getFragmentCollectionKey(fragmentCollectionKey));
	}

	@Override
	public String generateFragmentCollectionKey(long groupId, String name) {
		String fragmentCollectionKey = _getFragmentCollectionKey(name);

		fragmentCollectionKey = StringUtil.replace(
			fragmentCollectionKey, CharPool.SPACE, CharPool.DASH);

		String curFragmentCollectionKey = fragmentCollectionKey;

		int count = 0;

		while (true) {
			FragmentCollection fragmentCollection =
				fragmentCollectionPersistence.fetchByG_FCK(
					groupId, curFragmentCollectionKey);

			if (fragmentCollection == null) {
				return curFragmentCollectionKey;
			}

			curFragmentCollectionKey =
				fragmentCollectionKey + CharPool.DASH + count++;
		}
	}

	@Override
	public List<FragmentCollection> getFragmentCollections(
		long groupId, int start, int end) {

		return getFragmentCollections(groupId, start, end, null);
	}

	@Override
	public List<FragmentCollection> getFragmentCollections(
		long groupId, int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator) {

		return fragmentCollectionPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<FragmentCollection> getFragmentCollections(
		long groupId, String name, int start, int end,
		OrderByComparator<FragmentCollection> orderByComparator) {

		if (Validator.isNull(name)) {
			return fragmentCollectionPersistence.findByGroupId(
				groupId, start, end, orderByComparator);
		}

		return fragmentCollectionPersistence.findByG_LikeN(
			groupId, name, start, end, orderByComparator);
	}

	@Override
	public String[] getTempFileNames(
			long userId, long groupId, String folderName)
		throws PortalException {

		return TempFileEntryUtil.getTempFileNames(groupId, userId, folderName);
	}

	@Override
	public FragmentCollection updateFragmentCollection(
			long fragmentCollectionId, String name, String description)
		throws PortalException {

		FragmentCollection fragmentCollection =
			fragmentCollectionPersistence.findByPrimaryKey(
				fragmentCollectionId);

		validate(name);

		fragmentCollection.setModifiedDate(new Date());
		fragmentCollection.setName(name);
		fragmentCollection.setDescription(description);

		fragmentCollectionPersistence.update(fragmentCollection);

		return fragmentCollection;
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new FragmentCollectionNameException("Name must not be null");
		}
	}

	protected void validateFragmentCollectionKey(
			long groupId, String fragmentCollectionKey)
		throws PortalException {

		fragmentCollectionKey = _getFragmentCollectionKey(
			fragmentCollectionKey);

		FragmentCollection fragmentCollection =
			fragmentCollectionPersistence.fetchByG_FCK(
				groupId, fragmentCollectionKey);

		if (fragmentCollection != null) {
			throw new DuplicateFragmentCollectionKeyException();
		}
	}

	private String _getFragmentCollectionKey(String fragmentCollectionKey) {
		if (fragmentCollectionKey != null) {
			fragmentCollectionKey = fragmentCollectionKey.trim();

			return StringUtil.toLowerCase(fragmentCollectionKey);
		}

		return StringPool.BLANK;
	}

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

}