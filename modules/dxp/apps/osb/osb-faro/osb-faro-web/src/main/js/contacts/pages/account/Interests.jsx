import Interests from 'contacts/hoc/account/Interests';
import React from 'react';
import {PropTypes} from 'prop-types';

export default class InterestsPage extends React.Component {
	static propTypes = {
		router: PropTypes.object
	};

	render() {
		const {router} = this.props;

		return (
			<div className='account-interests-root'>
				<div className='row'>
					<div className='col-xl-12'>
						<Interests router={router} />
					</div>
				</div>
			</div>
		);
	}
}
