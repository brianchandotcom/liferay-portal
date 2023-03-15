import * as breadcrumbs from 'shared/util/breadcrumbs';
import BaseDataSourcePage from '../../components/data-source/BasePage';
import LiferayOverview from '../../components/liferay/Overview';
import React from 'react';
import {compose} from 'redux';
import {DataSource, User} from 'shared/util/records';
import {withCurrentUser} from 'shared/hoc';

interface ILiferayProps {
	currentUser: User;
	dataSource: DataSource;
	groupId: string;
	id: string;
}

export const LiferayDataSource: React.FC<ILiferayProps> = props => {
	const {dataSource, groupId, id} = props;

	return (
		<BaseDataSourcePage
			{...props}
			breadcrumbItems={[
				breadcrumbs.getDataSources({groupId}),
				breadcrumbs.getDataSourceName({
					active: true,
					label: dataSource.name
				})
			]}
			documentTitle={dataSource.name}
			pageDescription={dataSource.url}
			pageTitle={dataSource.name}
			showDelete
		>
			<LiferayOverview id={id} {...props} />
		</BaseDataSourcePage>
	);
};

export default compose<any>(withCurrentUser)(LiferayDataSource);
