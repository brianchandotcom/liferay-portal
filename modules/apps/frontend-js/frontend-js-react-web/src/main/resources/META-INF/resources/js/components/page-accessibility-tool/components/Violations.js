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
import ClayDropDown from '@clayui/drop-down';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClayPanel from '@clayui/panel';
import React, {useMemo, useState} from 'react';

import Occurrences from './Occurrences';
import PanelNavigator from './PanelNavigator';
import Rule from './Rule';
import {
	TYPES,
	useFilteredViolationsDispatch,
	useFilteredViolationsValues,
} from './useFilteredViolations';

const IMPACT_FILTER_OPTIONS = [
	{label: Liferay.Language.get('critical'), value: 'critical'},
	{label: Liferay.Language.get('serious'), value: 'serious'},
	{label: Liferay.Language.get('moderate'), value: 'moderate'},
	{label: Liferay.Language.get('minor'), value: 'minor'},
];

const CATEGORY_FILTER_OPTIONS = [
	{label: 'WCAG 2.0A', value: 'wcag2a'},
	{label: 'WCAG 2.0 Level AA', value: 'wcag2aa'},
	{label: 'WCAG 2.1 Level A', value: 'wcag21a'},
	{label: 'WCAG 2.1 Level AA', value: 'wcag21aa'},
	{label: 'Best Practices', value: 'best-practice'},
];

function ViolationsFilter() {
	const [dropdownExpanded, setDropdownExpanded] = useState(false);
	const dispatch = useFilteredViolationsDispatch();
	const {selectedCategories, selectedImpact} = useFilteredViolationsValues();

	return (
		<>
			<ClayDropDown
				active={dropdownExpanded}
				closeOnClickOutside
				onActiveChange={setDropdownExpanded}
				trigger={
					<ClayButtonWithIcon
						displayType="unstyled"
						small
						symbol="filter"
					/>
				}
			>
				<ClayDropDown.ItemList>
					<ClayDropDown.Group
						header={Liferay.Language.get('filter-by-impact')}
					>
						{IMPACT_FILTER_OPTIONS.map((item) => {
							const existsImpact =
								selectedImpact.findIndex(
									(element) => element === item.value
								) !== -1;

							return (
								<ClayDropDown.Section
									active={existsImpact}
									key={item.value}
								>
									<ClayCheckbox
										active={existsImpact}
										label={item.label}
										onChange={() => {
											if (existsImpact) {
												dispatch({
													payload: item.value,
													type: TYPES.IMPACT_REMOVE,
												});

												return;
											}

											dispatch({
												payload: item.value,
												type: TYPES.IMPACT_ADD,
											});
										}}
									></ClayCheckbox>
								</ClayDropDown.Section>
							);
						})}
					</ClayDropDown.Group>
					<ClayDropDown.Group
						header={Liferay.Language.get('filter-by-category')}
					>
						{CATEGORY_FILTER_OPTIONS.map((item) => {
							const existsCategory =
								selectedCategories.findIndex(
									(element) => element === item.value
								) !== -1;

							return (
								<ClayDropDown.Section key={item.value}>
									<ClayCheckbox
										active={existsCategory}
										label={item.label}
										onChange={() => {
											if (existsCategory) {
												dispatch({
													payload: item.value,
													type: TYPES.CATEGORY_REMOVE,
												});

												return;
											}

											dispatch({
												payload: item.value,
												type: TYPES.CATEGORY_ADD,
											});
										}}
									></ClayCheckbox>
								</ClayDropDown.Section>
							);
						})}
					</ClayDropDown.Group>
				</ClayDropDown.ItemList>
			</ClayDropDown>
		</>
	);
}

function ViolationsPanelHeaderTitle() {
	return (
		<div className="page-accessibility-tool__sidebar--violations-panel-header-title">
			<div className="inline-item inline-item-before">
				<ClayIcon className="text-danger" symbol="info-circle" />
			</div>
			<div className="inline-item">
				<span className="list-group-title">
					{Liferay.Language.get('accessibility-violations')}
				</span>
			</div>
			<div className="inline-item inline-item-after">
				<ViolationsFilter />
			</div>
		</div>
	);
}

