/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import lang from '../../../../util/lang';
import toLocalDateTimeFormatted from '../../../util/toLocalDateTimeFormatted';

import './DetailsTab.scss';

interface DetailsTabProps {
	definitionInfo: DefinitionInfo;
	timeZoneId: string;
}

export function DetailsTab({definitionInfo, timeZoneId}: DetailsTabProps) {
	const totalModifications = definitionInfo.totalModifications;

	const revisionMessage =
		Number(totalModifications) > 1
			? lang.sub(Liferay.Language.get('x-revisions'), [
					totalModifications,
				])
			: `${totalModifications} ${Liferay.Language.get('revision')}`;

	const language = Liferay.ThemeDisplay.getBCP47LanguageId();

	const workflowDefinitionDetails = [
		{
			title: Liferay.Language.get('created'),
			value: toLocalDateTimeFormatted(
				definitionInfo.dateCreated,
				language,
				timeZoneId
			),
		},
		{
			title: Liferay.Language.get('last-modified'),
			value: toLocalDateTimeFormatted(
				definitionInfo.dateModified,
				language,
				timeZoneId
			),
		},
		{
			title: Liferay.Language.get('total-modifications'),
			value: revisionMessage,
		},
	];

	return (
		<div className="lfr-workflow__details-tab-container">
			{workflowDefinitionDetails.map(({title, value}) => (
				<div
					className="lfr-workflow__details-tab-info-container"
					key={title}
				>
					<label className="lfr-workflow__details-tab-info-title">
						{title.toUpperCase()}
					</label>

					<span className="lfr-workflow__details-tab-info-details">
						{value}
					</span>
				</div>
			))}
		</div>
	);
}
