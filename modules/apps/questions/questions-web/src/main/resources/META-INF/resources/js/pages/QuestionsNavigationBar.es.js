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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayInput, ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useContext, useEffect, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../AppContext.es';
import Link from '../components/Link.es';
import SectionSubscription from '../components/SectionSubscription.es';
import useSection from '../hooks/useSection.es';
import {
	historyPushWithSlug,
	slugToText,
	useDebounceCallback,
} from '../utils/utils.es';

function getFilterOptions() {
	return [
		{
			label: Liferay.Language.get('latest-created'),
			value: 'latest-created',
		},
		{
			label: Liferay.Language.get('latest-edited'),
			value: 'latest-edited',
		},
		{
			label: Liferay.Language.get('week'),
			value: 'week',
		},
		{
			label: Liferay.Language.get('month'),
			value: 'month',
		},
	];
}

export default withRouter(
	({
		filterChange,
		history,
		match: {
			params: {sectionTitle},
		},
		searchChange,
		sectionChange,
	}) => {
		const context = useContext(AppContext);

		const historyPushParser = historyPushWithSlug(history.push);

		const [active, setActive] = useState(false);

		const [debounceCallback] = useDebounceCallback(value => {
			searchChange(value);
		}, 500);

		const section = useSection(slugToText(sectionTitle), context.siteKey);

		useEffect(() => {
			sectionChange(section);
		}, [section, sectionChange]);

		const filterOptions = getFilterOptions();

		const getParentSubSections = () =>
			(section &&
				section.parentSection &&
				section.parentSection.messageBoardSections.items) ||
			[];

		return (
			<div className="d-flex flex-column flex-lg-row justify-content-between">
				<div className="d-flex">
					<ClayDropDown
						active={active}
						className="questions-navigation-dropdown"
						onActiveChange={setActive}
						trigger={
							<div className="align-items-center d-flex h-100">
								{section.parentSection && (
									<ClayInput.Group>
										<ClayInput.GroupItem className="align-items-center">
											<div className="questions-navigation-parent-section-title text-truncate">
												{section.parentSection.title}
												{':'}
											</div>
										</ClayInput.GroupItem>

										<ClayInput.GroupItem
											className="questions-navigation-section-title text-truncate"
											shrink
										>
											{section.title ===
											section.parentSection.title
												? Liferay.Language.get('all')
												: section.title}
										</ClayInput.GroupItem>

										<ClayInput.GroupItem
											className="align-items-center"
											shrink
										>
											<ClayIcon symbol="caret-bottom" />
										</ClayInput.GroupItem>
									</ClayInput.Group>
								)}
							</div>
						}
					>
						<Link
							to={`/questions/${(section &&
								section.parentSection &&
								section.parentSection.title) ||
								sectionTitle}`}
						>
							<ClayDropDown.Help>
								{Liferay.Language.get('all')}
							</ClayDropDown.Help>
						</Link>

						<ClayDropDown.ItemList>
							<ClayDropDown.Group>
								{getParentSubSections().map((item, i) => (
									<ClayDropDown.Item href={item.href} key={i}>
										<Link to={'/questions/' + item.title}>
											{item.title}
										</Link>
									</ClayDropDown.Item>
								))}
							</ClayDropDown.Group>
						</ClayDropDown.ItemList>
					</ClayDropDown>

					{section && section.actions && section.actions.subscribe && (
						<div className="c-ml-3">
							<SectionSubscription section={section} />
						</div>
					)}
				</div>

				<div className="c-mt-3 c-mt-lg-0 d-flex flex-column flex-md-row">
					<ClayInput.Group className="justify-content-lg-end">
						<ClayInput.GroupItem shrink>
							<label
								className="align-items-center d-inline-flex m-0 text-secondary"
								htmlFor="questionsFilter"
							>
								{Liferay.Language.get('filter-by')}
							</label>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem shrink>
							<ClaySelect
								className="bg-transparent border-0"
								id="questionsFilter"
								onChange={event =>
									filterChange(event.target.value)
								}
							>
								{filterOptions.map(option => (
									<ClaySelect.Option
										key={option.value}
										label={option.label}
										value={option.value}
									/>
								))}
							</ClaySelect>
						</ClayInput.GroupItem>
					</ClayInput.Group>

					<ClayInput.Group className="c-mt-3 c-mt-md-0">
						<ClayInput.GroupItem>
							<ClayInput
								className="bg-transparent form-control input-group-inset input-group-inset-after"
								onChange={event =>
									debounceCallback(event.target.value)
								}
								placeholder={Liferay.Language.get('search')}
								type="text"
							/>

							<ClayInput.GroupInsetItem
								after
								className="bg-transparent"
								tag="span"
							>
								<ClayButtonWithIcon
									displayType="unstyled"
									symbol="search"
									type="submit"
								/>
							</ClayInput.GroupInsetItem>
						</ClayInput.GroupItem>

						{section &&
							section.actions &&
							section.actions['add-thread'] && (
								<ClayInput.GroupItem shrink>
									<ClayButton
										className="c-ml-3 d-none d-sm-block text-nowrap"
										displayType="primary"
										onClick={() =>
											historyPushParser(
												`/questions/${sectionTitle}/new`
											)
										}
									>
										{Liferay.Language.get('ask-question')}
									</ClayButton>

									<ClayButton
										className="btn-monospaced d-block d-sm-none position-fixed questions-button shadow"
										displayType="primary"
										onClick={() =>
											historyPushParser(
												`/questions/${sectionTitle}/new`
											)
										}
									>
										<ClayIcon symbol="pencil" />

										<span className="sr-only">
											{Liferay.Language.get(
												'ask-question'
											)}
										</span>
									</ClayButton>
								</ClayInput.GroupItem>
							)}
					</ClayInput.Group>
				</div>
			</div>
		);
	}
);
