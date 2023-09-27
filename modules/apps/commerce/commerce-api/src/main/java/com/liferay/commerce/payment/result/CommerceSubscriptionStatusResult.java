/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.result;

import java.io.Serializable;

/**
 * @author Luca Pellizzon
 */
public class CommerceSubscriptionStatusResult implements Serializable {

	public CommerceSubscriptionStatusResult(
		long paymentsFailed, long cyclesRemaining, long cyclesCompleted) {

		_paymentsFailed = paymentsFailed;
		_cyclesRemaining = cyclesRemaining;
		_cyclesCompleted = cyclesCompleted;
	}

	public long getCyclesCompleted() {
		return _cyclesCompleted;
	}

	public long getCyclesRemaining() {
		return _cyclesRemaining;
	}

	public long getPaymentsFailed() {
		return _paymentsFailed;
	}

	private final long _cyclesCompleted;
	private final long _cyclesRemaining;
	private final long _paymentsFailed;

}