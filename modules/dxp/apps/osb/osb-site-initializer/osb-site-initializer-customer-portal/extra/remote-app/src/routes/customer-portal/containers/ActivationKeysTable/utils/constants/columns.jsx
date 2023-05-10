/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
			name: i18n.translate('exp-date'),
			styles: 'bg-transparent text-neutral-10 font-weight-bold',
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
