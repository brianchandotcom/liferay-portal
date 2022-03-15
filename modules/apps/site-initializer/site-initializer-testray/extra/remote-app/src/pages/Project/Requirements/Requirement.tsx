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

import {useQuery} from '@apollo/client';
import ClayIcon from '@clayui/icon';
import {useEffect} from 'react';
import {useParams} from 'react-router-dom';

import Container from '../../../components/Layout/Container';
import ListView from '../../../components/ListView/ListView';
import Loading from '../../../components/Loading';
import MarkdownPreview from '../../../components/Markdown';
import QATable from '../../../components/Table/QATable';
import {
	TestrayRequirement,
	getCases,
	getRequirement,
} from '../../../graphql/queries';
import useHeader from '../../../hooks/useHeader';
import i18n from '../../../i18n';
import {DescriptionType} from '../../../types';

const Requirement = () => {
	const {requirementId} = useParams();

	const {setHeading, setTabs} = useHeader({shouldUpdate: false});

	const {data, loading} = useQuery<{requirement: TestrayRequirement}>(
		getRequirement,
		{
			variables: {
				testrayRequirementId: requirementId,
			},
		}
	);

	const testrayRequirement = data?.requirement;

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

	if (loading) {
		return <Loading />;
	}

	if (!testrayRequirement) {
		return null;
	}

	return (
		<>
			<Container title="Details">
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

			<Container className="mt-3" title="Cases">
				<ListView
					query={getCases}
					tableProps={{
						columns: [
							{
								key: 'priority',
								value: i18n.translate('priority'),
							},
							{key: 'name', value: i18n.translate('case-name')},
							{
								key: 'component',
								value: i18n.translate('component'),
							},
						],
					}}
					transformData={(data) => data?.cases}
				/>
			</Container>
		</>
	);
};

export default Requirement;
