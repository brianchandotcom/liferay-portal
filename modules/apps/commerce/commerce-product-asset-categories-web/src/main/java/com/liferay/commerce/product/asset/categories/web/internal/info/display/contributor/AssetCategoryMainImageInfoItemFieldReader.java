/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.asset.categories.web.internal.info.display.contributor;

import com.liferay.account.constants.AccountConstants;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.commerce.media.CommerceMediaResolverUtil;
import com.liferay.commerce.product.constants.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.commerce.product.util.comparator.CPAttachmentFileEntryPriorityComparator;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.item.field.reader.InfoItemFieldReader;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = InfoItemFieldReader.class)
public class AssetCategoryMainImageInfoItemFieldReader
	implements InfoItemFieldReader<AssetCategory> {

	@Override
	public InfoField getInfoField() {
		return InfoField.builder(
		).infoFieldType(
			ImageInfoFieldType.INSTANCE
		).namespace(
			AssetCategory.class.getSimpleName()
		).name(
			"mainImage"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "main-image")
		).build();
	}

	@Override
	public Object getValue(AssetCategory assetCategory) {
		try {
			CPAttachmentFileEntryPriorityComparator orderByComparator =
				new CPAttachmentFileEntryPriorityComparator(true);

			List<CPAttachmentFileEntry> cpAttachmentFileEntries =
				_cpAttachmentFileEntryService.getCPAttachmentFileEntries(
					_portal.getClassNameId(AssetCategory.class),
					assetCategory.getCategoryId(),
					CPAttachmentFileEntryConstants.TYPE_IMAGE,
					WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator);

			if (ListUtil.isEmpty(cpAttachmentFileEntries)) {
				return null;
			}

			CPAttachmentFileEntry cpAttachmentFileEntry =
				cpAttachmentFileEntries.get(0);

			return CommerceMediaResolverUtil.getURL(
				AccountConstants.ACCOUNT_ENTRY_ID_GUEST,
				cpAttachmentFileEntry.getCPAttachmentFileEntryId());
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetCategoryMainImageInfoItemFieldReader.class);

	@Reference
	private CPAttachmentFileEntryService _cpAttachmentFileEntryService;

	@Reference
	private Portal _portal;

}