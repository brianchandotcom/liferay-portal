/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker, Text} from '@clayui/core';
import Form from '@clayui/form';
import ClayLabel from '@clayui/label';
import Layout from '@clayui/layout';
import {navigate} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import SegmentExperience from '../../types/SegmentExperience';

const TriggerLabel = React.forwardRef(
	(
		{selectedItem, ...otherProps}: {selectedItem: SegmentExperience},
		ref: React.LegacyRef<HTMLButtonElement>
	) => {
		return (
			<button
				{...otherProps}
				className="btn btn-block btn-secondary btn-sm form-control-select"
				ref={ref}
			>
				<Layout.ContentRow className="c-pr-3" verticalAlign="center">
					<Layout.ContentCol className="text-truncate" expand>
						<Text size={3} weight="normal">
							{selectedItem.segmentsExperienceName}
						</Text>
					</Layout.ContentCol>

					<Layout.ContentCol>
						<ClayLabel
							className="bg-transparent c-m-0"
							displayType={
								selectedItem.active ? 'success' : 'secondary'
							}
						>
							{selectedItem.statusLabel}
						</ClayLabel>
					</Layout.ContentCol>
				</Layout.ContentRow>
			</button>
		);
	}
);

export default function ExperienceSelector({
	segmentsExperiences,
	selectedSegmentsExperience,
	...otherProps
}: {
	segmentsExperiences: SegmentExperience[];
	selectedSegmentsExperience: SegmentExperience;
}) {
	const [disabled, setDisabled] = useState(false);

	const handleExperienceChange = (key: React.Key) => {
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
		<Form.Group {...otherProps}>
			{(!disabled || !Liferay.FeatureFlags['LPS-186558']) && (
				<Picker
					aria-label={Liferay.Language.get('experience-selector')}
					as={TriggerLabel}
					disabled={disabled}
					id="experience-picker"
					items={segmentsExperiences}
					onSelectionChange={handleExperienceChange}
					selectedItem={selectedSegmentsExperience}
					selectedKey={
						selectedSegmentsExperience.segmentsExperienceId
					}
				>
					{(item) => (
						<Option
							aria-describedby={`${item.segmentsExperienceId}-description`}
							aria-labelledby={`${item.segmentsExperienceId}-title`}
							key={item.segmentsExperienceId}
							textValue={item.segmentsExperienceName}
						>
							<Layout.ContentRow>
								<Layout.ContentCol className="c-pl-0" expand>
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

								<Layout.ContentCol className="c-pr-0">
									<ClayLabel
										aria-hidden
										className="c-mr-0"
										displayType={
											item.active
												? 'success'
												: 'secondary'
										}
										id={`${item.segmentsExperienceId}-status`}
									>
										{item.statusLabel}
									</ClayLabel>
								</Layout.ContentCol>
							</Layout.ContentRow>
						</Option>
					)}
				</Picker>
			)}
		</Form.Group>
	);
}
