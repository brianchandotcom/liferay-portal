/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useEffect, useState} from 'react';

export function formatLabel(label) {
	return label.replaceAll('_', '-');
}

export function formatIcon(label) {
	return formatLabel(label).toLowerCase();
}

export function TranslationManagerLabel({
	defaultLanguageId,
	languageId,
	translatedLanguageIds,
}) {
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
}

export default function TranslationManager({
	availableLanguageIds,
	defaultLanguageId = themeDisplay.getDefaultLanguageId(),
	editingLanguageId = themeDisplay.getDefaultLanguageId(),
	onActiveChange = () => {},
	onEditingLanguageIdChange,
	showUserView = false,
	translatedLanguageIds,
	className,
}) {
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
}
