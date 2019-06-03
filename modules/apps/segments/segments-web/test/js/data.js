import {CONJUNCTIONS, RELATIONAL_OPERATORS} from 'utils/constants.es';

const {AND, OR} = CONJUNCTIONS;
const {EQ} = RELATIONAL_OPERATORS;

function generateItems(times) {
	const items = [];

	for (let i = 0; i < times; i++) {
		items.push({
			operatorName: EQ,
			propertyName: 'firstName',
			value: 'test'
		});
	}

	return items;
}

export function mockCriteria(numOfItems) {
	return {
		conjunctionName: AND,
		groupId: 'group_01',
		items: generateItems(numOfItems)
	};
}

export function mockCriteriaNested() {
	return {
		conjunctionName: AND,
		groupId: 'group_01',
		items: [
			{
				conjunctionName: OR,
				groupId: 'group_02',
				items: [
					{
						conjunctionName: AND,
						groupId: 'group_03',
						items: [
							{
								conjunctionName: OR,
								groupId: 'group_04',
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
}
