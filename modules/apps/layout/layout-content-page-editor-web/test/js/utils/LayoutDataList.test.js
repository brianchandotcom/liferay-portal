import {containsFragmentEntryLinkId} from '../../../src/main/resources/META-INF/resources/js/utils/LayoutDataList.es';

const LAYOUT_DATA_PERSONALIZATION = [
	{
		segmentsExperienceId: 'segmentsExperienceId1',
		layoutData: {
			"structure": [
				{
					"columns": [
						{
							"fragmentEntryLinkIds": [
								"37212",
								"37213"
							],
						}
					]
				},
				{
					"columns": [
						{
							"fragmentEntryLinkIds": ["37218"]
						},
						{
							"fragmentEntryLinkIds": ["37215"]
						}
					]
				}
			]
		}
	}, {
		segmentsExperienceId: 'segmentsExperienceId2',
		layoutData: {
			"structure": [
				{
					"columns": [
						{
							"fragmentEntryLinkIds": [
								"37212",
								"37213"
							]
						}
					]
				},
				{
					"columns": [
						{
							"fragmentEntryLinkIds": ["37214"]
						},
						{
							"fragmentEntryLinkIds": ["37215"]
						}
					]
				}
			]
		}
	}
]

describe('confirmFragmnetEntryLinkIdLayoutDataList ', () => {
	test('should confirm fragmentEntryLinkId presence in a LayoutData different than the one selected', () => {
		expect(
			containsFragmentEntryLinkId(
				LAYOUT_DATA_PERSONALIZATION,
				'37214',
				'segmentsExperienceId1'
			)
		).toBe(true);

		expect(
			containsFragmentEntryLinkId(
				LAYOUT_DATA_PERSONALIZATION,
				'37215',
				'segmentsExperienceId1'
			)
		).toBe(true);
	});

	test('should confirm fragmentEntryLinkId presence in a LayoutData', () => {
		expect(
			containsFragmentEntryLinkId(
				LAYOUT_DATA_PERSONALIZATION,
				'37214'
			)
		).toBe(true);
	});

	test('should confirm fragmentEntryLinkId ausence in a LayoutData different than the one selected', () => {
		expect(
			containsFragmentEntryLinkId(
				LAYOUT_DATA_PERSONALIZATION,
				'37214',
				'segmentsExperienceId2'
			)
		).toBe(false);
	})
})