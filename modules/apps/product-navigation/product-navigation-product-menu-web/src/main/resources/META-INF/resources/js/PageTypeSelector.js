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
import {Option, Picker} from '@clayui/core';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {fetch, navigate, openToast, setSessionValue} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useState} from 'react';

function PageTypeSelector({
	addCollectionLayoutURL,
	addLayoutURL,
	configureLayoutSetURL,
	namespace,
	pageTypeOptions,
	pageTypeSelectedOption,
	pagesTreeURL,
	showAddIcon,
}) {
	const [addPageDropdownActive, setAddPageDropdownActive] = useState(false);

	const handleSelect = (type) => {
		setSessionValue(`${namespace}PAGE_TYPE_SELECTED_OPTION`, type).then(
			() => {
				Liferay.Portlet.destroy(`#p_p_id${namespace}`, true);

				fetch(pagesTreeURL)
					.then((response) => {
						if (!response.ok) {
							throw new Error();
						}

						return response.text();
					})
					.then((productMenuContent) => {
						const sidebar = document.querySelector(
							'.lfr-product-menu-sidebar .sidebar-body .pages-tree'
						);

						sidebar.innerHTML = '';

						const range = document.createRange();
						range.selectNode(sidebar);

						sidebar.appendChild(
							range.createContextualFragment(productMenuContent)
						);
					})
					.catch(() => {
						openToast({
							message: Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
							title: Liferay.Language.get('error'),
							type: 'danger',
						});
					});
			}
		);
	};

	const handleOnAddCollectionPageClick = useCallback(() => {
		setAddPageDropdownActive(false);
		navigate(addCollectionLayoutURL);
	}, [addCollectionLayoutURL]);

	const handleOnAddPageClick = useCallback(() => {
		setAddPageDropdownActive(false);
		navigate(addLayoutURL);
	}, [addLayoutURL]);

	return (
		<div className="align-items-center d-flex page-type-selector">
			<Picker
				aria-label={Liferay.Language.get('pages-type')}
				className="form-control-sm w-auto"
				items={pageTypeOptions.filter((option) => option.items.length)}
				onSelectionChange={handleSelect}
				selectedKey={pageTypeSelectedOption}
			>
				{(group) => (
					<ClayDropDown.Group
						header={group.label}
						items={group.items}
						key={group.label}
					>
						{(item) => (
							<Option
								className="page-type-selector-option"
								id={item.value}
								key={item.value}
							>
								{item.label}
							</Option>
						)}
					</ClayDropDown.Group>
				)}
			</Picker>

			<div className="flex-fill flex-grow-1 text-right">
				{showAddIcon && (
					<ClayDropDown
						active={addPageDropdownActive}
						menuElementAttrs={{
							containerProps: {
								className: 'cadmin',
							},
						}}
						onActiveChange={setAddPageDropdownActive}
						trigger={
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get('add-page')}
								className="add-page-button"
								displayType="unstyled"
								size="sm"
								symbol="plus"
								title={Liferay.Language.get('add-page')}
							/>
						}
					>
						<ClayDropDown.ItemList>
							{addLayoutURL && (
								<ClayDropDown.Item
									data-value={Liferay.Language.get(
										'add-page'
									)}
									key={Liferay.Language.get('add-page')}
									onClick={handleOnAddPageClick}
									title={Liferay.Language.get('add-page')}
								>
									{Liferay.Language.get('add-page')}
								</ClayDropDown.Item>
							)}

							{addCollectionLayoutURL && (
								<ClayDropDown.Item
									data-value={Liferay.Language.get(
										'add-collection-page'
									)}
									key={Liferay.Language.get(
										'add-collection-page'
									)}
									onClick={handleOnAddCollectionPageClick}
									title={Liferay.Language.get(
										'add-collection-page'
									)}
								>
									{Liferay.Language.get(
										'add-collection-page'
									)}
								</ClayDropDown.Item>
							)}
						</ClayDropDown.ItemList>
					</ClayDropDown>
				)}
			</div>

			<div className="autofit-col ml-2">
				{configureLayoutSetURL && (
					<ClayLink
						aria-label={Liferay.Language.get('configure-pages')}
						borderless
						className="configure-link"
						displayType="unstyled"
						href={configureLayoutSetURL}
						monospaced
						outline
						title={Liferay.Language.get('configure-pages')}
					>
						<ClayIcon symbol="cog" />
					</ClayLink>
				)}
			</div>
		</div>
	);
}

PageTypeSelector.propTypes = {
	addCollectionLayoutURL: PropTypes.string,
	addLayoutURL: PropTypes.string,
	configureLayoutSetURL: PropTypes.string,
	namespace: PropTypes.string,
	pageTypeOptions: PropTypes.arrayOf(
		PropTypes.shape({
			items: PropTypes.arrayOf(
				PropTypes.shape({
					name: PropTypes.string,
					value: PropTypes.value,
				})
			),
			name: PropTypes.string,
			value: PropTypes.string,
		})
	),
	pageTypeSelectedOption: PropTypes.string,
	pageTypeSelectedOptionLabel: PropTypes.string,
	showAddIcon: PropTypes.bool,
};

export default PageTypeSelector;
