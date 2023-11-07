/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '../../../../../../../../common/I18n';
import {FilterIcon} from '../../../../../../../../common/icons/filter_icon'

const getInitialColumns = () => [
	{
		accessor: 'ticket',
		bodyClass: 'border-0',
		header: {
			name: (
				<div className="align-items-center d-flex">
					<div className="mr-2">{i18n.translate('ticket')}</div>

					<FilterIcon/>
				</div>
			),
			styles:
				'h6 border-bottom text-neutral-10 font-weight-bold table-cell-expand-smaller',
		},
		truncate: true,
	},
	{
		accessor: 'fileName',
		bodyClass: 'border-0',

		header: {
			name: (
				<div className="align-items-center d-flex">
					<div className="mr-2">{i18n.translate('file-name')}</div>

					<FilterIcon/>
				</div>
			),
			styles:
				'h6 border-bottom text-neutral-10 font-weight-bold table-cell-expand',
		},
		truncate: true,
	},
	{
		accessor: 'fileSize',
		bodyClass: 'border-0',
		header: {
			name: (
				<div className="align-items-center d-flex">
					<div className="mr-2">{i18n.translate('file-size')}</div>

					<FilterIcon/>
				</div>
			),
			styles:
				'h6 border-bottom text-neutral-10 font-weight-bold table-cell-expand-smaller',
		},
		truncate: true,
	},
	{
		accessor: 'attached',
		bodyClass: 'border-0',
		header: {
			name: (
				<div className="align-items-center d-flex">
					<div className="mr-2">{i18n.translate('attached')}</div>

					<FilterIcon/>
				</div>
			),
			styles:
				'h6 border-bottom text-neutral-10 font-weight-bold table-cell-expand-small',
		},
	},
];

const optionColumn = {
	accessor: 'options',
	align: 'right',
	bodyClass: 'border-0',
	header: {
		name: '',
		styles: 'border-bottom bg-transparent',
	},
};

export function getColumns(hasAccountAdministrator) {
	const columns = getInitialColumns();

	if (hasAccountAdministrator) {
		return [...columns, optionColumn];
	}

	return columns;
}
