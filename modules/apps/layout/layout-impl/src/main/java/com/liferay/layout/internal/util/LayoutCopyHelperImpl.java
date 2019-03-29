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

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.sites.kernel.util.Sites;

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

			return;
		}

		String data = layoutPageTemplateStructure.getData();

		if (Validator.isNull(data)) {
			return;
		}

		JSONObject dataJSONObject = JSONFactoryUtil.createJSONObject(data);

		JSONArray structureJSONArray = dataJSONObject.getJSONArray("structure");

		if (structureJSONArray == null) {
			return;
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
						_fragmentEntryLinkLocalService.addFragmentEntryLink(
							targetLayout.getUserId(), targetLayout.getGroupId(),
							0, fragmentEntryLink.getFragmentEntryId(),
							classNameId, targetLayout.getPlid(),
							fragmentEntryLink.getCss(),
							fragmentEntryLink.getHtml(),
							fragmentEntryLink.getJs(),
							fragmentEntryLink.getEditableValues(),
							fragmentEntryLink.getPosition(), serviceContext);

					newFragmentEntryLinkIdsJSONArray.put(
						newFragmentEntryLink.getFragmentEntryLinkId());
				}

				columnJSONObject.put(
					"fragmentEntryLinkIds", newFragmentEntryLinkIdsJSONArray);
			}
		}

		LayoutPageTemplateStructure targetLayoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					targetLayout.getGroupId(), classNameId,
					targetLayout.getPlid());

		if (targetLayoutPageTemplateStructure != null) {
			_layoutPageTemplateStructureLocalService.
				updateLayoutPageTemplateStructure(
					targetLayout.getGroupId(), classNameId,
					targetLayout.getPlid(), dataJSONObject.toString());
		}
		else {
			_layoutPageTemplateStructureLocalService.
				addLayoutPageTemplateStructure(
					targetLayout.getUserId(), targetLayout.getGroupId(),
					classNameId, targetLayout.getPlid(),
					dataJSONObject.toString(), serviceContext);
		}
	}

	private void _copyPortletPreferences(
		Layout sourceLayout, Layout targetLayout) {

		List<PortletPreferences> portletPreferencesList =
			_portletPreferencesLocalService.getPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, sourceLayout.getPlid());

		for (PortletPreferences portletPreferences : portletPreferencesList) {
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

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

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

			return _layoutLocalService.getLayout(_targetLayout.getPlid());
		}

		private CopyLayoutCallable(Layout sourceLayout, Layout targetLayout) {
			_sourceLayout = sourceLayout;
			_targetLayout = targetLayout;
		}

		private final Layout _sourceLayout;
		private final Layout _targetLayout;

	}

}