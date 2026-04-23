import {DocumentNode, useQuery} from '@apollo/client';

import {fetchPolicyDefinition} from 'shared/util/graphql';
import {getFilters, RawFilters} from 'shared/util/filter';
import {
	getSafeDecodedURIComponent,
	getSafeRangeSelectors,
	getSafeTouchpoint
} from 'shared/util/util';
import {ICommonVariables, Interval, RangeSelectors} from 'shared/types';
import {useParams} from 'react-router-dom';

export const useAssetVariables = (variables: ICommonVariables) => {
	const {type, ...commonVariables} = variables;
	const {
		assetId = '',
		channelId = '',
		title = '',
		touchpoint = ''
	} = useParams<{
		assetId: string;
		channelId: string;
		title: string;
		touchpoint: string;
	}>();

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
	Query,
	experienceId,
	filters,
	interval,
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
