import {AppContext} from '../../src/main/resources/META-INF/resources/js/components/AppContext';
import React from 'react';
import {MemoryRouter as Router} from 'react-router-dom';

export class MockRouter extends React.Component {
	constructor(props) {
		super(props);

		const {client, page = 1, query, search, sort} = this.props;

		this.contextState = {
			client,
			companyId: 1,
			defaultDelta: 20,
			deltas: [5, 10, 20, 30, 50, 75],
			maxPages: 3,
			namespace: 'workflow_',
			page,
			query,
			search,
			setStatus: this.setStatus.bind(this),
			setTitle: this.setTitle.bind(this),
			sort,
			status: null,
			title: null
		};
	}

	setStatus(status, callback) {
		this.contextState.status = status;

		if (callback) {
			callback();
		}
	}

	setTitle(title) {
		this.contextState.title = title;
	}

	render() {
		const defaultPath = `/processes/1/10/${encodeURIComponent(
			'title:asc'
		)}`;

		const {
			initialPath = defaultPath,
			page = 1,
			query,
			search,
			sort
		} = this.props;

		const initialEntries = [
			{
				match: {
					params: {
						page,
						search,
						sort
					}
				},
				pathname: initialPath,
				search: query || '?backPath=%2F'
			}
		];

		return (
			<Router initialEntries={initialEntries} keyLength={0}>
				<AppContext.Provider value={this.contextState}>
					{this.props.children}
				</AppContext.Provider>
			</Router>
		);
	}
}
