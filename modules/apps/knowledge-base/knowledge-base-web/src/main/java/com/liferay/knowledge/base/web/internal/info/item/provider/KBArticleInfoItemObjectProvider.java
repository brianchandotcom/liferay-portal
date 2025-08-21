/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.web.internal.info.item.provider;

import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.ERCInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	property = {
		"info.item.identifier=com.liferay.info.item.ClassPKInfoItemIdentifier",
		"info.item.identifier=com.liferay.info.item.ERCInfoItemIdentifier",
		"item.class.name=com.liferay.knowledge.base.model.KBArticle",
		"service.ranking:Integer=100"
	},
	service = InfoItemObjectProvider.class
)
public class KBArticleInfoItemObjectProvider
	implements InfoItemObjectProvider<KBArticle> {

	@Override
	public KBArticle getInfoItem(InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

		return getInfoItem(_getGroupId(), infoItemIdentifier);
	}

	@Override
	public KBArticle getInfoItem(
			long groupId, InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

		return _getInfoItem(
			_getGroupId(groupId, infoItemIdentifier), infoItemIdentifier);
	}

	private long _getCompanyId() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getCompanyId();
		}

		Long companyId = CompanyThreadLocal.getCompanyId();

		if (companyId != null) {
			return companyId;
		}

		throw new IllegalStateException(
			"Neither service context thread local nor company thread local " +
				"are initialized");
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

	private long _getGroupId(
			long groupId, InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

		try {
			if (!(infoItemIdentifier instanceof ERCInfoItemIdentifier)) {
				return groupId;
			}

			ERCInfoItemIdentifier ercInfoItemIdentifier =
				(ERCInfoItemIdentifier)infoItemIdentifier;

			if (Validator.isNull(
					ercInfoItemIdentifier.getScopeExternalReferenceCode())) {

				return groupId;
			}

			Group group = _groupLocalService.getGroupByExternalReferenceCode(
				ercInfoItemIdentifier.getScopeExternalReferenceCode(),
				_getCompanyId());

			return group.getGroupId();
		}
		catch (PortalException portalException) {
			throw new NoSuchInfoItemException(portalException);
		}
	}

	private KBArticle _getInfoItem(
			long groupId, InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier) &&
			!(infoItemIdentifier instanceof ERCInfoItemIdentifier)) {

			throw new NoSuchInfoItemException(
				"Unsupported info item identifier " + infoItemIdentifier);
		}

		KBArticle kbArticle = null;

		if (infoItemIdentifier instanceof ClassPKInfoItemIdentifier) {
			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)infoItemIdentifier;

			kbArticle = _getKbArticle(
				classPKInfoItemIdentifier.getClassPK(),
				classPKInfoItemIdentifier.getVersion());
		}
		else {
			ERCInfoItemIdentifier ercInfoItemIdentifier =
				(ERCInfoItemIdentifier)infoItemIdentifier;

			kbArticle = _getKbArticle(
				ercInfoItemIdentifier.getExternalReferenceCode(), groupId,
				ercInfoItemIdentifier.getVersion());
		}

		if ((kbArticle == null) ||
			(!Objects.equals(
				infoItemIdentifier.getVersion(),
				InfoItemIdentifier.VERSION_LATEST) &&
			 kbArticle.isDraft())) {

			throw new NoSuchInfoItemException(
				"Unable to get knowledge base article with info item " +
					"identifier " + infoItemIdentifier);
		}

		return kbArticle;
	}

	private KBArticle _getKbArticle(long classPK, String version) {
		if (Validator.isNull(version) ||
			Objects.equals(
				version, InfoItemIdentifier.VERSION_LATEST_APPROVED)) {

			return _kbArticleLocalService.fetchLatestKBArticle(
				classPK, WorkflowConstants.STATUS_APPROVED);
		}

		if (Objects.equals(version, InfoItemIdentifier.VERSION_LATEST)) {
			return _kbArticleLocalService.fetchLatestKBArticle(
				classPK, WorkflowConstants.STATUS_ANY);
		}

		KBArticle latestKBArticle = _kbArticleLocalService.fetchLatestKBArticle(
			classPK, WorkflowConstants.STATUS_ANY);

		return _kbArticleLocalService.fetchKBArticle(
			classPK, latestKBArticle.getGroupId(),
			GetterUtil.getInteger(version));
	}

	private KBArticle _getKbArticle(
		String externalReferenceCode, long groupId, String version) {

		if (Validator.isNull(version) ||
			Objects.equals(
				version, InfoItemIdentifier.VERSION_LATEST_APPROVED)) {

			return _kbArticleLocalService.
				fetchLatestKBArticleByExternalReferenceCode(
					groupId, externalReferenceCode,
					WorkflowConstants.STATUS_APPROVED);
		}

		if (Objects.equals(version, InfoItemIdentifier.VERSION_LATEST)) {
			return _kbArticleLocalService.
				fetchLatestKBArticleByExternalReferenceCode(
					groupId, externalReferenceCode);
		}

		return _kbArticleLocalService.fetchKBArticleByExternalReferenceCode(
			groupId, externalReferenceCode, GetterUtil.getInteger(version));
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private KBArticleLocalService _kbArticleLocalService;

}