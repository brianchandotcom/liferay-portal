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
import {Link, withRouter} from 'react-router-dom';

import {AppContext} from '../AppContext.es';
import useSection from '../hooks/useSection.es';
import {useDebounceCallback} from '../utils/utils.es';

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

		const [active, setActive] = useState(false);

		const [debounceCallback] = useDebounceCallback(value => {
			searchChange(value);
		}, 500);

		const section = useSection(sectionTitle, context.siteKey);

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
			<div className="autofit-padded-no-gutters autofit-row autofit-row-center">
				<div className="autofit-col autofit-col-expand">
					<ClayDropDown
						active={active}
						onActiveChange={setActive}
						trigger={
							<div>
								{section.parentSection && (
									<>
										{section.parentSection.title}
										{' : '}
										{section.title ===
										section.parentSection.title
											? Liferay.Language.get('all')
											: section.title}
									</>
								)}
							</div>
						}
					>
						<Link
							to={`/questions/${(section &&
								section.parentSection &&
								section.parentSection.id) ||
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
				</div>

				<div className="d-flex">
					{Liferay.Language.get('filter-by')}

					<ClaySelect
						onChange={event => filterChange(event.target.value)}
					>
						{filterOptions.map(option => (
							<ClaySelect.Option
								key={option.value}
								label={option.label}
								value={option.value}
							/>
						))}
					</ClaySelect>
				</div>

				<div className="d-flex">
					<ClayInput.Group>
						<ClayInput.GroupItem>
							<ClayInput
								className="form-control input-group-inset input-group-inset-after"
								onChange={event =>
									debounceCallback(event.target.value)
								}
								placeholder={Liferay.Language.get('search')}
								type="text"
							/>

							<ClayInput.GroupInsetItem after tag="span">
								<ClayButtonWithIcon
									displayType="unstyled"
									symbol="search"
									type="submit"
								/>
							</ClayInput.GroupInsetItem>
						</ClayInput.GroupItem>
					</ClayInput.Group>

					{context.canCreateThread && (
						<>
							<ClayButton
								className="c-ml-3 d-none d-sm-block text-nowrap"
								displayType="primary"
								onClick={() =>
									history.push(
										`/questions/${sectionTitle}/new`
									)
								}
							>
								{Liferay.Language.get('ask-question')}
							</ClayButton>

							<ClayButton
								className="btn-monospaced d-block d-sm-none position-fixed question-button shadow"
								displayType="primary"
								onClick={() =>
									history.push(
										`/questions/${sectionTitle}/new`
									)
								}
							>
								<ClayIcon symbol="pencil" />

								<span className="sr-only">
									{Liferay.Language.get('ask-question')}
								</span>
							</ClayButton>
						</>
					)}
				</div>
			</div>
		);
	}
);
