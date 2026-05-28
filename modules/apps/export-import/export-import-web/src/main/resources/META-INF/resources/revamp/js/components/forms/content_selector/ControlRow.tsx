/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import classnames from 'classnames';
import React, {ReactNode} from 'react';

export default function ControlRow({
	checkboxId,
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
		<ClayLayout.ContentRow className="align-items-center">
			<ClayLayout.ContentCol className="pr-2" expand={false}>
				<ClayCheckbox
					checked={selected}
					id={checkboxId}
					indeterminate={indeterminate}
					onChange={onToggle}
				/>
			</ClayLayout.ContentCol>

			<ClayLayout.ContentCol expand>
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

				{description && (
					<span className="d-block small text-secondary">
						{description}
					</span>
				)}
			</ClayLayout.ContentCol>

			{disclosure && (
				<ClayLayout.ContentCol expand={false}>
					{disclosure}
				</ClayLayout.ContentCol>
			)}
		</ClayLayout.ContentRow>
	);
}
