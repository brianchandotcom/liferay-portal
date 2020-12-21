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
import classNames from 'classnames';
import React, {useEffect, useState} from 'react';

export const formatLabel = (label) => label.replace('_', '-');

export const formatIcon = (label) => formatLabel(label).toLowerCase();

export const TranslationManagerLabel = ({
	defaultLanguageId,
	languageId,
	translatedLanguageIds,
}) => {
	let className = 'label-warning';
	let label = Liferay.Language.get('not-translated');

	if (languageId === defaultLanguageId) {
		className = 'label-info';
		label = Liferay.Language.get('default');
	}
	else if (translatedLanguageIds[languageId]) {
		className = 'label-success';
		label = Liferay.Language.get('translated');
	}

	return (
		<span className="autofit-col">
			<span className={classNames('label', className)}>
				<span className="label-item label-item-expand">{label}</span>
			</span>
		</span>
	);
};

export default ({
	availableLanguageIds,
	defaultLanguageId = themeDisplay.getDefaultLanguageId(),
	editingLanguageId = themeDisplay.getDefaultLanguageId(),
	onActiveChange = () => {},
	onEditingLanguageIdChange,
	showUserView = false,
	translatedLanguageIds,
	className,
}) => {
	const [active, setActive] = useState(false);
	const [_availableLanguageIds, setAvailableLanguageIds] = useState({});
	const [available, setAvailable] = useState({});

	useEffect(() => {
		setAvailable(Liferay.Language.available);
	}, []);

	useEffect(() => {
		setAvailableLanguageIds(availableLanguageIds || available);
	}, [available, availableLanguageIds]);

	useEffect(() => {
		onActiveChange(active);
	}, [active, onActiveChange]);

	return (
		<ClayDropDown
			active={active}
			className={classNames('localizable-dropdown', className)}
			onActiveChange={(newVal) => setActive(newVal)}
			trigger={
				<ClayButton
					displayType="secondary"
					monospaced={!showUserView}
					small={showUserView}
					symbol={formatLabel(editingLanguageId)}
				>
					<span className="inline-item">
						<ClayIcon symbol={formatIcon(editingLanguageId)} />
					</span>

					{showUserView ? (
						<span className="localizable-dropdown-label ml-2">
							{available[editingLanguageId]}
						</span>
					) : (
						<span className="btn-section">
							{formatLabel(editingLanguageId)}
						</span>
					)}

					{showUserView && (
						<ClayIcon className="ml-2" symbol="caret-bottom" />
					)}
				</ClayButton>
			}
		>
			<ClayDropDown.ItemList className="localizable-dropdown-ul">
				{Object.keys(_availableLanguageIds).map((languageId, index) => (
					<ClayDropDown.Item
						className={classNames('autofit-row', {
							['localizable-item-default']:
								languageId === defaultLanguageId,
						})}
						key={index}
						onClick={() => {
							onEditingLanguageIdChange(languageId);
							setActive(false);
						}}
					>
						<span className="autofit-col autofit-col-expand">
							<span className="autofit-section">
								<span className="inline-item inline-item-before">
									<ClayIcon symbol={formatIcon(languageId)} />
								</span>

								{showUserView
									? available[languageId]
									: formatLabel(languageId)}
							</span>
						</span>

						{!showUserView && (
							<TranslationManagerLabel
								defaultLanguageId={defaultLanguageId}
								languageId={languageId}
								translatedLanguageIds={translatedLanguageIds}
							/>
						)}
					</ClayDropDown.Item>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
};
