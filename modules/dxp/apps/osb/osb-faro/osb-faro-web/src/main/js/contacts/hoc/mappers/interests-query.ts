import {COUNT, getSortFromOrderIOMap} from 'shared/util/pagination';
import {OrderByDirections} from 'shared/util/constants';

export {getMapResultToProps} from 'sites/hocs/mappers/composition-query';

const mapPropsToOptions: object = ({
	channelId,
	delta,
	id,
	orderIOMap,
	page,
	query
}) => ({
	variables: {
		active: true,
		channelId,
		id,
		keywords: query,
		size: delta,
		sort: getSortFromOrderIOMap(orderIOMap),
		start: (page - 1) * delta
	}
});

const mapCardPropsToOptions: object = ({channelId, id}) => ({
	variables: {
		active: true,
		channelId,
		id,
		size: 5,
		sort: {
			column: COUNT,
			type: OrderByDirections.Descending
		},
		start: 0
	}
});

export {mapCardPropsToOptions, mapPropsToOptions};
