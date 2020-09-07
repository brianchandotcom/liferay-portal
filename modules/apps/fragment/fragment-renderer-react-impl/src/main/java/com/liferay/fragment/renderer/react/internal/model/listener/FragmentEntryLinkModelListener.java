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

package com.liferay.fragment.renderer.react.internal.model.listener;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.ModuleNameUtil;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistryUpdate;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = ModelListener.class)
public class FragmentEntryLinkModelListener
	extends BaseModelListener<FragmentEntryLink> {

	@Override
	public void onAfterCreate(FragmentEntryLink fragmentEntryLink) {
		if (!_isReact(fragmentEntryLink)) {
			return;
		}

		NPMRegistryUpdate npmRegistryUpdate = _npmRegistry.update();

		npmRegistryUpdate.registerJSModule(
			_jsPackage, _getModuleName(fragmentEntryLink), _dependencies,
			_getJs(fragmentEntryLink), null);

		npmRegistryUpdate.finish();
	}

	@Override
	public void onAfterRemove(FragmentEntryLink fragmentEntryLink) {
		if (!_isReact(fragmentEntryLink)) {
			return;
		}

		NPMRegistryUpdate npmRegistryUpdate = _npmRegistry.update();

		npmRegistryUpdate.unregisterJSModule(
			_jsPackage.getJSModule(_getModuleName(fragmentEntryLink)));

		npmRegistryUpdate.finish();
	}

	@Override
	public void onAfterUpdate(FragmentEntryLink fragmentEntryLink) {
		if (!_isReact(fragmentEntryLink)) {
			return;
		}

		NPMRegistryUpdate npmRegistryUpdate = _npmRegistry.update();

		JSModule jsModule = _jsPackage.getJSModule(
			_getModuleName(fragmentEntryLink));

		npmRegistryUpdate.updateJSModule(
			jsModule, _dependencies, _getJs(fragmentEntryLink), null);

		npmRegistryUpdate.finish();
	}

	@Activate
	protected void activate() {
		_jsPackage = _npmResolver.getJSPackage();

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinks(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		NPMRegistryUpdate npmRegistryUpdate = _npmRegistry.update();

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			if (!_isReact(fragmentEntryLink)) {
				continue;
			}

			npmRegistryUpdate.registerJSModule(
				_jsPackage, _getModuleName(fragmentEntryLink), _dependencies,
				_getJs(fragmentEntryLink), null);
		}

		npmRegistryUpdate.finish();
	}

	private String _getJs(FragmentEntryLink fragmentEntryLink) {
		return StringUtil.replace(
			fragmentEntryLink.getJs(), "'__FRAGMENT_MODULE_NAME__'",
			StringBundler.concat(
				StringPool.APOSTROPHE,
				ModuleNameUtil.getModuleResolvedId(
					_jsPackage, _getModuleName(fragmentEntryLink)),
				StringPool.APOSTROPHE));
	}

	private String _getModuleName(FragmentEntryLink fragmentEntryLink) {
		return "fragmentEntryLink/" +
			fragmentEntryLink.getFragmentEntryLinkId();
	}

	private boolean _isReact(FragmentEntryLink fragmentEntryLink) {
		String fragmentTypeHeader = "//# fragmentType=";

		String js = fragmentEntryLink.getJs();

		if (Validator.isNotNull(js) && js.startsWith(fragmentTypeHeader)) {
			String fragmentType = StringUtil.extractLast(
				StringUtil.extractFirst(js, StringPool.NEW_LINE),
				fragmentTypeHeader);

			return fragmentType.equals("react");
		}

		return false;
	}

	private static final List<String> _dependencies = Arrays.asList(
		"frontend-js-react-web$react");

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	private JSPackage _jsPackage;

	@Reference
	private NPMRegistry _npmRegistry;

	@Reference
	private NPMResolver _npmResolver;

}