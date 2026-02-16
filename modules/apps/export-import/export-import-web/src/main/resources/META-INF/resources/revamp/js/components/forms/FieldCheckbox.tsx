/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import React from 'react';

export function FieldCheckbox({
	checked,
	description,
	id,
	label,
	name,
	onChange,
	...restProps
}: {
	checked: boolean;
	description?: string;
	id?: string;
	label: string;
	name: string;
	onChange: (checked: boolean) => void;
} & React.ComponentProps<typeof ClayCheckbox>) {
	const fieldId = id ?? name;
	const labelId = `${fieldId}-label`;
	const descriptionId = `${fieldId}-description`;

	const handleChange = () => onChange(!checked);

	const stopPropagation = (event: React.SyntheticEvent) => {
		event.stopPropagation();
	};

	return (
		<div
			className="border mb-2 p-3 rounded text-3"
			onClick={handleChange}
			style={{cursor: 'pointer'}}
		>
			<ClayLayout.ContentRow padded>
				<ClayLayout.ContentCol expand={false}>
					<div className="pt-1">
						<ClayCheckbox
							{...restProps}
							aria-describedby={
								description ? descriptionId : undefined
							}
							aria-labelledby={labelId}
							checked={checked}
							id={fieldId}
							onChange={handleChange}
							onClick={stopPropagation}
						/>
					</div>
				</ClayLayout.ContentCol>

				<ClayLayout.ContentCol expand>
					<ClayLayout.ContentSection>
						<label
							className="font-weight-semi-bold mb-0 text-dark"
							htmlFor={fieldId}
							id={labelId}
							onClick={stopPropagation}
						>
							{label}
						</label>

						{description && (
							<div
								className="small text-secondary"
								id={descriptionId}
							>
								{description}
							</div>
						)}
					</ClayLayout.ContentSection>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>
		</div>
	);
}
