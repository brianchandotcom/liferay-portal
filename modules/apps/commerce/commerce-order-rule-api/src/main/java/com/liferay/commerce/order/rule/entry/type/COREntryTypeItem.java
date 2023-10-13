/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.rule.entry.type;

import java.math.BigDecimal;

/**
 * @author Alessio Antonio Rendina
 */
public class COREntryTypeItem {

	public COREntryTypeItem(
		long cpDefinitionId, long cpInstanceId, BigDecimal quantity) {

		_cpDefinitionId = cpDefinitionId;
		_cpInstanceId = cpInstanceId;
		_quantity = quantity;
	}

	public long getCPDefinitionId() {
		return _cpDefinitionId;
	}

	public long getCPInstanceId() {
		return _cpInstanceId;
	}

	public BigDecimal getQuantity() {
		return _quantity;
	}

	private final long _cpDefinitionId;
	private final long _cpInstanceId;
	private final BigDecimal _quantity;

}