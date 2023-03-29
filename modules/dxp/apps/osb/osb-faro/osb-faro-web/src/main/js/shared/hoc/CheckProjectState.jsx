import ActivatingDisplay from 'shared/components/workspaces/ActivatingDisplay';
import React from 'react';
import SuccessDisplay from 'shared/components/workspaces/SuccessDisplay';
import withAction from './WithAction';
import WorkspaceNotFound from 'shared/pages/WorkspaceNotFound';
import WorkspacesErrorDisplay from 'shared/components/workspaces/ErrorDisplay';
import {compose} from 'redux';
import {fetchProject} from '../actions/projects';
import {ProjectStates} from 'shared/util/constants';

/**
 * HOC for conditionally rendering SettingUpWorkspace.
 * If the project state is not ready, we will render SettingUpWorkspace.
 * @returns {Function} - The new component
 */
export default compose(
	withAction(
		({groupId}) => fetchProject({groupId}),
		(state, {groupId}) => state.getIn(['projects', groupId]),
		{
			propName: 'project',
			renderErrorPage: props => <WorkspaceNotFound {...props} />
		}
	),
	WrappedComponent => ({className, groupId, project, ...otherProps}) => {
		switch (project.state) {
			case ProjectStates.Ready:
			case ProjectStates.Scheduled:
				return (
					<WrappedComponent
						{...otherProps}
						className={className}
						groupId={groupId}
					/>
				);

			case ProjectStates.Deactivated:
			case ProjectStates.Maintenance:
			case ProjectStates.Unavailable:
				return (
					<WorkspacesErrorDisplay
						className={className}
						errorType={project.state}
					/>
				);

			case ProjectStates.Activating:
				return <ActivatingDisplay groupId={project.groupId} />;

			default:
				return (
					<SuccessDisplay
						friendlyURL={
							project.friendlyURL || `/${project.groupId}`
						}
					/>
				);
		}
	}
);
