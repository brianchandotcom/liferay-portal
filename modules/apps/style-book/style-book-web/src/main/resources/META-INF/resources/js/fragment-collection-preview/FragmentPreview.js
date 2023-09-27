/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				{variations.length ? (
					variations.map((variation) => {
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
					})
				) : (
					<VariationPreview
						fragmentEntryKey={fragment.fragmentEntryKey}
						key={fragment.label}
						label={fragment.label}
						namespace={namespace}
						previewURL={fragment.previewURL}
						showLabel={false}
						variation={[]}
					/>
				)}
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
