/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {PortletDataHandlerSection} from '../../../utils/mockPortletDataHandlerSections';
import ContentSection, {SectionSelection} from './ContentSection';

export type ContentSelection = Record<string, SectionSelection>;

interface ContentSelectorProps {
	onChange: (value: ContentSelection | undefined) => void;
	sections: PortletDataHandlerSection[];
	value: ContentSelection | undefined;
}

export default function ContentSelector({
	onChange,
	sections,
	value,
}: ContentSelectorProps) {
	const currentValue = value || {};

	return (
		<div className="mt-4">
			{sections.map((section: PortletDataHandlerSection) => (
				<ContentSection
					key={section.key}
					onChange={(sectionValue) => {
						const {[section.key]: _, ...newValue} = currentValue;

						if (sectionValue) {
							newValue[section.key] = sectionValue;
						}

						onChange(
							Object.keys(newValue).length
								? (newValue as ContentSelection)
								: undefined
						);
					}}
					section={section}
					value={currentValue[section.key]}
				/>
			))}
		</div>
	);
}
