import {
	dataSource,
	getDistributionSchema,
	getLayoutSchema,
	individual,
	individuals,
	segment,
	segments
} from 'shared/middleware/schema';
import {EntityTypes} from 'shared/util/constants';
import {
	mockIndividual,
	mockLayout,
	mockLiferayDataSource,
	mockSegment
} from 'test/data';
import {normalize} from 'normalizr';

describe('Schema', () => {
	it('should normalize an individual', () => {
		const action = mockIndividual('foo');

		expect(normalize(action, individual)).toMatchSnapshot();
	});

	it('should normalize an array of individuals', () => {
		const action = [mockIndividual('foo'), mockIndividual('bar')];

		expect(normalize(action, individuals)).toMatchSnapshot();
	});

	it('should normalize a segment', () => {
		const action = mockSegment('foo');

		expect(normalize(action, segment)).toMatchSnapshot();
	});

	it('should normalize an array of segments', () => {
		const action = [mockSegment('foo'), mockSegment('bar')];

		expect(normalize(action, segments)).toMatchSnapshot();
	});

	it('should normalize a liferay data-source', () => {
		const payload = mockLiferayDataSource(2);

		expect(normalize(payload, dataSource)).toMatchObject({
			entities: {
				dataSources: {
					2: {
						data: payload
					}
				}
			}
		});
	});

	describe('getLayoutSchema', () => {
		it('should normalize a layout schema with a faroEntity of individual', () => {
			const action = mockLayout(1, mockIndividual());

			expect(
				normalize(action, getLayoutSchema(EntityTypes.Individual))
			).toMatchSnapshot();
		});

		it('should normalize a layout schema with a faroEntity of segment', () => {
			const action = mockLayout(2);

			expect(
				normalize(
					action,
					getLayoutSchema(EntityTypes.IndividualsSegment)
				)
			).toMatchSnapshot();
		});
	});

	describe('getDistributionSchema', () => {
		it('should normalize distribution data', () => {
			const payload = [{count: 2, values: ['foo', 'bar']}];

			expect(normalize(payload, getDistributionSchema(3))).toMatchObject({
				entities: {
					distributions: {
						3: {
							data: payload
						}
					}
				}
			});
		});
	});
});
