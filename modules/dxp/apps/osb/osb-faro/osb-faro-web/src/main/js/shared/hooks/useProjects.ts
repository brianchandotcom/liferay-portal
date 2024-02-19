import {fetchJoinableProjects, fetchMany} from 'shared/api/projects';
import {fromJS} from 'immutable';
import {Project} from 'shared/util/records';
import {useEffect, useState} from 'react';

export const useFetchProjects = () => {
	const [loading, setLoading] = useState(true);
	const [data, setData] = useState([]);

	useEffect(() => {
		async function fetch() {
			try {
				const projects = await fetchMany();

				setData(projects.map(result => new Project(fromJS(result))));
				setLoading(false);
			} catch {
				throw new Error('Error on fetchProjects');
			}
		}

		fetch();
	}, []);

	return {
		data,
		loading
	};
};

export const useFetchJoinableProjects = () => {
	const [loading, setLoading] = useState(true);
	const [data, setData] = useState([]);

	useEffect(() => {
		async function fetch() {
			try {
				const projects = await fetchJoinableProjects();

				setData(projects);
				setLoading(false);
			} catch {
				throw new Error('Error on fetchJoinableProjects');
			}
		}

		fetch();
	}, []);

	return {
		data,
		loading
	};
};
