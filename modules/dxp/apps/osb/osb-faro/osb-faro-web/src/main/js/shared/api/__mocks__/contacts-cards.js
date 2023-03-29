import Promise from 'metal-promise';
import {mockCardTemplate, mockFieldMapping, mockSegment} from 'test/data';

export const preview = jest.fn(() =>
	Promise.resolve({
		contactsCardData: {individualFieldDistribution: []},
		contactsCardTemplate: mockCardTemplate(0, {
			fieldMapping: mockFieldMapping(0, {
				dataSourceFieldNames: {AB78DSF: 'jobTitle'}
			})
		}),
		contactsEntity: mockSegment()
	})
);
