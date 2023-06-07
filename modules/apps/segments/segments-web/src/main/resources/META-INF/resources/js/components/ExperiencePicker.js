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

import {Option, Picker, Text} from '@clayui/core';
import Label from '@clayui/label';
import Layout from '@clayui/layout';
import {navigate} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import '../../css/experience_picker.scss';

const TriggerLabel = React.forwardRef(({selectedItem, ...otherProps}, ref) => {
	return (
		<button
			{...otherProps}
			className="btn btn-sm btn-unstyled form-control-select"
			ref={ref}
			tabIndex={0}
		>
			<div
				className="c-inner"
				tabIndex="-1"
				title={Liferay.Language.get('experience-selector')}
			>
				<Layout.ContentRow className="flex-wrap" verticalAlign="center">
					<Layout.ContentCol
						className="experience-picker-text mr-2"
						expand
					>
						<Text size={4}>
							{selectedItem.segmentsExperienceName}
						</Text>
					</Layout.ContentCol>

					<Layout.ContentCol>
						<Label
							className="bg-transparent m-0"
							displayType={
								selectedItem.active ? 'success' : 'secondary'
							}
						>
							{selectedItem.statusLabel}
						</Label>
					</Layout.ContentCol>
				</Layout.ContentRow>
			</div>
		</button>
	);
});

const ExperiencePicker = ({
	segmentsExperiences,
	selectedSegmentsExperience,
}) => {
	const [disabled, setDisabled] = useState(false);
	const [selectedKey] = React.useState(
		selectedSegmentsExperience.segmentsExperienceId
	);

	const handleExperienceChange = (key) => {
		const newSelectedExperience = segmentsExperiences.find(
			(experience) => experience.segmentsExperienceId === key
		);

		if (newSelectedExperience && newSelectedExperience.url) {
			navigate(newSelectedExperience.url);
		}
	};

	useEffect(() => {
		Liferay.on('SimulationMenu:closeSimulationPanel', () =>
			setDisabled(false)
		);

		Liferay.on('SimulationMenu:openSimulationPanel', () =>
			setDisabled(true)
		);
	}, []);

	return (
		<>
			{(!disabled || !Liferay.FeatureFlags['LPS-186155']) && (
				<Picker
					aria-label={Liferay.Language.get('experience-selector')}
					as={TriggerLabel}
					disabled={disabled}
					id="experience-picker"
					items={segmentsExperiences}
					onSelectionChange={handleExperienceChange}
					selectedItem={selectedSegmentsExperience}
					selectedKey={selectedKey}
				>
					{(item) => (
						<Option
							aria-describedby={`${item.segmentsExperienceId}-description`}
							aria-labelledby={`${item.segmentsExperienceId}-title`}
							key={item.segmentsExperienceId}
							textValue={item.segmentsExperienceName}
						>
							<Layout.ContentRow>
								<Layout.ContentCol className="pl-0" expand>
									<Text
										id={`${item.segmentsExperienceId}-title`}
										size={3}
										weight="semi-bold"
									>
										{item.segmentsExperienceName}
									</Text>

									<Text
										aria-hidden
										color="secondary"
										id={`${item.segmentsExperienceId}-description`}
										size={3}
									>
										{`${Liferay.Language.get('segment')}:
											${item.segmentsEntryName}`}
									</Text>
								</Layout.ContentCol>

								<Layout.ContentCol className="pr-0">
									<Label
										aria-hidden
										className="mr-0"
										displayType={
											item.active
												? 'success'
												: 'secondary'
										}
										id={`${item.segmentsExperienceId}-status`}
									>
										{item.statusLabel}
									</Label>
								</Layout.ContentCol>
							</Layout.ContentRow>
						</Option>
					)}
				</Picker>
			)}
		</>
	);
};

export default ExperiencePicker;
