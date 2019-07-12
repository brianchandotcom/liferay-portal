/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render, fireEvent, wait} from '@testing-library/react';
import SegmentEdit from 'components/segment_edit/SegmentEdit.es';
import React from 'react';
import {SOURCES} from 'utils/constants.es';

const SOURCE_ICON_TESTID = 'source-icon';

describe('SegmentEdit', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {asFragment} = render(
			<SegmentEdit
				availableLocales={{
					en_US: ''
				}}
				defaultLanguageId="en_US"
				initialSegmentName={{
					en_US: 'Segment title'
				}}
				locale="en_US"
				redirect="/test-url"
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with an analytics cloud icon', () => {
		const {icon, name} = SOURCES.ASAH_FARO_BACKEND;

		const {getByTestId} = render(
			<SegmentEdit
				availableLocales={{
					en_US: ''
				}}
				defaultLanguageId="en_US"
				initialSegmentName={{
					en_US: 'Segment title'
				}}
				locale="en_US"
				redirect="/test-url"
				source={name}
			/>
		);

		const image = getByTestId(SOURCE_ICON_TESTID);

		expect(image).toHaveAttribute('src', icon);
	});

	it('renders with a dxp icon', () => {
		const {icon, name} = SOURCES.DEFAULT;

		const {getByTestId} = render(
			<SegmentEdit
				availableLocales={{
					en_US: ''
				}}
				defaultLanguageId="en_US"
				initialSegmentName={{
					en_US: 'Segment title'
				}}
				locale="en_US"
				redirect="/test-url"
				source={name}
			/>
		);

		const image = getByTestId(SOURCE_ICON_TESTID);

		expect(image).toHaveAttribute('src', icon);
	});

	it('renders with edit buttons if the user has update permissions', () => {
		const hasUpdatePermission = true;

		const {asFragment} = render(
			<SegmentEdit
				availableLocales={{
					en_US: ''
				}}
				defaultLanguageId="en_US"
				hasUpdatePermission={hasUpdatePermission}
				initialSegmentName={{
					en_US: 'Segment title'
				}}
				locale="en_US"
				redirect="/test-url"
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders without edit buttons if the user does not have update permissions', () => {
		const hasUpdatePermission = false;

		const {asFragment} = render(
			<SegmentEdit
				availableLocales={{
					en_US: ''
				}}
				defaultLanguageId="en_US"
				hasUpdatePermission={hasUpdatePermission}
				initialSegmentName={{
					en_US: 'Segment title'
				}}
				locale="en_US"
				redirect="/test-url"
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with given values', () => {
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
				defaultLanguageId="en_US"
				hasUpdatePermission={hasUpdatePermission}
				initialSegmentName={{
					en_US: 'Segment title'
				}}
				locale="en_US"
				propertyGroups={propertyGroups}
				redirect="/test-url"
				showInEditMode={true}
			/>
		);

		expect(getByTestId(contributors[0].inputId).value).toBe(
			contributors[0].initialQuery
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('redirects when cancelling without any edition', () => {
		const redirect = '/test-url';
		const mockConfirm = jest.fn();
		const mockNavigate = jest.fn();
		window.Liferay.Util.navigate = mockNavigate;
		window.confirm = mockConfirm;

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

		const {getByText} = render(
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
				redirect={redirect}
				showInEditMode={true}
			/>
		);

		const cancelButton = getByText('cancel');

		expect(cancelButton).not.toBe(null);

		fireEvent.click(cancelButton);

		expect(mockNavigate).toHaveBeenCalledTimes(1);
		expect(mockNavigate).toHaveBeenCalledWith(redirect);
		expect(mockConfirm).toHaveBeenCalledTimes(0);
	});

	it('redirects when cancelling after title edition', done => {
		const redirect = '/test-url';
		const mockConfirm = jest.fn();
		const mockNavigate = jest.fn();
		window.Liferay.Util.navigate = mockNavigate;
		window.confirm = mockConfirm;

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

		const {getByTestId, getByText} = render(
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
				redirect={redirect}
				showInEditMode={true}
			/>
		);

		const localizedInput = getByTestId('localized-main-input');
		const cancelButton = getByText('cancel');

		fireEvent.change(localizedInput, {target: {value: 'A'}});

		wait(() => expect(localizedInput.value).toBe('A')).then(function() {
			expect(cancelButton).not.toBe(null);

			fireEvent.click(cancelButton);

			expect(mockNavigate).toHaveBeenCalledTimes(0);

			expect(mockConfirm).toHaveBeenCalledTimes(1);
			expect(mockConfirm).toHaveBeenCalledWith(
				'criteria-cancel-confirmation-message'
			);
			done();
		});
	});
});
