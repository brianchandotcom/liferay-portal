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