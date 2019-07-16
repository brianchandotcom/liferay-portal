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

import React, {useState} from 'react';
import PropTypes from 'prop-types';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayButton from '@clayui/button';
import ClayLabel from '@clayui/label';
import CreateExperimentModal from './CreateExperimentModal.es';
import {mapExperimentsStatus} from '../../../utils/experimentsUtils.es';

function ExperimentsDropdown({
	createExperiment,
	activeExperience,
	experiments
}) {
	const [active, setActive] = useState(false);
	const [modalShown, setModalShown] = useState(false);

	const filteredExperiments = experiments.filter(
		e => e.segmentsExperienceId === activeExperience
	);

	const canCreateExperiment = !filteredExperiments.some(
		experiment => experiment.status === 0
	);

	return (
		<React.Fragment>
			<ClayDropDown
				active={active}
				className="ml-2"
				onActiveChange={setActive}
				trigger={
					<ClayButton displayType={'secondary'} small={true}>
						{Liferay.Language.get('ab')}
						<ClayIcon symbol={'caret-bottom'} />
					</ClayButton>
				}
			>
				{filteredExperiments.length > 0 ? (
					<React.Fragment>
						<div className="px-3 pt-2">
							<h6 className="text-uppercase">
								{Liferay.Language.get(
									'ab-tests-for-current-experience'
								)}
							</h6>
						</div>
						<ClayDropDown.ItemList className="pb-1">
							{filteredExperiments.map(experiment => {
								return (
									<ClayDropDown.Item
										className="d-flex justify-content-between align-items-start"
										key={experiment.segmentsExperimentId}
									>
										<span className="truncate-text mr-2">
											{experiment.name}
										</span>
										<ClayLabel displayType="secondary">
											{mapExperimentsStatus(
												experiment.status
											)}
										</ClayLabel>
									</ClayDropDown.Item>
								);
							})}
						</ClayDropDown.ItemList>
					</React.Fragment>
				) : (
					<div className="px-3 pt-3">
						<h2>
							{Liferay.Language.get(
								'ab-test-help-message-empty-state-title'
							)}
						</h2>
						<p>
							{Liferay.Language.get(
								'ab-test-help-message-empty-state-hint'
							)}
						</p>
					</div>
				)}
				{canCreateExperiment && (
					<div className="px-3 py-2">
						<ClayButton
							className="w-100"
							onClick={() => setModalShown(true)}
						>
							{Liferay.Language.get('create-new-test')}
						</ClayButton>
					</div>
				)}
			</ClayDropDown>

			{modalShown && (
				<CreateExperimentModal
					onCreateExperiment={createExperiment}
					setVisible={setModalShown}
					visible={modalShown}
				/>
			)}
		</React.Fragment>
	);
}

ExperimentsDropdown.propTypes = {
	createExperiment: PropTypes.func.isRequired,
	activeExperience: PropTypes.string.isRequired,
	experiments: PropTypes.arrayOf(
		PropTypes.shape({
			segmentsExperimentId: PropTypes.string.isRequired,
			name: PropTypes.string.isRequired,
			description: PropTypes.string,
			segmentsExperienceId: PropTypes.string.isRequired,
			status: PropTypes.number.isRequired
		})
	)
};

export default ExperimentsDropdown;
