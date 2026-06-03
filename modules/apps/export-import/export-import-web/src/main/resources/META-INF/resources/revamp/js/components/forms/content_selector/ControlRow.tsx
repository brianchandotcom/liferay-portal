/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import classnames from 'classnames';
import React, {ReactNode} from 'react';

import '../../../../css/utilities.scss';

export default function ControlRow({
	checkboxId,
	children,
	description,
	disclosure,
	indeterminate,
	label,
	labelClassName,
	onToggle,
	selected,
	tags,
}: {
	checkboxId: string;
	children?: ReactNode;
	description?: string;
	disclosure?: ReactNode;
	indeterminate: boolean;
	label: string;
	labelClassName?: string;
	onToggle: () => void;
	selected: boolean;
	tags?: ReactNode;
}) {
	return (
		<div className="checkbox-row">
			<ClayCheckbox
				aria-describedby={
					description ? `${checkboxId}-description` : undefined
				}
				checked={selected}
				id={checkboxId}
				indeterminate={indeterminate}
				onChange={onToggle}
			/>

			<div className="align-items-center d-flex justify-content-between ml-2">
				<span className="align-items-center d-inline-flex">
					<label
						className={classnames(
							'cursor-pointer mb-0',
							labelClassName
						)}
						htmlFor={checkboxId}
					>
						{label}
					</label>

					{tags}
				</span>

				{disclosure}
			</div>

			{(description || children) && (
				<div className="checkbox-row-content ml-2">
					{description && (
						<span
							className="d-block small text-secondary"
							id={`${checkboxId}-description`}
						>
							{description}
						</span>
					)}

					{children}
				</div>
			)}
		</div>
	);
}
