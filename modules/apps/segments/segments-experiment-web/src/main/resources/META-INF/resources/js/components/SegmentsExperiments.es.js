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
import ClaySelect from '@clayui/select';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {SegmentsExperienceType, SegmentsExperimentType} from '../types.es';

function SegmentsExperiments({
	segmentsExperiences = [],
	onCreateExperiment,
	segmentsExperiment,
	onEditExperiment,
	activeExperience,
	onSelectExperimentChange
}) {
	const [dropdown, setDropdown] = useState(false);

	return (
		<React.Fragment>
			{segmentsExperiences.length > 1 && (
				<div className="form-group">
					<label>{Liferay.Language.get('select-experience')}</label>
					<ClaySelect
						defaultValue={activeExperience}
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
					<ul className="list-unstyled">
						<li className="d-flex justify-content-between align-items-center">
							<h3 className="mb-0 text-dark">
								{segmentsExperiment.name}
							</h3>
							<ClayDropDown
								active={dropdown}
								onActiveChange={setDropdown}
								trigger={
									<ClayButton
										aria-label="open"
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
						</li>
					</ul>

					<ClayButton className="w-100 mt-2" disabled>
						{Liferay.Language.get('review-and-run-test')}
					</ClayButton>
				</React.Fragment>
			)}
			{!segmentsExperiment && (
				<React.Fragment>
					<h4 className="text-dark">
						{Liferay.Language.get('no-active-test-for-experience')}
					</h4>
					<p>{Liferay.Language.get('create-test-help-message')}</p>
					<ClayButton
						className="w-100"
						displayType="primary"
						onClick={() => onCreateExperiment(activeExperience)}
					>
						{Liferay.Language.get('create-test')}
					</ClayButton>
				</React.Fragment>
			)}
		</React.Fragment>
	);

	function _handleExperienceSelection(event) {
		const segmentsExperienceId = event.target.value;
		onSelectExperimentChange(segmentsExperienceId);
	}

	function _handleEditExperiment() {
		onEditExperiment();
	}
}

SegmentsExperiments.propTypes = {
	activeExperience: PropTypes.string.isRequired,
	onCreateExperiment: PropTypes.func.isRequired,
	onEditExperiment: PropTypes.func.isRequired,
	onSelectExperimentChange: PropTypes.func.isRequired,
	segmentsExperiment: SegmentsExperimentType,
	segmentsExperiences: PropTypes.arrayOf(SegmentsExperienceType)
};

export default SegmentsExperiments;
