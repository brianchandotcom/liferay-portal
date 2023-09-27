/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {useOutletContext, useParams} from 'react-router-dom';

import Button from '../../../components/Button';
import Container from '../../../components/Layout/Container';
import ListView from '../../../components/ListView';
import MarkdownPreview from '../../../components/Markdown';
import QATable from '../../../components/Table/QATable';
import SearchBuilder from '../../../core/SearchBuilder';
import i18n from '../../../i18n';
import {
	TestrayRequirement,
	TestrayRequirementCase,
	requirementsCasesResource,
	testrayCaseRequirementsImpl,
} from '../../../services/rest';
import {DescriptionType} from '../../../types';
import RequirementCaseLinkModal from './RequirementCaseLinkModal';
import useRequirementCaseActions from './useRequirementCaseActions';

const Requirement = () => {
	const {projectId} = useParams();
	const {
		testrayRequirement,
	}: {testrayRequirement: TestrayRequirement} = useOutletContext();
	const {actions, formModal} = useRequirementCaseActions(testrayRequirement);

	return (
		<>
			<Container collapsable title={i18n.translate('details')}>
				<QATable
					items={[
						{
							title: 'key',
							value: testrayRequirement.key,
						},
						{
							title: 'link',
							value: (
								<a
									href={testrayRequirement.linkURL}
									rel="noopener noreferrer"
									target="_blank"
								>
									{testrayRequirement.linkTitle}

									<ClayIcon
										className="ml-2"
										symbol="shortcut"
									/>
								</a>
							),
						},
						{
							title: 'team',
							value: testrayRequirement.component?.team?.name,
						},
						{
							title: i18n.translate('component'),
							value: testrayRequirement.component?.name,
						},
						{
							title: i18n.translate('jira-components'),
							value: testrayRequirement.components,
						},
						{
							title: i18n.translate('summary'),
							value: testrayRequirement.summary,
						},
						{
							title: i18n.translate('description'),
							value: (
								<>
									{testrayRequirement.descriptionType ===
									(DescriptionType.MARKDOWN as any) ? (
										<MarkdownPreview
											markdown={
												testrayRequirement.description
											}
										/>
									) : (
										testrayRequirement.description
									)}
								</>
							),
						},
					]}
				/>
			</Container>

			<Container className="mt-3">
				<ListView
					forceRefetch={formModal.forceRefetch}
					managementToolbarProps={{
						buttons: (
							<ClayManagementToolbar.Item>
								<Button
									displayType="secondary"
									onClick={() => formModal.open()}
									symbol="list-ul"
								>
									{i18n.translate('link-cases')}
								</Button>
							</ClayManagementToolbar.Item>
						),
						filterSchema: 'requirementCases',
						title: i18n.translate('cases'),
					}}
					resource={requirementsCasesResource}
					tableProps={{
						actions,
						columns: [
							{
								clickable: true,
								key: 'priority',
								render: (
									_,
									requirementCase: TestrayRequirementCase
								) => requirementCase?.case?.priority,
								value: i18n.translate('priority'),
							},
							{
								clickable: true,
								key: 'name',
								render: (
									_,
									requirementCase: TestrayRequirementCase
								) => requirementCase?.case?.name,
								value: i18n.translate('case-name'),
							},
							{
								clickable: true,
								key: 'component',
								render: (
									_,
									requirementCase: TestrayRequirementCase
								) => requirementCase?.case?.component?.name,
								value: i18n.translate('component'),
							},
						],
						navigateTo: ({case: Case}: TestrayRequirementCase) =>
							`/project/${projectId}/cases/${Case?.id}`,
					}}
					transformData={(response) =>
						testrayCaseRequirementsImpl.transformDataFromList(
							response
						)
					}
					variables={{
						filter: SearchBuilder.eq(
							'requirementId',
							testrayRequirement.id
						),
					}}
				>
					{({items}) => (
						<RequirementCaseLinkModal
							items={items}
							modal={formModal}
							projectId={projectId as string}
						/>
					)}
				</ListView>
			</Container>
		</>
	);
};

export default Requirement;
