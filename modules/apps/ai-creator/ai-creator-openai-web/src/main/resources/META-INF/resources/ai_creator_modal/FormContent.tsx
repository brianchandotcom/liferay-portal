/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import React from 'react';

interface Props {
	namespace: string;
}

const TONE_OPTIONS = [
	Liferay.Language.get('neutral'),
	Liferay.Language.get('casual'),
	Liferay.Language.get('friendly'),
	Liferay.Language.get('formal'),
	Liferay.Language.get('academic'),
].map((label) => ({
	label,
	value: label,
}));

export function FormContent({namespace}: Props) {
	const descriptionId = `${namespace}content`;
	const toneId = `${namespace}tone`;
	const wordCountId = `${namespace}words`;

	return (
		<>
			<ClayForm.Group>
				<label htmlFor={descriptionId}>
					{Liferay.Language.get('description')}

					<ClayIcon
						className="c-ml-1 reference-mark"
						focusable="false"
						role="presentation"
						symbol="asterisk"
					/>
				</label>

				<ClayInput
					id={descriptionId}
					name={descriptionId}
					required
					type="text"
				/>
			</ClayForm.Group>

			<ClayLayout.Row gutters>
				<ClayLayout.Col>
					<ClayForm.Group>
						<label htmlFor={toneId}>
							{Liferay.Language.get('tone')}
						</label>

						<ClaySelectWithOption
							id={toneId}
							name={toneId}
							options={TONE_OPTIONS}
						/>
					</ClayForm.Group>
				</ClayLayout.Col>

				<ClayLayout.Col>
					<ClayForm.Group>
						<label htmlFor={wordCountId}>
							{Liferay.Language.get('word-count')}
						</label>

						<ClayInput
							defaultValue="100"
							id={wordCountId}
							min="1"
							name={wordCountId}
							step="1"
							type="number"
						/>
					</ClayForm.Group>
				</ClayLayout.Col>
			</ClayLayout.Row>
		</>
	);
}
