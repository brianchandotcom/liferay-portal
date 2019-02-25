import fetch from '../shared/rest/fetch';
import ProcessListCard from './process-list/ProcessListCard';
import React from 'react';
import Router from '../shared/components/router/Router';

/**
 * @class
 * @classdesc Application starter.
 * */
export default class AppComponent extends React.Component {
	render() {
		const {companyId} = this.props;

		return (
			<div className="portal-workflow-metrics-app">
				<Router
					defautPath="process-list"
					paths={[
						{
							component: props => (
								<ProcessListCard
									{...props}
									client={fetch}
									companyId={companyId}
								/>
							),
							path: 'process-list',
							title: Liferay.Language.get('metrics')
						}
					]}
				/>
			</div>
		);
	}
}