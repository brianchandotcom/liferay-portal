/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.renderer.react.internal.helper;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.react.internal.util.FragmentEntryFragmentRendererReactUtil;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistryUpdate;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = FragmentEntryLinkJSModuleInitializerHelper.class)
public class FragmentEntryLinkJSModuleInitializerHelper {

	public void ensureInitialized() {
		if (_initialized) {
			return;
		}

		synchronized (this) {
			if (_initialized) {
				return;
			}

			JSPackage jsPackage = _npmResolver.getJSPackage();

			if (jsPackage == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to initialize because JS package is null");
				}

				return;
			}

			List<FragmentEntryLink> fragmentEntryLinks =
				_fragmentEntryLinkLocalService.getFragmentEntryLinks(
					FragmentConstants.TYPE_REACT, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);

			NPMRegistryUpdate npmRegistryUpdate = _npmRegistry.update();

			for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
				npmRegistryUpdate.registerJSModule(
					jsPackage,
					FragmentEntryFragmentRendererReactUtil.getModuleName(
						fragmentEntryLink),
					FragmentEntryFragmentRendererReactUtil.getDependencies(),
					FragmentEntryFragmentRendererReactUtil.getJs(
						fragmentEntryLink, jsPackage),
					null);
			}

			npmRegistryUpdate.finish();

			_initialized = true;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryLinkJSModuleInitializerHelper.class);

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	private volatile boolean _initialized;

	@Reference
	private NPMRegistry _npmRegistry;

	@Reference
	private NPMResolver _npmResolver;

}