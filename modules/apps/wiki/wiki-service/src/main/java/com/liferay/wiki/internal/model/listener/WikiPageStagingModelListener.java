/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.staging.model.listener.StagingModelListener;
import com.liferay.wiki.model.WikiPage;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(service = ModelListener.class)
public class WikiPageStagingModelListener extends BaseModelListener<WikiPage> {

	@Override
	public void onAfterCreate(WikiPage wikiPage) throws ModelListenerException {
		_stagingModelListener.onAfterCreate(wikiPage);
	}

	@Override
	public void onAfterRemove(WikiPage wikiPage) throws ModelListenerException {
		_stagingModelListener.onAfterRemove(wikiPage);
	}

	@Override
	public void onAfterUpdate(WikiPage originalWikiPage, WikiPage wikiPage)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(wikiPage);
	}

	@Reference
	private StagingModelListener<WikiPage> _stagingModelListener;

}