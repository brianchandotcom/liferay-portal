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
import ClayLabel from '@clayui/label';
import ClaySelect from '@clayui/select';
import ClickGoalPicker from './ClickGoalPicker/ClickGoalPicker.es';
import {SegmentsExperienceType, SegmentsExperimentType} from '../types.es';
import SegmentsExperimentsActions from './SegmentsExperimentsActions.es';
import SegmentsExperimentsDetails from './SegmentsExperimentsDetails.es';
import Variants from './Variants/Variants.es';
import {statusToLabelDisplayType, STATUS_DRAFT} from '../util/statuses.es';

function SegmentsExperiments({
	onCreateSegmentsExperiment,
	onDeleteSegmentsExperiment,
	onEditSegmentsExperiment,
	onEditSegmentsExperimentStatus,
	onRunExperiment,
	onSelectSegmentsExperienceChange,
	onWinnerExperiencePublishing,
	onExperimentDiscard,
	onTargetChange,
	segmentsExperiences = [],
	segmentsExperiment,
	selectedSegmentsExperienceId
}) {
	const [dropdown, setDropdown] = useState(false);

	const _selectedSegmentsExperienceId = segmentsExperiment
		? segmentsExperiment.segmentsExperienceId
		: selectedSegmentsExperienceId;
	// TODO there's some trouble here

	return (
		<>
			{segmentsExperiences.length > 1 && (
				<>
					<div className="form-group">
						<label>
							{Liferay.Language.get('select-experience')}
						</label>
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
					<hr />
				</>
			)}

			{segmentsExperiment && (
				<>
					<div className="d-flex justify-content-between align-items-center">
						<h3 className="mb-0 text-dark text-truncate">
							{segmentsExperiment.name}
						</h3>

						{segmentsExperiment.editable && (
							<ClayDropDown
								active={dropdown}
								data-testid="segments-experiments-drop-down"
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
									<ClayDropDown.Item
										onClick={_handleDeleteExperiment}
									>
										{Liferay.Language.get('delete')}
									</ClayDropDown.Item>
								</ClayDropDown.ItemList>
							</ClayDropDown>
						)}
					</div>

					<ClayLabel
						displayType={statusToLabelDisplayType(
							segmentsExperiment.status.value
						)}
					>
						{segmentsExperiment.status.label}
					</ClayLabel>

					<SegmentsExperimentsDetails
						segmentsExperiment={segmentsExperiment}
					/>

					{segmentsExperiment.goal.value === 'click' && (
						<ClickGoalPicker
							allowEdit={
								segmentsExperiment.status.value === STATUS_DRAFT
							}
							onSelectClickGoalTarget={selector => {
								onTargetChange(selector);
							}}
							target={segmentsExperiment.goal.target}
						/>
					)}

					<Variants
						selectedSegmentsExperienceId={
							selectedSegmentsExperienceId
						}
					/>

					<SegmentsExperimentsActions
						onEditSegmentsExperimentStatus={
							onEditSegmentsExperimentStatus
						}
						onExperimentDiscard={onExperimentDiscard}
						onRunExperiment={onRunExperiment}
						onWinnerExperiencePublishing={
							onWinnerExperiencePublishing
						}
					/>
				</>
			)}

			{!segmentsExperiment && (
				<div className="text-center">
					<h4 className="text-dark">
						{Liferay.Language.get(
							'no-active-tests-were-found-for-the-selected-experience'
						)}
					</h4>
					<p className="small">
						{Liferay.Language.get('create-test-help-message')}
					</p>
					<ClayButton
						displayType="secondary"
						onClick={() =>
							onCreateSegmentsExperiment(
								selectedSegmentsExperienceId
							)
						}
					>
						{Liferay.Language.get('create-test')}
					</ClayButton>
				</div>
			)}
		</>
	);

	function _handleDeleteExperiment() {
		const confirmed = confirm(
			Liferay.Language.get('are-you-sure-you-want-to-delete-this')
		);

		if (confirmed) return onDeleteSegmentsExperiment();
	}

	function _handleExperienceSelection(event) {
		const segmentsExperienceId = event.target.value;

		onSelectSegmentsExperienceChange(segmentsExperienceId);
	}

	function _handleEditExperiment() {
		onEditSegmentsExperiment();
	}
}

SegmentsExperiments.propTypes = {
	onCreateSegmentsExperiment: PropTypes.func.isRequired,
	onDeleteSegmentsExperiment: PropTypes.func.isRequired,
	onEditSegmentsExperiment: PropTypes.func.isRequired,
	onEditSegmentsExperimentStatus: PropTypes.func.isRequired,
	onExperimentDiscard: PropTypes.func.isRequired,
	onRunExperiment: PropTypes.func.isRequired,
	onSelectSegmentsExperienceChange: PropTypes.func.isRequired,
	onTargetChange: PropTypes.func.isRequired,
	onWinnerExperiencePublishing: PropTypes.func.isRequired,
	segmentsExperiences: PropTypes.arrayOf(SegmentsExperienceType),
	segmentsExperiment: SegmentsExperimentType,
	selectedSegmentsExperienceId: PropTypes.string.isRequired
};

export default SegmentsExperiments;
