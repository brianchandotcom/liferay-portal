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
import Variants from './Variants/Variants.es';
import {
	SegmentsVariantType,
	SegmentsExperienceType,
	SegmentsExperimentType
} from '../types.es';
import SegmentsExperimentsActions from './SegmentsExperimentsActions.es';
import SegmentsExperimentsDetails from './SegmentsExperimentsDetails.es';

const _statusToType = status => STATUS_TO_TYPE[status];

const STATUS_TO_TYPE = {
	0: 'secondary',
	1: 'primary',
	2: 'success',
	3: 'success',
	4: 'primary',
	5: 'warning',
	6: 'danger',
	7: 'warning'
};

function SegmentsExperiments({
	onCreateSegmentsExperiment,
	onEditSegmentsExperiment,
	onEditSegmentsExperimentStatus,
	onRunExperiment,
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
								</ClayDropDown.ItemList>
							</ClayDropDown>
						)}
					</div>

					<ClayLabel
						displayType={_statusToType(
							segmentsExperiment.status.value
						)}
					>
						{segmentsExperiment.status.label}
					</ClayLabel>

					<SegmentsExperimentsDetails
						segmentsExperiment={segmentsExperiment}
					/>

					<Variants
						editable={segmentsExperiment.editable}
						onVariantCreation={onVariantCreation}
						onVariantDeletion={onVariantDeletion}
						onVariantEdition={onVariantEdition}
						selectedSegmentsExperienceId={
							selectedSegmentsExperienceId
						}
						variants={variants}
					/>

					<SegmentsExperimentsActions
						onEditSegmentsExperimentStatus={
							onEditSegmentsExperimentStatus
						}
						onRunExperiment={onRunExperiment}
						segmentsExperiment={segmentsExperiment}
						variants={variants}
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
	onEditSegmentsExperiment: PropTypes.func.isRequired,
	onEditSegmentsExperimentStatus: PropTypes.func.isRequired,
	onRunExperiment: PropTypes.func.isRequired,
	onSelectSegmentsExperienceChange: PropTypes.func.isRequired,
	onVariantCreation: PropTypes.func.isRequired,
	onVariantDeletion: PropTypes.func.isRequired,
	onVariantEdition: PropTypes.func.isRequired,
	segmentsExperiences: PropTypes.arrayOf(SegmentsExperienceType),
	segmentsExperiment: SegmentsExperimentType,
	selectedSegmentsExperienceId: PropTypes.string.isRequired,
	variants: PropTypes.arrayOf(SegmentsVariantType)
};

export default SegmentsExperiments;
