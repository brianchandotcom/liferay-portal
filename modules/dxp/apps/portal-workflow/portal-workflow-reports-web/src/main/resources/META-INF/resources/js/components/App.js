import React from 'react';
import ProcessList from './process-list/ProcessListCard';
import OpenProcessesSummary from './open-processes-summary/OpenProcessesSummary';

export default class AppComponent extends React.Component {
	render() {
		return (
			<div className="portal-workflow-reports-app container-fluid">
				<div className="col-12">
					<OpenProcessesSummary />

					<ProcessList />
				</div>
			</div>
		);
	}
}