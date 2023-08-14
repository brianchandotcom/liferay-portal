/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.service.impl;

import com.liferay.fragment.exception.DuplicateFragmentCollectionKeyException;
import com.liferay.fragment.exception.FragmentCollectionNameException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCompositionLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.service.base.FragmentCollectionLocalServiceBaseImpl;
import com.liferay.fragment.service.persistence.FragmentCompositionPersistence;
import com.liferay.fragment.service.persistence.FragmentEntryPersistence;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
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

		User user = _userLocalService.getUser(userId);

		long companyId = user.getCompanyId();

		if (serviceContext != null) {
			companyId = serviceContext.getCompanyId();
		}
		else {
			serviceContext = new ServiceContext();
		}

		_validate(name);

		if (Validator.isNull(fragmentCollectionKey)) {
			fragmentCollectionKey = generateFragmentCollectionKey(
				groupId, name);
		}

		fragmentCollectionKey = _getFragmentCollectionKey(
			fragmentCollectionKey);

		_validateFragmentCollectionKey(groupId, fragmentCollectionKey);

		long fragmentCollectionId = counterLocalService.increment();

		FragmentCollection fragmentCollection =
			fragmentCollectionPersistence.create(fragmentCollectionId);

		fragmentCollection.setUuid(serviceContext.getUuid());
		fragmentCollection.setGroupId(groupId);
		fragmentCollection.setCompanyId(companyId);
		fragmentCollection.setUserId(user.getUserId());
		fragmentCollection.setUserName(user.getFullName());
		fragmentCollection.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		fragmentCollection.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		fragmentCollection.setFragmentCollectionKey(fragmentCollectionKey);
		fragmentCollection.setName(name);
		fragmentCollection.setDescription(description);

		return fragmentCollectionPersistence.update(fragmentCollection);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public FragmentCollection deleteFragmentCollection(
			FragmentCollection fragmentCollection)
		throws PortalException {

		// Fragment collection

		fragmentCollectionPersistence.remove(fragmentCollection);

		// Resources

		_resourceLocalService.deleteResource(
			fragmentCollection.getCompanyId(),
			FragmentCollection.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			fragmentCollection.getFragmentCollectionId());

		// Images

		PortletFileRepositoryUtil.deletePortletFolder(
			fragmentCollection.getResourcesFolderId(false));

		// Fragment compositions

		List<FragmentComposition> fragmentCompositions =
			_fragmentCompositionPersistence.findByFragmentCollectionId(
				fragmentCollection.getFragmentCollectionId());

		for (FragmentComposition fragmentComposition : fragmentCompositions) {
			_fragmentCompositionLocalService.deleteFragmentComposition(
				fragmentComposition);
		}

		// Fragment entries

		List<FragmentEntry> fragmentEntries =
			_fragmentEntryPersistence.findByFragmentCollectionId(
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
	public String getUniqueFragmentCollectionName(long groupId, String name) {
		FragmentCollection fragmentCollection =
			fragmentCollectionPersistence.fetchByG_LikeN_First(
				groupId, name, null);

		if (fragmentCollection == null) {
			return name;
		}

		int count = 1;

		while (true) {
			String newName = StringUtil.appendParentheticalSuffix(
				name, count++);

			fragmentCollection =
				fragmentCollectionPersistence.fetchByG_LikeN_First(
					groupId, newName, null);

			if (fragmentCollection == null) {
				return newName;
			}
		}
	}

	@Override
	public FragmentCollection updateFragmentCollection(
			long fragmentCollectionId, String name, String description)
		throws PortalException {

		FragmentCollection fragmentCollection =
			fragmentCollectionPersistence.findByPrimaryKey(
				fragmentCollectionId);

		_validate(name);

		fragmentCollection.setModifiedDate(new Date());
		fragmentCollection.setName(name);
		fragmentCollection.setDescription(description);

		return fragmentCollectionPersistence.update(fragmentCollection);
	}

	private String _getFragmentCollectionKey(String fragmentCollectionKey) {
		if (fragmentCollectionKey != null) {
			fragmentCollectionKey = fragmentCollectionKey.trim();

			return StringUtil.toLowerCase(fragmentCollectionKey);
		}

		return StringPool.BLANK;
	}

	private void _validate(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new FragmentCollectionNameException("Name must not be null");
		}

		if (name.contains(StringPool.PERIOD) ||
			name.contains(StringPool.SLASH)) {

			throw new FragmentCollectionNameException(
				"Name contains invalid characters");
		}
	}

	private void _validateFragmentCollectionKey(
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

	@Reference
	private FragmentCompositionLocalService _fragmentCompositionLocalService;

	@Reference
	private FragmentCompositionPersistence _fragmentCompositionPersistence;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private FragmentEntryPersistence _fragmentEntryPersistence;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}