import React from 'react';
import WorkspaceList from 'shared/components/workspaces/workspace-list';
import WorkspacesBasePage from 'shared/components/workspaces/BasePage';
import {compose, redirectIf, withCurrentUser, withProjects} from 'shared/hoc';
import {getBasicProjects, getSingleProjectRoute} from 'shared/util/projects';
import {Project, User} from 'shared/util/records';
import {PropTypes} from 'prop-types';
import {Routes, setUriQueryValue, toRoute} from 'shared/util/router';
import {sub} from 'shared/util/lang';

const checkDisabled = ({configured}) => configured;

export const routingFn = ({projects}) => {
	const basicProjects = getBasicProjects(projects);

	if (basicProjects.length === 1) {
		return getSingleProjectRoute(basicProjects[0]);
	} else if (
		!basicProjects.some(basicProject => !basicProject.get('groupId'))
	) {
		return setUriQueryValue(
			toRoute(Routes.WORKSPACES),
			'allBasicConfigured',
			true
		);
	} else {
		return null;
	}
};

export class SelectWorkspaceAccount extends React.Component {
	static propTypes = {
		currentUser: PropTypes.instanceOf(User).isRequired,
		projects: PropTypes.arrayOf(PropTypes.instanceOf(Project))
	};

	render() {
		const {currentUser, projects} = this.props;

		return (
			<div
				className={`select-account-root${
					this.props.className ? ` ${this.props.className}` : ''
				}`}
				key='SelectWorkspaceAccount'
			>
				<WorkspacesBasePage
					details={[
						<p key='SELECT'>
							{sub(
								Liferay.Language.get(
									'weve-found-multiple-accounts-associated-with-x-.-you-can-have-one-basic-tier-workspace-of-analytics-cloud-per-account.-please-associate-this-analytics-cloud-workspace-to-an-account'
								),
								[
									<b key='emailAddress'>
										{currentUser.emailAddress}
									</b>
								],
								false
							)}
						</p>
					]}
					title={Liferay.Language.get('select-account')}
				>
					<WorkspaceList
						accounts={getBasicProjects(projects)}
						checkDisabled={checkDisabled}
					/>
				</WorkspacesBasePage>
			</div>
		);
	}
}
export default compose(
	withProjects,
	withCurrentUser,
	redirectIf(routingFn)
)(SelectWorkspaceAccount);
