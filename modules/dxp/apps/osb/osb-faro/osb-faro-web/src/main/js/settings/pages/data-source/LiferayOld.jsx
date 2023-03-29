import * as breadcrumbs from 'shared/util/breadcrumbs';
import BaseDataSourcePage from 'settings/components/data-source/BasePage';
import React from 'react';
import {DataSource, User} from 'shared/util/records';
import {PropTypes} from 'prop-types';
import {withCurrentUser} from 'shared/hoc';

@withCurrentUser
export default class LiferayDataSource extends React.Component {
	static propTypes = {
		currentUser: PropTypes.instanceOf(User),
		dataSource: PropTypes.instanceOf(DataSource),
		groupId: PropTypes.string.isRequired,
		id: PropTypes.string
	};

	render() {
		const {currentUser, dataSource, groupId, id} = this.props;

		return (
			<BaseDataSourcePage
				breadcrumbItems={[
					breadcrumbs.getDataSources({groupId}),
					breadcrumbs.getDataSourceName({
						active: true,
						groupId,
						id,
						label: dataSource.name
					})
				]}
				currentUser={currentUser}
				dataSource={dataSource}
				documentTitle={dataSource.name}
				groupId={groupId}
				id={id}
				key='LIFERAY_OLD'
				pageTitle={Liferay.Language.get('configure-liferay-dxp')}
				showDelete
			/>
		);
	}
}
