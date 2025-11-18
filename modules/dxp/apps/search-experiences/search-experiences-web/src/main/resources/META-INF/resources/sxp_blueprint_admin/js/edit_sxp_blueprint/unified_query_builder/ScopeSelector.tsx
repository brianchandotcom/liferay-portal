/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {openSelectionModal} from 'frontend-js-components-web';
import React, {useContext, useState} from 'react';

import ThemeContext from '../../shared/ThemeContext';

type Scope = {
	erc: string;
	id: string;
	isActive: boolean;
	name: string;
	type: string;
};

export default function ScopeSelector({}) {
	const [collapseSection, setCollapseSection] = useState(false);
	const [scope, setScope] = useState<Scope[]>([]);

	const {
		namespace,
		selectScopeURL,
	}: {namespace: string; selectScopeURL: string} = useContext(ThemeContext);

	const _handleSelectScope = () => {
		openSelectionModal({
			id: `${namespace}selectScope`,
			onSelect: (selectedItem: {
				groupdescriptivename: string;
				groupexternalreferencecode: string;
				groupid: string;
				type?: string;
			}) => {
				if (!selectedItem) {
					return;
				}

				console.log(selectedItem);

				if (
					scope.find(
						(item) =>
							item.externalReferenceCode ===
							selectedItem.groupexternalreferencecode
					)
				) {
					return;
				}

				setScope([
					...scope,
					{
						erc: selectedItem.groupexternalreferencecode,
						id: selectedItem.groupid,
						isActive: true,
						name: selectedItem.groupdescriptivename,
						type: selectedItem.type || '',
					},
				]);
			},
			selectEventName: `${namespace}selectScope`,
			title: Liferay.Language.get('select-scope'),
			url: selectScopeURL,
		});
	};

	const _handleRemoveScope = (index: number) => {
		const newScope = [...scope];

		newScope.splice(index, 1);

		setScope(newScope);
	};

	return (
		<div className="sheet">
			<ClayLayout.Row justify="between">
				<ClayLayout.Col size={6}>
					<span className="text-6 text-weight-bold">
						{Liferay.Language.get('scope')}
					</span>
				</ClayLayout.Col>

				<ClayLayout.Col size={6}>
					<ClayButton
						aria-label={Liferay.Language.get('collapse')}
						className="c-ml-2 component-action float-right"
						displayType="unstyled"
						onClick={() => setCollapseSection(!collapseSection)}
					>
						<ClayIcon
							symbol={
								collapseSection ? 'angle-right' : 'angle-down'
							}
						/>
					</ClayButton>
				</ClayLayout.Col>
			</ClayLayout.Row>

			{!collapseSection && (
				<>
					<span className="text-4 text-secondary">
						{Liferay.Language.get('scope-selector-description')}
					</span>

					<div className="c-mt-4">
						<ClayButton
							displayType="secondary"
							onClick={_handleSelectScope}
						>
							<span className="inline-item inline-item-before">
								<ClayIcon symbol="plus" />
							</span>

							{Liferay.Language.get('select-scope')}
						</ClayButton>
					</div>

					{scope.map((scopeItem, index) => (
						<div
							className="c-mt-3 d-flex justify-content-between"
							key={index}
						>
							<span>
								{scopeItem.name} ({scopeItem.type})
							</span>

							<ClayButton
								aria-label={Liferay.Language.get('remove')}
								className="component-action"
								displayType="unstyled"
								onClick={() => _handleRemoveScope(index)}
							>
								<ClayIcon symbol="times" />
							</ClayButton>
						</div>
					))}
				</>
			)}
		</div>
	);
}
