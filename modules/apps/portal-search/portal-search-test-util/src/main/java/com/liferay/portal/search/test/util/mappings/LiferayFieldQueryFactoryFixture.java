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

package com.liferay.portal.search.test.util.mappings;

import com.liferay.portal.search.internal.analysis.SimpleKeywordTokenizer;
import com.liferay.portal.search.internal.query.QueriesImpl;
import com.liferay.portal.search.internal.query.field.AssetTagNamesFieldQueryBuilderFactory;
import com.liferay.portal.search.internal.query.field.DescriptionFieldQueryBuilder;
import com.liferay.portal.search.internal.query.field.FieldQueryBuilderFactoryImpl;
import com.liferay.portal.search.internal.query.field.FieldQueryFactoryImpl;
import com.liferay.portal.search.internal.query.field.SubstringFieldQueryBuilder;
import com.liferay.portal.search.internal.query.field.TitleFieldQueryBuilder;
import com.liferay.portal.search.query.field.FieldQueryFactory;
import com.liferay.portal.search.query.field.QueryPreProcessConfiguration;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public class LiferayFieldQueryFactoryFixture {

	public LiferayFieldQueryFactoryFixture() {
		QueriesImpl queriesImpl = new QueriesImpl();
		SimpleKeywordTokenizer simpleKeywordTokenizer =
			new SimpleKeywordTokenizer();

		_descriptionFieldQueryBuilder = createDescriptionFieldQueryBuilder(
			simpleKeywordTokenizer, queriesImpl);
		_substringFieldQueryBuilder = createSubstringFieldQueryBuilder(
			simpleKeywordTokenizer, queriesImpl);
		_titleFieldQueryBuilder = createTitleFieldQueryBuilder(
			simpleKeywordTokenizer, queriesImpl);

		FieldQueryBuilderFactoryImpl fieldQueryBuilderFactoryImpl =
			new FieldQueryBuilderFactoryImpl() {
				{
					descriptionFieldQueryBuilder =
						_descriptionFieldQueryBuilder;
					queryPreProcessConfiguration = Mockito.mock(
						QueryPreProcessConfiguration.class);
					substringFieldQueryBuilder = _substringFieldQueryBuilder;
					titleFieldQueryBuilder = _titleFieldQueryBuilder;
				}
			};

		AssetTagNamesFieldQueryBuilderFactory
			assetTagNamesFieldQueryBuilderFactory =
				new AssetTagNamesFieldQueryBuilderFactory() {
					{
						titleQueryBuilder = _titleFieldQueryBuilder;
					}
				};

		_fieldQueryFactory = new FieldQueryFactoryImpl() {
			{
				descriptionFieldQueryBuilder = _descriptionFieldQueryBuilder;

				addFieldQueryBuilderFactory(
					assetTagNamesFieldQueryBuilderFactory);
				addFieldQueryBuilderFactory(fieldQueryBuilderFactoryImpl);
			}
		};
	}

	public FieldQueryFactory getFieldQueryFactory() {
		return _fieldQueryFactory;
	}

	protected static DescriptionFieldQueryBuilder
		createDescriptionFieldQueryBuilder(
			SimpleKeywordTokenizer simpleKeywordTokenizer,
			QueriesImpl queriesImpl) {

		return new DescriptionFieldQueryBuilder() {
			{
				keywordTokenizer = simpleKeywordTokenizer;
				queries = queriesImpl;
			}
		};
	}

	protected static SubstringFieldQueryBuilder
		createSubstringFieldQueryBuilder(
			SimpleKeywordTokenizer simpleKeywordTokenizer,
			QueriesImpl queriesImpl) {

		return new SubstringFieldQueryBuilder() {
			{
				keywordTokenizer = simpleKeywordTokenizer;
				queries = queriesImpl;
			}
		};
	}

	protected static TitleFieldQueryBuilder createTitleFieldQueryBuilder(
		SimpleKeywordTokenizer simpleKeywordTokenizer,
		QueriesImpl queriesImpl) {

		return new TitleFieldQueryBuilder() {
			{
				keywordTokenizer = simpleKeywordTokenizer;
				queries = queriesImpl;
			}
		};
	}

	private final DescriptionFieldQueryBuilder _descriptionFieldQueryBuilder;
	private final FieldQueryFactory _fieldQueryFactory;
	private final SubstringFieldQueryBuilder _substringFieldQueryBuilder;
	private final TitleFieldQueryBuilder _titleFieldQueryBuilder;

}