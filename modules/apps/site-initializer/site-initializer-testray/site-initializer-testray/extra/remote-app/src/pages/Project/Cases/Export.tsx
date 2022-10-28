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
import React, {useMemo} from 'react';
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
	testrayCaseRequirementsImpl,
	testrayCaseRest,
} from '../../../services/rest';
import {STORAGE_KEYS} from '../../../util/constants';
import {searchUtil} from '../../../util/search';

type CaseWithRequirement = {
	[key: number]: TestrayRequirement[];
};

type CaseItemsProps = {
	caseWithRequirements: CaseWithRequirement;
	cases: TestrayCase[];
};

const RequirementTable = ({
	requirements,
}: {
	requirements: TestrayRequirement[];
}) => (
	<ClayTable>
		<ClayTable.Head>
			<ClayTable.Row>
				<ClayTable.Cell expanded headingCell>
					{i18n.translate('key')}
				</ClayTable.Cell>

				<ClayTable.Cell headingCell>
					{i18n.translate('link')}
				</ClayTable.Cell>

				<ClayTable.Cell headingCell>
					{i18n.translate('summary')}
				</ClayTable.Cell>
			</ClayTable.Row>
		</ClayTable.Head>

		<ClayTable.Body>
			{requirements.map(
				(requirement: TestrayRequirement, index: number) => (
					<ClayTable.Row key={index}>
						<ClayTable.Cell headingTitle>
							{requirement?.key}
						</ClayTable.Cell>

						<ClayTable.Cell>
							<a
								className="cursor-pointer"
								onClick={() =>
									window.open(requirement?.linkURL, '_blank')
								}
							>
								{requirement?.linkURL}
							</a>
						</ClayTable.Cell>

						<ClayTable.Cell>{requirement?.summary}</ClayTable.Cell>
					</ClayTable.Row>
				)
			)}
		</ClayTable.Body>
	</ClayTable>
);

const CaseItems: React.FC<CaseItemsProps> = ({caseWithRequirements, cases}) => {
	return (
		<div>
			{cases?.map((Case, index) => {
				const requirements = caseWithRequirements[Case?.id];

				return (
					<div className="mt-3" key={index}>
						<Container>
							<h5>{Case.name}</h5>

							<QATable
								items={[
									{
										title: i18n.translate('project-name'),
										value: Case?.project?.name,
									},

									{
										title: i18n.translate('type'),
										value: Case?.caseType?.name,
									},
									{
										title: i18n.translate('piority'),
										value: Case?.priority,
									},
									{
										title: i18n.translate('team'),
										value: Case?.component?.team?.name,
									},
									{
										title: i18n.translate('main-component'),
										value: Case?.component?.name,
									},
									{
										title: i18n.translate('description'),
										value: (
											<MarkdownPreview
												markdown={Case.description}
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
										title: i18n.translate('requirements'),
										value: requirements?.length && (
											<RequirementTable
												requirements={requirements}
											/>
										),
									},
								]}
							/>
						</Container>
					</div>
				);
			})}
		</div>
	);
};

const ExportCaseContainer: React.FC<CaseItemsProps> = ({
	caseWithRequirements,
	cases,
}) => (
	<div>
		{cases.map((Case: TestrayCase) => {
			const requirements = caseWithRequirements[Case?.id];

			return requirements.map((requirement) => (
				<div className="mt-3" key={requirement.key}>
					<Container>
						<h5>{requirement.key}</h5>

						<QATable
							items={[
								{
									title: i18n.translate('link'),
									value: (
										<a
											className="cursor-pointer"
											onClick={() =>
												window.open(
													requirement?.linkURL,
													'_blank'
												)
											}
										>
											{requirement?.linkURL}
										</a>
									),
								},
								{
									title: i18n.translate('team'),
									value: requirement?.component?.team?.name,
								},
								{
									title: i18n.translate('component'),
									value: Case?.component?.name,
								},
								{
									title: i18n.translate('jira-component'),
									value: Case?.component?.team?.name,
								},
								{
									title: i18n.translate('summary'),
									value: requirement?.summary,
								},
								{
									title: i18n.translate('description'),
									value: (
										<MarkdownPreview
											markdown={requirement?.description}
										/>
									),
								},
							]}
						/>
					</Container>
				</div>
			));
		})}
	</div>
);

const Export = () => {
	const {id} = useParams();

	const [caseIds] = useStorage(
		`${STORAGE_KEYS.EXPORT_CASE_IDS}-${id}`,
		[],
		sessionStorage
	);

	const {data: casesData, loading} = useFetch<APIResponse<TestrayCase>>(
		`/cases?filter=${searchUtil.in(
			'id',
			caseIds
		)}&nestedFields=caseType,component,project,team&nestedFieldsDepth=3&pageSize=1000`,
		(response) => testrayCaseRest.transformDataFromList(response)
	);

	const {data: requirementCasesData} = useFetch<
		APIResponse<TestrayRequirementCase>
	>(
		loading
			? null
			: `/requirementscaseses?filter=${searchUtil.in(
					'caseId',
					caseIds
			  )}&nestedFields=case.component,requirement,team&nestedFieldsDepth=3&pageSize=1000`,
		(response) =>
			testrayCaseRequirementsImpl.transformDataFromList(response)
	);

	const cases = casesData?.items || [];

	const casesWithRequirements = useMemo(() => {
		const requirementCases = requirementCasesData?.items || [];
		const casesWithRequirement: CaseWithRequirement = {};

		requirementCases.forEach((requirementCase) => {
			const caseId = requirementCase.case?.id;
			const requirement = requirementCase?.requirement;

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
	}, [requirementCasesData]);

	if (!caseIds?.length) {
		return <EmptyState />;
	}

	return (
		<div className="export-case-container p-3">
			<div>
				<h5>{i18n.translate('case')}</h5>

				<h5 className="mt-5">
					{i18n.translate('associated-requirements')}
				</h5>

				<CaseItems
					caseWithRequirements={casesWithRequirements}
					cases={cases}
				/>

				{!!cases.length && (
					<ExportCaseContainer
						caseWithRequirements={casesWithRequirements}
						cases={cases}
					/>
				)}
			</div>
		</div>
	);
};

export default Export;
