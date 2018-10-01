'use strict';

import groupBy from '../../../src/main/resources/META-INF/resources/liferay/util/group_by.es';

describe(
	'Liferay.Util.groupBy',
	()	=>	{
		it(
			'should group by a function',
			()	=>	{
				const example = [6.1, 4.2, 6.3];

				const result = {
					'4': [4.2],
					'6': [6.1, 6.3]
				};

				expect(groupBy(example, Math.floor)).toEqual(result);
			}
		);

		it(
			'should group by a iteratee property',
			()	=>	{
				const example = ['one', 'two', 'three'];

				const result = {
					'3': ['one', 'two'],
					'5': ['three']
				};

				expect(groupBy(example, 'length')).toEqual(result);
			}
		);

		it(
			'should group by a object property',
			()	=>	{
				const list = [
					{
						age: 33,
						name: 'Alex'
					},
					{
						age: 40,
						name: 'Dave'
					},
					{
						age: 35,
						name: 'Dan'
					},
					{
						age: 25,
						name: 'Irene'
					},
					{
						age: 44,
						name: 'Kurt'
					},
					{
						age: 33,
						name: 'Josh'
					}
				];

				const result = {
					'25': [
						{
							'age': 25,
							'name': 'Irene'
						}
					],
					'33': [
						{
							'age': 33,
							'name': 'Alex'
						},
						{
							'age': 33,
							'name': 'Josh'
						}
					],
					'35': [
						{
							'age': 35,
							'name': 'Dan'
						}
					],
					'40': [
						{
							'age': 40,
							'name': 'Dave'
						}
					],
					'44': [
						{
							'age': 44,
							'name': 'Kurt'
						}
					]
				};

				expect(groupBy(list, 'age')).toEqual(result);
			}
		);

		it(
			'should group by a object property if at least one of the objet does not have the property',
			()	=>	{
				const list = [
					{
						age: 40,
						country: 'USA',
						name: 'Dave'
					},
					{
						age: 35,
						country: 'USA',
						name: 'Dan'
					},
					{
						age: 44,
						name: 'Kurt'
					}
				];

				const result = {
					'USA': [
						{
							'age': 40,
							'country': 'USA',
							'name': 'Dave'
						},
						{
							'age': 35,
							'country': 'USA',
							'name': 'Dan'
						}
					],
					'undefined': [
						{'age': 44,
							'name': 'Kurt'
						}
					]
				};

				expect(groupBy(list, 'country')).toEqual(result);
			}
		);
	}
);