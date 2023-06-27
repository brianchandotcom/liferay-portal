import * as API from 'shared/api';
import ActivitiesChartTimeline from 'contacts/components/ActivitiesChartTimeline';
import autobind from 'autobind-decorator';
import Card from 'shared/components/Card';
import React from 'react';
import {Account} from 'shared/util/records';
import {autoCancel, hasRequest} from 'shared/util/request-decorator';
import {connect} from 'react-redux';
import {EntityTypes} from 'shared/util/constants';
import {getSafeChange} from 'shared/util/change';
import {getSafeRangeKey, INTERVAL_MAP} from 'shared/util/activities';
import {PropTypes} from 'prop-types';
import {WrapSafeResults} from 'shared/hoc/util';

@hasRequest
export class Activities extends React.Component {
	static propTypes = {
		account: PropTypes.instanceOf(Account).isRequired,
		channelId: PropTypes.string,
		groupId: PropTypes.string.isRequired,
		interval: PropTypes.string.isRequired,
		rangeSelectors: PropTypes.object.isRequired,
		timeZoneId: PropTypes.string.isRequired
	};

	state = {
		activityChange: 0,
		activityCount: 0,
		activityHistory: null,
		error: false,
		loading: true
	};

	componentDidMount() {
		this.handleFetchHistory();
	}

	componentDidUpdate(prevProps) {
		const {interval, rangeSelectors} = this.props;

		if (
			prevProps.rangeSelectors !== rangeSelectors ||
			prevProps.interval !== interval
		) {
			this.handleFetchHistory();
		}
	}

	@autoCancel
	@autobind
	handleFetchHistory() {
		this.setState({error: false, loading: true});

		const {
			account: {id},
			channelId,
			groupId,
			interval,
			rangeSelectors
		} = this.props;

		return API.activities
			.fetchHistory({
				channelId,
				contactsEntityId: id,
				contactsEntityType: EntityTypes.Account,
				groupId,
				interval: INTERVAL_MAP[interval],
				max: getSafeRangeKey(rangeSelectors.rangeKey),
				...rangeSelectors
			})
			.then(({activityAggregations, change: activityChange, count}) => {
				this.setState({
					activityChange: getSafeChange(activityChange),
					activityCount: count,
					activityHistory: activityAggregations,
					loading: false
				});
			})
			.catch(err => {
				if (!err.IS_CANCELLATION_ERROR) {
					this.setState({error: true, loading: false});
				}
			});
	}

	render() {
		const {
			props: {
				account: {id, type},
				channelId,
				groupId,
				interval,
				rangeSelectors,
				timeZoneId
			},
			state: {activityCount, activityHistory, error, loading}
		} = this;

		return (
			<Card.Body noPadding>
				<WrapSafeResults
					className='flex-grow-1'
					error={error}
					errorProps={{
						className: 'flex-grow-1',
						onReload: this.handleFetchHistory
					}}
					loading={loading}
				>
					<ActivitiesChartTimeline
						activitiesLabel={Liferay.Language.get(
							'accounts-activities-x'
						)}
						channelId={channelId}
						count={activityCount}
						entityType={type}
						groupId={groupId}
						history={activityHistory}
						id={id}
						interval={interval}
						rangeSelectors={rangeSelectors}
						timeZoneId={timeZoneId}
					/>
				</WrapSafeResults>
			</Card.Body>
		);
	}
}

export default connect((store, {groupId}) => ({
	timeZoneId: store.getIn([
		'projects',
		groupId,
		'data',
		'timeZone',
		'timeZoneId'
	])
}))(Activities);
