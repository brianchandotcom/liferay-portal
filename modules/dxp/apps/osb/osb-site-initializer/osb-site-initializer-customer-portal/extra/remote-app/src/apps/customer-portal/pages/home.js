import useGraphQL from '~/shared/hooks/useGraphql';
import { LiferayTheme } from '~/shared/services/liferay';
import { getKoroneikiAccountsByFilter } from '~/shared/services/liferay/graphql/koroneiki-accounts';
import { getUserAccountById } from '~/shared/services/liferay/graphql/user-accounts';
import { STORAGE_KEYS, Storage } from '~/shared/services/liferay/storage';
import { REACT_APP_LIFERAY_API } from '~/shared/utils';
import ProjectCard from '../components/ProjectCard';
import SearchProject from '../components/SearchProject';
import { status } from '../utils/constants';

const getEndDate = (endEndStr) => {
	const date = new Date(endEndStr);
	const month = date.toLocaleDateString('default', { month: 'short' });
	const day = date.getDate();
	const year = date.getFullYear();

	return `${month} ${day}, ${year}`;
}

const Home = () => {
	const { data: userAccount } = useGraphQL(getUserAccountById(LiferayTheme.getUserId()));

	if (userAccount) {
		Storage.setItem(STORAGE_KEYS.USER_APPLICATION, JSON.stringify({
			image: userAccount?.image && `${REACT_APP_LIFERAY_API}${userAccount?.image}`,
			name: userAccount?.name,
		}));
	} else {
		Storage.removeItem(STORAGE_KEYS.USER_APPLICATION);
	}

	const accountBriefs = userAccount?.accountBriefs;

	const { data: koroneikiAccounts } = useGraphQL(
		getKoroneikiAccountsByFilter({
			accountKeys: accountBriefs?.map(
				acc => acc.externalReferenceCode
			)
		})
	);

	const projects = koroneikiAccounts?.map(
		acc => ({
			endDate: getEndDate(acc.slaCurrentEndDate),
			region: acc.region,
			status: acc.slaCurrent ? status.active : status.expired,
			title: accountBriefs?.find(accBrief => accBrief.externalReferenceCode === acc.accountKey).name
		})) || [];


	return (
		<>
			<div
				className={`display-4 font-weight-bold mb-5${projects.length > 4 ? ' pb-2' : ''
					} text-neutral-0`}
			>
				Projects
			</div>
			{projects.length > 4 && (
				<div className="align-items-center d-flex justify-content-between mb-4">
					<SearchProject placeholder="Find a project" />

					<h5 className="m-0 text-neutral-3">
						{projects.length} projects
					</h5>
				</div>
			)}
			<div
				className={`d-flex flex-wrap home-projects${projects.length > 4 ? '-sm pt-2' : ''
					}`}
			>
				{projects.map((project, index) => (
					<ProjectCard
						key={index}
						{...project}
						small={projects.length > 4}
					/>
				))}
			</div>
		</>
	);
};

export default Home;
