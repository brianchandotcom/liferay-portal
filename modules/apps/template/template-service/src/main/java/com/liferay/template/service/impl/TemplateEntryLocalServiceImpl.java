/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.service.base.TemplateEntryLocalServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "model.class.name=com.liferay.template.model.TemplateEntry",
	service = AopService.class
)
public class TemplateEntryLocalServiceImpl
	extends TemplateEntryLocalServiceBaseImpl {

	@Override
	public TemplateEntry addTemplateEntry(
			String externalReferenceCode, long userId, long groupId,
			long ddmTemplateId, String infoItemClassName,
			String infoItemFormVariationKey, ServiceContext serviceContext)
		throws PortalException {

		TemplateEntry templateEntry = templateEntryPersistence.create(
			counterLocalService.increment());

		templateEntry.setUuid(serviceContext.getUuid());
		templateEntry.setExternalReferenceCode(externalReferenceCode);
		templateEntry.setGroupId(groupId);

		User user = _userLocalService.getUser(userId);

		templateEntry.setCompanyId(user.getCompanyId());
		templateEntry.setUserId(user.getUserId());
		templateEntry.setUserName(user.getFullName());

		templateEntry.setDDMTemplateId(ddmTemplateId);
		templateEntry.setInfoItemClassName(infoItemClassName);
		templateEntry.setInfoItemFormVariationKey(infoItemFormVariationKey);

		return templateEntryPersistence.update(templateEntry);
	}

	@Override
	public TemplateEntry deleteTemplateEntry(long templateEntryId) {
		TemplateEntry templateEntry =
			templateEntryPersistence.fetchByPrimaryKey(templateEntryId);

		return templateEntryLocalService.deleteTemplateEntry(templateEntry);
	}

	@Override
	public TemplateEntry deleteTemplateEntry(
		String externalReferenceCode, long groupId) {

		TemplateEntry templateEntry = templateEntryPersistence.fetchByERC_G(
			externalReferenceCode, groupId);

		return templateEntryLocalService.deleteTemplateEntry(templateEntry);
	}

	@Override
	public TemplateEntry deleteTemplateEntry(TemplateEntry templateEntry) {
		return templateEntryPersistence.remove(templateEntry);
	}

	@Override
	public TemplateEntry fetchTemplateEntryByDDMTemplateId(long ddmTemplateId) {
		return templateEntryPersistence.fetchByDDMTemplateId(ddmTemplateId);
	}

	@Override
	public List<TemplateEntry> getTemplateEntries(
		long groupId, int start, int end,
		OrderByComparator<TemplateEntry> orderByComparator) {

		return templateEntryPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<TemplateEntry> getTemplateEntries(
		long groupId, String infoItemClassName, String infoItemFormVariationKey,
		int start, int end,
		OrderByComparator<TemplateEntry> orderByComparator) {

		return templateEntryPersistence.findByG_IICN_IIFVK(
			groupId, infoItemClassName, infoItemFormVariationKey, start, end,
			orderByComparator);
	}

	@Override
	public List<TemplateEntry> getTemplateEntries(long[] groupIds) {
		return templateEntryPersistence.findByGroupId(groupIds);
	}

	@Override
	public List<TemplateEntry> getTemplateEntries(
		long[] groupIds, String infoItemClassName,
		String infoItemFormVariationKey, int start, int end,
		OrderByComparator<TemplateEntry> orderByComparator) {

		return templateEntryPersistence.findByG_IICN_IIFVK(
			groupIds, infoItemClassName, infoItemFormVariationKey, start, end,
			orderByComparator);
	}

	@Override
	public int getTemplateEntriesCount(long groupId) {
		return templateEntryPersistence.countByGroupId(groupId);
	}

	@Override
	public TemplateEntry updateTemplateEntry(long templateEntryId)
		throws PortalException {

		TemplateEntry templateEntry = templateEntryPersistence.findByPrimaryKey(
			templateEntryId);

		return templateEntryPersistence.update(templateEntry);
	}

	@Override
	public TemplateEntry updateTemplateEntry(
			String externalReferenceCode, long groupId)
		throws PortalException {

		TemplateEntry templateEntry = templateEntryPersistence.findByERC_G(
			externalReferenceCode, groupId);

		return templateEntryLocalService.updateTemplateEntry(
			templateEntry.getTemplateEntryId());
	}

	@Reference
	private UserLocalService _userLocalService;

}