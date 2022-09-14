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

import ClayAutocomplete from '@clayui/autocomplete';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {Fragment, useEffect, useState} from 'react';

import useBreadcrumb, {defaultEntities} from '../../hooks/useBreadcrumb';
import i18n from '../../i18n';

type BreadccrumbSearchProps = {
	maxEntitiesToSearch?: number;
	onBreadcrumbChange: (value: any) => void;
};

const BreadcrumbSearch: React.FC<BreadccrumbSearchProps> = ({
	onBreadcrumbChange,
	maxEntitiesToSearch = defaultEntities.length,
}) => {
	const [active, setActive] = useState(false);

	const {
		breadCrumb,
		entities,
		inputRef,
		items,
		onBackscape,
		onClickRow,
		search,
		setSearch,
	} = useBreadcrumb([...defaultEntities].slice(0, maxEntitiesToSearch));

	const MAX_BREADCRUMB_REACHED = breadCrumb.length === maxEntitiesToSearch;

	const onClickFormControl = () => {
		if (!items.length) {
			setActive(false);
		}
		setActive(!active);
	};

	useEffect(() => {
		onBreadcrumbChange(breadCrumb);
	}, [breadCrumb, onBreadcrumbChange]);

	return (
		<div className="breadcrumb-finder-navigator breadcrumb-search-container">
			<div className="breadcrumb-search-content">
				<div className="d-flex flex-column w-100">
					<ul className="d-flex overflow-hidden">
						{entities.map((entity, index) => {
							const selected = !!breadCrumb[index];

							return (
								<li className="mr-3" key={index}>
									<ClayIcon
										className="mr-1"
										color={selected ? 'green' : 'red'}
										symbol={
											selected
												? 'check-circle-full'
												: 'times-circle-full'
										}
									/>

									<span
										className={classNames({
											'build-danger': !selected,
											'build-success': selected,
										})}
									>
										{entity.name}
									</span>
								</li>
							);
						})}
					</ul>

					<div className="form-control" onClick={onClickFormControl}>
						<span
							className="selected-container"
							id="selectedContainer"
						>
							{breadCrumb.length >= 1 && (
								<div className="divider">/</div>
							)}

							{breadCrumb.map(({label}, index) => (
								<Fragment key={index}>
									<span className="breadcrumb-selected-item">
										{label}
									</span>

									{index !== breadCrumb.length - 1 && (
										<div className="divider">/</div>
									)}
								</Fragment>
							))}
						</span>

						<div className="breadcrumb-search-input-edit-wrapper">
							<input
								className="breadcrumb-search-input-edit"
								name="breadcrumbInputEdit"
								onChange={({target: {value}}) =>
									setSearch(value)
								}
								onKeyDown={({key}) => {
									if (key === 'Backspace' && search === '') {
										onBackscape();
									}
								}}
								ref={inputRef}
								tabIndex={-1}
								type="text"
								value={search}
							/>
						</div>
					</div>

					{!MAX_BREADCRUMB_REACHED && (
						<ClayAutocomplete className="mb-4">
							<ClayAutocomplete.DropDown active={active}>
								<ClayDropDown.ItemList>
									{!!items.length &&
										items?.map(
											(item: any, itemIndex: any) => (
												<ClayAutocomplete.Item
													key={item.label}
													match={item.label}
													onClick={() => {
														onClickRow(itemIndex);
													}}
													value={item.label}
												/>
											)
										)}

									{!items.length && (
										<ClayAutocomplete.Item
											match={i18n.translate(
												'no-results-found'
											)}
											value={i18n.translate(
												'no-results-found'
											)}
										/>
									)}
								</ClayDropDown.ItemList>
							</ClayAutocomplete.DropDown>
						</ClayAutocomplete>
					)}
				</div>
			</div>
		</div>
	);
};

export default BreadcrumbSearch;
