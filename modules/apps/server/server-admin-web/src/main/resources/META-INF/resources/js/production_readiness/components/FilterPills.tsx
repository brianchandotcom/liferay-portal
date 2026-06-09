/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React from 'react';

import {FilterValue} from '../types';

interface Props {
	onChange: (value: FilterValue) => void;
	value: FilterValue;
}

interface Pill {
	dot?: 'failed' | 'ignored' | 'passed';
	label: string;
	value: FilterValue;
}

const FilterPills: React.FC<Props> = ({onChange, value}) => {
	const pills: Pill[] = [
		{
			label: Liferay.Language.get('all-validations'),
			value: 'all',
		},
		{
			dot: 'passed',
			label: Liferay.Language.get('passed'),
			value: 'passed',
		},
		{
			dot: 'failed',
			label: Liferay.Language.get('failed'),
			value: 'failed',
		},
		{
			dot: 'ignored',
			label: Liferay.Language.get('ignored'),
			value: 'ignored',
		},
	];

	return (
		<div className="production-readiness-filters text-right">
			<div className="small text-secondary text-uppercase">
				{Liferay.Language.get('filters')}
			</div>

			<div
				aria-label={Liferay.Language.get('filters')}
				className="align-items-center d-flex"
				role="group"
			>
				{pills.map((pill) => (
					<ClayButton
						aria-pressed={value === pill.value}
						className="ml-2"
						displayType={
							value === pill.value ? 'secondary' : 'unstyled'
						}
						key={pill.value}
						onClick={() => onChange(pill.value)}
						small
					>
						{pill.dot && (
							<span
								className={`production-readiness-dot production-readiness-dot-${pill.dot} mr-2`}
							/>
						)}

						{pill.label}
					</ClayButton>
				))}
			</div>
		</div>
	);
};

export default FilterPills;
