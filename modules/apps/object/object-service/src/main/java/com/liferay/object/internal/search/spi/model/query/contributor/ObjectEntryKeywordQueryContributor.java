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

package com.liferay.object.internal.search.spi.model.query.contributor;

import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.util.RangeParserUtil;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchQuery;
import com.liferay.portal.kernel.search.generic.NestedQuery;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.search.generic.TermRangeQueryImpl;
import com.liferay.portal.kernel.search.generic.WildcardQueryImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.object.model.ObjectEntry",
	service = KeywordQueryContributor.class
)
public class ObjectEntryKeywordQueryContributor
	implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		SearchContext searchContext =
			keywordQueryContributorHelper.getSearchContext();

		long objectDefinitionId = GetterUtil.getLong(
			searchContext.getAttribute("objectDefinitionId"));

		if (_log.isDebugEnabled()) {
			_log.debug("Object definition ID " + objectDefinitionId);
		}

		if (objectDefinitionId == 0) {
			return;
		}

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(objectDefinitionId);

		for (ObjectField objectField : objectFields) {
			boolean indexed = true; //objectField.isIndexed(); //TODO

			if (!indexed) {
				continue;
			}

			String name = objectField.getName();

			if (Validator.isNull(keywords)) {
				keywords = _getKeywords(searchContext, name);

				if (Validator.isNull(keywords)) {
					continue;
				}
			}

			String type = objectField.getType();
			String localeString = ""; //objectField.getLocaleString(); //TODO

			//objectField.isIndexedAsKeyword(); //TODO

			boolean indexedAsKeyword = false;

			if (name.equals("emailAddress") ||
				name.equals("emailAddressDomain")) {

				indexedAsKeyword = true;
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Add search term ", keywords, " for object field ",
						name));
			}

			try {
				BooleanQuery nestedBooleanQuery = new BooleanQueryImpl();

				if (indexedAsKeyword) {
					keywords = StringUtil.toLowerCase(keywords);

					nestedBooleanQuery.add(
						new WildcardQueryImpl(
							"nestedFieldArray.value_keyword",
							keywords + StringPool.STAR),
						BooleanClauseOccur.MUST);

					nestedBooleanQuery.add(
						new TermQueryImpl(
							"nestedFieldArray.value_keyword", keywords),
						BooleanClauseOccur.SHOULD);
				}
				else if (type.equals("BigDecimal")) {
					_addRangeQuery(
						keywords, nestedBooleanQuery,
						"nestedFieldArray.value_double");
				}
				else if (type.equals("Blob")) {
					if (_log.isDebugEnabled()) {
						_log.debug("Blob field " + name + " is not searchable");
					}
				}
				else if (type.equals("Boolean")) {
					if (StringUtil.equalsIgnoreCase(keywords, "true") ||
						StringUtil.equalsIgnoreCase(keywords, "false")) {

						nestedBooleanQuery.add(
							new TermQueryImpl(
								"nestedFieldArray.value_boolean",
								StringUtil.toLowerCase(keywords)),
							BooleanClauseOccur.MUST);
					}
				}
				else if (type.equals("Date")) {
					_addRangeQuery(
						keywords, nestedBooleanQuery,
						"nestedFieldArray.value_date");
				}
				else if (type.equals("Double")) {
					_addRangeQuery(
						keywords, nestedBooleanQuery,
						"nestedFieldArray.value_double");
				}
				else if (type.equals("Integer")) {
					_addRangeQuery(
						keywords, nestedBooleanQuery,
						"nestedFieldArray.value_integer");
				}
				else if (type.equals("Long")) {
					_addRangeQuery(
						keywords, nestedBooleanQuery,
						"nestedFieldArray.value_long");
				}
				else if (type.equals("String")) {
					if (Validator.isBlank(localeString)) {
						nestedBooleanQuery.add(
							new MatchQuery(
								"nestedFieldArray.value_text", keywords),
							BooleanClauseOccur.MUST);
					}
					else {
						Locale locale = searchContext.getLocale();

						if (localeString.equals(locale.toString())) {
							nestedBooleanQuery.add(
								new MatchQuery(
									"nestedFieldArray.value_" + localeString,
									keywords),
								BooleanClauseOccur.MUST);
						}
					}
				}

				if (nestedBooleanQuery.hasClauses()) {
					nestedBooleanQuery.add(
						new TermQueryImpl("nestedFieldArray.fieldName", name),
						BooleanClauseOccur.MUST);

					NestedQuery nestedQuery = new NestedQuery(
						"nestedFieldArray", nestedBooleanQuery);

					booleanQuery.add(nestedQuery, BooleanClauseOccur.SHOULD);
				}
			}
			catch (ParseException parseException) {
				throw new SystemException(parseException);
			}
		}
	}

	private void _addRangeQuery(
			String keywords, BooleanQuery booleanQuery, String field)
		throws ParseException {

		if (Validator.isBlank(keywords)) {
			return;
		}

		String[] range = RangeParserUtil.parserRange(keywords);

		String lowerTerm = range[0];
		String upperTerm = range[1];

		if ((lowerTerm != null) && (upperTerm != null)) {
			booleanQuery.add(
				new TermRangeQueryImpl(field, lowerTerm, upperTerm, true, true),
				BooleanClauseOccur.MUST);
		}
	}

	private String _getKeywords(SearchContext searchContext, String field) {
		String value = StringPool.BLANK;

		Serializable serializable = searchContext.getAttribute(field);

		if (serializable != null) {
			Class<?> clazz = serializable.getClass();

			if (clazz.isArray()) {
				value = StringUtil.merge((Object[])serializable);
			}
			else {
				value = GetterUtil.getString(serializable);
			}
		}

		if (!Validator.isBlank(value) &&
			(searchContext.getFacet(field) != null)) {

			return null;
		}

		if (Validator.isBlank(value)) {
			value = searchContext.getKeywords();
		}

		if (Validator.isBlank(value)) {
			return null;
		}

		return value;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryKeywordQueryContributor.class);

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}