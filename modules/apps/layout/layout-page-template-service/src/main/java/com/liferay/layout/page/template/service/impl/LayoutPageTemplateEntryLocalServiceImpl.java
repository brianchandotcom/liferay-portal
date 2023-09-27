/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.service.impl;

import com.liferay.asset.kernel.NoSuchClassTypeException;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.exception.LayoutPageTemplateEntryNameException;
import com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException;
import com.liferay.layout.page.template.internal.validator.LayoutPageTemplateEntryValidator;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.base.LayoutPageTemplateEntryLocalServiceBaseImpl;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.NoSuchClassNameException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.staging.StagingGroupHelper;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = "model.class.name=com.liferay.layout.page.template.model.LayoutPageTemplateEntry",
	service = AopService.class
)
public class LayoutPageTemplateEntryLocalServiceImpl
	extends LayoutPageTemplateEntryLocalServiceBaseImpl {

	@Override
	public LayoutPageTemplateEntry addGlobalLayoutPageTemplateEntry(
			LayoutPrototype layoutPrototype)
		throws PortalException {

		Company company = _companyLocalService.getCompany(
			layoutPrototype.getCompanyId());

		return _addLayoutPageTemplateEntry(
			company.getGroupId(), layoutPrototype);
	}

	@Override
	public LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			LayoutPrototype layoutPrototype)
		throws PortalException {

		Company company = _companyLocalService.getCompany(
			layoutPrototype.getCompanyId());

		return _addLayoutPageTemplateEntry(
			company.getGroupId(), layoutPrototype);
	}

	@Override
	public LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long userId, long groupId, long layoutPageTemplateCollectionId,
			long classNameId, long classTypeId, String name, int type,
			long previewFileEntryId, boolean defaultTemplate,
			long layoutPrototypeId, long plid, long masterLayoutPlid,
			int status, ServiceContext serviceContext)
		throws PortalException {

		// Layout page template entry

		User user = _userLocalService.getUser(userId);

		_validate(groupId, name, type);

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
		layoutPageTemplateEntry.setLayoutPageTemplateEntryKey(
			_generateLayoutPageTemplateEntryKey(groupId, name));
		layoutPageTemplateEntry.setClassNameId(classNameId);
		layoutPageTemplateEntry.setClassTypeId(classTypeId);
		layoutPageTemplateEntry.setName(name);
		layoutPageTemplateEntry.setType(type);
		layoutPageTemplateEntry.setPreviewFileEntryId(previewFileEntryId);
		layoutPageTemplateEntry.setDefaultTemplate(defaultTemplate);

		layoutPageTemplateEntry = layoutPageTemplateEntryPersistence.update(
			layoutPageTemplateEntry);

		if ((type == LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE) &&
			(layoutPrototypeId == 0)) {

			serviceContext.setAttribute(
				"layoutPageTemplateEntryId", layoutPageTemplateEntryId);

			LayoutPrototype layoutPrototype =
				_layoutPrototypeLocalService.addLayoutPrototype(
					userId, user.getCompanyId(),
					Collections.singletonMap(
						LocaleUtil.getMostRelevantLocale(), name),
					Collections.emptyMap(), true, serviceContext);

			layoutPrototypeId = layoutPrototype.getLayoutPrototypeId();

			Layout layout = layoutPrototype.getLayout();

			plid = layout.getPlid();
		}

		layoutPageTemplateEntry.setLayoutPrototypeId(layoutPrototypeId);

		if (plid == 0) {
			Layout layout = _addLayout(
				userId, groupId, name, type, masterLayoutPlid, status,
				serviceContext);

			if (layout != null) {
				plid = layout.getPlid();
			}
		}

		layoutPageTemplateEntry.setPlid(plid);

		layoutPageTemplateEntry.setStatus(status);
		layoutPageTemplateEntry.setStatusByUserId(userId);
		layoutPageTemplateEntry.setStatusByUserName(user.getScreenName());
		layoutPageTemplateEntry.setStatusDate(new Date());

		layoutPageTemplateEntry = layoutPageTemplateEntryPersistence.update(
			layoutPageTemplateEntry);

		// Resources

		_resourceLocalService.addResources(
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
			long classNameId, long classTypeId, String name, int type,
			long masterLayoutPlid, int status, ServiceContext serviceContext)
		throws PortalException {

		// Layout page template entry

		_validate(classNameId, classTypeId, groupId);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			addLayoutPageTemplateEntry(
				userId, groupId, layoutPageTemplateCollectionId, classNameId,
				classTypeId, name, type, 0, false, 0, 0, masterLayoutPlid,
				status, serviceContext);

		// Dynamic data mapping structure link

		_ddmStructureLinkLocalService.addStructureLink(
			_classNameLocalService.getClassNameId(
				LayoutPageTemplateEntry.class),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			classTypeId);

		return layoutPageTemplateEntry;
	}

	@Override
	public LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long userId, long groupId, long layoutPageTemplateCollectionId,
			String name, int type, long masterLayoutPlid, int status,
			ServiceContext serviceContext)
		throws PortalException {

		return addLayoutPageTemplateEntry(
			userId, groupId, layoutPageTemplateCollectionId, 0, 0, name, type,
			0, false, 0, 0, masterLayoutPlid, status, serviceContext);
	}

	@Override
	public LayoutPageTemplateEntry copyLayoutPageTemplateEntry(
			long userId, long groupId, long layoutPageTemplateCollectionId,
			long sourceLayoutPageTemplateEntryId, ServiceContext serviceContext)
		throws Exception {

		LayoutPageTemplateEntry sourceLayoutPageTemplateEntry =
			layoutPageTemplateEntryPersistence.findByPrimaryKey(
				sourceLayoutPageTemplateEntryId);

		String name = _getUniqueCopyName(
			groupId, sourceLayoutPageTemplateEntry.getName(),
			sourceLayoutPageTemplateEntry.getType(),
			serviceContext.getLocale());

		long masterLayoutPlid = 0;

		Layout layout = _layoutLocalService.fetchLayout(
			sourceLayoutPageTemplateEntry.getPlid());

		if (layout != null) {
			masterLayoutPlid = layout.getMasterLayoutPlid();
		}

		LayoutPageTemplateEntry targetLayoutPageTemplateEntry =
			addLayoutPageTemplateEntry(
				userId, groupId, layoutPageTemplateCollectionId,
				sourceLayoutPageTemplateEntry.getClassNameId(),
				sourceLayoutPageTemplateEntry.getClassTypeId(), name,
				sourceLayoutPageTemplateEntry.getType(), 0, false,
				sourceLayoutPageTemplateEntry.getLayoutPrototypeId(), 0,
				masterLayoutPlid, WorkflowConstants.STATUS_DRAFT,
				serviceContext);

		FileEntry targetPreviewFileEntry = _copyPreviewFileEntry(
			sourceLayoutPageTemplateEntry, targetLayoutPageTemplateEntry,
			serviceContext);

		if (targetPreviewFileEntry == null) {
			return targetLayoutPageTemplateEntry;
		}

		return layoutPageTemplateEntryLocalService.
			updateLayoutPageTemplateEntry(
				targetLayoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
				targetPreviewFileEntry.getFileEntryId());
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public LayoutPageTemplateEntry deleteLayoutPageTemplateEntry(
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws PortalException {

		// Layout page template

		layoutPageTemplateEntryPersistence.remove(layoutPageTemplateEntry);

		// Resources

		_resourceLocalService.deleteResource(
			layoutPageTemplateEntry.getCompanyId(),
			LayoutPageTemplateEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId());

		// Layout

		Layout layout = _layoutLocalService.fetchLayout(
			layoutPageTemplateEntry.getPlid());

		LayoutSet layoutSet = _layoutSetLocalService.fetchLayoutSet(
			layoutPageTemplateEntry.getGroupId(), false);

		if ((layout != null) && (layoutSet != null)) {
			_layoutLocalService.deleteLayout(layout);
		}

		// Layout prototype

		if (!_stagingGroupHelper.isLocalStagingGroup(
				layoutPageTemplateEntry.getGroupId())) {

			long layoutPrototypeId =
				layoutPageTemplateEntry.getLayoutPrototypeId();

			if (layoutPrototypeId > 0) {
				LayoutPrototype layoutPrototype =
					_layoutPrototypeLocalService.fetchLayoutPrototype(
						layoutPrototypeId);

				if (layoutPrototype != null) {
					_layoutPrototypeLocalService.deleteLayoutPrototype(
						layoutPrototypeId);
				}
			}
		}

		// Portlet file entry

		if (layoutPageTemplateEntry.getPreviewFileEntryId() > 0) {
			PortletFileRepositoryUtil.deletePortletFileEntry(
				layoutPageTemplateEntry.getPreviewFileEntryId());
		}

		// Dynamic data mapping structure link

		if (Objects.equals(
				layoutPageTemplateEntry.getType(),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE) &&
			(layoutPageTemplateEntry.getClassTypeId() > 0)) {

			_ddmStructureLinkLocalService.deleteStructureLinks(
				_classNameLocalService.getClassNameId(
					LayoutPageTemplateEntry.class),
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId());
		}

		return layoutPageTemplateEntry;
	}

	@Override
	public LayoutPageTemplateEntry deleteLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId)
		throws PortalException {

		return deleteLayoutPageTemplateEntry(
			getLayoutPageTemplateEntry(layoutPageTemplateEntryId));
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
		long groupId, String layoutPageTemplateEntryKey) {

		return layoutPageTemplateEntryPersistence.fetchByG_LPTEK(
			groupId, layoutPageTemplateEntryKey);
	}

	@Override
	public LayoutPageTemplateEntry fetchLayoutPageTemplateEntry(
		long groupId, String name, int type) {

		return layoutPageTemplateEntryPersistence.fetchByG_N_T(
			groupId, name, type);
	}

	@Override
	public LayoutPageTemplateEntry fetchLayoutPageTemplateEntryByPlid(
		long plid) {

		return layoutPageTemplateEntryPersistence.fetchByPlid(plid);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId) {

		return layoutPageTemplateEntryPersistence.findByGroupId(groupId);
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
	public List<LayoutPageTemplateEntry>
		getLayoutPageTemplateEntriesByLayoutPrototypeId(
			long layoutPrototypeId) {

		return layoutPageTemplateEntryPersistence.findByLayoutPrototypeId(
			layoutPrototypeId);
	}

	@Override
	public LayoutPageTemplateEntry getLayoutPageTemplateEntry(
			long groupId, String layoutPageTemplateEntryKey)
		throws NoSuchPageTemplateEntryException {

		return layoutPageTemplateEntryPersistence.findByG_LPTEK(
			groupId, layoutPageTemplateEntryKey);
	}

	@Override
	public String getUniqueLayoutPageTemplateEntryName(
		long groupId, String name, int type) {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryPersistence.fetchByG_N_T(
				groupId, name, type);

		if (layoutPageTemplateEntry == null) {
			return name;
		}

		int count = 1;

		while (true) {
			String newName = StringUtil.appendParentheticalSuffix(
				name, count++);

			layoutPageTemplateEntry =
				layoutPageTemplateEntryPersistence.fetchByG_N_T(
					groupId, newName, type);

			if (layoutPageTemplateEntry == null) {
				return newName;
			}
		}
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

		return layoutPageTemplateEntryLocalService.
			updateLayoutPageTemplateEntry(layoutPageTemplateEntry);
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

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryPersistence.findByPrimaryKey(
				layoutPageTemplateEntryId);

		layoutPageTemplateEntry.setModifiedDate(new Date());
		layoutPageTemplateEntry.setClassNameId(classNameId);
		layoutPageTemplateEntry.setClassTypeId(classTypeId);

		return layoutPageTemplateEntryLocalService.
			updateLayoutPageTemplateEntry(layoutPageTemplateEntry);
	}

	@Override
	public LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
			long userId, long layoutPageTemplateEntryId, String name,
			int status)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryPersistence.findByPrimaryKey(
				layoutPageTemplateEntryId);

		if (!Objects.equals(layoutPageTemplateEntry.getName(), name)) {
			_validate(
				layoutPageTemplateEntry.getGroupId(), name,
				layoutPageTemplateEntry.getType());
		}

		layoutPageTemplateEntry.setModifiedDate(new Date());
		layoutPageTemplateEntry.setLayoutPageTemplateEntryKey(
			_generateLayoutPageTemplateEntryKey(
				layoutPageTemplateEntry.getGroupId(), name));
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

		_validate(
			layoutPageTemplateEntry.getGroupId(), name,
			layoutPageTemplateEntry.getType());

		layoutPageTemplateEntry.setModifiedDate(new Date());
		layoutPageTemplateEntry.setLayoutPageTemplateEntryKey(
			_generateLayoutPageTemplateEntryKey(
				layoutPageTemplateEntry.getGroupId(), name));
		layoutPageTemplateEntry.setName(name);

		layoutPageTemplateEntry =
			layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
				layoutPageTemplateEntry);

		Map<Locale, String> titleMap = Collections.singletonMap(
			LocaleUtil.getSiteDefault(), name);

		Layout draftLayout = _layoutLocalService.fetchDraftLayout(
			layoutPageTemplateEntry.getPlid());

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();
		}

		serviceContext.setAttribute(
			"layout.instanceable.allowed", Boolean.TRUE);

		_layoutLocalService.updateLayout(
			draftLayout.getGroupId(), draftLayout.isPrivateLayout(),
			draftLayout.getLayoutId(), draftLayout.getParentLayoutId(),
			titleMap, titleMap, draftLayout.getDescriptionMap(),
			draftLayout.getKeywordsMap(), draftLayout.getRobotsMap(),
			draftLayout.getType(), draftLayout.isHidden(),
			draftLayout.getFriendlyURLMap(), draftLayout.getIconImage(), null,
			draftLayout.getStyleBookEntryId(),
			draftLayout.getFaviconFileEntryId(),
			draftLayout.getMasterLayoutPlid(), serviceContext);

		Layout layout = _layoutLocalService.getLayout(
			layoutPageTemplateEntry.getPlid());

		_layoutLocalService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getParentLayoutId(), titleMap, titleMap,
			layout.getDescriptionMap(), layout.getKeywordsMap(),
			layout.getRobotsMap(), layout.getType(), layout.isHidden(),
			layout.getFriendlyURLMap(), layout.getIconImage(), null,
			layout.getStyleBookEntryId(), layout.getFaviconFileEntryId(),
			layout.getMasterLayoutPlid(), serviceContext);

		return layoutPageTemplateEntry;
	}

	@Override
	public LayoutPageTemplateEntry updateStatus(
			long userId, long layoutPageTemplateEntryId, int status)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

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

	private Layout _addLayout(
			long userId, long groupId, String name, int type,
			long masterLayoutPlid, int status, ServiceContext serviceContext)
		throws PortalException {

		boolean privateLayout = false;
		String layoutType = LayoutConstants.TYPE_ASSET_DISPLAY;

		if ((type == LayoutPageTemplateEntryTypeConstants.TYPE_BASIC) ||
			(type == LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT)) {

			privateLayout = true;
			layoutType = LayoutConstants.TYPE_CONTENT;
		}

		Map<Locale, String> titleMap = Collections.singletonMap(
			LocaleUtil.getSiteDefault(), name);

		UnicodeProperties typeSettingsUnicodeProperties =
			new UnicodeProperties();

		if (status == WorkflowConstants.STATUS_APPROVED) {
			typeSettingsUnicodeProperties.put("published", "true");
		}

		if ((type == LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT) ||
			(masterLayoutPlid > 0)) {

			typeSettingsUnicodeProperties.setProperty(
				"lfr-theme:regular:show-footer", Boolean.FALSE.toString());
			typeSettingsUnicodeProperties.setProperty(
				"lfr-theme:regular:show-header", Boolean.FALSE.toString());
			typeSettingsUnicodeProperties.setProperty(
				"lfr-theme:regular:show-header-search",
				Boolean.FALSE.toString());
			typeSettingsUnicodeProperties.setProperty(
				"lfr-theme:regular:wrap-widget-page-content",
				Boolean.FALSE.toString());
		}

		String typeSettings = typeSettingsUnicodeProperties.toString();

		serviceContext.setAttribute(
			"layout.instanceable.allowed", Boolean.TRUE);
		serviceContext.setAttribute("layout.page.template.entry.type", type);

		Layout layout = _layoutLocalService.addLayout(
			userId, groupId, privateLayout, 0, 0, 0, titleMap, titleMap, null,
			null, null, layoutType, typeSettings, true, true, new HashMap<>(),
			masterLayoutPlid, serviceContext);

		Layout draftLayout = layout.fetchDraftLayout();

		if ((type == LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT) ||
			(masterLayoutPlid > 0)) {

			LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
				groupId, false);

			String themeId = layoutSet.getThemeId();

			String colorSchemeId = _getColorSchemeId(
				layout.getCompanyId(), themeId, StringPool.BLANK);

			_layoutLocalService.updateLookAndFeel(
				groupId, privateLayout, draftLayout.getLayoutId(), themeId,
				colorSchemeId, StringPool.BLANK);

			layout = _layoutLocalService.updateLookAndFeel(
				groupId, privateLayout, layout.getLayoutId(), themeId,
				colorSchemeId, StringPool.BLANK);
		}

		return layout;
	}

	private LayoutPageTemplateEntry _addLayoutPageTemplateEntry(
			long groupId, LayoutPrototype layoutPrototype)
		throws PortalException {

		String nameXML = layoutPrototype.getName();

		Map<Locale, String> nameMap = _localization.getLocalizationMap(nameXML);

		Locale defaultLocale = LocaleUtil.fromLanguageId(
			_localization.getDefaultLanguageId(nameXML));

		Layout layout = layoutPrototype.getLayout();

		int status = WorkflowConstants.STATUS_APPROVED;

		if (!layoutPrototype.isActive()) {
			status = WorkflowConstants.STATUS_INACTIVE;
		}

		return addLayoutPageTemplateEntry(
			layoutPrototype.getUserId(), groupId, 0, 0, 0,
			nameMap.get(defaultLocale),
			LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE, 0, false,
			layoutPrototype.getLayoutPrototypeId(), layout.getPlid(), 0, status,
			new ServiceContext());
	}

	private FileEntry _copyPreviewFileEntry(
			LayoutPageTemplateEntry sourceLayoutPageTemplateEntry,
			LayoutPageTemplateEntry targetLayoutPageTemplateEntry,
			ServiceContext serviceContext)
		throws Exception {

		long sourcePreviewFileEntryId =
			sourceLayoutPageTemplateEntry.getPreviewFileEntryId();

		if (sourcePreviewFileEntryId <= 0) {
			return null;
		}

		FileEntry portletFileEntry = _portletFileRepository.getPortletFileEntry(
			sourcePreviewFileEntryId);

		Folder folder = portletFileEntry.getFolder();

		long targetLayoutPageTemplateEntryId =
			targetLayoutPageTemplateEntry.getLayoutPageTemplateEntryId();

		String fileName =
			targetLayoutPageTemplateEntryId + "_preview." +
				portletFileEntry.getExtension();

		return _portletFileRepository.addPortletFileEntry(
			portletFileEntry.getGroupId(), serviceContext.getUserId(),
			LayoutPageTemplateEntry.class.getName(),
			targetLayoutPageTemplateEntryId, LayoutAdminPortletKeys.GROUP_PAGES,
			folder.getFolderId(),
			_file.getBytes(portletFileEntry.getContentStream()), fileName,
			portletFileEntry.getMimeType(), false);
	}

	private String _generateLayoutPageTemplateEntryKey(
		long groupId, String name) {

		String layoutPageTemplateEntryKey = StringUtil.toLowerCase(name.trim());

		layoutPageTemplateEntryKey = StringUtil.replace(
			layoutPageTemplateEntryKey, CharPool.SPACE, CharPool.DASH);

		String curLayoutPageTemplateEntryKey = layoutPageTemplateEntryKey;

		int count = 0;

		while (true) {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				layoutPageTemplateEntryPersistence.fetchByG_LPTEK(
					groupId, curLayoutPageTemplateEntryKey);

			if (layoutPageTemplateEntry == null) {
				return curLayoutPageTemplateEntryKey;
			}

			curLayoutPageTemplateEntryKey =
				curLayoutPageTemplateEntryKey + CharPool.DASH + count++;
		}
	}

	private String _getColorSchemeId(
		long companyId, String themeId, String colorSchemeId) {

		Theme theme = _themeLocalService.getTheme(companyId, themeId);

		if (!theme.hasColorSchemes()) {
			colorSchemeId = StringPool.BLANK;
		}

		if (Validator.isNull(colorSchemeId)) {
			ColorScheme colorScheme = _themeLocalService.getColorScheme(
				companyId, themeId, colorSchemeId);

			colorSchemeId = colorScheme.getColorSchemeId();
		}

		return colorSchemeId;
	}

	private String _getUniqueCopyName(
		long groupId, String sourceName, int type, Locale locale) {

		String copy = _language.get(locale, "copy");

		String name = StringUtil.appendParentheticalSuffix(sourceName, copy);

		for (int i = 1;; i++) {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				layoutPageTemplateEntryPersistence.fetchByG_N_T(
					groupId, name, type);

			if (layoutPageTemplateEntry == null) {
				break;
			}

			name = StringUtil.appendParentheticalSuffix(
				sourceName, copy + StringPool.SPACE + i);
		}

		return name;
	}

	private void _validate(long classNameId, long classTypeId, long groupId)
		throws PortalException {

		String className = StringPool.BLANK;

		try {
			className = _portal.getClassName(classNameId);
		}
		catch (RuntimeException runtimeException) {
			if (_log.isDebugEnabled()) {
				_log.debug(runtimeException);
			}

			throw new NoSuchClassNameException(
				"Class name does not exist for class name ID " + classNameId);
		}

		InfoItemFormProvider<?> infoItemFormProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFormProvider.class, className);

		if (infoItemFormProvider == null) {
			throw new PortalException(
				"No item form provider is registered for class name ID " +
					classNameId);
		}

		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class, className);

		if (infoItemFormVariationsProvider == null) {
			return;
		}

		InfoItemFormVariation infoItemFormVariation =
			infoItemFormVariationsProvider.getInfoItemFormVariation(
				groupId, String.valueOf(classTypeId));

		if (infoItemFormVariation == null) {
			throw new NoSuchClassTypeException(
				"Class type does not exist for class name ID " + classNameId);
		}
	}

	private void _validate(long groupId, String name, int type)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new LayoutPageTemplateEntryNameException.MustNotBeNull(
				groupId);
		}

		LayoutPageTemplateEntryValidator.validateNameCharacters(name);

		int nameMaxLength = ModelHintsUtil.getMaxLength(
			LayoutPageTemplateEntry.class.getName(), "name");

		if (name.length() > nameMaxLength) {
			throw new LayoutPageTemplateEntryNameException.
				MustNotExceedMaximumSize(nameMaxLength);
		}

		if (Objects.equals(name, "Blank") &&
			(type == LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT)) {

			throw new LayoutPageTemplateEntryNameException.MustNotBeDuplicate(
				groupId, name);
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryPersistence.fetchByG_N_T(
				groupId, name, type);

		if (layoutPageTemplateEntry != null) {
			throw new LayoutPageTemplateEntryNameException.MustNotBeDuplicate(
				groupId, name);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateEntryLocalServiceImpl.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DDMStructureLinkLocalService _ddmStructureLinkLocalService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private File _file;

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPrototypeLocalService _layoutPrototypeLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private Localization _localization;

	@Reference
	private Portal _portal;

	@Reference
	private PortletFileRepository _portletFileRepository;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

	@Reference
	private ThemeLocalService _themeLocalService;

	@Reference
	private UserLocalService _userLocalService;

}