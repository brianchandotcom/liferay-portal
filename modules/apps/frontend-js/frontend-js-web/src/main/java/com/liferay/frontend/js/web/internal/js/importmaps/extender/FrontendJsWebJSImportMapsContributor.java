/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.js.importmaps.extender;

import com.liferay.frontend.js.importmaps.extender.DynamicJSImportMapsContributor;
import com.liferay.frontend.js.web.internal.language.LanguageState;

import java.io.PrintWriter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = DynamicJSImportMapsContributor.class)
public class FrontendJsWebJSImportMapsContributor
	implements DynamicJSImportMapsContributor {

	@Override
	public void writeGlobalImports(PrintWriter printWriter) {
		LanguageState languageState = LanguageState.get();

		printWriter.write("\"@liferay/language/\":\"/o/js/language/");
		printWriter.write(languageState.getHash());
		printWriter.write("/\"");
	}

	@Override
	public void writeScopedImports(PrintWriter printWriter) {
	}

}