/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayLayout from '@clayui/layout';
import {sub} from 'frontend-js-web';
import React, {useId} from 'react';

import '../../../../css/utilities.scss';
import {PageTreeModalConfiguration} from '../../../pages/export/components/PageTreeModal';
import {
	PreviewPortletDataHandlerBoolean,
	PreviewPortletDataHandlerSection as PortletDataHandlerSectionType,
} from '../../../types/portletDataHandler';
import {
	HandlerSelection,
	getInitialSelections,
	getSelectionSummary,
	isSelected,
	updateSelection,
} from '../../../utils/contentSelection';
import CollapsibleGroup from './CollapsibleGroup';
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
	const checkboxId = useId();

	const portletContextsValue = value || {};

	const controls =
		section.previewPortletDataHandlers.map<PreviewPortletDataHandlerBoolean>(
			(handler) => ({...handler, type: 'Boolean'})
		);

	const selected = controls.every((context) =>
		isSelected(portletContextsValue[context.name], context)
	);

	return (
		<ClayLayout.Sheet className="mt-0">
			<CollapsibleGroup
				bodyClassName="content-section-controls mt-2 overflow-auto pl-2"
				checkboxId={checkboxId}
				disclosure={({expanded, ...disclosureProps}) => (
					<ClayButtonWithIcon
						{...disclosureProps}
						aria-label={
							expanded
								? sub(
										Liferay.Language.get('collapse-x'),
										section.label
									)
								: sub(
										Liferay.Language.get('expand-x'),
										section.label
									)
						}
						className="text-secondary"
						displayType="unstyled"
						symbol={expanded ? 'angle-down' : 'angle-right'}
					/>
				)}
				indeterminate={
					!!Object.keys(portletContextsValue).length && !selected
				}
				label={section.label}
				labelClassName="font-weight-bold h3"
				onToggle={() =>
					onChange(
						selected ? undefined : getInitialSelections(controls)
					)
				}
				selected={selected}
				summary={getSelectionSummary(controls, portletContextsValue)}
				tags={
					<SectionTags
						additionCount={section.additionCount}
						deletionCount={
							showDeletions ? section.deletionCount : undefined
						}
					/>
				}
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
						pageTreeModalConfiguration={pageTreeModalConfiguration}
						showDeletions={showDeletions}
						topLevel
						value={portletContextsValue[context.name]}
					/>
				))}
			</CollapsibleGroup>
		</ClayLayout.Sheet>
	);
}
