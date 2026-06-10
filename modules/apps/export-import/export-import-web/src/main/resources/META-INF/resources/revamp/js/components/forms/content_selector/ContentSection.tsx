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
import {ExportImportProcess} from '../../../types/exportImportProcess';
import {
	PreviewPortletDataHandlerBoolean,
	PreviewPortletDataHandlerSection as PortletDataHandlerSectionType,
} from '../../../types/portletDataHandler';
import {
	CONTENT_SECTION_KEY,
	HandlerSelection,
	SITE_BUILDER_SECTION_KEY,
	getInitialSelections,
	getSelectionSummary,
	isSelected,
	updateSelection,
} from '../../../utils/contentSelection';
import CollapsibleGroup from './CollapsibleGroup';
import PortletDataControl from './PortletDataControl';
import SectionFooter from './SectionFooter';
import SectionTags from './SectionTags';

export type SectionSelection = Record<string, HandlerSelection>;

interface ContentSectionProps {
	commentsAndRatingsEnabled?: boolean;
	lookAndFeelEnabled?: boolean;
	onChange: (value: SectionSelection | undefined) => void;
	pageTreeModalConfiguration?: PageTreeModalConfiguration;
	process?: ExportImportProcess;
	section: PortletDataHandlerSectionType;
	showDeletions?: boolean;
	value: SectionSelection | undefined;
}

export default function ContentSection({
	commentsAndRatingsEnabled = false,
	lookAndFeelEnabled = false,
	onChange,
	pageTreeModalConfiguration,
	process = 'export',
	section,
	showDeletions,
	value,
}: ContentSectionProps) {
	const checkboxId = useId();

	const previewPortletDataHandlers =
		section.previewPortletDataHandlers.map<PreviewPortletDataHandlerBoolean>(
			(handler) => ({...handler, type: 'Boolean'})
		);

	const syntheticPreviewPortletDataHandlers = [
		{
			applies:
				lookAndFeelEnabled && section.name === SITE_BUILDER_SECTION_KEY,
			previewPortletDataHandler: {
				label: Liferay.Language.get('look-and-feel'),
				name: 'lookAndFeel',
				previewPortletDataHandlerControls: [
					{
						label: Liferay.Language.get('theme-settings'),
						name: 'themeSettings',
						type: 'Boolean',
					},
					{
						label: Liferay.Language.get('logo'),
						name: 'logo',
						type: 'Boolean',
					},
					{
						label: Liferay.Language.get('site-pages-settings'),
						name: 'sitePagesSettings',
						type: 'Boolean',
					},
					{
						label: Liferay.Language.get('site-template-settings'),
						name: 'siteTemplateSettings',
						type: 'Boolean',
					},
				],
				type: 'Boolean',
			} as PreviewPortletDataHandlerBoolean,
		},
	]
		.filter(({applies}) => applies)
		.map(({previewPortletDataHandler}) => previewPortletDataHandler);

	const allPreviewPortletDataHandlers = [
		...previewPortletDataHandlers,
		...syntheticPreviewPortletDataHandlers,
	];

	const sectionSelection = value || {};

	const allSelected = allPreviewPortletDataHandlers.every((context) =>
		isSelected(sectionSelection[context.name], context)
	);

	const anySelected = allPreviewPortletDataHandlers.some((context) =>
		isSelected(sectionSelection[context.name], context)
	);

	const sectionFooters = [
		{
			applies:
				commentsAndRatingsEnabled &&
				section.name === CONTENT_SECTION_KEY &&
				anySelected,
			fields: [
				{key: 'comments', label: Liferay.Language.get('comments')},
				{key: 'ratings', label: Liferay.Language.get('ratings')},
			],
			name: 'commentsAndRatings',
			subtitle:
				process === 'import'
					? Liferay.Language.get(
							'for-each-of-the-selected-content-types,-import-their'
						)
					: Liferay.Language.get(
							'for-each-of-the-selected-content-types,-export-their'
						),
			title: Liferay.Language.get('comments-and-ratings'),
		},
	].filter(({applies}) => applies);

	return (
		<ClayLayout.Sheet className="mt-0">
			<CollapsibleGroup
				bodyClassName="mt-2 pl-2"
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
					!allSelected &&
					allPreviewPortletDataHandlers.some(
						(context) =>
							sectionSelection[context.name] !== undefined
					)
				}
				label={section.label}
				labelClassName="font-weight-bold text-6"
				onToggle={() =>
					onChange(
						allSelected
							? undefined
							: getInitialSelections(
									allPreviewPortletDataHandlers
								)
					)
				}
				selected={allSelected}
				summary={getSelectionSummary(
					allPreviewPortletDataHandlers,
					sectionSelection
				)}
				tags={
					<SectionTags
						additionCount={section.additionCount}
						deletionCount={
							showDeletions ? section.deletionCount : undefined
						}
					/>
				}
			>
				<div className="content-section-controls overflow-auto">
					{allPreviewPortletDataHandlers.map((context) => (
						<PortletDataControl
							control={context}
							key={context.name}
							onChange={(controlValue) =>
								onChange(
									updateSelection(
										sectionSelection,
										context.name,
										controlValue
									)
								)
							}
							pageTreeModalConfiguration={
								pageTreeModalConfiguration
							}
							showDeletions={showDeletions}
							topLevel
							value={sectionSelection[context.name]}
						/>
					))}
				</div>

				{sectionFooters.map((sectionFooter) => (
					<SectionFooter
						fields={sectionFooter.fields}
						key={sectionFooter.name}
						name={sectionFooter.name}
						onChange={(sectionFooterValue) =>
							onChange(
								updateSelection(
									sectionSelection,
									sectionFooter.name,
									sectionFooterValue
								)
							)
						}
						subtitle={sectionFooter.subtitle}
						title={sectionFooter.title}
						value={sectionSelection[sectionFooter.name]}
					/>
				))}
			</CollapsibleGroup>
		</ClayLayout.Sheet>
	);
}
