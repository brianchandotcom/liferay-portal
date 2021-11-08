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
import {status} from '../utils/constants';

const PROJECT_THRESHOLD_COUNT = 4;

const Home = () => {
	const [keyword, setKeyword] = useState('');
	const {data: userAccount} = useGraphQL(
		getUserAccountById(LiferayTheme.getUserId())
	);

	useEffect(() => {
		if (userAccount) {
			Storage.setItem(
				STORAGE_KEYS.USER_APPLICATION,
				JSON.stringify({
					accountKey: userAccount.accountKey,
					image:
						userAccount.image &&
						`${REACT_APP_LIFERAY_API}${userAccount.image}`,
					name: userAccount.name,
				})
			);
		} else {
			Storage.removeItem(STORAGE_KEYS.USER_APPLICATION);
		}
	}, [userAccount]);

	const accountBriefs = userAccount?.accountBriefs || [];

	const {data: koroneikiAccounts} =
		useGraphQL(
			getKoroneikiAccountsByFilter({
				accountKeys: accountBriefs.map(
					({externalReferenceCode}) => externalReferenceCode
				),
			})
		) || [];

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
		koroneikiAccounts?.map(
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
				<Banner userName={userAccount?.name || ''} />
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

					<div
						className={classNames('d-flex flex-wrap', {
							'home-projects': !withManyProjects,
							'home-projects-sm pt-2': withManyProjects,
						})}
					>
						{projectsFiltered.map((project, index) => (
							<ProjectCard
								isSmall={withManyProjects}
								key={index}
								onClick={() => nextPage(project)}
								{...project}
							/>
						))}
					</div>
				</div>
			</div>
		</>
	);
};

export default Home;
