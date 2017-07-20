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

package com.liferay.commerce.product.internal.xstream.configurator;

import com.liferay.commerce.product.model.impl.CPAttachmentFileEntryImpl;
import com.liferay.commerce.product.model.impl.CPDefinitionImpl;
import com.liferay.commerce.product.model.impl.CPDefinitionLinkImpl;
import com.liferay.commerce.product.model.impl.CPDefinitionOptionRelImpl;
import com.liferay.commerce.product.model.impl.CPDefinitionOptionValueRelImpl;
import com.liferay.commerce.product.model.impl.CPInstanceImpl;
import com.liferay.commerce.product.model.impl.CPOptionCategoryImpl;
import com.liferay.commerce.product.model.impl.CPOptionImpl;
import com.liferay.commerce.product.model.impl.CPOptionValueImpl;
import com.liferay.commerce.product.model.impl.CPSpecificationOptionImpl;
import com.liferay.exportimport.kernel.xstream.XStreamAlias;
import com.liferay.exportimport.kernel.xstream.XStreamConverter;
import com.liferay.exportimport.kernel.xstream.XStreamType;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.xstream.configurator.XStreamConfigurator;

import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Andrea Di Giorgi
 */
@Component(immediate = true, service = XStreamConfigurator.class)
public class CPXStreamConfigurator implements XStreamConfigurator {

	@Override
	public List<XStreamType> getAllowedXStreamTypes() {
		return null;
	}

	@Override
	public List<XStreamAlias> getXStreamAliases() {
		return ListUtil.toList(_xStreamAliases);
	}

	@Override
	public List<XStreamConverter> getXStreamConverters() {
		return null;
	}

	@Activate
	protected void activate() {
		_xStreamAliases = new XStreamAlias[] {
			new XStreamAlias(
				CPAttachmentFileEntryImpl.class, "CPAttachmentFileEntry"),
			new XStreamAlias(CPDefinitionImpl.class, "CPDefinition"),
			new XStreamAlias(CPDefinitionLinkImpl.class, "CPDefinitionLink"),
			new XStreamAlias(
				CPDefinitionOptionRelImpl.class, "CPDefinitionOptionRel"),
			new XStreamAlias(
				CPDefinitionOptionValueRelImpl.class,
				"CPDefinitionOptionValueRel"),
			new XStreamAlias(CPInstanceImpl.class, "CPInstance"),
			new XStreamAlias(CPOptionCategoryImpl.class, "CPOptionCategory"),
			new XStreamAlias(CPOptionImpl.class, "CPOption"),
			new XStreamAlias(CPOptionValueImpl.class, "CPOptionValue"),
			new XStreamAlias(
				CPSpecificationOptionImpl.class, "CPSpecificationOption")
		};
	}

	private XStreamAlias[] _xStreamAliases;

}