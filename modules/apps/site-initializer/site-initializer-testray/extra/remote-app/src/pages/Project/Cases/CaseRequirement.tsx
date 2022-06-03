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
import {useOutletContext} from 'react-router-dom';

import Container from '../../../components/Layout/Container';
import ListView from '../../../components/ListView/ListView';
import {
	TestrayRequirementCase,
	TestraySuite,
	getRequirementCases,
} from '../../../graphql/queries';
import i18n from '../../../i18n';
import {searchUtil} from '../../../util/search';

const CaseRequirement = () => {
	const {
		projectId,
		testrayCase,
	}: {projectId: number; testrayCase: TestraySuite} = useOutletContext();

	return (
		<Container>
			<ListView
				managementToolbarProps={{title: i18n.translate('requirements')}}
				query={getRequirementCases}
				tableProps={{
					columns: [
						{
							clickable: true,
							key: 'key',
							render: (
								_,
								requirementCase: TestrayRequirementCase
							) => requirementCase.requirement.key,
							value: 'Key',
						},
						{
							key: 'linkTitle',
							render: (
								_,
								requirementCase: TestrayRequirementCase
							) => (
								<a
									href={requirementCase.requirement.linkURL}
									rel="noopener noreferrer"
									target="_blank"
								>
									{requirementCase.requirement.linkTitle}

									<ClayIcon
										className="ml-2"
										symbol="shortcut"
									/>
								</a>
							),
							value: 'Link',
						},
						{
							key: 'team',
							render: (
								_,
								requirementCase: TestrayRequirementCase
							) =>
								requirementCase.requirement.component?.team
									?.name,
							value: i18n.translate('team'),
						},
						{
							key: 'component',
							render: (
								_,
								requirementCase: TestrayRequirementCase
							) => requirementCase.requirement.component?.name,
							value: i18n.translate('component'),
						},
						{
							key: 'components',
							render: (
								_,
								requirementCase: TestrayRequirementCase
							) => requirementCase.requirement.components,
							value: i18n.translate('jira-components'),
						},
						{
							key: 'summary',
							render: (
								_,
								requirementCase: TestrayRequirementCase
							) => requirementCase.requirement.summary,
							value: i18n.translate('summary'),
						},
						{
							key: 'description',
							render: (
								_,
								requirementCase: TestrayRequirementCase
							) => requirementCase.requirement.description,
							value: i18n.translate('description'),
						},
					],
					navigateTo: ({requirement}: TestrayRequirementCase) =>
						`/project/${projectId}/requirements/${requirement.id}`,
				}}
				transformData={(data) => data?.requirementscaseses}
				variables={{
					filter: searchUtil.eq('caseId', testrayCase.id),
				}}
			/>
		</Container>
	);
};

export default CaseRequirement;
