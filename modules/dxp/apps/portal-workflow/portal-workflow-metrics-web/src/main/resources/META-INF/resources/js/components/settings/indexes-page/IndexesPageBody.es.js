/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayList from '@clayui/list';
import ClayProgressBar from '@clayui/progress-bar';
import React from 'react';

import PromisesResolver from '../../../shared/components/promises-resolver/PromisesResolver.es';
import {ALL_INDEXES_KEY, getIndexesGroups} from './IndexesConstants.es';
import List from './IndexesPageBodyList.es';
import {useReindexActions} from './hooks/useReindexActions.es';

function Body({items = []}) {
	const {getReindexStatus, handleReindex, isReindexing} = useReindexActions();

	const {completionPercentage = 0} = getReindexStatus(ALL_INDEXES_KEY);

	const groups = getIndexesGroups();

	items.forEach(({group, ...index}) => {
		if (groups[group]) {
			groups[group].indexes.push(index);
		}
	});

	return (
		<>
			<ClayList>
				<ClayList.Item
					className="autofit-row-center reindex-action"
					flex
				>
					<ClayList.ItemField
						className="font-weight-semi-bold"
						expand
					>
						{Liferay.Language.get('workflow-indexes')}
					</ClayList.ItemField>

					<ClayList.ItemField>
						{isReindexing(ALL_INDEXES_KEY) ? (
							<ClayProgressBar value={completionPercentage} />
						) : (
							<ClayButton
								displayType="primary"
								onClick={() => handleReindex(ALL_INDEXES_KEY)}
								small
							>
								{Liferay.Language.get('reindex-all')}
							</ClayButton>
						)}
					</ClayList.ItemField>
				</ClayList.Item>
			</ClayList>

			<PromisesResolver.Resolved>
				{Object.values(groups).map((group, index) => (
					<Body.List
						disabled={
							isReindexing(ALL_INDEXES_KEY) ||
							isReindexing(group.key)
						}
						getReindexStatus={getReindexStatus}
						handleReindex={handleReindex}
						isReindexing={isReindexing}
						key={index}
						{...group}
					/>
				))}
			</PromisesResolver.Resolved>
		</>
	);
}

Body.List = List;

export default Body;
