import classNames from 'classnames';
import {useEffect, useState} from 'react';
import useGraphQL from '~/common/hooks/useGraphql';
import {LiferayTheme} from '~/common/services/liferay';
import {getKoroneikiAccountsByFilter} from '~/common/services/liferay/graphql/koroneiki-accounts';
import {getUserAccountById} from '~/common/services/liferay/graphql/user-accounts';
import {STORAGE_KEYS, Storage} from '~/common/services/liferay/storage';
import {REACT_APP_LIFERAY_API} from '~/common/utils';
import Banner from '../components/Banner';
import ProjectCard from '../components/ProjectCard';
import SearchProject from '../components/SearchProject';
import BannerSkeleton from '../components/skeleton/BannerSkeleton';
import ProjectCardSkeleton from '../components/skeleton/ProjectCardSkeleton';
import {status} from '../utils/constants';

const PROJECT_THRESHOLD_COUNT = 4;

const Home = () => {
	const [keyword, setKeyword] = useState('');
	const {data, isLoading: isLoadingUser} = useGraphQL([
		getUserAccountById(LiferayTheme.getUserId()),
	]);

	useEffect(() => {
		if (data) {
			Storage.setItem(
				STORAGE_KEYS.USER_APPLICATION,
				JSON.stringify({
					accountKey: data.userAccount.accountKey,
					image:
						data.userAccount.image &&
						`${REACT_APP_LIFERAY_API}${data.userAccount.image}`,
					name: data.userAccount.name,
				})
			);
		} else {
			Storage.removeItem(STORAGE_KEYS.USER_APPLICATION);
		}
	}, [data]);

	const accountBriefs = data?.userAccount.accountBriefs || [];

	const {data: koroneikiAccountsData, isLoading: isLoadingKoroneiki} =
		useGraphQL([
			getKoroneikiAccountsByFilter({
				accountKeys: accountBriefs.map(
					({externalReferenceCode}) => externalReferenceCode
				),
			}),
		]) || [];

	const getStatus = (slaCurrent, slaExpired, slaFuture) => {
		if (slaCurrent.length) {
			return status.active;
		} else if (slaExpired.length) {
			return status.expired;
		} else if (slaFuture.length) {
			return status.future;
		}
	};

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
				slaExpired,
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
					expired: slaExpired,
					future: slaFuture,
				},
				status: getStatus(slaCurrent, slaExpired, slaFuture),
				title: accountBriefs.find(
					({externalReferenceCode}) =>
						externalReferenceCode === accountKey
				).name,
			})
		) || [];

	const projectsFiltered = projects.filter((project) =>
		keyword
			? project.title.toLowerCase().includes(keyword.toLowerCase())
			: true
	);
	const nextPage = (project) =>
		Storage.setItem(
			STORAGE_KEYS.KORONEIKI_APPLICATION,
			JSON.stringify(project)
		);
	const withManyProjects = projects.length > PROJECT_THRESHOLD_COUNT;

	return (
		<>
			<div
				className={classNames('mb-5 mt-5', {
					'pb-2': withManyProjects,
				})}
			>
				{!isLoadingUser & !isLoadingKoroneiki ? (
					<Banner userName={data?.userAccount.name || ''} />
				) : (
					<BannerSkeleton />
				)}
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
					{!isLoadingUser & !isLoadingKoroneiki ? (
						<div
							className={classNames('d-flex flex-wrap', {
								'home-projects': !withManyProjects,
								'home-projects-sm pt-2': withManyProjects,
							})}
						>
							{projectsFiltered.map((project, index) => (
								<ProjectCard
									key={index}
									onClick={() => nextPage(project)}
									small={withManyProjects}
									{...project}
								/>
							))}
						</div>
					) : (
						<div className="d-flex flex-wrap home-projects">
							<ProjectCardSkeleton />

							<ProjectCardSkeleton />
						</div>
					)}
				</div>
			</div>
			<div
				className={classNames('d-flex', 'flex-wrap', {
					'home-projects': !withManyProjects,
					'home-projects-sm pt-2': withManyProjects,
				})}
			>
				{projects.map((project, index) => (
					<ProjectCard
						key={index}
						onClick={() => nextPage(project)}
						small={withManyProjects}
						{...project}
					/>
				))}
			</div>
		</>
	);
};

export default Home;
