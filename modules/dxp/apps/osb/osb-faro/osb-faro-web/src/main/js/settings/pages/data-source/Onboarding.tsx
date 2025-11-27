import ConnectLiferayDXP from './ConnectLiferayDXP';
import ConnectSalesforce from './ConnectSalesforce';
import React from 'react';
import RouteNotFound from 'shared/components/RouteNotFound';
import {DataSourceTypes} from 'shared/util/constants';
import {useParams} from 'react-router-dom';

const PAGE_MAP = {
	[DataSourceTypes.Salesforce]: ConnectSalesforce,
	[DataSourceTypes.Liferay]: ConnectLiferayDXP
};

const DataSourceOnboarding = () => {
	const {id} = useParams();

	const Component = PAGE_MAP[id.toUpperCase()];

	if (Component) {
		return <Component />;
	}

	return <RouteNotFound />;
};

export default DataSourceOnboarding;
