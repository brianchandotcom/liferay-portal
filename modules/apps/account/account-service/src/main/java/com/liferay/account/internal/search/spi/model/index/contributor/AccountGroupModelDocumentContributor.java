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

package com.liferay.account.internal.search.spi.model.index.contributor;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupRelLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.account.model.AccountGroup",
	service = ModelDocumentContributor.class
)
public class AccountGroupModelDocumentContributor
	implements ModelDocumentContributor<AccountGroup> {

	@Override
	public void contribute(Document document, AccountGroup accountGroup) {
		document.addText(Field.DESCRIPTION, accountGroup.getDescription());
		document.addText(Field.NAME, accountGroup.getName());
		document.addKeyword(Field.TYPE, accountGroup.getType());
		document.addKeyword(
			"accountEntryIds", _getAccountEntryIds(accountGroup));
		document.addKeyword(
			"defaultAccountGroup", accountGroup.isDefaultAccountGroup());
	}

	private long[] _getAccountEntryIds(AccountGroup accountGroup) {
		DynamicQuery dynamicQuery = _accountGroupRelLocalService.dynamicQuery();

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"accountGroupId", accountGroup.getAccountGroupId()));
		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"classNameId",
				_classNameLocalService.getClassNameId(AccountEntry.class)));
		dynamicQuery.setProjection(ProjectionFactoryUtil.property("classPK"));

		return ArrayUtil.toLongArray(
			_accountGroupRelLocalService.dynamicQuery(dynamicQuery));
	}

	@Reference
	private AccountGroupRelLocalService _accountGroupRelLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

}