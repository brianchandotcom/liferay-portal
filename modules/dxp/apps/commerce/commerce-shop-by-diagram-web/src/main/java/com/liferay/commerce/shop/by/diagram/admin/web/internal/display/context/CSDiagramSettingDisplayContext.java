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

package com.liferay.commerce.shop.by.diagram.admin.web.internal.display.context;

import com.liferay.commerce.product.display.context.BaseCPDefinitionsDisplayContext;
import com.liferay.commerce.product.exception.NoSuchCPAttachmentFileEntryException;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.portlet.action.ActionHelper;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.shop.by.diagram.configuration.CSDiagramSettingImageConfiguration;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramSettingService;
import com.liferay.commerce.shop.by.diagram.type.CSDiagramType;
import com.liferay.commerce.shop.by.diagram.type.CSDiagramTypeRegistry;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CSDiagramSettingDisplayContext
	extends BaseCPDefinitionsDisplayContext {

	public CSDiagramSettingDisplayContext(
		ActionHelper actionHelper, HttpServletRequest httpServletRequest,
		CSDiagramSettingImageConfiguration csDiagramSettingImageConfiguration,
		CSDiagramSettingService csDiagramSettingService,
		CSDiagramTypeRegistry csDiagramTypeRegistry,
		ItemSelector itemSelector) {

		super(actionHelper, httpServletRequest);

		_csDiagramSettingImageConfiguration =
			csDiagramSettingImageConfiguration;
		_csDiagramSettingService = csDiagramSettingService;
		_csDiagramTypeRegistry = csDiagramTypeRegistry;
		_itemSelector = itemSelector;
	}

	public CSDiagramSetting fetchCSDiagramSetting() throws PortalException {
		if (_csDiagramSetting != null) {
			return _csDiagramSetting;
		}

		_csDiagramSetting =
			_csDiagramSettingService.fetchCSDiagramSettingByCPDefinitionId(
				getCPDefinitionId());

		return _csDiagramSetting;
	}

	public FileEntry fetchFileEntry() throws PortalException {
		CSDiagramSetting csDiagramSetting = fetchCSDiagramSetting();

		if (csDiagramSetting == null) {
			return null;
		}

		try {
			CPAttachmentFileEntry cpAttachmentFileEntry =
				csDiagramSetting.getCPAttachmentFileEntry();

			return cpAttachmentFileEntry.fetchFileEntry();
		}
		catch (NoSuchCPAttachmentFileEntryException
					noSuchCPAttachmentFileEntryException) {

			if (_log.isInfoEnabled()) {
				_log.info(
					noSuchCPAttachmentFileEntryException,
					noSuchCPAttachmentFileEntryException);
			}

			return null;
		}
	}

	public String getCSDiagramEntriesAPIURL() throws PortalException {
		CPDefinition cpDefinition = getCPDefinition();

		return "/o/headless-commerce-admin-catalog/v1.0/products/" +
			cpDefinition.getCProductId() + "/mapped-products";
	}

	public CSDiagramType getCSDiagramType(String type) {
		return _csDiagramTypeRegistry.getCSDiagramType(type);
	}

	public List<CSDiagramType> getCSDiagramTypes() {
		return _csDiagramTypeRegistry.getCSDiagramTypes();
	}

	public String[] getImageExtensions() {
		return _csDiagramSettingImageConfiguration.imageExtensions();
	}

	public String getImageItemSelectorUrl() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				cpRequestHelper.getRenderRequest());

		ImageItemSelectorCriterion imageItemSelectorCriterion =
			new ImageItemSelectorCriterion();

		imageItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new FileEntryItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, "addFileEntry",
			imageItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	public long getImageMaxSize() {
		return _csDiagramSettingImageConfiguration.imageMaxSize();
	}

	public double getRadius() {
		return _csDiagramSettingImageConfiguration.radius();
	}

	@Override
	public String getScreenNavigationCategoryKey() {
		CPType cpType = null;

		try {
			cpType = getCPType();
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		if (cpType != null) {
			return cpType.getName();
		}

		return super.getScreenNavigationCategoryKey();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CSDiagramSettingDisplayContext.class);

	private CSDiagramSetting _csDiagramSetting;
	private final CSDiagramSettingImageConfiguration
		_csDiagramSettingImageConfiguration;
	private final CSDiagramSettingService _csDiagramSettingService;
	private final CSDiagramTypeRegistry _csDiagramTypeRegistry;
	private final ItemSelector _itemSelector;

}