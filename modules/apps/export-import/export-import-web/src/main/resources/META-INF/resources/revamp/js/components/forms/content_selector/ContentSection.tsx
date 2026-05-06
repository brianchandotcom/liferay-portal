/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import React from 'react';

import {
	PortletDataHandlerBoolean,
	PortletDataHandlerSection as PortletDataHandlerSectionType,
} from '../../../types/portletDataHandler';
import {
	HandlerSelection,
	getInitialSelection,
	isSelected,
	updateSelection,
} from '../../../utils/contentSelection';
import PortletDataControl from './PortletDataControl';

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

	const controls = section.portletDataHandlers.map<PortletDataHandlerBoolean>(
		(handler) => ({...handler, type: 'Boolean'})
	);

	const selected = controls.every((context) =>
		isSelected(portletContextsValue[context.name], context)
	);

	const handleSelectAll = () => {
		if (selected) {
			onChange(undefined);
		}
		else {
			const newValue: SectionSelection = {};

			controls.forEach((context) => {
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
						indeterminate={
							!!Object.keys(portletContextsValue).length &&
							!selected
						}
						onChange={handleSelectAll}
					/>
				</ClayLayout.ContentCol>

				<ClayLayout.ContentCol expand>
					<div className="font-weight-bold h3 mb-0">
						{section.label}
					</div>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			<div
				className="overflow-auto pl-4"
				style={{
					maxHeight: '400px',
				}}
			>
				{controls.map((context) => (
					<PortletDataControl
						control={context}
						key={context.name}
						onChange={(controlValue) =>
							onChange(
								updateSelection(
									portletContextsValue,
									context.name,
									controlValue
								)
							)
						}
						value={portletContextsValue[context.name]}
					/>
				))}
			</div>
		</div>
	);
}
