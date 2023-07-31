/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker, Text} from '@clayui/core';
import Form from '@clayui/form';
import ClayLabel from '@clayui/label';
import Layout from '@clayui/layout';
import classNames from 'classnames';
import {navigate} from 'frontend-js-web';
import React from 'react';

import SegmentExperience from '../../types/SegmentExperience';

const TriggerLabel = React.forwardRef(
	(
		{
			displayType,
			selectedItem,
			...otherProps
		}: {
			displayType: 'light' | 'dark';
			selectedItem: SegmentExperience;
		},
		ref: React.LegacyRef<HTMLButtonElement>
	) => {
		return (
			<button
				{...otherProps}
				className={classNames(
					'btn btn-block btn-sm form-control-select',
					{'btn-secondary': displayType === 'light'}
				)}
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

interface Props {
	disabled?: boolean;
	displayType?: 'light' | 'dark';
	segmentsExperiences: SegmentExperience[];
	selectedSegmentsExperience: SegmentExperience;
}

export default function ExperienceSelector({
	disabled = false,
	displayType = 'light',
	segmentsExperiences,
	selectedSegmentsExperience,
	...otherProps
}: Props) {
	const handleExperienceChange = (key: React.Key) => {
		const newSelectedExperience = segmentsExperiences.find(
			(experience) => experience.segmentsExperienceId === key
		);

		if (newSelectedExperience && newSelectedExperience.url) {
			navigate(newSelectedExperience.url);
		}
	};

	return (
		<Form.Group {...otherProps}>
			<Picker
				aria-label={Liferay.Language.get('experience-selector')}
				as={TriggerLabel}
				disabled={disabled}
				displayType={displayType}
				id="experience-picker"
				items={segmentsExperiences}
				onSelectionChange={handleExperienceChange}
				selectedItem={selectedSegmentsExperience}
				selectedKey={selectedSegmentsExperience.segmentsExperienceId}
			>
				{(item) => (
					<Option
						key={item.segmentsExperienceId}
						textValue={item.segmentsExperienceName}
					>
						<Layout.ContentRow>
							<Layout.ContentCol className="c-pl-0" expand>
								<Text size={3} weight="semi-bold">
									{item.segmentsExperienceName}
								</Text>

								<Text aria-hidden color="secondary" size={3}>
									{`${Liferay.Language.get('segment')}:
										${item.segmentsEntryName}`}
								</Text>
							</Layout.ContentCol>

							<Layout.ContentCol className="c-pr-0">
								<ClayLabel
									aria-hidden
									className="c-mr-0"
									displayType={
										item.active ? 'success' : 'secondary'
									}
								>
									{item.statusLabel}
								</ClayLabel>
							</Layout.ContentCol>
						</Layout.ContentRow>
					</Option>
				)}
			</Picker>
		</Form.Group>
	);
}
