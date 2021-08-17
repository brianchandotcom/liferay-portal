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
import com.liferay.commerce.shop.by.diagram.configuration.CPDefinitionDiagramSettingImageConfiguration;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramSettingService;
import com.liferay.commerce.shop.by.diagram.type.CPDefinitionDiagramType;
import com.liferay.commerce.shop.by.diagram.type.CPDefinitionDiagramTypeRegistry;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.petra.string.StringPool;
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
public class CPDefinitionDiagramSettingDisplayContext
	extends BaseCPDefinitionsDisplayContext {

	public CPDefinitionDiagramSettingDisplayContext(
		ActionHelper actionHelper, HttpServletRequest httpServletRequest,
		CPDefinitionDiagramSettingImageConfiguration
			cpDefinitionDiagramSettingImageConfiguration,
		CPDefinitionDiagramSettingService cpDefinitionDiagramSettingService,
		CPDefinitionDiagramTypeRegistry cpDefinitionDiagramTypeRegistry,
		DLURLHelper dlURLHelper, ItemSelector itemSelector) {

		super(actionHelper, httpServletRequest);

		_cpDefinitionDiagramSettingImageConfiguration =
			cpDefinitionDiagramSettingImageConfiguration;
		_cpDefinitionDiagramSettingService = cpDefinitionDiagramSettingService;
		_cpDefinitionDiagramTypeRegistry = cpDefinitionDiagramTypeRegistry;
		_dlURLHelper = dlURLHelper;
		_itemSelector = itemSelector;
	}

	public CPDefinitionDiagramSetting fetchCPDefinitionDiagramSetting()
		throws PortalException {

		if (_cpDefinitionDiagramSetting != null) {
			return _cpDefinitionDiagramSetting;
		}

		_cpDefinitionDiagramSetting =
			_cpDefinitionDiagramSettingService.
				fetchCPDefinitionDiagramSettingByCPDefinitionId(
					getCPDefinitionId());

		return _cpDefinitionDiagramSetting;
	}

	public FileEntry fetchFileEntry() throws PortalException {
		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			fetchCPDefinitionDiagramSetting();

		if (cpDefinitionDiagramSetting == null) {
			return null;
		}

		try {
			CPAttachmentFileEntry cpAttachmentFileEntry =
				cpDefinitionDiagramSetting.getCPAttachmentFileEntry();

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

	public String getCPDefinitionDiagramEntriesApiURL() throws PortalException {
		CPDefinition cpDefinition = getCPDefinition();

		return "/o/headless-commerce-admin-catalog/v1.0/products/" +
			cpDefinition.getCProductId() + "/diagramEntries";
	}

	public CPDefinitionDiagramType getCPDefinitionDiagramType(String type) {
		return _cpDefinitionDiagramTypeRegistry.getCPDefinitionDiagramType(
			type);
	}

	public List<CPDefinitionDiagramType> getCPDefinitionDiagramTypes() {
		return _cpDefinitionDiagramTypeRegistry.getCPDefinitionDiagramTypes();
	}

	public String[] getImageExtensions() {
		return _cpDefinitionDiagramSettingImageConfiguration.imageExtensions();
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
		return _cpDefinitionDiagramSettingImageConfiguration.imageMaxSize();
	}

	public String getImageURL() {
		try {
			FileEntry fileEntry = fetchFileEntry();

			if (fileEntry != null) {
				return _dlURLHelper.getDownloadURL(
					fileEntry, fileEntry.getFileVersion(), null,
					StringPool.BLANK);
			}

			return StringPool.BLANK;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
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
		CPDefinitionDiagramSettingDisplayContext.class);

	private CPDefinitionDiagramSetting _cpDefinitionDiagramSetting;
	private final CPDefinitionDiagramSettingImageConfiguration
		_cpDefinitionDiagramSettingImageConfiguration;
	private final CPDefinitionDiagramSettingService
		_cpDefinitionDiagramSettingService;
	private final CPDefinitionDiagramTypeRegistry
		_cpDefinitionDiagramTypeRegistry;
	private final DLURLHelper _dlURLHelper;
	private final ItemSelector _itemSelector;

}