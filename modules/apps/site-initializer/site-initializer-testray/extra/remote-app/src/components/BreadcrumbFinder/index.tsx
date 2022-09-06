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

import classNames from 'classnames';
import {Fragment} from 'react';

import i18n from '../../i18n';
import useBreadcrumbFinder from './useBreadcrumbFinder';

const BreadcrumbFinder = () => {
	const {
		active,
		breadCrumb,
		index,
		inputRef,
		items,
		onBackscape,
		onClickRow,
		search,
		setSearch,
		tabDisabled,
	} = useBreadcrumbFinder();

	if (!active) {
		return null;
	}

	return (
		<div
			className="breadcrumb-finder-container breadcrumb-finder-navigator"
			id="breadcrumbFinderContainer"
		>
			<div className="breadcrumb-finder-overlay"></div>

			<div
				className="breadcrumb-finder-content"
				id="breadcrumbFinderContent"
			>
				<div className="d-flex flex-column w-100">
					<div>
						<span
							className="selected-container"
							id="selectedContainer"
						>
							{!!breadCrumb.length && (
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

						<div className="breadcrumb-input-edit-wrapper">
							<input
								className="breadcrumb-input-edit"
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

					<hr></hr>

					<ul className="list-unstyled">
						{items.map((item, itemIndex) => (
							<li
								className={classNames(
									'breadcrumb-finder-item cursor-pointer',
									{active: itemIndex === index}
								)}
								key={itemIndex}
								onClick={() => onClickRow(itemIndex)}
							>
								<div
									className={classNames(
										'd-flex justify-content-between result-item',
										{
											active: itemIndex === index,
										}
									)}
								>
									<span className="result-text">
										{item.label}
									</span>

									{itemIndex === index && (
										<div className="d-flex result-hotkey-hint-container">
											{!tabDisabled && (
												<span className="result-hotkey-hint">
													<kbd>TAB</kbd>

													<span>
														{i18n.translate(
															'search-in'
														)}
													</span>
												</span>
											)}

											<span className="ml-4 result-hotkey-hint">
												<kbd>ENTER</kbd>

												<span>
													{i18n.translate(
														'view-page'
													)}
												</span>
											</span>
										</div>
									)}
								</div>
							</li>
						))}
					</ul>
				</div>
			</div>
		</div>
	);
};

export default BreadcrumbFinder;
