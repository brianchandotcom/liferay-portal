/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-commerce-frontend-management-bar-state',
	(A) => {
		A.Do.before(
			(state) => {
				if (state.owner === 'liferay.component') {
					return new A.Do.Halt(null);
				}
			},
			Liferay.ManagementBar,
			'testRestoreTask'
		);
	},
	'',
	{
		requires: ['liferay-management-bar'],
	}
);
