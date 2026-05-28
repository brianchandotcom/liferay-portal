/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.capabilities;

import com.liferay.petra.string.StringPool;

/**
 * Immutable result of an external embedding capability gate evaluation.
 *
 * <p>
 * When {@link #isAvailable()} is {@code true}, {@link #getReason()} returns an
 * empty string. When the capability is unavailable, {@link #getReason()}
 * returns the i18n key identifying the first failed precondition.
 * </p>
 *
 * @author Rodrigo Guedes de Souza
 */
public final class ExternalEmbeddingEligibility {

	public static ExternalEmbeddingEligibility available() {
		return _AVAILABLE;
	}

	public static ExternalEmbeddingEligibility unavailable(String reason) {
		if ((reason == null) || reason.isEmpty()) {
			throw new IllegalArgumentException("Reason is null or empty");
		}

		return new ExternalEmbeddingEligibility(reason);
	}

	public String getReason() {
		return _reason;
	}

	public boolean isAvailable() {
		return _reason.isEmpty();
	}

	private ExternalEmbeddingEligibility(String reason) {
		_reason = reason;
	}

	private static final ExternalEmbeddingEligibility _AVAILABLE =
		new ExternalEmbeddingEligibility(StringPool.BLANK);

	private final String _reason;

}