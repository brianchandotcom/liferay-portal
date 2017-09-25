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

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.layout.page.template.exception.DuplicateLayoutPageTemplateException;
import com.liferay.layout.page.template.exception.LayoutPageTemplateNameException;
import com.liferay.layout.page.template.model.LayoutPageTemplate;
import com.liferay.layout.page.template.service.LayoutPageTemplateFragmentService;
import com.liferay.layout.page.template.service.base.LayoutPageTemplateLocalServiceBaseImpl;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Jürgen Kappler
 */
public class LayoutPageTemplateLocalServiceImpl
	extends LayoutPageTemplateLocalServiceBaseImpl {

	@Override
	public LayoutPageTemplate addLayoutPageTemplate(
			long groupId, long userId, long layoutPageTemplateFolderId,
			String name,
			Map<Integer, FragmentEntry> layoutPageTemplateFragments,
			ServiceContext serviceContext)
		throws PortalException {

		// Layout Page Template

		User user = userLocalService.getUser(userId);

		validate(groupId, name);

		long layoutPageTemplateId = counterLocalService.increment();

		LayoutPageTemplate layoutPageTemplate =
			layoutPageTemplatePersistence.create(layoutPageTemplateId);

		layoutPageTemplate.setGroupId(groupId);
		layoutPageTemplate.setCompanyId(user.getCompanyId());
		layoutPageTemplate.setUserId(user.getUserId());
		layoutPageTemplate.setUserName(user.getFullName());
		layoutPageTemplate.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		layoutPageTemplate.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		layoutPageTemplate.setLayoutPageTemplateFolderId(
			layoutPageTemplateFolderId);
		layoutPageTemplate.setName(name);

		layoutPageTemplatePersistence.update(layoutPageTemplate);

		// Resources

		resourceLocalService.addModelResources(
			layoutPageTemplate, serviceContext);

		// Layout Page Template Fragments

		if (layoutPageTemplateFragments != null) {
			for (int key : layoutPageTemplateFragments.keySet()) {
				FragmentEntry fragmentEntry = layoutPageTemplateFragments.get(
					key);

				_layoutPageTemplateFragmentService.
					addLayoutPageTemplateFragment(
						groupId, layoutPageTemplateId,
						fragmentEntry.getFragmentEntryId(), key,
						serviceContext);
			}
		}

		return layoutPageTemplate;
	}

	@Override
	public LayoutPageTemplate deleteLayoutPageTemplate(
			LayoutPageTemplate layoutPageTemplate)
		throws PortalException {

		// Layout Page Template

		layoutPageTemplatePersistence.remove(layoutPageTemplate);

		// Resources

		resourceLocalService.deleteResource(
			layoutPageTemplate.getCompanyId(),
			LayoutPageTemplate.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			layoutPageTemplate.getLayoutPageTemplateId());

		return layoutPageTemplate;
	}

	@Override
	public LayoutPageTemplate deleteLayoutPageTemplate(
			long layoutPageTemplateId)
		throws PortalException {

		LayoutPageTemplate layoutPageTemplate = getLayoutPageTemplate(
			layoutPageTemplateId);

		return deleteLayoutPageTemplate(layoutPageTemplate);
	}

	@Override
	public LayoutPageTemplate fetchLayoutPageTemplate(
		long layoutPageTemplateId) {

		return layoutPageTemplatePersistence.fetchByPrimaryKey(
			layoutPageTemplateId);
	}

	@Override
	public List<LayoutPageTemplate> fetchLayoutPageTemplates(
		long layoutPageTemplateFolderId) {

		return layoutPageTemplatePersistence.findByLayoutPageTemplateFolderId(
			layoutPageTemplateFolderId);
	}

	@Override
	public List<LayoutPageTemplate> getLayoutPageTemplates(
			long layoutPageTemplateFolderId, int start, int end)
		throws PortalException {

		return layoutPageTemplatePersistence.findByLayoutPageTemplateFolderId(
			layoutPageTemplateFolderId, start, end);
	}

	@Override
	public List<LayoutPageTemplate> getLayoutPageTemplates(
			long groupId, long layoutPageTemplateFolderId, int start, int end,
			OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws PortalException {

		return layoutPageTemplatePersistence.findByG_LPTFI(
			groupId, layoutPageTemplateFolderId, start, end, orderByComparator);
	}

	@Override
	public List<LayoutPageTemplate> getLayoutPageTemplates(
		long groupId, long layoutPageTemplateFolderId, String name, int start,
		int end, OrderByComparator<LayoutPageTemplate> orderByComparator) {

		if (Validator.isNull(name)) {
			return layoutPageTemplatePersistence.findByG_LPTFI(
				groupId, layoutPageTemplateFolderId, start, end,
				orderByComparator);
		}

		return layoutPageTemplatePersistence.findByG_LPTFI_LikeN(
			groupId, layoutPageTemplateFolderId, name, start, end,
			orderByComparator);
	}

	@Override
	public LayoutPageTemplate updateLayoutPageTemplate(
			long layoutPageTemplateId, String name,
			Map<Integer, FragmentEntry> layoutPageTemplateFragments,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutPageTemplate layoutPageTemplate =
			layoutPageTemplatePersistence.findByPrimaryKey(
				layoutPageTemplateId);

		if (!Objects.equals(layoutPageTemplate.getName(), name)) {
			validate(layoutPageTemplate.getGroupId(), name);
		}

		layoutPageTemplate.setModifiedDate(new Date());
		layoutPageTemplate.setName(name);

		layoutPageTemplatePersistence.update(layoutPageTemplate);

		// Layout Page Template Fragments

		_layoutPageTemplateFragmentService.deleteByLayoutPageTemplate(
			layoutPageTemplate.getGroupId(), layoutPageTemplateId);

		if (layoutPageTemplateFragments != null) {
			for (int key : layoutPageTemplateFragments.keySet()) {
				FragmentEntry fragmentEntry = layoutPageTemplateFragments.get(
					key);

				_layoutPageTemplateFragmentService.
					addLayoutPageTemplateFragment(
						layoutPageTemplate.getGroupId(), layoutPageTemplateId,
						fragmentEntry.getFragmentEntryId(), key,
						serviceContext);
			}
		}

		return layoutPageTemplate;
	}

	protected void validate(long groupId, String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new LayoutPageTemplateNameException(
				"Name must not be null for group " + groupId);
		}

		LayoutPageTemplate layoutPageTemplate =
			layoutPageTemplatePersistence.fetchByG_N(groupId, name);

		if (layoutPageTemplate != null) {
			throw new DuplicateLayoutPageTemplateException(name);
		}
	}

	@BeanReference(type = LayoutPageTemplateFragmentService.class)
	private LayoutPageTemplateFragmentService
		_layoutPageTemplateFragmentService;

}