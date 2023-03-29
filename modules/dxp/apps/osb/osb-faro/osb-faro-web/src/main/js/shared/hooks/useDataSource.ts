import * as API from 'shared/api';
import {DataSourceStatuses, DataSourceTypes} from 'shared/util/constants';
import {IStatesRendererContextProps} from 'shared/components/states-renderer/StatesRenderer';
import {Pagination} from 'shared/types';
import {useParams} from 'react-router-dom';
import {useRequest} from 'shared/hooks';

interface IDataSourceProps {
	contactsSelected: boolean;
	dateCreated: string;
	name: string;
	providerType: DataSourceTypes;
	sitesSelected: boolean;
	status: DataSourceStatuses;
}

interface IUseDataSourceProps extends IStatesRendererContextProps {
	items: IDataSourceProps[];
}

export const useDataSource: (
	queryParams?: Pagination
) => IStatesRendererContextProps & IUseDataSourceProps = (
	queryParams = {
		delta: 1,
		orderIOMap: undefined,
		page: 1,
		query: ''
	}
) => {
	const {groupId} = useParams();

	const {data = {items: []}, error, loading} = useRequest({
		dataSourceFn: API.dataSource.search,
		variables: {
			groupId,
			...queryParams
		}
	});

	return {
		empty: !data?.items.length && !error && !loading,
		error,
		items: data?.items,
		loading
	};
};
