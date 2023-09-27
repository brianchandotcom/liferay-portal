/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.staging.model.listener.StagingModelListener;
import com.liferay.wiki.model.WikiNode;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(service = ModelListener.class)
public class WikiNodeStagingModelListener extends BaseModelListener<WikiNode> {

	@Override
	public void onAfterCreate(WikiNode wikiNode) throws ModelListenerException {
		_stagingModelListener.onAfterCreate(wikiNode);
	}

	@Override
	public void onAfterRemove(WikiNode wikiNode) throws ModelListenerException {
		_stagingModelListener.onAfterRemove(wikiNode);
	}

	@Override
	public void onAfterUpdate(WikiNode originalWikiNode, WikiNode wikiNode)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(wikiNode);
	}

	@Reference
	private StagingModelListener<WikiNode> _stagingModelListener;

}