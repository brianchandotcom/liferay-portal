/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type;

import com.liferay.client.extension.type.annotation.CETProperty;

import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface CET {

	public String getBaseURL();

	public long getCompanyId();

	public Date getCreateDate();

	@CETProperty(name = "description", type = CETProperty.Type.String)
	public String getDescription();

	public String getEditJSP();

	public String getExternalReferenceCode();

	public Date getModifiedDate();

	@CETProperty(name = "name", type = CETProperty.Type.String)
	public String getName(Locale locale);

	public Properties getProperties();

	@CETProperty(
		defaultValue = "https://www.liferay.com", name = "sourceCodeURL",
		type = CETProperty.Type.String
	)
	public String getSourceCodeURL();

	public int getStatus();

	@CETProperty(name = "type", type = CETProperty.Type.String)
	public String getType();

	public String getTypeSettings();

	public default String getViewJSP() {
		return null;
	}

	public boolean hasProperties();

	public boolean isReadOnly();

}