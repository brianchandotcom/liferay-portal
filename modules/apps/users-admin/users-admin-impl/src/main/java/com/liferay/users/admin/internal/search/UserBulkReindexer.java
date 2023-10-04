/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.internal.search;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.search.spi.reindexer.BulkReindexer;

import java.util.Collection;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(
	property = "indexer.class.name=com.liferay.portal.kernel.model.User",
	service = BulkReindexer.class
)
public class UserBulkReindexer implements BulkReindexer {

	@Override
	public void reindex(long companyId, Collection<Long> classPKs) {
		int size = classPKs.size();

		if (size <= _databaseMaxParameters) {
			_reindex(companyId, classPKs);

			return;
		}

		List<Long> classPKsList = ListUtil.fromCollection(classPKs);

		int start = 0;
		int end = _databaseMaxParameters;

		while (start < size) {
			_reindex(companyId, ListUtil.subList(classPKsList, start, end));

			end += _databaseMaxParameters;
			start += _databaseMaxParameters;
		}
	}

	@Activate
	protected void activate() {
		DBType dbType = DBManagerUtil.getDBType();

		_databaseMaxParameters = GetterUtil.getInteger(
			PropsUtil.get(
				PropsKeys.DATABASE_MAX_PARAMETERS,
				new Filter(dbType.getName())),
			Integer.MAX_VALUE);
	}

	@Reference(
		target = "(indexer.class.name=com.liferay.portal.kernel.model.User)"
	)
	protected Indexer<User> indexer;

	@Reference
	protected UserLocalService userLocalService;

	private void _reindex(long companyId, Collection<Long> classPKs) {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			userLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> dynamicQuery.add(
				RestrictionsFactoryUtil.in("userId", classPKs)));
		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(User user) -> {
				if (!user.isGuestUser()) {
					try {
						indexableActionableDynamicQuery.addDocuments(
							indexer.getDocument(user));
					}
					catch (PortalException portalException) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index user " + user.getUserId(),
								portalException);
						}
					}
				}
			});

		try {
			indexableActionableDynamicQuery.performActions();
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserBulkReindexer.class);

	private int _databaseMaxParameters;

}