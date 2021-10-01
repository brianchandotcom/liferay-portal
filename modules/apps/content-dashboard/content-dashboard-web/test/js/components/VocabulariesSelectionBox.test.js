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

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import VocabulariesSelectionBox from '../../../src/main/resources/META-INF/resources/js/VocabulariesSelectionBox';

const mockProps = {
	leftBoxName: 'availableAssetVocabularyNames',
	leftList: [
		{key: 'extension', value: 'Extension (My company)', site: 1},
		{key: 'stage', value: 'Stage (Global)', site: 0},
		{key: 'topic', value: 'Topic (Global)', site: 0},
	],
	portletNamespace:
		'_com_liferay_content_dashboard_web_portlet_ContentDashboardAdminPortlet_',
	rightBoxName: 'currentAssetVocabularyNames',
	rightList: [
		{key: 'audience', value: 'Audience (Global)', site: 0},
		{key: 'region', value: 'Region (Liferay)', site: 2},
	],
};

const mockPropsWithoutSelected = {
	leftBoxName: 'availableAssetVocabularyNames',
	leftList: [
		{key: 'extension', value: 'Extension (My company)', site: 1},
		{key: 'stage', value: 'Stage (Global)', site: 0},
		{key: 'topic', value: 'Topic (Global)', site: 0},
		{key: 'audience', value: 'Audience (Global)', site: 0},
		{key: 'region', value: 'Region (Liferay)', site: 2},
	],
	portletNamespace:
		'_com_liferay_content_dashboard_web_portlet_ContentDashboardAdminPortlet_',
	rightBoxName: 'currentAssetVocabularyNames',
	rightList: [],
};

describe('VocabulariesSelectionBox', () => {
	beforeEach(() => {
		cleanup();
	});

	it('renders a VocabulariesSelectionBox with selected and not selected vocabularies', () => {
		const {container, getByText} = render(
			<VocabulariesSelectionBox {...mockProps} />
		);

		expect(getByText('Extension (My company)')).toBeInTheDocument();
		expect(getByText('Audience (Global)')).toBeInTheDocument();

		const availableVocabularies = container.querySelectorAll(
			'#availableAssetVocabularyNames option'
		);
		const selectedVocabularies = container.querySelectorAll(
			'#_com_liferay_content_dashboard_web_portlet_ContentDashboardAdminPortlet_currentAssetVocabularyNames option'
		);

		expect(availableVocabularies.length).toBe(3);
		expect(selectedVocabularies.length).toBe(2);
	});

	it('renders vocabularies from other sites disabled when we have a vocabulary from another site (not Global) selected', () => {
		const {container} = render(<VocabulariesSelectionBox {...mockProps} />);

		const disabledVocabularies = container.querySelectorAll(
			"#availableAssetVocabularyNames option[value='extension']:disabled"
		);
		expect(disabledVocabularies.length).toBe(1);
	});

	it('moves a vocabulary from left to right dislabing vocabularies from other sites', () => {
		const {container, getByText} = render(
			<VocabulariesSelectionBox {...mockPropsWithoutSelected} />
		);

		const availableVocabularies = container.querySelectorAll(
			'#availableAssetVocabularyNames option'
		);
		const selectedVocabularies = container.querySelectorAll(
			'#_com_liferay_content_dashboard_web_portlet_ContentDashboardAdminPortlet_currentAssetVocabularyNames option'
		);

		expect(availableVocabularies.length).toBe(5);
		expect(selectedVocabularies.length).toBe(0);

		fireEvent(
			getByText('Extension (My company)'),
			new MouseEvent('click', {
				bubbles: true,
				cancelable: true,
			})
		);
		const availableSelect = container.querySelector(
			'#availableAssetVocabularyNames'
		);
		fireEvent.change(availableSelect, {target: {value: 'extension'}});
		fireEvent.click(container.querySelector('.transfer-button-ltr'));

		const availableVocabulariesAfterLTR = container.querySelectorAll(
			'#availableAssetVocabularyNames option'
		);
		const selectedVocabulariesAfterLTR = container.querySelectorAll(
			'#_com_liferay_content_dashboard_web_portlet_ContentDashboardAdminPortlet_currentAssetVocabularyNames option'
		);

		expect(availableVocabulariesAfterLTR.length).toBe(4);
		expect(selectedVocabulariesAfterLTR.length).toBe(1);

		// Region  vocabulary must be disabled now becasue is from another site

		const disabledVocabularies = container.querySelectorAll(
			"#availableAssetVocabularyNames option[value='extension']:disabled"
		);
		expect(disabledVocabularies.length).toBe(1);
	});

	it('moves a vocabulary from right to left enabling vocabularies from other sites', () => {
		const {container, getByText} = render(
			<VocabulariesSelectionBox {...mockProps} />
		);

		const selectedSelect = container.querySelector(
			'#_com_liferay_content_dashboard_web_portlet_ContentDashboardAdminPortlet_currentAssetVocabularyNames'
		);
		fireEvent.change(selectedSelect, {target: {value: 'region'}});
		fireEvent.click(container.querySelector('.transfer-button-rtl'));

		const availableVocabulariesAfterRTL = container.querySelectorAll(
			'#availableAssetVocabularyNames option'
		);
		const selectedVocabulariesAfterRTL = container.querySelectorAll(
			'#_com_liferay_content_dashboard_web_portlet_ContentDashboardAdminPortlet_currentAssetVocabularyNames option'
		);

		expect(availableVocabulariesAfterRTL.length).toBe(4);
		expect(selectedVocabulariesAfterRTL.length).toBe(1);

		// There must be no disbled vocabularies because we have only one vocabulary selected
		// and it is from Global site

		const disabledVocabularies = container.querySelectorAll(
			"#availableAssetVocabularyNames option[value='extension']:disabled"
		);
		expect(disabledVocabularies.length).toBe(0);
	});
});
