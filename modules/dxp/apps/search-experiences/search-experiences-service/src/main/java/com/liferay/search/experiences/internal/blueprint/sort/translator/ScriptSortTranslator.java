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

package com.liferay.search.experiences.internal.blueprint.sort.translator;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.sort.ScriptSort;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.search.experiences.internal.blueprint.util.ScriptHelper;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=_script", service = SortTranslator.class
)
public class ScriptSortTranslator implements SortTranslator {

	@Override
	public Optional<Sort> translate(
		String field, JSONObject jsonObject, SortOrder sortOrder) {

		Optional<Script> optional = _scriptHelper.getScript(
			jsonObject.get("script"));

		if (!optional.isPresent()) {
			return Optional.empty();
		}

		ScriptSort scriptSort = _sorts.script(
			optional.get(), _getScriptSortType(jsonObject));

		scriptSort.setSortOrder(sortOrder);

		return Optional.of(scriptSort);
	}

	private ScriptSort.ScriptSortType _getScriptSortType(
		JSONObject jsonObject) {

		String s = jsonObject.getString("type");

		return ScriptSort.ScriptSortType.valueOf(StringUtil.toUpperCase(s));
	}

	@Reference
	private ScriptHelper _scriptHelper;

	@Reference
	private Sorts _sorts;

}