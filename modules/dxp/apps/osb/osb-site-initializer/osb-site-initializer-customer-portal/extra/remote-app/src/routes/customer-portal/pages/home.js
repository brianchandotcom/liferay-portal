import classNames from 'classnames';
import { useEffect } from 'react';
import useGraphQL from '~/common/hooks/useGraphql';
import { LiferayTheme } from '~/common/services/liferay';
import { getKoroneikiAccountsByFilter } from '~/common/services/liferay/graphql/koroneiki-accounts';
import { getUserAccountById } from '~/common/services/liferay/graphql/user-accounts';
import { STORAGE_KEYS, Storage } from '~/common/services/liferay/storage';
import { REACT_APP_LIFERAY_API } from '~/common/utils';
import ProjectCard from '../components/ProjectCard';
import SearchProject from '../components/SearchProject';
import { status } from '../utils/constants';

const Home = () => {
	const { data: userAccount } = useGraphQL(getUserAccountById(LiferayTheme.getUserId()));

	useEffect(() => {
		if (userAccount) {
			Storage.setItem(STORAGE_KEYS.USER_APPLICATION, JSON.stringify({
				image: userAccount.image && `${REACT_APP_LIFERAY_API}${userAccount.image}`,
				name: userAccount.name,
			}));
		} else {
			Storage.removeItem(STORAGE_KEYS.USER_APPLICATION);
		}
	}, [userAccount]);

	const accountBriefs = userAccount?.accountBriefs || [];

	const { data: koroneikiAccounts } = useGraphQL(
		getKoroneikiAccountsByFilter({
			accountKeys: accountBriefs.map(
				({ externalReferenceCode }) => externalReferenceCode
			)
		})
	) || [];

	const projects = koroneikiAccounts?.map(
		({ accountKey, liferayContactEmailAddress, liferayContactName, liferayContactRole, region, slaCurrent, slaCurrentEndDate }) => ({
			contact: {
				emailAddress: liferayContactEmailAddress,
				name: liferayContactName,
				role: liferayContactRole
			},
			region,
			sla: {
				current: slaCurrent,
				currentEndDate: slaCurrentEndDate
			},
			status: slaCurrent ? status.active : status.expired,
			title: accountBriefs.find(({ externalReferenceCode }) => externalReferenceCode === accountKey).name
		})) || [];

	const isManyProject = projects.length > 4;

	const nextPage = (project) => {
		Storage.setItem(STORAGE_KEYS.KORONEIKI_APPLICATION, JSON.stringify(project));
	};

	return (
		<>
			<div
				className={classNames("display-4", "font-weight-bold", "mb-5", {
					'pb-2': isManyProject
				})}
			>
				Projects
			</div>
			{isManyProject && (
				<div className="align-items-center d-flex justify-content-between mb-4">
					<SearchProject placeholder="Find a project" />

					<h5 className="m-0 text-neutral-3">
						{projects.length} projects
					</h5>
				</div>
			)}
			<div
				className={classNames("d-flex", "flex-wrap", {
					"home-projects": !isManyProject,
					"home-projects-sm pt-2": isManyProject,
				})}
			>
				{projects.map((project, index) => (
					<ProjectCard
						key={index}
						onClick={() => nextPage(project)}
						small={isManyProject}
						{...project}
					/>
				))}
			</div>
		</>
	);
};

export default Home;
