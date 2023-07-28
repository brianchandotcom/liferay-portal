/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const ExperienceDropdown = (props) => {
	const experiences = props.userExperiences;

	const [selectedKey, setSelectedKey] = useState('');

	const handleSelectionChange = (value) => {
		setSelectedKey(value);

		props.updatePreviewRender();
	};

	return (
		!!experiences.length && (
			<div className="ml-3 mr-2">
				<Picker
					aria-labelledby="picker-label"
					id="picker"
					items={experiences}
					onSelectionChange={(value) => handleSelectionChange(value)}
					placeholder="Select a User Experience"
					selectedKey={selectedKey}
				>
					{(experience) => (
						<ExperienceItem
							active={experience.active}
							id={experience.id}
							name={experience.name}
							segmentName={experience.segmentName}
						/>
					)}
				</Picker>
			</div>
		)
	);
};

ExperienceDropdown.propTypes = {
	status: PropTypes.string,
	updatePreviewRender: PropTypes.func,
	userExperiences: PropTypes.arrayOf(
		PropTypes.shape({
			active: PropTypes.number,
			id: PropTypes.number,
			name: PropTypes.string,
			segmentName: PropTypes.string,
		})
	),
};

const ExperienceItem = (props) => {

	return (
		<Option key={props.id}>
			<ClayLayout.ContentRow verticalAlign="center">
				<ClayLayout.ContentCol style={{flexShrink: 1, minWidth: 0}}>
					<ClayLayout.ContentSection>
						<span className="text-truncate-inline">
							<span
								className="font-weight-semi-bold text-truncate"
								data-tooltip-align="top"
								title={props.name}
							>
								{props.name}
							</span>

							<ExperienceStatusLabel active={props.active} />
						</span>

						<span className="text-truncate">
							<span className="mr-1 text-secondary">
								{Liferay.Language.get('segment') + ': '}
							</span>

							{props.segmentName}
						</span>
					</ClayLayout.ContentSection>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>
		</Option>
	);
};

ExperienceItem.propTypes = {
	active: PropTypes.number,
	id: PropTypes.number,
	name: PropTypes.string,
	segmentName: PropTypes.string,
};

const ExperienceStatusLabel = (props) => {
	const ACTIVE = 1;
	const INACTIVE = 0;

	let displayType = null;
	let label = null;

	if (props.active === ACTIVE) {
		displayType = 'success';
		label = Liferay.Language.get('active');
	}
	else if (props.active === INACTIVE) {
		displayType = 'secondary';
		label = Liferay.Language.get('inactive');
	}

	return (
		<ClayLabel
			className="flex-shrink-0 inline-item-after justify-content-end"
			displayType={displayType}
		>
			{label}
		</ClayLabel>
	);
};

ExperienceStatusLabel.propTypes = {
	active: PropTypes.number,
};

export default ExperienceDropdown;
