/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import classNames from 'classnames';
import {useEffect, useState} from 'react';
import client from '../../../../apolloClient';
import {
	getAccounts,
	getKoroneikiAccounts,
} from '../../../../common/services/liferay/graphql/queries';
import {PAGE_ROUTER_TYPES} from '../../../../common/utils/constants';
import ProjectCard from '../../components/ProjectCard';
import SearchProject from '../../components/SearchProject';
import {STATUS_TAG_TYPES} from '../../utils/constants';
import HomeSkeleton from './Skeleton';

const PROJECT_THRESHOLD_COUNT = 4;

const MAX_PAGE_SIZE = 10000;

const getStatus = (slaCurrent, slaFuture) => {
	if (slaCurrent) {
		return STATUS_TAG_TYPES.active;
	}

	if (slaFuture) {
		return STATUS_TAG_TYPES.future;
	}

	return STATUS_TAG_TYPES.expired;
};

const getKoroneikiFilter = (accounts) => {
	return accounts?.reduce(
		(
			koroneikiFilterAccumulator,
			{externalReferenceCode},
			index,
			{length: totalAccounts}
		) =>
			`${koroneikiFilterAccumulator}accountKey eq '${externalReferenceCode}'${
				index + 1 < totalAccounts ? ' or ' : ''
			}`,
		''
	);
};

const Home = ({userAccount}) => {
	const [keyword, setKeyword] = useState('');
	const [projects, setProjects] = useState([]);
	const [isLoading, setIsLoading] = useState(true);

	useEffect(() => {
		const getProjects = async (userAccount) => {
			const hasRoleBriefAdministrator = userAccount?.roleBriefs?.some(
				(role) => role.name === 'Administrator'
			);

			let accountKeysFilter;
			let accounts = [];

			if (hasRoleBriefAdministrator) {
				const {data: dataAccounts} = await client.query({
					query: getAccounts,
					variables: {
						pageSize: MAX_PAGE_SIZE,
					},
				});

				if (dataAccounts) {
					accounts = dataAccounts?.accounts?.items;
					accountKeysFilter = '';
				}
			}
			else if (userAccount?.accountBriefs?.length) {
				accounts = userAccount?.accountBriefs;
				accountKeysFilter = getKoroneikiFilter(accounts);
			}

			if (accounts.length) {
				const {data} = await client.query({
					query: getKoroneikiAccounts,
					variables: {
						filter: accountKeysFilter,
						pageSize: MAX_PAGE_SIZE,
					},
				});

				if (data) {
					setProjects(
						data.c?.koroneikiAccounts?.items.map(
							({
								accountKey,
								code,
								liferayContactEmailAddress,
								liferayContactName,
								liferayContactRole,
								region,
								slaCurrent,
								slaCurrentEndDate,
								slaFuture,
							}) => ({
								accountKey,
								code,
								contact: {
									emailAddress: liferayContactEmailAddress,
									name: liferayContactName,
									role: liferayContactRole,
								},
								region,
								sla: {
									current: slaCurrent,
									currentEndDate: slaCurrentEndDate,
									future: slaFuture,
								},
								status: getStatus(slaCurrent, slaFuture),
								title: accounts.find(
									({externalReferenceCode}) =>
										externalReferenceCode === accountKey
								)?.name,
							})
						) || []
					);
				}

				setIsLoading(false);
			}
		};

		getProjects(userAccount);
	}, [userAccount]);

	const nextPage = (project) => {
		window.location.href = PAGE_ROUTER_TYPES.project(project.accountKey);
	};

	const projectsFiltered = projects.filter((project) =>
		keyword
			? project.title.toLowerCase().includes(keyword.toLowerCase())
			: true
	);

	const withManyProjects = projects.length > PROJECT_THRESHOLD_COUNT;

	return (
		<div
			className={classNames({
				'cp-project-cards-container': !withManyProjects,
				'mx-auto cp-project-cards-container-sm': withManyProjects,
			})}
		>
			<div
				className={classNames({
					'd-flex flex-column w-100': withManyProjects,
					'ml-3': !withManyProjects,
				})}
			>
				{withManyProjects && (
					<div className="align-items-center d-flex justify-content-between mb-4">
						<SearchProject onChange={setKeyword} value={keyword} />

						<h5 className="m-0 text-neutral-7">
							{keyword
								? `${projectsFiltered.length} result${
										projectsFiltered.length === 1 ? '' : 's'
								  }`
								: `${projects.length} project${
										projects.length === 1 ? '' : 's'
								  }`}
						</h5>
					</div>
				)}

				{!isLoading ? (
					<div
						className={classNames('d-flex flex-wrap', {
							'cp-home-projects px-5': !withManyProjects,
							'cp-home-projects-sm pt-2': withManyProjects,
						})}
					>
						{projectsFiltered.length ? (
							projectsFiltered.map((project, index) => (
								<ProjectCard
									isSmall={withManyProjects}
									key={index}
									onClick={() => nextPage(project)}
									{...project}
								/>
							))
						) : (
							<p className="mx-auto">
								No projects match these criteria.
							</p>
						)}
					</div>
				) : (
					<div className="d-flex flex-wrap home-projects">
						<ProjectCard.Skeleton />

						<ProjectCard.Skeleton />
					</div>
				)}
			</div>
		</div>
	);
};

Home.Skeleton = HomeSkeleton;

export default Home;
