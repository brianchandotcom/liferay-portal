/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.search.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Crescenzo Rega
 */
@ExtendedObjectClassDefinition(
	category = "catalog",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.commerce.product.content.search.web.internal.configuration.CPSpecificationOptionFacetsPortletInstanceConfiguration",
	localization = "content/Language",
	name = "commerce-product-specification-option-facet-portlet-instance-configuration-name"
)
public interface CPSpecificationOptionFacetsPortletInstanceConfiguration {

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

	@Meta.AD(
		deflt = StringPool.TRUE, name = "frequencies-visible", required = false
	)
	public boolean frequenciesVisible();

	@Meta.AD(deflt = "1", name = "frequency-threshold", required = false)
	public int frequencyThreshold();

	@Meta.AD(deflt = "10", name = "max-specifications", required = false)
	public int maxSpecifications();

	@Meta.AD(deflt = "10", name = "max-terms", required = false)
	public int maxTerms();

	@Meta.AD(
		deflt = "priority:asc", name = "specifications-order", required = false
	)
	public String specificationsOrder();

}