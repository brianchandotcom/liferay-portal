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

package com.liferay.wiki.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.wiki.model.WikiPage;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tomas Polesovsky
 */
@Component(immediate = true, service = ModelListener.class)
public class CycleDetectorWikiPageModelListener
	extends BaseModelListener<WikiPage> {

	@Override
	public void onBeforeCreate(WikiPage model) throws ModelListenerException {
		if (isCycleDetectedInWikiPagesGraph(model)) {
			throw new ModelListenerException(
				"Unable to create wiki page " + model.getTitle() +
					" because a cycle was detected");
		}

		super.onBeforeCreate(model);
	}

	@Override
	public void onBeforeUpdate(WikiPage model) throws ModelListenerException {
		if (isCycleDetectedInWikiPagesGraph(model)) {
			throw new ModelListenerException(
				"Unable to update wiki page " + model.getTitle() +
					" because a cycle was detected");
		}

		super.onBeforeUpdate(model);
	}

	protected boolean isCycleDetectedInWikiPagesGraph(WikiPage wikiPage) {
		String title = wikiPage.getTitle();

		if (Validator.isBlank(title)) {
			return false;
		}

		WikiPage parentPage = wikiPage;

		title = title.trim();

		while (parentPage != null) {
			String parentTitle = parentPage.getParentTitle();

			if (Validator.isBlank(parentTitle)) {
				return false;
			}

			parentTitle = parentTitle.trim();

			if (StringUtil.equalsIgnoreCase(title, parentTitle)) {
				return true;
			}

			parentPage = parentPage.fetchParentPage();
		}

		return false;
	}

}