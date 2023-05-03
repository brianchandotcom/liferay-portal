import CriteriaSidebarItem from './CriteriaSidebarItem';
import React from 'react';
import {
	ACTIVITY_KEY,
	EVENT_KEY,
	FunctionalOperators,
	PropertyTypes,
	RelationalOperators,
	TimeSpans
} from '../utils/constants';
import {createCustomValueMap} from '../utils/custom-inputs';
import {jsDatetoYYYYMMDD} from '../utils/utils';
import {List} from 'immutable';
import {Property, PropertyGroup, PropertySubgroup} from 'shared/util/records';

/**
 * Returns a default value for a property provided.
 */
const getDefaultValue = (property: Property): any => {
	const {name, options, type} = property;

	switch (type) {
		case PropertyTypes.Date:
			return jsDatetoYYYYMMDD(new Date());
		case PropertyTypes.DateTime:
			return new Date().toISOString();
		case PropertyTypes.OrganizationDate: {
			return createCustomValueMap([
				{
					key: 'criterionGroup',
					value: [
						{
							operatorName: RelationalOperators.EQ,
							propertyName: name,
							value: jsDatetoYYYYMMDD(new Date())
						}
					]
				}
			]);
		}
		case PropertyTypes.SessionDateTime:
		case PropertyTypes.OrganizationDateTime:
			return createCustomValueMap([
				{
					key: 'criterionGroup',
					value: [
						{
							operatorName: RelationalOperators.EQ,
							propertyName: name,
							value: new Date().toISOString()
						}
					]
				}
			]);
		case PropertyTypes.Boolean:
			return 'true';
		case PropertyTypes.Interest:
			return createCustomValueMap([
				{
					key: 'criterionGroup',
					value: [
						{
							operatorName: RelationalOperators.EQ,
							propertyName: 'name',
							value: name
						},
						{
							operatorName: RelationalOperators.EQ,
							propertyName: 'score',
							value: 'true'
						}
					]
				}
			]);
		case PropertyTypes.AccountNumber:
		case PropertyTypes.AccountText:
		case PropertyTypes.OrganizationSelectText:
		case PropertyTypes.OrganizationText:
		case PropertyTypes.OrganizationNumber:
			return createCustomValueMap([
				{
					key: 'criterionGroup',
					value: [
						{
							operatorName: RelationalOperators.EQ,
							propertyName: name,
							value: ''
						}
					]
				}
			]);
		case PropertyTypes.Event:
			return createCustomValueMap([
				{
					key: 'criterionGroup',
					value: [
						{
							operatorName: RelationalOperators.EQ,
							propertyName: EVENT_KEY,
							value: name
						},
						{
							operatorName: FunctionalOperators.Contains,
							propertyName: 'attribute/',
							value: ''
						},
						{
							operatorName: RelationalOperators.GT,
							propertyName: 'day',
							value: TimeSpans.Last24Hours
						}
					]
				},
				{key: 'operator', value: RelationalOperators.GE},
				{key: 'value', value: 1}
			]);
		case PropertyTypes.Behavior:
			return createCustomValueMap([
				{
					key: 'criterionGroup',
					value: [
						{
							operatorName: RelationalOperators.EQ,
							propertyName: ACTIVITY_KEY,
							value: ''
						},
						{
							operatorName: RelationalOperators.GT,
							propertyName: 'day',
							value: TimeSpans.Last24Hours
						}
					]
				},
				{key: 'operator', value: RelationalOperators.GE},
				{key: 'value', value: 1}
			]);
		case PropertyTypes.OrganizationBoolean:
			return createCustomValueMap([
				{
					key: 'criterionGroup',
					value: [
						{
							operatorName: RelationalOperators.EQ,
							propertyName: name,
							value: 'true'
						}
					]
				}
			]);
		case PropertyTypes.SessionGeolocation:
		case PropertyTypes.SessionNumber:
		case PropertyTypes.SessionText:
			return createCustomValueMap([
				{
					key: 'criterionGroup',
					value: [
						{
							operatorName: RelationalOperators.EQ,
							propertyName: name,
							value: options.length ? options[0].value : ''
						},
						{
							operatorName: RelationalOperators.GT,
							propertyName: 'completeDate',
							value: TimeSpans.Last24Hours
						}
					]
				}
			]);
		case PropertyTypes.Text:
			if (options && !!options.length) {
				return options[0].value;
			}

			return '';
		default:
			return '';
	}
};

interface ICriteriaSidebarCollapseProps {
	propertyGroupsIList: List<PropertyGroup>;
	propertyKey: string;
	searchValue: string;
}

const CriteriaSidebarCollapse: React.FC<ICriteriaSidebarCollapseProps> = ({
	propertyGroupsIList,
	propertyKey,
	searchValue
}) => {
	const filterProperties = (): List<PropertySubgroup> => {
		const propertyGroup = propertyGroupsIList.find(
			propertyGroup => propertyKey === propertyGroup.propertyKey
		);

		const propertySubgroupsIList = propertyGroup
			? propertyGroup.propertySubgroups
			: List<PropertySubgroup>();

		if (searchValue) {
			return propertySubgroupsIList.map(
				({label, properties}) =>
					new PropertySubgroup({
						label,
						properties: properties.filter(({label}) => {
							const propertyLabel = label.toLowerCase();

							return propertyLabel.includes(
								searchValue.toLowerCase()
							);
						}) as List<Property>
					})
			) as List<PropertySubgroup>;
		}

		return propertySubgroupsIList;
	};

	const filteredProperties = filterProperties();

	const noResults = filteredProperties
		.filterNot(({properties}) => properties.isEmpty())
		.isEmpty();

	return (
		<ul className='property-subgroups-list active'>
			{noResults ? (
				<li className='empty-message'>
					{Liferay.Language.get('no-results-were-found')}
				</li>
			) : (
				filteredProperties.map(({label, properties}, i) => (
					<li key={`${label}-${i}`}>
						{label && (
							<div className='property-subgroup-label'>
								{label}
							</div>
						)}

						{properties.isEmpty() ? (
							<div className='empty-message'>
								{Liferay.Language.get('no-results-were-found')}
							</div>
						) : (
							<ul className='properties-list'>
								{properties.map((property, i) => {
									const {
										label,
										name,
										propertyKey,
										type
									} = property;

									return (
										<CriteriaSidebarItem
											className={`color--${propertyKey}`}
											defaultValue={getDefaultValue(
												property
											)}
											key={`${name}-${i}`}
											label={label}
											name={name}
											property={property}
											propertyKey={propertyKey}
											type={type}
										/>
									);
								})}
							</ul>
						)}
					</li>
				))
			)}
		</ul>
	);
};

export default CriteriaSidebarCollapse;
