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
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletWrapper;

/**
 * @author Daniel Raposo
 */
public class MissingPortlet extends PortletWrapper {

	public MissingPortlet(String className, String name, Portlet portlet) {
		super(portlet);

		_portletDataHandler = new MissingPortletDataHandler(className, name);
	}

	@Override
	public PortletDataHandler getPortletDataHandlerInstance() {
		return _portletDataHandler;
	}

	public static class MissingPortletDataHandler
		extends BasePortletDataHandler {

		public MissingPortletDataHandler(String className, String name) {
			setEmptyControlsAllowed(true);
			setPublishToLiveByDefault(true);

			_className = className;
			_name = name;
		}

		@Override
		public String getName() {
			return _name;
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
						null, _className, null, true, false, null, _className,
						null)
				});
		}

		private final String _className;
		private final String _name;

	}

	private final PortletDataHandler _portletDataHandler;

}