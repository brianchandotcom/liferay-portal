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
import {ClaySelectWithOption} from '@clayui/form';
import ClayTable from '@clayui/table';
import classNames from 'classnames';
import {openSelectionModal} from 'frontend-js-web';
import React, {useMemo, useState} from 'react';

import {GlobalCETOptionsDropDown} from './GlobalCETOptionsDropDown';
import {GlobalCETOrderHelpIcon} from './GlobalCETOrderHelpIcon';

const INHERITED_LABELS: Record<IInheritedOptions, string> = {
	'layout-set': Liferay.Language.get('layout-set'),
	'master-layout': Liferay.Language.get('master'),
};

const DEFAULT_LOAD_TYPE_OPTION: ILoadTypeOptions = 'default';

const LOAD_TYPE_OPTIONS: Record<
	ILoadTypeOptions,
	React.OptionHTMLAttributes<HTMLOptionElement>
> = {
	async: {label: 'async', value: 'async'},
	default: {label: 'default', value: 'default'},
	defer: {label: 'defer', value: 'defer'},
};

export default function GlobalJSCETsConfiguration({
	globalJSCETSelectorURL,
	globalJSCETs: initialGlobalJSCETs,
	portletNamespace,
	selectGlobalJSCETsEventName,
}: IProps) {
	const fixedGlobalJSCETs = useMemo(
		() =>
			initialGlobalJSCETs.filter(
				(globalJSCET) => globalJSCET.inheritedFrom
			),
		[initialGlobalJSCETs]
	);

	const [globalJSCETs, setGlobalJSCETs] = useState(() =>
		initialGlobalJSCETs.filter((globalJSCET) => !globalJSCET.inheritedFrom)
	);

	const allGlobalJSCETs = useMemo(
		() => [...fixedGlobalJSCETs, ...globalJSCETs],
		[fixedGlobalJSCETs, globalJSCETs]
	);

	const deleteGlobalJSCET = (deletedGlobalJSCET: IGlobalJSCET) => {
		setGlobalJSCETs((previousGlobalJSCETs) =>
			previousGlobalJSCETs.filter(
				(globalJSCET) =>
					globalJSCET.cetExternalReferenceCode !==
					deletedGlobalJSCET.cetExternalReferenceCode
			)
		);
	};

	const updateGlobalJSCET = <T extends keyof IGlobalJSCET>(
		globalJSCET: IGlobalJSCET,
		propName: T,
		value: IGlobalJSCET[T]
	) => {
		setGlobalJSCETs((previousGlobalJSCETs) =>
			previousGlobalJSCETs.map((oldGlobalJSCET) =>
				oldGlobalJSCET === globalJSCET
					? {...globalJSCET, [propName]: value}
					: oldGlobalJSCET
			)
		);
	};

	const handleClick = () => {
		openSelectionModal<{value: string[]}>({
			multiple: true,
			onSelect(selectedItems) {
				if (!selectedItems.value) {
					return;
				}

				const items = selectedItems.value.map((selectedItem) =>
					JSON.parse(selectedItem)
				);

				setGlobalJSCETs((previousGlobalJSCETs) => {
					const nextGlobalJSCETs = [
						...previousGlobalJSCETs,
						...items,
					];

					return nextGlobalJSCETs.filter(
						(globalJSCET, index) =>
							nextGlobalJSCETs.findIndex(
								({cetExternalReferenceCode}) =>
									globalJSCET.cetExternalReferenceCode ===
									cetExternalReferenceCode
							) === index
					);
				});
			},
			selectEventName: selectGlobalJSCETsEventName,
			title: Liferay.Language.get('select-javascript-extensions'),
			url: globalJSCETSelectorURL,
		});
	};

	return (
		<div className="global-js-cets-configuration">
			{globalJSCETs.map(({cetExternalReferenceCode, loadType}) => (
				<input
					key={cetExternalReferenceCode}
					name={`${portletNamespace}globalJSCETExternalReferenceCodes`}
					type="hidden"
					value={`${cetExternalReferenceCode}_${
						loadType || DEFAULT_LOAD_TYPE_OPTION
					}`}
				/>
			))}

			<h3 className="sheet-subtitle">
				{Liferay.Language.get('javascript-extensions')}
			</h3>

			<ClayButton
				className="mb-3"
				displayType="secondary"
				onClick={handleClick}
				small
				type="button"
			>
				{Liferay.Language.get('add-javascript-extensions')}
			</ClayButton>

			{globalJSCETs.length ? (
				<ClayTable>
					<ClayTable.Head>
						<ClayTable.Row>
							<ClayTable.Cell headingCell>
								<GlobalCETOrderHelpIcon
									buttonId={`${portletNamespace}_GlobalJSCETsConfigurationOrderHelpIcon`}
									title={Liferay.Language.get(
										'loading-order'
									)}
								>
									{Liferay.Language.get(
										'numbers-indicate-in-which-order-extensions-are-loaded-extensions-inherited-from-master-will-always-be-loaded-in-first-place'
									)}
								</GlobalCETOrderHelpIcon>
							</ClayTable.Cell>

							<ClayTable.Cell expanded headingCell>
								{Liferay.Language.get('name')}
							</ClayTable.Cell>

							<ClayTable.Cell expanded headingCell>
								{Liferay.Language.get('load-type')}
							</ClayTable.Cell>

							<ClayTable.Cell headingCell noWrap>
								{Liferay.Language.get('inherited')}
							</ClayTable.Cell>

							<ClayTable.Cell headingCell>
								<span className="sr-only">
									{Liferay.Language.get('options')}
								</span>
							</ClayTable.Cell>
						</ClayTable.Row>
					</ClayTable.Head>

					<ClayTable.Body>
						{allGlobalJSCETs.map((globalJSCET, index) => (
							<ExtensionRow
								deleteGlobalJSCET={deleteGlobalJSCET}
								globalJSCET={globalJSCET}
								key={globalJSCET.cetExternalReferenceCode}
								order={index + 1}
								portletNamespace={portletNamespace}
								updateGlobalJSCET={updateGlobalJSCET}
							/>
						))}
					</ClayTable.Body>
				</ClayTable>
			) : (
				<p className="text-secondary">
					{Liferay.Language.get(
						'no-javascript-extensions-were-loaded'
					)}
				</p>
			)}
		</div>
	);
}

