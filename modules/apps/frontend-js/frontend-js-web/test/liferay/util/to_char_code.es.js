'use strict';

import toCharCode from '../../../src/main/resources/META-INF/resources/liferay/util/to_char_code.es';

describe('Liferay.Util.toCharCode', () => {
	it('should return string', () => {
		const result = toCharCode('liferay');

		expect(typeof result).toBe('string');
		expect(result).toMatchSnapshot();
	});
});
