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
import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClaySelect from '@clayui/select';
import Variants from './Variants/Variants.es';
import {
	InitialSegmentsVariantType,
	SegmentsExperimentGoal,
	SegmentsExperienceType,
	SegmentsExperimentType
} from '../types.es';
import SegmentsExperimentsDetails from './SegmentsExperimentsDetails.es';

function SegmentsExperiments({
	initialGoals,
	onCreateSegmentsExperiment,
	onEditSegmentsExperiment,
	onSelectSegmentsExperienceChange,
	onVariantCreation,
	onVariantDeletion,
	onVariantEdition,
	segmentsExperiences = [],
	segmentsExperiment,
	selectedSegmentsExperienceId,
	variants
}) {
	const [dropdown, setDropdown] = useState(false);

	const _selectedSegmentsExperienceId = segmentsExperiment
		? segmentsExperiment.segmentsExperienceId
		: selectedSegmentsExperienceId;

	return (
		<>
			{segmentsExperiences.length > 1 && (
				<div className="form-group">
					<label>{Liferay.Language.get('select-experience')}</label>
					<ClaySelect
						defaultValue={_selectedSegmentsExperienceId}
						onChange={_handleExperienceSelection}
					>
						{segmentsExperiences.map(segmentsExperience => {
							return (
								<ClaySelect.Option
									key={
										segmentsExperience.segmentsExperienceId
									}
									label={segmentsExperience.name}
									value={
										segmentsExperience.segmentsExperienceId
									}
								/>
							);
						})}
					</ClaySelect>
				</div>
			)}

			{segmentsExperiment && (
				<>
					<div className="d-flex justify-content-between align-items-center">
						<h3 className="mb-0 text-dark text-truncate">
							{segmentsExperiment.name}
						</h3>
						<ClayDropDown
							active={dropdown}
							onActiveChange={setDropdown}
							trigger={
								<ClayButton
									aria-label={Liferay.Language.get(
										'show-actions'
									)}
									borderless
									displayType="secondary"
									small={true}
								>
									<ClayIcon symbol="ellipsis-v" />
								</ClayButton>
							}
						>
							<ClayDropDown.ItemList>
								<ClayDropDown.Item
									onClick={_handleEditExperiment}
								>
									{Liferay.Language.get('edit')}
								</ClayDropDown.Item>
							</ClayDropDown.ItemList>
						</ClayDropDown>
					</div>

					<SegmentsExperimentsDetails
						initialGoals={initialGoals}
						segmentsExperiment={segmentsExperiment}
					/>

					<Variants
						onVariantCreation={onVariantCreation}
						onVariantDeletion={onVariantDeletion}
						onVariantEdition={onVariantEdition}
						selectedSegmentsExperienceId={
							selectedSegmentsExperienceId
						}
						variants={variants}
					/>

					<ClayButton className="w-100 mt-2" disabled>
						{Liferay.Language.get('review-and-run-test')}
					</ClayButton>
				</>
			)}
			{!segmentsExperiment && (
				<>
					<h4 className="text-dark">
						{Liferay.Language.get(
							'no-active-tests-were-found-for-the-selected-experience'
						)}
					</h4>
					<p>{Liferay.Language.get('create-test-help-message')}</p>
					<ClayButton
						className="w-100"
						displayType="primary"
						onClick={() =>
							onCreateSegmentsExperiment(
								selectedSegmentsExperienceId
							)
						}
					>
						{Liferay.Language.get('create-test')}
					</ClayButton>
				</>
			)}
		</>
	);

	function _handleExperienceSelection(event) {
		const segmentsExperienceId = event.target.value;

		onSelectSegmentsExperienceChange(segmentsExperienceId);
	}

	function _handleEditExperiment() {
		onEditSegmentsExperiment();
	}
}

SegmentsExperiments.propTypes = {
	initialGoals: PropTypes.arrayOf(SegmentsExperimentGoal),
	onCreateSegmentsExperiment: PropTypes.func.isRequired,
	onEditSegmentsExperiment: PropTypes.func.isRequired,
	onSelectSegmentsExperienceChange: PropTypes.func.isRequired,
	onVariantCreation: PropTypes.func.isRequired,
	onVariantDeletion: PropTypes.func.isRequired,
	onVariantEdition: PropTypes.func.isRequired,
	segmentsExperiences: PropTypes.arrayOf(SegmentsExperienceType),
	segmentsExperiment: SegmentsExperimentType,
	selectedSegmentsExperienceId: PropTypes.string.isRequired,
	variants: PropTypes.arrayOf(InitialSegmentsVariantType)
};

export default SegmentsExperiments;
