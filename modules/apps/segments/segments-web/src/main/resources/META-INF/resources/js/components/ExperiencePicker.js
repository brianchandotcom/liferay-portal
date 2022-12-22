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
import React from 'react';

const TriggerLabel = React.forwardRef(({selectedItem, ...otherProps}, ref) => {
	return (
		<div ref={ref} {...otherProps} tabIndex={0}>
			<Layout.ContentRow verticalAlign="center">
				<Layout.ContentCol expand>
					<Text size={3} weight="semi-bold">
						{selectedItem.segmentsExperienceName}
					</Text>
				</Layout.ContentCol>

				<Layout.ContentCol>
					<Label
						displayType={
							selectedItem.active ? 'success' : 'secondary'
						}
					>
						{selectedItem.statusLabel}
					</Label>
				</Layout.ContentCol>
			</Layout.ContentRow>
		</div>
	);
});

const ExperiencePicker = ({experiences, selectedExperience}) => {
	return (
		<Picker
			as={TriggerLabel}
			items={experiences}
			selectedItem={selectedExperience}
		>
			{(item) => (
				<Option
					key={item.segmentsExperienceId}
					selectedItem={selectedExperience}
					textValue={item.segmentsExperienceName}
				>
					<a href={item.url}>
						<Layout.ContentRow>
							<Layout.ContentCol expand>
								<Text
									id={`${item.segmentsExperienceName}-title`}
									size={3}
									weight="semi-bold"
								>
									{item.segmentsExperienceName}
								</Text>

								<Text
									aria-hidden
									color="secondary"
									id={`${item.segmentsExperienceName}-description`}
									size={2}
								>
									{`${Liferay.Language.get('segment')}:
									${item.segmentsEntryName}`}
								</Text>
							</Layout.ContentCol>

							<Layout.ContentCol>
								<Label
									aria-hidden
									displayType={
										item.active ? 'success' : 'secondary'
									}
									id={`${item.segmentsExperienceName}-status`}
								>
									{item.statusLabel}
								</Label>
							</Layout.ContentCol>
						</Layout.ContentRow>
					</a>
				</Option>
			)}
		</Picker>
	);
};

export default ExperiencePicker;