function PanelSection({children, title}) {
	return (
		<ClayPanel
			displayTitle={title}
			displayType="unstyled"
			showCollapseIcon={false}
		>
			<ClayPanel.Body>{children}</ClayPanel.Body>
		</ClayPanel>
	);
}

function ViolationDescription({
	description,
	helpUrl,
	id,
	impact,
	nodes,
	onBack,
}) {
	const [occurrenceSelected, setOccurrenceSelected] = useState(null);

	if (!occurrenceSelected) {
		return (
			<>
				<PanelNavigator
					helpUrl={helpUrl}
					impact={impact}
					onBack={onBack}
					title={id}
				/>
				<div className="page-accessibility-tool__sidebar--occurrences-panel-wrapper">
					<ClayPanel.Group flush small>
						<PanelSection title={Liferay.Language.get('details')}>
							{description}
						</PanelSection>
						<PanelSection
							title={Liferay.Language.get('occurrences')}
						>
							<Occurrences.List
								nodes={nodes}
								onOccurrenceClicked={setOccurrenceSelected}
							/>
						</PanelSection>
					</ClayPanel.Group>
				</div>
			</>
		);
	}

	const {html, target, text} = occurrenceSelected;

	return (
		<Occurrences.Description
			helpUrl={helpUrl}
			html={html}
			impact={impact}
			onBack={() => setOccurrenceSelected(null)}
			target={target}
			title={text}
		/>
	);
}

function filterByCategories({receivedTags, selectedCategories}) {
	return selectedCategories.some((category) =>
		receivedTags.includes(category)
	);
}

function filterByImpact({receivedImpact, selectedImpact}) {
	return selectedImpact.includes(receivedImpact);
}

export default function Main({violations: initialViolations}) {
	const {selectedCategories, selectedImpact} = useFilteredViolationsValues();
	const [violationSelected, setViolationSelected] = useState(null);

	const violations = useMemo(() => {
		return initialViolations.filter(({impact, tags}) => {
			let removeFlag = true;

			if (selectedImpact.length) {
				removeFlag = filterByImpact({
					receivedImpact: impact,
					selectedImpact,
				});
			}

			if (selectedCategories.length) {
				removeFlag = filterByCategories({
					receivedTags: tags,
					selectedCategories,
				});
			}

			return removeFlag;
		});
	}, [selectedCategories, selectedImpact, initialViolations]);

	const hasViolations = !!violations.length;

	if (!violationSelected) {
		return (
			<>
				<div className="sidebar-section">
					<ViolationsPanelHeaderTitle />
				</div>
				<div className="page-accessibility-tool__sidebar--violations-panel-header-description">
					{!hasViolations
						? Liferay.Language.get(
								'there-are-no-accessibility-violations-in-this-page'
						  )
						: Liferay.Language.get(
								'set-of-rules-violated-by-the-highlighted-issues'
						  )}
				</div>
				{hasViolations && (
					<ClayList className="list-group-flush">
						{violations.map((violation) => {
							const {id, impact, nodes, ...props} = violation;

							return (
								<Rule
									id={id}
									impact={impact}
									key={id}
									nodes={nodes}
									onListItemClick={(violation) =>
										setViolationSelected(violation)
									}
									quantity={nodes.length}
									subtext={impact}
									title={id}
									{...props}
								/>
							);
						})}
					</ClayList>
				)}
			</>
		);
	}

	const {description, helpUrl, id, impact, nodes} = violationSelected;

	return (
		<ViolationDescription
			description={description}
			helpUrl={helpUrl}
			id={id}
			impact={impact}
			nodes={nodes}
			onBack={() => setViolationSelected(null)}
		/>
	);
}
