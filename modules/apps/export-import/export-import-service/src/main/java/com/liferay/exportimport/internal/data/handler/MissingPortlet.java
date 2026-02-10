/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.data.handler;

import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletWrapper;

import java.util.Locale;

/**
 * @author Daniel Raposo
 */
public class MissingPortlet extends PortletWrapper {

	public MissingPortlet(
		Portlet portlet, String portletDataHandlerKey, String portletId,
		String title) {

		super(portlet);

		_portletDataHandler = new MissingPortletDataHandler(
			portletDataHandlerKey, portletId, title);
	}

	@Override
	public PortletDataHandler getPortletDataHandlerInstance() {
		return _portletDataHandler;
	}

	public static class MissingPortletDataHandler
		extends BasePortletDataHandler {

		public MissingPortletDataHandler(
			String portletDataHandlerKey, String portletId, String title) {

			setEmptyControlsAllowed(true);
			setPublishToLiveByDefault(true);

			_portletDataHandlerKey = portletDataHandlerKey;
			_portletId = portletId;
			_title = title;
		}

		@Override
		public StagedModelType[] getDeletionSystemEventStagedModelTypes() {
			return new StagedModelType[] {
				new StagedModelType(_portletDataHandlerKey)
			};
		}

		@Override
		public String getTitle(Locale locale) {
			return _title;
		}

		@Override
		public boolean isBatch() {
			return true;
		}

		@Override
		protected long getExportModelCount(
			ManifestSummary manifestSummary,
			PortletDataHandlerControl[] portletDataHandlerControls) {

			return super.getExportModelCount(
				manifestSummary,
				new PortletDataHandlerControl[] {
					new PortletDataHandlerBoolean(
						_portletId, _portletDataHandlerKey, null, true, false,
						null, _portletDataHandlerKey, null)
				});
		}

		private final String _portletDataHandlerKey;
		private final String _portletId;
		private final String _title;

	}

	private final PortletDataHandler _portletDataHandler;

}