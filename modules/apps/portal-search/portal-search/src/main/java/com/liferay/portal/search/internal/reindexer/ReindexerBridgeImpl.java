/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.reindexer;

import com.liferay.portal.kernel.search.reindexer.ReindexerBridge;
import com.liferay.portal.search.reindexer.Reindexer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = ReindexerBridge.class)
public class ReindexerBridgeImpl implements ReindexerBridge {

	@Override
	public void reindex(long companyId, String className, long... classPKs) {
		_reindexer.reindex(companyId, className, classPKs);
	}

	@Reference
	private Reindexer _reindexer;

}