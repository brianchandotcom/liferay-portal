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
import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {PropTypes} from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

import {fetchResponse} from './../utils/api';

const CHARACTER_MIN = 2;

export default function SearchBar({handleSubmit, suggestionsURL}) {
	const containerRef = useRef(null);
	const [value, setValue] = useState('');
	const [state, setState] = useState({
		error: false,
		loading: false,
	});
	const [view, setView] = useState(false);
	const [resource, setResource] = useState({});

	const timeout = useRef();

	function _handleKeyDown(event) {
		if (event.key === 'Enter') {
			const trimValue = event.currentTarget.value.trim();

			setValue(trimValue);
			setView(false);
			handleSubmit(trimValue);
		}
	}

	const _hasResults = () =>
		!!(resource && resource.suggestions && resource.suggestions.length);

	useEffect(() => {
		setResource({});

		if (value.length >= CHARACTER_MIN) {
			setState({error: false, loading: true});

			clearTimeout(timeout.current);

			timeout.current = setTimeout(
				() =>
					fetchResponse(suggestionsURL, {q: value})
						.then((data) => {
							setResource(data);
							setState({
								error: false,
								loading: false,
							});
						})
						.catch(() => {
							setTimeout(() => {
								setState({
									error: true,
									loading: false,
								});
							}, 3000);
						}),
				100
			);
		}
		else {
			setState({error: false, loading: false});
		}
	}, [value]); //eslint-disable-line

	return (
		<ClayInput.Group className="searchbar">
			<ClayAutocomplete ref={containerRef}>
				<ClayAutocomplete.Input
					aria-label={Liferay.Language.get('keyword')}
					className="input-group-inset input-group-inset-after"
					onChange={(event) => {
						setView(true);
						setValue(event.target.value);
					}}
					onClick={() => {
						setView(true);
					}}
					onKeyDown={_handleKeyDown}
					placeholder={Liferay.Language.get('search')}
					value={value}
				/>
				<ClayAutocomplete.DropDown
					active={
						value.length >= CHARACTER_MIN &&
						view &&
						(_hasResults() || state.error)
					}
					alignElementRef={containerRef}
					onSetActive={setView}
				>
					<ClayDropDown.ItemList>
						{state.error && (
							<ClayDropDown.Item className="disabled">
								{Liferay.Language.get(
									'an-unexpected-error-occurred'
								)}
							</ClayDropDown.Item>
						)}
						{!state.error &&
							_hasResults() &&
							resource.suggestions.map((item) => (
								<ClayAutocomplete.Item
									key={item}
									match={value}
									onClick={() => {
										setValue(item);
										setView(false);
										handleSubmit(item);
									}}
									value={item}
								/>
							))}
					</ClayDropDown.ItemList>
				</ClayAutocomplete.DropDown>
				{state.loading ? (
					<ClayAutocomplete.LoadingIndicator />
				) : (
					<ClayInput.GroupInsetItem after>
						<ClayButton
							aria-label={Liferay.Language.get('search')}
							displayType="unstyled"
							onClick={() => {
								setView(false);
								handleSubmit(value);
							}}
						>
							<ClayIcon symbol="search" />
						</ClayButton>
					</ClayInput.GroupInsetItem>
				)}
			</ClayAutocomplete>
		</ClayInput.Group>
	);
}

SearchBar.propTypes = {
	handleSubmit: PropTypes.func,
	suggestionsURL: PropTypes.string,
};
