import * as API from 'shared/api';
import AddWorkspaceForm from 'shared/components/workspaces/AddWorkspaceForm';
import BasePage from 'settings/components/BasePage';
import Promise from 'metal-promise';
import React from 'react';
import {addAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {compose, withCurrentUser, withHistory, withQuery} from 'shared/hoc';
import {connect, ConnectedProps} from 'react-redux';
import {Project, User} from 'shared/util/records';
import {Routes, toRoute} from 'shared/util/router';
import {updateProject} from 'shared/actions/projects';
import {withProject} from 'shared/hoc/WithProject';

type History = {
	push: (path: string) => void;
};

const connector = connect(null, {
	addAlert,
	updateProject
});

type PropsFromRedux = ConnectedProps<typeof connector>;

interface IWorkspaceProps
	extends React.HTMLAttributes<HTMLElement>,
		PropsFromRedux {
	currentUser: User;
	emailAddressDomains: string[];
	groupId: string;
	project: Project;
	history: History;
}

export const Workspace: React.FC<IWorkspaceProps> = ({
	addAlert,
	currentUser,
	emailAddressDomains,
	groupId,
	history,
	project,
	updateProject
}) => {
	const handleSubmit = ({
		emailAddressDomains,
		friendlyURL,
		incidentReportEmailAddresses,
		name,
		timeZoneId
	}) =>
		updateProject({
			emailAddressDomains,
			friendlyURL,
			groupId,
			incidentReportEmailAddresses,
			name,
			timeZoneId
		})
			.then(() => {
				if (friendlyURL !== groupId) {
					history.push(
						toRoute(Routes.SETTINGS_WORKSPACE, {
							groupId: friendlyURL || project.groupId
						})
					);
				}

				addAlert({
					alertType: Alert.Types.Success,
					message: Liferay.Language.get('workspace-settings-saved')
				});
			})
			.catch(error => {
				if (!error.field) {
					addAlert({
						alertType: Alert.Types.Error,
						message: Liferay.Language.get('unknown-error'),
						timeout: false
					});
				}

				return Promise.reject(error);
			});

	return (
		<BasePage
			className='workspace-settings'
			groupId={groupId}
			key='workspaceSettingsPage'
			pageDescription={Liferay.Language.get(
				'view-and-edit-your-workspace-settings.-data-center-location-and-friendly-workspace-url-cannot-be-edited-once-it-has-been-set'
			)}
			pageTitle={Liferay.Language.get('workspace-settings')}
		>
			<AddWorkspaceForm
				className='add-workspace-root col-lg-7 pl-0'
				disabled={!currentUser.isAdmin()}
				editing
				emailAddressDomains={emailAddressDomains}
				onSubmit={handleSubmit}
				project={project}
			/>
		</BasePage>
	);
};

export default compose(
	connector,
	withCurrentUser,
	withHistory,
	withProject(true),
	withQuery(
		({groupId}) =>
			API.projects.fetchEmailAddressDomains({
				groupId
			}),
		val => val,
		({data, error}) => ({
			emailAddressDomains: error ? [] : data
		})
	)
)(Workspace);
