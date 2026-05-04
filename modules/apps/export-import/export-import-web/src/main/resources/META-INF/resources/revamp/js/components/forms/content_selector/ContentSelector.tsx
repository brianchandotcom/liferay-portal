/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import React from 'react';

import {PortletDataHandlerSection} from '../../../types/portletDataHandlerSection';
import {updateSelection} from '../../../utils/contentSelection';
import ContentSection, {SectionSelection} from './ContentSection';

export type ContentSelection = Record<string, SectionSelection>;

interface ContentSelectorProps {
	'aria-labelledby'?: string;
	'errorMessage'?: string;
	'name': string;
	'onChange': (value: ContentSelection | undefined) => void;
	'sections': PortletDataHandlerSection[];
	'value': ContentSelection | undefined;
}

export default function ContentSelector({
	'aria-labelledby': ariaLabelledby,
	errorMessage,
	name,
	onChange,
	sections,
	value,
}: ContentSelectorProps) {
	const currentValue = value || {};
	const errorId = errorMessage ? `${name}-error-message` : undefined;

	return (
		<div
			aria-describedby={errorId}
			aria-invalid={errorMessage ? true : undefined}
			aria-labelledby={ariaLabelledby}
			className="mt-4"
			role="group"
		>
			{sections.map((section: PortletDataHandlerSection) => (
				<ContentSection
					key={section.name}
					onChange={(sectionValue) =>
						onChange(
							updateSelection(
								currentValue,
								section.name,
								sectionValue
							)
						)
					}
					section={section}
					value={currentValue[section.name]}
				/>
			))}

			{errorMessage && (
				<ClayAlert
					displayType="danger"
					id={errorId}
					title={Liferay.Language.get('error-colon')}
				>
					{errorMessage}
				</ClayAlert>
			)}
		</div>
	);
}
