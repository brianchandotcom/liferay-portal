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

import com.liferay.asset.model.AssetEntryUsage;
import com.liferay.asset.service.AssetEntryUsageLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperienceModel;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.sites.kernel.util.Sites;

import java.util.Date;
import java.util.List;
import java.util.Map;
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

		_assetEntryUsageLocalService.deleteAssetEntryUsagesByPlid(
			targetLayout.getPlid());

		List<AssetEntryUsage> assetEntryUsages =
			_assetEntryUsageLocalService.getAssetEntryUsagesByPlid(
				sourceLayout.getPlid());

		for (AssetEntryUsage assetEntryUsage : assetEntryUsages) {
			_assetEntryUsageLocalService.addAssetEntryUsage(
				assetEntryUsage.getGroupId(), assetEntryUsage.getAssetEntryId(),
				assetEntryUsage.getContainerType(),
				assetEntryUsage.getContainerKey(), targetLayout.getPlid(),
				serviceContext);
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

		JSONObject dataJSONObject = JSONFactoryUtil.createJSONObject(data);

		JSONArray structureJSONArray = dataJSONObject.getJSONArray("structure");

		if (structureJSONArray == null) {
			return;
		}

		for (int i = 0; i < structureJSONArray.length(); i++) {
			JSONObject rowJSONObject = structureJSONArray.getJSONObject(i);

			JSONArray columnsJSONArray = rowJSONObject.getJSONArray("columns");

			for (int j = 0; j < columnsJSONArray.length(); j++) {
				JSONObject columnJSONObject = columnsJSONArray.getJSONObject(j);

				JSONArray fragmentEntryLinkIdsJSONArray =
					columnJSONObject.getJSONArray("fragmentEntryLinkIds");

				JSONArray newFragmentEntryLinkIdsJSONArray =
					JSONFactoryUtil.createJSONArray();

				for (int k = 0; k < fragmentEntryLinkIdsJSONArray.length();
					 k++) {

					FragmentEntryLink fragmentEntryLink =
						fragmentEntryLinkMap.get(
							fragmentEntryLinkIdsJSONArray.getLong(k));

					if (fragmentEntryLink == null) {
						continue;
					}

					FragmentEntryLink newFragmentEntryLink =
						(FragmentEntryLink)fragmentEntryLink.clone();

					newFragmentEntryLink.setUuid(serviceContext.getUuid());
					newFragmentEntryLink.setFragmentEntryLinkId(
						_counterLocalService.increment());
					newFragmentEntryLink.setUserId(targetLayout.getUserId());
					newFragmentEntryLink.setUserName(
						targetLayout.getUserName());
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

					newFragmentEntryLinkIdsJSONArray.put(
						newFragmentEntryLink.getFragmentEntryLinkId());

					_commentManager.copyDiscussion(
						targetLayout.getUserId(), targetLayout.getGroupId(),
						FragmentEntryLink.class.getName(),
						fragmentEntryLink.getFragmentEntryLinkId(),
						newFragmentEntryLink.getFragmentEntryLinkId(),
						className -> serviceContext);
				}

				columnJSONObject.put(
					"fragmentEntryLinkIds", newFragmentEntryLinkIdsJSONArray);
			}
		}

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructure(
				targetLayout.getGroupId(), classNameId, targetLayout.getPlid(),
				segmentsExperienceId, dataJSONObject.toString());
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

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private AssetEntryUsageLocalService _assetEntryUsageLocalService;

	@Reference
	private CommentManager _commentManager;

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private ImageLocalService _imageLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutSEOEntryLocalService _layoutSEOEntryLocalService;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference
	private Sites _sites;

	private class CopyLayoutCallable implements Callable<Layout> {

		@Override
		public Layout call() throws Exception {
			_sites.copyLookAndFeel(_targetLayout, _sourceLayout);
			_sites.copyPortletSetups(_sourceLayout, _targetLayout);
			_sites.copyPortletPermissions(_targetLayout, _sourceLayout);

			_copyLayoutPageTemplateStructure(_sourceLayout, _targetLayout);

			_copyPortletPreferences(_sourceLayout, _targetLayout);

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

			_layoutLocalService.updateLayout(
				_targetLayout.getGroupId(), _targetLayout.isPrivateLayout(),
				_targetLayout.getLayoutId(), _sourceLayout.getTypeSettings());

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
					_targetLayout.getLayoutId(), layoutSEOEntry.isEnabled(),
					layoutSEOEntry.getCanonicalURLMap(), serviceContext);
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