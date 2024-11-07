/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.category.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alessio Antonio Rendina
 */
@ExtendedObjectClassDefinition(
	category = "catalog",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.commerce.product.content.category.web.internal.configuration.CPCategoryContentPortletInstanceConfiguration",
	localization = "content/Language",
	name = "commerce-product-content-category-web-portlet-instance-configuration-name"
)
public interface CPCategoryContentPortletInstanceConfiguration {

	@Meta.AD(
		deflt = "", name = "asset-category-external-reference-code",
		required = false
	)
	public String assetCategoryExternalReferenceCode();

	@Meta.AD(
		deflt = "", description = "asset-category-id-description",
		name = "asset-category-id", required = false
	)
	public String assetCategoryId();

	@Meta.AD(name = "display-style", required = false)
	public String displayStyle();

	@Meta.AD(
		deflt = "", name = "display-style-group-external-reference-code",
		required = false
	)
	public String displayStyleGroupExternalReferenceCode();

	@Meta.AD(
		deflt = "0", description = "display-style-group-id-description",
		name = "display-style-group-id", required = false
	)
	public long displayStyleGroupId();

	@Meta.AD(
		description = "display-style-group-key-description",
		name = "display-style-group-key", required = false
	)
	public String displayStyleGroupKey();

	@Meta.AD(deflt = "false", name = "use-asset-category", required = false)
	public boolean useAssetCategory();

}