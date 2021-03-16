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

package com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.index;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.tuning.blueprints.misspellings.index.MisspellingSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(service = DocumentToMisspellingSetTranslator.class)
public class DocumentToMisspellingSetTranslatorImpl
	implements DocumentToMisspellingSetTranslator {

	@Override
	public MisspellingSet translate(Document document, String id) {
		return builder(
		).created(
			_parseDateStringFieldValue(
				document.getDate(MisspellingSetFields.CREATED))
		).groupId(
			document.getLong(MisspellingSetFields.GROUP_ID)
		).languageId(
			document.getString(MisspellingSetFields.LANGUAGE_ID)
		).misspellings(
			document.getStrings(MisspellingSetFields.MISSPELLINGS)
		).misspellingSetId(
			id
		).modified(
			_parseDateStringFieldValue(
				document.getDate(MisspellingSetFields.MODIFIED))
		).phrase(
			document.getString(MisspellingSetFields.PHRASE)
		).userId(
			document.getLong(MisspellingSetFields.USER_ID)
		).build();
	}

	@Override
	public List<MisspellingSet> translateAll(SearchHits searchHits) {
		List<SearchHit> list = searchHits.getSearchHits();

		Stream<SearchHit> stream = list.stream();

		return stream.map(
			searchHit -> translate(searchHit.getDocument(), searchHit.getId())
		).collect(
			Collectors.toList()
		);
	}

	protected MisspellingSet.MisspellingSetBuilder builder() {
		return new MisspellingSet.MisspellingSetBuilder();
	}

	private Date _parseDateStringFieldValue(String dateStringFieldValue) {
		if (Validator.isBlank(dateStringFieldValue)) {
			return null;
		}

		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

			return dateFormat.parse(dateStringFieldValue);
		}
		catch (Exception exception) {
			_log.error(exception.getMessage(), exception);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DocumentToMisspellingSetTranslatorImpl.class);

}