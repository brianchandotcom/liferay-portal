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

import React from 'react';
import PropTypes from 'prop-types';
import ClayList from '@clayui/list';
import ClayLabel from '@clayui/label';
import {statusToLabelDisplayType} from '../util/statuses.es';
import {SegmentsExperimentType} from '../types.es';
import SegmentsExperimentContext from '../context.es';
import {NO_EXPERIMENT_ILLUSTRATION_FILE_NAME} from '../util/contants.es';

const {useContext} = React;

function ExperimentsHistory({experimentHistory, onDeleteSegmentsExperiment}) {
	const {assetsPath} = useContext(SegmentsExperimentContext);

	const noHistoryIllustration = `${assetsPath}${NO_EXPERIMENT_ILLUSTRATION_FILE_NAME}`;

	return experimentHistory.length === 0 ? (
		<div className="text-center">
			<img
				alt=""
				className="my-3"
				src={noHistoryIllustration}
				width="120px"
			/>

			<h4>
				{Liferay.Language.get(
					'there-is-no-test-history-for-experience'
				)}
			</h4>

			<p className="text-secondary">
				{Liferay.Language.get(
					'completed-or-terminated-tests-will-be-archived-here'
				)}
			</p>
		</div>
	) : (
		<ClayList>
			{experimentHistory.map(experiment => {
				return (
					<ClayList.Item
						className="py-3"
						flex
						key={experiment.segmentsExperimentId}
					>
						<ClayList.ItemField expand>
							<ClayList.ItemTitle>
								{experiment.name}
							</ClayList.ItemTitle>
							<ClayList.ItemText className="text-secondary">
								{experiment.description}
							</ClayList.ItemText>
							<ClayList.ItemText>
								<ClayLabel
									displayType={statusToLabelDisplayType(
										experiment.status.value
									)}
								>
									{experiment.status.label}
								</ClayLabel>
							</ClayList.ItemText>
						</ClayList.ItemField>
						<ClayList.ItemField>
							<ClayList.QuickActionMenu>
								<ClayList.QuickActionMenu.Item
									onClick={() =>
										_handleDeleteExperiment(
											experiment.segmentsExperimentId
										)
									}
									symbol="times-circle"
								/>
							</ClayList.QuickActionMenu>
						</ClayList.ItemField>
					</ClayList.Item>
				);
			})}
		</ClayList>
	);

	function _handleDeleteExperiment(experimentId) {
		const confirmed = confirm(
			Liferay.Language.get('are-you-sure-you-want-to-delete-this')
		);

		if (confirmed) return onDeleteSegmentsExperiment(experimentId);
	}
}

ExperimentsHistory.propTypes = {
	experimentHistory: PropTypes.arrayOf(SegmentsExperimentType).isRequired,
	onDeleteSegmentsExperiment: PropTypes.func.isRequired
};

export default ExperimentsHistory;
