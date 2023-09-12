/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.helper;

import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.helper.LayoutCopyHelper;
import com.liferay.layout.model.LayoutClassedModelUsage;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.layout.util.structure.DeletedLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.json.JSONObject;
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
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.CopyLayoutThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.exportimport.staging.StagingAdvicesThreadLocal;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperienceModel;
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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(service = LayoutCopyHelper.class)
public class LayoutCopyHelperImpl implements LayoutCopyHelper {

	@Override
	public Layout copyLayoutContent(Layout sourceLayout, Layout targetLayout)
		throws Exception {

		return copyLayoutContent(
			TransformUtil.transformToLongArray(
				_segmentsExperienceLocalService.getSegmentsExperiences(
					sourceLayout.getGroupId(), sourceLayout.getPlid()),
				SegmentsExperienceModel::getSegmentsExperienceId),
			sourceLayout, targetLayout);
	}

	@Override
	public Layout copyLayoutContent(
			long segmentsExperienceId, Layout sourceLayout, Layout targetLayout)
		throws Exception {

		return _copyLayoutContent(
			true, sourceLayout, new long[] {segmentsExperienceId}, targetLayout,
			new long[] {
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(targetLayout.getPlid())
			});
	}

	@Override
	public Layout copyLayoutContent(
			long[] segmentsExperiencesIds, Layout sourceLayout,
			Layout targetLayout)
		throws Exception {

		return _copyLayoutContent(
			false, sourceLayout, segmentsExperiencesIds, targetLayout,
			segmentsExperiencesIds);
	}

