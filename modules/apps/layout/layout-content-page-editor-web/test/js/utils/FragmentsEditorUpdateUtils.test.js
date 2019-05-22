/* globals expect */
import {
	decodeId,
	encodeAssetId
} from '../../../src/main/resources/META-INF/resources/js/utils/FragmentsEditorIdUtils.es';

describe('FragmentsEditorIdUtils', () => {
	describe('#decodeId', () => {
		it('should decode a given base64 encoded object', () => {
			const obj = {
				a: 'a',
				b: 2
			};
			const str = btoa(JSON.stringify(obj));

			expect(decodeId(str)).toEqual(obj);
		});

		it('should throw an error when string is not base64 encoded', () => {
			const str = 'not base64 encoded';

			expect(() => decodeId(str)).toThrow();
		});

		it('should throw an error when string is not an object', () => {
			const str = btoa('not an object');

			expect(() => decodeId(str)).toThrow();
		});
	});

	describe('#encodeAssetId', () => {
		it('should add an encoded id to a given asset object', () => {
			const asset = {
				assetEntryTitle: 'My asset',
				classNameId: 11111,
				classPK: 22222
			};

			expect(encodeAssetId(asset)).toMatchSnapshot();
		});

		it('should throw an error when input is not an object', () => {
			const input = 'not an object';

			expect(() => encodeAssetId(input)).toThrowError();
		});

		it('should throw an error when input is not an asset', () => {
			const input = {
				a: 'a',
				b: 2
			};
			expect(() => encodeAssetId(input)).toThrowError();
		});
	});
});
