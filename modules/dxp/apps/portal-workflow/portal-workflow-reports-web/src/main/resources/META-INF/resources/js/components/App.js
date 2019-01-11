import ProcessList from './process-list/ProcessListCard';
import React from 'react';

export default class AppComponent extends React.Component {
	render() {
		const {companyId} = this.props;
		return (
			<div className="portal-workflow-reports-app container-fluid-1280 main-content-body">
				<ProcessList companyId={companyId} />
			</div>
		);
	}
}