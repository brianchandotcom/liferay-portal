import {useQuery} from '@apollo/client';
import classNames from 'classnames';
import {useState} from 'react';
import {LiferayTheme} from '../../../../common/services/liferay';
import {getKoroneikiAccounts} from '../../../../common/services/liferay/graphql/queries';
import {PARAMS_KEYS} from '../../../../common/services/liferay/search-params';
import ProjectCard from '../../components/ProjectCard';
import SearchProject from '../../components/SearchProject';
import {status} from '../../utils/constants';
import HomeSkeleton from './Skeleton';

const PROJECT_THRESHOLD_COUNT = 4;
const liferaySiteName = LiferayTheme.getLiferaySiteName();

const getStatus = (slaCurrent, slaFuture) => {
	if (slaCurrent) {
		return status.active;
	}

	if (slaFuture) {
		return status.future;
	}

	return status.expired;
};

const Home = ({userAccount}) => {
	const [keyword, setKeyword] = useState('');

	const {data, loading} = useQuery(getKoroneikiAccounts, {
		variables: {
			filter: userAccount.accountBriefs
				.map(
					(
						{externalReferenceCode},
						index,
						{length: totalAccountBriefs}
					) =>
						`accountKey eq '${externalReferenceCode}' ${
							index + 1 < totalAccountBriefs ? ' or ' : ' '
						}`
				)
				.join(' '),
		},
	});

	const koroneikiAccountsData = data?.c?.koroneikiAccounts?.items || [];

	const nextPage = (project) => {
		window.location.href = `${window.location.origin}/${liferaySiteName}/overview?${PARAMS_KEYS.PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE}=${project.accountKey}`;
	};

	const projects =
		koroneikiAccountsData.map(
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
				title: userAccount.accountBriefs.find(
					({externalReferenceCode}) =>
						externalReferenceCode === accountKey
				)?.name,
			})
		) || [];

	const projectsFiltered = projects.filter((project) =>
		keyword
			? project.title.toLowerCase().includes(keyword.toLowerCase())
			: true
	);

	const withManyProjects = projects.length > PROJECT_THRESHOLD_COUNT;

	return (
		<div
			className={classNames({
				'mx-auto project-cards-container-sm': withManyProjects,
				'pl-5 project-cards-container': !withManyProjects,
			})}
		>
			<div
				className={classNames('d-flex flex-column w-100', {
					'ml-3': !withManyProjects,
				})}
			>
				{withManyProjects && (
					<div className="align-items-center d-flex justify-content-between mb-4">
						<SearchProject onChange={setKeyword} value={keyword} />

						<h5 className="m-0 text-neutral-7">
							{projects.length} projects
						</h5>
					</div>
				)}

				{!loading ? (
					<div
						className={classNames('d-flex flex-wrap', {
							'home-projects': !withManyProjects,
							'home-projects-sm pt-2': withManyProjects,
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
