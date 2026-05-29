/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {HandlerSelection} from '../../../utils/contentSelection';
import {FieldCheckbox} from '../FieldCheckbox';

const FIELDS = [
	{key: 'comments', label: Liferay.Language.get('comments')},
	{key: 'ratings', label: Liferay.Language.get('ratings')},
] as const;

export default function CommentsAndRatings({
	onChange,
	subtitle,
	value,
}: {
	onChange: (commentsAndRatingsValue: HandlerSelection | undefined) => void;
	subtitle?: string;
	value: HandlerSelection | undefined;
}) {
	const selection = value && typeof value === 'object' ? value : undefined;

	return (
		<>
			<hr className="my-3" />

			<div className="p-3">
				<div className="font-weight-bold text-3">
					{Liferay.Language.get('comments-and-ratings')}
				</div>

				<small className="d-block mb-3 text-secondary">
					{subtitle ??
						Liferay.Language.get(
							'for-each-of-the-selected-content-types,-export-their'
						)}
				</small>

				<div className="c-gap-1 d-flex flex-column pl-4">
					{FIELDS.map(({key, label}) => (
						<FieldCheckbox
							bordered={false}
							checked={Boolean(selection?.[key])}
							key={key}
							label={label}
							name={`commentsAndRatings.${key}`}
							onChange={(checked) => {
								const nextSelection: Record<string, true> = {};

								FIELDS.forEach((other) => {
									if (
										other.key === key
											? checked
											: Boolean(selection?.[other.key])
									) {
										nextSelection[other.key] = true;
									}
								});

								onChange(
									Object.keys(nextSelection).length
										? (nextSelection as HandlerSelection)
										: undefined
								);
							}}
						/>
					))}
				</div>
			</div>
		</>
	);
}
