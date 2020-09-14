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
import ClayLayout from '@clayui/layout';
import ClayTable from '@clayui/table';
import React, {useCallback, useContext} from 'react';

import QuickActionKebab from '../../../shared/components/quick-action-kebab/QuickActionKebab.es';
import ChildLink from '../../../shared/components/router/ChildLink.es';
import {useRouter} from '../../../shared/hooks/useRouter.es';
import {formatDuration} from '../../../shared/util/duration.es';
import moment from '../../../shared/util/moment.es';
import {SLAListPageContext} from './SLAListPage.es';

const Item = ({
	dateModified,
	description,
	duration,
	id,
	name,
	processId,
	status,
}) => {
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
					)}{' '}
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
				{moment
					.utc(dateModified)
					.format(Liferay.Language.get('mmm-dd'))}
			</ClayTable.Cell>

			<ClayTable.Cell className="actions">
				<ClayLayout.ContentCol>
					<QuickActionKebab dropDownItems={dropDownItems} />
				</ClayLayout.ContentCol>
			</ClayTable.Cell>
		</ClayTable.Row>
	);
};

export {Item};
