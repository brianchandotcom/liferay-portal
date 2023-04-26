import * as API from 'shared/api';
import Card from 'shared/components/Card';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import getCN from 'classnames';
import ListGroup from 'shared/components/list-group';
import React from 'react';
import URLConstants from 'shared/util/url-constants';
import {compose, withEmpty, withRequest} from 'shared/hoc';
import {createOrderIOMap, NAME} from 'shared/util/pagination';
import {Link} from 'react-router-dom';
import {PropTypes} from 'prop-types';
import {Routes, toRoute} from 'shared/util/router';

const ITEMS_PER_CARD = 6;

function fetchInterestData({groupId, id}) {
	return API.interests.search({
		contactsEntityId: id,
		delta: ITEMS_PER_CARD,
		groupId,
		orderIOMap: createOrderIOMap(NAME),
		page: 1
	});
}

export const InterestsList = ({channelId, groupId, id, interests}) => (
	<ListGroup className='results-container' noBorder>
		{interests.map(({name}) => (
			<ListGroup.Item className='interest' key={name}>
				<ListGroup.ItemTitle className='text-truncate'>
					{name ? (
						<Link
							to={toRoute(
								Routes.CONTACTS_INDIVIDUAL_INTEREST_DETAILS,
								{
									channelId,
									groupId,
									id,
									interestId: name
								}
							)}
						>
							{name}
						</Link>
					) : (
						name
					)}
				</ListGroup.ItemTitle>
			</ListGroup.Item>
		))}
	</ListGroup>
);

const ListWithInterests = compose(
	withRequest(
		fetchInterestData,
		data => ({interests: data.items, total: data.total}),
		{
			page: false
		}
	),
	withEmpty({
		description: (
			<>
				<span className='mr-1'>
					{Liferay.Language.get(
						'check-back-later-to-verify-if-data-has-been-received-from-your-data-sources'
					)}
				</span>

				<a
					href={URLConstants.IndividualProfilesDocumentInterests}
					key='DOCUMENTATION'
					target='_blank'
				>
					{Liferay.Language.get('learn-more-about-interests')}
				</a>
			</>
		),
		spacer: true,
		title: Liferay.Language.get('there-are-no-interests-found')
	})
)(InterestsList);

export default class InterestsCard extends React.PureComponent {
	static propTypes = {
		channelId: PropTypes.string,
		entity: PropTypes.object.isRequired,
		groupId: PropTypes.string.isRequired
	};

	render() {
		const {
			props: {
				channelId,
				className,
				entity: {id},
				groupId
			}
		} = this;

		const classes = getCN('individual-interests-card-root', className);

		return (
			<Card className={classes}>
				<Card.Header>
					<Card.Title>
						{Liferay.Language.get('current-interests')}
					</Card.Title>
				</Card.Header>

				<ListWithInterests
					channelId={channelId}
					groupId={groupId}
					id={id}
				/>

				<Card.Footer>
					<ClayLink
						className='button-root'
						href={toRoute(Routes.CONTACTS_INDIVIDUAL_INTERESTS, {
							channelId,
							groupId,
							id
						})}
					>
						{Liferay.Language.get('view-all-interests')}

						<ClayIcon
							className='icon-root ml-2'
							symbol='angle-right'
						/>
					</ClayLink>
				</Card.Footer>
			</Card>
		);
	}
}
