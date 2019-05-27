import 'jest-dom/extend-expect';
import React from 'react';
import SegmentEdit from 'components/segment_edit/SegmentEdit.es';
import {cleanup, render} from 'react-testing-library';
import {SOURCES} from 'utils/constants.es';

const SOURCE_ICON_TESTID = 'source-icon';

describe('SegmentEdit', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {asFragment} = render(
			<SegmentEdit
				availableLocales={{
					en_US: ''
				}}
				defaultLanguageId='en_US'
				initialSegmentName={{
					en_US: 'Segment title'
				}}
				locale='en_US'
				redirect='/test-url'
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('should render with an analytics cloud icon', () => {
		const {icon, name} = SOURCES.ASAH_FARO_BACKEND;

		const {getByTestId} = render(
			<SegmentEdit
				availableLocales={{
					en_US: ''
				}}
				defaultLanguageId='en_US'
				initialSegmentName={{
					en_US: 'Segment title'
				}}
				locale='en_US'
				redirect='/test-url'
				source={name}
			/>
		);

		const image = getByTestId(SOURCE_ICON_TESTID);

		expect(image).toHaveAttribute('src', icon);
	});

	it('should render with a dxp icon', () => {
		const {icon, name} = SOURCES.DEFAULT;

		const {getByTestId} = render(
			<SegmentEdit
				availableLocales={{
					en_US: ''
				}}
				defaultLanguageId='en_US'
				initialSegmentName={{
					en_US: 'Segment title'
				}}
				locale='en_US'
				redirect='/test-url'
				source={name}
			/>
		);

		const image = getByTestId(SOURCE_ICON_TESTID);

		expect(image).toHaveAttribute('src', icon);
	});

	it('should render with edit buttons if the user has update permissions', () => {
		const hasUpdatePermission = true;

		const {asFragment} = render(
			<SegmentEdit
				availableLocales={{
					en_US: ''
				}}
				defaultLanguageId='en_US'
				hasUpdatePermission={hasUpdatePermission}
				initialSegmentName={{
					en_US: 'Segment title'
				}}
				locale='en_US'
				redirect='/test-url'
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('should render without edit buttons if the user does not have update permissions', () => {
		const hasUpdatePermission = false;

		const {asFragment} = render(
			<SegmentEdit
				availableLocales={{
					en_US: ''
				}}
				defaultLanguageId='en_US'
				hasUpdatePermission={hasUpdatePermission}
				initialSegmentName={{
					en_US: 'Segment title'
				}}
				locale='en_US'
				redirect='/test-url'
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('should render with given values', () => {
		const contributors = [
			{
				conjunctionId: 'and',
				conjunctionInputId: 'conjunction-input-1',
				initialQuery: "(value eq 'value')",
				inputId: 'input-id-for-backend-form',
				propertyKey: 'first-test-values-group'
			}
		];
		const hasUpdatePermission = true;

		const propertyGroups = [
			{
				entityName: 'First Test Values Group',
				name: 'First Test Values Group',
				properties: [
					{
						label: 'Value',
						name: 'value',
						options: [],
						selectEntity: null,
						type: 'string'
					}
				],
				propertyKey: 'first-test-values-group'
			}
		];

		const {asFragment, getByTestId} = render(
			<SegmentEdit
				availableLocales={{
					en_US: ''
				}}
				contributors={contributors}
				defaultLanguageId='en_US'
				hasUpdatePermission={hasUpdatePermission}
				initialSegmentName={{
					en_US: 'Segment title'
				}}
				locale='en_US'
				propertyGroups={propertyGroups}
				redirect='/test-url'
				showInEditMode={true}
			/>
		);

		expect(getByTestId(contributors[0].inputId).value).toBe(
			contributors[0].initialQuery
		);

		expect(asFragment()).toMatchSnapshot();
	});
});
