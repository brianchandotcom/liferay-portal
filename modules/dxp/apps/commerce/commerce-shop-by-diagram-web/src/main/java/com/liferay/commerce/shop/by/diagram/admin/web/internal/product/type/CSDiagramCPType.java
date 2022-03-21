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

package com.liferay.commerce.shop.by.diagram.admin.web.internal.product.type;

import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.shop.by.diagram.constants.CSDiagramCPTypeConstants;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramEntryLocalService;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramPinLocalService;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramSettingLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
@Component(
	configurationPid = "com.liferay.commerce.shop.by.diagram.configuration.CSDiagramCPTypeConfiguration",
	enabled = false, immediate = true,
	property = {
		"commerce.product.type.display.order:Integer=20",
		"commerce.product.type.name=" + CSDiagramCPTypeConstants.NAME
	},
	service = CPType.class
)
public class CSDiagramCPType implements CPType {

	@Override
	public void deleteCPDefinition(long cpDefinitionId) throws PortalException {
		_csDiagramEntryLocalService.deleteCSDiagramEntries(cpDefinitionId);

		_csDiagramPinLocalService.deleteCSDiagramPins(cpDefinitionId);

		_csDiagramSettingLocalService.deleteCSDiagramSettingByCPDefinitionId(
			cpDefinitionId);
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, CSDiagramCPTypeConstants.NAME);
	}

	@Override
	public String getName() {
		return CSDiagramCPTypeConstants.NAME;
	}

	@Override
	public boolean isConfigurationEnabled() {
		return false;
	}

	@Override
	public boolean isMediaEnabled() {
		return false;
	}

	@Override
	public boolean isOptionsEnabled() {
		return false;
	}

	@Override
	public boolean isProductGroupsEnabled() {
		return false;
	}

	@Override
	public boolean isSkusEnabled() {
		return false;
	}

	@Override
	public boolean isSubscriptionEnabled() {
		return false;
	}

	@Reference
	private CSDiagramEntryLocalService _csDiagramEntryLocalService;

	@Reference
	private CSDiagramPinLocalService _csDiagramPinLocalService;

	@Reference
	private CSDiagramSettingLocalService _csDiagramSettingLocalService;

}