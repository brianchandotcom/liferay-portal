/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import React from 'react';

import {PageTreeModalConfiguration} from '../../../pages/export/components/PageTreeModal';
import {PreviewPortletDataHandlerSection} from '../../../types/portletDataHandler';
import {
	updateSelection,
	withSiteBuilderSection,
} from '../../../utils/contentSelection';
import ContentSection, {SectionSelection} from './ContentSection';

export type ContentSelection = Record<string, SectionSelection>;

interface ContentSelectorProps {
	'aria-labelledby'?: string;
	'commentsAndRatingsEnabled'?: boolean;
	'commentsAndRatingsSubtitle'?: string;
	'errorMessage'?: string;
	'lookAndFeelEnabled'?: boolean;
	'name': string;
	'onChange': (value: ContentSelection | undefined) => void;
	'pageTreeModalConfiguration'?: PageTreeModalConfiguration;
	'sections': PreviewPortletDataHandlerSection[];
	'showDeletions'?: boolean;
	'value': ContentSelection | undefined;
}

export default function ContentSelector({
	'aria-labelledby': ariaLabelledby,
	commentsAndRatingsEnabled = false,
	commentsAndRatingsSubtitle,
	errorMessage,
	lookAndFeelEnabled = false,
	name,
	onChange,
	pageTreeModalConfiguration,
	sections,
	showDeletions,
	value,
}: ContentSelectorProps) {
	const currentValue = value || {};
	const errorId = errorMessage ? `${name}-error-message` : undefined;

	const visibleSections = sections.filter(
		(section) =>
			showDeletions || !!section.additionCount || !section.deletionCount
	);

	const renderedSections = lookAndFeelEnabled
		? withSiteBuilderSection(
				visibleSections,
				Liferay.Language.get('category.site_administration.build')
			)
		: visibleSections;

	return (
		<div
			aria-describedby={errorId}
			aria-invalid={errorMessage ? true : undefined}
			aria-labelledby={ariaLabelledby}
			className="c-gap-4 d-flex flex-column mt-4"
			role="group"
		>
			{renderedSections.map(
				(section: PreviewPortletDataHandlerSection) => (
					<ContentSection
						commentsAndRatingsEnabled={commentsAndRatingsEnabled}
						commentsAndRatingsSubtitle={commentsAndRatingsSubtitle}
						key={section.name}
						lookAndFeelEnabled={lookAndFeelEnabled}
						onChange={(sectionValue) =>
							onChange(
								updateSelection(
									currentValue,
									section.name,
									sectionValue
								)
							)
						}
						pageTreeModalConfiguration={pageTreeModalConfiguration}
						section={section}
						showDeletions={showDeletions}
						value={currentValue[section.name]}
					/>
				)
			)}

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
