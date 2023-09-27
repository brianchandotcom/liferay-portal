/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.external;

import com.liferay.document.library.repository.external.search.ExtRepositoryQueryMapper;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.Date;

/**
 * @author Iván Zaera
 * @author Sergio González
 */
public class ExtRepositoryQueryMapperImpl implements ExtRepositoryQueryMapper {

	public ExtRepositoryQueryMapperImpl(
		ExtRepositoryAdapter extRepositoryAdapter) {

		_extRepositoryAdapter = extRepositoryAdapter;
	}

	@Override
	public Date formatDateParameterValue(String fieldName, String fieldValue)
		throws SearchException {

		if (fieldName.equals(Field.CREATE_DATE) ||
			fieldName.equals(Field.MODIFIED_DATE)) {

			try {
				DateFormat searchSimpleDateFormat =
					DateFormatFactoryUtil.getSimpleDateFormat(
						_INDEX_DATE_FORMAT_PATTERN);

				return searchSimpleDateFormat.parse(fieldValue);
			}
			catch (ParseException parseException) {
				throw new SearchException(
					StringBundler.concat(
						"Unable to parse date ", fieldValue, " for field ",
						fieldName),
					parseException);
			}
		}
		else {
			throw new SearchException("Field " + fieldName + " is not a date");
		}
	}

	@Override
	public String formatParameterValue(String fieldName, String fieldValue)
		throws SearchException {

		if (fieldName.equals(Field.CREATE_DATE) ||
			fieldName.equals(Field.MODIFIED_DATE)) {

			throw new SearchException(
				"Use the method formatDateParameterValue to format the date " +
					"field " + fieldName);
		}
		else if (fieldName.equals(Field.FOLDER_ID)) {
			try {
				long folderId = GetterUtil.getLong(fieldValue);

				return _extRepositoryAdapter.getExtRepositoryObjectKey(
					folderId);
			}
			catch (PortalException portalException) {
				throw new SearchException(
					"Unable to get folder folder " + fieldValue,
					portalException);
			}
			catch (SystemException systemException) {
				throw new SearchException(
					"Unable to get folder folder " + fieldValue,
					systemException);
			}
		}
		else if (fieldName.equals(Field.USER_ID)) {
			try {
				long userId = GetterUtil.getLong(fieldValue);

				User user = UserLocalServiceUtil.getUserById(userId);

				return user.getScreenName();
			}
			catch (Exception exception) {
				throw new SearchException(
					"Unable to get user user " + fieldValue, exception);
			}
		}
		else {
			return fieldValue;
		}
	}

	private static final String _INDEX_DATE_FORMAT_PATTERN = PropsUtil.get(
		PropsKeys.INDEX_DATE_FORMAT_PATTERN);

	private final ExtRepositoryAdapter _extRepositoryAdapter;

}