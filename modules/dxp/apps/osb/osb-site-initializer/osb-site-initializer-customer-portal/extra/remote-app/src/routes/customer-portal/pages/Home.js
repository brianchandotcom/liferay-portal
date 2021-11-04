import classNames from 'classnames';
import { useContext, useEffect, useState } from 'react';
import useGraphQL from '~/common/hooks/useGraphql';
import { LiferayTheme } from '~/common/services/liferay';
import { getKoroneikiAccountsByFilter } from '~/common/services/liferay/graphql/koroneiki-accounts';
import { getUserAccountById } from '~/common/services/liferay/graphql/user-accounts';
import { STORAGE_KEYS, Storage } from '~/common/services/liferay/storage';
import { REACT_APP_LIFERAY_API } from '~/common/utils';
import Banner from '../components/Banner';
import ProjectCard from '../components/ProjectCard';
import SearchProject from '../components/SearchProject';
import { AppContext } from "../context";
import { status } from '../utils/constants';

const PROJECT_THRESHOLD_COUNT = 4;

const Home = () => {
	const { data: userAccount } = useGraphQL(getUserAccountById(LiferayTheme.getUserId()));
	const [{ assetsPath }] = useContext(AppContext);

	useEffect(() => {
		if (userAccount) {
			Storage.setItem(
				STORAGE_KEYS.USER_APPLICATION,
				JSON.stringify({
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

	const { data: koroneikiAccounts } =
		useGraphQL(
			getKoroneikiAccountsByFilter({
				accountKeys: accountBriefs.map(
					({ externalReferenceCode }) => externalReferenceCode
				),
			})
		) || [];

	const projects =
		koroneikiAccounts?.map(
			({
				accountKey,
				liferayContactEmailAddress,
				liferayContactName,
				liferayContactRole,
				region,
				slaCurrent,
				slaCurrentEndDate,
			}) => ({
				contact: {
					emailAddress: liferayContactEmailAddress,
					name: liferayContactName,
					role: liferayContactRole,
				},
				region,
				sla: {
					current: slaCurrent,
					currentEndDate: slaCurrentEndDate,
				},
				status: slaCurrent ? status.active : status.expired,
				title: accountBriefs.find(
					({ externalReferenceCode }) =>
						externalReferenceCode === accountKey
				).name,
			})
		) || [];

	const [currentProjects, setProjects] = useState(projects);
	const changeInput = (value) => setProjects(projects.filter((project) => project.title.toLowerCase().includes(value.toLowerCase())));
	const withManyProjects = projects.length > PROJECT_THRESHOLD_COUNT;

	const nextPage = (project) => {
		Storage.setItem(
			STORAGE_KEYS.KORONEIKI_APPLICATION,
			JSON.stringify(project)
		);
	};

	return (
		<>
			<div className="d-flex flex-column ml-2 pl-5">
				<div
					className={classNames('display-4 font-weight-bold mb-5', {
						'pb-2': withManyProjects
					})}
				>
					<Banner />

					<img
						src={`${assetsPath}/teste/banner_background_customer_portal.svg`}
					/>
				</div>
			</div>
			<div className={classNames("mx-auto", {
				"col-5": withManyProjects,
				"col-8 pl-6": !withManyProjects,
			})}>
				<div className="d-flex flex-column">
					{withManyProjects && (
						<div className="align-items-center d-flex justify-content-between mb-4 mr-5 pr-3">
							<SearchProject onChange={changeInput} />

							<h5 className="m-0 text-neutral-3">
								{projects.length} projects
							</h5>
						</div>
					)}
					<div
						className={classNames("d-flex", "flex-wrap", {
							"home-projects": !withManyProjects,
							"home-projects-sm pt-2": withManyProjects,
						})}
					>
						{currentProjects.map((project, index) => (
							<ProjectCard
								key={index}
								onClick={() => nextPage(project)}
								small={withManyProjects}
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
