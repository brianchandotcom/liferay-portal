/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

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

interface ResultsProps {
	fileName: string | null;
	importResults: Results;
}

function ImportResults({fileName, importResults}: ResultsProps) {
	const [expanded, setExpanded] = useState(
		!importResults['imported-draft'] && !importResults.invalid
	);

	return (
		<>
			{importResults.imported && (
				<ClayLayout.Sheet size="lg">
					<ClayPanel
						className="sheet-row"
						collapsable
						collapseHeaderClassNames="c-py-0"
						displayTitle={
							<ClayPanel.Title>
								<ClayIcon
									className="text-4 text-success"
									symbol="check-circle-full"
								/>

								<span className="c-ml-3 font-weight-semi-bold text-4 text-success">
									{sub(
										importResults.imported.length === 1
											? Liferay.Language.get(
													'x-item-was-imported'
											  )
											: Liferay.Language.get(
													'x-items-were-imported'
											  ),
										importResults.imported.length
									)}
								</span>
							</ClayPanel.Title>
						}
						expanded={expanded}
						onExpandedChange={() => {
							setExpanded(!expanded);
						}}
						showCollapseIcon
					>
						<ClayPanel.Body>
							<ul className="list-group sidebar-list-group">
								{importResults.imported.map((result, index) => (
									<li
										className="list-group-item list-group-item-flex p-0"
										key={index}
									>
										<ClayLayout.ContentCol expand>
											<div className="list-group-title">
												{result.name}
											</div>
										</ClayLayout.ContentCol>
									</li>
								))}
							</ul>
						</ClayPanel.Body>
					</ClayPanel>
				</ClayLayout.Sheet>
			)}

			{importResults['imported-draft'] && (
				<ClayLayout.Sheet size="lg">
					<ClayPanel
						displayTitle={
							<ClayPanel.Title>
								<ClayIcon
									className="text-4 text-warning"
									symbol="warning-full"
								/>

								<span className="c-ml-3 font-weight-semi-bold text-4 text-warning">
									{sub(
										importResults['imported-draft']
											.length === 1
											? Liferay.Language.get(
													'x-item-was-imported-with-warnings'
											  )
											: Liferay.Language.get(
													'x-items-were-imported-with-warnings'
											  ),
										importResults['imported-draft'].length
									)}
								</span>
							</ClayPanel.Title>
						}
					>
						<ClayPanel.Body className="c-px-0">
							<ul className="list-group sidebar-list-group">
								{importResults['imported-draft'].map(
									(result, index) => (
										<li
											className="list-group-item list-group-item-flex p-0"
											key={index}
										>
											<ClayLayout.ContentCol expand>
												<div className="list-group-title">
													{result.name}
												</div>

												<div className="list-group-subtext text-warning">
													{result.message}
												</div>
											</ClayLayout.ContentCol>

											<ClayLayout.ContentCol>
												<div className="list-group-subtitle">
													{fileName}
												</div>
											</ClayLayout.ContentCol>
										</li>
									)
								)}
							</ul>
						</ClayPanel.Body>
					</ClayPanel>
				</ClayLayout.Sheet>
			)}

			{importResults.invalid && (
				<ClayLayout.Sheet size="lg">
					<ClayPanel
						displayTitle={
							<ClayPanel.Title>
								<ClayIcon
									className="text-4 text-danger"
									symbol="exclamation-full"
								/>

								<span className="c-ml-3 font-weight-semi-bold text-4 text-danger">
									{sub(
										importResults.invalid.length === 1
											? Liferay.Language.get(
													'x-item-could-not-be-imported'
											  )
											: Liferay.Language.get(
													'x-items-could-not-be-imported'
											  ),
										importResults.invalid.length
									)}
								</span>
							</ClayPanel.Title>
						}
					>
						<ClayPanel.Body className="c-px-0">
							<ul className="list-group sidebar-list-group">
								{importResults.invalid.map((result, index) => (
									<li
										className="list-group-item list-group-item-flex p-0"
										key={index}
									>
										<ClayLayout.ContentCol expand>
											<div className="list-group-title">
												{result.name}
											</div>

											<div className="list-group-subtext text-danger">
												{result.message
													? result.message
													: Liferay.Language.get(
															'the-definition-of-the-item-is-invalid'
													  )}
											</div>
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
			)}
		</>
	);
}

export default ImportResults;
