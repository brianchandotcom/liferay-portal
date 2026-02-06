/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.image.upgrade;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Adolfo Pérez
 * @author Georgel Pop
 */
public class ImageCompanyIdUpgradeProcess<T> extends UpgradeProcess {

	public ImageCompanyIdUpgradeProcess(
		Supplier<ActionableDynamicQuery> actionableDynamicQuerySupplier,
		Function<T, Long> companyIdFunction, Function<T, Long> imageIdFunction,
		Function<T, String> imageERCFunction) {

		_actionableDynamicQuerySupplier = actionableDynamicQuerySupplier;
		_companyIdFunction = companyIdFunction;
		_imageIdFunction = imageIdFunction;
		_imageERCFunction = imageERCFunction;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if ((_companyIdFunction == null) ||
			((_imageIdFunction == null) && (_imageERCFunction == null))) {

			return;
		}

		String sql =
			"update Image set companyId = ? where externalReferenceCode = ?";

		if (_imageERCFunction == null) {
			sql = "update Image set companyId = ? where imageId = ?";
		}

		try (PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(connection, sql)) {

			ActionableDynamicQuery actionableDynamicQuery =
				_actionableDynamicQuerySupplier.get();

			actionableDynamicQuery.setPerformActionMethod(
				(T model) -> {
					Object identifier = _getIdentifier(model);

					if (identifier == null) {
						return;
					}

					long companyId = _companyIdFunction.apply(model);

					try {
						preparedStatement.setLong(1, companyId);

						if (_imageERCFunction == null) {
							if (!(identifier instanceof Long) ||
								((Long)identifier <= 0)) {

								return;
							}

							preparedStatement.setLong(2, (Long)identifier);
						}
						else {
							if (!(identifier instanceof String) ||
								Validator.isNull((String)identifier)) {

								return;
							}

							preparedStatement.setString(2, (String)identifier);
						}

						preparedStatement.addBatch();
					}
					catch (Exception exception) {
						_log.error(
							StringBundler.concat(
								"Cannot update image ", identifier,
								" to company ID ", companyId),
							exception);
					}
				});

			actionableDynamicQuery.performActions();

			preparedStatement.executeBatch();
		}
	}

	private Object _getIdentifier(T model) {
		if (_imageERCFunction != null) {
			return _imageERCFunction.apply(model);
		}

		return _imageIdFunction.apply(model);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImageCompanyIdUpgradeProcess.class);

	private final Supplier<ActionableDynamicQuery>
		_actionableDynamicQuerySupplier;
	private final Function<T, Long> _companyIdFunction;
	private final Function<T, String> _imageERCFunction;
	private final Function<T, Long> _imageIdFunction;

}