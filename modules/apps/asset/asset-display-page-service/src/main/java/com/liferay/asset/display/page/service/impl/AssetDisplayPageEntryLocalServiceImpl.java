/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.display.page.service.impl;

import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.model.AssetDisplayPageEntryTable;
import com.liferay.asset.display.page.service.base.AssetDisplayPageEntryLocalServiceBaseImpl;
import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.info.search.InfoSearchClassMapperRegistry;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "model.class.name=com.liferay.asset.display.page.model.AssetDisplayPageEntry",
	service = AopService.class
)
public class AssetDisplayPageEntryLocalServiceImpl
	extends AssetDisplayPageEntryLocalServiceBaseImpl {

	@Override
	public AssetDisplayPageEntry addAssetDisplayPageEntry(
			long userId, long groupId, long classNameId, long classPK,
			long layoutPageTemplateEntryId, int type,
			ServiceContext serviceContext)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		long assetDisplayPageEntryId = counterLocalService.increment();

		AssetDisplayPageEntry assetDisplayPageEntry =
			assetDisplayPageEntryPersistence.create(assetDisplayPageEntryId);

		assetDisplayPageEntry.setUuid(serviceContext.getUuid());
		assetDisplayPageEntry.setGroupId(groupId);
		assetDisplayPageEntry.setCompanyId(user.getCompanyId());
		assetDisplayPageEntry.setUserId(user.getUserId());
		assetDisplayPageEntry.setUserName(user.getFullName());
		assetDisplayPageEntry.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		assetDisplayPageEntry.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		assetDisplayPageEntry.setClassNameId(classNameId);
		assetDisplayPageEntry.setClassPK(classPK);
		assetDisplayPageEntry.setLayoutPageTemplateEntryId(
			layoutPageTemplateEntryId);
		assetDisplayPageEntry.setType(type);

		return assetDisplayPageEntryPersistence.update(assetDisplayPageEntry);
	}

	@Override
	public AssetDisplayPageEntry addAssetDisplayPageEntry(
			long userId, long groupId, long classNameId, long classPK,
			long layoutPageTemplateEntryId, ServiceContext serviceContext)
		throws PortalException {

		return addAssetDisplayPageEntry(
			userId, groupId, classNameId, classPK, layoutPageTemplateEntryId,
			AssetDisplayPageConstants.TYPE_DEFAULT, serviceContext);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteAssetDisplayPageEntry(
			long groupId, long classNameId, long classPK)
		throws PortalException {

		assetDisplayPageEntryPersistence.removeByG_C_C(
			groupId, classNameId, classPK);
	}

	@Override
	public AssetDisplayPageEntry fetchAssetDisplayPageEntry(
		long groupId, long classNameId, long classPK) {

		return assetDisplayPageEntryPersistence.fetchByG_C_C(
			groupId, classNameId, classPK);
	}

	@Override
	public List<AssetDisplayPageEntry> getAssetDisplayPageEntries(
		long classNameId, long classTypeId, long layoutPageTemplateEntryId,
		boolean defaultTemplate, int start, int end,
		OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		DSLQuery dslQuery = DSLQueryFactoryUtil.select(
			AssetDisplayPageEntryTable.INSTANCE
		).from(
			AssetDisplayPageEntryTable.INSTANCE
		).innerJoinON(
			AssetEntryTable.INSTANCE,
			AssetDisplayPageEntryTable.INSTANCE.classPK.eq(
				AssetEntryTable.INSTANCE.classPK)
		).where(
			_getPredicate(
				classNameId, classTypeId, layoutPageTemplateEntryId,
				defaultTemplate)
		).orderBy(
			AssetDisplayPageEntryTable.INSTANCE, orderByComparator
		).limit(
			start, end
		);

		return assetDisplayPageEntryPersistence.dslQuery(dslQuery);
	}

	@Override
	public List<AssetDisplayPageEntry>
		getAssetDisplayPageEntriesByLayoutPageTemplateEntryId(
			long layoutPageTemplateEntryId) {

		return assetDisplayPageEntryPersistence.findByLayoutPageTemplateEntryId(
			layoutPageTemplateEntryId);
	}

	@Override
	public List<AssetDisplayPageEntry>
		getAssetDisplayPageEntriesByLayoutPageTemplateEntryId(
			long layoutPageTemplateEntryId, int start, int end,
			OrderByComparator<AssetDisplayPageEntry> orderByComparator) {

		return assetDisplayPageEntryPersistence.findByLayoutPageTemplateEntryId(
			layoutPageTemplateEntryId, start, end, orderByComparator);
	}

	@Override
	public int getAssetDisplayPageEntriesCount(
		long classNameId, long classTypeId, long layoutPageTemplateEntryId,
		boolean defaultTemplate) {

		DSLQuery dslQuery = DSLQueryFactoryUtil.count(
		).from(
			AssetDisplayPageEntryTable.INSTANCE
		).innerJoinON(
			AssetEntryTable.INSTANCE,
			AssetDisplayPageEntryTable.INSTANCE.classPK.eq(
				AssetEntryTable.INSTANCE.classPK)
		).where(
			_getPredicate(
				classNameId, classTypeId, layoutPageTemplateEntryId,
				defaultTemplate)
		);

		return assetDisplayPageEntryPersistence.dslQueryCount(dslQuery);
	}

	@Override
	public int getAssetDisplayPageEntriesCountByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId) {

		return assetDisplayPageEntryPersistence.
			countByLayoutPageTemplateEntryId(layoutPageTemplateEntryId);
	}

	@Override
	public AssetDisplayPageEntry updateAssetDisplayPageEntry(
			long assetDisplayPageEntryId, long layoutPageTemplateEntryId,
			int type)
		throws PortalException {

		AssetDisplayPageEntry assetDisplayPageEntry =
			assetDisplayPageEntryPersistence.findByPrimaryKey(
				assetDisplayPageEntryId);

		assetDisplayPageEntry.setModifiedDate(new Date());
		assetDisplayPageEntry.setLayoutPageTemplateEntryId(
			layoutPageTemplateEntryId);
		assetDisplayPageEntry.setType(type);

		return assetDisplayPageEntryPersistence.update(assetDisplayPageEntry);
	}

	private Predicate _getPredicate(
		long classNameId, long classTypeId, long layoutPageTemplateEntryId,
		boolean defaultTemplate) {

		return AssetDisplayPageEntryTable.INSTANCE.classNameId.eq(
			classNameId
		).and(
			() -> {
				String searchClassName =
					_infoSearchClassMapperRegistry.getSearchClassName(
						_portal.getClassName(classNameId));

				return AssetEntryTable.INSTANCE.classNameId.eq(
					_portal.getClassNameId(searchClassName));
			}
		).and(
			AssetDisplayPageEntryTable.INSTANCE.layoutPageTemplateEntryId.eq(
				layoutPageTemplateEntryId
			).and(
				AssetDisplayPageEntryTable.INSTANCE.type.eq(
					AssetDisplayPageConstants.TYPE_SPECIFIC)
			).withParentheses(
			).or(
				() -> {
					if (defaultTemplate) {
						return AssetDisplayPageEntryTable.INSTANCE.type.eq(
							AssetDisplayPageConstants.TYPE_DEFAULT);
					}

					return null;
				}
			).withParentheses()
		).and(
			() -> {
				if (classTypeId > 0) {
					return AssetEntryTable.INSTANCE.classTypeId.eq(classTypeId);
				}

				return null;
			}
		);
	}

	@Reference
	private InfoSearchClassMapperRegistry _infoSearchClassMapperRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}