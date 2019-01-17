import * as ODataUtil from 'utils/odata.es';
import * as Utils from 'utils/utils.es';
import {mockCriteria, mockCriteriaNested} from 'test/data';
import 'libs/odata-parser';

describe(
	'odata-util',
	() => {
		beforeAll(
			() => {
				Utils.generateGroupId = jest.fn(() => 'group_01');
			}
		);

		describe(
			'buildQueryString',
			() => {
				it(
					'should build a query string from a flat criteria map',
					() => {
						expect(ODataUtil.buildQueryString([mockCriteria(1)]))
							.toEqual('(firstName eq \'test\')');
						expect(ODataUtil.buildQueryString([mockCriteria(3)]))
							.toEqual('(firstName eq \'test\' and firstName eq \'test\' and firstName eq \'test\')');
					}
				);

				it(
					'should build a query string from a criteria map with nested items',
					() => {
						expect(ODataUtil.buildQueryString([mockCriteriaNested()]))
							.toEqual('((((firstName eq \'test\' or firstName eq \'test\') and firstName eq \'test\') or firstName eq \'test\') and firstName eq \'test\')');
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
						expect(ODataUtil.translateQueryToCriteria('(firstName eq \'test\')'))
							.toEqual(
								{
									conjunctionName: 'and',
									groupId: 'group_01',
									items: [
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
						expect(ODataUtil.translateQueryToCriteria('(((firstName eq \'test\')))'))
							.toEqual(
								{
									conjunctionName: 'and',
									groupId: 'group_01',
									items: [
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
					'should handle a query string with "not" operator',
					() => {
						expect(ODataUtil.translateQueryToCriteria('(not (firstName eq \'test\'))'))
							.toEqual(
								{
									conjunctionName: 'and',
									groupId: 'group_01',
									items: [
										{
											'operatorName': 'not-eq',
											'propertyName': 'firstName',
											'value': 'test'
										}
									]
								}
							);
					}
				);

				it(
					'should handle a query string with "contains" operator',
					() => {
						expect(ODataUtil.translateQueryToCriteria('contains(firstName, \'test\')'))
							.toEqual(
								{
									conjunctionName: 'and',
									groupId: 'group_01',
									items: [
										{
											'operatorName': 'contains',
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
						expect(ODataUtil.translateQueryToCriteria())
							.toEqual(null);
						expect(ODataUtil.translateQueryToCriteria('()'))
							.toEqual(null);
						expect(ODataUtil.translateQueryToCriteria('(firstName eq \'test\' eq \'test\')'))
							.toEqual(null);
						expect(ODataUtil.translateQueryToCriteria('(firstName = \'test\')'))
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
						const testQuery = '(firstName eq \'test\')';

						const translatedMap = ODataUtil.translateQueryToCriteria(testQuery);

						const translatedString = ODataUtil.buildQueryString([translatedMap]);

						expect(translatedString).toEqual(testQuery);
					}
				);

				it(
					'should be able to translate a complex query string to map and back to string',
					() => {
						const testQuery = '((firstName eq \'test\' or firstName eq \'test\') and firstName eq \'test\')';

						const translatedMap = ODataUtil.translateQueryToCriteria(testQuery);

						const translatedString = ODataUtil.buildQueryString([translatedMap]);

						expect(translatedString).toEqual(testQuery);
					}
				);

				it(
					'should be able to translate a query string with "not" to map and back to string',
					() => {
						const testQuery = '((not (firstName eq \'test\')))';

						const translatedMap = ODataUtil.translateQueryToCriteria(testQuery);

						const translatedString = ODataUtil.buildQueryString([translatedMap]);

						expect(translatedString).toEqual(testQuery);
					}
				);

				it(
					'should be able to translate a complex query string with "not" to map and back to string',
					() => {
						const testQuery = '(firstName eq \'test\' and ((not (lastName eq \'foo\')) or (not (lastName eq \'bar\'))))';

						const translatedMap = ODataUtil.translateQueryToCriteria(testQuery);

						const translatedString = ODataUtil.buildQueryString([translatedMap]);

						expect(translatedString).toEqual(testQuery);
					}
				);

				it(
					'should be able to translate a query string with "contains" to map and back to string',
					() => {
						const testQuery = '(contains(firstName, \'test\'))';

						const translatedMap = ODataUtil.translateQueryToCriteria(testQuery);

						const translatedString = ODataUtil.buildQueryString([translatedMap]);

						expect(translatedString).toEqual(testQuery);
					}
				);

				it(
					'should be able to translate a query string with "contains" to map and back to string',
					() => {
						const testQuery = '(firstName eq \'test\' and (contains(lastName, \'foo\') or contains(lastName, \'bar\')))';

						const translatedMap = ODataUtil.translateQueryToCriteria(testQuery);

						const translatedString = ODataUtil.buildQueryString([translatedMap]);

						expect(translatedString).toEqual(testQuery);
					}
				);

				it(
					'should be able to translate a query string with "not contains" to map and back to string',
					() => {
						const testQuery = '((not (contains(firstName, \'test\'))))';

						const translatedMap = ODataUtil.translateQueryToCriteria(testQuery);

						const translatedString = ODataUtil.buildQueryString([translatedMap]);

						expect(translatedString).toEqual(testQuery);
					}
				);
			}
		);
	}
);