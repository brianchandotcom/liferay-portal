/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker, Text} from '@clayui/core';
import React, {useContext, useEffect, useState} from 'react';

import {ChartDispatchContext} from '../context/ChartStateContext';
import ConnectionContext from '../context/ConnectionContext';

type Experience = {
	id: string | null;
	name: string;
};

interface ExperienceDropdownProps {
	experiencesDataProvider?: () => Promise<Experience[]>;
}

const ALL_EXPERIENCES: Experience = {
	id: null,
	name: Liferay.Language.get('all-experiences'),
};

const ExperienceDropdown: React.FC<ExperienceDropdownProps> = ({
	experiencesDataProvider,
}) => {
	const dispatch = useContext(ChartDispatchContext);
	const {validAnalyticsConnection} = useContext(ConnectionContext);

	const [experiences, setExperiences] = useState<Experience[]>([
		ALL_EXPERIENCES,
	]);

	useEffect(() => {
		if (
			validAnalyticsConnection &&
			typeof experiencesDataProvider === 'function'
		) {
			experiencesDataProvider()
				.then((data) => {
					setExperiences([
						ALL_EXPERIENCES,
						...(Array.isArray(data) ? data : []),
					]);
				})
				.catch((error) => {
					console.error('Failed to fetch experiences:', error);
				});
		}
	}, [experiencesDataProvider, validAnalyticsConnection]);

	return (
		<div className="experience-dropdown">
			<Picker
				aria-label={Liferay.Language.get('all-experiences')}
				className="bg-white border-light form-control-sm"
				defaultSelectedKey="all-experiences"
				disabled={experiences.length <= 1}
				items={experiences}
				onSelectionChange={(key) => {
					dispatch({
						payload: {
							key:
								key === 'all-experiences'
									? null
									: (key as string),
						},
						type: 'CHANGE_EXPERIENCE_ID_KEY',
					});
				}}
				searchable
			>
				{(item) => (
					<Option
						key={item.id ?? 'all-experiences'}
						textValue={item.name}
					>
						<div className="w-100">
							<Text size={3}>{item.name}</Text>
						</div>
					</Option>
				)}
			</Picker>
		</div>
	);
};

export default ExperienceDropdown;
