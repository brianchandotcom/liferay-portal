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

package com.liferay.commerce.order.content.web.internal.importer.type;

import com.liferay.commerce.account.configuration.CommerceAccountGroupServiceConfiguration;
import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.configuration.CommerceOrderImporterTypeConfiguration;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.exception.CommerceOrderImporterTypeException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.content.web.internal.importer.type.util.CommerceOrderImporterTypeUtil;
import com.liferay.commerce.order.importer.item.CommerceOrderImporterItem;
import com.liferay.commerce.order.importer.item.CommerceOrderImporterItemImpl;
import com.liferay.commerce.order.importer.type.CommerceOrderImporterType;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.nio.charset.Charset;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	configurationPid = "com.liferay.commerce.configuration.CommerceOrderImporterTypeConfiguration",
	enabled = false, immediate = true,
	property = "commerce.order.importer.type.key=" + CSVCommerceOrderImporterTypeImpl.KEY,
	service = CommerceOrderImporterType.class
)
public class CSVCommerceOrderImporterTypeImpl
	implements CommerceOrderImporterType {

	public static final String KEY = "csv";

	@Override
	public CommerceOrder getCommerceOrder(
			CommerceOrder commerceOrder, Object object)
		throws Exception {

		if ((object == null) || !(object instanceof FileEntry)) {
			throw new CommerceOrderImporterTypeException();
		}

		return CommerceOrderImporterTypeUtil.getCommerceOrder(
			_commerceContextFactory, commerceOrder,
			_getCommerceOrderImporterItems(
				commerceOrder.getCompanyId(), (FileEntry)object),
			_commerceOrderItemService, _commerceOrderService,
			_userLocalService);
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.format(resourceBundle, "import-from-x", KEY);
	}

	@Override
	public boolean isActive(CommerceOrder commerceOrder)
		throws PortalException {

		if (!_commerceOrderImporterTypeConfiguration.enabled()) {
			return false;
		}

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
				commerceOrder.getGroupId());

		CommerceAccountGroupServiceConfiguration
			commerceAccountGroupServiceConfiguration =
				_configurationProvider.getConfiguration(
					CommerceAccountGroupServiceConfiguration.class,
					new GroupServiceSettingsLocator(
						commerceChannel.getGroupId(),
						CommerceAccountConstants.SERVICE_NAME));

		if (commerceAccountGroupServiceConfiguration.commerceSiteType() ==
				CommerceAccountConstants.SITE_TYPE_B2C) {

			return false;
		}

		return true;
	}

	@Override
	public void render(
			CommerceOrder commerceOrder, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/pending_commerce_orders/importer_type/csv.jsp");
	}

	@Override
	public void renderCommerceOrderPreview(
			CommerceOrder commerceOrder, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/pending_commerce_orders/importer_type/common/preview.jsp");
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_commerceOrderImporterTypeConfiguration =
			ConfigurableUtil.createConfigurable(
				CommerceOrderImporterTypeConfiguration.class, properties);
	}

	private CommerceOrderImporterItem[] _getCommerceOrderImporterItems(
			long companyId, FileEntry fileEntry)
		throws Exception {

		CSVParser csvParser = _getCSVParser(fileEntry);

		List<CSVRecord> csvRecords = csvParser.getRecords();

		CommerceOrderImporterItem[] commerceOrderImporterItems =
			new CommerceOrderImporterItem[csvRecords.size()];

		for (int i = 0; i < csvRecords.size(); i++) {
			CSVRecord csvRecord = csvRecords.get(i);

			String skuExternalReferenceCode = csvRecord.get(
				"skuExternalReferenceCode");
			long skuId = GetterUtil.getLong(csvRecord.get("skuId"));
			int quantity = GetterUtil.getInteger(csvRecord.get("quantity"));

			CPInstance cpInstance = null;

			if (skuId > 0) {
				cpInstance = _cpInstanceLocalService.fetchCPInstance(skuId);
			}

			if ((cpInstance == null) &&
				Validator.isNotNull(skuExternalReferenceCode)) {

				cpInstance =
					_cpInstanceLocalService.
						fetchCPInstanceByExternalReferenceCode(
							companyId, skuExternalReferenceCode);
			}

			if ((cpInstance == null) || (quantity < 1)) {
				throw new CommerceOrderImporterTypeException();
			}

			CommerceOrderImporterItemImpl commerceOrderImporterItemImpl =
				new CommerceOrderImporterItemImpl();

			commerceOrderImporterItemImpl.setCPInstanceId(
				cpInstance.getCPInstanceId());
			commerceOrderImporterItemImpl.setQuantity(quantity);

			commerceOrderImporterItems[i] = commerceOrderImporterItemImpl;
		}

		return commerceOrderImporterItems;
	}

	private CSVParser _getCSVParser(FileEntry fileEntry) throws Exception {
		CSVFormat csvFormat = CSVFormat.DEFAULT;

		csvFormat = csvFormat.withFirstRecordAsHeader();
		csvFormat = csvFormat.withIgnoreSurroundingSpaces();
		csvFormat = csvFormat.withNullString(StringPool.BLANK);

		try {
			return CSVParser.parse(
				FileUtil.createTempFile(fileEntry.getContentStream()),
				Charset.defaultCharset(), csvFormat);
		}
		catch (IOException ioException) {
			_log.error(ioException, ioException);

			throw new CommerceOrderImporterTypeException();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CSVCommerceOrderImporterTypeImpl.class);

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceContextFactory _commerceContextFactory;

	private volatile CommerceOrderImporterTypeConfiguration
		_commerceOrderImporterTypeConfiguration;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private UserLocalService _userLocalService;

}