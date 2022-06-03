/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayIcon from '@clayui/icon';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {useEffect} from 'react';
import {useOutletContext, useParams} from 'react-router-dom';

import Button from '../../../components/Button';
import Container from '../../../components/Layout/Container';
import ListView from '../../../components/ListView/ListView';
import MarkdownPreview from '../../../components/Markdown';
import QATable from '../../../components/Table/QATable';
import {
	TestrayRequirement,
	TestrayRequirementCase,
	getRequirementCases,
} from '../../../graphql/queries';
import useHeader from '../../../hooks/useHeader';
import i18n from '../../../i18n';
import {DescriptionType} from '../../../types';
import {searchUtil} from '../../../util/search';

const Requirement = () => {
	const {projectId} = useParams();
	const testrayRequirement: TestrayRequirement = useOutletContext();

	const {setHeading, setTabs} = useHeader({shouldUpdate: false});

	useEffect(() => {
		if (testrayRequirement) {
			setTimeout(() => {
				setHeading([{title: testrayRequirement.key}], true);
			}, 0);
		}
	}, [setHeading, testrayRequirement]);

	useEffect(() => {
		setTabs([]);
	}, [setTabs]);

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
					managementToolbarProps={{
						buttons: (
							<ClayManagementToolbar.Item>
								<Button
									displayType="secondary"
									symbol="list-ul"
								>
									{i18n.translate('link-cases')}
								</Button>
							</ClayManagementToolbar.Item>
						),
						title: i18n.translate('cases'),
					}}
					query={getRequirementCases}
					tableProps={{
						columns: [
							{
								clickable: true,
								key: 'priority',
								render: (
									_,
									requirementCase: TestrayRequirementCase
								) => requirementCase.case.priority,
								value: i18n.translate('priority'),
							},
							{
								clickable: true,
								key: 'name',
								render: (
									_,
									requirementCase: TestrayRequirementCase
								) => requirementCase.case.name,
								value: i18n.translate('case-name'),
							},
							{
								clickable: true,
								key: 'component',
								render: (
									_,
									requirementCase: TestrayRequirementCase
								) => requirementCase.case.component?.name,
								value: i18n.translate('component'),
							},
						],
						navigateTo: ({case: Case}: TestrayRequirementCase) =>
							`/project/${projectId}/cases/${Case.id}`,
					}}
					transformData={(data) => data?.requirementscaseses}
					variables={{
						filter: searchUtil.eq(
							'requirementId',
							testrayRequirement.id
						),
					}}
				/>
			</Container>
		</>
	);
};

export default Requirement;
