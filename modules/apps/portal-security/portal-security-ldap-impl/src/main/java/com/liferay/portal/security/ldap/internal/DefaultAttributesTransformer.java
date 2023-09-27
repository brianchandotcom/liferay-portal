/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.internal;

import com.liferay.portal.kernel.security.ldap.AttributesTransformer;

import javax.naming.directory.Attributes;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = AttributesTransformer.class)
public class DefaultAttributesTransformer implements AttributesTransformer {

	@Override
	public Attributes transformGroup(Attributes attributes) {
		return attributes;
	}

	@Override
	public Attributes transformUser(Attributes attributes) {
		return attributes;
	}

}