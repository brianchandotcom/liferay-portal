/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import Facet from '../../../src/main/resources/META-INF/resources/js/components/Facet';
import {RESOURCE, SELECTED_FACETS} from '../mocks/data';

import '@testing-library/jest-dom/extend-expect';

function renderFacets(props) {
	return render(
		<Facet
			facets={RESOURCE.facets}
			selectedFacets={{}}
			updateSelectedFacets={jest.fn()}
			{...props}
		/>
	);
}

describe('BlueprintsSearch', () => {
	it('renders the facet labels', () => {
		const {getByText} = renderFacets();

		RESOURCE.facets.map((item) => getByText(item.facetLabel));
	});

	it('renders the facets populated', () => {
		const {getByLabelText} = renderFacets({
			selectedFacets: SELECTED_FACETS,
		});

		SELECTED_FACETS['entryClassName'].map((item) =>
			getByLabelText(`${item.value}`)
		);
	});

	it('shows a dropdown of facet options when clicked on', () => {
		const {getByLabelText, getByText} = renderFacets({});

		const firstFacet = RESOURCE.facets[0];

		fireEvent.click(getByLabelText(firstFacet['facetLabel']));

		firstFacet['values'].map((item) =>
			expect(getByText(item.value)).toBeVisible()
		);
	});

	it('calls updateSelectedFacets when facets are clicked on', () => {
		const updateSelectedFacets = jest.fn();

		const {getAllByLabelText} = renderFacets({
			selectedFacets: SELECTED_FACETS,
			updateSelectedFacets,
		});

		fireEvent.click(getAllByLabelText('Remove', {exact: false})[0]);

		expect(updateSelectedFacets).toHaveBeenCalled();
	});
});
