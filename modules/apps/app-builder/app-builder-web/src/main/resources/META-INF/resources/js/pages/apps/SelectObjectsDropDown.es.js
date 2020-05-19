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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React, {useContext, useEffect, useRef, useState} from 'react';

import {AppContext} from '../../AppContext.es';
import isClickOutside from '../../utils/clickOutside.es';
import {addItem, request} from '../../utils/client.es';
import CustomObjectPopover from '../custom-object/CustomObjectPopover.es';
import DropDownWithSearch from './DropDownWithSearch.es';

export default ({onSelect, selectedvalue}) => {
	const {basePortletURL} = useContext(AppContext);
	const [fetchState, setFetchState] = useState({isLoading: true});
	const [isPopoverVisible, setPopoverVisible] = useState(false);
	const [items, setItems] = useState([]);

	const popoverRef = useRef();

	const selectRef = useRef();

	const emptyStateOnClick = () => {
		setPopoverVisible(!isPopoverVisible);
	};
	const handleOnSubmit = ({isAddFormView, name}) => {
		const addURL = `/o/data-engine/v2.0/data-definitions/by-content-type/app-builder`;

		addItem(addURL, {
			availableLanguageIds: ['en_US'],
			dataDefinitionFields: [],
			name: {
				value: name,
			},
		}).then(({id}) => {
			if (isAddFormView) {
				Liferay.Util.navigate(
					Liferay.Util.PortletURL.createRenderURL(basePortletURL, {
						dataDefinitionId: id,
						isAppsPortlet: true,
						mvcRenderCommandName: '/edit_form_view',
						newCustomObject: true,
					})
				);
			}
		});
	};

	const doFetch = () => {
		const ENDPOINT_CUSTOM_OBJECTS =
			'/o/data-engine/v2.0/data-definitions/by-content-type/app-builder';
		const ENDPOINT_NATIVE_OBJECTS =
			'/o/data-engine/v2.0/data-definitions/by-content-type/native-object';

		const params = {keywords: '', page: -1, pageSize: -1, sort: ''};

		const fetchCustomObjects = () =>
			request({endpoint: ENDPOINT_CUSTOM_OBJECTS, params});
		const fetchNativeObjects = () =>
			request({endpoint: ENDPOINT_NATIVE_OBJECTS, params});

		setFetchState({
			hasError: null,
			isLoading: true,
		});

		return Promise.all([fetchCustomObjects(), fetchNativeObjects()])
			.then(([customObjects, nativeObjects]) => {
				setItems(
					[...customObjects.items, ...nativeObjects.items].sort(
						(a, b) => a - b
					)
				);
				setFetchState({
					hasError: null,
					isLoading: false,
				});
			})
			.catch((hasError) => {
				setFetchState({
					hasError,
					isLoading: false,
				});
			});
	};

	useEffect(() => {
		doFetch();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const handleOnSelect = (event, newValue) => {
		event.stopPropagation();

		onSelect(newValue);
	};

	const stateProps = {
		emptyProps: {
			children: (
				<ClayButton
					className="emptyButton"
					displayType="secondary"
					onClick={emptyStateOnClick}
				>
					{Liferay.Language.get('new-custom-object')}
				</ClayButton>
			),
			label: Liferay.Language.get(
				'no-objects-yet-create-your-first-object'
			),
		},
		errorProps: {
			children: (
				<ClayButton displayType="link" onClick={doFetch} small>
					{Liferay.Language.get('retry')}
				</ClayButton>
			),
			label: Liferay.Language.get('failed-to-retrieve-objects'),
		},
		loadingProps: {
			label: Liferay.Language.get('retrieving-all-objects'),
		},
	};

	useEffect(() => {
		const handler = ({target}) => {
			const isOutside = isClickOutside(
				target,
				selectRef.current,
				popoverRef.current
			);

			if (isOutside) {
				setPopoverVisible(false);
			}
		};

		window.addEventListener('click', handler);

		return () => window.removeEventListener('click', handler);
	}, [popoverRef]);

	return (
		<>
			<DropDownWithSearch
				{...fetchState}
				items={items}
				label={Liferay.Language.get('select-object')}
				onSelect={handleOnSelect}
				stateProps={stateProps}
				trigger={
					<ClayButton
						className="clearfix w-100"
						displayType="secondary"
						ref={(element) => {
							selectRef.current = element;
						}}
					>
						<span className="float-left">
							{selectedvalue ||
								Liferay.Language.get('select-object')}
						</span>

						<ClayIcon
							className="float-right icon"
							symbol="caret-bottom"
						/>
					</ClayButton>
				}
			/>

			<CustomObjectPopover
				alignElement={selectRef.current}
				onCancel={() => {
					setPopoverVisible(false);
				}}
				onSubmit={handleOnSubmit}
				ref={popoverRef}
				visible={isPopoverVisible}
			/>
		</>
	);
};
