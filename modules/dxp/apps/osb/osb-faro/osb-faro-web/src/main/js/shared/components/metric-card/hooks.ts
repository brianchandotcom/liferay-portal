import {DocumentNode} from 'graphql';
import {fetchPolicyDefinition} from 'shared/util/graphql';
import {Filters, getFilters, RawFilters} from 'shared/util/filter';
import {getSafeRangeSelectors, getSafeTouchpoint} from 'shared/util/util';
import {Interval, RangeSelectors, SafeRangeSelectors} from 'shared/types';
import {useParams} from 'react-router-dom';
import {useQuery} from '@apollo/react-hooks';

interface ICommonVariables extends SafeRangeSelectors, Filters {
	interval: Interval;
}

export const useAssetVariables = (commonVariables: ICommonVariables) => {
	const {assetId, channelId, title, touchpoint} = useParams();

	return {
		assetId: decodeURIComponent(assetId),
		channelId,
		title: decodeURIComponent(title),
		touchpoint: getSafeTouchpoint(touchpoint),
		...commonVariables
	};
};

type TMetricQuery = {
	filters: RawFilters;
	interval: Interval;
	Query: DocumentNode;
	rangeSelectors: RangeSelectors;
	variables: (commonVariables: ICommonVariables) => any;
};

export const useMetricQuery = ({
	filters,
	interval,
	Query,
	rangeSelectors,
	variables
}: TMetricQuery) => {
	const {data, error, loading} = useQuery(Query, {
		fetchPolicy: fetchPolicyDefinition(rangeSelectors),
		variables: variables({
			interval,
			...getFilters(filters),
			...getSafeRangeSelectors(rangeSelectors)
		})
	});

	return {data, error, loading};
};
