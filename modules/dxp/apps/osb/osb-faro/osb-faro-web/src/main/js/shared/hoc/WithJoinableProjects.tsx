import * as API from '../../shared/api';
import React from 'react';
import withRequest from './WithRequest';
import {compose} from 'redux';

interface IWrappedComponentProps {
	joinableProjects: {
		groupId: number;
		name: string;
		requested: boolean;
	}[];
}

const WithJoinableProjects = (
	WrappedComponent: React.ComponentType<IWrappedComponentProps>
) =>
	compose(
		withRequest(API.projects.fetchJoinableProjects, results => ({
			joinableProjects: results
		}))
	)(({joinableProjects}) => (
		<WrappedComponent joinableProjects={joinableProjects} />
	));

export default WithJoinableProjects;
