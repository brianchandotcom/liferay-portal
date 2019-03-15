import fetch from '../shared/rest/fetch';
import ProcessListCard from './process-list/ProcessListCard';
import React from 'react';
import Router from '../shared/components/router/Router';
import SLAForm from './sla/SLAForm';
import SLAListCard from './sla/SLAListCard';

/**
 * @class
 * @classdesc Application starter.
 */
export default class AppComponent extends React.Component {
	render() {
		const {companyId, defaultPath = 'process-list'} = this.props;

		return (
			<div className="portal-workflow-metrics-app">
				<Router
					defaultPath={defaultPath}
					paths={[
						{
							component: props => (
								<SLAForm {...props} client={fetch} companyId={companyId} />
							),
							path: 'sla-form',
							title: Liferay.Language.get('new-sla')
						},
						{
							component: props => (
								<SLAListCard {...props} client={fetch} companyId={companyId} />
							),
							path: 'sla-list',
							title: Liferay.Language.get('slas')
						},
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