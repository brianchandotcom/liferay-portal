import ConnectDemandbase from './ConnectDemandbase';
import ConnectLiferayDXP from './ConnectLiferayDXP';
import ConnectSalesforce from './ConnectSalesforce';
import React from 'react';
import RouteNotFound from 'shared/components/RouteNotFound';
import {DataSourceTypes} from 'shared/util/constants';
import {useParams} from 'react-router-dom';

const PAGE_MAP = {
	[DataSourceTypes.Demandbase]: ConnectDemandbase,
	[DataSourceTypes.Liferay]: ConnectLiferayDXP,
	[DataSourceTypes.Salesforce]: ConnectSalesforce
};

const DataSourceOnboarding = () => {
	const {id = ''} = useParams<{id: string}>();

	const Component = (PAGE_MAP as {[key: string]: React.ComponentType})[
		id.toUpperCase()
	];

	if (Component) {
		return <Component />;
	}

	return <RouteNotFound />;
};

export default DataSourceOnboarding;
