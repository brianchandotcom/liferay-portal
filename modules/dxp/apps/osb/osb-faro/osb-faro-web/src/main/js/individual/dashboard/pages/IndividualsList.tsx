import * as API from 'shared/api';
import Card from 'shared/components/Card';
import React, {useMemo} from 'react';
import SearchableEntityTable from 'shared/components/SearchableEntityTable';
import {
	ACCOUNT_NAME,
	COUNTRY,
	createOrderIOMap,
	FIRST_ACTIVITY_DATE,
	LAST_ACTIVITY_DATE,
	NAME,
	PROFILE_TYPE
} from 'shared/util/pagination';
import {FilterOptionType} from 'shared/types';
import {IndividualsListCDPColumns} from 'shared/util/table-columns';
import {ProfileTypes} from 'segment/segment-editor/dynamic/utils/constants';
import {useParams} from 'react-router-dom';
import {useStatefulPagination} from 'shared/hooks/useStatefulPagination';

const mockData = {
	items: [
		{
			accountName: 'Joao Silva, Liferay Inc.',
			activitiesCount: 8,
			dataSourceIndividualPKs: [],
			dateCreated: 1769697362927,
			firstActivityDate: 1769697128235,
			id:
				'47ff64395860b1d498241d907069f649b98c198a95b3ba5303b87094058590c1',
			lastActivityDate: 1769697160365,
			name: 'Test Test',
			profileType: 'KNOWN',
			properties: {
				country: 'United States',
				email: 'test@liferay.com',
				familyName: 'Test',
				givenName: 'Test',
				image: null,
				jobTitle: '',
				worksFor: null
			},
			type: 2
		},
		{
			accountName: 'Seven Eleven',
			activitiesCount: 8,
			dataSourceIndividualPKs: [],
			dateCreated: 1769697362927,
			firstActivityDate: 1769697128235,
			id:
				'47ff64395860b1d498241d907069f649b98c198a95b3ba5303b87094058590c1',
			lastActivityDate: 1769697160365,
			name: 'Josh Dun',
			profileType: 'KNOWN',
			properties: {
				country: 'Canada',
				email: 'josh.dun@liferay.com',
				familyName: 'Dun',
				givenName: 'Josh',
				image: null,
				jobTitle: '',
				worksFor: null
			},
			type: 2
		},
		{
			activitiesCount: 3,
			dataSourceIndividualPKs: [],
			dateCreated: 1769697362927,
			firstActivityDate: 1769697128235,
			id:
				'47ff64395860b1d498241d907069f649b98c198a95b3ba5303b87094058590c1',
			lastActivityDate: 1769697160365,
			name: 'AC-79742349',
			profileType: 'ANONYMOUS',
			properties: {
				image: null,
				jobTitle: '',
				worksFor: null
			},
			type: 2
		}
	]
};

type Data = {
	channelId: string;
	delta: number;
	groupId: string;
	orderIOMap: string;
	profileTypes: ProfileTypes[];
	countries: string[];
	page: number;
	query: string;
};

const IndividualsList = () => {
	const {channelId, groupId} = useParams();

	const paginationParams = useStatefulPagination(null, {
		initialOrderIOMap: createOrderIOMap(NAME)
	});

	const fetchMembers = async (data: Data) => {
		const {
			channelId,
			countries,
			delta,
			groupId,
			orderIOMap,
			page,
			profileTypes,
			query
		} = data;

		return API.individuals.search({
			channelId,
			countries,
			delta,
			groupId,
			orderIOMap,
			page,
			profileTypes,
			query
		});

		return mockData;
	};

	// const {
	// 	data: countriesData,
	// 	error: countriesError,
	// 	loading: countriesLoading
	// } = useRequest({
	// 	dataSourceFn: API.individuals.fetchFieldValues,
	// 	variables: {
	// 		channelId,
	// 		fieldName: 'country',
	// 		groupId
	// 	}
	// });

	const FILTER_BY_OPTIONS: FilterOptionType[] = useMemo(() => {
		const countries = new Set(
			mockData?.items
				.map(item => item.properties?.country)
				.filter(Boolean)
		);

		return [
			{
				key: 'profileTypes',
				label: Liferay.Language.get('profile-type'),
				values: [
					{
						label: Liferay.Language.get('known'),
						value: ProfileTypes.KNOWN
					},
					{
						label: Liferay.Language.get('anonymous'),
						value: ProfileTypes.ANONYMOUS
					}
				]
			},
			{
				key: 'countries',
				label: Liferay.Language.get('country'),
				values: Array.from(countries).map(country => ({
					label: country,
					value: country
				}))
			}
		];
	}, [mockData]);

	const selectedFilters = {
		countries: paginationParams.filterBy.get('countries')?.toArray() || [],
		profileTypes:
			paginationParams.filterBy.get('profileTypes')?.toArray() || []
	};

	const ORDER_BY_OPTIONS = [
		{
			label: Liferay.Language.get('name'),
			value: NAME
		},
		{
			label: Liferay.Language.get('account-name'),
			value: ACCOUNT_NAME
		},
		{
			label: Liferay.Language.get('country'),
			value: COUNTRY
		},
		{
			label: Liferay.Language.get('first-seen'),
			value: FIRST_ACTIVITY_DATE
		},
		{
			label: Liferay.Language.get('last-active'),
			value: LAST_ACTIVITY_DATE
		},
		{
			label: Liferay.Language.get('profile-type'),
			value: PROFILE_TYPE
		}
	];

	return (
		<Card>
			<Card.Title className='card-header'>
				{'individuals-profiles'}
			</Card.Title>
			<Card.Body className='no-padding'>
				<div className='individuals-dashboard-known-individuals-root'>
					<SearchableEntityTable
						{...paginationParams}
						columns={[
							IndividualsListCDPColumns.getNameEmail({
								channelId,
								groupId
							}),
							IndividualsListCDPColumns.accountNames,
							IndividualsListCDPColumns.country,
							IndividualsListCDPColumns.firstSeen,
							IndividualsListCDPColumns.lastActive,
							IndividualsListCDPColumns.profileType
						]}
						dataSourceFn={fetchMembers}
						dataSourceParams={{
							channelId,
							countries: selectedFilters.countries.length
								? selectedFilters.countries
								: undefined,
							groupId,
							profileTypes: selectedFilters.profileTypes.length
								? selectedFilters.profileTypes
								: undefined
						}}
						filterByOptions={FILTER_BY_OPTIONS}
						key='individuals-list-table'
						orderByOptions={ORDER_BY_OPTIONS}
						rowIdentifier='id'
					/>
				</div>
			</Card.Body>
		</Card>
	);
};

export default IndividualsList;
