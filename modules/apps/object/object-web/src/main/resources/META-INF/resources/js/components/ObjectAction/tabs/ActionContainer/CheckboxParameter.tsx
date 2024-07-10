/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React from 'react';

interface CheckboxParameterProps {
	setValues: (values: Partial<ObjectAction>) => void;
	values: Partial<ObjectAction>;
}

import './CheckboxParameter.scss';

export function CheckboxParameter({setValues, values}: CheckboxParameterProps) {
	return (
		<>
			<div className="lfr-object__action-builder-checkbox-parameter-container">
				<ClayCheckbox
					checked={false}
					disabled={values.system}
					label={Liferay.Language.get('also-relate-entries')}
					onChange={({target: {checked}}) => {
						setValues({
							parameters: {
								...values.parameters,
								relatedObjectEntries: checked,
							},
						});
					}}
				/>

				<ClayTooltipProvider>
					<div
						data-tooltip-align="top"
						title={Liferay.Language.get(
							'automatically-relate-object-entries-involved-in-the-action'
						)}
					>
						<ClayIcon
							className="lfr-object__action-builder-tooltip-icon"
							symbol="question-circle-full"
						/>
					</div>
				</ClayTooltipProvider>
			</div>
		</>
	);
}
