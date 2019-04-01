import { AppContext } from '../../components/AppContext';
import React from 'react';
import { MemoryRouter as Router } from 'react-router-dom';

export class MockRouter extends React.Component {
	render() {
		const { client, page = 1, search, sort } = this.props;

		const contextState = {
			client,
			companyId: 1,
			defaultDelta: 20,
			deltas: [10, 20, 30, 50],
			maxPages: 3,
			page,
			search,
			setTitle: () => {},
			sort
		};

		const initialEntries = [
			{
				match: { params: { page, search, sort } },
				pathname: `/processes/1/10/${encodeURIComponent('title:asc')}`,
				search: '?backPath=%2F'
			},
			{
				pathname: '/',
				search: '?backPath=%2F'
			},
			{
				pathname: '/slas/1234',
				search: '?backPath=%2Fprocesses'
			}
		];

		return (
			<Router initialEntries={initialEntries} keyLength={0}>
				<AppContext.Provider value={contextState}>
					{this.props.children}
				</AppContext.Provider>
			</Router>
		);
	}
}