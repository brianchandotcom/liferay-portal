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

package com.liferay.dynamic.data.mapping.internal.xstream.configurator;

import com.liferay.dynamic.data.mapping.kernel.DDMFormField;
import com.liferay.dynamic.data.mapping.kernel.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMTemplateImpl;
import com.liferay.exportimport.kernel.xstream.XStreamAlias;
import com.liferay.exportimport.kernel.xstream.XStreamConverter;
import com.liferay.exportimport.kernel.xstream.XStreamType;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.xstream.configurator.XStreamConfigurator;

import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Máté Thurzó
 */
@Component(service = XStreamConfigurator.class)
public class DynamicDataMappingXStreamConfigurator
	implements XStreamConfigurator {

	@Override
	public List<XStreamType> getAllowedXStreamTypes() {
		return ListUtil.fromArray(_xStreamTypes);
	}

	@Override
	public List<XStreamAlias> getXStreamAliases() {
		return ListUtil.fromArray(_xStreamAliases);
	}

	@Override
	public List<XStreamConverter> getXStreamConverters() {
		return null;
	}

	@Activate
	protected void activate() {
		_xStreamAliases = new XStreamAlias[] {
			new XStreamAlias(DDMStructureImpl.class, "DDMStructure"),
			new XStreamAlias(DDMTemplateImpl.class, "DDMTemplate")
		};

		_xStreamTypes = new XStreamType[] {
			new XStreamType(
				com.liferay.dynamic.data.mapping.model.DDMFormField.class),
			new XStreamType(
				com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions.
					class),
			new XStreamType(
				com.liferay.dynamic.data.mapping.model.LocalizedValue.class),
			new XStreamType(
				com.liferay.dynamic.data.mapping.storage.DDMFormValues.class),
			new XStreamType(DDMFormField.class),
			new XStreamType(DDMFormFieldOptions.class),
			new XStreamType(DDMFormFieldValidation.class),
			new XStreamType(DDMFormRule.class),
			new XStreamType(DDMFormValues.class),
			new XStreamType(LocalizedValue.class)
		};
	}

	private XStreamAlias[] _xStreamAliases;
	private XStreamType[] _xStreamTypes;

}