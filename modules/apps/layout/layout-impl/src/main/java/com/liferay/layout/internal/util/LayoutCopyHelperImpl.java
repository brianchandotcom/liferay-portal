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

package com.liferay.layout.internal.util;

import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.layout.util.structure.DeletedLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.PortletPreferencesIds;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferenceValueLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CopyLayoutThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.exportimport.staging.StagingAdvicesThreadLocal;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.sites.kernel.util.Sites;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = LayoutCopyHelper.class)
public class LayoutCopyHelperImpl implements LayoutCopyHelper {

	@Override
	public Layout copyLayout(Layout sourceLayout, Layout targetLayout)
		throws Exception {

		List<SegmentsExperience> segmentsExperiences =
			_segmentsExperienceLocalService.getSegmentsExperiences(
				sourceLayout.getGroupId(), _portal.getClassNameId(Layout.class),
				sourceLayout.getPlid());

		List<Long> segmentsExperiencesIds = ListUtil.toList(
			segmentsExperiences,
			SegmentsExperience.SEGMENTS_EXPERIENCE_ID_ACCESSOR);

		segmentsExperiencesIds.add(0, SegmentsExperienceConstants.ID_DEFAULT);

		return copyLayout(
			ArrayUtil.toLongArray(segmentsExperiencesIds), sourceLayout,
			targetLayout);
	}

	@Override
	public Layout copyLayout(
			long segmentsExperienceId, Layout sourceLayout, Layout targetLayout)
		throws Exception {

		Consumer<Layout> consumer = processedTargetLayout -> {
			try {
				_copyLayoutPageTemplateStructureFromSegmentsExperience(
					segmentsExperienceId, sourceLayout, processedTargetLayout);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}
			}
		};

		Callable<Layout> callable = new CopyLayoutCallable(
			consumer, new long[] {segmentsExperienceId}, sourceLayout,
			targetLayout);

		boolean copyLayout = CopyLayoutThreadLocal.isCopyLayout();

		ServiceContext currentServiceContext =
			ServiceContextThreadLocal.getServiceContext();

