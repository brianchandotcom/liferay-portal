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
import com.liferay.commerce.product.portlet.action.ActionHelper;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramSettingService;
import com.liferay.commerce.shop.by.diagram.type.CPDefinitionDiagramType;
import com.liferay.commerce.shop.by.diagram.type.CPDefinitionDiagramTypeRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionDiagramSettingDisplayContext
	extends BaseCPDefinitionsDisplayContext {

	public CPDefinitionDiagramSettingDisplayContext(
		ActionHelper actionHelper, HttpServletRequest httpServletRequest,
		CPDefinitionDiagramSettingService cpDefinitionDiagramSettingService,
		CPDefinitionDiagramTypeRegistry cpDefinitionDiagramTypeRegistry) {

		super(actionHelper, httpServletRequest);

		_cpDefinitionDiagramSettingService = cpDefinitionDiagramSettingService;
		_cpDefinitionDiagramTypeRegistry = cpDefinitionDiagramTypeRegistry;
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

	public CPDefinitionDiagramType getCPDefinitionDiagramType(String type) {
		return _cpDefinitionDiagramTypeRegistry.getCPDefinitionDiagramType(
			type);
	}

	public List<CPDefinitionDiagramType> getCPDefinitionDiagramTypes() {
		return _cpDefinitionDiagramTypeRegistry.getCPDefinitionDiagramTypes();
	}

	public FileEntry getFileEntry() throws PortalException {
		CPDefinitionDiagramSetting cpDefinitionVirtualSetting =
			fetchCPDefinitionDiagramSetting();

		if (cpDefinitionVirtualSetting == null) {
			return null;
		}

		try {
			CPAttachmentFileEntry cpAttachmentFileEntry =
				cpDefinitionVirtualSetting.getCPAttachmentFileEntry();

			return cpAttachmentFileEntry.getFileEntry();
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
	private final CPDefinitionDiagramSettingService
		_cpDefinitionDiagramSettingService;
	private final CPDefinitionDiagramTypeRegistry
		_cpDefinitionDiagramTypeRegistry;

}