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

import com.liferay.dynamic.data.mapping.model.DDMStructureLink;
import com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.exception.DuplicateLayoutPageTemplateEntryException;
import com.liferay.layout.page.template.exception.LayoutPageTemplateEntryNameException;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.base.LayoutPageTemplateEntryLocalServiceBaseImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Jürgen Kappler
 */
public class LayoutPageTemplateEntryLocalServiceImpl
	extends LayoutPageTemplateEntryLocalServiceBaseImpl {

	@Override
	public LayoutPageTemplateEntry addGlobalLayoutPageTemplateEntry(
			LayoutPrototype layoutPrototype)
		throws PortalException {

		Company company = _companyLocalService.getCompany(
			layoutPrototype.getCompanyId());

		return addLayoutPageTemplateEntry(
			company.getGroupId(), layoutPrototype);
	}

	@Override
	public LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			LayoutPrototype layoutPrototype)
		throws PortalException {

		Company company = _companyLocalService.getCompany(
			layoutPrototype.getCompanyId());

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		long groupId = company.getGroupId();

		if (serviceContext != null) {
			long scopeGroupId = serviceContext.getScopeGroupId();

			if (scopeGroupId != 0) {
				groupId = scopeGroupId;
			}
		}

		return addLayoutPageTemplateEntry(groupId, layoutPrototype);
	}

	@Override
	public LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long userId, long groupId, long layoutPageTemplateCollectionId,
			long classNameId, long classTypeId, String name, int type,
			boolean defaultTemplate, long layoutPrototypeId,
			long previewFileEntryId, int status, ServiceContext serviceContext)
		throws PortalException {

		return addLayoutPageTemplateEntry(
			userId, groupId, layoutPageTemplateCollectionId, classNameId,
			classTypeId, name, type, defaultTemplate, 0, 0, 0, status,
			serviceContext);
	}

	@Override
	public LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long userId, long groupId, long layoutPageTemplateCollectionId,
			long classNameId, long classTypeId, String name, int type,
			boolean defaultTemplate, long layoutPrototypeId,
			long previewFileEntryId, long plid, int status,
			ServiceContext serviceContext)
		throws PortalException {

		// Layout page template entry

		User user = userLocalService.getUser(userId);

		validate(groupId, name);

		long layoutPageTemplateEntryId = counterLocalService.increment();

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryPersistence.create(
				layoutPageTemplateEntryId);

		layoutPageTemplateEntry.setUuid(serviceContext.getUuid());
		layoutPageTemplateEntry.setGroupId(groupId);
		layoutPageTemplateEntry.setCompanyId(user.getCompanyId());
		layoutPageTemplateEntry.setUserId(user.getUserId());
		layoutPageTemplateEntry.setUserName(user.getFullName());
		layoutPageTemplateEntry.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		layoutPageTemplateEntry.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		layoutPageTemplateEntry.setLayoutPageTemplateCollectionId(
			layoutPageTemplateCollectionId);
		layoutPageTemplateEntry.setClassNameId(classNameId);
		layoutPageTemplateEntry.setClassTypeId(classTypeId);
		layoutPageTemplateEntry.setName(name);
		layoutPageTemplateEntry.setType(type);
		layoutPageTemplateEntry.setPreviewFileEntryId(previewFileEntryId);
		layoutPageTemplateEntry.setDefaultTemplate(defaultTemplate);
		layoutPageTemplateEntry.setLayoutPrototypeId(layoutPrototypeId);

		if (plid == 0) {
			Layout layout = _addLayout(
				userId, groupId, name, type, serviceContext);

			if (layout != null) {
				plid = layout.getPlid();
			}
		}

		layoutPageTemplateEntry.setPlid(plid);

		layoutPageTemplateEntry.setStatus(status);
		layoutPageTemplateEntry.setStatusByUserId(userId);
		layoutPageTemplateEntry.setStatusByUserName(user.getScreenName());
		layoutPageTemplateEntry.setStatusDate(new Date());

		layoutPageTemplateEntryPersistence.update(layoutPageTemplateEntry);

		// Resources

		resourceLocalService.addResources(
			layoutPageTemplateEntry.getCompanyId(),
			layoutPageTemplateEntry.getGroupId(),
			layoutPageTemplateEntry.getUserId(),
			LayoutPageTemplateEntry.class.getName(),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(), false, true,
			true);

		return layoutPageTemplateEntry;
	}

	@Override
	public LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long userId, long groupId, long layoutPageTemplateCollectionId,
			String name, int type, int status, ServiceContext serviceContext)
		throws PortalException {

		return addLayoutPageTemplateEntry(
			userId, groupId, layoutPageTemplateCollectionId, name, type, 0,
			status, serviceContext);
	}

	@Override
	public LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long userId, long groupId, long layoutPageTemplateCollectionId,
			String name, int type, long layoutPrototypeId, int status,
			ServiceContext serviceContext)
		throws PortalException {

		return addLayoutPageTemplateEntry(
			userId, groupId, layoutPageTemplateCollectionId, name, type,
			layoutPrototypeId, 0, status, serviceContext);
	}

	@Override
	public LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long userId, long groupId, long layoutPageTemplateCollectionId,
			String name, int type, long layoutPrototypeId,
			long previewFileEntryId, int status, ServiceContext serviceContext)
		throws PortalException {

		return addLayoutPageTemplateEntry(
			userId, groupId, layoutPageTemplateCollectionId, 0, 0, name, type,
			false, layoutPrototypeId, previewFileEntryId, status,
			serviceContext);
	}

	@Override
	public LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long userId, long groupId, long layoutPageTemplateCollectionId,
			String name, int type, ServiceContext serviceContext)
		throws PortalException {

		return addLayoutPageTemplateEntry(
			userId, groupId, layoutPageTemplateCollectionId, name, type,
			WorkflowConstants.STATUS_DRAFT, serviceContext);
	}

	@Override
	public LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long userId, long groupId, long layoutPageTemplateCollectionId,
			String name, ServiceContext serviceContext)
		throws PortalException {

		return addLayoutPageTemplateEntry(
			userId, groupId, layoutPageTemplateCollectionId, name,
			LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
			WorkflowConstants.STATUS_DRAFT, serviceContext);
	}

	@Override
	public LayoutPageTemplateEntry deleteLayoutPageTemplateEntry(
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws PortalException {

		// Layout page template

		layoutPageTemplateEntryPersistence.remove(layoutPageTemplateEntry);

		// Resources

		resourceLocalService.deleteResource(
			layoutPageTemplateEntry.getCompanyId(),
			LayoutPageTemplateEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId());

		// Layout prototype

		long layoutPrototypeId = layoutPageTemplateEntry.getLayoutPrototypeId();

		if (layoutPrototypeId > 0) {
			LayoutPrototype layoutPrototype =
				_layoutPrototypeLocalService.fetchLayoutPrototype(
					layoutPrototypeId);

			if (layoutPrototype != null) {
				_layoutPrototypeLocalService.deleteLayoutPrototype(
					layoutPrototypeId);
			}
		}

		// Dynamic data mapping structure link

		if (Objects.equals(
				layoutPageTemplateEntry.getType(),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE) &&
			(layoutPageTemplateEntry.getClassTypeId() > 0)) {

			DDMStructureLink ddmStructureLink =
				_ddmStructureLinkLocalService.getUniqueStructureLink(
					classNameLocalService.getClassNameId(
						LayoutPageTemplateEntry.class),
					layoutPageTemplateEntry.getLayoutPageTemplateEntryId());

			_ddmStructureLinkLocalService.deleteDDMStructureLink(
				ddmStructureLink);
		}

		// Fragment entry instance links

		_fragmentEntryLinkLocalService.
			deleteLayoutPageTemplateEntryFragmentEntryLinks(
				layoutPageTemplateEntry.getGroupId(),
				classNameLocalService.getClassNameId(
					LayoutPageTemplateEntry.class.getName()),
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId());

		return layoutPageTemplateEntry;
	}

	@Override
	public LayoutPageTemplateEntry deleteLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId)
		throws PortalException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			getLayoutPageTemplateEntry(layoutPageTemplateEntryId);

		return deleteLayoutPageTemplateEntry(layoutPageTemplateEntry);
	}

	@Override
	public LayoutPageTemplateEntry fetchDefaultLayoutPageTemplateEntry(
		long groupId, long classNameId, long classTypeId) {

		return layoutPageTemplateEntryPersistence.fetchByG_C_C_D_First(
			groupId, classNameId, classTypeId, true, null);
	}

	@Override
	public LayoutPageTemplateEntry fetchFirstLayoutPageTemplateEntry(
		long layoutPrototypeId) {

		return layoutPageTemplateEntryPersistence.
			fetchByLayoutPrototypeId_First(layoutPrototypeId, null);
	}

	@Override
	public LayoutPageTemplateEntry fetchLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId) {

		return layoutPageTemplateEntryPersistence.fetchByPrimaryKey(
			layoutPageTemplateEntryId);
	}

	@Override
	public LayoutPageTemplateEntry fetchLayoutPageTemplateEntry(
		long groupId, String name) {

		return layoutPageTemplateEntryPersistence.fetchByG_N(groupId, name);
	}

	@Override
	public LayoutPageTemplateEntry fetchLayoutPageTemplateEntryByPlid(
		long plid) {

		return layoutPageTemplateEntryPersistence.fetchByPlid(plid);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId) {

		return getLayoutPageTemplateEntries(
			groupId, layoutPageTemplateCollectionId,
			WorkflowConstants.STATUS_ANY);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, int status) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return layoutPageTemplateEntryPersistence.findByG_L(
				groupId, layoutPageTemplateCollectionId);
		}

		return layoutPageTemplateEntryPersistence.findByG_L_S(
			groupId, layoutPageTemplateCollectionId, status);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, int start, int end) {

		return getLayoutPageTemplateEntries(
			groupId, layoutPageTemplateCollectionId,
			WorkflowConstants.STATUS_ANY, start, end);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, int status,
		int start, int end) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return layoutPageTemplateEntryPersistence.findByG_L(
				groupId, layoutPageTemplateCollectionId, start, end);
		}

		return layoutPageTemplateEntryPersistence.findByG_L_S(
			groupId, layoutPageTemplateCollectionId, status, start, end);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, int status,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return layoutPageTemplateEntryPersistence.findByG_L(
				groupId, layoutPageTemplateCollectionId, start, end,
				orderByComparator);
		}

		return layoutPageTemplateEntryPersistence.findByG_L_S(
			groupId, layoutPageTemplateCollectionId, status, start, end,
			orderByComparator);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return getLayoutPageTemplateEntries(
			groupId, layoutPageTemplateCollectionId,
			WorkflowConstants.STATUS_ANY, start, end, orderByComparator);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int status, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		if (status == WorkflowConstants.STATUS_ANY) {
			if (Validator.isNull(name)) {
				return layoutPageTemplateEntryPersistence.findByG_L(
					groupId, layoutPageTemplateCollectionId, start, end,
					orderByComparator);
			}

			return layoutPageTemplateEntryPersistence.findByG_L_LikeN(
				groupId, layoutPageTemplateCollectionId, name, start, end,
				orderByComparator);
		}

		if (Validator.isNull(name)) {
			return layoutPageTemplateEntryPersistence.findByG_L_S(
				groupId, layoutPageTemplateCollectionId, status, start, end,
				orderByComparator);
		}

		return layoutPageTemplateEntryPersistence.findByG_L_LikeN_S(
			groupId, layoutPageTemplateCollectionId, name, status, start, end,
			orderByComparator);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return getLayoutPageTemplateEntries(
			groupId, layoutPageTemplateCollectionId, name,
			WorkflowConstants.STATUS_ANY, start, end, orderByComparator);
	}

	@Override
	public LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId, boolean defaultTemplate) {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			fetchLayoutPageTemplateEntry(layoutPageTemplateEntryId);

		if (layoutPageTemplateEntry == null) {
			return null;
		}

		LayoutPageTemplateEntry defaultLayoutPageTemplateEntry =
			layoutPageTemplateEntryPersistence.fetchByG_C_C_D_First(
				layoutPageTemplateEntry.getGroupId(),
				layoutPageTemplateEntry.getClassNameId(),
				layoutPageTemplateEntry.getClassTypeId(), true, null);

		if (defaultTemplate && (defaultLayoutPageTemplateEntry != null) &&
			(defaultLayoutPageTemplateEntry.getLayoutPageTemplateEntryId() !=
				layoutPageTemplateEntryId)) {

			layoutPageTemplateEntry.setModifiedDate(new Date());
			defaultLayoutPageTemplateEntry.setDefaultTemplate(false);

			layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
				defaultLayoutPageTemplateEntry);
		}

		layoutPageTemplateEntry.setModifiedDate(new Date());
		layoutPageTemplateEntry.setDefaultTemplate(defaultTemplate);

		layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
			layoutPageTemplateEntry);

		return layoutPageTemplateEntry;
	}

	@Override
	public LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId, long previewFileEntryId)
		throws PortalException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryPersistence.findByPrimaryKey(
				layoutPageTemplateEntryId);

		layoutPageTemplateEntry.setModifiedDate(new Date());
		layoutPageTemplateEntry.setPreviewFileEntryId(previewFileEntryId);

		return layoutPageTemplateEntryLocalService.
			updateLayoutPageTemplateEntry(layoutPageTemplateEntry);
	}

	@Override
	public LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId, long classNameId, long classTypeId)
		throws PortalException {

		List<DDMStructureLink> ddmStructureLinks =
			_ddmStructureLinkLocalService.getStructureLinks(
				classNameLocalService.getClassNameId(
					LayoutPageTemplateEntry.class),
				layoutPageTemplateEntryId);

		if (ListUtil.isNotEmpty(ddmStructureLinks)) {
			DDMStructureLink ddmStructureLink = ddmStructureLinks.get(0);

			_ddmStructureLinkLocalService.deleteDDMStructureLink(
				ddmStructureLink);
		}

		// Layout page template entry

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryPersistence.findByPrimaryKey(
				layoutPageTemplateEntryId);

		layoutPageTemplateEntry.setModifiedDate(new Date());
		layoutPageTemplateEntry.setClassNameId(classNameId);
		layoutPageTemplateEntry.setClassTypeId(classTypeId);

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Layout layout = null;

		if (layoutPageTemplateEntry.getPlid() == 0) {
			layout = _addLayout(
				layoutPageTemplateEntry.getUserId(),
				layoutPageTemplateEntry.getGroupId(),
				layoutPageTemplateEntry.getName(),
				layoutPageTemplateEntry.getType(), serviceContext);
		}

		if (layout != null) {
			layoutPageTemplateEntry.setPlid(layout.getPlid());
		}

		layoutPageTemplateEntryPersistence.update(layoutPageTemplateEntry);

		// Dynamic data mapping structure link

		_ddmStructureLinkLocalService.addStructureLink(
			classNameLocalService.getClassNameId(LayoutPageTemplateEntry.class),
			layoutPageTemplateEntryId, classTypeId);

		return layoutPageTemplateEntry;
	}

	@Override
	public LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
			long userId, long layoutPageTemplateEntryId, String name,
			int status)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryPersistence.findByPrimaryKey(
				layoutPageTemplateEntryId);

		if (!Objects.equals(layoutPageTemplateEntry.getName(), name)) {
			validate(layoutPageTemplateEntry.getGroupId(), name);
		}

		layoutPageTemplateEntry.setModifiedDate(new Date());
		layoutPageTemplateEntry.setName(name);
		layoutPageTemplateEntry.setStatus(status);
		layoutPageTemplateEntry.setStatusByUserId(userId);
		layoutPageTemplateEntry.setStatusByUserName(user.getScreenName());
		layoutPageTemplateEntry.setStatusDate(new Date());

		return layoutPageTemplateEntryLocalService.
			updateLayoutPageTemplateEntry(layoutPageTemplateEntry);
	}

	@Override
	public LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId, String name)
		throws PortalException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryPersistence.findByPrimaryKey(
				layoutPageTemplateEntryId);

		if (Objects.equals(layoutPageTemplateEntry.getName(), name)) {
			return layoutPageTemplateEntry;
		}

		validate(layoutPageTemplateEntry.getGroupId(), name);

		layoutPageTemplateEntry.setModifiedDate(new Date());
		layoutPageTemplateEntry.setName(name);

		layoutPageTemplateEntry =
			layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
				layoutPageTemplateEntry);

		Map<Locale, String> titleMap = Collections.singletonMap(
			LocaleUtil.getSiteDefault(), name);

		Layout draftLayout = layoutLocalService.fetchLayout(
			classNameLocalService.getClassNameId(Layout.class),
			layoutPageTemplateEntry.getPlid());

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();
		}

		serviceContext.setAttribute(
			"layout.instanceable.allowed", Boolean.TRUE);

		layoutLocalService.updateLayout(
			draftLayout.getGroupId(), draftLayout.isPrivateLayout(),
			draftLayout.getLayoutId(), draftLayout.getParentLayoutId(),
			titleMap, titleMap, draftLayout.getDescriptionMap(),
			draftLayout.getKeywordsMap(), draftLayout.getRobotsMap(),
			draftLayout.getType(), draftLayout.isHidden(),
			draftLayout.getFriendlyURLMap(), draftLayout.getIconImage(), null,
			serviceContext);

		return layoutPageTemplateEntry;
	}

	@Override
	public LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId, String name,
			long[] fragmentEntryIds, String editableValues,
			ServiceContext serviceContext)
		throws PortalException {

		// Layout page template entry

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryPersistence.findByPrimaryKey(
				layoutPageTemplateEntryId);

		if (!Objects.equals(layoutPageTemplateEntry.getName(), name)) {
			validate(layoutPageTemplateEntry.getGroupId(), name);
		}

		layoutPageTemplateEntry.setModifiedDate(new Date());
		layoutPageTemplateEntry.setName(name);

		layoutPageTemplateEntryPersistence.update(layoutPageTemplateEntry);

		// Fragment entry instance links

		_fragmentEntryLinkLocalService.updateFragmentEntryLinks(
			serviceContext.getUserId(), layoutPageTemplateEntry.getGroupId(),
			classNameLocalService.getClassNameId(Layout.class.getName()),
			layoutPageTemplateEntry.getPlid(), fragmentEntryIds, editableValues,
			serviceContext);

		return layoutPageTemplateEntry;
	}

	@Override
	public LayoutPageTemplateEntry updateStatus(
			long userId, long layoutPageTemplateEntryId, int status)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryPersistence.findByPrimaryKey(
				layoutPageTemplateEntryId);

		layoutPageTemplateEntry.setModifiedDate(new Date());
		layoutPageTemplateEntry.setStatus(status);
		layoutPageTemplateEntry.setStatusByUserId(userId);
		layoutPageTemplateEntry.setStatusByUserName(user.getScreenName());
		layoutPageTemplateEntry.setStatusDate(new Date());

		return layoutPageTemplateEntryLocalService.
			updateLayoutPageTemplateEntry(layoutPageTemplateEntry);
	}

	protected LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long groupId, LayoutPrototype layoutPrototype)
		throws PortalException {

		String nameXML = layoutPrototype.getName();

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			nameXML);

		Locale defaultLocale = LocaleUtil.fromLanguageId(
			LocalizationUtil.getDefaultLanguageId(nameXML));

		Layout layout = layoutPrototype.getLayout();

		int status = WorkflowConstants.STATUS_APPROVED;

		if (!layoutPrototype.isActive()) {
			status = WorkflowConstants.STATUS_INACTIVE;
		}

		return addLayoutPageTemplateEntry(
			layoutPrototype.getUserId(), groupId, 0, 0, 0,
			nameMap.get(defaultLocale),
			LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE, false,
			layoutPrototype.getLayoutPrototypeId(), 0, layout.getPlid(), status,
			new ServiceContext());
	}

	protected void validate(long groupId, String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new LayoutPageTemplateEntryNameException(
				"Name must not be null for group " + groupId);
		}

		for (char c : _BLACKLIST_CHAR) {
			if (name.indexOf(c) >= 0) {
				throw new LayoutPageTemplateEntryNameException(
					"Invalid character in name");
			}
		}

		int nameMaxLength = ModelHintsUtil.getMaxLength(
			LayoutPageTemplateEntry.class.getName(), "name");

		if (name.length() > nameMaxLength) {
			throw new LayoutPageTemplateEntryNameException(
				"Maximum length of name exceeded");
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryPersistence.fetchByG_N(groupId, name);

		if (layoutPageTemplateEntry != null) {
			throw new DuplicateLayoutPageTemplateEntryException(
				StringBundler.concat(
					"Duplicate layout page template for group ", groupId,
					" with name ", name));
		}
	}

	private Layout _addLayout(
			long userId, long groupId, String name, int type,
			ServiceContext serviceContext)
		throws PortalException {

		Map<Locale, String> titleMap = Collections.singletonMap(
			LocaleUtil.getSiteDefault(), name);

		String layoutType = LayoutConstants.TYPE_ASSET_DISPLAY;

		if (type == LayoutPageTemplateEntryTypeConstants.TYPE_BASIC) {
			layoutType = LayoutConstants.TYPE_CONTENT;
		}

		serviceContext.setAttribute(
			"layout.instanceable.allowed", Boolean.TRUE);

		Layout layout = layoutLocalService.addLayout(
			userId, groupId, false, 0, titleMap, titleMap, null, null, null,
			layoutType, StringPool.BLANK, true, true, new HashMap<>(),
			serviceContext);

		serviceContext.setModifiedDate(layout.getModifiedDate());

		layoutLocalService.addLayout(
			userId, groupId, false, layout.getParentLayoutId(),
			classNameLocalService.getClassNameId(Layout.class),
			layout.getPlid(), layout.getNameMap(), titleMap,
			layout.getDescriptionMap(), layout.getKeywordsMap(),
			layout.getRobotsMap(), layoutType, StringPool.BLANK, true, true,
			Collections.emptyMap(), serviceContext);

		return layout;
	}

	private static final char[] _BLACKLIST_CHAR = {
		';', '/', '?', ':', '@', '=', '&', '\"', '<', '>', '#', '%', '{', '}',
		'|', '\\', '^', '~', '[', ']', '`'
	};

	@ServiceReference(type = CompanyLocalService.class)
	private CompanyLocalService _companyLocalService;

	@ServiceReference(type = DDMStructureLinkLocalService.class)
	private DDMStructureLinkLocalService _ddmStructureLinkLocalService;

	@ServiceReference(type = FragmentEntryLinkLocalService.class)
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@ServiceReference(type = LayoutPrototypeLocalService.class)
	private LayoutPrototypeLocalService _layoutPrototypeLocalService;

}