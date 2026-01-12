/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.spi.model.index.contributor;

import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.AuditedModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.search.ReindexCacheThreadLocal;
import com.liferay.portal.kernel.security.auth.FullNameGenerator;
import com.liferay.portal.kernel.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class UserDataUtil {

	public static String[] getUserData(
		Class<?> clazz, UserLocalService userLocalService, long userId) {

		Map<Long, String[]> userDataMap =
			ReindexCacheThreadLocal.getScopeReindexCache(
				UserDataUtil.class.getName(), clazz.getName(), () -> -1,
				() -> -1,
				count -> {
					Table<?> table = _getTable(clazz);

					if (table == null) {

						// DSL is not enabled for this model class, fallback to
						// user fetching.

						return null;
					}

					Map<Long, String[]> localUserDataMap = new HashMap<>();

					FullNameGenerator fullNameGenerator =
						FullNameGeneratorFactory.getInstance();

					for (Object[] values :
							userLocalService.<List<Object[]>>dslQuery(
								DSLQueryFactoryUtil.select(
									UserTable.INSTANCE.externalReferenceCode,
									UserTable.INSTANCE.firstName,
									UserTable.INSTANCE.middleName,
									UserTable.INSTANCE.lastName,
									UserTable.INSTANCE.languageId,
									UserTable.INSTANCE.userId
								).from(
									UserTable.INSTANCE
								).where(
									UserTable.INSTANCE.userId.in(
										DSLQueryFactoryUtil.selectDistinct(
											table.getColumn(
												"userId", Long.class)
										).from(
											table
										))
								),
								false)) {

						localUserDataMap.put(
							(Long)values[5],
							new String[] {
								(String)values[0],
								fullNameGenerator.getLocalizedFullName(
									(String)values[1], (String)values[2],
									(String)values[3],
									LocaleUtil.fromLanguageId(
										(String)values[4], false),
									0, 0)
							});
					}

					return localUserDataMap;
				});

		if (userDataMap == null) {
			User user = userLocalService.fetchUser(userId);

			if (user == null) {
				return null;
			}

			if (AuditedModel.class.isAssignableFrom(clazz)) {
				return new String[] {
					user.getExternalReferenceCode(), user.getFullName()
				};
			}

			return new String[] {user.getExternalReferenceCode()};
		}

		return userDataMap.get(userId);
	}

	private static Table<?> _getTable(Class<?> clazz) {
		Class<?> modelInterfaceClass = null;

		outerLoop:
		while (clazz != BaseModelImpl.class) {
			for (Class<?> interfaceClass : clazz.getInterfaces()) {
				ImplementationClassName implementationClassName =
					interfaceClass.getDeclaredAnnotation(
						ImplementationClassName.class);

				if (implementationClassName != null) {
					modelInterfaceClass = interfaceClass;

					break outerLoop;
				}
			}

			clazz = clazz.getSuperclass();
		}

		if (modelInterfaceClass == null) {
			return null;
		}

		ClassLoader classLoader = modelInterfaceClass.getClassLoader();

		try {
			Class<?> tableClass = classLoader.loadClass(
				modelInterfaceClass.getName() + "Table");

			Field field = tableClass.getField("INSTANCE");

			return (Table<?>)field.get(null);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			if (_log.isWarnEnabled()) {
				_log.warn(reflectiveOperationException);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(UserDataUtil.class);

}