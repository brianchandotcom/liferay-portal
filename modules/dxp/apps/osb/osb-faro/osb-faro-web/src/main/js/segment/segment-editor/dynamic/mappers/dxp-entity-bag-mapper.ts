import {getGraphQLVariablesFromPagination} from 'shared/util/pagination';
import {IPagination} from 'shared/types';

export const mapPropsToOptions = ({
	channelId,
	delta,
	orderIOMap,
	page,
	query
}: IPagination & {channelId: string}) => ({
	variables: {
		...getGraphQLVariablesFromPagination({
			delta,
			orderIOMap,
			page,
			query
		}),
		channelId
	}
});

export const getMapResultToProps = graphqlEntityType => ({
	[graphqlEntityType]: {dxpEntities, total}
}: {
	[key: string]: {
		dxpEntities: {id: string; name: string}[];
		total: number;
	};
}) => ({
	empty: !total,
	items: dxpEntities,
	total
});
