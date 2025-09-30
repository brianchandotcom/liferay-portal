/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.web.internal.layout.display.page;

import com.liferay.asset.util.AssetHelper;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.ERCInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.layout.display.page.BaseLayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = LayoutDisplayPageProvider.class)
public class KBArticleLayoutDisplayPageProvider
	extends BaseLayoutDisplayPageProvider<KBArticle> {

	@Override
	public String getClassName() {
		return KBArticle.class.getName();
	}

	@Override
	public String getDefaultURLSeparator() {
		return FriendlyURLResolverConstants.
			URL_SEPARATOR_KNOWLEDGE_BASE_ARTICLE;
	}

	@Override
	public LayoutDisplayPageObjectProvider<KBArticle>
		getLayoutDisplayPageObjectProvider(
			InfoItemReference infoItemReference) {

		return getLayoutDisplayPageObjectProvider(
			_getGroupId(), infoItemReference);
	}

	@Override
	public LayoutDisplayPageObjectProvider<KBArticle>
		getLayoutDisplayPageObjectProvider(KBArticle kbArticle) {

		try {
			KBArticle latestKBArticle =
				_kbArticleLocalService.fetchLatestKBArticle(
					kbArticle.getResourcePrimKey(), kbArticle.getGroupId());

			if ((latestKBArticle == null) || latestKBArticle.isExpired()) {
				return null;
			}

			return new KBArticleLayoutDisplayPageObjectProvider(
				kbArticle, _assetHelper);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Override
	public LayoutDisplayPageObjectProvider<KBArticle>
		getLayoutDisplayPageObjectProvider(
			long groupId, InfoItemReference infoItemReference) {

		return _getLayoutDisplayPageObjectProvider(
			_getGroupId(groupId, infoItemReference.getInfoItemIdentifier()),
			infoItemReference);
	}

	@Override
	public LayoutDisplayPageObjectProvider<KBArticle>
		getLayoutDisplayPageObjectProvider(long groupId, String urlTitle) {

		try {
			List<String> parts = StringUtil.split(urlTitle, CharPool.SLASH);

			if (parts.isEmpty()) {
				return null;
			}

			KBArticle kbArticle =
				_kbArticleLocalService.fetchKBArticleByUrlTitle(
					groupId, _getKBFolderId(groupId, parts),
					parts.get(parts.size() - 1));

			if (kbArticle == null) {
				return null;
			}

			KBArticle latestKBArticle =
				_kbArticleLocalService.fetchLatestKBArticle(
					kbArticle.getResourcePrimKey(), kbArticle.getGroupId());

			if ((latestKBArticle == null) || latestKBArticle.isExpired()) {
				return null;
			}

			return new KBArticleLayoutDisplayPageObjectProvider(
				kbArticle, _assetHelper);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
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
		long groupId, InfoItemIdentifier infoItemIdentifier) {

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

			Group group = GroupLocalServiceUtil.getGroupByExternalReferenceCode(
				ercInfoItemIdentifier.getScopeExternalReferenceCode(),
				_getCompanyId());

			return group.getGroupId();
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	private long _getKBFolderId(long groupId, List<String> urlTitleParts) {
		if (urlTitleParts.size() == 1) {
			return KBFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}

		KBArticle kbArticle = _kbArticleLocalService.fetchKBArticleByUrlTitle(
			groupId, urlTitleParts.get(0), urlTitleParts.get(1));

		if (kbArticle != null) {
			return kbArticle.getKbFolderId();
		}

		return KBFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}

	private KBArticleLayoutDisplayPageObjectProvider
		_getLayoutDisplayPageObjectProvider(
			long groupId, InfoItemReference infoItemReference) {

		try {
			InfoItemIdentifier infoItemIdentifier =
				infoItemReference.getInfoItemIdentifier();

			if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier) &&
				!(infoItemIdentifier instanceof ERCInfoItemIdentifier)) {

				return null;
			}

			KBArticle kbArticle = null;

			if (infoItemIdentifier instanceof ClassPKInfoItemIdentifier) {
				ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
					(ClassPKInfoItemIdentifier)
						infoItemReference.getInfoItemIdentifier();

				kbArticle = _kbArticleLocalService.fetchKBArticle(
					classPKInfoItemIdentifier.getClassPK());

				if (kbArticle == null) {
					kbArticle = _kbArticleLocalService.fetchLatestKBArticle(
						classPKInfoItemIdentifier.getClassPK(),
						WorkflowConstants.STATUS_ANY);
				}
			}
			else {
				ERCInfoItemIdentifier ercInfoItemIdentifier =
					(ERCInfoItemIdentifier)infoItemIdentifier;

				kbArticle =
					_kbArticleLocalService.
						fetchLatestKBArticleByExternalReferenceCode(
							groupId,
							ercInfoItemIdentifier.getExternalReferenceCode());
			}

			if ((kbArticle == null) || kbArticle.isDraft()) {
				return null;
			}

			return new KBArticleLayoutDisplayPageObjectProvider(
				kbArticle, _assetHelper);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private KBArticleLocalService _kbArticleLocalService;

}