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

package com.liferay.commerce.order.content.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.exception.CommerceOrderImporterTypeException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.importer.type.CommerceOrderImporterTypeRegistry;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import java.nio.charset.Charset;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_OPEN_ORDER_CONTENT,
		"mvc.command.name=/commerce_open_order_content/import_csv"
	},
	service = MVCActionCommand.class
)
public class ImportCSVMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		try {
			CSVParser csvParser = _getCSVParser(fileEntryId);

			List<String> headerNames = csvParser.getHeaderNames();

			if (!headerNames.containsAll(ListUtil.fromArray(_HEADER_NAMES))) {
				throw new CommerceOrderImporterTypeException();
			}

			for (CSVRecord csvRecord : csvParser.getRecords()) {
				if (csvRecord.size() < 3) {
					throw new CommerceOrderImporterTypeException();
				}
			}

			sendRedirect(
				actionRequest, actionResponse,
				_getPreviewCommerceOrderRedirect(actionRequest, fileEntryId));
		}
		catch (Exception exception) {
			if (exception instanceof CommerceOrderImporterTypeException) {
				hideDefaultErrorMessage(actionRequest);

				SessionErrors.add(
					actionRequest, "commerceOrderImporterTypeKey",
					ParamUtil.getString(
						actionRequest, "commerceOrderImporterTypeKey"));

				sendRedirect(
					actionRequest, actionResponse,
					ParamUtil.getString(actionRequest, "redirect"));
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}

				hideDefaultErrorMessage(actionRequest);

				SessionErrors.add(
					actionRequest, "commerceOrderImporterTypeKey",
					StringPool.BLANK);

				sendRedirect(
					actionRequest, actionResponse,
					ParamUtil.getString(actionRequest, "redirect"));
			}
		}
	}

	private CSVParser _getCSVParser(long fileEntryId) throws Exception {
		CSVFormat csvFormat = CSVFormat.DEFAULT;

		csvFormat = csvFormat.withFirstRecordAsHeader();
		csvFormat = csvFormat.withIgnoreSurroundingSpaces();
		csvFormat = csvFormat.withNullString(StringPool.BLANK);

		try {
			FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

			return CSVParser.parse(
				FileUtil.createTempFile(fileEntry.getContentStream()),
				Charset.defaultCharset(), csvFormat);
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException, ioException);
			}

			throw new CommerceOrderImporterTypeException();
		}
	}

	private String _getPreviewCommerceOrderRedirect(
			ActionRequest actionRequest, long fileEntryId)
		throws Exception {

		return PortletURLBuilder.create(
			PortletProviderUtil.getPortletURL(
				actionRequest, CommerceOrder.class.getName(),
				PortletProvider.Action.EDIT)
		).setMVCRenderCommandName(
			"/commerce_open_order_content/view_commerce_order_importer_type"
		).setBackURL(
			ParamUtil.getString(actionRequest, "backURL")
		).setParameter(
			"commerceOrderId",
			ParamUtil.getLong(actionRequest, "commerceOrderId")
		).setParameter(
			"commerceOrderImporterTypeKey",
			ParamUtil.getString(actionRequest, "commerceOrderImporterTypeKey")
		).setParameter(
			"fileEntryId", fileEntryId
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	private static final String[] _HEADER_NAMES = {
		"quantity", "skuExternalReferenceCode", "skuId"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		ImportCSVMVCActionCommand.class);

	@Reference
	private CommerceOrderImporterTypeRegistry
		_commerceOrderImporterTypeRegistry;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private Portal _portal;

}