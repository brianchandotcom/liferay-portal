import * as API from 'shared/api';
import React from 'react';
import withRequest from './WithRequest';
import {compose} from 'redux';
import {fromJS} from 'immutable';
import {Project} from 'shared/util/records';
import {PropTypes} from 'prop-types';

export const withProjects = WrappedComponent =>
	class extends React.Component {
		static propTypes = {
			projects: PropTypes.arrayOf(PropTypes.instanceOf(Project))
		};

		render() {
			const {className, projects, ...otherProps} = this.props;

			return (
				<WrappedComponent
					{...otherProps}
					className={className}
					projects={projects}
				/>
			);
		}
	};

export default compose(
	withRequest(API.projects.fetchMany, results => ({
		projects: results.map(result => new Project(fromJS(result)))
	})),
	withProjects
);
