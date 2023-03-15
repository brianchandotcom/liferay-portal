import {mapPropsToOptions, mapResultToProps} from '../touchpoint-list-query';

const context = {
	router: {
		params: {
			assetId: 'formId',
			title: 'AssetTitle',
			touchpoint: 'Any'
		}
	}
};

const data = {
	assetPages: [
		{
			assetId: 'https://www.liferay.com/digital-experience-platform',
			assetTitle: ''
		},
		{
			assetId: 'https://www.liferay.com/product/features/assets',
			assetTitle: ''
		},
		{
			assetId: 'https://www.liferay.com/company/gartner/thank-you',
			assetTitle: ''
		},
		{
			assetId:
				'https://www.liferay.com/resource?folderId=13811&title=Digital+Experience+Platforms+-+Designed+for+Digital+Transformation',
			assetTitle: 'https://www.liferay.com/company/gartner/thank-you'
		},
		{
			assetId:
				'https://www.liferay.com/resource?folderId=1645493&title=Advanced+Liferay+Architecture',
			assetTitle: 'https://www.liferay.com/company/gartner/thank-you'
		},
		{
			assetId:
				'https://www.liferay.com/resource?folderId=3292406&title=Enterprise+Subscription+Benefits',
			assetTitle: 'https://www.liferay.com/company/gartner/thank-you'
		},
		{
			assetId:
				'https://www.liferay.com/resource?folderId=13811&title=Three+Key+Strategies+for+Consistent+Customer+Experiences',
			assetTitle: 'https://www.liferay.com/company/gartner/thank-you'
		},
		{
			assetId:
				'https://www.liferay.com/resource?folderId=13811&title=Portal+Best+Practices',
			assetTitle: 'https://www.liferay.com/company/gartner/thank-you'
		},
		{
			assetId:
				'https://www.liferay.com/resource?folderId=13811&title=The+Evolving+Value+of+a+Portal',
			assetTitle: 'https://www.liferay.com/company/gartner/thank-you'
		},
		{
			assetId:
				'https://www.liferay.com/resource?folderId=1646951&title=Four+Types+of+Portals+That+Solve+Enterprise+Problems',
			assetTitle: 'https://www.liferay.com/company/gartner/thank-you'
		}
	]
};

describe('TouchpointsListQuery Mappers', () => {
	it('should extract items from result', () => {
		const props = mapResultToProps({data}, context);

		expect(props).toMatchSnapshot();
	});

	it('should include assetId in options', () => {
		const options = mapPropsToOptions({
			assetType: 'forms',
			filters: {},
			rangeSelectors: {rangeKey: '7'},
			router: context.router
		});

		expect(options).toEqual({
			variables: {
				assetId: 'formId',
				assetType: 'FORMS',
				devices: 'Any',
				location: 'Any',
				rangeEnd: null,
				rangeKey: 7,
				rangeStart: null,
				title: 'AssetTitle',
				touchpoint: null
			}
		});
	});
});
