/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayTable from '@clayui/table';
import React, {useCallback, useContext} from 'react';

import QuickActionKebab from '../../../shared/components/quick-action-kebab/QuickActionKebab.es';
import ChildLink from '../../../shared/components/router/ChildLink.es';
import {useRouter} from '../../../shared/hooks/useRouter.es';
import {formatDuration} from '../../../shared/util/duration.es';
import moment from '../../../shared/util/moment.es';
import {SLAListPageContext} from './SLAListPage.es';

const SPACE = ' ';

export default function Item({
	dateModified,
	description,
	duration,
	id,
	name,
	processId,
	status,
}) {
	const {
		history,
		location: {search},
	} = useRouter();
	const {showDeleteModal} = useContext(SLAListPageContext);

	const handleDelete = useCallback(() => {
		showDeleteModal(id);
	}, [id, showDeleteModal]);

	const blocked = status === 2;
	const durationString = formatDuration(duration);

	const blockedStatusClass = blocked ? 'text-danger' : '';

	const statusText = blocked
		? Liferay.Language.get('blocked')
		: Liferay.Language.get('running');

	const dropDownItems = [
		{
			label: Liferay.Language.get('edit'),
			onClick: () => {
				history.push({
					pathname: `/sla/${processId}/edit/${id}`,
					search,
				});
			},
		},
		{
			label: Liferay.Language.get('delete'),
			onClick: handleDelete,
		},
	];

	return (
		<ClayTable.Row>
			<ClayTable.Cell>
				<div className="table-list-title">
					{blocked && (
						<ClayIcon
							className="text-danger"
							symbol="exclamation-full"
						/>
					)}

					{SPACE}

					<ChildLink to={`/sla/${processId}/edit/${id}`}>
						{name}
					</ChildLink>
				</div>
			</ClayTable.Cell>

			<ClayTable.Cell>{description}</ClayTable.Cell>

			<ClayTable.Cell className={blockedStatusClass}>
				{statusText}
			</ClayTable.Cell>

			<ClayTable.Cell>{durationString}</ClayTable.Cell>

			<ClayTable.Cell>
				{moment(dateModified).format(Liferay.Language.get('mmm-dd'))}
			</ClayTable.Cell>

			<ClayTable.Cell className="actions">
				<ClayLayout.ContentCol>
					<QuickActionKebab dropDownItems={dropDownItems} />
				</ClayLayout.ContentCol>
			</ClayTable.Cell>
		</ClayTable.Row>
	);
}
