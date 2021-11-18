import classNames from 'classnames';
import {useState} from 'react';
import useGraphQL from '~/common/hooks/useGraphQL';
import {LiferayTheme} from '~/common/services/liferay';
import {getKoroneikiAccountsByFilter} from '~/common/services/liferay/graphql/koroneiki-accounts';
import {PARAMS_KEYS} from '~/common/services/liferay/search-params';
import Banner from '../../components/Banner';
import ProjectCard from '../../components/ProjectCard';
import SearchProject from '../../components/SearchProject';
import {status} from '../../utils/constants';
import HomeSkeleton from './Skeleton';

const PROJECT_THRESHOLD_COUNT = 4;
const liferaySiteName = LiferayTheme.getLiferaySiteName();

const openPage = (project) => {
	window.location.href = `${liferaySiteName}/overview?${PARAMS_KEYS.PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE}=${project.externalReferenceCode}`;
};

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

	const {data: koroneikiAccountsData, isLoading: isLoadingKoroneiki} =
		useGraphQL([
			getKoroneikiAccountsByFilter({
				accountKeys: userAccount.accountBriefs.map(
					({externalReferenceCode}) => externalReferenceCode
				),
			}),
		]) || [];

	const projects =
		koroneikiAccountsData?.koroneikiAccounts.map(
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
				).name,
			})
		) || [];

	const withManyProjects = projects.length > PROJECT_THRESHOLD_COUNT;
	const projectsFiltered = projects.filter((project) =>
		keyword
			? project.title.toLowerCase().includes(keyword.toLowerCase())
			: true
	);

	return (
		<>
			<div
				className={classNames('mb-5 mt-5', {
					'pb-2': withManyProjects,
				})}
			>
				<Banner userName={userAccount.name} />
			</div>
			<div
				className={classNames('mx-auto', {
					'col-5': withManyProjects,
					'col-8 pl-6': !withManyProjects,
				})}
			>
				<div className="d-flex flex-column">
					{withManyProjects && (
						<div className="align-items-center d-flex justify-content-between mb-4 mr-5">
							<SearchProject
								onChange={setKeyword}
								value={keyword}
							/>

							<h5 className="m-0 text-neutral-3">
								{projects.length} projects
							</h5>
						</div>
					)}

					{isLoadingKoroneiki ? (
						<div className="d-flex flex-wrap home-projects">
							<ProjectCard.Skeleton />

							<ProjectCard.Skeleton />
						</div>
					) : (
						<div
							className={classNames('d-flex flex-wrap', {
								'home-projects': !withManyProjects,
								'home-projects-sm pt-2': withManyProjects,
							})}
						>
							{projectsFiltered.map((project, index) => (
								<ProjectCard
									key={index}
									onClick={() => openPage(project)}
									small={withManyProjects}
									{...project}
								/>
							))}
						</div>
					)}
				</div>
			</div>
		</>
	);
};

Home.Skeleton = HomeSkeleton;

export default Home;
