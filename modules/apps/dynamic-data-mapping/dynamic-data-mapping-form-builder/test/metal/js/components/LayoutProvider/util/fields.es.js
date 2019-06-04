import mockPages from 'mock/mockPages.es';
import {generateFieldName} from 'source/components/LayoutProvider/util/fields.es';

describe('LayoutProvider/util/fields', () => {
	describe('generateFieldName(pages, desiredName, currentName)', () => {
		it('should generate a name based on the desired name', () => {
			expect(generateFieldName(mockPages, 'New  Name!')).toEqual(
				'NewName'
			);
		});

		it('should generate an incremental name when desired name is already being used', () => {
			expect(generateFieldName(mockPages, 'radio')).toEqual('radio1');
		});

		it('should generate an incremental name when changing desired name to an already used one', () => {
			expect(generateFieldName(mockPages, 'radio!!')).toEqual('radio1');
		});

		it('should fallback to currentName when generated name is invalid', () => {
			expect(generateFieldName(mockPages, 'radio!', 'radio')).toEqual(
				'radio'
			);
		});
	});
});
