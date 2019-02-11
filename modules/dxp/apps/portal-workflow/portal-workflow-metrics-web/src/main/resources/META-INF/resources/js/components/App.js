import graphqlClient from '../shared/apollo/client';
import ProcessListCard from './process-list/ProcessListCard';
import React from 'react';

export default class AppComponent extends React.Component {
	render() {
		const {companyId} = this.props;
		return (
			<div className="portal-workflow-metrics-app">
				<ProcessListCard client={graphqlClient} companyId={companyId} />
			</div>
		);
	}
}