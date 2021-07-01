/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.remote.web.component.admin.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Raymond Augé
 */
@DDMForm
@DDMFormLayout(
	paginationMode = com.liferay.dynamic.data.mapping.model.DDMFormLayout.SINGLE_PAGE_MODE,
	value = {
		@DDMFormLayoutPage(
			{
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12,
							value = {"name", "elementName", "webComponentUrl"}
						)
					}
				),
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 6, value = {"portletAlias", "instanceable"}
						),
						@DDMFormLayoutColumn(
							size = 6, value = "portletDisplayCategory"
						)
					}
				),
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12,
							value = {
								"webComponentCssUrl", "webComponentTopJsUrl",
								"portletServiceProperties"
							}
						)
					}
				)
			}
		)
	}
)
@ExtendedObjectClassDefinition(
	category = "widget-tools", factoryInstanceLabelAttribute = "name",
	nameArguments = {"elementName", "portletAlias"}
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.remote.web.component.admin.web.internal.configuration.RemoteWebComponentConfiguration",
	localization = "content/Language",
	name = "remote-web-component-configuration-name"
)
public interface RemoteWebComponentConfiguration {

	@DDMFormField(name = "%name", tip = "%name-description", type = "text")
	@Meta.AD(
		description = "name-description", name = "name", type = Meta.Type.String
	)
	public String name();

	@DDMFormField(label = "%element-name", tip = "%element-name-description")
	@Meta.AD(description = "element-name-description", name = "element-name")
	public String elementName();

	@DDMFormField(
		label = "%web-component-url", tip = "%web-component-url-description"
	)
	@Meta.AD(
		description = "web-component-url-description",
		name = "web-component-url"
	)
	public String[] webComponentUrl();

	@DDMFormField(
		label = "%web-component-css-url",
		tip = "%web-component-css-url-description"
	)
	@Meta.AD(
		description = "web-component-css-url-description",
		name = "web-component-css-url", required = false
	)
	public String[] webComponentCssUrl();

	@DDMFormField(
		label = "%web-component-top-js-url",
		tip = "%web-component-top-js-url-description"
	)
	@Meta.AD(
		description = "web-component-top-js-url-description",
		name = "web-component-top-js-url", required = false
	)
	public String[] webComponentTopJsUrl();

	@DDMFormField(label = "%portlet-alias", tip = "%portlet-alias-description")
	@Meta.AD(
		description = "portlet-alias-description", name = "portlet-alias",
		required = false
	)
	public String portletAlias();

	@Meta.AD(
		deflt = "false", description = "instanceable-desciption",
		name = "instanceable", required = false
	)
	public boolean instanceable();

	@DDMFormField(
		label = "%portlet-display-category", predefinedValue = "sample",
		tip = "%portlet-display-category-desciption"
	)
	@Meta.AD(
		deflt = "sample", description = "portlet-display-category-desciption",
		name = "portlet-display-category", required = false
	)
	public String portletDisplayCategory();

	@Meta.AD(
		deflt = "", description = "portlet-service-properties-description",
		name = "portlet-service-properties", required = false
	)
	public String portletServiceProperties();

}