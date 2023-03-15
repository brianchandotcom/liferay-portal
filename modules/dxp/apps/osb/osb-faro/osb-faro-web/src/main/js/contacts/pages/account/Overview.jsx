import * as API from 'shared/api';
import ActivitiesCard from 'contacts/components/account/ActivitiesCard';
import AssociatedSegmentsCard from 'contacts/components/AssociatedSegmentsCard';
import InfoCard from 'shared/components/InfoCard';
import InterestsCard from 'contacts/hoc/account/InterestsCard';
import KnownIndividualsCard from 'contacts/components/account/KnownIndividualsCard';
import NoResultsDisplay from 'shared/components/NoResultsDisplay';
import React from 'react';
import URLConstants from 'shared/util/url-constants';
import {Account} from 'shared/util/records';
import {createOrderIOMap, INDIVIDUAL_COUNT, NAME} from 'shared/util/pagination';
import {EntityTypes} from 'shared/util/constants';
import {PropTypes} from 'prop-types';
import {Routes, toRoute} from 'shared/util/router';

const ITEMS_PER_CARD = 5;

function getInfoItems(propertiesIMap, itemNames) {
	return itemNames.map(name => ({name, value: propertiesIMap.get(name)}));
}

function getContactItems(account) {
	const propertiesIMap = account.get('properties');

	return getInfoItems(propertiesIMap, [
		'phone',
		'fax',
		'billingAddress',
		'shippingAddress',
		'website'
	]);
}

function getFirmographicItems(account) {
	const propertiesIMap = account.get('properties');

	let description = propertiesIMap.get('description') || '';

	if (description.length > 400) {
		description = `${description.substr(0, 400).trim()}...`;
	}

	return [
		{name: 'name', value: account.get('name')},
		...getInfoItems(propertiesIMap, [
			'accountType',
			'accountNumber',
			'industry',
			'annualRevenue',
			'ownership',
			'numberOfEmployees',
			'yearStarted'
		]),
		{
			name: 'description',
			value: description
		}
	];
}

function fetchAssociatedSegments({channelId, groupId, id, searchValue}) {
	return API.individualSegment.search({
		channelId,
		contactsEntityId: id,
		contactsEntityType: EntityTypes.Account,
		delta: ITEMS_PER_CARD,
		groupId,
		orderIOMap: createOrderIOMap(INDIVIDUAL_COUNT),
		query: searchValue
	});
}

function fetchIndividuals({channelId, groupId, id}) {
	return API.individuals.search({
		accountId: id,
		channelId,
		delta: ITEMS_PER_CARD,
		groupId,
		orderIOMap: createOrderIOMap(NAME)
	});
}

export default class Overview extends React.Component {
	static propTypes = {
		account: PropTypes.instanceOf(Account).isRequired,
		channelId: PropTypes.string,
		groupId: PropTypes.string.isRequired,
		id: PropTypes.string.isRequired
	};

	render() {
		const {account, channelId, groupId, id} = this.props;

		return (
			<div className='row'>
				<div className='col-xl-7'>
					<ActivitiesCard
						account={account}
						channelId={channelId}
						groupId={groupId}
					/>

					<InterestsCard
						channelId={channelId}
						groupId={groupId}
						id={id}
					/>

					<AssociatedSegmentsCard
						channelId={channelId}
						dataSourceFn={fetchAssociatedSegments}
						groupId={groupId}
						id={id}
						noResultsRenderer={() => (
							<NoResultsDisplay
								description={
									<>
										{Liferay.Language.get(
											'create-a-segment-to-get-started'
										)}

										<a
											className='d-block'
											href={
												URLConstants.AccountSegmentsDocumentationLink
											}
											key='DOCUMENTATION'
											target='_blank'
										>
											{Liferay.Language.get(
												'learn-more-about-segments'
											)}
										</a>
									</>
								}
								spacer
								title={Liferay.Language.get(
									'there-are-no-segments-found'
								)}
							/>
						)}
						pageUrl={toRoute(Routes.CONTACTS_ACCOUNT_SEGMENTS, {
							channelId,
							groupId,
							id
						})}
					/>
				</div>

				<div className='col-xl-5'>
					<InfoCard
						header={Liferay.Language.get('account-firmographics')}
						image={account.photoURL}
						items={getFirmographicItems(account)}
					/>

					<InfoCard
						header={Liferay.Language.get('contact-information')}
						items={getContactItems(account)}
					/>

					<KnownIndividualsCard
						channelId={channelId}
						dataSourceFn={fetchIndividuals}
						groupId={groupId}
						id={id}
					/>
				</div>
			</div>
		);
	}
}
