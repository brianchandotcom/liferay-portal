import * as API from 'shared/api';
import client from 'shared/apollo/client';
import EventDefinitionsQuery from 'event-analysis/queries/EventDefinitionsQuery';
import Promise from 'metal-promise';
import React from 'react';
import {compose} from 'redux';
import {
	convertEventToProperty,
	convertFieldMappingToAccountProperty,
	convertFieldMappingToIndividualProperty,
	convertFieldMappingToOrganizationProperty
} from '../utils/utils';
import {createInterestProperty} from '../utils/utils';
import {
	DEVELOPER_MODE,
	ENABLE_ACCOUNTS,
	FieldContexts,
	FieldOwnerTypes
} from 'shared/util/constants';
import {EventTypes} from 'event-analysis/utils/types';
import {
	INDIVIDUAL_PROPERTIES,
	ORGANIZATION_PROPERTIES,
	SESSION_PROPERTIES,
	WEB_BEHAVIORS
} from '../utils/properties';
import {List} from 'immutable';
import {NAME} from 'shared/util/pagination';
import {OrderByDirections} from 'shared/util/constants';
import {PropertyGroup, PropertySubgroup} from 'shared/util/records';
import {sub} from 'shared/util/lang';
import {withRequest} from 'shared/hoc';

const MAX_DELTA = 500;

const fetchPropertyGroups = ({groupId}: {groupId: string}): Promise<any> =>
	Promise.all([
		API.fieldMappings.search({
			context: FieldContexts.Demographics,
			delta: MAX_DELTA,
			groupId,
			ownerType: FieldOwnerTypes.Individual
		}),
		API.fieldMappings.search({
			context: FieldContexts.Custom,
			delta: MAX_DELTA,
			groupId,
			ownerType: FieldOwnerTypes.Individual
		}),
		API.fieldMappings.search({
			context: FieldContexts.Organization,
			delta: MAX_DELTA,
			groupId,
			ownerType: FieldOwnerTypes.Account
		}),
		Promise.resolve(ORGANIZATION_PROPERTIES),
		API.fieldMappings.search({
			context: FieldContexts.Custom,
			delta: MAX_DELTA,
			groupId,
			ownerType: FieldOwnerTypes.Organization
		}),
		API.interests.searchKeywords({delta: MAX_DELTA, groupId}),
		Promise.resolve(SESSION_PROPERTIES),
		// TODO: LRAC-8210 Remove for release 3.1
		DEVELOPER_MODE
			? client.query({
					fetchPolicy: 'network-only',
					query: EventDefinitionsQuery,
					variables: {
						eventType: EventTypes.Custom,
						hidden: false,
						page: 0,
						size: MAX_DELTA,
						sort: {
							column: NAME,
							type: OrderByDirections.Ascending
						}
					}
			  })
			: Promise.resolve([]),
		Promise.resolve(WEB_BEHAVIORS)
	]);

const mapResultToProps = ([
	individualDemographicsMappings,
	individualCustomMappings,
	accountMappings,
	organizationProperties,
	organizationCustomMappings,
	interestKeywords,
	sessionProperties,
	eventProperties,
	webBehaviors
]) => {
	const individualDemographicProperties = individualDemographicsMappings.items.map(
		convertFieldMappingToIndividualProperty
	);

	let individualSubgroupsIList = List([
		new PropertySubgroup({
			properties: List(
				individualDemographicProperties.concat(INDIVIDUAL_PROPERTIES)
			)
		})
	]);

	individualSubgroupsIList = individualSubgroupsIList.push(
		new PropertySubgroup({
			label: Liferay.Language.get('dxp-custom-fields'),
			properties: List(
				individualCustomMappings.items.map(
					convertFieldMappingToIndividualProperty
				)
			)
		})
	);

	const organizationPropertyGroup = new PropertyGroup({
		label: sub(Liferay.Language.get('x-attributes'), [
			Liferay.Language.get('organization')
		]) as string,
		propertyKey: FieldOwnerTypes.Organization,
		propertySubgroups: List([
			new PropertySubgroup({properties: organizationProperties}),
			new PropertySubgroup({
				label: Liferay.Language.get('dxp-custom-fields'),
				properties: List(
					organizationCustomMappings.items.map(
						convertFieldMappingToOrganizationProperty
					)
				)
			})
		])
	});

	const propertyGroupsIList = List(
		[
			new PropertyGroup({
				label: Liferay.Language.get('events'),
				propertyKey: 'web',
				propertySubgroups: List(
					[
						new PropertySubgroup({
							// TODO: LRAC-8210 Remove for release 3.1
							label: DEVELOPER_MODE
								? Liferay.Language.get('default-events')
								: null,
							properties: webBehaviors
						}),
						// TODO: LRAC-8210 Remove for release 3.1
						DEVELOPER_MODE &&
							new PropertySubgroup({
								label: Liferay.Language.get('custom-events'),
								properties: List(
									eventProperties?.data?.eventDefinitions?.eventDefinitions?.map(
										convertEventToProperty
									)
								)
							})
					].filter(Boolean)
				)
			}),
			new PropertyGroup({
				label: sub(Liferay.Language.get('x-attributes'), [
					Liferay.Language.get('individual')
				]) as string,
				propertyKey: FieldOwnerTypes.Individual,
				propertySubgroups: individualSubgroupsIList
			}),
			ENABLE_ACCOUNTS &&
				new PropertyGroup({
					label: sub(Liferay.Language.get('x-attributes'), [
						Liferay.Language.get('account')
					]) as string,
					propertyKey: FieldOwnerTypes.Account,
					propertySubgroups: List([
						new PropertySubgroup({
							properties: List(
								accountMappings.items.map(
									convertFieldMappingToAccountProperty
								)
							)
						})
					])
				}),
			new PropertyGroup({
				label: Liferay.Language.get('interests'),
				propertyKey: 'interest',
				propertySubgroups: List([
					new PropertySubgroup({
						properties: List(
							interestKeywords.items.map(createInterestProperty)
						)
					})
				])
			}),
			new PropertyGroup({
				label: sub(Liferay.Language.get('x-attributes'), [
					Liferay.Language.get('session')
				]) as string,
				propertyKey: 'session',
				propertySubgroups: List([
					new PropertySubgroup({properties: sessionProperties})
				])
			})
		].filter(Boolean) as PropertyGroup[]
	);

	return {
		propertyGroupsIList: propertyGroupsIList.push(organizationPropertyGroup)
	};
};

export const withPropertyGroups = WrappedComponent =>
	class extends React.Component<{
		propertyGroupsIList: List<PropertyGroup>;
	}> {
		render() {
			const {propertyGroupsIList, ...otherProps} = this.props;

			return (
				<WrappedComponent
					{...otherProps}
					propertyGroupsIList={propertyGroupsIList}
				/>
			);
		}
	};

export default compose(
	withRequest(fetchPropertyGroups, mapResultToProps),
	withPropertyGroups
);
