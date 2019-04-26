import {
	Redirect,
	Route,
	HashRouter as Router,
	Switch
} from 'react-router-dom';
import { AppContext } from '../AppContext';
import { getPathname } from '../../shared/components/tabs/TabItem';
import { PendingItemsCard } from './process-items/ProcessItemsCard';
import React from 'react';
import Tabs from '../../shared/components/tabs/Tabs';
import WorkloadByStepCard from './workload-by-step/WorkloadByStepCard';

class ProcessDashboard extends React.Component {
	render() {
		const { processId, query } = this.props;
		const pendingTab = {
			key: 'pending',
			name: Liferay.Language.get('pending'),
			params: {
				page: 1,
				pageSize: this.context.defaultDelta,
				processId,
				sort: encodeURIComponent('overdueInstanceCount:asc')
			},
			path: '/dashboard/:processId/pending/:pageSize/:page/:sort',
			query
		};

		const defaultPathname = getPathname(pendingTab.params, pendingTab.path);

		return (
			<div className="workflow-process-dashboard">
				<Tabs tabs={[pendingTab]} />

				<Router>
					<Switch>
						<Redirect
							exact
							from="/dashboard/:processId"
							to={{
								pathname: defaultPathname,
								search: query
							}}
						/>

						<Route
							exact
							path={pendingTab.path}
							render={withParams(PendingItemsCard, WorkloadByStepCard)}
						/>
					</Switch>
				</Router>
			</div>
		);
	}
}

export const withParams = (...args) => ({ match: { params } }) =>
	args.map((Component, index) => <Component {...params} key={index} />);

ProcessDashboard.contextType = AppContext;
export default ProcessDashboard;