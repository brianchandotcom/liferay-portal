'use strict';

import groupBy from '../../../src/main/resources/META-INF/resources/liferay/util/groupBy.es';

describe(

	'Liferay.Util.groupBy',
	()	=>	{
		it(
			'should group by a function',
			()	=>	{
				var example = [6.1, 4.2, 6.3];
				var result = {
					'4': [4.2],
					'6': [6.1, 6.3]};

				expect(groupBy(example, Math.floor)).toEqual(result);
			}
		);

		it(
			'should group by a iteratee property',
			()	=>	{
				var example = ['one', 'two', 'three'];
				var result = {
					'3': ['one', 'two'],
					'5': ['three']};

				expect(groupBy(example, 'length')).toEqual(result);
			}
		);

		it(
			'should group by a object property',
			()	=>	{
				var list = [
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

				var result = {
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
					]};

				expect(groupBy(list, 'age')).toEqual(result);
			}
		);

		it(
			'should group by a object property if at least one of the objet does not have the property',
			()	=>	{
				var list = [
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

				var result = {
					'USA': [
						{
							'age': 40,
							'country': 'USA',
							'name': 'Dave'},
						{
							'age': 35,
							'country': 'USA',
							'name': 'Dan'}
					],
					'undefined': [
						{'age': 44,
							'name': 'Kurt'
						}
					]};

				expect(groupBy(list, 'country')).toEqual(result);
			}
		);
	}
);