	private void _copyAssetCategoryIdsAndAssetTagNames(
			Layout sourceLayout, Layout targetLayout)
		throws Exception {

		if (sourceLayout.isDraftLayout() || targetLayout.isDraftLayout()) {
			return;
		}

		_layoutLocalService.updateAsset(
			targetLayout.getUserId(), targetLayout,
			_assetCategoryLocalService.getCategoryIds(
				Layout.class.getName(), sourceLayout.getPlid()),
			_assetTagLocalService.getTagNames(
				Layout.class.getName(), sourceLayout.getPlid()));
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
				sourceLayoutLayoutClassedModelUsage.getClassPK(),
				sourceLayoutLayoutClassedModelUsage.
					getClassedModelExternalReferenceCode(),
				containerKey,
				sourceLayoutLayoutClassedModelUsage.getContainerType(),
				targetLayout.getPlid(),
				ServiceContextThreadLocal.getServiceContext());
		}
	}

	private Layout _copyLayoutContent(
			boolean copySegmentsExperience, Layout sourceLayout,
			long[] sourceSegmentsExperiencesIds, Layout targetLayout,
			long[] targetSegmentsExperiencesIds)
		throws Exception {

		boolean copyLayout = CopyLayoutThreadLocal.isCopyLayout();

		ServiceContext currentServiceContext =
			ServiceContextThreadLocal.getServiceContext();

		try {
			CopyLayoutThreadLocal.setCopyLayout(true);

			if (currentServiceContext == null) {
				ServiceContextThreadLocal.pushServiceContext(
					new ServiceContext());
			}

			return TransactionInvokerUtil.invoke(
				_transactionConfig,
				new CopyLayoutCallable(
					copySegmentsExperience, sourceLayout,
					sourceSegmentsExperiencesIds, targetLayout,
					targetSegmentsExperiencesIds));
		}
		catch (Throwable throwable) {
			throw new Exception(throwable);
		}
		finally {
			CopyLayoutThreadLocal.setCopyLayout(copyLayout);

			ServiceContextThreadLocal.pushServiceContext(currentServiceContext);
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

		Map<Long, FragmentEntryLink> fragmentEntryLinksMap = new HashMap<>();

		for (FragmentEntryLink fragmentEntryLink :
				_fragmentEntryLinkLocalService.
					getFragmentEntryLinksBySegmentsExperienceId(
						sourceLayout.getGroupId(), segmentsExperiencesIds,
						sourceLayout.getPlid(), false)) {

			fragmentEntryLinksMap.put(
				fragmentEntryLink.getFragmentEntryLinkId(), fragmentEntryLink);
		}

		_fragmentEntryLinkLocalService.
			deleteLayoutPageTemplateEntryFragmentEntryLinks(
				targetLayout.getGroupId(), segmentsExperiencesIds,
				targetLayout.getPlid());

		LayoutPageTemplateStructure targetLayoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					targetLayout.getGroupId(), targetLayout.getPlid());

		if (targetLayoutPageTemplateStructure == null) {
			_layoutPageTemplateStructureLocalService.
				addLayoutPageTemplateStructure(
					targetLayout.getUserId(), targetLayout.getGroupId(),
					targetLayout.getPlid(),
					_segmentsExperienceLocalService.
						fetchDefaultSegmentsExperienceId(
							targetLayout.getPlid()),
					null, ServiceContextThreadLocal.getServiceContext());
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
				LayoutStructure.of(data), targetLayout, fragmentEntryLinksMap,
				entry.getValue());

			_layoutPageTemplateStructureLocalService.
				updateLayoutPageTemplateStructureData(
					targetLayout.getGroupId(), targetLayout.getPlid(),
					entry.getValue(), dataJSONObject.toString());
		}
	}

	private void _copyLayoutPageTemplateStructureFromSegmentsExperience(
			Layout sourceLayout, long sourceSegmentsExperienceId,
			Layout targetLayout, long targetSegmentsExperienceId)
		throws Exception {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					sourceLayout.getGroupId(), sourceLayout.getPlid());

		String data = layoutPageTemplateStructure.getData(
			sourceSegmentsExperienceId);

		if (Validator.isNull(data)) {
			return;
		}

		Map<Long, FragmentEntryLink> fragmentEntryLinksMap = new HashMap<>();

		for (FragmentEntryLink fragmentEntryLink :
				_fragmentEntryLinkLocalService.
					getFragmentEntryLinksBySegmentsExperienceId(
						sourceLayout.getGroupId(), sourceSegmentsExperienceId,
						sourceLayout.getPlid(), false)) {

			fragmentEntryLinksMap.put(
				fragmentEntryLink.getFragmentEntryLinkId(), fragmentEntryLink);
		}

		LayoutStructure layoutStructure = LayoutStructure.of(data);

		for (DeletedLayoutStructureItem deletedLayoutStructureItem :
				layoutStructure.getDeletedLayoutStructureItems()) {

			layoutStructure.deleteLayoutStructureItem(
				deletedLayoutStructureItem.getItemId());
		}

		JSONObject dataJSONObject = _processDataJSONObject(
			layoutStructure, targetLayout, fragmentEntryLinksMap,
			targetSegmentsExperienceId);

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				targetLayout.getGroupId(), targetLayout.getPlid(),
				targetSegmentsExperienceId, dataJSONObject.toString());
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
			List<String> portletIds, Layout sourceLayout, Layout targetLayout)
		throws Exception {

		for (String portletId : portletIds) {
			String resourceName = PortletIdCodec.decodePortletName(portletId);
			String sourceResourcePrimKey = PortletPermissionUtil.getPrimaryKey(
				sourceLayout.getPlid(), portletId);

			Map<Long, Set<String>> sourceRoleIdsToActionIds =
				_resourcePermissionLocalService.
					getAvailableResourcePermissionActionIds(
						targetLayout.getCompanyId(), resourceName,
						ResourceConstants.SCOPE_INDIVIDUAL,
						sourceResourcePrimKey,
						ResourceActionsUtil.getPortletResourceActions(
							resourceName));

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

			_resourcePermissionLocalService.setResourcePermissions(
				targetLayout.getCompanyId(), resourceName,
				ResourceConstants.SCOPE_INDIVIDUAL,
				PortletPermissionUtil.getPrimaryKey(
					targetLayout.getPlid(), portletId),
				targetRoleIdsToActionIds);
		}
	}

	private void _copyPortletPreferences(
		List<String> portletIds, Layout sourceLayout, Layout targetLayout) {

		boolean stagingAdvicesThreadLocalEnabled =
			StagingAdvicesThreadLocal.isEnabled();

		try {
			StagingAdvicesThreadLocal.setEnabled(false);

			for (String portletId : portletIds) {
				Portlet portlet = _portletLocalService.getPortletById(
					portletId);

				if ((portlet == null) || portlet.isUndeployedPortlet()) {
					continue;
				}

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
		}
		finally {
			StagingAdvicesThreadLocal.setEnabled(
				stagingAdvicesThreadLocalEnabled);
		}
	}

	private void _deleteLayoutClassedModelUsages(
		List<LayoutClassedModelUsage> sourceLayoutLayoutClassedModelUsages,
		Layout targetLayout) {

		for (LayoutClassedModelUsage targetLayoutClassedModelUsage :
				_layoutClassedModelUsageLocalService.
					getLayoutClassedModelUsagesByPlid(targetLayout.getPlid())) {

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

		for (String portletId :
				_getLayoutPortletIds(layout, segmentsExperiencesIds)) {

			_resourcePermissionLocalService.deleteResourcePermissions(
				layout.getCompanyId(),
				PortletIdCodec.decodePortletName(portletId),
				ResourceConstants.SCOPE_INDIVIDUAL,
				PortletPermissionUtil.getPrimaryKey(
					layout.getPlid(), portletId));
		}
	}

	private List<String> _getLayoutPortletIds(
		Layout layout, long[] segmentsExperiencesIds) {

		List<String> layoutPortletIds = new ArrayList<>();

		for (FragmentEntryLink fragmentEntryLink :
				_fragmentEntryLinkLocalService.
					getFragmentEntryLinksBySegmentsExperienceId(
						layout.getGroupId(), segmentsExperiencesIds,
						layout.getPlid(), false)) {

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
				SegmentsExperience segmentsExperience =
					_segmentsExperienceLocalService.fetchSegmentsExperience(
						segmentsExperienceId);

				if (Objects.equals(
						segmentsExperience.getSegmentsExperienceKey(),
						SegmentsExperienceConstants.KEY_DEFAULT)) {

					segmentsExperienceIdsMap.put(
						segmentsExperience.getSegmentsExperienceId(),
						_segmentsExperienceLocalService.
							fetchDefaultSegmentsExperienceId(
								targetLayout.getPlid()));

					continue;
				}

				segmentsExperienceIdsMap.put(
					segmentsExperienceId, segmentsExperienceId);
			}

			return segmentsExperienceIdsMap;
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		for (long segmentsExperienceId : segmentsExperiencesIds) {
			SegmentsExperience segmentsExperience =
				_segmentsExperienceLocalService.fetchSegmentsExperience(
					segmentsExperienceId);

			if (Objects.equals(
					segmentsExperience.getSegmentsExperienceKey(),
					SegmentsExperienceConstants.KEY_DEFAULT)) {

				segmentsExperienceIdsMap.put(
					segmentsExperience.getSegmentsExperienceId(),
					_segmentsExperienceLocalService.
						fetchDefaultSegmentsExperienceId(
							targetLayout.getPlid()));

				continue;
			}

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
			newSegmentsExperience.setPlid(targetLayout.getPlid());

			_segmentsExperienceLocalService.addSegmentsExperience(
				newSegmentsExperience);

			segmentsExperienceIdsMap.put(
				segmentsExperience.getSegmentsExperienceId(),
				newSegmentsExperience.getSegmentsExperienceId());
		}

		return segmentsExperienceIdsMap;
	}

	private String _getTypeSettings(Layout sourceLayout, Layout targetLayout) {
		if (!sourceLayout.isDraftLayout() && !targetLayout.isDraftLayout()) {
			return sourceLayout.getTypeSettings();
		}

		if (targetLayout.isDraftLayout()) {
			UnicodeProperties typeSettingsUnicodeProperties =
				UnicodePropertiesBuilder.create(
					true
				).fastLoad(
					sourceLayout.getTypeSettings()
				).build();

			return UnicodePropertiesBuilder.create(
				true
			).fastLoad(
				sourceLayout.getTypeSettings()
			).setProperty(
				"published", Boolean.FALSE.toString()
			).setProperty(
				"query-string",
				typeSettingsUnicodeProperties.getProperty("query-string")
			).setProperty(
				"target", typeSettingsUnicodeProperties.getProperty("target")
			).setProperty(
				"targetType",
				typeSettingsUnicodeProperties.getProperty("targetType")
			).buildString();
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.create(
				true
			).fastLoad(
				targetLayout.getTypeSettings()
			).build();

		return UnicodePropertiesBuilder.create(
			true
		).fastLoad(
			sourceLayout.getTypeSettings()
		).setProperty(
			"query-string",
			typeSettingsUnicodeProperties.getProperty("query-string")
		).setProperty(
			"target", typeSettingsUnicodeProperties.getProperty("target")
		).setProperty(
			"targetType",
			typeSettingsUnicodeProperties.getProperty("targetType")
		).buildString();
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
			LayoutStructure layoutStructure, Layout targetLayout,
			Map<Long, FragmentEntryLink> fragmentEntryLinksMap,
			long targetSegmentsExperienceId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

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

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

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
			if (Objects.equals(
					_sourceLayout.getType(), LayoutConstants.TYPE_PORTLET)) {

				_sites.copyPortletPermissions(_targetLayout, _sourceLayout);
			}
			else {
				_deletePortletPermissions(
					_targetLayout, _targetSegmentsExperiencesIds);

				// LPS-108378 Copy structure before permissions and preferences

				if (_copySegmentsExperience) {
					_copyLayoutPageTemplateStructureFromSegmentsExperience(
						_sourceLayout, _sourceSegmentsExperiencesIds[0],
						_targetLayout, _targetSegmentsExperiencesIds[0]);
				}
				else {
					_copyLayoutPageTemplateStructure(
						_sourceSegmentsExperiencesIds, _sourceLayout,
						_targetLayout);
				}

				List<String> portletIds = _getLayoutPortletIds(
					_sourceLayout, _sourceSegmentsExperiencesIds);

				_copyPortletPermissions(
					portletIds, _sourceLayout, _targetLayout);

				_copyPortletPreferences(
					portletIds, _sourceLayout, _targetLayout);
			}

			// Copy classedModelUsages after copying the structure

			_copyLayoutClassedModelUsages(_sourceLayout, _targetLayout);

			_sites.copyExpandoBridgeAttributes(_sourceLayout, _targetLayout);
			_sites.copyPortletSetups(_sourceLayout, _targetLayout);

			_copyAssetCategoryIdsAndAssetTagNames(_sourceLayout, _targetLayout);

			_copyLayoutSEOEntry(_sourceLayout, _targetLayout);

			_copyLayoutClientExtensions(_sourceLayout, _targetLayout);

			Image image = _imageLocalService.getImage(
				_sourceLayout.getIconImageId());

			byte[] imageBytes = null;

			if (image != null) {
				imageBytes = image.getTextObj();
			}

			return _layoutLocalService.updateLayout(
				_targetLayout.getGroupId(), _targetLayout.isPrivateLayout(),
				_targetLayout.getLayoutId(),
				_getTypeSettings(_sourceLayout, _targetLayout), imageBytes,
				_sourceLayout.getThemeId(), _sourceLayout.getColorSchemeId(),
				_sourceLayout.getStyleBookEntryId(), _sourceLayout.getCss(),
				_sourceLayout.getFaviconFileEntryId(),
				_sourceLayout.getMasterLayoutPlid());
		}

		private CopyLayoutCallable(
			boolean copySegmentsExperience, Layout sourceLayout,
			long[] sourceSegmentsExperiencesIds, Layout targetLayout,
			long[] targetSegmentsExperiencesIds) {

			_copySegmentsExperience = copySegmentsExperience;
			_sourceLayout = sourceLayout;
			_sourceSegmentsExperiencesIds = sourceSegmentsExperiencesIds;
			_targetLayout = targetLayout;
			_targetSegmentsExperiencesIds = targetSegmentsExperiencesIds;
		}

		private void _copyLayoutClientExtensions(
				Layout sourceLayout, Layout targetLayout)
			throws Exception {

			long classNameId = _portal.getClassNameId(Layout.class);

			_clientExtensionEntryRelLocalService.deleteClientExtensionEntryRels(
				classNameId, targetLayout.getPlid());

			List<ClientExtensionEntryRel> clientExtensionEntryRels =
				_clientExtensionEntryRelLocalService.
					getClientExtensionEntryRels(
						classNameId, sourceLayout.getPlid());

			for (ClientExtensionEntryRel clientExtensionEntryRel :
					clientExtensionEntryRels) {

				_clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
					targetLayout.getUserId(), targetLayout.getGroupId(),
					classNameId, targetLayout.getPlid(),
					clientExtensionEntryRel.getCETExternalReferenceCode(),
					clientExtensionEntryRel.getType(),
					clientExtensionEntryRel.getTypeSettings(),
					ServiceContextThreadLocal.getServiceContext());
			}
		}

		private final boolean _copySegmentsExperience;
		private final Layout _sourceLayout;
		private final long[] _sourceSegmentsExperiencesIds;
		private final Layout _targetLayout;
		private final long[] _targetSegmentsExperiencesIds;

	}

}