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
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import PropTypes from 'prop-types';
import React, {useCallback, useState} from 'react';

function PageTypeSelector({
	addCollectionLayoutURL,
	addLayoutURL,
	configureLayoutSetURL,
	namespace,
	pageTypeOptions,
	pageTypeSelectedOption,
	pageTypeSelectedOptionLabel,
	showAddIcon,
}) {
	const [addPageDropdownActive, setAddPageDropdownActive] = useState(false);
	const [pageTypeDropdownActive, setPageTypeDropdownActive] = useState(false);

	const handleSelect = (type) => {
		setPageTypeDropdownActive(false);

		Liferay.Util.Session.set(
			`${namespace}PAGE_TYPE_SELECTED_OPTION`,
			type
		).then(() => {
			Liferay.Util.navigate(window.location.href);
		});
	};

	const handleOnAddCollectionPageClick = useCallback(() => {
		setAddPageDropdownActive(false);
		Liferay.Util.navigate(addCollectionLayoutURL);
	}, [addCollectionLayoutURL]);

	const handleOnAddPageClick = useCallback(() => {
		setAddPageDropdownActive(false);
		Liferay.Util.navigate(addLayoutURL);
	}, [addLayoutURL]);

	return (
		<div className="align-items-center d-flex page-type-selector">
			<ClayDropDown
				active={pageTypeDropdownActive}
				menuElementAttrs={{
					containerProps: {
						className: 'cadmin',
					},
				}}
				onActiveChange={setPageTypeDropdownActive}
				trigger={
					<ClayButton
						className="form-control-select text-left"
						displayType="secondary"
						small
						type="button"
					>
						{pageTypeSelectedOptionLabel}
					</ClayButton>
				}
			>
				<ClayDropDown.ItemList>
					{pageTypeOptions
						.filter((option) => option.items.length)
						.map((option, index) => (
							<React.Fragment key={index}>
								<ClayDropDown.Item disabled key={option.value}>
									{option.name}
								</ClayDropDown.Item>
								{option.items.map((item) => (
									<ClayDropDown.Item
										className="page-type-selector-option"
										key={item.value}
										onClick={() => handleSelect(item.value)}
										symbolRight={
											item.value ===
											pageTypeSelectedOption
												? 'check'
												: null
										}
									>
										{item.name}
									</ClayDropDown.Item>
								))}
							</React.Fragment>
						))}
				</ClayDropDown.ItemList>
			</ClayDropDown>

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
							<ClayButton
								aria-haspopup="true"
								className="dropdown-toggle"
								displayType="unstyled"
							>
								<ClayIcon symbol="plus" />
							</ClayButton>
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
						borderless
						className="configure-link"
						displayType="unstyled"
						href={configureLayoutSetURL}
						monospaced
						outline
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
