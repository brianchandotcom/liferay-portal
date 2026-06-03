/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import classnames from 'classnames';
import React from 'react';

import '../../../css/utilities.scss';

export function OptionRow({
	bordered = false,
	description,
	descriptionId,
	inline = false,
	input,
	label,
	labelId,
}: {
	bordered?: boolean;
	description?: string;
	descriptionId?: string;
	inline?: boolean;
	input: React.ReactNode;
	label: string;
	labelId: string;
}) {
	const labelSpan = (
		<span
			className={classnames(
				'font-weight-semi-bold text-dark',
				!inline && 'd-block'
			)}
			id={labelId}
		>
			{label}

			{inline && description && ':'}
		</span>
	);

	const descriptionSpan = description && (
		<span
			className={
				inline
					? 'font-weight-normal ml-1 text-dark'
					: 'd-block small text-secondary'
			}
			id={descriptionId}
		>
			{description}
		</span>
	);

	return (
		<label
			className={classnames(
				'cursor-pointer d-block mb-2 text-3',
				bordered && 'border p-3 rounded'
			)}
		>
			<ClayLayout.ContentRow className="align-items-center checkbox-row">
				<ClayLayout.ContentCol className="pr-2" expand={false}>
					{input}
				</ClayLayout.ContentCol>

				<ClayLayout.ContentCol expand>
					{inline ? (
						<span>
							{labelSpan}

							{descriptionSpan}
						</span>
					) : (
						<>
							{labelSpan}
							{descriptionSpan}
						</>
					)}
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>
		</label>
	);
}
