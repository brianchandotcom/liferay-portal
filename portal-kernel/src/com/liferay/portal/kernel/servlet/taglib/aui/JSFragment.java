/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet.taglib.aui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Iván Zaera Avellón
 */
public class JSFragment {

	public JSFragment(
		Collection<AMDRequire> amdRequires, Collection<String> auiUses,
		String code, Collection<ESImport> esImports) {

		if (esImports != null) {
			_esImports.addAll(esImports);
		}

		if (amdRequires != null) {
			_amdRequires.addAll(amdRequires);
		}

		if (auiUses != null) {
			_auiUses.addAll(auiUses);
		}

		_code = code;
	}

	public JSFragment(
		Collection<AMDRequire> amdRequires, String code,
		Collection<ESImport> esImports) {

		this(amdRequires, null, code, esImports);
	}

	public JSFragment(String code) {
		this(null, null, code, null);
	}

	public JSFragment(String code, Collection<ESImport> esImports) {
		this(null, null, code, esImports);
	}

	public List<AMDRequire> getAMDRequires() {
		return _amdRequires;
	}

	public List<String> getAUIUses() {
		return _auiUses;
	}

	public String getCode() {
		return _code;
	}

	public List<ESImport> getESImports() {
		return _esImports;
	}

	public boolean isRaw() {
		if (_amdRequires.isEmpty() && _auiUses.isEmpty() &&
			_esImports.isEmpty()) {

			return true;
		}

		return false;
	}

	private final List<AMDRequire> _amdRequires = new ArrayList<>();
	private final List<String> _auiUses = new ArrayList<>();
	private final String _code;
	private final List<ESImport> _esImports = new ArrayList<>();

}