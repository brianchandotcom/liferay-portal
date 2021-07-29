/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.index;

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilderFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(service = SynonymSetToDocumentTranslator.class)
public class SynonymSetToDocumentTranslatorImpl
	implements SynonymSetToDocumentTranslator {

	@Override
	public Document translate(SynonymSet synonymSet) {
		return _documentBuilderFactory.builder(
		).setString(
			SynonymSetFields.SYNONYMS, synonymSet.getSynonyms()
		).setString(
			SynonymSetFields.UID, synonymSet.getSynonymSetDocumentId()
		).build();
	}

	@Reference(unbind = "-")
	protected void setDocumentBuilderFactory(
		DocumentBuilderFactory documentBuilderFactory) {

		_documentBuilderFactory = documentBuilderFactory;
	}

	private DocumentBuilderFactory _documentBuilderFactory;

}