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
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {
	DataLayoutBuilderActions,
	SearchInput,
	Sidebar
} from 'data-engine-taglib';
import React, {
	useCallback,
	useContext,
	useEffect,
	useLayoutEffect,
	useRef,
	useState
} from 'react';

import {useKeyDown} from '../../hooks/index.es';
import isClickOutside from '../../utils/clickOutside.es';
import CustomObjectFieldsList from './CustomObjectFieldsList.es';
import FormViewContext from './FormViewContext.es';

const DropDown = () => {
	const [{fieldTypes}, dispatch] = useContext(FormViewContext);
	const [active, setActive] = useState(false);
	const [showFieldTypes, setShowFieldTypes] = useState(false);

	const onActiveChange = newVal => {
		setActive(newVal);
		setShowFieldTypes(false);
	};

	const onClickFieldType = fieldTypeName => {
		setActive(false);
		dispatch({
			payload: {fieldTypeName},
			type: DataLayoutBuilderActions.ADD_CUSTOM_OBJECT_FIELD
		});
	};

	useLayoutEffect(() => {
		if (active) {
			const {parentElement} = document.querySelector(
				'.custom-object-dropdown-list'
			);

			parentElement.classList.add('custom-object-dropdown-menu');
		}
	}, [active]);

	const filteredFieldTypes = fieldTypes.filter(
		({group}) => group === 'basic'
	);

	filteredFieldTypes.sort(({displayOrder: a}, {displayOrder: b}) => a - b);

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={Align.BottomRight}
			className="custom-object-dropdown"
			onActiveChange={onActiveChange}
			trigger={
				<ClayButtonWithIcon displayType="unstyled" symbol="plus" />
			}
		>
			<ClayDropDown.ItemList className="custom-object-dropdown-list">
				{showFieldTypes ? (
					filteredFieldTypes.map(({icon, label, name}) => (
						<ClayDropDown.Item
							key={name}
							onClick={() => onClickFieldType(name)}
							symbolLeft={icon}
						>
							{label}
						</ClayDropDown.Item>
					))
				) : (
					<>
						<ClayDropDown.Item
							key={'add'}
							onClick={() => setShowFieldTypes(true)}
						>
							{Liferay.Language.get('add-field-to-object')}
						</ClayDropDown.Item>
						<ClayDropDown.Item key={'import'}>
							{Liferay.Language.get(
								'import-fields-from-spreadsheet'
							)}
						</ClayDropDown.Item>
					</>
				)}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
};

const Header = ({onCloseSearch, onSearch, searchText}) => {
	const [searchMode, setSearchMode] = useState(false);

	const closeSearch = useCallback(() => {
		setSearchMode(false);
		onCloseSearch();
	}, [onCloseSearch, setSearchMode]);

	const onClickClose = () => closeSearch();
	const onClickSearch = () => setSearchMode(true);

	useKeyDown(() => {
		if (searchMode) {
			closeSearch();
		}
	}, 27);

	const searchInputRef = useRef();

	useEffect(() => {
		if (searchMode && searchInputRef.current) {
			searchInputRef.current.focus();
		}
	}, [searchInputRef, searchMode]);

	const [{dataDefinition}] = useContext(FormViewContext);
	const {
		name: {en_US: dataDefinitionName = ''}
	} = dataDefinition;

	return (
		<ClayForm onSubmit={event => event.preventDefault()}>
			<div
				className={classNames(
					'custom-object-sidebar-header',
					'mt-4',
					'p-2',
					{
						'ml-4': !searchMode
					}
				)}
			>
				<div className="autofit-row autofit-row-center">
					{searchMode ? (
						<>
							<div className="autofit-col autofit-col-expand">
								<SearchInput
									clearButton={false}
									onChange={searchText =>
										onSearch(searchText)
									}
									ref={searchInputRef}
									searchText={searchText}
								/>
							</div>
							<div className="autofit-col ml-2" key="closeButton">
								<ClayButtonWithIcon
									displayType="unstyled"
									onClick={onClickClose}
									symbol="times"
								/>
							</div>
						</>
					) : (
						<>
							<div className="autofit-col autofit-col-expand">
								<h3>{dataDefinitionName}</h3>
							</div>

							<div className="autofit-col" key="searchButton">
								<ClayButtonWithIcon
									displayType="unstyled"
									onClick={onClickSearch}
									symbol="search"
								/>
							</div>

							<div className="autofit-col" key="dropdown">
								<DropDown />
							</div>
						</>
					)}
				</div>
			</div>
		</ClayForm>
	);
};

export default () => {
	const [
		{
			dataDefinition: {dataDefinitionFields},
			focusedCustomObjectField
		},
		dispatch
	] = useContext(FormViewContext);
	const [searchText, setSearchText] = useState('');
	const sidebarRef = useRef();

	useKeyDown(() => {
		if (Object.keys(focusedCustomObjectField).length > 0) {
			dispatch({
				payload: {dataDefinitionField: {}},
				type:
					DataLayoutBuilderActions.UPDATE_FOCUSED_CUSTOM_OBJECT_FIELD
			});
		}
	}, 27);

	useEffect(() => {
		const eventHandler = ({target}) => {
			if (
				isClickOutside(
					target,
					'.data-layout-builder-sidebar',
					'.dropdown-menu'
				)
			) {
				dispatch({
					payload: {dataDefinitionField: {}},
					type:
						DataLayoutBuilderActions.UPDATE_FOCUSED_CUSTOM_OBJECT_FIELD
				});
			}
		};

		window.addEventListener('click', eventHandler);

		return () => window.removeEventListener('click', eventHandler);
	}, [dispatch]);

	const empty = dataDefinitionFields.length === 0;

	return (
		<Sidebar closeable={false} ref={sidebarRef}>
			<>
				<Header
					onCloseSearch={() => setSearchText('')}
					onSearch={searchText => setSearchText(searchText)}
					searchText={searchText}
				/>

				<Sidebar.Body className={classNames({empty})}>
					{empty ? (
						<div className="custom-object-sidebar-empty">
							<ClayIcon symbol="custom-field" />

							<h3>
								{Liferay.Language.get(
									'there-are-no-fields-yet'
								)}
							</h3>

							<p>
								{Liferay.Language.get(
									'any-field-added-to-the-object-or-to-a-form-view-appears-here'
								)}
							</p>
						</div>
					) : (
						<CustomObjectFieldsList keywords={searchText} />
					)}
				</Sidebar.Body>
			</>
		</Sidebar>
	);
};
