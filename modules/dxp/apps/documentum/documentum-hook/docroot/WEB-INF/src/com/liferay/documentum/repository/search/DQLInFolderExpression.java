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

package com.liferay.documentum.repository.search;

import com.liferay.petra.string.StringBundler;

/**
 * @author Mika Koivisto
 */
public class DQLInFolderExpression implements DQLCriterion {

	public DQLInFolderExpression(String objectId, boolean decend) {
		_objectId = objectId;
		_decend = decend;
	}

	@Override
	public String toQueryFragment() {
		StringBundler sb = new StringBundler(5);

		sb.append("FOLDER(ID('");
		sb.append(_objectId);
		sb.append("')");

		if (_decend) {
			sb.append(", DESCEND");
		}

		sb.append(")");

		return sb.toString();
	}

	private final boolean _decend;
	private final String _objectId;

}