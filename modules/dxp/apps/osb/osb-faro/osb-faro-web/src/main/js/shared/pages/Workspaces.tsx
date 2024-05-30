import ClayLink from '@clayui/link';
import EmptyState from 'shared/components/workspaces/EmptyState';
import JoinableWorkspacesWrapper from 'shared/components/workspaces/JoinableWorkspacesWrapper';
import Loading from 'shared/components/Loading';
import React from 'react';
import WorkspaceList from 'shared/components/workspaces/workspace-list';
import WorkspacesBasePage from 'shared/components/workspaces/BasePage';
import {ENABLE_ADD_TRIAL_WORKSPACE} from 'shared/util/constants';
import {isString} from 'lodash';
import {PLANS} from 'shared/util/subscriptions';
import {Redirect} from 'react-router';
import {Routes, toRoute} from 'shared/util/router';
import {
	useFetchJoinableProjects,
	useFetchProjects
} from 'shared/hooks/useProjects';

export const routingFn = ({projects}) => {
	if (projects.length === 1 && !projects[0].groupId) {
		return toRoute(Routes.WORKSPACE_ADD_WITH_CORP_PROJECT_UUID, {
			corpProjectUuid: projects[0].corpProjectUuid
		});
	}

	return null;
};

const WorkspacesContent = ({
	joinableProjects,
	loading,
	loadingJoinableProjects,
	projects
}) => {
	if (loading) {
		return <Loading spacer />;
	}

	const filteredProjects = projects.filter(
		({faroSubscription, groupId}) =>
			faroSubscription.name !== PLANS.basic.name || groupId
	);

	if (!projects.length && !joinableProjects.length) {
		return <EmptyState />;
	}

	return (
		<>
			{!!filteredProjects.length && (
				<WorkspaceList
					accounts={filteredProjects}
					displayAccountHeaders
					displayPlanInfo
				/>
			)}

			{loadingJoinableProjects ? (
				<Loading spacer />
			) : (
				!!joinableProjects.length && (
					<JoinableWorkspacesWrapper
						details={Liferay.Language.get(
							'workspaces-you-can-request-access-to-based-on-your-email-domain'
						)}
						title={Liferay.Language.get('workspaces-you-can-join')}
					>
						<WorkspaceList
							accounts={joinableProjects}
							isJoinableProjects
						/>
					</JoinableWorkspacesWrapper>
				)
			)}

			{ENABLE_ADD_TRIAL_WORKSPACE && (
				<div className='mt-4'>
					<ClayLink
						button
						className='button-root'
						displayType='secondary'
						href={toRoute(Routes.WORKSPACE_ADD_TRIAL)}
						small
					>
						{Liferay.Language.get('start-free-trial')}
					</ClayLink>
				</div>
			)}
		</>
	);
};

const Workspaces: any = () => {
	const {data: projects, loading} = useFetchProjects();
	const {
		data: joinableProjects,
		loading: loadingJoinableProjects
	} = useFetchJoinableProjects();

	if (projects.length === 1 && !projects[0].groupId) {
		return toRoute(Routes.WORKSPACE_ADD_WITH_CORP_PROJECT_UUID, {
			corpProjectUuid: projects[0].corpProjectUuid
		});
	}

	const handleDetails = () => {
		if (projects.length) {
			return [
				<p key='SELECT'>
					{Liferay.Language.get('workspaces-you-have-joined')}
				</p>
			];
		} else if (!loading && !projects.length && !joinableProjects.length) {
			return [
				<p key='EMPTY_STATE'>
					{Liferay.Language.get(
						'you-are-not-a-part-of-any-workspaces,-lets-create-a-new-one'
					)}
				</p>
			];
		}
	};

	const handleTitle = () => {
		if (projects.length || (!projects.length && !joinableProjects.length)) {
			return Liferay.Language.get('your-workspaces');
		}
	};

	const route = routingFn({projects});

	if (isString(route)) {
		return <Redirect push to={route} />;
	}

	return (
		<div className='workspaces-root' key='Workspaces'>
			<WorkspacesBasePage details={handleDetails()} title={handleTitle()}>
				<WorkspacesContent
					joinableProjects={joinableProjects}
					loading={loading}
					loadingJoinableProjects={loadingJoinableProjects}
					projects={projects}
				/>
			</WorkspacesBasePage>
		</div>
	);
};

export default Workspaces;
