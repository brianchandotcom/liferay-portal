/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.channel.internal.resource.v1_0;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.exception.NoSuchCPDisplayLayoutException;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.DefaultProductDisplayPage;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.DefaultProductDisplayPageResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.settings.FallbackKeysSettingsUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/default-product-display-page.properties",
	scope = ServiceScope.PROTOTYPE,
	service = DefaultProductDisplayPageResource.class
)
public class DefaultProductDisplayPageResourceImpl
	extends BaseDefaultProductDisplayPageResourceImpl {

	public void deleteChannelByExternalReferenceCodeDefaultProductDisplayPage(
			String externalReferenceCode)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.
				getCommerceChannelByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		deleteChannelIdDefaultProductDisplayPage(
			commerceChannel.getCommerceChannelId());
	}

	public void deleteChannelIdDefaultProductDisplayPage(Long id)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(id);

		Settings settings = FallbackKeysSettingsUtil.getSettings(
			new GroupServiceSettingsLocator(
				commerceChannel.getGroupId(),
				CPConstants.RESOURCE_NAME_CP_DISPLAY_LAYOUT));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		modifiableSettings.reset("productLayoutUuid");

		modifiableSettings.store();
	}

	public DefaultProductDisplayPage
			getChannelByExternalReferenceCodeDefaultProductDisplayPage(
				String externalReferenceCode)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.
				getCommerceChannelByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		return getChannelIdDefaultProductDisplayPage(
			commerceChannel.getCommerceChannelId());
	}

	public DefaultProductDisplayPage getChannelIdDefaultProductDisplayPage(
			Long id)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(id);

		Settings settings = FallbackKeysSettingsUtil.getSettings(
			new GroupServiceSettingsLocator(
				commerceChannel.getGroupId(),
				CPConstants.RESOURCE_NAME_CP_DISPLAY_LAYOUT));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		String productLayoutUuid = modifiableSettings.getValue(
			"productLayoutUuid", StringPool.BLANK);

		if (Validator.isBlank(productLayoutUuid)) {
			throw new NoSuchCPDisplayLayoutException();
		}

		return _toDefaultProductDisplayPage(commerceChannel);
	}

	public DefaultProductDisplayPage
			postChannelByExternalReferenceCodeDefaultProductDisplayPage(
				String externalReferenceCode,
				DefaultProductDisplayPage defaultProductDisplayPage)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.
				getCommerceChannelByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		return postChannelIdDefaultProductDisplayPage(
			commerceChannel.getCommerceChannelId(), defaultProductDisplayPage);
	}

	public DefaultProductDisplayPage postChannelIdDefaultProductDisplayPage(
			Long id, DefaultProductDisplayPage defaultProductDisplayPage)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(id);

		_validateLayoutUuid(
			commerceChannel.getSiteGroupId(),
			defaultProductDisplayPage.getPageUuid());

		Settings settings = FallbackKeysSettingsUtil.getSettings(
			new GroupServiceSettingsLocator(
				commerceChannel.getGroupId(),
				CPConstants.RESOURCE_NAME_CP_DISPLAY_LAYOUT));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		modifiableSettings.setValue(
			"productLayoutUuid", defaultProductDisplayPage.getPageUuid());

		modifiableSettings.store();

		return _toDefaultProductDisplayPage(commerceChannel);
	}

	private Map<String, Map<String, String>> _getActions(
		CommerceChannel commerceChannel) {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				ActionKeys.DELETE, commerceChannel.getCommerceChannelId(),
				"deleteChannelIdDefaultProductDisplayPage",
				commerceChannel.getUserId(), CommerceChannel.class.getName(),
				commerceChannel.getGroupId())
		).put(
			"get",
			addAction(
				ActionKeys.VIEW, commerceChannel.getCommerceChannelId(),
				"getChannelIdDefaultProductDisplayPage",
				commerceChannel.getUserId(), CommerceChannel.class.getName(),
				commerceChannel.getGroupId())
		).put(
			"post",
			addAction(
				ActionKeys.UPDATE, commerceChannel.getCommerceChannelId(),
				"postChannelIdDefaultProductDisplayPage",
				commerceChannel.getUserId(), CommerceChannel.class.getName(),
				commerceChannel.getGroupId())
		).build();
	}

	private DefaultProductDisplayPage _toDefaultProductDisplayPage(
			CommerceChannel commerceChannel)
		throws Exception {

		return _defaultProductDisplayPageDTOConvertor.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commerceChannel), _dtoConverterRegistry,
				commerceChannel.getCommerceChannelId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private void _validateLayoutUuid(long groupId, String uuid)
		throws Exception {

		Layout layout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
			uuid, groupId, false);

		if (layout == null) {
			layout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
				uuid, groupId, true);
		}

		if (layout == null) {
			throw new NoSuchLayoutException();
		}
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.channel.internal.dto.v1_0.converter.DefaultProductDisplayPageDTOConverter)"
	)
	private DTOConverter<ModifiableSettings, DefaultProductDisplayPage>
		_defaultProductDisplayPageDTOConvertor;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private LayoutLocalService _layoutLocalService;

}