import DropdownRangeKey from 'shared/components/DropdownRangeKey';
import TimeRangeQuery from 'shared/queries/TimeRangeQuery';
import {compose} from 'redux';
import {graphql} from '@apollo/react-hoc';
import {mapResultToProps} from 'cerebro-shared/hocs/mappers/dropdown-range-key';
import {withError} from 'cerebro-shared/hocs/utils';
import {withLoading} from 'shared/hoc/util';

export default compose(
	graphql(TimeRangeQuery, {
		props: mapResultToProps
	}),
	withError(),
	withLoading({center: false})
)(DropdownRangeKey);
