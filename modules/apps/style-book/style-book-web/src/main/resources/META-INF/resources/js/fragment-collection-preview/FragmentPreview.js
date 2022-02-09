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

import React, {useMemo} from 'react';

import {VariationPreview} from './VariationPreview';
import {combine} from './combine';

export function FragmentPreview({fragment, namespace}) {
	const variations = useMemo(() => getFragmentVariations(fragment), [
		fragment,
	]);

	return (
		<section className="fragment-preview p-5">
			<div className="cadmin">
				<h3 className="mb-3">{fragment.label}</h3>
			</div>

			<div
				className="fragment-preview__list"
				style={{'--variation-count': variations.length}}
			>
				{variations.map((variation) => {
					const label = `${fragment.label} ${variation
						.map((part) => part.label)
						.join(' ')}`;

					return (
						<VariationPreview
							fragmentEntryKey={fragment.fragmentEntryKey}
							key={label}
							label={label}
							namespace={namespace}
							previewURL={fragment.previewURL}
							showLabel={variations.length > 1}
							variation={variation}
						/>
					);
				})}
			</div>
		</section>
	);
}

function getFragmentVariations(fragment) {
	const configurationValues =
		fragment.configuration.fieldSets?.flatMap((fieldSet) =>
			fieldSet.fields
				.filter(
					(field) =>
						field.type === 'select' &&
						Array.isArray(field.typeOptions?.validValues)
				)
				.map((field) =>
					field.typeOptions.validValues.map((validValue) => ({
						label: validValue.label || validValue.value,
						name: field.name,
						value: validValue.value,
					}))
				)
		) || [];

	return combine(...configurationValues);
}
