/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.document.library.kernel.processor.RawMetadataProcessor;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Marco Galluzzi
 */
public class ClassNameUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		long oldClassNameId = _getClassNameId(
			"com.liferay.document.library.kernel.util.RawMetadataProcessor");

		if (oldClassNameId == 0) {
			return;
		}

		long newClassNameId = _getClassNameId(
			RawMetadataProcessor.class.getName());

		if (newClassNameId == 0) {
			_updateValue(oldClassNameId);
		}
		else {
			_updateDDMStructureClassNameId(newClassNameId, oldClassNameId);

			_deleteClassName(oldClassNameId);
		}
	}

	private void _deleteClassName(long classNameId) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"delete from ClassName_ where classNameId = ?")) {

			preparedStatement.setLong(1, classNameId);

			preparedStatement.execute();
		}
	}

	private long _getClassNameId(String value) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select classNameId from ClassName_ where value = ?")) {

			preparedStatement.setString(1, value);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return resultSet.getLong("classNameId");
			}
		}

		return 0;
	}

	private void _updateDDMStructureClassNameId(
			long newClassNameId, long oldClassNameId)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update DDMStructure set classNameId = ? where classNameId = " +
					"?")) {

			preparedStatement.setLong(1, newClassNameId);
			preparedStatement.setLong(2, oldClassNameId);

			preparedStatement.execute();
		}
	}

	private void _updateValue(long classNameId) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update ClassName_ set value = ? where classNameId = ? ")) {

			preparedStatement.setString(
				1, RawMetadataProcessor.class.getName());
			preparedStatement.setLong(2, classNameId);

			preparedStatement.executeUpdate();
		}
	}

}