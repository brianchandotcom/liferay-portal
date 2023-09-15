/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import getCN from 'classnames';
import {addParams, fetch} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import {SCOPE_TYPES} from '../../utils/constants.es';
import {sub} from '../../utils/language.es';
import ScopeSelectModal from './ScopeSelectModal.es';

/**
 * Input component that appears when site or blueprint option is chosen
 * under the "Scope" tab.
 *
 * If the scope has been defined already, component is disabled and
 * fetches the existing site/blueprint for display.
 *
 * If scope has not been defined yet, component is enabled and fetches a
 * list of sites/blueprints for the user to select from in the dropdown.
 */

const ScopeSelect = ({
	disabled,
	fetchItemsUrl,
	fetchItemByIdUrl,
	hidden,
	initialSelected,
	locator = {
		id: 'externalReferenceCode',
		label: 'descriptiveName',
	},
	onSelect,
	title,
	touched = false,
	type = SCOPE_TYPES.SITE,
}) => {
	const [activeDropdown, setActiveDropdown] = useState(false);
	const [displayName, setDisplayName] = useState('');
	const [loading, setLoading] = useState(false);
	const [resourceItems, setResourceItems] = useState([]);
	const [selected, setSelected] = useState(initialSelected || '');

	const alignElementRef = useRef(null);
	const menuElementRef = useRef(null);

	const _fetchDropdownItems = () => {
		setLoading(true);

		fetch(
			addParams(
				{activePage: 1, pageSize: 5},
				`${
					window.location.origin
				}${Liferay.ThemeDisplay.getPathContext()}${fetchItemsUrl}`
			),
			{
				credentials: 'include',
				headers: new Headers({
					'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
					'x-csrf-token': Liferay.authToken,
				}),
				method: 'GET',
			}
		)
			.then((response) => {
				if (response.ok) {
					return response.json();
				}

				throw new Error();
			})
			.then(({items}) => {
				setResourceItems(items);
			})
			.catch(() => {
				setResourceItems([]);
			})
			.finally(() => {
				setLoading(false);
			});
	};

	const _handleActiveDropdownChange = () => {
		setActiveDropdown(!activeDropdown);

		if (!resourceItems.length) {
			_fetchDropdownItems();
		}
	};

	const _handleSelect = (value, displayName) => {
		setDisplayName(displayName);
		setSelected(typeof value === 'string' ? value : value.toString());

		setActiveDropdown(false);
	};

	useEffect(() => {
		if (!hidden) {
			onSelect({
				value: selected,
			});
		}
	}, [hidden, selected, onSelect]);

	useEffect(() => {

		// Fetch the display name of initially selected, if available.

		if (initialSelected) {
			fetch(
				`${
					window.location.origin
				}${Liferay.ThemeDisplay.getPathContext()}
				${fetchItemByIdUrl}${initialSelected}`,
				{
					credentials: 'include',
					headers: new Headers({
						'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
						'x-csrf-token': Liferay.authToken,
					}),
					method: 'GET',
				}
			)
				.then((response) => {
					if (response.ok) {
						return response.json();
					}

					throw new Error();
				})
				.then((item) => {
					setDisplayName(item[locator.label] || initialSelected);
				})
				.catch(() => {
					setDisplayName(initialSelected);
				});
		}
	}, []); //eslint-disable-line

	return (
		<div
			className={getCN('c-mb-3 form-group', {
				['has-error']: touched && !selected,
				hide: hidden,
			})}
		>
			<label className={getCN({disabled})} htmlFor={type}>
				{title}

				{!disabled && (
					<ClayIcon
						className="c-ml-1 reference-mark"
						symbol="asterisk"
					/>
				)}
			</label>

			<button
				className={getCN('form-control form-control-select', {
					'text-muted': disabled,
				})}
				disabled={disabled || hidden}
				id={type}
				onBlur={() => onSelect({touched: true})}
				onClick={_handleActiveDropdownChange}
				ref={alignElementRef}
				style={disabled ? {backgroundImage: 'unset'} : {}}
				type="button"
			>
				{displayName}
			</button>

			<ClayDropDown.Menu
				active={activeDropdown}
				alignElementRef={alignElementRef}
				closeOnClickOutside
				onActiveChange={setActiveDropdown}
				ref={menuElementRef}
				style={{
					maxHeight: '360px',
					maxWidth: 'unset',
					width: alignElementRef.current?.clientWidth,
				}}
			>
				<ClayDropDown.ItemList>
					{loading ? (
						<ClayDropDown.Section>
							<ClayLoadingIndicator
								displayType="secondary"
								size="sm"
							/>
						</ClayDropDown.Section>
					) : resourceItems.length ? (
						<ClayDropDown.Group items={resourceItems} key={type}>
							{(item) => (
								<ClayDropDown.Item
									key={item[locator.id]}
									onClick={() =>
										_handleSelect(
											item[locator.id],
											item[locator.label]
										)
									}
								>
									<div className="list-group-title">
										{item[locator.label]}
									</div>

									<div className="list-group-text">
										{type === SCOPE_TYPES.SITE
											? sub(
													Liferay.Language.get(
														'x-child-sites'
													),
													[item.sites?.length]
											  )
											: sub(
													Liferay.Language.get(
														'external-reference-code-x'
													),
													[item.externalReferenceCode]
											  )}
									</div>
								</ClayDropDown.Item>
							)}
						</ClayDropDown.Group>
					) : (
						<ClayDropDown.Section>
							<span className="text-secondary">
								{Liferay.Language.get('no-results-found')}
							</span>
						</ClayDropDown.Section>
					)}

					{!!resourceItems.length && (
						<ClayDropDown.Section>
							<ScopeSelectModal
								fetchItemsUrl={fetchItemsUrl}
								locator={locator}
								onSubmit={_handleSelect}
								selected={selected}
								title={title}
								type={type}
							>
								<ClayButton
									className="w-100"
									displayType="secondary"
									onClick={() => setActiveDropdown(false)}
								>
									{Liferay.Language.get('view-more')}
								</ClayButton>
							</ScopeSelectModal>
						</ClayDropDown.Section>
					)}
				</ClayDropDown.ItemList>
			</ClayDropDown.Menu>
		</div>
	);
};

export default ScopeSelect;
