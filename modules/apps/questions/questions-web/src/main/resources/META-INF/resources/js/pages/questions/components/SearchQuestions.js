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

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React from 'react';

const SearchQuestions = ({
	debounceCallback,
	loading,
	questions,
	search,
	slugToText,
}) => {
	return (
		<ClayInput.GroupItem>
			<ClayInput
				autoFocus={!!search}
				className="bg-transparent form-control input-group-inset input-group-inset-after"
				defaultValue={(search && slugToText(search)) || ''}
				disabled={
					!search &&
					questions &&
					questions.items &&
					!questions.items.length
				}
				onChange={(event) => debounceCallback(event.target.value)}
				placeholder={Liferay.Language.get('search')}
				type="text"
			/>

			<ClayInput.GroupInsetItem
				after
				className="bg-transparent"
				tag="span"
			>
				{loading && (
					<button
						className="btn btn-monospaced btn-unstyled"
						type="button"
					>
						<ClayLoadingIndicator className="mb-0 mt-0" small />
					</button>
				)}

				{!loading &&
					((!!search && (
						<ClayButtonWithIcon
							displayType="unstyled"
							onClick={() => {
								debounceCallback('');
							}}
							symbol="times-circle"
						/>
					)) || (
						<ClayButtonWithIcon
							displayType="unstyled"
							symbol="search"
							type="search"
						/>
					))}
			</ClayInput.GroupInsetItem>
		</ClayInput.GroupItem>
	);
};

export default SearchQuestions;
