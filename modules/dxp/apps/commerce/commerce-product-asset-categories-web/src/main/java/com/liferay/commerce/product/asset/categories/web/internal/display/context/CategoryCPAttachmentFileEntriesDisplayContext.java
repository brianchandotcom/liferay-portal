/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.asset.categories.web.internal.display.context;

import com.liferay.commerce.product.definitions.web.configuration.AttachmentsConfiguration;
import com.liferay.commerce.product.definitions.web.display.context.BaseCPDefinitionsDisplayContext;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;

import java.util.Collections;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CategoryCPAttachmentFileEntriesDisplayContext
	extends BaseCPDefinitionsDisplayContext {

	public CategoryCPAttachmentFileEntriesDisplayContext(
			ActionHelper actionHelper,
			AttachmentsConfiguration attachmentsConfiguration,
			HttpServletRequest httpServletRequest, ItemSelector itemSelector)
		throws PortalException {

		super(actionHelper, httpServletRequest);

		_attachmentsConfiguration = attachmentsConfiguration;
		_itemSelector = itemSelector;
	}

	public CPAttachmentFileEntry getCPAttachmentFileEntry()
		throws PortalException {

		if (_cpAttachmentFileEntry != null) {
			return _cpAttachmentFileEntry;
		}

		_cpAttachmentFileEntry = actionHelper.getCPAttachmentFileEntry(
			cpRequestHelper.getRenderRequest());

		return _cpAttachmentFileEntry;
	}

	public String[] getImageExtensions() {
		return _attachmentsConfiguration.imageExtensions();
	}

	public long getImageMaxSize() {
		return _attachmentsConfiguration.imageMaxSize();
	}

	public String getItemSelectorUrl() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				cpRequestHelper.getRenderRequest());

		ImageItemSelectorCriterion imageItemSelectorCriterion =
			new ImageItemSelectorCriterion();

		imageItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new FileEntryItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "addCategoryCPAttachmentFileEntry",
			imageItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	private final AttachmentsConfiguration _attachmentsConfiguration;
	private CPAttachmentFileEntry _cpAttachmentFileEntry;
	private final ItemSelector _itemSelector;

}