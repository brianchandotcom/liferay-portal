/* eslint-disable no-undef */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import i18n from '../../../../../../common/I18n';

export const ACTIVATE_COLUMNS = [
	{
		accessor: 'envName',
		bodyClass: 'border-0 cursor-pointer',
		expanded: true,
		header: {
			description: i18n.translate('description'),
			name: i18n.translate('environment-name'),
			styles: 'bg-transparent',
		},
	},
	{
		accessor: 'keyType',
		bodyClass: 'border-0 cursor-pointer',
		header: {
			description: `${i18n.translate('host-name')} / ${i18n.translate(
				'cluster-size'
			)}`,
			name: i18n.translate('key-type'),
			noWrap: true,
			styles: 'bg-transparent',
		},
	},
	{
		accessor: 'envType',
		bodyClass: 'border-0 cursor-pointer',
		header: {
			name: i18n.translate('environment-type'),
			styles: 'bg-transparent text-neutral-10 font-weight-bold',
		},
	},
	{
		accessor: 'expirationDate',
		bodyClass: 'border-0 cursor-pointer',
		header: {
			name: `${i18n.translate('start-date')} - \n ${i18n.translate(
				'exp-date'
			)}`,

			styles:
				'bg-transparent text-neutral-10 font-weight-bold white-space',
		},
		noWrap: true,
	},
	{
		accessor: 'status',
		align: 'center',
		bodyClass: 'border-0 cursor-pointer',
		header: {
			name: i18n.translate('status'),
			styles: 'bg-transparent text-neutral-10 font-weight-bold',
		},
	},
	{
		accessor: 'download',
		align: 'center',
		bodyClass: 'border-0',
		disableCustomClickOnRow: true,
		header: {
			name: <ClayIcon symbol="download" />,
			styles: 'bg-transparent text-neutral-10 font-weight-bold',
		},
	},
];
