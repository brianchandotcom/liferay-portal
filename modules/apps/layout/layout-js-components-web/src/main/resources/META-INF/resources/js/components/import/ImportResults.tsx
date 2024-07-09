/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React from 'react';

const RESULTS_DATA = {
	error: {
		cssClass: 'text-danger',
		icon: 'exclamation-full',
		titles: {
			plural: Liferay.Language.get('x-items-could-not-be-imported'),
			singular: Liferay.Language.get('x-item-could-not-be-imported'),
		},
	},
	success: {
		cssClass: 'text-success',
		icon: 'check-circle-full',
		titles: {
			plural: Liferay.Language.get('x-items-were-imported'),
			singular: Liferay.Language.get('x-item-was-imported'),
		},
	},
	warning: {
		cssClass: 'text-warning',
		icon: 'warning-full',
		titles: {
			plural: Liferay.Language.get('x-items-were-imported-with-warnings'),
			singular: Liferay.Language.get('x-item-was-imported-with-warnings'),
		},
	},
};

interface Result {
	messages: string[];
	name: string;
	type: number;
}

export interface Results {
	error: Result[];
	success: Result[];
	warning: Result[];
}

interface ResultsProps {
	importResults: Results;
}

export default function ImportResults({importResults}: ResultsProps) {
	return (
		<>
			<ImportResultsSection
				data={RESULTS_DATA.success}
				panelProps={{
					collapsable: true,
					collapseHeaderClassNames: 'c-p-0',
					defaultExpanded:
						!importResults.warning && !importResults.error,
				}}
				results={importResults.success}
			/>

			<ImportResultsSection
				data={RESULTS_DATA.warning}
				results={importResults.warning}
			/>

			<ImportResultsSection
				data={RESULTS_DATA.error}
				defaultMessage={Liferay.Language.get(
					'the-definition-of-the-item-is-invalid'
				)}
				results={importResults.error}
			/>
		</>
	);
}

interface SectionProps {
	data: (typeof RESULTS_DATA)[keyof typeof RESULTS_DATA];
	defaultMessage?: string;
	panelProps?: object;
	results: Result[];
}

function ImportResultsSection({
	data,
	defaultMessage,
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
							className={classNames('text-4', cssClass)}
							symbol={icon}
						/>

						<span
							className={classNames(
								'c-ml-3 font-weight-semi-bold text-4',
								cssClass
							)}
						>
							{sub(title, results.length)}
						</span>
					</ClayPanel.Title>
				}
			>
				<ClayPanel.Body className="c-px-4 sheet-row">
					<ul className="list-group sidebar-list-group">
						{results.map((result, index) => {
							const messages = result.messages
								? result.messages
								: defaultMessage
									? [defaultMessage]
									: null;

							return (
								<li
									className="list-group-item list-group-item-flex p-0"
									key={index}
								>
									<ClayLayout.ContentCol expand>
										<div className="list-group-title">
											<span className="inline-item inline-item-after">
												<ClayIcon
													className="c-mr-2 text-secondary"
													symbol={
														result.type === 0
															? 'folder'
															: 'page-template'
													}
												/>

												{result.name}
											</span>
										</div>

										{messages &&
											messages.map((message) => (
												<div
													className={classNames(
														'list-group-subtext',
														cssClass
													)}
													key={message}
												>
													{message}
												</div>
											))}
									</ClayLayout.ContentCol>
								</li>
							);
						})}
					</ul>
				</ClayPanel.Body>
			</ClayPanel>
		</ClayLayout.Sheet>
	);
}

export function getResultsText(importResults: Results) {
	let text = '';

	Object.entries(importResults).forEach(([key, results]) => {
		if (results.length) {
			const {titles} = RESULTS_DATA[key as keyof typeof RESULTS_DATA];

			const resultsText = sub(
				results.length === 1 ? titles.singular : titles.plural,
				results.length
			);
			text = text + ` ${resultsText}`;
		}
	});

	return text;
}
