/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alessio Antonio Rendina
 */
@ExtendedObjectClassDefinition(
	category = "catalog",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.commerce.product.content.web.internal.configuration.CPCompareContentMiniPortletInstanceConfiguration",
	localization = "content/Language",
	name = "commerce-product-compare-content-mini-portlet-instance-configuration-name"
)
public interface CPCompareContentMiniPortletInstanceConfiguration {

	@Meta.AD(
		deflt = CPPortletKeys.CP_COMPARE_CONTENT_MINI_WEB,
		name = "cp-content-list-renderer-key", required = false
	)
	public String cpContentListRendererKey();

	@Meta.AD(name = "cp-type-list-entry-renderer-key", required = false)
	public String cpTypeListEntryRendererKey();

	@Meta.AD(name = "display-style", required = false)
	public String displayStyle();

	@Meta.AD(deflt = "0", name = "display-style-group-id", required = false)
	public long displayStyleGroupId();

	@Meta.AD(deflt = "5", name = "products-limit", required = false)
	public int productsLimit();

	@Meta.AD(deflt = "custom", name = "selection-style", required = false)
	public String selectionStyle();

}