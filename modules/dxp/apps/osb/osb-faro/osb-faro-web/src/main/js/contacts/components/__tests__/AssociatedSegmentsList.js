import AssociatedSegmentsList from '../AssociatedSegmentsList';
import Promise from 'metal-promise';
import {renderWithStore} from 'test/mock-store';

describe('AssociatedSegmentsList', () => {
	it('should render', () => {
		const component = renderWithStore(AssociatedSegmentsList, {
			channelId: '123123',
			dataSourceFn: () => Promise.resolve({}),
			groupId: '23',
			id: 'test',
			total: 2
		});

		expect(
			component.find(AssociatedSegmentsList).shallow()
		).toMatchSnapshot();
	});
});