interface IExtensionRowProps {
	deleteGlobalJSCET: (globalJSCET: IGlobalJSCET) => unknown;
	globalJSCET: IGlobalJSCET;
	order: number;
	portletNamespace: string;
	updateGlobalJSCET: <T extends keyof IGlobalJSCET>(
		globalJSCET: IGlobalJSCET,
		propName: T,
		value: IGlobalJSCET[T]
	) => unknown;
}

function ExtensionRow({
	deleteGlobalJSCET,
	globalJSCET,
	order,
	portletNamespace,
	updateGlobalJSCET,
}: IExtensionRowProps) {
	const disabled = Boolean(globalJSCET.inheritedFrom);
	const dropdownTriggerId = `${portletNamespace}_GlobalJSCETsConfigurationOptionsButton_${globalJSCET.cetExternalReferenceCode}`;

	const dropdownItems = [
		{
			label: Liferay.Language.get('delete'),
			onClick: () => deleteGlobalJSCET(globalJSCET),
			symbolLeft: 'trash',
		},
	];

	const inheritedLabel = globalJSCET.inheritedFrom
		? Liferay.Util.sub(
				Liferay.Language.get('from-x'),
				INHERITED_LABELS[globalJSCET.inheritedFrom]
		  )
		: '-';

	return (
		<ClayTable.Row
			className={classNames({disabled})}
			key={globalJSCET.cetExternalReferenceCode}
		>
			<ClayTable.Cell>{order}</ClayTable.Cell>

			<ClayTable.Cell expanded headingTitle>
				{globalJSCET.name}
			</ClayTable.Cell>

			<ClayTable.Cell expanded>
				<ClaySelectWithOption
					defaultValue={
						globalJSCET.loadType || DEFAULT_LOAD_TYPE_OPTION
					}
					onChange={(event) =>
						updateGlobalJSCET(
							globalJSCET,
							'loadType',
							event.target.value as ILoadTypeOptions
						)
					}
					options={Object.values(LOAD_TYPE_OPTIONS)}
					sizing="sm"
				/>
			</ClayTable.Cell>

			<ClayTable.Cell noWrap>{inheritedLabel}</ClayTable.Cell>

			<ClayTable.Cell>
				{disabled ? null : (
					<GlobalCETOptionsDropDown
						dropdownItems={dropdownItems}
						dropdownTriggerId={dropdownTriggerId}
					/>
				)}
			</ClayTable.Cell>
		</ClayTable.Row>
	);
}

type IInheritedOptions = 'layout-set' | 'master-layout';
type ILoadTypeOptions = 'default' | 'async' | 'defer';

interface IGlobalJSCET {
	cetExternalReferenceCode: string;
	inheritedFrom: IInheritedOptions | null;
	loadType?: ILoadTypeOptions;
	name: string;
}

interface IProps {
	globalJSCETSelectorURL: string;
	globalJSCETs: IGlobalJSCET[];
	portletNamespace: string;
	selectGlobalJSCETsEventName: string;
}
