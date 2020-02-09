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
import com.liferay.layout.util.structure.FragmentLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperienceModel;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.sites.kernel.util.Sites;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
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

		Callable<Layout> callable = new CopyLayoutCallable(
			sourceLayout, targetLayout);

		try {
			return TransactionInvokerUtil.invoke(_transactionConfig, callable);
		}
		catch (Throwable t) {
			throw new Exception(t);
		}
	}

	private void _copyAssetCategoryIdsAndAssetTagNames(
			Layout sourceLayout, Layout targetLayout)
		throws PortalException {

		if (_isDraft(sourceLayout)) {
			return;
		}

		long[] assetCategoryIds = _assetCategoryLocalService.getCategoryIds(
			Layout.class.getName(), sourceLayout.getPlid());

		String[] assetTagNames = _assetTagLocalService.getTagNames(
			Layout.class.getName(), sourceLayout.getPlid());

		Layout layout = targetLayout;

		if (_isDraft(targetLayout)) {
			layout = _layoutLocalService.getLayout(targetLayout.getClassPK());
		}

		_layoutLocalService.updateAsset(
			layout.getUserId(), layout, assetCategoryIds, assetTagNames);
	}

	private void _copyLayoutPageTemplateStructure(
			Layout sourceLayout, Layout targetLayout)
		throws Exception {

		long classNameId = _portal.getClassNameId(Layout.class);

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					sourceLayout.getGroupId(), classNameId,
					sourceLayout.getPlid());

		if (layoutPageTemplateStructure == null) {
			LayoutPageTemplateStructure targetLayoutPageTemplateStructure =
				_layoutPageTemplateStructureLocalService.
					fetchLayoutPageTemplateStructure(
						targetLayout.getGroupId(), classNameId,
						targetLayout.getPlid());

			if (targetLayoutPageTemplateStructure != null) {
				_layoutPageTemplateStructureLocalService.
					deleteLayoutPageTemplateStructure(
						targetLayoutPageTemplateStructure);
			}

			_fragmentEntryLinkLocalService.
				deleteLayoutPageTemplateEntryFragmentEntryLinks(
					targetLayout.getGroupId(), classNameId,
					targetLayout.getPlid());

			layoutPageTemplateStructure =
				_layoutPageTemplateStructureLocalService.
					rebuildLayoutPageTemplateStructure(
						sourceLayout.getGroupId(), classNameId,
						sourceLayout.getPlid());
		}

		ServiceContext serviceContext = Optional.ofNullable(
			ServiceContextThreadLocal.getServiceContext()
		).orElse(
			new ServiceContext()
		);

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinks(
				sourceLayout.getGroupId(), classNameId, sourceLayout.getPlid());

		Stream<FragmentEntryLink> stream = fragmentEntryLinks.stream();

		Map<Long, FragmentEntryLink> fragmentEntryLinkMap = stream.collect(
			Collectors.toMap(
				FragmentEntryLink::getFragmentEntryLinkId,
				fragmentEntryLink -> fragmentEntryLink));

		_fragmentEntryLinkLocalService.
			deleteLayoutPageTemplateEntryFragmentEntryLinks(
				targetLayout.getGroupId(), classNameId, targetLayout.getPlid());

		LayoutPageTemplateStructure targetLayoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					targetLayout.getGroupId(), classNameId,
					targetLayout.getPlid());

		if (targetLayoutPageTemplateStructure == null) {
			_layoutPageTemplateStructureLocalService.
				addLayoutPageTemplateStructure(
					targetLayout.getUserId(), targetLayout.getGroupId(),
					classNameId, targetLayout.getPlid(), null, serviceContext);
		}

		for (Long segmentsExperienceId :
				_getSegmentsExperienceIds(
					sourceLayout.getGroupId(), classNameId,
					sourceLayout.getPlid())) {

			_copyLayoutPageTemplateStructureExperience(
				layoutPageTemplateStructure, segmentsExperienceId, classNameId,
				targetLayout, fragmentEntryLinkMap, serviceContext);
		}

		_layoutClassedModelUsageLocalService.
			deleteLayoutClassedModelUsagesByPlid(targetLayout.getPlid());

		List<LayoutClassedModelUsage> layoutClassedModelUsages =
			_layoutClassedModelUsageLocalService.
				getLayoutClassedModelUsagesByPlid(sourceLayout.getPlid());

		for (LayoutClassedModelUsage layoutClassedModelUsage :
				layoutClassedModelUsages) {

			_layoutClassedModelUsageLocalService.addLayoutClassedModelUsage(
				layoutClassedModelUsage.getGroupId(),
				layoutClassedModelUsage.getClassNameId(),
				layoutClassedModelUsage.getClassPK(),
				layoutClassedModelUsage.getContainerKey(),
				layoutClassedModelUsage.getContainerType(),
				targetLayout.getPlid(), serviceContext);
		}
	}

	private void _copyLayoutPageTemplateStructureExperience(
			LayoutPageTemplateStructure layoutPageTemplateStructure,
			long segmentsExperienceId, long classNameId, Layout targetLayout,
			Map<Long, FragmentEntryLink> fragmentEntryLinkMap,
			ServiceContext serviceContext)
		throws Exception {

		String data = layoutPageTemplateStructure.getData(segmentsExperienceId);

		if (Validator.isNull(data)) {
			return;
		}

		JSONObject dataJSONObject = _processDataJSONObject(
			data, classNameId, targetLayout, fragmentEntryLinkMap,
			serviceContext);

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructure(
				targetLayout.getGroupId(), classNameId, targetLayout.getPlid(),
				segmentsExperienceId, dataJSONObject.toString());
	}

	private void _copyPortletPermissions(
			Layout sourceLayout, Layout targetLayout)
		throws Exception {

		if (Objects.equals(
				sourceLayout.getType(), LayoutConstants.TYPE_PORTLET)) {

			_sites.copyPortletPermissions(targetLayout, sourceLayout);

			return;
		}

		if (!(sourceLayout.isTypeAssetDisplay() ||
			  sourceLayout.isTypeContent())) {

			return;
		}

		_deletePortletPermissions(targetLayout);

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinks(
				sourceLayout.getGroupId(),
				_portal.getClassNameId(Layout.class.getName()),
				sourceLayout.getPlid());

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			List<String> portletIds =
				_portletRegistry.getFragmentEntryLinkPortletIds(
					fragmentEntryLink);

			for (String portletId : portletIds) {
				Group targetGroup = targetLayout.getGroup();
				String resourceName = PortletIdCodec.decodePortletName(
					portletId);
				String sourceResourcePrimKey =
					PortletPermissionUtil.getPrimaryKey(
						sourceLayout.getPlid(), portletId);
				String targetResourcePrimKey =
					PortletPermissionUtil.getPrimaryKey(
						targetLayout.getPlid(), portletId);
				List<String> actionIds =
					ResourceActionsUtil.getPortletResourceActions(resourceName);

				List<Role> roles = _roleLocalService.getGroupRelatedRoles(
					targetLayout.getGroupId());

				for (Role role : roles) {
					String roleName = role.getName();

					if (roleName.equals(RoleConstants.ADMINISTRATOR) ||
						(!targetGroup.isLayoutSetPrototype() &&
						 targetLayout.isPrivateLayout() &&
						 roleName.equals(RoleConstants.GUEST))) {

						continue;
					}

					List<String> actions =
						_resourcePermissionLocalService.
							getAvailableResourcePermissionActionIds(
								targetLayout.getCompanyId(), resourceName,
								ResourceConstants.SCOPE_INDIVIDUAL,
								sourceResourcePrimKey, role.getRoleId(),
								actionIds);

					_resourcePermissionLocalService.setResourcePermissions(
						targetLayout.getCompanyId(), resourceName,
						ResourceConstants.SCOPE_INDIVIDUAL,
						targetResourcePrimKey, role.getRoleId(),
						actions.toArray(new String[0]));
				}
			}
		}
	}

	private void _copyPortletPreferences(
		Layout sourceLayout, Layout targetLayout) {

		List<PortletPreferences> portletPreferencesList =
			_portletPreferencesLocalService.getPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, sourceLayout.getPlid());

		_portletPreferencesLocalService.deletePortletPreferences(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, targetLayout.getPlid());

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			Portlet portlet = _portletLocalService.getPortletById(
				portletPreferences.getPortletId());

			if ((portlet == null) || portlet.isUndeployedPortlet()) {
				continue;
			}

			PortletPreferences targetPortletPreferences =
				_portletPreferencesLocalService.fetchPortletPreferences(
					PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, targetLayout.getPlid(),
					portletPreferences.getPortletId());

			if (targetPortletPreferences != null) {
				targetPortletPreferences.setPreferences(
					portletPreferences.getPreferences());

				_portletPreferencesLocalService.updatePortletPreferences(
					targetPortletPreferences);
			}
			else {
				_portletPreferencesLocalService.addPortletPreferences(
					targetLayout.getCompanyId(),
					PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, targetLayout.getPlid(),
					portletPreferences.getPortletId(),
					_portletLocalService.getPortletById(
						portletPreferences.getPortletId()),
					portletPreferences.getPreferences());
			}
		}
	}

	private void _deletePortletPermissions(Layout layout) throws Exception {
		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinks(
				layout.getGroupId(),
				_portal.getClassNameId(Layout.class.getName()),
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

	private long[] _getSegmentsExperienceIds(
		long groupId, long classNameId, long classPK) {

		List<SegmentsExperience> segmentsExperiences =
			_segmentsExperienceLocalService.getSegmentsExperiences(
				groupId, classNameId, classPK);

		Stream<SegmentsExperience> stream = segmentsExperiences.stream();

		return ArrayUtil.append(
			stream.mapToLong(
				SegmentsExperienceModel::getSegmentsExperienceId
			).toArray(),
			new long[] {SegmentsExperienceConstants.ID_DEFAULT});
	}

	private boolean _isDraft(Layout layout) {
		if (layout.getClassPK() <= 0) {
			return false;
		}

		if (layout.getClassNameId() != _portal.getClassNameId(
				Layout.class.getName())) {

			return false;
		}

		return true;
	}

	private JSONObject _processDataJSONObject(
			String data, long classNameId, Layout targetLayout,
			Map<Long, FragmentEntryLink> fragmentEntryLinkMap,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutStructure layoutStructure = LayoutStructure.of(data);

		for (LayoutStructureItem layoutStructureItem :
				layoutStructure.getLayoutStructureItems()) {

			if (!(layoutStructureItem instanceof FragmentLayoutStructureItem)) {
				continue;
			}

			FragmentLayoutStructureItem fragmentLayoutStructureItem =
				(FragmentLayoutStructureItem)layoutStructureItem;

			FragmentEntryLink fragmentEntryLink = fragmentEntryLinkMap.get(
				fragmentLayoutStructureItem.getFragmentEntryLinkId());

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
			newFragmentEntryLink.setOriginalFragmentEntryLinkId(0);
			newFragmentEntryLink.setClassNameId(classNameId);
			newFragmentEntryLink.setClassPK(targetLayout.getPlid());
			newFragmentEntryLink.setLastPropagationDate(
				serviceContext.getCreateDate(new Date()));

			newFragmentEntryLink =
				_fragmentEntryLinkLocalService.addFragmentEntryLink(
					newFragmentEntryLink);

			fragmentLayoutStructureItem.setFragmentEntryLinkId(
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
			_sites.copyExpandoBridgeAttributes(_sourceLayout, _targetLayout);
			_sites.copyLookAndFeel(_targetLayout, _sourceLayout);
			_sites.copyPortletSetups(_sourceLayout, _targetLayout);

			_copyAssetCategoryIdsAndAssetTagNames(_sourceLayout, _targetLayout);

			_copyPortletPermissions(_sourceLayout, _targetLayout);

			_copyPortletPreferences(_sourceLayout, _targetLayout);

			_copyLayoutPageTemplateStructure(_sourceLayout, _targetLayout);

			_layoutLocalService.updateMasterLayoutPlid(
				_targetLayout.getGroupId(), _targetLayout.isPrivateLayout(),
				_targetLayout.getLayoutId(),
				_sourceLayout.getMasterLayoutPlid());

			UnicodeProperties properties = new UnicodeProperties();

			properties.load(_sourceLayout.getTypeSettings());

			if ((_sourceLayout.getClassNameId() == _portal.getClassNameId(
					Layout.class)) &&
				(_targetLayout.getPlid() == _sourceLayout.getClassPK())) {

				properties.put("published", Boolean.TRUE.toString());

				_layoutLocalService.updateLayout(
					_sourceLayout.getGroupId(), _sourceLayout.isPrivateLayout(),
					_sourceLayout.getLayoutId(), properties.toString());
			}

			_layoutLocalService.updateLayout(
				_targetLayout.getGroupId(), _targetLayout.isPrivateLayout(),
				_targetLayout.getLayoutId(), properties.toString());

			_targetLayout.setType(_sourceLayout.getType());

			Image image = _imageLocalService.getImage(
				_sourceLayout.getIconImageId());

			byte[] imageBytes = null;

			if (image != null) {
				imageBytes = image.getTextObj();
			}

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			if (serviceContext == null) {
				serviceContext = new ServiceContext();
			}

			serviceContext.setAttribute(
				"layout.instanceable.allowed", Boolean.TRUE);

			LayoutSEOEntry layoutSEOEntry =
				_layoutSEOEntryLocalService.fetchLayoutSEOEntry(
					_sourceLayout.getGroupId(), _sourceLayout.isPrivateLayout(),
					_sourceLayout.getLayoutId());

			if (layoutSEOEntry == null) {
				LayoutSEOEntry targetLayoutSEOEntry =
					_layoutSEOEntryLocalService.fetchLayoutSEOEntry(
						_targetLayout.getGroupId(),
						_targetLayout.isPrivateLayout(),
						_targetLayout.getLayoutId());

				if (targetLayoutSEOEntry != null) {
					_layoutSEOEntryLocalService.deleteLayoutSEOEntry(
						_targetLayout.getGroupId(),
						_targetLayout.isPrivateLayout(),
						_targetLayout.getLayoutId());
				}
			}
			else {
				_layoutSEOEntryLocalService.updateLayoutSEOEntry(
					_targetLayout.getUserId(), _targetLayout.getGroupId(),
					_targetLayout.isPrivateLayout(),
					_targetLayout.getLayoutId(),
					layoutSEOEntry.isCanonicalURLEnabled(),
					layoutSEOEntry.getCanonicalURLMap(),
					layoutSEOEntry.isOpenGraphDescriptionEnabled(),
					layoutSEOEntry.getOpenGraphDescriptionMap(),
					layoutSEOEntry.getOpenGraphImageAltMap(),
					layoutSEOEntry.getOpenGraphImageFileEntryId(),
					layoutSEOEntry.isOpenGraphTitleEnabled(),
					layoutSEOEntry.getOpenGraphTitleMap(), serviceContext);
			}

			return _layoutLocalService.updateIconImage(
				_targetLayout.getPlid(), imageBytes);
		}

		private CopyLayoutCallable(Layout sourceLayout, Layout targetLayout) {
			_sourceLayout = sourceLayout;
			_targetLayout = targetLayout;
		}

		private final Layout _sourceLayout;
		private final Layout _targetLayout;

	}

}