		try {
			CopyLayoutThreadLocal.setCopyLayout(true);

			return TransactionInvokerUtil.invoke(_transactionConfig, callable);
		}
		catch (Throwable throwable) {
			throw new Exception(throwable);
		}
		finally {
			CopyLayoutThreadLocal.setCopyLayout(copyLayout);

			ServiceContextThreadLocal.pushServiceContext(currentServiceContext);
		}
	}

	@Override
	public Layout copyLayout(
			long[] segmentsExperiencesIds, Layout sourceLayout,
			Layout targetLayout)
		throws Exception {

		Consumer<Layout> consumer = processedTargetLayout -> {
			try {
				_copyLayoutPageTemplateStructure(
					segmentsExperiencesIds, sourceLayout,
					processedTargetLayout);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}
			}
		};

		Callable<Layout> callable = new CopyLayoutCallable(
			consumer, segmentsExperiencesIds, sourceLayout, targetLayout);

		boolean copyLayout = CopyLayoutThreadLocal.isCopyLayout();

		ServiceContext currentServiceContext =
			ServiceContextThreadLocal.getServiceContext();

		try {
			CopyLayoutThreadLocal.setCopyLayout(true);

			return TransactionInvokerUtil.invoke(_transactionConfig, callable);
		}
		catch (Throwable throwable) {
			throw new Exception(throwable);
		}
		finally {
			CopyLayoutThreadLocal.setCopyLayout(copyLayout);

			ServiceContextThreadLocal.pushServiceContext(currentServiceContext);
		}
	}

	private void _copyAssetCategoryIdsAndAssetTagNames(
			Layout sourceLayout, Layout targetLayout)
		throws Exception {

		if (sourceLayout.isDraftLayout() || targetLayout.isDraftLayout()) {
			return;
		}

		long[] assetCategoryIds = _assetCategoryLocalService.getCategoryIds(
			Layout.class.getName(), sourceLayout.getPlid());

		String[] assetTagNames = _assetTagLocalService.getTagNames(
			Layout.class.getName(), sourceLayout.getPlid());

		_layoutLocalService.updateAsset(
			targetLayout.getUserId(), targetLayout, assetCategoryIds,
			assetTagNames);
	}

	private void _copyLayoutClassedModelUsages(
		Layout sourceLayout, Layout targetLayout) {

		List<LayoutClassedModelUsage> sourceLayoutLayoutClassedModelUsages =
			_layoutClassedModelUsageLocalService.
				getLayoutClassedModelUsagesByPlid(sourceLayout.getPlid());

		_deleteLayoutClassedModelUsages(
			sourceLayoutLayoutClassedModelUsages, targetLayout);

		List<LayoutClassedModelUsage> targetLayoutLayoutClassedModelUsages =
			_layoutClassedModelUsageLocalService.
				getLayoutClassedModelUsagesByPlid(targetLayout.getPlid());

		for (LayoutClassedModelUsage sourceLayoutLayoutClassedModelUsage :
				sourceLayoutLayoutClassedModelUsages) {

			if (_hasLayoutClassedModelUsage(
					targetLayoutLayoutClassedModelUsages,
					sourceLayoutLayoutClassedModelUsage)) {

				continue;
			}

			String containerKey =
				sourceLayoutLayoutClassedModelUsage.getContainerKey();

			long containerType =
				sourceLayoutLayoutClassedModelUsage.getContainerType();

			if (containerType == _portal.getClassNameId(
					FragmentEntryLink.class.getName())) {

				long fragmentEntryLinkId = GetterUtil.getLong(
					sourceLayoutLayoutClassedModelUsage.getContainerKey());

				FragmentEntryLink fragmentEntryLink =
					_fragmentEntryLinkLocalService.getFragmentEntryLink(
						sourceLayout.getGroupId(), fragmentEntryLinkId,
						targetLayout.getPlid());

				if (fragmentEntryLink != null) {
					containerKey = String.valueOf(
						fragmentEntryLink.getFragmentEntryLinkId());
				}
			}

			_layoutClassedModelUsageLocalService.addLayoutClassedModelUsage(
				sourceLayoutLayoutClassedModelUsage.getGroupId(),
				sourceLayoutLayoutClassedModelUsage.getClassNameId(),
				sourceLayoutLayoutClassedModelUsage.getClassPK(), containerKey,
				sourceLayoutLayoutClassedModelUsage.getContainerType(),
				targetLayout.getPlid(),
				ServiceContextThreadLocal.getServiceContext());
		}
	}

	private void _copyLayoutPageTemplateStructure(
			long[] segmentsExperiencesIds, Layout sourceLayout,
			Layout targetLayout)
		throws Exception {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					sourceLayout.getGroupId(), sourceLayout.getPlid());

		if (layoutPageTemplateStructure == null) {
			LayoutPageTemplateStructure targetLayoutPageTemplateStructure =
				_layoutPageTemplateStructureLocalService.
					fetchLayoutPageTemplateStructure(
						targetLayout.getGroupId(), targetLayout.getPlid());

			if (targetLayoutPageTemplateStructure != null) {
				_layoutPageTemplateStructureLocalService.
					deleteLayoutPageTemplateStructure(
						targetLayoutPageTemplateStructure);
			}

			_fragmentEntryLinkLocalService.
				deleteLayoutPageTemplateEntryFragmentEntryLinks(
					targetLayout.getGroupId(), targetLayout.getPlid());

			layoutPageTemplateStructure =
				_layoutPageTemplateStructureLocalService.
					rebuildLayoutPageTemplateStructure(
						sourceLayout.getGroupId(), sourceLayout.getPlid());
		}

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.
				getFragmentEntryLinksBySegmentsExperienceId(
					sourceLayout.getGroupId(), segmentsExperiencesIds,
					sourceLayout.getPlid());

		Stream<FragmentEntryLink> stream = fragmentEntryLinks.stream();

		Map<Long, FragmentEntryLink> fragmentEntryLinksMap = stream.collect(
			Collectors.toMap(
				FragmentEntryLink::getFragmentEntryLinkId,
				fragmentEntryLink -> fragmentEntryLink));

		_fragmentEntryLinkLocalService.
			deleteLayoutPageTemplateEntryFragmentEntryLinks(
				targetLayout.getGroupId(), segmentsExperiencesIds,
				targetLayout.getPlid());

		LayoutPageTemplateStructure targetLayoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					targetLayout.getGroupId(), targetLayout.getPlid());

		if (targetLayoutPageTemplateStructure == null) {
			long defaultSegmentsExperienceId =
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(targetLayout.getPlid());

			_layoutPageTemplateStructureLocalService.
				addLayoutPageTemplateStructure(
					targetLayout.getUserId(), targetLayout.getGroupId(),
					targetLayout.getPlid(), defaultSegmentsExperienceId, null,
					ServiceContextThreadLocal.getServiceContext());
		}

		Map<Long, Long> segmentsExperienceIdsMap = _getSegmentsExperienceIds(
			segmentsExperiencesIds, sourceLayout, targetLayout);

		for (Map.Entry<Long, Long> entry :
				segmentsExperienceIdsMap.entrySet()) {

			String data = layoutPageTemplateStructure.getData(entry.getKey());

			if (Validator.isNull(data)) {
				_segmentsExperienceLocalService.deleteSegmentsExperience(
					entry.getKey());

				continue;
			}

			JSONObject dataJSONObject = _processDataJSONObject(
				data, targetLayout, fragmentEntryLinksMap, entry.getValue());

			_layoutPageTemplateStructureLocalService.
				updateLayoutPageTemplateStructureData(
					targetLayout.getGroupId(), targetLayout.getPlid(),
					entry.getValue(), dataJSONObject.toString());
		}
	}

	private void _copyLayoutPageTemplateStructureFromSegmentsExperience(
			long segmentsExperienceId, Layout sourceLayout, Layout targetLayout)
		throws Exception {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					sourceLayout.getGroupId(), sourceLayout.getPlid());

		String data = layoutPageTemplateStructure.getData(segmentsExperienceId);

		if (Validator.isNull(data)) {
			return;
		}

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.
				getFragmentEntryLinksBySegmentsExperienceId(
					sourceLayout.getGroupId(), segmentsExperienceId,
					sourceLayout.getPlid());

		Stream<FragmentEntryLink> stream = fragmentEntryLinks.stream();

		Map<Long, FragmentEntryLink> fragmentEntryLinksMap = stream.collect(
			Collectors.toMap(
				FragmentEntryLink::getFragmentEntryLinkId,
				fragmentEntryLink -> fragmentEntryLink));

		LayoutStructure layoutStructure = LayoutStructure.of(data);

		for (DeletedLayoutStructureItem deletedLayoutStructureItem :
				layoutStructure.getDeletedLayoutStructureItems()) {

			layoutStructure.deleteLayoutStructureItem(
				deletedLayoutStructureItem.getItemId());
		}

		long defaultSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				targetLayout.getPlid());

		JSONObject dataJSONObject = _processDataJSONObject(
			layoutStructure.toString(), targetLayout, fragmentEntryLinksMap,
			defaultSegmentsExperienceId);

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				targetLayout.getGroupId(), targetLayout.getPlid(),
				defaultSegmentsExperienceId, dataJSONObject.toString());
	}

	private void _copyLayoutSEOEntry(Layout sourceLayout, Layout targetLayout)
		throws Exception {

		if (sourceLayout.isDraftLayout() || targetLayout.isDraftLayout()) {
			return;
		}

		LayoutSEOEntry layoutSEOEntry =
			_layoutSEOEntryLocalService.fetchLayoutSEOEntry(
				sourceLayout.getGroupId(), sourceLayout.isPrivateLayout(),
				sourceLayout.getLayoutId());

		if (layoutSEOEntry == null) {
			LayoutSEOEntry targetLayoutSEOEntry =
				_layoutSEOEntryLocalService.fetchLayoutSEOEntry(
					targetLayout.getGroupId(), targetLayout.isPrivateLayout(),
					targetLayout.getLayoutId());

			if (targetLayoutSEOEntry != null) {
				_layoutSEOEntryLocalService.deleteLayoutSEOEntry(
					targetLayout.getGroupId(), targetLayout.isPrivateLayout(),
					targetLayout.getLayoutId());
			}

			return;
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		serviceContext.setAttribute(
			"layout.instanceable.allowed", Boolean.TRUE);

		_layoutSEOEntryLocalService.copyLayoutSEOEntry(
			targetLayout.getUserId(), targetLayout.getGroupId(),
			targetLayout.isPrivateLayout(), targetLayout.getLayoutId(),
			layoutSEOEntry.isCanonicalURLEnabled(),
			layoutSEOEntry.getCanonicalURLMap(),
			layoutSEOEntry.getDDMStorageId(),
			layoutSEOEntry.isOpenGraphDescriptionEnabled(),
			layoutSEOEntry.getOpenGraphDescriptionMap(),
			layoutSEOEntry.getOpenGraphImageAltMap(),
			layoutSEOEntry.getOpenGraphImageFileEntryId(),
			layoutSEOEntry.isOpenGraphTitleEnabled(),
			layoutSEOEntry.getOpenGraphTitleMap(), serviceContext);
	}

	private void _copyPortletPermissions(
			long[] segmentsExperiencesIds, Layout sourceLayout,
			Layout targetLayout)
		throws Exception {

		_deletePortletPermissions(targetLayout, segmentsExperiencesIds);

		List<String> portletIds = _getLayoutPortletIds(
			sourceLayout, segmentsExperiencesIds);

		for (String portletId : portletIds) {
			String resourceName = PortletIdCodec.decodePortletName(portletId);
			String sourceResourcePrimKey = PortletPermissionUtil.getPrimaryKey(
				sourceLayout.getPlid(), portletId);
			List<String> actionIds =
				ResourceActionsUtil.getPortletResourceActions(resourceName);

			Map<Long, Set<String>> sourceRoleIdsToActionIds =
				_resourcePermissionLocalService.
					getAvailableResourcePermissionActionIds(
						targetLayout.getCompanyId(), resourceName,
						ResourceConstants.SCOPE_INDIVIDUAL,
						sourceResourcePrimKey, actionIds);

			if (sourceRoleIdsToActionIds.isEmpty()) {
				continue;
			}

			Group targetGroup = targetLayout.getGroup();

			Set<Long> roleIds = new HashSet<>();

			for (Role role :
					_roleLocalService.getGroupRelatedRoles(
						targetLayout.getGroupId())) {

				String roleName = role.getName();

				if (roleName.equals(RoleConstants.ADMINISTRATOR) ||
					(!targetGroup.isLayoutSetPrototype() &&
					 targetLayout.isPrivateLayout() &&
					 roleName.equals(RoleConstants.GUEST))) {

					continue;
				}

				roleIds.add(role.getRoleId());
			}

			Map<Long, String[]> targetRoleIdsToActionIds = new HashMap<>();

			for (Map.Entry<Long, Set<String>> entry :
					sourceRoleIdsToActionIds.entrySet()) {

				Long roleId = entry.getKey();

				if (roleIds.contains(roleId)) {
					Set<String> sourceActionIds = entry.getValue();

					targetRoleIdsToActionIds.put(
						roleId, sourceActionIds.toArray(new String[0]));
				}
			}

			String targetResourcePrimKey = PortletPermissionUtil.getPrimaryKey(
				targetLayout.getPlid(), portletId);

			_resourcePermissionLocalService.setResourcePermissions(
				targetLayout.getCompanyId(), resourceName,
				ResourceConstants.SCOPE_INDIVIDUAL, targetResourcePrimKey,
				targetRoleIdsToActionIds);
		}
	}

	private void _copyPortletPreferences(
		long[] segmentsExperiencesIds, Layout sourceLayout,
		Layout targetLayout) {

		boolean stagingAdvicesThreadLocalEnabled =
			StagingAdvicesThreadLocal.isEnabled();

		try {
			StagingAdvicesThreadLocal.setEnabled(false);

			List<String> portletIds = _getLayoutPortletIds(
				sourceLayout, segmentsExperiencesIds);

			List<String> targetPortletIds = _getLayoutPortletIds(
				targetLayout, segmentsExperiencesIds);

			for (String portletId : portletIds) {
				Portlet portlet = _portletLocalService.getPortletById(
					portletId);

				if ((portlet == null) || portlet.isUndeployedPortlet()) {
					continue;
				}

				targetPortletIds.remove(portletId);

				PortletPreferencesIds portletPreferencesIds =
					_portletPreferencesFactory.getPortletPreferencesIds(
						sourceLayout.getCompanyId(), sourceLayout.getGroupId(),
						0, sourceLayout.getPlid(), portletId);

				javax.portlet.PortletPreferences jxPortletPreferences =
					_portletPreferencesLocalService.fetchPreferences(
						portletPreferencesIds);

				if (jxPortletPreferences == null) {
					continue;
				}

				PortletPreferences targetPortletPreferences =
					_portletPreferencesLocalService.fetchPortletPreferences(
						portletPreferencesIds.getOwnerId(),
						portletPreferencesIds.getOwnerType(),
						targetLayout.getPlid(), portletId);

				if (targetPortletPreferences != null) {
					_portletPreferencesLocalService.updatePreferences(
						targetPortletPreferences.getOwnerId(),
						targetPortletPreferences.getOwnerType(),
						targetPortletPreferences.getPlid(),
						targetPortletPreferences.getPortletId(),
						jxPortletPreferences);
				}
				else {
					_portletPreferencesLocalService.addPortletPreferences(
						targetLayout.getCompanyId(),
						portletPreferencesIds.getOwnerId(),
						portletPreferencesIds.getOwnerType(),
						targetLayout.getPlid(), portletId, portlet,
						PortletPreferencesFactoryUtil.toXML(
							jxPortletPreferences));
				}
			}

			for (String portletId : targetPortletIds) {
				try {
					_portletPreferencesLocalService.deletePortletPreferences(
						PortletKeys.PREFS_OWNER_ID_DEFAULT,
						PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
						targetLayout.getPlid(), portletId);
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to delete portlet preferences for " +
								"portlet " + portletId,
							exception);
					}
				}
			}
		}
		finally {
			StagingAdvicesThreadLocal.setEnabled(
				stagingAdvicesThreadLocalEnabled);
		}
	}

	private void _deleteLayoutClassedModelUsages(
		List<LayoutClassedModelUsage> sourceLayoutLayoutClassedModelUsages,
		Layout targetLayout) {

		List<LayoutClassedModelUsage> targetLayoutClassedModelUsages =
			_layoutClassedModelUsageLocalService.
				getLayoutClassedModelUsagesByPlid(targetLayout.getPlid());

		for (LayoutClassedModelUsage targetLayoutClassedModelUsage :
				targetLayoutClassedModelUsages) {

			if (!_hasLayoutClassedModelUsage(
					sourceLayoutLayoutClassedModelUsages,
					targetLayoutClassedModelUsage)) {

				_layoutClassedModelUsageLocalService.
					deleteLayoutClassedModelUsage(
						targetLayoutClassedModelUsage);
			}
		}
	}

	private void _deletePortletPermissions(
			Layout layout, long[] segmentsExperiencesIds)
		throws Exception {

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.
				getFragmentEntryLinksBySegmentsExperienceId(
					layout.getGroupId(), segmentsExperiencesIds,
					layout.getPlid());

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			List<String> portletIds =
				_portletRegistry.getFragmentEntryLinkPortletIds(
					fragmentEntryLink);

			for (String portletId : portletIds) {
				String resourceName = PortletIdCodec.decodePortletName(
					portletId);

				String resourcePrimKey = PortletPermissionUtil.getPrimaryKey(
					layout.getPlid(), portletId);

				_resourcePermissionLocalService.deleteResourcePermissions(
					layout.getCompanyId(), resourceName,
					ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey);
			}
		}
	}

	private List<String> _getLayoutPortletIds(
		Layout layout, long[] segmentsExperiencesIds) {

		List<String> layoutPortletIds = new ArrayList<>();

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.
				getFragmentEntryLinksBySegmentsExperienceId(
					layout.getGroupId(), segmentsExperiencesIds,
					layout.getPlid());

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			layoutPortletIds.addAll(
				_portletRegistry.getFragmentEntryLinkPortletIds(
					fragmentEntryLink));
		}

		return layoutPortletIds;
	}

	private Map<Long, Long> _getSegmentsExperienceIds(
		long[] segmentsExperiencesIds, Layout sourceLayout,
		Layout targetLayout) {

		Map<Long, Long> segmentsExperienceIdsMap = new HashMap<>();

		if (sourceLayout.isDraftLayout() || targetLayout.isDraftLayout()) {
			for (long segmentsExperienceId : segmentsExperiencesIds) {
				segmentsExperienceIdsMap.put(
					segmentsExperienceId, segmentsExperienceId);
			}

			return segmentsExperienceIdsMap;
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		for (long segmentsExperienceId : segmentsExperiencesIds) {
			if (segmentsExperienceId ==
					SegmentsExperienceConstants.ID_DEFAULT) {

				segmentsExperienceIdsMap.put(
					SegmentsExperienceConstants.ID_DEFAULT,
					SegmentsExperienceConstants.ID_DEFAULT);

				continue;
			}

			SegmentsExperience segmentsExperience =
				_segmentsExperienceLocalService.fetchSegmentsExperience(
					segmentsExperienceId);

			SegmentsExperience newSegmentsExperience =
				(SegmentsExperience)segmentsExperience.clone();

			newSegmentsExperience.setUuid(serviceContext.getUuid());
			newSegmentsExperience.setSegmentsExperienceId(
				_counterLocalService.increment());
			newSegmentsExperience.setUserId(targetLayout.getUserId());
			newSegmentsExperience.setUserName(targetLayout.getUserName());
			newSegmentsExperience.setCreateDate(
				serviceContext.getCreateDate(new Date()));
			newSegmentsExperience.setModifiedDate(
				serviceContext.getModifiedDate(new Date()));
			newSegmentsExperience.setSegmentsExperienceKey(
				String.valueOf(_counterLocalService.increment()));
			newSegmentsExperience.setClassNameId(
				_portal.getClassNameId(Layout.class));
			newSegmentsExperience.setClassPK(targetLayout.getPlid());

			_segmentsExperienceLocalService.addSegmentsExperience(
				newSegmentsExperience);

			segmentsExperienceIdsMap.put(
				segmentsExperience.getSegmentsExperienceId(),
				newSegmentsExperience.getSegmentsExperienceId());
		}

		return segmentsExperienceIdsMap;
	}

	private boolean _hasLayoutClassedModelUsage(
		List<LayoutClassedModelUsage> layoutClassedModelUsages,
		LayoutClassedModelUsage targetLayoutClassedModelUsage) {

		for (LayoutClassedModelUsage layoutClassedModelUsage :
				layoutClassedModelUsages) {

			if ((layoutClassedModelUsage.getClassNameId() ==
					targetLayoutClassedModelUsage.getClassNameId()) &&
				(layoutClassedModelUsage.getClassPK() ==
					targetLayoutClassedModelUsage.getClassPK()) &&
				Objects.equals(
					layoutClassedModelUsage.getContainerKey(),
					targetLayoutClassedModelUsage.getContainerKey()) &&
				(layoutClassedModelUsage.getContainerType() ==
					targetLayoutClassedModelUsage.getContainerType())) {

				return true;
			}
		}

		return false;
	}

	private JSONObject _processDataJSONObject(
			String data, Layout targetLayout,
			Map<Long, FragmentEntryLink> fragmentEntryLinksMap,
			long targetSegmentsExperienceId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		LayoutStructure layoutStructure = LayoutStructure.of(data);

		for (LayoutStructureItem layoutStructureItem :
				layoutStructure.getLayoutStructureItems()) {

			if (!(layoutStructureItem instanceof
					FragmentStyledLayoutStructureItem)) {

				continue;
			}

			FragmentStyledLayoutStructureItem
				fragmentStyledLayoutStructureItem =
					(FragmentStyledLayoutStructureItem)layoutStructureItem;

			FragmentEntryLink fragmentEntryLink = fragmentEntryLinksMap.get(
				fragmentStyledLayoutStructureItem.getFragmentEntryLinkId());

			if (fragmentEntryLink == null) {
				continue;
			}

			FragmentEntryLink newFragmentEntryLink =
				(FragmentEntryLink)fragmentEntryLink.clone();

			newFragmentEntryLink.setUuid(serviceContext.getUuid());
			newFragmentEntryLink.setFragmentEntryLinkId(
				_counterLocalService.increment());
			newFragmentEntryLink.setUserId(targetLayout.getUserId());
			newFragmentEntryLink.setUserName(targetLayout.getUserName());
			newFragmentEntryLink.setCreateDate(
				serviceContext.getCreateDate(new Date()));
			newFragmentEntryLink.setModifiedDate(
				serviceContext.getModifiedDate(new Date()));
			newFragmentEntryLink.setOriginalFragmentEntryLinkId(
				fragmentEntryLink.getFragmentEntryLinkId());
			newFragmentEntryLink.setSegmentsExperienceId(
				targetSegmentsExperienceId);
			newFragmentEntryLink.setClassNameId(
				_portal.getClassNameId(Layout.class));
			newFragmentEntryLink.setClassPK(targetLayout.getPlid());
			newFragmentEntryLink.setPlid(targetLayout.getPlid());
			newFragmentEntryLink.setLastPropagationDate(
				serviceContext.getCreateDate(new Date()));

			newFragmentEntryLink =
				_fragmentEntryLinkLocalService.addFragmentEntryLink(
					newFragmentEntryLink);

			fragmentStyledLayoutStructureItem.setFragmentEntryLinkId(
				newFragmentEntryLink.getFragmentEntryLinkId());

			_commentManager.copyDiscussion(
				targetLayout.getUserId(), targetLayout.getGroupId(),
				FragmentEntryLink.class.getName(),
				fragmentEntryLink.getFragmentEntryLinkId(),
				newFragmentEntryLink.getFragmentEntryLinkId(),
				className -> serviceContext);
		}

		return layoutStructure.toJSONObject();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutCopyHelperImpl.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private CommentManager _commentManager;

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private ImageLocalService _imageLocalService;

	@Reference
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutSEOEntryLocalService _layoutSEOEntryLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesFactory _portletPreferencesFactory;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private PortletPreferenceValueLocalService
		_portletPreferenceValueLocalService;

	@Reference
	private PortletRegistry _portletRegistry;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference
	private Sites _sites;

	private class CopyLayoutCallable implements Callable<Layout> {

		@Override
		public Layout call() throws Exception {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			if (serviceContext == null) {
				ServiceContextThreadLocal.pushServiceContext(
					new ServiceContext());
			}

			_sites.copyExpandoBridgeAttributes(_sourceLayout, _targetLayout);
			_sites.copyLookAndFeel(_targetLayout, _sourceLayout);
			_sites.copyPortletSetups(_sourceLayout, _targetLayout);

			if (Objects.equals(
					_sourceLayout.getType(), LayoutConstants.TYPE_PORTLET)) {

				_sites.copyPortletPermissions(_targetLayout, _sourceLayout);
			}
			else {

				// LPS-108378 Copy structure before permissions and preferences

				_consumer.accept(_targetLayout);

				// Copy classedModelUsages after copying the structure

				_copyLayoutClassedModelUsages(_sourceLayout, _targetLayout);

				_copyPortletPermissions(
					_segmentsExperiencesIds, _sourceLayout, _targetLayout);
			}

			_copyAssetCategoryIdsAndAssetTagNames(_sourceLayout, _targetLayout);

			_copyLayoutSEOEntry(_sourceLayout, _targetLayout);

			_copyPortletPreferences(
				_segmentsExperiencesIds, _sourceLayout, _targetLayout);

			_layoutLocalService.updateMasterLayoutPlid(
				_targetLayout.getGroupId(), _targetLayout.isPrivateLayout(),
				_targetLayout.getLayoutId(),
				_sourceLayout.getMasterLayoutPlid());

			_layoutLocalService.updateStyleBookEntryId(
				_targetLayout.getGroupId(), _targetLayout.isPrivateLayout(),
				_targetLayout.getLayoutId(),
				_sourceLayout.getStyleBookEntryId());

			UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.load(
				_sourceLayout.getTypeSettings()
			).build();

			_layoutLocalService.updateLayout(
				_targetLayout.getGroupId(), _targetLayout.isPrivateLayout(),
				_targetLayout.getLayoutId(), unicodeProperties.toString());

			Image image = _imageLocalService.getImage(
				_sourceLayout.getIconImageId());

			byte[] imageBytes = null;

			if (image != null) {
				imageBytes = image.getTextObj();
			}

			return _layoutLocalService.updateIconImage(
				_targetLayout.getPlid(), imageBytes);
		}

		private CopyLayoutCallable(
			Consumer<Layout> consumer, long[] segmentsExperiencesIds,
			Layout sourceLayout, Layout targetLayout) {

			_consumer = consumer;
			_segmentsExperiencesIds = segmentsExperiencesIds;
			_sourceLayout = sourceLayout;
			_targetLayout = targetLayout;
		}

		private final Consumer<Layout> _consumer;
		private final long[] _segmentsExperiencesIds;
		private final Layout _sourceLayout;
		private final Layout _targetLayout;

	}

}