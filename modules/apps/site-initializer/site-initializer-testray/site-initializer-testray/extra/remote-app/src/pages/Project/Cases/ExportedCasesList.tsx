/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the<QA hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayTable from '@clayui/table';
import {useMemo} from 'react';
import {useParams} from 'react-router-dom';

import EmptyState from '../../../components/EmptyState';
import Container from '../../../components/Layout/Container';
import MarkdownPreview from '../../../components/Markdown';
import QATable from '../../../components/Table/QATable';
import {useFetch} from '../../../hooks/useFetch';
import useStorage from '../../../hooks/useStorage';
import i18n from '../../../i18n';
import {
	APIResponse,
	TestrayCase,
	TestrayRequirement,
	TestrayRequirementCase,
	testrayCaseRequirementsRest,
	testrayCaseRest,
} from '../../../services/rest';
import {searchUtil} from '../../../util/search';

const ExportedCases: React.FC = () => {
	const {exportId} = useParams();

	const [caseIds] = useStorage(`caseIds-${exportId}`, [], sessionStorage);

	const {data: cases, loading} = useFetch<APIResponse<TestrayCase>>(
		`/cases?filter=${searchUtil.in(
			'id',
			caseIds
		)}&nestedFields=project,caseType,component,team&nestedFieldsDepth=3&pageSize=1000`,
		(response) => testrayCaseRest.transformDataFromList(response)
	);

	const {data: requirements} = useFetch<APIResponse<TestrayRequirementCase>>(
		loading
			? null
			: `/requirementscaseses?filter=${searchUtil.in(
					'caseId',
					caseIds
			  )}&nestedFields=requirement,case&pageSize=1000`,
		(response) =>
			testrayCaseRequirementsRest.transformDataFromList(response)
	);

	const caseItems = cases?.items || [];

	const requirementItems = useMemo(() => {
		const requirementsCases = requirements?.items || [];
		const casesWithRequirement: {
			[key: number]: TestrayRequirement[];
		} = {};

		requirementsCases.forEach((requirementsCase) => {
			const caseId = requirementsCase.case?.id;
			const requirement = requirementsCase?.requirement;

			if (!caseId || !requirement) {
				return;
			}

			if (requirement && casesWithRequirement[caseId]) {
				casesWithRequirement[caseId].push(requirement);
			} else {
				casesWithRequirement[caseId] = [requirement];
			}
		});

		return casesWithRequirement;
	}, [requirements]);

	if (!caseIds?.length) {
		return <EmptyState />;
	}

	return (
		<div className="p-3">
			{caseItems?.length &&
				caseItems?.map((Case, index) => {
					const _requirements = requirementItems[Case?.id];

					return (
						<>
							<div className="mt-3" key={index}>
								<Container>
									<h5>{Case.name}</h5>

									<QATable
										items={[
											{
												title: i18n.translate(
													'project-name'
												),
												value: Case?.project?.name,
											},

											{
												title: i18n.translate('type'),
												value: Case?.caseType?.name,
											},
											{
												title: i18n.translate(
													'piority'
												),
												value: Case?.priority,
											},
											{
												title: i18n.translate('team'),
												value:
													Case?.component?.team?.name,
											},
											{
												title: i18n.translate(
													'main-component'
												),
												value: Case?.component?.name,
											},
											{
												title: i18n.translate(
													'description'
												),
												value: (
													<MarkdownPreview
														markdown={
															Case.description
														}
													/>
												),
											},
											{
												title: i18n.translate(
													'estimated-duration'
												),
												value: Case?.estimatedDuration,
											},
											{
												title: i18n.translate('steps'),
												value: Case?.steps,
											},
											{
												title: i18n.translate(
													'data-last-modified'
												),
												value: Case?.dateModified,
											},
											{
												title: i18n.translate(
													'all-issue-found'
												),
												value: Case?.name,
											},
											{
												title: i18n.translate(
													'requirements'
												),
												value: _requirements?.length && (
													<>
														<ClayTable>
															<ClayTable.Head>
																<ClayTable.Row>
																	<ClayTable.Cell
																		expanded
																		headingCell
																	>
																		{i18n.translate(
																			'key'
																		)}
																	</ClayTable.Cell>

																	<ClayTable.Cell
																		headingCell
																	>
																		{i18n.translate(
																			'link'
																		)}
																	</ClayTable.Cell>

																	<ClayTable.Cell
																		headingCell
																	>
																		{i18n.translate(
																			'summary'
																		)}
																	</ClayTable.Cell>
																</ClayTable.Row>
															</ClayTable.Head>

															<ClayTable.Body>
																{_requirements.map(
																	(
																		items: TestrayRequirement,
																		index: number
																	) => {
																		return (
																			<ClayTable.Row
																				key={
																					index
																				}
																			>
																				<ClayTable.Cell
																					headingTitle
																				>
																					{
																						items?.key
																					}
																				</ClayTable.Cell>

																				<ClayTable.Cell>
																					<a
																						className="cursor-pointer"
																						onClick={() =>
																							window.open(
																								items?.linkURL,
																								'_blank'
																							)
																						}
																					>
																						{
																							items?.linkURL
																						}
																					</a>
																				</ClayTable.Cell>

																				<ClayTable.Cell>
																					{
																						items?.summary
																					}
																				</ClayTable.Cell>
																			</ClayTable.Row>
																		);
																	}
																)}
															</ClayTable.Body>
														</ClayTable>
													</>
												),
											},
										]}
									/>
								</Container>
							</div>
						</>
					);
				})}
		</div>
	);
};

export default ExportedCases;
