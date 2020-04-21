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

import ClayButton from '@clayui/button';
import ClayProgressBar from '@clayui/progress-bar';
import React, {useMemo} from 'react';

import PromisesResolver from '../../../shared/components/promises-resolver/PromisesResolver.es';
import {ALL_INDEXES_KEY, getIndexesGroups} from './IndexesConstants.es';
import {List} from './IndexesPageBodyList.es';
import {useReindexActions} from './hooks/useReindexActions.es';

const Body = ({items = []}) => {
	const {getReindexStatus, handleReindex, isReindexing} = useReindexActions();

	const {completionPercentage = 0} = useMemo(
		() => getReindexStatus(ALL_INDEXES_KEY),
		[getReindexStatus]
	);

	const groups = useMemo(() => {
		const groups = getIndexesGroups();

		items.forEach(({group, ...index}) => {
			if (groups[group]) {
				groups[group].indexes.push(index);
			}
		});

		return Object.values(groups);
	}, [items]);

	return (
		<>
			<div className="mb-4 p-3 sheet">
				<div className="autofit-row autofit-row-center">
					<div className="autofit-col autofit-col-expand">
						<h5
							className="font-weight-semi-bold m-0 py-2"
							data-testid="reindexAllLabel"
						>
							{Liferay.Language.get('workflow-indexes')}
						</h5>
					</div>

					<div className="autofit-col">
						{isReindexing(ALL_INDEXES_KEY) ? (
							<ClayProgressBar
								data-testid="reindexAllStatus"
								value={completionPercentage}
							/>
						) : (
							<ClayButton
								data-testid="reindexAllBtn"
								onClick={() => handleReindex(ALL_INDEXES_KEY)}
								small
							>
								{Liferay.Language.get('reindex-all')}
							</ClayButton>
						)}
					</div>
				</div>
			</div>

			<PromisesResolver.Resolved>
				{groups.map((group, index) => (
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
};

Body.List = List;

export {Body};
