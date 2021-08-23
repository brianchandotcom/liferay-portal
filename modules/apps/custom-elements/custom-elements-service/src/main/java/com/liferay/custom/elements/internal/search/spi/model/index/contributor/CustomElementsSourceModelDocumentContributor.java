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

import com.liferay.custom.elements.model.CustomElementsSource;
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
	property = "indexer.class.name=com.liferay.custom.elements.model.CustomElementsSource",
	service = ModelDocumentContributor.class
)
public class CustomElementsSourceModelDocumentContributor
	implements ModelDocumentContributor<CustomElementsSource> {

	@Override
	public void contribute(
		Document document, CustomElementsSource customElementsSource) {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Indexing custom element source " + customElementsSource);
		}

		document.addText(Field.NAME, customElementsSource.getName());
		document.addText(Field.URL, customElementsSource.getURLs());

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Document " + customElementsSource + " indexed successfully");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomElementsSourceModelDocumentContributor.class);

}