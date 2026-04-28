/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.feature.flag;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderAttachment;
import com.liferay.commerce.service.CommerceOrderAttachmentLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(
	property = "feature.flag.key=LPD-6252", service = FeatureFlagListener.class
)
public class CommerceOrderAttachmentFeatureFlagListener
	implements FeatureFlagListener {

	@Override
	public void onValue(
		long companyId, String featureFlagKey, boolean enabled) {

		if (!enabled || !Objects.equals(featureFlagKey, "LPD-6252")) {
			return;
		}

		// The data migration will be moved to an upgrade process when
		// the feature flag LPD-6252 is removed. It cannot be moved now
		// because any attachment created between the release and the
		// removal of the feature flag would be lost.

		long classNameId = _classNameLocalService.getClassNameId(
			CommerceOrder.class);

		ActionableDynamicQuery actionableDynamicQuery =
			_dlFileEntryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property classNameIdProperty = PropertyFactoryUtil.forName(
					"classNameId");

				dynamicQuery.add(classNameIdProperty.eq(classNameId));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(DLFileEntry dlFileEntry) -> _migrate(dlFileEntry));

		try {
			actionableDynamicQuery.performActions();

			Indexer<CommerceOrderAttachment> indexer =
				_indexerRegistry.nullSafeGetIndexer(
					CommerceOrderAttachment.class);

			indexer.reindexCompany(companyId);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	private void _migrate(DLFileEntry dlFileEntry) throws PortalException {
		if (ListUtil.exists(
				_commerceOrderAttachmentLocalService.
					getCommerceOrderAttachments(
						dlFileEntry.getClassPK(), QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null),
				commerceOrderAttachment ->
					commerceOrderAttachment.getFileEntryId() ==
						dlFileEntry.getFileEntryId())) {

			return;
		}

		CommerceOrderAttachment commerceOrderAttachment =
			_commerceOrderAttachmentLocalService.createCommerceOrderAttachment(
				_counterLocalService.increment());

		commerceOrderAttachment.setGroupId(dlFileEntry.getGroupId());
		commerceOrderAttachment.setCompanyId(dlFileEntry.getCompanyId());
		commerceOrderAttachment.setUserId(dlFileEntry.getUserId());
		commerceOrderAttachment.setUserName(dlFileEntry.getUserName());
		commerceOrderAttachment.setCreateDate(dlFileEntry.getCreateDate());
		commerceOrderAttachment.setModifiedDate(dlFileEntry.getModifiedDate());
		commerceOrderAttachment.setCommerceOrderId(dlFileEntry.getClassPK());
		commerceOrderAttachment.setFileEntryId(dlFileEntry.getFileEntryId());
		commerceOrderAttachment.setPriority(0);
		commerceOrderAttachment.setRestricted(false);
		commerceOrderAttachment.setTitle(dlFileEntry.getTitle());
		commerceOrderAttachment.setType("purchaseOrderDocument");

		commerceOrderAttachment =
			_commerceOrderAttachmentLocalService.addCommerceOrderAttachment(
				commerceOrderAttachment);

		_resourceLocalService.addModelResources(
			commerceOrderAttachment.getCompanyId(),
			commerceOrderAttachment.getGroupId(),
			commerceOrderAttachment.getUserId(),
			CommerceOrderAttachment.class.getName(),
			commerceOrderAttachment.getCommerceOrderAttachmentId(), null, null);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderAttachmentFeatureFlagListener.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CommerceOrderAttachmentLocalService
		_commerceOrderAttachmentLocalService;

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private ResourceLocalService _resourceLocalService;

}