/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.web.internal.info.item.provider;

import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.ERCInfoItemIdentifier;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GroupThreadLocal;

import java.util.Objects;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemDetailsProvider.class
)
public class KBArticleInfoItemDetailsProvider
	implements InfoItemDetailsProvider<KBArticle> {

	@Override
	public InfoItemClassDetails getInfoItemClassDetails() {
		return new InfoItemClassDetails(KBArticle.class.getName());
	}

	@Override
	public InfoItemDetails getInfoItemDetails(KBArticle kbArticle) {
		return getInfoItemDetails(
			_getGroupId(), ClassPKInfoItemIdentifier.class, kbArticle);
	}

	@Override
	public InfoItemDetails getInfoItemDetails(
		long groupId,
		Class<? extends InfoItemIdentifier> infoItemIdentifierClass,
		KBArticle kbArticle) {

		if (Objects.equals(
				infoItemIdentifierClass, ClassPKInfoItemIdentifier.class)) {

			return new InfoItemDetails(
				getInfoItemClassDetails(),
				new InfoItemReference(
					KBArticle.class.getName(), kbArticle.getResourcePrimKey()));
		}

		if (Objects.equals(
				infoItemIdentifierClass, ERCInfoItemIdentifier.class)) {

			return new InfoItemDetails(
				getInfoItemClassDetails(),
				new InfoItemReference(
					KBArticle.class.getName(),
					new ERCInfoItemIdentifier(
						kbArticle.getExternalReferenceCode(),
						_getScopeExternalReferenceCode(groupId, kbArticle))));
		}

		return null;
	}

	private long _getGroupId() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getScopeGroupId();
		}

		Long groupId = GroupThreadLocal.getGroupId();

		if (groupId != null) {
			return groupId;
		}

		throw new IllegalStateException(
			"Neither service context thread local nor group thread local are " +
				"initialized");
	}

	private String _getScopeExternalReferenceCode(
		long groupId, GroupedModel groupedModel) {

		if (groupId == groupedModel.getGroupId()) {
			return null;
		}

		Group group = _groupLocalService.fetchGroup(groupedModel.getGroupId());

		if (group == null) {
			return null;
		}

		return group.getExternalReferenceCode();
	}

	@Reference
	private GroupLocalService _groupLocalService;

}