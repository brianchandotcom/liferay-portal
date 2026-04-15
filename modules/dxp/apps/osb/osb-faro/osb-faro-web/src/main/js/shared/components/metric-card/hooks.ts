import {DocumentNode} from 'apollo-boost';
import {fetchPolicyDefinition} from 'shared/util/graphql';
import {Filters, getFilters, RawFilters} from 'shared/util/filter';
import {
	getSafeDecodedURIComponent,
	getSafeRangeSelectors,
	getSafeTouchpoint
} from 'shared/util/util';
import {Interval, RangeSelectors, SafeRangeSelectors} from 'shared/types';
import {useParams} from 'react-router-dom';
import {useQuery} from '@apollo/react-hooks';

interface ICommonVariables extends SafeRangeSelectors, Filters {
	interval: Interval;
	type?: string;
}

export const useAssetVariables = (variables: ICommonVariables) => {
	const {type, ...commonVariables} = variables;
	const {assetId, channelId, title, touchpoint} = useParams();

	return {
		assetId: getSafeDecodedURIComponent(assetId),
		touchpoint: getSafeTouchpoint(touchpoint),
		...(type !== 'objectEntry' && {
			channelId,
			title: getSafeDecodedURIComponent(title)
		}),
		...commonVariables
	};
};

type TMetricQuery = {
	filters: RawFilters;
	experienceId?: string;
	interval: Interval;
	Query: DocumentNode;
	rangeSelectors: RangeSelectors;
	variables: (commonVariables: ICommonVariables) => any;
};

export const useMetricQuery = ({
	experienceId,
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
			...getSafeRangeSelectors(rangeSelectors),
			...(experienceId && {experienceId})
		})
	});

	return {data, error, loading};
};
