/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import React from 'react';

interface Props {
	portletNamespace: string;
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

export function FormContent({portletNamespace}: Props) {
	const descriptionId = `${portletNamespace}content`;
	const toneId = `${portletNamespace}tone`;
	const wordCountId = `${portletNamespace}words`;

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
					placeholder={Liferay.Language.get('write-something')}
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
