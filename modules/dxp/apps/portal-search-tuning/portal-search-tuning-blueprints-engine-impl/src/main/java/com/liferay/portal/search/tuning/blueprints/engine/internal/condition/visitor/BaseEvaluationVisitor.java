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

package com.liferay.portal.search.tuning.blueprints.engine.internal.condition.visitor;

import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Petteri Karttunen
 */
public abstract class BaseEvaluationVisitor {

	public BaseEvaluationVisitor(JSONObject conditionJSONObject, boolean not) {
		this.conditionJSONObject = conditionJSONObject;
		this.not = not;
	}

	protected boolean returnValue(boolean value) {
		if (not) {
			return !value;
		}

		return value;
	}

	protected final JSONObject conditionJSONObject;
	protected final boolean not;

}