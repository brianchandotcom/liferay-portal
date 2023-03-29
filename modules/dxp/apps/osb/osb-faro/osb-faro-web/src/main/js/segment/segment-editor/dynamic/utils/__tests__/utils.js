import * as data from 'test/data';
import * as utils from '../utils';
import {
	ACTIVITY_KEY,
	Conjunctions,
	CustomFunctionOperators,
	isKnown,
	isUnknown,
	PropertyTypes,
	RelationalOperators,
	SUPPORTED_OPERATORS_MAP,
	TimeSpans
} from '../constants';
import {fromJS, Map} from 'immutable';
import {Property} from 'shared/util/records';

const {
	AccountsFilter,
	ActivitiesFilterByCount,
	InterestsFilter,
	SessionsFilter
} = CustomFunctionOperators;
const {And} = Conjunctions;
const {EQ, GT} = RelationalOperators;

describe('utils', () => {
	describe('createInterestProperty', () => {
		it('should create an Interest Property', () => {
			const name = 'Some Interest Property';
			const property = utils.createInterestProperty(name);

			expect(property).toBeInstanceOf(Property);
			expect(property.name).toBe(name);
			expect(property.label).toBe(name);
			expect(property.propertyKey).toBe('interest');
			expect(property.type).toBe('interest');
		});
	});

	describe('createNewGroup', () => {
		it('should create a new CriterionGroup', () => {
			const items = [];

			const criterionGroup = utils.createNewGroup(items);

			expect(criterionGroup.items).toBe(items);
			expect(criterionGroup.conjunctionName).toBe(And);
			expect(criterionGroup.criteriaGroupId).toBeTruthy();
		});
	});

	describe('generateGroupId', () => {
		it('should generate a group id', () => {
			expect(utils.generateGroupId()).toContain('group_');
		});
	});

	describe('generateRowId', () => {
		it('should generate a row id', () => {
			expect(utils.generateRowId()).toContain('row_');
		});
	});

	describe('getChildGroupIds', () => {
		it('should grab child group ids', () => {
			const excluded = 'excluded';
			const criterionGroup = {
				criteriaGroupId: excluded,
				items: [
					{criteriaGroupId: 'test', items: []},
					{criteriaGroupId: 'foo', items: []},
					{
						criteriaGroupId: 'stuff',
						items: [{criteriaGroupId: 'bar', items: []}]
					}
				]
			};

			const childGroupIds = utils.getChildGroupIds(criterionGroup);

			expect(childGroupIds.length).toBe(4);
			expect(childGroupIds.includes(excluded)).toBeFalse();
		});
	});

	describe('getPropertyContextFromRaw', () => {
		it('should get context name from a raw property string', () => {
			const context = 'custom';

			expect(
				utils.getPropertyContextFromRaw(`${context}/city/value`)
			).toBe(context);
		});

		it('should return null if no context exists', () => {
			expect(utils.getPropertyContextFromRaw('Foo')).toBeNull();
		});
	});

	describe('getPropertyNameFromRaw', () => {
		it('should get property name from a raw property string', () => {
			const propertyName = 'city';

			expect(
				utils.getPropertyNameFromRaw(
					`demographics/${propertyName}/value`
				)
			).toBe(propertyName);
		});
	});

	describe('getSupportedOperatorsFromType', () => {
		it('should get the supported operators from type', () => {
			expect(
				utils.getSupportedOperatorsFromType(PropertyTypes.AccountNumber)
			).toBe(SUPPORTED_OPERATORS_MAP[PropertyTypes.AccountNumber]);
		});
	});

	describe('isCriterionGroup', () => {
		it('should return true when value is a CriterionGroup', () => {
			expect(
				utils.isCriterionGroup({
					conjunctionName: And,
					criteriaGroupId: 'foo',
					items: []
				})
			).toBeTrue();
		});

		it('should return false when value is a Criterion', () => {
			expect(utils.isCriterionGroup(data.generateCriterion())).toBe(
				false
			);
		});

		it('should return false when value null', () => {
			expect(utils.isCriterionGroup(null)).toBeFalse();
		});
	});

	describe('isMap', () => {
		it('should return true when value is an instance of ImmutableMap', () => {
			expect(utils.isMap(new Map())).toBeTrue();
		});

		it('should return false when value is not an instance of ImmutableMap', () => {
			expect(utils.isMap({})).toBeFalse();
		});
	});

	describe('isOfKnownType', () => {
		it('should return true when value is isKnown or isUnknown', () => {
			expect(utils.isOfKnownType(isKnown)).toBeTrue();
			expect(utils.isOfKnownType(isUnknown)).toBeTrue();
		});

		it('should return false when value is not isKnown or isUnknown', () => {
			expect(utils.isOfKnownType('foo')).toBeFalse();
		});
	});

	describe('objectToFormData', () => {
		it('should convert input object to FormData', () => {
			expect(utils.objectToFormData({foo: 'bar'})).toBeInstanceOf(
				FormData
			);
		});
	});

	describe('parseActivityKey', () => {
		it('should return an object containing the eventId, id, & objectType from the activityKey', () => {
			expect(utils.parseActivityKey('foo#bar#test')).toMatchObject({
				eventId: 'bar',
				id: 'test',
				objectType: 'foo'
			});
		});
	});

	describe('jsDatetoYYYYMMDD', () => {
		it('should return Date object as a string in YYYY-MM-DD format', () => {
			expect(utils.jsDatetoYYYYMMDD(new Date('12-31-2012'))).toBe(
				'2012-12-31'
			);
		});
	});

	describe('findPropertyByCriterion', () => {
		it('should return the blog viewed Property when provided with a blog viewed Criterion', () => {
			const criterion = data.generateCriterion({
				operatorName: ActivitiesFilterByCount,
				propertyName: ACTIVITY_KEY,
				value: fromJS({
					criterionGroup: {
						conjunctionName: And,
						criteriaGroupId: 'group_0',
						items: [
							{
								operatorName: EQ,
								propertyName: ACTIVITY_KEY,
								value: 'Blog#blogViewed#123123'
							},
							{
								operatorName: GT,
								propertyName: 'day',
								value: TimeSpans.Last24Hours
							}
						]
					},
					operator: GT,
					value: 12
				})
			});

			const property = utils.findPropertyByCriterion(criterion);

			expect(property).toBeInstanceOf(Property);
			expect(property.entityType).toBe('Blog');
			expect(property.name).toBe('blogViewed');
			expect(property.propertyKey).toBe('web');
			expect(property.type).toBe('behavior');
		});

		it('should return the account name Property when provided with an account name Criterion', () => {
			const criterion = data.generateCriterion({
				operatorName: AccountsFilter,
				propertyName: 'organization/accountName/value',
				value: fromJS({
					criterionGroup: {
						conjunctionName: And,
						criteriaGroupId: 'group_0',
						items: [
							{
								operatorName: EQ,
								propertyName: 'organization/accountName/value',
								value: 'foo'
							}
						]
					}
				})
			});

			const mockProperty = new Property();

			const referencedPropertiesIMap = fromJS({
				account: {
					organization: {
						accountName: mockProperty
					}
				}
			});

			const property = utils.findPropertyByCriterion(
				criterion,
				referencedPropertiesIMap
			);

			expect(property).toBe(mockProperty);
		});

		it('should return the session url Property when provided with a session url Criterion', () => {
			const criterion = data.generateCriterion({
				operatorName: SessionsFilter,
				propertyName: 'context/url',
				value: fromJS({
					criterionGroup: {
						conjunctionName: And,
						criteriaGroupId: 'group_0',
						items: [
							{
								operatorName: EQ,
								propertyName: 'context/url',
								value: 'https://www.liferay.com'
							},
							{
								operatorName: GT,
								propertyName: 'completeDate',
								value: TimeSpans.Last24Hours
							}
						]
					}
				})
			});

			const property = utils.findPropertyByCriterion(criterion);

			expect(property).toBeInstanceOf(Property);
			expect(property.name).toBe('context/url');
			expect(property.propertyKey).toBe('session');
			expect(property.type).toBe('session-text');
		});

		it('should return the interest Property when provided with an interest Criterion', () => {
			const criterion = data.generateCriterion({
				operatorName: InterestsFilter,
				propertyName: 'name',
				value: fromJS({
					criterionGroup: {
						conjunctionName: And,
						criteriaGroupId: 'group_0',
						items: [
							{
								operatorName: EQ,
								propertyName: 'name',
								value: 'foo'
							},
							{
								operatorName: EQ,
								propertyName: 'score',
								value: 'true'
							}
						]
					}
				})
			});

			const property = utils.findPropertyByCriterion(criterion);

			expect(property).toBeInstanceOf(Property);
			expect(property.name).toBe('name');
			expect(property.propertyKey).toBe('interest');
			expect(property.type).toBe('interest');
		});

		it('should return the given name Property when provided with a given name Criterion', () => {
			const criterion = data.generateCriterion({
				operatorName: EQ,
				propertyName: 'demographics/givenName/value',
				value: 'test'
			});

			const mockProperty = new Property();

			const referencedPropertiesIMap = fromJS({
				individual: {
					demographics: {
						givenName: mockProperty
					}
				}
			});

			const property = utils.findPropertyByCriterion(
				criterion,
				referencedPropertiesIMap
			);

			expect(property).toBe(mockProperty);
		});
	});

	describe('isValid', () => {
		it.each`
			value        | result
			${'test'}    | ${true}
			${123}       | ${true}
			${null}      | ${true}
			${true}      | ${true}
			${false}     | ${true}
			${''}        | ${false}
			${undefined} | ${false}
		`('should return $result if $value', ({result, value}) => {
			expect(utils.isValid(value)).toBe(result);
		});
	});

	describe('validateSegmentInputs', () => {
		it('should validate as false if a criterion is invalid', () => {
			expect(
				utils.validateSegmentInputs(
					data.mockNewCriteria(10, {valid: false})
				)
			).toBeFalse();
		});

		it('should validate as true if all criterions are valid', () => {
			expect(
				utils.validateSegmentInputs(data.mockNewCriteriaNested())
			).toBeTrue();
		});
	});

	describe('invalidateCriterionWithMissingProperty', () => {
		it('should invalidate criterions with missing property', () => {
			const criteria = data.mockNewCriteria(1, {
				propertyName: 'demographics/firstName/value'
			});

			expect(
				utils.invalidateCriterionWithMissingProperty(
					criteria,
					new Map({
						demographics: new Map()
					})
				).items[0].valid
			).toBeFalse();
		});

		it('should not invalidate criterions with matched property', () => {
			const criteria = data.mockNewCriteria(1, {
				propertyName: 'demographics/firstName/value',
				valid: true
			});

			expect(
				utils.invalidateCriterionWithMissingProperty(
					criteria,
					new Map({
						individual: new Map({
							demographics: new Map({firstName: new Property()})
						})
					})
				).items[0].valid
			).toBeTrue();
		});
	});

	describe('convertFieldMappingToAccountProperty', () => {
		const accountFieldMapping = {
			context: 'organization',
			id: '345606994945962466',
			name: 'accountName',
			ownerType: 'account',
			rawType: 'Text'
		};

		it('should convert fieldMapping to an Account Property Record', () => {
			expect(
				utils.convertFieldMappingToAccountProperty(accountFieldMapping)
			).toMatchSnapshot();
		});

		it('should convert fieldMappingIMap to an Account Property Record', () => {
			expect(
				utils.convertFieldMappingToAccountProperty(accountFieldMapping)
			).toMatchSnapshot();
		});
	});

	describe('convertFieldMappingToIndividualProperty', () => {
		const individualFieldMapping = {
			context: 'demographics',
			id: '335454102264596251',
			name: 'additionalName',
			ownerType: 'individual',
			rawType: 'Text'
		};

		it('should convert fieldMapping to an Individual Property Record', () => {
			expect(
				utils.convertFieldMappingToIndividualProperty(
					individualFieldMapping
				)
			).toMatchSnapshot();
		});

		it('should convert fieldMappingIMap to an Individual Property Record', () => {
			expect(
				utils.convertFieldMappingToIndividualProperty(
					individualFieldMapping
				)
			).toMatchSnapshot();
		});
	});

	describe('convertFieldMappingsToProperties', () => {
		it('should convert fieldMappings to Properties', () => {
			const fieldMappingsIMap = fromJS({
				account: {
					organization: {
						accountName: {
							context: 'organization',
							id: '345606994945962466',
							name: 'accountName',
							ownerType: 'account',
							rawType: 'Text'
						}
					}
				},
				individual: {
					custom: {
						additionaName: {
							context: 'custom',
							id: '123123',
							name: 'customIndividualField',
							ownerType: 'individual',
							rawType: 'Text'
						}
					},
					demographics: {
						additionaName: {
							context: 'demographics',
							id: '335454102264596251',
							name: 'additionalName',
							ownerType: 'individual',
							rawType: 'Text'
						}
					}
				},
				organization: {
					custom: {
						additionaName: {
							context: 'custom',
							id: '321321',
							name: 'customOrganizationField',
							ownerType: 'organization',
							rawType: 'Text'
						}
					}
				}
			});

			expect(
				utils.convertFieldMappingsToProperties(fieldMappingsIMap)
			).toMatchSnapshot();
		});
	});
});
