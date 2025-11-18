/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

/**
 * @author Mikel Lorza
 */
public class FragmentEntryReference {

	public FragmentEntryReference(
		String fragmentEntryERC, String fragmentEntryKey,
		String fragmentEntryScopeERC, String rendererKey) {

		_fragmentEntryERC = fragmentEntryERC;
		_fragmentEntryKey = fragmentEntryKey;
		_fragmentEntryScopeERC = fragmentEntryScopeERC;
		_rendererKey = rendererKey;
	}

	public String getFragmentEntryERC() {
		return _fragmentEntryERC;
	}

	public String getFragmentEntryKey() {
		return _fragmentEntryKey;
	}

	public String getFragmentEntryScopeERC() {
		return _fragmentEntryScopeERC;
	}

	public String getRendererKey() {
		return _rendererKey;
	}

	private final String _fragmentEntryERC;
	private final String _fragmentEntryKey;
	private final String _fragmentEntryScopeERC;
	private final String _rendererKey;

}