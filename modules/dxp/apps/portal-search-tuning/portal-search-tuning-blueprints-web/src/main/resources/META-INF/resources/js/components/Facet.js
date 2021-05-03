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

import ClayForm from '@clayui/form';
import {PropTypes} from 'prop-types';
import React, {useState} from 'react';

import MultiSelect from './MultiSelect';

const LOCATOR = {
	label: 'text',
	value: 'value',
};

function FacetInput({
	facetLabel,
	items,
	onItemsChange,
	parameterName,
	values = [],
}) {
	const [value, setValue] = useState('');

	return (
		<ClayForm.Group>
			<label>{facetLabel}</label>

			<MultiSelect
				aria-label={facetLabel}
				disabledClearAll
				inputName={parameterName}
				inputValue={value}
				items={items}
				locator={LOCATOR}
				onChange={setValue}
				onItemsChange={onItemsChange}
				sourceItems={values}
			/>
		</ClayForm.Group>
	);
}

FacetInput.propTypes = {
	facetLabel: PropTypes.string,
	items: PropTypes.arrayOf(PropTypes.object),
	onItemsChange: PropTypes.func,
	parameterName: PropTypes.string,
	values: PropTypes.arrayOf(PropTypes.object),
};

export default function Facet({facets, onChange, selectedFacets}) {
	return (
		<div className="search-facets">
			{Object.keys(facets).map((facet, index) => (
				<FacetInput
					facetLabel={facets[facet].label}
					items={selectedFacets[facets[facet].parameterName]}
					key={index}
					onItemsChange={(items) =>
						onChange(facets[facet].parameterName, items)
					}
					{...facets[facet]}
				/>
			))}
		</div>
	);
}

Facet.propTypes = {
	facets: PropTypes.arrayOf(PropTypes.object),
	onChange: PropTypes.func,
	selectedFacets: PropTypes.object,
};
