import ClayButton from '@clayui/button';
import ClayLink from '@clayui/link';
import EmptyState from 'shared/components/workspaces/EmptyState';
import getCN from 'classnames';
import JoinableWorkspacesWrapper from 'shared/components/workspaces/JoinableWorkspacesWrapper';
import React, {useEffect} from 'react';
import WorkspaceList from 'shared/components/workspaces/workspace-list';
import WorkspacesBasePage, {
	BasePageContext
} from 'shared/components/workspaces/BasePage';
import {clearStore} from 'shared/actions/store';
import {close, modalTypes, open} from 'shared/actions/modals';
import {
	compose,
	redirectIf,
	WithJoinableProjects,
	withProjects
} from 'shared/hoc';
import {connect} from 'react-redux';
import {groupBy, map, sortBy} from 'lodash';
import {PLANS} from 'shared/util/subscriptions';
import {PROD_MODE} from 'shared/util/constants';
import {Project} from 'shared/util/records';
import {PropTypes} from 'prop-types';
import {Routes, toRoute} from 'shared/util/router';
import {useApolloClient} from '@apollo/react-hooks';

/**
 * Organizes the projects by account
 * @param {Array.<Record>} projects Array of Project Records
 * @return {Array.<Object>} Array of account objects with nested project arrays
 */
export function sortProjectsByAccount(projects) {
	return sortBy(
		map(groupBy(projects, 'accountKey'), (value, key) => ({
			accountKey: key,
			projects: sortBy(value, 'name')
		})),
		'accountKey'
	);
}

export function routingFn({projects}) {
	if (projects.length === 1 && !projects[0].groupId) {
		return toRoute(Routes.WORKSPACE_ADD_WITH_CORP_PROJECT_UUID, {
			corpProjectUuid: projects[0].corpProjectUuid
		});
	} else {
		return null;
	}
}

const filterProjects = projects =>
	projects.filter(
		({faroSubscription, groupId}) =>
			faroSubscription.name !== PLANS.basic.name || groupId
	);

const Workspaces = ({
	className,
	clearStore,
	close,
	joinableProjects = [],
	open,
	projects
}) => {
	const client = useApolloClient();
	const projectsFiltered = filterProjects(projects);

	useEffect(() => {
		clearStore();

		client.clearStore();
	}, []);

	const renderButtons = () => (
		<div className='mt-4'>
			<ClayButton
				className='button-root mr-2'
				displayType='primary'
				onClick={() =>
					open(modalTypes.CONTACT_SALES_MODAL, {
						onClose: close
					})
				}
				size='sm'
			>
				{Liferay.Language.get('buy-paid-tier')}
			</ClayButton>

			{!PROD_MODE && (
				<ClayLink
					button
					className='button-root'
					displayType='secondary'
					href={toRoute(Routes.WORKSPACE_ADD_TRIAL)}
					small
				>
					{Liferay.Language.get('start-free-trial')}
				</ClayLink>
			)}
		</div>
	);

	const handleDetails = () => {
		if (projects.length > 0) {
			return [
				<p key='SELECT'>
					{Liferay.Language.get('workspaces-you-have-joined')}
				</p>
			];
		} else if (!projects.length > 0 && !joinableProjects.length > 0) {
			return [
				<p key='EMPTY_STATE'>
					{Liferay.Language.get(
						'you-are-not-a-part-of-any-workspaces,-lets-create-a-new-one'
					)}
				</p>
			];
		}
	};

	const handleJoinableProjects = () => {
		if (joinableProjects.length > 0) {
			return (
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
			);
		}
	};

	const handleProjects = () => {
		if (projectsFiltered.length) {
			return (
				<WorkspaceList
					accounts={projectsFiltered}
					displayAccountHeaders
					displayPlanInfo
				/>
			);
		}
	};

	const handleTitle = () => {
		if (projects.length || (!projects.length && !joinableProjects.length)) {
			return Liferay.Language.get('your-workspaces');
		}
	};

	return (
		<div className={getCN('workspaces-root', className)} key='Workspaces'>
			<WorkspacesBasePage details={handleDetails()} title={handleTitle()}>
				<BasePageContext.Consumer>
					{({currentUser}) =>
						projects.length > 0 || joinableProjects.length > 0 ? (
							<>
								{handleProjects()}
								{handleJoinableProjects()}
								{renderButtons(currentUser.userId)}
							</>
						) : (
							<EmptyState />
						)
					}
				</BasePageContext.Consumer>
			</WorkspacesBasePage>
		</div>
	);
};

Workspaces.propTypes = {
	joinableProjects: PropTypes.array,
	projects: PropTypes.arrayOf(PropTypes.instanceOf(Project))
};

export {Workspaces};

export default compose(
	WithJoinableProjects,
	withProjects,
	redirectIf(routingFn),
	connect(null, {clearStore, close, open})
)(Workspaces);
