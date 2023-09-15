/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayResultsBar} from '@clayui/management-toolbar';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {Dispatch, SetStateAction} from 'react';

interface SidebarHeaderProps {
	navHistory: ObjectDefinition[][];
	onBackClick: voidReturn;
	setNavHistory: Dispatch<SetStateAction<ObjectDefinition[][]>>;
	setSearchKeyword: Dispatch<SetStateAction<string>>;
	setViewRelatedObjects: Dispatch<SetStateAction<boolean>>;
	viewRelatedObjects: boolean;
}

export default function SidebarHeader({
	navHistory,
	setNavHistory,
	setSearchKeyword,
	viewRelatedObjects,
}: SidebarHeaderProps) {
	const getParentLabel = (history: ObjectDefinition[][]) => {
		let label;

		for (const item of history[1]) {
			const matches: boolean[] = [];

			if (item.objectRelationships.length === history[0].length) {
				item.objectRelationships.forEach(
					({objectDefinitionId2}, index) =>
						matches.push(
							objectDefinitionId2 === history[0][index].id
						)
				);
			}

			if (matches.length && !matches.includes(false)) {
				label = item.label[Liferay.ThemeDisplay.getDefaultLanguageId()];

				break;
			}
		}

		return label ?? '';
	};

	return (
		<div
			className={classNames({
				'no-padding-bottom': viewRelatedObjects,
				'sidebar-header': true,
			})}
		>
			<span className="sidebar-header-title">
				{Liferay.Language.get('properties')}
			</span>

			<div className="card-divider"></div>

			<div className="search-container">
				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayInput
							aria-label={Liferay.Language.get('search')}
							className="form-control input-group-inset input-group-inset-after"
							onChange={({target: {value}}) =>
								setSearchKeyword(value)
							}
							placeholder={Liferay.Language.get('search')}
							type="text"
						/>

						<ClayInput.GroupInsetItem
							after
							className="pr-3"
							tag="span"
						>
							<ClayIcon symbol="search" />
						</ClayInput.GroupInsetItem>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</div>

			{navHistory?.length > 1 && (
				<>
					<div className="card-divider" />

					<div className="related-objects-results-bar-container">
						<ClayResultsBar>
							<ClayResultsBar.Item className="results-angle-left">
								<ClayButtonWithIcon
									aria-label="Back"
									displayType="unstyled"
									onClick={() =>
										navHistory.length > 1 &&
										setNavHistory([...navHistory.slice(1)])
									}
									symbol="angle-left"
								/>
							</ClayResultsBar.Item>

							<ClayResultsBar.Item className="results-info">
								<span className="component-text text-truncate">
									{sub(
										Liferay.Language.get(
											'x-related-objects-for-x'
										),
										navHistory[0].length,
										`"${getParentLabel(navHistory)}`
									)}
								</span>
							</ClayResultsBar.Item>

							<ClayResultsBar.Item className="results-close-button">
								<ClayButtonWithIcon
									aria-label="Search"
									displayType="unstyled"
									onClick={() =>
										setNavHistory((previous) => [
											previous[previous.length - 1],
										])
									}
									symbol="times-circle"
								/>
							</ClayResultsBar.Item>
						</ClayResultsBar>
					</div>
				</>
			)}
		</div>
	);
}
