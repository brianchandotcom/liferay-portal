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

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {fetch, objectToFormData} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState, useCallback} from 'react';
import {useDebounceCallback} from './useDebounceCallback.es';

const MAX_ITEMS_TO_SHOW = 10;

function LayoutFinder(props) {
	const [keywords, setKeywords] = useState('');
	const [layouts, setLayouts] = useState([]);
	const [loading, setLoading] = useState(false);
	const [totalCount, setTotalCount] = useState(0);

	const handleFormSubmit = useCallback(event => {
		event.preventDefault();
		event.stopPropagation();
	}, []);

	const [updatePageResults, cancelUpdatePageResults] = useDebounceCallback(
		newKeywords => {
			fetch(props.findLayoutsURL, {
				body: objectToFormData({
					[`${props.namespace}keywords`]: newKeywords
				}),
				method: 'post'
			})
				.then(response => {
					return response.ok
						? response.json()
						: {
								layouts: [],
								totalCount: 0
						  };
				})
				.then(response => {
					setLoading(false);
					setLayouts(response.layouts);
					setTotalCount(response.totalCount);
				});
		},
		1000
	);

	const handleOnChange = useCallback(
		event => {
			const newKeywords = event.target.value;

			setKeywords(newKeywords);

			const tree = document.querySelector(
				`[data-treeid="${props.treeId}"]`
			);

			if (newKeywords.length == 0) {
				setLoading(false);
				setLayouts([]);
				setTotalCount(0);

				cancelUpdatePageResults();

				tree.classList.remove('hide');
			} else {
				setLoading(true);
				updatePageResults(newKeywords);

				if (!tree.classList.contains('hide')) {
					tree.classList.add('hide');
				}
			}
		},
		[cancelUpdatePageResults, props.treeId, updatePageResults]
	);

	return (
		<div className="layout-finder">
			<form onSubmit={handleFormSubmit} role="search">
				<label
					className="sr-only"
					htmlFor={`${props.namespace}-layout-finder-page-input`}
				>
					{`${Liferay.Language.get('page-name')}: `}
				</label>

				<input
					autoComplete="off"
					autoFocus
					className="form-control"
					id={`${props.namespace}-layout-finder-page-input`}
					onChange={handleOnChange}
					placeholder={Liferay.Language.get(
						'start-typing-to-find-a-page'
					)}
					type="text"
					value={keywords}
				/>
			</form>

			{totalCount > 0 && (
				<>
					<nav className="mt-2">
						{layouts.map(
							(layout, layoutIndex) =>
								layoutIndex < MAX_ITEMS_TO_SHOW && (
									<a
										className="d-block p-2 text-break"
										href={layout.url}
										key={layout.url}
									>
										{layout.name}
									</a>
								)
						)}
					</nav>

					{totalCount > MAX_ITEMS_TO_SHOW && (
						<div>
							<div className="mt-2 text-center">
								{Liferay.Util.sub(
									Liferay.Language.get(
										'there-are-x-more-results-narrow-your-search'
									),
									totalCount - MAX_ITEMS_TO_SHOW
								)}
							</div>

							<div className="text-center">
								<a
									className="text-primary"
									href={`${props.administrationPortletURL}&${props.administrationPortletNamespace}keywords=${keywords}`}
								>
									{Liferay.Language.get(
										'view-in-page-administration'
									)}
								</a>
							</div>
						</div>
					)}
				</>
			)}

			{loading && (
				<ClayLoadingIndicator className="mt-3 mb-0" light small />
			)}

			{totalCount === 0 && !loading && keywords.length > 1 && (
				<div className="text-center mt-3">
					{Liferay.Language.get('page-not-found')}
				</div>
			)}
		</div>
	);
}

LayoutFinder.propTypes = {
	administrationPortletNamespace: PropTypes.string,
	administrationPortletURL: PropTypes.string,
	findLayoutsURL: PropTypes.string,
	namespace: PropTypes.string,
	treeId: PropTypes.string,
	viewInPageAdministrationURL: PropTypes.string
};

export default function(props) {
	return <LayoutFinder {...props} />;
}
