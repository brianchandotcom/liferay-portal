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

import ListView, {ListViewProps} from '../../../components/ListView';
import StatusBadge from '../../../components/StatusBadge';
import {StatusBadgeType} from '../../../components/StatusBadge/StatusBadge';
import {TableProps} from '../../../components/Table';
import i18n from '../../../i18n';
import {PickList, testrayCaseResultImpl} from '../../../services/rest';
import dayjs from '../../../util/date';

type CaseResultHistoryProps = {
	listViewProps?: Partial<ListViewProps>;
	tableProps?: Partial<TableProps>;
};

const CaseResultHistory: React.FC<CaseResultHistoryProps> = ({
	listViewProps,
	tableProps,
}) => (
	<ListView
		initialContext={{
			columns: {
				caseType: false,
				dateCreated: false,
				dateModified: false,
				issues: false,
				team: false,
			},
		}}
		managementToolbarProps={{
			title: i18n.translate('test-history'),
			visible: true,
		}}
		resource={testrayCaseResultImpl.resource}
		tableProps={{
			columns: [
				{
					clickable: true,
					key: 'dateCreated',
					render: (date) => dayjs(date).format('lll'),
					size: 'sm',
					value: i18n.translate('create-date'),
				},
				{
					clickable: true,
					key: 'build',
					render: (build) => {
						return build?.gitHash;
					},
					value: i18n.translate('git-hash'),
				},
				{
					clickable: true,
					key: 'product-version',
					render: (_, {build}) => build?.productVersion?.name,
					value: i18n.translate('product-version'),
				},
				{
					clickable: true,
					key: 'run',
					render: (run) => run?.externalReferencePK,
					size: 'lg',
					value: i18n.translate('environment'),
				},
				{
					clickable: true,
					key: 'routine',
					render: (_, {build}) => build?.routine?.name,
					value: i18n.translate('routine'),
				},
				{
					key: 'dueStatus',
					render: (dueStatus: PickList) => {
						return (
							<StatusBadge
								type={dueStatus.key as StatusBadgeType}
							>
								{dueStatus.name}
							</StatusBadge>
						);
					},
					value: i18n.translate('status'),
				},
				{
					key: 'warnings',
					value: i18n.translate('warnings'),
				},
				{key: 'issues', value: i18n.translate('issues')},
				{key: 'errors', value: i18n.translate('errors')},
			],
			...tableProps,
		}}
		transformData={(response) =>
			testrayCaseResultImpl.transformDataFromList(response)
		}
		{...listViewProps}
	/>
);

export default CaseResultHistory;
