import React from 'react';
import Touchpoints from 'sites/hocs/Touchpoints';

interface ITouchpointsPageProps {
	router: object;
}

export default class TouchpointsPage extends React.Component<ITouchpointsPageProps> {
	render() {
		const {router} = this.props;

		return (
			<div className='sites-dashboard-touchpoints-list-root'>
				<div className='row'>
					<div className='col-xl-12'>
						<Touchpoints router={router} />
					</div>
				</div>
			</div>
		);
	}
}
