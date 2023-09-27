/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.portal.search.elasticsearch7.internal.script.ScriptTranslator;
import com.liferay.portal.search.query.ScriptQuery;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = ScriptQueryTranslator.class)
public class ScriptQueryTranslatorImpl implements ScriptQueryTranslator {

	@Override
	public QueryBuilder translate(ScriptQuery scriptQuery) {
		Script script = _scriptTranslator.translate(scriptQuery.getScript());

		return QueryBuilders.scriptQuery(script);
	}

	private final ScriptTranslator _scriptTranslator = new ScriptTranslator();

}