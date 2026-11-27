/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.portal.search.tuning.rankings.index.Ranking;

/**
 * @author Shuyang Zhou
 */
public class PinImpl implements Ranking.Pin {

	public PinImpl(String documentId, int position) {
		_documentId = documentId;
		_position = position;
	}

	@Override
	public String getDocumentId() {
		return _documentId;
	}

	@Override
	public int getPosition() {
		return _position;
	}

	private final String _documentId;
	private final int _position;

}