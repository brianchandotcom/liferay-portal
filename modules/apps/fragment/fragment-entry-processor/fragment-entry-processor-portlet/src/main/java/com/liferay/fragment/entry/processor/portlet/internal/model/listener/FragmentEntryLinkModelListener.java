/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.entry.processor.portlet.internal.model.listener;

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.service.LayoutClassedModelUsageLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;

import java.util.List;
import java.util.Objects;

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

		for (String portletId :
				_portletRegistry.getFragmentEntryLinkPortletIds(
					fragmentEntryLink)) {

			try {
				_portletLocalService.deletePortlet(
					fragmentEntryLink.getCompanyId(), portletId,
					fragmentEntryLink.getPlid());

				LayoutPageTemplateEntry layoutPageTemplateEntry =
					_layoutPageTemplateEntryLocalService.
						fetchLayoutPageTemplateEntryByPlid(
							fragmentEntryLink.getPlid());

				if ((layoutPageTemplateEntry != null) &&
					Objects.equals(
						layoutPageTemplateEntry.getType(),
						LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT)) {

					long[] excludedPlids = {
						PortletKeys.PREFS_PLID_SHARED,
						fragmentEntryLink.getPlid()
					};

					Layout masterDraftLayout = _layoutLocalService.fetchLayout(
						_portal.getClassNameId(Layout.class),
						fragmentEntryLink.getPlid());

					if (masterDraftLayout != null) {
						excludedPlids = ArrayUtil.append(
							excludedPlids, masterDraftLayout.getPlid());
					}

					List<PortletPreferences> portletPreferences =
						_portletPreferencesLocalService.
							getPortletPreferencesByPortletId(portletId);

					for (PortletPreferences curPortletPreferences :
							portletPreferences) {

						if (ArrayUtil.contains(
								excludedPlids,
								curPortletPreferences.getPlid())) {

							continue;
						}

						_portletPreferencesLocalService.
							deletePortletPreferences(
								curPortletPreferences.
									getPortletPreferencesId());
					}
				}

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

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryLinkModelListener.class);

	@Reference
	private LayoutClassedModelUsageLocalService
		_layoutClassedModelUsageLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private PortletRegistry _portletRegistry;

}