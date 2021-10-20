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

package com.liferay.layout.page.template.service.impl;

import com.liferay.layout.page.template.exception.DuplicateLayoutPageTemplateCollectionException;
import com.liferay.layout.page.template.exception.LayoutPageTemplateCollectionNameException;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.base.LayoutPageTemplateCollectionLocalServiceBaseImpl;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = "model.class.name=com.liferay.layout.page.template.model.LayoutPageTemplateCollection",
	service = AopService.class
)
public class LayoutPageTemplateCollectionLocalServiceImpl
	extends LayoutPageTemplateCollectionLocalServiceBaseImpl {

	@Override
	public LayoutPageTemplateCollection addLayoutPageTemplateCollection(
			long userId, long groupId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		// Layout page template collection

		User user = _userLocalService.getUser(userId);

		validate(groupId, name);

		long layoutPageTemplateId = counterLocalService.increment();

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			layoutPageTemplateCollectionPersistence.create(
				layoutPageTemplateId);

		layoutPageTemplateCollection.setUuid(serviceContext.getUuid());
		layoutPageTemplateCollection.setGroupId(groupId);
		layoutPageTemplateCollection.setCompanyId(user.getCompanyId());
		layoutPageTemplateCollection.setUserId(user.getUserId());
		layoutPageTemplateCollection.setUserName(user.getFullName());
		layoutPageTemplateCollection.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		layoutPageTemplateCollection.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		layoutPageTemplateCollection.setLayoutPageTemplateCollectionKey(
			_generateLayoutPageTemplateCollectionKey(groupId, name));
		layoutPageTemplateCollection.setName(name);
		layoutPageTemplateCollection.setDescription(description);

		layoutPageTemplateCollection =
			layoutPageTemplateCollectionPersistence.update(
				layoutPageTemplateCollection);

		// Resources

		_resourceLocalService.addModelResources(
			layoutPageTemplateCollection, serviceContext);

		return layoutPageTemplateCollection;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public LayoutPageTemplateCollection deleteLayoutPageTemplateCollection(
			LayoutPageTemplateCollection layoutPageTemplateCollection)
		throws PortalException {

		/// Layout page template collection

		layoutPageTemplateCollectionPersistence.remove(
			layoutPageTemplateCollection);

		// Resources

		_resourceLocalService.deleteResource(
			layoutPageTemplateCollection.getCompanyId(),
			LayoutPageTemplateCollection.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			layoutPageTemplateCollection.getLayoutPageTemplateCollectionId());

		// Layout page template entries

		List<LayoutPageTemplateEntry> layoutPageTemplateEntries =
			_layoutPageTemplateEntryLocalService.getLayoutPageTemplateEntries(
				layoutPageTemplateCollection.getGroupId(),
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId());

		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				layoutPageTemplateEntries) {

			_layoutPageTemplateEntryLocalService.deleteLayoutPageTemplateEntry(
				layoutPageTemplateEntry);
		}

		return layoutPageTemplateCollection;
	}

	@Override
	public LayoutPageTemplateCollection deleteLayoutPageTemplateCollection(
			long layoutPageTemplateCollectionId)
		throws PortalException {

		return deleteLayoutPageTemplateCollection(
			getLayoutPageTemplateCollection(layoutPageTemplateCollectionId));
	}

	@Override
	public LayoutPageTemplateCollection fetchLayoutPageTemplateCollection(
		long layoutPageTemplateCollectionId) {

		return layoutPageTemplateCollectionPersistence.fetchByPrimaryKey(
			layoutPageTemplateCollectionId);
	}

	@Override
	public LayoutPageTemplateCollection fetchLayoutPageTemplateCollection(
		long groupId, String layoutPageTemplateCollectionKey) {

		return layoutPageTemplateCollectionPersistence.fetchByG_LPTCK(
			groupId, layoutPageTemplateCollectionKey);
	}

	@Override
	public List<LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
		long groupId, int start, int end) {

		return layoutPageTemplateCollectionPersistence.findByGroupId(
			groupId, start, end);
	}

	@Override
	public List<LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
		long groupId, int start, int end,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator) {

		return layoutPageTemplateCollectionPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
		long groupId, String name, int start, int end,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator) {

		if (Validator.isNull(name)) {
			return layoutPageTemplateCollectionPersistence.findByGroupId(
				groupId, start, end, orderByComparator);
		}

		return layoutPageTemplateCollectionPersistence.findByG_LikeN(
			groupId, _customSQL.keywords(name, false, WildcardMode.SURROUND)[0],
			start, end, orderByComparator);
	}

	@Override
	public int getLayoutPageTemplateCollectionsCount(long groupId) {
		return layoutPageTemplateCollectionPersistence.countByGroupId(groupId);
	}

	@Override
	public int getLayoutPageTemplateCollectionsCount(
		long groupId, String name) {

		if (Validator.isNull(name)) {
			return layoutPageTemplateCollectionPersistence.countByGroupId(
				groupId);
		}

		return layoutPageTemplateCollectionPersistence.countByG_LikeN(
			groupId,
			_customSQL.keywords(name, false, WildcardMode.SURROUND)[0]);
	}

	@Override
	public LayoutPageTemplateCollection updateLayoutPageTemplateCollection(
			long layoutPageTemplateCollectionId, String name,
			String description)
		throws PortalException {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			layoutPageTemplateCollectionPersistence.findByPrimaryKey(
				layoutPageTemplateCollectionId);

		if (!Objects.equals(layoutPageTemplateCollection.getName(), name)) {
			validate(layoutPageTemplateCollection.getGroupId(), name);
		}

		layoutPageTemplateCollection.setModifiedDate(new Date());
		layoutPageTemplateCollection.setName(name);
		layoutPageTemplateCollection.setDescription(description);

		return layoutPageTemplateCollectionPersistence.update(
			layoutPageTemplateCollection);
	}

	protected void validate(long groupId, String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new LayoutPageTemplateCollectionNameException(
				"Name must not be null for group " + groupId);
		}

		int nameMaxLength = ModelHintsUtil.getMaxLength(
			LayoutPageTemplateEntry.class.getName(), "name");

		if (name.length() > nameMaxLength) {
			throw new LayoutPageTemplateCollectionNameException(
				"Maximum length of name exceeded");
		}

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			layoutPageTemplateCollectionPersistence.fetchByG_N(groupId, name);

		if (layoutPageTemplateCollection != null) {
			throw new DuplicateLayoutPageTemplateCollectionException(name);
		}
	}

	private String _generateLayoutPageTemplateCollectionKey(
		long groupId, String name) {

		String layoutPageTemplateCollectionKey = StringUtil.replace(
			StringUtil.toLowerCase(name.trim()),
			new char[] {CharPool.FORWARD_SLASH, CharPool.SPACE},
			new char[] {CharPool.DASH, CharPool.DASH});

		String curLayoutPageTemplateCollectionKey =
			layoutPageTemplateCollectionKey;

		int count = 0;

		while (true) {
			LayoutPageTemplateCollection layoutPageTemplateCollection =
				layoutPageTemplateCollectionPersistence.fetchByG_LPTCK(
					groupId, curLayoutPageTemplateCollectionKey);

			if (layoutPageTemplateCollection == null) {
				return curLayoutPageTemplateCollectionKey;
			}

			curLayoutPageTemplateCollectionKey =
				curLayoutPageTemplateCollectionKey + CharPool.DASH + count++;
		}
	}

	@Reference
	private CustomSQL _customSQL;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}