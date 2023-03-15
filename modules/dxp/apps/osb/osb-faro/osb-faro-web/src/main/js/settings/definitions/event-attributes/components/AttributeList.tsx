import EventAttributeDefinitionsQuery, {
	EventAttributeDefinitionsData,
	EventAttributeDefinitionsVariables
} from 'event-analysis/queries/EventAttributeDefinitionsQuery';
import ListComponent from 'shared/hoc/ListComponent';
import NoResultsDisplay from 'shared/components/NoResultsDisplay';
import React from 'react';
import {attributeListColumns} from 'shared/util/table-columns';
import {AttributeTypes} from 'event-analysis/utils/types';
import {
	createOrderIOMap,
	getSortFromOrderIOMap,
	NAME
} from 'shared/util/pagination';
import {mapListResultsToProps} from 'shared/util/mappers';
import {useParams} from 'react-router-dom';
import {useQuery} from '@apollo/react-hooks';
import {useQueryPagination} from 'shared/hooks';

const AttributeList: React.FC = () => {
	const {delta, orderIOMap, page, query} = useQueryPagination({
		initialOrderIOMap: createOrderIOMap(NAME)
	});

	const {channelId, groupId} = useParams();

	const response = useQuery<
		EventAttributeDefinitionsData,
		EventAttributeDefinitionsVariables
	>(EventAttributeDefinitionsQuery, {
		variables: {
			keyword: query,
			page: page - 1,
			size: delta,
			sort: getSortFromOrderIOMap(orderIOMap),
			type: AttributeTypes.Local
		}
	});

	return (
		<ListComponent
			{...mapListResultsToProps(response, result => ({
				items:
					result.eventAttributeDefinitions.eventAttributeDefinitions,
				total: result.eventAttributeDefinitions.total
			}))}
			columns={[
				attributeListColumns.getName({channelId, groupId}),
				attributeListColumns.displayName,
				attributeListColumns.description,
				attributeListColumns.sampleValue,
				attributeListColumns.dataType
			]}
			delta={delta}
			entityLabel={Liferay.Language.get('attributes').toLowerCase()}
			noResultsRenderer={
				<NoResultsDisplay
					title={Liferay.Language.get('empty-title-pages')}
				/>
			}
			orderIOMap={orderIOMap}
			page={page}
			query={query}
			rowIdentifier='id'
			showFilterAndOrder={false}
		/>
	);
};

export default AttributeList;
