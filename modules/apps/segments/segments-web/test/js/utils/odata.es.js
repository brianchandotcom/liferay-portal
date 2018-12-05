import * as Util from '../../../src/main/resources/META-INF/resources/js/utils/odata.es';
import {
	CONJUNCTIONS,
	RELATIONAL_OPERATORS
} from '../../../src/main/resources/META-INF/resources/js/utils/constants.es';
import '../../../src/main/resources/META-INF/resources/js/libs/odata-parser';

const {AND, OR} = CONJUNCTIONS;
const {EQ} = RELATIONAL_OPERATORS;

function generateItems(times) {
	let items = [];

	for (let i = 0; i < times; i++) {
		items.push(
			{
				operatorName: EQ,
				propertyName: 'firstName',
				value: 'test'
			}
		);
	}

	return items;
}

const CRITERIA_MAP_A = {
	conjunctionName: AND,
	items: generateItems(1)
};

const CRITERIA_MAP_B = {
	conjunctionName: AND,
	items: generateItems(3)
};

const CRITERIA_MAP_C = {
	conjunctionName: AND,
	items: [
		{
			conjunctionName: OR,
			items: generateItems(2)
		},
		...generateItems(1)
	]
};

const CRITERIA_MAP_D = {
	conjunctionName: AND,
	items: [
		{
			conjunctionName: OR,
			items: [
				{
					conjunctionName: AND,
					items: [
						{
							conjunctionName: OR,
							items: generateItems(2)
						},
						...generateItems(1)
					]
				},
				...generateItems(1)
			]
		},
		...generateItems(1)
	]
};

describe(
	'odata-util',
	() => {
		describe(
			'buildQueryString',
			() => {
				it(
					'should build a query string from a flat criteria map',
					() => {
						expect(Util.buildQueryString([CRITERIA_MAP_A]))
							.toEqual(`(firstName eq 'test')`);
						expect(Util.buildQueryString([CRITERIA_MAP_B]))
							.toEqual(`(firstName eq 'test' and firstName eq 'test' and firstName eq 'test')`);
					}
				);

				it(
					'should build a query string from a criteria map with nested items',
					() => {
						expect(Util.buildQueryString([CRITERIA_MAP_C]))
							.toEqual(`((firstName eq 'test' or firstName eq 'test') and firstName eq 'test')`);
						expect(Util.buildQueryString([CRITERIA_MAP_D]))
							.toEqual(`((((firstName eq 'test' or firstName eq 'test') and firstName eq 'test') or firstName eq 'test') and firstName eq 'test')`);
					}
				);
			}
		);

		describe(
			'translateQueryToCriteria',
			() => {
				it(
					'should translate a query string into a criteria map',
					() => {
						expect(Util.translateQueryToCriteria(`(firstName eq 'test')`))
							.toEqual(
								{
									'conjunctionName': 'and',
									'items': [
										{
											'operatorName': 'eq',
											'propertyName': 'firstName',
											'value': 'test'
										}
									]
								}
							);
					}
				);

				it(
					'should handle a query string with empty groups',
					() => {
						expect(Util.translateQueryToCriteria(`(((firstName eq 'test')))`))
							.toEqual(
								{
									'conjunctionName': 'and',
									'items': [
										{
											'operatorName': 'eq',
											'propertyName': 'firstName',
											'value': 'test'
										}
									]
								}
							);
					}
				);

				it(
					'should return null if the query is empty or invalid',
					() => {
						expect(Util.translateQueryToCriteria())
							.toEqual(null);
						expect(Util.translateQueryToCriteria(`()`))
							.toEqual(null);
						expect(Util.translateQueryToCriteria(`(firstName eq 'test' eq 'test')`))
							.toEqual(null);
						expect(Util.translateQueryToCriteria(`(firstName = 'test')`))
							.toEqual(null);
					}
				);
			}
		);

		describe(
			'conversion to and from',
			() => {
				it(
					'should be able to translate a query string to map and back to string',
					() => {
						const translatedMap = Util.translateQueryToCriteria(`(firstName eq 'test')`);

						const translatedString = Util.buildQueryString([translatedMap]);

						expect(translatedString)
							.toEqual(`(firstName eq 'test')`);
					}
				);

				it(
					'should be able to translate a complex query string to map and back to string',
					() => {
						const translatedMap = Util.translateQueryToCriteria(`((firstName eq 'test' or firstName eq 'test') and firstName eq 'test')`);

						const translatedString = Util.buildQueryString([translatedMap]);

						expect(translatedString)
							.toEqual(`((firstName eq 'test' or firstName eq 'test') and firstName eq 'test')`);
					}
				);
			}
		);
	}
);