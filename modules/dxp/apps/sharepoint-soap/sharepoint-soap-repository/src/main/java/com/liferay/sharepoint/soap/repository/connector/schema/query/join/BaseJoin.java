/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.schema.query.join;

import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.sharepoint.soap.repository.connector.schema.BaseNode;
import com.liferay.sharepoint.soap.repository.connector.schema.query.QueryClause;

/**
 * @author Iván Zaera
 */
public abstract class BaseJoin extends BaseNode implements QueryClause {

	public BaseJoin(QueryClause leftQueryClause, QueryClause rightQueryClause) {
		_leftQueryClause = leftQueryClause;
		_rightQueryClause = rightQueryClause;
	}

	public QueryClause getLeftQueryClause() {
		return _leftQueryClause;
	}

	public QueryClause getRightQueryClause() {
		return _rightQueryClause;
	}

	@Override
	protected void populate(Element element) {
		_leftQueryClause.attach(element);
		_rightQueryClause.attach(element);
	}

	private final QueryClause _leftQueryClause;
	private final QueryClause _rightQueryClause;

}