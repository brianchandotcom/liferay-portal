/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import {sub} from 'frontend-js-web';
import React from 'react';

const RESULTS_DATA: ResultsData = {
	'imported': {
		cssClass: 'text-success',
		icon: 'check-circle-full',
		titles: {
			plural: Liferay.Language.get('x-items-were-imported'),
			singular: Liferay.Language.get('x-item-was-imported'),
		},
	},
	'imported-draft': {
		cssClass: 'text-warning',
		icon: 'warning-full',
		titles: {
			plural: Liferay.Language.get('x-items-were-imported-with-warnings'),
			singular: Liferay.Language.get('x-item-was-imported-with-warnings'),
		},
	},
	'invalid': {
		cssClass: 'text-danger',
		icon: 'exclamation-full',
		titles: {
			plural: Liferay.Language.get('x-items-could-not-be-imported'),
			singular: Liferay.Language.get('x-item-could-not-be-imported'),
		},
	},
};

interface Result {
	message: string;
	name: string;
	type: 'fragment' | 'composition';
}

export interface Results {
	'imported': Result[];
	'imported-draft': Result[];
	'invalid': Result[];
}

interface ResultsDataEntry {
	cssClass: string;
	icon: string;
	titles: {
		plural: string;
		singular: string;
	};
}

interface ResultsData {
	'imported': ResultsDataEntry;
	'imported-draft': ResultsDataEntry;
	'invalid': ResultsDataEntry;
}

interface ResultsProps {
	fileName: string | null;
	importResults: Results;
}

function ImportResults({fileName, importResults}: ResultsProps) {
	return (
		<>
			<ImportResultsSection
				data={RESULTS_DATA.imported}
				fileName={fileName}
				panelProps={{
					collapsable: true,
					collapseHeaderClassNames: 'c-p-0',
					defaultExpanded:
						!importResults['imported-draft'] &&
						!importResults.invalid,
				}}
				results={importResults.imported}
			/>

			<ImportResultsSection
				data={RESULTS_DATA['imported-draft']}
				fileName={fileName}
				results={importResults['imported-draft']}
			/>

			<ImportResultsSection
				data={RESULTS_DATA.invalid}
				defaultMessage={Liferay.Language.get(
					'the-definition-of-the-item-is-invalid'
				)}
				fileName={fileName}
				results={importResults.invalid}
			/>
		</>
	);
}

interface SectionProps {
	data: ResultsDataEntry;
	defaultMessage?: string;
	fileName: string | null;
	panelProps?: object;
	results: Result[];
}

function ImportResultsSection({
	data,
	defaultMessage,
	fileName,
	panelProps,
	results,
}: SectionProps) {
	if (!results) {
		return null;
	}

	const {cssClass, icon, titles} = data;
	const title = results.length === 1 ? titles.singular : titles.plural;

	return (
		<ClayLayout.Sheet size="lg">
			<ClayPanel
				{...panelProps}
				displayTitle={
					<ClayPanel.Title>
						<ClayIcon
							className={`text-4 ${cssClass}`}
							symbol={icon}
						/>

						<span
							className={`c-ml-3 font-weight-semi-bold text-4 ${cssClass}`}
						>
							{sub(title, results.length)}
						</span>
					</ClayPanel.Title>
				}
			>
				<ClayPanel.Body className="c-px-4 sheet-row">
					<ul className="list-group sidebar-list-group">
						{results.map((result, index) => (
							<li
								className="list-group-item list-group-item-flex p-0"
								key={index}
							>
								<ClayLayout.ContentCol expand>
									<div className="list-group-title">
										{result.name}
									</div>

									{result.message ||
										(defaultMessage && (
											<div
												className={`list-group-subtext ${cssClass}`}
											>
												{result.message ||
													defaultMessage}
											</div>
										))}
								</ClayLayout.ContentCol>

								<ClayLayout.ContentCol>
									<div className="list-group-subtitle">
										{fileName}
									</div>
								</ClayLayout.ContentCol>
							</li>
						))}
					</ul>
				</ClayPanel.Body>
			</ClayPanel>
		</ClayLayout.Sheet>
	);
}

export default ImportResults;
