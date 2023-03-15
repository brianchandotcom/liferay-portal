import * as API from 'shared/api';
import BaseDetails from '../BaseDetails';
import React from 'react';
import {PropTypes} from 'prop-types';

function fetchAccountDetails({groupId, id}) {
	return API.accounts.fetchDetails({accountId: id, groupId});
}

export default class Details extends React.Component {
	static propTypes = {
		groupId: PropTypes.string.isRequired
	};

	render() {
		const {groupId, id} = this.props;

		return (
			<BaseDetails
				dataSourceFn={fetchAccountDetails}
				groupId={groupId}
				id={id}
				title={Liferay.Language.get('account-attributes')}
			/>
		);
	}
}
