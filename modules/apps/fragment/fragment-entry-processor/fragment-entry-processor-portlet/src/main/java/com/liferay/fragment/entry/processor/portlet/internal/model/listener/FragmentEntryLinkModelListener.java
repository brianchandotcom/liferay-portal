/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.entry.processor.portlet.internal.model.listener;

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ModelListener.class)
public class FragmentEntryLinkModelListener
	extends BaseModelListener<FragmentEntryLink> {

	@Override
	public void onAfterRemove(FragmentEntryLink fragmentEntryLink)
		throws ModelListenerException {

		if (ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		List<String> portletIds =
			_portletRegistry.getFragmentEntryLinkPortletIds(fragmentEntryLink);

		List<String> usedPortletNames = _getUsedNoninstanceablePortletNames(
			fragmentEntryLink, portletIds);

		for (String portletId : portletIds) {
			if (usedPortletNames.contains(
					PortletIdCodec.decodePortletName(portletId))) {

				continue;
			}

			try {
				_portletLocalService.deletePortlet(
					fragmentEntryLink.getCompanyId(), portletId,
					fragmentEntryLink.getPlid());

				_layoutClassedModelUsageLocalService.
					deleteLayoutClassedModelUsages(
						portletId, _portal.getClassNameId(Portlet.class),
						fragmentEntryLink.getPlid());
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}
	}

	private List<String> _getUsedNoninstanceablePortletNames(
			FragmentEntryLink fragmentEntryLink, List<String> portletIds)
		throws ModelListenerException {

		List<String> portletNames = TransformUtil.transform(
			portletIds,
			portletId -> {
				Portlet portlet = _portletLocalService.getPortletById(
					fragmentEntryLink.getCompanyId(), portletId);

				if (portlet.isInstanceable()) {
					return null;
				}

				return PortletIdCodec.decodePortletName(portletId);
			});

		if (ListUtil.isEmpty(portletNames)) {
			return Collections.emptyList();
		}

		List<String> usedPortletNames = new ArrayList<>();

		for (FragmentEntryLink curFragmentEntryLink :
				_fragmentEntryLinkLocalService.
					getFragmentEntryLinksBySegmentsExperienceId(
						fragmentEntryLink.getGroupId(),
						fragmentEntryLink.getSegmentsExperienceId(),
						fragmentEntryLink.getPlid(), false)) {

			if (curFragmentEntryLink.getFragmentEntryLinkId() ==
					fragmentEntryLink.getFragmentEntryLinkId()) {

				continue;
			}

			for (String portletId :
					_portletRegistry.getFragmentEntryLinkPortletIds(
						null, curFragmentEntryLink)) {

				String portletName = PortletIdCodec.decodePortletName(
					portletId);

				if (portletNames.contains(portletName)) {
					usedPortletNames.add(portletName);
				}
			}
		}

		return usedPortletNames;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryLinkModelListener.class);

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletRegistry _portletRegistry;

}