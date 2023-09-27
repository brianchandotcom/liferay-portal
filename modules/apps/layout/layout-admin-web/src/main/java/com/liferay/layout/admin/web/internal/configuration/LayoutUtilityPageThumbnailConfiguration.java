/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Bárbara Cabrera
 */
@ExtendedObjectClassDefinition(category = "pages")
@Meta.OCD(
	id = "com.liferay.layout.admin.web.internal.configuration.LayoutUtilityPageThumbnailConfiguration",
	localization = "content/Language",
	name = "layout-utility-page-thumbnail-configuration-name"
)
public interface LayoutUtilityPageThumbnailConfiguration {

	@Meta.AD(
		deflt = ".gif,.jpeg,.jpg,.png",
		name = "allowed-thumbnail-file-extensions", required = false
	)
	public String[] thumbnailExtensions();

}