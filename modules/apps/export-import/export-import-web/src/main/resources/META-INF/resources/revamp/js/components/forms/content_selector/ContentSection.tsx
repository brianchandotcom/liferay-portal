/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import React from 'react';

import {
	PortletDataHandler,
	PortletDataHandlerSection as PortletDataHandlerSectionType,
} from '../../../utils/mockPortletDataHandlerSections';
import PortletDataControl, {
	HandlerSelection,
	getInitialSelection,
	isSelected,
} from './PortletDataControl';

export type SectionSelection = Record<string, HandlerSelection>;

interface ContentSectionProps {
	onChange: (value: SectionSelection | undefined) => void;
	section: PortletDataHandlerSectionType;
	value: SectionSelection | undefined;
}

export default function ContentSection({
	onChange,
	section,
	value,
}: ContentSectionProps) {
	const portletContextsValue = value || {};

	const selected = section.portletDataHandlers.every((context) =>
		isSelected(portletContextsValue[context.name], context)
	);

	const isPresent = !!Object.keys(portletContextsValue).length;

	const handleSelectAll = () => {
		if (selected) {
			onChange(undefined);
		}
		else {
			const newValue: SectionSelection = {};

			section.portletDataHandlers.forEach((context) => {
				newValue[context.name] = getInitialSelection(context);
			});

			onChange(newValue);
		}
	};

	return (
		<div className="mb-5 sheet">
			<ClayLayout.ContentRow padded>
				<ClayLayout.ContentCol expand={false}>
					<ClayCheckbox
						checked={selected}
						indeterminate={isPresent && !selected}
						onChange={handleSelectAll}
					/>
				</ClayLayout.ContentCol>

				<ClayLayout.ContentCol expand>
					<div className="font-weight-bold h3 mb-0">
						{section.name}
					</div>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			<div
				className="overflow-auto pl-4"
				style={{
					maxHeight: '400px',
				}}
			>
				{section.portletDataHandlers.map(
					(context: PortletDataHandler) => (
						<PortletDataControl
							control={context}
							key={context.name}
							onChange={(controlValue) => {
								const {[context.name]: _, ...newEntries} =
									portletContextsValue;

								if (controlValue) {
									newEntries[context.name] = controlValue;
								}

								onChange(
									Object.keys(newEntries).length
										? (newEntries as SectionSelection)
										: undefined
								);
							}}
							value={portletContextsValue[context.name]}
						/>
					)
				)}
			</div>
		</div>
	);
}
