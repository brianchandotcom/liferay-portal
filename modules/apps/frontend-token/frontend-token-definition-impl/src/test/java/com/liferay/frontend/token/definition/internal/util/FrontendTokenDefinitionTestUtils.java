/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.token.definition.internal.util;

import com.liferay.client.extension.type.ThemeCSSCET;

import java.util.Date;
import java.util.Locale;
import java.util.Properties;

/**
 * @author Anderson Luiz
 * @author Thiago Buarque
 */
public class FrontendTokenDefinitionTestUtils {

	public static ThemeCSSCET newDummyThemeCSSCET() {
		return new ThemeCSSCET() {

			@Override
			public String getBaseURL() {
				return null;
			}

			@Override
			public String getClayURL() {
				return null;
			}

			@Override
			public long getCompanyId() {
				return 123;
			}

			@Override
			public Date getCreateDate() {
				return null;
			}

			@Override
			public String getDescription() {
				return null;
			}

			@Override
			public String getEditJSP() {
				return null;
			}

			@Override
			public String getExternalReferenceCode() {
				return "any-external-reference-code";
			}

			@Override
			public String getFrontendTokenDefinition() {
				return "{}";
			}

			@Override
			public String getFrontendTokenDefinitionFileName() {
				return null;
			}

			@Override
			public String getMainURL() {
				return null;
			}

			@Override
			public Date getModifiedDate() {
				return null;
			}

			@Override
			public String getName() {
				return null;
			}

			@Override
			public String getName(Locale locale) {
				return null;
			}

			@Override
			public Properties getProperties() {
				return null;
			}

			@Override
			public String getSourceCodeURL() {
				return null;
			}

			@Override
			public int getStatus() {
				return 0;
			}

			@Override
			public String getType() {
				return null;
			}

			@Override
			public String getTypeSettings() {
				return null;
			}

			@Override
			public boolean hasProperties() {
				return false;
			}

			@Override
			public boolean isReadOnly() {
				return false;
			}

		};
	}

}