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

import ClayLabel from '@clayui/label';
import ClayList from '@clayui/list';
import PropTypes from 'prop-types';
import React from 'react';

import SegmentsExperimentContext from '../context.es';
import {SegmentsExperimentType} from '../types.es';
import {NO_EXPERIMENT_ILLUSTRATION_FILE_NAME} from '../util/contants.es';
import {statusToLabelDisplayType} from '../util/statuses.es';

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

		if (confirmed) {
			return onDeleteSegmentsExperiment(experimentId);
		}
	}
}

ExperimentsHistory.propTypes = {
	experimentHistory: PropTypes.arrayOf(SegmentsExperimentType).isRequired,
	onDeleteSegmentsExperiment: PropTypes.func.isRequired
};

export default ExperimentsHistory;
