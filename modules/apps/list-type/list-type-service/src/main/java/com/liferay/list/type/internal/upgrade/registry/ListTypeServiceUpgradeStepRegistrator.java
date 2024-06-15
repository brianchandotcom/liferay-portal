/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.list.type.internal.upgrade.registry;

import com.liferay.list.type.internal.upgrade.v1_3_0.ListTypeDefinitionUpgradeProcess;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.upgrade.BaseExternalReferenceCodeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Murilo Stodolni
 */
@Component(service = UpgradeStepRegistrator.class)
public class ListTypeServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "1.1.0",
			new BaseExternalReferenceCodeUpgradeProcess() {

				@Override
				protected String[][] getTableAndPrimaryKeyColumnNames() {
					return new String[][] {
						{"ListTypeDefinition", "listTypeDefinitionId"}
					};
				}

			});

		registry.register(
			"1.1.0", "1.2.0",
			new BaseExternalReferenceCodeUpgradeProcess() {

				@Override
				protected String[][] getTableAndPrimaryKeyColumnNames() {
					return new String[][] {
						{"ListTypeEntry", "listTypeEntryId"}
					};
				}

			});

		registry.register(
			"1.2.0", "1.3.0", new ListTypeDefinitionUpgradeProcess());

		registry.register(
			"1.3.0", "1.3.1",
			UpgradeProcessFactory.runSQL(
				StringBundler.concat(
					"update Contact_ set prefixListTypeId = 0 where ",
					"prefixListTypeId in (select listTypeId from ListType ",
					"where (name is null or name = '') and (type_ = '",
					ListTypeConstants.CONTACT_PREFIX, "'))"),
				StringBundler.concat(
					"update Contact_ set suffixListTypeId = 0 where ",
					"suffixListTypeId in (select listTypeId from ListType ",
					"where (name is null or name = '') and (type_ = '",
					ListTypeConstants.CONTACT_SUFFIX, "'))"),
				StringBundler.concat(
					"delete from ListType where (name is null or name = '') ",
					"and (type_ = '", ListTypeConstants.CONTACT_PREFIX,
					"' or type_ = '", ListTypeConstants.CONTACT_SUFFIX, "')")));
	}

}