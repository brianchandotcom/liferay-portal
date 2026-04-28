/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.web.internal.display.context;

import com.liferay.commerce.model.CommerceOrderAttachment;
import com.liferay.commerce.order.web.internal.display.context.helper.CommerceOrderRequestHelper;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionServiceUtil;
import com.liferay.list.type.service.ListTypeEntryServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.Portal;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;

/**
 * @author Tancredi Covioli
 */
public class EditCommerceOrderAttachmentDisplayContext {

	public EditCommerceOrderAttachmentDisplayContext(
		CommerceOrderAttachment commerceOrderAttachment, long commerceOrderId,
		HttpServletRequest httpServletRequest) {

		_commerceOrderAttachment = commerceOrderAttachment;
		_commerceOrderId = commerceOrderId;

		_commerceOrderRequestHelper = new CommerceOrderRequestHelper(
			httpServletRequest);
	}

	public String getAddURL() {
		return _getBaseURL();
	}

	public long getAttachmentId() {
		if (_commerceOrderAttachment == null) {
			return 0;
		}

		return _commerceOrderAttachment.getCommerceOrderAttachmentId();
	}

	public List<ListTypeEntry> getAttachmentTypes() {
		try {
			ListTypeDefinition listTypeDefinition =
				ListTypeDefinitionServiceUtil.
					fetchListTypeDefinitionByExternalReferenceCode(
						"L_COMMERCE_ORDER_ATTACHMENT_TYPES",
						_commerceOrderRequestHelper.getCompanyId());

			if (listTypeDefinition == null) {
				return Collections.emptyList();
			}

			return ListTypeEntryServiceUtil.getListTypeEntries(
				listTypeDefinition.getListTypeDefinitionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return Collections.emptyList();
		}
	}

	public long getCommerceOrderId() {
		return _commerceOrderId;
	}

	public String getEditURL() {
		if (_commerceOrderAttachment == null) {
			return _getBaseURL();
		}

		return StringBundler.concat(
			_getBaseURL(), StringPool.SLASH,
			_commerceOrderAttachment.getCommerceOrderAttachmentId());
	}

	public long getFileEntryId() {
		if (_commerceOrderAttachment == null) {
			return 0;
		}

		return _commerceOrderAttachment.getFileEntryId();
	}

	public String getFileEntryName() {
		long fileEntryId = getFileEntryId();

		if (fileEntryId <= 0) {
			return StringPool.BLANK;
		}

		try {
			FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
				fileEntryId);

			return fileEntry.getFileName();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return StringPool.BLANK;
		}
	}

	public String getMode() {
		if (_commerceOrderAttachment == null) {
			return "add";
		}

		return "edit";
	}

	public String getPriority() {
		if (_commerceOrderAttachment == null) {
			return "0";
		}

		return String.valueOf(_commerceOrderAttachment.getPriority());
	}

	public boolean getRestricted() {
		if (_commerceOrderAttachment == null) {
			return false;
		}

		return _commerceOrderAttachment.isRestricted();
	}

	public String getTitle() {
		if (_commerceOrderAttachment == null) {
			return StringPool.BLANK;
		}

		return _commerceOrderAttachment.getTitle();
	}

	public String getType() {
		if (_commerceOrderAttachment == null) {
			return StringPool.BLANK;
		}

		return _commerceOrderAttachment.getType();
	}

	private String _getBaseURL() {
		return StringBundler.concat(
			Portal.PATH_MODULE, "/headless-commerce-admin-order/v1.0/orders/",
			_commerceOrderId, "/attachments");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCommerceOrderAttachmentDisplayContext.class);

	private final CommerceOrderAttachment _commerceOrderAttachment;
	private final long _commerceOrderId;
	private final CommerceOrderRequestHelper _commerceOrderRequestHelper;

}