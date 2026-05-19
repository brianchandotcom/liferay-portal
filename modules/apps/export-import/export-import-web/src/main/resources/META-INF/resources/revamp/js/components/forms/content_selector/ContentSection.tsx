/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import React, {useId, useState} from 'react';

import '../../../../css/utilities.scss';
import {PageTreeModalConfiguration} from '../../../pages/export/components/PageTreeModal';
import {
	PreviewPortletDataHandlerBoolean,
	PreviewPortletDataHandlerSection as PortletDataHandlerSectionType,
} from '../../../types/portletDataHandler';
import {
	HandlerSelection,
	getInitialSelection,
	isSelected,
	updateSelection,
} from '../../../utils/contentSelection';
import PortletDataControl from './PortletDataControl';
import SectionTags from './SectionTags';

export type SectionSelection = Record<string, HandlerSelection>;

interface ContentSectionProps {
	onChange: (value: SectionSelection | undefined) => void;
	pageTreeModalConfiguration?: PageTreeModalConfiguration;
	section: PortletDataHandlerSectionType;
	showDeletions?: boolean;
	value: SectionSelection | undefined;
}

export default function ContentSection({
	onChange,
	pageTreeModalConfiguration,
	section,
	showDeletions,
	value,
}: ContentSectionProps) {
	const [expanded, setExpanded] = useState(false);
	const bodyId = useId();
	const checkboxId = useId();

	const portletContextsValue = value || {};

	const controls =
		section.previewPortletDataHandlers.map<PreviewPortletDataHandlerBoolean>(
			(handler) => ({...handler, type: 'Boolean'})
		);

	const selected = controls.every((context) =>
		isSelected(portletContextsValue[context.name], context)
	);

	const summary = controls
		.filter((context) => portletContextsValue[context.name] !== undefined)
		.map((context) => context.label)
		.join(', ');

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
		<ClayLayout.Sheet className="mt-0">
			<ClayLayout.ContentRow className="align-items-center">
				<ClayLayout.ContentCol className="pr-2" expand={false}>
					<ClayCheckbox
						checked={selected}
						id={checkboxId}
						indeterminate={
							!!Object.keys(portletContextsValue).length &&
							!selected
						}
						onChange={handleSelectAll}
					/>
				</ClayLayout.ContentCol>

				<ClayLayout.ContentCol expand>
					<span className="align-items-center d-inline-flex">
						<label
							className="cursor-pointer font-weight-bold h3 mb-0"
							htmlFor={checkboxId}
						>
							{section.label}
						</label>

						<SectionTags
							additionCount={section.additionCount}
							deletionCount={
								showDeletions
									? section.deletionCount
									: undefined
							}
						/>
					</span>
				</ClayLayout.ContentCol>

				<ClayLayout.ContentCol expand={false}>
					<ClayButtonWithIcon
						aria-controls={bodyId}
						aria-expanded={expanded}
						aria-label={section.label}
						className="text-secondary"
						displayType="unstyled"
						onClick={() => setExpanded((prev) => !prev)}
						symbol={expanded ? 'angle-down' : 'angle-right'}
					/>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>

			<small className="d-block pl-4 text-secondary">
				{summary || <>&nbsp;</>}
			</small>

			{expanded && (
				<div
					className="content-section-controls mt-2 overflow-auto pl-2"
					id={bodyId}
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
							pageTreeModalConfiguration={
								pageTreeModalConfiguration
							}
							showDeletions={showDeletions}
							value={portletContextsValue[context.name]}
						/>
					))}
				</div>
			)}
		</ClayLayout.Sheet>
	);
}
