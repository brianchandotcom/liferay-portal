/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React from 'react';

import {FilterValue} from '../types';

interface Props {
	onChange: (value: FilterValue) => void;
	value: FilterValue;
}

interface Pill {
	dot?: 'danger' | 'secondary' | 'success';
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
			dot: 'success',
			label: Liferay.Language.get('passed'),
			value: 'passed',
		},
		{
			dot: 'danger',
			label: Liferay.Language.get('failed'),
			value: 'failed',
		},
		{
			dot: 'secondary',
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
				{pills.map((pill) => {
					const pressed = value === pill.value;

					return (
						<ClayButton
							aria-pressed={pressed}
							className="ml-2"
							displayType={pressed ? 'secondary' : 'unstyled'}
							key={pill.value}
							onClick={() => onChange(pill.value)}
							small
						>
							{pill.dot && (
								<span
									aria-hidden="true"
									className={`mr-2 text-${pill.dot}`}
								>
									<ClayIcon symbol="simple-circle" />
								</span>
							)}

							{pill.label}
						</ClayButton>
					);
				})}
			</div>
		</div>
	);
};

export default FilterPills;
