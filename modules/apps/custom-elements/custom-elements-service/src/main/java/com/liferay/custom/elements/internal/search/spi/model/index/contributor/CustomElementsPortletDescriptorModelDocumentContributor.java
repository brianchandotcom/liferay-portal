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

package com.liferay.custom.elements.internal.search.spi.model.index.contributor;

import com.liferay.custom.elements.model.CustomElementsPortletDescriptor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	property = "indexer.class.name=com.liferay.custom.elements.model.CustomElementsPortletDescriptor",
	service = ModelDocumentContributor.class
)
public class CustomElementsPortletDescriptorModelDocumentContributor
	implements ModelDocumentContributor<CustomElementsPortletDescriptor> {

	@Override
	public void contribute(
		Document document,
		CustomElementsPortletDescriptor customElementsPortletDescriptor) {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Indexing custom element portlet descriptor " +
					customElementsPortletDescriptor);
		}

		document.addText(Field.NAME, customElementsPortletDescriptor.getName());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + customElementsPortletDescriptor +
					" indexed successfully");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomElementsPortletDescriptorModelDocumentContributor.class);

}