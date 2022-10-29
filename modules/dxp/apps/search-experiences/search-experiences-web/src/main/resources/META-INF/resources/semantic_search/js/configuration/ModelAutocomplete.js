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

import ClayAutocomplete from '@clayui/autocomplete';
import {useResource} from '@clayui/data-provider';
import {FocusScope} from '@clayui/shared';
import React, {useState} from 'react';

/**
 * When Hugging Face is selected as the Sentence Transform Provider, this input
 * will query for applicable models.
 * This can be found on: System Settings > Search Experiences > Semantic Search
 */
function ModelAutocomplete({
	label,
	name,
	onBlur,
	onChange,
	required,
	value = '',
}) {
	const [networkStatus, setNetworkStatus] = useState(4);

	const {resource} = useResource({
		fetchPolicy: 'cache-first',
		link: `${window.location.origin}${Liferay.ThemeDisplay.getPathContext()}
		/o/search-experiences-rest/v1.0/sentence-transformer/ml-models`,
		onNetworkStatusChange: setNetworkStatus,
		variables: {
			limit: 20,
			pipeline_tag: 'feature-extraction',
			query: value,
		},
	});

	return (
		<FocusScope>
			<ClayAutocomplete
				aria-labelledby={label}
				id={name}
				items={(resource?.items || []).map(({modelId}) => modelId)}
				loadingState={networkStatus}
				messages={{
					loading: Liferay.Language.get('loading'),
					notFound: Liferay.Language.get('no-results-found'),
				}}
				name={name}
				onBlur={onBlur}
				onChange={onChange}
				onItemsChange={() => {}}
				required={required}
				value={value}
			>
				{(item) => (
					<ClayAutocomplete.Item key={item}>
						{item}
					</ClayAutocomplete.Item>
				)}
			</ClayAutocomplete>
		</FocusScope>
	);
}

export default ModelAutocomplete;
