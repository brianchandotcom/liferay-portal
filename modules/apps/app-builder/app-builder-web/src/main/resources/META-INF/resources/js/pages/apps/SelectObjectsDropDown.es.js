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
import {addItem, getItem} from '../../utils/client.es';
import CustomObjectPopover from '../custom-object/CustomObjectPopover.es';
import DropDownWithSearch from './DropDownWithSearch.es';

export default ({onSelect, selectedValue}) => {
	const {basePortletURL} = useContext(AppContext);
	const [state, setState] = useState({
		error: null,
		isLoading: true,
	});
	const [isPopoverVisible, setPopoverVisible] = useState(false);
	const [items, setItems] = useState([]);

	const popoverRef = useRef();

	const selectRef = useRef();

	const onClick = () => {
		setPopoverVisible(!isPopoverVisible);
	};
	const onSubmit = ({isAddFormView, name}) => {
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
						appsPortlet: true,
						dataDefinitionId: id,
						mvcRenderCommandName: '/edit_form_view',
						newCustomObject: true,
					})
				);
			}
		});
	};

	const doFetch = () => {
		const params = {keywords: '', page: -1, pageSize: -1, sort: ''};

		setState({
			error: null,
			isLoading: true,
		});

		return Promise.all([
			getItem(
				'/o/data-engine/v2.0/data-definitions/by-content-type/app-builder',
				params
			),
			getItem(
				'/o/data-engine/v2.0/data-definitions/by-content-type/native-object',
				params
			),
		])
			.then(([customObjects, nativeObjects]) => {
				setItems(
					[...customObjects.items, ...nativeObjects.items].sort(
						(a, b) => a - b
					)
				);
				setState({
					error: null,
					isLoading: false,
				});
			})
			.catch((error) => {
				setState({
					error,
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
					onClick={onClick}
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
				{...state}
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
							{selectedValue ||
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
				onSubmit={onSubmit}
				ref={popoverRef}
				visible={isPopoverVisible}
			/>
		</>
	);
};
