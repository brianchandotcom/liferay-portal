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

package com.liferay.dynamic.data.mapping.form.values.query.internal;

import com.liferay.dynamic.data.mapping.form.values.query.DDMFormValuesQuery;
import com.liferay.dynamic.data.mapping.form.values.query.DDMFormValuesQueryFactory;
import com.liferay.dynamic.data.mapping.form.values.query.DDMFormValuesQuerySyntaxException;
import com.liferay.dynamic.data.mapping.form.values.query.internal.parser.DDMFormValuesQueryLexer;
import com.liferay.dynamic.data.mapping.form.values.query.internal.parser.DDMFormValuesQueryParser;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDMFormValuesQueryFactory.class)
public class DDMFormValuesQueryFactoryImpl
	implements DDMFormValuesQueryFactory {

	@Override
	public DDMFormValuesQuery create(DDMFormValues ddmFormValues, String query)
		throws DDMFormValuesQuerySyntaxException {

		try {
			DDMFormValuesQueryParser.PathContext pathContext =
				createPathContext(query);

			DDMFormValuesQueryListener ddmFormValuesQueryListener =
				new DDMFormValuesQueryListener();

			ParseTreeWalker parseTreeWalker = new ParseTreeWalker();

			parseTreeWalker.walk(ddmFormValuesQueryListener, pathContext);

			return new DDMFormValuesQueryImpl(
				ddmFormValues,
				ddmFormValuesQueryListener.getDDMFormValuesFilters());
		}
		catch (Exception e) {
			throw new DDMFormValuesQuerySyntaxException(e);
		}
	}

	protected DDMFormValuesQueryParser.PathContext createPathContext(
		String query) {

		CharStream charStream = new ANTLRInputStream(query);

		DDMFormValuesQueryLexer ddmFormValuesQueryLexer =
			new DDMFormValuesQueryLexer(charStream);

		DDMFormValuesQueryParser ddmFormValuesQueryParser =
			new DDMFormValuesQueryParser(
				new CommonTokenStream(ddmFormValuesQueryLexer));

		ddmFormValuesQueryParser.setErrorHandler(new BailErrorStrategy());

		return ddmFormValuesQueryParser.path();
	}

}