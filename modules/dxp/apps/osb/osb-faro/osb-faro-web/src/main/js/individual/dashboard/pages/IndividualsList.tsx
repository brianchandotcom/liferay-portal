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
import {
	Conjunctions,
	ProfileTypes,
	RelationalOperators
} from 'segment/segment-editor/dynamic/utils/constants';
import {FilterOptionType} from 'shared/types';
import {IndividualsListCDPColumns} from 'shared/util/table-columns';
import {useParams} from 'react-router-dom';
import {useRequest} from 'shared/hooks/useRequest';
import {useStatefulPagination} from 'shared/hooks/useStatefulPagination';

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

const DEFAULT_FILTER_BY_OPTIONS: FilterOptionType[] = [
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
	}
];

function transformCountriesInQueryString(countries: string[]) {
	if (!countries || countries.length === 0) {
		return;
	}

	return countries
		.map(
			country =>
				`(demographics/country/value ${RelationalOperators.EQ} '${country}')`
		)
		.join(Conjunctions.Or);
}

const IndividualsList = () => {
	const {channelId, groupId} = useParams();

	const paginationParams = useStatefulPagination(null, {
		initialOrderIOMap: createOrderIOMap(NAME)
	});

	const {data: countriesData, loading: countriesLoading} = useRequest({
		dataSourceFn: API.individuals.fetchFieldValues,
		variables: {
			channelId,
			fieldMappingFieldName: 'country',
			groupId
		}
	});

	const FILTER_BY_OPTIONS: FilterOptionType[] = useMemo(() => {
		const countries = countriesData?.items;

		if (countries) {
			return [
				{
					key: 'countries',
					label: Liferay.Language.get('country'),
					values: countries.map(country => ({
						label: country.value,
						value: country.value
					}))
				},
				...DEFAULT_FILTER_BY_OPTIONS
			];
		}

		return DEFAULT_FILTER_BY_OPTIONS;
	}, [countriesData, countriesLoading]);

	const selectedFilters = {
		filter: transformCountriesInQueryString(
			paginationParams.filterBy.get('countries')?.toArray()
		),
		profileTypes:
			paginationParams.filterBy.get('profileTypes')?.toArray() || []
	};

	return (
		<Card>
			<Card.Title className='card-header'>
				{Liferay.Language.get('individuals-profiles')}
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
						dataSourceFn={API.individuals.search}
						dataSourceParams={{
							channelId,
							filter: selectedFilters.filter,
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
