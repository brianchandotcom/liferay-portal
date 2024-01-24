/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import getCN from 'classnames';
import React, {useRef, useState} from 'react';

import {fetchResponse} from '../../utils/api.es';
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
	disabled = false,
	error,
	fetchItemsUrl,
	selected,
	locator = {
		id: 'externalReferenceCode',
		label: 'descriptiveName',
	},
	onSelect,
	onBlur,
	title,
	touched = false,
	type,
}) => {
	const [activeDropdown, setActiveDropdown] = useState(false);
	const [displayName, setDisplayName] = useState('');
	const [loading, setLoading] = useState(false);
	const [resourceItems, setResourceItems] = useState([]);

	const alignElementRef = useRef(null);
	const menuElementRef = useRef(null);

	const _fetchDropdownItems = () => {
		setLoading(true);

		fetchResponse(fetchItemsUrl, {
			page: 1,
			pageSize: 5,
		})
			.then(({items}) => {
				setResourceItems(items || []);
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
		onSelect(typeof value === 'string' ? value : value.toString());

		setActiveDropdown(false);
	};

	return (
		<div
			className={getCN('c-mb-3 form-group', {
				['has-error']: error && touched,
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
				disabled={disabled}
				id={type}
				onBlur={onBlur}
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
										{sub(
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

			{error && touched && (
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>{error}</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			)}
		</div>
	);
};

export default ScopeSelect;
