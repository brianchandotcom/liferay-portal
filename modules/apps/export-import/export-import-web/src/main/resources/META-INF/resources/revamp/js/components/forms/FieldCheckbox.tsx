/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import React from 'react';

const noop = () => {};

export function FieldCheckbox({
	checked = false,
	description,
	id,
	label,
	name,
	onChange = noop,
	...restProps
}: {
	checked?: boolean;
	description?: string;
	id?: string;
	label: string;
	name: string;
	onChange?: (checked: boolean) => void;
} & Omit<React.ComponentProps<typeof ClayCheckbox>, 'checked'>) {
	const fieldId = id ?? name;
	const labelId = `${fieldId}-label`;
	const descriptionId = `${fieldId}-description`;

	const toggle = () => onChange(!checked);

	const handleKeyDown = (event: React.KeyboardEvent<HTMLDivElement>) => {
		if (event.key === ' ' || event.key === 'Enter') {
			event.preventDefault();
			toggle();
		}
	};

	return (
		<div
			aria-checked={checked}
			aria-describedby={description ? descriptionId : undefined}
			aria-labelledby={labelId}
			className="border mb-2 p-3 rounded text-3"
			onClick={toggle}
			onKeyDown={handleKeyDown}
			role="checkbox"
			tabIndex={0}
		>
			<ClayLayout.ContentRow padded>
				<ClayLayout.ContentCol expand={false}>
					<div className="pointer-events-none pt-1">
						<ClayCheckbox
							{...restProps}
							checked={checked}
							name={name}
							tabIndex={-1}
						/>
					</div>
				</ClayLayout.ContentCol>

				<ClayLayout.ContentCol expand>
					<ClayLayout.ContentSection>
						<div
							className="font-weight-semi-bold text-dark"
							id={labelId}
						>
							{label}
						</div>

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
