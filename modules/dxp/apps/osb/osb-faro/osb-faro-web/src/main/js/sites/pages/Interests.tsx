import Interests from 'sites/hocs/Interests';
import React from 'react';

interface IInterestsPageProps extends React.HTMLAttributes<HTMLDivElement> {
	router: {
		params: object;
	};
}

export default class InterestsPage extends React.Component<IInterestsPageProps> {
	render() {
		const {router} = this.props;

		return (
			<div className='sites-dashboard-interests-root'>
				<div className='row'>
					<div className='col-xl-12'>
						<Interests router={router} />
					</div>
				</div>
			</div>
		);
	}
}
