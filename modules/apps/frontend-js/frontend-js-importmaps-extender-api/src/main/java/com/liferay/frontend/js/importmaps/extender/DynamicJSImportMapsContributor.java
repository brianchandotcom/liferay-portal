/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.importmaps.extender;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.Writer;

/**
 * A low level version of {@code JSImportMapsContributor} that allows changing
 * the import maps dynamically every time they are rendered.
 *
 * NOTE: For static import maps it's safer and faster to implement
 * {@code JSImportMapsContributor} instead of this interface.
 *
 * @author Iván Zaera Avellón
 */
public interface DynamicJSImportMapsContributor {

	public void writeGlobalImports(
			HttpServletRequest httpServletRequest, Writer writer)
		throws IOException;

	public void writeScopedImports(
			HttpServletRequest httpServletRequest, Writer writer)
		throws IOException;

}