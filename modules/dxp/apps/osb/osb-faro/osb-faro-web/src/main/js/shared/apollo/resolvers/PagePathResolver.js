export default () => ({
	__typename: 'PagePath',
	canonicalUrl: 'https://www.liferay.com',
	followingPagePathNodes: [
		{
			__typename: 'PagePathNode',
			canonicalUrl: 'https://www.site1.com',
			title: 'Site 1',
			views: 10000
		},
		{
			__typename: 'PagePathNode',
			canonicalUrl: 'https://www.site2.com',
			title: 'Site 2',
			views: 10000
		},
		{
			__typename: 'PagePathNode',
			canonicalUrl: 'https://www.site3.com',
			title: 'Site 3',
			views: 5000
		},
		{
			__typename: 'PagePathNode',
			canonicalUrl: null,
			title: 'others',
			views: 500
		},
		{
			__typename: 'PagePathNode',
			canonicalUrl: null,
			title: 'drop-offs',
			views: 8000
		}
	],
	previousPagePathNodes: [
		{
			__typename: 'PagePathNode',
			canonicalUrl: 'https://www.google.com',
			title: 'Google',
			views: 10000
		},
		{
			__typename: 'PagePathNode',
			canonicalUrl: 'https://www.facebook.com',
			title: 'Facebook',
			views: 10000
		},
		{
			__typename: 'PagePathNode',
			canonicalUrl: 'https://www.instagram.com',
			title: 'Instagram',
			views: 8000
		},
		{
			__typename: 'PagePathNode',
			canonicalUrl: null,
			title: 'direct',
			views: 5000
		},
		{
			__typename: 'PagePathNode',
			canonicalUrl: null,
			title: 'others',
			views: 1000
		}
	],
	title: 'Liferay Home Page',
	views: 100000
});
