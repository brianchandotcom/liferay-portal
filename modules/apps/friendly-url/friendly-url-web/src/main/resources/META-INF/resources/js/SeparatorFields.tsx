/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React from 'react';

type Props = {
	errors: {
		errorMessage?: string;
		fields?: Record<string, string>;
	};
	fields: Array<{
		label: string;
		name: string;
		value: string;
	}>;
	url: string;
};

export default function SeparatorFields({errors, fields, url}: Props) {
	const id = useId();

	return (
		<>
			{fields.map((field) => {
				const error = errors.fields?.[field.name];

				return (
					<ClayForm.Group
						className={classNames({
							'has-error': error,
						})}
						key={field.name}
					>
						<label className="mb-0" htmlFor={field.name}>
							{field.label}
						</label>

						<p className="mb-1 small text-secondary">{url}</p>

						<p className="sr-only" id={id}>
							{sub(
								Liferay.Language.get(
									'this-will-work-as-a-suffix-for-x'
								),
								url
							)}
						</p>

						<ClayInput.Group>
							<ClayInput.GroupItem prepend shrink>
								<ClayInput.GroupText aria-hidden="true">
									/
								</ClayInput.GroupText>
							</ClayInput.GroupItem>

							<ClayInput.GroupItem append>
								<ClayInput
									aria-describedby={id}
									defaultValue={field.value}
									id={field.name}
									name={field.name}
								/>
							</ClayInput.GroupItem>
						</ClayInput.Group>

						{error ? (
							<ClayForm.FeedbackGroup>
								<ClayForm.FeedbackItem>
									<ClayForm.FeedbackIndicator symbol="exclamation-full" />

									{error}
								</ClayForm.FeedbackItem>
							</ClayForm.FeedbackGroup>
						) : null}
					</ClayForm.Group>
				);
			})}
		</>
	);
}
