/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.headless.delivery.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.delivery.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.ParentKnowledgeBaseFolderUtil;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseFolderResource;
import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.constants.KBConstants;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.knowledge.base.service.KBFolderLocalService;
import com.liferay.knowledge.base.service.KBFolderService;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/knowledge-base-folder.properties",
	scope = ServiceScope.PROTOTYPE, service = KnowledgeBaseFolderResource.class
)
public class KnowledgeBaseFolderResourceImpl
	extends BaseKnowledgeBaseFolderResourceImpl {

	@Override
	public void deleteKnowledgeBaseFolder(Long knowledgeBaseFolderId)
		throws Exception {

		_kbFolderService.deleteKBFolder(knowledgeBaseFolderId);
	}

	@Override
	public void deleteSiteKnowledgeBaseFolderByExternalReferenceCode(
			Long siteId, String externalReferenceCode)
		throws Exception {

		KBFolder kbFolder =
			_kbFolderLocalService.getKBFolderByExternalReferenceCode(
				externalReferenceCode, siteId);

		_kbFolderService.deleteKBFolder(kbFolder.getKbFolderId());
	}

	@Override
	public KnowledgeBaseFolder getKnowledgeBaseFolder(
			Long knowledgeBaseFolderId)
		throws Exception {

		return _toKnowledgeBaseFolder(
			_kbFolderService.getKBFolder(knowledgeBaseFolderId));
	}

	@Override
	public Page<KnowledgeBaseFolder>
			getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
				Long parentKnowledgeBaseFolderId, Pagination pagination)
		throws Exception {

		KBFolder kbFolder = _kbFolderService.getKBFolder(
			parentKnowledgeBaseFolderId);

		return Page.of(
			HashMapBuilder.put(
				"create",
				addAction(
					KBActionKeys.ADD_KB_FOLDER,
					"postKnowledgeBaseFolderKnowledgeBaseFolder",
					KBConstants.RESOURCE_NAME_ADMIN, kbFolder.getGroupId())
			).put(
				"get",
				addAction(
					ActionKeys.VIEW,
					"getKnowledgeBaseFolderKnowledgeBaseFoldersPage",
					KBConstants.RESOURCE_NAME_ADMIN, kbFolder.getGroupId())
			).build(),
			transform(
				_kbFolderService.getKBFolders(
					kbFolder.getGroupId(), parentKnowledgeBaseFolderId,
					pagination.getStartPosition(), pagination.getEndPosition()),
				this::_toKnowledgeBaseFolder),
			pagination,
			_kbFolderService.getKBFoldersCount(
				kbFolder.getGroupId(), parentKnowledgeBaseFolderId));
	}

	@Override
	public KnowledgeBaseFolder
			getSiteKnowledgeBaseFolderByExternalReferenceCode(
				Long siteId, String externalReferenceCode)
		throws Exception {

		return _toKnowledgeBaseFolder(
			_kbFolderService.getKBFolderByExternalReferenceCode(
				siteId, externalReferenceCode));
	}

	@Override
	public Page<KnowledgeBaseFolder> getSiteKnowledgeBaseFoldersPage(
			Long siteId, Pagination pagination)
		throws Exception {

		return Page.of(
			HashMapBuilder.put(
				"create",
				addAction(
					KBActionKeys.ADD_KB_FOLDER, "postSiteKnowledgeBaseFolder",
					KBConstants.RESOURCE_NAME_ADMIN, siteId)
			).put(
				"createBatch",
				addAction(
					KBActionKeys.ADD_KB_FOLDER,
					"postSiteKnowledgeBaseFolderBatch",
					KBConstants.RESOURCE_NAME_ADMIN, siteId)
			).put(
				"deleteBatch",
				addAction(
					KBActionKeys.DELETE, "deleteKnowledgeBaseFolderBatch",
					KBConstants.RESOURCE_NAME_ADMIN, null)
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, "getSiteKnowledgeBaseFoldersPage",
					KBConstants.RESOURCE_NAME_ADMIN, siteId)
			).put(
				"updateBatch",
				addAction(
					KBActionKeys.UPDATE, "putKnowledgeBaseFolderBatch",
					KBConstants.RESOURCE_NAME_ADMIN, null)
			).build(),
			transform(
				_kbFolderService.getKBFolders(
					siteId, 0, pagination.getStartPosition(),
					pagination.getEndPosition()),
				this::_toKnowledgeBaseFolder),
			pagination, _kbFolderService.getKBFoldersCount(siteId, 0));
	}

	@Override
	public KnowledgeBaseFolder postKnowledgeBaseFolderKnowledgeBaseFolder(
			Long parentKnowledgeBaseFolderId,
			KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		KBFolder parentKBFolder = _kbFolderService.getKBFolder(
			parentKnowledgeBaseFolderId);

		return _addKnowledgeBaseFolder(
			knowledgeBaseFolder.getExternalReferenceCode(),
			parentKBFolder.getGroupId(), parentKnowledgeBaseFolderId,
			knowledgeBaseFolder);
	}

	@Override
	public KnowledgeBaseFolder postSiteKnowledgeBaseFolder(
			Long siteId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		return _addKnowledgeBaseFolder(
			knowledgeBaseFolder.getExternalReferenceCode(), siteId, null,
			knowledgeBaseFolder);
	}

	@Override
	public KnowledgeBaseFolder putKnowledgeBaseFolder(
			Long knowledgeBaseFolderId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		return _updateKnowledgeBaseFolder(
			_kbFolderLocalService.getKBFolder(knowledgeBaseFolderId),
			knowledgeBaseFolder);
	}

	@Override
	public KnowledgeBaseFolder
			putSiteKnowledgeBaseFolderByExternalReferenceCode(
				Long siteId, String externalReferenceCode,
				KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		KBFolder kbFolder =
			_kbFolderLocalService.fetchKBFolderByExternalReferenceCode(
				externalReferenceCode, siteId);

		if (kbFolder != null) {
			return _updateKnowledgeBaseFolder(kbFolder, knowledgeBaseFolder);
		}

		return _addKnowledgeBaseFolder(
			externalReferenceCode, siteId,
			knowledgeBaseFolder.getParentKnowledgeBaseFolderId(),
			knowledgeBaseFolder);
	}

	@Override
	protected Long getPermissionCheckerGroupId(Object id) throws Exception {
		KBFolder kbFolder = _kbFolderService.getKBFolder((Long)id);

		return kbFolder.getGroupId();
	}

	@Override
	protected String getPermissionCheckerPortletName(Object id) {
		return KBConstants.RESOURCE_NAME_ADMIN;
	}

	@Override
	protected String getPermissionCheckerResourceName(Object id) {
		return KBFolder.class.getName();
	}

	private KnowledgeBaseFolder _addKnowledgeBaseFolder(
			String externalReferenceCode, long groupId,
			Long parentResourcePrimKey, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		if (parentResourcePrimKey == null) {
			parentResourcePrimKey = KBFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}

		return _toKnowledgeBaseFolder(
			_kbFolderService.addKBFolder(
				externalReferenceCode, groupId, _getClassNameId(),
				parentResourcePrimKey, knowledgeBaseFolder.getName(),
				knowledgeBaseFolder.getDescription(),
				_createServiceContext(
					groupId, knowledgeBaseFolder,
					knowledgeBaseFolder.getViewableByAsString())));
	}

	private ServiceContext _createServiceContext(
		long groupId, KnowledgeBaseFolder knowledgeBaseFolder,
		String viewableBy) {

		return ServiceContextBuilder.create(
			groupId, contextHttpServletRequest, viewableBy
		).expandoBridgeAttributes(
			CustomFieldsUtil.toMap(
				KBFolder.class.getName(), contextCompany.getCompanyId(),
				knowledgeBaseFolder.getCustomFields(),
				contextAcceptLanguage.getPreferredLocale())
		).build();
	}

	private long _getClassNameId() {
		return _portal.getClassNameId(KBFolder.class.getName());
	}

	private KnowledgeBaseFolder _toKnowledgeBaseFolder(KBFolder kbFolder)
		throws Exception {

		if (kbFolder == null) {
			return null;
		}

		return new KnowledgeBaseFolder() {
			{
				actions = HashMapBuilder.put(
					"delete",
					addAction(
						ActionKeys.DELETE, kbFolder,
						"deleteKnowledgeBaseFolder")
				).put(
					"get",
					addAction(
						ActionKeys.VIEW, kbFolder, "getKnowledgeBaseFolder")
				).put(
					"replace",
					addAction(
						ActionKeys.UPDATE, kbFolder, "putKnowledgeBaseFolder")
				).build();
				creator = CreatorUtil.toCreator(
					_portal, contextUriInfo,
					_userLocalService.fetchUser(kbFolder.getUserId()));
				customFields = CustomFieldsUtil.toCustomFields(
					contextAcceptLanguage.isAcceptAllLanguages(),
					KBFolder.class.getName(), kbFolder.getKbFolderId(),
					kbFolder.getCompanyId(),
					contextAcceptLanguage.getPreferredLocale());
				dateCreated = kbFolder.getCreateDate();
				dateModified = kbFolder.getModifiedDate();
				description = kbFolder.getDescription();
				externalReferenceCode = kbFolder.getExternalReferenceCode();
				id = kbFolder.getKbFolderId();
				name = kbFolder.getName();
				numberOfKnowledgeBaseArticles =
					_kbArticleService.getKBArticlesCount(
						kbFolder.getGroupId(), kbFolder.getKbFolderId(), 0);
				numberOfKnowledgeBaseFolders =
					_kbFolderService.getKBFoldersCount(
						kbFolder.getGroupId(), kbFolder.getKbFolderId());
				parentKnowledgeBaseFolder =
					ParentKnowledgeBaseFolderUtil.toParentKnowledgeBaseFolder(
						kbFolder.getParentKBFolder());
				siteId = kbFolder.getGroupId();
			}
		};
	}

	private KnowledgeBaseFolder _updateKnowledgeBaseFolder(
			KBFolder kbFolder, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		return _toKnowledgeBaseFolder(
			_kbFolderService.updateKBFolder(
				_getClassNameId(), kbFolder.getParentKBFolderId(),
				kbFolder.getKbFolderId(), knowledgeBaseFolder.getName(),
				knowledgeBaseFolder.getDescription(),
				_createServiceContext(0, knowledgeBaseFolder, null)));
	}

	@Reference
	private KBArticleService _kbArticleService;

	@Reference
	private KBFolderLocalService _kbFolderLocalService;

	@Reference
	private KBFolderService _kbFolderService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}