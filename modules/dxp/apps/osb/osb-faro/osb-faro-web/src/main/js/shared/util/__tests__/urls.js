import {getUrl} from '../urls';

describe('Url Utils', () => {
	describe('getUrl', () => {
		it('should return the url for touchpoint', () => {
			const path =
				'/workspace/:groupId/pages/known-individuals/:touchpoint/:title';
			const router = {
				params: {
					groupId: '32719',
					title: 'my page',
					touchpoint: 'http://mypage.com/'
				},
				query: {
					rangeKey: '30'
				}
			};

			expect(getUrl(path, router)).toEqual(
				'/workspace/32719/pages/known-individuals/http%3A%2F%2Fmypage.com%2F/my%20page?rangeKey=30'
			);
		});

		it('should return the url for assets', () => {
			const path =
				'/workspace/:groupId/assets/blogs/:assetId/known-individuals/:touchpoint/:title';
			const router = {
				params: {
					assetId: '123',
					groupId: '32719',
					title: 'my asset',
					touchpoint: 'Any'
				},
				query: {
					rangeKey: '30'
				}
			};

			expect(getUrl(path, router)).toEqual(
				'/workspace/32719/assets/blogs/123/known-individuals/Any/my%20asset?rangeKey=30'
			);
		});
	});
});
