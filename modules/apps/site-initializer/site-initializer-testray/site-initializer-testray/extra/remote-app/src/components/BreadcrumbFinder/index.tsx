/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {Fragment, useEffect} from 'react';
import {useParams} from 'react-router-dom';
import {HeaderTitle} from '~/atoms';

import {defaultEntities} from '../../hooks/useBreadcrumb';
import i18n from '../../i18n';
import useBreadcrumbFinder from './useBreadcrumbFinder';

type BreadcrumbFinderProps = {
	heading: HeaderTitle[];
};

const BreadcrumbFinder: React.FC<BreadcrumbFinderProps> = ({heading}) => {
	const {
		active,
		breadCrumb,
		index,
		inputRef,
		items,
		listItemRef,
		listRef,
		onBackscape,
		onClickRow,
		onInputKeyPress,
		search,
		setActive,
		setBreadCrumb,
		setSearch,
		tabDisabled,
	} = useBreadcrumbFinder();

	const params = useParams();

	useEffect(() => {
		if (heading.length) {
			const {buildId, caseResultId, projectId, routineId} = params;

			const projectFlowIds = [
				projectId,
				routineId,
				buildId,
				caseResultId,
			];

			if (projectId) {
				setBreadCrumb(
					heading.map(({title}, index) => ({
						entity: defaultEntities[index],
						label: title,
						value: Number(projectFlowIds[index] ?? 0),
					}))
				);
			}
			else {
				setBreadCrumb([]);
			}
		}
	}, [heading, params, setBreadCrumb]);

	if (!active) {
		return null;
	}

	return (
		<div className="breadcrumb-finder-container breadcrumb-finder-navigator">
			<div
				className="breadcrumb-finder-overlay overlay"
				onClick={() => setActive((active) => !active)}
			/>

			<div className="breadcrumb-finder-content">
				<div className="d-flex flex-column flex-nowrap w-100">
					<div className="d-flex flex-nowrap flex-row w-100">
						<span className="selected-container">
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

						<div className="breadcrumb-input-edit-wrapper d-flex flex-nowrap flex-row w-100">
							<input
								className="breadcrumb-input-edit d-flex w-100"
								name="breadcrumbInputEdit"
								onChange={({target: {value}}) =>
									setSearch(value)
								}
								onKeyDown={({key}) => {
									if (key === 'Backspace' && search === '') {
										return onBackscape();
									}
									onInputKeyPress(key);
								}}
								ref={inputRef}
								tabIndex={-1}
								type="text"
								value={search}
							/>
						</div>
					</div>

					<hr />

					<ul className="list-unstyled" ref={listRef}>
						{items.map((item, itemIndex) => (
							<li
								className={classNames(
									'breadcrumb-finder-item cursor-pointer',
									{active: itemIndex === index}
								)}
								id={item.label}
								key={itemIndex}
								onClick={() => onClickRow(itemIndex)}
								ref={listItemRef[itemIndex]}
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
