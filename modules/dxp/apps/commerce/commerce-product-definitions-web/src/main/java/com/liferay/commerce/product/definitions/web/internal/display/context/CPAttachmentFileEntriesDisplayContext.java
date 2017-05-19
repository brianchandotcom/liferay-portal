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

package com.liferay.commerce.product.definitions.web.internal.display.context;

import com.liferay.commerce.product.definitions.web.display.context.BaseCPDefinitionsDisplayContext;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CPAttachmentFileEntriesDisplayContext
	extends BaseCPDefinitionsDisplayContext<CPAttachmentFileEntry> {

	public CPAttachmentFileEntriesDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CPAttachmentFileEntryService cpAttachmentFileEntryService,
			ItemSelector itemSelector)
		throws PortalException {

		super(actionHelper, httpServletRequest, "CPAttachmentFileEntry");

		setDefaultOrderByCol("priority");
		setDefaultOrderByType("asc");

		_cpAttachmentFileEntryService = cpAttachmentFileEntryService;
		_itemSelector = itemSelector;
	}

	@Override
	public SearchContainer<CPAttachmentFileEntry> getSearchContainer()
		throws PortalException {

		return null;
	}

	private final CPAttachmentFileEntryService _cpAttachmentFileEntryService;
	private final ItemSelector _itemSelector;

}