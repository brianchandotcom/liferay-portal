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
import ClayIcon from '@clayui/icon';
import ClaySelect from '@clayui/select';
import ClayDropDown from '@clayui/drop-down';
import Variants from './variants/Variants.es';
import {
	SegmentsExperienceType,
	SegmentsExperimentType,
	initialSegmentsVariantType
} from '../types.es';

function SegmentsExperiments({
	segmentsExperiences = [],
	onCreateSegmentsExperiment,
	onVariantCreation,
	segmentsExperiment,
	onEditSegmentsExperiment,
	onSelectSegmentsExperienceChange,
	selectedSegmentsExperienceId,
	variants
}) {
	const [dropdown, setDropdown] = useState(false);

	return (
		<React.Fragment>
			{segmentsExperiences.length > 1 && (
				<div className="form-group">
					<label>{Liferay.Language.get('select-experience')}</label>
					<ClaySelect
						defaultValue={selectedSegmentsExperienceId}
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
				<React.Fragment>
					<div className="d-flex justify-content-between align-items-center">
						<h3 className="mb-0 text-dark">
							{segmentsExperiment.name}
						</h3>
						<ClayDropDown
							active={dropdown}
							onActiveChange={setDropdown}
							trigger={
								<ClayButton
									aria-label="open"
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

					<Variants
						onVariantCreation={onVariantCreation}
						variants={variants}
					/>

					<ClayButton className="w-100 mt-2" disabled>
						{Liferay.Language.get('review-and-run-test')}
					</ClayButton>
				</React.Fragment>
			)}
			{!segmentsExperiment && (
				<React.Fragment>
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
				</React.Fragment>
			)}
		</React.Fragment>
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
	onVariantCreation: PropTypes.func.isRequired,
	onEditSegmentsExperiment: PropTypes.func.isRequired,
	onSelectSegmentsExperienceChange: PropTypes.func.isRequired,
	selectedSegmentsExperienceId: PropTypes.string.isRequired,
	segmentsExperiment: SegmentsExperimentType,
	segmentsExperiences: PropTypes.arrayOf(SegmentsExperienceType),
	variants: PropTypes.arrayOf(initialSegmentsVariantType)
};

export default SegmentsExperiments;
