/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.zendesk.search;

/**
 * @author Amos Fong
 */
public class ZendeskTicketQuery extends Query {

	public ZendeskTicketQuery() {
		addCriterion("type:ticket");
	}

}