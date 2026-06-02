/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {HandlerSelection} from '../../../utils/contentSelection';
import {FieldCheckbox} from '../FieldCheckbox';

export interface SectionFooterField {
	key: string;
	label: string;
}

interface SectionFooterProps {
	fields: readonly SectionFooterField[];
	name: string;
	onChange: (value: HandlerSelection | undefined) => void;
	subtitle?: string;
	title: string;
	value: HandlerSelection | undefined;
}

export default function SectionFooter({
	fields,
	name,
	onChange,
	subtitle,
	title,
	value,
}: SectionFooterProps) {
	const selection = value && typeof value === 'object' ? value : undefined;

	return (
		<>
			<hr className="my-3" />

			<div className="p-3">
				<div className="font-weight-bold text-3">{title}</div>

				{subtitle ? (
					<small className="d-block mb-3 text-secondary">
						{subtitle}
					</small>
				) : null}

				<div className="c-gap-1 d-flex flex-column pl-4">
					{fields.map((field) => (
						<FieldCheckbox
							bordered={false}
							checked={Boolean(selection?.[field.key])}
							key={field.key}
							label={field.label}
							name={`${name}.${field.key}`}
							onChange={(checked) => {
								const nextSelection: Record<string, true> = {};

								fields.forEach((otherField) => {
									if (
										otherField.key === field.key
											? checked
											: Boolean(
													selection?.[otherField.key]
												)
									) {
										nextSelection[otherField.key] = true;
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
