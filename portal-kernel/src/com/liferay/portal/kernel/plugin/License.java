/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.plugin;

import com.liferay.portal.kernel.util.Accessor;

import java.io.Serializable;

/**
 * @author Jorge Ferrer
 */
public class License implements Serializable {

	public static final Accessor<License, String> NAME_ACCESSOR =
		new Accessor<License, String>() {

			@Override
			public String get(License license) {
				return license.getName();
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<License> getTypeClass() {
				return License.class;
			}

		};

	public String getName() {
		return _name;
	}

	public String getUrl() {
		return _url;
	}

	public boolean isOsiApproved() {
		return _osiApproved;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOsiApproved(boolean osiApproved) {
		_osiApproved = osiApproved;
	}

	public void setUrl(String url) {
		_url = url;
	}

	private String _name;
	private boolean _osiApproved;
	private String _url;

}