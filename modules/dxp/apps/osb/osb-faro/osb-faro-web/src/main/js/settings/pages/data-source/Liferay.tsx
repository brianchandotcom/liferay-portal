import * as breadcrumbs from 'shared/util/breadcrumbs';
import BaseDataSourcePage from '../../components/data-source/BasePage';
import LiferayOverview from '../../components/liferay/Overview';
import React from 'react';
import {DataSource} from 'shared/util/records';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';

interface ILiferayProps {
	dataSource: DataSource;
	groupId: string;
	id: string;
}

const LiferayDataSource: React.FC<ILiferayProps> = props => {
	const {dataSource, groupId, id} = props;
	const currentUser = useCurrentUser();

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
			<LiferayOverview {...props} currentUser={currentUser} id={id} />
		</BaseDataSourcePage>
	);
};

export default LiferayDataSource;
