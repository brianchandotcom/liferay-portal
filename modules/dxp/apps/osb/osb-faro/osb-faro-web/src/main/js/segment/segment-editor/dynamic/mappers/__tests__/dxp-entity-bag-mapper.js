import {createOrderIOMap} from 'shared/util/pagination';
import {getMapResultToProps, mapPropsToOptions} from '../dxp-entity-bag-mapper';

describe('DXPEntityBag Mapper', () => {
	it('should map props to options', () => {
		expect(
			mapPropsToOptions({
				channelId: '123',
				delta: 10,
				orderIOMap: createOrderIOMap('name', 'ASC'),
				page: 1,
				query: ''
			})
		).toEqual(
			expect.objectContaining({
				variables: {
					channelId: '123',
					keywords: '',
					size: 10,
					sort: {column: 'name', type: 'ASC'},
					start: 0
				}
			})
		);
	});

	it('should map results to props', () => {
		expect(
			getMapResultToProps('organizations')({
				organizations: {dxpEntities: [], total: 0}
			})
		).toEqual(
			expect.objectContaining({
				empty: true,
				items: [],
				total: 0
			})
		);
	});
});
