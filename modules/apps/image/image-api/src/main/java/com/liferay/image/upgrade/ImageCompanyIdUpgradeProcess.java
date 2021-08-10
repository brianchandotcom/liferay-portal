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

package com.liferay.image.upgrade;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Adolfo Pérez
 */
public class ImageCompanyIdUpgradeProcess<T> extends UpgradeProcess {

	public ImageCompanyIdUpgradeProcess(
		Supplier<ActionableDynamicQuery> actionableDynamicQuerySupplier,
		Function<T, Long> companyIdFunction,
		Function<T, Long> imageIdFunction) {

		_actionableDynamicQuerySupplier = actionableDynamicQuerySupplier;
		_companyIdFunction = companyIdFunction;
		_imageIdFunction = imageIdFunction;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						"update Image set companyId = ? where imageId = ?"))) {

			ActionableDynamicQuery actionableDynamicQuery =
				_actionableDynamicQuerySupplier.get();

			actionableDynamicQuery.setPerformActionMethod(
				(T model) -> {
					try {
						preparedStatement.setLong(
							1, _companyIdFunction.apply(model));
						preparedStatement.setLong(
							2, _imageIdFunction.apply(model));

						preparedStatement.addBatch();
					}
					catch (Exception exception) {
						_log.error(
							StringBundler.concat(
								"Cannot update image ",
								_imageIdFunction.apply(model), " to company ID",
								_companyIdFunction.apply(model)),
							exception);
					}
				});

			actionableDynamicQuery.performActions();

			preparedStatement.executeBatch();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImageCompanyIdUpgradeProcess.class.getName());

	private final Supplier<ActionableDynamicQuery>
		_actionableDynamicQuerySupplier;
	private final Function<T, Long> _companyIdFunction;
	private final Function<T, Long> _imageIdFunction;

}